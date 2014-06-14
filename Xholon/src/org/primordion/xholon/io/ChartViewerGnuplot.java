/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2006, 2007, 2008 Ken Webb
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
 */

package org.primordion.xholon.io;

//import java.io.File;
//import java.io.IOException;
//import java.io.Writer;
import java.util.Date;

import org.primordion.xholon.base.IXholon;
//import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.base.XholonWithPorts;
//import org.primordion.xholon.util.MiscIo;

/**
 * <p>Captures data, and creates a script to display the data in a gnuplot chart.</p>
 * <p>Data is captured by writing data points to a file at each time step.
 * The file is in comma delimited .csv format, and can therefore be processed by various applications,
 * including Microsoft Excel, and gnuplot. In addition to writing out the data, this class also
 * produces a gnuplot script file that will generate a .png file from the data. You can download
 * gnuplot free from www.gnuplot.info. On a Windows computer, if you double-click
 * the .csv file, it will be directly loaded into Excel as a spreadsheet, from which you can
 * easily generate a chart. Data and script files are written by default to the statistics directory.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://www.gnuplot.info/">Gnuplot website</a>
 * @see <a href="https://github.com/chhu/gnuplot-JS">An API to a JavaScript gnuplot port using emscripten</a>
 * @see <a href="http://gnuplot.respawned.com/">Gnuplot online</a>
 * @since 0.3 (Created on April 24, 2006)
 */
@SuppressWarnings("serial")
public class ChartViewerGnuplot extends AbstractChartViewer implements IChartViewer {

	protected String pathName = null;
	protected String typeOfData = null;
	protected int maxTimeSteps = 0;
	//protected Writer dOut = null;      // data file
	StringBuilder sbd = null; // accumulated data
	protected long dateTime = 0;           // unique date/time used as part of data and script file names
	protected String statsSeries = null;   // part of the file names, to allow various series of data
	protected String dataDelimiter = null; // delimiter between data items on a line (ex: one,two)
	
	// Formats of various files
	protected String graphicFormat = null; // ex: "png" png (also "svg" "gif" "postscript" and many others)
	protected String dataFormat    = null; // ex: "csv" comma delimited; can be read in by Excel and other spreadsheets
	protected String scriptFormat  = null; // ex: "plt" gnuplot
	protected String textFormat    = null; // ex: "txt" notes
	
	// Various labels and parameters of the plot
	protected String xLabel = null;
	protected String yLabel = null;
	protected String title = null;
	protected String gpTitle1 = null;
	protected String gpTitle2 = null;
	protected String gpTitle3 = null;
	protected String commentString = null;
	protected int yTicksSpacing = 0;
	protected boolean useNotes = false;
	protected boolean showLegend = true;
	protected boolean useScript = true;
	
	// Array of names of series that are not connected to tree nodes
	protected String seriesNameNonNode[] = null;
	
	// GWT gnuplot-JS
	protected String outFileName = null; // name of the output data file
	protected String outPathCsv = "gnuplot/csv/";
	protected String outPathPlt = "gnuplot/plt/";
	protected boolean useGnuplotJS = true;
	protected int terminalWidth = 1024;
	protected int terminalHeight = 768;
	
	/**
	 * default constructor
	 */
	public ChartViewerGnuplot() {}
	
	/*
	 * @see org.primordion.xholon.io.IChartViewer#initialize(org.primordion.xholon.base.IXholon, int, java.lang.String, int, java.lang.String)
	 */
	public void initialize(IXholon chartRoot, int nameConcatLevels, String typeOfData, int writeType, String pathName)
	{
	  //consoleLog("ChartViewerGnuplot initialize() start");
		this.chartRoot = (XholonWithPorts)chartRoot;
		this.nameConcatLevels = nameConcatLevels;
		this.pathName = pathName;
		this.typeOfData = typeOfData;
		this.writeType = writeType;
		init();
	}
	
	/**
	 * Initialize the chart viewer.
	 * Create datasets to store each type of data that's being captured for display in a chart.
	 */
	protected void init()
	{
	  //consoleLog("ChartViewerGnuplot init() start");
		int i;
		dateTime = new Date().getTime();
		statsSeries = "a";
		dataDelimiter = ",";
		commentString = "#";
		yTicksSpacing = 10000;
		gpTitle1 = "Gnuplot script file, generated from a Xholon application.";
		
		// Formats of various files
		if (useGnuplotJS) {
		  graphicFormat = "svg";
		}
		else {
		  graphicFormat = "png"; // .png
		}
		dataFormat    = "csv"; // .csv comma delimited; can be read in by Excel and other spreadsheets
		scriptFormat  = "plt"; // .plt gnuplot
		textFormat    = "txt"; // .txt notes
		
		seriesCount = 0;
		XholonWithPorts node = (XholonWithPorts)chartRoot.getFirstChild();
		while ((node != null) && (!(node.getXhc().hasAncestor("XYSeries")))) {
			node = (XholonWithPorts)node.getNextSibling();
		}
		firstXYSeries = node;
		// count number of XYSeries required
		while ((node != null) && (node.getXhc().hasAncestor("XYSeries"))) {
			seriesCount++;
			node = (XholonWithPorts)node.getNextSibling();
		}
		// open file
		if (seriesCount > 0) {
			sbd = new StringBuilder();
			outFileName = pathName + statsSeries + dateTime + "_" + typeOfData + "." + dataFormat;
			// create any missing output directories
			//File dirOut = new File(pathName);
			//dirOut.mkdirs(); // will create a new directory only if there is no existing one
			//dOut = MiscIo.openOutputFile(pathName + statsSeries + dateTime + "_" + typeOfData + "." + dataFormat);
			sbd.append( commentString ); // put comment string at start of heading line
		}
		// write headings
		node = firstXYSeries;
		if (sbd != null) {
			sbd.append("time" + dataDelimiter);
			String seriesName = null;
			for (i = 0; i < seriesCount; i++) {
				// concatenate 1 or 2 levels; not likely to be more than 2
				if (nameConcatLevels == 1) {
					seriesName = node.getPort(0).getName();
				}
				else {
					seriesName = (
							(XholonWithPorts)node.getPort(0).getParentNode()).getXhcName()
							+ "__"
							+ node.getPort(0).getName();
				}
				sbd.append(seriesName  + dataDelimiter);
				node = (XholonWithPorts)node.getNextSibling();
			}
			sbd.append( "\n");
		}
		// notes
		createNotes();
		// script
		createScript();
	}
	
	/*
	 * @see org.primordion.xholon.io.IChartViewer#createXySeries(int, java.lang.String[])
	 * this method is never called; init() does the work instead
	 */
	public void createXySeries(int seriesCount, String seriesName[])
	{
	  this.seriesNameNonNode = seriesName;
		if (sbd == null) {
			sbd = new StringBuilder();
			//dOut = MiscIo.openOutputFile(pathName + statsSeries + dateTime + "_" + typeOfData + "." + dataFormat);
			sbd.append( commentString + "time" + dataDelimiter); // put comment string at start of heading line
		}
		this.seriesCount = seriesCount;
		if (seriesCount > 0) {
			for (int i = 0; i < seriesCount; i++) {
				sbd.append(seriesName[i]  + dataDelimiter);
			}
			sbd.append( "\n");
		}
	}

	/*
	 * @see org.primordion.xholon.io.IChartViewer#capture(int)
	 */
	public void capture(double timeStep) {
	  //consoleLog("ChartViewerGnuplot capture1() start " + timeStep);
		if (sbd == null) {return;}
		XholonWithPorts node = firstXYSeries;
		sbd.append(timeStep + dataDelimiter);
		for (int i = 0; i < seriesCount; i++) {
			switch (writeType) {
			case WRITE_AS_LONG:
			case WRITE_AS_INT:
			case WRITE_AS_SHORT:
				sbd.append((long)node.getPort(0).getVal()).append(dataDelimiter);
				break;
			case WRITE_AS_DOUBLE:
			case WRITE_AS_FLOAT:
				sbd.append(node.getPort(0).getVal()).append(dataDelimiter);
				break;
			default:
				break;
			}
			node = (XholonWithPorts)node.getNextSibling();
		}
		sbd.append( "\n");
		maxTimeSteps++;
	}

	/*
	 * @see org.primordion.xholon.io.IChartViewer#capture(int, double, double[])
	 * this method isn't called
	 */
	public void capture(int numTimeSeries, double xVal, double yVal[])
	{
	  //consoleLog("ChartViewerGnuplot capture2() start " + numTimeSeries);
		if (sbd == null) {return;}
		sbd.append(xVal + dataDelimiter);
		for (int i = 0; i < numTimeSeries; i++) {
			sbd.append(yVal[i]).append(dataDelimiter);
		}
		sbd.append( "\n");
		maxTimeSteps++;
	}
	
	/*
	 * @see org.primordion.xholon.io.IChartViewer#chart()
	 */
	public void chart() {
		chart(true);
	}
	
	/*
	 * @see org.primordion.xholon.io.IChartViewer#chart(boolean)
	 */
	public void chart(boolean showLegend)
	{
	  //consoleLog("ChartViewerGnuplot chart() start " + showLegend);
		this.showLegend = showLegend;
		/* GWT
		try {
			dOut.write(sbd.toString());
			dOut.flush();
		} catch (IOException e) {
			Xholon.getLogger().error("", e);
		}*/
		//consoleLog(sbd.toString());
		//println(sbd.toString()); // for use by GWT
		writeToTarget(sbd.toString(), outFileName, outPathCsv, chartRoot);
	}
	
	/**
	 * Create a script or program that will process the saved data.
	 */
	protected void createScript() {
		if (!useScript) {return;}
		//System.out.println("ChartViewerGnuplot Creating gnuplot script ...");
		StringBuilder sbs = new StringBuilder();
		String fileName = statsSeries + dateTime + "_" + typeOfData;
		String dataFileName = fileName + "." + dataFormat;
		
		// write out the lines of the gnuplot file
		sbs.append( commentString + " " + gpTitle1 + "\n" );
		sbs.append( commentString + " Data file: " + fileName + "." + dataFormat + "\n");
		sbs.append( commentString + " see: http://gnuplot.respawned.com/\n");
		if (!useGnuplotJS) {sbs.append( "reset\n" );}
		
		if (useGnuplotJS) {
		  // gnuplot-JS  set output 'out.svg'
		  sbs
		  .append( "set output \"")
		  .append("out")
		  .append(".")
		  .append(graphicFormat)
		  .append("\"\n");
		  // gnuplot-JS  set terminal svg size 400,300 enhanced fname 'arial'  fsize 10 butt solid
		  sbs
		  .append( "set terminal ")
		  .append(graphicFormat)
		  .append(" size ").append(terminalWidth).append(",").append(terminalHeight)
		  .append(" enhanced fname 'arial' fsize 10 butt solid")
		  .append("\n" );
		}
		else {
		  sbs.append( "set output \"" + fileName + "." + graphicFormat + "\"\n");
		  sbs.append( "set terminal " + graphicFormat + " size 1024,768\n" );
		}
		
		sbs.append( "set datafile separator \"" + dataDelimiter + "\"\n" );
		sbs.append( "set xlabel \"" + ((XholonWithPorts)chartRoot.getFirstChild()).getRoleName() + "\"\n" );
		sbs.append( "set ylabel \"" + ((XholonWithPorts)chartRoot.getFirstChild().getNextSibling()).getRoleName() + "\"\n" );
		//sbs.append( "unset ytics\n" );
		//sbs.append( "set y2tics border mirror " + yTicksSpacing + "\n" );
		//sbs.append( "set format y \"%.0f\"\n");
		sbs.append( "set format y \"" + yFormat + "\"\n");
		sbs.append( "set mxtics 5\n" );
		sbs.append( "set border 13\n" );
		sbs.append( "set title \"" + chartRoot.getRoleName() + " (" + dateTime + ")\"\n" );
		if (showLegend) {
			sbs.append( "set key right box 3\n" );
		}
		else {
			sbs.append( "unset key\n" );
		}
		//sbs.append( "set xrange [0:" + maxTimeSteps + "]\n" );
		if (setXRangeManually) {
			sbs.append( "set xrange [" + minX + ":" + maxX + "]\n" );
		}
		if (setYRangeManually) {
			sbs.append( "set yrange [" + minY + ":" + maxY + "]\n" );
		}
		// otherwise xrange and yrange handled automatically by gnuplot
		//sbs.append( commentString + "set yrange [0:25]\n" );
		// skip timeStep, which is the first value in each row
		// gnuplot-JS  plot  "data.txt" using 1:2 title 'Col-Force' with lines, "data.txt" using 1:3 title 'Beam-Force' with linespoints
		XholonWithPorts node = firstXYSeries;
		String seriesName = null;
		for (int i = 0; i < seriesCount; i++) {
			if (i == 0) {
				sbs.append( "plot \"" );
			}
			else {
				sbs.append( "     \"" );
			}
			
			sbs
			.append(useGnuplotJS ? "data.txt" : dataFileName)
			.append("\" using ")
			.append(i+2)
			.append(" title \"");
			
			if (node != null) { // if names are contained in tree nodes of the app
				if (nameConcatLevels == 1) {
					seriesName = node.getPort(0).getName();
				}
				else {
					seriesName = (
							(XholonWithPorts)node.getPort(0).getParentNode()).getXhcName()
							+ "__"
							+ node.getPort(0).getName();
				}
				sbs.append( seriesName );
				node = (XholonWithPorts)node.getNextSibling();
			}
			else {
				sbs.append( seriesNameNonNode[i]);
			}
			
			if (useGnuplotJS) {
			  sbs.append( "\" with lines");
			}
			else {
			  sbs.append( "\" with lines " + (i+1) );
			}
			
			if (i == seriesCount - 1) { // last entry to plot
				sbs.append("\n");
			}
			else {
				sbs.append( ", \\\n" );
			}
		}
		if (!useGnuplotJS) {sbs.append( "pause -1 \"Hit return to exit\"\n" );}
		
		// create any missing output directories
		/* GWT
		File dirOut = new File(pathName);
		dirOut.mkdirs(); // will create a new directory only if there is no existing one
		Writer sOut = MiscIo.openOutputFile(pathName + fileName + "." + scriptFormat);
		try {
			sOut.write(sbs.toString());
		} catch (IOException e) {
			Xholon.getLogger().error("", e);
			//e.printStackTrace();
		}
		MiscIo.closeOutputFile(sOut);
		*/
		//println(sbs.toString()); // for use by GWT
		writeToTarget(sbs.toString(), dataFileName, outPathPlt, chartRoot);
	}
	
	/**
	 * Create a notes file that can be used by a human to capture observations as the simulation is running.
	 * It may be inititilized with various parameters relevant to the application.
	 */
	public void createNotes()
	{
		if (!useNotes) {return;}
		StringBuilder sbn = new StringBuilder();
		String fileName = statsSeries + dateTime + "_" + typeOfData;
		// write out some lines of text
		sbn.append( commentString + " " + gpTitle1 + "\n" );
		sbn.append( commentString + " Data file: " + fileName + "." + dataFormat + "\n");
		
		// create any missing output directories
		/* GWT
		File dirOut = new File(pathName);
		dirOut.mkdirs(); // will create a new directory only if there is no existing one
		Writer nOut = MiscIo.openOutputFile(pathName + fileName + "." + textFormat);
		if (nOut == null) {return;}
		try {
			nOut.write(sbn.toString());
		} catch (IOException e) {
			Xholon.getLogger().error("ChartViewerGnuplot: unable to write notes file.", e);
		}
		MiscIo.closeOutputFile(nOut);
		*/
		println(sbn.toString()); // for use by GWT
	}
	
	/*
	 * @see org.primordion.xholon.io.IChartViewer#remove()
	 */
	public void remove() {} // don't need to do anything
}

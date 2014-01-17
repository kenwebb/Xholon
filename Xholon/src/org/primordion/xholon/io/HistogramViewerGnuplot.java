/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2007, 2008 Ken Webb
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

//import java.io.IOException;
//import java.io.Writer;
import java.util.Date;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.base.XholonWithPorts;
//import org.primordion.xholon.util.MiscIo;

/**
 * <p>Captures data, and creates a histogram using gnuplot.</p>
 * <p>The file is in comma delimited .csv format, and can therefore be processed by various applications,
 * including Microsoft Excel, and gnuplot. In addition to writing out the data, this class also
 * produces a gnuplot script file that will generate a .png file from the data. You can download
 * gnuplot free from www.gnuplot.info. On a Windows computer, if you double-click
 * the .csv file, it will be directly loaded into Excel as a spreadsheet, from which you can
 * easily generate a chart. Data and script files are written by default to the statistics directory.</p>
</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.6 (Created on May 3, 2007)
 */
public class HistogramViewerGnuplot extends AbstractHistogramViewer implements IHistogramViewer {
	
	protected String pathName;
	protected String typeOfData;
	protected int writeType;
	
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
	
	/**
	 * default constructor
	 */
	public HistogramViewerGnuplot() {}
	
	/**
	 * constructor
	 * @param histRootModel The root node of a Xholon subtree in the model,
	 * that will be traversed looking for instances of the xholon class.
	 * @param histRootView Root node in the view. This is the "HistogramViewer" node.
	 * @param xhClass A Xholon class whose instances will be queried for values, using getVal().
	 * @param numBins Number of bins.
	 * @param nameConcatLevels Number of composite levels to concatenate in deriving name.
	 * @param pathName Path in which data and plot files will be created.
	 * @param typeOfData The type of data will be included as part of the file name.
	 * @param writeType Type of data to write.
	 */
	public HistogramViewerGnuplot(
			IXholon histRootModel,
			IXholon histRootView,
			IXholonClass xhClass,
			int numBins,
			int nameConcatLevels,
			String pathName,
			String typeOfData,
			int writeType)
	{
		initialize(histRootModel, histRootView, xhClass, numBins, nameConcatLevels, pathName, typeOfData, writeType);
	}
	
	/**
	 * This method may not be useful for the Gnuplot concrete class.
	 * @see org.primordion.xholon.io.IHistogramViewer#initialize(org.primordion.xholon.base.IXholon, org.primordion.xholon.base.IXholon, org.primordion.xholon.base.IXholonClass, int, int)
	 */
	public void initialize(IXholon histRootModel, IXholon histRootView, IXholonClass xhClass, int numBins, int nameConcatLevels)
	{
		this.histRootModel = (XholonWithPorts)histRootModel;
		this.histRootView = (XholonWithPorts)histRootView;
		this.xhClass = xhClass;
		this.numBins = numBins;
		this.nameConcatLevels = nameConcatLevels;
	}

	/**
	 * Initialize the histogram viewer.
	 * @param histRootModel The root node of a Xholon subtree in the model,
	 * that will be traversed looking for instances of the xholon class.
	 * @param histRootView Root node in the view. This is the "HistogramViewer" node.
	 * @param xhClass A Xholon class whose instances will be queried for values, using getVal().
	 * @param numBins Number of bins.
	 * @param nameConcatLevels Number of composite levels to concatenate in deriving name.
	 * @param pathName Path in which data and plot files will be created.
	 * @param typeOfData The type of data will be included as part of the file name.
	 * @param writeType Type of data to write.
	 */
	public void initialize(
			IXholon histRootModel,
			IXholon histRootView,
			IXholonClass xhClass,
			int numBins,
			int nameConcatLevels,
			String pathName,
			String typeOfData,
			int writeType)
	{
	  consoleLog("HistogramViewerGnuplot initialize start");
	  consoleLog(histRootModel);
	  consoleLog(histRootView);
	  consoleLog(xhClass);
	  consoleLog(numBins);
	  consoleLog(nameConcatLevels);
	  consoleLog(pathName);
	  consoleLog(typeOfData);
	  consoleLog(writeType);

		this.histRootModel = (XholonWithPorts)histRootModel;
		this.histRootView = (XholonWithPorts)histRootView;
		this.xhClass = xhClass;
		this.numBins = numBins;
		this.nameConcatLevels = nameConcatLevels;
		this.pathName = pathName;
		this.typeOfData = typeOfData;
		this.writeType = writeType;
		
		dateTime = new Date().getTime();
		statsSeries = "h";
		dataDelimiter = ",";
		commentString = "#";
		yTicksSpacing = 10000;
		gpTitle1 = "Gnuplot script file, generated from a Xholon application.";
		
		// Formats of various files
		graphicFormat = "png"; // .png
		dataFormat    = "csv"; // .csv comma delimited; can be read in by Excel and other spreadsheets
		scriptFormat  = "plt"; // .plt gnuplot
		textFormat    = "txt"; // .txt notes
		
		consoleLog("HistogramViewerGnuplot initialize end");
	}
	
	/*
	 * @see org.primordion.xholon.io.AbstractHistogramViewer#chart(double[])
	 */
	public void chart(double[] values) {
		consoleLog("gnuplot histograms are not yet completely implemented");
		/*try {
			if (dOut == null) {
				dOut = MiscIo.openOutputFile(pathName + statsSeries + dateTime + "_" + typeOfData + "." + dataFormat);
				dOut.write( commentString + "time" + "\n"); // put comment string at start
			}
			for (int i = 0; i < values.length; i++) {
				dOut.write(values[i] + "\n");
			}
			MiscIo.closeOutputFile( dOut );
		} catch (IOException e) {
			Xholon.getLogger().error("", e);
			//e.printStackTrace();
		}*/
		
		if (sbd == null) {
			sbd = new StringBuilder();
		}
		// put comment string at start
		sbd
		.append(commentString)
		.append("time")
		.append("\n");
		for (int i = 0; i < values.length; i++) {
			sbd
			.append(values[i])
			.append("\n");
		}
		println(sbd.toString()); // for use by GWT
	}

	/*
	 * @see org.primordion.xholon.io.AbstractHistogramViewer#remove()
	 */
	public void remove() {} // don't need to do anything
}

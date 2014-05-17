/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2013 Ken Webb
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

import java.util.Date;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
//import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.base.XholonWithPorts;

/**
 * Captures data, and creates a histogram using D3.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on December 17, 2013)
 */
public class HistogramViewerD3 extends AbstractHistogramViewer implements IHistogramViewer {
	
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
	public HistogramViewerD3() {}
	
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
	public HistogramViewerD3(
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
	  //consoleLog("HistogramViewerD3 initialize start");
	  //consoleLog(histRootModel);
	  //consoleLog(histRootView);
	  //consoleLog(xhClass);
	  //consoleLog(numBins);
	  //consoleLog(nameConcatLevels);
	  //consoleLog(pathName);
	  //consoleLog(typeOfData);
	  //consoleLog(writeType);

		this.histRootModel = (XholonWithPorts)histRootModel;
		this.histRootView = (XholonWithPorts)histRootView;
		this.xhClass = xhClass;
		this.numBins = numBins;
		this.nameConcatLevels = nameConcatLevels;
		this.pathName = pathName;
		this.typeOfData = typeOfData;
		this.writeType = writeType;
		
		dateTime = new Date().getTime();
		//statsSeries = "h";
		//dataDelimiter = ",";
		//commentString = "#";
		//yTicksSpacing = 10000;
		//gpTitle1 = "Gnuplot script file, generated from a Xholon application.";
		
		// Formats of various files
		//graphicFormat = "png"; // .png
		//dataFormat    = "csv"; // .csv comma delimited; can be read in by Excel and other spreadsheets
		//scriptFormat  = "plt"; // .plt gnuplot
		//textFormat    = "txt"; // .txt notes
		
		consoleLog("HistogramViewerD3 initialize end");
	}
	
	/*
	 * @see org.primordion.xholon.io.AbstractHistogramViewer#chart(double[])
	 */
	public void chart(double[] values) {
	  // test d3 with values between 0 and 1; for Stupid Model 6, timestep 15
	  for (int i = 0; i < values.length; i++) {
	    values[i] = values[i];
	  }
		chartD3(values, numBins, 960, 500);
	}
	
	/**
	 * see http://bl.ocks.org/mbostock/3048450
	 */
	protected native void chartD3(double[] values, int numBins, int widthArg, int heightArg) /*-{
	  $wnd.console.log(values);
	  $wnd.console.log(numBins);
	  
	  var minX = $wnd.d3.min(values);
	  var maxX = $wnd.d3.max(values);
	  $wnd.console.log(minX);
	  $wnd.console.log(maxX);
	  
		// A formatter for counts.
    var formatCount = $wnd.d3.format(",.0f");

    var margin = {top: 10, right: 30, bottom: 30, left: 30},
        width = widthArg - margin.left - margin.right,
        height = heightArg - margin.top - margin.bottom;

    var x = $wnd.d3.scale.linear()
        .domain([0, maxX]) // domain x has to be 0
        .range([0, width]);
    
    // Generate a histogram using twenty uniformly-spaced bins.
    var data = $wnd.d3.layout.histogram()
        .bins(x.ticks(numBins))
        (values);
    $wnd.console.log(data);

    var y = $wnd.d3.scale.linear()
        .domain([0, $wnd.d3.max(data, function(d) { return d.y; })])
        .range([height, 0]);
    
    var xAxis = $wnd.d3.svg.axis()
        .scale(x)
        .orient("bottom");

    var svg = $wnd.d3.select("#xhchart").append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
      .append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

    var bar = svg.selectAll(".bar")
        .data(data)
      .enter().append("g")
        .attr("class", "bar")
        .attr("transform", function(d) { return "translate(" + x(d.x) + "," + y(d.y) + ")"; });

    bar.append("rect")
        .attr("x", 1)
        .attr("width", x(data[0].dx) - 1)
        .attr("height", function(d) { return height - y(d.y); });

    bar.append("text")
        .attr("dy", ".75em")
        .attr("y", 6)
        .attr("x", x(data[0].dx) / 2)
        .attr("text-anchor", "middle")
        .text(function(d) { return formatCount(d.y); });

    svg.append("g")
        .attr("class", "x axis")
        .attr("transform", "translate(0," + height + ")")
        .call(xAxis);
	}-*/;

	/*
	 * @see org.primordion.xholon.io.AbstractHistogramViewer#remove()
	 */
	public void remove() {} // don't need to do anything
}

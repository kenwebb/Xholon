/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2014 Ken Webb
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
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.io.gwt.HtmlScriptHelper;

/**
 * <p>Captures data, and displays the data in a web browser as a C3.js line chart.
 * C3.js is a "D3-based reusable chart library".</p>
 * Complete working example of a C3 line chart:
 * <pre>
<html>
  <head>
    <link rel="stylesheet" type="text/css" href="lib/c3.css">
  </head>
  <body>
    <div id="chart"></div>
    <script src="d3.v3.min.js" charset="utf-8"></script>
    <script src="lib/c3.min.js"></script>
    <script>
      var chart = c3.generate({
        bindto: '#chart',
        data: {
          x: 'x',
          columns: [
            ['x', 130, 140, 200, 300, 450, 550],
            ['one', 200, 350, 100, 200, 50, 100],
            ['two', 210, 360, 90, 220, 45, 150]
          ]
        }
      });
    </script>
  </body>
</html>
 * </pre>
 * TODO
 * - don't show the small filled circles along the lines
 * - show a title for the chart
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://c3js.org/">C3.js website</a>
 * @since 0.9.1 (Created on March 15, 2014)
 */
@SuppressWarnings("serial")
public class ChartViewerC3 extends AbstractChartViewer implements IChartViewer {

  protected String typeOfData = null;
  protected int maxTimeSteps = 0;
  protected StringBuilder sbd = null; // accumulated data/script
  protected StringBuilder[] sbXYSeries = null; // one StringBuilder per XYSeries
  protected StringBuilder sbTimeStep = null; // optionally include all the time steps as 'x'
  protected boolean shouldIncludeTimeSteps = true;
  
  protected long dateTime = 0;           // unique date/time used as part of data and script file names
  protected String statsSeries = null;   // part of the file names, to allow various series of data
  protected String dataDelimiter = null; // delimiter between data items on a line (ex: one,two)
  
  /**
   * ID of the HTML element where the chart will be rendered.
   */
  protected String elementId = "xhchart";
  
  // Various labels and parameters of the plot
  protected String xLabel = null;
  protected String yLabel = null;
  protected String title = null;
  protected int yTicksSpacing = 0;
  protected boolean showLegend = true;
  protected String legend = null;
  protected int width = 900; //600;
  protected int height = 525; //350;
  
  // Array of names of series that are not connected to tree nodes
  protected String seriesNameNonNode[] = null;
  
  /**
   * Colors that can be used to draw lines in a line chart.
   */
  protected String seriesColor[] = {
      "FF0000","00FF00","0000FF","FFFF00",
      "FF00FF","00FFFF","007FFF","7F00FF",
      "7FFF00","FF007F"};
  
  /**
   * default constructor
   */
  public ChartViewerC3() {}
  
  /*
   * @see org.primordion.xholon.io.IChartViewer#initialize(org.primordion.xholon.base.IXholon, int, java.lang.String, int, java.lang.String)
   */
  public void initialize(IXholon chartRoot, int nameConcatLevels, String typeOfData, int writeType, String pathName)
  {
    this.chartRoot = (XholonWithPorts)chartRoot;
    this.nameConcatLevels = nameConcatLevels;
    this.typeOfData = typeOfData;
    this.writeType = writeType;
    init();
  }
  
  /**
   * Initialize the chart viewer.
   */
  protected void init()
  {
    dateTime = new Date().getTime();
    statsSeries = "a";
    dataDelimiter = ",";
    yTicksSpacing = 10000;
    
    legend = "";
    seriesCount = 0;
    if (shouldIncludeTimeSteps) {
      sbTimeStep = new StringBuilder().append("['x'");
    }
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
    if (seriesCount > 0) {
      sbd = new StringBuilder();
      
      // initialize the StringBuffer for each XYSeries
      node = firstXYSeries;
      sbXYSeries = new StringBuilder[seriesCount];
      for (int i = 0; i < seriesCount; i++) {
        String columnName = "";
        if (nameConcatLevels == 1) {
          columnName += node.getPort(0).getName();
        }
        else {
          columnName += (
              (XholonWithPorts)node.getPort(0).getParentNode()).getXhcName()
              + "__"
              + node.getPort(0).getName();
        }
        sbXYSeries[i] = new StringBuilder()
        .append("['")
        .append(columnName)
        .append("'");
        node = (XholonWithPorts)node.getNextSibling();
      }
      
      // load the C3 .js and .css files
      if (!isDefinedC3()) {
        //chartRoot.consoleLog("about to call loadC3()");
        loadC3();
      }
    }
  }
  
  /**
   * TODO fix this, or delete
   * @see org.primordion.xholon.io.IChartViewer#createXySeries(int, java.lang.String[])
   */
  public void createXySeries(int seriesCount, String seriesName[])
  {
    this.seriesNameNonNode = seriesName;
    if (sbd == null) {}
    this.seriesCount = seriesCount;
    if (seriesCount > 0) {
      for (int i = 0; i < seriesCount; i++) {}
    }
  }

  /*
   * @see org.primordion.xholon.io.IChartViewer#capture(int)
   */
  public void capture(double timeStep) {
    if (seriesCount == 0) {return;}
    if (shouldIncludeTimeSteps) {
      sbTimeStep.append(", ").append(timeStep);
    }
    XholonWithPorts node = firstXYSeries;
    for (int i = 0; i < seriesCount; i++) {
      switch (writeType) {
      case WRITE_AS_LONG:
      case WRITE_AS_INT:
      case WRITE_AS_SHORT:
        long longVal = (long)node.getPort(0).getVal();
        sbXYSeries[i].append(", ").append(longVal);
        if (longVal > maxY) {
          maxY = longVal;
        }
        break;
      case WRITE_AS_DOUBLE:
      case WRITE_AS_FLOAT:
        double doubleVal = node.getPort(0).getVal();
        sbXYSeries[i].append(", ").append(doubleVal);
        if (doubleVal > maxY) {
          maxY = doubleVal;
        }
        break;
      default:
        break;
      }
      node = (XholonWithPorts)node.getNextSibling();
    }
    maxTimeSteps++;
  }

  /**
   * Used only by some of the ealontro models.
   * TODO this code needs to be fixed before it can be used
   * @see org.primordion.xholon.io.IChartViewer#capture(int, double, double[])
   */
  public void capture(int numTimeSeries, double xVal, double yVal[])
  {
    if (sbd == null) {return;}
    sbd.append(xVal + dataDelimiter);
    for (int i = 0; i < numTimeSeries; i++) {
      sbd.append(yVal[i] + dataDelimiter);
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
    this.showLegend = showLegend;
    if (seriesCount > 0) {
      sbd
      .append("var chart = c3.generate( {\n")
      .append("  bindto: '#" + elementId + "',\n")
      .append("  data: {\n");
      if (shouldIncludeTimeSteps) {
        sbd.append("    x: 'x',\n");
      }
      sbd
      .append("    columns: [\n");
      if (shouldIncludeTimeSteps) {
        sbd.append(sbTimeStep.toString()).append("],\n");
      }
      for (int i = 0; i < seriesCount; i++) {
        sbd.append(sbXYSeries[i].toString()).append("]");
        if (i+1 < seriesCount) {
          sbd.append(",");
        }
        sbd.append("\n");
      }
      sbd
      .append("    ]\n")
      .append("  },\n");
      if (showLegend) {
        sbd
        .append("  legend: {\n")
        .append("    position: 'right'\n")
        .append("  },\n");
      }
      else {
        sbd
        .append("  legend: {\n")
        .append("    show: false\n")
        .append("  },\n");
      }
      sbd
      .append("  axis: {\n")
      .append("    y: {\n")
      .append("      label: '")
      .append(escape(((XholonWithPorts)chartRoot.getFirstChild().getNextSibling()).getRoleName()))
      .append("'\n")
      .append("    },\n")
      .append("    x: {\n")
      .append("      label: '")
      .append(escape(((XholonWithPorts)chartRoot.getFirstChild()).getRoleName()))
      .append("'\n")
      .append("    }\n")
      .append("  }\n")
      .append("});\n");
    }
    if (chartRoot.getApp().isApplet()) {}
    else if (chartRoot.getApp().isUseGwt()) {
      chartRoot.println(sbd.toString());
      pasteScript("chartScript", sbd.toString());
    }
    else {
      // TODO possibly write to a new browser window; open window and then write data
      System.out.println(sbd.toString());
    }
  }
  
  /**
   * Escape special characters in the input String.
   * For now, all it does is convert all "'" to "\'",
   * without checking to see if it's already escaped.
   * @param inStr The input String (ex: "This is the Earth's surface.").
   * @return An escaped String (ex: "This is the Earth\'s surface.").
   */
  protected String escape(String inStr) {
    return inStr.replaceAll("'", "\\\\'");
  }
  
  protected void pasteScript(String scriptId, String scriptContent) {
    HtmlScriptHelper.fromString(scriptContent, true);
  }
  
  /**
   * Load C3 library asynchronously.
   */
  protected void loadC3() {
    loadCss("xholon/lib/c3.css");
    require(this);
  }
  
  /**
   * use requirejs
   */
  protected native void require(final ChartViewerC3 cv) /*-{
    //console.log("starting require ..");
    //console.log($wnd.requirejs.config);
    $wnd.requirejs.config({
      enforceDefine: false,
      paths: {
        c3: [
          "xholon/lib/c3.min"
        ]
      }
    });
    //console.log("require 1");
    $wnd.require(["c3"], function(c3) {
      
    });
    //console.log("require ended");
  }-*/;
  
  /**
   * Is $wnd.c3 defined.
   * @return it is defined (true), it's not defined (false)
   */
  protected native boolean isDefinedC3() /*-{
    //console.log("isDefinedC3()");
    return (typeof $wnd.c3 != "undefined"); // && (typeof $wnd.c3.??? != "undefined");
  }-*/;
  
  protected native void loadCss(String url) /*-{
    var link = $doc.createElement("link");
    link.type = "text/css";
    link.rel = "stylesheet";
    link.href = url;
    $doc.getElementsByTagName("head")[0].appendChild(link);
  }-*/;
  
  /*
   * @see org.primordion.xholon.io.IChartViewer#remove()
   */
  public void remove() {} // don't need to do anything
  
  public int getWidth() {return width;}
  public void setWidth(int width) {this.width = width;}
  public int getHeight() {return height;}
  public void setHeight(int height) {this.height = height;}
  
}

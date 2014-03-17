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
 * <p>Captures data, and displays the data in a web browser as a NVD3.js line chart.
 * NVD3 builds "Re-usable charts for d3.js".</p>
 * Complete working example of a NVD3 line chart:
 * <pre>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>My First Chart</title>
    <link href="nv.d3.css" rel="stylesheet" type="text/css">
    <script src="lib/d3.v3.js"></script>
    <script src="nv.d3.js"></script>
  </head>
  <body>
    <svg style='height:600px'/>
    <script>
function myData() {
  var series1 = [];
  for(var i = 1; i < 100; i ++) {
    series1.push({
      x: i, y: 100 / i
    });
  }

  return [
    {
      key: "Series #1",
      values: series1,
      color: "#0000ff"
    }
  ];
}

nv.addGraph(function() {
  var chart = nv.models.lineChart();

  chart.xAxis
    .axisLabel("X-axis Label");

  chart.yAxis
    .axisLabel("Y-axis Label")
    .tickFormat(d3.format("d"));

  d3.select("svg")
    .datum(myData())
    .transition().duration(500).call(chart);

  nv.utils.windowResize(
    function() {
      chart.update();
    }
  );

  return chart;
});
    </script>
  </body>
</html>
    </script>
  </body>
</html>
 * </pre>
 * TODO
 * - 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://nvd3.org/index.html">NVD3 website</a>
 * @see <a href="https://github.com/novus/nvd3/wiki/Sample-chart-%28your-first-nvd3-chart%21%29">NVD3 simple sample line chart</a>
 * @since 0.9.1 (Created on March 16, 2014)
 */
@SuppressWarnings("serial")
public class ChartViewerNVD3 extends AbstractChartViewer implements IChartViewer {

  protected String typeOfData = null;
  protected int maxTimeSteps = 0;
  protected StringBuilder sbd = null; // accumulated data/script
  protected StringBuilder[] sbXYSeries = null; // one StringBuilder per XYSeries
  
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
  protected int height = 600; //525; //350;
  
  // Array of names of series that are not connected to tree nodes
  protected String seriesNameNonNode[] = null;
  
  /**
   * Colors that can be used to draw lines in a line chart.
   * Color scheme from: colorbrewer2.org
   */
  protected String seriesColor[] = {
    "a6cee3", "1f78b4", "b2df8a", "33a02c", "fb9a99", "e31a1c",
    "fdbf6f", "ff7f00", "cab2d6", "6a3d9a", "ffff99", "b15928"
  };
  protected int seriesColorIndex = 0;
  
  /**
   * default constructor
   */
  public ChartViewerNVD3() {}
  
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
        sbXYSeries[i] = new StringBuilder();
        node = (XholonWithPorts)node.getNextSibling();
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
    XholonWithPorts node = firstXYSeries;
    for (int i = 0; i < seriesCount; i++) {
      switch (writeType) {
      case WRITE_AS_LONG:
      case WRITE_AS_INT:
      case WRITE_AS_SHORT:
        long longVal = (long)node.getPort(0).getVal();
        sbXYSeries[i]
        .append("{x: ")
        .append(timeStep)
        .append(", y: ")
        .append(longVal)
        .append("}");
        sbXYSeries[i].append(",");
        if (longVal > maxY) {
          maxY = longVal;
        }
        break;
      case WRITE_AS_DOUBLE:
      case WRITE_AS_FLOAT:
        double doubleVal = node.getPort(0).getVal();
        sbXYSeries[i]
        .append("{x: ")
        .append(timeStep)
        .append(", y: ")
        .append(doubleVal)
        .append("}");
        sbXYSeries[i].append(",");
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
      .append("function myData() {\n")
      .append("  return [\n");
      IXholon node = firstXYSeries;
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
        sbd
        .append("    {\n")
        .append("      key: \"")
        .append(columnName)
        .append("\",\n")
        .append("      values: [")
        .append(sbXYSeries[i])
        .append("],\n")
        .append("      color: \"#")
        .append(seriesColor[seriesColorIndex++])
        .append("\"\n")
        .append("    }");
        if (i+1 < seriesCount) {
          sbd.append(",");
        }
        sbd.append("\n");
        node = (XholonWithPorts)node.getNextSibling();
        if (seriesColorIndex >= seriesColor.length) {
          seriesColorIndex = 0;
        }
      }
      sbd
      .append("  ];\n\n")
      .append("}\n");
      
      sbd
      .append("nv.addGraph(function() {\n")
      .append("  var chart = nv.models.lineChart();\n")
      .append("  chart.xAxis\n")
      .append("    .axisLabel(\"")
      .append(escape(((XholonWithPorts)chartRoot.getFirstChild()).getRoleName()))
      .append("\");\n")
      .append("  chart.yAxis\n")
      .append("    .axisLabel(\"")
      .append(escape(((XholonWithPorts)chartRoot.getFirstChild().getNextSibling()).getRoleName()))
      .append("\")\n")
      .append("    .tickFormat(d3.format(\"d\"));\n")
      .append("  d3.select(\"div#")
      .append(elementId)
      .append("\").append(\"svg\")\n")
      .append("    .attr(\"height\", \"")
      .append(height)
      .append("px\")\n")
      .append("    .datum(myData())\n")
      .append("    .transition().duration(500).call(chart);\n")
      .append("  nv.utils.windowResize(\n")
      .append("    function() {\n")
      .append("      chart.update();\n")
      .append("    }\n")
      .append("  );\n")
      .append("  return chart;\n")
      .append("});\n");
    }
    if (chartRoot.getApp().isApplet()) {}
    else if (chartRoot.getApp().isUseGwt()) {
      //chartRoot.println(sbd.toString());
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
  
  /*
   * @see org.primordion.xholon.io.IChartViewer#remove()
   */
  public void remove() {} // don't need to do anything
  
  public int getWidth() {return width;}
  public void setWidth(int width) {this.width = width;}
  public int getHeight() {return height;}
  public void setHeight(int height) {this.height = height;}
  
}

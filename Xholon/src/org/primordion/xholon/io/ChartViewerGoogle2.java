/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2012 Ken Webb
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
//import java.io.StringWriter;
//import java.io.Writer;
import java.util.Date;

//import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.IXholon;
//import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.base.XholonWithPorts;
//import org.primordion.xholon.exception.XholonConfigurationException;
import org.primordion.xholon.io.gwt.HtmlScriptHelper;
//import org.primordion.xholon.service.IScriptService;
//import org.primordion.xholon.service.IXholonService;

/**
 * <p>Captures data, and displays the data in a web browser as a Google chart.
 * This is intended to be especially useful for a Java applet such as Bestiary.
 * The HTML page where the chart is generated must contain in the head:</p>
 * <p>This class should also be usable with GWT.</p>
 * <pre>
&lt;script type="text/javascript" src="https://www.google.com/jsapi">&lt;/script>
&lt;script type="text/javascript">
  google.load('visualization', '1.0', {'packages':['corechart']});
&lt;/script>
 * </pre>
 * and must contain in the body a target element where the chart will be drawn, for example:
 * <pre>
&lt;div id="usercontent">&lt;/div>
 * </pre>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="https://developers.google.com/chart/">Google Developers - Google Charts website</a>
 * @see <a href="http://code.google.com/apis/ajax/playground/">Google Code Playground</a>
 * @since 0.8.1 (Created on Feb 5, 2012)
 */
@SuppressWarnings("serial")
public class ChartViewerGoogle2 extends AbstractChartViewer implements IChartViewer {

	protected String typeOfData = null;
	protected int maxTimeSteps = 0;
	protected StringBuilder sbd = null; // accumulated data/script
	protected long dateTime = 0;           // unique date/time used as part of data and script file names
	protected String statsSeries = null;   // part of the file names, to allow various series of data
	protected String dataDelimiter = null; // delimiter between data items on a line (ex: one,two)
	
	/**
	 * ID of the HTML element where the chart will be rendered.
	 * Google API playground requires 'visualization'.
	 * Xholon applets such as Bestiary have used 'usercontent'.
	 */
	protected String elementId = "xhchart"; //"visualization";
	
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
	
	protected StringBuilder sbRowData;
	
	/**
	 * default constructor
	 */
	public ChartViewerGoogle2() {}
	
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
		sbRowData = new StringBuilder();
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
		sbRowData.append("['").append(timeStep).append("'").append(dataDelimiter);
		XholonWithPorts node = firstXYSeries;
		for (int i = 0; i < seriesCount; i++) {
			switch (writeType) {
			case WRITE_AS_LONG:
			case WRITE_AS_INT:
			case WRITE_AS_SHORT:
				long longVal = (long)node.getPort(0).getVal();
				sbRowData.append(longVal);
				if (i < seriesCount-1) {sbRowData.append(dataDelimiter);}
				if (longVal > maxY) {
					maxY = longVal;
				}
				break;
			case WRITE_AS_DOUBLE:
			case WRITE_AS_FLOAT:
				double doubleVal = node.getPort(0).getVal();
				sbRowData.append(doubleVal);
				if (i < seriesCount-1) {sbRowData.append(dataDelimiter);}
				if (doubleVal > maxY) {
					maxY = doubleVal;
				}
				break;
			default:
				break;
			}
			node = (XholonWithPorts)node.getNextSibling();
		}
		sbRowData.append("],\n");
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
			sbd.append("function drawVisualization() {\n");
			sbd.append("  var options = {title:'" + escape(chartRoot.getRoleName()) + "',"
				+ "width:" + width + ",height:" + height + ",hAxis:{title:'"
				+ escape(((XholonWithPorts)chartRoot.getFirstChild()).getRoleName())
				+ "'},vAxis:{title:'"
				+ escape(((XholonWithPorts)chartRoot.getFirstChild().getNextSibling()).getRoleName())
				+ "'}};\n");
			sbd.append("  var data = new google.visualization.DataTable();\n");
			sbd.append("  data.addColumn('string', '"
					+ ((XholonWithPorts)chartRoot.getFirstChild()).getRoleName() + "');\n");
			XholonWithPorts node = firstXYSeries;
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
				sbd.append("  data.addColumn('number','" + columnName + "');\n");
				node = (XholonWithPorts)node.getNextSibling();
			}
			sbd.append("  data.addRows([\n");
			sbd.append(sbRowData.toString());
			sbd.append("  ]);\n");
			sbd.append("  var chart = new google.visualization.LineChart(document.getElementById('" + elementId + "'));\n");
			sbd.append("  chart.draw(data, options);\n");
			sbd.append("}\n");
			sbd.append("// remove the following line for Google API playground\n");
			sbd.append("drawVisualization();");
		}
		if (chartRoot.getApp().isApplet()) {
			/*IXholon scriptService = Application.getApplication().getService(IXholonService.XHSRV_SCRIPT);
			if (scriptService != null) {
				try {
					String scriptContent = sbd.toString();
					((IScriptService)scriptService).evalScript(chartRoot, "BrowserJS", scriptContent);
				} catch (XholonConfigurationException e) {
					Xholon.getLogger().error(e.toString());
				}
			}*/
		}
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
	
	//protected native void pasteScript(String scriptId, String scriptContent)
	/*-{
	  // add script
    var script = $doc.createElement('script');
    script.setAttribute('id', scriptId);
    script.setAttribute('type', 'text/javascript');
    try {
      // fails with IE
      script.appendChild(document.createTextNode(scriptContent));      
    } catch(e) {
      script.text = scriptContent;
    }
    
    $doc.getElementsByTagName('head')[0].appendChild(script);
	}-*/
	//;
	
	/*
	 * @see org.primordion.xholon.io.IChartViewer#remove()
	 */
	public void remove() {} // don't need to do anything
	
	public int getWidth() {return width;}
	public void setWidth(int width) {this.width = width;}
	public int getHeight() {return height;}
	public void setHeight(int height) {this.height = height;}
	
}

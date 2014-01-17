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

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.io.gwt.HtmlScriptHelper;

/**
 * <p>Captures data, and creates a histogram using Google Charts.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="https://developers.google.com/chart/interactive/docs/gallery/histogram">Google Charts site</a>
 * @since 0.9.0 (Created on December 17, 2013)
 */
public class HistogramViewerGoogle2 extends AbstractHistogramViewer implements IHistogramViewer {
	
	StringBuilder sbd = null;
	
	/**
	 * ID of the HTML element where the chart will be rendered.
	 * Google API playground requires 'visualization'.
	 */
	protected String elementId = "xhchart";
	
	protected int width = 900;
	protected int height = 525;
	
	/**
	 * default constructor
	 */
	public HistogramViewerGoogle2() {}
	
	/**
	 * constructor
	 * @param histRootModel The root node of a Xholon subtree in the model,
	 * that will be traversed looking for instances of the xholon class.
	 * @param histRootView Root node in the view. This is the "HistogramViewer" node.
	 * @param xhClass A Xholon class whose instances will be queried for values, using getVal().
	 * @param numBins Number of bins.
	 * @param nameConcatLevels Number of composite levels to concatenate in deriving name.
	 */
	public HistogramViewerGoogle2(IXholon histRootModel, IXholon histRootView, IXholonClass xhClass, int numBins, int nameConcatLevels)
	{
		initialize(histRootModel, histRootView, xhClass, numBins, nameConcatLevels);
	}
	
	/*
	 * @see org.primordion.xholon.io.IHistogramViewer#initialize(org.primordion.xholon.base.IXholon, org.primordion.xholon.base.IXholon, org.primordion.xholon.base.IXholonClass, int, int)
	 */
	public void initialize(IXholon histRootModel, IXholon histRootView, IXholonClass xhClass, int numBins, int nameConcatLevels)
	{
		this.histRootModel = histRootModel;
		this.histRootView = histRootView;
		this.xhClass = xhClass;
		this.numBins = numBins;
		this.nameConcatLevels = nameConcatLevels;
		sbd = new StringBuilder();
	}
	
	/*
	 * @see org.primordion.xholon.io.IHistogramViewer#chart(double[])
	 */
	public void chart(double values[]) {
	  if (histRootView == null) {return;}
		String title = escape(histRootView.getRoleName());
		String xAxisLabel = escape(histRootView.getFirstChild().getRoleName());
		String yAxisLabel = escape(histRootView.getFirstChild().getNextSibling().getRoleName());
		consoleLog("HistogramViewerGoogle2 chart()");
		consoleLog(values);
		sbd.append("function drawHistogram() {\n");
		sbd.append("  var options = {\n")
		  .append("    title:'").append(title).append("',\n")
			//.append("    width:").append(width).append(",\n")
			//.append("    height:").append(height).append(",\n")
			.append("    hAxis:{title:'").append(xAxisLabel).append("'},\n")
			.append("    vAxis:{title:'").append(yAxisLabel).append("'}\n")
			.append("  };\n");
		sbd.append("  var data = google.visualization.arrayToDataTable([\n");
		
		//sbd.append(values);
		for (int i = 0; i < values.length; i++) {
		  sbd.append("    [").append(values[i]).append("],\n");
		}
		
		sbd.append("]);\n");
		sbd.append("  var chart = new google.visualization.Histogram(document.getElementById('" + elementId + "'));\n");
		sbd.append("  chart.draw(data, options);\n");
		sbd.append("}\n");
		sbd.append("// remove the following line for Google API playground\n");
		sbd.append("drawHistogram();");
		if (histRootModel.getApp().isUseGwt()) {
			//histRootModel.println(sbd.toString());
			histRootModel.consoleLog(sbd.toString());
			pasteScript("histScript", sbd.toString());
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
	 * @see org.primordion.xholon.io.IHistogramViewer#remove()
	 */
	public void remove() {} // don't need to do anything
}

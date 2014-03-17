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

package org.primordion.xholon.script;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;

/**
 * Plot a chart. The default is to use google2.
 * This is a sample Xholon script, written in normal compiled Java.
 * It's located in the common default package for Java scripts.
 * It can therefore be pasted into a running Xholon app simply as:
 * <p><pre>&lt;Plot/></pre></p>
 * <p><pre>&lt;Plot dataPlotter="gnuplot"/></pre></p>
 * <p><pre>&lt;Plot dataPlotter="google2" dataPlotterParams="Leaky Bucket,Time (s),Depth (m),./statistics/,leakybucket,1,WRITE_AS_DOUBLE"/></pre></p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on August 16, 2012)
 */
public class Plot extends XholonScript {
	
	private static final String DEFAULT_DATA_PLOTTER = "google2";
	private static final String DEFAULT_MODE = "ifNotAlready";
	
	/**
	 * Append this to end of modelName to get compete dataPlotterParams.
	 */
	private static final String DEFAULT_PARTIAL_DATAPLOTTERPARAMS
		= ",Time (timesteps),Y,./statistics/,stats,1,WRITE_AS_LONG";
	
	/** JFreeChart, gnuplot, jdbc, jpa, google, google2, c3, nvd3, none */
	private String dataPlotter = DEFAULT_DATA_PLOTTER;
	
	/** "Title,Time (timesteps),Y,./statistics/,stats,1,WRITE_AS_LONG" */
	private String dataPlotterParams = null;
	
	/** ifNotAlready (0-->1), replaceExisting (1-->1), new (n-->n+1), null */
	private String mode = DEFAULT_MODE;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#postConfigure()
	 */
	public void postConfigure()
	{
		IApplication app = this.getApp();
		if (app != null) {
			boolean shouldCreate = false;
			if ("new".equals(mode)) {
				shouldCreate = true;
				this.makeDataPlotterParams(app);
			}
			else if (!app.getUseDataPlotter() && "ifNotAlready".equals(mode)) {
				shouldCreate = true;
				this.makeDataPlotterParams(app);
			}
			if (dataPlotter != null) {
				app.setUseDataPlotter(dataPlotter);
			}
			if (dataPlotterParams != null) {
				app.setDataPlotterParams(dataPlotterParams);
			}
			if (shouldCreate) {
				app.createChart(this.getParentNode());
			}
		}
    this.removeChild();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setAttributeVal(java.lang.String, java.lang.String)
	 */
	public int setAttributeVal(String attrName, String attrVal) {
		if ("dataPlotter".equals(attrName)) {
			this.dataPlotter = attrVal;
		}
		else if ("dataPlotterParams".equals(attrName)) {
			this.dataPlotterParams = attrVal;
		}
		else if ("mode".equals(attrName)) {
			this.mode = attrVal;
		}
		return 0;
	}
	
	/**
	 * Make a set of data plotter params, if they don't already exist.
	 * @param app
	 */
	protected void makeDataPlotterParams(IApplication app) {
		if (dataPlotterParams == null) {
			String modelName = app.getModelName();
			if (modelName == null) {
				modelName = "Title";
			}
			modelName.replace(',', '_'); // replace any commas, which are used as separators
			dataPlotterParams = modelName + DEFAULT_PARTIAL_DATAPLOTTERPARAMS;
		}
	}
	
	/**
	 * Write a Plot script to an XML writer.
	 * This has to be a static method,
	 * because Plot nodes typically remove themselves from the tree,
	 * and are therefore not available later to write themselves out.
	 * @param xmlWriter
	 * @param app
	 */
	public static void writeXml(IXholon2Xml xholon2xml, IXmlWriter xmlWriter, IApplication app) {
		xmlWriter.writeStartElement("Plot");
		xmlWriter.writeAttribute("mode", "ifNotAlready");
		String dp = "google2";
		if (app.getUseDataPlotter()) {
			if (app.getUseGnuplot()) {dp="gnuplot";}
			//else if (app.getUseGoogle()) {dp="google";}
			else if (app.getUseGoogle2()) {dp="google2";}
			else if (app.getUseC3()) {dp="c3";}
			else if (app.getUseNVD3()) {dp="nvd3";}
		}
		xmlWriter.writeAttribute("dataPlotter", dp);
		xmlWriter.writeAttribute("dataPlotterParams", app.getDataPlotterParams());
		xholon2xml.writeSpecial(app);
		xmlWriter.writeEndElement("Plot");
	}
	
}

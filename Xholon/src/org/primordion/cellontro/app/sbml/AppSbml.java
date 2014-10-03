/* Cellontro - models & simulates cells and other biological entities
 * Copyright (C) 2005, 2006, 2007, 2008 Ken Webb
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

package org.primordion.cellontro.app.sbml;

import org.primordion.cellontro.base.BioXholonClass;
import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.InheritanceHierarchy;
import org.primordion.xholon.exception.XholonConfigurationException;
import org.primordion.xholon.io.IAbout;
import org.primordion.xholon.io.IChartViewer;
import org.primordion.xholon.service.AbstractXholonService;
import org.primordion.xholon.service.creation.TreeNodeFactoryNew;
import org.primordion.xholon.util.MiscRandom;

/**
 * SBML Application.
 * Use this class if you're developing an application that executes a model read from
 * a Systems Biology Markup Language (SBML) file. SBML is used to exchange models between
 * different software packages.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.2 (Created on December 8, 2005)
 */
public class AppSbml extends Application {

	// how many time step intervals to chart
	private int chartInterval = 128; // 128
	
	//private Class<IXholon> sbmlConcreteClass = null;
	//private String appName = null;

	/**
	 * Constructor.
	 */
	public AppSbml()
	{
		super();
		chartViewer = null;
	}
	
	public int getTimeStepMultiplier() {
	  return ((XhAbstractSbml)root).getTimeStepMultiplier();
	}
	
	public void setTimeStepMultiplier(int timeStepMultiplier) {
	  ((XhAbstractSbml)root).setTimeStepMultiplier(timeStepMultiplier);
	}
	
	public int getChartInterval() {
	  return chartInterval;
	}
	
	public void setChartInterval(int chartInterval) {
	  this.chartInterval = chartInterval;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#initialize(java.lang.String)
	 */
	public void initialize( String configFileName ) throws XholonConfigurationException
	{
		super.initialize(configFileName);
		if (chartInterval > ((XhAbstractSbml)root).getTimeStepMultiplier()) {
			chartInterval = ((XhAbstractSbml)root).getTimeStepMultiplier();
		}
	}
	
	/*
	 * @see org.primordion.xholon.app.Application#step()
	 */
	protected void step()
	{
		for (int i = 0; i < ((XhAbstractSbml)root).getTimeStepMultiplier(); i++) {
			if (getUseDataPlotter() && ((i % chartInterval) == 0)) {
				chartViewer.capture((((double)i / ((XhAbstractSbml)root).getTimeStepMultiplier())) + timeStep);
			}
			root.act();
		}
	}

	/*
	 * @see org.primordion.xholon.app.IApplication#wrapup()
	 */
	public void wrapup()
	{
		root.preAct();
		if (getUseDataPlotter()) {
			chartViewer.capture(timeStep);
		}
		super.wrapup();
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#about()
	 */
	public void about()
	{
		String title = "About Cellontro/SBML";
		String text;
		if (modelName == null) {
			text = "Cellontro/SBML using " + aboutText;
		}
		else {
			text = modelName + "\n" + "Cellontro/SBML using " + aboutText;
		}
		IAbout about = (IAbout)getService(AbstractXholonService.XHSRV_ABOUT);
		if (about != null) {
			about.about(title, text, 350, 200);
		}
		else {
			System.out.println(title);
			System.out.println(text);
		}
	}
	
	/**
	 * main
	 * @param args One optional command line argument.
	 */
	public static void main(String[] args) {
		String configFileName = null;
		if (args.length > 0){
			configFileName = "./config/sbml/" + args[0] + "_xhn.xml";
		}
		else {
			System.out.println("usage: java AppSbml <classNameBase>");
			System.out.println("ex:    java AppSbml Glycolysis");
			System.out.println("note:  This would be converted into ./config/sbml/Glycolysis_xhn.xml");
		}
		appMain(new String[0], "org.primordion.cellontro.app.AppSbml", configFileName);
		/*
		IApplication app;
		app = new AppSbml();
		app.initialize(configFileName);
		app.initControl();
		app.initViewers();
		app.setControllerState(Control.CS_RUNNING);
		app.process();
		app.wrapup();
		*/
	}
}

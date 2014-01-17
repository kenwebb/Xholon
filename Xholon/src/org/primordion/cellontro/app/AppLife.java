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

package org.primordion.cellontro.app;

import org.primordion.cellontro.base.IBioXholon;
import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonTime;
//import org.primordion.xholon.base.XPath;
import org.primordion.xholon.exception.XholonConfigurationException;
import org.primordion.xholon.io.IAbout;
import org.primordion.xholon.service.AbstractXholonService;

/**
 * Life.
 * <p>This simulation extends the basic cell model. Many instances of the 
 * cell are embedded within multiple subsystems of an organism, which in turn is embedded
 * within an ecosystem. This demonstrates a deeply nested hierarchical structure, and the
 * ability to reuse structures.</p>
 * <p>Which version of the model actually executes depends on which
 * configuration file is selected.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Jun 8, 2005)
 */
public class AppLife extends Application {

	private static final int SIZE_MONITORED = 6;
	//private boolean writeSbml = true; // set to true to have Cellontro write a SBML 2 file for this model
	//private XPath xpath;
	
	// monitoring
	private IXholon xhMonitored[];
	
	/**
	 * Constructor.
	 */
	public AppLife()
	{
		chartViewer = null;
		//xpath = new XPath();
		// monitoring
		xhMonitored = new IBioXholon[SIZE_MONITORED];
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#initialize(java.lang.String)
	 */
	public void initialize( String configFileName ) throws XholonConfigurationException
	{
		super.initialize(configFileName);
		
		// Monitoring with plotting of the monitored data.
		if (getUseDataPlotter()) {
			// Monitor these values as the BloodPlasma and contained Erythrocyte circulate in the CirculatorySystem.
			IXholon monitorContext = getXPath().evaluate("./descendant::Heart/LeftVentricle/BloodPlasma[2]", root);
			xhMonitored[0] = getXPath().evaluate("ExtraCellularSolution/Glucose", monitorContext);
			xhMonitored[1] = getXPath().evaluate("ExtraCellularSolution/Oxygen", monitorContext);
			xhMonitored[2] = getXPath().evaluate("ExtraCellularSolution/CarbonDioxide", monitorContext);
			xhMonitored[3] = getXPath().evaluate("Erythrocyte[1]/descendant::Glucose", monitorContext);
			xhMonitored[4] = getXPath().evaluate("Erythrocyte[1]/descendant::Oxygen", monitorContext);
			xhMonitored[5] = getXPath().evaluate("Erythrocyte[1]/descendant::CarbonDioxide", monitorContext);
		}
		
		// write an SBML level 2 file
		//if (writeSbml) {
		//	IXholon sbmlContext = xpath.evaluate("./descendant::Eye/descendant::Cone/TerminalButtons", root);
		//	if (sbmlContext != null) {
		//		Cellontro2Sbml cellontro2sbml = new Cellontro2Sbml("./sbml/LifeModel.xml", "LifeModel", sbmlContext);
		//		cellontro2sbml.writeAll();
		//	}
		//}
	}

	/*
	 * @see org.primordion.xholon.app.Application#step()
	 */
	protected void step()
	{
		if (getUseDataPlotter()) {
			chartViewer.capture(timeStep);
		}
		root.preAct();
		root.act();
		root.postAct();
		XholonTime.sleep( getTimeStepInterval() );
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
	 * @see org.primordion.xholon.app.Application#shouldBePlotted(org.primordion.xholon.base.IXholon)
	 */
	protected boolean shouldBePlotted(IXholon modelNode)
	{
		for (int i = 0; i < SIZE_MONITORED; i++) {
			if (modelNode == xhMonitored[i]) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#about()
	 */
	public void about()
	{
		String title = "About Cellontro";
		String text;
		if (modelName == null) {
			text = "Cellontro using " + aboutText;
		}
		else {
			text = modelName + "\n" + "Cellontro using " + aboutText;
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
	 * This is the main entry point for the Xholon application.
	 * @param args Any arguments that your application may need.
	 * <p>arg[0] could be any of the following:</p>
	 * <p>./config/cellontro/Cell/Cell_xhn.xml</p>
	 * <p>./config/cellontro/Life3d/Life3d_SingleCells_xhn.xml</p>
	 * <p>./config/cellontro/Cell/Cell_BioSystems_Jul03_xhn.xml</p>
	 * <p>./config/cellontro/Cell/Cell_BioSystems_Oct03_xhn.xml</p>
	 * <p>./config/cellontro/Cell/Cell_BioSystems_Plus01_xhn.xml</p>
	 * <p>./config/cellontro/Life/Life_xhn.xml</p>
	 */
	public static void main(String[] args) {
		appMain(args, "org.primordion.cellontro.app.AppLife",
			"./config/cellontro/Life/Life_xhn.xml");
	}
}

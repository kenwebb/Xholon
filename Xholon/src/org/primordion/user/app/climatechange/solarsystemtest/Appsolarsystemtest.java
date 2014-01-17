/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2011 Ken Webb
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

package org.primordion.user.app.climatechange.solarsystemtest;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.IPortInterface;
import org.primordion.xholon.base.PortInterface;
import org.primordion.xholon.base.XholonTime;
import org.primordion.xholon.exception.XholonConfigurationException;
import org.primordion.xholon.io.GridViewerDetails;

/**
	solarsystemtest application - Xholon Java
	<p>Xholon 0.8.1 http://www.primordion.com/Xholon</p>
*/
public class Appsolarsystemtest extends Application {

// Variables

public Appsolarsystemtest() {super();}

public void initialize(String configFileName) throws XholonConfigurationException
{
  consoleLog("Appsolarsystemtest.initialize( 1");
	super.initialize(configFileName);
	consoleLog("Appsolarsystemtest.initialize( 2");
	if (getUseInteractions()) {
	  consoleLog("Appsolarsystemtest.initialize( 3");
		// include complete class name on sequence diagrams, rather than truncating long names
		interaction.setMaxNameLen(100);
		interaction.setMaxDataLen(6);
		IPortInterface providedRequiredInterface = new PortInterface();
		providedRequiredInterface.setInterface(Xhsolarsystemtest.getSignalIDs());
		providedRequiredInterface.setInterfaceNames(Xhsolarsystemtest.getSignalNames());
		interaction.setProvidedRequiredInterface(providedRequiredInterface);
		consoleLog("Appsolarsystemtest.initialize( 4");
	}
}

protected void step()
{
	//root.preAct();
	root.act();
	if (getUseDataPlotter()) {
		chartViewer.capture(timeStep);
	}
	root.postAct();
	if (shouldStepView) {
		view.act();
	}
	XholonTime.sleep( getTimeStepInterval() );
	if (getUseGridViewer()) {
		for (int vIx = 0; vIx < gridViewers.size(); vIx++) {
			GridViewerDetails gvd = (GridViewerDetails)gridViewers.get(vIx);
			if (gvd.useGridViewer) {
				//gvd.gridFrame.setInfoLabel("Time step: " + timeStep);
				gvd.gridPanel.paintComponent(null); //gvd.gridPanel.getGraphics());
			}
		}
	}
}

public void wrapup()
{
	root.preAct();
	if (getUseDataPlotter()) {
		chartViewer.capture(getTimeStep());
	}
	if (getUseGridViewer()) {
		for (int vIx = 0; vIx < gridViewers.size(); vIx++) {
			GridViewerDetails gvd = (GridViewerDetails)gridViewers.get(vIx);
			if (gvd.useGridViewer) {
				//gvd.gridFrame.setInfoLabel("Time step: " + timeStep);
				gvd.gridPanel.paintComponent(null); //gvd.gridPanel.getGraphics());
			}
		}
	}
	super.wrapup();
}

protected boolean shouldBePlotted(org.primordion.xholon.base.IXholon modelNode)
{
	if ((modelNode.getXhType() & org.primordion.xholon.base.IXholonClass.XhtypePurePassiveObject)
	    == org.primordion.xholon.base.IXholonClass.XhtypePurePassiveObject) {
		return true;
	}
	else {
		return false;
	}
}

public Object findGwtClientBundle() {
  return Resources.INSTANCE;
}

public static void main(String[] args) {
    appMain(args, "org.primordion.user.app.climatechange.solarsystemtest.Appsolarsystemtest",
        "/org/primordion/user/app/climatechange/solarsystemtest/_xhn.xml");
}

}

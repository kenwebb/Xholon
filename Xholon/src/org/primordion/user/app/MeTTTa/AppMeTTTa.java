/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2008 Ken Webb
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

package org.primordion.user.app.MeTTTa;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.IPortInterface;
import org.primordion.xholon.base.PortInterface;
import org.primordion.xholon.base.XholonTime;
import org.primordion.xholon.exception.XholonConfigurationException;
import org.primordion.xholon.io.GridViewerDetails;

/**
 * Xholon MeTTTa
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.7.1 (Created on May 10, 2008)
*/
public class AppMeTTTa extends Application {

// default constructor
public AppMeTTTa() {super();}

@Override
public void initialize(String configFileName) throws XholonConfigurationException
{
	super.initialize(configFileName);
	if (getUseInteractions()) {
		// include complete class name on sequence diagrams, rather than truncating long names
		interaction.setMaxNameLen(100);
		interaction.setMaxDataLen(30);
		IPortInterface providedRequiredInterface = new PortInterface();
		providedRequiredInterface.setInterface(XhMeTTTa.getSignalIDs());
		providedRequiredInterface.setInterfaceNames(XhMeTTTa.getSignalNames());
		interaction.setProvidedRequiredInterface(providedRequiredInterface);
	}
}

@Override
protected void step()
{
	root.preAct();
	if (getUseDataPlotter()) {
		chartViewer.capture(timeStep);
	}
	root.act();
	root.postAct();
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

@Override
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

@Override
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

/*
 * main
 */
public static void main(String[] args) {
    appMain(args, "org.primordion.user.app.MeTTTa.AppMeTTTa",
        "./config/user/MeTTTa/MeTTTa_xhn.xml");
}

}

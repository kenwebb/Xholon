/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2010 Ken Webb
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

package org.primordion.user.app.RavaszHnm;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonTime;
import org.primordion.xholon.exception.XholonConfigurationException;
import org.primordion.xholon.io.IViewer;
import org.primordion.xholon.io.GridViewerDetails;

/**
	RavaszHnm application - Xholon Java
	<p>Xholon 0.8.1 http://www.primordion.com/Xholon</p>
*/
public class AppRavaszHnm extends Application {

// Variables

public AppRavaszHnm() {super();}

public void initialize(String configFileName) throws XholonConfigurationException
{
	super.initialize(configFileName);
}

// Setters and Getters
public void setAppSpecificParam1(String AppSpecificParam1) {XhRavaszHnm.setAppSpecificParam1(AppSpecificParam1);}
public String getAppSpecificParam1() {return XhRavaszHnm.getAppSpecificParam1();}

public boolean setParam(String pName, String pValue)
{
	if ("AppSpecificParam1".equals(pName)) {setAppSpecificParam1(pValue); return true;}
	return super.setParam(pName, pValue);
}

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

/*
 * @see org.primordion.xholon.app.Application#invokeGraphicalNetworkViewer(org.primordion.xholon.base.IXholon, java.lang.String)
 */
public IViewer invokeGraphicalNetworkViewer(IXholon xhStart, String graphicalNetworkViewerParams)
{
	String myParams = "null,1000,1000,12,LAYOUT_FR,false,"
		+ "Ravasz Barabasi Hierarchical Network Model" + ",false";
	return super.invokeGraphicalNetworkViewer(xhStart, myParams);
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

public static void main(String[] args) {
    appMain(args, "org.primordion.user.app.AppRavaszHnm",
        "./config/user/RavaszHnm/RavaszHnm_xhn.xml");
}

}

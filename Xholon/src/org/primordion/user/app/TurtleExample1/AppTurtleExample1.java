/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2007, 2008 Ken Webb
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

package org.primordion.user.app.TurtleExample1;

import com.google.gwt.user.client.Window.Location;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;
import org.primordion.xholon.exception.XholonConfigurationException;
import org.primordion.xholon.io.GridViewerDetails;

/**
	TurtleExample1 application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   21/02/2007</p>
	<p>File:   TurtleExample1.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Wed Feb 21 13:46:13 EST 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class AppTurtleExample1 extends Application {

// Variables

public AppTurtleExample1() {super();}

public void initialize(String configFileName) throws XholonConfigurationException
{
	super.initialize(configFileName);
}

// Setters and Getters
public void setAppSpecificParam1(String AppSpecificParam1) {XhTurtleExample1.setAppSpecificParam1(AppSpecificParam1);}
public String getAppSpecificParam1() {return XhTurtleExample1.getAppSpecificParam1();}

public boolean setParam(String pName, String pValue)
{
	if ("AppSpecificParam1".equals(pName)) {
	  String wlDraw = Location.getParameter("draw");
	  if (wlDraw == null) {
	    setAppSpecificParam1(pValue);
	  }
	  else {
	    // override with value from URL line (ex: &draw=nil)
	    setAppSpecificParam1(wlDraw);
	  }
	  return true;
	}
	return super.setParam(pName, pValue);
}

protected void step()
{
	//root.preAct();
	if (getUseDataPlotter()) {
		chartViewer.capture(timeStep);
	}
	root.act();
	//root.postAct();
	XholonTime.sleep( getTimeStepInterval() );
	if (getUseGridViewer()) {
		for (int vIx = 0; vIx < gridViewers.size(); vIx++) {
			GridViewerDetails gvd = (GridViewerDetails)gridViewers.get(vIx);
			if (gvd.useGridViewer) {
				//gvd.gridFrame.setInfoLabel("Time step: " + timeStep);
				gvd.gridPanel.paintComponent(null); //gvd.gridPanel.getGraphics()); // GWT
			}
		}
	}
}

public void wrapup()
{
	root.preAct();
	if (getUseDataPlotter()) {
		chartViewer.capture(timeStep);
	}
	if (getUseGridViewer()) {
		for (int vIx = 0; vIx < gridViewers.size(); vIx++) {
			GridViewerDetails gvd = (GridViewerDetails)gridViewers.get(vIx);
			if (gvd.useGridViewer) {
				//gvd.gridFrame.setInfoLabel("Time step: " + timeStep);
				gvd.gridPanel.paintComponent(null); //gvd.gridPanel.getGraphics()); // GWT
			}
		}
	}
	super.wrapup();
}

protected boolean shouldBePlotted(org.primordion.xholon.base.IXholon modelNode)
{
	//if ((modelNode.getXhType() & org.primordion.xholon.base.IXholonClass.XhtypePurePassiveObject)
	//    == org.primordion.xholon.base.IXholonClass.XhtypePurePassiveObject) {
	if (modelNode.isPassiveObject()) {
		return true;
	}
	else {
		return false;
	}
}

/*
 * @see org.primordion.xholon.app.IApplication#findGwtClientBundle()
 */
public Object findGwtClientBundle() {
  return Resources.INSTANCE;
}

public static void main(String[] args) {
    appMain(args, "org.primordion.user.app.AppTurtleExample1",
        "./config/user/TurtleExample1/TurtleExample1_xhn.xml");
}

}

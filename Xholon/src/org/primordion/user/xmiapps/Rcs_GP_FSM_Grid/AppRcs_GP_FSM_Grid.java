package org.primordion.user.xmiapps.Rcs_GP_FSM_Grid;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;
import org.primordion.xholon.io.GridViewerDetails;

/**
	Rcs_GP_FSM_Grid application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   23/05/2007</p>
	<p>File:   Rcs_GP_FSM_Grid.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Wed May 23 13:17:28 EDT 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class AppRcs_GP_FSM_Grid extends Application {

// Variables

public AppRcs_GP_FSM_Grid() {super();}

// Setters and Getters
protected void step()
{
	if (getUseDataPlotter()) {
		chartViewer.capture(timeStep);
	}
	root.preAct();
	root.act();
	root.postAct();
	XholonTime.sleep( getTimeStepInterval() );
	if (getUseGridViewer()) {
		for (int vIx = 0; vIx < gridViewers.size(); vIx++) {
			GridViewerDetails gvd = (GridViewerDetails)gridViewers.get(vIx);
			if (gvd.useGridViewer) {
				//gvd.gridFrame.setInfoLabel("Time step: " + timeStep);
				gvd.gridPanel.paintComponent(null); //((org.primordion.xholon.io.GridPanel)gvd.gridPanel).getGraphics());
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
  super.wrapup();
}

protected boolean shouldBePlotted(org.primordion.xholon.base.IXholon modelNode)
{
	if ((modelNode.getXhcId() == CeRcs_GP_FSM_Grid.Aggregator_G1PCE)
	        || (modelNode.getXhcId() ==CeRcs_GP_FSM_Grid.Aggregator_GlcCE)) {
	  return true;
	}
	else {
	  return false;
	}
}

public static void main(String[] args) {
    appMain(args, "org.primordion.xholon.xmiapps.AppRcs_GP_FSM_Grid",
        "./config/xmiapps/Rcs_GP_FSM_Grid/Rcs_GP_FSM_Grid_xhn.xml");
}

}

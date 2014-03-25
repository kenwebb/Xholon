package org.primordion.user.xmiapps.__XholonTemplate__;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;
import org.primordion.xholon.exception.XholonConfigurationException;
import org.primordion.xholon.io.GridViewerDetails;

/**
	__XholonTemplate__ application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   23/05/2007</p>
	<p>File:   __XholonTemplate__.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Thu Feb 22 13:00:05 EST 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class App__XholonTemplate__ extends Application {

// Variables

public App__XholonTemplate__() {super();}

public void initialize(String configFileName) throws XholonConfigurationException
{
	super.initialize(configFileName);
}

// Setters and Getters
public void setAppSpecificParam1(String AppSpecificParam1) {Xh__XholonTemplate__.setAppSpecificParam1(AppSpecificParam1);}
public String getAppSpecificParam1() {return Xh__XholonTemplate__.getAppSpecificParam1();}

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
		chartViewer.capture(timeStep);
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

public static void main(String[] args) {
    appMain(args, "org.primordion.xholon.xmiapps.App__XholonTemplate__",
        "./config/xmiapps/__XholonTemplate__/__XholonTemplate___xhn.xml");
}

}

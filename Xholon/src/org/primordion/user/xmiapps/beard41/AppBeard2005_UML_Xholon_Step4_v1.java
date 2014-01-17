package org.primordion.user.xmiapps.beard41;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;
import org.primordion.xholon.exception.XholonConfigurationException;

/**
	Beard2005_UML_Xholon_Step4_v1 application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   22/02/2007</p>
	<p>File:   Beard2005_UML_Xholon_Step4_v1.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Thu Feb 22 12:38:46 EST 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class AppBeard2005_UML_Xholon_Step4_v1 extends Application {

// Variables
// how many time step intervals to chart
private int chartInterval = 8; // Application

public AppBeard2005_UML_Xholon_Step4_v1() {super();}

public void initialize(String configFileName)
{
	try {
		super.initialize(configFileName);
	} catch (XholonConfigurationException e) {
		e.printStackTrace();
	}
	if (chartInterval > XhBeard2005_UML_Xholon_Step4_v1.timeStepMultiplier) {
		chartInterval = XhBeard2005_UML_Xholon_Step4_v1.timeStepMultiplier;
	}
}

// Setters and Getters
protected void step()
{
	for (int i = 0; i < XhBeard2005_UML_Xholon_Step4_v1.timeStepMultiplier; i++) {
		root.preAct();
		if (getUseDataPlotter() && ((i % chartInterval) == 0)) {
			chartViewer.capture((((double)i / XhBeard2005_UML_Xholon_Step4_v1.timeStepMultiplier)) + timeStep);
		}
		root.act();
		root.postAct();
	}
	XholonTime.sleep( getTimeStepInterval() );
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
	if (((modelNode.getXhType() & org.primordion.xholon.base.IXholonClass.XhtypePurePassiveObject)
			== org.primordion.xholon.base.IXholonClass.XhtypePurePassiveObject)
			&& (modelNode.getXhcId() != CeBeard2005_UML_Xholon_Step4_v1.PHCE)) {
		return true;
	}
	else {
		return false;
	}
}

public static void main(String[] args) {
    appMain(args, "org.primordion.xholon.xmiapps.AppBeard2005_UML_Xholon_Step4_v1",
        "./config/xmiapps/Beard2005_UML_Xholon_Step4_v1/Beard2005_UML_Xholon_Step4_v1_xhn.xml");
}

}

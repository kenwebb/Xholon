package org.primordion.user.xmiapps.Rcs_GP_MM_NoSymbols;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;

/**
	Rcs_GP_MM_NoSymbols application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   23/05/2007</p>
	<p>File:   Rcs_GP_MM_NoSymbols.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Thu Feb 22 12:59:18 EST 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class AppRcs_GP_MM_NoSymbols extends Application {

// Variables

public AppRcs_GP_MM_NoSymbols() {super();}

// Setters and Getters
protected void step()
{
	if (getUseDataPlotter()) {
		chartViewer.capture(timeStep);
	}
    root.act();
    XholonTime.sleep( getTimeStepInterval() );
}

public void wrapup()
{
	if (getUseDataPlotter()) {
		chartViewer.capture(timeStep);
	}
  super.wrapup();
}

protected boolean shouldBePlotted(org.primordion.xholon.base.IXholon modelNode)
{
if ((modelNode.getXhcId() == CeRcs_GP_MM_NoSymbols.SolutionCE)
        || (modelNode.getXhcId() ==CeRcs_GP_MM_NoSymbols.GlyCE)) {
  return true;
}
else {
  return false;
}
}

public static void main(String[] args) {
    appMain(args, "org.primordion.xholon.xmiapps.AppRcs_GP_MM_NoSymbols",
        "./config/xmiapps/Rcs_GP_MM_NoSymbols/Rcs_GP_MM_NoSymbols_xhn.xml");
}

}

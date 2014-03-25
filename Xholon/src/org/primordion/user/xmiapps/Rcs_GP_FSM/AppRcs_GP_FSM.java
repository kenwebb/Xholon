package org.primordion.user.xmiapps.Rcs_GP_FSM;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;

/**
	Rcs_GP_FSM application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   23/05/2007</p>
	<p>File:   Rcs_GP_FSM.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Mon Mar 05 15:47:43 EST 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class AppRcs_GP_FSM extends Application {

// Variables

public AppRcs_GP_FSM() {super();}

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

public static void main(String[] args) {
    appMain(args, "org.primordion.xholon.xmiapps.AppRcs_GP_FSM",
        "./config/xmiapps/Rcs_GP_FSM/Rcs_GP_FSM_xhn.xml");
}

}

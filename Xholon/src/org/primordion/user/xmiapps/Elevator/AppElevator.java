package org.primordion.user.xmiapps.Elevator;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;

/**
	Elevator application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   23/05/2007</p>
	<p>File:   Elevator.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Wed May 23 12:19:43 EDT 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class AppElevator extends Application {

// Variables

public AppElevator() {super();}

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
    appMain(args, "org.primordion.xholon.xmiapps.AppElevator",
        "./config/xmiapps/Elevator/Elevator_xhn.xml");
}

}

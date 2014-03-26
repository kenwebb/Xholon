package org.primordion.user.xmiappsTc.StateMachineOnly;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;

/**
	StateMachineOnly application - Xholon Java
	<p>Author: Compaq_Administrator</p>
	<p>Date:   Mon Oct 01 17:14:29 EDT 2007</p>
	<p>File:   StateMachineOnly.uml</p>
	<p>Target: Xholon 0.6 http://www.primordion.com/Xholon</p>
	<p>UML:  </p>
	<p>XMI: , </p>
	<p>XSLT: 1.0, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class AppStateMachineOnly extends Application {

// Variables

public AppStateMachineOnly() {super();}

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
    appMain(args, "org.primordion.xholon.xmiappsTc.AppStateMachineOnly",
        "./config/xmiappsTc/StateMachineOnly/StateMachineOnly_xhn.xml");
}

}

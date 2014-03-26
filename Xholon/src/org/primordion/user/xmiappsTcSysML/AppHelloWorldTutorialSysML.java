package org.primordion.user.xmiappsTcSysML;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;

/**
	HelloWorldTutorialSysML application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   Tue Sep 25 14:30:05 EDT 2007</p>
	<p>File:   HelloWorldTutorialSysML.sysml</p>
	<p>Target: Xholon 0.6 http://www.primordion.com/Xholon</p>
	<p>SysML: </p>
	<p>XMI: , </p>
	<p>XSLT: 1.0, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class AppHelloWorldTutorialSysML extends Application {

// Variables

public AppHelloWorldTutorialSysML() {super();}

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
    appMain(args, "org.primordion.xholon.xmiappsTcSysML.AppHelloWorldTutorialSysML",
        "./config/xmiappsTcSysML/HelloWorldTutorialSysML/HelloWorldTutorialSysML_xhn.xml");
}

}

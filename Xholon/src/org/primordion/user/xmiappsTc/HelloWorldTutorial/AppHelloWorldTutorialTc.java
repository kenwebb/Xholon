package org.primordion.user.xmiappsTc.HelloWorldTutorial;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;

/**
	HelloWorldTutorial application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   17/07/2007</p>
	<p>File:   HelloWorldTutorial.uml</p>
	<p>Target: Xholon 0.6 http://www.primordion.com/Xholon</p>
	<p>UML:  </p>
	<p>XMI: 2.1, </p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class AppHelloWorldTutorialTc extends Application {

// Variables

public AppHelloWorldTutorialTc() {super();}

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
    appMain(args, "org.primordion.xholon.xmiappsTc.AppHelloWorldTutorial",
        "./config/xmiappsTc/HelloWorldTutorial/HelloWorldTutorial_xhn.xml");
}

}

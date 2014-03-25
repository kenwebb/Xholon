package org.primordion.user.xmiapps.HelloWorldTutorial_plus;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;

/**
	HelloWorldTutorial_plus application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   23/05/2007</p>
	<p>File:   HelloWorldTutorial_plus.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Wed Apr 04 16:42:36 EDT 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class AppHelloWorldTutorial_plus extends Application {

// Variables

public AppHelloWorldTutorial_plus() {super();}

// Setters and Getters
protected void step()
{
    root.preAct();
	if (getUseDataPlotter()) {
		chartViewer.capture(timeStep);
	}
    root.act();
    root.postAct();
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

public static void main(String[] args) {
    appMain(args, "org.primordion.xholon.xmiapps.AppHelloWorldTutorial_plus",
        "./config/xmiapps/HelloWorldTutorial_plus/HelloWorldTutorial_plus_xhn.xml");
}

}

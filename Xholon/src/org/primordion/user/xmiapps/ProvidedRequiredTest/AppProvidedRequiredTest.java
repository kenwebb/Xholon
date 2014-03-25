package org.primordion.user.xmiapps.ProvidedRequiredTest;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;

/**
	ProvidedRequiredTest application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   23/05/2007</p>
	<p>File:   ProvidedRequiredTest.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Thu Feb 22 12:51:54 EST 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class AppProvidedRequiredTest extends Application {

// Variables

public AppProvidedRequiredTest() {super();}

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
    appMain(args, "org.primordion.xholon.xmiapps.AppProvidedRequiredTest",
        "./config/xmiapps/ProvidedRequiredTest/ProvidedRequiredTest_xhn.xml");
}

}

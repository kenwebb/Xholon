package org.primordion.user.xmiapps.Watch;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;


/**
	Watch application - Inheritance Hierarchy
	<p>Author: Compaq_Administrator</p>
	<p>Date:   23/03/2007</p>
	<p>File:   Watch.zuml</p>
	<p>Target: Xholon 0.4 http://www.primordion.com/Xholon</p>
	<p>UML: Poseidon 3.2 or 4.2</p>
	<p>XMI: 1.2, Thu Oct 12 18:38:39 EDT 2006, Netbeans XMI Writer</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class AppWatch extends Application {

public AppWatch() {super();}

// Setters and Getters
protected void step()
{
	if (getUseDataPlotter()) {
		chartViewer.capture(timeStep);
	}
    root.act();
    XholonTime.sleep( getTimeStepInterval() );
}

public static void main(String[] args) {
    appMain(args, "org.primordion.xholon.xmiapps.AppWatch",
        "./config/xmiapps/Watch/Watch_xhn.xml");
}

}

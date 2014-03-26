package org.primordion.user.xmiappsArgoUML;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;


/**
	HelloWorldTutorial application - Inheritance Hierarchy
	<p>Author: Compaq_Administrator</p>
	<p>Date:   Wed Sep 12 10:58:06 EDT 2007</p>
	<p>File:   HelloWorldTutorial.xmi</p>
	<p>Target: Xholon 0.7 http://www.primordion.com/Xholon</p>
	<p>UML: ArgoUML 0.24</p>
	<p>XMI: 1.2, Tue Sep 11 19:25:34 EDT 2007, ArgoUML (using Netbeans XMI Writer version 1.0)</p>
	<p>XSLT: 1.0, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class AppHelloWorldTutorialArgoUML extends Application {

public AppHelloWorldTutorialArgoUML() {super();}

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
    appMain(args, "org.primordion.xholon.xmiappsArgoUML.AppHelloWorldTutorial",
        "./config/xmiappsArgoUML/HelloWorldTutorial/HelloWorldTutorial_xhn.xml");
}

}

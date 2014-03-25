package org.primordion.user.xmiapps.StopWatch_Xhym;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;

/**
	StopWatch application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   08/03/2007</p>
	<p>File:   StopWatch.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Thu Feb 22 12:53:05 EST 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class AppStopWatch_Xhym extends Application {

// Variables

public AppStopWatch_Xhym() {super();}

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
    appMain(args, "org.primordion.xholon.xmiapps.AppStopWatch",
        "./config/xmiapps/StopWatch/StopWatch_xhn.xml");
}

}

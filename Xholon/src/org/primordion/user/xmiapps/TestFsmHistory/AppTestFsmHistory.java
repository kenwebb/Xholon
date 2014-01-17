package org.primordion.user.xmiapps.TestFsmHistory;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;

/**
	TestFsmHistory application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   25/06/2007</p>
	<p>File:   TestFsmHistory.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Mon Jun 25 18:16:57 EDT 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class AppTestFsmHistory extends Application {

// Variables

public AppTestFsmHistory() {super();}

// Setters and Getters
public void setTestScenario(int TestScenario) {XhTestFsmHistory.setTestScenario(TestScenario);}
public int getTestScenario() {return XhTestFsmHistory.getTestScenario();}

public boolean setParam(String pName, String pValue)
{
	if ("TestScenario".equals(pName)) {setTestScenario(Integer.parseInt(pValue)); return true;}
	return super.setParam(pName, pValue);
}

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
    appMain(args, "org.primordion.xholon.xmiapps.AppTestFsmHistory",
        "./config/xmiapps/TestFsmHistory/TestFsmHistory_xhn.xml");
}

}

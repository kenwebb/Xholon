package org.primordion.user.xmiapps.TestFsmOrthogonal;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;

/**
	TestFsmOrthogonal application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   23/05/2007</p>
	<p>File:   TestFsmOrthogonal.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Wed Apr 04 14:59:39 EDT 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class AppTestFsmOrthogonal extends Application {

// Variables

public AppTestFsmOrthogonal() {super();}

// Setters and Getters
public void setTestScenario(int TestScenario) {XhTestFsmOrthogonal.setTestScenario(TestScenario);}
public int getTestScenario() {return XhTestFsmOrthogonal.getTestScenario();}

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
    appMain(args, "org.primordion.xholon.xmiapps.AppTestFsmOrthogonal",
        "./config/xmiapps/TestFsmOrthogonal/TestFsmOrthogonal_xhn.xml");
}

}

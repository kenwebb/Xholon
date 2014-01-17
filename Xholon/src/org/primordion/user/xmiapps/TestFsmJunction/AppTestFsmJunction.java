package org.primordion.user.xmiapps.TestFsmJunction;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;

/**
	TestFsmJunction application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   23/05/2007</p>
	<p>File:   TestFsmJunction.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Mon Apr 02 16:59:34 EDT 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class AppTestFsmJunction extends Application {

// Variables

public AppTestFsmJunction() {super();}

// Setters and Getters
public void setTestScenario(int TestScenario) {XhTestFsmJunction.setTestScenario(TestScenario);}
public int getTestScenario() {return XhTestFsmJunction.getTestScenario();}

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
    appMain(args, "org.primordion.xholon.xmiapps.AppTestFsmJunction",
        "./config/xmiapps/TestFsmJunction/TestFsmJunction_xhn.xml");
}

}

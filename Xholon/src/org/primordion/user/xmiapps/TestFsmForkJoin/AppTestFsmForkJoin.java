package org.primordion.user.xmiapps.TestFsmForkJoin;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;

/**
	TestFsmForkJoin application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   23/05/2007</p>
	<p>File:   TestFsmForkJoin.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Wed Apr 04 14:40:41 EDT 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class AppTestFsmForkJoin extends Application {

// Variables

public AppTestFsmForkJoin() {super();}

// Setters and Getters
public void setTestScenario(int TestScenario) {XhTestFsmForkJoin.setTestScenario(TestScenario);}
public int getTestScenario() {return XhTestFsmForkJoin.getTestScenario();}

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
    appMain(args, "org.primordion.xholon.xmiapps.AppTestFsmForkJoin",
        "./config/xmiapps/TestFsmForkJoin/TestFsmForkJoin_xhn.xml");
}

}

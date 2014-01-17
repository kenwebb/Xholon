package org.primordion.user.xmiapps.TestFsm;

import java.util.ArrayList;
import java.util.List;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonTime;

/**
  TestFsm application - Xholon Java
  <p>Author: KenWebb</p>
  <p>Date:   26/06/2007</p>
  <p>File:   TestFsm.mdzip</p>
  <p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
  <p>UML: MagicDraw UML 11.5</p>
  <p>XMI: 2.1, Tue Jun 26 17:39:57 EDT 2007</p>
  <p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class AppTestFsm extends Application {

// Variables

public AppTestFsm() {super();}

// Setters and Getters
public void setTestScenario(int TestScenario) {XhTestFsm.setTestScenario(TestScenario);}
public int getTestScenario() {return XhTestFsm.getTestScenario();}

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
    appMain(args, "org.primordion.xholon.xmiapps.AppTestFsm",
        "./config/xmiapps/TestFsm/TestFsm_xhn.xml");
}

}

package org.primordion.user.xmiapps.Elevator;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonTime;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.xholon.io.IViewer;

/**
	Elevator application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   23/05/2007</p>
	<p>File:   Elevator.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Wed May 23 12:19:43 EDT 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class AppElevator_ShowStates extends Application {

// Variables

public AppElevator_ShowStates() {super();}

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

/*
 * @see org.primordion.xholon.app.Application#shouldBePlotted(org.primordion.xholon.base.IXholon)
 */
protected boolean shouldBePlotted(IXholon modelNode)
{
	IXholon fsmOwner = getXPath().evaluate("ancestor::Door", modelNode);
	if (fsmOwner == null) {
		fsmOwner = getXPath().evaluate("ancestor::Hoist", modelNode);
	}
	if (fsmOwner == null) {
		fsmOwner = getXPath().evaluate("ancestor::UserJTree", modelNode);
	}
	if (fsmOwner == null) {
		fsmOwner = getXPath().evaluate("ancestor::FloorSelectionButton", modelNode);
	}
	if (fsmOwner == null) {
		return false;
	}
	
	if ((modelNode.getXhcId() == CeStateMachineEntity.StateCE)
			|| (modelNode.getXhcId() == CeStateMachineEntity.FinalStateCE)) {
		return true;
	}
	else {
		return false;
	}
}

/*
 * @see org.primordion.xholon.app.IApplication#invokeDataPlotter()
 */
public IViewer invokeDataPlotter()
{
	if (getUseDataPlotter()) {
		chartViewer.chart(false);
	}
	return null;
}


public static void main(String[] args) {
    appMain(args, "org.primordion.xholon.xmiapps.AppElevator",
        "./config/xmiapps/Elevator/Elevator_xhn.xml");
}

}

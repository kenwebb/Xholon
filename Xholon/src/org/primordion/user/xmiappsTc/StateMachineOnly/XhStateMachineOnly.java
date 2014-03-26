package org.primordion.user.xmiappsTc.StateMachineOnly;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.base.IXholon;

/**
	StateMachineOnly application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   Mon Oct 01 17:14:29 EDT 2007</p>
	<p>File:   StateMachineOnly.uml</p>
	<p>Target: Xholon 0.6 http://www.primordion.com/Xholon</p>
	<p>UML:  </p>
	<p>XMI: , </p>
	<p>XSLT: 1.0, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class XhStateMachineOnly extends XholonWithPorts implements CeStateMachineOnly {

// references to other Xholon instances; indices into the port array

// Signals, Events

// Variables
public String roleName = null;

// Constructor
public XhStateMachineOnly() {super();}

// Setters and Getters
public void setRoleName(String roleName) {this.roleName = roleName;}
public String getRoleName() {return roleName;}

public void initialize()
{
	super.initialize();
}

public void act()
{
	switch(xhc.getId()) {
	case StateMachineWithNoClassCE:
		processMessageQ();
		break;
	default:
		break;
	}
	super.act();
}

public void processReceivedMessage(IMessage msg)
{
	switch(xhc.getId()) {
	default:
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getClass() == StateMachineEntity.class) {
				((StateMachineEntity)node).doStateMachine(msg); // StateMachine
				break;
			}
			else {
				node = node.getNextSibling();
			}
		}
		if (node == null) {
			println("XhStateMachineOnly: message with no receiver " + msg);
		}
		break;
	}
}

public void performActivity(int activityId, IMessage msg)
{
	switch (activityId) {
		case 1204747485: //  StateMachineWithNoClass 
println("\nTransitioning from initial pseudostate to state One.");
			break;
		case 1464937485: //  StateMachineWithNoClass 
print("Transitioning from state One to state Two. This completion transition occurs because there is no explicitly defined trigger leading from One to Two.");
			break;
	default:
		println("XhStateMachineOnly: performActivity() unknown Activity " + activityId);
		break;
	}
}

public boolean performGuard(int activityId, IMessage msg)
{
	switch (activityId) {
	default:
		println("XhStateMachineOnly: performGuard() unknown Activity " + activityId);
		return false;
	}
}

public String toString() {
	String outStr = getName();
	if ((port != null) && (port.length > 0)) {
		outStr += " [";
		for (int i = 0; i < port.length; i++) {
			if (port[i] != null) {
				outStr += " port:" + port[i].getName();
			}
		}
		outStr += "]";
	}
	if (getVal() != 0.0) {
		outStr += " val:" + getVal();
	}
	return outStr;
}

}

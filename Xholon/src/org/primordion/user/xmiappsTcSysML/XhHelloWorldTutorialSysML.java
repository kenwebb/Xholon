package org.primordion.user.xmiappsTcSysML;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.base.IXholon;

/**
	HelloWorldTutorialSysML application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   Tue Sep 25 14:30:04 EDT 2007</p>
	<p>File:   HelloWorldTutorialSysML.sysml</p>
	<p>Target: Xholon 0.6 http://www.primordion.com/Xholon</p>
	<p>UML:  </p>
	<p>XMI: , </p>
	<p>XSLT: 1.0, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class XhHelloWorldTutorialSysML extends XholonWithPorts implements CeHelloWorldTutorialSysML {

// references to other Xholon instances; indices into the port array
public static final int Partner = 0;

// Signals, Events
public static final int SIGNAL_ONE = 100;

// Variables
public String roleName = null;

// Constructor
public XhHelloWorldTutorialSysML() {super();}

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
	case HelloWorldSystemCE:
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
			println("XhHelloWorldTutorialSysML: message with no receiver " + msg);
		}
		break;
	}
}

public void performActivity(int activityId, IMessage msg)
{
	switch (activityId) {
		case 1722095925: // Hello Top 
print("{Hello ");
port[Partner].sendMessage(SIGNAL_ONE, null, this);
			break;
		case 1322195935: // Hello Top SIGNAL_ONE
print("{Hello ");
port[Partner].sendMessage(SIGNAL_ONE, null, this);
			break;
		case 1733375005: // World Top 

			break;
		case 1616145015: // World Top SIGNAL_ONE
println("World}");
port[Partner].sendMessage(SIGNAL_ONE, null, this);
			break;
	default:
		println("XhHelloWorldTutorialSysML: performActivity() unknown Activity " + activityId);
		break;
	}
}

public boolean performGuard(int activityId, IMessage msg)
{
	switch (activityId) {
	default:
		println("XhHelloWorldTutorialSysML: performGuard() unknown Activity " + activityId);
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

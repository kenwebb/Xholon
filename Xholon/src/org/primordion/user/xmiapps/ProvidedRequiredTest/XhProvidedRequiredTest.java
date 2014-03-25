package org.primordion.user.xmiapps.ProvidedRequiredTest;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.base.IXholon;

/**
	ProvidedRequiredTest application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   23/05/2007</p>
	<p>File:   ProvidedRequiredTest.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Thu Feb 22 12:51:54 EST 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class XhProvidedRequiredTest extends XholonWithPorts implements CeProvidedRequiredTest {

// references to other Xholon instances; indices into the port array
public static final int PortTypeOne = 0;

// Signals, Events
public static final int SIG2 = 102;
public static final int SIG1 = 101;
public static final int SIG3 = 103;

// Variables
public String roleName = null;

// Constructor
public XhProvidedRequiredTest() {super();}

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
	case ClassACE:
		processMessageQ();
		break;
	case XholonClassCE:
	{

	}
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
			println("XhProvidedRequiredTest: message with no receiver " + msg);
		}
		break;
	}
}

public void performActivity(int activityId, IMessage msg)
{
	switch (activityId) {
		case 941399: // ClassC ClassC_FSM SIG1
// ClassC self transition
println("class C");
port[PortTypeOne].sendMessage(SIG2, null, this);
			break;
		case 76331264: // ClassB ClassB_FSM 
// ClassB initial transition
println("ClassB initial transition");
port[PortTypeOne].sendMessage(SIG1, null, this);
			break;
		case 49661329: // ClassB ClassB_FSM SIG2
// ClassB self-transition
println("class B");
port[PortTypeOne].sendMessage(SIG1, null, this);
			break;
	default:
		println("XhProvidedRequiredTest: performActivity() unknown Activity " + activityId);
		break;
	}
}

public boolean performGuard(int activityId, IMessage msg)
{
	switch (activityId) {
	default:
		println("XhProvidedRequiredTest: performGuard() unknown Activity " + activityId);
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

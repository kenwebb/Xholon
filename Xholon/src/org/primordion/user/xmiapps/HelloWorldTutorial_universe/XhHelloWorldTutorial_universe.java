package org.primordion.user.xmiapps.HelloWorldTutorial_universe;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.base.IXholon;

/**
	HelloWorldTutorial_universe application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   23/05/2007</p>
	<p>File:   HelloWorldTutorial_universe.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Thu Feb 22 12:50:56 EST 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class XhHelloWorldTutorial_universe extends XholonWithPorts implements CeHelloWorldTutorial_universe {

// references to other Xholon instances; indices into the port array
public static final int Partner1 = 0;
public static final int Partner2 = 1;

// Signals, Events
public static final int SIGNAL_ONE = 100;

// Variables
public String roleName = null;

// Constructor
public XhHelloWorldTutorial_universe() {super();}

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
			println("XhHelloWorldTutorial_universe: message with no receiver " + msg);
		}
		break;
	}
}

public void performActivity(int activityId, IMessage msg)
{
	switch (activityId) {
		case 94941553: // Hello  
print("{Hello ");
port[Partner1].sendMessage(SIGNAL_ONE, null, this);
port[Partner2].sendMessage(SIGNAL_ONE, null, this);
			break;
		case 83811557: // Hello StateMachine_Hello SIGNAL_ONE
print("{Hello ");
msg.getSender().sendMessage(SIGNAL_ONE, null, this, msg.getIndex());

			break;
		case 74791634: // World  SIGNAL_ONE
println("World}");
port[Partner1].sendMessage(SIGNAL_ONE, null, this);

			break;
		case 28791325: // Universe  SIGNAL_ONE
println("Universe}");
port[Partner1].sendMessage(SIGNAL_ONE, null, this);
			break;
	default:
		println("XhHelloWorldTutorial_universe: performActivity() unknown Activity " + activityId);
		break;
	}
}

public boolean performGuard(int activityId, IMessage msg)
{
	switch (activityId) {
	default:
		println("XhHelloWorldTutorial_universe: performGuard() unknown Activity " + activityId);
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

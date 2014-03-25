package org.primordion.user.xmiapps.HelloWorldTutorial_plus;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.Message;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.base.IXholon;

/**
	HelloWorldTutorial_plus application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   23/05/2007</p>
	<p>File:   HelloWorldTutorial_plus.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Wed Apr 04 16:42:36 EDT 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class XhHelloWorldTutorial_plus extends XholonWithPorts implements CeHelloWorldTutorial_plus {

// references to other Xholon instances; indices into the port array
public static final int Partner = 0;

// Signals, Events
public static final int SIGNAL_ONE = 100;

// Variables
public String roleName = null;
public double val = 0.0; // Hello

// Constructor
public XhHelloWorldTutorial_plus() {super();}

// Setters and Getters
public void setRoleName(String roleName) {this.roleName = roleName;}
public String getRoleName() {return roleName;}

public void setVal(double val) {this.val = val;}
public double getVal() {return val;}

public void initialize()
{
	super.initialize();
	val = 0.0;
}

public void preAct()
{
	switch(xhc.getId()) {
	case HelloCE:
	{
// Hello preAct()
println("preAct() says HELLO");
	}
		break;
	default:
		break;
	}
	super.preAct();
}

public void act()
{
	switch(xhc.getId()) {
	case HelloWorldSystemCE:
		processMessageQ();
		break;
	case HelloCE:
	{
		// Do Activity
		if (this.hasChildNodes()) {
			((StateMachineEntity)getFirstChild()).doStateMachine(new Message(ISignal.SIGNAL_DOACTIVITY,null,null,this));
		}
		else {
			System.out.println("Xhact: doActivity with no receiver ");
		}
// Hello act()
println("This is the Hello act() operation");
	}
		break;
	case WorldCE:
	{
// World act()
println("this is World's act() function");
	}
		break;
	default:
		break;
	}
	super.act();
}

public void postAct()
{
	switch(xhc.getId()) {
	case WorldCE:
	{
// World postAct()
println("World postAct() " + 10);
	}
		break;
	default:
		break;
	}
	super.postAct();
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
			System.out.println("XhHelloWorldTutorial_plus: message with no receiver " + msg);
		}
		break;
	}
}

public void performActivity(int activityId, IMessage msg)
{
	switch (activityId) {
		case 8521985: // Hello  
// Hello initial transition
print("{HELLO ");
port[Partner].sendMessage(SIGNAL_ONE, null, this);

			break;
		case 2511987: // Hello  SIGNAL_ONE
print("{Hello ");
port[Partner].sendMessage(SIGNAL_ONE, null, this);
			break;
		case 1841252: // World  SIGNAL_ONE
println("Brenda}");
port[Partner].sendMessage(SIGNAL_ONE, null, this);
			break;
	case 33991929: // doActivity SayingHello
	{
// This is "do activity" code for Hello SayingHello state
println("--> Hello SayingHello: do activity. This code should be executed each time step, while SayingHello is the active state. <--");
	}
		break;
	case 98971921: // doActivity 
	{
// This is "do activity" code for Hello
println("==> Hello: do activity. This code should be executed each time step.");
	}
		break;
	default:
		System.out.println("XhHelloWorldTutorial_plus: performActivity() unknown Activity " + activityId);
		break;
	}
}

public boolean performGuard(int activityId, IMessage msg)
{
	switch (activityId) {
	default:
		System.out.println("XhHelloWorldTutorial_plus: performGuard() unknown Activity " + activityId);
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

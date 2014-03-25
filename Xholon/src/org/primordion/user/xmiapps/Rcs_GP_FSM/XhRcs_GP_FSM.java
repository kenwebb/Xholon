package org.primordion.user.xmiapps.Rcs_GP_FSM;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.Message;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.base.IXholon;

/**
	Rcs_GP_FSM application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   23/05/2007</p>
	<p>File:   Rcs_GP_FSM.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Mon Mar 05 15:47:43 EST 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class XhRcs_GP_FSM extends XholonWithPorts implements CeRcs_GP_FSM {

// references to other Xholon instances; indices into the port array
public static final int Substrate = 0;
public static final int Product = 1;
public static final int Regulation = 1;

// Signals, Events
public static final int S_ACTIVATE = 100;
public static final int S_DEACTIVATE = 200;

// Variables
public String roleName = null;
protected static int rInt; // RcsEnzyme
protected static final int MAX_SUB = 20; // RcsEnzyme
public double val = 0; // XholonClass

// Constructor
public XhRcs_GP_FSM() {super();}

// Setters and Getters
public void setRoleName(String roleName) {this.roleName = roleName;}
public String getRoleName() {return roleName;}

public void initialize()
{
	super.initialize();
	val = 0;
}

// XholonClass
public double getVal()
{
return val;
}

// XholonClass
public void incVal(double incAmount)
{
val += incAmount;
}

// XholonClass
public void decVal(double decAmount)
{
val -= decAmount;
}

// XholonClass
public void setVal(double amount)
{
val = amount;
}

public void act()
{
	switch(xhc.getId()) {
	case GPaseSystemCE:
		processMessageQ();
		break;
	case GPaseCE:
	{
		// Do Activity
		if (this.hasChildNodes()) {
			((StateMachineEntity)getFirstChild()).doStateMachine(new Message(ISignal.SIGNAL_DOACTIVITY,null,null,this));
		}
		else {
			System.out.println("XhGPase: doActivity with no receiver ");
		}
	}
		break;
	case PKinaseCE:
	{
		// Do Activity
		if (this.hasChildNodes()) {
			((StateMachineEntity)getFirstChild()).doStateMachine(new Message(ISignal.SIGNAL_DOACTIVITY,null,null,this));
		}
		else {
			System.out.println("XhPKinase: doActivity with no receiver ");
		}
	}
		break;
	case PPhosphataseCE:
	{
		// Do Activity
		if (this.hasChildNodes()) {
			((StateMachineEntity)getFirstChild()).doStateMachine(new Message(ISignal.SIGNAL_DOACTIVITY,null,null,this));
		}
		else {
			System.out.println("XhPPhosphatase: doActivity with no receiver ");
		}
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
			System.out.println("XhRcs_GP_FSM: message with no receiver " + msg);
		}
		break;
	}
}

public void performActivity(int activityId, IMessage msg)
{
	switch (activityId) {
		case 648819534: // GPase  
// initialize substrate and product amounts
port[Product].setVal(0.0);
port[Substrate].setVal(50.0);
			break;
	case 446219361: // doActivity Active
	{
if (port[Substrate].getVal() > 0.0) {
  port[Substrate].decVal(1.0);
  port[Product].incVal(1.0);
}
	}
		break;
	case 856619429: // doActivity Active
	{
rInt = org.primordion.xholon.util.MiscRandom.getRandomInt(0, (int)port[Substrate].getVal()+1);
if (rInt == 0) {
  port[Regulation].sendMessage(S_ACTIVATE, null, this);
}
	}
		break;
	case 199619492: // doActivity Active
	{
rInt = (int)port[Substrate].getVal();
if (rInt < MAX_SUB) {
  rInt = org.primordion.xholon.util.MiscRandom.getRandomInt(rInt, MAX_SUB+1);
}
if (rInt >= MAX_SUB) {
  port[Regulation].sendMessage(S_DEACTIVATE, null, this);
}
	}
		break;
	default:
		System.out.println("XhRcs_GP_FSM: performActivity() unknown Activity " + activityId);
		break;
	}
}

public boolean performGuard(int activityId, IMessage msg)
{
	switch (activityId) {
	default:
		System.out.println("XhRcs_GP_FSM: performGuard() unknown Activity " + activityId);
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

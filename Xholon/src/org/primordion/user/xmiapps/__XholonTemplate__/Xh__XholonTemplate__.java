package org.primordion.user.xmiapps.__XholonTemplate__;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.base.IXholon;

/**
	__XholonTemplate__ application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   23/05/2007</p>
	<p>File:   __XholonTemplate__.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Thu Feb 22 13:00:05 EST 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class Xh__XholonTemplate__ extends XholonWithPorts implements Ce__XholonTemplate__ {

// references to other Xholon instances; indices into the port array
public static final int SamplePort = 0;

// Signals, Events
public static final int SIG_SAMPLE = 100;

// Variables
public String roleName = null;
private double val; // AnotherPartOfTheSystem
protected static String appSpecificParam1 = "This is a test parameter."; // XhnParams

// Constructor
public Xh__XholonTemplate__() {super();}

// Setters and Getters
public void setRoleName(String roleName) {this.roleName = roleName;}
public String getRoleName() {return roleName;}

public void setVal(double val) {this.val = val;}
public double getVal() {return val;}

public static void setAppSpecificParam1(String AppSpecificParam1Arg) {appSpecificParam1 = AppSpecificParam1Arg;}
public static String getAppSpecificParam1() {return appSpecificParam1;}

public void initialize()
{
	super.initialize();
}

// AnotherPartOfTheSystem
public void decVal(double decAmount)
{
	val -= decAmount;
}

// AnotherPartOfTheSystem
public void incVal(double incAmount)
{
	val += incAmount;
}

public void postConfigure()
{
	switch(xhc.getId()) {
	case APartOfTheSystemCE:
	{
		// do some sort of post configuration
	}
		break;
	default:
		break;
	}
	super.postConfigure();
}

public void preAct()
{
	switch(xhc.getId()) {
	case APartOfTheSystemCE:
	{
		// 
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
	case TheSystemCE:
		processMessageQ();
		break;
	case APartOfTheSystemCE:
	{
		double v = port[SamplePort].getVal();
		println("acting ... " + v);
		port[SamplePort].setVal(v + 1);
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
	case APartOfTheSystemCE:
	{
		// 
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
			println("Xh__XholonTemplate__: message with no receiver " + msg);
		}
		break;
	}
}

public void performActivity(int activityId, IMessage msg)
{
	switch (activityId) {
	default:
		println("Xh__XholonTemplate__: performActivity() unknown Activity " + activityId);
		break;
	}
}

public boolean performGuard(int activityId, IMessage msg)
{
	switch (activityId) {
	default:
		println("Xh__XholonTemplate__: performGuard() unknown Activity " + activityId);
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

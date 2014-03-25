package org.primordion.user.xmiapps.StopWatch_Xhym;

import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.ObservableXholonWithPorts;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.util.ClassHelper;

/**
	StopWatch application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   08/03/2007</p>
	<p>File:   StopWatch.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Thu Feb 22 12:53:05 EST 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class XhStopWatch_Xhym extends ObservableXholonWithPorts implements CeStopWatch_Xhym {

// references to other Xholon instances; indices into the port array
public static final int Partner = 0;

// Signals, Events
public static final int start = 100;
public static final int stop = 200;
public static final int reset = 300;
public static final int split = 400;
public static final int unsplit = 410;

// Variables
public String roleName = null;
public int state; // TestHarness

// Constructor
public XhStopWatch_Xhym() {super();}

// Setters and Getters
public void setRoleName(String roleName) {this.roleName = roleName;}
public String getRoleName() {return roleName;}

public void setState(int state) {this.state = state;}
public int getState() {return state;}

public void initialize()
{
	super.initialize();
}

public void act()
{
	switch(xhc.getId()) {
	case StopWatchSystemCE:
		processMessageQ();
		break;
	case TestHarnessCE:
	{
		// test the Stop Watch state machine
		int testScenario = 2;
		if (state == 0) {
			println("Testing the Stop Watch state machine ... ");
			switch(testScenario) {
			case 1:
				// complete loop
				port[Partner].sendMessage(start, null, this);
				port[Partner].sendMessage(split, null, this);
				port[Partner].sendMessage(stop, null, this);
				port[Partner].sendMessage(reset, null, this);
				// complete loop with no pause
				port[Partner].sendMessage(start, null, this);
				port[Partner].sendMessage(stop, null, this);
				port[Partner].sendMessage(reset, null, this);
				// complete loop with pause
				port[Partner].sendMessage(start, null, this);
				port[Partner].sendMessage(split, null, this);
				port[Partner].sendMessage(unsplit, null, this);
				port[Partner].sendMessage(split, null, this);
				port[Partner].sendMessage(stop, null, this);
				port[Partner].sendMessage(reset, null, this);
				break;
			case 2:
				break;
			default:
				println(testScenario + " is an invalid TestScenario.");
				break;
			}
			state = 1;
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
			//if (StateMachineEntity.class.isAssignableFrom(node.getClass())) {
			if (ClassHelper.isAssignableFrom(StateMachineEntity.class, node.getClass())) {
				((StateMachineEntity)node).doStateMachine(msg); // StateMachine
				break;
			}
			else {
				node = node.getNextSibling();
			}
		}
		if (node == null) {
			println("XhStopWatch: message with no receiver " + msg);
		}
		break;
	}
}

public void performActivity(int activityId, IMessage msg)
{
	switch (activityId) {
	default:
		println("XhStopWatch: performActivity() unknown Activity " + activityId);
		break;
	}
}

public boolean performGuard(int activityId, IMessage msg)
{
	switch (activityId) {
	default:
		println("XhStopWatch: performGuard() unknown Activity " + activityId);
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

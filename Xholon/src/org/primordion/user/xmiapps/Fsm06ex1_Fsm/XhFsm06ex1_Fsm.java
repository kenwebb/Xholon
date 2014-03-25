package org.primordion.user.xmiapps.Fsm06ex1_Fsm;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.base.IXholon;

/**
	Fsm06ex1_Fsm application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   23/05/2007</p>
	<p>File:   Fsm06ex1_Fsm.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Thu Feb 22 12:46:10 EST 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class XhFsm06ex1_Fsm extends XholonWithPorts implements CeFsm06ex1_Fsm {

// references to other Xholon instances; indices into the port array
public static final int Partner = 0;

// Signals, Events
public static final int SOUT_P = 100;
public static final int SOUT_Q = 110;
public static final int SOUT_R = 120;
public static final int SIN_M = 200;
public static final int SIN_N = 210;

// Variables
public String roleName = null;
private int mChoice; // Membrane2

// Constructor
public XhFsm06ex1_Fsm() {super();}

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
	case Membrane1CE:
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
			println("XhFsm06ex1_Fsm: message with no receiver " + msg);
		}
		break;
	}
}

public void performActivity(int activityId, IMessage msg)
{
	switch (activityId) {
		case 471992: // Membrane2 Top SIN_N
println("M2: bn -> aq");
println("M2: (q, out)");
port[Partner].sendMessage(SOUT_Q, null, this);
			break;
		case 7811995: // Membrane2 Top SIN_M
println("M2: bm -> cr");
println("M2: (r, out)");
port[Partner].sendMessage(SOUT_R, null, this);
			break;
		case 96918: // Membrane2 Top SIN_M
// the choice of which transition to take is non-deterministic
// get a random number, either 0 or 1
mChoice = org.primordion.xholon.util.MiscRandom.getRandomInt(0,9);
			break;
		case 715137: // Membrane2 Top 
println("M2: am -> bp");
println("M2: (p, out)");
port[Partner].sendMessage(SOUT_P, null, this);
			break;
		case 698141: // Membrane2 Top 
println("M2: am -> c");
			break;
		case 9281219: // Membrane3 c SOUT_P
println("M3: ap -> bn");
println("M3: (n, out)");
port[Partner].sendMessage(SIN_N, null, this);
			break;
		case 6941214: // Membrane3 c SOUT_Q
println("M3: bq -> am");
println("M3: (m, out)");
port[Partner].sendMessage(SIN_M, null, this);
			break;
		case 53419: // Membrane3 Top 
println("Starting ...");
port[Partner].sendMessage(SIN_M, null, this);
			break;
		case 8251291: // Membrane3 Top SOUT_Q
println("M3: dq -> e");
			break;
		case 7731217: // Membrane3 Top SOUT_R
println("M3: cr -> d, where c is either a or b");
			break;
		case 9291293: // Membrane3 Top SOUT_P
println("M3: dp -> a");
			break;
	default:
		println("XhFsm06ex1_Fsm: performActivity() unknown Activity " + activityId);
		break;
	}
}

public boolean performGuard(int activityId, IMessage msg)
{
	switch (activityId) {
		case 459135: // 1
			return mChoice > 0;
		case 867138: // 2
			return mChoice == 0;
	default:
		println("XhFsm06ex1_Fsm: performGuard() unknown Activity " + activityId);
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

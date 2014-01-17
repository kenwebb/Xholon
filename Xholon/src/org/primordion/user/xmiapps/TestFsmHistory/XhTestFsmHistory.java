package org.primordion.user.xmiapps.TestFsmHistory;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.base.IXholon;

/**
	TestFsmHistory application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   25/06/2007</p>
	<p>File:   TestFsmHistory.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Mon Jun 25 18:16:57 EDT 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class XhTestFsmHistory extends XholonWithPorts implements CeTestFsmHistory {

// references to other Xholon instances; indices into the port array
public static final int Partner = 0;

// Signals, Events
public static final int SIG1 = 100;
public static final int SIG3 = 300;
public static final int SIG2 = 200;

// Variables
public String roleName = null;
protected static int m = 10; // FsmXholon_14_147deep
protected static int n; // FsmXholon_14_147deep
public static final int TEST_SCENARIO_1 = 1; // TestHarness
public static final int TEST_SCENARIO_2 = 2; // TestHarness
public static final int TEST_SCENARIO_3 = 3; // TestHarness
public static final int TEST_SCENARIO_4 = 4; // TestHarness
public static final int TEST_SCENARIO_5 = 5; // TestHarness
public static final int TEST_SCENARIO_6 = 6; // TestHarness
protected int state; // TestHarness
protected static int x = 10; // FsmXholon_14_147
protected static int y = 5; // FsmXholon_14_147
protected static int testScenario = 1; // XhnParams

// Constructor
public XhTestFsmHistory() {super();}

// Setters and Getters
public void setRoleName(String roleName) {this.roleName = roleName;}
public String getRoleName() {return roleName;}

public static void setM(int mArg) {m = mArg;}
public static int getM() {return m;}

public static void setN(int nArg) {n = nArg;}
public static int getN() {return n;}

public static void setX(int xArg) {x = xArg;}
public static int getX() {return x;}

public static void setY(int yArg) {y = yArg;}
public static int getY() {return y;}

public static void setTestScenario(int TestScenarioArg) {testScenario = TestScenarioArg;}
public static int getTestScenario() {return testScenario;}

public void initialize()
{
	super.initialize();
}

public void act()
{
	switch(xhc.getId()) {
	case TestFsmSystemCE:
		processMessageQ();
		break;
	case TestHarnessCE:
	{
			// do actions required as part of an initial transition in a state machine
			// Xholon StateMachine classes do this automatically
			if (state == 0) {
				println("Harness ");
				switch(testScenario) {
				case TEST_SCENARIO_1: // sequence: A A1 C A1
					port[Partner].sendMessage(SIG1, null, this);
					port[Partner].sendMessage(SIG2, null, this);
					port[Partner].sendMessage(SIG2, null, this);
					break;
				case TEST_SCENARIO_2: // sequence: A A1 A2 C A2
					port[Partner].sendMessage(SIG1, null, this);
					port[Partner].sendMessage(SIG1, null, this);
					port[Partner].sendMessage(SIG2, null, this);
					port[Partner].sendMessage(SIG2, null, this);
					break;
				case TEST_SCENARIO_3: // sequence: 
					break;
				default:
					System.out.println(testScenario + " is an invalid TestScenario.");
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
			if (node.getClass() == StateMachineEntity.class) {
				((StateMachineEntity)node).doStateMachine(msg); // StateMachine
				break;
			}
			else {
				node = node.getNextSibling();
			}
		}
		if (node == null) {
			System.out.println("XhTestFsmHistory: message with no receiver " + msg);
		}
		break;
	}
}

public void performActivity(int activityId, IMessage msg)
{
	switch (activityId) {
		case 96321592: // FsmXholon_14_147deep B SIG1
println("147deep [A2 to A1]");
			break;
		case 66391595: // FsmXholon_14_147deep B SIG1
println("147deep [A1 to A2]");
			break;
		case 65781612: // FsmXholon_14_147deep B 
println("147deep [History to A1 or A2]");
			break;
		case 98441586: // FsmXholon_14_147deep Top 
println("147deep init");
			break;
		case 56511589: // FsmXholon_14_147deep Top SIG1
println("147deep [A to A1]");
			break;
		case 16391598: // FsmXholon_14_147deep Top SIG2
println("147deep [B to C]");
			break;
		case 99541619: // FsmXholon_14_147deep Top SIG2
println("147deep [C to History]");
			break;
		case 52731327: // FsmXholon_14_147 B SIG1
println("147 [A2 to A1]");
			break;
		case 47971324: // FsmXholon_14_147 B SIG1
println("147 [A1 to A2]");
			break;
		case 43841338: // FsmXholon_14_147 B 
println("147 [History to A1 or A2]");
			break;
		case 84911939: // FsmXholon_14_147 B 
println("147 init in B");
			break;
		case 99291396: // FsmXholon_14_147 Top 
println("147 init");
			break;
		case 86441321: // FsmXholon_14_147 Top SIG1
println("147 [A to A1]");
			break;
		case 99711332: // FsmXholon_14_147 Top SIG2
println("147 [B to C]");
			break;
		case 94841944: // FsmXholon_14_147 Top SIG2
println("147 [C to History]");
			break;
		case 98771394: // entry A1
println("Current state: A1");
			break;
		case 69311395: // entry A2
println("Current state: A2");
			break;
		case 74911349: // entry A1
println("Current state: A1");
			break;
		case 79981343: // entry A2
println("Current state: A2");
			break;
	default:
		System.out.println("XhTestFsmHistory: performActivity() unknown Activity " + activityId);
		break;
	}
}

public boolean performGuard(int activityId, IMessage msg)
{
	switch (activityId) {
	default:
		System.out.println("XhTestFsmHistory: performGuard() unknown Activity " + activityId);
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

package org.primordion.user.xmiapps.TestFsmJunction;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.base.IXholon;

/**
	TestFsmJunction application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   23/05/2007</p>
	<p>File:   TestFsmJunction.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Mon Apr 02 16:59:34 EDT 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class XhTestFsmJunction extends XholonWithPorts implements CeTestFsmJunction {

// references to other Xholon instances; indices into the port array
public static final int Partner = 0;

// Signals, Events
public static final int SIG1 = 100;
public static final int SIG3 = 300;
public static final int SIG2 = 200;

// Variables
public String roleName = null;
protected static int m = 10; // FsmXholon_14_169
protected static int n; // FsmXholon_14_169
public static final int TEST_SCENARIO_1 = 1; // TestHarness
public static final int TEST_SCENARIO_2 = 2; // TestHarness
public static final int TEST_SCENARIO_3 = 3; // TestHarness
public static final int TEST_SCENARIO_4 = 4; // TestHarness
public static final int TEST_SCENARIO_5 = 5; // TestHarness
public static final int TEST_SCENARIO_6 = 6; // TestHarness
protected int state; // TestHarness
protected static int x = 10; // FsmXholon_14_170
protected static int y = 5; // FsmXholon_14_170
protected static int testScenario = 5; // XhnParams

// Constructor
public XhTestFsmJunction() {super();}

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
				case TEST_SCENARIO_1: // sequence: P g [n == 0] / a; c U
					n = 0;
					port[Partner].sendMessage(SIG1, null, this);
					break;
				case TEST_SCENARIO_2: // sequence: P g [n > 0] / a; d V
					n = 17;
					port[Partner].sendMessage(SIG1, null, this);
					break;
				case TEST_SCENARIO_3: // sequence: Q h [m > 0 and n == 0] / b; c U
					m = 1;
					n = 0;
					port[Partner].sendMessage(SIG2, null, this);
					break;
				case TEST_SCENARIO_4: // sequence: Q h [m > 0 and n > 0] / b; d V
					m = 1;
					n = 2;
					port[Partner].sendMessage(SIG2, null, this);
					break;
				case TEST_SCENARIO_5: // sequence: S e / a; p; b; q; c T
					port[Partner].sendMessage(SIG1, null, this);
					break;
				case TEST_SCENARIO_6: // sequence: S f / p; d; q T
					port[Partner].sendMessage(SIG2, null, this);
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
			System.out.println("XhTestFsmJunction: message with no receiver " + msg);
		}
		break;
	}
}

public void performActivity(int activityId, IMessage msg)
{
	switch (activityId) {
		case 77371499: // FsmXholon_14_169 Top SIG1
println("activity a");
			break;
		case 889914: // FsmXholon_14_169 Top SIG2
println("activity b");
			break;
		case 3189199: // FsmXholon_14_169 Top 
println("activity c");
			break;
		case 6792194: // FsmXholon_14_169 Top 
println("activity d");
			break;
		case 62861983: // FsmXholon_14_169 Top 
println("init");
			break;
		case 78961991: // FsmXholon_14_170 X SIG2
println("activity d");
			break;
		case 95771987: // FsmXholon_14_170 X SIG1
println("activity a");
			break;
		case 54431999: // FsmXholon_14_170 X 
println("activity b");
			break;
		case 45941992: // FsmXholon_14_170 Y 
println("activity c");
			break;
		case 89341997: // FsmXholon_14_170 Top 
println("init Figure 14-170");
			break;
		case 14291995: // entry Y
println("entry / q");
			break;
		case 59791993: // exit X
println("exit / p");
			break;
	default:
		System.out.println("XhTestFsmJunction: performActivity() unknown Activity " + activityId);
		break;
	}
}

public boolean performGuard(int activityId, IMessage msg)
{
	switch (activityId) {
		case 819917: // 1
			return m > 0;
		case 82319: // 1
			return n == 0;
		case 6186192: // 2
			return n > 0;
	default:
		System.out.println("XhTestFsmJunction: performGuard() unknown Activity " + activityId);
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

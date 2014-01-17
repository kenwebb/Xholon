package org.primordion.user.xmiapps.TestFsmOrthogonal;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.base.IXholon;

/**
	TestFsmOrthogonal application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   23/05/2007</p>
	<p>File:   TestFsmOrthogonal.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Wed Apr 04 14:59:39 EDT 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class XhTestFsmOrthogonal extends XholonWithPorts implements CeTestFsmOrthogonal {

// references to other Xholon instances; indices into the port array
public static final int Partner = 0;

// Signals, Events
public static final int LAB_DONE = 100;
public static final int PROJECT_DONE = 200;
public static final int PASS = 300;
public static final int FAIL = 310;

// Variables
public String roleName = null;
public static final int TEST_SCENARIO_1 = 1; // TestHarness
public static final int TEST_SCENARIO_2 = 2; // TestHarness
public static final int TEST_SCENARIO_3 = 3; // TestHarness
public static final int TEST_SCENARIO_4 = 4; // TestHarness
public static final int TEST_SCENARIO_5 = 5; // TestHarness
public int state; // TestHarness
protected static int testScenario = 4; // XhnParams

// Constructor
public XhTestFsmOrthogonal() {super();}

// Setters and Getters
public void setRoleName(String roleName) {this.roleName = roleName;}
public String getRoleName() {return roleName;}

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
	case TEST_SCENARIO_1:
		// first orthogonal region
		port[Partner].sendMessage(LAB_DONE, null, this);
		port[Partner].sendMessage(LAB_DONE, null, this);
		break;
	case TEST_SCENARIO_2:
		// second orthogonal region
		port[Partner].sendMessage(PROJECT_DONE, null, this);
		break;
	case TEST_SCENARIO_3:
		// third orthogonal region
		port[Partner].sendMessage(PASS, null, this);
		break;
	case TEST_SCENARIO_4:
		// all orthogonal regions
		port[Partner].sendMessage(PROJECT_DONE, null, this);
		port[Partner].sendMessage(LAB_DONE, null, this);
		port[Partner].sendMessage(PASS, null, this);
		port[Partner].sendMessage(LAB_DONE, null, this);
		break;
	case TEST_SCENARIO_5:
		// fail
		port[Partner].sendMessage(FAIL, null, this);
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
			System.out.println("XhTestFsmOrthogonal: message with no receiver " + msg);
		}
		break;
	}
}

public void performActivity(int activityId, IMessage msg)
{
	switch (activityId) {
		case 3271624: // FsmXholon Studying LAB_DONE
println("Transitioning from Lab1 to Lab2");
			break;
		case 3961627: // FsmXholon Studying LAB_DONE
println("Transitioning from Lab2 to FinalState");
			break;
		case 6671631: // FsmXholon Studying PROJECT_DONE
println("Transitioning from TermProject to FinalState");
			break;
		case 5781633: // FsmXholon Studying PASS
println("Transitioning from FinalTest to FinalState");
			break;
		case 381636: // FsmXholon Studying FAIL
println("Transitioning from FinalTest to Failed");
			break;
		case 811259: // FsmXholon CourseAttempt 
println("Transitioning from Studying to Passed");
			break;
	default:
		System.out.println("XhTestFsmOrthogonal: performActivity() unknown Activity " + activityId);
		break;
	}
}

public boolean performGuard(int activityId, IMessage msg)
{
	switch (activityId) {
	default:
		System.out.println("XhTestFsmOrthogonal: performGuard() unknown Activity " + activityId);
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

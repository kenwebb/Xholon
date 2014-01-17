package org.primordion.user.xmiapps.TestFsmForkJoin;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.base.IXholon;

/**
	TestFsmForkJoin application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   23/05/2007</p>
	<p>File:   TestFsmForkJoin.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Wed Apr 04 14:40:41 EDT 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class XhTestFsmForkJoin extends XholonWithPorts implements CeTestFsmForkJoin {

// references to other Xholon instances; indices into the port array
public static final int Partner = 0;
public static final int OtherPartner = 1;

// Signals, Events
public static final int SIG1 = 100;
public static final int SIG3 = 300;
public static final int SIG2 = 200;

// Variables
public String roleName = null;
public static final int TEST_SCENARIO_1 = 1; // TestHarness
public static final int TEST_SCENARIO_2 = 2; // TestHarness
public static final int TEST_SCENARIO_3 = 3; // TestHarness
public static final int TEST_SCENARIO_4 = 4; // TestHarness
public static final int TEST_SCENARIO_5 = 5; // TestHarness
public static final int TEST_SCENARIO_6 = 6; // TestHarness
protected int state; // TestHarness
protected static int testScenario = 2; // XhnParams

// Constructor
public XhTestFsmForkJoin() {super();}

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
					break;
				case TEST_SCENARIO_2:
					port[Partner].sendMessage(SIG1, null, this); // event e1 to 14-68
					port[OtherPartner].sendMessage(SIG2, null, this); // event f1 to 14-71
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
			System.out.println("XhTestFsmForkJoin: message with no receiver " + msg);
		}
		break;
	}
}

public void performActivity(int activityId, IMessage msg)
{
	switch (activityId) {
		case 99913: // FsmXholon_14_68 Process 
println("14-68 A1 to A2");
			break;
		case 417417: // FsmXholon_14_68 Process 
println("14-68 A2 to join");
			break;
		case 633114: // FsmXholon_14_68 Process 
println("14-68 B1 to B2");
			break;
		case 674318: // FsmXholon_14_68 Process 
println("14-68 B2 to join");
			break;
		case 72581664: // FsmXholon_14_68 Top SIG1
println("14-68 event e1 received; Setup to fork");
			break;
		case 36915: // FsmXholon_14_68 Top 
println("14-68 fork to A1");
			break;
		case 347816: // FsmXholon_14_68 Top 
println("14-68 fork to B1");
			break;
		case 994219: // FsmXholon_14_68 Top 
println("14-68 join to Cleanup");
			break;
		case 53371665: // FsmXholon_14_68 Top 
println("14-68 init to Setup");
			break;
		case 34341828: // FsmXholon_14_71 Process 
println("14-71 init to A1");
			break;
		case 9251831: // FsmXholon_14_71 Process 
println("14-71 A2 to final");
			break;
		case 86341829: // FsmXholon_14_71 Process 
println("14-71 A1 to A2");
			break;
		case 72421839: // FsmXholon_14_71 Process 
println("14-71 init to B1");
			break;
		case 72671833: // FsmXholon_14_71 Process SIG1
println("14-71 B2 to final");
			break;
		case 98491832: // FsmXholon_14_71 Process 
println("14-71 B1 to B2");
			break;
		case 47491834: // FsmXholon_14_71 Process SIG2
println("14-71 B2 to Cleanup");
			break;
		case 12951826: // FsmXholon_14_71 Top 
println("14-71 init");
			break;
		case 97321827: // FsmXholon_14_71 Top 
println("14-71 Setup to Process");
			break;
		case 81211837: // FsmXholon_14_71 Top 
println("14-71 Process to Cleanup");
			break;
	default:
		System.out.println("XhTestFsmForkJoin: performActivity() unknown Activity " + activityId);
		break;
	}
}

public boolean performGuard(int activityId, IMessage msg)
{
	switch (activityId) {
	default:
		System.out.println("XhTestFsmForkJoin: performGuard() unknown Activity " + activityId);
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

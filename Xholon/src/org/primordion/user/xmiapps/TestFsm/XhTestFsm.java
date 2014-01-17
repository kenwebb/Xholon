package org.primordion.user.xmiapps.TestFsm;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.base.IXholon;

/**
	TestFsm application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   26/06/2007</p>
	<p>File:   TestFsm.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Tue Jun 26 17:39:57 EDT 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class XhTestFsm extends XholonWithPorts implements CeTestFsm {

// references to other Xholon instances; indices into the port array
public static final int Partner = 0;

// Signals, Events
public static final int SIG1 = 100;
public static final int SIG3 = 300;
public static final int SIG2 = 200;

// Variables
public String roleName = null;
protected static int x = 10; // FsmXholon
protected static int y = 5; // FsmXholon
protected double aDouble = 123456789000.0123456789; // FsmXholon
public String aString = "This is a string"; // FsmXholon
long aLong[] = new long[24]; // TestHarness
private boolean aBooleanTrue = true; // TestHarness
public char aChar = 'Q'; // TestHarness
protected byte aByte = 127; // TestHarness
protected short aShort = 32767; // TestHarness
private float aFloat = 1234.567890f; // TestHarness
public Integer anInteger; // TestHarness
public Integer myInteger = new Integer(17); // TestHarness
public static final int aStaticInt = 42; // TestHarness
public int state; // TestHarness
public static final int TEST_SCENARIO_1 = 1; // TestHarness
public static final int TEST_SCENARIO_2 = 2; // TestHarness
public static final int TEST_SCENARIO_3 = 3; // TestHarness
public static final int TEST_SCENARIO_4 = 4; // TestHarness
public static final int TEST_SCENARIO_5 = 5; // TestHarness
public static final int TEST_SCENARIO_6 = 6; // TestHarness
private boolean aBooleanFalse; // TestHarness
protected static int testScenario = 4; // XhnParams

// Constructor
public XhTestFsm() {super();}

// Setters and Getters
public void setRoleName(String roleName) {this.roleName = roleName;}
public String getRoleName() {return roleName;}

public static void setX(int xArg) {x = xArg;}
public static int getX() {return x;}

public static void setY(int yArg) {y = yArg;}
public static int getY() {return y;}

public void setADouble(double aDouble) {this.aDouble = aDouble;}
public double getADouble() {return aDouble;}

public void setAString(String aString) {this.aString = aString;}
public String getAString() {return aString;}

public void setALong(long aLong[]) {this.aLong = aLong;}
public long[] getALong() {return aLong;}

public void setABooleanTrue(boolean aBooleanTrue) {this.aBooleanTrue = aBooleanTrue;}
public boolean getABooleanTrue() {return aBooleanTrue;}

public void setAChar(char aChar) {this.aChar = aChar;}
public char getAChar() {return aChar;}

public void setAByte(byte aByte) {this.aByte = aByte;}
public byte getAByte() {return aByte;}

public void setAShort(short aShort) {this.aShort = aShort;}
public short getAShort() {return aShort;}

public void setAFloat(float aFloat) {this.aFloat = aFloat;}
public float getAFloat() {return aFloat;}

public void setAnInteger(Integer anInteger) {this.anInteger = anInteger;}
public Integer getAnInteger() {return anInteger;}

public void setMyInteger(Integer myInteger) {this.myInteger = myInteger;}
public Integer getMyInteger() {return myInteger;}

public static int getAStaticInt() {return aStaticInt;}

public void setState(int state) {this.state = state;}
public int getState() {return state;}

public void setABooleanFalse(boolean aBooleanFalse) {this.aBooleanFalse = aBooleanFalse;}
public boolean getABooleanFalse() {return aBooleanFalse;}

public static void setTestScenario(int TestScenarioArg) {testScenario = TestScenarioArg;}
public static int getTestScenario() {return testScenario;}

public void initialize()
{
	super.initialize();
	aDouble = 123456789000.0123456789;
	aString = "This is a string";
	aBooleanTrue = true;
	aChar = 'Q';
	aByte = 127;
	aShort = 32767;
	aFloat = 1234.567890f;
	myInteger = new Integer(17);
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
				case TEST_SCENARIO_1: // states: A1 B A1 A3
					setX(10); setY(5);
					port[Partner].sendMessage(SIG2, null, this);
					port[Partner].sendMessage(SIG3, null, this);
					port[Partner].sendMessage(SIG1, null, this);
					break;
				case TEST_SCENARIO_2: // states: A1 A1 A2
					x = 9; y = 6;
					port[Partner].sendMessage(SIG2, null, this);
					port[Partner].sendMessage(SIG3, null, this); // should be deferred
					port[Partner].sendMessage(SIG1, null, this);
					break;
				case TEST_SCENARIO_3: // states: A1 B A1 A3 terminate
					x = 10; y = 5;
					port[Partner].sendMessage(SIG2, null, this);
					port[Partner].sendMessage(SIG1, null, this);
					port[Partner].sendMessage(SIG1, null, this);
					port[Partner].sendMessage(SIG1, null, this);
					break;
				case TEST_SCENARIO_4: // states: A1 B finalState
					x = 10; y = 5;
					port[Partner].sendMessage(SIG2, null, this);
					port[Partner].sendMessage(SIG2, null, this);
					break;
				case TEST_SCENARIO_5: // states: A1
					break;
				case TEST_SCENARIO_6: // states: A1 A1 A3 B
					x = 11; y = 6;
					port[Partner].sendMessage(SIG2, null, this);
					port[Partner].sendMessage(SIG3, null, this); // should be deferred
					port[Partner].sendMessage(SIG1, null, this);
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
			System.out.println("XhTestFsm: message with no receiver " + msg);
		}
		break;
	}
}

public void performActivity(int activityId, IMessage msg)
{
	switch (activityId) {
		case 6621749: // FsmXholon StateA SIG2
println("StateA1 trans SIG2");
			break;
		case 1571744: // FsmXholon StateA 
println("Choice else");
			break;
		case 9761723: // FsmXholon StateA SIG1
println("StateA1 trans SIG1 x < 10");
			break;
		case 5181731: // FsmXholon StateA SIG1
println("StateA1 trans SIG1 x >= 10");
			break;
		case 439146: // FsmXholon StateA SIG1
println("StateA3  trans SIG1");
			break;
		case 8341955: // FsmXholon StateA SIG3
println("StateA3  trans SIG3");
			break;
		case 7291938: // FsmXholon StateA 
println("Choice y == 5");
			break;
		case 9161769: // FsmXholon Top 
println("init transition");
			break;
		case 5681751: // FsmXholon Top SIG3
println("StateB  trans SIG3");
			break;
		case 891125: // FsmXholon Top SIG2
println("StateB  trans SIG2");
			break;
		case 9421219: // FsmXholon Top SIG1
println("StateB  trans SIG1");
			break;
		case 79713: // FsmXholon Top 
println("StateA  from entry point to StateA1");
			break;
		case 3631956: // FsmXholon Top 
println("StateA  from exit point to StateB");
			break;
		case 3691739: // entry StateA1
println("StateA1 entry");

			break;
		case 6291735: // entry StateA2
println("StateA2 entry");
			break;
		case 5991737: // entry StateA3
println("StateA3 entry");
			break;
		case 1641291: // entry StateA

			break;
		case 1141733: // exit StateA1
println("StateA1 exit");

			break;
	default:
		System.out.println("XhTestFsm: performActivity() unknown Activity " + activityId);
		break;
	}
}

public boolean performGuard(int activityId, IMessage msg)
{
	switch (activityId) {
		case 4651742: // 2
			return true; // else
		case 9661724: // CNPT_OUTGOING2
			return x < 10;
		case 4621726: // CNPT_OUTGOING3
			return x >= 10;
		case 1111937: // 1
			return getY() == 5;
	default:
		System.out.println("XhTestFsm: performGuard() unknown Activity " + activityId);
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

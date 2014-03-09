/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2007, 2008 Ken Webb
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
 */

package org.primordion.user.app.TestFsm;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.XholonWithPorts;

/**
 * Test FSM. Test all finite state machine (FSM) features supported by Xholon.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.7 (Created on August 27, 2007)
 */

public class XhTestFsmKW extends XholonWithPorts implements CeTestFsmKW {

	// references to other Xholon instance; indices into the port array
	public static final int P_PARTNER = 0;
	
	// Signals, Events
	// signals for interface used between P_PARTNER ports
	public static final int SIG1 = 100; // no data
	public static final int SIG2 = 200; // no data
	public static final int SIG3 = 300; // no data
	
	// Activity IDs
	public static final int ACTIVITY_5 = 5;
	
	// Test Scenarios
	public static final int TEST_SCENARIO_1 = 1;
	public static final int TEST_SCENARIO_2 = 2;
	public static final int TEST_SCENARIO_3 = 3;
	public static final int TEST_SCENARIO_4 = 4;
	public static final int TEST_SCENARIO_5 = 5;
	protected static int testScenario = TEST_SCENARIO_4;
	
	public int state = 0;
	public String roleName = null;
	
	// static variables for use by FsmXholon to test guard conidions on transitions
	protected static int x = 10;
	protected static int y = 5;
		
	/**
	 * Constructor.
	 */
	public XhTestFsmKW() {}
	
	/**
	 * Set which test scenario to run.
	 * @param testScen The test scenario.
	 */
	public static void setTestScenario(int testScen) {testScenario = testScen;}
	
	/**
	 * Get which test scenario is being run.
	 * @return The test scenario.
	 */
	public static int getTestScenario() {return testScenario;}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
		state = 0;
		roleName = null;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setRoleName(java.lang.String)
	 */
	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getRoleName()
	 */
	public String getRoleName()
	{
		return roleName;
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#act()
	 */
	public void act()
	{
		switch(xhc.getId()) {
		case TestFsmSystemCE:
			// Only process the messages that are currently in the Q.
			// Don't process messages added to Q during this timestep.
			processMessageQ();
			break;

		case TestHarnessCE:
			// do actions required as part of an initial transition in a state machine
			// Xholon StateMachine classes do this automatically
			if (state == 0) {
				println("Harness ");
				switch(testScenario) {
				case TEST_SCENARIO_1: // states: A1 B A1 A3
					x = 10; y = 5;
					port[P_PARTNER].sendMessage(SIG2, null, this);
					port[P_PARTNER].sendMessage(SIG3, null, this);
					port[P_PARTNER].sendMessage(SIG1, null, this);
					break;
				case TEST_SCENARIO_2: // states: A1 A1 A2
					x = 9; y = 6;
					port[P_PARTNER].sendMessage(SIG2, null, this);
					port[P_PARTNER].sendMessage(SIG3, null, this);
					port[P_PARTNER].sendMessage(SIG1, null, this);
					break;
				case TEST_SCENARIO_3: // states: A1 B A1 A3 terminate
					x = 10; y = 5;
					port[P_PARTNER].sendMessage(SIG2, null, this);
					port[P_PARTNER].sendMessage(SIG1, null, this);
					port[P_PARTNER].sendMessage(SIG1, null, this);
					port[P_PARTNER].sendMessage(SIG1, null, this);
					break;
				case TEST_SCENARIO_4: // states: A1 B finalState
					x = 10; y = 5;
					port[P_PARTNER].sendMessage(SIG2, null, this);
					port[P_PARTNER].sendMessage(SIG2, null, this);
					break;
				case TEST_SCENARIO_5: // states: A1
					break;
				default:
					println(testScenario + " is an invalid TestScenario.");
					break;
				}
				state = 1;
			}
			break;
			
		default:
			break;
		}

		super.act();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#processReceivedMessage(org.primordion.xholon.base.Message)
	 */
	public void processReceivedMessage(IMessage msg)
	{
		switch(xhc.getId()) {
		
		// This is the FSM being tested.
		case FsmXholonCE:
			if (this.hasChildNodes()) {
				((StateMachineEntity)getFirstChild()).doStateMachine(msg); // StateMachine
			}
			break; // end FsmXholonCE
		
		default:
			println("XhTestFsmKw: message with no receiver " + msg);
			break;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#performActivity(int, org.primordion.xholon.base.IMessage)
	 */
	public void performActivity(int activityId, IMessage msg)
	{
		switch (activityId) {
		case 1: // init
			println("init transition");
			break;
		case 2: // entry StateA
			println("StateA entry");
			break;
		case 3: // entry StateA1
			println("StateA1 entry");
			break;
		case 4: // exit StateA1
			println("StateA1 exit");
			break;
		case ACTIVITY_5: //5: // sig2
			println("StateA1 trans SIG2");
			break;
		case 6: // sig1lt10
			println("StateA1 trans SIG1 x < 10");
			break;
		case 7: // sig1ge10
			println("StateA1 trans SIG1 x >= 10");
			break;
		case 8: // entry StateA2
			println("StateA2 entry");
			break;
		case 9: // entry StateA3
			println("StateA3 entry");
			break;
		case 10: // sig1
			println("StateA3  trans SIG1");
			break;
		case 11: // sig3
			println("StateA3  trans SIG3");
			break;
		case 12: // else
			println("Choice else");
			break;
		case 13: // choice y == 5
			println("Choice y == 5");
			break;
		case 14: // sig3
			println("StateB  trans SIG3");
			break;
		case 15: // sig2
			println("StateB  trans SIG2");
			break;
		case 16: // sig1
			println("StateB  trans SIG1");
			break;
		default:
			println("XhTestFsmKW: performActivity() unknown Activity " + activityId);
			break;
		}
	}

	/*
	 * @see org.primordion.xholon.base.Xholon#performGuard(int, org.primordion.xholon.base.IMessage)
	 */
	public boolean performGuard(int activityId, IMessage msg)
	{
		switch (activityId) {
		case 101: // StateA1 x < 10
			return x < 10;
		case 102: // StateA1 x >= 10
			return x >= 10;
		case 103: // ChoiceA else
			return true;
		case 104: // ChoiceA y == 5
			return y == 5;
		default:
			println("XhTestFsmKW: performGuard() unknown Activity " + activityId);
			return false;
		}
	}
	
	/*
	 * @see java.lang.Object#toString()
	 */
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
		outStr += " state=" + state;
		return outStr;
	}
}

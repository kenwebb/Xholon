/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2006, 2007, 2008 Ken Webb
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

package org.primordion.memcomp.app.Fsm06ex1_Fsm;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.util.MiscRandom;

/**
 * Membrane Computing.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on Jan 17, 2006)
 */
public class XhFsm06ex1_Fsm extends XholonWithPorts {

	// references to other Xholon instances; indices into the port array
	public static final int P_PARTNER = 0;

	// Signals, Events
	public static final int SOUT_P = 100;
	public static final int SOUT_Q = 110;
	public static final int SOUT_R = 120;
	public static final int SIN_M = 200;
	public static final int SIN_N = 210;

	public int state = 0;
	public String roleName = null;
	
	// static variable for use by Membrane2 to test guard conidion on transitions
	protected static int mChoice = 2; // only valid values will be 0 and 1
	
	/**
	 * Constructor.
	 */
	public XhFsm06ex1_Fsm() {}
	
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
		case CeFsm06ex1_Fsm.Membrane1CE:
			// Only process the messages that are currently in the Q.
			// Don't process messages added to Q during this timestep.
			processMessageQ();
			break;
		
		case CeFsm06ex1_Fsm.Membrane2CE:
			// send self a message to get things going
			if (state == 0) {
				println("Starting ... ");
				this.sendMessage(SIN_M, null, this);
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
		
		// Membrane2
		case CeFsm06ex1_Fsm.Membrane2CE:
			switch(msg.getSignal()) {
			case SIN_M: println("M2: (m, in)"); break;
			case SIN_N: println("M2: (n, in)"); break;
			}
			if (this.hasChildNodes()) {
				((StateMachineEntity)getFirstChild()).doStateMachine(msg); // StateMachine
			}
			break; // end FsmXholonCE
		
		// Membrane3
		case CeFsm06ex1_Fsm.Membrane3CE:
			switch(msg.getSignal()) {
			case SOUT_P: println("M3: (p, in)"); break;
			case SOUT_Q: println("M3: (q, in)"); break;
			case SOUT_R: println("M3: (r, in)"); break;
			}
			if (this.hasChildNodes()) {
				((StateMachineEntity)getFirstChild()).doStateMachine(msg); // StateMachine
			}
			break; // end FsmXholonCE
		
		default:
			println("XhFsm06ex1_Fsm: message with no receiver " + msg);
			break;
		}
	}
	
	public void performActivity(int activityId, IMessage msg)
	{
		switch (activityId) {
		case 36471992: // sinNfromB
println("M2: bn -> aq");
println("M2: (q, out)");
port[P_PARTNER].sendMessage(SOUT_Q, null, this);
			break;
		case 887811995: // mFromB
println("M2: bm -> cr");
println("M2: (r, out)");
port[P_PARTNER].sendMessage(SOUT_R, null, this);
			break;
		case 5596918: // sinMfromA
// the choice of which transition to take is non-deterministic
// get a random number, either 0 or 1
mChoice = MiscRandom.getRandomInt(0,9);
			break;
		case 75715137: // mChoice>0
println("M2: am -> bp");
println("M2: (p, out)");
port[P_PARTNER].sendMessage(SOUT_P, null, this);
			break;
		case 85698141: // mChoice==0
println("M2: am -> c");
			break;
		case 899281219: // pFromA
println("M3: ap -> bn");
println("M3: (n, out)");
port[P_PARTNER].sendMessage(SIN_N, null, this);
			break;
		case 856941214: // qFromB
println("M3: bq -> am");
println("M3: (m, out)");
port[P_PARTNER].sendMessage(SIN_M, null, this);
			break;
		case 6553419: // 
println("Starting ...");
port[P_PARTNER].sendMessage(SIN_M, null, this);
			break;
		case 598251291: // qFromD
println("M3: dq -> e");
			break;
		case 257731217: // rFromC
println("M3: cr -> d, where c is either a or b");
			break;
		case 839291293: // pFromD
println("M3: dp -> a");
			break;
		default:
			println("XhStateMachineEntities: performActivity() unknown Activity " + activityId);
			break;
		}
	}

	/*
	 * @see org.primordion.xholon.base.Xholon#performGuard(int)
	 */
	public boolean performGuard(int activityId, IMessage msg)
	{
		switch (activityId) {
		case 22459135: // 1
			return mChoice > 0;
		case 95867138: // 2
			return mChoice == 0;
		default:
			println("XhStateMachineEntities: performGuard() unknown Activity " + activityId);
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
		//outStr += " state=" + state;
		return outStr;
	}
}

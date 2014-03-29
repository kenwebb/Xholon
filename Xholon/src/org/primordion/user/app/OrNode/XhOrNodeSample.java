/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2005, 2006, 2007, 2008 Ken Webb
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

package org.primordion.user.app.OrNode;

import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.XholonWithPorts;

/**
 * OrNode Sample.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on April 27, 2008)
 */

public class XhOrNodeSample extends XholonWithPorts implements CeOrNodeSample {

	// references to other Xholon instance; indices into the port array
	public static final int P_PARTNER = 0;
	
	// Signals, Events
	// signals for interface used between P_PARTNER ports
	public static final int SIGNAL_ONE = 100; // no data
	
	public int state = 0;
	private String roleName = null;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setRoleName(java.lang.String)
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getRoleName()
	 */
	public String getRoleName() {
		return roleName;
	}
	
	/**
	 * Constructor.
	 */
	public XhOrNodeSample() {}
	
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
	 * @see org.primordion.xholon.base.IXholon#act()
	 */
	public void act()
	{
		switch(xhc.getId()) {
		case HelloWorldSystemCE:
			// Only process the messages that are currently in the Q.
			// Don't process messages added to Q during this timestep.
			processMessageQ();
			break;

		case HelloCE:
			// do actions required as part of an initial transition in a state machine
			// Xholon StateMachine classes do this automatically
			if (state == 0) {
				print("Hello ");
				// test setting the onlyChild by sending a system message
				IXholon onlyChild = port[P_PARTNER].getFirstChild().getNextSibling();
				port[P_PARTNER].sendMessage(ISignal.SIGNAL_ORNODE, onlyChild, this);
				// send a regular message
				port[P_PARTNER].sendMessage(SIGNAL_ONE, null, this);
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
		int event = msg.getSignal();
		
		switch(xhc.getId()) {
		case HelloCE:
			switch (state) {
			case 1: // Ready
				switch(event) {
				case SIGNAL_ONE:
					print("Hello ");
					port[P_PARTNER].sendMessage(SIGNAL_ONE, null, this);
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
			break; // end Hello
		
		case WorldCE:
			switch (state) {
			case 0: // Ready
				switch(event) {
				case SIGNAL_ONE:
					println(getWorld() + " !");
					port[P_PARTNER].sendMessage(SIGNAL_ONE, null, this);
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
			break; // end World
		
		default:
			println("XhHelloWorld: message with no receiver " + msg);
			break;
		}
	}
	
	/**
	 * Get the word for "World" in the language specified by the current only child.
	 * @return The word for "World" in the current language.
	 */
	protected String getWorld() {
		if ("en".equals(getRoleName())) {
			return "World";
		}
		else if ("fr".equals(getRoleName())) {
			return "Monde";
		}
		else if ("es".equals(getRoleName())) {
			return "Mondo";
		}
		else if ("de".equals(getRoleName())) {
			return "Welt";
		}
		else if ("ru".equals(getRoleName())) {
			return "Mir";
		}
		else {
			return "BUG";
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

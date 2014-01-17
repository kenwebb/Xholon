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

package org.primordion.user.app.helloworldjnlp;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.XholonWithPorts;

/**
 * Hello World. This is the detailed behavior of a sample Xholon application.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on October 10, 2005)
 */
public class XhHelloWorld extends XholonWithPorts implements CeHelloWorld {

	// references to other Xholon instance; indices into the port array
	public static final int P_PARTNER = 0;
	
	// Signals, Events
	public static final int SIGNAL_ONE = 100; // no data
	
	public int state = 0;
	public String roleName = null;
	
	/**
	 * Constructor.
	 */
	public XhHelloWorld() {}
	
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
			processMessageQ();
			break;

		case HelloCE:
			if (state == 0) {
			  System.out.println(port);
			  if (port != null) {
			    System.out.println(port.length);
			    if (port.length > 0) {
			      System.out.println(port[P_PARTNER]);
			    }
			  }
				print("Hello ");
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
					println("World !");
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
	
	public int getState() {
	  return state;
	}
	
	public void setState(int state) {
	  this.state = state;
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

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

package org.primordion.user.app.HelloWorld_TestTime;

import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholonTime;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.XholonTime;
import org.primordion.xholon.base.XholonTimerTask;
import org.primordion.xholon.base.XholonWithPorts;

/**
 * Hello World. This is the detailed behavior of a sample Xholon application.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on May 4, 2006)
 */

public class XhHelloWorld_TestTime extends XholonWithPorts implements CeHelloWorld {

	// references to other Xholon instance; indices into the port array
	public static final int P_PARTNER = 0;
	
	// Signals, Events
	// signals for interface used between P_PARTNER ports
	public static final int SIGNAL_ONE = 100; // no data
	
	public int state = 0;
	public String roleName = null;
	
	// xholon timers
	static private IXholonTime xhTime = new XholonTime();
	private XholonTimerTask xhTimerTask = null;
	
	// for use with SVG = path from root
	private String mySvgId = null;
	private int inc = 1;
		
	/**
	 * Constructor.
	 */
	public XhHelloWorld_TestTime() {}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
		state = 0;
		roleName = null;
	}
	
	public void postConfigure() {
	  switch(xhc.getId()) {
		//case HelloWorldSystemCE:
		case HelloCE:
		case WorldCE:
	    mySvgId = getXPath().getExpression(this, this.getApp().getRoot(), false);
	    break;
	  default:
	    break;
	  }
	  super.postConfigure();
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
				port[P_PARTNER].sendMessage(SIGNAL_ONE, null, this);
				state = 1;
				// set a timer
				//xhTimerTask = xhTime.timeoutRelative(this, 0);
				xhTimerTask = xhTime.timeoutRepeat(this, "HELLO", 0, 1000);
				//println("Hello scheduledExecutionTime: " + xhTimerTask.scheduledExecutionTime()); // GWT
				//xhTime.timeoutRepeat(this, "BONJOUR", 0, 5000);
			}
			break;
			
		case WorldCE:
			if (state == 0) {
				state = 1;
				// set a timer
				//xhTimerTask = xhTime.timeoutRelative(this, 0);
				xhTimerTask = xhTime.timeoutRepeat(this, "WORLD", 0, 2000);
			}
			break;
			
		default:
			break;
		}

		super.act();
	}
	
	private void doSvg() {
	  if (mySvgId != null) {
		  inc *= -1;
		  doSvg1(mySvgId, inc);
		  doSvg2("text10", this.getApp().getTimeStep());
		}
	}
	
	private native void doSvg1(String svgId, int inc) /*-{
	  var ele = $doc.getElementById(svgId);
	  if (ele) {
	    ele.width.baseVal.value += inc;
	    //ele.getBoundingClientRect().width += inc; // NO
	    //ele.getBBox().width += inc; // NO
	    //ele.setAttributeNS(null, "width", 30); // YES
	  }
	}-*/;
	
	private native void doSvg2(String svgId, int timeStep) /*-{
	  var ele = $doc.getElementById(svgId);
	  if (ele) {
	    ele.firstChild.nodeValue = "HelloWorldSystem timeStep: " + timeStep;
	  }
	}-*/;
	
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
					//print("Hello ");
					port[P_PARTNER].sendMessage(SIGNAL_ONE, null, this);
					break;
				case ISignal.SIGNAL_TIMEOUT:
					println("[Hello] " + msg);
					doSvg();
					//println("Hello scheduledExecutionTime: " + xhTimerTask.scheduledExecutionTime()); // GWT
					// xhTimerTask = xhTime.timeoutRelative(this, 1000);
					//xhTimerTask = xhTime.timeoutRelative(this, new Long(new Date().getTime()), 1000);
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
			case 1: // Ready
				switch(event) {
				case SIGNAL_ONE:
					//println("World !");
					port[P_PARTNER].sendMessage(SIGNAL_ONE, null, this);
					break;
				case ISignal.SIGNAL_TIMEOUT:
					println("[World] " + msg);
					doSvg();
					//xhTimerTask = xhTime.timeoutRelative(this, 1000);
					xhTime.cancel(xhTimerTask);
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
	
	/*
	 * @see org.primordion.xholon.base.Xholon#cleanup()
	 */
	public void cleanup()
	{
		if (xhTimerTask != null) {
			//xhTime.cancel(xhTimerTask);
		}
		super.cleanup();
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

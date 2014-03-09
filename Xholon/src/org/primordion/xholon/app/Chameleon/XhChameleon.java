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

package org.primordion.xholon.app.Chameleon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.base.XholonWithPorts;

/**
	Chameleon application - Xholon Java
	<p>Xholon 0.8.1 http://www.primordion.com/Xholon</p>
*/
public class XhChameleon extends XholonWithPorts implements CeChameleon {
	
	// time step multiplier
	public static int timeStepMultiplier = 1;
	
	// delta t (an increment in time)
	protected static double dt = 1.0 / (double)timeStepMultiplier;
	
	// references to other Xholon instances; indices into the port array
	public static final int SamplePort = 0;
	
	// Signals, Events
	public static final int SIG_SAMPLE = 100;
	
	// Variables
	public String roleName = null;
	private double val;
	
	// Nodes that have registered to receive unhandled messages.
	protected Map<Integer,IXholon> forwardees = null;
	
	// Constructor
	public XhChameleon() {super();}
	
	// Setters and Getters
	public void setRoleName(String roleName) {this.roleName = roleName;}
	public String getRoleName() {return roleName;}
	
	public void setVal(double val) {this.val = val;}
	public double getVal() {return val;}
	
	public void incVal(double incAmount) {val += incAmount;}
	public void decVal(double decAmount) {val -= decAmount;}
	
	public static int getTimeStepMultiplier() {
		return timeStepMultiplier;
	}

	public static void setTimeStepMultiplier(int timeStepMultiplier) {
		XhChameleon.timeStepMultiplier = timeStepMultiplier;
		dt = 1.0 / (double)timeStepMultiplier;
	}
	
	public static double getDt() {
		return dt;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getAllPorts()
	 */
	@SuppressWarnings("unchecked")
	public List getAllPorts() {
		List<PortInformation> xhcPortList = this.getXhc().getPortInformation();
		List<PortInformation> realPortList = new ArrayList<PortInformation>();
		// eval the XPath expressions to determine the reffed nodes
		Iterator<PortInformation> portIt = xhcPortList.iterator();
		while (portIt.hasNext()) {
			PortInformation pi = (PortInformation)portIt.next();
			IXholon reffedNode = this.getXPath().evaluate(pi.getXpathExpression().trim(), this);
			if (reffedNode == null) {
				//xhcPortList.remove(pi); // causes java.util.ConcurrentModificationException
			}
			else {
				pi.setReffedNode(reffedNode);
				realPortList.add(pi);
			}
		}
		return realPortList;
	}
	
	public void initialize()
	{
		super.initialize();
	}
	
	public void configure() {
	  //consoleLog("configure");
	  bindPorts();
	  if (firstChild != null) {
			firstChild.configure();
		}
		if (nextSibling != null) {
			nextSibling.configure();
		}
	}
	
	public void postConfigure()
	{
		switch(xhc.getId()) {
		default:
			break;
		}
		super.postConfigure();
	}
	
	public void act()
	{
		switch(xhc.getId()) {
		case ChameleonCE:
			processMessageQ();
			break;
		default:
			break;
		}
		super.act();
	}
	
	/*public void processReceivedMessage(IMessage msg)
	{
		switch(xhc.getId()) {
		default:
			break;
		}
	}*/
	
	/*
	 * @see org.primordion.xholon.base.IXholon#registerMessageForwardee(org.primordion.xholon.base.IXholon, int[])
	 */
	public void registerMessageForwardee(IXholon forwardee, int[] signal) {
	  if (forwardee == null) {return;}
	  if (forwardees == null) {
	    forwardees = new HashMap<Integer,IXholon>();
	  }
	  if (signal == null) {
	    forwardees.put(null, forwardee);
	  }
	  else {
	    for (int i = 0; i < signal.length; i++) {
	      forwardees.put(signal[i], forwardee);
	    }
	  }
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#forwardMessage(org.primordion.xholon.base.Message)
	 */
	public void forwardMessage(IMessage msg) {
	  int signal = msg.getSignal();
	  IXholon forwardee = forwardees.get(signal);
	  if (forwardee == null) {
	    forwardee = forwardees.get(null);
	  }
    if (forwardee != null) {
      forwardee.processReceivedMessage(msg);
    }
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#performActivity(int, org.primordion.xholon.base.Message)
	 */
	public void performActivity(int activityId, IMessage msg)
	{
		int signal = msg.getSignal();
	  IXholon forwardee = forwardees.get(signal);
	  if (forwardee == null) {
	    forwardee = forwardees.get(null);
	  }
    if (forwardee != null) {
      forwardee.performActivity(activityId, msg);
    }
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#performGuard(int, org.primordion.xholon.base.Message)
	 */
	public boolean performGuard(int activityId, IMessage msg)
	{
		int signal = msg.getSignal();
	  IXholon forwardee = forwardees.get(signal);
	  if (forwardee == null) {
	    forwardee = forwardees.get(null);
	  }
    if (forwardee != null) {
      return forwardee.performGuard(activityId, msg);
    }
		return false;
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

/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2014 Ken Webb
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

package org.primordion.xholon.app.Furcifer;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
//import java.util.Map;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInformation;
//import org.primordion.xholon.base.XholonWithPorts;

/**
	Furcifer application - Xholon Java
	This class has all types of variables, etc. for unit testing.
	<p>Xholon 0.9.0 http://www.primordion.com/Xholon</p>
*/
public class FurciferAll extends XhFurcifer implements CeFurcifer {
	
	// time step multiplier
	//public static int timeStepMultiplier = 1;
	
	// delta t (an increment in time)
	//protected static double dt = 1.0 / (double)timeStepMultiplier;
	
	// references to other Xholon instance; indices into the port array
	public static final int P_PARTNER = 0;
	
	// Signals, Events
	public static final int SIGNAL_ONE = 100; // no data
	
	// Variables (needed for unit testing)
	public int state = 0;
	public String roleName = null;
	private double val;
	private String val_String;
	private Object val_Object;
	private String anno;
	
	// Constructor
	public FurciferAll() {super();}
	
	// Setters and Getters
	public void setRoleName(String roleName) {this.roleName = roleName;}
	public String getRoleName() {return roleName;}
	
	public void setVal(double val) {this.val = val;}
	public double getVal() {return val;}
	
	public void incVal(double incAmount) {val += incAmount;}
	public void decVal(double decAmount) {val -= decAmount;}
	
	public void setVal_String(String val) {this.val_String = val;}
	public String getVal_String() {return val_String;}
	
	public void setVal_Object(Object val) {this.val_Object = val;}
	public Object getVal_Object() {return val_Object;}
	
	public void setAnnotation(String val) {this.anno = val;}
	public String getAnnotation() {return anno;}

	//public static int getTimeStepMultiplier() {
	//	return timeStepMultiplier;
	//}

	//public static void setTimeStepMultiplier(int timeStepMultiplier) {
	//	FurciferAll.timeStepMultiplier = timeStepMultiplier;
	//	dt = 1.0 / (double)timeStepMultiplier;
	//}
	
	//public static double getDt() {
	//	return dt;
	//}
	
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
		state = 0;
		roleName = null;
	}
	
	/*public void configure() {
	  //consoleLog("configure");
	  bindPorts();
	  if (firstChild != null) {
			firstChild.configure();
		}
		if (nextSibling != null) {
			nextSibling.configure();
		}
	}*/
	
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
			println("FurciferAll: message with no receiver " + msg);
			break;
		}
	}
	
	public int getState() {
	  return state;
	}
	
	public void setState(int state) {
	  this.state = state;
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
		outStr += " state=" + state;
		return outStr;
	}
	
}

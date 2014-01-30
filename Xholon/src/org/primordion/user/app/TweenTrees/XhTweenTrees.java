/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2010 Ken Webb
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

package org.primordion.user.app.TweenTrees;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.XholonWithPorts;

/**
	TweenTrees application - Xholon Java
	<p>Xholon 0.8.1 http://www.primordion.com/Xholon</p>
*/
public class XhTweenTrees extends XholonWithPorts implements CeTweenTrees {

// references to other Xholon instances; indices into the port array
public static final int SamplePort = 0;

// Signals, Events
public static final int SIG_SAMPLE = 100;

// Variables
public String roleName = null;
private double val; // AnotherPartOfTheSystem
protected static String appSpecificParam1 = "This is a test parameter."; // XhnParams

// Constructor
public XhTweenTrees() {super();}

// Setters and Getters
public void setRoleName(String roleName) {this.roleName = roleName;}
public String getRoleName() {return roleName;}

public void setVal(double val) {this.val = val;}
public double getVal() {return val;}

public static void setAppSpecificParam1(String AppSpecificParam1Arg) {appSpecificParam1 = AppSpecificParam1Arg;}
public static String getAppSpecificParam1() {return appSpecificParam1;}

public void initialize()
{
	super.initialize();
}

public void postConfigure()
{
	switch(xhc.getId()) {
	//case RrrCE:
		/*
		 * At runtime, Rrr will be responsible for making itself and its subtree into a tween tree.
		 * It will:
		 * - disconnect B and C,
		 * - connect B to S,
		 * - connect V to C,
		 * - remove itself as a child of TheSystem and thus becoming a true tween tree. 
		 */
		//IXholon bbb = getXPath().evaluate("ancestor::TheSystem/Aaa/Bbb", this);
		//IXholon bbbTarget = bbb.getPort(0);
		//IXholon sss = getXPath().evaluate("./Sss", this);
		//IXholon vvv = getXPath().evaluate("./Vvv", this);
		//((XholonWithPorts)bbb).setPort(0, sss);
		//((XholonWithPorts)vvv).setPort(0, bbbTarget);
		//removeChild();
		//break;
	default:
		break;
	}
	super.postConfigure();
}

public void preAct()
{
	switch(xhc.getId()) {
	default:
		break;
	}
	super.preAct();
}

public void act()
{
	switch(xhc.getId()) {
	case TheSystemCE:
		processMessageQ();
		break;
	case BbbCE:
		port[0].sendMessage(123, getName("^^C^^^"), this);
		break;
	default:
		break;
	}
	super.act();
}

public void postAct()
{
	switch(xhc.getId()) {
	default:
		break;
	}
	super.postAct();
}

public void processReceivedMessage(IMessage msg)
{
	switch(xhc.getId()) {
	case CccCE:
		System.out.println("Ccc received message: " + msg);
		break;
	default:
		//for (int i = 0; i < port.length; i++) {
		//	if (port[i] != null) {
		//		port[i].sendMessage(msg.getSignal(), msg.getData() + " " + getName("^^C^^^"), this); //msg.getSender());
		//	}
		//}
		break;
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

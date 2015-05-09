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

package org.primordion.user.app.TurtleExample1;

import org.primordion.xholon.base.ITurtle;
import org.primordion.xholon.base.PatchOwner;
import org.primordion.xholon.base.XholonWithPorts;

/**
	TurtleExample1 application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   21/02/2007</p>
	<p>File:   TurtleExample1.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Wed Feb 21 13:46:13 EST 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class XhTurtleExample1 extends XholonWithPorts implements CeTurtleExample1 {

// references to other Xholon instances; indices into the port array
public static final int PatchOwner = 0;

// Signals, Events
public static final int SIG_SELF = 100;

// Variables
public String roleName = null;
protected static String appSpecificParam1 = ""; // XhnParams

// Constructor
public XhTurtleExample1() {super();}

// Setters and Getters
public void setRoleName(String roleName) {this.roleName = roleName;}
public String getRoleName() {return roleName;}

public static void setAppSpecificParam1(String AppSpecificParam1Arg) {appSpecificParam1 = AppSpecificParam1Arg;}
public static String getAppSpecificParam1() {return appSpecificParam1;}

public void initialize()
{
	super.initialize();
}

public void postConfigure()
{
	switch(xhc.getId()) {
	case TurtleObserverCE:
	{
	  if (!("nil".equals(appSpecificParam1))) {
		  ((PatchOwner)port[PatchOwner]).cp();
		  ((PatchOwner)port[PatchOwner]).crt(12); // 12 for inspi
		}
		
		// test creation of turtles of a special breed
		//((PatchOwner)port[PatchOwner]).crt(10, "Sheep");
		
		// get first turtle and move it around
		if ("test".equals(appSpecificParam1)) {
			ITurtle t = (ITurtle)getXPath().evaluate("./descendant::Turtle", port[PatchOwner]);
			moveTurtleAround(t);
		}
	}
		break;
	default:
		break;
	}
	super.postConfigure();
}

public void act()
{
	switch(xhc.getId()) {
	case TheSystemCE:
		processMessageQ();
		break;
	default:
		break;
	}
}

/**
 * Test having a Turtle Observer move a turtle around.
 * @param t
 */
protected void moveTurtleAround(ITurtle t)
{
	if (t != null) {
		t.jump(20);
		// draw a square
		for (int i = 0; i < 4; i++) {
			t.fd(40);
			t.rt(90);
		}
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

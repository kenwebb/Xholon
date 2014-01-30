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

package org.primordion.user.app.WaterLogic;

import java.util.Vector;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.util.MiscRandom;

/**
	WaterLogic application - Xholon Java
	<p>Xholon 0.7 http://www.primordion.com/Xholon</p>
*/
public class XhWaterLogic extends XholonWithPorts implements CeWaterLogic {

// references to other Xholon instances; indices into the port array
public static final int SamplePort = 0;

// Signals, Events
public static final int SIG_TRACE = 100;

// Variables
public String roleName = null;
private boolean val;
protected static int testScenario = 1; // XhnParams

// Constructor
public XhWaterLogic() {super();}

// Setters and Getters
public void setRoleName(String roleName) {this.roleName = roleName;}
public String getRoleName() {return roleName;}

public void setVal(boolean val) {this.val = val;}
public boolean getVal_boolean() {return val;}

public static void setTestScenario(int TestScenarioArg) {testScenario = TestScenarioArg;}
public static int getTestScenario() {return testScenario;}

public void initialize()
{
	super.initialize();
}

public void postConfigure()
{
	int siblingCount = getNumSiblings();
	IXholon target;
	int rNumJ;
	
	switch(xhc.getId()) {
	case NerveStateCE:
	{
		switch (testScenario) {
		case 1: // each nerveState has only one connector (de Bono "Jelly Fish")
			target = this; // initialize target to current node
			// find nth sibling, and make this the target node
			rNumJ = MiscRandom.getRandomInt(1, siblingCount);
			for (int j = 0; j < rNumJ; j++) {
				target = target.getNextSibling();
				if (target == null) {
					// have reached last sibling, so wrap around to the first sibling
					target = this.getFirstSibling();
				}
			}
			// find the first available port, and have this point to the target node
			for (int k = 0; k < port.length; k++) {
				if (port[k] == null) {
					port[k] = target;
					break;
				}
			}
			break;
		case 2:
			int rNumI = MiscRandom.getRandomInt(1, 3); // create between 1 and n links
			for (int i = 0; i < rNumI; i++) {
				target = this;
				rNumJ = MiscRandom.getRandomInt(1, siblingCount);
				for (int j = 0; j < rNumJ; j++) {
					target = target.getNextSibling();
					if (target == null) {
						// have reached last sibling, so wrap around to the first sibling
						target = this.getFirstSibling();
					}
				}
				for (int k = 0; k < port.length; k++) {
					if (port[k] == null) {
						port[k] = target;
						break;
					}
				}
			}
			break;
		default:
			println("Invalid test scenario: " + testScenario);
			break;
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
	case WaterLogicSystemCE:
		processMessageQ();
		break;
	default:
		break;
	}
	//super.act();
}

public void processReceivedMessage(IMessage msg)
{
	switch(getXhcId()) {
	case NerveStateCE:
		print(" " + getId());
		if (val == false) {
			port[0].sendMessage(SIG_TRACE, null, this);
			val = true; // this node has been reached
		}
		else {
			print("\n");
		}
		break;
	default:
		break;
	}
}

/*
 * @see org.primordion.xholon.base.Xholon#handleNodeSelection()
 */
public Object handleNodeSelection()
{
	if (getXhcId() == NerveStateCE) {
		// initialize whether or not all node have yet been reached
		Vector v = getSiblings();
		for (int i = 0; i < v.size(); i++) {
			IXholon sib = (IXholon)v.elementAt(i);
			sib.setVal(false);
		}
		print(" " + getId());
		port[0].sendMessage(SIG_TRACE, null, this);
	}
	return toString();
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

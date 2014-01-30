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

package org.primordion.user.app.RavaszHnm;

import java.util.ArrayList;
import java.util.List;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.base.XholonWithPorts;

/**
	RavaszHnm application - Xholon Java
	<p>Xholon 0.8.1 http://www.primordion.com/Xholon</p>
*/
public class XhRavaszHnm extends XholonWithPorts implements CeRavaszHnm {

// references to other Xholon instances; indices into the port array
public static final int SamplePort = 0;

// Signals, Events
public static final int SIG_SAMPLE = 100;

// Variables
public String roleName = null;

/**
 * The number of siblings that a PeripheralNode has.
 * ex: 1.0 2.0 3.0 4.0 5.0 ...
 */
private double val;

protected static String appSpecificParam1 = "This is a test parameter.";

// Constructor
public XhRavaszHnm() {super();}

// Setters and Getters
public void setRoleName(String roleName) {this.roleName = roleName;}
public String getRoleName() {return roleName;}

public void setVal(double val) {this.val = val;}
public double getVal() {return val;}

public static void setAppSpecificParam1(String AppSpecificParam1Arg) {appSpecificParam1 = AppSpecificParam1Arg;}
public static String getAppSpecificParam1() {return appSpecificParam1;}

/*
 * @see org.primordion.xholon.base.IXholon#getAllPorts(org.primordion.xholon.base.IXholon)
 */
public List getAllPorts()
{
	List portList = new ArrayList();
	switch (getXhcId()) {
	case PeripheralNodeCE:
		// node links to its n siblings, plus all its ancestors
		IXholon linkedNode = getNextSibling();
		for (int i = 0; i < getVal(); i++) {
			if ((linkedNode == null) || (linkedNode.getXhcId() != this.getXhcId())) {
				//break;
				linkedNode = getFirstSibling();
			}
			PortInformation portInfo = new PortInformation("sibling", linkedNode);
			portList.add(portInfo);
			linkedNode = linkedNode.getNextSibling();
		}
		linkedNode = getParentNode();
		if (this.getNumSiblings() > getVal()) {
			// this PeripheralNode has CentralNode siblings
			PortInformation portInfo = new PortInformation("centralNode", linkedNode);
			portList.add(portInfo);
		}
		else {
			while (!linkedNode.isRootNode()) {
				PortInformation portInfo = new PortInformation("centralNode", linkedNode);
				portList.add(portInfo);
				linkedNode = linkedNode.getParentNode();
			}
		}
		break;
	default:
		break;
	}
	return portList;
}

/*
 * @see org.primordion.xholon.base.XholonWithPorts#postConfigure()
 */
public void postConfigure()
{
	switch(xhc.getId()) {
	case PeripheralNodeCE:
	case CentralNodeCE:
		// set this recursively, if it hasn't already been set
		if (getVal() == 0.0) {
			setVal(getParentNode().getVal());
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
	case HierarchicalNetworkModelCE:
		processMessageQ();
		break;
	default:
		break;
	}
	super.act();
}

public void processReceivedMessage(IMessage msg)
{
	switch(xhc.getId()) {
	default:
		break;
	}
}

public String toString() {
	String outStr = getName();
	switch(xhc.getId()) {
	case HierarchicalNetworkModelCE:
		outStr += " n=" + this.getNumLevels() + " N=" + this.getNumChildren(true);
		break;
	case PeripheralNodeCE:
		outStr += " " + getAllPorts();
		break;
	case CentralNodeCE:
		outStr += " depth:" + depth() + " n:" + (depth()-1);
		break;
	default:
		break;
	}
	return outStr;
}

}

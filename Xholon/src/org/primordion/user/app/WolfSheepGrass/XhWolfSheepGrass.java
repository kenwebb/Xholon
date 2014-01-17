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

package org.primordion.user.app.WolfSheepGrass;

import org.primordion.xholon.base.PatchOwner;
import org.primordion.xholon.base.XholonWithPorts;

/**
 * Wolf Sheep Grass.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on March 1, 2007)
 * @see The original NetLogo wolf sheep predation model is: Copyright 1998 Uri Wilensky. All rights reserved.
 * See http://ccl.northwestern.edu/netlogo/models/WolfSheepPredation for terms of use.
*/
public class XhWolfSheepGrass extends XholonWithPorts implements CeWolfSheepGrass {

// references to other Xholon instances; indices into the port array
public static final int PatchOwner = 0;

// Variables
public String roleName = null;

// Constructor
public XhWolfSheepGrass() {super();}

// Setters and Getters
public void setRoleName(String roleName) {this.roleName = roleName;}
public String getRoleName() {return roleName;}

public void initialize()
{
	super.initialize();
}

public void postConfigure()
{
	switch(xhc.getId()) {
	case TurtleObserverCE:
	{
		((PatchOwner)port[PatchOwner]).ca();
		((PatchOwner)port[PatchOwner]).crt(WolfSheep.initialNumberSheep, "Sheep");
		((PatchOwner)port[PatchOwner]).crt(WolfSheep.initialNumberWolves, "Wolf");
		println("MinPxcor : " + ((PatchOwner)port[PatchOwner]).getMinPxcor());
		println("MinPycor : " + ((PatchOwner)port[PatchOwner]).getMinPycor());
		println("MaxPxcor : " + ((PatchOwner)port[PatchOwner]).getMaxPxcor());
		println("MaxPycor : " + ((PatchOwner)port[PatchOwner]).getMaxPycor());
		println("WorldWidth : " + ((PatchOwner)port[PatchOwner]).getWorldWidth());
		println("WorldHeight : " + ((PatchOwner)port[PatchOwner]).getWorldHeight());
	}
		break;
	default:
		break;
	}
	super.postConfigure();
}

public void act()
{
	if (isRootNode()) {
		processMessageQ();
	}
	super.act();
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

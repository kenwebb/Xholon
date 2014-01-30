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

package org.primordion.user.app.Risk;

import org.primordion.xholon.base.XholonWithPorts;

/**
	Risk application - Xholon Java
	<p>Xholon 0.8.1 http://www.primordion.com/Xholon</p>
*/
public class XhRisk extends XholonWithPorts implements CeRisk {

// Variables
public String roleName = null;

// Constructor
public XhRisk() {super();}

// Setters and Getters
public void setRoleName(String roleName) {this.roleName = roleName;}
public String getRoleName() {return roleName;}

/**
 * Get an array of signal IDs.
 * These are used by Interaction to produce sequence diagrams.
 * @return An array.
 */
public static int[] getSignalIDs()
{
	int[] signalIDs = {
		IRiskSignal.SIG_ATTACK,
		IRiskSignal.SIG_MOVE_ARMIES,
		IRiskSignal.SIG_PLACE_ARMIES,
		IRiskSignal.SIG_USE_CARDS,
		IRiskSignal.SIG_END_TURN,
		IRiskSignal.SIG_START_TURN,
		IRiskSignal.SIG_SHOW_CARDS,
		IRiskSignal.SIG_SHOW_TERRITORIES,
		IRiskSignal.SIG_SHOW_CONTINENTS,
		IRiskSignal.SIG_CONQUERED_TERRITORY,
		IRiskSignal.SIG_ELIMINATED_PLAYER,
		IRiskSignal.SIG_MOVE_ARMIES_NONE,
		IRiskSignal.SIG_STARTED_TURN,
		IRiskSignal.SIG_ACK,
		IRiskSignal.SIG_NAK
	};
	return signalIDs;
}

/**
 * Get an array of signal names.
 * These are used by Interaction to produce sequence diagrams.
 * @return An array.
 */
public static String[] getSignalNames()
{
	String[] signalNames = {
		"attack",
		"moveArmies",
		"placeArmies",
		"useCards",
		"endTurn",
		"startTurn",
		"showCards",
		"showTerritories",
		"showContinents",
		"conqueredTerritory",
		"eliminatedPlayer",
		"moveArmiesNone",
		"startedTurn",
		"ack",
		"nak"
	};
	return signalNames;
}

/*
 * @see org.primordion.xholon.base.Xholon#act()
 */
public void act()
{
	switch(xhc.getId()) {
	case TheSystemCE:
		processMessageQ();
		break;
	default:
		break;
	}
	super.act();
}

/*
 * @see org.primordion.xholon.base.XholonWithPorts#toString()
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
	if (getVal() != 0.0) {
		outStr += " val:" + getVal();
	}
	return outStr;
}

}

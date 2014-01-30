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

import org.primordion.xholon.base.IXholon;

/**
 * Players is a container for Plyer objects.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on January 15, 2010)
 */
public class Players extends XhRisk {
	
	private IXholon world = null;

	public IXholon getWorld() {
		return world;
	}

	public void setWorld(IXholon world) {
		this.world = world;
	}
	
	/*
	 * @see org.primordion.user.app.Risk.XhRisk#postConfigure()
	 */
	public void postConfigure() {
		// distribute starting army units to each player
		int numPlayers = getNumChildren(false);
		int startingUnits = 0;
		switch (numPlayers) {
		case 2: startingUnits = 36; break;
		case 3: startingUnits = 35; break;
		case 4: startingUnits = 30; break;
		case 5: startingUnits = 25; break;
		case 6: startingUnits = 20; break;
		default: break;
		}
		IXholon player = getFirstChild();
		while (player != null) {
			((Player)player).setUnplacedArmies(startingUnits);
			player = player.getNextSibling();
		}
		super.postConfigure();
	}
	
}

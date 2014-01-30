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
 * A Territory is a region or country within a continent.
 * The main goal of players in Risk is to conquer territories.
 * The winner is the player who conquers all territories in the world.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on January 15, 2010)
 */
public class Territory extends XhRisk {
	
	/**
	 * A territory must have one or more armies on it.
	 */
	private int quantityArmies = 0;
	
	/**
	 * The player who currently occupies this territory.
	 */
	private IXholon occupier = null;

	public int getQuantityArmies() {
		return quantityArmies;
	}

	public void setQuantityArmies(int quantityArmies) {
		this.quantityArmies = quantityArmies;
	}
	
	/**
	 * Increment the quantity of armies on this territory.
	 * @param inc The increment quantity.
	 */
	public void incQuantityArmies(int inc) {
		quantityArmies += inc;
	}
	
	/**
	 * Decrement the quantity of armies on this territory.
	 * @param dec The decrement quantity.
	 */
	public void decQuantityArmies(int dec) {
		quantityArmies -= dec;
	}
	
	/**
	 * Get the player who currently occupies this territory.
	 * @return
	 */
	public IXholon getOccupier()
	{
		return occupier;
	}

	/**
	 * Set the player who currently occupies this territory.
	 * @param occupier
	 */
	public void setOccupier(IXholon occupier) {
		this.occupier = occupier;
	}
	
	/*
	 * @see org.primordion.user.app.Risk.XhRisk#toString()
	 */
	public String toString()
	{
		return "Territory " + getXhcName() + " is occupied by " + occupier + " with " + quantityArmies + " armies.";
	}
	
}

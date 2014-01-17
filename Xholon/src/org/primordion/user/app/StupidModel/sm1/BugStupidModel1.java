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

package org.primordion.user.app.StupidModel.sm1;

import org.primordion.xholon.base.AbstractGrid;
import org.primordion.xholon.base.IGrid;
import org.primordion.xholon.util.MiscRandom;

/**
	StupidModel1 application - Xholon Java
	<p>Xholon 0.5 http://www.primordion.com/Xholon</p>
*/
public class BugStupidModel1 extends XhStupidModel1 {
	
	private int lastMoved = -1; // which time step the bug was last moved; prevents multiple moves in same time step
	
	/*
	 * @see org.primordion.xholon.base.Xholon#act()
	 */
	public void act()
	{
		move();
	}
	
	/**
	 * Move to another location within 4 cells of the current location.
	 */
	protected void move()
	{
		// get a randomly chosen grid location within +/-4 cells of current location, in both X and Y directions
		int ts = getApp().getTimeStep();
		if (lastMoved < ts) {
			lastMoved = ts;
			boolean foundNewLocation = false;
			int count = 0;
			while ((!foundNewLocation) && (count < 10)) {
				int moveX = MiscRandom.getRandomInt(0, 9) - 4;
				int moveY = MiscRandom.getRandomInt(0, 9) - 4;
				int portX = moveX > 0 ? IGrid.P_EAST : IGrid.P_WEST;
				int portY = moveY > 0 ? IGrid.P_NORTH : IGrid.P_SOUTH;
				int moveIncX = moveX > 0 ? -1 : 1;
				int moveIncY = moveY > 0 ? -1 : 1;
				count++;
				// locate the intended destination
				AbstractGrid destination = (AbstractGrid)parentNode;
				while ((destination != null) && (moveX != 0)) {
					destination = (AbstractGrid)destination.port[portX];
					moveX += moveIncX;
				}
				while ((destination != null) && (moveY != 0)) {
					destination = (AbstractGrid)destination.port[portY];
					moveY += moveIncY;
				}
				// if no one is already at the intended destination, then move there
				if ((destination != null) && (!destination.hasChildNodes())) {
					removeChild(); // detach self from parent and sibling links
					appendChild(destination); // insert as the last child of the destination grid cell
					foundNewLocation = true;
				}
			}
		}
	}
}

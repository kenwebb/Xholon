/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2012 Ken Webb
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

package org.primordion.xholon.mech.petrinet.grid;

import org.primordion.xholon.base.AbstractGrid;
import org.primordion.xholon.base.IGrid;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.util.MiscRandom;

/**
 * A place or transition may move each time step.
 * This class is intended for use in a Grid.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on July 30, 2012)
 */
public class MovingBehavior extends PneBehavior {
	private static final long serialVersionUID = -8099068326262469941L;

	/**
	 * The default number of cells to move at one time.
	 */
	private static final int DEFAULT_MOVE_SIZE = 1;
	
	/**
	 * The number of cells to move at one time.
	 */
	private int moveSize = DEFAULT_MOVE_SIZE;
	
	/**
	 * The upper limit of a random number that will determine the actual +/- move size.
	 */
	private int moveSizeRandom = DEFAULT_MOVE_SIZE * 2 + 1;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#act()
	 */
	public void act() {
		move();
		super.act();
	}
	
	/**
	 * Move to another location within n cells of the current location.
	 */
	protected void move()
	{
		// get a randomly chosen grid location within +/-n cells of current location, in both X and Y directions
		boolean foundNewLocation = false;
		int count = 0;
		while ((!foundNewLocation) && (count < 10)) {
			int moveX = MiscRandom.getRandomInt(0, moveSizeRandom) - moveSize;
			int moveY = MiscRandom.getRandomInt(0, moveSizeRandom) - moveSize;
			int portX = moveX > 0 ? IGrid.P_EAST : IGrid.P_WEST;
			int portY = moveY > 0 ? IGrid.P_NORTH : IGrid.P_SOUTH;
			int moveIncX = moveX > 0 ? -1 : 1;
			int moveIncY = moveY > 0 ? -1 : 1;
			count++;
			// locate the intended destination
			IXholon pneNode = getPne();
			AbstractGrid destination = (AbstractGrid)pneNode.getParentNode();
			while ((destination != null) && (moveX != 0)) {
				destination = (AbstractGrid)destination.port[portX];
				moveX += moveIncX;
			}
			while ((destination != null) && (moveY != 0)) {
				destination = (AbstractGrid)destination.port[portY];
				moveY += moveIncY;
			}
			// move there
			if (destination != null) {
				pneNode.removeChild(); // detach self from parent and sibling links
				pneNode.appendChild(destination); // insert as the last child of the destination grid cell
				foundNewLocation = true;
			}
		}
	}
	
	public int getMoveSize() {
		return moveSize;
	}

	public void setMoveSize(int moveSize) {
		this.moveSize = moveSize;
		this.moveSizeRandom = moveSize * 2 + 1;
	}
	
}

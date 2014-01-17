package org.primordion.user.app.Bestiary;

import org.primordion.xholon.base.AbstractGrid;
import org.primordion.xholon.base.IGrid;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.util.MiscRandom;

/**
 * A beast may move each time step.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on May 27, 2010)
 */
public abstract class MovingBeastBehavior extends BeastBehavior {
	
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
	 * @see org.primordion.user.app.Bestiary.XhBestiary#act()
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
			AbstractGrid destination = (AbstractGrid)getBeast().getParentNode();
			while ((destination != null) && (moveX != 0)) {
				destination = (AbstractGrid)destination.port[portX];
				moveX += moveIncX;
			}
			while ((destination != null) && (moveY != 0)) {
				destination = (AbstractGrid)destination.port[portY];
				moveY += moveIncY;
			}
			IXholon artifact = destination.getFirstChild();
			if (artifact != null) {
				switch (artifact.getXhcId()) {
				case DoorCE:
					// can the beast move through the door?
					if (!canMoveThruDoor(artifact)) {
						destination = null;
					}
					break;
				case WallSectionCE:
					destination = null;
				default:
					break;
				}
			}
			// move there
			if (destination != null) {
				getBeast().removeChild(); // detach self from parent and sibling links
				getBeast().appendChild(destination); // insert as the last child of the destination grid cell
				foundNewLocation = true;
			}
		}
	}
	
	/**
	 * Can this beast move through the door?
	 * @param door A door.
	 * @return true or false
	 */
	protected abstract boolean canMoveThruDoor(IXholon door);

	public int getMoveSize() {
		return moveSize;
	}

	public void setMoveSize(int moveSize) {
		this.moveSize = moveSize;
		this.moveSizeRandom = moveSize * 2 + 1;
	}
	
}

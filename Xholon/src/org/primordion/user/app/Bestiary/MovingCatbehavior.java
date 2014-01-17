package org.primordion.user.app.Bestiary;

import org.primordion.xholon.base.IXholon;

/**
 * A cat may move each time step.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on May 27, 2010)
 */
public class MovingCatbehavior extends MovingBeastBehavior {
	
	/**
	 * A cat can move through doors that are open.
	 * @see org.primordion.user.app.Bestiary.MovingBeastBehavior#canMoveThruDoor(org.primordion.xholon.base.IXholon)
	 */
	protected boolean canMoveThruDoor(IXholon door) {
		if (door.getVal_int() == 0) { // if door is closed
			return false;
		}
		return true;
	}
	
}

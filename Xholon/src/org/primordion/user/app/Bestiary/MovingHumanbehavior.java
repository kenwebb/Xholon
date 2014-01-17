package org.primordion.user.app.Bestiary;

import org.primordion.xholon.base.IXholon;

/**
 * A human may move each time step.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on May 27, 2010)
 */
public class MovingHumanbehavior extends MovingBeastBehavior {
	
	/**
	 * A human can move through any door.
	 * @see org.primordion.user.app.Bestiary.MovingBeastBehavior#canMoveThruDoor(org.primordion.xholon.base.IXholon)
	 */
	protected boolean canMoveThruDoor(IXholon door) {
		return true;
	}
	
}

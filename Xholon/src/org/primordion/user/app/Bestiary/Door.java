package org.primordion.user.app.Bestiary;

import org.primordion.xholon.base.XholonWithPorts;

/**
 * Door
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on May 27, 2010)
 */
public class Door extends XholonWithPorts implements CeBestiary {
	
	private static final int DOOR_CLOSED = 0;
	private static final int DOOR_OPENED = 1;
	
	/**
	 * Whether the door is currently opened or closed.
	 */
	private int doorStatus = DOOR_OPENED;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal_int()
	 */
	public int getVal_int() {
		return doorStatus;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#doAction(java.lang.String)
	 */
	public void doAction(String action)
	{
		if ("button1".equals(action)) {
			if (doorStatus == DOOR_CLOSED) {doorStatus = DOOR_OPENED;}
			else {doorStatus = DOOR_CLOSED;}
		}
	}
	
}

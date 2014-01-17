package org.primordion.user.app.Bestiary;

/**
 * Human
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on May 27, 2010)
*/
public class Human extends Beast {
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toString()
	 */
	public String toString()
	{
		return super.toString() + " behaviors:" + this.getNumChildren(false);
	}
	
}

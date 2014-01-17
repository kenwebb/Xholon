package org.primordion.user.app.Bestiary;

/**
 * Cat
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on May 27, 2010)
*/
public class Cat extends Beast {
	
	private double catSize = 1.0;
	
	/**
	 * Get the current cat size.
	 * @return The current bug size.
	 */
	public double getCatSize() {
		return catSize;
	}
	
	/**
	 * Set the size of this cat.
	 * @param bugSize The new size.
	 */
	public void setCatSize(double catSize) {
		this.catSize = catSize;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toString()
	 */
	public String toString()
	{
		return super.toString()
			+ " size:" + getCatSize()
			+ " behaviors:" + this.getNumChildren(false);
	}
}

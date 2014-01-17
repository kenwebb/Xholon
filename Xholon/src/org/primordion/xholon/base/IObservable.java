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

package org.primordion.xholon.base;

/**
 * An Observable is a Xholon that is observed by another Xholon.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on March 12, 2007)
 * @see based on Java.util.Observable class; inspired by Gamma et al. (1995). Design Patterns. (Observer pattern)
 */
public interface IObservable extends IXholon {
	
	/**
	 * Adds an observer to the set of observers for this object.
	 * @param o An observer.
	 */
	public void addObserver(IXholon o);
	
	/**
	 * Returns the number of observers of this Observable object.
	 * @return The current number of observers.
	 */
	public int countObservers();
	
	/**
	 * Deletes an observer from the set of observers of this object.
	 * @param o An observer.
	 */
	public void deleteObserver(IXholon o);
	
	/**
	 * Clears the observer list so that this object no longer has any observers.
	 */
	public void deleteObservers();
	
	/**
	 * If this object has changed, then notify all of its observers.
	 */
	public void notifyObservers();
	
	/**
	 * If this object has changed, then notify all of its observers.
	 * @param arg Some data.
	 */
	public void notifyObservers(Object arg);
	
	/**
	 * Tests if this object has changed.
	 * @return true if and only if the setChanged method has been called more recently
	 * than the clearChanged method on this object; false otherwise.
	 */
	public boolean hasChanged();
	
	/**
	 * Get changed data.
	 * @return Some Object, or null.
	 */
	public Object getChangedData();
}

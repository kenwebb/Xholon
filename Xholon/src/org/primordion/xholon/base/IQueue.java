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

import java.util.Vector;

/**
 * This is a generic Queue data structure that can be used to store references
 * to any type of data. Items are added at one end of the Queue and taken
 * off at the other end, which is why a Queue is called a First In First Out
 * (FIFO) structure.
 *
 * WARNING: A Queue can fill up to maximum capacity, or it may become empty.
 * Check the return value when calling the enqueue and dequeue functions to
 * see if it is Q_FULL or null.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.6 (Created on April 16, 2007)
 */
public interface IQueue extends IXholon {

	//	 Special values to indicate state of the Queue
	public static final int Q_FULL = 0;

	public static final int Q_OK = 1;

	/**
	 * Add an object to the queue.
	 * @param p_item The object.
	 * @return Q_OK or Q_FULL
	 */
	public abstract int enqueue(Object p_item);

	/**
	 * Take an object off the queue.
	 * @return An instance of Object, or null.
	 */
	public abstract Object dequeue();

	/**
	 * Get number of items currently in the queue.
	 * @return Current queue size.
	 */
	public abstract int getSize();

	/**
	 * Get maximum number of items that can be in the queue.
	 * @return Maximum queue size.
	 */
	public abstract int getMaxSize();
	
	/**
	 * Set the maximum number of items that can be in the queue,
	 * and allocate space for that many items.
	 * If there are already some items in the queue,
	 * then the method will atttempt to move all of them into the new internal data structure.
	 * If the new maxSize is less than the old maxSize,
	 * then some of the existing items may be lost.
	 * @param maxSize Maximum queue size.
	 */
	public void setMaxSize(int maxSize);
	
	/**
	 * Randomize the order of the items in the queue.
	 */
	public abstract void shuffle();
	
	/**
	 * Sort the items in the queue.
	 * To use this capability, the Java class of the items in the queue must implement Comparable,
	 * with its "public int compareTo(Object o)" method.
	 */
	public abstract void sort();
	
	/**
	 * Empty the Queue, and discard its contents.
	 */
	public abstract void empty();

	/**
	 * Get the internal item array. This should be used for debugging purposes only.
	 * @return An array of Object items.
	 */
	public abstract Vector<?> getItems();

}

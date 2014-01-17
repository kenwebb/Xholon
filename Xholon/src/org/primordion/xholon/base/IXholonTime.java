/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2005, 2006, 2007, 2008 Ken Webb
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

//import java.util.Date; // GWT

/**
 * Handle time in a Xholon application.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on April 15, 2006)
 */
public interface IXholonTime {
	
	public static final int NUM_MILLISECONDS_PER_SECOND = 1000;
	
	// Timer types
	public static final int TIMERTYPE_RELATIVE = 1;
	public static final int TIMERTYPE_REPEAT   = 2;
	public static final int TIMERTYPE_ABSOLUTE = 3;
	
	/**
	 * Timeout after a specified number of milliseconds, relative to the current time.
	 * @param clientNode The xholon client node that will receive a message when the timeout occurs.
	 * @param delay The number of milliseconds to wait before timing out. The long will be cast to an int.
	 */
	public abstract XholonTimerTask timeoutRelative( IXholon clientNode, long delay );
	
	/**
	 * Timeout after a specified number of milliseconds, relative to the current time.
	 * @param clientNode The xholon client node that will receive a message when the timeout occurs.
	 * @param data User data to return as part of the message when the timeout occurs.
	 * @param delay The number of milliseconds to wait before timing out. The long will be cast to an int.
	 */
	public abstract XholonTimerTask timeoutRelative( IXholon clientNode, Object data, long delay );
	
	/**
	 * Timeout repeatedly every so many milliseconds.
	 * @param clientNode The xholon client node that will receive a message each time the timeout occurs.
	 * @param delay The number of milliseconds to delay before the first timeout occurs.
	 *  This parameter is ignored.
	 * @param period The number of milliseconds in each timeout period, after the first timeout.
	 *  The long will be cast to an int.
	 */
	public abstract XholonTimerTask timeoutRepeat( IXholon clientNode, long delay, long period );
	
	/**
	 * Timeout repeatedly every so many milliseconds.
	 * @param clientNode The xholon client node that will receive a message each time the timeout occurs.
	 * @param data User data to return as part of the message when the timeout occurs.
	 * @param delay The number of milliseconds to delay before the first timeout occurs.
	 *  This parameter is ignored.
	 * @param period The number of milliseconds in each timeout period, after the first timeout.
	 *  The long will be cast to an int.
	 */
	public abstract XholonTimerTask timeoutRepeat( IXholon clientNode, Object data, long delay, long period );
	
	/**
	 * Timeout at a specified absolute time in the future.
	 * @param time The absolute time and date at which the timeout will occur.
	 * @param clientNode The xholon client node that will receive a message when the timeout occurs.
	 */
	//public abstract XholonTimerTask timeoutAbsolute( IXholon clientNode, Date time ); // GWT
	
	/**
	 * Cancel a relative, repeat or absolute timeout.
	 */
	public abstract void cancel(XholonTimerTask xhTimerTask);
	
	/**
	 * Cancel all timeouts.
	 * This should probably only be called when an application has finished running,
	 * and especially before another application is subsequently opened.
	 */
	public void cancelAll();
}

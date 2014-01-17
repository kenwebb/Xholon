/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2013 Ken Webb
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

package org.primordion.xholon.io;

/**
 * A frame in which to display 2D grids.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on July 13, 2013)
 */
public interface IGridFrame {
	
	/**
	 * Sets some text that will be displayed in the frame.
	 * @param info Some informative text.
	 */
	public abstract void setInfoLabel(String info);
	
	/**
	 * Reset the frame to its default values.
	 * This should be done when a new model is run after a previous one.
	 */
	public abstract void reset();
	
	/**
	 * Wrapup at the end of an Application run.
	 */
	public abstract void wrapup();
}

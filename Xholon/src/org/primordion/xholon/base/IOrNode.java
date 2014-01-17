/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2008 Ken Webb
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
 * An OrNode can have multiple children, but at a given point in time,
 * only one of these children can be active.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.7.1 (Created on April 27, 2008)
 */
public interface IOrNode extends IXholon {

	/**
	 * Set the child that is currently active.
	 * @param onlyChild The only currently active child.
	 */
	public void setOnlyChild(IXholon onlyChild);
	
	/**
	 * Get the child that is currently active.
	 * @return The only currently active child.
	 */
	public IXholon getOnlyChild();
}

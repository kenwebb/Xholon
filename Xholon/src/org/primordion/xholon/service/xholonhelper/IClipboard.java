/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2009 Ken Webb
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

package org.primordion.xholon.service.xholonhelper;

/**
 * Interface for interacting with the Java clipboard.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on July 15, 2009)
 */
public interface IClipboard {

	/**
	 * Read a String from the clipboard, such as an XML or XQuery String.
	 * @return A String, or null.
	 */
	public abstract String readStringFromClipboard();

	/**
	 * Write a String to the clipboard.
	 * Is there really a need for both a write and a copy method?
	 * @param str A String.
	 */
	public abstract void writeStringToClipboard(String str);

	/**
	 * Copy a String to the cipboard.
	 * Is there really a need for both a write and a copy method?
	 * @param str A String.
	 */
	public abstract void copyStringToClipboard(String str);

}
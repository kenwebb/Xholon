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

import org.primordion.xholon.base.Xholon;

/**
 * Factory that returns an object (IClipboard) that can interact with the Java clipboard.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on July 15, 2009)
 */
public class ClipboardFactory {
	
	private static final String implName = "org.primordion.xholon.service.xholonhelper.XholonClipboard";
	
	/**
	 * The singleton instance.
	 */
	private static IClipboard xholonClipboard = null;
	
	private ClipboardFactory() {}
	
	/**
	 * Get the singleton instance of IClipboard.
	 * @return The instance, or null.
	 */
	public static IClipboard instance()
	{
		if (xholonClipboard == null) {
		  xholonClipboard = new org.primordion.xholon.service.xholonhelper.XholonClipboard();
		  /* GWT
			try {
				xholonClipboard = (IClipboard)Class.forName(implName).newInstance();
				
			} catch (InstantiationException e) {
				Xholon.getLogger().error("Unable to instantiate optional XholonClipboard class {}", e);
			} catch (IllegalAccessException e) {
				Xholon.getLogger().error("Unable to load optional XholonClipboard class {}", e);
			} catch (ClassNotFoundException e) {
				Xholon.getLogger().error("Unable to load optional XholonClipboard class " + implName);
			} catch (NoClassDefFoundError e) {
				Xholon.getLogger().error("Unable to load optional XholonClipboard class {}", e);
			}*/
		}
		return xholonClipboard;
	}
	
}

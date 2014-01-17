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
 * Factory that returns an object (ICutCopyPaste).
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on September 19, 2009)
 */
public class CutCopyPasteFactory {
	
	private static final String implName = "org.primordion.xholon.service.xholonhelper.CutCopyPaste";
	
	/**
	 * The singleton instance.
	 */
	private static ICutCopyPaste cutCopyPasteInstance = null;
	
	private CutCopyPasteFactory() {}
	
	/**
	 * Get the singleton instance of ICutCopyPaste.
	 * @return The instance, or null.
	 */
	public static ICutCopyPaste instance()
	{
		if (cutCopyPasteInstance == null) {
		  cutCopyPasteInstance = new org.primordion.xholon.service.xholonhelper.CutCopyPaste();
		  /* GWT
			try {
				cutCopyPasteInstance = (ICutCopyPaste)Class.forName(implName).newInstance();
				
			} catch (InstantiationException e) {
				Xholon.getLogger().error("Unable to instantiate optional CutCopyPaste class {}", e);
			} catch (IllegalAccessException e) {
				Xholon.getLogger().error("Unable to load optional CutCopyPaste class {}", e);
			} catch (ClassNotFoundException e) {
				Xholon.getLogger().error("Unable to load optional CutCopyPaste class " + implName);
			} catch (NoClassDefFoundError e) {
				Xholon.getLogger().error("Unable to load optional CutCopyPaste class {}", e);
			}
			*/
		}
		return cutCopyPasteInstance;
	}
	
}

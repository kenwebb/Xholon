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

package org.primordion.xholon.base;

/**
 * XPathFactory encapsulates a singleton instance of a class that implements IXPath.
 * This class has been replaced by XPathService.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on July 19, 2009)
 * @deprecated replaced by XPathService
 */
public class XPathFactory {
	
	private static String implName = "org.primordion.xholon.base.XPath";
	
	/**
	 * The singleton instance.
	 */
	private static IXPath singleton = null;
	
	private XPathFactory() {}
	
	/**
	 * Get the singleton instance of a class that implements IXPath.
	 * @return The singleton instance.
	 */
	public static IXPath getInstance() {
		if (singleton == null) {
  	  /* GWT ant build
			try {
				singleton = (IXPath)Class.forName(implName).newInstance();
				
			} catch (InstantiationException e) {
				Xholon.getLogger().error("Unable to instantiate " + implName + " class {}", e);
			} catch (IllegalAccessException e) {
				Xholon.getLogger().error("Unable to load " + implName + " IXPath class {}", e);
			} catch (ClassNotFoundException e) {
				Xholon.getLogger().error("Unable to load " + implName + " IXPath class " + implName);
			} catch (NoClassDefFoundError e) {
				Xholon.getLogger().error("Unable to load " + implName + " IXPath class {}", e);
			}
			*/
  		singleton = new XPath();
		}
		return singleton;
	}

	public static String getImplName() {
		return implName;
	}

	public static void setImplName(String implName) {
		XPathFactory.implName = implName;
	}
}

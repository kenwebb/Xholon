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
 * ReflectionFactory encapsulates a singleton instance of a class that implements IReflection.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on December 7, 2006)
 */
public class ReflectionFactory {
	
	//private static String implName = "org.primordion.xholon.base.ReflectionJavaStandard";
	private static String implName = "org.primordion.xholon.base.ReflectionJavaMicro";
	//private static String implName = "org.primordion.xholon.base.ReflectionReflectAsm";
	
	/**
	 * The singleton instance.
	 */
	private static IReflection singleton = null;
	
	private ReflectionFactory() {}
	
	/**
	 * Get the singleton instance of a class that implements IReflection.
	 * @return The singleton instance.
	 */
	public static IReflection instance() {
		if (singleton == null) {
		  singleton = new org.primordion.xholon.base.ReflectionJavaMicro();
			/*try {
				singleton = (IReflection)Class.forName(implName).newInstance();
				
			} catch (InstantiationException e) {
				Xholon.getLogger().error("Unable to instantiate " + implName + " class {}", e);
			} catch (IllegalAccessException e) {
				Xholon.getLogger().error("Unable to load " + implName + " IReflection class {}", e);
			} catch (ClassNotFoundException e) {
				Xholon.getLogger().error("Unable to load " + implName + " IReflection class " + implName);
			} catch (NoClassDefFoundError e) {
				Xholon.getLogger().error("Unable to load " + implName + " IReflection class {}", e);
			}*/
		}
		return singleton;
	}

	public static String getImplName() {
		return implName;
	}

	public static void setImplName(String implName) {
		ReflectionFactory.implName = implName;
	}
}

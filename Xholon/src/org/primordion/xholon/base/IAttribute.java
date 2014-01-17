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
 * Attribute.
 * A Xholon Attribute is based loosely on the UML concept of Attribute.
 * A UML Attribute has:
 *   visibility (public, protected, private, or package),
 *   a name,
 *   a type,
 *   a multiplicity,
 *   a default initial value.
 * A Xholon Attribute:
 * can be part of a composite structure tree just like any other xholon,
 *   has a roleName (UML name),
 *   is implemented by a concrete Java class (UML type, ex: Attribute_double),
 *   encapsulates a single value called "val" with public visibility,
 *   has getVal and setVal methods,
 *   may have incVal and decVal methods,
 *   may be able to processed received messages,
 *   is typically a Xholon PurePassiveObject,
 *   acts as a server that any number of other xholons can access,
 *   does not have any ports of its own but may be accessed through a port on some other xholon,
 *   has no act() method,
 *   has no default value other than the normal Java default.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on March 5, 2007)
 */
public interface IAttribute extends IXholon {
	
	// Actions that can be taken during the postConfigure() method.
	
	/** Do nothing. */
	public static final int ATTR_NULL = 0;
	
	/** Take the default actions. */
	public static final int ATTR_DEFAULT = 1;
	
	/** Set the value of the attribute in the parent node. */
	public static final int ATTR_SETPARENT = 2;
	
	/** Remove this attribute node from the Xholon tree. */
	public static final int ATTR_REMOVE = 3;
	
	/** Set the Xholon ID of this attribute node. */
	public static final int ATTR_SETID = 4;
	
	/** Set the value in the parent, and remove from the Xholon tree. */
	public static final int ATTR_SETPARENT_REMOVE = 5;
	
	/** Set the value in the parent, and set the Xholon ID. */
	public static final int ATTR_SETPARENT_SETID = 6;
	
	/**
	 * Get the value of an Attribute as a String.
	 * @return An instance of String.
	 */
	public abstract String getVal_String();
	
	/**
	 * Set the value of an Attribute, by passing in a representation of it as a String.
	 * @param A String representation of the desired value.
	 */
	public abstract void setVal(String val);
	
	/**
	 * Get the value of an Attribute as an Object (Integer, Double, Boolean, String, etc.).
	 * @return An instance of some subclass of Object.
	 */
	public abstract Object getVal_Object();
	
	/**
	 * Set the value of an Attribute, by passing in a representation of it as an Object.
	 * @param An Object representation of the desired value.
	 */
	public abstract void setVal(Object val);
}

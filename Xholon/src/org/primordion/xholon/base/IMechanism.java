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

import org.primordion.xholon.app.IApplication;

/**
 * A mechanism is a collection of related IXholonClasses.
 * It's the Xholon equivalent of an XML namespace.
 * A mechanism can be a "pure" mechanism, or it can be a viewer mechanism.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on April 10, 2009)
 */
public interface IMechanism extends IXholon {
	
	/**
	 * A RangeStart or RangeEnd that has no value.
	 */
	public static final int RANGE_NULL = -1;
	
	/**
	 * The lowest value used as an ID in a mechanism.
	 * IDs lower than this are normal application-specific xholon classes.
	 * This is the value of StateMachineEntity in config/_common/_mechanism/StateMachineEntity.xml .
	 */
	public static final int MECHANISM_ID_START = 1000000;
	
	/**
	 * The default namespace.
	 */
	public static final String DEFAULT_NAMESPACE = "http://www.primordion.com/namespace/Xholon";
	
	public abstract String getNamespaceUri();

	public abstract void setNamespaceUri(String namespaceUri);

	public abstract String getDefaultPrefix();

	public abstract void setDefaultPrefix(String defaultPrefix);
	
	public abstract int getRangeStart();

	public abstract void setRangeStart(int rangeStart);

	public abstract int getRangeEnd();

	public abstract void setRangeEnd(int rangeEnd);
	
	/**
	 * Get the optional decoration object.
	 * @returns An instance of IDecoration, or null.
	 */
	public abstract IDecoration getDecoration();
	
	/**
	 * Set the optional decoration object.
	 * @param An instance of IDecoration.
	 */
	public abstract void setDecoration(IDecoration decoration);
	
	/**
	 * Get the IXholonClass nodes that belong to this IMechanism.
	 * @return An array, or null.
	 */
	public abstract IXholonClass[] getMembers();
	
	/**
	 * Create a new &lt;UserMechanism/&gt; node.
	 * It will be created as a child of &lt;UserMechanisms/&gt; .
	 * This method should only be executed by the root Mechanism, XhMechanisms .
	 * @param roleName The name of the new Mechanism (required).
	 * @param namespaceUri Namespace URI (required).
	 * @param defaultPrefix Default namespace prefix, or null.
	 * @param rangeStart Start of the range, or IMechanism.RANGE_NULL (-1) if unknown.
	 * @param rangeEnd End of the range, or IMechanism.RANGE_NULL (-1) if unknown.
	 * @param app The instance of IApplication that the new Mechanism will be located in.
	 * @return The newly created instance of IMechanism.
	 */
	public abstract IMechanism createNewUserMechanism(
			String roleName, String namespaceUri, String defaultPrefix,
			int rangeStart, int rangeEnd,
			IApplication app);
	
	/**
	 * Find a Mechanism node that matches the specified NamespaceUri.
	 * This method should only be executed by the root Mechanism, XhMechanisms .
	 * @param namespaceUri A NamspaceUri ex: http://www.primordion.com/namespace/Action
	 * @return An IMechanism node, or null.
	 */
	public abstract IMechanism findMechanism(String namespaceUri);
	
}

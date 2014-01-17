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

package org.primordion.xholon.io.xml;

import org.primordion.xholon.base.ContainmentData;

/**
 * Constants for use with Xml2XholonClass.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on December 17, 2009)
 */
public interface IXml2XholonClass {
	
	/**
	 * The character that separates navinfo elements.
	 * ex: port[ONE]="..."^port[TWO]="..."^
	 */
	public static final char NAVINFO_SEPARATOR = ContainmentData.CD_SEPARATOR; //'^';
	
	/**
	 * The lowest value used as an ID in a mechanism.
	 * IDs lower than this are normal application-specific xholon classes.
	 * This is the value of StateMachineEntity in config/_common/_mechanism/StateMachineEntity.xml .
	 */
	//public static final int MECHANISM_ID_START = 1000000;
	
	/**
	 * The standard namespaceUri for the default mechanism/namespace.
	 * TODO retrieve this from app.getDefaultMechanism().getNamespaceUri();
	 */
	public static final String MECHANISM_DEFAULT_NAMESPACEURI = "http://www.primordion.com/namespace/Xholon";
	
}

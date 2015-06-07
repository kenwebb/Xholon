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

import org.primordion.xholon.base.IXholon;

/**
 * Find the first child or next sibling with some specified characteristics.
 * Example of use:
 * <pre>
IXholon role = ((IFindChildSibWith)getService(
    IXholonService.XHSRV_XHOLON_HELPER))
    .findFirstChildWithXhClass(this, CeRole.RoleCE);
 * </pre>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on September 19, 2009)
 */
public interface IFindChildSibWith extends IXholon {
	
	/**
	 * Find a context node's first child that matches the specified XholonClass id.
	 * @param contextNode 
	 * @param childXhClass A XholonClass id.
	 * @return A matching child xholon, or null.
	 */
	public abstract IXholon findFirstChildWithXhClass(IXholon contextNode, int childXhClassId);
	
	/**
	 * Find a context node's first child that matches the specified XholonClass name.
	 * @param contextNode 
	 * @param childXhClass A XholonClass name.
	 * @return A matching child xholon, or null.
	 */
	public abstract IXholon findFirstChildWithXhClass(IXholon contextNode, String childXhClassName);

	/**
	 * Find a context node's first child with an ancestor (possibly itself) that matches the specified XholonClass id.
	 * @param contextNode 
	 * @param childXhClass A XholonClass id.
	 * @return A matching child xholon, or null.
	 */
	public abstract IXholon findFirstChildWithAncestorXhClass(IXholon contextNode, int childXhClassId);

	/**
	 * Find a context node's first child with an ancestor (possibly itself) that matches the specified XholonClass name.
	 * @param contextNode 
	 * @param childXhClass A XholonClass id.
	 * @return A matching child xholon, or null.
	 */
	public abstract IXholon findFirstChildWithAncestorXhClass(IXholon contextNode, String childXhClassName);

	/**
	 * Find a context node's first child with the specified role name.
	 * @param contextNode 
	 * @param roleName A role name.
	 * @return A matching child xholon, or null.
	 */
	public abstract IXholon findFirstChildWithRoleName(IXholon contextNode, String roleName);

	/**
	 * Find a context node's next sibling that matches the specified XholonClass id.
	 * @param contextNode 
	 * @param childXhClass A XholonClass id.
	 * @return A matching sibling xholon, or null.
	 */
	public abstract IXholon findNextSiblingWithXhClass(IXholon contextNode, int siblingXhClassId);

	/**
	 * Find a context node's next sibling with an ancestor (possibly itself) that matches the specified XholonClass id.
	 * @param contextNode 
	 * @param childXhClass A XholonClass id.
	 * @return A matching sibling xholon, or null.
	 */
	public abstract IXholon findNextSiblingWithAncestorXhClass(IXholon contextNode, int siblingXhClassId);
	
	/**
	 * Find a context node's first descendant with the specified role name.
	 * @param contextNode 
	 * @param roleName A role name.
	 * @return A matching descendant xholon, or null.
	 */
	public abstract IXholon findFirstDescWithRoleName(IXholon contextNode, String roleName);
	
}

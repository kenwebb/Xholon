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

package org.primordion.xholon.service.processor;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;

/**
 * A TreeProcessor that fuddifies a Xholon tree or subtree.
 * <p>source for idea: Walls, Spring in Action</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on August 31, 2009)
 */
public class Fuddifier extends AbstractTreeProcessor {
	
	public Fuddifier() {}
	
	/*
	 * @see org.primordion.xholon.service.AbstractTreeProcessor#processRequest(org.primordion.xholon.base.IMessage)
	 */
	public Object processRequest(IMessage msg)
	{
		IXholon rootNode = (IXholon)msg.getData();
		//System.out.println(this.getClass().getName() + ":" + rootNode);
		return visitAll(rootNode);
	}
	
	/**
	 * Visit all nodes in the tree.
	 * @param rootNode The root node of a tree or subtree.
	 * @return The revised tree.
	 */
	private IXholon visitAll(IXholon rootNode)
	{
		rootNode.visit(this);
		return rootNode;
	}
	
	/**
	 * @see org.primordion.xholon.base.IXholon#visit(org.primordion.xholon.base.IXholon)
	 * @param visitee The node in the tree that is currently being visited.
	 */
	public boolean visit(IXholon visitee)
	{
		String rName = visitee.getRoleName();
		if (rName != null) {
			visitee.setRoleName(fuddify(rName));
		}
		return true;
	}
	
	/**
	 * Fuddify a String.
	 * source: Walls, Spring in Action
	 * @param orig The original String.
	 * @return The fuddified String.
	 */
	private String fuddify(String orig)
	{
		if (orig == null) {return orig;}
		return orig.replaceAll("(r|l)", "w").replaceAll("(R|L)", "W");
	}
	
}

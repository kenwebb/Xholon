/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2010 Ken Webb
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

package org.primordion.xholon.script;

import org.primordion.xholon.base.IXholon;

/**
 * This Xholon script renumbers all nodes in the subtree.
 * It reuses the current ID of it's parent node as the starting point.
 * This may not always be the best way to renumber.
 * <p>This script is located in the common default package for Java scripts.
 * It can therefore be pasted into a running Xholon app simply as:</p>
 * <pre>&lt;Renumber/></pre>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on February 16, 2010)
 */
public class Renumber extends XholonScript {
	
	private int nextId = 0;
	
	public void postConfigure()
	{
		nextId = getParentNode().getId();
		getParentNode().visit(this);
        removeChild();
	}
	
    public boolean visit(IXholon visitee)
    {
    	visitee.setId(nextId++);
    	return true;
    }
    
}

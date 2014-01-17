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

package org.primordion.xholon.script;

import org.primordion.xholon.base.IXholon;

/**
 * This is a sample Xholon script, written in normal compiled Java.
 * It's located in the common default package for Java scripts.
 * It can therefore be pasted into a running Xholon app simply as:
 * <pre>&lt;Tabulator/></pre>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 8, 2009)
 */
public class Tabulator extends XholonScript {
	
	int count = 0;
	
	public void postConfigure()
	{
		getParentNode().visit(this);
        println("There are " + count + " nodes in the subtree rooted by " + getParentNode() + ".");
        removeChild();
	}
	
    public boolean visit(IXholon visitee)
    {
    	//println("visitor " + this + " visitee " + visitee);
    	count++;
    	return true;
    }
    
}

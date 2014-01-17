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

package org.primordion.user.app.StupidModel.sm5tg;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IGrid;
import org.primordion.xholon.base.XholonWithPorts;

/**
	StupidModel5tg application - Xholon Java
	<p>Xholon 0.5 http://www.primordion.com/Xholon</p>
*/
public class XhStupidModel5tg extends XholonWithPorts implements CeStupidModel5tg {
	
	// Constructor
	public XhStupidModel5tg() {super();}
	
	/*
	 * @see org.primordion.xholon.base.XholonWithPorts#postConfigure()
	 */
	public void postConfigure()
	{
		switch(xhc.getId()) {
		case StupidModelCE:
		{
			// Move all bugs to random positions within the one or more grids.
			IXholon pop = getXPath().evaluate("Population", this);
			while (pop != null) {
				IXholon grid = getXPath().evaluate("Grid", pop);
				IXholon xhParent = getXPath().evaluate("Bugs", pop);
				((IGrid)grid).moveXholonsToGrid(xhParent, false);
				pop = pop.getNextSibling();
			}
		}
			break;
		default:
			break;
		}
		super.postConfigure();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toString()
	 */
	public String toString() {
		String outStr = getName();
		return outStr;
	}
}

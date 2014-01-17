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

package org.primordion.user.app.StupidModel.sm1;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IGrid;
import org.primordion.xholon.base.XholonWithPorts;

/**
	StupidModel1 application - Xholon Java
	<p>Xholon 0.5 http://www.primordion.com/Xholon</p>
*/
public class XhStupidModel1 extends XholonWithPorts implements CeStupidModel1 {

	// Constructor
	public XhStupidModel1() {super();}
	
	/*
	 * @see org.primordion.xholon.base.XholonWithPorts#postConfigure()
	 */
	public void postConfigure()
	{
		switch(xhc.getId()) {
		case StupidModelCE:
		{
			// Move all bugs to random positions within the grid.
			IXholon grid = getXPath().evaluate("descendant::Grid", this);
			IXholon xhParent = getXPath().evaluate("descendant::Bugs", this);
			((IGrid)grid).moveXholonsToGrid(xhParent, false);
		}
			break;
		default:
			break;
		}
		super.postConfigure();
	}
}

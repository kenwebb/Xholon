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

package org.primordion.user.app.Hex;

import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.XholonHelperService;
import org.primordion.xholon.util.MiscRandom;

/**
 * <p>This class is designed to implement the behavior of the "Monster" XholonClass, in the Hex model.
 * To make use of this class:</p>
 * <p>(1) Load and Start the GridSamples --> Hex model.</p>
 * <p>(2) Select and copy the following text to the clipboard:
 * <pre>&lt;Monster xhType='XhtypePureActiveObject' implName='org.primordion.xholon.samples.Monster'/&gt;</pre>
 * </p>
 * <p>(3) In the XholonGui, select InheritanceHierarchy --> XholonClass --> Creature.
 * Right-click the Creature node and select Edit --> Paste After .</p>
 * <p>(4) Select and copy the following text to the clipboard: <pre>&lt;Monster/&gt;</pre></p>
 * <p>(5) In the XholonGui, select CompositeStructureHierarchy --> hex_0 --> row:hexRow_1 --> gridCell:hexGridCell_2 .
 * Right-click the HexGridCell node and select Edit --> Paste Last Child .</p>
 * <p>Something new should start to happen, whatever is coded in the act() method below.</p>
 * <p>Alternatively, you can create an instance of Monster by only doing steps 4 and 5 above.
 * This will create an ad-hoc xholon that:
 * - contains an internal instance of the Monster XholonClass, and
 * - is implemented by a Java class with the same name in the application's root package.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on September 11, 2009)
 */
public class Monster extends XhHex {
	
	/*
	 * @see org.primordion.xholon.samples.XhHex#act()
	 */
	public void act()
	{
		//System.out.println(getName());
		if (hasMoved == false) {
			int r = MiscRandom.getRandomInt(0, 6); // get random integer between 0 and 5 inclusive
			moveAdjacent(r);
		}
		if (hasNextSibling()) {
			// only do the actions if the nextSibling is not already a monster
			// it's a monster if it has the same XholonClass ID
			if (getNextSibling().getXhcId() != this.getXhcId()) {
				// gobble up some hapless creature that happens to be in the same grid cell
				getNextSibling().removeChild();
				// create a zombie monster in its place
				XholonHelperService xholonHelperService = (XholonHelperService)getService(IXholonService.XHSRV_XHOLON_HELPER);
				xholonHelperService.pasteAfter(this, "<Monster roleName='Zombie'/>");
			}
		}
		super.act();
	}
	
}

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

package org.primordion.user.app.Hex;

//import java.awt.Color;
import com.google.gwt.canvas.dom.client.CssColor;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.io.GridPanel;
import org.primordion.xholon.io.IGridPanel;

/**
 * A graphic panel in which to display the Hex 2D grid.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on February 6, 2007)
 */
public class GridPanelHex extends GridPanel implements IGridPanel, CeHex {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 */
	public GridPanelHex() {}
	
	/**
	 * Constructor.
	 * @param gridOwner Xholon that owns the grid.
	 */
	public GridPanelHex(IXholon gridOwner) { super(gridOwner);}
	
	/*
	 * @see org.primordion.xholon.io.IGridPanel#getColor(org.primordion.xholon.base.IXholon)
	 */
	public CssColor getColor(IXholon xhNode)
	{
		// specific to Hex
		switch(xhNode.getXhcId()) {
		case HexGridCellCE:
			if (xhNode.hasChildNodes()) { // Creature is here
				if (xhNode.getFirstChild().getXhcId() == CreatureCE) {
					return CssColor.make("yellow"); //Color.YELLOW;
				}
				else if ("Monster".equals(xhNode.getFirstChild().getXhcName())) {
					return CssColor.make("green"); //Color.GREEN;
				}
				else {
					return CssColor.make("black"); //Color.BLACK;
				}
			}
			if (((XhHex)xhNode).alive) {
				return CssColor.make("blue"); //Color.BLUE;
			}
			else {
				return CssColor.make("red"); //Color.RED;
			}
		default:
			return CssColor.make("gray"); //Color.GRAY;
		}
	}
}

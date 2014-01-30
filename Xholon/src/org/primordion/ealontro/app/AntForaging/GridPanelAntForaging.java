/* Ealontro - systems that evolve and adapt to their environment
 * Copyright (C) 2006, 2007, 2008 Ken Webb
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

package org.primordion.ealontro.app.AntForaging;

//import java.awt.Color;
import com.google.gwt.canvas.dom.client.CssColor;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.io.GridPanel;
import org.primordion.xholon.io.IGridPanel;

/**
 * A graphic panel in which to display the Ant System 2D grid.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.2 (Created on November 27, 2005)
 */
public class GridPanelAntForaging extends GridPanel implements IGridPanel {
	
	private static final long serialVersionUID = 1L;
	
	protected static final CssColor COLOR_DEFAULT = CssColor.make("black"); //Color.BLACK; // background color
	protected static final CssColor COLOR_EMPTY   = COLOR_DEFAULT;
	protected static final CssColor COLOR_NEST    = CssColor.make("red"); //Color.RED;
	protected static final CssColor COLOR_FOOD    = CssColor.make("blue"); //Color.BLUE;
	protected static final CssColor COLOR_ANT     = CssColor.make("white"); //Color.WHITE;

	/**
	 * Constructor
	 */
	public GridPanelAntForaging() {}
	
	/**
	 * Constructor.
	 * @param gridOwner Xholon that owns the grid.
	 */
	public GridPanelAntForaging(IXholon gridOwner) { super(gridOwner);}
	
	/*
	 * @see org.primordion.xholon.io.IGridPanel#getColor(org.primordion.xholon.base.IXholon)
	 */
	public CssColor getColor(IXholon xhNode)
	{
		// specific to Ant System
		switch(xhNode.getXhcId()) {
		case CeAntForaging.GridCellCE:
			// display whether or not there's an Ant or Ants here
			if (xhNode.getFirstChild() != null) {
				int numChildren = xhNode.getChildNodes(false).size();
				if (xhNode.getFirstChild().getXhcId() == CeAntForaging.NestCE) {
					if (numChildren == 1) { // Nest with Food is the only child; no Ants here
						return COLOR_NEST;
					}
					else {
						numChildren--; // don't count Nest as an Ant
					}
				}
				if (numChildren > 25) {
					numChildren = 25;
				}
				numChildren *= 10;
				numChildren = 255 - numChildren;
				//return new Color(numChildren, numChildren, numChildren);
				return CssColor.make("rgb(" + numChildren + "," + numChildren + "," + numChildren + ")");
			}
			else if (((XhAntForaging)xhNode).hasNest) {
				return COLOR_NEST;
			}
			else if (((XhAntForaging)xhNode).hasFood()) {
				return COLOR_FOOD;
			}
			else {
				int pherQuantity = (int)((XhAntForaging)xhNode).getPheromoneLevel();
				if ( pherQuantity >= XhAntForaging.PHEROMONE_MIN_DISPLAY) {
					pherQuantity *= 5;
					if (pherQuantity > 255) {
						pherQuantity = 255;
					}
					//return new Color(0, pherQuantity, 0); // a shade of green
					return CssColor.make("rgb(" + 0 + "," + pherQuantity + "," + 0 + ")");
				}
				else {
					return COLOR_EMPTY;
				}
			}
		default:
			return COLOR_DEFAULT;
		} // end switch
	}
}

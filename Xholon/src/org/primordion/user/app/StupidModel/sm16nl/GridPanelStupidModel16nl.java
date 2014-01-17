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

package org.primordion.user.app.StupidModel.sm16nl;

//import java.awt.Color;
import com.google.gwt.canvas.dom.client.CssColor;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.io.GridPanel;
import org.primordion.xholon.io.IGridPanel;

/**
 * A graphic panel in which to display the 2D grid.
 * <p>Xholon 0.5 http://www.primordion.com/Xholon</p>
 */
public class GridPanelStupidModel16nl extends GridPanel implements IGridPanel, CeStupidModel16nl {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 */
	public GridPanelStupidModel16nl() {super();}
	
	/**
	 * Constructor
	 * @param gridOwner Xholon that owns the grid. */
	public GridPanelStupidModel16nl(IXholon gridOwner) {super(gridOwner);}
	
	/*
	 * @see org.primordion.xholon.io.IGridPanel#getColor(org.primordion.xholon.base.IXholon)
	 */
	public CssColor getColor(IXholon xhNode)
	{
		switch(xhNode.getXhcId()) {
		case HabitatCellCE:
			if (xhNode.hasChildNodes() && !useShapes) {
				switch (xhNode.getFirstChild().getXhcId()) {
				case BugCE:
					return CssColor.make("red"); //Color.RED;
				default:
					return CssColor.make("gray"); //Color.GRAY; // shouldn't happen
				}
			}
			else {
				int foodAvailGreen;
				if (app.getTimeStep() == 0) {
					foodAvailGreen = (int)(((HabitatCellStupidModel16nl)xhNode).foodProductionRate * 16384.0);
				}
				else {
					foodAvailGreen = (int)(xhNode.getVal() * 1024.0);
				}
				if (foodAvailGreen > 255) {
					foodAvailGreen = 255;
				}
				return CssColor.make(0, foodAvailGreen, 0); //Color(0, foodAvailGreen, 0);
			}
		case BugCE:
			if (xhNode.getId() == 4) {
				return CssColor.make("green"); //Color.GREEN;
			}
			int bugSizeRed = (int)((BugStupidModel16nl)xhNode).getBugSize() * 25;
			if (bugSizeRed > 255) {bugSizeRed = 255;}
			return CssColor.make( 255, 255-bugSizeRed, 255-bugSizeRed );
		case PredatorCE:
			return CssColor.make("blue"); //Color.BLUE;
		default:
			return CssColor.make("gray"); //Color.GRAY; // shouldn't happen
		}
	}
	
	/*
	 * @see org.primordion.xholon.io.GridPanel#getShape(org.primordion.xholon.base.IXholon)
	 */
	public int getShape(IXholon xhNode)
	{
		switch(xhNode.getXhcId()) {
		case BugCE:
			return GPSHAPE_CIRCLE;
		case PredatorCE:
			return GPSHAPE_TRIANGLE;
		default:
			return GPSHAPE_NOSHAPE;
		}
	}
}

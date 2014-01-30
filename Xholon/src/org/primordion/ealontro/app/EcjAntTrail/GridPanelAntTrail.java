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

package org.primordion.ealontro.app.EcjAntTrail;

//import java.awt.Color;
import com.google.gwt.canvas.dom.client.CssColor;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.io.GridPanel;
import org.primordion.xholon.io.IGridPanel;

/**
 * A graphic panel in which to display the Ant Trail 2D grid.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on June 8, 2006)
 */
public class GridPanelAntTrail extends GridPanel implements IGridPanel, CeAntTrail {
	
	private static final long serialVersionUID = 1L;
	
	protected static final CssColor COLOR_DEFAULT = CssColor.make("black"); //Color.BLACK; // background color
	protected static final CssColor COLOR_EMPTY   = COLOR_DEFAULT;
	protected static final CssColor COLOR_TRAIL   = CssColor.make("red"); //Color.RED;
	protected static final CssColor COLOR_FOOD    = CssColor.make("blue"); //Color.BLUE;
	protected static final CssColor COLOR_ANT     = CssColor.make("white"); //Color.WHITE;
	protected static final CssColor COLOR_EMPTY_VISITED   = CssColor.make("#D3D3D3"); //Color.LIGHT_GRAY;
	protected static final CssColor COLOR_TRAIL_VISITED   = CssColor.make("pink"); //Color.PINK;
	protected static final CssColor COLOR_FOOD_VISITED    = CssColor.make("cyan"); //Color.CYAN;

	/**
	 * Constructor
	 */
	public GridPanelAntTrail() {}
	
	/**
	 * Constructor.
	 * @param gridOwner Xholon that owns the grid.
	 */
	public GridPanelAntTrail(IXholon gridOwner) { super(gridOwner);}
	
	/*
	 * @see org.primordion.xholon.io.IGridPanel#getColor(org.primordion.xholon.base.IXholon)
	 */
	public CssColor getColor(IXholon xhNode)
	{
		// specific to Ant Trail System
		switch(xhNode.getXhcId()) {
		case GridCellCE:
			switch( (int)xhNode.getVal() ) {
			case XhAntTrail.VAL_EMPTY: return COLOR_EMPTY;
			case XhAntTrail.VAL_TRAIL: return COLOR_TRAIL;
			case XhAntTrail.VAL_FOOD:  return COLOR_FOOD;
			case XhAntTrail.VAL_EMPTY_ANT:
			case XhAntTrail.VAL_TRAIL_ANT:
			case XhAntTrail.VAL_FOOD_ANT:
				return COLOR_ANT;
			case XhAntTrail.VAL_EMPTY_VISITED: return COLOR_EMPTY_VISITED;
			case XhAntTrail.VAL_TRAIL_VISITED: return COLOR_TRAIL_VISITED;
			case XhAntTrail.VAL_FOOD_VISITED:  return COLOR_FOOD_VISITED;
			default:
				return COLOR_DEFAULT;
			}
		default:
			return COLOR_DEFAULT;
		} // end switch
	}
}

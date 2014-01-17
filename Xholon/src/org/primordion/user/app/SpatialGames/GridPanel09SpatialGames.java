/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2005, 2006, 2007, 2008 Ken Webb
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

package org.primordion.user.app.SpatialGames;

//import java.awt.Color;
import com.google.gwt.canvas.dom.client.CssColor;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.io.GridPanel;
import org.primordion.xholon.io.IGridPanel;

/**
 * A graphic panel in which to display the Spatial Games grid.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.7 (Created on September 16, 2007)
 */
public class GridPanel09SpatialGames extends GridPanel implements IGridPanel, Ce09SpatialGames {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public GridPanel09SpatialGames() {}
	
	/**
	 * Constructor.
	 * @param gridOwner Xholon that owns the grid.
	 */
	public GridPanel09SpatialGames(IXholon gridOwner) { super(gridOwner);}
	
	/*
	 * @see org.primordion.xholon.io.IGridPanel#getColor(org.primordion.xholon.base.IXholon)
	 */
	public CssColor getColor(IXholon xhNode)
	{
		// specific to Spatial Games
		switch (xhNode.getXhcId()) {
		case CwasCCE:
			return CssColor.make("blue"); //Color.BLUE;
		case DwasDCE:
			return CssColor.make("red"); //Color.RED;
		case CwasDCE:
			return CssColor.make("green"); //Color.GREEN;
		case DwasCCE:
			return CssColor.make("yellow"); //Color.YELLOW;
		default:
			return CssColor.make("black"); //Color.BLACK;
		}
	}
}

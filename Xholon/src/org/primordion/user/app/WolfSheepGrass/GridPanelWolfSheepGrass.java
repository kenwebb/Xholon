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
package org.primordion.user.app.WolfSheepGrass;

//import java.awt.Color;
import com.google.gwt.canvas.dom.client.CssColor;

import org.primordion.xholon.base.IPatch;
import org.primordion.xholon.base.ITurtlePatchColor;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.io.GridPanel;
import org.primordion.xholon.io.IGridPanel;

/**
 * A graphic panel in which to display the 2D grid.
 * Wolf Sheep Grass.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on March 1, 2007)
 * @see The original NetLogo wolf sheep predation model is: Copyright 1998 Uri Wilensky. All rights reserved.
 * See http://ccl.northwestern.edu/netlogo/models/WolfSheepPredation for terms of use.
 */
public class GridPanelWolfSheepGrass extends GridPanel implements IGridPanel, CeWolfSheepGrass {
	private static final long serialVersionUID = 1L;
	//Color brown = new Color(157, 110, 72);
	//Color green = new Color( 89, 176, 60);
	CssColor brown = CssColor.make("rgb(157, 110, 72)"); //new Color(157, 110, 72);
	CssColor green = CssColor.make("rgb( 89, 176, 60)"); //new Color( 89, 176, 60);
	
/**
 * Constructor
 */
public GridPanelWolfSheepGrass() {super();}

/**
 * Constructor
 * @param gridOwner Xholon that owns the grid. */
public GridPanelWolfSheepGrass(IXholon gridOwner) {super(gridOwner);}

/*
 * @see org.primordion.xholon.io.IGridPanel#getColor(org.primordion.xholon.base.IXholon)
 */
public CssColor getColor(IXholon xhNode)
{
	switch(xhNode.getXhcId()) {
	case GrassCE:
		if (xhNode.hasChildNodes()) {
			switch (xhNode.getFirstChild().getXhcId()) {
			case SheepCE:
				return CssColor.make("white"); //Color.WHITE;
			case WolfCE:
				return CssColor.make("black"); //Color.BLACK;
			default:
				return CssColor.make("gray"); //Color.GRAY;
			}
		}
		else {
			switch (((IPatch)xhNode).getPcolor()) {
			case ITurtlePatchColor.TPCOLOR_BROWN: return brown;
			case ITurtlePatchColor.TPCOLOR_GREEN: return green;
			default: return CssColor.make("black"); //Color.BLACK;
			}
		}
	default:
		return CssColor.make("gray"); //Color.GRAY;
	}
}
}

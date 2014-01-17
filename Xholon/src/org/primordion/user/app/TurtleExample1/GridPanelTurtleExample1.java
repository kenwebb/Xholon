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

package org.primordion.user.app.TurtleExample1;

//import java.awt.Color;
import com.google.gwt.canvas.dom.client.CssColor;

import org.primordion.xholon.base.IPatch;
import org.primordion.xholon.base.ITurtlePatchColor;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.io.GridPanel;
import org.primordion.xholon.io.IGridPanel;

/**
	TurtleExample1 application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   21/02/2007</p>
	<p>File:   TurtleExample1.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Wed Feb 21 13:46:13 EST 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/

/**
 * A graphic panel in which to display the 2D grid.
 */
public class GridPanelTurtleExample1 extends GridPanel implements IGridPanel, CeTurtleExample1 {
private static final long serialVersionUID = 1L;

/**
 * Constructor
 */
public GridPanelTurtleExample1() {super();}

/**
 * Constructor
 * @param gridOwner Xholon that owns the grid. */
public GridPanelTurtleExample1(IXholon gridOwner) {super(gridOwner);}

/*
 * @see org.primordion.xholon.io.IGridPanel#getColor(org.primordion.xholon.base.IXholon)
 */
public CssColor getColor(IXholon xhNode)
{
	switch(xhNode.getXhcId()) {
	case PatchCE:
		if (xhNode.hasChildNodes()) {
			switch (xhNode.getFirstChild().getXhcId()) {
			default:
				return CssColor.make("green"); //Color.GREEN;
			}
		}
		else {
			switch (((IPatch)xhNode).getPcolor()) {
			case ITurtlePatchColor.TPCOLOR_GRAY: return CssColor.make("blue"); //Color.BLUE;
			case ITurtlePatchColor.TPCOLOR_RED: return CssColor.make("red"); //Color.RED;
			case ITurtlePatchColor.TPCOLOR_ORANGE: return CssColor.make("orange"); //Color.ORANGE;
			case ITurtlePatchColor.TPCOLOR_BROWN: return CssColor.make("magenta"); //Color.MAGENTA;
			case ITurtlePatchColor.TPCOLOR_YELLOW: return CssColor.make("yellow"); //Color.YELLOW;
			case ITurtlePatchColor.TPCOLOR_GREEN: return CssColor.make("pink"); //Color.PINK;
			case ITurtlePatchColor.TPCOLOR_LIME: return CssColor.make("green"); //Color.GREEN;
			case ITurtlePatchColor.TPCOLOR_TURQUOISE: return CssColor.make("cyan"); //Color.CYAN;
			case ITurtlePatchColor.TPCOLOR_CYAN: return CssColor.make("red"); //Color.RED;
			case ITurtlePatchColor.TPCOLOR_SKY: return CssColor.make("orange"); //Color.ORANGE;
			case ITurtlePatchColor.TPCOLOR_BLUE: return CssColor.make("blue"); //Color.BLUE;
			case ITurtlePatchColor.TPCOLOR_VIOLET: return CssColor.make("green"); //Color.GREEN;
			case ITurtlePatchColor.TPCOLOR_MAGENTA: return CssColor.make("magenta"); //Color.MAGENTA;
			case ITurtlePatchColor.TPCOLOR_PINK: return CssColor.make("gray"); //Color.GRAY;
			default: return CssColor.make("black"); //Color.BLACK;
			}
			
		}
	default:
		return CssColor.make("gray"); //Color.GRAY;
	}
}
}

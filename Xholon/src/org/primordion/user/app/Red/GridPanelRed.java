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

package org.primordion.user.app.Red;

//import java.awt.Color;
import com.google.gwt.canvas.dom.client.CssColor;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.io.GridPanel;
import org.primordion.xholon.io.IGridPanel;

/**
	Red application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   21/02/2007</p>
	<p>File:   .mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Wed Feb 21 13:46:13 EST 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/

/**
 * A graphic panel in which to display the 2D grid.
 */
public class GridPanelRed extends GridPanel implements IGridPanel, CeRed {
private static final long serialVersionUID = 1L;

/**
 * Constructor
 */
public GridPanelRed() {super();}

/**
 * Constructor
 * @param gridOwner Xholon that owns the grid. */
public GridPanelRed(IXholon gridOwner) {super(gridOwner);}

/*
 * @see org.primordion.xholon.io.IGridPanel#getColor(org.primordion.xholon.base.IXholon)
 */
public CssColor getColor(IXholon xhNode)
{
	switch(xhNode.getXhcId()) {
	case GridCellCE:
		if (xhNode.hasChildNodes()) {
			switch (xhNode.getFirstChild().getXhcId()) {
			default:
				return CssColor.make("blue"); //Color.BLUE;
			}
		}
		else {
			//return Color.RED;
			//return new Color(
			//	((XhRed)xhNode).rgb.r,
			//	((XhRed)xhNode).rgb.g,
			//	((XhRed)xhNode).rgb.b);
		  return CssColor.make("rgb(" + ((XhRed)xhNode).rgb.r + ","
		    + ((XhRed)xhNode).rgb.g + ","
		    + ((XhRed)xhNode).rgb.b + ")");
		}
	default:
		return CssColor.make("gray"); //Color.GRAY;
	}
}
}

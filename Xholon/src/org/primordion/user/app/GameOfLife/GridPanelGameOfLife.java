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

package org.primordion.user.app.GameOfLife;

// GWT
//import java.awt.Color;
//import java.awt.event.MouseEvent;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.event.dom.client.MouseDownEvent;

//import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.io.GridPanel;
import org.primordion.xholon.io.IGridPanel;

/**
 * A graphic panel in which to display the Game of Life 2D grid.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.2 (Created on November 27, 2005)
 */
public class GridPanelGameOfLife extends GridPanel implements IGridPanel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 */
	public GridPanelGameOfLife() {}
	
	/**
	 * Constructor.
	 * @param gridOwner Xholon that owns the grid.
	 */
	public GridPanelGameOfLife(IXholon gridOwner) { super(gridOwner);}
	
	/*
	 * @see org.primordion.xholon.io.IGridPanel#getColor(org.primordion.xholon.base.IXholon)
	 */
	public CssColor getColor(IXholon xhNode)
	{
		// specific to Game of Life
		if (((XhGameOfLife)xhNode).alive) {
			return CssColor.make("green"); //Color.GREEN;
		}
		else {
			return CssColor.make("white"); //Color.WHITE;
		}
	}
	
	/*
	 * @see org.primordion.xholon.io.GridPanel#handleButton1Event(java.awt.event.MouseEvent)
	 */
	protected void handleButton1Event(final MouseDownEvent me) {
		IXholon selectedGridCell = getSelectedGridCell(me);
		if (selectedGridCell == null) {return;}
		// toggle whether the grid cell is alive or dead
		selectedGridCell.setVal(!selectedGridCell.getVal_boolean());
		int widthHeight = getCellSize();
		int x = me.getX(); x = x - (x % widthHeight);
		int y = me.getY(); y = y - (y % widthHeight);
		//this.repaint(x, y, widthHeight, widthHeight);
		context.setFillStyle(getColor(selectedGridCell));
    context.beginPath();
    context.rect(x, y, widthHeight, widthHeight);
    context.closePath();
    context.fill();
	}
	
}

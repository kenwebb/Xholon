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

package org.primordion.xholon.io;

//import java.awt.Color; // GWT
import com.google.gwt.canvas.dom.client.CssColor;

import org.primordion.xholon.base.IXholon;

/**
 * A graphic panel in which to display 2D grids.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.2 (Created on November 27, 2005)
 */
public interface IGridPanel {
	
	// Shapes
	public static final int GPSHAPE_NOSHAPE = 0;
	public static final int GPSHAPE_CIRCLE  = 1;
	public static final int GPSHAPE_TRIANGLE  = 2;
	public static final int GPSHAPE_RECTANGLE  = 3;
	public static final int GPSHAPE_PENTAGON  = 4;
	public static final int GPSHAPE_HEXAGON  = 5;
	public static final int GPSHAPE_OCTOGON  = 6;
	public static final int GPSHAPE_STAR  = 7; // 5 or 6 pointed star
	public static final int GPSHAPE_TURTLE  = 8; // Logo-like turtle
	
	public static final int GPSHAPE_SMALLCIRCLE = 11;
	public static final int GPSHAPE_SMALLRECTANGLE = 12;
	public static final int GPSHAPE_REVERSETRIANGLE = 13; // upside-down triangle
	public static final int GPSHAPE_CROSS = 14;
	public static final int GPSHAPE_DIAMOND = 15;
	public static final int GPSHAPE_WYE = 16;
	public static final int GPSHAPE_LRTRIANGLE = 17; // left-to-right triangle
	public static final int GPSHAPE_RLTRIANGLE = 18; // right-to-left triangle

	/**
	 * Get grid owner.
	 * @return Grid owner.
	 */
	public abstract IXholon getGridOwner();
	
	/**
	 * Initialize the GridPanel.
	 * @param gridOwner Owner of the grid.
	 */
	public abstract void initGridPanel(IXholon gridOwner);
	/**
	 * Get the number of rows in the grid.
	 * @return The number of rows.
	 */
	public abstract int getNumRows();

	/**
	 * Get the number of columns in the grid.
	 * @return The number of columns.
	 */
	public abstract int getNumCols();

	/**
	 * Get the size of a grid cell.
	 * @return The size in pixels.
	 */
	public abstract int getCellSize();

	/**
	 * Set the size of a grid cell.
	 * @param cellSize The size in pixels.
	 */
	public abstract void setCellSize(int cellSize);
	
	/**
	 * Get whether or not to use shapes to draw objects.
	 * @return true or false
	 */
	public abstract boolean getUseShapes();
	
	/**
	 * Set whether or not to use shapes to draw objects.
	 * @param useShapes true or false
	 */
	public abstract void setUseShapes(boolean useShapes);

	/**
	 * Get the neighborhood type, as defined in IGrid.
	 * @return The neighborhood type.
	 */
	public abstract int getNeighType();
	
	/**
	 * Set the neighborhood type, as defined in IGrid.
	 * @param neighType The neighborhood type.
	 */
	public abstract void setNeighType(int neighType);
	
	/*
	 * @see javax.swing.JComponent#paintComponent(com.google.gwt.canvas.dom.client.Context2d)
	 */
	public abstract void paintComponent(com.google.gwt.canvas.dom.client.Context2d c); // GWT
	
	/**
	 * This is a method inherited by GridPanel from JPanel.
	 * @return The Graphics object used to paint the grid.
	 */
	//public abstract java.awt.Graphics getGraphics(); // GWT

	/**
	 * Get the color that this xholon should currently be displayed as.
	 * @param xhNode An instance of Xholon.
	 * @return A color.
	 */
	public abstract com.google.gwt.canvas.dom.client.CssColor getColor(IXholon xhNode); // GWT
	
	/**
	 * Get the shape that should be used to represent this xholon.
	 * @param xhNode An instance of Xholon.
	 * @return A shape identifier.
	 */
	public abstract int getShape(IXholon xhNode);
	
	public abstract void setGridCellColor(CssColor gridCellColor); // GWT
	public abstract void setGridCellColor(int rgbColor);
	
	public abstract boolean isCellsCanSupplyOwnColor();
	public abstract void setCellsCanSupplyOwnColor(boolean cellsCanSupplyOwnColor);
	
	/**
	 * Returns a data URL for the current content of the canvas element.
	 * @return a data URL for the current content of this element.
	 */
	public abstract String toDataUrl();
	
	/**
	 * Returns a data URL for the current content of the canvas element, with a specified type.
	 * @param type - the type of the data url, e.g., image/jpeg or image/png.
	 * @return a data URL for the current content of this element with the specified type.
	 */
	public abstract String toDataUrl(String type);
	
}

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

import org.primordion.xholon.base.IXholon;

/**
 * Interface for a graphical tree viewer, for example TreeViewerJung.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.2 (Created on November 29, 2005)
 */
public interface IGraphicalTreeViewer extends IXholon, IViewer {

	/**
	 * Set the default x and y distances between vertices.
	 * @param distX Distance in the x direction (system default is 50).
	 * @param distY Distance in the y direction (system default is 50).
	 */
	public abstract void setDistances(int distX, int distY);

	/**
	 * Set the size of the graph.
	 * @param sizeX Size in the x direction.
	 * @param sizeY Size in the y direction.
	 */
	public abstract void setGraphSize(int sizeX, int sizeY);

	/**
	 * Set the font size for displaying labels on vertices.
	 * @param fontSize The font size (default is 9).
	 */
	public abstract void setLabelFontSize(int fontSize);

	/**
	 * Create a graph using a tree layout.
	 * @param xhRoot The root of the IXholon tree.
	 * @param title The title to appear in the viewer window,
	 * @param showNodeLabels Whether vertices (nodes) should initially appear labelled.
	 */
	public abstract void createGraph(IXholon xhRoot, String title,
			boolean showNodeLabels);
	
	/**
	 * Remove the graph from the screen.
	 */
	public abstract void remove();
}
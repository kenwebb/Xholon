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
 * Interface for a graphical network viewer, for example AbstractNetworkViewerJung.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.2 (Created on November 29, 2005)
 */
public interface IGraphicalNetworkViewer extends IXholon, IViewer {

	// Layout types.
	/** No algorithm specified. */
	public static final int LAYOUT_NONE = -1;
	/** Fruchterman-Reingold algorithm. */
	public static final int LAYOUT_FR = 0;
	/** Implementation of a self-organizing map. */
	public static final int LAYOUT_ISOM = 1;
	/** Kamada-Kawai algorithm. */
	public static final int LAYOUT_KK = 2;
	/** Kamada-Kawai algorithm using integers. */
	public static final int LAYOUT_KKINT = 3;
	/** Spring. */
	public static final int LAYOUT_SPRING = 4;
	/** Static. */
	public static final int LAYOUT_STATIC = 5;
	/** Circle. */
	public static final int LAYOUT_CIRCLE = 6;
	/** Directed Acyclic Graph. */
	public static final int LAYOUT_DAG = 7;
	
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
	 * Select one of the layout types available with JUNG.
	 * The default is the FRLayout.
	 * @param layoutType The layout type.
	 */
	public abstract void setLayoutType( int layoutType);
	
	/**
	 * Select whether of not to include containers, and their edges, in the graph.
	 * @param showContainers Whether or not to show containers.
	 */
	public abstract void setShowContainers( boolean showContainers );
	
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
	
	/**
	 * Refresh the physical display to reflect changed information,
	 * such as the current coloring of the icons when states change in a state machine.
	 */
	public abstract void refresh();
	
	/**
	 * Refresh the physical display for this sub tree only, to reflect changed information,
	 * such as the current coloring of the icons when states change in a state machine.
	 */
	public abstract void refresh(IXholon xhNode);
	
	/**
	 * Is this network viewer active?
	 * If it's active then it can be refreshed.
	 * @return true or false
	 */
	public abstract boolean isActive();
}
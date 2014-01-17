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

package org.primordion.xholon.base;

/**
 * A grid is a structure containing M rows and N columns (gridCells). This structure can be either a
 * proper grid or a torus. A torus wraps between left and right, and between top and bottom. 
 * Each Xholon node within the grid can have a Von Neumann (4 neighbors)
 * or Moore (8 neighbors) neighborhood.</p>
 * <p>Specify these parameters in your ClassDetails configuration file:</p>
 * <p>Gmg Moore neighborhood, Grid</p>
 * <p>Gmt Moore neighborhood, Torus</p>
 * <p>Gvg Von Neumann neighborhood, Grid</p>
 * <p>Gvt Von Neumann neighborhood, Torus</p>
 * 
 * <p>[Update: January 29, 2007] There is a requirement for Hexagonal neighborhoods.</p>
 * <p>Ghg Hexagonal neighborhood, Grid</p>
 * <p>Ght Hexagonal neighborhood, Torus</p>
 * 
 * <p>[Update: February 15, 2007] There is a requirement for 1D Cellular Automaton (CA) neighborhoods.</p>
 * <p>Gcg CA neigborhood, Grid</p>
 * <p>Gct CA neigborhood, Torus</p>
 * 
 * <p>[Update: January 29, 2007] There are some similarities in the concept of a grid, and the concept of a regular graph.
 * As defined above, a grid is rectangular.
 * According to Rosen (Discrete Mathematics and Its Applications, 4th ed., 1999, McGraw-Hill, p.455),
 * "a simple graph is called regular if every vertex of this graph has the same degree".
 * For practical purposes, I am including Grid and Regular Graph in the same Java interface.
 * I am adding a number of types of special simple regular graphs: (see Rosen, p. 447+)</p>
 * <p>Gsk Sibling neighborhood, Complete graph (all nodes connected)</p>
 * <p>Gss Sibling neighborhood, Complete graph including Self (all nodes connected, including connection to self)</p>
 * <p>Gsc Sibling neighborhood, Cycle</p>
 * <p>Gsw Sibling neighborhood, Wheel</p>
 * <p>A Sibling Neighborhood is the neighborhood that includes all siblings of a specified node.</p>
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Jul 9, 2005)
 */
public interface IGrid {

	// Grid directions - Rectangular grids
	public static final int P_STAYHERE  = -1;
	public static final int P_NORTH     = 0; // pNorth
	public static final int P_NORTHEAST = 4; // pNorthEast
	public static final int P_EAST      = 1; // pEast
	public static final int P_SOUTHEAST = 5; // pSouthEast
	public static final int P_SOUTH     = 2; // pSouth
	public static final int P_SOUTHWEST = 6; // pSouthWest
	public static final int P_WEST      = 3; // pWest
	public static final int P_NORTHWEST = 7; // pNorthWest
	// Grid directions - Hexagonal grid (clockwise from top)
	public static final int P_HEX0 = 0;
	public static final int P_HEX1 = 1;
	public static final int P_HEX2 = 2;
	public static final int P_HEX3 = 3;
	public static final int P_HEX4 = 4;
	public static final int P_HEX5 = 5;
	// Grid directions - 1D CA grid
	// note: left and right are from an external perspective, where left is toward the start of the row
	public static final int P_CALEFTNEIGHBOR  = 0; // left neighbor in current time step; analogous to P_WEST
	public static final int P_CARIGHTNEIGHBOR = 1; // right neighbor in current time step; analogous to P_EAST
	public static final int P_CAFUTURESELF    = 2; // self in next time step; analogous to P_SOUTH
	// Neighborhood
	public static final int NEIGHBORHOOD_VON_NEUMANN = 1; // 4 neighbors
	public static final int NEIGHBORHOOD_MOORE       = 2; // 8 neighbors
	public static final int NEIGHBORHOOD_HEXAGONAL   = 3; // 6 neighbors
	public static final int NEIGHBORHOOD_SIBLING     = 4; // n neighbors, where n = number of siblings
	public static final int NEIGHBORHOOD_1DCA        = 5; // 3 neighbors
	// Boundary
	public static final int BOUNDARY_GRID  = 1;
	public static final int BOUNDARY_TORUS = 2; // boundaries wrap
	// Sibling neighborhoods, regular graphs
	public static final int REGULAR_COMPLETE      = 1; // k complete graph
	public static final int REGULAR_COMPLETE_SELF = 2; // s complete graph including self
	public static final int REGULAR_CYCLE         = 3; // c cycle
	public static final int REGULAR_WHEEL         = 4; // w wheel
	
	/**
	 * Set reference to north link, if this is a grid.
	 */
	public void setNorth(int row, int col, int boundType);
	/**
	 * Set reference to north east link, if this is a grid.
	 */
	public void setNorthEast(int row, int col, int boundType);
	/**
	 * Set reference to east link, if this is a grid.
	 */
	public void setEast(int row, int col, int boundType);
	/**
	 * Set reference to south east link, if this is a grid.
	 */
	public void setSouthEast(int row, int col, int boundType);
	/**
	 * Set reference to south link, if this is a grid.
	 */
	public void setSouth(int row, int col, int boundType);
	/**
	 * Set reference to south west link, if this is a grid.
	 */
	public void setSouthWest(int row, int col, int boundType);
	/**
	 * Set reference to west link, if this is a grid.
	 */
	public void setWest(int row, int col, int boundType);
	/**
	 * Set reference to north west link, if this is a grid.
	 */
	public void setNorthWest(int row, int col, int boundType);
	
	/**
	 * Set reference to Hex0 (top) link, if this is a hexagonal grid.
	 */
	public void setHex0(int row, int col, int boundType);
	/**
	 * Set reference to Hex1 link, if this is a hexagonal grid.
	 */
	public void setHex1(int row, int col, int boundType);
	/**
	 * Set reference to Hex2 link, if this is a hexagonal grid.
	 */
	public void setHex2(int row, int col, int boundType);
	/**
	 * Set reference to Hex3 link, if this is a hexagonal grid.
	 */
	public void setHex3(int row, int col, int boundType);
	/**
	 * Set reference to Hex4 link, if this is a hexagonal grid.
	 */
	public void setHex4(int row, int col, int boundType);
	/**
	 * Set reference to Hex5 link, if this is a hexagonal grid.
	 */
	public void setHex5(int row, int col, int boundType);
	
	/**
	 * Set reference to Left Neighbor link, if this is a 1D CA grid.
	 */
	public void setCaLeft(int row, int col, int boundType);
	/**
	 * Set reference to Right Neighbor link, if this is a 1D CA grid.
	 */
	public void setCaRight(int row, int col, int boundType);
	/**
	 * Set reference to Future Self link, if this is a 1D CA grid.
	 */
	public void setCaFuture(int row, int col, int boundType);
	
	/**
	 * Set references to all siblings, if this is a complete graph.
	 */
	public void setSiblingsComplete(int numNeighbors);
	/**
	 * Set references to all siblings and to self, if this is a complete graph including self.
	 */
	public void setSiblingsCompleteSelf(int numNeighbors);
	/**
	 * Set references to two adjacent siblings, if this is a cycle.
	 */
	public void setSiblingsCycle();
	/**
	 * Set references to two adjacent siblings, and to parent, if this is a wheel.
	 */
	public void setSiblingsWheel();
	
	/**
	 * Get the neighborhood type, as defined in IGrid.
	 * @return The neighborhood type.
	 */
	public int getNeighType();
	
	/**
	 * Get the number of neighbors that a grid cell has.
	 * @return The number of neighbors.
	 */
	public int getNumNeighbors();
	
	/**
	 * Move a set of xholons randomly into a grid structure.
	 * All immediate children of the parent node will be moved.
	 * This method should be called on the grid owner.
	 * @param xhParent The parent of the xholons that should be moved.
	 * @param allowMultiple Whether multiple xholons are allowed in the same grid cell.
	 */
	public void moveXholonsToGrid(IXholon xhParent, boolean allowMultiple);
}

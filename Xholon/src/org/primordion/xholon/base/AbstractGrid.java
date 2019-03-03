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

import java.util.ArrayList;
import java.util.List;

import org.primordion.xholon.util.Misc;
import org.primordion.xholon.util.MiscRandom;

/**
 * <p>AbstractGrid is an abstract class that your detailed behavior class (ex: XhGameOfLife)
 * should extend if it contains a grid superimposed on some portion of the tree.</p>
 * @see IGrid
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Jul 9, 2005)
 */
public abstract class AbstractGrid extends XholonWithPorts implements IGrid {
	private static final long serialVersionUID = 5738597062063499371L;
	
	// constants
	public static final int P_BEHAVIOR = 0; // active object's reference to behavior, such as a GP
	public static final int P_NEXT = 0; // node's link to next node in a linked list
	public static final int P_PREVIOUS = 1; // node's link to next node in a linked list
	
	/**
	 * Should the Grid nodes be validated to ensure they only have valid children?
	 * This should normally be false, because a Grid is not yet a formal Xholon mechanism.
	 * Some perfectly valid grids would not pass the test provided here.
	 */
	protected static final boolean SHOULD_VALIDATE_GRID = false;
	
	/**
	 * Constructor.
	 */
	public AbstractGrid() {}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
	}
	
	/*
	 * @see org.primordion.xholon.base.IGrid#getNeighType()
	 */
	// normally called on the grid owner, the parent of Row
	public int getNeighType() {
		int nType = NEIGHBORHOOD_MOORE; // at least one grid is of this type
		//AbstractGrid row = (AbstractGrid)getFirstChild();
		IXholon row = getFirstChild();
		if (row == null) {
		  // when user requests attributes or other menu items, it may be called on GridCell or Row, so check for row == null
		  return nType;
		}
		if ((row.getXhcName().equals("Row"))
				|| row.getXhType() == IXholonClass.XhtypeGridEntity
				|| ("row".equals(row.getRoleName()))) {
			AbstractGrid gridCell = (AbstractGrid)row.getFirstChild();
			switch(gridCell.getNumNeighbors()) {
			case 2:
				row = (AbstractGrid)row.getNextSibling();
				gridCell = (AbstractGrid)row.getFirstChild();
				switch(gridCell.getNumNeighbors()) {
				case 2:
					nType = NEIGHBORHOOD_1DCA; // Gcg
					break;
				case 3:
					nType = NEIGHBORHOOD_VON_NEUMANN; // Gvg
					break;
				default:
					break;
				}
				break;
			case 3:
				gridCell = (AbstractGrid)gridCell.getNextSibling();
				switch(gridCell.getNumNeighbors()) {
				case 2:
					nType = NEIGHBORHOOD_HEXAGONAL; // Ghg
					break;
				case 3:
					// TODO could also be NEIGHBORHOOD_1DCA; // Gct
					if (gridCell.getNextSibling() == null) {
						nType = NEIGHBORHOOD_MOORE; // Gmg
					}
					else {
						gridCell = (AbstractGrid)gridCell.getNextSibling();
						if (gridCell.getNumNeighbors() == 3) {
							nType = NEIGHBORHOOD_1DCA; // Gct
						}
						else { // 5
							nType = NEIGHBORHOOD_HEXAGONAL; // Ghg
						}
					}
					break;
				case 5:
					nType = NEIGHBORHOOD_MOORE; // Gmg
					break;
				default:
					break;
				}
				break;
			case 4:
				nType = NEIGHBORHOOD_VON_NEUMANN; // Gvt
				break;
			case 5:
			case 6:
				nType = NEIGHBORHOOD_HEXAGONAL; // Ght
				break;
			case 8:
				nType = NEIGHBORHOOD_MOORE; // Gmt
				break;
			default:
				break;
			}
		}
		return nType;
	}
	
	/*
	 * @see org.primordion.xholon.base.IGrid#getNumNeighbors()
	 */
	// called on a grid cell
	public int getNumNeighbors() {
		int nNeigh = 0;
		// calculate actual number
		if (port != null) {
			for (int i = 0; i < port.length; i++) {
				if (port[i] != null) {
					nNeigh++;
				}
			}
		}
		return nNeigh;
	}
	
	@Override
	public Object getNeighbors() {
	  Object arr = null;
		if (port != null) {
			for (int i = 0; i < port.length; i++) {
				if (port[i] != null) {
					arr = makeNeighbor(port[i], arr);
				}
			}
		}
		return arr;
	}
	
	public native Object makeNeighbor(IXholon node, Object arr) /*-{
		if (!arr) {arr = [];}
		arr.push(node);
		return arr;
	}-*/;
	
	/*
	 * @see org.primordion.xholon.base.IXholon#configure(java.lang.String, int)
	 */
	public int configure(String instructions, int instructIx)
	{
		instructIx++;
		if (instructions.charAt(instructIx) == 'G') {
			// Von Neumann (4), Moore (8), Hexagonal (6), or Sibling (n) neighborhood
			instructIx++;
			char inChar = instructions.charAt(instructIx);
			switch (inChar) {
			case 'v': // Von Neumann neighborhood
			case 'm': // Moore neighborhood
				instructIx = configureRectangularNeighborhood(instructions, instructIx);
				break;
			case 'h': // Hexagonal neighborhood
				instructIx = configureHexagonalNeighborhood(instructions, instructIx);
				break;
			case 'c': // 1D CA neighborhood
				instructIx = configure1dCaNeighborhood(instructions, instructIx);
				break;
			case 's': // Sibling neighborhood
				instructIx = configureSiblingNeighborhood(instructions, instructIx);
				break;
			default:
				break;
			}
		}
		else if (instructions.charAt(instructIx) == 'f') {
			// function argument (GP) - handled by subclass of AbstractGrid
			instructIx += 2; // ex: f2.
		}
		return instructIx;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#postConfigure()
	 */
	public void postConfigure()
	{
		super.postConfigure();
		if (!isValid()) {
			logger.error("Grid node " + this.getName()
					+ " has children that are invalid within a Xholon grid.");
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#preReconfigure()
	 */
	public void preReconfigure()
	{
		super.preReconfigure();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#reconfigure()
	 */
	public void reconfigure()
	{
		super.reconfigure();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#postReconfigure()
	 */
	public void postReconfigure()
	{
		super.postReconfigure();
	}
	
	/**
	 * Configure a Rectangular neighborhood,
	 * either a Von Neumann neighborhood or a Moore neighborhood.
	 * @param instructions A String containing the instructions to configure the neighborhood.
	 * @param instructIx An index into the instructions String.
	 * @return An updated index into the instructions String.
	 */
	protected int configureRectangularNeighborhood(String instructions, int instructIx)
	{
		// Game of Life, or any grid
		// get global position in grid (row, col)
		char inChar = instructions.charAt(instructIx);
		int row = getRow();
		int col = getCol();
		int boundType;
		int neighType;
		if (inChar == 'v') { // v Von Neumann neighborhood (4 neighbors)
			neighType = NEIGHBORHOOD_VON_NEUMANN;
		}
		else { // m Moore neighborhood (8 neighbors)
			neighType = NEIGHBORHOOD_MOORE;
		}
		instructIx++;
		// Grid or Torus
		inChar = instructions.charAt(instructIx);
		if (inChar == 'g') { // Grid boundary
			boundType = BOUNDARY_GRID;
		}
		else { // t Torus boundary
			boundType = BOUNDARY_TORUS;
		}
		instructIx++;
		// set all the links
		setNorth(row, col, boundType);
		setSouth(row, col, boundType);
		setWest(row, col, boundType);
		setEast(row, col, boundType);
		if (neighType == NEIGHBORHOOD_MOORE) { // do diagonals as well
			setNorthEast(row, col, boundType);
			setSouthEast(row, col, boundType);
			setSouthWest(row, col, boundType);
			setNorthWest(row, col, boundType);
		}
		return instructIx;
	}
	
	/**
	 * Configure a Hexagonal neighborhood.
	 * @param instructions A String containing the instructions to configure the neighborhood.
	 * @param instructIx An index into the instructions String.
	 * @return An updated index into the instructions String.
	 */
	protected int configureHexagonalNeighborhood(String instructions, int instructIx)
	{
		int row = getRow();
		int col = getCol();
		int boundType;
		instructIx++;
		// Grid or Torus
		char inChar = instructions.charAt(instructIx);
		if (inChar == 'g') { // Grid boundary
			boundType = BOUNDARY_GRID;
		}
		else { // t Torus boundary
			boundType = BOUNDARY_TORUS;
		}
		instructIx++;
		// set all the hexagonal links
		setHex0(row, col, boundType);
		setHex3(row, col, boundType);
		setHex2(row, col, boundType);
		setHex1(row, col, boundType);
		setHex5(row, col, boundType);
		setHex4(row, col, boundType);
		return instructIx;
	}
	
	/**
	 * Configure a Sibling neighborhood.
	 * @param instructions A String containing the instructions to configure the neighborhood.
	 * @param instructIx An index into the instructions String.
	 * @return An updated index into the instructions String.
	 */
	protected int configureSiblingNeighborhood(String instructions, int instructIx)
	{
		int numNeighbors = getNumSiblings();
		instructIx++;
		// Complete, Cycle or Wheel
		char inChar = instructions.charAt(instructIx);
		if (inChar == 'k') { // Complete
			setSiblingsComplete(numNeighbors);
		}
		else if (inChar == 's') { // Complete including Self
			setSiblingsCompleteSelf(numNeighbors);
		}
		else if (inChar == 'c') { // Cycle
			setSiblingsCycle();
		}
		else { // w Wheel
			setSiblingsWheel();
		}
		instructIx++;
		return instructIx;
	}
	
	/**
	 * Configure a 1D CA neighborhood.
	 * @param instructions A String containing the instructions to configure the neighborhood.
	 * @param instructIx An index into the instructions String.
	 * @return An updated index into the instructions String.
	 */
	protected int configure1dCaNeighborhood(String instructions, int instructIx)
	{
		// get global position in grid (row, col)
		char inChar = instructions.charAt(instructIx);
		//setRowCol();
		int row = getRow();
		int col = getCol();
		int boundType;
		instructIx++;
		// Grid or Torus
		inChar = instructions.charAt(instructIx);
		if (inChar == 'g') { // Grid boundary
			boundType = BOUNDARY_GRID;
		}
		else { // t Torus boundary
			boundType = BOUNDARY_TORUS;
		}
		instructIx++;
		// set all the links
		setCaLeft(row, col, boundType);
		setCaRight(row, col, boundType);
		setCaFuture(row, col, boundType);
		return instructIx;
	}
	
	/*
	 * @see org.primordion.xholon.base.IGrid#setNorth()
	 */
	public void setNorth(int row, int col, int boundType) {
		IXholon node = null;
		int j;
		if (row > 0) {
			node = parentNode.getParentNode();
			node = node.getFirstChild();
			for (j = 0; j < row-1; j++) {
				node = node.getNextSibling();
			}
			node = node.getFirstChild();
			for (j = 0; j < col; j++) {
				node = node.getNextSibling();
			}
			port[P_NORTH] = (AbstractGrid)node;
		}
		else {
			port[P_NORTH] = null;
		}
		if ((port[P_NORTH] == null) && (boundType == BOUNDARY_TORUS)) {
			// must be in the first row
			node = parentNode;
			while (node.getNextSibling() != null) {
				node = node.getNextSibling();
			}
			node = node.getFirstChild();
			for (j = 0; j < col; j++) {
				node = node.getNextSibling();
			}
			port[P_NORTH] = (AbstractGrid)node;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IGrid#setSouth()
	 */
	public void setSouth(int row, int col, int boundType) {
		IXholon node = null;
		int j;
		node = parentNode.getNextSibling();
		if (node != null) {
			node = node.getFirstChild();
			if (node == null) { // found Dummy
				port[P_SOUTH] = null;
			}
			else {
				for (j = 0; j < col; j++) {
					node = node.getNextSibling();
				}
				port[P_SOUTH] = (AbstractGrid)node;
			}
		}
		else {
			port[P_SOUTH] = null;
		}
		if ((port[P_SOUTH] == null) && (boundType == BOUNDARY_TORUS)) {
			// must be the last row
			node = parentNode.getParentNode();
			node = node.getFirstChild(); // get first row
			node = node.getFirstChild(); // get first cell in first row
			for (j = 0; j < col; j++) {
				node = node.getNextSibling();
			}
			port[P_SOUTH] = (AbstractGrid)node;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IGrid#setWest()
	 */
	public void setWest(int row, int col, int boundType) {
		IXholon node = null;
		int j;
		if (col > 0) {
			node = parentNode.getFirstChild();
			for (j = 0; j < col-1; j++) {
				node = node.getNextSibling();
			}
			port[P_WEST] = (AbstractGrid)node;
		}
		else {
			port[P_WEST] = null;
		}
		if ((port[P_WEST] == null) && (boundType == BOUNDARY_TORUS)) {
			// must be first column
			node = this;
			while (node.getNextSibling() != null) {
				node = node.getNextSibling();
			}
			port[P_WEST] = (AbstractGrid)node;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IGrid#setEast()
	 */
	public void setEast(int row, int col, int boundType) {
		IXholon node = null;
		port[P_EAST] = (AbstractGrid)nextSibling; // will be null if this is last cell in row
		if ((port[P_EAST] == null) && (boundType == BOUNDARY_TORUS)) {
			// must be last column
			node = parentNode; // get row
			node = node.getFirstChild(); // get first column of this row
			port[P_EAST] = (AbstractGrid)node;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IGrid#setNorthEast()
	 */
	public void setNorthEast(int row, int col, int boundType) {
		IXholon node = null;
		if (port[P_NORTH] != null) {
			port[P_NORTHEAST] = ((AbstractGrid)port[P_NORTH]).port[P_EAST];
		}
		else {
			port[P_NORTHEAST] = null;
		}
		if ((port[P_NORTHEAST] == null) && (boundType == BOUNDARY_TORUS)) {
			// must be in the first row
			node = port[P_NORTH].getNextSibling();
			if (node == null) { // must be northeast corner
				node = port[P_NORTH].getParentNode(); // get row
				node = node.getFirstChild();
			}
			port[P_NORTHEAST] = (AbstractGrid)node;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IGrid#setSouthEast()
	 */
	public void setSouthEast(int row, int col, int boundType) {
		IXholon node = null;
		if (port[P_SOUTH] != null) {
			port[P_SOUTHEAST] = (AbstractGrid)port[P_SOUTH].getNextSibling();
		}
		else {
			port[P_SOUTHEAST] = null;
		}
		if ((port[P_SOUTHEAST] == null) && (boundType == BOUNDARY_TORUS)) {
			// last column
			node = port[P_SOUTH].getParentNode();
			port[P_SOUTHEAST] = (AbstractGrid)node.getFirstChild();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IGrid#setSouthWest()
	 */
	public void setSouthWest(int row, int col, int boundType) {
		IXholon node = null;
		if (port[P_WEST] != null) {
			port[P_SOUTHWEST] = ((AbstractGrid)port[P_WEST]).port[P_SOUTH];
		}
		else {
			port[P_SOUTHWEST] = null;
		}
		if ((port[P_SOUTHWEST] == null) && (boundType == BOUNDARY_TORUS)) {
			// must be the first column
			node = port[P_SOUTH];
			while (node.getNextSibling() != null) {
				node = node.getNextSibling();
			}
			port[P_SOUTHWEST] = (AbstractGrid)node;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IGrid#setNorthWest()
	 */
	public void setNorthWest(int row, int col, int boundType) {
		IXholon node = null;
		int j;
		if (port[P_NORTH] != null) {
			port[P_NORTHWEST] = ((AbstractGrid)port[P_NORTH]).port[P_WEST];
			// southeast
			if ((port[P_NORTHWEST] != null) && (((AbstractGrid)port[P_NORTHWEST]).port[P_SOUTHEAST] == null)) {
				((AbstractGrid)port[P_NORTHWEST]).port[P_SOUTHEAST] = this;
			}
		}
		else {
			port[P_NORTHWEST] = null;
		}
		if ((port[P_NORTHWEST] == null) && (boundType == BOUNDARY_TORUS)) {
			// must be in the first row
			node = port[P_NORTH].getParentNode();
			node = node.getFirstChild();
			if (col > 0) {
				for (j= 0; j < col-1; j++) {
					node = node.getNextSibling();
				}
			}
			else {
				while (node.getNextSibling() != null) {
					node = node.getNextSibling();
				}
			}
			port[P_NORTHWEST] = (AbstractGrid)node;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IGrid#setHex0()
	 */
	public void setHex0(int row, int col, int boundType) {
		IXholon node = null;
		int j;
		if (row > 0) {
			node = parentNode.getParentNode();
			node = node.getFirstChild();
			for (j = 0; j < row-1; j++) {
				node = node.getNextSibling();
			}
			node = node.getFirstChild();
			for (j = 0; j < col; j++) {
				node = node.getNextSibling();
			}
			port[P_HEX0] = (AbstractGrid)node;
		}
		else {
			port[P_HEX0] = null;
		}
		if ((port[P_HEX0] == null) && (boundType == BOUNDARY_TORUS)) {
			// must be in the first row
			node = parentNode;
			while (node.getNextSibling() != null) {
				node = node.getNextSibling();
			}
			node = node.getFirstChild();
			for (j = 0; j < col; j++) {
				node = node.getNextSibling();
			}
			port[P_HEX0] = (AbstractGrid)node;
		}

	}
	
	/*
	 * @see org.primordion.xholon.base.IGrid#setHex1()
	 */
	public void setHex1(int row, int col, int boundType) {
		IXholon node = null;
		if (Misc.isEven(col)) {
			port[P_HEX1] = (AbstractGrid)nextSibling; // will be null if this is last cell in row
			if ((port[P_HEX1] == null) && (boundType == BOUNDARY_TORUS)) {
				// must be last column
				node = parentNode; // get row
				node = node.getFirstChild(); // get first column of this row
				port[P_HEX1] = (AbstractGrid)node;
			}
		}
		else { // odd
			if (port[P_HEX0] != null) {
				port[P_HEX1] = ((AbstractGrid)port[P_HEX0]).port[P_HEX2];
			}
			else {
				port[P_HEX1] = null;
			}
			if ((port[P_HEX1] == null) && (boundType == BOUNDARY_TORUS)) {
				// must be in the first row
				node = port[P_HEX0].getNextSibling();
				if (node == null) { // must be northeast corner
					node = port[P_HEX0].getParentNode(); // get row
					node = node.getFirstChild();
				}
				port[P_HEX1] = (AbstractGrid)node;
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IGrid#setHex2()
	 */
	public void setHex2(int row, int col, int boundType) {
		IXholon node = null;
		if (Misc.isEven(col)) {
			if (port[P_HEX3] != null) {
				port[P_HEX2] = (AbstractGrid)port[P_HEX3].getNextSibling();
			}
			else {
				port[P_HEX2] = null;
			}
			if ((port[P_HEX2] == null) && (boundType == BOUNDARY_TORUS)) {
				// last column
				node = port[P_HEX3].getParentNode();
				port[P_HEX2] = (AbstractGrid)node.getFirstChild();
			}
		}
		else { // odd
			port[P_HEX2] = (AbstractGrid)nextSibling; // will be null if this is last cell in row
			if ((port[P_HEX2] == null) && (boundType == BOUNDARY_TORUS)) {
				// must be last column
				node = parentNode; // get row
				node = node.getFirstChild(); // get first column of this row
				port[P_HEX2] = (AbstractGrid)node;
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IGrid#setHex3()
	 */
	public void setHex3(int row, int col, int boundType) {
		IXholon node = null;
		int j;
		node = parentNode.getNextSibling();
		if (node != null) {
			node = node.getFirstChild();
			if (node == null) { // found Dummy
				port[P_HEX3] = null;
			}
			else {
				for (j = 0; j < col; j++) {
					node = node.getNextSibling();
				}
				port[P_HEX3] = (AbstractGrid)node;
			}
		}
		else {
			port[P_HEX3] = null;
		}
		if ((port[P_HEX3] == null) && (boundType == BOUNDARY_TORUS)) {
			// must be the last row
			node = parentNode.getParentNode();
			node = node.getFirstChild(); // get first row
			node = node.getFirstChild(); // get first cell in first row
			for (j = 0; j < col; j++) {
				node = node.getNextSibling();
			}
			port[P_HEX3] = (AbstractGrid)node;
		}

	}
	
	/*
	 * @see org.primordion.xholon.base.IGrid#setHex4()
	 */
	public void setHex4(int row, int col, int boundType) {
		IXholon node = null;
		if (Misc.isEven(col)) {
			if (port[P_HEX5] != null) {
				port[P_HEX4] = ((AbstractGrid)port[P_HEX5]).port[P_HEX3];
			}
			else {
				port[P_HEX4] = null;
			}
			if ((port[P_HEX4] == null) && (boundType == BOUNDARY_TORUS)) {
				// must be the first column
				node = port[P_HEX3];
				while (node.getNextSibling() != null) {
					node = node.getNextSibling();
				}
				port[P_HEX4] = (AbstractGrid)node;
			}
		}
		else { // odd
			int j;
			if (col > 0) {
				node = parentNode.getFirstChild();
				for (j = 0; j < col-1; j++) {
					node = node.getNextSibling();
				}
				port[P_HEX4] = (AbstractGrid)node;
			}
			else {
				port[P_HEX4] = null;
			}
			if ((port[P_HEX4] == null) && (boundType == BOUNDARY_TORUS)) {
				// must be first column
				node = this;
				while (node.getNextSibling() != null) {
					node = node.getNextSibling();
				}
				port[P_HEX4] = (AbstractGrid)node;
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IGrid#setHex5()
	 */
	public void setHex5(int row, int col, int boundType) {
		IXholon node = null;
		if (Misc.isEven(col)) {
			int j;
			if (col > 0) {
				node = parentNode.getFirstChild();
				for (j = 0; j < col-1; j++) {
					node = node.getNextSibling();
				}
				port[P_HEX5] = (AbstractGrid)node;
			}
			else {
				port[P_HEX5] = null;
			}
			if ((port[P_HEX5] == null) && (boundType == BOUNDARY_TORUS)) {
				// must be first column
				node = this;
				while (node.getNextSibling() != null) {
					node = node.getNextSibling();
				}
				port[P_HEX5] = (AbstractGrid)node;
			}
		}
		else { // odd
			if (port[P_HEX0] != null) {
				port[P_HEX5] = ((AbstractGrid)port[P_HEX0]).getPreviousSibling();
			}
			else {
				port[P_HEX5] = null;
			}
			if ((port[P_HEX5] == null) && (boundType == BOUNDARY_TORUS)) {
				// must be the first column
				node = port[P_HEX0];
				while (node.getNextSibling() != null) {
					node = node.getNextSibling();
				}
				port[P_HEX5] = (AbstractGrid)node;
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IGrid#setCaLeft()
	 */
	public void setCaLeft(int row, int col, int boundType) {
		IXholon node = null;
		int j;
		if (col > 0) {
			node = parentNode.getFirstChild();
			for (j = 0; j < col-1; j++) {
				node = node.getNextSibling();
			}
			port[P_CALEFTNEIGHBOR] = (AbstractGrid)node;
		}
		else {
			port[P_CALEFTNEIGHBOR] = null;
		}
		if ((port[P_CALEFTNEIGHBOR] == null) && (boundType == BOUNDARY_TORUS)) {
			// must be first column
			node = this;
			while (node.getNextSibling() != null) {
				node = node.getNextSibling();
			}
			port[P_CALEFTNEIGHBOR] = (AbstractGrid)node;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IGrid#setCaRight()
	 */
	public void setCaRight(int row, int col, int boundType) {
		IXholon node = null;
		port[P_CARIGHTNEIGHBOR] = (AbstractGrid)nextSibling; // will be null if this is last cell in row
		if ((port[P_CARIGHTNEIGHBOR] == null) && (boundType == BOUNDARY_TORUS)) {
			// must be last column
			node = parentNode; // get row
			node = node.getFirstChild(); // get first column of this row
			port[P_CARIGHTNEIGHBOR] = (AbstractGrid)node;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IGrid#setCaFuture()
	 */
	public void setCaFuture(int row, int col, int boundType) {
		IXholon node = null;
		int j;
		node = parentNode.getNextSibling();
		if (node != null) {
			node = node.getFirstChild();
			if (node == null) { // found Dummy
				port[P_CAFUTURESELF] = null;
			}
			else {
				for (j = 0; j < col; j++) {
					node = node.getNextSibling();
				}
				port[P_CAFUTURESELF] = (AbstractGrid)node;
			}
		}
		else {
			port[P_CAFUTURESELF] = null;
		}
		if ((port[P_CAFUTURESELF] == null) && (boundType == BOUNDARY_TORUS)) {
			// must be the last row
			node = parentNode.getParentNode();
			node = node.getFirstChild(); // get first row
			node = node.getFirstChild(); // get first cell in first row
			for (j = 0; j < col; j++) {
				node = node.getNextSibling();
			}
			port[P_CAFUTURESELF] = (AbstractGrid)node;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IGrid#setSiblingsComplete()
	 */
	public void setSiblingsComplete(int numNeighbors) {
		IXholon node = getNextSibling();
		port = new IXholon[numNeighbors];
		for (int i = 0; i < numNeighbors; i++) {
			if (node == null) { // have reached rightmost sibling
				node = getFirstSibling(); // get leftmost sibling
			}
			port[i] = (AbstractGrid)node;
			node = node.getNextSibling();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IGrid#setSiblingsCompleteSelf()
	 */
	public void setSiblingsCompleteSelf(int numNeighbors) {
		IXholon node = getNextSibling();
		port = new IXholon[numNeighbors+1];
		port[0] = this; // self
		for (int i = 0; i < numNeighbors; i++) {
			if (node == null) { // have reached rightmost sibling
				node = getFirstSibling(); // get leftmost sibling
			}
			if (i+1 < port.length) {
				port[i+1] = (AbstractGrid)node;
			}
			node = node.getNextSibling();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IGrid#setSiblingsCycle()
	 */
	public void setSiblingsCycle() {
		port = new IXholon[2];
		// get link to next sibling in cycle
		IXholon node = getNextSibling();
		if (node == null) { // have reached rightmost sibling
			node = getFirstSibling(); // get leftmost sibling
		}
		port[0] = (AbstractGrid)node;
		// get link to previous sibling in cycle
		node = getPreviousSibling();
		if (node == null) { // have reached leftmost sibling
			node = this.getLastSibling(); // get rightmost sibling
		}
		port[1] = (AbstractGrid)node;
	}
	
	/*
	 * @see org.primordion.xholon.base.IGrid#setSiblingsWheel()
	 */
	public void setSiblingsWheel() {
		port = new IXholon[3];
		// get link to next sibling in cycle
		IXholon node = getNextSibling();
		if (node == null) { // have reached rightmost sibling
			node = getFirstSibling(); // get leftmost sibling
		}
		port[0] = (AbstractGrid)node;
		// get link to previous sibling in cycle
		node = getPreviousSibling();
		if (node == null) { // have reached leftmost sibling
			node = this.getLastSibling(); // get rightmost sibling
		}
		port[1] = (AbstractGrid)node;
		port[2] = (AbstractGrid)getParentNode();
	}
	
	/**
	 * Get this xholon's row in the grid.
	 * @return A zero-indexed row number.
	 */
	protected int getRow() {
		int row = 0;
		IXholon node = parentNode.getParentNode();
		node = node.getFirstChild();
		while (!node.equals(parentNode)) {
			row++;
			node = node.getNextSibling();
		}
		return row;
	}
	
	/**
	 * Get this xholon's column in the grid.
	 * @return A zero-indexed column number.
	 */
	protected int getCol() {
		int col = 0;
		IXholon node = parentNode.getFirstChild();
		while (!node.equals(this)) {
			col++;
			node = node.getNextSibling();
		}
		return col;
	}
	
	/*
	 * @see org.primordion.xholon.base.IGrid#moveXholonsToGrid(org.primordion.xholon.base.IXholon)
	 */
	public void moveXholonsToGrid(IXholon xhParent, boolean allowMultiple)
	{
		int numRows = getNumChildren(false);
		int numCols = getFirstChild().getNumChildren(false);
		IXholon thisNode = xhParent.getFirstChild();
		while (thisNode != null) {
			// save nextNode because the link from thisNode will be destroyed while it's being moved
			IXholon nextNode = thisNode.getNextSibling();
			boolean moved = false;
			while (!moved) {
				moved = moveXholonToGrid(thisNode, numRows, numCols, allowMultiple);
			}
			thisNode = nextNode;
		}
	}
	
	/**
	 * Move a single xholon randomly into a grid structure.
	 * This method should be called on the grid owner.
	 * @param xhNode The node that should be moved.
	 * @param grid The grid owner.
	 * @param numRows The number of rows in the grid.
	 * @param numCols The number of columns in one row of the grid.
	 * @param allowMultiple Whether multiple xholons are allowed in the same grid cell.
	 */
	protected boolean moveXholonToGrid(IXholon xhNode, int numRows, int numCols, boolean allowMultiple) {
		int rRow = MiscRandom.getRandomInt(0, numRows);
		int rCol = MiscRandom.getRandomInt(0, numCols);
		IXholon currentRow = getFirstChild();
		while (currentRow != null) {
			if (rRow-- == 0) {
				IXholon currentCol = currentRow.getFirstChild();
				while (currentCol != null) {
					if (rCol-- == 0) {
						if (!allowMultiple && currentCol.hasChildNodes()) {
							return false;
						}
						else {
							xhNode.removeChild(); // detach the node from parent and sibling links
							xhNode.appendChild(currentCol); // insert as the last child of the new parent
							return true;
						}
					}
					else {
						currentCol = currentCol.getNextSibling();
					}
				}
			}
			else {
				currentRow = currentRow.getNextSibling();
			}
		}
		return false;
	}
	
	/**
	 * Get the number of ports in this type of grid.
	 * @return Number of ports.
	 */
	public int getMaxPorts() {
		int numNeighbors = 8;
		int neigborhoodType;
		String instructions = getXhc().getConfigurationInstructions();
		if ((instructions != null) && (instructions.length() > 3)) {
			neigborhoodType = instructions.charAt(2); // ex: CGmt CGvt CGht CGct CGss
		}
		else {
			logger.warn("Abstract Grid getMaxPorts(): Class instructions are invalid for "
				+ getName() + " " + instructions);
			return numNeighbors;
		}
		if (neigborhoodType == 's') { // Sibling neighborhood ex: CGss
			numNeighbors = getNumSiblings();
			if (instructions.charAt(3) == 's') { // include self in set of siblings
				numNeighbors++;
			}
			return numNeighbors;
		}
		XholonWithPorts referenceNode = (XholonWithPorts)parentNode.getParentNode().getFirstChild().getFirstChild();
		if (referenceNode == this) {
			// calculate numNeighbors from first principles
			switch (neigborhoodType) {
			case 'v': // Von Neumann neighborhood
				numNeighbors = 4;
				break;
			case 'm': // Moore neighborhood
				numNeighbors = 8;
				break;
			case 'h': // Hexagonal neighborhood
				numNeighbors = 6;
				break;
			case 'c': // 1D CA neighborhood
				numNeighbors = 3;
				break;
			default:
				logger.warn("Abstract Grid getMaxPorts(): Unknown neighborhood type. " + instructions);
				break;
			}
		}
		else {
			numNeighbors = referenceNode.port.length;
		}
		return numNeighbors;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setPorts()
	 */
	public void setPorts() {
		// move up the inheritance hierarchy looking for first parent with a xhType
		IXholonClass iXhc = xhc;
		while ((!iXhc.isRootNode()) && (iXhc.getXhType() == IXholonClass.XhtypeNone)) {
			iXhc = (IXholonClass)iXhc.getParentNode();
		}
		// is this a grid cell?
		if ("GridCell".equals(getXhcName())
				|| getXhType() == IXholonClass.XhtypeGridEntityActivePassive
				|| "gridcell".equals(getRoleName())) { // first child of type "GridCell"
			int maxPorts = getMaxPorts();
			if (maxPorts > 0) {
				//System.out.print("" + maxPorts);
				port = new IXholon[maxPorts];
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getAllPorts()
	 */
	public List<PortInformation> getAllPorts() {
		List<PortInformation> portList = new ArrayList<PortInformation>();
		if (port != null) {
			for (int i = 0; i < port.length; i++) {
				if (port[i] != null) {
				  // public PortInformation(String fieldName, int fieldNameIndex, IXholon reffedNode) {
					portList.add(new PortInformation("port", i, port[i]));
				}
			}
		}
		return portList;
	}
	
	@Override
	/**
	 * Prevent a search for referencing nodes.
	 * Grid nodes are typically bidirectionally connected to each other,
	 * so it's redundant and very time consuming to search for nodes that
	 * reference this node.
	 * @return an empty list
	 */
	public List<IXholon> searchForReferencingNodes() {
	  return new ArrayList<IXholon>();
	}
	
	/**
	 * Is this a valid node in a Grid mechanism?
	 * All children of a &lt;Grid> node must be &lt;Row> nodes.
	 * All children of a &lt;Row> node must be &lt;GridCell> nodes.
	 * <p>Grid is not yet a formal Xholon mechanism, and is currently just an informal concept.
	 * When it does become formalized, this method should be changed to use GridCE, RowCE, GridCellCE .</p>
	 * @return true or false
	 */
	protected boolean isValid()
	{
		if (SHOULD_VALIDATE_GRID) {
			if ("Grid".equals(getXhcName())) {
				IXholon node = getFirstChild();
				while (node != null) {
					if (!"Row".equals(node.getXhcName())) {
						return false;
					}
					node = node.getNextSibling();
				}
			}
			else if ("Row".equals(getXhcName())) {
				IXholon node = getFirstChild();
				while (node != null) {
					if (!"GridCell".equals(node.getXhcName())) {
						return false;
					}
					node = node.getNextSibling();
				}
			}
		}
		return true;
	}
	
}

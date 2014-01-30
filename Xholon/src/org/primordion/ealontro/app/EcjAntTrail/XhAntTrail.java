/* Ealontro - systems that evolve and adapt to their environment
 * Copyright (C) 2006, 2007, 2008 Ken Webb
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

package org.primordion.ealontro.app.EcjAntTrail;

import com.google.gwt.core.client.GWT;

import org.primordion.xholon.base.AbstractGrid;
import org.primordion.xholon.base.IActivity;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.util.MiscIoGwt;

/**
 * ECJ Ant Trail.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on June 7, 2006)
 */
public class XhAntTrail extends AbstractGrid implements IActivity, CeAntTrail {
	
	// ports
	public static final int P_BEHAVIOR = 0; // also defined in AbstractGrid
	public static final int P_GRID = 1; // Ant points to a position in the Grid
	public static final int P_FOOD = 2; // Ant points to accumulated Food
	//private static final int SIZE_MYAPP_PORTS = 4; // Von Neumann neighborhood, 4 neighbors
	
	// Grid attributes; possible values of val
	public static final int VAL_EMPTY = 0; // no trail or food
	public static final int VAL_TRAIL = 1; // has part of the trail
	public static final int VAL_FOOD  = 2; // trail with food
	public static final int VAL_EMPTY_ANT = 8; // Ant is currently here; no trail or food
	public static final int VAL_TRAIL_ANT = 9; // Ant is currently here; has part of the trail
	public static final int VAL_FOOD_ANT  = 10; // Ant is currently here; trail with food
	public static final int VAL_EMPTY_VISITED = 16;
	public static final int VAL_TRAIL_VISITED = 17;
	public static final int VAL_FOOD_VISITED  = 18; // had food that has now been eaten
	
	// state variables, control variables
	public int val; // whether a gridCell has part of a trail, and/or a food pellet
	public String roleName;
	
	// direction
	public static int direction = P_EAST; // Ant initially faces East; static because there's only 1 ant
	public static String directionName[] = {"North", "East", "South", "West"};
	
	// trail file name
	public static String trailFile = "dummy"; // "config/ealontro/EcjAntTrail/santaFe.trl"

	/**
	 * Constructor.
	 */
	public XhAntTrail() { val = VAL_EMPTY;}

	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
	}
	
	/*
	 * @see org.primordion.xholon.base.AbstractGrid#setPorts()
	 */
	public void setPorts()
	{
		switch(xhc.getId()) {
		case AntCE:
			// Because this node inherits from AbstractGrid but is not really a grid node,
			// it won't normally get its ports set.
			// It's being done manually here.
			port = new IXholon[3];
			break;
		default:
			super.setPorts();
			break;
		}
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#postConfigure()
	 */
	public void postConfigure()
	{
		switch(xhc.getId()) {
		case TrailCE:
			readGridFile(trailFile); //("config/ealontro/EcjAntTrail/santaFe.trl");
			break;
		case AntCE:
			port[P_GRID].setVal(VAL_EMPTY_ANT);
			break;
		default:
			break;
		}
		// execute recursively
		if (firstChild != null) {
			firstChild.postConfigure();
		}
		if (nextSibling != null) {
			nextSibling.postConfigure();
		}
	}
	
	/**
	 * Read a file that describes attributes and objects contained within a grid.
	 * This action is performed by the Trail xholon.
	 * @param fileName Name of the grid description file.
	 */
	protected void readGridFile(String fileName)
	{
	  MiscIoGwt gridIn = new MiscIoGwt();
		boolean rc = gridIn.openInputSync(GWT.getHostPageBaseURL() + fileName);
		XhAntTrail rowNode = (XhAntTrail)getXPath().evaluate("ancestor::Structure/Grid/Row", this);
		XhAntTrail node = (XhAntTrail)getXPath().evaluate("GridCell", rowNode);
		String rowIn;
		if (rc) {
			rowIn = gridIn.readLine(); // first line contains xy size of grid; discard
			rowIn = gridIn.readLine();
			while (rowIn != null) { // keep reading until last line is read
				for (int ix = 0; ix < rowIn.length(); ix++) {
					switch (rowIn.charAt(ix)) {
					case '#': // trail with food
						node.val = VAL_FOOD;
						break;
					case '.': // trail with no food
						node.val = VAL_TRAIL;
						break;
					case ' ': // no trail and no food
						node.val = VAL_EMPTY;
						break;
					default:
						break;
					}
					node = (XhAntTrail)node.port[P_EAST];
					if (node == null) {
						break; // exit for loop
					}
				}
				rowNode = (XhAntTrail)rowNode.getNextSibling();
				if (rowNode == null) {
					break; // exit while loop
				}
				node = (XhAntTrail)getXPath().evaluate("GridCell", rowNode);
				rowIn = gridIn.readLine();
			}
		}
		else {
			// error
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getVal()
	 */
	public double getVal()
	{ return this.val; }
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setVal(double)
	 */
	public void setVal(int val)
	{ this.val = val; }
	
	/*
	 * @see org.primordion.xholon.base.IXholon#incVal(double)
	 */
	public void incVal(double incAmount)
	{ this.val += incAmount; }
	
	/*
	 * @see org.primordion.xholon.base.IXholon#decVal(double)
	 */
	public void decVal(double decAmount)
	{ this.val += decAmount; }
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setRoleName(java.lang.String)
	 */
	public void setRoleName(String roleName)
	{ this.roleName = roleName; }
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getRoleName()
	 */
	public String getRoleName()
	{ return roleName; }
	
	/*
	 * @see org.primordion.xholon.base.IXholon#act()
	 */
	public void act()
	{
		switch(xhc.getId()) {
		case AntCE:
			// behavior as specified by Behavior xholon
			performVoidActivity(port[P_BEHAVIOR].getFirstChild()); // get PfWrapper
			break;
		default:
			break;
		}
		super.act();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#performVoidActivity(org.primordion.xholon.base.IXholon)
	 */
	public void performVoidActivity(IXholon activity)
	{
		switch(activity.getXhcId()) {
		case PfWrapperCE:     wrapper(activity);     break;
		case PfIfFoodAheadCE: ifFoodAhead(activity); break;
		case PfProgn2CE:      progn2(activity);      break;
		case PfProgn3CE:      progn3(activity);      break;
		case PfProgn4CE:      progn4(activity);      break;
		case PfProgn5CE:      progn5(activity);      break;
		case PfProgn7CE:      progn7(activity);      break;
		case PfLeftCE:        left();                break;
		case PfRightCE:       right();               break;
		case PfMoveCE:        move();                break;
		default:
			System.out.println("XhAntTrail: behavior for activity " + activity.getXhcName() + " not yet implemented");
			break;
		}
	}

	/**
	 * Primitive Function - Wrapper.
	 * @param activity The IXholon activity node.
	 */
	protected void wrapper(IXholon activity)
	{
		performVoidActivity(activity.getFirstChild());
	}
	
	/**
	 * Primitive Function - Is there food in the next grid cell?
	 * @param actvity The IXholon activity node.
	 */
	protected void ifFoodAhead(IXholon activity)
	{
		if (port[P_GRID].getPort(direction).getVal() == VAL_FOOD) {
			performVoidActivity(activity.getFirstChild());
		}
		else {
			performVoidActivity(activity.getFirstChild().getNextSibling());
		}
	}

	/**
	 * Primitive Function - Process two grouped statements.
	 * @param actvity The IXholon activity node.
	 */
	protected void progn2(IXholon activity)
	{
		IXholon activityNode;
		activityNode = activity.getFirstChild();
		performVoidActivity(activityNode);
		activityNode = activityNode.getNextSibling();
		performVoidActivity(activityNode);
	}

	/**
	 * Primitive Function - Process three grouped statements.
	 * @param actvity The IXholon activity node.
	 */
	protected void progn3(IXholon activity)
	{
		IXholon activityNode;
		activityNode = activity.getFirstChild();
		performVoidActivity(activityNode);
		activityNode = activityNode.getNextSibling();
		performVoidActivity(activityNode);
		activityNode = activityNode.getNextSibling();
		performVoidActivity(activityNode);
	}

	/**
	 * Primitive Function - Process four grouped statements.
	 * @param actvity The IXholon activity node.
	 */
	protected void progn4(IXholon activity)
	{
		IXholon activityNode;
		activityNode = activity.getFirstChild();
		performVoidActivity(activityNode);
		activityNode = activityNode.getNextSibling();
		performVoidActivity(activityNode);
		activityNode = activityNode.getNextSibling();
		performVoidActivity(activityNode);
		activityNode = activityNode.getNextSibling();
		performVoidActivity(activityNode);
	}

	/**
	 * Primitive Function - Process five grouped statements.
	 * @param actvity The IXholon activity node.
	 */
	protected void progn5(IXholon activity)
	{
		IXholon activityNode;
		activityNode = activity.getFirstChild();
		performVoidActivity(activityNode);
		activityNode = activityNode.getNextSibling();
		performVoidActivity(activityNode);
		activityNode = activityNode.getNextSibling();
		performVoidActivity(activityNode);
		activityNode = activityNode.getNextSibling();
		performVoidActivity(activityNode);
		activityNode = activityNode.getNextSibling();
		performVoidActivity(activityNode);
	}

	/**
	 * Primitive Function - Process seven grouped statements.
	 * @param actvity The IXholon activity node.
	 */
	protected void progn7(IXholon activity)
	{
		IXholon activityNode;
		activityNode = activity.getFirstChild();
		performVoidActivity(activityNode);
		activityNode = activityNode.getNextSibling();
		performVoidActivity(activityNode);
		activityNode = activityNode.getNextSibling();
		performVoidActivity(activityNode);
		activityNode = activityNode.getNextSibling();
		performVoidActivity(activityNode);
		activityNode = activityNode.getNextSibling();
		performVoidActivity(activityNode);
		activityNode = activityNode.getNextSibling();
		performVoidActivity(activityNode);
		activityNode = activityNode.getNextSibling();
		performVoidActivity(activityNode);
	}

	/**
	 * Primitive Function - Turn left.
	 */
	protected void left()
	{
		switch( direction ) {
		case P_NORTH: direction = P_WEST; break;
		case P_EAST: direction = P_NORTH; break;
		case P_SOUTH: direction = P_EAST; break;
		case P_WEST: direction = P_SOUTH; break;
		default: break;
		}
		val++; // keep track of how many moves the ant makes
	}

	/**
	 * Primitive Function - Turn right.
	 */
	protected void right()
	{
		switch( direction ) {
		case P_NORTH: direction = P_EAST; break;
		case P_EAST: direction = P_SOUTH; break;
		case P_SOUTH: direction = P_WEST; break;
		case P_WEST: direction = P_NORTH; break;
		default: break;
		}
		val++; // keep track of how many moves the ant makes
	}

	/**
	 * Primitive Function - Move in the current direction.
	 */
	protected void move()
	{
		switch ((int)port[P_GRID].getVal()) {
		case VAL_EMPTY_ANT: port[P_GRID].setVal(VAL_EMPTY_VISITED); break;
		case VAL_TRAIL_ANT: port[P_GRID].setVal(VAL_TRAIL_VISITED); break;
		case VAL_FOOD_ANT:  port[P_GRID].setVal(VAL_FOOD_VISITED); break;
		default: break;
		}
		
		port[P_GRID] = port[P_GRID].getPort(direction);
		
		switch ((int)port[P_GRID].getVal()) {
		case VAL_EMPTY:
		case VAL_EMPTY_VISITED:
			port[P_GRID].setVal(VAL_EMPTY_ANT);
			break;
		case VAL_TRAIL:
		case VAL_TRAIL_VISITED:
			port[P_GRID].setVal(VAL_TRAIL_ANT);
			break;
		case VAL_FOOD:
			port[P_FOOD].incVal(1.0); // accumulate food
			port[P_GRID].setVal(VAL_FOOD_ANT);
			break;
		case VAL_FOOD_VISITED:
			port[P_GRID].setVal(VAL_FOOD_ANT);
			break;
		default: break;
		}
		val++; // keep track of how many moves the ant makes
	}


	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String outStr = getName();
		IXholon node;
		switch(xhc.getId()) {
		case AntCE:
			outStr += "[direction:" + directionName[direction] + " moves: " + val + "]";
			break;
		case EcjAntTrailSystemCE:
			node = getFirstChild().getNextSibling();
			outStr += "[sizeOfPfTree:" + (node.treeSize() - 1) + "]";
			break;
		case FoodCE:
			outStr += " [val:" + val + "]";
			break;
		case GridCellCE:
			outStr += " [val:" + val + "]";
			break;
		default:
			break;
		} // end switch
		if ((port != null) && (port.length > 0)) {
			outStr += " [";
			for (int i = 0; i < port.length; i++) {
				if (port[i] != null) {
					outStr += " port:" + port[i].getName();
				}
			}
			outStr += "]";
		}
		return outStr;
	}
}

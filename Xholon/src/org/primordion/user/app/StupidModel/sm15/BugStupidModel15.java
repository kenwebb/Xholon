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

package org.primordion.user.app.StupidModel.sm15;

//import java.util.Collections;
import java.util.Vector;
import org.primordion.xholon.base.AbstractGrid;
import org.primordion.xholon.base.IGrid;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.util.MiscRandom;
import org.primordion.xholon.util.XholonCollections;
//import org.primordion.xholon.util.Random;

/**
	StupidModel15 application - Xholon Java
	<p>Xholon 0.5 http://www.primordion.com/Xholon</p>
*/
public class BugStupidModel15 extends XhStupidModel15 implements Comparable {
	
	public static double maxConsumptionRate = 1.0;
	
	private double bugSize = 1.0; // the bug's default size
	
	public static double survivalProbability = 0.95;
	
	public static double initialBugSizeMean = 0.11;
	public static double initialBugSizeSD = 0.033;
	
	// Ports
	public static final int P_Statistics = 0;
	public static final int P_Scheduler = 1;
	
	// Signals
	public static final int SIG_SCHEDULE_ME = 100;
	
	/**
	 * Get the current bug size.
	 * @return The current bug size.
	 */
	public double getBugSize() {
		return bugSize;
	}
	
	/**
	 * Set the size of this bug.
	 * @param bugSize The new size.
	 */
	public void setBugSize(double bugSize) {
		this.bugSize = bugSize;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal()
	 */
	public double getVal() {
		return getBugSize();
	}
	
	/*
	 * @see org.primordion.user.app.StupidModel.XhStupidModel14#postConfigure()
	 */
	public void postConfigure()
	{
		// set initial bug size
		//double bsize = Random.normal.nextDouble();
		// GWT doesn't have access to the colt library cern.jet.random, so use uniform rather than normal distribution
	  double bsize = MiscRandom.getRandomDouble(0.0, 1.0);
		if (bsize < 0.0) {
			bsize = 0.0;
		}
		setBugSize(bsize);
		super.postConfigure();
	}
	
	/*
	 * @see org.primordion.user.app.StupidModel.XhStupidModel3#act()
	 */
	public void act()
	{
		if (bugSize >= 10.0) {
			reproduce();
		}
		else {
			move(); // first bug action
			grow(); // second bug action
			if (MiscRandom.getRandomDouble(0.0, 1.0) > survivalProbability) {
				die();
			}
			else {
				// schedule me to run the next time step (Yeh, I'm still alive!)
				port[P_Scheduler].sendMessage(SIG_SCHEDULE_ME, null, this);
			}
		}
	}
	
	/**
	 * Move to the best location within 4 cells of the current location.
	 */
	protected void move()
	{
		// get all possible destinations within a radius of 4
		Vector v = ((HabitatCellStupidModel15)parentNode).inRadius(4);
		XholonCollections.shuffle(v);
		// locate the best destination, the one with the highest food availability
		IXholon bestDestination = parentNode;
		double highestFoodAvailability = 0.0;
		for (int i = 0; i < v.size(); i++) {
			IXholon destination = (IXholon)v.elementAt(i);
			double foodAvailability = destination.getVal();
			if (foodAvailability > highestFoodAvailability) {
				bestDestination = destination;
				highestFoodAvailability = foodAvailability;
			}
		}
		// move to the best destination
		if (bestDestination != parentNode) {
			removeChild(); // detach self from parent and sibling links
			appendChild(bestDestination); // insert as the last child of the destination grid cell
		}
	}
	
	/**
	 * Grow.
	 */
	protected void grow()
	{
		double foodAvailability = parentNode.getVal();
		double foodConsumption = min(maxConsumptionRate, foodAvailability); // = bug growth
		bugSize += foodConsumption;
		parentNode.setVal(foodAvailability - foodConsumption);
		port[P_Statistics].setVal(bugSize);
	}
	
	/**
	 * Reproduce.
	 */
	protected void reproduce()
	{
		String myImplName = getClass().getName();
		String myXhcName = getXhcName();
		IXholon myParent = parentNode;
		die();
		for (int i = 0; i < 5; i++) {
			BugStupidModel15 newBug = (BugStupidModel15)myParent.appendChild(myXhcName, null, myImplName);
			newBug.setBugSize(0.0);
			newBug.setPorts();
			newBug.configure();
			if (newBug.moveNew()) {
				newBug.port[P_Scheduler].sendMessage(SIG_SCHEDULE_ME, null, newBug);
			}
			else {
				newBug.die();
			}
		}
	}
	
	/**
	 * Move new bug to another location within 3 cells of the current location.
	 */
	protected boolean moveNew()
	{
		// get a randomly chosen grid location within +/-4 cells of current location, in both X and Y directions
		boolean foundNewLocation = false;
		int count = 0;
		while ((!foundNewLocation) && (count < 5)) {
			int moveX = MiscRandom.getRandomInt(0, 9) - 3;
			int moveY = MiscRandom.getRandomInt(0, 9) - 3;
			int portX = moveX > 0 ? IGrid.P_EAST : IGrid.P_WEST;
			int portY = moveY > 0 ? IGrid.P_NORTH : IGrid.P_SOUTH;
			int moveIncX = moveX > 0 ? -1 : 1;
			int moveIncY = moveY > 0 ? -1 : 1;
			//System.out.println(moveX + " " + moveY);
			count++;
			// locate the intended destination
			AbstractGrid destination = (AbstractGrid)parentNode;
			while ((destination != null) && (moveX != 0)) {
				destination = (AbstractGrid)destination.port[portX];
				moveX += moveIncX;
			}
			while ((destination != null) && (moveY != 0)) {
				destination = (AbstractGrid)destination.port[portY];
				moveY += moveIncY;
			}
			// if no one is already at the intended destination, then move there
			if ((destination != null) && (!destination.hasChildNodes())) {
				removeChild(); // detach self from parent and sibling links
				appendChild(destination); // insert as the last child of the destination grid cell
				foundNewLocation = true;
			}
		}
		if (!foundNewLocation) {
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * Die.
	 */
	protected void die()
	{
		removeChild();
		remove();
	}
	
	/**
	 * Get the smaller of two numbers.
	 * @param one A number.
	 * @param two Another number.
	 * @return The smaller of the two numbers.
	 */
	protected double min(double one, double two)
	{
		if (one < two) {return one;}
		else {return two;}
	}
	
	/*
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		// sorts in descending order
		if (bugSize < ((BugStupidModel15)o).bugSize) {return 1;}
		else if (bugSize > ((BugStupidModel15)o).bugSize) {return -1;}
		return 0;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toString()
	 */
	public String toString()
	{
		return super.toString() + " bugSize:" + getBugSize();
	}
}

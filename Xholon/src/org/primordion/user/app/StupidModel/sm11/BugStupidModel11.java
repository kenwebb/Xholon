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

package org.primordion.user.app.StupidModel.sm11;

import java.util.Vector;
import org.primordion.xholon.base.IXholon;

/**
	StupidModel11 application - Xholon Java
	<p>Xholon 0.5 http://www.primordion.com/Xholon</p>
*/
public class BugStupidModel11 extends XhStupidModel11 implements Comparable {
	
	public static double maxConsumptionRate = 1.0;
	
	private double bugSize = 1.0; // the bug's size
	
	private static double maxBugSize = 100.0; // the model should stop when the largest bug reaches this size
	
	// port
	public static int P_Statistics = 0;
	
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
	 * @see org.primordion.user.app.StupidModel.XhStupidModel3#act()
	 */
	public void act()
	{
		move(); // first bug action
		grow(); // second bug action
		if (bugSize >= maxBugSize) {
			processingComplete = true;
		}
	}
	
	/**
	 * Move to the best location within 4 cells of the current location.
	 */
	protected void move()
	{
		// get all possible destinations within a radius of 4
		Vector v = ((HabitatCellStupidModel11)parentNode).inRadius(4);
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
		if (bugSize < ((BugStupidModel11)o).bugSize) {return 1;}
		else if (bugSize > ((BugStupidModel11)o).bugSize) {return -1;}
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

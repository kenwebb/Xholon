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

import org.primordion.xholon.base.IQueue;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IGrid;
import org.primordion.xholon.base.XholonWithPorts;

/**
	StupidModel11 application - Xholon Java
	<p>Xholon 0.5 http://www.primordion.com/Xholon</p>
*/
public class XhStupidModel11 extends XholonWithPorts implements CeStupidModel11 {
	
	protected static boolean processingComplete = false;
	
	public String roleName = null;
	
	// Constructor
	public XhStupidModel11() {super();}
	
	/**
	 * Is all desired or possible processing complete?
	 * @return true or false
	 */
	public static boolean isProcessingComplete() {return processingComplete;}
	
	/**
	 * Set processing complete.
	 * @param pComplete true or false
	 */
	public static void setProcessingComplete(boolean pComplete) {processingComplete = pComplete;}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setRoleName(java.lang.String)
	 */
	public void setRoleName(String roleName) {this.roleName = roleName;}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getRoleName()
	 */
	public String getRoleName() {return roleName;}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(double)
	 */
	public void setVal(double val)
	{
		switch(getXhcId()) {
		case StatisticsCE:
			IXholon count = getFirstChild();
			IXholon minimum = count.getNextSibling();
			IXholon mean = minimum.getNextSibling();
			IXholon maximum = mean.getNextSibling();
			if (val < minimum.getVal_double()) {
				minimum.setVal(val);
			}
			if (val > maximum.getVal_double()) {
				maximum.setVal(val);
			}
			// calculate the new mean
			// newMean = (oldMean * oldN + val) / (oldN + 1)
			double n = count.getVal_double();
			mean.setVal((mean.getVal_double() * n + val) / (n + 1));
			count.incVal(1.0);
			break;
		default:
			break;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.XholonWithPorts#postConfigure()
	 */
	public void postConfigure()
	{
		switch(xhc.getId()) {
		case StupidModelCE:
			IXholon grid = getXPath().evaluate("descendant::Grid", this);
			IQueue xhParent = (IQueue)getXPath().evaluate("descendant::Bugs", this);
			// Put bugs into a scheduling Queue, so the order in which they act can be shuffled.
			xhParent.setMaxSize(xhParent.getNumChildren(false)); // initialize the Q
			IXholon bug = xhParent.getFirstChild();
			while (bug != null) {
				if (xhParent.enqueue(bug) == IQueue.Q_FULL) {
					System.out.println("XhStupidModel11 postConfigure() schedulingQ Q_FULL");
				}
				bug = bug.getNextSibling();
			}
			// And move all bugs to random positions within the grid.
			((IGrid)grid).moveXholonsToGrid(xhParent, false);
			break;
		default:
			break;
		}
		super.postConfigure();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#act()
	 */
	public void act()
	{
		switch(xhc.getId()) {
		case StatisticsCE:
			// reset the count, minimum, mean, maximum to their initial values
			IXholon count = getFirstChild();
			count.setVal(0.0);
			IXholon minimum = count.getNextSibling();
			minimum.setVal(Double.MAX_VALUE);
			IXholon mean = minimum.getNextSibling();
			mean.setVal(0.0);
			IXholon maximum = mean.getNextSibling();
			maximum.setVal(Double.MIN_VALUE);
			break;
		default:
			break;
		}
		super.act();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toString()
	 */
	public String toString() {
		String outStr = getName();
		return outStr;
	}
}

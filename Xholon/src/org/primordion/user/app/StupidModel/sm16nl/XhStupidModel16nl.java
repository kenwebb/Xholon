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

package org.primordion.user.app.StupidModel.sm16nl;

import com.google.gwt.core.client.GWT;

//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.Reader;

import org.primordion.xholon.base.IQueue;
import org.primordion.xholon.base.ITurtle;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IGrid;
import org.primordion.xholon.base.PatchOwner;
import org.primordion.xholon.base.XholonWithPorts;
//import org.primordion.xholon.util.MiscIo;
import org.primordion.xholon.util.MiscIoGwt;
//import org.primordion.xholon.util.Random;
import org.primordion.xholon.util.StringTokenizer;

/**
	StupidModel16nl application - Xholon Java
	<p>Xholon 0.5 http://www.primordion.com/Xholon</p>
*/
public class XhStupidModel16nl extends XholonWithPorts implements CeStupidModel16nl {
	
	protected static boolean processingComplete = false;
	
	public String roleName = null;
	
	public static String stupidCellData = null;
	
	// Constructor
	public XhStupidModel16nl() {super();}
	
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
			// Initialize normal distribution
			// GWT doesn't have access to the colt library cern.jet.random
			//Random.createNormal(BugStupidModel16nl.initialBugSizeMean, BugStupidModel16nl.initialBugSizeSD);
			// Get Grid
			IXholon grid = getXPath().evaluate("descendant::Grid", this);
			((PatchOwner)grid).ca(); // initialize the grid
			readGridFile(grid, stupidCellData);
			
			// Get Bugs
			IQueue xhParent = (IQueue)getXPath().evaluate("descendant::Bugs", this);
			// Put bugs into a scheduling Queue, so the order in which they act can be shuffled.
			xhParent.setMaxSize(xhParent.getNumChildren(false) * 50); // initialize the Q
			ITurtle bug = (ITurtle)xhParent.getFirstChild();
			while (bug != null) {
				if (xhParent.enqueue(bug) == IQueue.Q_FULL) {
					System.out.println("XhStupidModel16nl bug postConfigure() schedulingQ Q_FULL");
				}
				bug = (ITurtle)bug.getNextSibling();
			}
			// And move all bugs to random positions within the grid.
			((IGrid)grid).moveXholonsToGrid(xhParent, false);
			// initialize turtle characteristics of the bugs
			for (int i = 0; i < xhParent.getSize(); i++) {
				bug = (ITurtle)xhParent.dequeue();
				bug.setXcor(bug.getPxcor());
				bug.setYcor(bug.getPycor());
				bug.setHeading(0.0);
				//bug.setColor(color);
				//bug.setPenMode(penMode);
				xhParent.enqueue(bug); // put it back on the queue
			}
			
			// Get Predators
			xhParent = (IQueue)getXPath().evaluate("descendant::Predators", this);
			// Put predators into a scheduling Queue.
			xhParent.setMaxSize(xhParent.getNumChildren(false)); // initialize the Q
			ITurtle predator = (ITurtle)xhParent.getFirstChild();
			while (predator != null) {
				if (xhParent.enqueue(predator) == IQueue.Q_FULL) {
					System.out.println("XhStupidModel16nl predator postConfigure() schedulingQ Q_FULL");
				}
				predator = (ITurtle)predator.getNextSibling();
			}
			// And move all predators to random positions within the grid.
			((IGrid)grid).moveXholonsToGrid(xhParent, false);
			// initialize turtle characteristics of the predators
			for (int i = 0; i < xhParent.getSize(); i++) {
				predator = (ITurtle)xhParent.dequeue();
				predator.setXcor(predator.getPxcor());
				predator.setYcor(predator.getPycor());
				predator.setHeading(0.0);
				//predator.setColor(color);
				//predator.setPenMode(penMode);
				xhParent.enqueue(predator); // put it back on the queue
			}
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
	
	/**
	 * Read a file that defines the food production rates for each habitat cell.
	 * This action is performed by the Grid xholon.
	 * @param fileName Name of the grid description file.
	 */
	/*protected void readGridFile(IXholon grid, String fileName)
	{
		Reader gridIn = MiscIo.openInputFile(fileName);
		IXholon colNode = grid.getFirstChild().getLastChild();
		IXholon node = colNode;
		if (gridIn.getClass() == BufferedReader.class) {
			try {
				String rowIn = ((BufferedReader)gridIn).readLine(); // first three lines contain header info; discard
				rowIn = ((BufferedReader)gridIn).readLine();
				rowIn = ((BufferedReader)gridIn).readLine();
				rowIn = ((BufferedReader)gridIn).readLine();
				while (rowIn != null) { // keep reading until last line is read
					StringTokenizer st = new StringTokenizer(rowIn);
					Integer.parseInt(st.nextToken()); // x
					Integer.parseInt(st.nextToken()); // y
					((HabitatCellStupidModel16nl)node).foodProductionRate = Double.parseDouble(st.nextToken());
					//System.out.println(x + ":" + y + ":" + node.foodProduction);
					rowIn = ((BufferedReader)gridIn).readLine();
					node = (HabitatCellStupidModel16nl)node.getPort(IGrid.P_SOUTH);
					if (node == null) {
						if (colNode == null) {
							break; // end of grid
						}
						colNode = colNode.getPort(IGrid.P_WEST);
						node = colNode;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			// error
		}
		MiscIo.closeInputFile(gridIn);
	}*/
	
  protected void readGridFile(IXholon grid, String fileName) {
	  MiscIoGwt gridIn = new MiscIoGwt();
		boolean rc = gridIn.openInputSync(GWT.getHostPageBaseURL()
		  + "config/StupidModel/Stupid_Cell.Data");
		IXholon colNode = grid.getFirstChild().getLastChild();
		IXholon node = colNode;
		if (rc) {
		  String rowIn = gridIn.readLine(); // first three lines contain header info; discard
		  rowIn = gridIn.readLine();
		  rowIn = gridIn.readLine();
		  rowIn = gridIn.readLine();
		  while (rowIn != null) { // keep reading until last line is read
				StringTokenizer st = new StringTokenizer(rowIn);
				Integer.parseInt(st.nextToken()); // x
				Integer.parseInt(st.nextToken()); // y
				((HabitatCellStupidModel16nl)node).foodProductionRate = Double.parseDouble(st.nextToken());
				//System.out.println(x + ":" + y + ":" + node.foodProduction);
				rowIn = gridIn.readLine();
				node = (HabitatCellStupidModel16nl)node.getPort(IGrid.P_SOUTH);
				if (node == null) {
					if (colNode == null) {
						break; // end of grid
					}
					colNode = colNode.getPort(IGrid.P_WEST);
					node = colNode;
				}
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toString()
	 */
	public String toString() {
		String outStr = getName();
		return outStr;
	}
}

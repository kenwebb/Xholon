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

import com.google.gwt.core.client.GWT;

//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.Reader;
import java.util.Vector;
import org.primordion.xholon.base.GridEntity;
import org.primordion.xholon.base.IGrid;
import org.primordion.xholon.base.IXholon;
//import org.primordion.xholon.util.MiscIo;
import org.primordion.xholon.util.MiscIoGwt;
import org.primordion.xholon.util.StringTokenizer;

/**
	StupidModel15 application - Xholon Java
	<p>Xholon 0.5 http://www.primordion.com/Xholon</p>
*/
public class HabitatCellStupidModel15 extends GridEntity implements CeStupidModel15 {
	
	//public static double maxFoodProductionRate = 0.01;
	
	private double foodAvailability = 0.0;
	public double foodProductionRate = 0.0;
	
	public static String stupidCellData = null;
	
	/**
	 * Return a filtered list of all neighbors within the specified radius.
	 * This works for a torus where each cell has 8 neighbors.
	 * @param radius Distance from the current cell. The current cell is a radius of 0 from itself.
	 * @return A vector containing zero or more gridCells.
	 */
	public Vector inRadius(int radius)
	{
		int side = radius + radius + 1; // size (in gridCells) of one side of the square
		int area = side * side; // total area (in gridCells) of the square
		Vector v = new Vector(area);
		IXholon node = this;
		IXholon newNode;
		int i, j;
		for (i = 0; i < radius; i++) { // locate NORTHWEST corner of the square
			newNode = node.getPort(IGrid.P_NORTHWEST);
			if (newNode == null) {break;}
			node = newNode;
		}
		IXholon startOfRow = node; // start of the current row
		for (i = 0; i < side; i++) {
			for (j = 0; j < side; j++) {
				if (((HabitatCellStupidModel15)node).filter()) {
					v.addElement(node);
				}
				newNode = node.getPort(IGrid.P_EAST);
				if (newNode == null) {break;}
				node = newNode;
			}
			newNode = startOfRow.getPort(IGrid.P_SOUTH);
			if (newNode == null) {break;}
			startOfRow = newNode;
			node = startOfRow;
		}
		return v;
	}
	
	/**
	 * Filter whether or not this node should be included in the inRadius() vector.
	 * @return true or false
	 */
	private boolean filter()
	{
		if (hasChildNodes()) {return false;}
		else {return true;}
	}
	
	/*
	 * @see org.primordion.xholon.base.AbstractGrid#postConfigure()
	 */
	public void postConfigure()
	{
		switch(getXhcId()) {
		case GridCE:
			// read stupid cell data file
			readGridFile(stupidCellData);
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
		switch(getXhcId()) {
		case HabitatCellCE:
			//double foodProduction = Misc.getRandomDouble(0, maxFoodProductionRate);
			foodAvailability += foodProductionRate;
			// DO NOT directly execute children, which are of type "Bug"
			if (nextSibling != null) {
				nextSibling.act();
			}
			break;
		default:
			super.act();
			break;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal()
	 */
	public double getVal()
	{
		return foodAvailability;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(double)
	 */
	public void setVal(double val)
	{
		foodAvailability = val;
	}
	
	/**
	 * Read a file that defines the food production rates for each habitat cell.
	 * This action is performed by the Grid xholon.
	 * @param fileName Name of the grid description file.
	 */
	/*protected void readGridFile(String fileName)
	{
		Reader gridIn = MiscIo.openInputFile(fileName);
		IXholon colNode = getFirstChild().getLastChild();
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
					((HabitatCellStupidModel15)node).foodProductionRate = Double.parseDouble(st.nextToken());
					//System.out.println(x + ":" + y + ":" + node.foodProduction);
					rowIn = ((BufferedReader)gridIn).readLine();
					node = (HabitatCellStupidModel15)node.getPort(P_SOUTH);
					if (node == null) {
						if (colNode == null) {
							break; // end of grid
						}
						colNode = colNode.getPort(P_WEST);
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
	
	protected void readGridFile(String fileName) {
	  MiscIoGwt gridIn = new MiscIoGwt();
		boolean rc = gridIn.openInputSync(GWT.getHostPageBaseURL()
		  + "config/StupidModel/Stupid_Cell.Data");
		IXholon colNode = getFirstChild().getLastChild();
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
				((HabitatCellStupidModel15)node).foodProductionRate = Double.parseDouble(st.nextToken());
				//System.out.println(x + ":" + y + ":" + node.foodProduction);
				rowIn = gridIn.readLine();
				node = (HabitatCellStupidModel15)node.getPort(P_SOUTH);
				if (node == null) {
					if (colNode == null) {
						break; // end of grid
					}
					colNode = colNode.getPort(P_WEST);
					node = colNode;
				}
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.GridEntity#toString()
	 */
	public String toString()
	{
		return super.toString()
			+ " foodAvailability:" + foodAvailability
			+ "foodProductionRate:" + foodProductionRate;
	}
}

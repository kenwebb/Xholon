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

package org.primordion.xholon.base;

import org.primordion.xholon.exception.XholonConfigurationException;

/**
 * PatchOwner owns a collection of Logo Patches. It's the parent of the first row in the grid.
 * It knows how to create turtles within the patches it owns,
 * and can also clear its patches and turtles back to their default values.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on March 1, 2007)
 * <p>
 * TODO add May 15, 2007):
 * diffuse
 * diffuse4
 * patch
 * </p>
 */
public class PatchOwner extends AbstractGrid implements IXholon {
	private static final long serialVersionUID = 7575833950416416213L;
	
	// PatchOwner variables
	protected static final int POVAR_NOTYETSET = -1;
	protected int maxPxcor = POVAR_NOTYETSET;
	protected int maxPycor = POVAR_NOTYETSET;
	protected int minPxcor = POVAR_NOTYETSET;
	protected int minPycor = POVAR_NOTYETSET;
	
	// initial values
	protected int nextColor = 135;
	protected double nextHeading = 345.0;
	
	/**
	 * Get next color.
	 * @return A color value.
	 */
	protected int getNextColor()
	{
		if (nextColor >= 130) {
			nextColor = 5;
		}
		else {
			nextColor += 10;
		}
		return nextColor;
	}
	
	/**
	 * Get next heading.
	 * @return A heading.
	 */
	protected double getNextHeading()
	{
		nextHeading += 15.0;
		if (nextHeading >= 360.0) {
			nextHeading -= 360.0;
		}
		return nextHeading;
	}
	
	/**
	 * Clear patches, turtles, etc.
	 */
	public void clearAll()
	{
		ca();
	}
	
	/**
	 * Clear patches, turtles, etc.
	 * This method implements the same functionality as clearAll().
	 */
	public void ca()
	{
		cd();
		cp();
		ct();
	}
	
	/**
	 * Clear the drawing surface.
	 */
	public void clearDrawing()
	{
		// TODO implement	
	}
	
	/**
	 * Clear the drawing surface.
	 * This method implements the same functionality as clearDrawing().
	 */
	public void cd()
	{
		// TODO implement
	}
	
	/**
	 * Set all patch variables to their initial values.
	 */
	public void clearPatches()
	{
		cp();
	}
	
	/**
	 * Set all patch variables to their initial values.
	 * This method implements the same functionality as clearPatches().
	 */
	public void cp()
	{
		//int rowNum = 0;
		int rowNum = getNumChildren(false) / 2;
		IXholon row = getFirstChild();
		while (row != null) {
			//int patchNum = 0;
			int patchNum = -(row.getNumChildren(false) / 2);
			IPatch patch = (IPatch)row.getFirstChild();
			while (patch != null) {
				patch.setPcolor(ITurtlePatchColor.TPCOLOR_DEFAULT);
				patch.setPxcor(patchNum);
				patch.setPycor(rowNum);
				patchNum++;
				patch = (IPatch)patch.getNextSibling();
			}
			rowNum--;
			row = row.getNextSibling();
		}
	}
	
	/**
     * Kill all turtles within the scope of this PatchOwner.
     */
    public void clearTurtles()
    {
    	// TODO implement
    }
    
    /**
     * Kill all turtles within the scope of this PatchOwner.
     * This method implements the same functionality as clearTurtles().
     */
    public void ct()
    {
    	// TODO implement
    }
    
    /**
	 * Create a given number of new turtles in the center of the grid.
	 * @param numTurtles The number of new turtles to create.
	 */
    public void createTurtles(int numTurtles)
    {
    	crt(numTurtles);
    }
    
    /**
	 * Create a given number of new turtles in the center of the grid.
	 * This method implements the same functionality as createTurtles().
	 * @param numTurtles The number of new turtles to create.
	 */
	public void crt(int numTurtles)
	{
		// find center of grid
		IXholon centerPatch = getNthChild(getNumChildren(false) / 2, false); // get the center row
		centerPatch = centerPatch.getNthChild(centerPatch.getNumChildren(false) / 2, false); // get the center col
		String implName = getClassNode("Turtle").getImplName();
		// create turtles in center
		for (int i = 0; i < numTurtles; i++) {
			ITurtle turtle;
			try {
				if (implName == null) { // this is a generic turtle
					turtle = (ITurtle)getFactory().getXholonNode("org.primordion.xholon.base.Turtle");
				}
				else { // this is a specialized implementation of turtle, but NOT a "breed"
					turtle = (ITurtle)getFactory().getXholonNode(implName);
				}
			} catch (XholonConfigurationException e) {
				logger.error(e.getMessage(), e.getCause());
				return;
			}
			turtle.setId(getNextId());
			if (centerPatch.hasChildNodes()) {
				turtle.setParentSiblingLinks(centerPatch.getLastChild());
			}
			else {
				turtle.setParentChildLinks( centerPatch );
			}
			turtle.setXhc(getClassNode("Turtle"));
			turtle.setxy(0.0, 0.0);
			turtle.setHeading(getNextHeading());
			turtle.setTColor(getNextColor());
			turtle.pu();
			turtle.initWhenMoved(ITurtle.WHENMOVED_INIT);
		}
	}
	
	/**
	 * Create a given number of new turtles in the center of the grid, of a given breed.
	 * @param numTurtles The number of new turtles to create.
	 * @param breed The breed of turtle to create.
	 */
	public void createTurtles(int numTurtles, String breed)
    {
    	crt(numTurtles, breed);
    }
	
    /**
	 * Create a given number of new turtles in the center of the grid, of a given breed.
	 * This method implements the same functionality as createTurtles(numTurtles, breed).
	 * @param numTurtles The number of new turtles to create.
	 * @param breed The breed of turtle to create.
	 */
	public void crt(int numTurtles, String breed)
	{
		// find center of grid
		IXholon centerPatch = getNthChild(getNumChildren(false) / 2, false); // get the center row
		centerPatch = centerPatch.getNthChild(centerPatch.getNumChildren(false) / 2, false); // get the center col
		IXholonClass xhcNode = getClassNode(breed);
		if (xhcNode == null) {
			System.err.println("Unable to create turtles of breed " + breed);
			return;
		}
		String implName = xhcNode.getImplName();
		
		// move up the inheritance hierarchy looking for first parent with a non-null implName
		IXholonClass xhcSearchNode = xhcNode;
		while ((!xhcSearchNode.isRootNode()) && (implName == null)) {
			xhcSearchNode = (IXholonClass)xhcSearchNode.getParentNode();
			implName = xhcSearchNode.getImplName();
		}
		
		// create turtles in center
		for (int i = 0; i < numTurtles; i++) {
			ITurtle turtle;
			try {
				if (implName == null) { // this is a generic turtle
					turtle = (ITurtle)getFactory().getXholonNode("org.primordion.xholon.base.Turtle");
				}
				else { // this is a specialized implementation of turtle
					turtle = (ITurtle)getFactory().getXholonNode(implName);
				}
			} catch (XholonConfigurationException e) {
				logger.error(e.getMessage(), e.getCause());
				return;
			}
			turtle.setId(getNextId());
			if (centerPatch.hasChildNodes()) {
				turtle.setParentSiblingLinks(centerPatch.getLastChild());
			}
			else {
				turtle.setParentChildLinks( centerPatch );
			}
			turtle.setXhc(xhcNode);
			turtle.setxy(0.0, 0.0);
			turtle.setHeading(getNextHeading());
			turtle.setTColor(getNextColor());
			turtle.pu();
			turtle.initWhenMoved(ITurtle.WHENMOVED_INIT);
		}
	}
	
	/**
	 * Asks each patch to share the current value of a specified variable with all of its neighbors.
	 * @param patchVariable 
	 * @param number 
	 */
	public void diffuse(String patchVariable, double number)
	{
		// TODO implement
	}
	
	/**
	 * Asks each patch to share the current value of a specified variable with all of its neighbors.
	 * @param patchVariable 
	 * @param number 
	 */
	public void diffuse4(String patchVariable, double number)
	{
		// TODO implement
	}
	
	/**
	 * Return the patch with the specified absolute coordinates.
	 * @param pxcor An x coordinate.
	 * @param pycor A y coordinate.
	 * @return A patch instance, or null.
	 */
	public IPatch patch(int pxcor, int pycor)
	{
		// validate the coordinate range
		if ((pxcor < getMinPxcor()) || (pxcor > getMaxPxcor())) {return null;}
		if ((pycor < getMinPycor()) || (pycor > getMaxPycor())) {return null;}
		// get the patch with the lowest x and y coordinate.
		IXholon row = getLastChild();
		IPatch patch = (IPatch)row.getFirstChild();
		// find the patch with the specified y coordinate
		while (pycor > patch.getPycor()) {
			patch = (IPatch)patch.getPort(P_NORTH);
		}
		// find the patch with the specified x coordinate
		while (pxcor > patch.getPxcor()) {
			patch = (IPatch)patch.getPort(P_EAST);
		}
		println(patch.getPxcor() + " " + patch.getPycor());
		return patch;
	}
	
	
	// getters and setters
	
	/**
	 * Return the maximum Patch x coordinate.
	 * @return A Patch coordinate.
	 */
	public int getMaxPxcor()
	{
		if (maxPxcor == POVAR_NOTYETSET) {
			// get pxcor of the last patch in the first row
			IXholon row = getFirstChild();
			IPatch patch = (IPatch)row.getLastChild();
			maxPxcor = patch.getPxcor();
		}
		return maxPxcor;
	}
	
	/**
	 * Return the maximum Patch y coordinate.
	 * @return A Patch coordinate.
	 */
	public int getMaxPycor()
	{
		if (maxPycor == POVAR_NOTYETSET) {
			// get pycor of the first patch in the first row
			IXholon row = getFirstChild();
			IPatch patch = (IPatch)row.getFirstChild();
			maxPycor = patch.getPycor();
		}
		return maxPycor;
	}
	
	/**
	 * Return the minimum Patch x coordinate.
	 * @return A Patch coordinate.
	 */
	public int getMinPxcor()
	{
		if (minPxcor == POVAR_NOTYETSET) {
			// get pxcor of the first patch in the first row
			IXholon row = getFirstChild();
			IPatch patch = (IPatch)row.getFirstChild();
			minPxcor = patch.getPxcor();
		}
		return minPxcor;
	}

	/**
	 * Return the minimum Patch y coordinate.
	 * @return A Patch coordinate.
	 */
	public int getMinPycor()
	{
		if (minPycor == POVAR_NOTYETSET) {
			// get pycor of the first patch in the last row
			IXholon row = getLastChild();
			IPatch patch = (IPatch)row.getFirstChild();
			minPycor = patch.getPycor();
		}
		return minPycor;
	}
	
	/**
	 * Return the width of the world (the grid width).
	 * @return The width in Patch units.
	 */
	public int getWorldWidth()
	{
		return getMaxPxcor() - getMinPxcor() + 1;
	}
	
	/**
	 * Return the height of the world (the grid height).
	 * @return The height in Patch units.
	 */
	public int getWorldHeight()
	{
		return getMaxPycor() - getMinPycor() + 1;
	}
	
	/**
	 * Return the aggregator for the specified turtle or patch.
	 * @param turtleOrPatch A turtle or patch instance.
	 * @return An Attribute node, or null.
	 * TODO this is slow
	 */
	public IAttribute getAggregator(IXholon turtleOrPatch)
	{
		IXholon node = getNextSibling();
		while (node != null) {
			if (node.getRoleName().equals(turtleOrPatch.getXhcName())) {
				return (IAttribute)node;
			}
			node = node.getNextSibling();
		}
		return null;
	}
}

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
import org.primordion.xholon.util.ClassHelper;
import org.primordion.xholon.util.MiscRandom;

/**
 * A patch is an agent in the turtle mechanism.
 * The turtle mechanism is based on Logo and NetLogo.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on February 28, 2007)
 * @see the NetLogo website http://ccl.northwestern.edu/netlogo/
 */
public class Patch extends GridEntity implements IPatch {
	private static final long serialVersionUID = -9121248805394228886L;
	
	// NetLogo built-in variables for patches:
	//   pcolor plabel plabel-color pxcor pycor
	public int pcolor; // a NetLogo color between 0 and 139
	//public String plabel; // Xholon getRoleName() getName()
	//public int plabelColor;
	public int pxcor, pycor; // x, y position in global coordinates
	
	/*
	 * @see org.primordion.xholon.base.IPatch#beep()
	 */
	public void beep()
	{
	  // not available in GWT
		//java.awt.Toolkit.getDefaultToolkit().beep();
	}
	
	/*
	 * @see org.primordion.xholon.base.IPatch#distance(org.primordion.xholon.base.IXholon)
	 */
	public double distance(IXholon turtleOrPatch)
	{
		if (ClassHelper.isAssignableFrom(Turtle.class, turtleOrPatch.getClass())) {
			return distancexy(((ITurtle)turtleOrPatch).getXcor(), ((ITurtle)turtleOrPatch).getYcor());
		}
		else { // this must be a IPatch
			return distancexy(((IPatch)turtleOrPatch).getPxcor(), ((IPatch)turtleOrPatch).getPycor());
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IPatch#distancexy(double, double)
	 */
	public double distancexy(double x, double y)
	{
		double deltaX = pxcor - x;
		double deltaY = pycor - y;
		return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	}
	
	/**
	 * This works for a torus or grid where each cell has 8 neighbors.
	 * @see org.primordion.xholon.base.IPatch#inRadius(int)
	 */
	public IAgentSet inRadius(int radius)
	{
		int side = radius + radius + 1; // size (in gridCells) of one side of the square
		int area = side * side; // total area (in gridCells) of the square
		IAgentSet as = new AgentSet(area);
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
				as.add(node);
				newNode = node.getPort(IGrid.P_EAST);
				if (newNode == null) {break;}
				node = newNode;
			}
			newNode = startOfRow.getPort(IGrid.P_SOUTH);
			if (newNode == null) {break;}
			startOfRow = newNode;
			node = startOfRow;
		}
		return as.shuffle();
	}

	/**
	 * This works for a torus or grid where each cell has 8 neighbors.
	 * @see org.primordion.xholon.base.IPatch#inRadius(int, int)
	 */
	public IAgentSet inRadius(int radius, int filterId)
	{
		int side = radius + radius + 1; // size (in gridCells) of one side of the square
		int area = side * side; // total area (in gridCells) of the square
		IAgentSet as = new AgentSet(area);
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
				if (node.performBooleanActivity(filterId)) { // filter the node
					as.add(node);
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
		return as.shuffle();
	}

	/*
	 * @see org.primordion.xholon.base.IPatch#neighbors()
	 */
    public IAgentSet neighbors()
    {
    	IAgentSet as = new AgentSet(8);
    	for (int i = 0; i < 8; i++) {
    		IXholon neighbor = getPort(i);
    		if (neighbor != null) {
    			as.add(neighbor);
    		}
    	}
    	return as.shuffle();
    }
    
    /*
     * @see org.primordion.xholon.base.IPatch#neighbors4()
     */
    public IAgentSet neighbors4()
    {
    	IAgentSet as = new AgentSet(4);
    	for (int i = 0; i < 4; i++) {
    		IXholon neighbor = getPort(i);
    		if (neighbor != null) {
    			as.add(neighbor);
    		}
    	}
    	return as.shuffle();
    }
    
    /*
     * @see org.primordion.xholon.base.IPatch#patch(int, int)
     */
    public IPatch patch(int pxcor, int pycor)
    {
    	return getPatchOwner().patch(pxcor, pycor);
    }
    
    /*
     * @see org.primordion.xholon.base.IPatch#patchAt(int, int)
     */
    public IPatch patchAt(int dx, int dy)
    {
    	IXholon node = this;
    	int i;
    	if (dx > 0) {
	    	for (i = 0; i < dx; i++) {
	    		node = node.getPort(IGrid.P_EAST);
				if (node == null) {return null;}
	    	}
    	}
    	else if (dx < 0){
    		for (i = 0; i > dx; i--) {
	    		node = node.getPort(IGrid.P_WEST);
				if (node == null) {return null;}
	    	}
    	}
    	if (dy > 0) {
	    	for (i = 0; i < dy; i++) {
	    		node = node.getPort(IGrid.P_NORTH);
				if (node == null) {return null;}
	    	}
    	}
    	else if (dy < 0){
    		for (i = 0; i > dy; i--) {
	    		node = node.getPort(IGrid.P_SOUTH);
				if (node == null) {return null;}
	    	}
    	}
    	return (IPatch)node;
    }
    
    /*
     * @see org.primordion.xholon.base.IPatch#patchAtHeadingAndDistance(double, double)
     */
    public IPatch patchAtHeadingAndDistance(double heading, double distance)
    {
    	double dx = Math.sin(Math.toRadians(heading)) * distance;
    	double dy = Math.cos(Math.toRadians(heading)) * distance;
    	return patchAt((int)dx, (int)dy);
    }
    
    /*
     * @see org.primordion.xholon.base.IPatch#sprout(org.primordion.xholon.base.IXholonClass, int, int)
     */
    public void sprout(IXholonClass xhClazz, int numTurtles, int commandId)
    {
    	// TODO implement
		String implName = xhClazz.getImplName();
		
		// move up the inheritance hierarchy looking for first parent with a non-null implName
		IXholonClass xhcSearchNode = xhClazz;
		while ((!xhcSearchNode.isRootNode()) && (implName == null)) {
			xhcSearchNode = (IXholonClass)xhcSearchNode.getParentNode();
			implName = xhcSearchNode.getImplName();
		}
		
		// create turtles in the same patch as this turtle
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
			turtle.appendChild(this);
			turtle.setXhc(xhClazz);
			turtle.setXcor(pxcor);
			turtle.setYcor(pycor);
			turtle.setHeading(MiscRandom.getRandomInt(0, 360));
			turtle.setTColor(MiscRandom.getRandomInt(0, 140));
			turtle.setPenMode(ITurtle.PENMODE_DOWN);
			turtle.initWhenMoved(ITurtle.WHENMOVED_INIT);
			if (commandId != COMMANDID_NONE) {
				turtle.performActivity(commandId);
			}
		}
    }
    
    /*
     * @see org.primordion.xholon.base.IPatch#towards(org.primordion.xholon.base.IXholon)
     */
    public double towards(IXholon turtleOrPatch)
    {
		if (ClassHelper.isAssignableFrom(Turtle.class, turtleOrPatch.getClass())) {
			return towardsxy(((ITurtle)turtleOrPatch).getXcor(), ((ITurtle)turtleOrPatch).getYcor());
		}
		else { // this must be a IPatch
			return towardsxy(((IPatch)turtleOrPatch).getPxcor(), ((ITurtle)turtleOrPatch).getPycor());
		}
    }
    
    /*
     * @see org.primordion.xholon.base.IPatch#towardsxy(double, double)
     */
    public double towardsxy(double x, double y)
    {
		double h = Math.atan2(x, y);
		return Math.toDegrees(h);
    }
    
    /*
     * @see org.primordion.xholon.base.IPatch#turtlesAt(int, int)
     */
    public IAgentSet turtlesAt(int dx, int dy)
    {
    	IPatch node = patchAt(dx, dy);
    	if (node == null) {
    		return null;
    	}
    	return node.turtlesHere();
    }
    
    /*
     * @see org.primordion.xholon.base.IPatch#turtlesHere()
     */
    public IAgentSet turtlesHere()
    {
    	if (!hasChildNodes()) {
    		return null;
    	}
    	IAgentSet as = new AgentSet();
    	IXholon node = getFirstChild();
    	while (node != null) {
    		as.add(node);
    		node = node.getNextSibling();
    	}
    	return as.shuffle();
    }
    
    /*
     * @see org.primordion.xholon.base.IPatch#turtlesOn()
     */
    public IAgentSet turtlesOn()
    {
    	return turtlesHere();
    }
    
    /*
     * @see org.primordion.xholon.base.IPatch#with(java.util.Vector)
     */
    public IAgentSet with(IAgentSet asIn, int filterId)
    {
    	if (asIn == null) {return null;}
    	IAgentSet asOut = new AgentSet();
    	for (int i = 0; i < asIn.size(); i++) {
    		IXholon node = (IXholon)asIn.get(i);
    		if (node.performBooleanActivity(filterId)) {
    			asOut.add(node);
    		}
    	}
    	return asOut.shuffle();
    }
	
	// getters and setters for all Patch variables
	
	/*
	 * @see org.primordion.xholon.base.IPatch#getPcolor()
	 */
	public int getPcolor()
	{
		return pcolor;
	}
	
	/*
	 * @see org.primordion.xholon.base.IPatch#setPcolor(int)
	 */
	public void setPcolor(int pcolor)
	{
		this.pcolor = pcolor;
	}
    
	/*
	 * @see org.primordion.xholon.base.IPatch#getPlabel()
	 */
	public String getPlabel()
	{
		// TODO is this the right thing to return?
		return getName();
	}
	
	/*
	 * @see org.primordion.xholon.base.IPatch#setPlabel(java.lang.String)
	 */
	public void setPlabel(String plabel)
	{
		// TODO is this the right thing to set?
		setRoleName(plabel);
	}
	
	/*
	 * @see org.primordion.xholon.base.IPatch#getPxcor()
	 */
	public int getPxcor()
	{
		return pxcor;
	}
	
	/*
	 * @see org.primordion.xholon.base.IPatch#setPxcor(double)
	 */
	public void setPxcor(int pxcor)
	{
		this.pxcor = pxcor;
	}
	
	/*
	 * @see org.primordion.xholon.base.IPatch#getPycor()
	 */
	public int getPycor()
	{
		return pycor;
	}
	
	/*
	 * @see org.primordion.xholon.base.IPatch#setPycor(double)
	 */
	public void setPycor(int pycor)
	{
		this.pycor = pycor;
	}
	
	/**
	 * Get the owner of this Patch.
	 * @return A PatchOwner instance.
	 */
	private PatchOwner getPatchOwner() {return (PatchOwner)getParentNode().getParentNode();}
	
	/*
	 * @see org.primordion.xholon.base.IPatch#aggregate(double)
	 */
	public void aggregate(double amount)
	{
		IAttribute agg = getPatchOwner().getAggregator(this);
		if (agg != null) {
			agg.incVal(amount);
		}
	}
	
    /*
     * @see org.primordion.xholon.base.Xholon#toString()
     */
    public String toString() {
    	String outStr = getName();
    	outStr += " pcolor: " + pcolor;
    	outStr += " pxcor: " + pxcor;
    	outStr += " pycor: " + pycor;
    	return outStr;
    }
}

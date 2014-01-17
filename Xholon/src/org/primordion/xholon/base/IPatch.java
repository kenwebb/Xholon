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

//import java.util.Vector;

/**
 * A patch is an agent in the turtle mechanism.
 * The turtle mechanism is based on Logo and NetLogo.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on February 28, 2007)
 * @see the NetLogo website http://ccl.northwestern.edu/netlogo/
 * <p>
 * TODO add (May 15 2007):
 * beep
 * inRadius
 * patch
 * patchAt
 * patchAtHeadingAndDistance
 * sprout
 * towards
 * towardsxy
 * turtlesAt
 * turtlesHere
 * turtlesOn
 * with
 * </p>
 */
public interface IPatch extends IXholon, IGrid {
	
	// default command and filter ID
	public static final int COMMANDID_NONE = -1;
	public static final int FILTERID_NONE = -1;
	
	/**
	 * Make a single short beep sound.
	 */
	public void beep();
	
	/**
	 * Return the distance between self and the specified turtle or patch.
	 * @param turtleOrPatch A turtle or patch object.
	 * @return A distance in units.
	 */
	public double distance(IXholon turtleOrPatch);
	
	/**
	 * Return the distance between yourself and the specified x, y coordinates.
	 * @param x A global x coordinate.
	 * @param y A global y coordinate.
	 * @return A distance in units.
	 */
	public double distancexy(double x, double y);
	
	/**
	 * Return a list of all neighbors (patches) within the specified radius.
	 * This works for a torus where each cell has 8 neighbors.
	 * @param radius Distance from the current patch. The current patch is a radius of 0 from itself.
	 * @return A vector containing zero or more gridCells.
	 */
	public IAgentSet inRadius(int radius);
	
	/**
	 * Return a list of all neighbors (patches) within the specified radius.
	 * This works for a torus where each cell has 8 neighbors.
	 * @param radius Distance from the current patch. The current patch is a radius of 0 from itself.
	 * @param filterId An ID that can be used by performBooleanActivity() to select a specific filter.
	 * @return A vector containing zero or more gridCells.
	 */
	public IAgentSet inRadius(int radius, int filterId);
	
    /**
     * Return the 8 surrounding neighbor patches.
     * @return A collection of IPatch instances.
     */
    public IAgentSet neighbors();
    
    /**
     * Return the 4 surrounding neighbor patches.
     * @return A collection of IPatch instances.
     */
    public IAgentSet neighbors4();
    
	/**
	 * Return the patch with the specified absolute coordinates.
	 * @param pxcor An x coordinate.
	 * @param pycor A y coordinate.
	 * @return A patch instance, or null.
	 */
    public IPatch patch(int pxcor, int pycor);
    
    /**
     * Return the single patch at the specified x and y relative distance.
     * @param dx A relative distance in the x direction, in patches.
     * @param dy A relative distance in the y direction, in patches.
     * @return A patch, or null.
     */
    public IPatch patchAt(int dx, int dy);
    
    /**
     * Return the single patch at the specified absolute heading and relative distance.
     * @param heading An absolute angle measured in degrees.
     * @param distance A distance measured in units.
     * @return A patch, or null.
     */
    public IPatch patchAtHeadingAndDistance(double heading, double distance);
    
    /**
     * Create a specified number of new turtles on the current patch.
     * @param xhClazz The XholonClass that the new turtles should be members of.
     * @param numTurtles The number of new turtles to hatch.
     * @param commandId The ID of a set of turtle commands that can be executed
     * by calling performActivity(int commandId) on each new turtle.
     */
    public void sprout(IXholonClass xhClazz, int numTurtles, int commandId);
    
	/**
	 * Return the heading between self and the specified turtle or patch.
	 * @param turtleOrPatch A turtle or patch object.
	 * @return A A heading angle in degrees.
	 */
	public double towards(IXholon turtleOrPatch);
	
	/**
	 * Return the heading between self and the specified x, y coordinates.
	 * @param x A global x coordinate.
	 * @param y A global y coordinate.
	 * @return A heading angle in degrees.
	 */
	public double towardsxy(double x, double y);
    
    /**
     * Return the turtles located at the single patch at the specified x and y relative distance.
     * @param dx A relative distance in the x direction, in patches.
     * @param dy A relative distance in the y direction, in patches.
     * @return A collection of ITurtle instances.
     */
    public IAgentSet turtlesAt(int dx, int dy);
    
    /**
     * Return the turtles located at the current patch.
     * @return A collection of ITurtle instances.
     */
    public IAgentSet turtlesHere();
    
    /**
     * Return the turtles that are on the given patch or patches,
     * or standing on the same patch as the given turtle or turtles.
     * @return A collection of ITurtle instances.
     */
    public IAgentSet turtlesOn();
    
    /**
     * Filters the input vector to produce an output vector.
     * @param vIn A collection of ITurtle instances.
     * @param filterId The ID of a turtle filter.
     * @return A filtered collection of ITurtle instances.
     */
    public IAgentSet with(IAgentSet vIn, int filterId);
	
    // getters and setters for all Patch variables
    
	/**
	 * Get the patch's pcolor.
	 * @return A pcolor value, as defined in ITurtlePatchColor.
	 */
	public int getPcolor();
	
	/**
	 * Set the patch's pcolor.
	 * NetLogo: set pcolor value
	 * @param pcolor A pcolor value, as defined in ITurtlePatchColor.
	 */
	public void setPcolor(int pcolor);
	
	/**
	 * Get the patch's label, a String that helps to identify it.
	 * @return A label.
	 */
	public String getPlabel();
	
	/**
	 * Set or change the patch's label.
	 * Warning: Use caution when changing the label.
	 * @param label
	 */
	public void setPlabel(String plabel);
	
	/**
	 * Get the patch's x coordinate.
	 * @return The pxcor value.
	 */
	public int getPxcor();
	
	/**
	 * Set the patch's x coordinate.
	 * @param xcor A pxcor value.
	 */
	public void setPxcor(int pxcor);
	
	/**
	 * Get the patch's y coordinate.
	 * @return The pycor value.
	 */
	public int getPycor();
	
	/**
	 * Set the patch's y coordinate.
	 * @param ycor A pycor value.
	 */
	public void setPycor(int pycor);
	
	/**
	 * Aggregate by keeping a count of the number of individuals of a given type of Patch.
	 * @param amount The amount to increment or decrement the current population count.
	 * The amount can be a positive (increment) or negative (decrement) number.
	 */
	public void aggregate(double amount);
}

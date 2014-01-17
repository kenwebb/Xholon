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
 * A turtle is an agent in the turtle mechanism.
 * The turtle mechanism is based on Logo and NetLogo.
 * The abbreviated version of a turtle command (fd bk rt lt) should be used instead of the unabbreviated command.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on February 24, 2007)
 * @see the NetLogo website http://ccl.northwestern.edu/netlogo/
 * <p>
 * TODO add (May 15 2007):
 * beep
 * downhill
 * downhill4
 * inCone
 * inRadius
 * patchAhead
 * patchAt
 * patchAtHeadingAndDistance
 * patchHere
 * patchLeftAndAhead
 * patchRightAndAhead
 * stamp
 * stampErase
 * turtlesAt
 * turtlesHere
 * turtlesOn
 * uphill
 * uphill4
 * with
 * </p>
 */
public interface ITurtle extends IXholon {

	// possible values of penMode
	public static final int PENMODE_UP = 1; // up
	public static final int PENMODE_DOWN = 2; // down
	public static final int PENMODE_ERASE = 3; // erase
	
	// whenMoved initial value
	public static final int WHENMOVED_INIT = -1;
	
	// default command and filter ID
	public static final int COMMANDID_NONE = -1;
	public static final int FILTERID_NONE = -1;
	
    /**
	 * Move back a given distance.
	 * @param distance A distance measured in units.
	 */
    public void back(double distance);
    
    /**
	 * Move back a given distance.
	 * This method implements the same functionality as back().
	 * @param distance A distance measured in units.
	 */
    public void bk(double distance);
    
	/**
	 * Make a single short beep sound.
	 */
    public void beep();
    
    /**
     * Can this turtle legally move the specified distance, given its current heading?
     * @param distance A distance measured in units.
     * @return true or false
     */
    public boolean canMove(double distance);
	
	/**
	 * Remove yourself permanently from the gene pool.
	 */
	public void die();
	
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
	 * "Return the turtle heading in the direction of the minimum value
	 * of the variable patch-variable, of the patches in a one-patch radius of the turtle."
	 * @return An angle between 0 and 359 degrees.
	 */
	public double downhill();
	
	/**
	 * "Return the turtle heading in the direction of the minimum value
	 * of the variable patch-variable, of the patches in a one-patch radius of the turtle."
	 * @return An angle between 0 and 359 degrees.
	 */
	public double downhill4();
	
	/**
	 * Returns the delta in the x direction if the turtle moved one unit along its current heading.
	 */
	public double dx();
	
	/**
	 * Returns the delta in the y direction if the turtle moved one unit along its current heading.
	 */
	public double dy();
	
	/**
	 * Turn to face the specified turtle or patch.
	 * @param turtleOrPatch A turtle or patch object.
	 */
	public void face(IXholon turtleOrPatch);
	
	/**
	 * Turn to face the specified x, y coordinates.
	 * @param x A global x coordinate.
	 * @param y A global y coordinate.
	 */
	public void facexy(double x, double y);
	
    /**
	 * Move forward a given distance.
	 * @param distance A distance measured in units.
	 */
    public void forward(double distance);
    
	/**
	 * Move forward a given distance.
	 * This method implements the same functionality as forward().
	 * @param distance A distance measured in units.
	 */
    public void fd(double distance);
    
    /**
     * Hatch a specified number of new turtles, each identical to this turtle.
     * @param numTurtles The number of new turtles to hatch.
     * @param commandId The ID of a set of turtle commands that can be executed.
     * by calling performActivity(int commandId) on each new turtle.
     */
    public void hatch(int numTurtles, int commandId);
    
    /**
     * Hide from view.
     */
    public void hideTurtle();
    
    /**
     * Hide from view.
     * This method implements the same functionality as hideTurtle().
     */
    public void ht();
    
    /**
     * Go home, by returning to the home coordinates.
     */
    public void home();
    
    /**
     * Return agents that fall within a cone defined by the distance and angle .
     * @param distance A distance measured in units.
     * @param angle An angle measured in degrees.
     * @return A collection of ITurtle or IPatch instances.
     */
    public IAgentSet inCone(double distance, double angle);
    
	/**
	 * Return a list of all neighbors (turtles) within the specified radius.
	 * This works for a torus where each cell has 8 neighbors.
	 * @param radius Distance from the current patch. The current patch is a radius of 0 from itself.
	 * @return A vector containing zero or more turtles.
	 */
	public IAgentSet inRadius(int radius);

	/**
	 * Return a list of all neighbors (turtles) within the specified radius.
	 * This works for a torus where each cell has 8 neighbors.
	 * @param radius Distance from the current patch. The current patch is a radius of 0 from itself.
	 * @param filterId An ID that can be used by performBooleanActivity() to select a specific filter.
	 * @return A vector containing zero or more gridCells.
	 */
	public IAgentSet inRadius(int radius, int filterId);
	
    /**
     * Jump forward a given distance, without effecting any intervening patches or turtles.
     * @param distance A distance measured in units.
     */
    public void jump(double distance);
    
    /**
     * Jump directly to the specified patch.
     * @param aPatch A patch.
     */
    public void jump(IPatch aPatch);
    
    /**
     * Jump directly to the patch that the specified turtle is currently in.
     * @param aTurtle A turtle.
     */
    public void jump(ITurtle aTurtle);
    
    /**
	 * Turn left a given angle.
	 * @param angle An angle measured in degrees.
	 */
    public void left(double angle);
    
    /**
	 * Turn left a given angle.
	 * This method implements the same functionality as left().
	 * @param angle A relative angle measured in degrees.
	 */
    public void lt(double angle);
    
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
     * Return all other turtles that are also currently located at this turtle's patch.
     * @return A collection of ITurtle instances.
     */
    public IAgentSet otherTurtlesHere();
    
    /**
     * Return the single patch that is the given distance along the turtle's current heading.
     * @param distance A distance measured in units.
     * @return An IPatch instance.
     */
    public IPatch patchAhead(double distance);
    
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
     * Return the patch that the turtle is currently on.
     * @return A patch.
     */
    public IPatch patchHere();
    
    /**
     * Return the single patch that is the given distance from the turtle,
     * in the direction turned left the given angle from the turtle's current heading.
     * @param distance A distance measured in units.
     * @param angle An angle measured in degrees.
     * @return A patch, or null.
     */
    public IPatch patchLeftAndAhead(double distance, double angle);
    
    /**
     * Return the single patch that is the given distance from the turtle,
     * in the direction turned right the given angle from the turtle's current heading.
     * @param distance A distance measured in units.
     * @param angle An angle measured in degrees.
     * @return A patch, or null.
     */
    public IPatch patchRightAndAhead(double distance, double angle);
    
	/**
	 * Set the pen down, so it is able to draw.
	 */
	public void penDown();
	
	/**
	 * Set the pen down, so it is able to draw.
	 * This method implements the same functionality as penDown().
	 */
	public void pd();
	
	/**
	 * Set the pen to erase.
	 */
	public void penErase();
	
	/**
	 * Set the pen to erase.
	 * This method implements the same functionality as penErase().
	 */
	public void pe();
	
	/**
	 * Set the pen up, so it is unable to draw.
	 */
	public void penUp();
	
	/**
	 * Set the pen up, so it is unable to draw.
	 * This method implements the same functionality as penUp().
	 */
	public void pu();
	
    /**
	 * Turn right a given angle.
	 * @param angle An angle measured in degrees.
	 */
    public void right(double angle);
    
    /**
	 * Turn right a given angle.
	 * This method implements the same functionality as right().
	 * @param angle A relative angle measured in degrees.
	 */
    public void rt(double angle);

	/**
	 * Set the turtle's x and y coordinates, and move to that location.
	 * @param x New value for the turtle's x coordinate.
	 * @param y New value for the turtle's y coordinate.
	 */
	public void setxy(double x, double y);
	
    /**
     * Show in the viewer.
     */
    public void showTurtle();
    
    /**
     * Show in the viewer.
     * This method implements the same functionality as showTurtle().
     */
    public void st();
    
    /**
     * Leave an inprint or stamp of self at the current patch.
     */
    public void stamp();
    
    /**
     * Erase any inprint or stamp at the current patch.
     */
    public void stampErase();
    
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
     * "Return the turtle heading in the direction of the maximum value
	 * of the variable patch-variable, of the patches in a one-patch radius of the turtle."
	 * @return An angle between 0 and 359 degrees.
     *
     */
	public double uphill();
	
	/**
     * "Return the turtle heading in the direction of the maximum value
	 * of the variable patch-variable, of the patches in a one-patch radius of the turtle."
	 * @return An angle between 0 and 359 degrees.
	 */
	public double uphill4();
	
    /**
     * Filters the input vector to produce an output vector.
     * @param vIn A collection of ITurtle instances.
     * @return A filtered collection of ITurtle instances.
     */
	public IAgentSet with(IAgentSet vIn, int filterId);
	
	
    // getters and setters for all Turtle variables
    
	/**
	 * Get the turtle's breed id.
	 */
	public int getBreed();
	
	/**
	 * Set or change the turtle's breed id.
	 * Warning: Use caution when changing the breed.
	 * @param breed A breed id.
	 */
	public void setBreed(int breed);
	
	/**
	 * Get the turtle's color.
	 * @return A color value, as defined in ITurtlePatchColor.
	 */
	public int getColor();
	
	/**
	 * Set the turtle's color.
	 * NetLogo: set color value
	 * @param color A color value, as defined in ITurtlePatchColor.
	 */
	public void setColor(int color);
	
	/**
	 * Get the turtle's heading.
	 * @return An absolute angle measured in degrees.
	 */
	public double getHeading();
	
	/**
	 * Set the turtle's heading.
	 * NetLogo: set heading value
	 * @param heading An absolute angle measured in degrees.
	 */
	public void setHeading(double heading);
	
	/**
	 * Get the turtle's hide status. Is it hidden or visible?
	 * @return true or false
	 */
	public boolean getIsHidden();
	
	/**
	 * Set the turtle's hide status.
	 * @param isHidden hidden (true) or visible (false)
	 */
	public void setIsHidden(boolean isHidden);
	
	/**
	 * Get the turtle's label, a String that helps to identify it.
	 * @return A label.
	 */
	public String getLabel();
	
	/**
	 * Set or change the turtle's label.
	 * Warning: Use caution when changing the label.
	 * @param label
	 */
	public void setLabel(String label);
	
	/**
	 * Get the turtle's pen mode.
	 * @return A pen mode, one of PENMODE_UP, PENMODE_DOWN, or PENMODE_ERASE.
	 */
	public int getPenMode();
	
	/**
	 * Set the turtle's pen mode.
	 * @param penMode A pen mode, one of PENMODE_UP, PENMODE_DOWN, or PENMODE_ERASE.
	 */
	public void setPenMode(int penMode);
	
	/**
	 * Get the turtle's who id.
	 * @return A who id.
	 */
	public int getWho();
	
	/**
	 * Set the turtle's who id.
	 * Warning: Use caution when changing the who id.
	 * @param who A who id.
	 */
	public void setWho(int who);
	
	/**
	 * Get the turtle's x coordinate.
	 * @return The xcor value.
	 */
	public double getXcor();
	
	/**
	 * Set the turtle's x coordinate.
	 * @param xcor An xcor value.
	 */
	public void setXcor(double xcor);
	
	/**
	 * Get the turtle's y coordinate.
	 * @return The ycor value.
	 */
	public double getYcor();
	
	/**
	 * Set the turtle's y coordinate.
	 * @param ycor An ycor value.
	 */
	public void setYcor(double ycor);
	
	
    // getters and setters for all associated Patch variables
    
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
	
	// get global information; should only be used for configuration
	
	/**
	 * Return the maximum Patch x coordinate.
	 * @return A Patch coordinate.
	 */
	public int getMaxPxcor();
	
	/**
	 * Return the maximum Patch y coordinate.
	 * @return A Patch coordinate.
	 */
	public int getMaxPycor();
	
	/**
	 * Return the minimum Patch x coordinate.
	 * @return A Patch coordinate.
	 */
	public int getMinPxcor();
	
	/**
	 * Return the minimum Patch y coordinate.
	 * @return A Patch coordinate.
	 */
	public int getMinPycor();
	
	/**
	 * Return the width of the world (the grid width).
	 * @return The width in Patch units.
	 */
	public int getWorldWidth();
	
	/**
	 * Return the height of the world (the grid height).
	 * @return The height in Patch units.
	 */
	public int getWorldHeight();
	
	// additional methods
	
	/**
	 * Has this turtle already moved this time step?
	 * This method is required to prevent turtles from moving multiple times in the same time step,
	 * which would happen when a turtle moves to a patch that hasn't yet been reached
	 * in the tree traversal this time step. It would perform its action, move to a new patch postion,
	 * and subsequently be re-executed as a child of the second patch.
	 */
	public boolean hasAlreadyMoved();
	
	/**
	 * Initialize the whenMoved variable.
	 * This is intended for internal use only.
	 * @param when A time step, normally WHENMOVED_INIT (-1).
	 */
	public void initWhenMoved(int when);
	
	/**
	 * Aggregate by keeping a count of the number of individuals of a given "breed".
	 * @param amount The amount to increment or decrement the current population count.
	 * The amount can be a positive (increment) or negative (decrement) number.
	 */
	public void aggregate(double amount);
}

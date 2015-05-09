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
import org.primordion.xholon.util.Misc;

/**
 * A turtle is an agent in the turtle mechanism.
 * The turtle mechanism is based on Logo and NetLogo.
 * The abbreviated version of a turtle command (ex: fd bk rt lt) should be used instead of the unabbreviated command.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on February 24, 2007)
 * @see the NetLogo website http://ccl.northwestern.edu/netlogo/
 */
public class Turtle extends XholonWithPorts implements ITurtle {
	private static final long serialVersionUID = 8324399273496072903L;
	
	protected static final String COMMENT_START = ";"; // start of a NetLogo comment
	protected static final int ACTIONIX_INITIAL = -1; // initial value of actionIx
	
	// NetLogo built-in variables for turtles:
	//   breed color heading hidden? label label-color pen-mode pen-size shape size who xcor ycor
	//public int breed; // Xholon getXhcId() or getXhc()
	public int color; // a NetLogo color between 0 and 139
	public double heading; // current heading as an angle in degrees
	public boolean isHidden; // whether the turtle is currently visible or not
	//public String label; // Xholon getRoleName() getName() or getId() or toString()
	//public int labelColor;
	public int penMode = PENMODE_UP;
	//public int penSize;
	//public int shape;
	//public int size;
	//public int who; // Xholon getId()
	public double xcor, ycor; // current x, y position in global coordinates
	protected int whenMoved; // in which time step did this turtle last move?
	
	public String roleName = null;
	
	protected StringBuilder sb = null;
	
	/**
   * An optional array of actions to automatically perform, during successive timesteps.
   */
  protected String[] actions = new String[0];
  
  /**
   * Index into the actions array.
   */
  protected int actionIx = ACTIONIX_INITIAL;
	
	public Turtle() {}
	
	@Override
  public void setRoleName(String roleName) {this.roleName = roleName;}
  @Override
  public String getRoleName() {return roleName;}
	
	/*
<Turtle/>
	*/
	
	/* Can paste the following into a Patch in Turtle Example 1, using XholonConsole
<Turtle><script><![CDATA[
  var me;
  var beh = {
    postConfigure: function() {
      me = this.cnode.parent();
      me.action("color 25"); // orange
      me.action("pd");
      me.action("fd 10");
      me.action("rt 90");
      me.action("fd 10");
      me.action("rt 90");
      me.action("fd 10");
      me.action("rt 90");
      me.action("fd 10");
      me.action("rt 90");
    }
  }
]]></script></Turtle>
	*/
	
	/*
<Turtle roleName="Franklin">
  <Attribute_String><![CDATA[
    pu
    color 25
    pd
    fd 10
    rt 90
    fd 10
    rt 90
    fd 10
    rt 90
    fd 10
  ]]></Attribute_String>
</Turtle>
	*/
	
	@Override
  public void setVal(String actionsStr) {
    this.setVal_String(actionsStr);
  }
  
  @Override
  public void setVal_String(String actionsStr) {
    // set the contents of the actions array
    if ((actionsStr != null) && (actionsStr.length() > 0)) {
      /*if (transcript && ((actionIx > ACTIONIX_INITIAL) && (actionIx < actions.length))) {
        // show a warning message if a new script overlays an existing uncompleted one
        String msg = "overwriting script with " + (actions.length - actionIx) + " actions left.";
        this.println(this.getName(IXholon.GETNAME_ROLENAME_OR_CLASSNAME) + ": " + msg);
      }*/
      actions = actionsStr.split("\n");
      actionIx = ACTIONIX_INITIAL;
    }
  }
  
  @Override
  public String getVal_String() {
    // get the value of the current item in the actions array, or null
    if ((actionIx > ACTIONIX_INITIAL) && (actionIx < actions.length)) {
      return actions[actionIx];
    }
    return null;
  }
  
	@Override
  public void postConfigure() {
    if (this.getFirstChild() != null) {
      this.setVal_String(this.getFirstChild().getVal_String());
      this.getFirstChild().removeChild();
    }
    // testing
    for (int i = 0; i < actions.length; i++) {
      doAction(actions[i]);
    }
    super.postConfigure();
  }
	
	@Override
  public void processReceivedMessage(IMessage msg) {
    //consoleLog("Turtle processReceivedMessage( " + msg.getData());
    switch (msg.getSignal()) {
    case ISignal.SIGNAL_XHOLON_CONSOLE_REQ:
      String responseStr = processCommands((String)msg.getData());
      if ((responseStr != null) && (responseStr.length() > 0)) {
        responseStr = COMMENT_START + responseStr;
        msg.getSender().sendMessage(ISignal.SIGNAL_XHOLON_CONSOLE_RSP, "\n" + responseStr, this);
      }
      break;
    default:
      super.processReceivedMessage(msg);
      break;
    }
  }
  
  @Override
  public IMessage processReceivedSyncMessage(IMessage msg) {
    //consoleLog("Turtle processReceivedSyncMessage( " + msg.getData());
    switch (msg.getSignal()) {
    case ISignal.SIGNAL_XHOLON_CONSOLE_REQ:
      String responseStr = processCommands((String)msg.getData());
      if ((responseStr != null) && (responseStr.length() > 0)) {
        responseStr = COMMENT_START + responseStr;
        //consoleLog(responseStr);
      }
      return new Message(ISignal.SIGNAL_XHOLON_CONSOLE_RSP, "\n" + responseStr, this, msg.getSender());
    default:
      return super.processReceivedSyncMessage(msg);
    }
  }
	
	@Override
  public void doAction(String action) {
    String responseStr = processCommands(action);
    /*if (transcript && (responseStr != null) && (responseStr.length() > 0)) {
      this.println(" " + responseStr);
    }
    if (speech && (responseStr != null) && (responseStr.length() > 0)) {
      this.speak(" " + responseStr, ssuLang, ssuVoice, ssuVolume, ssuRate, ssuPitch);
    }
    if (debug) {
      this.consoleLog(responseStr);
    }*/
  }
  
  protected String processCommands(String cmds) {
    //consoleLog("Turtle processCommands( " + cmds);
    sb = new StringBuilder();
    processCommand(cmds);
    return sb.toString();
  }
  
  protected void processCommand(String cmd) {
    //consoleLog("Turtle processCommand( " + cmd);
    cmd = cmd.trim();
    if (cmd.length() == 0) {return;}
    if (cmd.startsWith(COMMENT_START)) {return;}
    String[] data = cmd.split(" ");
    int len = data.length;
    //consoleLog("Turtle processCommand( " + data[0]);
    switch (data[0]) {
    case "back":
    case "bk":
      if (len == 2) {
        bk(Misc.atod(data[1], 0));
      }
      break;
    case "beep":
      beep();
      break;
    // case "can-move":
    case "color": // or "set color" ?
      if (len == 2) {
        //consoleLog("color " + data[1]);
        setColor(Misc.atoi(data[1], 0));
        //consoleLog(getColor());
      }
      else {
        sb.append("Please specify a color integer between 0 and 140 (ex: ")
        .append(data[0])
        .append(" 35).");
      }
      break;
    case "die":
      die();
      break;
    // case "distance":
    case "distancexy":
      if (len == 3) {
        double distance = distancexy(Misc.atod(data[1], 0), Misc.atod(data[2], 0));
        sb.append(distance);
      }
      break;
    case "downhill":
      sb.append(downhill());
      break;
    case "downhill4":
      sb.append(downhill4());
      break;
    case "dx":
      sb.append(dx());
      break;
    case "dy":
      sb.append(dy());
      break;
    // case "face":
    case "facexy":
      if (len == 3) {
        facexy(Misc.atod(data[1], 0), Misc.atod(data[2], 0));
      }
      break;
    case "forward":
    case "fd":
      if (len == 2) {
        //consoleLog("fd " + data[1]);
        fd(Misc.atod(data[1], 0));
      }
      break;
    case "hatch":
      if (len == 2) {
        hatch(Misc.atoi(data[1], 0), COMMANDID_NONE);
      }
      else if (len == 3) {
        hatch(Misc.atoi(data[1], 0), Misc.atoi(data[2], 0));
      }
      break;
    case "hide-turtle":
    case "ht":
      ht();
      break;
    case "home":
      home();
      break;
    //case "in-cone":
    //case "in-radius":
    case "jump":
      if (len == 1) {
        jump(Misc.atod(data[1], 0));
      }
      break;
    case "left":
    case "lt":
      if (len == 2) {
        lt(Misc.atod(data[1], 0));
      }
      break;
    //case "neighbors":
    //case "neighbors4":
    //case "other turtles-here":
    //case "patch-ahead":
    //case "patch-at":
    //case "patch-at-heading-and-distance":
    //case "patch-here":
    //case "patch-left-and-ahead":
    //case "patch-right-and-ahead":
    case "pen-down":
    case "pd":
      //consoleLog("pd");
      pd();
      break;
    case "pen-erase":
    case "pe":
      pe();
      break;
    case "pen-up":
    case "pu":
      pu();
      break;
    case "right":
    case "rt":
      if (len == 2) {
        rt(Misc.atod(data[1], 0));
      }
      break;
    case "setxy":
      if (len == 3) {
        setxy(Misc.atod(data[1], 0), Misc.atod(data[2], 0));
      }
      break;
    case "show-turtle":
    case "st":
      showTurtle();
      break;
    case "stamp":
      stamp();
      break;
    case "stamp-erase":
      stampErase();
      break;
    //case "towards":
    case "towardsxy":
      if (len == 3) {
        towardsxy(Misc.atod(data[1], 0), Misc.atod(data[2], 0));
      }
      break;
    //case "turtles-at":
    //case "turtles-here":
    //case "turtles-on":
    case "uphill":
      sb.append(uphill());
      break;
    case "uphill4":
      sb.append(uphill4());
      break;
    default: break;
    }
  }
	
	/*
     * @see org.primordion.xholon.base.ITurtle#back(double)
     */
    public void back(double distance)
    {
        bk(distance);
    }
	
	/*
     * @see org.primordion.xholon.base.ITurtle#bk(double)
     */
    public void bk(double distance)
    {
        fd(-distance);
    }
	
    /*
     * @see org.primordion.xholon.base.ITurtle#beep()
     */
	public void beep() {
		((IPatch)parentNode).beep();
	}

    /*
     * @see org.primordion.xholon.base.ITurtle#canMove(double)
     */
    public boolean canMove(double distance)
    {
    	// TODO implement
    	return false;
    }
    	
	/*
	 * @see org.primordion.xholon.base.ITurtle#die()
	 */
	public void die()
	{
		removeChild();
		remove();
		whenMoved = Integer.MAX_VALUE; // ensure that it can never move again
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#distance(org.primordion.xholon.base.IXholon)
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
	 * @see org.primordion.xholon.base.ITurtle#distancexy(double, double)
	 */
	public double distancexy(double x, double y)
	{
		double deltaX = xcor - x;
		double deltaY = ycor - y;
		return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#downhill()
	 */
	public double downhill() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#downhill4()
	 */
	public double downhill4() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#dx()
	 */
	public double dx()
	{
		return Math.sin(Math.toRadians(heading));
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#dy()
	 */
	public double dy()
	{
		return Math.cos(Math.toRadians(heading));
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#face(org.primordion.xholon.base.IXholon)
	 */
	public void face(IXholon turtleOrPatch)
	{
		if (ClassHelper.isAssignableFrom(Turtle.class, turtleOrPatch.getClass())) {
			facexy(((ITurtle)turtleOrPatch).getXcor(), ((ITurtle)turtleOrPatch).getYcor());
		}
		else { // this must be a IPatch
			facexy(((IPatch)turtleOrPatch).getPxcor(), ((IPatch)turtleOrPatch).getPycor());
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#facexy(double, double)
	 */
	public void facexy(double x, double y)
	{
		//consoleLog("facexy x=" + x + " y=" + y + " xcor=" + xcor + " ycor=" + ycor + " heading=" + heading);
		heading = towardsxy(x, y);
		//consoleLog("new heading=" + heading);
	}
	
	/*
     * @see org.primordion.xholon.base.ITurtle#forward(double)
     */
    public void forward(double distance)
    {
        fd(distance);
    }
	
	/*
     * @see org.primordion.xholon.base.ITurtle#fd(double)
     */
    public void fd(double distance)
    {
    	// convert heading from degrees to radians first
    	double h = Math.toRadians(heading);
    	double deltaX = distance * Math.sin(h);
    	double deltaY = distance * Math.cos(h);
    	IXholon newParentNode = null;
    	switch (penMode) {
    	case PENMODE_UP:
    		newParentNode = moveRel(xcor, ycor, deltaX, deltaY);
    		break;
    	case PENMODE_DOWN:
    		newParentNode = lineRel(xcor, ycor, deltaX, deltaY, color);
    		break;
    	case PENMODE_ERASE:
    		break;
    	default:
    		break;
    	}
    	xcor += deltaX;
    	ycor += deltaY;
    	// warp the turtle to its new position
    	removeChild();
    	appendChild(newParentNode);
    	// handle wrap at borders
    	xcor = getPxcor() + (xcor - (int)xcor);
    	ycor = getPycor() + (ycor - (int)ycor);
    	whenMoved = getApp().getTimeStep(); // record when last moved
    }
	
    /*
     * @see org.primordion.xholon.base.ITurtle#hatch(int, int)
     */
    public void hatch(int numTurtles, int commandId)
    {
		String implName = xhc.getImplName();
		
		// move up the inheritance hierarchy looking for first parent with a non-null implName
		IXholonClass xhcSearchNode = xhc;
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
			turtle.setParentSiblingLinks(this);
			turtle.setXhc(xhc);
			turtle.setXcor(xcor);
			turtle.setYcor(ycor);
			turtle.setHeading(heading);
			turtle.setColor(color);
			turtle.setPenMode(penMode);
			turtle.initWhenMoved(ITurtle.WHENMOVED_INIT);
			if (commandId != COMMANDID_NONE) {
				turtle.performActivity(commandId);
			}
		}
    }
    
    /*
     * @see org.primordion.xholon.base.ITurtle#hideTurtle()
     */
    public void hideTurtle()
    {
    	ht();
    }
    
    /*
     * @see org.primordion.xholon.base.ITurtle#ht()
     */
    public void ht()
    {
    	isHidden = true;
    }
    
    /*
     * @see org.primordion.xholon.base.ITurtle#home()
     */
    public void home()
    {
    	setxy(0.0, 0.0);
    }
    
	public IAgentSet inCone(double distance, double angle) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#inRadius(int)
	 */
	public IAgentSet inRadius(int radius) {
		IAgentSet asPatch = ((IPatch)parentNode).inRadius(radius); // get all patches within the radius
		IAgentSet asTurtle = new AgentSet();
		for (int i = 0; i < asPatch.size(); i++) { // get all turtles within those patches
			IPatch pNode = (IPatch)asPatch.get(i);
			IAgentSet asTurtlesThere = pNode.turtlesHere();
			if (asTurtlesThere != null) {
				asTurtle.addAll(asTurtlesThere);
			}
		}
		return asTurtle;
	}
    
	/*
	 * @see org.primordion.xholon.base.ITurtle#inRadius(int, int)
	 * TODO filter on both the patches and the turtles?
	 */
	public IAgentSet inRadius(int radius, int filterId) {
		IAgentSet asPatch = ((IPatch)parentNode).inRadius(radius, filterId); // get all patches within the radius
		IAgentSet asTurtle = new AgentSet();
		for (int i = 0; i < asPatch.size(); i++) { // get all turtles within those patches
			IPatch pNode = (IPatch)asPatch.get(i);
			IAgentSet asTurtlesThere = pNode.turtlesHere();
			if (asTurtlesThere != null) {
				asTurtle.addAll(asTurtlesThere);
			}
		}
		return asTurtle;
	}
    
    /*
     * @see org.primordion.xholon.base.ITurtle#jump(double)
     */
    public void jump(double distance)
    {
      //consoleLog(this.getName() + " jump( " + distance);
    	pu();
    	fd(distance);
    	pd();
    }
    
    /*
     * @see org.primordion.xholon.base.ITurtle#jump(org.primordion.xholon.base.IPatch)
     */
    public void jump(IPatch aPatch)
    {
		removeChild(); // detach self from parent and sibling links
		appendChild(aPatch); // insert as the last child of the destination grid cell
		xcor = aPatch.getPxcor() + (xcor - (int)xcor);
		ycor = aPatch.getPycor() + (ycor - (int)ycor);
    	whenMoved = getApp().getTimeStep(); // record when last moved
    }
    
    /*
     * @see org.primordion.xholon.base.ITurtle#jump(org.primordion.xholon.base.ITurtle)
     */
    public void jump(ITurtle aTurtle)
    {
    	jump(((IPatch)aTurtle.getParentNode()));
    }
    
	/*
     * @see org.primordion.xholon.base.ITurtle#left(double)
     */
    public void left(double angle)
    {
        lt(angle);
    }
	
    /*
     * @see org.primordion.xholon.base.ITurtle#lt(double)
     */
    public void lt(double angle)
    {
    	heading -= angle;
        heading = heading % 360;
        if (heading < 0) {
        	heading += 360;
        }
    }
    
    /*
     * @see org.primordion.xholon.base.ITurtle#neighbors()
     */
    public IAgentSet neighbors()
    {
    	return(((IPatch)parentNode).neighbors());
    }
    
    /*
     * @see org.primordion.xholon.base.ITurtle#neighbors4()
     */
    public IAgentSet neighbors4()
    {
    	return(((IPatch)parentNode).neighbors4());
    }
    
    /*
     * @see org.primordion.xholon.base.ITurtle#otherTurtlesHere()
     */
    public IAgentSet otherTurtlesHere()
    {
    	return new AgentSet(getSiblings()).shuffle();
    }
    
    /*
     * @see org.primordion.xholon.base.ITurtle#patchAhead(double)
     */
	public IPatch patchAhead(double distance) {
		return ((IPatch)parentNode).patchAtHeadingAndDistance(heading, distance);
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#patchAt(int, int)
	 */
	public IPatch patchAt(int dx, int dy) {
		return ((IPatch)parentNode).patchAt(dx, dy);
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#patchAtHeadingAndDistance(double, double)
	 */
	public IPatch patchAtHeadingAndDistance(double heading, double distance) {
		return ((IPatch)parentNode).patchAtHeadingAndDistance(heading, distance);
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#patchHere()
	 */
	public IPatch patchHere() {
		return (IPatch)parentNode;
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#patchLeftAndAhead(double, double)
	 */
	public IPatch patchLeftAndAhead(double distance, double angle) {
		return ((IPatch)parentNode).patchAtHeadingAndDistance(heading - angle, distance);
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#patchRightAndAhead(double, double)
	 */
	public IPatch patchRightAndAhead(double distance, double angle) {
		return ((IPatch)parentNode).patchAtHeadingAndDistance(heading + angle, distance);
	}
  
  /*
   * @see org.primordion.xholon.base.ITurtle#penDown()
   */
  public void penDown()
  {
  	pd();
  }
  
  /*
	 * @see org.primordion.xholon.base.ITurtle#pd()
	 */
	public void pd()
	{
		penMode = PENMODE_DOWN;
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#penErase()
	 */
	public void penErase()
	{
		pe();
	}
    /*
	 * @see org.primordion.xholon.base.ITurtle#pe()
	 */
	public void pe()
	{
		penMode = PENMODE_ERASE;
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#penUp()
	 */
	public void penUp()
	{
		pu();
	}
	
    /*
	 * @see org.primordion.xholon.base.ITurtle#pu()
	 */
	public void pu()
	{
		penMode = PENMODE_UP;
	}

    /*
     * @see org.primordion.xholon.base.ITurtle#right(double)
     */
    public void right(double angle)
    {
        rt(angle);
    }
    
    /*
     * @see org.primordion.xholon.base.ITurtle#rt(double)
     */
    public void rt(double angle)
    {
        heading += angle;
        heading = heading % 360;
        if (heading < 0) {
        	heading += 360;
        }
    }
    
    /*
	 * @see org.primordion.xholon.base.ITurtle#setxy(double, double)
	 */
	public void setxy(double x, double y)
	{
		double savedHeading = heading;
		int savedPenMode = penMode;
		facexy(x, y);
		jump(distancexy(x, y));
		xcor = x;
		ycor = y;
		heading = savedHeading;
		penMode = savedPenMode;
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#showTurtle()
	 */
	public void showTurtle()
	{
		st();
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#st()
	 */
	public void st()
	{
		isHidden = false;
	}
	
	public void stamp() {
		// TODO Auto-generated method stub
		
	}

	public void stampErase() {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#towards(org.primordion.xholon.base.IXholon)
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
	 * @see org.primordion.xholon.base.ITurtle#towardsxy(double, double)
	 */
	public double towardsxy(double x, double y)
	{
	  // this works, but only if x and y are 0.0 (when moving home())
	  // Notes re NetLogo:
	  // - tangent is x over y, rather than y over x in standard trig
	  // - atan2 x y = heading
	  // - degrees rather than radians
	  // - angles increase clockwise
	  // - 0 degrees is North, 90 East, 180 South, 270 West
	  // - 0 <= heading < 360
	  // see http://www.turtlezero.com/wiki/doku.php?id=guide:trigonometry
	  double h = Math.atan2(xcor - x, ycor - y);
	  // add 180 to the heading to reverse it
		return Math.toDegrees(h) + 180;
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#turtlesAt(int, int)
	 */
	public IAgentSet turtlesAt(int dx, int dy) {
		return ((IPatch)parentNode).turtlesAt(dx, dy);
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#turtlesHere()
	 */
	public IAgentSet turtlesHere() {
		return ((IPatch)parentNode).turtlesHere();
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#turtlesOn()
	 */
	public IAgentSet turtlesOn() {
		return ((IPatch)parentNode).turtlesOn();
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#uphill()
	 */
	public double uphill() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#uphill4()
	 */
	public double uphill4() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#with(java.util.Vector, int)
	 */
	public IAgentSet with(IAgentSet asIn, int filterId) {
    	if (asIn == null) {return null;}
    	IAgentSet asOut = new AgentSet();
    	for (int i = 0; i < asIn.size(); i++) {
    		IXholon node = (IXholon)asIn.get(i);
    		if (node.performBooleanActivity(filterId)) {
    			asOut.add(node);
    		}
    	}
    	return null;
	}
	
    
	// getters and setters for all Turtle variables
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#getBreed()
	 */
	public int getBreed()
	{
		return getXhcId();
	}
	/*
	 * @see org.primordion.xholon.base.ITurtle#setBreed(int)
	 */
	public void setBreed(int breed)
	{
		this.setXhc(parentNode.getClassNode(breed));
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#getColor()
	 */
	public int getColor()
	{
		return color;
	}
	
    /*
	 * @see org.primordion.xholon.base.ITurtle#setColor(double)
	 */
	public void setColor(int color)
	{
		this.color = color;
	}
    
	/*
	 * @see org.primordion.xholon.base.ITurtle#getHeading()
	 */
	public double getHeading()
	{
		return heading;
	}
	
    /*
	 * @see org.primordion.xholon.base.ITurtle#setHeading(double)
	 */
	public void setHeading(double heading)
	{
		this.heading = heading;
	}
    
	/*
	 * @see org.primordion.xholon.base.ITurtle#getIsHidden()
	 */
	public boolean getIsHidden()
	{
		return isHidden;
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#setIsHidden(boolean)
	 */
	public void setIsHidden(boolean isHidden)
	{
		this.isHidden = isHidden;
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#getLabel()
	 */
	public String getLabel()
	{
		// TODO is this the right thing to return?
		return getName();
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#setLabel(java.lang.String)
	 */
	public void setLabel(String label)
	{
		// TODO is this the right thing to set?
		setRoleName(label);
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#getPenMode()
	 */
	public int getPenMode()
	{
		return penMode;
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#setPenMode(int)
	 */
	public void setPenMode(int penMode)
	{
		this.penMode = penMode;
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#getWho()
	 */
	public int getWho()
	{
		return getId();
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#setWho(int)
	 */
	public void setWho(int who)
	{
		this.setId(who);
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#getXcor()
	 */
	public double getXcor()
	{
		return xcor;
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#setXcor(double)
	 */
	public void setXcor(double xcor)
	{
		this.xcor = xcor;
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#getYcor()
	 */
	public double getYcor()
	{
		return ycor;
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#setYcor(double)
	 */
	public void setYcor(double ycor)
	{
		this.ycor = ycor;
	}
	
	
	// getters and setters for all associated Patch variables
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#getPcolor()
	 */
	public int getPcolor()
	{
		return ((IPatch)parentNode).getPcolor();
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#setPcolor(int)
	 */
	public void setPcolor(int pcolor)
	{
		((IPatch)parentNode).setPcolor(pcolor);
	}
    
	/*
	 * @see org.primordion.xholon.base.ITurtle#getPlabel()
	 */
	public String getPlabel()
	{
		return ((IPatch)parentNode).getPlabel();
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#setPlabel(java.lang.String)
	 */
	public void setPlabel(String plabel)
	{
		((IPatch)parentNode).setPlabel(plabel);
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#getPxcor()
	 */
	public int getPxcor()
	{
		return ((IPatch)parentNode).getPxcor();
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#setPxcor(double)
	 */
	public void setPxcor(int pxcor)
	{
		((IPatch)parentNode).setPxcor(pxcor);
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#getPycor()
	 */
	public int getPycor()
	{
		return ((IPatch)parentNode).getPycor();
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#setPycor(double)
	 */
	public void setPycor(int pycor)
	{
		((IPatch)parentNode).setPycor(pycor);
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#getMaxPxcor()
	 */
	public int getMaxPxcor() {return getPatchOwner().getMaxPxcor();}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#getMaxPycor()
	 */
	public int getMaxPycor() {return getPatchOwner().getMaxPycor();}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#getMinPxcor()
	 */
	public int getMinPxcor() {return getPatchOwner().getMinPxcor();}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#getMinPycor()
	 */
	public int getMinPycor() {return getPatchOwner().getMinPycor();}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#getWorldWidth()
	 */
	public int getWorldWidth() {return getPatchOwner().getWorldWidth();}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#getWorldHeight()
	 */
	public int getWorldHeight() {return getPatchOwner().getWorldHeight();}
	
	/**
	 * Return the owner of this patch.
	 * @return Patch owner.
	 */
	private PatchOwner getPatchOwner() {return (PatchOwner)getParentNode().getParentNode().getParentNode();}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#hasAlreadyMoved()
	 */
	public boolean hasAlreadyMoved()
	{
		if (whenMoved >= getApp().getTimeStep()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#initWhenMoved(int)
	 */
	public void initWhenMoved(int when)
	{
		whenMoved = when;
	}
	
	/*
	 * @see org.primordion.xholon.base.ITurtle#aggregate(double)
	 */
	// TODO this is slow; is it possible to cache the aggregator?
	public void aggregate(double amount)
	{
		IAttribute agg = getPatchOwner().getAggregator(this);
		if (agg != null) {
			agg.incVal(amount);
		}
	}
	
	/**
	 * Draw a line using the Bresenham algorithm.
	 * @param x0 X coordinate, start of line.
	 * @param y0 Y coordinate, start of line.
	 * @param x1 X coordinate, end of line.
	 * @param y1 Y coordinate, end of line.
	 * @param colr The color to set each patch along the line.
	 * @return The patch that the turtle has moved to,
     * or the turtle's current patch if it is unable to move there.
	 * @see http://www.cs.unc.edu/~mcmillan/comp136/Lecture6/Lines.html
	 */
    public IXholon lineBresenham(int x0, int y0, int x1, int y1, int colr)
    {
    	IPatch node = (IPatch)getParentNode(); // get the turtle's current patch
        int dy = y1 - y0; // delta Y
        int dx = x1 - x0; // delta X
        int stepx, stepy; // step increments in X and Y direction; +1 or -1 depending on direction of movement

        if (dy < 0) { dy = -dy;  stepy = -1; } else { stepy = 1; }
        if (dx < 0) { dx = -dx;  stepx = -1; } else { stepx = 1; }
        dy <<= 1;
        dx <<= 1;

        node.setPcolor(colr); // color the patch
        if (dx > dy) {
            int fraction = dy - (dx >> 1);
            while (x0 != x1) {
                if (fraction >= 0) {
                    y0 += stepy;
                    if (stepy == 1) {
    					node = (IPatch)node.getPort(IGrid.P_NORTH);
    					if (node == null) {break;}
    				}
    				else {
    					node = (IPatch)node.getPort(IGrid.P_SOUTH);
    					if (node == null) {break;}
    				}
                    fraction -= dx;
                }
                x0 += stepx;
                if (stepx == 1) {
					node = (IPatch)node.getPort(IGrid.P_EAST);
					if (node == null) {break;}
				}
				else {
					node = (IPatch)node.getPort(IGrid.P_WEST);
					if (node == null) {break;}
				}
                fraction += dy;
                node.setPcolor(colr); // color the patch
            }
        } else {
            int fraction = dx - (dy >> 1);
            while (y0 != y1) {
                if (fraction >= 0) {
                    x0 += stepx;
                    if (stepx == 1) {
    					node = (IPatch)node.getPort(IGrid.P_EAST);
    					if (node == null) {break;}
    				}
    				else {
    					node = (IPatch)node.getPort(IGrid.P_WEST);
    					if (node == null) {break;}
    				}
                    fraction -= dy;
                }
                y0 += stepy;
                if (stepy == 1) {
					node = (IPatch)node.getPort(IGrid.P_NORTH);
					if (node == null) {break;}
				}
				else {
					node = (IPatch)node.getPort(IGrid.P_SOUTH);
					if (node == null) {break;}
				}
                fraction += dx;
                node.setPcolor(colr); // color the patch
            }
        }
        if (node == null) {
        	return (IPatch)getParentNode();
        }
        return node;
    }
    
	/**
     * Line Absolute.
     * Draw a line by changing the value of each gridCell that the turtle passes through
     * when moving forward or back a given distance.
     * @param xa The current x position of the turtle.
     * @param ya The current y position of the turtle.
     * @param xb The destination x position of the turtle.
     * @param yb The destination y position of the turtle.
     * @param colr The color of the line.
     * @return The gridCell xholon at the destination position. 
     * @see Harrington, S. (1987). Computer Graphics - A programming approach. New York: McGraw-Hill.
     *      Algorithm 1.1, VECGEN(), p. 15
     */
    protected IXholon lineAbs(double xa, double ya, double xb, double yb, int colr)
    {
    	return lineBresenham((int)xa, (int)ya, (int)xb, (int)yb, colr);
    }
	
    /**
     * Line Relative.
     * Draw a line by changing the value of each gridCell that the turtle passes through
     * when moving forward or back a given distance.
     * @param xa The current x position of the turtle.
     * @param ya The current y position of the turtle.
     * @param dx The distance (delta) to draw in the x direction.
     * @param dy The distance (delta) to draw in the y direction.
     * @param colr The color of the line.
     * @return The gridCell xholon at the destination position.
     */
    protected IXholon lineRel(double xa, double ya, double dx, double dy, int colr)
    {
    	return lineBresenham((int)xa, (int)ya, (int)(xa + dx), (int)(ya + dy), colr);
    }
    
    /**
     * Move Absolute.
     * @param xa The current x position of the turtle.
     * @param ya The current y position of the turtle.
     * @param xb The destination x position of the turtle.
     * @param yb The destination y position of the turtle.
     * @return The patch that the turtle has moved to,
     * or the turtle's current patch if it is unable to move there.
     */
    protected IXholon moveAbs(double xa, double ya, double xb, double yb)
    {
    	IXholon node = getParentNode();
    	xa = xa > 0 ? xa = Math.floor(xa) : Math.ceil(xa);
    	xb = xb > 0 ? xb = Math.floor(xb) : Math.ceil(xb);
    	ya = ya > 0 ? ya = Math.floor(ya) : Math.ceil(ya);
    	yb = yb > 0 ? yb = Math.floor(yb) : Math.ceil(yb);
    	int dx = (int)(xb - xa);
    	int dy = (int)(yb - ya);
    	
    	int i;
    	// move along x axis
    	if (dx > 0) {
	    	for (i = 0; i < dx; i++) {
	    		node = node.getPort(IGrid.P_EAST);
	    		if (node == null) {break;}
	    	}
    	}
    	else {
    		for (i = 0; i > dx; i--) {
	    		node = node.getPort(IGrid.P_WEST);
	    		if (node == null) {break;}
	    	}
    	}
    	if (node == null) {
    		return getParentNode(); // return this turtle's own parent
    	}
    	// move along y axis
    	if (dy > 0) {
	    	for (i = 0; i < dy; i++) {
	    		node = node.getPort(IGrid.P_NORTH);
	    		if (node == null) {break;}
	    	}
    	}
    	else {
    		for (i = 0; i > dy; i--) {
	    		node = node.getPort(IGrid.P_SOUTH);
	    		if (node == null) {break;}
	    	}
    	}
    	if (node == null) {
    		return getParentNode(); // return this turtle's own parent
    	}
    	return node;
    }

    /**
     * Move Relative.
     * @param xa The current x position of the turtle.
     * @param ya The current y position of the turtle.
     * @param dx The distance (delta) to move in the x direction.
     * @param dy The distance (delta) to move in the y direction.
     * @return The patch that the turtle has moved to,
     * or the turtle's current patch if it is unable to move there.
     */
    protected IXholon moveRel(double xa, double ya, double dx, double dy)
    {
    	return moveAbs(xa, ya, xa + dx, ya + dy);
    }
    
    /*
     * @see org.primordion.xholon.base.Xholon#toString()
     */
    public String toString() {
    	String outStr = getName();
    	outStr += " color: " + color;
    	outStr += " heading: " + heading;
    	outStr += " isHidden: " + isHidden;
    	outStr += " penMode: ";
    	switch(penMode) {
    	case ITurtle.PENMODE_UP: outStr += "up"; break;
    	case ITurtle.PENMODE_DOWN: outStr += "down"; break;
    	case ITurtle.PENMODE_ERASE: outStr += "erase"; break;
    	default: break;
    	}
    	outStr += " xcor: " + xcor;
    	outStr += " ycor: " + ycor;
    	outStr += " patch: " + parentNode.getName();
    	return outStr;
    }
}

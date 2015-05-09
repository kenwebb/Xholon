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

package org.primordion.user.app.TurtleExample1;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PatchOwner;
import org.primordion.xholon.base.Turtle;

public class TurtleExample extends Turtle {

// Signals, Events
public static final int SIG_SELF = 100;

// Variables
protected static String appSpecificParam1 = ""; // XhnParams

// toInspi angle, etc.
protected double recurseArg = 0.0;

// Constructor
public TurtleExample() {super();}

// GWT changed the following two methods to non-static
public void setAppSpecificParam1(String AppSpecificParam1Arg) {appSpecificParam1 = AppSpecificParam1Arg;}
public String getAppSpecificParam1() {return appSpecificParam1;}

public void initialize()
{
	super.initialize();
}

public void postConfigure()
{
	//System.out.println("turtle: " + getName() + " patch: " + parent.getName());
	pu();
	//fd(60.0);
	//rt(180.0); fd(60.0); lt(180);
	//lt(90.0); fd(60.0); rt(90.0); // move starting position left
	//rt(90.0); fd(60.0); lt(90.0);
	pd();
	//setColor(ITurtle.COLR_MAGENTA);
	// set an initial angle
	//rt(11.0);
	setAppSpecificParam1(XhTurtleExample1.getAppSpecificParam1());
	this.sendMessage(SIG_SELF, null, this); // schedule by sending self a message
	super.postConfigure();
}

public void processReceivedMessage(IMessage msg)
{
  switch (msg.getSignal()) {
  case SIG_SELF:
    // allow user to set the turtle actions
	  setAppSpecificParam1(XhTurtleExample1.getAppSpecificParam1());
	  actTurtle();
	  this.sendMessage(SIG_SELF, null, this); // reschedule by sending self a message
	  break;
	default:
	  super.processReceivedMessage(msg);
	  break;
	}
}

/**
 * Do turtle actions.
 */
protected void actTurtle()
{
	if ("Sheep".equals(getXhcName())) {
		fd(45); lt(90);
		fd(30); rt(90);
		fd(15); lt(90);
		fd(20); lt(90);
		fd(30); lt(90);
		fd(10); rt(90);
		fd(30); lt(90);
		fd(10); lt(90);
		fd(15); rt(90);
		fd(20); rt(90);
		fd(15); lt(90);
		fd(10); lt(90);
		rt(1);
	}
	else if ("circle".equals(appSpecificParam1)) {
		//rt(1.0); fd(1.0);
		rt(2.0); fd(2.0);
	}
	else if ("square".equals(appSpecificParam1)) {
		toSquare(40.0); rt(30.0);
	}
	else if ("poly".equals(appSpecificParam1)) {
		//toPoly(50.0, 108.0); // stars 144.0 135.0 108.0
		//toPoly(10.0, 120.0); // triangle
		//toPoly(10.0, 90.0); // square
		//toPoly(40.0, 72.0); // pentagon
		//toPoly(10.0, 60.0); // hexagon
		toPoly(37.123, 45.0); // octogon
		//toPoly(10.0, 40.0); // nine-sided
		//toPoly(20.0, 36.0); // ten-sided
		//toPoly(20.0, 360.0 / 11.0); // eleven-sided
		//toPoly(20.0, 30.0); // twelve-sided
		//toPoly(20.0, 360.0 / 13.0); // thirteen-sided
	}
	else if ("house".equals(appSpecificParam1)) {
		toPoly(30.0, 90.0); // house part
		fd(30.0); rt(30.0);
		toPoly(30.0, 120.0); // roof
		lt(30.0); bk(30.0);
	}
	else if ("toPolyspi".equals(appSpecificParam1)) {
		recurseArg = toPolyspi(recurseArg, 117.0, 3.0); // recurseArg=5.0 10.0  arg2=95.0 90.0 120.0 117.0
	}
	else if ("toInspi".equals(appSpecificParam1)) {
		//recurseArg = toInspi(20.0, recurseArg, 20.0); // recurseArg=2.0
		//recurseArg = toInspi(20.0, recurseArg, 30.0); // recurseArg=40.0
		recurseArg = toInspi(8.0, recurseArg, 7.0); // 12 turtles, recurseArg=0.0
	}
	else if ("toEquispi".equals(appSpecificParam1)) {
		recurseArg = toEquispi(recurseArg, 20.0, 1.04); // recurseArg=2.0
	}
	else if ("toBranch".equals(appSpecificParam1)) {
		toBranch(40, 6);
	}
	else if ("toLBranch".equals(appSpecificParam1)) {
		toLBranch(10, 20, 7);
	}
	else if ("toLDragon".equals(appSpecificParam1)) {
		pu(); fd(40); rt(90); fd(50); lt(90); pd(); // move to upper right quadrant
		toLDragon(4, 11);
	}
	else if ("clearPatches".equals(appSpecificParam1)) {
    // the first turtle invokes the patch owner to clear the patches
    IXholon node = this;
	  while (node != null) {
		  if (node.getXhcName().equals("PatchOwner")) {
		    ((PatchOwner)node).clearPatches();
			  break;
		  }
		  node = node.getParentNode();
	  }
		XhTurtleExample1.setAppSpecificParam1("clearingPatches");
	}
	else if ("clearingPatches".equals(appSpecificParam1)) {
	  this.home();
	}
	else if ("clearTurtles".equals(appSpecificParam1)) {
	  XhTurtleExample1.setAppSpecificParam1("clearingTurtles");
	}
	else if ("clearingTurtles".equals(appSpecificParam1)) {
	  this.die();
	}
	else if ("nil".equals(appSpecificParam1)) {
	  // do nothing
	}
	
}

/**
 * Draw a square.
 * Example of a higher level procedure.
 * This is NOT part of the core turtle language.
 * @param size The length of the edges of the square.
 */
public void toSquare(double size)
{
	for (int i = 0; i < 4; i++) {
		fd(size);
		rt(90.0);
	}
}

/**
 * Draw a polygon.
 * Example of a higher level procedure.
 * This is NOT part of the core turtle language.
 * @param side The length of a side of the polygon.
 * @param angle The angle in degrees between sides.
 */
public void toPoly(double side, double angle)
{
	for (int i = 0; i < 360.0 / angle; i++) {
		fd(side);
		rt(angle);
	}
}

/**
 * 
 * source: Abelson, H. and diSessa, A. (1980). Turtle Geometry. Cambridge, MA: MIT Press. (p.19)
 * @param side
 * @param angle
 * @param inc
 * @return
 */
public double toPolyspi(double side, double angle, double inc)
{
	fd(side);
	rt(angle);
	return side + inc;
}

/**
 * 
 * source: Abelson, H. and diSessa, A. (1980). Turtle Geometry. Cambridge, MA: MIT Press. (p.20)
 * @param side
 * @param angle
 * @param inc
 * @return
 */
public double toInspi(double side, double angle, double inc)
{
	fd(side);
	rt(angle);
	//return toInspi(side, angle + inc, inc);
	return angle + inc;
}

/**
 * 
 * source: Abelson, H. and diSessa, A. (1980). Turtle Geometry. Cambridge, MA: MIT Press. (p.77)
 * @param side
 * @param angle
 * @param scale
 * @return
 */
public double toEquispi(double side, double angle, double scale)
{
	fd(side);
	lt(angle);
	//return toEquispi(side * scale, angle, scale);
	return side * scale;
}

/**
 * 
 * source: Abelson, H. and diSessa, A. (1980). Turtle Geometry. Cambridge, MA: MIT Press. (p.83)
 * @param length
 * @param level
 */
public void toBranch(double length, int level)
{
	if (level == 0) {return;}
	fd(length);
	lt(45.0);
	toBranch(length / 2, level - 1);
	rt(90.0);
	toBranch(length / 2, level - 1);
	// turn and back up to make the procedure state-transparent
	lt(45.0);
	bk(length);
}

/**
 * 
 * source: Abelson, H. and diSessa, A. (1980). Turtle Geometry. Cambridge, MA: MIT Press. (p.84)
 * @param length
 * @param angle
 * @param level
 */
public void toLBranch(double length, double angle, int level)
{
	// draw a long stem
	fd(2 * length);
	// do next level
	toNode(length, angle, level);
	// make LBranch state-transparent
	bk(2 * length);
}
public void toRBranch(double length, double angle, int level)
{
	// draw a long stem
	fd(length);
	// do next level
	toNode(length, angle, level);
	// make RBranch state-transparent
	bk(length);
}
protected void toNode(double length, double angle, int level)
{
	if (level == 0) {return;}
	// point along left branch and draw it
	lt(angle);
	toLBranch(length, angle, level - 1);
	// draw right branch
	rt(2 * angle);
	toRBranch(length, angle, level - 1);
	// make node state-transparent
	lt(angle);
}

/**
 * source: Abelson, H. and diSessa, A. (1980). Turtle Geometry. Cambridge, MA: MIT Press. (p.93)
 * @param size
 * @param level
 */
public void toLDragon(double size, int level)
{
	if (level == 0) {
		fd(size);
		return;
	}
	toLDragon(size, level - 1);
	lt(90);
	toRDragon(size, level - 1);
}
public void toRDragon(double size, int level)
{
	if (level == 0) {
		fd(size);
		return;
	}
	toLDragon(size, level - 1);
	rt(90);
	toRDragon(size, level - 1);
}
}

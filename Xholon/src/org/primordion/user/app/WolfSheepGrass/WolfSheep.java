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
package org.primordion.user.app.WolfSheepGrass;

import org.primordion.xholon.base.IPatch;
import org.primordion.xholon.base.ITurtlePatchColor;
import org.primordion.xholon.base.Turtle;
import org.primordion.xholon.util.MiscRandom;

/**
 * Wolf Sheep Grass.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on March 1, 2007)
 * @see The original NetLogo wolf sheep predation model is: Copyright 1998 Uri Wilensky. All rights reserved.
 * See http://ccl.northwestern.edu/netlogo/models/WolfSheepPredation for terms of use.
*/
public class WolfSheep extends Turtle implements CeWolfSheepGrass {
	
	// Parameters
	public static int initialNumberSheep = 100; // Sheep
	public static double sheepGainFromFood = 4.0; // Sheep
	public static double sheepReproduce = 4; // Sheep; a percentage
	public static int initialNumberWolves = 50; // Wolf
	public static double wolfGainFromFood = 20.0; // Wolf
	public static double wolfReproduce = 5; // Wolf; a percentage
	
	// Command IDs
	public static final int COMMANDID_HATCH = 100;

	// Variables
	public double energy = 0; // Turtle
	public boolean isGrabbed = false; // Sheep

	// Constructor
	public WolfSheep() {super();}
	
	// Parameter setters and getters
	// Sheep
	public static void setInitialNumberSheep(int InitialNumberSheepArg) {initialNumberSheep = InitialNumberSheepArg;}
	public static int getInitialNumberSheep() {return initialNumberSheep;}
	
	public static void setSheepGainFromFood(double SheepGainFromFoodArg) {sheepGainFromFood = SheepGainFromFoodArg;}
	public static double getSheepGainFromFood() {return sheepGainFromFood;}
	
	public static void setSheepReproduce(double SheepReproduceArg) {sheepReproduce = SheepReproduceArg;}
	public static double getSheepReproduce() {return sheepReproduce;}
	
	// Wolves
	public static void setInitialNumberWolves(int InitialNumberWolvesArg) {initialNumberWolves = InitialNumberWolvesArg;}
	public static int getInitialNumberWolves() {return initialNumberWolves;}
	
	public static void setWolfGainFromFood(double WolfGainFromFoodArg) {wolfGainFromFood = WolfGainFromFoodArg;}
	public static double getWolfGainFromFood() {return wolfGainFromFood;}
	
	public static void setWolfReproduce(double WolfReproduceArg) {wolfReproduce = WolfReproduceArg;}
	public static double getWolfReproduce() {return wolfReproduce;}

	public void initialize()
	{
		super.initialize();
	}

	public void postConfigure()
	{
		super.postConfigure();
		if (hasAlreadyMoved()) {return;}
		//System.out.println("turtle: " + getName() + " patch: " + parent.getName());
		switch(xhc.getId()) {
		case SheepCE:
			//set color white
		    //set size 1.5  ;; easier to see
		    //set label-color blue - 2
		    //set energy random (2 * sheep-gain-from-food)
		    //setxy random-xcor random-ycor
		    //set grabbed? false
			setTColor(ITurtlePatchColor.TPCOLOR_WHITE);
			energy = MiscRandom.getRandomDouble(0.0, 2.0 * sheepGainFromFood);
			if ((getWorldWidth() > 100) || (getWorldHeight() > 100)) {
				setxy(MiscRandom.getRandomDouble(-25.0, +25.0), MiscRandom.getRandomDouble(-25.0, +25.0));
			}
			else {
			setxy(MiscRandom.getRandomDouble(getMinPxcor(), getMaxPxcor()),
					MiscRandom.getRandomDouble(getMinPycor(), getMaxPycor()));
			}
			isGrabbed = false;
			pu();
			aggregate(1.0); // aggregate Sheep
			break;
		case WolfCE:
			//set color black
		    //set size 1.5  ;; easier to see
		    //set energy random (2 * wolf-gain-from-food)
		    //setxy random-xcor random-ycor
			setTColor(ITurtlePatchColor.TPCOLOR_BLACK);
			energy = MiscRandom.getRandomDouble(0.0, 2.0 * wolfGainFromFood);
			if ((getWorldWidth() > 100) || (getWorldHeight() > 100)) {
				setxy(MiscRandom.getRandomDouble(-25.0, +25.0), MiscRandom.getRandomDouble(-25.0, +25.0));
			}
			else {
			setxy(MiscRandom.getRandomDouble(getMinPxcor(), getMaxPxcor()),
					MiscRandom.getRandomDouble(getMinPycor(), getMaxPycor()));
			}
			pu();
			aggregate(1.0); // aggregate Wolf
			break;
		default:
			break;
		}
		//System.out.println("turtle: " + getName() + " patch: " + parent.getName());
	}
	
	public void act()
	{
		//System.out.println("turtle: " + getName() + " patch: " + parent.getName()
		//		+ " hasAlreadyMoved: " + hasAlreadyMoved());
		super.act();
		if (hasAlreadyMoved()) {return;}
		switch(xhc.getId()) {
		case SheepCE:
			move();
			//if grass? [
			//           set energy energy - 1  ;; deduct energy for sheep only if grass? switch is on
			//           eat-grass
			//         ]
			//         reproduce-sheep
			//         death
			if (Grass.growGrass) {
				energy--;
				eatGrass();
			}
			reproduceSheep();
			death();
			break;
		case WolfCE:
			move();
			//ask wolves [
			//            move
			//            set energy energy - 1  ;; wolves lose energy as they move
			//            catch-sheep
			//            reproduce-wolves
			//            death
			//          ]
			energy--;
			catchSheep();
			reproduceWolves();
			death();
			break;
		default:
			break;
		}
	}
	
	/**
	 * Move one unit in a randomized heading
	 * (Sheep and Wolf).
	 */
	protected void move()
	{
		//to move  ;; turtle procedure
		//  rt random-float 50 - random-float 50
		//  fd 1
		//end
		rt(MiscRandom.getRandomDouble(0.0, 50.0) - MiscRandom.getRandomDouble(0.0, 50.0));
		fd(1);
	}
	
	/**
	 * Eat grass
	 * (Sheep).
	 */
	protected void eatGrass()
	{
		//to eat-grass  ;; sheep procedure
		//  ;; sheep eat grass, turn the patch brown
		//  if pcolor = green [
		//    set pcolor brown
		//    set energy energy + sheep-gain-from-food  ;; sheep gain energy by eating
		//  ]
		//end
		if (getPcolor() == ITurtlePatchColor.TPCOLOR_GREEN) {
			setPcolor(ITurtlePatchColor.TPCOLOR_BROWN);
			((IPatch)parentNode).aggregate(-0.25);
			energy += sheepGainFromFood;
		}
	}
	
	/**
	 * Reproduce sheep
	 * (Sheep).
	 */
	protected void reproduceSheep()
	{
		//to reproduce-sheep  ;; sheep procedure
		//  if random-float 100 < sheep-reproduce [  ;; throw "dice" to see if you will reproduce
		//    set energy (energy / 2)          ;; divide energy between parent and offspring
		//    hatch 1 [ rt random-float 360 fd 1 ]   ;; hatch an offspring and move it forward 1 step
		//  ]
		//end
		if (MiscRandom.getRandomDouble(0.0, 100.0) < sheepReproduce) {
			energy = energy / 2.0;
			hatch(1, COMMANDID_HATCH);
			aggregate(1.0); // aggregate Sheep
		}
	}
	
	/**
	 * Reproduce wolves
	 * (Wolf).
	 */
	protected void reproduceWolves()
	{
		if (MiscRandom.getRandomDouble(0.0, 100.0) < wolfReproduce) {
			energy = energy / 2.0;
			hatch(1, COMMANDID_HATCH);
			aggregate(1.0); // aggregate Wolf
		}
	}
	
	/**
	 * Catch sheep
	 * (Wolf).
	 */
	protected void catchSheep()
	{
		//to catch-sheep  ;; wolf procedure
		//  let prey one-of (sheep-here             ;; grab a random sheep
		//                           with [not grabbed?]) ;; that no one else is grabbing
		//  if prey != nobody                             ;; did we get one?  if so,
		//    [ set grabbed?-of prey true                 ;; prevent other wolves from grabbing it
		//      ask prey [ die ]                          ;; kill it
		//      set energy energy + wolf-gain-from-food ]     ;; get energy from eating
		//end
		WolfSheep prey = (WolfSheep)getFirstSibling();
		while (prey != null) {
			if ((prey.getXhcId() == SheepCE) && (!prey.isGrabbed)) {
				prey.isGrabbed = true;
				prey.aggregate(-1.0); // aggregate Sheep
				prey.die();
				energy += wolfGainFromFood;
				break;
			}
			prey = (WolfSheep)getNextSibling();
		}
	}
	
	/**
	 * Die
	 * (Sheep and Wolf).
	 */
	protected void death()
	{
		//to death  ;; turtle procedure
		//  ;; when energy dips below zero, die
		//  if energy < 0 [ die ]
		//end
		//System.out.println("death:" + getName() + " " + org.primordion.xholon.base.XholonTime.timeStep);
		if (energy < 0) {
			aggregate(-1.0); // aggregate Sheep or Wolf
			die();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#performActivity(int)
	 */
	public void performActivity(int commandId)
	{
		switch (commandId) {
		case COMMANDID_HATCH: // from reproduceSheep and reproduceWolf [ rt random-float 360 fd 1 ]
			// give the new sheep or wolf half the energy that its mother (it's right-sibling) had
			energy = ((WolfSheep)getPreviousSibling()).energy;
			rt(MiscRandom.getRandomDouble(0.0, 360.0));
			fd(1);
			break;
		default:
			break;
		}
	}
}

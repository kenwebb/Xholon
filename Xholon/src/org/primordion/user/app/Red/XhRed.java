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

package org.primordion.user.app.Red;

import org.primordion.xholon.base.AbstractGrid;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.io.vrml.RGBTypeInt;
import org.primordion.xholon.util.MiscRandom;

/**
	Red application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   21/02/2007</p>
	<p>File:   .mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Wed Feb 21 13:46:13 EST 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class XhRed extends AbstractGrid implements CeRed {

// references to other Xholon instances; indices into the port array
public static final int SamplePort = 0;

// Signals, Events
public static final int SIG_SAMPLE = 100;

// Variables
public String roleName = null;
private double val; // AnotherPartOfTheSystem
protected static String appSpecificParam1 = "This is a test parameter."; // XhnParams

// colors
RGBTypeInt rgb = new RGBTypeInt();

// Constructor
public XhRed() {super();}

// Setters and Getters
public void setRoleName(String roleName) {this.roleName = roleName;}
public String getRoleName() {return roleName;}

public void setVal(double val) {this.val = val;}
public double getVal() {return val;}

public static void setAppSpecificParam1(String AppSpecificParam1Arg) {appSpecificParam1 = AppSpecificParam1Arg;}
public static String getAppSpecificParam1() {return appSpecificParam1;}

public void initialize()
{
	super.initialize();
}

// AnotherPartOfTheSystem
public void decVal(double decAmount)
{
	val -= decAmount;
}

// AnotherPartOfTheSystem
public void incVal(double incAmount)
{
	val += incAmount;
}

/*
 * @see org.primordion.xholon.base.AbstractGrid#setPorts()
 */
public void setPorts()
{
	switch(xhc.getId()) {
	case APartOfTheSystemCE:
		// Because this node inherits from AbstractGrid but is not really ad grid node,
		// it won't normally get its ports set.
		// It's being done manually here.
		port = new IXholon[1];
		break;
	default:
		super.setPorts();
		break;
	}
}

public void postConfigure()
{
	switch(xhc.getId()) {
	case APartOfTheSystemCE:
	{
		// do some sort of post configuration
	}
		break;
	case GridCellCE:
		rgb.r = MiscRandom.getRandomInt(0, 256);
		rgb.g = MiscRandom.getRandomInt(0, 256);
		rgb.b = MiscRandom.getRandomInt(0, 256);
		break;
	default:
		break;
	}
	super.postConfigure();
}

public void preAct()
{
	switch(xhc.getId()) {
	case APartOfTheSystemCE:
	{
		// 
	}
		break;
	default:
		break;
	}
	super.preAct();
}

public void act()
{
	switch(xhc.getId()) {
	case TheSystemCE:
		processMessageQ();
		break;
	case APartOfTheSystemCE:
	{
		double v = port[SamplePort].getVal();
		System.out.println("It's turning red, right before your eyes :-) " + v);
		port[SamplePort].setVal(v + 1);
	}
		break;
	case GridCellCE:
		if (rgb.g > rgb.r) {rgb.g--;}
		if (rgb.b > rgb.r) {rgb.b--;}
		if (rgb.r >= rgb.g && rgb.r >= rgb.b && rgb.r < 255) {
			if (rgb.r < rgb.g + 100 || rgb.r < rgb.b + 100) {
				rgb.r++;
			}
			else if (rgb.r < rgb.g + rgb.b) {
				rgb.r++;
			}
			else if (rgb.r < MiscRandom.getRandomInt(100, 255)) {
				rgb.r++;
			}
		}
		if (rgb.r == 255) {
			if (rgb.g > rgb.b) {
				rgb.g--;
			}
			else {
				if (rgb.b > 0) {
					rgb.b--;
				}
			}
		}
		
		//if (rgb.r < rgb.g) {rgb.r++;}
		//if (rgb.r < rgb.b) {rgb.r++;}
		break;
	default:
		break;
	}
	super.act();
}

public void postAct()
{
	switch(xhc.getId()) {
	case APartOfTheSystemCE:
	{
		// 
	}
		break;
	default:
		break;
	}
	super.postAct();
}

public void processReceivedMessage(IMessage msg)
{
	switch(xhc.getId()) {
	default:
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getClass() == StateMachineEntity.class) {
				((StateMachineEntity)node).doStateMachine(msg); // StateMachine
				break;
			}
			else {
				node = node.getNextSibling();
			}
		}
		if (node == null) {
			System.out.println("XhRed: message with no receiver " + msg);
		}
		break;
	}
}

public void performActivity(int activityId, IMessage msg)
{
	switch (activityId) {
	default:
		System.out.println("XhReda: performActivity() unknown Activity " + activityId);
		break;
	}
}

public boolean performGuard(int activityId, IMessage msg)
{
	switch (activityId) {
	default:
		System.out.println("XhRed: performGuard() unknown Activity " + activityId);
		return false;
	}
}

public String toString() {
	String outStr = getName();
	switch(xhc.getId()) {
	case APartOfTheSystemCE:
		outStr += " I'm just a small cog in the system.";
		break;
	case AnotherPartOfTheSystemCE:
		outStr += " val:" + getVal();
		break;
	case GridCellCE:
		outStr += " r:" + rgb.r + " g:" + rgb.g + " b" + rgb.b;
		break;
	default:
		/*if ((port != null) && (port.length > 0)) {
			outStr += " [";
			for (int i = 0; i < port.length; i++) {
				if (port[i] != null) {
					outStr += " port:" + port[i].getName();
				}
			}
			outStr += "]";
		} */
		break;
	}
	return outStr;
}

}

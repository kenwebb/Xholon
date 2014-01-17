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

package org.primordion.user.app.climatechange.carboncycle03;

import org.primordion.xholon.base.XholonWithPorts;

/**
	carboncycle03 application - Xholon Java
	<p>Xholon 0.8.1 http://www.primordion.com/Xholon</p>
*/
public class Xhcarboncycle03 extends XholonWithPorts implements Cecarboncycle03 {

// Signals, Events

/**
 * Get an array of signal IDs.
 * @return An array.
 */
public static int[] getSignalIDs()
{
	int[] signalIDs = {
			
	};
	return signalIDs;
}

/**
 * Get an array of signal names.
 * @return An array.
 */
public static String[] getSignalNames()
{
	String[] signalNames = {
			
	};
	return signalNames;
}

// Variables
public String roleName = null;
private double val;

// Constructor
public Xhcarboncycle03() {super();}

// Setters and Getters
public void setRoleName(String roleName) {this.roleName = roleName;}
public String getRoleName() {return roleName;}

public void setVal(double val) {this.val = val;}
public double getVal() {return val;}
public void incVal(double val) {this.val += val;}
public void decVal(double val) {this.val -= val;}

public void initialize()
{
	super.initialize();
}

public void postConfigure()
{
	switch(xhc.getId()) {
	default:
		break;
	}
	super.postConfigure();
}

public void act()
{
	switch(xhc.getId()) {
	case CarbonCycleSystemCE:
		processMessageQ();
		break;
	default:
		break;
	}
	super.act();
}

public String toString() {
	String outStr = getName();
	return outStr;
}

}

/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2005, 2006, 2007, 2008 Ken Webb
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

package org.primordion.user.app.Collisions;

import java.util.Vector;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.util.IJavaTypes;
import org.primordion.xholon.util.Misc;

/**
 * Collisions. This is the detailed behavior of a sample Xholon application.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on August 11, 2005)
 */
public class XhCollisions extends XholonWithPorts {

	// references to other Xholon instances
	// indices into the port array
	private static final int P_POP = 0;
	private static final int P_POP_TOTAL = 1;
	
	public int val = 0;
	public float rate = 0.0f;
	public float proportionLicensed = 0.0f;
	public int avgVehicleKilometers = 0;
	public String roleName = null;
	
	/** Constructor. */
	public XhCollisions() {}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
		val = 0;
		rate = 0.0f;
		proportionLicensed = 0.0f;
		avgVehicleKilometers = 0;
		roleName = null;
	}
	
	// setters and getters
	public float getRate() {return rate;}
	public void setRate(float rate) {this.rate = rate;}
	public float getProportionLicensed() {return proportionLicensed;}
	public void setProportionLicensed(float proportionLicensed) {this.proportionLicensed = proportionLicensed;}
	public int getAvgVehicleKilometers() {return avgVehicleKilometers;}
	public void setAvgVehicleKilometers(int avgVehicleKilometers) {this.avgVehicleKilometers = avgVehicleKilometers;}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getVal()
	 */
	public double getVal() {return val;}

	/*
	 * @see org.primordion.xholon.base.IXholon#setVal(int)
	 */
	public void setVal(int val) {this.val = val;}

	/*
	 * @see org.primordion.xholon.base.IXholon#setRoleName(java.lang.String)
	 */
	public void setRoleName(String roleName) {this.roleName = roleName;}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getRoleName()
	 */
	public String getRoleName() {return roleName;}

	/*
	 * @see org.primordion.xholon.base.Xholon#setAttributeVal(java.lang.String, java.lang.String)
	 */
	// this is only needed if using JavaMicro reflection
	public int setAttributeVal(String attrName, String attrVal)
	{
		int classType = super.setAttributeVal(attrName, attrVal);
		if (classType == IJavaTypes.JAVACLASS_UNKNOWN) {
			if ("rate".equals(attrName)) {
				rate = Misc.atof(attrVal, 0);
				classType = IJavaTypes.JAVACLASS_float;
			}
			else if ("proportionLicensed".equals(attrName)) {
				proportionLicensed = Misc.atof(attrVal, 0);
				classType = IJavaTypes.JAVACLASS_float;
			}
			else if ("avgVehicleKilometers".equals(attrName)) {
				avgVehicleKilometers = Misc.atoi(attrVal, 0);
				classType = IJavaTypes.JAVACLASS_int;
			}
		}
		return classType;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#configure()
	 */
	public void configure()
	{
		// compute port[] values manually
		if (xhc.getParentNode().getName().equals("DriverGmGf")) {
			if (!(((XhCollisions)parentNode).getXhc().getName().equals("Drivers"))) {
				// get references to relevant population data, to compute Driver totals
				// get ref to pop corresponding to this age group and sex
				String targetBecName = "Pop" + ((XhCollisions)parentNode).getXhc().getName().substring(6);
				
				IXholon node = getXPath().evaluate(
						"../../../Populations/" + targetBecName + "/PopGmGf", this);
				
				if (xhc.getName().equals("DriverGm")) {
					port[P_POP] = (XhCollisions)node;
				}
				else { // "Driver-Gf"
					port[P_POP] = (XhCollisions)node.getNextSibling();
				}
				// get ref to total pop corresponding to this sex
				node = getXPath().evaluate(
						"../../../Populations/PopA6599", this);
				node = node.getNextSibling();
				
				if (xhc.getName().equals("DriverGm")) {
					port[P_POP_TOTAL] = (XhCollisions)node;
				}
				else {
					port[P_POP_TOTAL] = (XhCollisions)node.getNextSibling();
				}
			}
		}
		super.configure();
	} // end configure()
	
	/*
	 * @see org.primordion.xholon.base.IXholon#preAct()
	 */
	public void preAct()
	{
		int i;
		if (xhc.getParentNode().getName().equals("PopGmGf")) {
			if (((XhCollisions)parentNode).getXhc().getName().equals("Populations")) {
				// calculate total population
				int totalPop = 0;
				Vector v = parentNode.getChildNodes(false); // get all Pop-Annnn
				XhCollisions node;
				for (i = 0; i < 7; i++) {
					node = (XhCollisions)v.elementAt(i);
					if (xhc.getName().equals("PopGm")) {
						node = (XhCollisions)node.getFirstChild();
					}
					else { // "Pop-Gf"
						node = (XhCollisions)node.getFirstChild().getNextSibling();
					}
					totalPop += node.val;
				}
				val = totalPop;
			}
		}
		super.preAct();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#act()
	 */	
	public void act()
	{
		if (xhc.getParentNode().getName().equals("PopGmGf")) {
			val += val * rate;
		}
		super.act();
	}
	
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String outStr = getName();
		if ((port != null) && (port.length > 0)) {
			outStr += " [";
			for (int i = 0; i < port.length; i++) {
				if (port[i] != null) {
					outStr += " port:" + port[i].getName();
				}
			}
			outStr += "]";
		}
		outStr += " ";
		if (xhc.hasAncestor("Population")) {
			outStr += " [val:" + val
					+ " rate:" + rate
					+ "]";
		}
		else if (xhc.hasAncestor("Driver")) {
			outStr += " [proportionLicensed:" + proportionLicensed
					+ " avgVehicleKilometers:" + avgVehicleKilometers
					+ "]";
		}
		else if (xhc.getName().equals("Scenario")) {
			outStr += " [rate:" + rate
					+ "]";
		}
		return outStr;
	}
	
	/**
	 * Utility function to determine the rate that will convert startVal into expectedEndVal.
	 * @param startVal Starting value.
	 * @param expectedEndVal Expected end value.
	 * @param numYears Number of years or other units of time.
	 * @return The rate.
	 */
	private float converge(int startVal, int expectedEndVal, int numYears)
	{
		int TOLERANCE = 2;
		float LOW_RATE = 0.0f; // 0%
		float HIGH_RATE = 0.1f; // 10%
		float rate = (LOW_RATE + HIGH_RATE) / 2; // start with a rate half way between LOW_RATE and HIGH_RATE
		float prevLowRate = LOW_RATE;
		float prevHighRate = HIGH_RATE; 
		int computedEndVal;
		boolean done = false;
		boolean isNeg = false;
		if (startVal > expectedEndVal) { // rate will need to be negative
			isNeg = true;
			int temp = startVal;
			startVal = expectedEndVal;
			expectedEndVal = temp;
		}
		
		while (!done) {
			computedEndVal = startVal;
			for (int i = 0; i < numYears; i++) {
				computedEndVal += computedEndVal * rate;
			}
			if (Math.abs(computedEndVal - expectedEndVal) > TOLERANCE) {
				if (computedEndVal > expectedEndVal) {
					prevHighRate = rate;
					rate = (prevLowRate + rate) / 2;
				}
				else {
					prevLowRate = rate;
					rate = (rate + prevHighRate) / 2;
				}
			}
			else {
				done = true;
			}
		}
		if (isNeg) {
			rate *= -1.0f;
		}
		return rate;
	}
	
	/**
	 * main
	 * @param args none
	 */
	public static void main(String[] args) {
		XhCollisions col = new XhCollisions();
		
		System.out.println(col.converge(4038600, 4343300, 25));
		System.out.println(col.converge(3831300, 4108300, 25));
		System.out.println();
		System.out.println(col.converge(1066900, 1065800, 25));
		System.out.println(col.converge(1023200, 1022100, 25));
		System.out.println();
		System.out.println(col.converge(2195300, 2410700, 25));
		System.out.println(col.converge(2143200, 2343900, 25));
		System.out.println();
		System.out.println(col.converge(2656400, 2617400, 25));
		System.out.println(col.converge(2628900, 2565300, 25));
		System.out.println();
		System.out.println(col.converge(2236600, 2503400, 25));
		System.out.println(col.converge(2251000, 2447700, 25));
		System.out.println();
		System.out.println(col.converge(1432400, 2500000, 25));
		System.out.println(col.converge(1478800, 2500200, 25));
		System.out.println();
		System.out.println(col.converge(1677200, 3666200, 25));
		System.out.println(col.converge(2253900, 4345900, 25));
	}
}

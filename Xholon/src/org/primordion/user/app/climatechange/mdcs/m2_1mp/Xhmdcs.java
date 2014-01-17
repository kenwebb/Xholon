/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2011 Ken Webb
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

package org.primordion.user.app.climatechange.mdcs.m2_1mp;

import org.primordion.xholon.base.IIntegration;
import org.primordion.xholon.base.XholonWithPorts;

/**
 * MDCS m2_1 model, with message passing.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 4, 2011)
 */
public class Xhmdcs extends XholonWithPorts implements Cemdcs {

	// time step multiplier
	public static int timeStepMultiplier = IIntegration.M_128;
	
	// delta t (an increment in time)
	protected static double dt = 1.0 / (double)timeStepMultiplier;
	
	/**
	 * Initial value for various doubles until the simulation reaches basic
	 * stability.
	 */
	public static final double VAL_NULL = Double.NEGATIVE_INFINITY;

	// signals
	
	/** Shortwave radiation (visible/UV), typically originating at a star such as the Sun. */
	public static final int SIG_SW = 101;
	
	/** Reflected shortwave radiation, typically from a planet's atmosphere or surface. */
	public static final int SIG_SW_REFLECTED = 102;
	
	/** Longwave radiation (infrared), typically originating at a planet such as Earth. */
	public static final int SIG_LW = 201;
	
	/**
	 * Get an array of signal IDs.
	 * @return An array.
	 */
	public static int[] getSignalIDs()
	{
		int[] signalIDs = {
				SIG_SW,
				SIG_SW_REFLECTED,
				SIG_LW
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
				"shortwave",
				"reflected",
				"longwave"
		};
		return signalNames;
	}

	// Variables
	public String roleName = null;
	private double val = VAL_NULL;
	private String units = null;

	// Constructor
	public Xhmdcs() {
		super();
	}

	// Setters and Getters
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setVal(double val) {
		this.val = val;
	}

	public void setVal(Object val) {
		this.val = (Double)val;
	}

	public double getVal() {
		return val;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public String getUnits() {
		return units;
	}

	public static int getTimeStepMultiplier() {
		return timeStepMultiplier;
	}

	public static void setTimeStepMultiplier(int timeStepMultiplier) {
		Xhmdcs.timeStepMultiplier = timeStepMultiplier;
		dt = 1.0 / (double)timeStepMultiplier;
	}
	
	public static double getDt() {
		return dt;
	}

	/**
	 * Convert a temperature from Kelvin to Celcius. [°C] = [K] − 273.15
	 * @param temperature A temperature in degrees Kelvin.
	 * @return The temperature in degrees Celcius.
	 */
	protected double k2c(double temperature) {
		return temperature - 273.15;
	}

	public void act() {
		switch (xhc.getId()) {
		case TheSystemCE:
			processMessageQ();
			break;
		default:
			break;
		}
		super.act();
	}
	
	public int setAttributeVal(String attrName, String attrVal) {
		if ("units".equals(attrName)) {
			setUnits(attrVal);
		}
		else {
			return super.setAttributeVal(attrName, attrVal);
		}
		return 0;
	}
	
	public String toString() {
		String outStr = getName();
		if (this.getVal() != VAL_NULL) {
			outStr += " " + this.getVal();
		}
		if (this.getUnits() != null) {
			outStr += " {" + this.getUnits() + "}";
		}
		return outStr;
	}

}

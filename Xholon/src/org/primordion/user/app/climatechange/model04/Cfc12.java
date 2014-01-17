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

package org.primordion.user.app.climatechange.model04;

import org.primordion.xholon.base.IXholon;

/**
 * CFC-12 (a halocarbon) is a greenhouse gas (GHG).
 * This class is an active object with access to some other object that contains
 * the current level of this greenhouse gas (GHG) in the atmosphere.
 * As an active object, it helps to implement the greenhouse effect.
 * It can act as a proxy to a separate Carbon Cycle model, such as carboncycle01.
 * Or it can implement some other way of changing the quantity of the GHG.
 * @author ken
 *
 */
public class Cfc12 extends Xhmodel04 {
	
	/** Level of this GHG (in ppmv) in the atmosphere in 1750, the base year. */
	//private static final double GHG_LEVEL_PPMV_1750 = 278.0; // CO2
	
	/** The atmosphere that this GHG is part of. */
	private IXholon atmosphere = null;
	
	/** An optional passive object that knows the current level of this GHG. */
	private IXholon ghgLevelObj = null;
	
	/** Current level of this GHG in the atmosphere.
	 * In billions of tonnes of carbon (GtC).
	 **/
	private double ghgLevel = 750.0; // CO2 default value
	
	public void act() {
		if (ghgLevelObj != null) {
			ghgLevel = ghgLevelObj.getVal();
		}
		// Increment the value of backRadiation in Atmosphere.
		((Atmosphere)atmosphere).incBackRadiation(getGhgRf());
		super.act();
	}

	public IXholon getGhgLevelObj() {
		return ghgLevelObj;
	}

	public void setGhgLevelObj(IXholon ghgLevelObj) {
		this.ghgLevelObj = ghgLevelObj;
	}

	public double getGhgLevel() {
		return ghgLevel;
	}

	public void setGhgLevel(double ghgLevel) {
		this.ghgLevel = ghgLevel;
	}
	
	/**
	 * Get the Methane level in the atmosphere.
	 * @return The current Methane level in units of ppmv.
	 */
	public double getGhgLevelPpmv() {
		return 1.79; // is this the correct current level?
	}
	
	/**
	 * Get the current value of the GHG radiative forcing (delta F).
	 * "The present radiative forcing due to CFC-12 is therefore 0.17 Wm−2, which is the third highest forcing among the well-mixed greenhouse gases." (TAR)
	 * @see IPCC TAR ch.6
	 * @return The current radiative forcing (RF) for this GHG in the atmosphere,
	 * in units of W/m2
	 */
	public double getGhgRf() {
		//return 5.35 * Math.log(getGhgLevelPpmv() / GHG_LEVEL_PPMV_1750); // CO2
		return 0.17;
	}

	public IXholon getAtmosphere() {
		return atmosphere;
	}

	public void setAtmosphere(IXholon atmosphere) {
		this.atmosphere = atmosphere;
	}
	
}

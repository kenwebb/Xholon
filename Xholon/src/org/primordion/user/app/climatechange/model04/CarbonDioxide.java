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
 * Carbon Dioxide (CO2) is a greenhouse gas (GHG).
 * This class is an active object with access to some other object that contains
 * the current level of this GHG in the atmosphere.
 * As an active object, it helps to implement the greenhouse effect.
 * It can act as a proxy to a separate Carbon Cycle model, such as carboncycle01.
 * Or it can implement some other way of changing the quantity of the GHG.
 * @author ken
 *
 */
public class CarbonDioxide extends Xhmodel04 {
	
	/** Level of this GHG (in ppmv) in the atmosphere in 1750, the base year. */
	private static final double GHG_LEVEL_PPMV_1750 = 278.0; // CO2
	
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
	 * Convert the internal GHG level in billions of tonnes of carbon (GtC),
	 * to parts per million by volume (ppmv).
	 * "1 ppm by volume of atmosphere CO2 = 2.13 Gt C
    (Uses atmospheric mass (Ma) = 5.137 × 1018 kg)"
     * The current 2010/2011 value is 390.0 or 392.0 ppmv.
	 * @see http://cdiac.ornl.gov/pns/convert.html
	 * @return The current CO2 level in units of ppmv.
	 */
	public double getGhgLevelPpmv() {
		return ghgLevel / 2.13;
	}
	
	/**
	 * Get the current value of the GHG radiative forcing (delta F).
	 * "The forcing since pre-industrial times" is estimated to be 1.46 W/m2 (TAR)
	 * @see IPCC TAR ch.6
	 * @return The current radiative forcing (RF) for this GHG in the atmosphere,
	 * in units of W/m2
	 */
	public double getGhgRf() {
		return 5.35 * Math.log(getGhgLevelPpmv() / GHG_LEVEL_PPMV_1750); // CO2
	}

	public IXholon getAtmosphere() {
		return atmosphere;
	}

	public void setAtmosphere(IXholon atmosphere) {
		this.atmosphere = atmosphere;
	}
	
}

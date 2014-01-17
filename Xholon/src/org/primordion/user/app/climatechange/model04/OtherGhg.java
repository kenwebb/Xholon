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
 * The TAR (ch. 6) lists the RF for numerous other greenhouse gases (GHG), not otherwise included in this model:
<pre>
CF4         0.003
C2F6        0.001
SF6         0.002
HFC-23      0.002
HFC-134a    0.001
HFC-152a    0.0
CFC-13      0.001
CFC-113     0.03
CFC-114     0.005
CFC-115     0.001
CCl4        0.01
CH3CCl3     0.004
HCFC-22     0.03
HCFC-141b   0.001
HCFC-142b   0.002
Halon-1211  0.001
Halon-1301  0.001
----------------------
SUM         0.095 W/m2
</pre>
 * This class is an active object with access to some other object that contains
 * the current level of this greenhouse gas (GHG) in the atmosphere.
 * As an active object, it helps to implement the greenhouse effect.
 * It can act as a proxy to a separate Carbon Cycle model, such as carboncycle01.
 * Or it can implement some other way of changing the quantity of the GHG.
 * @author ken
 *
 */
public class OtherGhg extends Xhmodel04 {
	
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
	 * This is the sum of all other greenhouse gases (TAR ch. 6).
	 * @see IPCC TAR ch.6
	 * @return The current radiative forcing (RF) for this GHG in the atmosphere,
	 * in units of W/m2
	 */
	public double getGhgRf() {
		//return 5.35 * Math.log(getGhgLevelPpmv() / GHG_LEVEL_PPMV_1750); // CO2
		return 0.095;
	}

	public IXholon getAtmosphere() {
		return atmosphere;
	}

	public void setAtmosphere(IXholon atmosphere) {
		this.atmosphere = atmosphere;
	}
	
}

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
 * The Sun generates and radiates energy into space.
 * @author ken
 *
 */
public class Sun extends Xhmodel04 {
	
	/**
	 * solar constant
	 * Solar radiation per unit area (W * m^-2), at Earth distance (1 AU)
	 * "Earth receives 174 petawatts (PW) of incoming solar radiation (insolation)
	 * at the upper atmosphere" per year (wikipedia Solar_energy).
	 * Earth mean radius is 6,371.0 km (wikipedia Earth) = 6371000 meters.
	 * Area of a circle is PI * r^2 .
	 * A petawatt (PW) is 10^15 .
	 * 6371000^2 * PI * 1367 = 1.743^17 = 174.3^15 .
	 */
	private double solarConstant = 1367.0;
	
	/** port */
	private IXholon space = null;

	public void act() {
		// send energy through space
		space.sendMessage(SIG_SOLAR_CONSTANT, new Double(solarConstant), this);
		super.act();
	}
	
	public IXholon getSpace() {
		return space;
	}

	public void setSpace(IXholon space) {
	  System.out.println("Sun setSpace( " + space);
		this.space = space;
	}

	public double getSolarConstant() {
		return solarConstant;
	}

	public void setSolarConstant(double solarConstant) {
		this.solarConstant = solarConstant;
	}

}

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
 * Calculate a post-albedo value.
 * @author ken
 *
 */
public class SurfaceAlbedoAO extends Xhmodel04 {
	
	private IXholon planet = null;
	
	public void act() {
		double reflectedSolarRadiation = ((Surface)planet).getAlbedo() * ((Surface)planet).getNetShortwaveFlux();
		((Surface)planet).setReflectedSolarRadiation(reflectedSolarRadiation);
		super.act();
	}

	public IXholon getPlanet() {
		return planet;
	}

	public void setPlanet(IXholon planet) {
		this.planet = planet;
	}
}

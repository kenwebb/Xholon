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

package org.primordion.user.app.climatechange.mdcs.m2_2mp;

import org.primordion.xholon.base.IXholon;

/**
 * Water on the surface of a planet.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 4, 2011)
 */
public class Water extends Xhmdcs {
	
	// properties of the water
	private IXholon density = null;
	private IXholon specificHeat = null;
	private IXholon depth = null;
	private IXholon heatCapacity = null;
	
	public void postConfigure() {
		heatCapacity.setVal(density.getVal() * depth.getVal() * specificHeat.getVal());
		super.postConfigure();
	}

	public IXholon getDensity() {
		return density;
	}

	public void setDensity(IXholon density) {
		this.density = density;
	}

	public IXholon getSpecificHeat() {
		return specificHeat;
	}

	public void setSpecificHeat(IXholon specificHeat) {
		this.specificHeat = specificHeat;
	}

	public IXholon getDepth() {
		return depth;
	}

	public void setDepth(IXholon depth) {
		this.depth = depth;
	}

	public IXholon getHeatCapacity() {
		return heatCapacity;
	}

	public void setHeatCapacity(IXholon heatCapacity) {
		this.heatCapacity = heatCapacity;
	}

}

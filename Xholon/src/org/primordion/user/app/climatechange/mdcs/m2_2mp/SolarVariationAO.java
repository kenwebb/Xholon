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
 * Solar variation object.
 * This optional active object (AO) can be pasted into the model at runtime.
 * To do this, copy the following XML to the clipboard,
 * and paste it as the last child of Sun:
 * <p>&lt;SolarVariationAO/&gt;</p>
 * This object can be used to test the entire model.
 * It creates a large growing perturbation that should cause large changes in all other values in the model.
 * @author ken
 *
 */
public class SolarVariationAO extends Xhmdcs {
	
	/** The star (ex: Sun) where the variation is located. */
	private IXholon star = null;
	
	/** 1.4% increase in solarConstant causes 1 degree increase in Earth temperature */
	private double solarConstantMultiplier = 0.002; //0.014;
	
	public void postConfigure() {
		if (star == null) {
			star = this.getParentNode();
		}
		super.postConfigure();
	}
	
	public void act() {
		double solarConstant = ((Sun)star).getSolarConstant() * (1.0 + solarConstantMultiplier * dt);
		((Sun)star).setSolarConstant(solarConstant);
		super.act();
	}

	public IXholon getStar() {
		return star;
	}

	public void setStar(IXholon star) {
		this.star = star;
	}

	public double getSolarConstantMultiplier() {
		return solarConstantMultiplier;
	}

	public void setSolarConstantMultiplier(double solarConstantMultiplier) {
		this.solarConstantMultiplier = solarConstantMultiplier;
	}
	
}

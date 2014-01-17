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
 * Sunspot active object.
 * This class simulates the observed 11 year sunspot cycle.
 * This optional active object (AO) can be pasted into the model at runtime.
 * To do this, copy the following XML to the clipboard,
 * and paste it as the last child of Sun:
 * <p>&lt;SunspotCycleAO/&gt;</p>
 * <p>"The Sun is a weakly variable star and its luminosity therefore fluctuates.
 * The major fluctuation is the eleven-year solar cycle (sunspot cycle),
 * which causes a periodic variation of about ±0.1%.
 * Any other variation over the last 200–300 years is thought to be much smaller than this."</p>
 * <p>n = 50•sin{2π/11[(t – 1750) + 11/4]} + 60</p>
 * @see <a href="http://en.wikipedia.org/wiki/Solar_luminosity">Solar luminosity</a>
 * @see <a href="http://au.answers.yahoo.com/question/index?qid=20110522003213AA7CalY">sunspot cycle calculation</a>
 * @author ken
 *
 */
public class SunspotCycleAO extends Xhmdcs {
	
	/** The star (ex: Sun) where the sunspots are located. */
	private IXholon star = null;
	
	private double baseSolarConstant = 0.0;
	private double minSunspots = 0.0;
	private double multiplier = 1.0;
	
	public void postConfigure() {
		if (star == null) {
			star = this.getParentNode();
		}
		baseSolarConstant = ((Sun)star).getSolarConstant();
		// calc sunspot range
		minSunspots = calcNumSunspots(6);
		double maxSunspots = calcNumSunspots(1);
		// calc solar constant diff range
		double minSolarConstant = baseSolarConstant * -0.001;
		double maxSolarConstant = baseSolarConstant * 0.001;
		// calc multiplier
		multiplier = (maxSolarConstant - minSolarConstant) / (maxSunspots - minSunspots);
		super.postConfigure();
	}
	
	public void act() {
		double solarConstantDiff = calcSolarConstantDiff(getApp().getTimeStep());
		((Sun)star).setSolarConstant(baseSolarConstant + solarConstantDiff);
		super.act();
	}
	
	/**
	 * Calculate an average solar constant difference for a specific year.
	 * @param year ex: 1751 or 1980
	 * @return The difference in the solar constant.
	 */
	protected double calcSolarConstantDiff(int year) {
		return (calcNumSunspots(year) - minSunspots) * multiplier;
	}
	
	/**
	 * Calculate the number of sunspots for a specific year.
	 * @param year ex: 1750 or 2011
	 * @return A number of sunspots.
	 */
	protected double calcNumSunspots(int year) {
		return 50 * Math.sin(2*Math.PI/11 * (year + (11 / 4))) + 60;
	}

	public IXholon getStar() {
		return star;
	}

	public void setStar(IXholon star) {
		this.star = star;
	}

}

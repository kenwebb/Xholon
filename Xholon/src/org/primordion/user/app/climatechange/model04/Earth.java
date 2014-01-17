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

/**
 * Earth
 * @author ken
 *
 */
public class Earth extends Xhmodel04 {
	
	/** Earth's average albedo. */
	private double albedo = 0.31;
	/** 3.3% increase in albedo causes 1 degree decrease in Earth temperature. */
	private double albedoMultiplier = 1.0; //1.033;
	
	/** Earth's effective emissivity. */
	private double emissivity = 0.612; // or 0.64 ???
	/** 1.4% increase in emissivity causes 1 degree decrease in Earth temperature. */
	private double emissivityMultiplier = 1.0; //1.014;
	
	/** Earth's current temperature in degrees Kelvin (this should be a calculated value). */
	private double temperature = 288.0; // 15C
	
	/** Earth's radius (meters). */
	private double radius = 6.371e6;
	
	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getAlbedo() {
		return albedo;
	}

	public void setAlbedo(double albedo) {
		this.albedo = albedo;
	}

	public double getAlbedoMultiplier() {
		return albedoMultiplier;
	}

	public void setAlbedoMultiplier(double albedoMultiplier) {
		this.albedoMultiplier = albedoMultiplier;
	}

	public double getEmissivity() {
		return emissivity;
	}

	public void setEmissivity(double emissivity) {
		this.emissivity = emissivity;
	}
	
	public double getEmissivityMultiplier() {
		return emissivityMultiplier;
	}

	public void setEmissivityMultiplier(double emissivityMultiplier) {
		this.emissivityMultiplier = emissivityMultiplier;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}
	
}

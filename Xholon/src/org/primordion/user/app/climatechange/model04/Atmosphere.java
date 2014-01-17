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

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;

/**
 * Atmosphere.
 * All values, unless otherwise specified, are in units of W/m2 .
 * @author ken
 *
 */
public class Atmosphere extends Xhmodel04 {
	
	/** Shortwave absorption factor.
	 * See my notes from August 21, 2011 on a separate sheet of paper,
	 * where I conclude that absorbedByAtmosphere is best calculated as a percentage of incomingSolarRadiation.
	 * See K&T 1997 p. 204.
	 * See Trenberth 2009 p. 317.
	 * See Kim and Ramanathan 2008 abstract.
	 * Contributors to this quantity (67), in order of importance, are:
	 *   H2O (38), O3 (15), overlapping effects (12), O2 (2), CO2 (0) (K&T 1997 p. 204).
	 * There's a large uncertainty in this value, from 19% to 23% (or more).
	 */
	private double shortwaveAbsorptionFactor = 0.196; // range 0.19 to 0.23
	
	// ports
	private IXholon topOfAtmosphere = null;
	private IXholon surface = null;
	
	/** Earth's incoming solar radiation per unit area (W * m^-2). Earth's global average solar flux. */
	private double incomingSolarRadiation = VAL_NULL;
	
	/** Reflected solar radiation from clouds, aerosols, atmospheric gases. */
	private double reflectedSolarRadiation = VAL_NULL;
	
	/** Net shortwave flux that will be absorbed or reflected by the Surface. */
	private double netShortwaveFlux = VAL_NULL;
	
	/** Incoming solar radiation absorbed by atmosphere as heat. */
	private double absorbedByAtmosphere = VAL_NULL;
	
	/** Sensible heat flux SH (incoming from surface). */
	private double sensibleHeatFlux = VAL_NULL;
	
	/** Latent heat flux LH (incoming from surface). */
	private double latentHeatFlux = VAL_NULL;
	
	/** Surface radiation (incoming from surface). */
	private double surfaceRadiation = VAL_NULL;
	
	/** Atmospheric Window from surface to top of atmosphere. */
	private double atmosphericWindow = 40.0; // initial value from K&T
	
	/** Emitted by atmosphere. */
	private double emittedByAtmosphere = 165.0; // initial value from K&T
	
	/** Emitted by clouds. */
	private double emittedByClouds = 30.0; // initial value from K&T
	
	/**
	 * Back radiation from natural base levels (year 1750) of greenhouse gases.
	 * This value should be held constant during the lifetime of the simulation,
	 * although it could be changed during initialization.
	 * I assume this is just the K&T value (324) minus the IPCC GHG radiative forcing (2.43).
	 */
	private double backRadiationBase = 321.0;
	
	/** Back radiation from greenhouse gases.
	 * This is equal to a constant base quantity plus a variable man-made increment.
	 */
	private double backRadiation = 2.4; // 2.43
	
	/** Outgoing longwave radiation. */
	private double outgoingLongwave = VAL_NULL; //165.0 + 30.0 + 40.0;
	
	/** Total accumulated longwave infrared heat energy absorbed by the atmosphere, from all sources. */
	private double heat = 0.0;
	
	/** Albedo of the atmosphere. Based on planetary albedo of 0.31 */
	private double albedo = 0.225;
	
	/** outgoing proportions (upwards from the atmosphere) */
	private double emittedByAtmospherePro = 0.0; // 
	private double emittedByCloudsPro = 0.0; // 
	
	public void postConfigure() {
		double outgoingTotal = atmosphericWindow + emittedByAtmosphere + emittedByClouds
			+ backRadiationBase + backRadiation;
		emittedByAtmospherePro = emittedByAtmosphere / outgoingTotal;
		emittedByCloudsPro = emittedByClouds / outgoingTotal;
		super.postConfigure();
	}
	
	public void act() {
		if (incomingSolarRadiation != VAL_NULL) {
			backRadiation = backRadiationBase;
			super.act(); // invoke children Active Objects first
			absorbedByAtmosphere = incomingSolarRadiation * shortwaveAbsorptionFactor; // 67
			heat += absorbedByAtmosphere;
			netShortwaveFlux = incomingSolarRadiation - reflectedSolarRadiation - absorbedByAtmosphere;
			double incomingTotal = absorbedByAtmosphere + sensibleHeatFlux + latentHeatFlux + surfaceRadiation;
			emittedByAtmosphere = incomingTotal * emittedByAtmospherePro;
			emittedByClouds = incomingTotal * emittedByCloudsPro;
			atmosphericWindow = incomingTotal - emittedByAtmosphere - emittedByClouds - backRadiation;
			outgoingLongwave = emittedByAtmosphere + emittedByClouds + atmosphericWindow;
			
			surface.sendMessage(SIG_NET_SHORTWAVE_FLUX, new Double(netShortwaveFlux), this);
			topOfAtmosphere.sendMessage(SIG_REFLECTED_SOLAR_RADIATION, new Double(reflectedSolarRadiation), this);
			topOfAtmosphere.sendMessage(SIG_OUTGOING_LONGWAVE_RADIATION, new Double(outgoingLongwave), this);
			surface.sendMessage(SIG_BACK_RADIATION, new Double(backRadiation), this);
		}
	}
	
	public void processReceivedMessage(IMessage msg) {
		switch(msg.getSignal()) {
		case SIG_INCOMING_SOLAR_RADIATION:
			incomingSolarRadiation = ((Double)msg.getData()).doubleValue();
			break;
		case SIG_REFLECTED_SOLAR_RADIATION:
			// reflected by surface; relay to TopOfAtmosphere
			topOfAtmosphere.sendMessage(SIG_REFLECTED_SOLAR_RADIATION, msg.getData(), this);
			break;
		case SIG_SENSIBLE_HEAT_FLUX:
			sensibleHeatFlux = ((Double)msg.getData()).doubleValue();
			break;
		case SIG_LATENT_HEAT_FLUX:
			latentHeatFlux = ((Double)msg.getData()).doubleValue();
			break;
		case SIG_SURFACE_RADIATION:
			surfaceRadiation = ((Double)msg.getData()).doubleValue();
			break;
		default:
			break;
		}
	}

	public IXholon getTopOfAtmosphere() {
		return topOfAtmosphere;
	}

	public void setTopOfAtmosphere(IXholon topOfAtmosphere) {
		this.topOfAtmosphere = topOfAtmosphere;
	}

	public IXholon getSurface() {
		return surface;
	}

	public void setSurface(IXholon surface) {
		this.surface = surface;
	}

	public double getIncomingSolarRadiation() {
		return incomingSolarRadiation;
	}

	public void setIncomingSolarRadiation(double incomingSolarRadiation) {
		this.incomingSolarRadiation = incomingSolarRadiation;
	}

	public double getReflectedSolarRadiation() {
		return reflectedSolarRadiation;
	}

	public void setReflectedSolarRadiation(double reflectedSolarRadiation) {
		this.reflectedSolarRadiation = reflectedSolarRadiation;
	}

	public double getNetShortwaveFlux() {
		return netShortwaveFlux;
	}

	public void setNetShortwaveFlux(double netShortwaveFlux) {
		this.netShortwaveFlux = netShortwaveFlux;
	}

	public double getAbsorbedByAtmosphere() {
		return absorbedByAtmosphere;
	}

	public void setAbsorbedByAtmosphere(double absorbedByAtmosphere) {
		this.absorbedByAtmosphere = absorbedByAtmosphere;
	}

	public double getAtmosphericWindow() {
		return atmosphericWindow;
	}

	public void setAtmosphericWindow(double atmosphericWindow) {
		this.atmosphericWindow = atmosphericWindow;
	}

	public double getEmittedByAtmosphere() {
		return emittedByAtmosphere;
	}

	public void setEmittedByAtmosphere(double emittedByAtmosphere) {
		this.emittedByAtmosphere = emittedByAtmosphere;
	}

	public double getEmittedByClouds() {
		return emittedByClouds;
	}

	public void setEmittedByClouds(double emittedByClouds) {
		this.emittedByClouds = emittedByClouds;
	}

	public double getBackRadiationBase() {
		return backRadiationBase;
	}

	public void setBackRadiationBase(double backRadiationBase) {
		this.backRadiationBase = backRadiationBase;
	}

	public double getBackRadiation() {
		return backRadiation;
	}

	public void setBackRadiation(double backRadiation) {
		this.backRadiation = backRadiation;
	}
	
	public void incBackRadiation(double backRadiation) {
		this.backRadiation += backRadiation;
	}

	public double getOutgoingLongwave() {
		return outgoingLongwave;
	}

	public void setOutgoingLongwave(double outgoingLongwave) {
		this.outgoingLongwave = outgoingLongwave;
	}

	public double getHeat() {
		return heat;
	}

	public void setHeat(double heat) {
		this.heat = heat;
	}

	public double getAlbedo() {
		return albedo;
	}

	public void setAlbedo(double albedo) {
		this.albedo = albedo;
	}

	public double getShortwaveAbsorptionFactor() {
		return shortwaveAbsorptionFactor;
	}

	public void setShortwaveAbsorptionFactor(double shortwaveAbsorptionFactor) {
		this.shortwaveAbsorptionFactor = shortwaveAbsorptionFactor;
	}
	
}

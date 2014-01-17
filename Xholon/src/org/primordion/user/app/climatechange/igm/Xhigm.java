/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2007, 2008 Ken Webb
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

package org.primordion.user.app.climatechange.igm;

import org.primordion.xholon.base.XholonWithPorts;

/**
 * igm application - Xholon Java
 * <p>
 * Xholon 0.8.1 http://www.primordion.com/Xholon
 * </p>
 */
public class Xhigm extends XholonWithPorts implements Ceigm {

	/**
	 * Stefan-Boltzmann constant (J * K^-4 * m^-2 * s^-1) see: wikipedia
	 * Stefan–Boltzmann constant "the total energy radiated per unit surface
	 * area of a black body in unit time is proportional to the fourth power of
	 * the thermodynamic temperature"
	 */
	private final double STEFAN_BOLTZMANN_CONSTANT = 5.67e-8;

	// Signals, Events
	/*
	 * messages: fluxes (W m^-2) solar constant (from Sun to
	 * Earth/TopOfAtmosphere) incoming solar radiation, insolation (from
	 * TopOfAtmosphere to Atmosphere) reflected solar radiation (from Atmosphere
	 * to Space) reflected solar radiation (from Surface to Space) net shortwave
	 * flux, SW down only (from Atmosphere to Surface) surface radiation, LW up,
	 * IR, greenhouse effect (from Surface to Atmosphere) back radiation, LW
	 * down, IR, greenhouse effect (from Atmosphere to Surface) latent heat
	 * flux, LH, evaporation (from Surface to Atmosphere) sensible heat flux,
	 * SH, dry convection (from Surface to Atmosphere) outgoing longwave
	 * radiation (from TopOfAtmosphere to Space)
	 */
	/** Solar constant (from Sun to Earth/TopOfAtmosphere). */
	public static final int SIG_SOLAR_CONSTANT = 101;

	/**
	 * Incoming solar radiation (visible/UV), insolation (from TopOfAtmosphere
	 * to Atmosphere).
	 */
	public static final int SIG_INCOMING_SOLAR_RADIATION = 102;

	/**
	 * Reflected solar radiation from clouds, aerosols, atmospheric gases (from
	 * Atmosphere to TopOfAtmosphere). Reflected solar radiation from surface
	 * (from Surface to TopOfAtmosphere).
	 */
	public static final int SIG_REFLECTED_SOLAR_RADIATION = 103;

	/** Net shortwave flux, SW down only (from Atmosphere to Surface). */
	public static final int SIG_NET_SHORTWAVE_FLUX = 104;

	/**
	 * Surface radiation, LW up, IR, greenhouse effect (from Surface to
	 * Atmosphere).
	 */
	public static final int SIG_SURFACE_RADIATION = 201;

	/**
	 * Back radiation, LW down, IR, greenhouse effect (from Atmosphere to
	 * Surface).
	 */
	public static final int SIG_BACK_RADIATION = 202;

	/**
	 * Latent heat flux, LH, evaporation, Evapo-transpiration (from Surface to
	 * Atmosphere).
	 */
	public static final int SIG_LATENT_HEAT_FLUX = 203;

	/**
	 * Sensible heat flux, SH, dry convection, Thermals (from Surface to
	 * Atmosphere).
	 */
	public static final int SIG_SENSIBLE_HEAT_FLUX = 204;

	/** Outgoing longwave radiation (from TopOfAtmosphere to Space). */
	public static final int SIG_OUTGOING_LONGWAVE_RADIATION = 205;

	/**
	 * Initial value for various doubles until the simulation reaches basic
	 * stability.
	 */
	public static final double VAL_NULL = Double.NEGATIVE_INFINITY;

	/**
	 * Get an array of signal IDs.
	 * 
	 * @return An array.
	 */
	public static int[] getSignalIDs() {
		int[] signalIDs = { SIG_SOLAR_CONSTANT, SIG_INCOMING_SOLAR_RADIATION,
				SIG_REFLECTED_SOLAR_RADIATION, SIG_NET_SHORTWAVE_FLUX,
				SIG_SURFACE_RADIATION, SIG_BACK_RADIATION,
				SIG_LATENT_HEAT_FLUX, SIG_SENSIBLE_HEAT_FLUX,
				SIG_OUTGOING_LONGWAVE_RADIATION };
		return signalIDs;
	}

	/**
	 * Get an array of signal names.
	 * 
	 * @return An array.
	 */
	public static String[] getSignalNames() {
		String[] signalNames = { "solarConstant", "incomingSW", "reflected",
				"netSW", "surfaceRad", "backRad", "latentHeat", "sensibleHeat",
				"outgoingLW" };
		return signalNames;
	}

	// Variables
	public String roleName = null;
	private double val = Double.NEGATIVE_INFINITY;

	// Constructor
	public Xhigm() {
		super();
	}

	// Setters and Getters
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setVal(double val) {
		this.val = val;
	}

	public double getVal() {
		return val;
	}

	/**
	 * Convert a temperature from Kelvin to Celcius. [°C] = [K] − 273.15
	 * 
	 * @param temperature
	 *            A temperature in degrees Kelvin.
	 * @return The temperature in degrees Celcius.
	 */
	protected double k2c(double temperature) {
		return temperature - 273.15;
	}

	/**
	 * Convert a temperature from Kelvin to Fahrenheit. [°F] = [K] × 9⁄5 −
	 * 459.67
	 * 
	 * @param temperature
	 *            A temperature in degrees Kelvin.
	 * @return The temperature in degrees Fahrenheit.
	 */
	protected double k2f(double temperature) {
		return (temperature * (9.0 / 5.0)) - 459.67;
	}

	/**
	 * 2 types of energy flux: radiative flux, heat flux wikipedia: Energy flux
	 * "Energy flux is the rate of transfer of energy through a surface."
	 * Convert an energy flux in watts per square meter to a temperature in
	 * degrees Kelvin. (390/.0000000567)pow0.25 --> 287.98°K (14.83°C)
	 * 
	 * @param wattsPerSquareMeter
	 *            An energy flux in watts per square meter.
	 * @return The temperature in degrees Kelvin.
	 */
	protected double ef2k(double wattsPerSquareMeter) {
		return Math.pow(wattsPerSquareMeter / STEFAN_BOLTZMANN_CONSTANT, 0.25);
	}

	/**
	 * Convert a temperature in degrees Kelvin to an energy flux in watts per
	 * square meter.
	 * 
	 * @param temperature
	 *            A temperature in degrees Kelvin.
	 * @return The energy flux in watts per square meter.
	 */
	protected double k2ef(double temperature) {
		return Math.pow(temperature, 4.0) * STEFAN_BOLTZMANN_CONSTANT;
	}

	public void initialize() {
		super.initialize();
	}

	public void postConfigure() {
		switch (xhc.getId()) {
		default:
			break;
		}
		super.postConfigure();
	}

	public void act() {
		switch (xhc.getId()) {
		case IdealizedGreenhouseModelCE:
			processMessageQ();
			break;
		default:
			break;
		}
		super.act();
	}

	public String toString() {
		String outStr = getName();
		if (val != Double.NEGATIVE_INFINITY) {
			outStr += " " + val;
		}
		return outStr;
	}

}

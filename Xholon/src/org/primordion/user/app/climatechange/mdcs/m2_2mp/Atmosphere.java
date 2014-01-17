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

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;

/**
 * Atmosphere
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 4, 2011)
 */
public class Atmosphere extends Xhmdcs {
	
	// ports
	private IXholon topOfAtmosphere = null;
	private IXholon surface = null;
	
	// properties of the atmosphere
	private IXholon energy = null;
	private IXholon temperature = null;
	private IXholon albedo = null;
	private IXholon mass = null;
	private IXholon specificHeat = null;
	private IXholon heatCapacity = null;
	private IXholon absorption = null;
	private IXholon emissivity = null;
	
	// fluxes
	
	private IXholon swIn = null; // Solar_to_atmosphere
	
	/** Infrared radiation from a planet. */
	private IXholon lwIn = null; // IR
	
	private IXholon lwUp = null; // IR_to_space
	private IXholon lwDown = null; // IR_down = IR_atmosphere
	
	private double irAtmosphere = 0.0; // local IR_atmosphere
	private double irToSpace = 0.0; // local IR_to_space
	private double ir = 0.0; // local IR
	
	/** Reflected visible/UV from the surface of a planet. */
	private IXholon reflectedShortwaveRadiation = null;
	private double relectedFromAtmosphere = 0.0; // local
	
	/** Robinson: Solar_to_atmosphere */
	private double absorbedByAtmosphere = 0.0; // local
	
	// constants
	private IXholon stefanBoltzmannConstant = null;
	private IXholon secondsPerTimeStep = null;
	
	public void postConfigure() {
		super.postConfigure();
		heatCapacity.setVal(mass.getVal() * specificHeat.getVal());
		energy.setVal(temperature.getVal() * heatCapacity.getVal());
		irAtmosphere = stefanBoltzmannConstant.getVal() * emissivity.getVal() * Math.pow(temperature.getVal(), 4);
		// ir is a function of surface temperature
		ir = ((Surface)surface).getInfrared();
		irToSpace = ir * (1.0 - emissivity.getVal()) + irAtmosphere;
	}
	
	public void act() {
		relectedFromAtmosphere = swIn.getVal() * albedo.getVal();
		absorbedByAtmosphere = (swIn.getVal() - relectedFromAtmosphere) * absorption.getVal();
		//Energy_in_Atmosphere(t) = Energy_in_Atmosphere(t - dt) + (IR + Solar_to_atmosphere - IR_down - IR_to_space) * dt
		energy.setVal(energy.getVal()
				+ (lwIn.getVal() + absorbedByAtmosphere - irAtmosphere - irToSpace) * secondsPerTimeStep.getVal() * dt); // TODO
		temperature.setVal(energy.getVal() / heatCapacity.getVal());
		irAtmosphere = stefanBoltzmannConstant.getVal() * emissivity.getVal() * Math.pow(temperature.getVal(), 4);
		ir = lwIn.getVal();
		irToSpace = ir * (1.0 - emissivity.getVal()) + irAtmosphere;
		sendMessages();
		super.act();
	}
	
	public void postAct() {
		//sendMessages();
		super.postAct();
	}
	
	protected void sendMessages() {
		surface.sendMessage(SIG_SW,
				swIn.getVal() - relectedFromAtmosphere - absorbedByAtmosphere, this);
		surface.sendMessage(SIG_LW, irAtmosphere, this);
		topOfAtmosphere.sendMessage(SIG_LW, irToSpace, this);
		topOfAtmosphere.sendMessage(SIG_SW_REFLECTED, reflectedShortwaveRadiation.getVal() + relectedFromAtmosphere, this);
	}
	
	public void processReceivedMessage(IMessage msg)
	{
		switch(msg.getSignal()) {
		case SIG_SW:
			swIn.setVal(msg.getData());
			break;
		case SIG_LW:
			lwIn.setVal(msg.getData());
			break;
		case SIG_SW_REFLECTED:
			reflectedShortwaveRadiation.setVal(msg.getData()); // from surface
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

	public IXholon getEnergy() {
		return energy;
	}

	public void setEnergy(IXholon energy) {
		this.energy = energy;
	}

	public IXholon getTemperature() {
		return temperature;
	}

	public void setTemperature(IXholon temperature) {
		this.temperature = temperature;
	}

	public IXholon getAlbedo() {
		return albedo;
	}

	public void setAlbedo(IXholon albedo) {
		this.albedo = albedo;
	}

	public IXholon getMass() {
		return mass;
	}

	public void setMass(IXholon mass) {
		this.mass = mass;
	}

	public IXholon getSpecificHeat() {
		return specificHeat;
	}

	public void setSpecificHeat(IXholon specificHeat) {
		this.specificHeat = specificHeat;
	}

	public IXholon getHeatCapacity() {
		return heatCapacity;
	}

	public void setHeatCapacity(IXholon heatCapacity) {
		this.heatCapacity = heatCapacity;
	}

	public IXholon getAbsorption() {
		return absorption;
	}

	public void setAbsorption(IXholon absorption) {
		this.absorption = absorption;
	}

	public IXholon getEmissivity() {
		return emissivity;
	}

	public void setEmissivity(IXholon emissivity) {
		this.emissivity = emissivity;
	}

	public IXholon getSwIn() {
		return swIn;
	}

	public void setSwIn(IXholon swIn) {
		this.swIn = swIn;
	}

	public IXholon getLwIn() {
		return lwIn;
	}

	public void setLwIn(IXholon lwIn) {
		this.lwIn = lwIn;
	}

	public IXholon getLwUp() {
		return lwUp;
	}

	public void setLwUp(IXholon lwUp) {
		this.lwUp = lwUp;
	}

	public IXholon getLwDown() {
		return lwDown;
	}

	public void setLwDown(IXholon lwDown) {
		this.lwDown = lwDown;
	}

	public IXholon getReflectedShortwaveRadiation() {
		return reflectedShortwaveRadiation;
	}

	public void setReflectedShortwaveRadiation(IXholon reflectedShortwaveRadiation) {
		this.reflectedShortwaveRadiation = reflectedShortwaveRadiation;
	}

	public IXholon getStefanBoltzmannConstant() {
		return stefanBoltzmannConstant;
	}

	public void setStefanBoltzmannConstant(IXholon stefanBoltzmannConstant) {
		this.stefanBoltzmannConstant = stefanBoltzmannConstant;
	}

	public IXholon getSecondsPerTimeStep() {
		return secondsPerTimeStep;
	}

	public void setSecondsPerTimeStep(IXholon secondsPerTimeStep) {
		this.secondsPerTimeStep = secondsPerTimeStep;
	}

	public double getIr() {
		return ir;
	}

	public void setIr(double ir) {
		this.ir = ir;
	}

	public double getIrAtmosphere() {
		return irAtmosphere;
	}

	public void setIrAtmosphere(double irAtmosphere) {
		this.irAtmosphere = irAtmosphere;
	}

	public double getIrToSpace() {
		return irToSpace;
	}

	public void setIrToSpace(double irToSpace) {
		this.irToSpace = irToSpace;
	}

	public double getRelectedFromAtmosphere() {
		return relectedFromAtmosphere;
	}

	public void setRelectedFromAtmosphere(double relectedFromAtmosphere) {
		this.relectedFromAtmosphere = relectedFromAtmosphere;
	}

	public double getAbsorbedByAtmosphere() {
		return absorbedByAtmosphere;
	}

	public void setAbsorbedByAtmosphere(double absorbedByAtmosphere) {
		this.absorbedByAtmosphere = absorbedByAtmosphere;
	}
	
}

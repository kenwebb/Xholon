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

package org.primordion.user.app.climatechange.mdcs.m2_1mp;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;

/**
 * The surface of a planet.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 4, 2011)
 */
public class Surface extends Xhmdcs {
	
	// ports to other domain objects
	private IXholon space = null;
	
	// properties of the surface
	private IXholon energy = null;
	private IXholon albedo = null;
	private IXholon temperature = null;
	
	// the surface consists of water
	private Water water = null;
	
	// fluxes, and flux-related
	private IXholon solarIn = null;
	private double solar = 0.0;
	private double infrared = 0.0;
	
	// constants
	private IXholon stefanBoltzmannConstant = null;
	private IXholon secondsPerTimeStep = null;
	
	public void postConfigure() {
		super.postConfigure();
		energy.setVal(temperature.getVal() * water.getHeatCapacity().getVal());
		solar = solarIn.getVal() * (1.0 - albedo.getVal()) * secondsPerTimeStep.getVal();
		infrared = stefanBoltzmannConstant.getVal() * Math.pow(temperature.getVal(), 4) * secondsPerTimeStep.getVal();
	}
	
	public void act() {
		energy.setVal(energy.getVal() + (solar - infrared) * dt);
		temperature.setVal(energy.getVal() / water.getHeatCapacity().getVal());
		solar = solarIn.getVal() * (1.0 - albedo.getVal()) * secondsPerTimeStep.getVal();
		infrared = stefanBoltzmannConstant.getVal() * Math.pow(temperature.getVal(), 4) * secondsPerTimeStep.getVal();
		super.act();
	}
	
	public void postAct() {
		space.sendMessage(SIG_SW_REFLECTED, solarIn.getVal() * albedo.getVal(), this);
		space.sendMessage(SIG_LW, infrared / secondsPerTimeStep.getVal(), this);
		super.postAct();
	}

	public void processReceivedMessage(IMessage msg)
	{
		switch(msg.getSignal()) {
		case SIG_SW:
			solarIn.setVal(msg.getData());
			break;
		default:
			break;
		}
	}
	
	public IXholon getEnergy() {
		return energy;
	}

	public void setEnergy(IXholon energy) {
		this.energy = energy;
	}

	public IXholon getAlbedo() {
		return albedo;
	}

	public void setAlbedo(IXholon albedo) {
		this.albedo = albedo;
	}

	public IXholon getTemperature() {
		return temperature;
	}

	public void setTemperature(IXholon temperature) {
		this.temperature = temperature;
	}

	public Water getWater() {
		return water;
	}

	public void setWater(Water water) {
		this.water = water;
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

	public IXholon getSpace() {
		return space;
	}

	public void setSpace(IXholon space) {
		this.space = space;
	}

	public double getSolar() {
		return solar;
	}

	public void setSolar(double solar) {
		this.solar = solar;
	}

	public IXholon getSolarIn() {
		return solarIn;
	}

	public void setSolarIn(IXholon solarIn) {
		this.solarIn = solarIn;
	}

	public double getInfrared() {
		return infrared;
	}

	public void setInfrared(double infrared) {
		this.infrared = infrared;
	}
	
}

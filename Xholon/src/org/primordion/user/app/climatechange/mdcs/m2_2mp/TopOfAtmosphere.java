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
 * Top of atmosphere
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 4, 2011)
 */
public class TopOfAtmosphere extends Xhmdcs {
	
	// ports
	private IXholon space = null;
	private IXholon atmosphere = null;
	
	// properties
	/**
	 * Albedo as used in the Robinson STELLA model.
	 * If it has a value of 0.0, then it will have no effect.
	 */
	private IXholon albedo = null;
	
	/** Solar Constant flux. */
	private IXholon solarConstant = null;
	
	/** Solar Constant divided by 4. Local to Tpf. */
	private IXholon solarConstantDivFour = null;
	
	/** Reflected visible/UV from the surface and/or atmosphere of a planet. */
	private IXholon reflectedShortwaveRadiation = null;
	
	/** Longwave radiation from atmosphere, out to space. */
	private IXholon lwOut = null;
	
	public void act() {
		solarConstantDivFour.setVal(solarConstant.getVal() / 4.0);
		sendMessages();
		super.act();
	}
	
	public void postAct() {
		//sendMessages();
		super.postAct();
	}
	
	protected void sendMessages() {
		// calc and send averaged value
		atmosphere.sendMessage(SIG_SW, solarConstant.getVal() / 4.0 * (1.0 - albedo.getVal()), this);
		if (albedo.getVal() > 0.0) {
			space.sendMessage(SIG_SW_REFLECTED, solarConstant.getVal() / 4.0 * albedo.getVal(), this);
		}
	}
	
	public void processReceivedMessage(IMessage msg)
	{
		switch(msg.getSignal()) {
		case SIG_SW:
			solarConstant.setVal(msg.getData());
			break;
		case SIG_LW:
			lwOut.setVal(msg.getData()); // from atmosphere
			space.sendMessage(msg.getSignal(), msg.getData(), this); // relay
			break;
		case SIG_SW_REFLECTED:
			reflectedShortwaveRadiation.setVal(msg.getData()); // from atmosphere
			space.sendMessage(msg.getSignal(), msg.getData(), this); // relay
			break;
		default:
			break;
		}
	}

	public IXholon getSpace() {
		return space;
	}

	public void setSpace(IXholon space) {
		this.space = space;
	}

	public IXholon getAtmosphere() {
		return atmosphere;
	}

	public void setAtmosphere(IXholon atmosphere) {
		this.atmosphere = atmosphere;
	}

	public IXholon getSolarConstant() {
		return solarConstant;
	}

	public void setSolarConstant(IXholon solarConstant) {
		this.solarConstant = solarConstant;
	}

	public IXholon getSolarConstantDivFour() {
		return solarConstantDivFour;
	}

	public void setSolarConstantDivFour(IXholon solarConstantDivFour) {
		this.solarConstantDivFour = solarConstantDivFour;
	}

	public IXholon getAlbedo() {
		return albedo;
	}

	public void setAlbedo(IXholon albedo) {
		this.albedo = albedo;
	}

	public IXholon getReflectedShortwaveRadiation() {
		return reflectedShortwaveRadiation;
	}

	public void setReflectedShortwaveRadiation(IXholon reflectedShortwaveRadiation) {
		this.reflectedShortwaveRadiation = reflectedShortwaveRadiation;
	}

	public IXholon getLwOut() {
		return lwOut;
	}

	public void setLwOut(IXholon lwOut) {
		this.lwOut = lwOut;
	}
	
}

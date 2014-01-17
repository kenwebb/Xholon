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
 * Space surrounds and separates stars, planets, and other astronomical objects.
 * Stars send shortwave radiation (visible/UV light) through Space.
 * Planets send longwave radiation (infrared heat) into Space.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 4, 2011)
 */
public class Space extends Xhmdcs {
	
	// ports
	private IXholon planet = null;
	
	// fluxes
	
	/** Solar Constant. */
	private IXholon solarConstant = null;
	
	/** Infrared radiation from a planet. */
	private IXholon longwaveRadiation = null;
	
	/** Reflected visible/UV from the surface of a planet. */
	private IXholon reflectedShortwaveRadiation = null;
	
	public void processReceivedMessage(IMessage msg)
	{
		switch(msg.getSignal()) {
		case SIG_SW:
			solarConstant.setVal(msg.getData());
			planet.sendMessage(SIG_SW, (Double)msg.getData() / 4.0, this);
			break;
		case SIG_LW:
			longwaveRadiation.setVal(msg.getData());
			break;
		case SIG_SW_REFLECTED:
			reflectedShortwaveRadiation.setVal(msg.getData());
			break;
		default:
			break;
		}
	}

	public IXholon getPlanet() {
		return planet;
	}

	public void setPlanet(IXholon planet) {
		this.planet = planet;
	}

	public IXholon getSolarConstant() {
		return solarConstant;
	}

	public void setSolarConstant(IXholon solarConstant) {
		this.solarConstant = solarConstant;
	}

	public IXholon getLongwaveRadiation() {
		return longwaveRadiation;
	}

	public void setLongwaveRadiation(IXholon longwaveRadiation) {
		this.longwaveRadiation = longwaveRadiation;
	}

	public IXholon getReflectedShortwaveRadiation() {
		return reflectedShortwaveRadiation;
	}

	public void setReflectedShortwaveRadiation(IXholon reflectedShortwaveRadiation) {
		this.reflectedShortwaveRadiation = reflectedShortwaveRadiation;
	}
	
}

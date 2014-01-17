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
 * Space surrounds and separates stars, planets, and other astronomical objects.
 * Stars send shortwave radiation (visible/UV light) through Space.
 * Planets send longwave radiation (infrared heat) into Space.
 * Space could contain child actions, for example:
 * - an action that adjusts the shortwave radiation value based on a planet's current distance
 * - an action that forwards shortwave radiation to multiple planets
 * By default, Space just relays shortwave radiation to a planet, without changing it in any way.
 * @author ken
 *
 */
public class Space extends Xhmodel04 {
	
	// ports
	private IXholon planet = null;
	
	/** visible/UV radiation from a star; children may change this value */
	private double shortwaveRadiation = 0.0;
	
	/** infrared rediation from a planet */
	private double longwaveRadiation = 0.0;
	
	public void act() {} // disable
	
	public void sendMessage(IMessage msg) {
		switch(msg.getSignal()) {
		case SIG_SOLAR_CONSTANT:
			if (this.hasChildNodes()) {
				// queue the message, so child nodes have a chance to handle it
				super.sendMessage(msg);
			}
			else {
				// there are no active objects here, so just relay the message to the planet
				planet.sendMessage(msg);
			}
			break;
		default:
			super.sendMessage(msg);
			break;
		}
	}
	
	public void processReceivedMessage(IMessage msg)
	{
		switch(msg.getSignal()) {
		case SIG_SOLAR_CONSTANT:
			shortwaveRadiation = ((Double)msg.getData()).doubleValue();
			super.act(); // invoke children Active Objects
			planet.sendMessage(msg.getSignal(), new Double(shortwaveRadiation), this);
			break;
		case SIG_REFLECTED_SOLAR_RADIATION:
			break;
		case SIG_OUTGOING_LONGWAVE_RADIATION:
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

	public double getShortwaveRadiation() {
		return shortwaveRadiation;
	}

	public void setShortwaveRadiation(double shortwaveRadiation) {
		this.shortwaveRadiation = shortwaveRadiation;
	}

	public double getLongwaveRadiation() {
		return longwaveRadiation;
	}

	public void setLongwaveRadiation(double longwaveRadiation) {
		this.longwaveRadiation = longwaveRadiation;
	}
	
}

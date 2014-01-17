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
 * 
 * @author ken
 *
 */
public class TopOfAtmosphere extends Xhmodel04 {
	
	// ports
	private IXholon space = null;
	private IXholon atmosphere = null;
	
	/** Solar constant per unit area (W * m^-2) (this is an external forcing) */
	private double solarConstant = VAL_NULL;
	
	/** Earth's incoming solar radiation per unit area (W * m^-2). Earth's global average solar flux. */
	private double incomingSolarRadiation = VAL_NULL;
	
	public void act() {
		if (solarConstant != VAL_NULL) {
			super.act(); // invoke children Active Objects first
			atmosphere.sendMessage(SIG_INCOMING_SOLAR_RADIATION, new Double(incomingSolarRadiation), this);
		}
	}
	
	public void processReceivedMessage(IMessage msg) {
		switch(msg.getSignal()) {
		case SIG_SOLAR_CONSTANT:
			solarConstant = ((Double)msg.getData()).doubleValue();
			break;
		case SIG_REFLECTED_SOLAR_RADIATION:
			// dump the radiation back into space
			space.sendMessage(SIG_REFLECTED_SOLAR_RADIATION, msg.getData(), this);
			break;
		case SIG_OUTGOING_LONGWAVE_RADIATION:
			// dump the heat into space
			//space.sendMessage(SIG_OUTGOING_LONGWAVE_RADIATION, msg.getData(), this);
			// ERBE satellite measurement is 235 W * m^-2 (Kiehl & Trenberth (1997) p.198)
			space.sendMessage(SIG_OUTGOING_LONGWAVE_RADIATION, msg.getData(), this);
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

	public double getSolarConstant() {
		return solarConstant;
	}

	public void setSolarConstant(double solarConstant) {
		this.solarConstant = solarConstant;
	}

	public double getIncomingSolarRadiation() {
		return incomingSolarRadiation;
	}

	public void setIncomingSolarRadiation(double incomingSolarRadiation) {
		this.incomingSolarRadiation = incomingSolarRadiation;
	}
	
}

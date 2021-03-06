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
 * The Sun generates and radiates energy into space.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 4, 2011)
 */
public class Sun extends Xhmdcs {
	
	private double solarConstant = 1367.0;
	
	// ports
	private IXholon space = null;

	public void preAct() {
		// send energy through space
		space.sendMessage(SIG_SW, solarConstant, this);
		super.preAct();
	}
	
	public IXholon getSpace() {
		return space;
	}

	public void setSpace(IXholon space) {
		this.space = space;
	}

	public double getSolarConstant() {
		return solarConstant;
	}

	public void setSolarConstant(double solarConstant) {
		this.solarConstant = solarConstant;
	}

}

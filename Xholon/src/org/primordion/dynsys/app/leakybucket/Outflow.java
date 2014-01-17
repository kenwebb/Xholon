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

package org.primordion.dynsys.app.leakybucket;

import org.primordion.xholon.base.IXholon;

/**
 * An outflow.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on December 18, 2011)
 */
public class Outflow extends Xhleakybucket {
	
	// properties of the outflow
	private IXholon g = null; // AccelerationDueToGravity
	private IXholon depth = null;
	private IXholon areaOfHole = null;
	private IXholon vfr = null; // VolumetricFlowRate
	
	/*
	 * @see org.primordion.xholon.base.Xholon#act()
	 */
	public void act() {
		double speed = Math.sqrt(2.0 * g.getVal() * depth.getVal());
		vfr.setVal(speed * areaOfHole.getVal());
		super.act();
	}

	public IXholon getG() {
		return g;
	}

	public void setG(IXholon g) {
		this.g = g;
	}

	public IXholon getDepth() {
		return depth;
	}

	public void setDepth(IXholon depth) {
		this.depth = depth;
	}

	public IXholon getAreaOfHole() {
		return areaOfHole;
	}

	public void setAreaOfHole(IXholon areaOfHole) {
		this.areaOfHole = areaOfHole;
	}

	public IXholon getVfr() {
		return vfr;
	}

	public void setVfr(IXholon vfr) {
		this.vfr = vfr;
	}

}

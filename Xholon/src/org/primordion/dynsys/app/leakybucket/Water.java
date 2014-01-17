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
 * A volume of water.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on December 18, 2011)
 */
public class Water extends Xhleakybucket {
	
	// properties of the water
	private IXholon depth = null;
	private IXholon volume = null;
	private IXholon areaOfBucket = null;
	private IXholon vfrOfInflow = null;
	private IXholon vfrOfOutflow = null;
	
	public void postConfigure() {
		// set the initial volume
		volume.setVal(areaOfBucket.getVal() * depth.getVal());
		super.postConfigure();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#act()
	 */
	public void act() {
		depth.setVal(volume.getVal() / areaOfBucket.getVal());
		volume.incVal((vfrOfInflow.getVal() - vfrOfOutflow.getVal()) * dt);
		super.act();
	}
	
	public IXholon getDepth() {
		return depth;
	}

	public void setDepth(IXholon depth) {
		this.depth = depth;
	}

	public IXholon getVolume() {
		return volume;
	}

	public void setVolume(IXholon volume) {
		this.volume = volume;
	}

	public IXholon getAreaOfBucket() {
		return areaOfBucket;
	}

	public void setAreaOfBucket(IXholon areaOfBucket) {
		this.areaOfBucket = areaOfBucket;
	}

	public IXholon getVfrOfInflow() {
		return vfrOfInflow;
	}

	public void setVfrOfInflow(IXholon vfrOfInflow) {
		this.vfrOfInflow = vfrOfInflow;
	}

	public IXholon getVfrOfOutflow() {
		return vfrOfOutflow;
	}

	public void setVfrOfOutflow(IXholon vfrOfOutflow) {
		this.vfrOfOutflow = vfrOfOutflow;
	}
	
}

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

package org.primordion.dynsys.app.stability;

import org.primordion.xholon.base.IXholon;

/**
 * A ball located on a hill or in a valley.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on December 17, 2011)
 */
public class Ball extends Xhstability {
	
	// properties of the ball
	private IXholon position = null;
	private IXholon velocity = null;
	private IXholon acceleration = null;
	private IXholon constant = null; // a constant force
	
	/*
	 * @see org.primordion.xholon.base.Xholon#act()
	 */
	public void act() {
	  // the following 3 lines must be in this order
		acceleration.setVal(position.getVal() * constant.getVal());
		velocity.incVal(acceleration.getVal() * dt);
		position.incVal(velocity.getVal() * dt);
		super.act();
	}
	
	public IXholon getPosition() {
		return position;
	}

	public void setPosition(IXholon position) {
		this.position = position;
	}

	public IXholon getVelocity() {
		return velocity;
	}

	public void setVelocity(IXholon velocity) {
		this.velocity = velocity;
	}

	public IXholon getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(IXholon acceleration) {
		this.acceleration = acceleration;
	}

	public IXholon getConstant() {
		return constant;
	}

	public void setConstant(IXholon constant) {
		this.constant = constant;
	}
	
}

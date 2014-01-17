/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2007, 2008 Ken Webb
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

package org.primordion.dynsys.app;

import org.primordion.xholon.base.IIntegration;
import org.primordion.xholon.base.XholonWithPorts;

/**
 * Dynamical Systems - Gravity1.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on January 17, 2007)
 */
public class XhGravity1 extends XholonWithPorts implements CeGravity1 {

	// ports
	public static final int P_Gravity = 0;
	
	// time step multiplier
	public static final int timeStepMultiplier = IIntegration.M_2;
	
	// xholon attributes
	public double position = 0.0;
	public double velocity = 0.0;
	public double gravitationalForce = 0.0;
	
	//private double val = 0.0;
	public String roleName = null;
	
	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
		roleName = null;
	}
	
	public double getPosition() {
		return position;
	}
	
	public void setPosition(double position) {
		this.position = position;
	}
	
	public double getVelocity() {
		return velocity;
	}
	
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}
	
	public double getGravitationalForce() {
		return gravitationalForce;
	}
	
	public void setGravitationalForce(double gravitationalForce) {
		this.gravitationalForce = gravitationalForce;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal()
	 */
	public double getVal() {return position;}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal_double()
	 */
	public double getVal_double() {return gravitationalForce;}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(double)
	 */
	public void setVal(double val) {{ this.position = val; }}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setRoleName(java.lang.String)
	 */
	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getRoleName()
	 */
	public String getRoleName()
	{
		return roleName;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#act()
	 */
	public void act()
	{
		switch(xhc.getId()) {
		case SuspendedMassCE:
			velocity = velocity + (port[P_Gravity].getVal_double() / timeStepMultiplier);
			position = position - (velocity / timeStepMultiplier);
			if (position < 0) {
				// it's landed
				position = 0;
				velocity = 0;
			}
			break;
		
		default:
			break;
		}
		
		super.act();
	}
	
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String outStr = getName();
		switch(xhc.getId()) {
		case SuspendedMassCE:
			if ((port != null) && (port.length > 0)) {
				outStr += " [";
				for (int i = 0; i < port.length; i++) {
					if (port[i] != null) {
						outStr += " port:" + port[i].getName();
					}
				}
				outStr += "]";
			}
			outStr += " position:" + position;
			outStr += " velocity:" + velocity;
			break;
		case EarthCE:
			outStr += " position:" + position;
			outStr += " gravitationalForce:" + gravitationalForce;
			break;
		default:
			break;
		}
		return outStr;
	}
}

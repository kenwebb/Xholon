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

import org.primordion.xholon.base.IIntegration;
import org.primordion.xholon.base.XholonWithPorts;

/**
 * Leaky Bucket model.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on December 18, 2011)
 */
public class Xhleakybucket extends XholonWithPorts {

	// time step multiplier
	public static int timeStepMultiplier = IIntegration.M_128;
	
	// delta t (an increment in time)
	protected static double dt = 1.0 / (double)timeStepMultiplier;
	
	// Variables
	public String roleName = null;

	// Constructor
	public Xhleakybucket() {
		super();
	}

	// Setters and Getters
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}

	public static int getTimeStepMultiplier() {
		return timeStepMultiplier;
	}

	public static void setTimeStepMultiplier(int timeStepMultiplier) {
		Xhleakybucket.timeStepMultiplier = timeStepMultiplier;
		dt = 1.0 / (double)timeStepMultiplier;
	}
	
	public static double getDt() {
		return dt;
	}

}

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
 * Dynamical Systems - Interest.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on January 16, 2007)
 */
public class XhInterest extends XholonWithPorts implements CeInterest {

	// ports
	public static final int P_SavAcctBalance = 0;
	public static final int P_InterestRate = 1;
	
	// time step multiplier
	public static final int timeStepMultiplier = IIntegration.M_MINUTELY;
	
	private double val = 0.0;
	public String roleName = null;
	
	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
		val = 0.0;
		roleName = null;
	}

	/*
	 * @see org.primordion.xholon.base.Xholon#getVal()
	 */
	public double getVal() {return val;}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(double)
	 */
	public void setVal(double val) {{ this.val = val; }}
	
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
		case InterestCE:
			double balance = port[P_SavAcctBalance].getVal();
			double iRate = port[P_InterestRate].getVal();
			//for (int i = 0; i < timeStepMultiplier; i++) {
				balance += (balance * iRate) / timeStepMultiplier;
			//}
			port[P_SavAcctBalance].setVal(balance);
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
		case InterestCE:
			if ((port != null) && (port.length > 0)) {
				outStr += " [";
				for (int i = 0; i < port.length; i++) {
					if (port[i] != null) {
						outStr += " port:" + port[i].getName();
					}
				}
				outStr += "]";
			}
			break;
		case SavingsAccountBalanceCE:
		case InterestRateCE:
			outStr += " val=" + getVal();
			break;
		default:
			break;
		}
		return outStr;
	}
}

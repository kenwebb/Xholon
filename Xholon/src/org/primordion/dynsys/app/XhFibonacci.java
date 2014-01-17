/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2006, 2007, 2008 Ken Webb
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

import org.primordion.xholon.base.XholonWithPorts;

/**
 * Dynamical Systems - Fibonacci.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on Dec 30, 2005)
 */
public class XhFibonacci extends XholonWithPorts implements CeFibonacci {

	public static final int P_INDVAR1 = 0;
	public static final int P_INDVAR2 = 1;
	public static final int P_DEPVAR  = 2;
	
	private int val = 0;
	public String roleName = null;
	
	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
		val = 0;
		roleName = null;
	}

	// both getVal() and getVal_int() functions are needed in this model
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal_int()
	 */
	public int getVal_int() {return val;}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal()
	 */
	public double getVal() {return val;}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(double)
	 */
	public void setVal(int val) {{ this.val = val; }}
	
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
		case FibonacciFunctionCE:
			switch (getApp().getTimeStep()) {
			case 0:
				((XhFibonacci)port[P_DEPVAR]).setVal(0);
				break;
			case 1:
				((XhFibonacci)port[P_DEPVAR]).setVal(1);
				break;
			default:
				((XhFibonacci)port[P_DEPVAR]).setVal(
						((XhFibonacci)port[P_INDVAR1]).getVal_int()
						+ ((XhFibonacci)port[P_INDVAR2]).getVal_int());
				break;
			}
			break;
		
		default:
			break;
		}
		
		super.act();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#postAct()
	 */
	public void postAct()
	{
		switch(xhc.getId()) {
		case FibonacciFunctionCE:
			((XhFibonacci)port[P_INDVAR2]).setVal(((XhFibonacci)port[P_INDVAR1]).getVal_int());
			((XhFibonacci)port[P_INDVAR1]).setVal(((XhFibonacci)port[P_DEPVAR]).getVal_int());
			break;
		default:
			break;
		}
		
		super.postAct();
	}
	
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String outStr = getName();
		switch(xhc.getId()) {
		case FibonacciFunctionCE:
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
		case NCE:
		case NMinus1CE:
		case NMinus2CE:
			outStr += " val=" + getVal_int();
			break;
		default:
			break;
		}
		return outStr;
	}
}

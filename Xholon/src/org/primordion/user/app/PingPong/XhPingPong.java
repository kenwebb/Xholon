/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2005, 2006, 2007, 2008 Ken Webb
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

package org.primordion.user.app.PingPong;

import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.XholonWithPorts;

/**
 * Ping Pong. This is the detailed behavior of a sample Xholon application.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on July 8, 2005)
 */
public class XhPingPong extends XholonWithPorts {

	// references to other Xholon instances
	// indices into the port array
	public static final int P_PARTNER = 0; // ping pong partner
	//public int sum = 0;
	public long sum = 0;
	
	/**
	 * Constructor.
	 */
	public XhPingPong() {}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
		sum = 0;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal()
	 */
	public double getVal() {
		return sum;
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#setVal(int)
	 */
	public void setVal(int val) {sum = val;}
	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(long)
	 */
	public void setVal(long val) {sum = val;}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#preAct()
	 */
	public void preAct()
	{
		super.preAct();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#act()
	 */
	public void act()
	{
		if (xhc.getXhType() == IXholonClass.XhtypeBehFgsxxx) {
			println(getName() + ": " + sum);
			((XhPingPong)port[P_PARTNER]).sum = sum + 1;
		}
		super.act();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#postAct()
	 */
	public void postAct()
	{
		super.postAct();
	}
	
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String outStr = getName();
		if ((port != null) && (port.length > 0)) {
			outStr += " [";
			for (int i = 0; i < port.length; i++) {
				if (port[i] != null) {
					outStr += " port:" + port[i].getName();
				}
			}
			outStr += "]";
		}
		outStr += " sum:" + getVal();
		return outStr;
	}
}

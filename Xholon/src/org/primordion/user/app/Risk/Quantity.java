/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2010 Ken Webb
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

package org.primordion.user.app.Risk;

/**
 * Players must be able to select a quantity between 0 and 199, or All.
 * Each Quantity object encapsulates a specific unchanging quantity.
 * Multiple Quantity objects can be summed.
 * For example, combine Thirty + Ten + Five + Three to select the number 48.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on January 15, 2010)
 */
public class Quantity extends XhRisk {
	
	/**
	 * A quantity encapulates an integer value.
	 */
	private int val = 0;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal_int()
	 */
	public int getVal_int() {
		return this.val;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(int)
	 */
	public void setVal(int val) {
		this.val = val;
	}
	
	/*
	 * @see org.primordion.user.app.Risk.XhRisk#postConfigure()
	 */
	public void postConfigure() {
		if ("Zero".equals(getXhcName())) {
			val = 0;
		}
		else if ("One".equals(getXhcName())) {
			val = 1;
		}
		else if ("Two".equals(getXhcName())) {
			val = 2;
		}
		else if ("Three".equals(getXhcName())) {
			val = 3;
		}
		else if ("Five".equals(getXhcName())) {
			val = 5;
		}
		else if ("Ten".equals(getXhcName())) {
			val = 10;
		}
		else if ("Twenty".equals(getXhcName())) {
			val = 20;
		}
		else if ("Thirty".equals(getXhcName())) {
			val = 30;
		}
		else if ("Fifty".equals(getXhcName())) {
			val = 50;
		}
		else if ("Hundred".equals(getXhcName())) {
			val = 100;
		}
		else if ("All".equals(getXhcName())) {
			val = Integer.MAX_VALUE;
		}
		super.postConfigure();
	}
	
}

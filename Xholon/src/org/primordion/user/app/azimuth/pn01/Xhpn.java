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

package org.primordion.user.app.azimuth.pn01;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.XholonWithPorts;

/**
 * Azimuth Petri Net app.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 14, 2011)
 */
public class Xhpn extends XholonWithPorts implements Cepn {

	/**
	 * Unicode black large circle.
	 */
	private static final char TOKEN = '\u2B24';
	
	/**
	 * A Petri Net state can theoretically have zero or more tokens.
	 * In this simple implementation, it only ever has zero or one tokens.
	 */
	private int val = 0;
	
	@Override
	public void setVal(int val) {
		this.val = val;
	}

	@Override
	public int getVal_int() {
		return val;
	}
	
	@Override
	public char getVal_char() {
		if (val == 0) {
			return ' ';
		}
		else {
			return TOKEN;
		}
	}
	
	@Override
	public void act() {
		if (this.getXhcId() == TheSystemCE) {
			this.processMessageQ();
		}
		else if (getXhc().hasAncestor(ReactionCE)) {
			if ((port[0].getVal_int() >= 1) && (port[1].getVal_int() >= 1)) {
				super.act();
				int portNum = 0;
				port[portNum++].setVal(0);
				port[portNum++].setVal(0);
				while ((portNum < port.length) && (isBound(port[portNum]))) {
					port[portNum++].setVal(1);
				}
				return;
			}
		}
		super.act();
	}
	
	@Override
	public void processReceivedMessage(IMessage msg) {
		if (getXhc().hasAncestor(CompoundCE)) {
			if (val == 0) {
				val = 1;
			}
			else {
				val = 0;
			}
		}
	}

	@Override
	public Object handleNodeSelection() {
		if (getXhc().hasAncestor(CompoundCE)) {
			this.sendMessage(0, null, this);
		}
		return toString();
	}
	
	@Override
	public String toString() {
		String outStr = getName();
		if (getXhc().hasAncestor(CompoundCE)) {
			outStr += " tokens:" + val;
		}
		return outStr;
	}

}

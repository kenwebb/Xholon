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

import org.primordion.xholon.util.MiscRandom;

/**
 * Players roll a set of Die objects to determine the outcomes of attacks.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on January 15, 2010)
 */
public class Die extends XhRisk {
	
	/**
	 * Make the default value 0, so it's clear that the die has not been rolled this turn.
	 */
	private static final int DEFAULT_VAL = 0;
	
	/**
	 * The current value of the die.
	 * After being rolled, a die has a value of 1 2 3 4 5 or 6.
	 * When not in use, a die has a value of 0.
	 */
	private int val = DEFAULT_VAL;

	public int getVal_int() {
		return val;
	}

	public void setVal(int val) {
		this.val = val;
	}
	
	/**
	 * Reset the value of the die to 0.
	 */
	public void reset() {
		val = DEFAULT_VAL;
	}
	
	/**
	 * Roll a die to obtain a value between >= 1 and < 7.
	 */
	public void roll() {
		val = MiscRandom.getRandomInt(1, 7);
	}
	
	/*
	 * @see org.primordion.user.app.Risk.XhRisk#toString()
	 */
	public String toString()
	{
		return super.toString() + " val:" + val;
	}
	
}

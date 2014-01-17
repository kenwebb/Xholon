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

package org.primordion.user.app.rcs.Rcs1;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.util.MiscRandom;

/**
 * Regulated Catalyzing System - Glycogen Phosphorylase (GP) - Symbolic.
 * This version of the GP system uses symbols for state and event, rather than a UML MagicDraw FSM.
 * It is manually coded rather than generated from a UML MagicDraw model.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on Jan 26, 2006)
 */
public class XhRcs1 extends XholonWithPorts implements CeRcs1 {

	// Ports
	public static final int Substrate = 0; // Catalyst, Regulator
	public static final int Product = 1; // Catalyst
	public static final int Regulation = 1; // Regulator
	
	// Signals sent in messages from Regulator to Catalyst
	protected static final int S_ACTIVATE   = 100;
	protected static final int S_DEACTIVATE = 110;
	
	// Catalyst States; Regulator has just STATE_ACTIVE
	protected static final int STATE_INACTIVE = 0;
	protected static final int STATE_ACTIVE   = 1;
	
	// Maximum quantity of Substrate.
	// This is only an approximate upper limit because the Regulator enzymes act probabilistically.
	public static int MAX_SUB = 20;
	
	// State of an active object such as an enzyme.
	public int state = STATE_INACTIVE;
	
	// Role name.
	public String roleName = null;
	
	// Value held by passive objects such as Glucose_1_Phosphate and Glycogen.
	public double val = 0.0;
	
	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
		state = STATE_INACTIVE;
		roleName = null;
		val = 0.0;
	}
	
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
	 * @see org.primordion.xholon.base.IXholon#getVal()
	 */
	public double getVal() {return val;}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setVal(double)
	 */
	public void setVal(double val)
	{this.val = val;}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#incVal(double)
	 */
	public void incVal(double incAmount)
	{val += incAmount;}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#decVal(double)
	 */
	public void decVal(double decAmount)
	{val -= decAmount;}

	/*
	 * @see org.primordion.xholon.base.IXholon#act()
	 */
	public void act()
	{
		int rInt; // random integer
		switch(xhc.getId()) {
		
		case GlycogenPhosphorylaseSystemCE:
			// Only process the messages that are currently in the Q.
			// Don't process messages added to Q during this timestep.
			processMessageQ();
			break;
		
		case GlycogenPhosphorylaseCE:
			if (state == STATE_ACTIVE) {
				if (port[Substrate].getVal() > 0.0) {
					port[Substrate].decVal(1.0);
					port[Product].incVal(1.0);
				}
			}
			break;
		
		case PhosphorylaseKinaseCE:
			rInt = MiscRandom.getRandomInt(0, (int)port[Substrate].getVal()+1);
			if (rInt == 0) {
				port[Regulation].sendMessage(S_ACTIVATE, null, this);
			}
			break;
		
		case PhosphorylasePhosphataseCE:
			rInt = (int)port[Substrate].getVal();
			if (rInt < MAX_SUB) {
				rInt = MiscRandom.getRandomInt(rInt, MAX_SUB+1);
			}
			if (rInt >= MAX_SUB) {
				port[Regulation].sendMessage(S_DEACTIVATE, null, this);
			}
			break;
		
		default:
			break;
		}
		
		super.act();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#processReceivedMessage(org.primordion.xholon.base.Message)
	 */
	public void processReceivedMessage(IMessage msg)
	{
		int event = msg.getSignal();
		
		//if (xhClass.hasAncestor("GlycogenPhosphorylase")) {
		if (getXhcId() == GlycogenPhosphorylaseCE) {
			switch (state) {
			case STATE_INACTIVE:
				switch(event) {
				case S_ACTIVATE:
					state = STATE_ACTIVE;
					break;
				default:
					break;
				}
				break;
			case STATE_ACTIVE:
				switch(event) {
				case S_DEACTIVATE:
					state = STATE_INACTIVE;
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		}
		else {
			System.out.println("processReceivedMessage: unhandled msg " + msg + " received by " + getName());
		}
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
		/*if (xhClass.hasAncestor("GlycogenPhosphorylase")) {
			outStr += " state:" + state;
		}
		else if (xhClass.hasAncestor("Substance")) {
			outStr += " val:" + val;
		}*/
		switch(xhc.getId()) {
		case GlycogenPhosphorylaseCE:
			outStr += " state:" + state;
			break;
		case GlycogenCE:
		case Glucose_1_PhosphateCE:
			outStr += " val:" + getVal();
		default:
			break;
		}
		return outStr;
	}
}

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

package org.primordion.memcomp.app.CoopSys02ex343_2;

import org.primordion.memcomp.base.IMembraneComputing;
import org.primordion.memcomp.base.XhAbstractMemComp;

/**
 * Membrane Computing.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on Jan 12, 2006)
 */
public class XhCoopSys02ex343_2 extends XhAbstractMemComp implements IMembraneComputing, CeCoopSys02ex343_2 {

	/*
	 * @see org.primordion.xholon.base.IXholon#act()
	 */
	public void act()
	{
		switch(xhc.getId()) {
		
		// CompletionDetector
		case CompletionDetectorCE:
			// computation is complete unless some rule gets applied during this timeStep
			computationComplete = true;
			break;
		
		// any instance of RuleSet
		case RuleSetCE:
			actRuleSet();
			break;
		
		// R1 rule set
		case R1r1CE:
			int vPort11[] = {P_IN3};
			applyRule("dcz", "n", vPort11, false);
			break;
		case R1r2CE:
			int vPort12[] = {P_IN3};
			applyRule("d", "y", vPort12, false);
			break;
		
		// R2 rule set
		case R2r1CE:
			int vPort21[] = {P_HERE};
			applyRule("ac", "z", vPort21, false);
			break;
		case R2r2CE:
			int vPort22[] = {P_HERE};
			applyRule("az", "c", vPort22, false);
			break;
		case R2r3CE:
			int vPort23[] = {P_HERE};
			applyRule("d", "d", vPort23, true);
			break;
		
		default:
			break;
		}
		
		super.act();
	}
	
	public int getPriority() {
	  return priority;
	}
	
	public void setPriority(int priority) {
	  this.priority = priority;
	}
	
}

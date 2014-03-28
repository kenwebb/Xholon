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

package org.primordion.memcomp.app.CoopSys02ex343_1;

import org.primordion.memcomp.base.IMembraneComputing;
import org.primordion.memcomp.base.XhAbstractMemComp;

/**
 * Membrane Computing.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on Jan 2, 2006)
 */
public class XhCoopSys02ex343_1 extends XhAbstractMemComp implements IMembraneComputing, CeCoopSys02ex343_1 {

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
			int vPort11[] = {P_OUT};
			applyRule("d", "d", vPort11, false);
			break;
		
		// R2 rule set
		case R2r3CE:
			int vPort23[] = {P_HERE};
			applyRule("b", "d", vPort23, false);
			break;
		case R2r4CE:
			int vPort24[] = {P_HERE, P_HERE};
			applyRule("d", "de", vPort24, false);
			break;
		case R2r1CE:
			int vPort21[] = {P_HERE};
			applyRule("ff", "f", vPort21, false);
			break;
		case R2r2CE:
			int vPort22[] = {P_HERE};
			applyRule("f", null, vPort22, true);
			break;
		
		// R3 rule set
		case R3r1CE:
			int vPort31[] = {P_HERE, P_HERE};
			applyRule("a", "ab", vPort31, false);
			break;
		case R3r2CE:
			int vPort32[] = {P_HERE};
			applyRule("a", "b", vPort32, true);
			break;
		case R3r3CE:
			int vPort33[] = {P_HERE, P_HERE};
			applyRule("f", "ff", vPort33, false);
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

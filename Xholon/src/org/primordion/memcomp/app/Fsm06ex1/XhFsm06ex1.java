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

package org.primordion.memcomp.app.Fsm06ex1;

import org.primordion.memcomp.base.IMembraneComputing;
import org.primordion.memcomp.base.XhAbstractMemComp;

/**
 * Membrane Computing.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on Jan 12, 2006)
 */
public class XhFsm06ex1 extends XhAbstractMemComp implements IMembraneComputing, CeFsm06ex1 {

	/*
	 * @see org.primordion.xholon.base.IXholon#act()
	 */
	public void act()
	{
		int matchCount = 0;
		String ruleStr = null;
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
		
		// R2 rule set
		case R2r1CE:
			matchCount = applyRule("am", "bp");
			ruleStr = "M2: am -> bp";
			break;
		case R2r2CE:
			matchCount = applyRule("bn", "aq");
			ruleStr = "M2: bn -> aq";
			break;
		case R2r3CE:
			matchCount = applyRule("bm", "cr");
			ruleStr = "M2: bm -> cr";
			break;
		case R2r4CE:
			matchCount = applyRuleSymport("m", SD_IN);
			ruleStr = "M2: (m, in)";
			break;
		case R2r5CE:
			matchCount = applyRuleSymport("n", SD_IN);
			ruleStr = "M2: (n, in)";
			break;
		case R2r6CE:
			matchCount = applyRuleSymport("p", SD_OUT);
			ruleStr = "M2: (p, out)";
			break;
		case R2r7CE:
			matchCount = applyRuleSymport("q", SD_OUT);
			ruleStr = "M2: (q, out)";
			break;
		case R2r8CE:
			matchCount = applyRuleSymport("r", SD_OUT);
			ruleStr = "M2: (r, out)";
			break;
		case R2r9CE: // non-deterministic
			matchCount = applyRule("am", "c");
			ruleStr = "M2: am -> c";
			break;
		
		// R3 rule set
		case R3r1CE:
			matchCount = applyRule("ap", "bn");
			ruleStr = "M3: ap -> bn";
			break;
		case R3r2CE:
			matchCount = applyRule("bq", "am");
			ruleStr = "M3: bq -> am";
			break;
		case R3r3CE:
			matchCount = applyRule("ar", "d");
			ruleStr = "M3: ar -> d";
			break;
		case R3r4CE:
			matchCount = applyRule("br", "d");
			ruleStr = "M3: br -> d";
			break;
		case R3r5CE:
			matchCount = applyRule("dp", "a");
			ruleStr = "M3: dp -> a";
			break;
		case R3r6CE:
			matchCount = applyRule("dq", "e");
			ruleStr = "M3: dq -> e";
			break;
		case R3r7CE:
			matchCount = applyRuleSymport("p", SD_IN);
			ruleStr = "M3: (p, in)";
			break;
		case R3r8CE:
			matchCount = applyRuleSymport("q", SD_IN);
			ruleStr = "M3: (q, in)";
			break;
		case R3r9CE:
			matchCount = applyRuleSymport("r", SD_IN);
			ruleStr = "M3: (r, in)";
			break;
		case R3r10CE:
			matchCount = applyRuleSymport("m", SD_OUT);
			ruleStr = "M3: (m, out)";
			break;
		case R3r11CE:
			matchCount = applyRuleSymport("n", SD_OUT);
			ruleStr = "M3: (n, out)";
			break;
		
		default:
			break;
		}
		
		if (matchCount > 0) {
			println(this.getXhcName() + " " + ruleStr); // display name and contents of this rule
		}
		
		super.act();
	}
}

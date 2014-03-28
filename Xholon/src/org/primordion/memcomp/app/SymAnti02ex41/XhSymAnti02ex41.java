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

package org.primordion.memcomp.app.SymAnti02ex41;

import org.primordion.memcomp.base.IMembraneComputing;
import org.primordion.memcomp.base.XhAbstractMemComp;

/**
 * Membrane Computing.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on Jan 4, 2006)
 */
public class XhSymAnti02ex41 extends XhAbstractMemComp implements IMembraneComputing, CeSymAnti02ex41 {

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
			applyRuleAntiport("c", "_");	// (c,out;#,in)
			break;
		case R1r2CE:
			applyRuleAntiport("ca", "cbb");	// (ca,out;cbb,in)
			break;
		case R1r3CE:
			applyRuleAntiport("ca", "zbb");	// (ca,out;c'bb,in)
			break;
		case R1r4CE:
			applyRuleAntiport("da", "_");	// (da,out;#,in)
			break;
		case R1r5CE:
			applyRuleAntiport("zd", "e");	// (c'd,out;e,in)
			break;
		case R1r6CE:
			applyRuleAntiport("eb", "ea");	// (eb,out;ea,in)
			break;
		case R1r7CE:
			applyRuleAntiport("eb", "ya");	// (eb,out;e'a,in)
			break;
		case R1r8CE:
			applyRuleAntiport("fb", "_");	// (fb,out;#,in)
			break;
		case R1r9CE:
			applyRuleAntiport("yf", "cdf");	// (e'f,out;cdf,in)
			break;
		case R1r10CE:
			applyRuleAntiport("yf", "g");	// (e'f,out;g,in)
			break;
		
		// R2 rule set
		case R2r1CE:
			applyRuleAntiport("d", "z");	// (d,out;c',in)
			break;
		case R2r2CE:
			applyRuleSymport("z", SD_OUT);	// (c',out)
			break;
		case R2r3CE:
			applyRuleAntiport("f", "y");	// (f,out;e',in)
			break;
		case R2r4CE:
			applyRuleSymport("y", SD_OUT);	// (e',out)
			break;
		case R2r5CE:
			applyRuleSymport("df", SD_IN);	// (df,in)
			break;
		case R2r6CE:
			applyRuleSymport("_", SD_IN);	// (#,in)
			break;
		case R2r7CE:
			applyRuleSymport("_", SD_OUT);	// (#,out)
			break;
		case R2r8CE:
			applyRuleSymport("da", SD_IN);	// (da,in)
			break;
		case R2r9CE:
			applyRuleSymport("g", SD_OUT);	// (g,out)
			break;
		
		default:
			break;
		}
		
		super.act();
	}
}

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

package org.primordion.memcomp.app.BraneCalc1;

import org.primordion.memcomp.base.XhAbstractBraneCalc;

/**
 * Membrane Computing.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on Jan 14, 2006)
 */
public class XhBraneCalc1 extends XhAbstractBraneCalc implements CeBraneCalc1 {

	/*
	 * @see org.primordion.xholon.base.IXholon#act()
	 */
	public void act()
	{
		switch(xhc.getId()) {
		
		// Phago, Exo, Pino actions
		case PhagoCE:
			phago(port[P_MEMBRANEP], port[P_MEMBRANEQ], port[P_PATCH]);
			break;
		case ExoCE:
			exo(port[P_MEMBRANEP], port[P_MEMBRANEQ]);
			break;
		case PinoCE:
			pino(port[P_MEMBRANEP], port[P_PATCH]);
			break;
		
		// Mate, Bud, Drip actions
		case MateCE:
			mate(port[P_MEMBRANEP], port[P_MEMBRANEQ], port[P_PATCH]);
			break;
		case BudCE:
			bud(port[P_MEMBRANEP], port[P_MEMBRANEQ], port[P_PATCH]);
			break;
		case DripCE:
			drip(port[P_MEMBRANEP], port[P_PATCH]);
			break;
		
		default:
			break;
		}
		
		super.act();
	}
}

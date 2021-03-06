/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2009 Ken Webb
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

package org.primordion.xholon.mech.mathml.content;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.mech.mathml.MathML;

/**
 * MathML 2.0 - minus
 * Minus is either a unary (-3) or a binary (7 - 3) operator.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://www.w3.org/Math/">MathML website</a>
 * @since 0.8 (Created on April 24, 2009)
 */
public class Minus extends MathML {
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal()
	 */
	public double getVal()
	{
		double val = 0.0;
		IXholon operand1 = getNextSibling();
		if (operand1 != null) {
			IXholon operand2 = operand1.getNextSibling();
			if (operand2 == null) {
				// unary minus
				val = operand1.getVal() * -1.0;
			}
			else {
				// binary minus
				val = operand1.getVal() - operand2.getVal();
			}
		}
		return val;
	}
	
}

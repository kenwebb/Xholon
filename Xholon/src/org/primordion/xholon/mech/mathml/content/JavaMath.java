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
import org.primordion.xholon.mech.mathml.CeMathML;
import org.primordion.xholon.mech.mathml.MathML;

/**
 * MathML 2.0 - functions that are implemented in the java.lang.Math class
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://www.w3.org/Math/">MathML website</a>
 * @since 0.8 (Created on May 3, 2009)
 */
public class JavaMath extends MathML implements CeMathML {
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal()
	 */
	public double getVal()
	{
		double val = 0.0;
		switch (getXhcId()) {
		
		//								constants
		
		case piCE:
		{
			val = Math.PI;
			break;
		}
		
		case exponentialeCE:
		{
			val = Math.E;
			break;
		}
		
		//								functions
		
		case absCE:
		{
			IXholon operand = getNextSibling();
			if (operand != null) {
				val = Math.abs(operand.getVal());
			}
			break;
		}
		case arccosCE:
		{
			IXholon operand = getNextSibling();
			if (operand != null) {
				val = Math.acos(operand.getVal());
			}
			break;
		}
		case arcsinCE:
		{
			IXholon operand = getNextSibling();
			if (operand != null) {
				val = Math.asin(operand.getVal());
			}
			break;
		}
		case arctanCE:
		{
			IXholon operand = getNextSibling();
			if (operand != null) {
				val = Math.atan(operand.getVal());
			}
			break;
		}
		case ceilingCE:
		{
			IXholon operand = getNextSibling();
			if (operand != null) {
				val = Math.ceil(operand.getVal());
			}
			break;
		}
		case cosCE:
		{
			IXholon operand = getNextSibling();
			if (operand != null) {
				val = Math.cos(operand.getVal());
			}
			break;
		}
		case expCE:
		{
			IXholon operand = getNextSibling();
			if (operand != null) {
				val = Math.exp(operand.getVal());
			}
			break;
		}
		case floorCE:
		{
			IXholon operand = getNextSibling();
			if (operand != null) {
				val = Math.floor(operand.getVal());
			}
			break;
		}
		case logCE:
		{
			IXholon operand = getNextSibling();
			if (operand != null) {
				val = Math.log(operand.getVal());
			}
			break;
		}
		case maxCE:
		{
			IXholon arg1 = getNextSibling();
			if (arg1 != null) {
				IXholon arg2 = arg1.getNextSibling();
				val = Math.max(arg1.getVal(), arg2.getVal());
			}
			break;
		}
		case minCE:
		{
			IXholon arg1 = getNextSibling();
			if (arg1 != null) {
				IXholon arg2 = arg1.getNextSibling();
				val = Math.min(arg1.getVal(), arg2.getVal());
			}
			break;
		}
		case powerCE:
		{
			IXholon arg1 = getNextSibling();
			if (arg1 != null) {
				IXholon arg2 = arg1.getNextSibling();
				val = Math.pow(arg1.getVal(), arg2.getVal());
			}
			break;
		}
		case remCE:
		{
			IXholon arg1 = getNextSibling();
			if (arg1 != null) {
				IXholon arg2 = arg1.getNextSibling();
				//val = Math.IEEEremainder(arg1.getVal(), arg2.getVal());
				val = IEEEremainder(arg1.getVal(), arg2.getVal());
			}
			break;
		}
		case sinCE:
		{
			IXholon operand = getNextSibling();
			if (operand != null) {
				val = Math.sin(operand.getVal());
			}
			break;
		}
		case tanCE:
		{
			IXholon operand = getNextSibling();
			if (operand != null) {
				val = Math.tan(operand.getVal());
			}
			break;
		}
		default:
			logger.error("JavaMath is unable to process MathML type: " + getXhcName());
			break;
		}
		return val;
	}
	
	/**
	 * Math.IEEEremainder() is not implemented in GWT
	 * This is claimed to work in:
	 *   https://groups.google.com/forum/#!topic/Google-Web-Toolkit-Contributors/I50Ry-x8ur0
	 */
	protected double IEEEremainder(double f1, double f2) {
   double r = Math.abs(f1 % f2);
   if(Double.isNaN(r) || r == f2 || r <= Math.abs(f2) / 2.0) {
      return r;
   }
   else {
      return Math.signum(f1) * (r - f2);
   }
}
	
}

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

package org.primordion.xholon.service.mathscieng;

//import org.jscience.physics.amount.Amount;
import org.primordion.xholon.base.Xholon;

/**
 * Provides access to the JScience library.
 * This class is never used in original Xholon.
 * <p>JScience is a "comprehensive Java library for the scientific community."</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://jscience.org/">JScience website</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on December 11, 2011)
 */
public class JScience extends Xholon implements IJScience {
	
	/* GWT
	public Object createAmount(String measure) {
		return Amount.valueOf(measure);
	}
	
	public double doubleValue(Object amount) {
		Amount a = (Amount)amount;
		double dVal = a.doubleValue(a.getUnit());
		return dVal;
	}
	
	public long longValue(Object amount) {
		Amount a = (Amount)amount;
		long lVal = a.longValue(a.getUnit());
		return lVal;
	}
	
	public String getUnit(Object amount) {
		Amount a = (Amount)amount;
		return a.getUnit().toString();
	}*/
	
}

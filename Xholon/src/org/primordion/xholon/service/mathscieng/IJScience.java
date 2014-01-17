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

/**
 * This interface is never used in original Xholon.
<pre>
&lt;XholonAmount>234 mA&lt;/XholonAmount>
</pre>
or
<pre>
&lt;Length>1.0 m&lt;/Length> Amount&lt;Length> x = Amount.valueOf(1.0, METRE);
</pre>
or
<pre>
&lt;Length units="m">1.0&lt;/Length> Amount&lt;Length> x = Amount.valueOf(1.0, METRE);
</pre>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://jscience.org/">JScience website</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on December 11, 2011)
 */
public interface IJScience {
	
	/**
	 * Create an org.jscience.physics.amount.Amount object.
	 * <p>Amount<ElectricCurrent> m2 = Amount.valueOf("234 mA")</p>
	 * @param measure ex: "234 mA"
	 * @return An instance of Amount.
	 */
	/*public abstract Object createAmount(String measure);
	
	public abstract double doubleValue(Object amount);
	
	public abstract long longValue(Object amount);
	
	public abstract String getUnit(Object amount);*/
	
}

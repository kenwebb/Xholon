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

package org.primordion.xholon.base;

/**
 * A quantity directly encapsulates a scalar or vector,
 * for example, an instance of org.jscience.physics.amount.Amount,
 * or javax.measure.VectorMeasure .
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on December 12, 2011)
 */
public interface IQuantity {
	
	/**
	 * Get Unit.
	 * @return The String representation of a JScience Unit, or equivalent.
	 */
	public String getUnit();
	
	/**
	 * Get Standard Unit.
	 * @return The String representation of a JScience Standard Unit, or equivalent.
	 */
	public String getStandardUnit();
	
	/**
	 * Get Dimension.
	 * @return The String representation of a JScience Dimension, or equivalent.
	 */
	public String getDimension();
	
	/**
	 * Get the array of magnitudes for this scalar or vector.
	 * @return An array of double values.
	 * For a scalar or 1D vector, there will be 1 values (typically X).
	 * For a 2D vector, there will be 2 values (typically X Y).
	 * For a 3D vector, there will be 3 values (typically X Y Z).
	 * For an n-dimensional vector, there will be n values.
	 */
	public double[] getValues();
	
	/**
	 * Get a specified value from the array of magnitudes for this scalar or vector.
	 * @param index The index of the requested magnitude.
	 * Index must be >= 0, and < the dimension of the vector.
	 * For a scalar or 1D vector, index is assumed to be == 0 and is not checked.
	 * For a 2D vector, 0 <= index < 2 .
	 * @return The value associated with that index.
	 */
	public double getValueN(int index);
	
	/**
	 * Based on the method in org.jscience.physics.amount.Amount
	 * <pre>public Amount&lt;Q> plus(Amount that) throws ConversionException;</pre>
	 * @param that
	 * @return
	 */
	//public abstract IQuantity plus(IQuantity that);
}

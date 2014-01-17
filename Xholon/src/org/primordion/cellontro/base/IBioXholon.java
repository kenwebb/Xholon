/* Cellontro - models & simulates cells and other biological entities
 * Copyright (C) 2005, 2006, 2007, 2008 Ken Webb
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

package org.primordion.cellontro.base;

import org.primordion.xholon.base.IXholon;

/**
 * <p>An instance of IBioXholon represents some biological entity,
 * and exists as a node in a composite structure hierarchy tree.
 * Each such IXholon object goes through an initial configure() process,
 * and may subsequently go through a regular act() process at each time step.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.2 (Created on December 12, 2005)
 */public interface IBioXholon extends IXholon {

	/**
	 * Get phene value.
	 * @return The phenotype value.
	 */
	public abstract double getPheneVal();
	
	/**
	 * Set phene val.
	 * @param pheneVal The pheotype value.
	 */
	public abstract void setPheneVal(double pheneVal);
	
	/**
	 * Increment the phene value by incAmount.
	 * @param incAmount The amount by which to increment the phenotype value.
	 */
	public abstract void incPheneVal( double incAmount );
	
	/**
	 * Decrement the phene value by incAmount.
	 * @param decAmount The amount by which to decrement the phenotype value.
	 */
	public abstract void decPheneVal( double decAmount );

	/**
	 * Get maximum number of reactants (substrates) that any xholon instance may have in this model.
	 * @return Maximum number of reactants.
	 */
	public abstract int getNumReactants();
	/**
	 * Set maximum number of reactants (substrates) that any xholon instance may have in this model.
	 * @param numR Maximum number of reactants.
	 */
	public abstract void setNumReactants(int numR);
	
	/**
	 * Get maximum number of products that any xholon instance may have in this model.
	 * @return Maximum number of products.
	 */
	public abstract int getNumProducts();
	/**
	 * Set maximum number of products that any xholon instance may have in this model.
	 * @param numP Maximum number of products.
	 */
	public abstract void setNumProducts(int numP);
	
	/**
	 * Get maximum number of modifiers (activator, inhibitor) that any xholon instance may have in this model.
	 * @return Maximum number of modifiers.
	 */
	public abstract int getNumModifiers();
	/**
	 * Set maximum number of modifiers (activator, inhibitor) that any xholon instance may have in this model.
	 * @param numM Maximum number of modifiers.
	 */
	public abstract void setNumModifiers(int numM);
}

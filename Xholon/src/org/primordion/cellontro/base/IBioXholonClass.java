/* Cellontro - models & simulates cells and other biological entities
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

package org.primordion.cellontro.base;

import org.primordion.xholon.base.IXholonClass;

public interface IBioXholonClass extends IXholonClass {

	// Feature Type
	//  Reversibility
	//  Number of Substrates
	//  Number of Products
	//  Number of Activators
	//  Number of Inhibitors
	//  Number of Coenzymes
	// Example: Irr_Sb1_Pr1_Ac0_In0_Co0
	//          Irreversible, 1 Substrate, 1 Product, 0 Activator, 0 Inhibitor, 0 Coenzyme
	public static final int FeatureType_Null = 0;

	public static final int Irr_Sb1_Pr1_Ac0_In0_Co0 = 1;

	public static final int Irr_Sb1_Pr2_Ac0_In0_Co0 = 2;

	public static final int Irr_Sb1_Pr1_Ac0_In0_CoN = 3;

	public static final int Irr_Sb2_Pr1_Ac0_In0_CoN = 4;

	public static final int Irr_Sb1_Pr1_Ac0_In1_CoN = 5;

	public static final int Irr_Sb1_Pr1_Ac1_In0_CoN = 6;

	public static final int Irr_Sb1_Pr1_AcN_InN_CoN = 7;

	public static final int Rev_Sb1_Pr1_Ac0_In0_Co0 = 8;

	public static final int Rev_Sb1_Pr1_Ac0_In0_CoN = 9;

	public static final int Rev_Sb1_Pr2_Ac0_In0_Co0 = 10;

	// SM Count
	public static final int SMCOUNT_SUBSTRATE = 0;

	public static final int SMCOUNT_PRODUCT = 1;

	public static final int SMCOUNT_ACTIVATOR = 2;

	public static final int SMCOUNT_INHIBITOR = 3;

	public static final int SMCOUNT_COENZYME = 4;

	public static final int SIZE_SMCOUNT = 5;

	// Active Object Types
	public static final int AOT_NULL = 0;

	public static final int AOT_ENZYME = 1;

	public static final int AOT_BILAYER = 2;

	public static final int AOT_TRANSPORTER = 3;

	// positions in geneVal array
	public static final int GENEVAL_KM = 0; // Enzyme kinetics Km

	public static final int GENEVAL_V = 1; // Enzyme kinetics V

	public static final int GENEVAL_EFF_KM = 2; // Enzyme kinetics Effector Km

	public static final int SIZE_ARRAY_GENEVAL = 3;

	/**
	 * Get feature type.
	 * @return The feature type.
	 */
	public abstract int getFeatureType();

	/**
	 * Set feature type.
	 * @param featureType The feature type.
	 */
	public abstract void setFeatureType(int featureType);

	/**
	 * Is this reaction reversible.
	 * @return true or false
	 */
	public abstract boolean isReversible();

	/**
	 * Set the feature type of an enzyme, based on how many substrates,
	 * products, activators, inhibitors, and coenzymes (features) it interacts with.
	 * @param smCount An array containing a count of various features.
	 */
	public abstract void setFeatureType(int smCount[]);

	/**
	 * Get active object type.
	 * @return The active object type.
	 */
	public abstract int getActiveObjectType();

	/**
	 * Set active object type.
	 * @param activeObjectType The active object type.
	 */
	public abstract void setActiveObjectType(int activeObjectType);
}
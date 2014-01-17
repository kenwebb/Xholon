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

//import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.XholonClass;

/**
 * BioXholonClass is an application specific extension of XholonClass.
 * It encapsulates a geneVal, a featureType, and an activeObjectType.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Oct 12, 2005)
 */
public class BioXholonClass extends XholonClass implements IBioXholonClass {

	private int featureType = FeatureType_Null;
	public boolean reversible = true; // SBML default is true
	private int activeObjectType = AOT_NULL;
	public int geneVal[] = null; // Gene value contained within an active object
	
	/**
	 * Constructor.
	 */
	public BioXholonClass() {}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
		featureType = FeatureType_Null;
		reversible = true;
		activeObjectType = AOT_NULL;
		geneVal = new int[SIZE_ARRAY_GENEVAL];
		for (int i = 0; i < SIZE_ARRAY_GENEVAL; i++) {
			geneVal[i] = Integer.MAX_VALUE;
		}
	}
	
	/* 
	 * @see org.primordion.cellontro.base.IBioXholonClass#getFeatureType()
	 */
	public int getFeatureType() {return featureType;}
	
	/* 
	 * @see org.primordion.cellontro.base.IBioXholonClass#setFeatureType(int)
	 */
	public void setFeatureType(int featureType) {this.featureType = featureType;}
	
	/* 
	 * @see org.primordion.cellontro.base.IBioXholonClass#isReversible()
	 */
	public boolean isReversible() {return reversible;}
	
	/* 
	 * @see org.primordion.cellontro.base.IBioXholonClass#setFeatureType(int[])
	 */
	public void setFeatureType(int smCount[]) {
		reversible = false; // this is the default for now for all non-SBML models
		if (smCount[SMCOUNT_PRODUCT] == 2) {
			if (isReversible()) {
				setFeatureType(Rev_Sb1_Pr2_Ac0_In0_Co0);
			}
			else {
				setFeatureType(Irr_Sb1_Pr2_Ac0_In0_Co0);
			}
		}
		else if (smCount[SMCOUNT_SUBSTRATE] == 2) { // 2 substrates
			setFeatureType(Irr_Sb2_Pr1_Ac0_In0_CoN);
		}
		else if ((smCount[SMCOUNT_ACTIVATOR] == 0) && (smCount[SMCOUNT_INHIBITOR] == 0)) { // 0 effectors (0 activators, and 0 inhibitors)
			if (smCount[SMCOUNT_COENZYME] == 0) { // 0 coenzymes
				if (isReversible()) {
					setFeatureType(Rev_Sb1_Pr1_Ac0_In0_Co0);
				}
				else {
					setFeatureType(Irr_Sb1_Pr1_Ac0_In0_Co0);
				}
			}
			else { // N coenzymes
				if (isReversible()) {
					setFeatureType(Rev_Sb1_Pr1_Ac0_In0_CoN);
				}
				else {
					setFeatureType(Irr_Sb1_Pr1_Ac0_In0_CoN);
				}
			}
		}
		else if (smCount[SMCOUNT_ACTIVATOR] == 1) { // 1 activator
			setFeatureType(Irr_Sb1_Pr1_Ac1_In0_CoN);
		}
		else if (smCount[SMCOUNT_INHIBITOR] == 1) { // 1 inhibitor
			setFeatureType(Irr_Sb1_Pr1_Ac0_In1_CoN);
		}
		else {
			setFeatureType(Irr_Sb1_Pr1_AcN_InN_CoN);
		}
	}
	
	/* 
	 * @see org.primordion.cellontro.base.IBioXholonClass#getActiveObjectType()
	 */
	public int getActiveObjectType() {return activeObjectType;}
	
	/* 
	 * @see org.primordion.cellontro.base.IBioXholonClass#setActiveObjectType(int)
	 */
	public void setActiveObjectType(int activeObjectType) {this.activeObjectType = activeObjectType;}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#configure()
	 */
	public void configure() {
		super.configure();
		
		// set active object type
		//if ((getXhType() & IXholonClass.XhtypePureActiveObject) == IXholonClass.XhtypePureActiveObject) {
		if (isActiveObject()) {
			if (hasAncestor("Enzyme")) {
				setActiveObjectType(AOT_ENZYME);
			}
			else if (hasAncestor("LipidBilayer")) {
				setActiveObjectType(AOT_BILAYER);
			}
			else if (hasAncestor("TransportProtein")) {
				setActiveObjectType(AOT_TRANSPORTER);
			}
			// else it must be from an SBML model
		}
	}
	
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return super.toString();
		//  + " " + geneVal[0] + " " + geneVal[1]; // GWT
	}
}

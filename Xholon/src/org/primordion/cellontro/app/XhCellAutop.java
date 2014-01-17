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

package org.primordion.cellontro.app;

import org.primordion.cellontro.base.BioXholonClass;
import org.primordion.cellontro.base.IBioXholon;
import org.primordion.cellontro.base.IBioXholonClass;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;

/**
<p>An instance of XhCellAutop represents some biological entity,
and exists as a node in a composite structure hierarchy tree.
Each such Xholon object goes through an initial configure() process,
and may subsequently go through a regular act() process at each time step.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Oct 29, 2005)
 */
public class XhCellAutop extends XholonWithPorts implements IBioXholon {

	// References to other Xholon instances. Indices into the port array.
	// first set are used by CellBilayer
	// Glucose
	public static final int P_SM_INT1 = 0; // Glucose in the Cytosol (a sample other small molecule)
	public static final int P_SM_EXT1 = 1; // Glucose ouside the membrane
	// Water
	public static final int P_SM_INT2 = 2; // Water in the Cytosol
	public static final int P_SM_EXT2 = 3; // Water ouside the membrane
	// Phosphatidyl_Ethanolamine (PE), PE_InVicinity
	public static final int P_SM_STR3 = 4; // PE that makes up the CellBilayer itself (structural lipid molecules)
	public static final int P_SM_INT3 = 5; // PE_InVicinity (PE in the Cytosol, located in vicinity of CellBilayer)
	// Phosphatidic_Acid
	public static final int P_SM_INT4 = 6; // Phosphatidic_Acid in the Cytosol (a small molecule)
	public static final int P_SM_STR4 = 7; // Phosphatidic_Acid momentarily within the CellBilayer structure itself
	// Ethanolamine
	public static final int P_SM_INT5 = 8; // Ethanolamine in the Cytosol (a small molecule)
	public static final int P_SM_STR5 = 9; // Ethanolamine momentarily within the CellBilayer structure itself
	// next set are used by enzymes
	public static final int P_SM_SUB1 = 0; // substrate 1
	public static final int P_SM_PRD1 = 1; // product 1
	public static final int P_SM_SUB2 = 2; // substrate 2
	public static final int P_SM_PRD2 = 3; // product 2
	public static final int P_SM_ACT1 = 4; // activator
	public static final int P_SM_INH1 = 5; // inhibitor
	public static final int P_SM_COE1 = 6; // substrate coenzyme
	public static final int P_SM_COE2 = 7; // product coenzyme
		
	public int state = 0;
	public String roleName = null;
	
	public double pheneVal = 0.0; // Phene value contained within a passive object
	private static int smCount[] = new int[IBioXholonClass.SIZE_SMCOUNT];
	// enzyme kinetics
	private static int enzymeLevel; // number of enzyme instances represented by this instance of Xholon
	private static double nTimes;
	private static double s, s1, s2;
	
	// Non-catalized rate constants (Gepasi "Mass action kinetics")
	public static final int KLipidDecay    = 1000; // Decay constant for lipids in CellBilayer
	public static final int KWaterPressure = 1000; // Pressure of water on lipids in Cytosol
	public static final int KInVicinity    =  100; // Rate at which CellBilayer incorporates PE into itself
	// Minimum amount of Water required to push PE toward CellBilayer
	public static final int MinWater = 2000;

	// exceptions
	//public SecurityException securityException;
	
	/**
	 * Constructor.
	 */
	public XhCellAutop() {}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
		state = 0;
		roleName = null;
		pheneVal = Double.MAX_VALUE;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getVal()
	 */
	public double getVal()
	{return getPheneVal();}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(double)
	 */
	public void setVal(double val)
	{setPheneVal(val);}
	
	/*
	 * @see org.primordion.cellontro.base.IBioXholon#getPheneVal()
	 */
	public double getPheneVal()
	{ return pheneVal; }
	
	/*
	 * @see org.primordion.cellontro.base.IBioXholon#setPheneVal(double)
	 */
	public void setPheneVal(double pheneVal)
	{this.pheneVal = pheneVal;}
	
	/*
	 * @see org.primordion.cellontro.base.IBioXholon#incPheneVal(double)
	 */
	public void incPheneVal( double incAmount )
	{ pheneVal += incAmount; }
	
	/*
	 * @see org.primordion.cellontro.base.IBioXholon#decPheneVal(double)
	 */
	public void decPheneVal( double decAmount )
	{ pheneVal -= decAmount; }

	// these functions are not currently used in this model, but must be overridden by subclasses
	public int getNumReactants() {return 0;}
	public void setNumReactants(int numR) {}
	public int getNumProducts() {return 0;}
	public void setNumProducts(int numP) {}
	public int getNumModifiers() {return 0;}
	public void setNumModifiers(int numM) {}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setRoleName(java.lang.String)
	 */
	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getRoleName()
	 */
	public String getRoleName()
	{
		return roleName;
	}

	
	/*
	 * @see org.primordion.xholon.base.IXholon#configure()
	 */
	public void configure()
	{
		super.configure();
		
		// set the feature type for this XholonClass
		if ((port != null) && (((IBioXholonClass)xhc).getFeatureType() == IBioXholonClass.FeatureType_Null)
				&& (((IBioXholonClass)xhc).getActiveObjectType() == IBioXholonClass.AOT_ENZYME)) {
			if (port[P_SM_SUB2] != null) {smCount[IBioXholonClass.SMCOUNT_SUBSTRATE] = 2;}
			else {smCount[IBioXholonClass.SMCOUNT_SUBSTRATE] = 1;}
			if (port[P_SM_PRD2] != null) {smCount[IBioXholonClass.SMCOUNT_PRODUCT] = 2;}
			else {smCount[IBioXholonClass.SMCOUNT_PRODUCT] = 1;}
			if (port[P_SM_ACT1] != null) {smCount[IBioXholonClass.SMCOUNT_ACTIVATOR] = 1;}
			else {smCount[IBioXholonClass.SMCOUNT_ACTIVATOR] = 0;}
			if (port[P_SM_INH1] != null) {smCount[IBioXholonClass.SMCOUNT_INHIBITOR] = 1;}
			else {smCount[IBioXholonClass.SMCOUNT_INHIBITOR] = 0;}
			if (port[P_SM_COE1] != null) {smCount[IBioXholonClass.SMCOUNT_COENZYME] = 1;}
			else {smCount[IBioXholonClass.SMCOUNT_COENZYME] = 0;}
			((IBioXholonClass)xhc).setFeatureType(smCount);
		}
	} // end configure()
	
	/*
	 * @see org.primordion.xholon.base.IXholon#preAct()
	 */
	public void preAct()
	{
		// execute recursively
		//try {
			if (firstChild != null) {
				firstChild.preAct();
			}
			if (nextSibling != null) {
				nextSibling.preAct();
			}
		//} catch (StackOverflowError e) {
		//	System.err.println("XhCellAutop preAct() stack overflow: " + this + " ");
		//	return;
		//}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#act()
	 * recursive; application should call this only for root
	 */
	public void act()
	{
		//if ((xhClass.getXhType() & IXholonClass.XhtypePureActiveObject) == IXholonClass.XhtypePureActiveObject) {
		if (isActiveObject()) {
			if (xhc.hasAncestor("Enzyme")) {
				// enzyme kinetics

				if (pheneVal == Double.MAX_VALUE) {
					enzymeLevel = 1;
				}
				else {
					enzymeLevel = (int)pheneVal; // default
				}
				switch(((IBioXholonClass)xhc).getFeatureType()) {
				
				// Irreversible Michaelis-Menten kinetics (Gepasi)
				// should use Ordered Bi Uni kinetics (Gepasi) - ex: TCA1
				// Irreversible, 2 Substrate, 1 Product, 0 Activator, 0 Inhibitor, N Coenzyme
				case IBioXholonClass.Irr_Sb2_Pr1_Ac0_In0_CoN:
					s1 = ((XhCellAutop)port[P_SM_SUB1]).getPheneVal();
					s2 = ((XhCellAutop)port[P_SM_SUB2]).getPheneVal();
					s = (s1 < s2) ? s1 : s2;
					nTimes = enzymeLevel * ((((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_V] * s) / (((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] + s));
					((XhCellAutop)port[P_SM_SUB1]).decPheneVal(nTimes);
					((XhCellAutop)port[P_SM_SUB2]).decPheneVal(nTimes);
					((XhCellAutop)port[P_SM_PRD1]).incPheneVal(nTimes);
					if (port[P_SM_COE1] != null) {
						((XhCellAutop)port[P_SM_COE1]).decPheneVal(nTimes); // coenzyme
						((XhCellAutop)port[P_SM_COE2]).incPheneVal(nTimes);
					}
					break;

				default:
					System.out.println("act() for unhandled enzyme " + getName());
					break;
				} // end switch
			}
			else if (xhc.hasAncestor("LipidBilayer")) {
				// Transport Glucose across the CellBilayer.
				((XhCellAutop)port[P_SM_INT1]).incPheneVal( ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] );
				((XhCellAutop)port[P_SM_EXT1]).decPheneVal( ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] );
				// Transport Water across the CellBilayer.
				((XhCellAutop)port[P_SM_INT2]).incPheneVal( ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] );
				((XhCellAutop)port[P_SM_EXT2]).decPheneVal( ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] );
				// Incorporate PE_InVicinity into structural Phosphatidyl_Ethanolamine.
				((XhCellAutop)port[P_SM_STR3]).incPheneVal( ((XhCellAutop)port[P_SM_INT3]).getPheneVal()
						/ KInVicinity );
				((XhCellAutop)port[P_SM_INT3]).decPheneVal( ((XhCellAutop)port[P_SM_INT3]).getPheneVal()
						/ KInVicinity );
				// Move all disintegrated Phosphatidic_Acid back to the Cytosol.
				((XhCellAutop)port[P_SM_INT4]).incPheneVal( ((XhCellAutop)port[P_SM_STR4]).getPheneVal() );
				((XhCellAutop)port[P_SM_STR4]).decPheneVal( ((XhCellAutop)port[P_SM_STR4]).getPheneVal() );
				// Move all disintegrated Ethanolamine back to the Cytosol.
				((XhCellAutop)port[P_SM_INT5]).incPheneVal( ((XhCellAutop)port[P_SM_STR5]).getPheneVal() );
				((XhCellAutop)port[P_SM_STR5]).decPheneVal( ((XhCellAutop)port[P_SM_STR5]).getPheneVal() );
			}
			else if (xhc.hasAncestor("LipidDisintegration")) {
				// Disintegrate some proportion of the lipids in the CellBilayer.
				double nTimesDec = ((XhCellAutop)port[P_SM_SUB1]).getPheneVal() / KLipidDecay;
				double nTimesInc = nTimesDec / 2.0;
				((XhCellAutop)port[P_SM_SUB1]).decPheneVal( nTimesDec );
				((XhCellAutop)port[P_SM_PRD1]).incPheneVal( nTimesInc );
				((XhCellAutop)port[P_SM_PRD2]).incPheneVal( nTimesInc );
			}
			else if ((xhc.hasAncestor("Water"))
					&& (((XhCellAutop)(getParentNode())).getXhcName().equals("Cytosol"))) {
				// Push the hydrophobic PE lipids toward the CellBilayer
				if (getPheneVal() > MinWater) {
					nTimes = ((XhCellAutop)port[P_SM_SUB1]).getPheneVal() / KWaterPressure;
					((XhCellAutop)port[P_SM_SUB1]).decPheneVal( nTimes ); // PE
					((XhCellAutop)port[P_SM_PRD1]).incPheneVal( nTimes ); // PE_InVicinity
				}
			}
		}

		// execute recursively
		//try {
			if (firstChild != null) {
				firstChild.act();
			}
			if (nextSibling != null) {
				nextSibling.act();
			}
		//} catch (StackOverflowError e) {
		//	System.err.println("XhCellAutop act() stack overflow: " + this + " ");
		//	return;
		//}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toXmlAttributes(int, org.primordion.xholon.io.xml.XmlWriter)
	 */
	public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter)
	{	
		toXmlAttributes_standard(xholon2xml, xmlWriter);
		if (getPheneVal() != Double.MAX_VALUE) {
			toXmlAttribute(xholon2xml, xmlWriter, "pheneVal", Double.toString(getPheneVal()), double.class);
		}
	}
	
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		int i = 0;
		String tStr = getName();
		//if ((xhClass.getXhType() & IXholonClass.XhtypePurePassiveObject) == IXholonClass.XhtypePurePassiveObject) {
		if (isPassiveObject()) {
			tStr += " [pheneVal:" + pheneVal
					+ "]";
		}
		//if (((xhClass.getXhType() & IXholonClass.XhtypePureActiveObject) == IXholonClass.XhtypePureActiveObject)
		if (isActiveObject() || (xhc.getXhType() == IXholonClass.XhtypeConfigContainer)) {
			if (xhc.hasAncestor("Enzyme")) {
				tStr += " [reaction:";
				if (port[P_SM_SUB1] != null) {
					tStr += port[P_SM_SUB1].getName();
				}
				else {
					tStr += "null";
				}
				if (port[P_SM_SUB2] != null) {
					tStr += " + " + port[P_SM_SUB2].getName();
				}
				tStr += " --> ";
				if (port[P_SM_PRD1] != null) {
					tStr += port[P_SM_PRD1].getName();
				}
				else {
					tStr += "null";
				}
				if (port[P_SM_PRD2] != null) {
					tStr += " + " + port[P_SM_PRD2].getName();
				}
				tStr += "]";
			}
			else if (xhc.hasAncestor("LipidBilayer")) {
				// bilayer or transporter
				tStr += " [transfer:";
				if (port[P_SM_EXT1] != null) {
					tStr += port[P_SM_EXT1].getName();
				}
				else {
					tStr += "null";
				}
				tStr += " <--> ";
				if (port[P_SM_INT1] != null) {
					tStr += port[P_SM_INT1].getName();
				}
				else {
					tStr += "null";
				}
				if ((port[P_SM_EXT2] != null) && (port[P_SM_INT2] != null)) {
					tStr += ", " + port[P_SM_EXT2].getName();
					tStr += " <--> " + port[P_SM_INT2].getName();
				}
				if ((port[P_SM_INT3] != null) && (port[P_SM_STR3] != null)) {
					tStr += ", " + port[P_SM_INT3].getName();
					tStr += " <--> " + port[P_SM_STR3].getName();
				}
				if ((port[P_SM_STR4] != null) && (port[P_SM_INT4] != null)) {
					tStr += ", " + port[P_SM_STR4].getName();
					tStr += " <--> " + port[P_SM_INT4].getName();
				}
				if ((port[P_SM_STR5] != null) && (port[P_SM_INT5] != null)) {
					tStr += ", " + port[P_SM_STR5].getName();
					tStr += " <--> " + port[P_SM_INT5].getName();
				}
				tStr += "]";
			}
			else { // show any active ports
				if (port != null) {
					tStr += "[";
					for (i = 0; i < port.length; i++) {
						if (port[i] != null) {
							tStr += " port:" + port[i].getName();
						}
					}
					tStr += "]";
				}
				else {
					tStr += " [active object with no port array]";
				}
			}
		}
		return tStr;
	}
}

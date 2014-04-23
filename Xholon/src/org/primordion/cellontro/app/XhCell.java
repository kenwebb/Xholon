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

import java.util.List;
import org.primordion.cellontro.base.BioXholonClass;
import org.primordion.cellontro.base.IBioXholon;
import org.primordion.cellontro.base.IBioXholonClass;
//import org.primordion.xholon.base.Control;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;

/**
<p>An instance of XhCell represents some biological entity,
and exists as a node in a composite structure hierarchy tree.
Each such Xholon object goes through an initial configure() process,
and may subsequently go through a regular act() process at each time step.</p>

<p>At configure() time (Basic Cell Model):</p><ul type="square">
<li>If its xhType = XhtypePureActiveObject Then</li><ul type="disc">
<li>    It will have a gene value (geneVal) that it gets from its BioXholonClass.</li>
<li>    It will search for adjacent internal (P_SM_INT) and/or external (P_SM_EXT) spaces that
        contain phene values, using instructions from BioXholonClass.</li></ul>
<li>Else if its xhType = xhTypePurePassiveObject Then</li><ul type="disc">
<li>    It will have a phene value (pheneVal) that it gets from its BioXholonClass.</li>
<li>    It will probably be acted upon by one or more xhtypePureActiveObject instances
        at each time step.</li></ul>
<li>Else if its xhType = xhtypePureContainer Then</li><ul type="disc">
<li>    It is only a container for objects of the other two xhTypes.</li></ul>
<li>Endif</li></ul>

<p>At act() time (Basic Cell Model):</p><ul type="square">
<li>If its xhType = XhtypePureActiveObject Then</li><ul type="disc">
<li>    If it's an Enzyme Then</li><ul type="circle">
<li>        Increment or decrement the internal (P_SM_INT) pheneVal it points to,
            by an amount specified by its geneVal.</li></ul>
<li>    Else if it's a Bilayer or a TransportProtein Then</li><ul type="circle">
<li>        Move geneVal number of units between the internal (P_SM_INT) pheneVal it points to,
            and the external (P_SM_EXT) pheneVal it points to.</li></ul>
<li>    Endif</li></ul>
<li>Else if its xhType = xhTypePurePassiveObject Then</li><ul type="disc">
<li>    Print the current value of its pheneVal.</li></ul>
<li>Endif</li></ul>

<p>An enzyme active object is a simple abstraction of a biological enzyme that acts on small
molecules contained within the same internal space.
A bilayer is a simple abstraction of a biological membrane (lipid bilayer) that moves small
molecules between its internal space and the space external to it.
A transport protein is a simple abstraction that acts much like a bilayer.</p>
 * 
 * <p>TODO Align this application with XhAbstractSbml, and with Xh apps generated from external SBML.</p>
 * <p>TODO Allow the kinetics to be readily exportable in SBML format.</p>
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on July 8, 2005)
 */
public class XhCell extends XholonWithPorts implements IBioXholon {

	// References to other Xholon instances.
	// These are indices into the port array.
	
	// The first set (EXT, INT) are used by bilayers and transport proteins.
	// The EXT constants must preceed all the INT ones.
	public static final int P_SM_EXT1 = 0; // a small molecule located externally outside this membrane
	public static final int P_SM_EXT2 = 1;
	public static final int P_SM_EXT3 = 2;
	public static final int P_SM_EXT4 = 3;
	public static final int P_SM_INT1 = 4; // a small molecule located internally within this membrane
	public static final int P_SM_INT2 = 5;
	public static final int P_SM_INT3 = 6;
	public static final int P_SM_INT4 = 7;
	private static final int NUM_EXTERNAL = 4; // P_SM_EXT_ 0 1 2 3
	private static final int NUM_INTERNAL = 4; // P_SM_INT_ 4 5 6 7
	
	// The next set are used by enzymes.
	// The SUB constants must preceed all the PRD ones.
	public static final int P_SM_SUB1 = 0; // substrate 1
	public static final int P_SM_SUB2 = 1; // substrate 2
	public static final int P_SM_COE1 = 2; // substrate coenzyme
	public static final int P_SM_PRD1 = 3; // product 1
	public static final int P_SM_PRD2 = 4; // product 2
	public static final int P_SM_COE2 = 5; // product coenzyme
	public static final int P_SM_ACT1 = 6; // activator
	public static final int P_SM_INH1 = 7; // inhibitor
	private static final int NUM_SUBSTRATES = 3; // P_SM_ 0 1 2
	private static final int NUM_PRODUCTS   = 3; // P_SM_ 3 4 5
	private static final int NUM_MODIFIERS  = 2; // P_SM_ 6 7
	
	public int state = 0;
	public String roleName = null;
	
	public double pheneVal = 0.0; // Phene value contained within a passive object
	private static int smCount[] = new int[IBioXholonClass.SIZE_SMCOUNT];
	// enzyme kinetics
	private static int enzymeLevel; // number of enzyme instances represented by this instance of Xholon
	private static double nTimes;
	private static double s, s1, s2, sk, ik, a;
	
	// exceptions
	//public SecurityException securityException;
	
	/**
	 * Constructor.
	 */
	public XhCell()
	{
	}
	
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

	/*
	 * @see org.primordion.cellontro.base.IBioXholon#getNumReactants()
	 */
	public int getNumReactants() {
		if (((IBioXholonClass)xhc).getActiveObjectType() == IBioXholonClass.AOT_ENZYME) {
			return NUM_SUBSTRATES;
		}
		else { // bilayer or transport protein
			return NUM_EXTERNAL;
		}
	}
	/*
	 * @see org.primordion.cellontro.base.IBioXholon#setNumReactants(int)
	 */
	public void setNumReactants(int numR) {} // unused

	/*
	 * @see org.primordion.cellontro.base.IBioXholon#getNumProducts()
	 */
	public int getNumProducts() {
		if (((IBioXholonClass)xhc).getActiveObjectType() == IBioXholonClass.AOT_ENZYME) {
			return NUM_PRODUCTS;
		}
		else { // bilayer or transport protein
			return NUM_INTERNAL;
		}
	}
	/*
	 * @see org.primordion.cellontro.base.IBioXholon#setNumProducts(int)
	 */
	public void setNumProducts(int numP) {} // unused

	/*
	 * @see org.primordion.cellontro.base.IBioXholon#getNumModifiers()
	 */
	public int getNumModifiers() {return NUM_MODIFIERS;}
	/*
	 * @see org.primordion.cellontro.base.IBioXholon#setNumModifiers(int)
	 */
	public void setNumModifiers(int numM) {} // unused

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
		//	System.err.println("XhCell_Class preAct() stack overflow: " + this + " ");
		//	return;
		//}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#act()
	 * recursive; application should call this only for root
	 * TODO should be checking to ensure the phene values can't become negative
	 */
	public void act()
	{
		//if ((xhClass.getXhType() & IXholonClass.XhtypePureActiveObject) == IXholonClass.XhtypePureActiveObject) {
		if (isActiveObject()) {
			// TODO the actual computation should be performed in an SBML-like function
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
				// Irreversible, 1 Substrate, 1 Product, 0 Activator, 0 Inhibitor, 0 Coenzyme
				case IBioXholonClass.Irr_Sb1_Pr1_Ac0_In0_Co0:
					s = ((XhCell)port[P_SM_SUB1]).getPheneVal();
					nTimes = enzymeLevel * ((((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_V] * s) / (((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] + s));
					((XhCell)port[P_SM_SUB1]).decPheneVal(nTimes);
					((XhCell)port[P_SM_PRD1]).incPheneVal(nTimes);
					break;
				
				// Irreversible Michaelis-Menten kinetics (Gepasi)
				// Irreversible, 1 Substrate, 2 Product, 0 Activator, 0 Inhibitor, 0 Coenzyme
				case IBioXholonClass.Irr_Sb1_Pr2_Ac0_In0_Co0:
					s = ((XhCell)port[P_SM_SUB1]).getPheneVal();
					nTimes = enzymeLevel * ((((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_V] * s) / (((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] + s));
					((XhCell)port[P_SM_SUB1]).decPheneVal(nTimes);
					((XhCell)port[P_SM_PRD1]).incPheneVal(nTimes);
					((XhCell)port[P_SM_PRD2]).incPheneVal(nTimes);
					break;
				
				// Irreversible Michaelis-Menten kinetics (Gepasi) - with coenzymes
				// Irreversible, 1 Substrate, 1 Product, 0 Activator, 0 Inhibitor, N Coenzyme
				case IBioXholonClass.Irr_Sb1_Pr1_Ac0_In0_CoN:
					s = ((XhCell)port[P_SM_SUB1]).getPheneVal();
					nTimes = enzymeLevel * ((((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_V] * s) / (((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] + s));
					((XhCell)port[P_SM_SUB1]).decPheneVal(nTimes);
					((XhCell)port[P_SM_PRD1]).incPheneVal(nTimes);
					// handle coenz; ex: Nad --> NadH; just like a substrate --> product
					if (port[P_SM_COE1] != null) {
						((XhCell)port[P_SM_COE1]).decPheneVal(nTimes);
						((XhCell)port[P_SM_COE2]).incPheneVal(nTimes);
					}
					break;

				// Irreversible Michaelis-Menten kinetics (Gepasi)
				// should use Ordered Bi Uni kinetics (Gepasi) - ex: TCA1
				// Irreversible, 2 Substrate, 1 Product, 0 Activator, 0 Inhibitor, N Coenzyme
				case IBioXholonClass.Irr_Sb2_Pr1_Ac0_In0_CoN:
					s1 = ((XhCell)port[P_SM_SUB1]).getPheneVal();
					s2 = ((XhCell)port[P_SM_SUB2]).getPheneVal();
					s = (s1 < s2) ? s1 : s2;
					nTimes = enzymeLevel * ((((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_V] * s) / (((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] + s));
					((XhCell)port[P_SM_SUB1]).decPheneVal(nTimes);
					((XhCell)port[P_SM_SUB2]).decPheneVal(nTimes);
					((XhCell)port[P_SM_PRD1]).incPheneVal(nTimes);
					if (port[P_SM_COE1] != null) {
						((XhCell)port[P_SM_COE1]).decPheneVal(nTimes); // coenzyme
						((XhCell)port[P_SM_COE2]).incPheneVal(nTimes);
					}
					break;

				// Noncompetitive inhibition kinetics (Gepasi); use for Hexokinase
				// Irreversible, 1 Substrate, 1 Product, 0 Activator, 1 Inhibitor, N Coenzyme
				case IBioXholonClass.Irr_Sb1_Pr1_Ac0_In1_CoN:
					sk = ((XhCell)port[P_SM_SUB1]).getPheneVal() / ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM];
					ik = ((XhCell)port[P_SM_INH1]).getPheneVal() / ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_EFF_KM];
					nTimes = enzymeLevel * ((((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_V] * sk) / (1+ik+sk*(1+ik)));
					((XhCell)port[P_SM_SUB1]).decPheneVal(nTimes);
					((XhCell)port[P_SM_PRD1]).incPheneVal(nTimes);
					if (port[P_SM_COE1] != null) {
						((XhCell)port[P_SM_COE1]).decPheneVal(nTimes); // coenzyme
						((XhCell)port[P_SM_COE2]).incPheneVal(nTimes);
					}
					break;

				// Catalytic activation kinetics (Gepasi)
				// Irreversible, 1 Substrate, 1 Product, 1 Activator, 0 Inhibitor, N Coenzyme
				case IBioXholonClass.Irr_Sb1_Pr1_Ac1_In0_CoN:
					s = ((XhCell)port[P_SM_SUB1]).getPheneVal();
					a = ((XhCell)port[P_SM_ACT1]).getPheneVal();
					nTimes = enzymeLevel * ((a * s * ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_V])
							/ ((a + ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_EFF_KM])
									* (s + ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM])));
					((XhCell)port[P_SM_SUB1]).decPheneVal(nTimes);
					((XhCell)port[P_SM_PRD1]).incPheneVal(nTimes);
					if (port[P_SM_COE1] != null) {
						((XhCell)port[P_SM_COE1]).decPheneVal(nTimes); // coenzyme
						((XhCell)port[P_SM_COE2]).incPheneVal(nTimes);
					}
					break;

				// Noncompetitive inhibition kinetics AND Catalytic activation kinetics (Gepasi)
				// Irreversible, 1 Substrate, 1 Product, N Activator, N Inhibitor, N Coenzyme
				case IBioXholonClass.Irr_Sb1_Pr1_AcN_InN_CoN:
					break;

				// Reversible Michaelis-Menten kinetics (Gepasi)
				// Reversible, 1 Substrate, 1 Product, 0 Activator, 0 Inhibitor, 0 Coenzyme
				case IBioXholonClass.Rev_Sb1_Pr1_Ac0_In0_Co0:
					break;
				
				// Reversible Michaelis-Menten kinetics (Gepasi) - with coenzymes
				// Reversible, 1 Substrate, 1 Product, 0 Activator, 0 Inhibitor, N Coenzyme
				case IBioXholonClass.Rev_Sb1_Pr1_Ac0_In0_CoN:
					break;

				// Reversible Michaelis-Menten kinetics (Gepasi) - should use Ordered Uni Bi kinetics
				// Reversible, 1 Substrate, 2 Product, 0 Activator, 0 Inhibitor, 0 Coenzyme
				case IBioXholonClass.Rev_Sb1_Pr2_Ac0_In0_Co0:
					break;

				default:
					break;
				} // end switch
			}
			else if ((((BioXholonClass)xhc).getParentNode().getName().equals("TransportProtein"))
					|| (((BioXholonClass)xhc).getParentNode().getName().equals("LipidBilayer"))) {
				// transport protein or lipid bilayer
				if (port[P_SM_INT1] != null) {
					((XhCell)port[P_SM_INT1]).incPheneVal( ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] ); // inc
				}
				if (port[P_SM_EXT1] != null) {
					((XhCell)port[P_SM_EXT1]).incPheneVal( ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] * -1 ); // dec
				}
				if (port[P_SM_INT2] != null) {
					((XhCell)port[P_SM_INT2]).incPheneVal( ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] ); // inc
				}
				if (port[P_SM_EXT2] != null) {
					((XhCell)port[P_SM_EXT2]).incPheneVal( ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] * -1 ); // dec
				}
				if (port[P_SM_INT3] != null) {
					((XhCell)port[P_SM_INT3]).incPheneVal( ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] ); // inc
				}
				if (port[P_SM_EXT3] != null) {
					((XhCell)port[P_SM_EXT3]).incPheneVal( ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] * -1 ); // dec
				}
				if (port[P_SM_INT4] != null) {
					((XhCell)port[P_SM_INT4]).incPheneVal( ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] ); // inc
				}
				if (port[P_SM_EXT4] != null) {
					((XhCell)port[P_SM_EXT4]).incPheneVal( ((BioXholonClass)xhc).geneVal[IBioXholonClass.GENEVAL_KM] * -1 ); // dec
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
		//	System.err.println("XhCell_Chart act() stack overflow: " + this + " ");
		//	return;
		//}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#postAct()
	 */
	public void postAct()
	{
	  // execute recursively
		//try {
			if (firstChild != null) {
				firstChild.postAct();
			}
			if (nextSibling != null) {
				nextSibling.postAct();
			}
		//} catch (StackOverflowError e) {
		//	System.err.println("XhCell_Chart postAct() stack overflow: " + this + " ");
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
			else if ((xhc.hasAncestor("TransportProtein"))
					|| (xhc.hasAncestor("LipidBilayer"))) {
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
				if ((port[P_SM_EXT3] != null) && (port[P_SM_INT3] != null)) {
					tStr += ", " + port[P_SM_EXT3].getName();
					tStr += " <--> " + port[P_SM_INT3].getName();
				}
				if ((port[P_SM_EXT4] != null) && (port[P_SM_INT4] != null)) {
					tStr += ", " + port[P_SM_EXT4].getName();
					tStr += " <--> " + port[P_SM_INT4].getName();
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
		else if (xhc.hasAncestor("XYSeries")) {
			if (port != null) {
				tStr += "[";
				if ((port.length > 0) && (port[0] != null)) {
					tStr += " port:" + port[0].getName();
				}
				tStr += "]";
			}
		}
		// Show BioXholon instances that reference this BioXholon instance
		List<IXholon> rnV = searchForReferencingNodes();
		if (!rnV.isEmpty()) {
			tStr += " # reffed by: ";
			for (i = 0; i < rnV.size(); i++) {
				tStr += rnV.get(i).getName() + " ";
			}
			tStr += "#";
		}
		return tStr;
	}
}

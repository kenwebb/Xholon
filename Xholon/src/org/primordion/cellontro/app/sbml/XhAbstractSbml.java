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

package org.primordion.cellontro.app.sbml;

import java.util.List;
import org.primordion.cellontro.base.BioXholonClass;
import org.primordion.cellontro.base.IBioXholon;
import org.primordion.cellontro.base.IBioXholonClass;
//import org.primordion.xholon.base.Control;
import org.primordion.xholon.base.IIntegration;
import org.primordion.xholon.base.IXholon;
//import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;

/**
 * SBML Application.
 * <p>This class will be automatically extended if you use Sbml2Cellontro to build a
 * Cellontro application that executes a model read from
 * a Systems Biology Markup Language (SBML) file. SBML is used to exchange models between
 * different software packages.</p>
 * <p>An instance of XhAbstractSbml represents some biological entity,
 * and exists as a node in a composite structure hierarchy tree.
 * Each such Xholon/Cellontro object goes through an initial configure() process,
 * and may subsequently go through a regular act() process at each time step.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.2 (Created on December 8, 2005)
 */
public abstract class XhAbstractSbml extends XholonWithPorts implements IBioXholon {
	
	//public int state;
	public String roleName = null;
	
	public double pheneVal = 0.0; // Phene value contained within a passive object
	// enzyme kinetics
	protected static int enzymeLevel = 1; // number of enzyme instances represented by this instance of Xholon
	protected static double nTimes;
	
	// exceptions
	//public SecurityException securityException;
	
	/**
	 * Constructor.
	 */
	public XhAbstractSbml() {}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
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
	
	// abstract methods that must be overridden in concrete classes
	/* @see org.primordion.cellontro.base.IBioXholon#getNumReactants() */
	public abstract int getNumReactants();
	/* @see org.primordion.cellontro.base.IBioXholon#setNumReactants(int) */
	public abstract void setNumReactants(int numR);
	/* @see org.primordion.cellontro.base.IBioXholon#getNumProducts() */
	public abstract int getNumProducts();
	/* @see org.primordion.cellontro.base.IBioXholon#setNumProducts(int) */
	public abstract void setNumProducts(int numP);
	/* @see org.primordion.cellontro.base.IBioXholon#getNumModifiers() */
	public abstract int getNumModifiers();
	/* @see org.primordion.cellontro.base.IBioXholon#setNumModifiers(int) */
	public abstract void setNumModifiers(int numM);
	
	// override these default behaviors in concrete classes
	/**
	 * Get the time step multiplier, needed for numerical integration.
	 * @return Time step multiplier.
	 */
	public int getTimeStepMultiplier() {return IIntegration.M_DEFAULT;}
	/**
	 * Set the time step multiplier, needed for numerical integration.
	 * @param timeStepMultiplier Time step multiplier.
	 */
	public static void setTimeStepMultiplier(int timeStepMultiplier) {};
	
	/*
	 * @see org.primordion.xholon.base.IXholon#configure()
	 */
	public void configure()
	{
		super.configure();
		
		// Active Objects
		// Try to set the active object type.
		//if ((xhClass.getXhType() & IXholonClass.XhtypePureActiveObject) == IXholonClass.XhtypePureActiveObject) {
		if (isActiveObject()) {
			if (((IBioXholonClass)xhc).getActiveObjectType() == IBioXholonClass.AOT_NULL) {
				if (port != null) {
					// Do all ports reference xholons that are in the same container? If so, assume Enzyme.
					if (port[0] != null) {
						IXholon container = port[0].getParentNode();
						for (int i = 1; i < port.length; i++) {
							if ((port[i] != null) && (container != port[i].getParentNode())) {
								// assume it's a bilayer; can't distinguish bilayer from transport protein
								((IBioXholonClass)xhc).setActiveObjectType(IBioXholonClass.AOT_BILAYER);
								break;
							}
						}
						// assume it's an enzyme; all substrates, products and modifiers are in same container
						((IBioXholonClass)xhc).setActiveObjectType(IBioXholonClass.AOT_ENZYME);
					}
				}
			}
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
		//	System.err.println("XhAbstractSbml preAct() stack overflow: " + this + " ");
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
		int i, j;
		String tStr = getName();
		//if ((xhClass.getXhType() & IXholonClass.XhtypePurePassiveObject) == IXholonClass.XhtypePurePassiveObject) {
		if (isPassiveObject()) {
			tStr += " [pheneVal:" + pheneVal
					+ "]";
		}
		//if (((xhClass.getXhType() & IXholonClass.XhtypePureActiveObject) == IXholonClass.XhtypePureActiveObject)
		if (isActiveObject() || (xhc.getXhType() == IXholonClass.XhtypeConfigContainer)) {
			switch(((IBioXholonClass)xhc).getActiveObjectType()) {
			case IBioXholonClass.AOT_ENZYME:
				// [reaction:One + Two --> Three]
				tStr += " [reaction:";
				for (i = 0, j = 0; i < getNumReactants(); i++, j++) {
					if (port[j] != null) {
						if (i > 0) {
							tStr += " + ";
						}
						tStr += port[j].getName();
					}
				}
				if (((BioXholonClass)xhc).reversible) {
					tStr += " = ";
				}
				else {
					tStr += " -> ";
				}
				for (i = 0; i < getNumProducts(); i++, j++) {
					if (port[j] != null) {
						if (i > 0) {
							tStr += " + ";
						}
						tStr += port[j].getName();
					}
				}
				for (i = 0; i < getNumModifiers(); i++, j++) {
					if (port[j] != null) {
						if (i > 0) {
							tStr += ", ";
						}
						else {
							tStr += "; ";
						}
						tStr += port[j].getName();
					}
				}
				tStr += "]";
				break;
			case IBioXholonClass.AOT_BILAYER: // can't distinguish bilayer from transporter
			case IBioXholonClass.AOT_TRANSPORTER:
				// [transfer:One <-> Two, Three <-> Four]
				tStr += " [transfer:";
				tStr += "]";
				break;
			default:
				break;
			} // end switch
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

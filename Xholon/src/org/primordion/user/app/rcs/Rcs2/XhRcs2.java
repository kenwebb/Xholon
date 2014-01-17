/* Xholon Runtime Framework - executes event-driven & dynamic applications
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

package org.primordion.user.app.rcs.Rcs2;

import org.primordion.xholon.util.MiscRandom;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.XholonWithPorts;

/**
 * Regulated Catalyzing System - Glycogen Phosphorylase (GP) - Molecular Machine (MM).
 * This version of the GP system uses the concept of a molecular machine.
 * It is manually coded rather than generated from a UML MagicDraw model.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on October 21, 2006)
 */
public class XhRcs2 extends XholonWithPorts implements CeRcs2 {

	// Ports
	public static final int Substrate = 0; // Catalyst, Regulator
	public static final int Product = 1; // Catalyst
	public static final int Regulation = 1; // Regulator
	public static final int AxpPort = 2; // Regulator references ATP or ADP
	
	// Maximum quantity of Substrate.
	// This is only an approximate upper limit because the Regulator enzymes act probabilistically.
	private static final int MAX_SUB = 20;
	
	// xholon classes for use in activate() and deactivate()
	private static IXholonClass adpXholonClass = null;    // ADP
	private static IXholonClass atpXholonClass = null;    // ATP
	private static IXholonClass gPaseAXholonClass = null; // GlycogenPhosphorylaseA
	private static IXholonClass gPaseBXholonClass = null; // GlycogenPhosphorylaseB
	//private static XholonClass glucoseXholonClass = null;    // Glucose
	private static IXholonClass g1pXholonClass = null;    // Glucose_1_Phosphate
	
	private static IXholon axp[] = new IXholon[2];
	
	// Role name.
	public String roleName = null;

	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
		roleName = null;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#initStatics()
	 */
	public void initStatics()
	{
		adpXholonClass = getClassNode("Adp");
		atpXholonClass = getClassNode("Atp");
		gPaseAXholonClass = getClassNode("GlycogenPhosphorylaseA");
		gPaseBXholonClass = getClassNode("GlycogenPhosphorylaseB");
		//glucoseXholonClass = getClassNode("Glucose");
		g1pXholonClass = getClassNode("Glucose_1_Phosphate");
	}
	
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
	 * @see org.primordion.xholon.tutorials.rcs.XhAbstractRcs#getVal()
	 */
	public double getVal()
	{
		// count number of children and return that
		return getNumChildren(false);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#act()
	 */
	public void act()
	{
		int rInt; // random integer
		int numSubstrate = 0; // number of substrate molecules currently existing
		
		switch(xhc.getId()) {
		
		case GlycogenPhosphorylaseACE: // active form of the catalyst
			if (port[Substrate].hasChildNodes()) {
				// Remove a glucose from the end of the glycogen
				IXholon glucose = port[Substrate].getFirstChild();
				if (glucose != null) {
					glucose.removeChild();
					// Remove a phosphoryl group from an atp; atp --> adp
					IXholon atp = port[AxpPort];
					if (atp != null) {
						IXholon pGrp = atp.getFirstChild();
						if (pGrp != null) {
							pGrp.removeChild();
							atp.setXhc(adpXholonClass);
							// Glucose + PhosphorylGroup --> Glucose_1_Phosphate
							pGrp.appendChild(glucose);
							glucose.setXhc(g1pXholonClass);
							// Put the Glucose_1_Phosphate into solution
							glucose.appendChild(port[Product]);
							// Set port so the catalyst will use the next atp in the next time step
							port[AxpPort] = atp.getNextSibling();
						}
					}
				}
			}
			break;
		
		case PhosphorylaseKinaseCE:
			numSubstrate = port[Substrate].getNumChildren(false);
			rInt = MiscRandom.getRandomInt(0, numSubstrate+1);
			if (rInt == 0) {
				axp[0] = port[AxpPort];
				axp[1] = port[AxpPort].getNextSibling();
			    ((XhRcs2)port[Regulation]).activate(axp);
			}
			break;
		
		case PhosphorylasePhosphataseCE:
			numSubstrate = port[Substrate].getNumChildren(false);
			rInt = numSubstrate;
			if (rInt < MAX_SUB) {
				rInt = MiscRandom.getRandomInt(rInt, MAX_SUB+1);
			}
			if (rInt >= MAX_SUB) {
				axp[0] = port[AxpPort];
				axp[1] = port[AxpPort].getNextSibling();
			    ((XhRcs2)port[Regulation]).deactivate(axp);
			}
			break;
		
		default:
			break;
		}
		
		super.act();
	}

    /**
     * Catalyst should deactivate itself if not already inactive.
     * If it contains phosphoryl groups, it should move these to Adp molecules.
     * @param adp Adp molecules in the environment that can accept phosphoryl groups.
     */
    protected void deactivate(IXholon adp[])
    {
        if (hasChildNodes() && (getFirstChild()).getXhcId() == PhosphorylGroupCE) {
        	for (int i = 0; i < adp.length; i++) {
	        	IXholon phosphorylGroup = getFirstChild();
	        	phosphorylGroup.removeChild();
	            phosphorylGroup.insertFirstChild(adp[i]);
	            // set its type from Adp to Atp
	            adp[i].setXhc(atpXholonClass);
        	}
    		// set this type from GlycogenPhosphorylaseACE to GlycogenPhosphorylaseBCE
    		setXhc(gPaseBXholonClass);
        }
    }

    /**
     * Catalyst should activate itself if not already active.
     * If it does not contain phosphoryl groups, it should get two from the ATP.
     * @param atp Atp molecules in the environment, each of which can donate a phosphoryl group.
     */
    protected void activate(IXholon atp[])
    {
    	if (!(hasChildNodes() && (getFirstChild()).getXhcId() == PhosphorylGroupCE)) {
    		for (int i = 0; i < atp.length; i++) {
	    		IXholon phosphorylGroup = atp[i].getFirstChild();
	    		phosphorylGroup.removeChild();
	    		phosphorylGroup.insertFirstChild(this);
	    		// set its type from Atp to Adp
	    		atp[i].setXhc(adpXholonClass);
    		}
    		// set this type from GlycogenPhosphorylaseBCE to GlycogenPhosphorylaseACE
    		setXhc(gPaseAXholonClass);
    	}
    }

	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String outStr = getName();
		
		if ((port != null) && (port.length > 0)) {
			outStr += " [";
			for (int i = 0; i < port.length; i++) {
				if (port[i] != null) {
					outStr += " port:" + port[i].getName();
				}
			}
			outStr += "]";
		}
		switch(xhc.getId()) {
		case GlycogenPhosphorylaseACE: // active form of the catalyst
			outStr += " state:Active";
			break;
		case GlycogenPhosphorylaseBCE: // inactive form of the catalyst
			outStr += " state:Inactive";
			break;
		case GlycogenCE:
		case SolutionCE:
			outStr += " val:" + getVal();
		default:
			break;
		}
		
		//if (xhClass.hasAncestor("Catalyst")) {
		//	outStr += " state:" + (hasChildNodes() ? "Inactive" : "Active");
		//}
		//else if (xhClass.hasAncestor("Glycogen") || xhClass.hasAncestor("Solution")) {
		//	outStr += " val:" + getVal();
		//}
		return outStr;
	}
}

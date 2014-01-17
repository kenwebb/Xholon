/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2007 Ken Webb
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
 */

package org.primordion.dynsys.app;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.AbstractGrid;
import org.primordion.xholon.base.IIntegration;

/**
 * Dynamical Systems - ScheffranNActor.
 * @author Ken Webb
 * @since 0.5 (Created on January 16, 2007)
 */
public class XhScheffranNActor extends AbstractGrid implements CeScheffranNActor {
	
	// Input parameters.
	/** Number of actors. */
	protected static int numActors = 2; // default is 2 if nothing specified in config file

	// time step
	public static final int timeStepMultiplier = IIntegration.M_128; // M_128
	
	// xholon attributes
	public double value = 0.0;
	public double cost = 0.0;
	public double f[] = new double[numActors];
	public double valueGoal = 0.0;
	public double k = 0.0;
	public double maxCost = 0.0;
	public double tau = 0.0;
	private double dC = 0.0;
	private double dV = 0.0;
	
	public String roleName = null;
	
	/*
	 * @see org.primordion.xholon.base.ITreeNode#initialize()
	 */
	public void initialize()
	{
		super.initialize();
		roleName = null;
	}

	/*
	 * @see org.primordion.xholon.base.Xholon#getVal()
	 */
	// for use by Viewers
	public double getVal() {return value;} // return cost or value
	
	// for use by other actor
	public double getVal_double() {return cost;}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(double)
	 */
	public void setVal(double val) {{ this.cost = val; }}
	
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	/*public double getF1() {
		return f1;
	}

	public void setF1(double f1) {
		this.f1 = f1;
	}

	public double getF2() {
		return f2;
	}

	public void setF2(double f2) {
		this.f2 = f2;
	}*/

	public double getValueGoal() {
		return valueGoal;
	}

	public void setValueGoal(double valueGoal) {
		this.valueGoal = valueGoal;
	}

	public double getK() {
		return k;
	}

	public void setK(double k) {
		this.k = k;
	}

	public double getMaxCost() {
		return maxCost;
	}

	public void setMaxCost(double maxCost) {
		this.maxCost = maxCost;
	}

	public double getTau() {
		return tau;
	}

	public void setTau(double tau) {
		this.tau = tau;
	}

	public double getDC() {
		return dC;
	}

	public void setDC(double dc) {
		dC = dc;
	}

	public double getDV() {
		return dV;
	}

	public void setDV(double dv) {
		dV = dv;
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
	 * @see org.primordion.xholon.base.ITreeNode#act()
	 */
	public void act()
	{
		switch(xhc.getId()) {
		case ActorCE:
			dV = 0.0;
			for (int i = 0; i < f.length; i++) {
				dV += f[i] * port[i].getVal_double();
				//System.out.println(getName() + " " + i +  " " + dV + " += " + f[i] + " * " + port[i].getVal_double());
			}
			dC = -k * cost * (maxCost - cost) * (value - valueGoal + (tau * dV));
			dV /=  timeStepMultiplier;
			dC /=  timeStepMultiplier;
			value += dV;
			cost += dC;
			setVal(cost);
			break;
		
		default:
			break;
		}
		
		super.act();
	}
	
	/**
	 * Set number of actors.
	 * @param tNumber The number of actors.
	 */
	public static void setNumActors(int nActors)
	{
		numActors = nActors;
	}
	
	/**
	 * Get the number of actors.
	 * @return The number of actors.
	 */
	public static int getNumActors()
	{
		return numActors;
	}
	
	public int setAttributeVal(String attrName, String attrVal) {
	  if ("f".equals(attrName)) {
	    String[] farr = attrVal.split(",");
	    for (int i = 0; i < farr.length; i++) {
	      f[i] = Double.parseDouble(farr[i]);
	    }
	    return 0;
	  }
	  IApplication app = this.getApp();
	  Class clazz = XhScheffranNActor.class;
	  if (app.isAppSpecificAttribute(this, clazz, attrName)) {
	    app.setAppSpecificAttribute(this, clazz, attrName, attrVal);
	  }
	  return 0;
	}
	
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String outStr = getName();
		switch(xhc.getId()) {
		case ActorCE:
			if ((port != null) && (port.length > 0)) {
				outStr += " [";
				for (int i = 0; i < port.length; i++) {
					if (port[i] != null) {
						outStr += " port:" + port[i].getName();
					}
				}
				outStr += "]";
			}
			outStr += " value:" + value;
			outStr += " cost:" + cost;
			outStr += " f:";
			for (int i = 0; i < f.length; i ++) {
				outStr += f[i] + " ";
			}
			outStr += " valueGoal:" + valueGoal;
			outStr += " k:" + k;
			outStr += " maxCost:" + maxCost;
			outStr += " tau:" + tau;
			break;
		default:
			break;
		}
		return outStr;
	}
}

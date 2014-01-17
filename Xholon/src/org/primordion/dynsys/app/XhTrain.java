/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2007, 2008 Ken Webb
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

package org.primordion.dynsys.app;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IIntegration;
import org.primordion.xholon.base.XholonWithPorts;

/**
 * Dynamical Systems - Train.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on January 20, 2007)
 */
public class XhTrain extends XholonWithPorts implements CeTrain {

	// ports
	public static final int P_Spring = 0;
	public static final int P_Mass1 = 0;
	public static final int P_Mass2 = 1;
	
	// time step multiplier
	public static final int timeStepMultiplier = IIntegration.M_8; // M_8 or 28
	
	// units
	// N is kg*m/sec^2 (Newton, a unit of force)
	// mu*g*mass is kg/sec
	// velocity*mu*g*mass is kg*m/sec^2
	
	// constants for this model
	public static final double k = 1.0; // Spring stiffness coefficient; units:N/sec
	public static final double mu = 0.002; // units:sec/m
	public static final double g = 9.8; // units:m/sec^2
	
	// xholon attributes
	public double mass = 0.0; // mass of the engine or car; units:kg
	public double force = 0.0; // force applied by the engine; units:N
	public double acceleration = 0.0; // units:m/sec^2
	public double velocity = 0.0; // units:m/sec
	public double xPosition = 0.0; // position in the x direction; units:m
	// 
	public double friction = 0.0; // units:N
	public double sumOfForces = 0.0; // units:N
	
	//private double val = 0.0;
	public String roleName = null;
	
	// Input parameters.
	/** Type of data to plot on a chart.
	 * 1 xPosition
	 * 2 velocity
	 * 3 acceleration
	 */
	protected static int plotType = 1; // default is 1 if nothing specified in config file
	
	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
		roleName = null;
	}

	/*
	 * @see org.primordion.xholon.base.Xholon#getVal()
	 */
	public double getVal() {
		switch (plotType) {
		case 1:
			return xPosition;
		case 2:
			return velocity;
		case 3:
			return acceleration;
		default:
			return 0.0;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(double)
	 */
	public void setVal(double val) {{ this.xPosition = val; }}
	
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
	 * @see org.primordion.xholon.base.IXholon#act()
	 */
	public void act()
	{
		switch(xhc.getId()) {
		case EngineCE:
			force = getApp().getTimeStep() < (1000 * 0.5) ? +1 : -1;
			friction = velocity * mu * g * mass;
			sumOfForces = force - friction - ((XhTrain)port[P_Spring]).force;
			acceleration = sumOfForces / mass;
			velocity = velocity + (acceleration / timeStepMultiplier);
			xPosition = xPosition + (velocity / timeStepMultiplier);
			break;
		case CouplerCE: // spring
			force = ((((XhTrain)port[P_Mass1]).xPosition - ((XhTrain)port[P_Mass2]).xPosition) * k);
			break;
		case CarCE:
			friction = velocity * mu * g * mass;
			sumOfForces = ((XhTrain)port[P_Spring]).force - friction;
			acceleration = sumOfForces / mass;
			velocity = velocity + (acceleration / timeStepMultiplier);
			xPosition = xPosition + (velocity / timeStepMultiplier);
			break;
		default:
			break;
		}
		super.act();
	}
	
	/**
	 * Set which type of data to plot on a chart.
	 * @param pType The type of data.
	 */
	public static void setPlotType(int pType)
	{
		plotType = pType;
	}
	
	/**
	 * Get the type of data to plot.
	 * @return The plot type.
	 */
	public static int getPlotType()
	{
		return plotType;
	}
	
	public void setMass(double mass) {this.mass = mass;}
	public double getMass() {return mass;}
	
	public void setForce(double force) {this.force = force;}
	public double getForce() {return force;}
	
	public void setAcceleration(double acceleration) {this.acceleration = acceleration;}
	public double getAcceleration() {return acceleration;}
	
	public void setVelocity(double velocity) {this.velocity = velocity;}
	public double getVelocity() {return velocity;}
	
	public void setXPosition(double xPosition) {this.xPosition = xPosition;}
	public double getXPosition() {return xPosition;}
	
	public int setAttributeVal(String attrName, String attrVal) {
	  //IApplication app = this.getApp();
	  //Class clazz = XhTrain.class;
	  //if (app.isAppSpecificAttribute(this, clazz, attrName)) {
	  //  app.setAppSpecificAttribute(this, clazz, attrName, attrVal);
	  //}
	  /*else {
	    System.out.println("XhTrain isAppSpecificAttribute() NO: " + attrName);
	    if ("mass".equals(attrName)) {
	      setMass(Double.parseDouble(attrVal));
	    }
	    if ("force".equals(attrName)) {
	      setForce(Double.parseDouble(attrVal));
	    }
	    if ("acceleration".equals(attrName)) {
	      setAcceleration(Double.parseDouble(attrVal));
	    }
	    if ("velocity".equals(attrName)) {
	      setVelocity(Double.parseDouble(attrVal));
	    }
	    if ("xPosition".equals(attrName)) {
	      setXPosition(Double.parseDouble(attrVal));
	    }
	  }*/
	  Class clazz = XhTrain.class;
	  this.getApp().setAppSpecificAttribute(this, clazz, attrName, attrVal);
	  return 0;
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
		case EngineCE:
			outStr += " mass:" + mass;
			outStr += " force:" + force;
			outStr += " friction:" + friction;
			outStr += " sumOfForces:" + sumOfForces;
			outStr += " acceleration:" + acceleration;
			outStr += " velocity:" + velocity;
			outStr += " xPosition:" + xPosition;
			break;
		case CarCE:
			outStr += " mass:" + mass;
			outStr += " friction:" + friction;
			outStr += " sumOfForces:" + sumOfForces;
			outStr += " acceleration:" + acceleration;
			outStr += " velocity:" + velocity;
			outStr += " xPosition:" + xPosition;
			break;
		case CouplerCE:
			outStr += " force:" + force;
			break;
		default:
			break;
		}
		return outStr;
	}
}

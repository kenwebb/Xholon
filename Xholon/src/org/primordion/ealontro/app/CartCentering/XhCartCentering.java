/* Ealontro - systems that evolve and adapt to their environment
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

package org.primordion.ealontro.app.CartCentering;

import org.primordion.xholon.base.IActivity;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonWithPorts;

/**
 * Cart Centering System with Genetic Programming.
 * <p>source: Koza, J. (1992). Genetic Programming. p.122-147</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on May 11, 2006)
 */
public class XhCartCentering extends XholonWithPorts implements IActivity, CeCartCentering {
	
	//                  ports
	// Cart, Force
	public static final int P_XPOSITION    = 0;
	public static final int P_VELOCITY     = 1;
	public static final int P_ACCELERATION = 2;
	// Force
	public static final int P_CART         = 3; // to get mass
	public static final int P_BEHAVIOR     = 4;
	
	// state variables, control variables
	private static final double NEGATIVE_ONE = -1.0;
	public double val = 0.0; // xPosition, velocity, acceleration, mass, force
	public String roleName = null;

	/**
	 * Constructor.
	 */
	public XhCartCentering() {}

	/*
	 * @see org.primordion.xholon.base.IXholon#initialize()
	 */
	public void initialize()
	{
		super.initialize();
		val = 0.0;
		roleName = null;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getVal()
	 */
	public double getVal()
	{ return this.val; }
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setVal(double)
	 */
	public void setVal(double val)
	{ this.val = val; }
	
	/*
	 * @see org.primordion.xholon.base.IXholon#incVal(double)
	 */
	public void incVal(double incAmount)
	{ this.val += incAmount; }
	
	/*
	 * @see org.primordion.xholon.base.IXholon#decVal(double)
	 */
	public void decVal(double decAmount)
	{ this.val += decAmount; }
	
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
		super.act();
		switch(xhc.getId()) {
		case ForceCE:
			// behavior as specified by GeneticProgram
			val = performDoubleActivity(port[P_BEHAVIOR].getFirstChild());
			port[P_ACCELERATION].setVal(val / ((XhCartCentering)port[P_CART]).getVal());
			break;
		case CartCE:
			//velocity = velocity + acceleration;
			//xPosition = xPosition + velocity;
			port[P_VELOCITY].incVal(port[P_ACCELERATION].getVal()); // velocity = velocity + acceleration;
			port[P_XPOSITION].incVal(port[P_VELOCITY].getVal()); // xPosition = xPosition + velocity;
			break;
		default:
			break;
		} // end switch
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#performDoubleActivity(org.primordion.xholon.base.IXholon)
	 */
	public double performDoubleActivity(IXholon activity)
	{
		switch(activity.getXhcId()) {
		// Non-terminal Functions
		case PfAddCE:
			return performDoubleActivity(activity.getFirstChild())
			+ performDoubleActivity(activity.getFirstChild().getNextSibling());
		case PfSubtractCE:
			return performDoubleActivity(activity.getFirstChild())
			- performDoubleActivity(activity.getFirstChild().getNextSibling());
		case PfMultiplyCE:
			return performDoubleActivity(activity.getFirstChild())
			* performDoubleActivity(activity.getFirstChild().getNextSibling());
		case PfDivideCE: // handle divide by 0
			double numerator = performDoubleActivity(activity.getFirstChild());
			double denominator =  performDoubleActivity(activity.getFirstChild().getNextSibling());
			if (denominator == 0.0) {
				return 1.0;
			}
			else {
				return numerator / denominator;
			}
		case PfABSCE:
			return Math.abs(performDoubleActivity(activity.getFirstChild()));
		case PfGTCE:
			return performDoubleActivity(activity.getFirstChild())
			> performDoubleActivity(activity.getFirstChild().getNextSibling())
			? +1.0 : -1.0;
		// Terminals
		case PfXPositionCE:
			// use static Force ref to Cart, rather than the dynamic Activity ref to Cart
			return port[P_XPOSITION].getVal();
		case PfVelocityCE:
			// use static Force ref to Cart, rather than the dynamic Activity ref to Cart
			return port[P_VELOCITY].getVal();
		case PfNegOneCE:
			return NEGATIVE_ONE;
		// top level activity ProgN
		case PfWrapperCE:
			return performDoubleActivity(activity.getFirstChild()) > 0.0 ? +1.0 : -1.0;
		default:
			System.out.println("XhCartCentering: behavior for activity " + activity.getXhcName() + " not yet implemented");
			return 0.0;
		}
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String outStr = getName();
		IXholon node;
		switch(xhc.getId()) {
		case GeneticProgramCE:
			outStr += ", sizeOfPfTree:" + (treeSize() - 1) + "]";
			break;
		case CartCenteringSystemCE:
			node = getXPath().evaluate("descendant::XPosition", this); // get xPosition
			outStr += " [xPosition:" + node.getVal();
			node = node.getNextSibling();
			outStr += " velocity:" + node.getVal();
			node = node.getNextSibling();
			outStr += " acceleration:" + node.getVal() + "]";
			break;
		case ForceCE:
			break;
		case CartCE:
			outStr += " [mass:" + val + "]";
			break;
		case XPositionCE:
		case VelocityCE:
		case AccelerationCE:
			outStr += " [val:" + val + "]";
			break;
		default:
			break;
		} // end switch
		if ((port != null) && (port.length > 0)) {
			outStr += " [";
			for (int i = 0; i < port.length; i++) {
				if (port[i] != null) {
					outStr += " port:" + port[i].getName();
				}
			}
			outStr += "]";
		}
		return outStr;
	}
}

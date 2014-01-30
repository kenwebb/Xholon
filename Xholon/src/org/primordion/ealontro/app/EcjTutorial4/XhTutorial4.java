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

package org.primordion.ealontro.app.EcjTutorial4;

import org.primordion.xholon.base.IActivity;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonWithPorts;

/**
 * ECJ Tutorial 4.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on June 12, 2006)
 */
public class XhTutorial4 extends XholonWithPorts implements IActivity, CeTutorial4 {
	
	// ports; used by MultiValuedRegression
	public static final int P_BEHAVIOR        = 0;
	public static final int P_XVARIABLE       = 1;
	public static final int P_YVARIABLE       = 2;
	public static final int P_RESULT          = 3;
	public static final int P_EXPECTED_RESULT = 4;
	
	// state variables, control variables
	public double val = 0.0;
	public String roleName = null;
	
	/**
	 * Constructor.
	 */
	public XhTutorial4() {}

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
	{ this.roleName = roleName; }
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getRoleName()
	 */
	public String getRoleName()
	{ return roleName; }
	
	/*
	 * @see org.primordion.xholon.base.IXholon#act()
	 */
	public void act()
	{
		switch(xhc.getId()) {
		case MultiValuedRegressionCE:
			// behavior as specified by Behavior xholon
			port[P_RESULT].setVal(performDoubleActivity(port[P_BEHAVIOR].getFirstChild()));
			double currentX = x();
			double currentY = y();
			port[P_EXPECTED_RESULT].setVal(currentX * currentX * currentY + currentX * currentY + currentY);
			port[P_XVARIABLE].incVal(0.1);
			port[P_YVARIABLE].incVal(0.1);
			break;
		default:
			break;
		}
		super.act();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#performDoubleActivity(org.primordion.xholon.base.IXholon)
	 */
	public double performDoubleActivity(IXholon activity)
	{
		switch(activity.getXhcId()) {
		case PfWrapperCE: return wrapper(activity);
		// non-terminals
		case PfAddCE:     return add(activity);
		case PfSubCE:     return sub(activity);
		case PfMulCE:     return mul(activity);
		// terminals
		case PfXCE:       return x();
		case PfYCE:       return y();
		default:
			System.out.println("XhTutorial4: behavior for activity " + activity.getXhcName() + " not yet implemented");
			return 0.0;
		}
	}

	/**
	 * Primitive Function - Wrapper.
	 * @param activity The IXholon activity node.
	 */
	protected double wrapper(IXholon activity)
	{
		return performDoubleActivity(activity.getFirstChild());
	}
	
	protected double add(IXholon activity)
	{
		IXholon activityNode = activity.getFirstChild();
		return performDoubleActivity(activityNode)
		+ performDoubleActivity(activityNode.getNextSibling());
	}

	protected double sub(IXholon activity)
	{
		IXholon activityNode = activity.getFirstChild();
		return performDoubleActivity(activityNode)
		- performDoubleActivity(activityNode.getNextSibling());
	}
	
	protected double mul(IXholon activity)
	{
		IXholon activityNode = activity.getFirstChild();
		return performDoubleActivity(activityNode)
		* performDoubleActivity(activityNode.getNextSibling());
	}
	
	protected double x()
	{
		return port[P_XVARIABLE].getVal();
	}

	protected double y()
	{
		return port[P_YVARIABLE].getVal();
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String outStr = getName();
		IXholon node;
		switch(xhc.getId()) {
		case GeneticProgramCE:
			outStr += "[sizeOfPfTree:" + (treeSize() - 1)
					+ "]";
			break;
		case EcjTutorial4SystemCE:
			node = getFirstChild().getNextSibling();
			outStr += "[sizeOfPfTree:" + (node.treeSize() - 1) + "]";
			break;
		case MultiValuedRegressionCE:
			break;
		case XVariableCE:
		case YVariableCE:
		case ResultCE:
		case ExpectedResultCE:
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

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

package org.primordion.user.app.StupidModel.sm16nl;

import org.primordion.xholon.base.Patch;

/**
	StupidModel16nl application - Xholon Java
	<p>Xholon 0.5 http://www.primordion.com/Xholon</p>
*/
public class HabitatCellStupidModel16nl extends Patch implements CeStupidModel16nl {
	
	private double foodAvailability = 0.0;
	public double foodProductionRate = 0.0;
	
	//public static String stupidCellData = null;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#performBooleanActivity(int)
	 */
	public boolean performBooleanActivity(int filterId)
	{
		switch(filterId) {
		case 101:
			if (hasChildNodes()) {return false;}
			else {return true;}
		default:
			return true;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.AbstractGrid#postConfigure()
	 */
	public void postConfigure()
	{
		switch(getXhcId()) {
		case GridCE:
			break;
		default:
			break;
		}
		super.postConfigure();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#act()
	 */
	public void act()
	{
		switch(getXhcId()) {
		case HabitatCellCE:
			//double foodProduction = Misc.getRandomDouble(0, maxFoodProductionRate);
			foodAvailability += foodProductionRate;
			// DO NOT directly execute children, which are of type "Bug"
			if (nextSibling != null) {
				nextSibling.act();
			}
			break;
		default:
			super.act();
			break;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal()
	 */
	public double getVal()
	{
		return foodAvailability;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(double)
	 */
	public void setVal(double val)
	{
		foodAvailability = val;
	}
	
	/*
	 * @see org.primordion.xholon.base.GridEntity#toString()
	 */
	public String toString()
	{
		return super.toString()
			+ " foodAvailability:" + foodAvailability
			+ "foodProductionRate:" + foodProductionRate;
	}
}

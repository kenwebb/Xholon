package org.primordion.user.app.Bestiary;

import org.primordion.xholon.base.GridEntity;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.util.MiscRandom;

/**
 * Habitat grid cell. This is where the beasts hang out.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on May 27, 2010)
*/
public class HabitatCellBestiary extends GridEntity {
	
	public static double maxFoodProductionRate = 0.01;
	
	private double foodAvailability = 0.0;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#act()
	 */
	public void act()
	{
		double foodProduction = MiscRandom.getRandomDouble(0, maxFoodProductionRate);
		foodAvailability += foodProduction;
		// DO NOT directly execute children, which are of type "Beast" (Cat, Human)
		if (nextSibling != null) {
			nextSibling.act();
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
	 * @see org.primordion.xholon.base.Xholon#doAction(java.lang.String)
	 */
	public void doAction(String action)
	{
		// button 1 was pressed while mouse was over this grid cell
		// give all children and other descendants a chance to handle the button press
		IXholon node = getFirstChild();
		while (node != null) {
			node.doAction(action);
			node = node.getNextSibling();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.GridEntity#toString()
	 */
	public String toString()
	{
		//return super.toString() + " foodAvailability:" + foodAvailability;
		StringBuilder sb = new StringBuilder()
		.append(getXhcName())
		.append(" foodAvailability:")
		.append(foodAvailability)
		.append(" row:")
		.append(getRow())
		.append(" col:")
		.append(getCol());
		if (hasChildNodes()) {
			sb.append(" children:");
			IXholon node = getFirstChild();
			while (node != null) {
				sb.append(" ").append(node.getXhcName()).append("_").append(node.getId());
				node = node.getNextSibling();
			}
		}
		return sb.toString();
	}
}

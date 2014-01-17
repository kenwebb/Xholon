package org.primordion.user.app.Bestiary;

/**
 * A cat may grow each time step.
 * <p>
 * &lt;GrowingCatbehavior/>
 * </p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on May 27, 2010)
 */
public class GrowingCatbehavior extends BeastBehavior {
	
	/*
	 * @see org.primordion.user.app.Bestiary.XhBestiary#act()
	 */
	public void act() {
		grow();
		super.act();
	}
	
	public static double maxConsumptionRate = 1.0;
	
	private static double maxCatSize = 100.0; // the model should stop when the largest bug reaches this size
	
	/**
	 * Get the current bug size.
	 * @return The current bug size.
	 */
	public double getCatSize() {
		return ((Cat)getBeast()).getCatSize();
	}
	
	/**
	 * Set the size of this bug.
	 * @param bugSize The new size.
	 */
	public void setCatSize(double bugSize) {
		((Cat)getBeast()).setCatSize(bugSize);
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal()
	 */
	public double getVal() {
		return getCatSize();
	}
	
	/**
	 * Grow.
	 */
	protected void grow()
	{
		if (getCatSize() >= maxCatSize) {return;}
		double foodAvailability = parentNode.getParentNode().getVal();
		double foodConsumption = min(maxConsumptionRate, foodAvailability); // = bug growth
		setCatSize(getCatSize() + foodConsumption);
		parentNode.getParentNode().setVal(foodAvailability - foodConsumption);
	}
	
	/**
	 * Get the smaller of two numbers.
	 * @param one A number.
	 * @param two Another number.
	 * @return The smaller of the two numbers.
	 */
	protected double min(double one, double two)
	{
		if (one < two) {return one;}
		else {return two;}
	}
	
}

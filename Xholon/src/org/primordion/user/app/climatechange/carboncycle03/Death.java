package org.primordion.user.app.climatechange.carboncycle03;

import org.primordion.xholon.base.IXholon;

public class Death extends Xhcarboncycle03 {
	
	/**
	 * EAS model uses a special factor: "Death Terrestrial_Biosphere*0.09565"
	 */
	private static final double DEATH_FACTOR = 0.09565;
	
	private IXholon terrestrialVegetation = null;
	private IXholon soil = null;
	
	public void postConfigure() {
		terrestrialVegetation = ((Flows)getParentNode()).getTerrestrialVegetation();
		soil = ((Flows)getParentNode()).getSoil();
		this.setVal(terrestrialVegetation.getVal() * DEATH_FACTOR);
		super.postConfigure();
	}
	
	public void act() {
		double rate = this.getVal();
		terrestrialVegetation.decVal(rate);
		soil.incVal(rate);
		super.act();
	}
	
	public void postAct() {
		this.setVal(terrestrialVegetation.getVal() * DEATH_FACTOR);
		super.postAct();
	}

}

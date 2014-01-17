package org.primordion.user.app.climatechange.carboncycle03;

import org.primordion.xholon.base.IXholon;

public class Respiration extends Xhcarboncycle03 {
	
	private double rateFactor = 0.0;
	
	private IXholon terrestrialVegetation = null;
	private IXholon atmosphere = null;
	
	public void postConfigure() {
		terrestrialVegetation = ((Flows)getParentNode()).getTerrestrialVegetation();
		atmosphere = ((Flows)getParentNode()).getAtmosphere();
		rateFactor = this.getVal() / terrestrialVegetation.getVal();
		super.postConfigure();
	}
	
	public void act() {
		double rate = this.getVal();
		terrestrialVegetation.decVal(rate);
		atmosphere.incVal(rate);
		super.act();
	}
	
	public void postAct() {
		this.setVal(terrestrialVegetation.getVal() * rateFactor);
		super.postAct();
	}

}

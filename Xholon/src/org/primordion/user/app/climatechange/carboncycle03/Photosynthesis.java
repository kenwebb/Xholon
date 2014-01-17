package org.primordion.user.app.climatechange.carboncycle03;

import org.primordion.xholon.base.IXholon;

public class Photosynthesis extends Xhcarboncycle03 {
	
	private double rateFactor = 0.0;
	
	private IXholon atmosphere = null;
	private IXholon terrestrialVegetation = null;
	
	public void postConfigure() {
		atmosphere = ((Flows)getParentNode()).getAtmosphere();
		terrestrialVegetation = ((Flows)getParentNode()).getTerrestrialVegetation();
		rateFactor = this.getVal() / terrestrialVegetation.getVal(); // depends on terrestrialVegetation
		// TODO also depends to a lesser extent on CO2 level in the atmosphere; see IPCC
		super.postConfigure();
	}
	
	public void act() {
		double rate = this.getVal();
		atmosphere.decVal(rate);
		terrestrialVegetation.incVal(rate);
		super.act();
	}
	
	public void postAct() {
		this.setVal(terrestrialVegetation.getVal() * rateFactor);
		super.postAct();
	}
	
}

package org.primordion.user.app.climatechange.carboncycle03;

import org.primordion.xholon.base.IXholon;

public class VegToOcean extends Xhcarboncycle03 {
	
	private double rateFactor = 0.0;
	
	private IXholon terrestrialVegetation = null;
	private IXholon surfaceOcean = null;
	
	public void postConfigure() {
		terrestrialVegetation = ((Flows)getParentNode()).getTerrestrialVegetation();
		surfaceOcean = ((Flows)getParentNode()).getSurfaceOcean();
		rateFactor = this.getVal() / terrestrialVegetation.getVal();
		super.postConfigure();
	}
	
	public void act() {
		double rate = this.getVal();
		terrestrialVegetation.decVal(rate);
		surfaceOcean.incVal(rate);
		super.act();
	}
	
	public void postAct() {
		this.setVal(terrestrialVegetation.getVal() * rateFactor);
		super.postAct();
	}

}

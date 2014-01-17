package org.primordion.user.app.climatechange.carboncycle03;

import org.primordion.xholon.base.IXholon;

public class MarinePhotosynthesis extends Xhcarboncycle03 {
	
	private double rateFactor = 0.0;
	
	// reservoirs
	private IXholon surfaceOcean = null;
	private IXholon marineOrganisms = null;
	
	public void postConfigure() {
		surfaceOcean = ((Flows)getParentNode()).getSurfaceOcean();
		marineOrganisms = ((Flows)getParentNode()).getMarineOrganisms();
		rateFactor = this.getVal() / marineOrganisms.getVal();
		super.postConfigure();
	}
	
	public void act() {
		double rate = this.getVal();
		surfaceOcean.decVal(rate);
		marineOrganisms.incVal(rate);
		super.act();
	}
	
	public void postAct() {
		this.setVal(marineOrganisms.getVal() * rateFactor);
		super.postAct();
	}
	
}

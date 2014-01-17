package org.primordion.user.app.climatechange.carboncycle03;

import org.primordion.xholon.base.IXholon;

public class MarineRespiration extends Xhcarboncycle03 {
	
	private double rateFactor = 0.0;
	
	// reservoirs
	private IXholon marineOrganisms = null;
	private IXholon surfaceOcean = null;
	
	public void postConfigure() {
		marineOrganisms = ((Flows)getParentNode()).getMarineOrganisms();
		surfaceOcean = ((Flows)getParentNode()).getSurfaceOcean();
		rateFactor = this.getVal() / marineOrganisms.getVal();
		super.postConfigure();
	}
	
	public void act() {
		double rate = this.getVal();
		marineOrganisms.decVal(rate);
		surfaceOcean.incVal(rate);
		super.act();
	}
	
	public void postAct() {
		this.setVal(marineOrganisms.getVal() * rateFactor);
		super.postAct();
	}
	
}

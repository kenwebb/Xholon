package org.primordion.user.app.climatechange.carboncycle03;

import org.primordion.xholon.base.IXholon;

public class GasExchangeDown extends Xhcarboncycle03 {
	
	private double rateFactor = 0.0;
	
	// reservoirs
	private IXholon atmosphere = null;
	private IXholon surfaceOcean = null;
	
	public void postConfigure() {
		atmosphere = ((Flows)getParentNode()).getAtmosphere();
		surfaceOcean = ((Flows)getParentNode()).getSurfaceOcean();
		rateFactor = this.getVal() / atmosphere.getVal();
		//this.setVal(((atmosphere.getVal() - surfaceOcean.getVal()) / 75.0) + 92.0);
		super.postConfigure();
	}
	
	public void act() {
		double rate = this.getVal();
		atmosphere.decVal(rate);
		surfaceOcean.incVal(rate);
		super.act();
	}
	
	public void postAct() {
		//this.setVal(((atmosphere.getVal() - surfaceOcean.getVal()) / 75.0) + 92.0);
		this.setVal(atmosphere.getVal() * rateFactor);
		super.postAct();
	}

}

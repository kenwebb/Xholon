package org.primordion.user.app.climatechange.carboncycle03;

import org.primordion.xholon.base.IXholon;

public class GasExchangeUp extends Xhcarboncycle03 {
	
	private double rateFactor = 0.0;
	
	// reservoirs
	private IXholon atmosphere = null;
	private IXholon surfaceOcean = null;
	
	public void postConfigure() {
		atmosphere = ((Flows)getParentNode()).getAtmosphere();
		surfaceOcean = ((Flows)getParentNode()).getSurfaceOcean();
		rateFactor = this.getVal() / surfaceOcean.getVal();
		//this.setVal(((surfaceOcean.getVal() - atmosphere.getVal()) / 75.0) + 92.0);
		super.postConfigure();
	}
	
	public void act() {
		double rate = this.getVal();
		atmosphere.incVal(rate);
		surfaceOcean.decVal(rate);
		super.act();
	}
	
	public void postAct() {
		//this.setVal(((surfaceOcean.getVal() - atmosphere.getVal()) / 75.0) + 92.0);
		this.setVal(surfaceOcean.getVal() * rateFactor);
		super.postAct();
	}

}

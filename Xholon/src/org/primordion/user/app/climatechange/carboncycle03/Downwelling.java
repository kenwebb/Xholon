package org.primordion.user.app.climatechange.carboncycle03;

import org.primordion.xholon.base.IXholon;

public class Downwelling extends Xhcarboncycle03 {
	
	private double rateFactor = 0.0;
	
	private IXholon surfaceOcean = null;
	private IXholon deepOcean = null;
	
	public void postConfigure() {
		surfaceOcean = ((Flows)getParentNode()).getSurfaceOcean();
		deepOcean = ((Flows)getParentNode()).getDeepOcean();
		rateFactor = this.getVal() / surfaceOcean.getVal();
		super.postConfigure();
	}
	
	public void act() {
		double rate = this.getVal();
		surfaceOcean.decVal(rate);
		deepOcean.incVal(rate);
		super.act();
	}
	
	public void postAct() {
		this.setVal(surfaceOcean.getVal() * rateFactor);
		super.postAct();
	}

}

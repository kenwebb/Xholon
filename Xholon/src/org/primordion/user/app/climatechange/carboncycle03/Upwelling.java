package org.primordion.user.app.climatechange.carboncycle03;

import org.primordion.xholon.base.IXholon;

public class Upwelling extends Xhcarboncycle03 {
	
	private double rateFactor = 0.0;
	
	private IXholon surfaceOcean = null;
	private IXholon deepOcean = null;
	
	public void postConfigure() {
		surfaceOcean = ((Flows)getParentNode()).getSurfaceOcean();
		deepOcean = ((Flows)getParentNode()).getDeepOcean();
		rateFactor = this.getVal() / deepOcean.getVal();
		super.postConfigure();
	}
	
	public void act() {
		double rate = this.getVal();
		surfaceOcean.incVal(rate);
		deepOcean.decVal(rate);
		super.act();
	}
	
	public void postAct() {
		this.setVal(deepOcean.getVal() * rateFactor);
		super.postAct();
	}

}

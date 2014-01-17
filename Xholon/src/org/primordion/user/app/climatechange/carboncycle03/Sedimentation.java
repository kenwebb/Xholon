package org.primordion.user.app.climatechange.carboncycle03;

import org.primordion.xholon.base.IXholon;

public class Sedimentation extends Xhcarboncycle03 {
	
	private double rateFactor = 0.0;
	
	// reservoirs
	private IXholon deepOcean = null;
	private IXholon surfaceSediment = null;
	
	public void postConfigure() {
		deepOcean = ((Flows)getParentNode()).getDeepOcean();
		surfaceSediment = ((Flows)getParentNode()).getSurfaceSediment();
		rateFactor = this.getVal() / deepOcean.getVal();
		super.postConfigure();
	}
	
	public void act() {
		double rate = this.getVal();
		deepOcean.decVal(rate);
		surfaceSediment.incVal(rate);
		super.act();
	}
	
	public void postAct() {
		this.setVal(deepOcean.getVal() * rateFactor);
		super.postAct();
	}
	
}

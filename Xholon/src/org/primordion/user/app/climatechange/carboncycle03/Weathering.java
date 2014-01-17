package org.primordion.user.app.climatechange.carboncycle03;

import org.primordion.xholon.base.IXholon;

public class Weathering extends Xhcarboncycle03 {
	
	private double rateFactor = 0.0;
	
	/**
	 * Extra Weathering value in IPCC diagram.
	 * Perhaps this carbon originates in deep sediment (rocks on hills/mountains/land)?
	 */
	private double magicTerm = 0.2;
	
	// reservoirs
	private IXholon atmosphere = null;
	private IXholon surfaceOcean = null;
	
	public void postConfigure() {
		atmosphere = ((Flows)getParentNode()).getAtmosphere();
		surfaceOcean = ((Flows)getParentNode()).getSurfaceOcean();
		rateFactor = this.getVal() / atmosphere.getVal();
		super.postConfigure();
	}
	
	public void act() {
		double rate = this.getVal();
		atmosphere.decVal(rate);
		surfaceOcean.incVal(rate + magicTerm);
		super.act();
	}
	
	public void postAct() {
		this.setVal(atmosphere.getVal() * rateFactor);
		super.postAct();
	}

}

package org.primordion.user.app.climatechange.carboncycle03;

import org.primordion.xholon.base.IXholon;

public class Decay extends Xhcarboncycle03 {
	
	/**
	 * EAS model uses a special factor: "Decay Soil_Carbon*0.0392857"
	 */
	private static final double DECAY_FACTOR = 0.0392857;
	
	private IXholon soil = null;
	private IXholon atmosphere = null;
	
	public void postConfigure() {
		soil = ((Flows)getParentNode()).getSoil();
		atmosphere = ((Flows)getParentNode()).getAtmosphere();
		this.setVal(soil.getVal() * DECAY_FACTOR);
		super.postConfigure();
	}
	
	public void act() {
		double rate = this.getVal();
		soil.decVal(rate);
		atmosphere.incVal(rate);
		super.act();
	}
	
	public void postAct() {
		this.setVal(soil.getVal() * DECAY_FACTOR);
		super.postAct();
	}

}

package org.primordion.user.app.climatechange.igm;

import org.primordion.xholon.base.IXholon;

public class SolarInputSw extends Xhigm {
	
	private IXholon solarConstantSw = null;
	
	public void act() {
		setVal(0.25 * solarConstantSw.getVal());
		super.act();
	}

	public IXholon getSolarConstantSw() {
		return solarConstantSw;
	}

	public void setSolarConstantSw(IXholon solarConstantSw) {
		this.solarConstantSw = solarConstantSw;
	}
}

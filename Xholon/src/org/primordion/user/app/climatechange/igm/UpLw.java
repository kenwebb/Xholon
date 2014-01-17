package org.primordion.user.app.climatechange.igm;

import org.primordion.xholon.base.IXholon;

public class UpLw extends Xhigm {
	
	private IXholon emissivity = null;
	private IXholon atmosphericTemperature = null;
	
	public void act() {
		setVal(emissivity.getVal() * k2ef(atmosphericTemperature.getVal()));
		super.act();
	}

	public IXholon getEmissivity() {
		return emissivity;
	}

	public void setEmissivity(IXholon emissivity) {
		this.emissivity = emissivity;
	}

	public IXholon getAtmosphericTemperature() {
		return atmosphericTemperature;
	}

	public void setAtmosphericTemperature(IXholon atmosphericTemperature) {
		this.atmosphericTemperature = atmosphericTemperature;
	}
}

package org.primordion.user.app.climatechange.igm;

import org.primordion.xholon.base.IXholon;

public class ReflectedSw extends Xhigm {
	
	private IXholon solarInputSw = null;
	private IXholon albedo = null;
	
	public void act() {
		setVal(solarInputSw.getVal() * albedo.getVal());
		super.act();
	}

	public IXholon getSolarInputSw() {
		return solarInputSw;
	}

	public void setSolarInputSw(IXholon solarInputSw) {
		this.solarInputSw = solarInputSw;
	}

	public IXholon getAlbedo() {
		return albedo;
	}

	public void setAlbedo(IXholon albedo) {
		this.albedo = albedo;
	}
}

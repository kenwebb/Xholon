package org.primordion.user.app.climatechange.igm;

import org.primordion.xholon.base.IXholon;

public class FromSurfaceLw extends Xhigm {

	private IXholon surfaceTemperature = null;
	
	public void act() {
		setVal(k2ef(surfaceTemperature.getVal()));
		super.act();
	}

	public IXholon getSurfaceTemperature() {
		return surfaceTemperature;
	}

	public void setSurfaceTemperature(IXholon surfaceTemperature) {
		this.surfaceTemperature = surfaceTemperature;
	}
}

package org.primordion.user.app.climatechange.igm;

import org.primordion.xholon.base.IXholon;

public class ThruAtmosLw extends Xhigm {
	
	private IXholon emissivity = null;
	private IXholon fromSurfaceLw = null;
	
	public void act() {
		setVal((1 - emissivity.getVal()) * fromSurfaceLw.getVal());
		super.act();
	}

	public IXholon getEmissivity() {
		return emissivity;
	}

	public void setEmissivity(IXholon emissivity) {
		this.emissivity = emissivity;
	}

	public IXholon getFromSurfaceLw() {
		return fromSurfaceLw;
	}

	public void setFromSurfaceLw(IXholon fromSurfaceLw) {
		this.fromSurfaceLw = fromSurfaceLw;
	}
}

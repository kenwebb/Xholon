package org.primordion.user.app.climatechange.mdcs.m2_2mp;

import org.primordion.xholon.base.IXholon;

public class Celsius extends Xhmdcs {
	
	private IXholon k = null;
	
	public double getVal() {
		return k2c(k.getVal());
	}

	public IXholon getK() {
		return k;
	}

	public void setK(IXholon k) {
		this.k = k;
	}
	
}

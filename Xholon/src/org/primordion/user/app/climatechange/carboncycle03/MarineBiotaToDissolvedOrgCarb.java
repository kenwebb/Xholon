package org.primordion.user.app.climatechange.carboncycle03;

import org.primordion.xholon.base.IXholon;

public class MarineBiotaToDissolvedOrgCarb extends Xhcarboncycle03 {
	
	// reservoirs
	private IXholon marineOrganisms = null;
	private IXholon dissolvedOrganicCarbon = null;
	
	public void postConfigure() {
		marineOrganisms = ((Flows)getParentNode()).getMarineOrganisms();
		dissolvedOrganicCarbon = ((Flows)getParentNode()).getDissolvedOrganicCarbon();
		super.postConfigure();
	}
	
	public void act() {
		double rate = this.getVal();
		marineOrganisms.decVal(rate);
		dissolvedOrganicCarbon.incVal(rate);
		super.act();
	}
	
}

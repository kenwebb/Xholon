package org.primordion.user.app.climatechange.carboncycle03;

import org.primordion.xholon.base.IXholon;

public class DissolvedOrgCarbToOcean extends Xhcarboncycle03 {
	
	// reservoirs
	private IXholon dissolvedOrganicCarbon = null;
	private IXholon deepOcean = null;
	
	public void postConfigure() {
		dissolvedOrganicCarbon = ((Flows)getParentNode()).getDissolvedOrganicCarbon();
		deepOcean = ((Flows)getParentNode()).getDeepOcean();
		super.postConfigure();
	}
	
	public void act() {
		double rate = this.getVal();
		dissolvedOrganicCarbon.decVal(rate);
		deepOcean.incVal(rate);
		super.act();
	}
	
}

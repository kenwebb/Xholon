package org.primordion.user.app.climatechange.igm;

import org.primordion.xholon.base.IXholon;

public class Temperature extends Xhigm {
	
	private IXholon effectiveTemperature = null;
	private String parentXhcName = null;
	
	public void postConfigure() {
		parentXhcName = this.getParentNode().getXhcName();
		if (parentXhcName.equals("Atmosphere")) { // T_a
			effectiveTemperature = this.getParentNode().getFirstSibling();
			setVal(this.getVal() * effectiveTemperature.getVal());
		}
		else if (parentXhcName.equals("Surface")) { // T_s
			effectiveTemperature = this.getParentNode().getFirstSibling();
			setVal(this.getVal() * effectiveTemperature.getVal());
		}
		super.postConfigure();
	}
	
	public void act() {
		// TODO EffectiveTemperature should be a new class
		if (parentXhcName.equals("Atmosphere")) { // T_a
			
		}
		else if (parentXhcName.equals("Surface")) { // T_s
			
		}
		else if (parentXhcName.equals("Earth")) { // T_e
			//setVal();
		}
		super.act();
	}

	public IXholon getEffectiveTemperature() {
		return effectiveTemperature;
	}
}

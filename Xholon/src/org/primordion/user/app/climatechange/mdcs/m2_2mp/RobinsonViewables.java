package org.primordion.user.app.climatechange.mdcs.m2_2mp;

import org.primordion.xholon.base.IXholon;

public class RobinsonViewables extends Xhmdcs {
	
	private String xpathExpr = null; // xpathExpr
	private IXholon secondsPerTimeStep = null;
	
	public String getVal_String() {
		return xpathExpr;
	}

	public void setVal(String val) {
		this.xpathExpr = val;
	}

	public void postConfigure() {
		secondsPerTimeStep = this.getXPath().evaluate(xpathExpr, this);
		this.setVal(secondsPerTimeStep.getVal());
		//System.out.println(xpathExpr);
		//System.out.println(secondsPerTimeStep);
		super.postConfigure();
	}
	
	public void act() {
		System.out.println("\nTime:" + getApp().getTimeStep());
		super.act();
	}
	
}

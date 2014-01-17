package org.primordion.user.app.climatechange.mdcs.m2_2mp;

import org.primordion.xholon.base.IXholon;

public class RobinsonViewable extends Xhmdcs {
	
	private String xpathExpr = null;
	private IXholon node = null;
	private double secondsPerTimeStep = 0.0;
	
	public String getVal_String() {
		return xpathExpr;
	}

	public void setVal(String val) {
		this.xpathExpr = val;
	}

	public void postConfigure() {
		if (xpathExpr != null) {
			node = this.getXPath().evaluate(xpathExpr, this);
			secondsPerTimeStep = this.getParentNode().getVal();
			//System.out.println(xpathExpr);
			//System.out.println(node);
		}
		show();
		super.postConfigure();
	}
	
	public void act() {
		show();
		super.act();
	}
	protected void show() {
		if (node != null) {
			StringBuffer sb = new StringBuffer();
			sb.append(this.getRoleName()).append(":");
			// Atmosphere
			if ("Solar_to_atmosphere".equals(roleName)) {
				double v = ((Atmosphere)node).getAbsorbedByAtmosphere();
				sb.append(v).append(" (").append(v * secondsPerTimeStep).append(")");
			}
			else if ("IR".equals(roleName)) {
				double v = ((Atmosphere)node).getIr();
				sb.append(v).append(" (").append(v * secondsPerTimeStep).append(")");
			}
			else if ("IR_atmosphere".equals(roleName)) {
				double v = ((Atmosphere)node).getIrAtmosphere();
				sb.append(v).append(" (").append(v * secondsPerTimeStep).append(")");
			}
			else if ("IR_to_space".equals(roleName)) {
				double v = ((Atmosphere)node).getIrToSpace();
				sb.append(v).append(" (").append(v * secondsPerTimeStep).append(")");
			}
			else if ("Energy_in_atmosphere".equals(roleName)) {
				sb.append(((Atmosphere)node).getEnergy().getVal());
			}
			else if ("T_atmosphere".equals(roleName)) {
				sb.append(((Atmosphere)node).getTemperature().getVal());
			}
			// Surface
			else if ("Energy".equals(roleName)) {
				sb.append(((Surface)node).getEnergy().getVal());
			}
			else if ("Temperature".equals(roleName)) {
				sb.append(((Surface)node).getTemperature().getVal());
			}
			else {
				double v = node.getVal();
				sb.append(v).append(" (").append(v * secondsPerTimeStep).append(")");
			}
			System.out.println(sb);
		}
	}
}

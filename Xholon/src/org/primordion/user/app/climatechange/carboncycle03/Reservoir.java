package org.primordion.user.app.climatechange.carboncycle03;

public class Reservoir extends Xhcarboncycle03 {
	
	public String toString() {
		String outStr = super.toString();
		outStr += " " + getVal();
		return outStr;
	}
	
}

package org.primordion.user.app.climatechange.carboncycle03;

import org.primordion.xholon.base.IXholon;

public class FossilCarbon extends Reservoir {
	
	public double getVal() {
		if (this.hasChildNodes()) {
			// this is a FossilCarbon node
			IXholon node = getFirstChild();
			double val = 0.0;
			while (node != null) {
				val += node.getVal();
				node = node.getNextSibling();
			}
			return val;
		}
		else {
			// this is a child of a FossilCarbon node
			return super.getVal();
		}
	}
}

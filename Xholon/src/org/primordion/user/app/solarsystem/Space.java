package org.primordion.user.app.solarsystem;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonWithPorts;

public class Space extends XholonWithPorts {
	
	protected IXholon planets = null;
	
	/** Total luminosity of a star, in watts (W). */
	//protected double luminosity = Double.NEGATIVE_INFINITY;

	public void processReceivedMessage(IMessage msg) {
		if (this.planets == null) {
			this.planets = this.getPlanetsNative();
		}
		
		// forward message
		switch (msg.getSignal()) {
		case ISolarSystem.SIG_LUMINOSITY:
			if (this.planets == null) {return;}
			IXholon planet = this.planets.getFirstChild();
			while (planet != null) {
				//double radius = ((Planet)planet).getRadius();
				double r = ((Planet)planet).getSemimajorAxis();
				// rad: The flux density is simply defined as the amount of flux passing through a unit-area.
				// my calculator: 3.9E26/(4.0*PI*1.5E11^2) = 1379
				//
				// http://answers.yahoo.com/question/index?qid=20100326095734AA3NnEJ
				// I = P/4πr² = 3.9x10^26/4π(1.5x10^11)²
				// I = (3.9/4*2.25π) x10^(26-22) = 1.38x10³ W/m² = 1.38 kW/m²
				double luminosity = (Double)msg.getData();
				double rad = luminosity / (4.0 * Math.PI * Math.pow(r, 2.0));
				planet.sendMessage(ISolarSystem.SIG_LUMINOSITY, rad, this);
				planet = planet.getNextSibling();
			}
			break;
		default:
			break;
		}
	}
	
	//public void act() {
	//	
	//	super.act();
	//}
	
	public IXholon getPlanets() {
		return planets;
	}

	public void setPlanets(IXholon planets) {
		this.planets = planets;
	}
	
	protected native IXholon getPlanetsNative() /*-{
		return this["planets"];
	}-*/;

}

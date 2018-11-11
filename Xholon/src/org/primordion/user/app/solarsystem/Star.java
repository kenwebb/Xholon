package org.primordion.user.app.solarsystem;

import org.primordion.xholon.base.IXholon;

public class Star extends AstronomicalObject {
	
	/**
	 * STEFAN_BOLTZMANN_CONSTANT
	 */
	private final static double SIGMA = 5.67e-8;
	
	/** The star radiates energy to space. */
	protected IXholon space = null;
	
	/**
	 * The star's radiative flux in watts (W). 1 watt = 1 Joule/second.
	 * The luminosity of the Sun is 3.846×10^26 W or 3.9x10^26 W or 3.846e26
	 * @see <p><a href="http://en.wikipedia.org/wiki/Luminosity">wikipedia</a>
	 * Luminosity "the amount of electromagnetic energy a body radiates per unit of time."</p>
	 * "the luminosity of a star L (assuming the star is a black body, which is a good approximation)
	 * is also related to temperature T and radius R of the star by the equation:
	 * L = 4 * pi * R^2 * sigma * T^4
	 * where
	 *   σ is the Stefan-Boltzmann constant 5.67×10−8 W·m-2·K-4 "
	 */
	protected double luminosity = Double.NEGATIVE_INFINITY;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#act()
	 */
	public void act() {
		this.luminosity = 4.0 * Math.PI * Math.pow(this.getRadius(), 2.0) * SIGMA * Math.pow(this.getTemperature(), 4.0);
		if (this.space == null) {
			this.space = getSpaceNative();
		}
		if (this.space != null) {
			this.space.sendMessage(ISolarSystem.SIG_LUMINOSITY, this.luminosity, this);
		}
		super.act();
	}
	
	public IXholon getSpace() {
		return this.space;
	}

	public void setSpace(IXholon space) {
		this.space = space;
	}
	
	protected native IXholon getSpaceNative() /*-{
		return this["space"];
	}-*/;

	/**
	 * @return the total luminosity of the star in W
	 */
	public double getLuminosity() {
		return luminosity;
	}
	
}

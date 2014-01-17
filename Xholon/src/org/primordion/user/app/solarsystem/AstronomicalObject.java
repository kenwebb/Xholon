package org.primordion.user.app.solarsystem;

import org.primordion.xholon.base.XholonWithPorts;

/**
 * 
 * Every astronomical object (Star, Planet, StarSystem, etc.) has some basic properties.
 * Additional properties can be derived from these.
 * @author ken
 *
 */
public class AstronomicalObject extends XholonWithPorts {
	
	/** ex: "Earth" */
	protected String roleName = null;
	
	/** Uniform Resource Identifier */
	protected String uri = null;
	
	/**
	 * Mass of the object.
	 * Mass of the Earth 5.9736e24 kg
	 */
	protected double mass = Double.NEGATIVE_INFINITY;
	
	/**
	 * Mean radius of the object.
	 * Mean radius of Earth	6371000.0 m
	 */
	protected double radius = Double.NEGATIVE_INFINITY;
	
	/**
	 * Semi-major axis.
	 * Approximately the mean distance between the object
	 * and the primary object that it orbits.
	 * Mean distance between the planet and its primary star.
	 * mean distance from Earth to Sun (1 astronomical unit AU) = 149,597,870.7 km (1.496e11 m).
	 * For a StarSystem, this would be the distance to the center of its galaxy.
	 */
	protected double semimajorAxis = Double.NEGATIVE_INFINITY;
	
	/**
	 * Effective temperature of the object.
	 * Sun Photosphere (effective) = 5778 K
	 */
	protected double temperature = Double.NEGATIVE_INFINITY;

	/*
	 * @see org.primordion.xholon.base.Xholon#getRoleName()
	 */
	public String getRoleName() {
		return roleName;
	}

	/*
	 * @see org.primordion.xholon.base.Xholon#setRoleName(java.lang.String)
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/*
	 * @see org.primordion.xholon.base.Xholon#setUri(java.lang.String)
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getUri()
	 */
	public String getUri() {
		if (uri != null) {
			return uri;
		}
		return super.getUri();
	}
	
	/**
	 * @return Mass of the object in kg.
	 */
	public double getMass() {
		return mass;
	}

	/**
	 * @param mass The mass of the object in kg.
	 */
	public void setMass(double mass) {
		this.mass = mass;
	}

	/**
	 * @return Mean radius of the object in m.
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * @param radius Mean radius of the object in m.
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}

	/**
	 * @return The mean distance to the primary in m (ex: distance from Earth to Sun).
	 */
	public double getSemimajorAxis() {
		return semimajorAxis;
	}

	/**
	 * @param semimajorAxis The mean distance to the primary in m (ex: distance from Earth to Sun).
	 */
	public void setSemimajorAxis(double semimajorAxis) {
		this.semimajorAxis = semimajorAxis;
	}
	
	/**
	 * @return Effective temperature of the object in K.
	 */
	public double getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature Effective temperature of the object in K.
	 */
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	
	/**
	 * Get the volume of the object (m^3).
	 * This is a derived property.
	 * <p>volume = 4 * pi * radius^3 / 3</p>
	 * @return
	 */
	public double getVolume() {
		return 4.0 * Math.PI * Math.pow(radius, 3.0);
	}
	
	/**
	 * Get the average density of the object (kg / m^3).
	 * This is a derived property.
	 * <p>density = mass / volume</p>
	 * @return
	 */
	public double getDensity() {
		return mass / getVolume();
	}
	
}

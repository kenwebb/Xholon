package org.primordion.user.app.climatechange.carboncycle03;

import org.primordion.xholon.base.IXholon;

/**
 * Cache all the reservoirs to make it easier for Flow objects to find them.
 * @author ken
 *
 */
public class Flows extends Xhcarboncycle03 {
	
	// reservoirs that individual Flow objects may need reference to
	private IXholon atmosphere = null;
	private IXholon terrestrialVegetation = null;
	private IXholon soil = null;
	private IXholon surfaceOcean = null;
	private IXholon deepOcean = null;
	private IXholon surfaceSediment = null;
	private IXholon marineOrganisms = null;
	private IXholon dissolvedOrganicCarbon = null;
	private IXholon coal = null;
	private IXholon oil = null;
	private IXholon naturalGas = null;
	private IXholon carbonateRock = null;
	private IXholon kerogen = null;
	
	public IXholon getAtmosphere() {
		return atmosphere;
	}
	public void setAtmosphere(IXholon atmosphere) {
		this.atmosphere = atmosphere;
	}
	public IXholon getTerrestrialVegetation() {
		return terrestrialVegetation;
	}
	public void setTerrestrialVegetation(IXholon terrestrialVegetation) {
		this.terrestrialVegetation = terrestrialVegetation;
	}
	public IXholon getSoil() {
		return soil;
	}
	public void setSoil(IXholon soil) {
		this.soil = soil;
	}
	public IXholon getSurfaceOcean() {
		return surfaceOcean;
	}
	public void setSurfaceOcean(IXholon surfaceOcean) {
		this.surfaceOcean = surfaceOcean;
	}
	public IXholon getDeepOcean() {
		return deepOcean;
	}
	public void setDeepOcean(IXholon deepOcean) {
		this.deepOcean = deepOcean;
	}
	public IXholon getSurfaceSediment() {
		return surfaceSediment;
	}
	public void setSurfaceSediment(IXholon surfaceSediment) {
		this.surfaceSediment = surfaceSediment;
	}
	public IXholon getMarineOrganisms() {
		return marineOrganisms;
	}
	public void setMarineOrganisms(IXholon marineOrganisms) {
		this.marineOrganisms = marineOrganisms;
	}
	public IXholon getDissolvedOrganicCarbon() {
		return dissolvedOrganicCarbon;
	}
	public void setDissolvedOrganicCarbon(IXholon dissolvedOrganicCarbon) {
		this.dissolvedOrganicCarbon = dissolvedOrganicCarbon;
	}
	public IXholon getCoal() {
		return coal;
	}
	public void setCoal(IXholon coal) {
		this.coal = coal;
	}
	public IXholon getOil() {
		return oil;
	}
	public void setOil(IXholon oil) {
		this.oil = oil;
	}
	public IXholon getNaturalGas() {
		return naturalGas;
	}
	public void setNaturalGas(IXholon naturalGas) {
		this.naturalGas = naturalGas;
	}
	public IXholon getCarbonateRock() {
		return carbonateRock;
	}
	public void setCarbonateRock(IXholon carbonateRock) {
		this.carbonateRock = carbonateRock;
	}
	public IXholon getKerogen() {
		return kerogen;
	}
	public void setKerogen(IXholon kerogen) {
		this.kerogen = kerogen;
	}

}

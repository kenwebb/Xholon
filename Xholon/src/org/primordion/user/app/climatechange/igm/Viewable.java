/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2011 Ken Webb
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
 */

package org.primordion.user.app.climatechange.igm;

import org.primordion.xholon.base.IXholon;

public class Viewable extends Xhigm implements Ceigm {

	/**
	 * Each instance of Viewable monitors a specific variable in some Model
	 * object
	 */
	private IXholon topOfAtmosphere = null;
	private IXholon atmosphere = null;
	private IXholon surface = null;

	/*
	 * @see org.primordion.user.app.climatechange.model04.Xhmodel04#getVal()
	 */
	public double getVal() {
		switch (this.getXhcId()) {
		/*
		 * case SolarConstantVCE: return
		 * fix(((TopOfAtmosphere)topOfAtmosphere).getSolarConstant()); case
		 * IncomingSolarRadiationVCE: return
		 * fix(((TopOfAtmosphere)topOfAtmosphere).getIncomingSolarRadiation());
		 * case ReflectedByAtmosphereVCE: return
		 * fix(((Atmosphere)atmosphere).getReflectedSolarRadiation()); case
		 * ReflectedBySurfaceVCE: return
		 * fix(((Surface)surface).getReflectedSolarRadiation()); case
		 * ReflectedTotalVCE: return
		 * fix(((Atmosphere)atmosphere).getReflectedSolarRadiation()) +
		 * fix(((Surface)surface).getReflectedSolarRadiation()); case
		 * AbsorbedByAtmosphereVCE: return
		 * fix(((Atmosphere)atmosphere).getAbsorbedByAtmosphere()); case
		 * AbsorbedBySurfaceSwVCE: return
		 * fix(((Surface)surface).getAbsorbedBySurfaceSw()); case
		 * SensibleHeatVCE: // Thermals return
		 * fix(((Surface)surface).getSensibleHeatFlux()); case LatentHeatVCE: //
		 * Evapo-transpiration return
		 * fix(((Surface)surface).getLatentHeatFlux()); case
		 * SurfaceRadiationVCE: return
		 * fix(((Surface)surface).getSurfaceRadiation()); case
		 * SurfaceToAtmosphereVCE: // SurfaceRadiationV - AtmosphericWindowV
		 * return fix(((Surface)surface).getSurfaceRadiation()) -
		 * fix(((Atmosphere)atmosphere).getAtmosphericWindow()); case
		 * AtmosphericWindowVCE: return
		 * fix(((Atmosphere)atmosphere).getAtmosphericWindow()); case
		 * BackRadiationVCE: return
		 * fix(((Atmosphere)atmosphere).getBackRadiation()); case
		 * AbsorbedBySurfaceLwVCE: return
		 * fix(((Surface)surface).getAbsorbedBySurfaceLw()); case
		 * EmittedByAtmosphereVCE: return
		 * fix(((Atmosphere)atmosphere).getEmittedByAtmosphere()); case
		 * EmittedByCloudsVCE: return
		 * fix(((Atmosphere)atmosphere).getEmittedByClouds()); case
		 * NetAbsorbedByEarthVCE: return 0.0; case OutgoingLongwaveVCE: return
		 * fix(((Atmosphere)atmosphere).getOutgoingLongwave());
		 */
		default:
			return 0.0;
		}
	}

	/**
	 * Fix a value by transforming bad values, and by rounding it to the closest
	 * integer. For example, negative infinity is converted to 0.0 .
	 * 
	 * @param x
	 *            A double.
	 * @return A fixed double.
	 */
	protected double fix(double x) {
		double xFixed = x;
		if (x == Double.NEGATIVE_INFINITY) {
			xFixed = 0.0;
		}
		// return Math.rint(xFixed);
		return xFixed;
	}

	/*
	 * @see org.primordion.user.app.climatechange.model04.Xhmodel04#toString()
	 */
	public String toString() {
		String outStr = getName() + " ";
		switch (this.getXhcId()) {
		/*
		 * case SolarConstantVCE: outStr +=
		 * ((TopOfAtmosphere)topOfAtmosphere).getSolarConstant(); break; case
		 * IncomingSolarRadiationVCE: outStr +=
		 * ((TopOfAtmosphere)topOfAtmosphere).getIncomingSolarRadiation();
		 * break; case ReflectedByAtmosphereVCE: outStr +=
		 * ((Atmosphere)atmosphere).getReflectedSolarRadiation(); break; case
		 * ReflectedBySurfaceVCE: outStr +=
		 * ((Surface)surface).getReflectedSolarRadiation(); break; case
		 * ReflectedTotalVCE: outStr +=
		 * ((Atmosphere)atmosphere).getReflectedSolarRadiation() +
		 * ((Surface)surface).getReflectedSolarRadiation(); break; case
		 * AbsorbedByAtmosphereVCE: outStr +=
		 * ((Atmosphere)atmosphere).getAbsorbedByAtmosphere(); break; case
		 * AbsorbedBySurfaceSwVCE: outStr +=
		 * ((Surface)surface).getAbsorbedBySurfaceSw(); break; case
		 * SensibleHeatVCE: // Thermals outStr +=
		 * ((Surface)surface).getSensibleHeatFlux(); break; case LatentHeatVCE:
		 * // Evapo-transpiration outStr +=
		 * ((Surface)surface).getLatentHeatFlux(); break; case
		 * SurfaceRadiationVCE: outStr +=
		 * ((Surface)surface).getSurfaceRadiation(); break; case
		 * SurfaceToAtmosphereVCE: // SurfaceRadiationV - AtmosphericWindowV
		 * outStr += ((Surface)surface).getSurfaceRadiation() -
		 * ((Atmosphere)atmosphere).getAtmosphericWindow(); break; case
		 * AtmosphericWindowVCE: outStr +=
		 * ((Atmosphere)atmosphere).getAtmosphericWindow(); break; case
		 * BackRadiationVCE: outStr +=
		 * ((Atmosphere)atmosphere).getBackRadiation(); break; case
		 * AbsorbedBySurfaceLwVCE: outStr +=
		 * ((Surface)surface).getAbsorbedBySurfaceLw(); break; case
		 * EmittedByAtmosphereVCE: outStr +=
		 * ((Atmosphere)atmosphere).getEmittedByAtmosphere(); break; case
		 * EmittedByCloudsVCE: outStr +=
		 * ((Atmosphere)atmosphere).getEmittedByClouds(); break; case
		 * NetAbsorbedByEarthVCE: outStr += 0.0; break; case
		 * OutgoingLongwaveVCE: outStr +=
		 * ((Atmosphere)atmosphere).getOutgoingLongwave(); break;
		 */
		default:
			break;
		}
		return outStr;
	}

	public IXholon getTopOfAtmosphere() {
		return topOfAtmosphere;
	}

	public void setTopOfAtmosphere(IXholon topOfAtmosphere) {
		this.topOfAtmosphere = topOfAtmosphere;
	}

	public IXholon getAtmosphere() {
		return atmosphere;
	}

	public void setAtmosphere(IXholon atmosphere) {
		this.atmosphere = atmosphere;
	}

	public IXholon getSurface() {
		return surface;
	}

	public void setSurface(IXholon surface) {
		this.surface = surface;
	}

}

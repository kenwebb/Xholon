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

package org.primordion.dynsys.app.stability;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;
import org.primordion.xholon.exception.XholonConfigurationException;

/**
 * Stability model.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on December 15, 2011)
 */
public class Appstability extends Application {

	// how many time step intervals to chart
	private int chartInterval = 16; // 2

	public Appstability() {
		super();
		timeStep = 0;
		chartViewer = null;
		if (chartInterval > Xhstability.timeStepMultiplier) {
			chartInterval = Xhstability.timeStepMultiplier;
		}
	}
	
	public int getChartInterval() {
		return chartInterval;
	}

	public void setChartInterval(int chartInterval) {
		this.chartInterval = chartInterval;
	}

	public boolean setParam(String pName, String pValue)
	{
		if ("TimeStepMultiplier".equals(pName)) {
			Xhstability.setTimeStepMultiplier(Integer.parseInt(pValue));
			return true;
		}
		else if ("ChartInterval".equals(pName)) {
			setChartInterval(Integer.parseInt(pValue));
			return true;
		}
		return super.setParam(pName, pValue);
	}

	public void initialize(String configFileName) throws XholonConfigurationException
	{
		super.initialize(configFileName);
	}

	protected void step() {
		for (int i = 0; i < Xhstability.timeStepMultiplier; i++) {
			if (getUseDataPlotter() && ((i % chartInterval) == 0)) {
				chartViewer.capture((((double)i / Xhstability.timeStepMultiplier)) + timeStep);
			}
		  if (shouldStepView) {
			  view.act();
		  }
			root.act();
		}
		if (timeStepInterval > 0) {
			XholonTime.sleep(timeStepInterval);
		}
	}

	public void wrapup() {
		if (getUseDataPlotter()) {
			chartViewer.capture(getTimeStep());
		}
		super.wrapup();
	}

	protected boolean shouldBePlotted(org.primordion.xholon.base.IXholon modelNode) {
		if ((modelNode.getXhType() & org.primordion.xholon.base.IXholonClass.XhtypePurePassiveObject) == org.primordion.xholon.base.IXholonClass.XhtypePurePassiveObject) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#findGwtClientBundle()
	 */
	public Object findGwtClientBundle() {
	  return Resources.INSTANCE;
	}

	public static void main(String[] args) {
		appMain(args, "org.primordion.dynsys.app.stability.Appstability",
				"/org/primordion/dynsys/app/stability/_xhn.xml");
	}

}

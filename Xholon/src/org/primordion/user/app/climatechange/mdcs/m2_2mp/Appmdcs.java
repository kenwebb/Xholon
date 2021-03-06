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

package org.primordion.user.app.climatechange.mdcs.m2_2mp;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.IPortInterface;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInterface;
import org.primordion.xholon.base.XholonTime;
import org.primordion.xholon.exception.XholonConfigurationException;
import org.primordion.xholon.service.svg.ISvgView;

/**
 * MDCS m2_1 model, with message passing.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 4, 2011)
 */
public class Appmdcs extends Application {

	// how many time step intervals to chart
	private int chartInterval = 16; // 2

	public Appmdcs() {
		super();
		timeStep = 0;
		chartViewer = null;
		if (chartInterval > Xhmdcs.timeStepMultiplier) {
			chartInterval = Xhmdcs.timeStepMultiplier;
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
			Xhmdcs.setTimeStepMultiplier(Integer.parseInt(pValue));
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
		if (getUseInteractions()) {
			// include complete class name on sequence diagrams, rather than truncating long names
			interaction.setMaxNameLen(100);
			interaction.setMaxDataLen(8);
			IPortInterface providedRequiredInterface = new PortInterface();
			providedRequiredInterface.setInterface(Xhmdcs.getSignalIDs());
			providedRequiredInterface.setInterfaceNames(Xhmdcs.getSignalNames());
			interaction.setProvidedRequiredInterface(providedRequiredInterface);
		}
		//showSvgA();
		//showSvgB();
	}
	
	protected void showSvgA() {
		IXholon svgService = this.getService("SvgViewService");
		StringBuffer sb = new StringBuffer()
		.append("Energy Budget,")
		.append("http://upload.wikimedia.org/wikipedia/commons/d/d2/Sun_climate_system_alternative_(German)_2008.svg,")
		.append(",")
		.append("/org/primordion/user/app/climatechange/mdcs/m2_2mp/i18n/EnergyBudgetSvgLabels_en.xml");
		svgService.sendSyncMessage(ISvgView.SIG_SETUP_REQ, sb.toString(), this);
		svgService.sendSyncMessage(ISvgView.SIG_DISPLAY_REQ, null, this);
	}
	
	protected void showSvgB() {
		IXholon svgService = this.getService("SvgViewService");
		StringBuffer sb = new StringBuffer()
		.append("Idealized Greenhouse Model,")
		.append("http://upload.wikimedia.org/wikipedia/commons/9/9a/IdealizedGreenhouseEmissivity78.svg");
		//.append("/org/primordion/user/app/climatechange/igm/IdealizedGreenhouseEmissivity78.svg");
		svgService.sendSyncMessage(ISvgView.SIG_SETUP_REQ, sb.toString(), this);
		svgService.sendSyncMessage(ISvgView.SIG_DISPLAY_REQ, null, this);
	}
	
	protected void step() {
		root.preAct();
		for (int i = 0; i < Xhmdcs.timeStepMultiplier; i++) {
			if (getUseDataPlotter() && ((i % chartInterval) == 0)) {
				chartViewer.capture((((double)i / Xhmdcs.timeStepMultiplier)) + timeStep);
			}
			root.act();
		}
		root.postAct();
		if (shouldStepView) {
			view.act();
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

  public Object findGwtClientBundle() {
	  return Resources.INSTANCE;
	}
	
	public static void main(String[] args) {
		appMain(args, "org.primordion.user.app.climatechange.mdcs.m2_2mp.Appmdcs",
				"/org/primordion/user/app/climatechange/mdcs/m2_2mp/_xhn.xml");
	}

}

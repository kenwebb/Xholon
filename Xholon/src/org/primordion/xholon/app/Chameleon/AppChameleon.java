/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2007, 2008 Ken Webb
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

package org.primordion.xholon.app.Chameleon;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;
import org.primordion.xholon.exception.XholonConfigurationException;
import org.primordion.xholon.io.GridViewerDetails;

/**
	Chameleon application - Xholon Java
	<p>Xholon 0.8.1 http://www.primordion.com/Xholon</p>
*/
public class AppChameleon extends Application {
	
	/** how many time step intervals to chart */
	private int chartInterval = 1;
	
	public AppChameleon() {super();}
	
	public int getChartInterval() {
		return chartInterval;
	}

	public void setChartInterval(int chartInterval) {
		this.chartInterval = chartInterval;
	}

	@Override
	public String getParam(String pName)
	{
		if ("TimeStepMultiplier".equals(pName)) {
			return Integer.toString(XhChameleon.getTimeStepMultiplier());
		}
		else if ("ChartInterval".equals(pName)) {
			return Integer.toString(getChartInterval());
		}
		else if ("Dt".equals(pName)) {
			return Double.toString(XhChameleon.getDt());
		}
		return super.getParam(pName);
	}

	@Override
	public boolean setParam(String pName, String pValue)
	{
		if ("TimeStepMultiplier".equals(pName)) {
			XhChameleon.setTimeStepMultiplier(Integer.parseInt(pValue));
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
	
	protected void step()
	{
		root.preAct();
		for (int i = 0; i < XhChameleon.timeStepMultiplier; i++) {
			if (getUseDataPlotter() && (chartViewer != null) && ((i % chartInterval) == 0)) {
				chartViewer.capture((((double)i / XhChameleon.timeStepMultiplier)) + timeStep);
			}
			root.act();
		}
		if (shouldStepView) {
			view.act();
		}
		if (shouldStepAvatar) {
			avatar.act();
		}
		root.postAct();
		XholonTime.sleep( getTimeStepInterval() );
		if (getUseGridViewer()) {
			for (int vIx = 0; vIx < gridViewers.size(); vIx++) {
				GridViewerDetails gvd = (GridViewerDetails)gridViewers.get(vIx);
				if (gvd.useGridViewer) {
					//gvd.gridFrame.setInfoLabel("Time step: " + timeStep);
					gvd.gridPanel.paintComponent(null); //gvd.gridPanel.getGraphics()); // GWT
				}
			}
		}
		this.postStep();
	}
	
	public void wrapup()
	{
		root.preAct();
		if (getUseDataPlotter() && (chartViewer != null)) {
			chartViewer.capture(getTimeStep());
		}
		if (getUseGridViewer()) {
			for (int vIx = 0; vIx < gridViewers.size(); vIx++) {
				GridViewerDetails gvd = (GridViewerDetails)gridViewers.get(vIx);
				if (gvd.useGridViewer) {
					//gvd.gridFrame.setInfoLabel("Time step: " + timeStep);
					gvd.gridPanel.paintComponent(null); //gvd.gridPanel.getGraphics()); // GWT
				}
			}
		}
		super.wrapup();
	}
	
	protected boolean shouldBePlotted(org.primordion.xholon.base.IXholon modelNode)
	{
		if ((modelNode.getXhType() & org.primordion.xholon.base.IXholonClass.XhtypePurePassiveObject)
		    == org.primordion.xholon.base.IXholonClass.XhtypePurePassiveObject) {
			return true;
		}
		else {
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
	    appMain(args, "org.primordion.user.app.Chameleon.AppChameleon",
	        "/org/primordion/user/app/Chameleon/Chameleon_xhn.xml");
	}
	
}

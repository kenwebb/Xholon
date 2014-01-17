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

package org.primordion.dynsys.app;

import org.primordion.xholon.app.Application;

/**
 * Dynamical Systems - Train.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on January 20, 2007)
 */
public class AppTrain extends Application {

	// how many time step intervals to chart
	private int chartInterval = 2; // 2

	/**
	 * Constructor.
	 */
	public AppTrain()
	{
		super();
		timeStep = 0;
		chartViewer = null;
		if (chartInterval > XhTrain.timeStepMultiplier) {
			chartInterval = XhTrain.timeStepMultiplier;
		}
	}
	
	/**
	 * Process one time step. A concrete class should override this method,
	 * unless it only needs to do the simple root.preAct(), root.act(), root.postAct() cycle.
	 */
	protected void step()
	{
		for (int i = 0; i < XhTrain.timeStepMultiplier; i++) {
			if (getUseDataPlotter() && ((i % chartInterval) == 0)) {
				chartViewer.capture((((double)i / XhTrain.timeStepMultiplier)) + timeStep);
			}
			root.act();
		}
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#wrapup()
	 */
	public void wrapup()
	{
		if (getUseDataPlotter()) {
			chartViewer.capture(timeStep);
		}
		super.wrapup();
	}
	
	//          Setters/Getters for input parameters
	
	/** @param plotType The plotType to set. */
	public void setPlotType(int plotType)
		{XhTrain.setPlotType(plotType);}
	
	/** @return Returns the plotType. */
	public int getPlotType() {return XhTrain.getPlotType();}
	
	/*
	 * @see org.primordion.xholon.app.Application#setParam(java.lang.String, java.lang.String)
	 */
	public boolean setParam(String pName, String pValue)
	{
		if ("PlotType".equals(pName)) {setPlotType(Integer.parseInt(pValue)); return true;}
		return super.setParam(pName, pValue);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		appMain(args, "org.primordion.dynsys.app.AppTrain",
				"./config/dynsys/Train/Train_xhn.xml");
	}
}

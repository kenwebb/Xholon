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

package org.primordion.user.app.StupidModel.sm13;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.IControl;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonTime;
import org.primordion.xholon.io.GridViewerDetails;

/**
	StupidModel13 application - Xholon Java
	<p>Xholon 0.5 http://www.primordion.com/Xholon</p>
*/
public class AppStupidModel13 extends Application {

	public AppStupidModel13() {super();}
	
	public double getMaxConsumptionRate() {return BugStupidModel13.maxConsumptionRate;}
	public double getMaxFoodProductionRate() {return HabitatCellStupidModel13.maxFoodProductionRate;}
	public double getSurvivalProbability() {return BugStupidModel13.survivalProbability;}

	public void setMaxConsumptionRate(double maxConsumptionRate)
	{
		BugStupidModel13.maxConsumptionRate = maxConsumptionRate;
	}
	
	public void setMaxFoodProductionRate(double maxFoodProductionRate)
	{
		HabitatCellStupidModel13.maxFoodProductionRate = maxFoodProductionRate;
	}
	
	public void setSurvivalProbability(double survivalProbability)
	{
		BugStupidModel13.survivalProbability = survivalProbability;
	}
	
	/*
	 * @see org.primordion.xholon.app.Application#step()
	 */
	protected void step()
	{
		if (getUseDataPlotter()) {
			chartViewer.capture(timeStep);
		}
		root.act();
		XholonTime.sleep( getTimeStepInterval() );
		if (getUseGridViewer()) {
			for (int vIx = 0; vIx < gridViewers.size(); vIx++) {
				GridViewerDetails gvd = (GridViewerDetails)gridViewers.get(vIx);
				if (gvd.useGridViewer) {
					//gvd.gridFrame.setInfoLabel("Time step: " + timeStep);
					gvd.gridPanel.paintComponent(null); //gvd.gridPanel.getGraphics());
				}
			}
		}
		if (XhStupidModel13.isProcessingComplete()) {
			setControllerState(IControl.CS_STOPPED);
			System.out.println("Processing is complete");
			XhStupidModel13.setProcessingComplete(false); // reset in case this app is run again
			if (getUseGridViewer()) { // close the grid viewer window
				for (int vIx = 0; vIx < gridViewers.size(); vIx++) {
					GridViewerDetails gvd = (GridViewerDetails)gridViewers.get(vIx);
					if (gvd.useGridViewer) {
						//gvd.gridFrame.removeAll();
						//gvd.gridFrame.dispose();
						gvd.gridFrame.reset();
					}
				}
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.app.Application#wrapup()
	 */
	public void wrapup()
	{
		if (getUseDataPlotter()) {
			chartViewer.capture(timeStep);
		}
		if (getUseGridViewer()) { // close the grid viewer window
			for (int vIx = 0; vIx < gridViewers.size(); vIx++) {
				GridViewerDetails gvd = (GridViewerDetails)gridViewers.get(vIx);
				if (gvd.useGridViewer) {
					//gvd.gridFrame.removeAll();
					//gvd.gridFrame.dispose();
					gvd.gridFrame.reset();
				}
			}
		}
		super.wrapup();
	}
	
	/*
	 * @see org.primordion.xholon.app.Application#shouldBePlotted(org.primordion.xholon.base.IXholon)
	 */
	protected boolean shouldBePlotted(IXholon modelNode)
	{
		if (modelNode.getXhcId() == CeStupidModel13.AggregatorCE
				//&& (!modelNode.getRoleName().equals("count"))) {
				&& (modelNode.getRoleName().equals("count"))) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/** main
	 * @param args
	 */
	public static void main(String[] args) {
	    appMain(args, "org.primordion.user.app.StupidModel.AppStupidModel13",
	        "./config/StupidModel/StupidModel13/StupidModel13_xhn.xml");
	}
}

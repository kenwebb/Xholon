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

package org.primordion.user.app.StupidModel.sm16nl;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.IControl;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonTime;
import org.primordion.xholon.io.GridViewerDetails;

/**
	StupidModel16nl application - Xholon Java
	<p>Xholon 0.5 http://www.primordion.com/Xholon</p>
*/
public class AppStupidModel16nl extends Application {

	public AppStupidModel16nl() {super();}
	
	public double getMaxConsumptionRate() {return BugStupidModel16nl.maxConsumptionRate;}
	//public double getMaxFoodProductionRate() {return HabitatCellStupidModel16nl.maxFoodProductionRate;}
	public double getSurvivalProbability() {return BugStupidModel16nl.survivalProbability;}
	public double getInitialBugSizeMean() {return BugStupidModel16nl.initialBugSizeMean;}
	public double getInitialBugSizeSD() {return BugStupidModel16nl.initialBugSizeSD;}
	public String getStupidCellData() {return XhStupidModel16nl.stupidCellData;}

	public void setMaxConsumptionRate(double maxConsumptionRate)
	{
		BugStupidModel16nl.maxConsumptionRate = maxConsumptionRate;
	}
	
	public void setMaxFoodProductionRate(double maxFoodProductionRate)
	{
		
	}
	
	public void setSurvivalProbability(double survivalProbability)
	{
		BugStupidModel16nl.survivalProbability = survivalProbability;
	}
	
	public void setInitialBugSizeMean(double initialBugSizeMean)
	{
		BugStupidModel16nl.initialBugSizeMean = initialBugSizeMean;
	}
	
	public void setInitialBugSizeSD(double initialBugSizeSD)
	{
		BugStupidModel16nl.initialBugSizeSD = initialBugSizeSD;
	}
	
	public void setStupidCellData(String stupidCellData)
	{
		XhStupidModel16nl.stupidCellData = stupidCellData;
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
		if (XhStupidModel16nl.isProcessingComplete()) {
			setControllerState(IControl.CS_STOPPED);
			System.out.println("Processing is complete");
			XhStupidModel16nl.setProcessingComplete(false); // reset in case this app is run again
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
		if (modelNode.getXhcId() == CeStupidModel16nl.AggregatorCE
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
	    appMain(args, "org.primordion.user.app.StupidModel.AppStupidModel16nl",
	        "./config/StupidModel/StupidModel16nl/StupidModel16nl_xhn.xml");
	}
}

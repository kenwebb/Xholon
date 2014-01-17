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

package org.primordion.user.app.StupidModel.sm7;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.IControl;
import org.primordion.xholon.base.XholonTime;
import org.primordion.xholon.io.GridViewerDetails;

/**
	StupidModel7 application - Xholon Java
	<p>Xholon 0.5 http://www.primordion.com/Xholon</p>
*/
public class AppStupidModel7 extends Application {

	public AppStupidModel7() {super();}
	
	public double getMaxConsumptionRate() {return BugStupidModel7.maxConsumptionRate;}
	public double getMaxFoodProductionRate() {return HabitatCellStupidModel7.maxFoodProductionRate;}

	public void setMaxConsumptionRate(double maxConsumptionRate)
	{
		BugStupidModel7.maxConsumptionRate = maxConsumptionRate;
	}
	
	public void setMaxFoodProductionRate(double maxFoodProductionRate)
	{
		HabitatCellStupidModel7.maxFoodProductionRate = maxFoodProductionRate;
	}
	
	/*
	 * @see org.primordion.xholon.app.Application#step()
	 */
	protected void step()
	{
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
		if (XhStupidModel7.isProcessingComplete()) {
			setControllerState(IControl.CS_STOPPED);
			System.out.println("Processing is complete");
			XhStupidModel7.setProcessingComplete(false); // reset in case this app is run again
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
		if (getUseGridViewer()) { // close the grid viewer window
			for (int vIx = 0; vIx < gridViewers.size(); vIx++) {
				GridViewerDetails gvd = (GridViewerDetails)gridViewers.get(vIx);
				if (gvd.useGridViewer) {
					//gvd.gridFrame.removeAll();
					gvd.gridFrame.wrapup();
				}
			}
		}
		super.wrapup();
	}
	
	/** main
	 * @param args
	 */
	public static void main(String[] args) {
	    appMain(args, "org.primordion.user.app.StupidModel.AppStupidModel7",
	        "./config/StupidModel/StupidModel7/StupidModel7_xhn.xml");
	}
}

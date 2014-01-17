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

package org.primordion.user.app.SpatialGames;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;
import org.primordion.xholon.io.GridViewerDetails;


/**
 * This is the detailed behavior of a sample Xholon application.
 * Source of model concept:
 *   Nowak, Martin A. (2006).
 *   Evolutionary Dynamics: Exploring the equations of life. Cambridge, MA: Belknap/Harvard.
 *   chapter 9 - Spatial Games
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.7 (Created on September 16, 2007)
 * @see http://www.univie.ac.at/virtuallabs/ VirtualLabs in evolutionary game theory, by Christoph Hauert
 */
public class App09SpatialGames extends Application {
	
	/** Constructor. */
	public App09SpatialGames() {
		super();
	}
	
	/**
	 * 
	 * @return
	 */
	public static double getPayoffB() {
		return Xh09SpatialGames.getPAYOFF_c();
	}
	
	/**
	 * 
	 * @param b
	 */
	public static void setPayoffB(double b) {
		Xh09SpatialGames.setPAYOFF_c(b);
	}
	
	/**
	 * 
	 * @return
	 */
	public static int getLayout()
	{
		return Xh09SpatialGames.getLayout();
	}
	
	/**
	 * 
	 * @param layout
	 */
	public static void setLayout(int layout)
	{
		Xh09SpatialGames.setLayout(layout);
	}

	
	/*
	 * @see org.primordion.xholon.app.Application#step()
	 */
	protected void step()
	{
		root.preAct();
		root.act();
		root.postAct();
		XholonTime.sleep( getTimeStepInterval() );
		if (getUseGridViewer()) {
			for (int vIx = 0; vIx < gridViewers.size(); vIx++) {
				GridViewerDetails gvd = (GridViewerDetails)gridViewers.get(vIx);
				if (gvd.useGridViewer) {
					//gvd.gridFrame.setInfoLabel("Time step: " + (timeStep+1) + " b: " + Xh09SpatialGames.getPAYOFF_c());
					gvd.gridPanel.paintComponent(null); //gvd.gridPanel.getGraphics());
				}
			}
		}
	}

	/*
	 * @see org.primordion.xholon.app.IApplication#wrapup()
	 */
	public void wrapup()
	{
		root.preAct();
		super.wrapup();
		if (getUseGridViewer()) {
			for (int vIx = 0; vIx < gridViewers.size(); vIx++) {
				GridViewerDetails gvd = (GridViewerDetails)gridViewers.get(vIx);
				if (gvd.useGridViewer) {
					//gvd.gridFrame.setInfoLabel("Time step: " + maxProcessLoops);
					gvd.gridPanel.paintComponent(null); //gvd.gridPanel.getGraphics());
				}
			}
		}
	}
	
	/**
	 * main
	 */
	public static void main(String[] args) {
		appMain(args, "org.primordion.xholon.samples.App09SpatialGames",
				"./config/evoldyn/09SpatialGames/SpatialGames1_xhn.xml");
	}
}

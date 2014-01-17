/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2007 Ken Webb
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
 */

package org.primordion.dynsys.app;

import org.primordion.xholon.app.Application;

/**
 * Dynamical Systems - ScheffranTwoActor.
 * @author Ken Webb
 * @since 0.5 (Created on January 16, 2007)
 */
public class AppScheffranTwoActor extends Application {

	// how many time step intervals to chart
	private int chartInterval = 8;
	
	/**
	 * Constructor.
	 */
	public AppScheffranTwoActor()
	{
		super();
		timeStep = 0;
		chartViewer = null;
		if (chartInterval > XhScheffranTwoActor.timeStepMultiplier) {
			chartInterval = XhScheffranTwoActor.timeStepMultiplier;
		}
	}
	
	/**
	 * Process one time step. A concrete class should override this method,
	 * unless it only needs to do the simple root.preAct(), root.act(), root.postAct() cycle.
	 */
	protected void step()
	{
		for (int i = 0; i < XhScheffranTwoActor.timeStepMultiplier; i++) {
			if (getUseDataPlotter() && ((i % chartInterval) == 0)) {
				chartViewer.capture((((double)i / XhScheffranTwoActor.timeStepMultiplier)) + timeStep);
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
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		appMain(args, "org.primordion.dynsys.app.AppScheffranTwoActor",
				"./config/dynsys/ScheffranTwoActor/ScheffranTwoActor_xhn.xml");
	}

}

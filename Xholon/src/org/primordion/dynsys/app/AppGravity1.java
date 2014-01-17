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
 * Dynamical Systems - Gravity1.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on January 17, 2007)
 */
public class AppGravity1 extends Application {

	// how many time step intervals to chart
	private int chartInterval = 8196;

	/**
	 * Constructor.
	 */
	public AppGravity1()
	{
		super();
		timeStep = 0;
		chartViewer = null;
		if (chartInterval > XhGravity1.timeStepMultiplier) {
			chartInterval = XhGravity1.timeStepMultiplier;
		}
	}
	
	/**
	 * Process one time step. A concrete class should override this method,
	 * unless it only needs to do the simple root.preAct(), root.act(), root.postAct() cycle.
	 */
	protected void step()
	{
		for (int i = 0; i < XhGravity1.timeStepMultiplier; i++) {
			if (getUseDataPlotter() && ((i % chartInterval) == 0)) {
				//chartViewer.capture(XholonTime.timeStep);
				chartViewer.capture((((double)i / XhGravity1.timeStepMultiplier)) + timeStep);
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
		appMain(args, "org.primordion.dynsys.app.AppGravity1",
				"./config/dynsys/Gravity1/Gravity1_xhn.xml");
	}

}

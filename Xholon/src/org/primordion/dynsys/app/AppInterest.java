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
 * Dynamical Systems - Interest.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on January 16, 2007)
 */
public class AppInterest extends Application {

	/**
	 * Constructor.
	 */
	public AppInterest()
	{
		super();
		timeStep = 0;
		chartViewer = null;
	}
	
	/**
	 * Process one time step. A concrete class should override this method,
	 * unless it only needs to do the simple root.preAct(), root.act(), root.postAct() cycle.
	 */
	protected void step()
	{
		if (getUseDataPlotter()) {
			chartViewer.capture(timeStep + 1983);
		}
		// The same result occurs whether the loop is here or in XhInterest,
		// but it's 10 times faster with the loop in XhInterest.
		for (int i = 0; i < XhInterest.timeStepMultiplier; i++) {
			root.act();
		}
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#wrapup()
	 */
	public void wrapup()
	{
		if (getUseDataPlotter()) {
			chartViewer.capture(timeStep + 1983);
		}
		super.wrapup();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		appMain(args, "org.primordion.dynsys.app.AppInterest",
				"./config/dynsys/Interest/Interest_xhn.xml");
	}

}

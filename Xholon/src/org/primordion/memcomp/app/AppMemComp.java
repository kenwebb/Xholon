/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2006, 2007, 2008 Ken Webb
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

package org.primordion.memcomp.app;

import org.primordion.memcomp.base.XhAbstractMemComp;
import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.IControl;

/**
 * Membrane Computing.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on Jan 2, 2006)
 */
public class AppMemComp extends Application {

	/**
	 * Constructor.
	 */
	public AppMemComp()
	{
		super();
		timeStep = 0;
	}
	
	/**
	 * Process one time step. A concrete class should override this method,
	 * unless it only needs to do the simple root.preAct(), root.act(), root.postAct() cycle.
	 */
	protected void step()
	{
	  root.act();
		root.postAct();
		if (XhAbstractMemComp.isComputationComplete()) {
			// no need to continue processing
			setControllerState(IControl.CS_STOPPED);
			if (XhAbstractMemComp.isComputationSuccessful()) {
			  println("Computation was successful.");
		  }
		}
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#wrapup()
	 */
	public void wrapup()
	{
	  if (XhAbstractMemComp.isComputationSuccessful()) {
	    // wrapup() will probably never get called in this situation, so put the println in step()
			println("Computation was successful.");
		}
		else {
			println("Computation was unsuccessful.");
		}
		super.wrapup();
	}
}

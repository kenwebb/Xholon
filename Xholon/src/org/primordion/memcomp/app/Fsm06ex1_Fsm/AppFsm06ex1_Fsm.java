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

package org.primordion.memcomp.app.Fsm06ex1_Fsm;

import org.primordion.memcomp.app.AppMemComp;
import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;

/**
 * Membrane Computing.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on Jan 17, 2006)
 */
public class AppFsm06ex1_Fsm extends Application {

	/**
	 * Constructor.
	 */
	public AppFsm06ex1_Fsm()
	{
		super();
	}

	/*
	 * @see org.primordion.xholon.app.Application#step()
	 */
	protected void step()
	{
		if (getUseDataPlotter()) {
			chartViewer.capture(timeStep);
		}
		println("TIMESTEP: " + timeStep);
		root.act();
	}
	
	public void wrapup()
	{
		if (getUseDataPlotter()) {
			chartViewer.capture(timeStep);
		}
	  super.wrapup();
	}
	
	protected boolean shouldBePlotted(IXholon modelNode)
	{
		if ((modelNode.getXhcId() == CeStateMachineEntity.StateCE)
				|| (modelNode.getXhcId() == CeStateMachineEntity.FinalStateCE)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * This is the main entry point for the Xholon application.
	 * @param args Any arguments that your application may need.
	 * There is currently one optional command line argument.
	 */
	public static void main(String[] args) {
		appMain(args, "org.primordion.memcomp.app.AppFsm06ex1_Fsm",
				"./config/memcomp/Fsm06ex1/Fsm06ex1_Fsm_xhn.xml");
	}
}

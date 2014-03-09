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

package org.primordion.user.app.TestFsm;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;

/**
 * Test FSM. Test all finite state machine (FSM) features supported by Xholon.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.7 (Created on August 27, 2007)
 */
public class AppTestFsmKW extends Application {

	/** Constructor. */
	public AppTestFsmKW() {super();}
	
	//               Setters/Getters for input parameters
	
	/** @param testScenario The testScenario to set. */
	public void setTestScenario(int testScenario)
		{XhTestFsmKW.setTestScenario(testScenario);}
	
	/** @return Returns the testScenario. */
	public int getTestScenario() {return XhTestFsmKW.getTestScenario();}
	
	/*
	 * @see org.primordion.xholon.app.Application#setParam(java.lang.String, java.lang.String)
	 */
	public boolean setParam(String pName, String pValue)
	{
		if ("TestScenario".equals(pName)) {setTestScenario(Integer.parseInt(pValue)); return true;}
		return super.setParam(pName, pValue);
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
	
	/*
	 * @see org.primordion.xholon.app.Application#shouldBePlotted(org.primordion.xholon.base.IXholon)
	 */
	protected boolean shouldBePlotted(IXholon modelNode)
	{
		if ((modelNode.getXhcId() == CeStateMachineEntity.StateCE)
				|| (modelNode.getXhcId() == CeStateMachineEntity.FinalStateCE)
				|| (modelNode.getXhcId() == CeStateMachineEntity.StateMachineCE)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * This is the main entry point for the Xholon application.
	 * @param args Any arguments that your application may need.
	 */
	public static void main(String[] args) {
		appMain(args, "org.primordion.xholon.samples.AppTestFsmKW",
				"./config/TestFsm/TestFsmKW_xhn.xml");
	}
}

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

package org.primordion.user.app.rcs.Rcs1;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.IXholon;

/**
 * Regulated Catalyzing System - Glycogen Phosphorylase (GP) - Symbolic.
 * This version of the GP system uses symbols for state and event, rather than a UML MagicDraw FSM.
 * It is manually coded rather than generated from a UML MagicDraw model.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on Jan 26, 2006)
 */
public class AppRcs1 extends Application {

	/*
	 * @see org.primordion.xholon.app.Application#step()
	 */
	protected void step()
	{
		root.preAct();
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
		//if ((modelNode.getXhc().getXhType() & IXholonClass.XhtypePurePassiveObject) == IXholonClass.XhtypePurePassiveObject) {
		if (modelNode.isPassiveObject()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		appMain(args, "org.primordion.xholon.tutorials.rcs.AppRcs1",
				"./config/Rcs/Rcs1_xhn.xml");
	}
}

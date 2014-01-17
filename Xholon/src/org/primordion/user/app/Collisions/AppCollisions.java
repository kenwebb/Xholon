/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2005, 2006, 2007, 2008 Ken Webb
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

package org.primordion.user.app.Collisions;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.IXholon;

/**
 * Collisions. This is a sample Xholon application.
 * <p>This is a partial implementation of a statistical analysis described in a research paper.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on August 11, 2005)
 */
public class AppCollisions extends Application {

	/**
	 * Constructor.
	 */
	public AppCollisions() {super();}

	/*
	 * @see org.primordion.xholon.app.Application#step()
	 */
	protected void step()
	{
		root.preAct();
		if (getUseDataPlotter()) {
			chartViewer.capture(timeStep + 2001);
		}
		root.act();
	}

	/*
	 * @see org.primordion.xholon.app.IApplication#wrapup()
	 */
	public void wrapup()
	{
		root.preAct(); // print final values
		if (getUseDataPlotter()) {
			chartViewer.capture(timeStep + 2001);
		}
		super.wrapup();
	}
	
	/*
	 * @see org.primordion.xholon.app.Application#shouldBePlotted(org.primordion.xholon.base.IXholon)
	 */
	protected boolean shouldBePlotted(IXholon modelNode)
	{
		if ((modelNode.getXhc().getParentNode().getName().equals("PopGmGf"))
				&& (!(((XhCollisions)modelNode.getParentNode()).getXhc().getName().equals("Populations")))) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * main
	 * @param args One optional command line argument.
	 */
	public static void main(String[] args) {
		appMain(args, "org.primordion.xholon.samples.AppCollisions",
				"./config/Collisions/Collisions_xhn.xml");
	}
}

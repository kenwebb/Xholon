/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2008 Ken Webb
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

package org.primordion.user.app.OrNode;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;

/**
 * OrNode Sample. This is a sample Xholon application.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on April 27, 2008)
 */
public class AppOrNodeSample extends Application {

	/**
	 * Constructor.
	 */
	public AppOrNodeSample() {super();}
	
	/*
	 * @see org.primordion.xholon.app.Application#step()
	 */
	protected void step()
	{
		root.act();
		saveSnapshot();
		XholonTime.sleep( getTimeStepInterval() );
	}

	/*
	 * @see org.primordion.xholon.app.IApplication#wrapup()
	 */
	public void wrapup()
	{
		super.wrapup();
	}

	/**
	 * This is the main entry point for the Xholon application.
	 * @param args Any arguments that your application may need.
	 * There is currently one optional command line argument.
	 */
	public static void main(String[] args) {
		appMain(args, "org.primordion.xholon.samples.OrNode.AppOrNodeSample",
				"./config/OrNodeSample/OrNodeSample_xhn.xml");
	}
}

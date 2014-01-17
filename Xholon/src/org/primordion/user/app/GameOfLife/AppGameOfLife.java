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

package org.primordion.user.app.GameOfLife;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.IXholon;
//import org.primordion.xholon.base.XholonTime;
//import org.primordion.xholon.io.GridPanel;
import org.primordion.xholon.io.GridViewerDetails;

/**
 * Conway's classic Game of Life. This is a sample Xholon application.
 * <p>This is the classic Conway Game of Life implemented using Xholon.
 * It demonstrates how to implement a grid within a Xholon tree.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Jun 8, 2005)
 */
public class AppGameOfLife extends Application {
	
	/** Constructor. */
	public AppGameOfLife(){super();}
	
	/*
	 * @see org.primordion.xholon.app.Application#step()
	 */
	protected void step()
	{
		root.preAct();
		root.act();
		root.postAct();
		//XholonTime.sleep( getTimeStepInterval() );
		if (getUseGridViewer()) {
		  System.out.println("using grid viewer 1");
			for (int vIx = 0; vIx < gridViewers.size(); vIx++) {
				GridViewerDetails gvd = (GridViewerDetails)gridViewers.get(vIx);
				if (gvd.useGridViewer) {
				  System.out.println("using grid viewer 2: " + gvd + " " + gvd.gridPanel);
					//gvd.gridFrame.setInfoLabel("Time step: " + timeStep); // GWT
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
				  /* GWT
					gvd.gridFrame.setInfoLabel("Time step: " + maxProcessLoops);
					gvd.gridPanel.paintComponent(gvd.gridPanel.getGraphics());
					*/
				}
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.app.IApplication#findGwtClientBundle()
	 */
	public Object findGwtClientBundle() {
	  return Resources.INSTANCE;
	}
	
	/**
	 * main
	 * @param args One optional command line argument.
	 */
	public static void main(String[] args) {
		appMain(args, "org.primordion.xholon.samples.AppGameOfLife",
				"./config/GameOfLife/GameOfLife_Big_xhn.xml");
		//appMain(args, "org.primordion.xholon.samples.AppGameOfLife",
		//"./config/GameOfLife/GameOfLife_xhn.xml");
	}
}

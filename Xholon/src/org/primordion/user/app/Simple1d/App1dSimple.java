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

package org.primordion.user.app.Simple1d;

import org.primordion.xholon.app.Application;
//import org.primordion.xholon.base.XholonTime;
import org.primordion.xholon.io.GridViewerDetails;

/**
 * 1D Cellular Automaton.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on February 15, 2007)
 */
public class App1dSimple extends Application {
	
	/** Constructor. */
	public App1dSimple(){super();}
	
	//          Setters/Getters for input parameters
	
	/** @param caRuleNumber The caRuleNumber to set. */
	public void setCaRuleNumber(int caRuleNumber)
		{Xh1dSimple.setCaRuleNumber(caRuleNumber);}
	
	/** @return Returns the caRuleNumber. */
	public int getCaRuleNumber() {return Xh1dSimple.getCaRuleNumber();}
	
	/*
	 * @see org.primordion.xholon.app.Application#setParam(java.lang.String, java.lang.String)
	 */
	public boolean setParam(String pName, String pValue)
	{
		if ("CaRuleNumber".equals(pName)) {setCaRuleNumber(Integer.parseInt(pValue)); return true;}
		return super.setParam(pName, pValue);
	}
	
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
			for (int vIx = 0; vIx < gridViewers.size(); vIx++) {
				GridViewerDetails gvd = (GridViewerDetails)gridViewers.get(vIx);
				if (gvd.useGridViewer) {
					//gvd.gridFrame.setInfoLabel("CA Rule: " + Xh1dSimple.getCaRuleNumber()
					//		+ "     Time step: " + timeStep);
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
		if ((getUseGridViewer()) && (gridViewers != null)) {
			for (int vIx = 0; vIx < gridViewers.size(); vIx++) {
				GridViewerDetails gvd = (GridViewerDetails)gridViewers.get(vIx);
				if (gvd.useGridViewer) {
					//gvd.gridFrame.setInfoLabel("CA Rule: " + Xh1dSimple.getCaRuleNumber()
					//		+ "     Time step: " + timeStep);
					gvd.gridPanel.paintComponent(null); //gvd.gridPanel.getGraphics());
				}
			}
		}
	}
	
	/**
	 * main
	 * @param args One optional command line argument.
	 */
	public static void main(String[] args) {
		appMain(args, "org.primordion.xholon.samples.App1dSimple",
				"./config/ca/1dSimple/1dSimple_xhn.xml");
	}
}

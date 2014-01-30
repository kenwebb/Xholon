/* Ealontro - systems that evolve and adapt to their environment
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

package org.primordion.ealontro.app.EcjAntTrail;

import org.primordion.xholon.app.Application;
//import org.primordion.xholon.base.IXPath;
import org.primordion.xholon.base.IControl;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonTime;
//import org.primordion.xholon.io.GridPanel;
import org.primordion.xholon.exception.XholonConfigurationException;
import org.primordion.xholon.io.GridViewerDetails;

/**
 * Ant Trail.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on June 7, 2006)
 */
public class AppAntTrail extends Application implements CeAntTrail {

	// Santa Fe Trail - 89 and 400
	// Los Altos Trail - 156 and 2000
	//                                 Input parameters.
	/** maximum number of food pellets along the trail. */
	protected int maxFood = 89;
	/** ECJ uses this to determine when to stop, so Xholon is using it too; = left + right + move */
	protected int maxMoves = 400;

	//IXPath xp;
	
	public AppAntTrail()
	{
		super();
		chartViewer = null;
	}
	
	//                                        Setters for input parameters
	/** @param trailFile The trailFile to set. */
	public void setTrailFile(String trailFile) {XhAntTrail.trailFile = trailFile;}
	/** @param maxFood The maximum number of food pellets that can be accumulated. */
	public void setMaxFood(int maxFood) {this.maxFood = maxFood;}
	/** @param maxMoves The maximum number of moves that the Ant can take. */
	public void setMaxMoves(int maxMoves) {this.maxMoves = maxMoves;}

	//                                        Getters for input parameters
	/** @return Returns the trailFile. */
	public String getTrailFile() {return XhAntTrail.trailFile;}
	/** @return Returns the maximum number of food pellets that can be accumulated. */
	public int getMaxFood() {return maxFood;}
	/** @return Returns the maximum number of moves that the Ant can take. */
	public int getMaxMoves() {return maxMoves;}

	/*
	 * @see org.primordion.xholon.app.Application#setParam(java.lang.String, java.lang.String)
	 */
	public boolean setParam(String pName, String pValue)
	{
		if ("TrailFile".equals(pName)) {setTrailFile(pValue); return true;}
		if ("MaxFood".equals(pName)) {setMaxFood(Integer.parseInt(pValue)); return true;}
		if ("MaxMoves".equals(pName)) {setMaxMoves(Integer.parseInt(pValue)); return true;}
		return super.setParam(pName, pValue);
	}

	/*
	 * @see org.primordion.xholon.app.IApplication#initialize(java.lang.String)
	 */
	public void initialize( String configFileName ) throws XholonConfigurationException
	{
		modelName = configFileName;
		super.initialize(configFileName);
		
		//root.postConfigure();
	}

	/*
	 * @see org.primordion.xholon.app.Application#step()
	 */
	protected void step()
	{
		root.act();
		GridViewerDetails gvd = (GridViewerDetails)gridViewers.get(0);
		int currentFood = (int)((XhAntTrail)gvd.gridOwner.getPreviousSibling().getFirstChild()).getVal();
		int currentMoves = (int)((XhAntTrail)gvd.gridOwner.getPreviousSibling()).getVal();
		if (getUseDataPlotter()) {
			chartViewer.capture(timeStep);
		}
		XholonTime.sleep( getTimeStepInterval() );
		if (useGridViewer) {
			//gvd.gridFrame.setInfoLabel("Time step: " + timeStep
			//		+ "  Food found: " + currentFood
			//		+ "  Ant moves: " + currentMoves);
			gvd.gridPanel.paintComponent(null); //gvd.gridPanel.getGraphics());
		}
		if ((currentFood >= maxFood) || (currentMoves >= maxMoves)) {
			setControllerState(IControl.CS_STOPPED);
		}
	}
	
	/*
	 * @see org.primordion.xholon.app.Application#shouldBePlotted(org.primordion.xholon.base.IXholon)
	 */
	protected boolean shouldBePlotted(IXholon modelNode)
	{
		if (modelNode.getXhcId() == FoodCE) {
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
	public static void main(String[] args)
	{
		appMain(args, "org.primordion.ealontro.app.AppAntTrail",
				"./config/ealontro/EcjAntTrail/AntTrail_1_xhn.xml");
	}
}

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

package org.primordion.ealontro.app.AntForaging;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonTime;
import org.primordion.xholon.exception.XholonConfigurationException;
import org.primordion.xholon.io.GridViewerDetails;
//import org.primordion.xholon.io.GridPanel;
import org.primordion.xholon.util.Misc;
import org.primordion.ealontro.app.AntForaging.XhAntForaging;

/**
 * Ant System with Genetic Programming.
 * <p>The Ant System is a Xholon implementation of the example presented by John Koza
 * in his book Genetic Programming. It demonstrates how genetic programming can be used
 * to evolve tree-based behaviors, is an example of a grid implemented in Xholon, and
 * also demonstrates mobility as ants move about on the grid.</p>
 * <p>source: Koza, J. (1992). Genetic Programming. p.329-344</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Jun 8, 2005)
 */
public class AppAntForaging extends Application implements CeAntForaging {

	protected boolean saveSnapshots = true;
	
	public AppAntForaging()
	{
		super();
	}
	
	//                                        Setters for input parameters
	
	/** @param pheromoneEvaporationRate The pheromoneEvaporationRate to set. */
	public void setPheromoneEvaporationRate(double pheromoneEvaporationRate)
		{XhAntForaging.setPheromoneEvaporationRate(pheromoneEvaporationRate);}
	
	/** @param pheromoneDiffusionRate The pheromoneDiffusionRate to set. */
	public void setPheromoneDiffusionRate(double pheromoneDiffusionRate)
		{XhAntForaging.setPheromoneDiffusionRate(pheromoneDiffusionRate);}
	
	/** @param usePheromone The usePheromone to set. */
	public void setUsePheromone(boolean usePheromone) {XhAntForaging.setUsePheromone(usePheromone);}
	
	/** @param usePheromoneDiffusion The usePheromoneDiffusion to set. */
	public void setUsePheromoneDiffusion(boolean usePheromoneDiffusion)
		{XhAntForaging.setUsePheromoneDiffusion(usePheromoneDiffusion);}
		
	//                                        Getters for input parameters
	
	/** @return Returns the pheromoneEvaporationRate. */
	public double getPheromoneEvaporationRate() {return XhAntForaging.getPheromoneEvaporationRate();}
	
	/** @return Returns the pheromoneDiffusionRate. */
	public double getPheromoneDiffusionRate() {return XhAntForaging.getPheromoneDiffusionRate();}
	
	/** @return Returns the usePheromone. */
	public boolean getUsePheromone() {return XhAntForaging.getUsePheromone();}
	
	/** @return Returns the usePheromoneDiffusion. */
	public boolean getUsePheromoneDiffusion() {return XhAntForaging.getUsePheromoneDiffusion();}
	
	/*
	 * @see org.primordion.xholon.app.Application#setParam(java.lang.String, java.lang.String)
	 */
	public boolean setParam(String pName, String pValue)
	{
		if ("PheromoneEvaporationRate".equals(pName)) {setPheromoneEvaporationRate(Double.parseDouble(pValue)); return true;}
		if ("PheromoneDiffusionRate".equals(pName)) {setPheromoneDiffusionRate(Double.parseDouble(pValue)); return true;}
		if ("UsePheromone".equals(pName)) {setUsePheromone(Misc.booleanValue(pValue)); return true;}
		if ("UsePheromoneDiffusion".equals(pName)) {setUsePheromoneDiffusion(Misc.booleanValue(pValue)); return true;}
		return super.setParam(pName, pValue);
	}

	/*
	 * @see org.primordion.xholon.app.IApplication#initialize(java.lang.String)
	 */
	public void initialize( String configFileName ) throws XholonConfigurationException
	{
		modelName = configFileName;
		
		super.initialize(configFileName);
	}
	
	/*
	 * @see org.primordion.xholon.app.Application#step()
	 */
	protected void step()
	{
		root.act();
		if (getUseDataPlotter()) {
			chartViewer.capture(timeStep);
		}
		root.postAct();
		XholonTime.sleep( getTimeStepInterval() );
		if (useGridViewer) {
			for (int vIx = 0; vIx < gridViewers.size(); vIx++) {
				GridViewerDetails gvd = (GridViewerDetails)gridViewers.get(vIx);
				if (gvd.useGridViewer) {
					//gvd.gridFrame.setInfoLabel("Time step: " + timeStep
					//		+ " Food at nest: " + ((XhAntForaging)gvd.gridOwner.getNextSibling()).getRawFitness());
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
		root.preAct(); // print final grid positions
		if (getUseDataPlotter()) {
			chartViewer.capture(timeStep);
		}
		super.wrapup();
		
		if (getUseGridViewer()) {
			for (int vIx = 0; vIx < gridViewers.size(); vIx++) {
				GridViewerDetails gvd = (GridViewerDetails)gridViewers.get(vIx);
				if (gvd.useGridViewer) {
					//gvd.gridFrame.setInfoLabel("Time step: " + maxProcessLoops
					//		+ " Food at nest: " + ((XhAntForaging)gvd.gridOwner.getNextSibling()).getRawFitness());
					gvd.gridPanel.paintComponent(null); //gvd.gridPanel.getGraphics());
				}
			}
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
	public static void main(String[] args) {
		appMain(args, "org.primordion.ealontro.app.AppAntForaging",
				"./config/ealontro/AntForaging/AntForaging_xhn.xml");
	}
}

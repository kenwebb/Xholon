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

package org.primordion.user.app.WolfSheepGrass;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonTime;
import org.primordion.xholon.exception.XholonConfigurationException;
import org.primordion.xholon.io.GridViewerDetails;

/**
 * Wolf Sheep Grass.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on March 1, 2007)
 * @see The original NetLogo wolf sheep predation model is: Copyright 1998 Uri Wilensky. All rights reserved.
 * See http://ccl.northwestern.edu/netlogo/models/WolfSheepPredation for terms of use.
*/
public class AppWolfSheepGrass extends Application implements CeWolfSheepGrass {

// Variables

public AppWolfSheepGrass() {super();}

public void initialize(String configFileName) throws XholonConfigurationException
{
	super.initialize(configFileName);
}

// Setters and Getters
// Grass
public void setGrowGrass(boolean GrowGrass) {Grass.setGrowGrass(GrowGrass);}
public boolean getGrowGrass() {return Grass.getGrowGrass();}

public void setGrassRegrowthTime(int GrassRegrowthTime) {Grass.setGrassRegrowthTime(GrassRegrowthTime);}
public int getGrassRegrowthTime() {return Grass.getGrassRegrowthTime();}

// Sheep
public static void setInitialNumberSheep(int InitialNumberSheep) {WolfSheep.setInitialNumberSheep(InitialNumberSheep);}
public static int getInitialNumberSheep() {return WolfSheep.getInitialNumberSheep();}

public static void setSheepGainFromFood(double SheepGainFromFood) {WolfSheep.setSheepGainFromFood(SheepGainFromFood);}
public static double getSheepGainFromFood() {return WolfSheep.getSheepGainFromFood();}

public static void setSheepReproduce(double SheepReproduce) {WolfSheep.setSheepReproduce(SheepReproduce);}
public static double getSheepReproduce() {return WolfSheep.getSheepReproduce();}

// Wolves
public static void setInitialNumberWolves(int InitialNumberWolves) {WolfSheep.setInitialNumberWolves(InitialNumberWolves);}
public static int getInitialNumberWolves() {return WolfSheep.getInitialNumberWolves();}

public static void setWolfGainFromFood(double WolfGainFromFood) {WolfSheep.setWolfGainFromFood(WolfGainFromFood);}
public static double getWolfGainFromFood() {return WolfSheep.getWolfGainFromFood();}

public static void setWolfReproduce(double WolfReproduce) {WolfSheep.setWolfReproduce(WolfReproduce);}
public static double getWolfReproduce() {return WolfSheep.getWolfReproduce();}

// this will only be needed if you are using Java Micro Edition
public boolean setParam(String pName, String pValue)
{
	if ("GrowGrass".equals(pName)) {setGrowGrass(Boolean.valueOf(pValue).booleanValue()); return true;}
	if ("GrassRegrowthTime".equals(pName)) {setGrassRegrowthTime(Integer.parseInt(pValue)); return true;}
	if ("InitialNumberSheep".equals(pName)) {setInitialNumberSheep(Integer.parseInt(pValue)); return true;}
	if ("SheepGainFromFood".equals(pName)) {setSheepGainFromFood(Double.parseDouble(pValue)); return true;}
	if ("SheepReproduce".equals(pName)) {setSheepReproduce(Double.parseDouble(pValue)); return true;}
	if ("InitialNumberWolves".equals(pName)) {setInitialNumberWolves(Integer.parseInt(pValue)); return true;}
	if ("WolfGainFromFood".equals(pName)) {setWolfGainFromFood(Double.parseDouble(pValue)); return true;}
	if ("WolfReproduce".equals(pName)) {setWolfReproduce(Double.parseDouble(pValue)); return true;}
	return super.setParam(pName, pValue);
}

protected void step()
{
	root.preAct();
	if (getUseDataPlotter()) {
		chartViewer.capture(timeStep);
	}
	root.act();
	root.postAct();
	if (timeStepInterval > 0) {
		XholonTime.sleep( timeStepInterval );
	}
	//XholonTime.sleep( getTimeStepInterval() );
	if (getUseGridViewer()) {
		for (int vIx = 0; vIx < gridViewers.size(); vIx++) {
			GridViewerDetails gvd = (GridViewerDetails)gridViewers.get(vIx);
			if (gvd.useGridViewer) {
				//gvd.gridFrame.setInfoLabel("Time step: " + timeStep);
				gvd.gridPanel.paintComponent(null); //gvd.gridPanel.getGraphics());
			}
		}
	}
}

public void wrapup()
{
	root.preAct();
	if (getUseDataPlotter()) {
		chartViewer.capture(timeStep);
	}
	if (getUseGridViewer()) {
		for (int vIx = 0; vIx < gridViewers.size(); vIx++) {
			GridViewerDetails gvd = (GridViewerDetails)gridViewers.get(vIx);
			if (gvd.useGridViewer) {
				//gvd.gridFrame.setInfoLabel("Time step: " + timeStep);
				gvd.gridPanel.paintComponent(null); //gvd.gridPanel.getGraphics());
			}
		}
	}
	super.wrapup();
}

protected boolean shouldBePlotted(IXholon modelNode)
{
	if (modelNode.getXhcId() == AggregatorCE) {
		return true;
	}
	else {
		return false;
	}
}

public static void main(String[] args) {
    appMain(args, "org.primordion.user.app.WolfSheepGrass.AppWolfSheepGrass",
        "./config/user/WolfSheepGrass/WolfSheepGrass_xhn.xml");
}

}

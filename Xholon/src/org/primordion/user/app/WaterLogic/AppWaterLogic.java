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

package org.primordion.user.app.WaterLogic;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;
import org.primordion.xholon.exception.XholonConfigurationException;

/**
	WaterLogic application - Xholon Java
	<p>Xholon 0.7 http://www.primordion.com/Xholon</p>
*/
public class AppWaterLogic extends Application {

// Variables

public AppWaterLogic() {super();}

public void initialize(String configFileName) throws XholonConfigurationException
{
	super.initialize(configFileName);
}

// Setters and Getters
public void setTestScenario(int TestScenario) {XhWaterLogic.setTestScenario(TestScenario);}
public int getTestScenario() {return XhWaterLogic.getTestScenario();}

public boolean setParam(String pName, String pValue)
{
	if ("TestScenario".equals(pName)) {setTestScenario(Integer.parseInt(pValue)); return true;}
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
	XholonTime.sleep( getTimeStepInterval() );
}

public void wrapup()
{
	root.preAct();
	if (getUseDataPlotter()) {
		chartViewer.capture(timeStep);
	}
	this.invokeGraphicalNetworkViewer();
	super.wrapup();
}

public static void main(String[] args) {
    appMain(args, "org.primordion.user.app.AppWaterLogic",
        "./config/user/WaterLogic/WaterLogic_xhn.xml");
}

}

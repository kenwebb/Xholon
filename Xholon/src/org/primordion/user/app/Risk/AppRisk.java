/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2010 Ken Webb
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

package org.primordion.user.app.Risk;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.IPortInterface;
import org.primordion.xholon.base.PortInterface;
//import org.primordion.xholon.base.XholonTime;
import org.primordion.xholon.exception.XholonConfigurationException;

/**
	Risk application - Xholon Java
	<p>Xholon 0.8.1 http://www.primordion.com/Xholon</p>
*/
public class AppRisk extends Application {

// Variables

public AppRisk() {super();}

public void initialize(String configFileName) throws XholonConfigurationException
{
	super.initialize(configFileName);
	if (getUseInteractions()) {
		// include complete class name on sequence diagrams, rather than truncating long names
		interaction.setMaxNameLen(100);
		interaction.setMaxDataLen(30);
		IPortInterface providedRequiredInterface = new PortInterface();
		providedRequiredInterface.setInterface(XhRisk.getSignalIDs());
		providedRequiredInterface.setInterfaceNames(XhRisk.getSignalNames());
		interaction.setProvidedRequiredInterface(providedRequiredInterface);
	}
}

protected void step()
{
	root.act();
	//XholonTime.sleep( getTimeStepInterval() ); // not necessary with GWT
}

public void wrapup()
{
	super.wrapup();
}

public static void main(String[] args) {
    appMain(args, "org.primordion.user.app.Risk.AppRisk",
        "./config/user/Risk/Risk_xhn.xml");
}

}

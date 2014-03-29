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

package org.primordion.user.app.mathmltest1;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.XholonTime;
import org.primordion.xholon.exception.XholonConfigurationException;

/**
 * MathML example.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on April 21, 2009)
 */
public class AppMathmlTest1 extends Application {

// Variables

public AppMathmlTest1() {super();}

public void initialize(String configFileName) throws XholonConfigurationException
{
	super.initialize(configFileName);
}

protected void step()
{
	root.preAct();
	root.act();
	root.postAct();
	XholonTime.sleep( getTimeStepInterval() );
}

public void wrapup()
{
	root.preAct();
	super.wrapup();
}

public static void main(String[] args) {
    appMain(args, "org.primordion.user.app.mathmltest1.AppTest1",
        "./config/user/mathmltest1/MathML_Test1_xhn.xml");
}

}

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

package org.primordion.user.app.Generic;

import org.primordion.xholon.app.Application;

/**
 * A generic Xholon application, used for test purposes.
 * <p>To test core features of the Xholon runtime framework,
 * see the toString() function of XhGeneric.
 * That function can be made to return any type of information about individual Xholon nodes,
 * that will then be displayed by the default observer.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on July 18, 2005)
 */
public class AppGeneric extends Application {

	/** Constructor. */
	public AppGeneric() {}
	
	//          Setters/Getters for input parameters
	
	/** @param testNumber The testNumber to set. */
	public void setTestNumber(int testNumber)
		{XhGeneric.setTestNumber(testNumber);}
	
	/** @return Returns the testNumber. */
	public int getTestNumber() {return XhGeneric.getTestNumber();}
	
	/*
	 * @see org.primordion.xholon.app.Application#setParam(java.lang.String, java.lang.String)
	 */
	public boolean setParam(String pName, String pValue)
	{
		if ("TestNumber".equals(pName)) {setTestNumber(Integer.parseInt(pValue)); return true;}
		return super.setParam(pName, pValue);
	}
	
	/**
	 * main
	 * @param args One optional command line argument.
	 */
	public static void main(String[] args) {
		appMain(args, "org.primordion.xholon.samples.AppGeneric",
				"./config/Generic/Generic_xhn.xml");
	}
}

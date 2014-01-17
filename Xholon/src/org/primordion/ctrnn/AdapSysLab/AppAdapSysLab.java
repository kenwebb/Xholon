/* Xholon Runtime Framework - executes event-driven & dynamic applications
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

package org.primordion.ctrnn.AdapSysLab;

/**
 * Continuous-Time Recurrent Neural Network (CTRNN).
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on Feb 9, 2006)
 */
public class AppAdapSysLab extends AppCtrnn {

	//          Setters/Getters for input parameters
	
	/** @param numLightsOn The numLightsOn to set. */
	public void setNumLightsOn(final int numLightsOn)
		{XhAdapSysLab.setNumLightsOn(numLightsOn);}
	
	/** @return Returns the numLightsOn. */
	public int getNumLightsOn() {return XhAdapSysLab.getNumLightsOn();}

	/*
	 * @see org.primordion.xholon.app.Application#setParam(java.lang.String, java.lang.String)
	 */
	public boolean setParam(final String pName, final String pValue)
	{
		if ("NumLightsOn".equals(pName)) {setNumLightsOn(Integer.parseInt(pValue)); return true;}
		return super.setParam(pName, pValue);
	}
	
	public Object findGwtClientBundle() {
	  return Resources.INSTANCE;
	}
	
	/**
	 * main
	 * @param args One optional command line argument.
	 */
	public static void main(final String[] args) {
		appMain(args, "org.primordion.ctrnn.app.AppAdapSysLab",
				"./config/ctrnn/AdapSysLab/AdapSysLab_xhn.xml");
	}
}

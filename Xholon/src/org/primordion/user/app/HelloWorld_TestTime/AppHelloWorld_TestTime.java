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

package org.primordion.user.app.HelloWorld_TestTime;

import org.primordion.xholon.app.Application;
//import org.primordion.xholon.base.XholonTime;
//import org.primordion.xholon.base.IXholon;

/**
 * Test XholonTime using Hello World. This is a sample Xholon application.
 * <p>Two active objects of different classes collaborate to display "Hello World!"
 * on the console. They coordinate their behaviors by exchanging messages through ports.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on May 4, 2006)
 */
public class AppHelloWorld_TestTime extends Application {

	/**
	 * Constructor.
	 */
	public AppHelloWorld_TestTime()	{super();}

	/*
	 * @see org.primordion.xholon.app.Application#step()
	 */
	protected void step()
	{
		root.act();
		//XholonTime.sleep( getTimeStepInterval() ); // GWT
	}
	
	/*
	 * @see org.primordion.xholon.app.Application#wrapup()
	 */
	public void wrapup()
	{
		root.cleanup(); // cancel timers
		super.wrapup();
	}

	/*
	 * @see org.primordion.xholon.app.IApplication#makeAppSpecificNode(java.lang.String)
	 */
	/*public IXholon makeAppSpecificNode(String implName) {
		if ("org.primordion.xholon.tutorials.XhHelloWorld_TestTime".equals(implName)) {
			return new XhHelloWorld_TestTime();
		}
		return null;
	}*/
	
	/*
	 * @see org.primordion.xholon.app.IApplication#findAppSpecificClass(java.lang.String)
	 */
	/*public Class<IXholon> findAppSpecificClass(String className) {
	  if ("org.primordion.xholon.tutorials.XhHelloWorld_TestTime".equals(className)) {
	    //System.out.println(org.primordion.xholon.tutorials.XhHelloWorld_TestTime.class);
	    Class clazz = org.primordion.xholon.tutorials.XhHelloWorld_TestTime.class;
	    return (Class<IXholon>)clazz;
	  }
	  return null;
	}*/

	/*
	 * @see org.primordion.xholon.app.IApplication#findAppSpecificConstantValue(Class, java.lang.String)
	 */
	/*public int findAppSpecificConstantValue(Class aClass, String constName) {
	  System.out.println("AppCell.findAppSpecificConstantValue( " + constName);
	  if ("P_PARTNER".equals(constName)) {return XhHelloWorld_TestTime.P_PARTNER;}
	  else {return Integer.MIN_VALUE;}
	}*/
	
	/**
	 * This is the main entry point for the Xholon application.
	 * @param args Any arguments that your application may need.
	 */
	public static void main(String[] args) {
		appMain(args, "org.primordion.xholon.tutorials.AppHelloWorld_TestTime",
				"./config/HelloWorld/HelloWorld_TestTime_xhn.xml");
	}
}

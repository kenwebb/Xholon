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

package org.primordion.user.app.helloworldjnlp;

import java.util.ArrayList;
import java.util.List;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.IXholon;
//import org.primordion.xholon.base.XholonTime; // GWT

/**
 * Hello World. This is a sample Xholon application.
 * <p>Two active objects of different classes collaborate to display "Hello World!"
 * on the console. They coordinate their behaviors by exchanging messages through ports.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on October 10, 2005)
 */
public class AppHelloWorld extends Application {

	/**
	 * Constructor.
	 */
	public AppHelloWorld() {super();}
	
	/*
	 * @see org.primordion.xholon.app.Application#step()
	 */
	protected void step()
	{
	  root.act();
		//XholonTime.sleep( getTimeStepInterval() ); // GWT
	}

	/*
	 * @see org.primordion.xholon.app.IApplication#wrapup()
	 */
	public void wrapup()
	{
		super.wrapup();
	}
	
	public Object getAppSpecificAttribute(IXholon node, Class<IXholon> clazz, String attrName) {
	  System.out.println("AppHelloWorld getAppSpecificAttribute " + attrName);
	  //if (node == this) {return null;} // this is already handled by app.setParam()
	  if ("org.primordion.user.app.helloworldjnlp.XhHelloWorld".equals(clazz.getName())) {
	    if ("State".equals(attrName)) {
	      return ((XhHelloWorld)node).state; // .getState()
	    }
	  }
	  Class superclass = clazz.getSuperclass();
	  if (superclass.getName().startsWith("org.primordion.user.app.helloworldjnlp.")) {
	    // recursively call this same method using the clazz superclass which is in the same package
	    return getAppSpecificAttribute(node, superclass, attrName);
	  }
	  return null; //super.getAppSpecificAttribute(node, clazz, attrName);
	}
	
	// assume that attrVal is a String
	public void setAppSpecificAttribute(IXholon node, Class<IXholon> clazz, String attrName, Object attrVal) {
	  System.out.println("AppHelloWorld setAppSpecificAttribute " + attrName + " " + attrVal);
	  try {
	    //if (node == this) {return;} // this is already handled by app.setParam()
	    if ("org.primordion.user.app.helloworldjnlp.XhHelloWorld".equals(clazz.getName())) {
	      if ("State".equals(attrName)) {
	        ((XhHelloWorld)node).state = Integer.parseInt((String)attrVal);
	        //((XhHelloWorld)node).setState(Integer.parseInt((String)attrVal)); // this is what it should be
	      }
	    }
	  } catch(java.lang.NumberFormatException e) {}
	  Class superclass = clazz.getSuperclass();
	  if (superclass.getName().startsWith("org.primordion.user.app.helloworldjnlp.")) {
	    // recursively call this same method using the clazz superclass which is in the same package
	    setAppSpecificAttribute(node, superclass, attrName, attrVal);
	  }
	}
	
	public Object[][] getAppSpecificAttributes(IXholon node, Class<IXholon> clazz, boolean returnAll) {
	  List names = new ArrayList();
		List values = new ArrayList();
		List types = new ArrayList();
		
		if ("org.primordion.user.app.helloworldjnlp.XhHelloWorld".equals(clazz.getName())) {
		  names.add("State");values.add(((XhHelloWorld)node).state);types.add(Integer.class);
		}
		
	  if (returnAll) {
		  Class superclass = clazz.getSuperclass();
	    if (superclass.getName().startsWith("org.primordion.user.app.helloworldjnlp.")) {
	      // recursively call this same method using the clazz superclass which is in the same package
	      Object[][] scAttrs = getAppSpecificAttributes(node, superclass, returnAll);
	      if (scAttrs != null) {
		      for (int i = 0; i < scAttrs.length; i++) {
		        names.add(scAttrs[i][0]);values.add(scAttrs[i][1]);types.add(scAttrs[i][2]);
		      }
		    }
		  }
		}
		
		// Put all these attributes into an n by 3 array
		Object attributes[][] = new Object[names.size()][3];
		for (int attrIx = 0; attrIx < names.size(); attrIx++) {
			attributes[attrIx][0] = names.get(attrIx);
			attributes[attrIx][1] = values.get(attrIx);
			attributes[attrIx][2] = types.get(attrIx);
		}
		return attributes;
	}
	
	/**
	 * This is the main entry point for the Xholon application.
	 * @param args Any arguments that your application may need.
	 * There is currently one optional command line argument.
	 */
	public static void main(String[] args) {
		appMain(args, "org.primordion.user.app.helloworldjnlp.AppHelloWorld",
				"/org/primordion/user/app/helloworldjnlp/HelloWorld_xhn.xml");
	}
}

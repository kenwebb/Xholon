/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2015 Ken Webb
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

package org.primordion.xholon.base;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Python scripts using Brython.
 * Convert Python code to JavaScript,
 * and then use the code in Behavior_gwtjs to process the JavaScript.
 * This class should be used with XholonPython.html rather than Xholon.html .
 * It requires brython.js .
 * Example of JavaScript code that invokes brython:
__BRYTHON__.brython();
var src = "from browser import console\nx = 2\ny = 3\nconsole.log(x + y)";
var js = __BRYTHON__.py2js(src, "__main__", "__main__", "__builtins__").to_js();
console.log(js);
eval(js);
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://www.brython.info">Brython website</a>
 * @since 0.9.1 (Created on August 12, 2015)
 */
public class Behavior_gwtpython extends Behavior_gwtjs {
	
	/**
	 * The scripting language used.
	 */
	protected String scriptLanguage = "gwtpython";
	
	@Override
	public void postConfigure() {
	  // convert scriptContent from Python to JavaScript
	  this.setScriptContent(this.py2js(this.getScriptContent()));
	  consoleLog("This behavior is a " + this.getXhcName());
	  pythonClass(this.getXhcName());
		super.postConfigure();
  }
  
  protected native void pythonClass(String xhcName) /*-{
    $wnd.console.log($wnd.xh[xhcName]);
  }-*/;
  
  /**
   * Convert a Python script into a JavaScript script.
   */
  protected native String py2js(String scriptPy) /*-{
    if (!$wnd.__BRYTHON__) {return null;}
    __BRYTHON__ = $wnd.__BRYTHON__;
    var scriptJs = $wnd.__BRYTHON__.py2js(scriptPy, "__main__", "__main__", "__builtins__").to_js();
    return scriptJs;
  }-*/;
	
	protected native void postConfigure(JavaScriptObject bobj) /*-{
	  bobj.postConfigure();
	}-*/;
	
	protected native JavaScriptObject configureJso(IXholon cnode, String scriptContent) /*-{
	  $wnd.console.log("Behavior_gwtpython 1");
	  $wnd.console.log(scriptContent);
	  eval(scriptContent);
	  $wnd.console.log("Behavior_gwtpython 2");
	  $wnd.console.log(typeof beh);
	  $wnd.console.log($wnd);
	  
	  var behModule = $wnd.__BRYTHON__.modules.__main___beh_3;
	  $wnd.console.log(behModule);
	  
	  try {
	    if (typeof beh === 'undefined') {
	      $wnd.console.log("beh was undefined");
	      return null;
	    }
	    else {
	      $wnd.console.log(beh);
	      beh.cnode = cnode;
	      return beh;
	    }
	  } catch(e) {
	    $wnd.console.log("beh was non-existent");
	    return null;
	  }
	}-*/;
	
	protected native boolean hasFunction(JavaScriptObject bobj, String fname) /*-{
	  $wnd.console.log("bobj[fname] " + fname);
	  if (bobj && bobj[fname]) {
	    $wnd.console.log(true);
	    return true;
	  }
	  $wnd.console.log(false);
	  return false;
	}-*/;

}

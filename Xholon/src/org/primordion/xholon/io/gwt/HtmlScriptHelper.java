/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2013 Ken Webb
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

package org.primordion.xholon.io.gwt;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.ScriptInjector;
//import com.google.gwt.dom.client.Node;
//import com.google.gwt.user.client.Window;

/**
 * Provide help for creating HTML script tags.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on December 14, 2013)
 */
public class HtmlScriptHelper {
  
  /**
   * 
   * or use GWT.getModuleBaseForStaticFiles() http://www.primordion.com/Xholon/gwt/xholon/ + "lib/"
   */
  private static final String DEFAULT_REQUIRE_PATH =
    //"http://www.primordion.com/Xholon/gwt/lib/";
    GWT.getModuleBaseForStaticFiles() + "lib/";
  
  /** constructor */
  private HtmlScriptHelper() {}
  
  /**
   * Inject a script into the app, and execute it once.
   * @param scriptBody 
   * @param removeTag Whether or not to remove the HTML tag after the script is run.
   *        GWT default is true
   * @return 
   */
  public static JavaScriptObject fromString(String scriptBody, boolean removeTag) {
    JavaScriptObject jso = ScriptInjector.fromString(scriptBody)
    .setWindow(ScriptInjector.TOP_WINDOW)
    .setRemoveTag(removeTag)
    .inject();
    return jso;
  }
  
  /**
   * USE require.js INSTEAD.
   * This method should only be used for very simple cases,
   * where only one script is being loaded.
   * Make a required script available.
   * Load it into a script element in html head, if it's not already loaded.
   * Do nothing if it's already loaded.
   * TYpically, this is invoked from the user's postConfigure() code.
   * @param scriptName The name of the script file (ex: 'd3.v2.min.js').
   * @param scriptPath The path to the script (ex: 'http://www.primordion.com/Xholon/webEdition/lib/').
   *   If null or missing, then a default path is assumed.
   */
  public static void requireScript(String scriptName, String scriptPath) {
    if ((scriptName == null) || (scriptName.length() == 0)) {return;}
    if ((scriptPath == null) || (scriptPath.length() == 0)) {
      scriptPath = DEFAULT_REQUIRE_PATH;
    }
    else if (!(scriptPath.startsWith("http://")) && !(scriptPath.startsWith("https://"))) {
      // this may be a malicious URI, perhaps starting with "javascript:"
      return;
    }
    if (!isLoaded(scriptName)) {
      final JavaScriptObject jso = ScriptInjector.fromUrl(scriptPath + scriptName)
      .setWindow(ScriptInjector.TOP_WINDOW)
      .setRemoveTag(false)
      .setCallback( new Callback<Void,Exception>() {
        public void onSuccess(Void result) {
          consoleLog("Script load success.");
        }
        public void onFailure(Exception reason) {
          consoleLog("Script load failed.");
          // TODO if it's a 404 or other error where the script can't be loaded, delete the script tag
          // the following code won't compile "error: variable jso might not have been initialized"
          /*if (jso != null) {
            Node scriptNode = (Node)jso.cast();
            consoleLog(scriptNode);
            scriptNode.removeFromParent();
          }*/
        }
      })
      .inject();
      consoleLog(jso); // nodeName/tagName="SCRIPT" localName="script"
    }
  }
  
  private static native void consoleLog(Object obj) /*-{
    if ($wnd.console) {
      $wnd.console.log(obj);
    }
  }-*/;
  
  /**
   * Determine if a script with a specified name is already loaded.
   * @param scriptName The name of a script file (ex: 'd3.v2.min.js').
   */
  private static native final boolean isLoaded(String scriptName) /*-{
    var loaded = false;
    var scripts = $doc.scripts;
    for (var i = 0; i < scripts.length; i++) {
      var aScript = scripts[i];
      var src = aScript.getAttribute('src');
      if (src && src.indexOf(scriptName) != -1) {
        loaded = true;
        break;
      }
    }
    $wnd.console.log(scriptName + " isLoaded : " + loaded);
    return loaded;
  }-*/;
  
}

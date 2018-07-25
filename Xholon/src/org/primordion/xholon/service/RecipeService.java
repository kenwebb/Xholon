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

package org.primordion.xholon.service;

import com.google.gwt.core.client.JsArrayString;

//import java.util.Map;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.Message;
import org.primordion.xholon.base.XholonMap;
import org.primordion.xholon.service.recipe.IRecipe;
//import org.primordion.xholon.service.recipe.MinecraftStyleRecipeBook;
//import org.primordion.xholon.service.recipe.JsonRulesEngineRecipeBook;

/**
 * A service that adds and gets recipes.
 * The recipe objects are retained as children of this service.
 * Examples:
var recipeService = xh.service("RecipeService");
 *
 * Numerous implementations are possible:
 * - MinecraftStyleRecipeBook
 * - JsonRulesEngineRecipeBook
 * - CeptreJsonRulesEngineRecipeBook
 *
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on June 23, 2018)
 */
public class RecipeService extends AbstractXholonService implements IRecipe {
  
  /**
   * Name of the class that implements the service.
   * This is the default.
   */
  //private String defaultImplName = null; //"org.primordion.xholon.service.recipe.MinecraftStyleRecipeBook"; // ???
  
  /**
   * The firstChild of RecipeService is a XholonMap.
   */
  protected XholonMap map = null;
  
  /**
   * Return a reference to this service.
   * @see org.primordion.xholon.service.AbstractXholonService#getService(java.lang.String)
   */
  public IXholon getService(String serviceName) {
    if (serviceName.startsWith(getXhcName())) {
      map = (XholonMap)this.getFirstChild();
      if (map == null) {
        return null;
      }
      return this;
    }
    else {
      return null;
    }
  }
  
  @Override
  public void processReceivedMessage(IMessage msg) {
    processReceivedCommon(msg);
  }
  
  @Override
  public IMessage processReceivedSyncMessage(IMessage msg) {
    Object data = processReceivedCommon(msg);
    return new Message(SIG_RECIPE_RESP, data, this, msg.getSender());
  }
  
  /**
   * Process a received async or sync message.
   */
  protected Object processReceivedCommon(IMessage msg) {
    Object data = msg.getData();
    
    switch (msg.getSignal()) {
      case SIG_ADD_RECIPE_BOOK_REQ:
        {
        // data = full name of recipe book + JSON content as a String
        String rbname = null;
        String jsonStr = null;
		    if (data instanceof String[]) {
			    String[] params = (String[])data;
			    if (params.length != 2) {return null;}
			    rbname = params[0];
			    jsonStr = params[1];
		    }
		    else if (data instanceof JsArrayString) {
			    JsArrayString params = (JsArrayString)data;
			    if (params.length() != 2) {return null;}
			    rbname = params.get(0);
			    jsonStr = params.get(1);
		    }
		    else {
			    return null;
		    }
        IXholon node = map.appendChild("Attribute_Object", rbname);
        node.setVal_Object(this.jsonParse(jsonStr));
        String[] rbnameParts = rbname.split("-");
        if ((rbnameParts.length > 1) && ("JsonRulesEngineRecipeBook".equals(rbnameParts[1]) || "CeptreJsonRulesEngineRecipeBook".equals(rbnameParts[1])) ) {
          this.requireJsonRulesEngine();
        }
        }
        break;
      case SIG_GET_RECIPE_BOOK_REQ:
        {
        // data = full name of recipe book
        String key = (String)data;
        Object value = map.get(key);
        return value;
        }
      case SIG_GET_RECIPE_REQ:
        {
        // data = full name of recipe book + recipe name
        String rbname = null;
        String rname = null;
		    if (data instanceof String[]) {
			    String[] params = (String[])data;
			    if (params.length != 2) {return null;}
			    rbname = params[0];
			    rname = params[1];
		    }
		    else if (data instanceof JsArrayString) {
			    JsArrayString params = (JsArrayString)data;
			    if (params.length() != 2) {return null;}
			    rbname = params.get(0);
			    rname = params.get(1);
		    }
		    else {
			    return null;
		    }
		    if ((rbname == null) || (rname == null)) {return null;}
        String key = rbname;
        Object rbook = map.get(key);
        if (rbook == null) {return null;}
        return getItem(rbook, rname);
        }
    default:
      //super.processReceivedMessage(msg);
      break;
    }
    return null;
  }
  
  protected native Object jsonParse(String jsonStr) /*-{
    return $wnd.JSON.parse(jsonStr);
  }-*/;
  
  protected native Object getItem(Object obj, String item) /*-{
    return obj[item];
  }-*/;
  
  /**
   * Load the json-rules-engine JavaScript files.
   */
  protected native void requireJsonRulesEngine() /*-{
    $wnd.xh.require("json-rules-engine");
  }-*/;
  
}

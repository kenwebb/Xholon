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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window.Location;

import java.util.Date;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXPath;
import org.primordion.xholon.base.Message;
import org.primordion.xholon.base.transferobject.IXholonPatch;
import org.primordion.xholon.base.transferobject.XholonPatch;
import org.primordion.xholon.service.XholonHelperService;
import org.primordion.xholon.service.meteor.IMeteorPlatformService;

/**
 * Handle interaction with the Meteor Platform.
 * The method "boolean isExistsMeteor()" is in Xholon.java .
 * TODO support RFC 5261
 *
 * ##################################################################
 * Usage from inside Xholon:
 * ##################################################################
 
// removeAllItems(String collName)
var mps = $wnd.xh.service('MeteorPlatformService');
if (mps) {
  $wnd.console.log(mps.name());
  var node = $wnd.xh.root();
  var msg = mps.call(-3892, null, node);
  $wnd.console.log(msg);
  $wnd.console.log(msg.data);
}

// removeAllSessionItems(String collName, String sessionId)
var mps = $wnd.xh.service('MeteorPlatformService');
if (mps) {
  $wnd.console.log(mps.name());
  var node = $wnd.xh.root();
  var sessionId = null;
  var msg = mps.call(-3887, sessionId, node);
  $wnd.console.log(msg);
  $wnd.console.log(msg.data);
}

// removeItem(String collName, String meteorId)
var mps = $wnd.xh.service('MeteorPlatformService');
if (mps) {
  $wnd.console.log(mps.name());
  var node = $wnd.xh.root();
  var meteorId = "a1b2c3d4";
  var msg = mps.call(-3891, meteorId, node);
  $wnd.console.log(msg);
  $wnd.console.log(msg.data);
}

// collLength(String collName)
var mps = $wnd.xh.service('MeteorPlatformService');
if (mps) {
  $wnd.console.log(mps.name());
  var node = $wnd.xh.root();
  var msg = mps.call(-3890, null, node);
  $wnd.console.log(msg);
  $wnd.console.log("Collection length: " + msg.data);
}

// fetchAllItems(String collName)
var mps = $wnd.xh.service('MeteorPlatformService');
if (mps) {
  $wnd.console.log(mps.name());
  var node = $wnd.xh.root();
  var msg = mps.call(-3894, null, node);
  $wnd.console.log(msg);
  var items = msg.data;
  $wnd.console.log(items);
}

// fetchAllSessionItems(String collName, String sessionId)
var mps = $wnd.xh.service('MeteorPlatformService');
if (mps) {
  $wnd.console.log(mps.name());
  var node = $wnd.xh.root();
  var sessionId = null;
  var msg = mps.call(-3888, sessionId, node);
  $wnd.console.log(msg);
  var items = msg.data;
  $wnd.console.log(items);
}

// fetchItem(String collName, String meteorId)
var mps = $wnd.xh.service('MeteorPlatformService');
if (mps) {
  $wnd.console.log(mps.name());
  var node = $wnd.xh.root();
  var meteorId = "a1b2c3d4";
  var msg = mps.call(-3893, meteorId, node);
  $wnd.console.log(msg);
  var item = msg.data;
  $wnd.console.log(item);
}

 * ##################################################################
 * Usage from Developer Tools:
 * ##################################################################

// removeAllItems(String collName)
var mps = xh.service('MeteorPlatformService');
if (mps) {
  console.log(mps.name());
  var node = xh.root();
  var msg = mps.call(-3892, null, node);
  console.log(msg);
  console.log(msg.data);
}

// removeAllSessionItems(String collName, String sessionId)
var mps = xh.service('MeteorPlatformService');
if (mps) {
  console.log(mps.name());
  var node = xh.root();
  var sessionId = null;
  var msg = mps.call(-3887, sessionId, node);
  console.log(msg);
  console.log(msg.data);
}

// removeItem(String collName, String meteorId)
var mps = xh.service('MeteorPlatformService');
if (mps) {
  console.log(mps.name());
  var node = xh.root();
  var meteorId = "a1b2c3d4";
  var msg = mps.call(-3891, meteorId, node);
  console.log(msg);
  console.log(msg.data);
}

// collLength(String collName)
var mps = xh.service('MeteorPlatformService');
if (mps) {
  console.log(mps.name());
  var node = xh.root();
  var msg = mps.call(-3890, null, node);
  console.log(msg);
  console.log("Collection length: " + msg.data);
}

// fetchAllItems(String collName)
var mps = xh.service('MeteorPlatformService');
if (mps) {
  console.log(mps.name());
  var node = xh.root();
  var msg = mps.call(-3894, null, node);
  console.log(msg);
  var items = msg.data;
  console.log(items);
}

// fetchAllSessionItems(String collName, String sessionId)
var mps = xh.service('MeteorPlatformService');
if (mps) {
  console.log(mps.name());
  var node = xh.root();
  var sessionId = null;
  var msg = mps.call(-3888, sessionId, node);
  console.log(msg);
  var items = msg.data;
  console.log(items);
}

// fetchItem(String collName, String meteorId)
var mps = xh.service('MeteorPlatformService');
if (mps) {
  console.log(mps.name());
  var node = xh.root();
  var meteorId = "a1b2c3d4";
  var msg = mps.call(-3893, meteorId, node);
  console.log(msg);
  var item = msg.data;
  console.log(item);
}

 *
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="https://www.meteor.com/">Meteor website</a>
 * @since 0.9.1 (Created on April 21, 2015)
 */
public class MeteorPlatformService extends AbstractXholonService implements IMeteorPlatformService {
  
  /** This is a single global control. */
  protected boolean shouldWrite = true;
  /** This is a single global control. */
  protected boolean shouldRead = true;
  
  /** The name of a Meteor collection. */
  protected String collName = "Test02"; // "Test01"
  
  /**
   * Index of the next item to read from the Meteor collection.
   */
  protected int indexNextRead = 0;
  
  protected IApplication app = null;
  protected IXPath xpath = null;
  
  /**
   * Every local Meteor session has a unique ID.
   * This can be used to tell local collection items from remotely-genned collection items.
   */
  protected String sessionId = null;
  
  protected XholonHelperService xholonHelperService = null;
  
  /**
   * The value of the "app" parameter from the URL.
   */
  protected String appName = null;
  
  @Override
  public IXholon getService(String serviceName)
  {
    if (serviceName.equals(getXhcName())) {
      //consoleLog("MeteorPlatformService");
      if (app == null) {
        app = this.getApp();
      }
      if (xpath == null) {
        xpath = this.getXPath();
      }
      if (sessionId == null) {
        sessionId = makeSessionId();
      }
      if (xholonHelperService == null) {
        xholonHelperService = (XholonHelperService)app.getService(IXholonService.XHSRV_XHOLON_HELPER);
      }
      if (appName == null) {
        appName = Location.getParameter("app");
        if (appName != null) {
          // convert "+" back to " ", etc.
          appName = URL.decodeQueryString(appName);
        }
      }
      //createMongoCollection(this.getCollName()); // I probably don't want to do this explicitly on the client
      return this;
    }
    return null;
  }
  
  @Override
  public void processReceivedMessage(IMessage msg) {
    switch (msg.getSignal()) {
    default:
      super.processReceivedMessage(msg);
    }
  }
  
  @Override
  public IMessage processReceivedSyncMessage(IMessage msg) {
    consoleLog(msg.getVal_Object());
    switch (msg.getSignal()) {
    case SIG_EXISTS_METEOR_REQ:
      return new Message(SIG_METEOR_RESP, isExistsMeteor(), this, msg.getSender());
    case SIG_SHOULD_WRITE_REQ:
    {
      if (msg.getData() != null) {
        consoleLog(msg.getData());
        // msg.getData() must be a boolean
        boolean sw = (Boolean)msg.getData();
        setShouldWrite(sw);
      }
      consoleLog("shouldWrite now equals " + isShouldWrite());
      return new Message(SIG_METEOR_RESP, isShouldWrite(), this, msg.getSender());
    }
    case SIG_SHOULD_READ_REQ:
    {
      if (msg.getData() != null) {
        // msg.getData() must be a boolean
        boolean sr = (Boolean)msg.getData();
        setShouldRead(sr);
      }
      return new Message(SIG_METEOR_RESP, isShouldRead(), this, msg.getSender());
    }
    case SIG_COLL_INSERT_REQ:
    {
      Object robj = "Unable to insert item " + msg.getData();
      if (isShouldWrite() && isExistsMeteor()) {
        robj = null;
        insertItem(msg.getSender(), (String[])msg.getData());
      }
      return new Message(SIG_METEOR_RESP, robj, this, msg.getSender());
    }
    case SIG_COLL_FETCH_ALLITEMS_REQ:
    {
      Object robj = "Unable to fetch all items";
      if (isExistsMeteor()) {
        robj = fetchAllItems(collName);
      }
      return new Message(SIG_METEOR_RESP, robj, this, msg.getSender());
    }
    case SIG_COLL_FETCH_ALLSESSIONITEMS_REQ:
    {
      Object robj = "Unable to fetch all session items " + msg.getData();
      String sid = sessionId; // use the current sessionId by default
      if (msg.getData() != null) {
        sid = (String)msg.getData(); // use a sessionId passed in by the user
      }
      if (isExistsMeteor()) {
        robj = fetchAllSessionItems(collName, sid);
      }
      return new Message(SIG_METEOR_RESP, robj, this, msg.getSender());
    }
    case SIG_COLL_FETCH_ALLAPPITEMS_REQ:
    {
      Object robj = "Unable to fetch all app items " + msg.getData();
      String appn = appName; // use the current appName by default
      if (msg.getData() != null) {
        appn = (String)msg.getData(); // use a appName passed in by the user; should I allow this ?
      }
      if (isExistsMeteor()) {
        robj = fetchAllAppItems(collName, appn);
        consoleLog("MeteorPlatformService SIG_COLL_FETCH_ALLAPPITEMS_REQ ");
        consoleLog(robj);
      }
      return new Message(SIG_METEOR_RESP, robj, this, msg.getSender());
    }
    case SIG_COLL_FETCH_ITEM_REQ:
    {
      Object robj = "Unable to fetch item " + msg.getData();
      if (isExistsMeteor() && (msg.getData() != null)) {
        robj = fetchItem(collName, (String)msg.getData());
      }
      return new Message(SIG_METEOR_RESP, robj, this, msg.getSender());
    }
    case SIG_COLL_REMOVE_ALLITEMS_REQ:
    {
      Object robj = "Unable to remove all items";
      if (isExistsMeteor()) {
        robj = null;
        removeAllItems(collName);
      }
      return new Message(SIG_METEOR_RESP, robj, this, msg.getSender());
    }
    case SIG_COLL_REMOVE_ALLSESSIONITEMS_REQ:
    {
      Object robj = "Unable to remove all session items " + msg.getData();
      String sid = sessionId; // use the current sessionId by default
      if (msg.getData() != null) {
        sid = (String)msg.getData(); // use a sessionId passed in by the user
      }
      if (isExistsMeteor()) {
        robj = null;
        removeAllSessionItems(collName, sid);
      }
      return new Message(SIG_METEOR_RESP, robj, this, msg.getSender());
    }
    case SIG_COLL_REMOVE_ALLAPPITEMS_REQ:
    {
      Object robj = "Unable to remove all app items " + msg.getData();
      String appn = appName; // use the current appName by default
      if (msg.getData() != null) {
        appn = (String)msg.getData(); // use a appName passed in by the user
      }
      if (isExistsMeteor()) {
        robj = null;
        removeAllAppItems(collName, appn);
      }
      return new Message(SIG_METEOR_RESP, robj, this, msg.getSender());
    }
    case SIG_COLL_REMOVE_ITEM_REQ:
    {
      Object robj = "Unable to remove item " + msg.getData();
      if (isExistsMeteor() && (msg.getData() != null)) {
        robj = null;
        removeItem(collName, (String)msg.getData());
      }
      return new Message(SIG_METEOR_RESP, robj, this, msg.getSender());
    }
    case SIG_COLL_LENGTH_REQ:
    {
      int robj = 0;
      if (isExistsMeteor()) {
        robj = collLength(collName);
      }
      return new Message(SIG_METEOR_RESP, robj, this, msg.getSender());
    }
    case SIG_COLL_UPDATE_ITEM_REQ:
    {
      return new Message(SIG_METEOR_RESP, "NOT YET IMPLEMENTED", this, msg.getSender());
    }
    case SIG_COLL_SET_COLLNAME_REQ:
    {
      Object robj = "Unable to set collection name " + msg.getData();
      if (isExistsMeteor() && (msg.getData() != null)) {
        robj = null;
        this.setCollName((String)msg.getData());
        //createMongoCollection(this.getCollName());  // I probably don't want to do this explicitly on the client
      }
      return new Message(SIG_METEOR_RESP, robj, this, msg.getSender());
    }
    case SIG_COLL_GET_COLLNAME_REQ:
    {
      Object robj = null;
      if (isExistsMeteor()) {
        robj = this.getCollName();
      }
      return new Message(SIG_METEOR_RESP, robj, this, msg.getSender());
    }
    case SIG_COLL_SET_APP_REQ:
    {
      Object robj = "Unable to set app name " + msg.getData();
      if (isExistsMeteor() && (msg.getData() != null)) {
        robj = null;
        this.setAppName((String)msg.getData());
      }
      return new Message(SIG_METEOR_RESP, robj, this, msg.getSender());
    }
    case SIG_COLL_GET_APP_REQ:
    {
      Object robj = null;
      if (isExistsMeteor()) {
        robj = this.getAppName();
      }
      return new Message(SIG_METEOR_RESP, robj, this, msg.getSender());
    }
    default:
    {
      return super.processReceivedSyncMessage(msg);
    }
    
    } // end switch
    
  } // end processReceivedSyncMessage()
  
  /**
   * Insert something in a local Meteor collection (database).
   * Specify "add" "replace" "remove" as per RFC 5261
   * @param contextNode 
   * @param data XML that should be inserted at contextNode, patch op (ex: "add"), edit action (ex: "append").
   */
  protected void insertItem(IXholon contextNode, String[] data) {
    consoleLog("starting insertItem " + data.length);
    //consoleLog("contextNode " + contextNode);
    String xpathExpr = ".";
    if (contextNode != app.getRoot()) {
      xpathExpr = xpath.getExpression(app.getRoot(), contextNode); //, false);
    }
    consoleLog("xpathExpr " + xpathExpr);
    if ((xpathExpr == null) || xpathExpr.length() == 0) {xpathExpr = ".";}
    //consoleLog("xpathExpr " + xpathExpr);
    //String[] str = data.split(",");
    //consoleLog(str);
    String xmlStr = null;
    String patchOp = IXholonPatch.PATCHOP_ADD; //"add"; // default
    String editAction = IXholonPatch.POS_APPEND; //"append"; // default
    switch (data.length) {
    case 1:
      xmlStr = data[0];
      break;
    case 2:
      xmlStr = data[0];
      patchOp = data[1];
      break;
    case 3:
      xmlStr = data[0];
      patchOp = data[1];
      editAction = data[2]; // this might be an attribute name if it's a addAttribute
      break;
    default:
      return;
    }
    
    // TODO handle attributes and text
    XholonPatch xhp = new XholonPatch(appName);
    switch (patchOp) {
    case IXholonPatch.PATCHOP_ADD:
      switch (editAction) {
      case IXholonPatch.POS_APPEND:
        xhp.addElementAppend(xpathExpr, xmlStr);
        break;
      case IXholonPatch.POS_PREPEND:
        xhp.addElementPrepend(xpathExpr, xmlStr);
        break;
      case IXholonPatch.POS_BEFORE:
        xhp.addElementBefore(xpathExpr, xmlStr);
        break;
      case IXholonPatch.POS_AFTER:
        xhp.addElementAfter(xpathExpr, xmlStr);
        break;
      default:
        if (editAction.startsWith("@")) {
          // editAction contains the type, which is the name of an attribute (ex: "@val")
          xhp.addAttribute(xpathExpr, editAction, xmlStr);
          break;
        }
        else {
          return;
        }
      }
      break;
    case IXholonPatch.PATCHOP_REPLACE:
      xhp.replaceElement(xpathExpr, xmlStr);
      break;
    case IXholonPatch.PATCHOP_REMOVE:
      xhp.removeElement(xpathExpr);
      break;
    case IXholonPatch.PATCHOP_MOVE:
      // source target
      consoleLog("meteor move " + editAction + " " + xpathExpr + " " + xmlStr);
      switch (editAction) {
      case IXholonPatch.POS_APPEND:
        xhp.moveElementAppend(xpathExpr, xmlStr);
        break;
      case IXholonPatch.POS_PREPEND:
        xhp.moveElementPrepend(xpathExpr, xmlStr);
        break;
      case IXholonPatch.POS_BEFORE:
        xhp.moveElementBefore(xpathExpr, xmlStr);
        break;
      case IXholonPatch.POS_AFTER:
        xhp.moveElementAfter(xpathExpr, xmlStr);
        break;
      default:
        break;
      }
      break;
    default:
      break;
    }
    insertItem(collName, xhp.getVal_Object(), sessionId);
  }
  
  /**
   * Insert an item into a Meteor collection.
   * Object {_id: "Z32o6Siws4h3u3Nik", op: "add", sel: "descendant::Hello", pos: "append", content: "<World roleName="Universe"/>"}
   */
  protected native void insertItem(String collName, Object obj, String sessionId) /*-{
    obj.sessionId = sessionId;
    $wnd.console.log(obj);
	  if (typeof $wnd[collName] === "undefined") {
	    $wnd.console.log("MeteorPlatformService.insertItem() $wnd[" + collName + "] is undefined");
	  }
	  else {
	    $wnd.console.log("MeteorPlatformService.insertItem() $wnd[" + collName + "] before");
	    $wnd.console.log(obj);
	    $wnd.console.log($wnd[collName].insert);
      var insertRC = $wnd[collName].insert(obj);
      $wnd.console.log(insertRC);
      $wnd.console.log("MeteorPlatformService.insertItem() $wnd[" + collName + "] after");
    }
  }-*/;
  
  /**
	 * Process new items from a Meteor collection.
	 * The Meteor collection acts like a queue.
	 * Don't process items that were already inserted by this app locally.
	 * @param collName Name of the Meteor collection, or null (will use default name).
	 */
	@Override
	public void processMeteorQ(String collName) {
	  if (collName == null) {collName = this.collName;}
	  if (isShouldRead() && isExistsMeteor()) {
	    JsArray items = null;
	    if ("Test01".equals(collName)) {
	      // "Test01" records do not include the appName
	      // TODO this is just a temporary fix for backwards compatibility
	      items = fetchAllItems(collName);
	    }
	    else {
	      items = fetchAllAppItems(collName, appName);
	      //consoleLog("MeteorPlatformService.processMeteorQ() 1 ");
	      //consoleLog(items);
	    }
	    for (int i = indexNextRead; i < items.length(); i++) {
	      JavaScriptObject item = items.get(i);
	      consoleLog(item);
	      XholonPatch xhp = new XholonPatch(item);
	      if (this.sessionId.equals(xhp.getSessionId())) {
	        consoleLog("this is a locally inserted item");
	      }
	      else {
	        consoleLog("this is a remotely inserted item");
	        
	        String op = xhp.getOp();
	        String xmlStr = xhp.getContent();
	        String sel = xhp.getSel();
	        String pos = xhp.getPos();
	        String type = xhp.getType();
	        String source = xhp.getSource();
	        String target = xhp.getTarget();
	        IXholon cnode = null; //xpath.evaluate(sel, app.getRoot());
	        
	        switch (op) {
          case IXholonPatch.PATCHOP_ADD:
            cnode = xpath.evaluate(sel, app.getRoot());
	          if ((xmlStr != null) && (cnode != null)) {
	            consoleLog(cnode.getName());
	            switch (pos) {
              case "prepend":
                xholonHelperService.pasteFirstChild(cnode, xmlStr);
                break;
              case "before":
                xholonHelperService.pasteBefore(cnode, xmlStr);
                break;
              case "after":
                xholonHelperService.pasteAfter(cnode, xmlStr);
                break;
              case "append":
                xholonHelperService.pasteLastChild(cnode, xmlStr);
                break;
              default:
                if ((type != null) && (type.startsWith("@"))) {
                  if ("@role".equals(type)) {
                    cnode.setRoleName(xmlStr);
                  }
                  else if ("@val".equals(type)) {
                    cnode.setVal(Double.parseDouble(xmlStr));
                  }
                  else if ("@str".equals(type)) {
                    cnode.setVal_String(xmlStr);
                  }
                  else {
                    setAttributeValNative(cnode, type.substring(1), xmlStr);
                  }
                }
                else {
                  xholonHelperService.pasteLastChild(cnode, xmlStr);
                }
                break;
              } // end switch(pos)
	          } // end if
	          break;
	        case IXholonPatch.PATCHOP_REPLACE:
	          // TODO
	          //cnode = xpath.evaluate(sel, app.getRoot());
	          break;
	        case IXholonPatch.PATCHOP_REMOVE:
	          cnode = xpath.evaluate(sel, app.getRoot());
	          if (cnode != null) {
	            cnode.removeChild();
	          }
	          break;
	        case IXholonPatch.PATCHOP_MOVE:
	          consoleLog("TODO move");
	          cnode = xpath.evaluate(source, app.getRoot());
	          if (cnode != null) {
	            consoleLog("TODO move " + cnode.getName());
	            IXholon tnode = xpath.evaluate(target, app.getRoot());
  	          // TODO
  	          if (tnode != null) {
  	            consoleLog("TODO move " + tnode.getName());
	              cnode.removeChild();
	              cnode.appendChild(tnode);
	            }
	          }
	          break;
	        default:
	          break;
	        } // end switch(patchOp)
	      } // end else
	    } // end for
	    
	    indexNextRead = items.length();
	  } // end if
	} // end processMeteorQ()
	
	protected native void setAttributeValNative(IXholon node, String attrName, Object attrValue) /*-{
	  node[attrName] = attrValue;
	}-*/;
	
	/**
	 * Create a named Mongo Collection, if it doesn't already exist.
	 * I probably don't want to do this explicitly on the client; it should be sdet on the server ?
	 * @param collName (ex: "Test02")
	 */
	protected native void createMongoCollection(String collName) /*-{
	  if ((typeof $wnd[collName] === "undefined") && ($wnd.Mongo)) {
	    $wnd[collName] = new $wnd.Mongo.Collection(collName);
	  }
	}-*/;
	
	protected native int collLength(String collName) /*-{
	  if (typeof $wnd[collName] === "undefined") {
	    $wnd.console.log("MeteorPlatformService.collLength() $wnd[" + collName + "] is undefined");
	    return 0;
	  }
	  else {
	    return $wnd[collName].find().fetch().length;
	  }
	}-*/;
	
	protected native Object fetchItem(String collName, int itemIndex) /*-{
	  if (typeof $wnd[collName] === "undefined") {
	    $wnd.console.log("MeteorPlatformService.fetchItem1() $wnd[" + collName + "] is undefined");
	    return null;
	  }
	  else {
	    return $wnd[collName].find().fetch()[itemIndex];
	  }
	}-*/;
	
	protected native Object fetchItem(String collName, String meteorId) /*-{
	  if (typeof $wnd[collName] === "undefined") {
	    $wnd.console.log("MeteorPlatformService.fetchItem2() $wnd[" + collName + "] is undefined");
	    return null;
	  }
	  else {
	    return $wnd[collName].findOne(meteorId);
	  }
	}-*/;
	
	protected native JsArray fetchAllItems(String collName) /*-{
	  if (typeof $wnd[collName] === "undefined") {
	    $wnd.console.log("MeteorPlatformService.fetchAllItems() $wnd[" + collName + "] is undefined");
	    return [];
	  }
	  else {
	    return $wnd[collName].find().fetch();
	  }
	}-*/;
	
	/*
   * Fetch all items from the specified Collection, for the specified sessionId.
	 */
	protected native JsArray fetchAllSessionItems(String collName, String sessionId) /*-{
	  if (typeof $wnd[collName] === "undefined") {
	    $wnd.console.log("MeteorPlatformService.fetchAllSessionItems() $wnd[" + collName + "] is undefined");
	    return [];
	  }
	  else {
	    var mongoSelector = {sessionId: sessionId};
	    return $wnd[collName].find(mongoSelector).fetch();
	  }
	}-*/;
	
	/*
   * Fetch all items from the specified Collection, for the specified appName.
	 */
	protected native JsArray fetchAllAppItems(String collName, String appName) /*-{
	  if (typeof $wnd[collName] === "undefined") {
	    $wnd.console.log("MeteorPlatformService.fetchAllAppItems() $wnd[" + collName + "] is undefined");
	    return [];
	  }
	  else {
	    var mongoSelector = {app: appName};
	    if (appName == null) {
	      mongoSelector = {app: {$exists: false}};
	    }
	    return $wnd[collName].find(mongoSelector).fetch();
	  }
	}-*/;
	
	protected native void removeItem(String collName, String meteorId) /*-{
	  if (typeof $wnd[collName] === "undefined") {
	    $wnd.console.log("MeteorPlatformService.removeItem() $wnd[" + collName + "] is undefined");
	  }
	  else {
	    $wnd[collName].remove(meteorId);
	  }
	}-*/;
	
	protected native void removeAllItems(String collName) /*-{
	  if (typeof $wnd[collName] === "undefined") {
	    $wnd.console.log("MeteorPlatformService.removeAllItems() $wnd[" + collName + "] is undefined");
	  }
	  else {
	    var items = $wnd[collName].find().fetch();
	    for (var i = 0; i < items.length; i++) {
	      var id = items[i]._id;
	      $wnd[collName].remove(id);
	    }
	  }
	}-*/;
	
	protected native void removeAllSessionItems(String collName, String sessionId) /*-{
	  if (typeof $wnd[collName] === "undefined") {
	    $wnd.console.log("MeteorPlatformService.removeAllSessionItems() $wnd[" + collName + "] is undefined");
	  }
	  else {
	    var mongoSelector = {sessionId: sessionId};
	    var items = $wnd[collName].find(mongoSelector).fetch();
	    for (var i = 0; i < items.length; i++) {
	      var id = items[i]._id;
	      $wnd[collName].remove(id);
	    }
	  }
	}-*/;
	
	protected native void removeAllAppItems(String collName, String appName) /*-{
	  if (typeof $wnd[collName] === "undefined") {
	    $wnd.console.log("MeteorPlatformService.removeAllAppItems() $wnd[" + collName + "] is undefined");
	  }
	  else {
	    var mongoSelector = {app: appName};
	    var items = $wnd[collName].find(mongoSelector).fetch();
	    for (var i = 0; i < items.length; i++) {
	      var id = items[i]._id;
	      $wnd[collName].remove(id);
	    }
	  }
	}-*/;
	
	protected native void updateItem(String collName, String meteorId, Object obj, String sessionId) /*-{
	  if (typeof $wnd[collName] === "undefined") {
	    $wnd.console.log("MeteorPlatformService.updateItem() $wnd[" + collName + "] is undefined");
	  }
	  else {
	    obj.sessionId = sessionId;
      $wnd.console.log(obj);
      // TODO
      //$wnd[collName].update(meteorId, obj);
    }
	}-*/;
	
	/**
	 * Make and return a session ID.
	 */
	protected native String makeSessionId() /*-{
	  if ($wnd.Meteor) {
	    return $wnd.Meteor.uuid();
	  }
	  return null;
	}-*/;
  
  // actions
  String enableWrite = "Enable write";
  String disableWrite = "Disable write";
  String enableRead = "Enable read";
  String disableRead = "Disable read";
  String actionList[] = {enableWrite, disableWrite, enableRead, disableRead};
  
  @Override
  public String[] getActionList() {
    return actionList;
  }
  
  @Override
  public void doAction(String action) {
    if (enableWrite.equals(action)) {setShouldWrite(true);}
    else if (disableWrite.equals(action)) {setShouldWrite(false);}
    else if (enableRead.equals(action)) {setShouldRead(true);}
    else if (disableRead.equals(action)) {setShouldRead(false);}
  }
  
  /**
   * @return the shouldWrite
   */
  public boolean isShouldWrite() {
    return shouldWrite;
  }

  /**
   * @param shouldWrite 
   */
  public void setShouldWrite(boolean shouldWrite) {
    this.shouldWrite = shouldWrite;
  }

  /**
   * @return the shouldRead
   */
  public boolean isShouldRead() {
    return shouldRead;
  }

  /**
   * @param shouldRead the shouldRead to set
   */
  public void setShouldRead(boolean shouldRead) {
    this.shouldRead = shouldRead;
  }
  
  public String getCollName() {
    return collName;
  }
  
  public void setCollName(String collName) {
    this.collName = collName;
  }
  
  public String getAppName() {
    return collName;
  }
  
  public void setAppName(String appName) {
    this.appName = appName;
  }
  
}

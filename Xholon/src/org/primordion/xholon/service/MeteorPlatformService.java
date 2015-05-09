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

/**
 * Handle interaction with the Meteor Platform.
 * TODO support RFC 5261
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="https://www.meteor.com/">Meteor website</a>
 * @since 0.9.1 (Created on April 21, 2015)
 */
public class MeteorPlatformService extends AbstractXholonService {
  
  public static final int SIG_EXISTS_METEOR_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 101; // -3898
  public static final int SIG_SHOULD_WRITE_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 103; // -3896
  public static final int SIG_SHOULD_READ_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 104; // -3895
  
  /**
   * A request to insert something in a local Meteor collection (database).
   */
  public static final int SIG_COLL_INSERT_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 102; // -3897
  
  
  
  //public static final int SIG_EXISTS_METEOR_RESP = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 201; // -3798
  
  /**
   * A generic response to a request to do something.
   */
  public static final int SIG_METEOR_RESP = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 202; // -3797
  
  /** This is a single global control. */
  protected boolean shouldWrite = true;
  /** This is a single global control. */
  protected boolean shouldRead = true;
  
  /** The name of a Meteor collection. */
  protected String collName = "Test01";
  
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
  
  XholonHelperService xholonHelperService = null;
  
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
      if (msg.getData() != null) {
        consoleLog(msg.getData());
        // msg.getData() must be a boolean
        boolean sw = (Boolean)msg.getData();
        setShouldWrite(sw);
      }
      consoleLog("shouldWrite now equals " + isShouldWrite());
      return new Message(SIG_METEOR_RESP, isShouldWrite(), this, msg.getSender());
    case SIG_SHOULD_READ_REQ:
      if (msg.getData() != null) {
        // msg.getData() must be a boolean
        boolean sr = (Boolean)msg.getData();
        setShouldRead(sr);
      }
      return new Message(SIG_METEOR_RESP, isShouldRead(), this, msg.getSender());
    case SIG_COLL_INSERT_REQ:
      if (isShouldWrite() && isExistsMeteor()) {
        collInsert(msg.getSender(), (String[])msg.getData());
      }
      return new Message(SIG_METEOR_RESP, null, this, msg.getSender());
    default:
      return super.processReceivedSyncMessage(msg);
    }
    
  }
  
  /**
   * Insert something in a local Meteor collection (database).
   * Specify "add" "replace" "remove" as per RFC 5261
   * @param contextNode 
   * @param data XML that should be inserted at contextNode, patch op (ex: "add"), edit action (ex: "append").
   */
  protected void collInsert(IXholon contextNode, String[] data) {
    consoleLog("starting collInsert " + data.length);
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
    
    //collInsert(patchOp, collName, xpathExpr, editAction, xmlStr, sessionId);
    // TODO handle attributes and text
    XholonPatch xhp = new XholonPatch();
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
    collInsert(xhp.getVal_Object(), collName, sessionId);
  }
  
  /**
   * Insert an item into a Meteor collection.
   * Object {_id: "Z32o6Siws4h3u3Nik", op: "add", sel: "descendant::Hello", pos: "append", content: "<World roleName="Universe"/>"}
   */
//  protected native void collInsert(String patchOp, String collName, String sel, String pos, String xmlStr, String sessionId) /*-{
//    var obj = {};
//    obj.op = patchOp;
//    obj.sel = sel;
//    obj.pos = pos;
//    obj.content = xmlStr;
//    obj.sessionId = sessionId;
//    $wnd.console.log(obj);
//    $wnd[collName].insert(obj);
//  }-*/;
  
  /**
   * Insert an item into a Meteor collection.
   * Object {_id: "Z32o6Siws4h3u3Nik", op: "add", sel: "descendant::Hello", pos: "append", content: "<World roleName="Universe"/>"}
   */
  protected native void collInsert(Object obj, String collName, String sessionId) /*-{
    obj.sessionId = sessionId;
    $wnd.console.log(obj);
    $wnd[collName].insert(obj);
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
	    JsArray items = fetch(collName);
	    //consoleLog(items);
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
	        consoleLog("op " + op);
	        String xmlStr = xhp.getContent();
	        consoleLog("xmlStr " + xmlStr);
	        String sel = xhp.getSel();
	        consoleLog("sel " + sel);
	        String pos = xhp.getPos();
	        consoleLog("pos " + pos);
	        String type = xhp.getType();
	        consoleLog("type " + type);
	        String source = xhp.getSource();
	        consoleLog("source " + source);
	        String target = xhp.getTarget();
	        consoleLog("target " + target);
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
	
	//protected native int collLength(String collName) /*-{
	//  return $wnd[collName].find().fetch().length;
	//}-*/;
	
	protected native JsArray fetch(String collName) /*-{
	  return $wnd[collName].find().fetch();
	}-*/;
	
	//protected native Object fetchItem(String collName, int itemIndex) /*-{
	//  return $wnd[collName].find().fetch()[itemIndex];
	//}-*/;
	
	//protected native String op(JavaScriptObject item) /*-{
	//  return item.op;
	//}-*/;
	
	//protected native String sel(JavaScriptObject item) /*-{
	//  return item.sel;
	//}-*/;
	
	//protected native String pos(JavaScriptObject item) /*-{
	//  return item.pos;
	//}-*/;
	
	//protected native String content(JavaScriptObject item) /*-{
	//  return item.content;
	//}-*/;
	
	//protected native String sessionId(JavaScriptObject item) /*-{
	//  return item.sessionId;
	//}-*/;
	
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
  
  // this method has been moved to Xholon.java
  /**
   * Has the Meteor platform been loaded into the DOM?
   */
  //public native boolean isExistsMeteor() /*-{
  //  if ($wnd.Meteor) {
  //    return true;
  //  }
  //  else {
  //    return false;
  //  }
  //}-*/;
  
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
  
}
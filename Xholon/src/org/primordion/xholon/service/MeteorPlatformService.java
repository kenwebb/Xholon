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

import java.util.Date;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Message;

/**
 * Handle interaction with the Meteor Platform.
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
  
  
  
  public static final int SIG_EXISTS_METEOR_RESP = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 201; // -3798
  
  /**
   * A generic response to a request to do something.
   */
  public static final int SIG_METEOR_RESP = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 202; // -3797
  
  protected boolean shouldWrite = false;
  protected boolean shouldRead = false;
  
  protected String collName = "Test01";
  
  protected IApplication app = null;
  
  @Override
  public IXholon getService(String serviceName)
  {
    if (serviceName.equals(getXhcName())) {
      consoleLog("MeteorPlatformService");
      app = this.getApp();
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
        collInsert(msg.getSender(), (String)msg.getData());
      }
      return new Message(SIG_METEOR_RESP, null, this, msg.getSender());
    default:
      return super.processReceivedSyncMessage(msg);
    }
    
  }
  
  /**
   * Insert something in a local Meteor collection (database).
   * TODO also specify "add" "replace" "remove" as per RFC 5261
   * @param contextNode 
   * @param data XML that should be inserted at contextNode, and edit action (ex: "pasteLastChild").
   */
  protected void collInsert(IXholon contextNode, String data) {
    consoleLog("starting collInsert " + data);
    //consoleLog("contextNode " + contextNode);
    String xpathExpr = getXPath().getExpression(app.getRoot(), contextNode); //, false);
    consoleLog("xpathExpr " + xpathExpr);
    if ((xpathExpr == null) || xpathExpr.length() == 0) {xpathExpr = ".";}
    //consoleLog("xpathExpr " + xpathExpr);
    String[] str = data.split(",");
    consoleLog(str);
    String xmlStr = null;
    String editAction = "pasteLastChild"; // default
    switch (str.length) {
    case 1:
      xmlStr = str[0];
      break;
    case 2:
      xmlStr = str[0];
      editAction = str[1];
      break;
    default:
      return;
    }
    
    collInsert("add", collName, xpathExpr, editAction, xmlStr);
  }
  
  /**
   * 
   * Test01.insert({title: "Testing 1234", content: "one two three four"});
   * Object {sel: ".", pos: "pasteLastChild", xmlStr: "<World roleName="Universe"/>"}
   */
  protected native void collInsert(String patchOp, String collName, String sel, String pos, String xmlStr) /*-{
    var obj = {};
    obj.op = patchOp;
    obj.sel = sel;
    obj.pos = pos;
    obj.content = xmlStr;
    $wnd.console.log(obj);
    $wnd[collName].insert(obj);
  }-*/;
  
  // I'm probably NOT going to do actions
  /*String one = "One";
  String two = "Two";
  String actionList[] = {one, two};
  
  @Override
  public String[] getActionList() {
    return actionList;
  }
  
  @Override
  public void doAction(String action) {
    
  }*/
  
  /**
   * Has the Meteor platform been loaded into the DOM?
   */
  public native boolean isExistsMeteor() /*-{
    if ($wnd.Meteor) {
      return true;
    }
    else {
      return false;
    }
  }-*/;
  
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

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

package org.primordion.xholon.service.remotenode;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Message;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.util.ClassHelper;

/**
 * Enable simple cross-application communications.
 * One window must be a child of the other window.
 * cross-application, same browser, same domain, no postMessage required
 * see "The Love Letter - Jake - no postMessage()" and "... Helen ..." workbooks
 *
 * TODO
 * - useIframe = false  Google Chrome sees the separate window as a popup
 *   Uncaught SecurityError: Blocked a frame with origin "http://localhost" from accessing a frame with origin "swappedout://".  The frame requesting access has a protocol of "http", the frame being accessed has a protocol of "swappedout". Protocols must match.
 *   it works if I allow popups for that domain
 *
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on September 10, 2015)
 */
public class CrossApp extends AbstractRemoteNode implements IRemoteNode {
  
  public CrossApp() {
    this.println("instantiated a " + this.getClass().getName());
    setOnDataJsonSync(false);
    setOnDataTextSync(true);
    setOnDataTextAction(false);
  }
  
  @Override
  public boolean isUsable() {
    // This type of communications is built in to all browsers.
    return true;
  };
  
  @Override
  protected native Object fixListenParams(String listenParams) /*-{
    var obj = {};
    obj.localid = null;
    obj.remoteXPathExpr = null;
    if (listenParams) {
      var data = listenParams.split(",", 2);
      $wnd.console.log(data);
      switch(data.length) {
      // break is intentionally left out so the cases will fall through
      case 2: obj.remoteXPathExpr = data[1];
      case 1: obj.localid = data[0];
      default: break;
      }
    }
    if (obj.localid.length == 0) {
      obj.localid = null;
    }
    return obj;
  }-*/;
  
  @Override
  protected native Object fixConnectParams(String connectParams) /*-{
    var obj = {};
    obj.useIframe = true;
    obj.remoteUrl = "";
    obj.remoteid = "";
    if (connectParams) {
      var data = connectParams.split(",", 3);
      $wnd.console.log(data);
      switch(data.length) {
      // break is intentionally left out so the cases will fall through
      case 3: if (data[2] == "false") {obj.useIframe = false;}
      case 2: obj.remoteUrl = data[1];
      case 1: obj.remoteid = data[0];
      default: break;
      }
    }
    if (obj.remoteid.length == 0) {
      obj.remoteid = null;
    }
    if (obj.remoteUrl.length == 0) {
      obj.remoteUrl = null;
    }
    return obj;
  }-*/;
  
  @Override
  protected native void listen(Object listenParams, IXholon reffedNode) /*-{
    var localid = listenParams.localid;
    var remoteXPathExpr = listenParams.remoteXPathExpr;
    if (localid == null) {
      var appName = "" + $wnd.xh.html.urlparam("app").replace(/\+/g," ").substring(0,18);
      localid = appName + "_" + new Date().getTime() + "_" + reffedNode.id();
    }
    this.println(localid);
    this.println(remoteXPathExpr);
    
    // assume that this app has been opened by another app
    var otherWindow = null;
    if ($wnd.self == $wnd.top) {
      // this app is in a separately opened window
      $wnd.console.log("this app is in a separately opened window");
      otherWindow = $wnd.opener;
    }
    else {
      // this app is in an iframe
      $wnd.console.log("this app is in an iframe");
      otherWindow = $wnd.top;
    }
    $wnd.console.log(otherWindow);
    if (remoteXPathExpr) {
      //this.remoteNode = otherWindow.xh.root().xpath(remoteXPathExpr);
      this.port(1, otherWindow.xh.root().xpath(remoteXPathExpr));
    }
    else {
      //this.remoteNode = otherWindow.xh.root();
      this.port(1, otherWindow.xh.root());
    }
    if (this.port(1)) { //this.remoteNode) {
      $wnd.console.log(this.port(1).name()); //this.remoteNode.name());
      $wnd.console.log("let the other window/app know that this window/app is ready");
      // let the other window/app know that this window/app is ready
      //this.remoteNode.msg(201, null, reffedNode);
      this.port(1).msg(@org.primordion.xholon.base.ISignal::SIGNAL_READY, null, reffedNode); // -11
    }
  }-*/;
  
  @Override
  protected native void connect(Object connectParams, IXholon reffingNode) /*-{
    var remoteid = connectParams.remoteid;
    var remoteUrl = connectParams.remoteUrl;
    var useIframe = connectParams.useIframe;
    //var appName = "" + $wnd.xh.html.urlparam("app").replace(/\+/g," ").substring(0,18);
    //var localid = appName + "_" + new Date().getTime() + "_" + reffingNode.id();
    //this.println("localid " + localid);
    this.println("remoteid " + remoteid);
    this.println("remoteUrl " + remoteUrl);
    this.println("useIframe " + useIframe);
    
    var url = $wnd.location.protocol
      + "//"
      + $wnd.location.hostname
      + $wnd.location.port
      + $wnd.location.pathname
      + remoteUrl;
    this.println("url " + url);
    
    var otherWindow = null;
    if (useIframe) {
      otherWindow = this.@org.primordion.xholon.service.remotenode.AbstractRemoteNode::createIframe(Ljava/lang/String;)(url).contentWindow;
    }
    else {
      // Google Chrome will invoke its popup blocker to prevent this; user must enable popups for this domain
      otherWindow = $wnd.open(url, remoteid);
    }
    $wnd.console.log(otherWindow);
  }-*/;
  
  @Override
  protected native boolean txRemote(int signal, Object data) /*-{
    //if (typeof this.connexn === "undefined") {return false;}
    //var jsonStr = '{"signal":' + signal + ',"data":' + $wnd.JSON.stringify(data) + '}';
    //this.connexn.send(jsonStr);
    if (!this.port(1)) {return false;}
    this.port(1).msg({signal:signal, data:data, sender:this});
    return true;
  }-*/;
  
  @Override
  protected native boolean txRemote(Object data) /*-{
    //if (typeof this.connexn === "undefined") {return false;}
    //this.connexn.send(data);
    if (!this.port(1)) {return false;}
    this.port(1).msg({signal:-9, data:data, sender:this});
    return true;
  }-*/;
  
  // actions
  private static final String showThisNode = "Show This Node";
  
  /** action list */
  private String[] actions = {showThisNode};
  
  @Override
  public String[] getActionList()
  {
    return actions;
  }
  
  @Override
  public void setActionList(String[] actionList)
  {
    actions = actionList;
  }
  
  @Override
  public void doAction(String action)
  {
    if (action == null) {return;}
    if (showThisNode.equals(action)) {
      this.println(this.getClass().getName() + " " + this.getName());
      this.println(" onDataJsonSync   " + this.isOnDataJsonSync());
      this.println(" onDataTextSync   " + this.isOnDataTextSync());
      this.println(" onDataTextAction " + this.isOnDataTextAction());
    }
  }
  
}

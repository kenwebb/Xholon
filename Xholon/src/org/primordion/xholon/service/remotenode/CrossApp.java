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
  public boolean isColocated() {
    return true;
  }
  
  // child window, Jake
  // ex: <port name="trop" index="1" connector="RemoteNodeService-CrossApp,1,NoBoundariesSystem/Helen"/>
  @Override
  protected native Object fixListenParams(String listenParams) /*-{
    var obj = {};
    obj.remoteXPathExpr = null; // ex: NoBoundariesSystem/Helen
    obj.localPortName = null; // ex: "0"  "1"  "2"  "world"  "helen"
    if (listenParams) {
      var data = listenParams.split(",", 2);
      $wnd.console.log(data);
      switch(data.length) {
      // break is intentionally left out so the cases will fall through
      case 2: obj.remoteXPathExpr = data[1];
      case 1: obj.localPortName = data[0];
      default: break;
      }
    }
    $wnd.console.log(obj);
    if (obj.remoteXPathExpr.length == 0) {obj.remoteXPathExpr = null;}
    if (obj.localPortName.length == 0) {obj.localPortName = null;}
    $wnd.console.log(obj);
    return obj;
  }-*/;
  
  // parent window, Helen
  // ex: <port name="port" index="1" connector="RemoteNodeService-CrossApp,1,NoBoundariesSystem/Jake,Jake,?app=88113e296a37af87317f&amp;src=gist&amp;gui=clsc,false"/>
  @Override
  protected native Object fixConnectParams(String connectParams) /*-{
    var obj = {};
    obj.useIframe = true;
    obj.remoteUrl = ""; // remote URL fragment
    // ex: "?app=The%20Love%20Letter%20-%20Jake%20-%20CrossApp&amp;src=lstr&amp;gui=clsc"
    obj.remoteWindowName = ""; // ex: "Jake"
    obj.remoteXPathExpr = null; // ex: "NoBoundariesSystem/Jake"
    obj.localPortName = null; // ex: "0"  "1"  "2"  "world"  "helen"
    if (connectParams) {
      var data = connectParams.split(",", 5);
      $wnd.console.log(data);
      switch(data.length) {
      // break is intentionally left out so the cases will fall through
      case 5: if (data[4] == "false") {obj.useIframe = false;}
      case 4: obj.remoteUrl = data[3];
      case 3: obj.remoteWindowName = data[2];
      case 2: obj.remoteXPathExpr = data[1];
      case 1: obj.localPortName = data[0];
      default: break;
      }
    }
    if (obj.remoteUrl.length == 0) {obj.remoteUrl = null;}
    if (obj.remoteWindowName.length == 0) {obj.remoteWindowName = null;}
    if (obj.remoteXPathExpr.length == 0) {obj.remoteXPathExpr = null;}
    if (obj.localPortName.length == 0) {obj.localPortName = null;}
    return obj;
  }-*/;
  
  // child window, Jake
  @Override
  protected native void listen(Object listenParams, IXholon reffedNode) /*-{
    this.role(reffedNode.name("R^^^^^"));
    var localPortName = listenParams.localPortName;
    var remoteXPathExpr = listenParams.remoteXPathExpr;
    // assume that this app has been opened by another app
    var otherWindow = null;
    if ($wnd.self == $wnd.top) {
      // this app is in a separately opened window
      otherWindow = $wnd.opener;
    }
    else {
      // this app is in an iframe
      otherWindow = $wnd.top;
    }
    if (remoteXPathExpr) {
      this.port(1, otherWindow.xh.root().xpath(remoteXPathExpr));
    }
    else {
      this.port(1, otherWindow.xh.root());
    }
    if (this.port(1)) {
      // have the local node's port reference the remote node
      reffedNode.port(localPortName, this.port(1));
      // let the other window/app know that this window/app is ready
      this.port(1).msg(@org.primordion.xholon.base.ISignal::SIGNAL_READY, null, reffedNode); // -11
    }
  }-*/;
  
  // parent window, Helen
  @Override
  protected native void connect(Object connectParams, IXholon reffingNode) /*-{
    this.role(reffingNode.name("R^^^^^"));
    var localPortName = connectParams.localPortName;
    var remoteXPathExpr = connectParams.remoteXPathExpr;
    var remoteWindowName = connectParams.remoteWindowName;
    var remoteUrl = connectParams.remoteUrl;
    var useIframe = connectParams.useIframe;
    var url = this.@org.primordion.xholon.service.remotenode.AbstractRemoteNode::makeOriginStr()();
    url += $wnd.location.pathname + remoteUrl;
    var otherWindow = null;
    if (useIframe) {
      otherWindow = this.@org.primordion.xholon.service.remotenode.AbstractRemoteNode::createIframe(Ljava/lang/String;)(url).contentWindow;
    }
    else {
      // Google Chrome will invoke its popup blocker to prevent this; user must enable popups for this domain
      otherWindow = $wnd.open(url, remoteWindowName);
    }
    // otherWindow.xh doesn't exist yet; save for connectFinish()
    connectParams.otherWindow = otherWindow;
    connectParams.reffingNode = reffingNode;
    this.connectParams = connectParams;
  }-*/;
  
  /**
   * Finish the connection, if not already done.
   * otherWindow.xh does exist now.
   * This has to be done before txRemote() can be called.
   */
  protected native void connectFinish() /*-{
    if (this.connectParams.remoteXPathExpr) {
      this.port(1, this.connectParams.otherWindow.xh.root().xpath(this.connectParams.remoteXPathExpr));
    }
    else {
      this.port(1, this.connectParams.otherWindow.xh.root());
    }
    if (this.port(1)) {
      // have the local node's port reference the remote node
      this.connectParams.reffingNode.port(this.connectParams.localPortName, this.port(1));
    }
  }-*/;
  
  @Override
  protected boolean txRemote(IMessage msg) {
    if (this.getPort(1) == null) {
      connectFinish();
    }
    if (this.getPort(1) == null) {
      return false;
    }
    this.getPort(1).processReceivedMessage(msg);
    return true;
  }
  
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

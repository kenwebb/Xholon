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
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Message;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.util.ClassHelper;

/**
 * Enable cross-domain communications using HTML5 MessageChannel.
 *
 * To test an event listener from Dev Tools:
var abc = function(event) {console.log(event);}
window.addEventListener("message", abc);
window.postMessage("hello","*"); // helen_46 has received a message: hello
window.postMessage("<Two/>","*"); // helen_46 gets a new <Two/> child
window.postMessage("hello two","http://127.0.0.1:8888"); // OK
window.postMessage("hello two","http://127.0.0.1:888");
// Failed to execute 'postMessage' on 'DOMWindow': The target origin provided ('http://127.0.0.1:888') does not match the recipient window's origin ('http://127.0.0.1:8888').
window.postMessage("hello three",window.location.origin); // OK
window.postMessage("hello three",location.origin); // OK

// otherWindow
temp1.postMessage("temp1 un","*");
temp1.postMessage({signal: 102, data: "un deux trois"},"*"); // Helen has received a message (signal:102) from MessageChannel: un deux trois
temp1.postMessage("txPort2", "http://127.0.0.1:8888", []);
 *
 * MSC - Establishing a connection between port1 and port2 of the MessageChannel
+-------+                        +------+
| Helen |                        | Jake |
|connect|                        |listen|
+-------+                        +------+
    |      -- open window     ->    |
    |      <- -11 ready       --    |
    |      -- [channel.port2] ->    |
    |port1 <- Message back --  port2|
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on September 10, 2015)
 */
public class MessageChannel extends AbstractRemoteNode implements IRemoteNode {
  
  public MessageChannel() {
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
  
  // child window, Jake
  @Override
  protected native Object fixListenParams(String listenParams) /*-{
    var obj = {};
    obj.remoteXPathExpr = null; // ex: NoBoundariesSystem/Helen
    obj.localPortName = null; // ex: "0"  "1"  "2"  "world"  "helen"
    if (listenParams) {
      var data = listenParams.split(",", 2);
      switch(data.length) {
      // break is intentionally left out so the cases will fall through
      case 2: obj.remoteXPathExpr = data[1];
      case 1: obj.localPortName = data[0];
      default: break;
      }
    }
    if (obj.remoteXPathExpr.length == 0) {obj.remoteXPathExpr = null;}
    if (obj.localPortName.length == 0) {obj.localPortName = null;}
    return obj;
  }-*/;
  
  // parent window, Helen
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
    var myself = this;
    var jsObj = {};
    jsObj.signal = @org.primordion.xholon.base.ISignal::SIGNAL_READY; // -11
    jsObj.data = null;
    jsObj.sender = reffedNode.name();
    if (otherWindow) {
      otherWindow.postMessage(jsObj, myself.@org.primordion.xholon.service.remotenode.MessageChannel::makeOriginStr()());
    }
    listenParams.otherWindow = otherWindow;
    listenParams.reffedNode = reffedNode;
    var receiveMessageWNlis = function(event) {
      $wnd.console.log("receiveMessageWNlis");
      $wnd.console.log(event);
      if (event.data == "txPort2") {
        listenParams.myMcPort = event.ports[0];
        listenParams.myMcPort.onmessage = receiveMessagePRTlis; // use different functions for the two event listeners
        // Channel messages should NOT contain an origin string
        listenParams.myMcPort.postMessage("Message back from the other window");
        return;
      }
      otherWindow = event.source;
      var expectedOrigin = myself.@org.primordion.xholon.service.remotenode.MessageChannel::makeOriginStr()();
      if (event.origin && (event.origin.length > 0) && (event.origin !== expectedOrigin)) {
        $wnd.console.log("message origin is invalid " + event.origin + "; expected " + expectedOrigin);
        return;
      }
      else {
        var jsObj = {};
        jsObj.signal = 101;
        jsObj.data = event.data;
        myself.@org.primordion.xholon.service.remotenode.MessageChannel::rxRemote(Ljava/lang/Object;Lorg/primordion/xholon/base/IXholon;)(jsObj, reffedNode);
      }
    }
    $wnd.addEventListener("message", receiveMessageWNlis);
    var receiveMessagePRTlis = function(event) {
      $wnd.console.log("receiveMessagePRTlis");
      $wnd.console.log(event);
      var jsObj = {};
      jsObj.signal = 101; // maybe it should be 102? NO
      jsObj.data = event.data;
      myself.@org.primordion.xholon.service.remotenode.MessageChannel::rxRemote(Ljava/lang/Object;Lorg/primordion/xholon/base/IXholon;)(jsObj, reffedNode);
    }
    listenParams.myMcPort = null;
    this.connectParams = listenParams;
  }-*/;
  
  // parent window, Helen
  @Override
  protected native void connect(Object connectParams, IXholon reffingNode) /*-{
    var myself = this;
    this.role(reffingNode.name("R^^^^^"));
    var localPortName = connectParams.localPortName;
    var remoteXPathExpr = connectParams.remoteXPathExpr;
    var remoteWindowName = connectParams.remoteWindowName;
    var remoteUrl = connectParams.remoteUrl;
    var useIframe = connectParams.useIframe;
    var url = myself.@org.primordion.xholon.service.remotenode.MessageChannel::makeOriginStr()();
    url += $wnd.location.pathname + remoteUrl;
    var otherWindow = null; // Jake's window
    if (useIframe) {
      otherWindow = this.@org.primordion.xholon.service.remotenode.AbstractRemoteNode::createIframe(Ljava/lang/String;)(url).contentWindow;
    }
    else {
      // Google Chrome will invoke its popup blocker to prevent this; user must enable popups for this domain
      otherWindow = $wnd.open(url, remoteWindowName);
      //otherWindow.addEventListener('load', function() {console.log("TEST of otherWindow.addEventListener('load'");}, true);
    }
    // MessageChannel
    var channel = new MessageChannel();
    if (!otherWindow) {
      $wnd.console.log("otherWindow is null");
    }
    connectParams.otherWindow = otherWindow;
    connectParams.reffingNode = reffingNode;
    connectParams.channel = channel;
    connectParams.myMcPort = channel.port1;
    this.connectParams = connectParams;
    var receiveMessageWNcon = function(event) { // WN window events
      $wnd.console.log("receiveMessageWNcon");
      $wnd.console.log(event);
      var expectedOrigin = myself.@org.primordion.xholon.service.remotenode.MessageChannel::makeOriginStr()();
      // Channel messages do not contain the origin
      if (event.origin && (event.origin.length > 0) && (event.origin !== expectedOrigin)) {
        $wnd.console.log("message origin is invalid " + event.origin + "; expected " + expectedOrigin);
        $wnd.console.log(event);
        return;
      }
      else {
        if (event.data && event.data.signal && (event.data.signal == @org.primordion.xholon.base.ISignal::SIGNAL_READY)) { // signal -11
          otherWindow.postMessage("txPort2", myself.@org.primordion.xholon.service.remotenode.MessageChannel::makeOriginStr()(), [channel.port2]);
        }
        myself.@org.primordion.xholon.service.remotenode.MessageChannel::rxRemote(Ljava/lang/Object;Lorg/primordion/xholon/base/IXholon;)(event.data, connectParams.reffingNode);
      }
    }
    var receiveMessagePRTcon = function(event) { // PRT channel port events
      $wnd.console.log("receiveMessagePRTcon");
      $wnd.console.log(event);
      var expectedOrigin = myself.@org.primordion.xholon.service.remotenode.MessageChannel::makeOriginStr()();
      // Channel messages do not contain the origin
      if (event.origin && (event.origin.length > 0) && (event.origin !== expectedOrigin)) {
        $wnd.console.log("message origin is invalid " + event.origin + "; expected " + expectedOrigin);
        $wnd.console.log(event);
        return;
      }
      else {
        // assume that event.data is a JavaScript Object; in fact it's a simple String
        //myself.@org.primordion.xholon.service.remotenode.MessageChannel::rxRemote(Ljava/lang/Object;Lorg/primordion/xholon/base/IXholon;)(event.data, connectParams.reffingNode);
        var jsObj = {};
        jsObj.signal = 102;
        jsObj.data = event.data;
        myself.@org.primordion.xholon.service.remotenode.MessageChannel::rxRemote(Ljava/lang/Object;Lorg/primordion/xholon/base/IXholon;)(jsObj, connectParams.reffingNode);
      }
    }
    // I need both of the following listeners; BUT maybe they interfere with each other and can't use the same function
    $wnd.addEventListener("message", receiveMessageWNcon);
    channel.port1.onmessage = receiveMessagePRTcon;
  }-*/;
  
  @Override
  protected boolean txRemote(IMessage msg) {
    this.postMessage(msg);
    return true;
  }
  
  @Override
  protected boolean txRemote(int signal, Object data) {
    this.postMessage(signal, data);
    return true;
  }
  
  /**
   * Transmit/Send a message to a remote app using a PeerJS connection.
   * Typically this will be an XML string, that's a serialization of a IXholon node or subtree.
   * @param data A Xholon IMessage data.
   */
  @Override
  protected native boolean txRemote(Object data) /*-{
    this.connectParams.myMcPort.postMessage(data);
    return true;
  }-*/;
  
  protected native void postMessage(IMessage msg) /*-{
    this.connectParams.myMcPort.postMessage(msg.obj().data);
  }-*/;
  
  protected native void postMessage(int signal, Object data) /*-{
    this.connectParams.myMcPort.postMessage(data);
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

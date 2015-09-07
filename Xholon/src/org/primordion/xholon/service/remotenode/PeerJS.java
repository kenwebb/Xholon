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

/**
 * Provide access to the PeerJS library, to use WebRTC.
 * This class requires $wnd.Peer which is available in XholonWebRTC.html .
 * This class can be tested using:
 *   http://www.primordion.com/Xholon/gwt/PeerjsChat.html
 * Testing works well with the basic Java HelloWorld app:
 *   http://localhost/Xholon_localhost/XholonWebRTC.html?app=HelloWorld&gui=clsc
 * 
 * TODO
 * - be able to specify whether to use msg() or call() in onData()
 *   onDataJsonSync = false
 *   onDataTextSync = true
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on September 2, 2015)
 */
public class PeerJS extends Xholon implements IRemoteNode {
  
  protected static final String PEERJS_DEMO_API_KEY = "lwjd5qra8257b9";
  
  public PeerJS() {
    setOnDataJsonSync(false);
    setOnDataTextSync(true);
  }
  
  protected native void setOnDataJsonSync(boolean onDataJsonSync) /*-{
    this.onDataJsonSync = onDataJsonSync;
  }-*/;
  
  protected native boolean getOnDataJsonSync() /*-{
    return this.onDataJsonSync;
  }-*/;
  
  protected native void setOnDataTextSync(boolean onDataTextSync) /*-{
    this.onDataTextSync = onDataTextSync;
  }-*/;
  
  protected native boolean getOnDataTextSync() /*-{
    return this.onDataTextSync;
  }-*/;
  
  @Override
  public native boolean isUsable() /*-{
    return typeof $wnd.Peer !== "undefined";
  }-*/;
  
  @Override
  public void processReceivedMessage(IMessage msg) {
    switch (msg.getSignal()) {
    default:
      // this message is from the local reffedNode
      // format it as a JSON object, and call connection.send(obj);
      //consoleLog("processReceivedMessage " + msg.getSignal() + " " + msg.getData());
      sendRemote(msg.getSignal(), msg.getData(), this);
    }
  }
  
  @Override
  public IMessage processReceivedSyncMessage(IMessage msg) {
    consoleLog(msg.getVal_Object());
    switch (msg.getSignal()) {
    
    /**
     The PeerJS ID could be a simple name such as "Alligator",
     or a generated name that includes the Xholon app name + the date/time + Xholon ID of the remote node.
     For example: WebRTC peerjs Avatar_1441024140451_40
     The Xholon ID could be just the numeric id (40), or it could be the node's default name (avatar_40),
     or it could be an XPath expression from root (Chameleon/PhysicalSystem/City/Avatar)
    */
    
    /**
     * Each app that wants to use WebRTC needs to set up a Listener
     * that registers itself with the PeerJS server.
     * One listener per node
     * Each listener will need a unique PeerJS ID.
     * 
     */
    case SIG_LISTEN_REQ:
    {
      String localid = null;
      String key = null;
      int debug = 3; // debug level (0 - 3)
      if (msg.getData() != null) {
        // Alligator
        // Alligator_123,lwjd5qra8257b9,3
        // Alligator,,1
        // ,,2
        String[] data = ((String)msg.getData()).split(",", 3);
        consoleLog(data);
        switch(data.length) {
        // break is intentionally left out so the cases will fall through
        case 3: debug = Integer.parseInt(data[2]);
        case 2: key = data[1];
        case 1: localid = data[0];
        default: break;
        }
      }
      if (key == null || key.length() == 0) {
        key = PEERJS_DEMO_API_KEY;
      }
      if (localid.length() == 0) {
        localid = null;
      }
      listen(localid, key, debug, msg.getSender(), this);
      return new Message(SIG_LISTEN_RESP, null, this, msg.getSender());
    }
    
    // initiate a remote connection
    case SIG_CONNECT_REQ:
    {
      String remoteid = null;
      String key = null;
      int debug = 3; // debug level (0 - 3)
      if (msg.getData() != null) {
        // Alligator
        // Alligator_123,lwjd5qra8257b9,3
        // Alligator,,1
        // ,,2
        String[] data = ((String)msg.getData()).split(",", 3);
        consoleLog(data);
        switch(data.length) {
        // break is intentionally left out so the cases will fall through
        case 3: debug = Integer.parseInt(data[2]);
        case 2: key = data[1];
        case 1: remoteid = data[0];
        default: break;
        }
      }
      if (key == null || key.length() == 0) {
        key = PEERJS_DEMO_API_KEY;
      }
      if (remoteid.length() == 0) {
        remoteid = null;
      }
      connect(remoteid, key, debug, msg.getSender(), this);
      return new Message(SIG_CONNECT_RESP, null, this, msg.getSender());
    }
    
    case SIG_ON_DATA_JSON_SYNC_REQ:
      setOnDataJsonSync((Boolean)msg.getData());
      return new Message(SIG_ON_DATA_RESP, null, this, msg.getSender());
      
    case SIG_ON_DATA_TEXT_SYNC_REQ:
      setOnDataTextSync((Boolean)msg.getData());
      return new Message(SIG_ON_DATA_RESP, null, this, msg.getSender());
    
    default:
    {
      return super.processReceivedSyncMessage(msg);
    }
    
    } // end switch
  } // end processReceivedSyncMessage()
  
  /**
   * Listen for remote apps that want to reference a node within this app, using a PeerJS connection.
   * @param localid A PeerJS ID, or null.
   * @param key A PeerJS API key, or null.
   * @param debug A PeerJS debug level (0-3).
   * @param reffedNode The node in this app that can be reffed by nodes in other apps.
   * @param me This PeerJS node.
   */
  protected native void listen(String localid, String key, int debug, IXholon reffedNode, IXholon me) /*-{
    if (typeof $wnd.Peer === "undefined") {return;}
    var meName = me.name();
    if (localid == null) {
      var appName = "" + $wnd.xh.html.urlparam("app").replace(/\+/g," ").substring(0,18);
      localid = appName + "_" + new Date().getTime() + "_" + reffedNode.id();
    }
    me.println(localid);
    me.println(key);
    me.println(debug);
    var peer = new $wnd.Peer(localid, {key: key, debug: debug});
    peer.on('connection', function(connection) {
      $wnd.console.log(connection.label);
      if (connection.label != "file") { // Peerjs Chat also tries to set up a "file" connection
        me.connexn = connection; // cache the connection object so I can send messages on it
        connection.on('data', function(data) {
          me.@org.primordion.xholon.service.remotenode.PeerJS::onData(Ljava/lang/String;Lorg/primordion/xholon/base/IXholon;Lorg/primordion/xholon/base/IXholon;)(data, reffedNode, me);
        });
      }
    });
  }-*/;
  
  /**
   * Connect to a remote node that's listening for connection requests.
   * @param remoteid A valid PeerJS ID.
   * @param key A PeerJS API key, or null.
   * @param debug A PeerJS debug level (0-3).
   * @param reffingNode The node in this app that is trying to reference nodes in other apps.
   * @param me This PeerJS node.
   */
  protected native void connect(String remoteid, String key, int debug, IXholon reffingNode, IXholon me) /*-{
    if (typeof $wnd.Peer === "undefined") {return;}
    var meName = me.name();
    var appName = "" + $wnd.xh.html.urlparam("app").replace(/\+/g," ").substring(0,18);
    var localid = appName + "_" + new Date().getTime() + "_" + reffingNode.id();
    me.println("localid " + localid);
    me.println("remoteid " + remoteid);
    me.println(key);
    me.println(debug);
    var peer = new $wnd.Peer(localid, {key: key, debug: debug});
    var connection = peer.connect(remoteid);
    me.connexn = connection; // cache the connection object so I can send messages on it
    connection.on('data', function(data) {
      me.@org.primordion.xholon.service.remotenode.PeerJS::onData(Ljava/lang/String;Lorg/primordion/xholon/base/IXholon;Lorg/primordion/xholon/base/IXholon;)(data, reffingNode, me);
    });
  }-*/;
  
  /**
   * Handle connection.on('data', function(data) .
   * @param data The data received from a remote source.
   *   This can be either a JSON string, or a plain text string.
   *   If it's JSON, then it's probably from a remote IXholon node.
   *   If it's plain text, then this is probably a human-generated command for an Avatar,
   *     such as from PeerjsChat.html
   * @param node The IXholon node that this PeerJS instance sends to, using call() or msg() .
   * @param me This instance of PeerJS.
   */
  protected native void onData(String data, IXholon node, IXholon me) /*-{
    if (data.substring(0,1) == "{") {
      var obj = $wnd.JSON.parse(data);
      if (obj) {
        var objSignal = obj.signal ? obj.signal : -9;
        var objData = obj.data ? obj.data : "";
        if (this.onDataJsonSync) {
          node.call(objSignal, objData, me);
        }
        else {
          node.msg(objSignal, objData, me);
        }
      }
    }
    else {
      if (this.onDataTextSync) {
        var respMsg = node.call(-9, data, me);
        me.connexn.send(respMsg.data);
      }
      else {
        node.msg(-9, data, me);
      }
    }
  }-*/;
  
  /**
   * Send a message to a remote app using a PeerJS connection.
   * proxy.msg(103, "Proxy 1", xh.root().first());
   * {"signal":101,"data":"One two three"}
   * @param signal A Xholon IMessage signal.
   * @param data A Xholon IMessage data.
   * @param me This instance of PeerJS.
   */
  protected native void sendRemote(int signal, Object data, IXholon me) /*-{
    if (typeof me.connexn === "undefined") {return;}
    var jsonStr = '{"signal":' + signal + ',"data":"' + data + '"}';
    me.connexn.send(jsonStr);
  }-*/;
  
}

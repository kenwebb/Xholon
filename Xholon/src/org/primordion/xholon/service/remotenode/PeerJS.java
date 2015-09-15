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

//import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;
//import org.primordion.xholon.base.Message;
//import org.primordion.xholon.base.Xholon;

/**
 * Provide access to the PeerJS library, to use WebRTC.
 * This class requires $wnd.Peer which is available in XholonWebRTC.html .
 * This class can be tested using:
 *   http://www.primordion.com/Xholon/gwt/PeerjsChat.html
 * Testing works well with the basic Java HelloWorld app:
 *   http://localhost/Xholon_localhost/XholonWebRTC.html?app=HelloWorld&gui=clsc
 * 
 * - be able to specify whether to use msg() or call() or action() in onData()
 *   onDataJsonSync   = false
 *   onDataTextSync   = true
 *   onDataTextAction = false
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on September 2, 2015)
 */
public class PeerJS extends AbstractRemoteNode implements IRemoteNode {
  
  protected static final String PEERJS_DEMO_API_KEY = "lwjd5qra8257b9";
  
  public PeerJS() {
    setOnDataJsonSync(false);
    setOnDataTextSync(true);
    setOnDataTextAction(false);
  }
  
  @Override
  public native boolean isUsable() /*-{
    return typeof $wnd.Peer !== "undefined";
  }-*/;
  
  @Override
  protected native Object fixListenParams(String listenParams) /*-{
    var obj = {};
    obj.localid = null;
    obj.key = null;
    obj.debug = 3; // debug level (0 - 3)
    if (listenParams) {
      // Alligator
      // Alligator_123,lwjd5qra8257b9,3
      // Alligator,,1
      // ,,2
      var data = listenParams.split(",", 3);
      $wnd.console.log(data);
      switch(data.length) {
      // break is intentionally left out so the cases will fall through
      case 3: obj.debug = data[2];
      case 2: obj.key = data[1];
      case 1: obj.localid = data[0];
      default: break;
      }
    }
    if (obj.key == null || obj.key.length == 0) {
      obj.key = @org.primordion.xholon.service.remotenode.PeerJS::PEERJS_DEMO_API_KEY;
    }
    if (obj.localid.length == 0) {
      obj.localid = null;
    }
    return obj;
  }-*/;
  
  @Override
  protected native Object fixConnectParams(String connectParams) /*-{
    var obj = {};
    obj.remoteid = null;
    obj.key = null;
    obj.debug = 3; // debug level (0 - 3)
    if (connectParams) {
      // Alligator
      // Alligator_123,lwjd5qra8257b9,3
      // Alligator,,1
      // ,,2
      var data = connectParams.split(",", 3);
      $wnd.console.log(data);
      switch(data.length) {
      // break is intentionally left out so the cases will fall through
      case 3: obj.debug = data[2];
      case 2: obj.key = data[1];
      case 1: obj.remoteid = data[0];
      default: break;
      }
    }
    if (obj.key == null || obj.key.length == 0) {
      obj.key = @org.primordion.xholon.service.remotenode.PeerJS::PEERJS_DEMO_API_KEY;
    }
    if (obj.remoteid.length == 0) {
      obj.remoteid = null;
    }
    return obj;
  }-*/;
  
  @Override
  protected native void listen(Object listenParams, IXholon reffedNode) /*-{
    if (typeof $wnd.Peer === "undefined") {return;}
    var localid = listenParams.localid; //  A PeerJS ID, or null.
    var key = listenParams.key; // A PeerJS API key, or null.
    var debug = listenParams.debug; // A PeerJS debug level (0-3).
    if (localid == null) {
      var appName = "" + $wnd.xh.html.urlparam("app").replace(/\+/g," ").substring(0,18);
      localid = appName + "_" + new Date().getTime() + "_" + reffedNode.id();
    }
    this.println(localid);
    this.println(key);
    this.println(debug);
    this.peer = new $wnd.Peer(localid, {key: key, debug: debug});
    var myself = this;
    this.peer.on('connection', function(connection) {
      if (connection.label != "file") { // Peerjs Chat also tries to set up a "file" connection
        myself.connexn = connection; // cache the connection object so I can send messages on it
        connection.on('data', function(data) {
          myself.@org.primordion.xholon.service.remotenode.PeerJS::rxRemote(Ljava/lang/Object;Lorg/primordion/xholon/base/IXholon;)(data, reffedNode);
        });
      }
    });
  }-*/;
  
  @Override
  protected native void connect(Object connectParams, IXholon reffingNode) /*-{
    if (typeof $wnd.Peer === "undefined") {return;}
    var remoteid = connectParams.remoteid; // A valid PeerJS ID.
    var key = connectParams.key; // A PeerJS API key, or null.
    var debug = connectParams.debug; // A PeerJS debug level (0-3).
    var appName = "" + $wnd.xh.html.urlparam("app").replace(/\+/g," ").substring(0,18);
    var localid = appName + "_" + new Date().getTime() + "_" + reffingNode.id();
    this.println("localid " + localid);
    this.println("remoteid " + remoteid);
    this.println(key);
    this.println(debug);
    this.peer = new $wnd.Peer(localid, {key: key, debug: debug});
    this.connexn = this.peer.connect(remoteid);
    var myself = this;
    this.connexn.on('data', function(data) {
      myself.@org.primordion.xholon.service.remotenode.PeerJS::rxRemote(Ljava/lang/Object;Lorg/primordion/xholon/base/IXholon;)(data, reffingNode);
    });
  }-*/;
    
  /**
   * Closes the data connection gracefully, cleaning up underlying DataChannels and PeerConnections.
   */
  @Override
  protected native void closeConnection() /*-{
    if (typeof this.connexn === "undefined") {return;}
    this.connexn.close();
  }-*/;
  
  /**
   * Transmit/Send a message to a remote app using a PeerJS connection.
   * proxy.msg(103, "Proxy 1", xh.root().first());
   * {"signal":101,"data":"One two three"}
   * JSON.stringify(data) will escape any embedded " quotes to \", and will put " around the string .
   * @param signal A Xholon IMessage signal.
   * @param data A Xholon IMessage data.
   */
  @Override
  protected native boolean txRemote(int signal, Object data) /*-{
    if (typeof this.connexn === "undefined") {return false;}
    var jsonStr = '{"signal":' + signal + ',"data":' + $wnd.JSON.stringify(data) + '}';
    this.connexn.send(jsonStr);
    return true;
  }-*/;
  
  /**
   * Transmit/Send a message to a remote app using a PeerJS connection.
   * Typically this will be an XML string, that's a serialization of a IXholon node or subtree.
   * @param data A Xholon IMessage data.
   */
  @Override
  protected native boolean txRemote(Object data) /*-{
    if (typeof this.connexn === "undefined") {return false;}
    this.connexn.send(data);
    return true;
  }-*/;
  
  // actions
  private static final String showPeer = "Show Peer";
	private static final String showConnection = "Show Connection";
	private static final String showThisNode = "Show This Node";
	
	/** action list */
	private String[] actions = {showPeer, showConnection, showThisNode};
	
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
		if (showPeer.equals(action)) {
		  showPeer();
		}
		if (showConnection.equals(action)) {
			showConnection();
		}
		if (showThisNode.equals(action)) {
		  this.println("onDataJsonSync   " + this.isOnDataJsonSync());
		  this.println("onDataTextSync   " + this.isOnDataTextSync());
		  this.println("onDataTextAction " + this.isOnDataTextAction());
		}
	}
	
	/**
	 * Show information about the PeerJS Peer.
	 */
	protected native void showPeer() /*-{
	  if (typeof this.peer === "undefined") {
	    this.println("The peer is undefined.");
	    return;
	  }
	  this.println("A peer object exists.");
	  this.println(" id " + this.peer.id);
	  this.println(" connections " + this.peer.connections);
	  this.println(" disconnected " + this.peer.disconnected);
	  this.println(" destroyed " + this.peer.destroyed);
	}-*/;
	
	/**
	 * Show information about the current WebRTC connection.
	 */
	protected native void showConnection() /*-{
	  if (typeof this.connexn === "undefined") {
	    this.println("The connection is undefined.");
	    return;
	  }
	  this.println("A connection object exists.");
	  this.println(" label " + this.connexn.label);
	  this.println(" peer  " + this.connexn.peer);
	  this.println(" serialization " + this.connexn.serialization);
	  this.println(" type  " + this.connexn.type);
	  this.println(" bufferSize " + this.connexn.bufferSize);
	}-*/;
	
}

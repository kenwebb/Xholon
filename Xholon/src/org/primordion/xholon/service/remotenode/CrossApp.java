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
  public void processReceivedMessage(IMessage msg) {
    int signal = msg.getSignal();
    switch (signal) {
    
    /*
    signal bit options for XML:
    initial 1-bit filler so it's in the ISignal.SIGNAL_MIN_USER range  1
    send as JSON, or as plain text  1 0
    remove the node from the CSH, or leave it intact  1 0
    append, prepend, before, after on receiving end  000 001 010 011
      also option to pass the message/XML on to the user node  100
    
    example signals for XML:
    signal = 1 0b00001  send as plain text, and leave the node intact, and append (the default)
    signal = 3 0b00011  send as JSON, and leave the node intact, and append
    signal = 5 0b00101  send as plain text, and remove the node from the CSH, and append
    signal = 7 0b00111  send as JSON, and remove the node from the CSH, and append
    signal = 9 0b01001  send as plain text, and leave the node intact, and prepend
    */
    default:
      // this message is from the local reffedNode
      // - if data is a IXholon node, then:
      //   serialize the node and its subtree as XML,
      //   optionally remove the node from the local CSH,
      //   send the XML to the remote location as plain text or JSON,
      //   where the remote location will append/prepend/before/appear the XML to the remote CSH
      
      // TODO just call processReceivedMessage(msg) on the remoteNode
      
      Object data = msg.getData();
      if (ClassHelper.isAssignableFrom(Xholon.class, data.getClass())) {
        String serStr = serialize((IXholon)data, formatName, efParams);
        int sendJson = signal & 2; // 0b10
        int removeNode = signal & 4;  // 0b100
        if (sendJson == 2) {
          if (sendRemote(signal, serStr) && removeNode == 4) {
            ((IXholon)data).removeChild(); // remove the node from the Xholon CSH
            if ((IXholon)data == msg.getSender()) {
              // close the connection with the remote end, if the removed node is the reffed/ing node
              this.closeConnection();
            }
          }
        }
        else {
          if (sendRemote(serStr) && removeNode == 4) {
            ((IXholon)data).removeChild(); // remove the node from the Xholon CSH
            if ((IXholon)data == msg.getSender()) {
              // close the connection with the remote end, if the removed node is the reffed/ing node
              this.closeConnection();
            }
          }
        }
      }
      else {
        if (sendRemote(signal, (String)data)) {}
      }
    }
  }
  
  @Override
  public IMessage processReceivedSyncMessage(IMessage msg) {
    consoleLog(msg.getVal_Object());
    switch (msg.getSignal()) {
    
    // listen for  a remote connection
    case SIG_LISTEN_REQ:
    {
      String localid = null;
      String remoteXPathExpr = null;
      if (msg.getData() != null) {
        String[] data = ((String)msg.getData()).split(",", 2);
        consoleLog(data);
        switch(data.length) {
        // break is intentionally left out so the cases will fall through
        case 2: remoteXPathExpr = data[1];
        case 1: localid = data[0];
        default: break;
        }
      }
      if (localid.length() == 0) {
        localid = null;
      }
      listen(localid, remoteXPathExpr, msg.getSender());
      return new Message(SIG_LISTEN_RESP, null, this, msg.getSender());
    }
    
    // initiate a remote connection
    case SIG_CONNECT_REQ:
    {
      boolean useIframe = true;
      String remoteUrl = "";
      String remoteid = "";
      if (msg.getData() != null) {
        String[] data = ((String)msg.getData()).split(",", 3);
        consoleLog(data);
        switch(data.length) {
        // break is intentionally left out so the cases will fall through
        case 3: useIframe = Boolean.parseBoolean(data[2]);
        case 2: remoteUrl = data[1];
        case 1: remoteid = data[0];
        default: break;
        }
      }
      if (remoteid.length() == 0) {
        remoteid = null;
      }
      else if (remoteUrl.length() == 0) {
        remoteUrl = null;
      }
      connect(remoteid, remoteUrl, useIframe, msg.getSender());
      return new Message(SIG_CONNECT_RESP, null, this, msg.getSender());
    }
    
    case SIG_ON_DATA_JSON_SYNC_REQ:
      setOnDataJsonSync((Boolean)msg.getData());
      return new Message(SIG_ON_DATA_RESP, null, this, msg.getSender());
      
    case SIG_ON_DATA_TEXT_SYNC_REQ:
      setOnDataTextSync((Boolean)msg.getData());
      return new Message(SIG_ON_DATA_RESP, null, this, msg.getSender());
    
    case SIG_ON_DATA_TEXT_ACTION_REQ:
      setOnDataTextAction((Boolean)msg.getData());
      return new Message(SIG_ON_DATA_RESP, null, this, msg.getSender());
    
    default:
    {
      return super.processReceivedSyncMessage(msg);
    }
    
    } // end switch
  } // end processReceivedSyncMessage()
  
  /**
   * Listen for remote apps that want to reference a node within this app.
   * @param localid A local ID, or null.
   * @param remoteXPathExpr XPath expression relative to other window's xh.root() .
   * @param reffedNode The node in this app that can be reffed by nodes in other apps.
   */
  protected native void listen(String localid, String remoteXPathExpr, IXholon reffedNode) /*-{
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
      this.remoteNode = otherWindow.xh.root().xpath(remoteXPathExpr); //first().first();
    }
    else {
      this.remoteNode = otherWindow.xh.root();
    }
    if (this.remoteNode) {
      $wnd.console.log(this.remoteNode.name());
      $wnd.console.log("let the other window/app know that this window/app is ready");
      // let the other window/app know that this window/app is ready
      this.remoteNode.msg(201, null, reffedNode);
    }
  }-*/;
  
  /**
   * Connect to a remote node that's listening for connection requests.
   * @param remoteid A remote ID (ex: "Jake").
   * @param remoteUrl A partial URL
       (ex: "?app=The%20Love%20Letter%20-%20Jake%20-%20CrossApp&src=lstr&gui=clsc").
   * @param useIframe Whether to use an Iframe or a new window/tab
   * @param reffingNode The node in this app that is trying to reference nodes in other apps.
   */
  protected native void connect(String remoteid, String remoteUrl, boolean useIframe, IXholon reffingNode) /*-{
    var appName = "" + $wnd.xh.html.urlparam("app").replace(/\+/g," ").substring(0,18);
    var localid = appName + "_" + new Date().getTime() + "_" + reffingNode.id();
    this.println("localid " + localid);
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
      //otherWindow = this.createIframe(url).contentWindow;
      otherWindow = this.@org.primordion.xholon.service.remotenode.CrossApp::createIframe(Ljava/lang/String;)(url).contentWindow;
    }
    else {
      // Google Chrome will invoke its popup blocker to prevent this; user must enable popups for this domain
      otherWindow = $wnd.open(url, remoteid);
    }
    $wnd.console.log(otherWindow);
  }-*/;
  
  /**
   * Create a new iframe.
   * @param url 
   * @return A Window/Iframe object.
   */
  protected native Object createIframe(String url) /*-{
    var iframe = $doc.createElement('iframe');
    iframe.src = url;
    iframe.width = "100%"; // 900
    iframe.height = 500;
    $doc.getElementsByTagName('body')[0].appendChild(iframe);
    return iframe;
  }-*/;
  
  /**
   * Close the data connection gracefully.
   * TODO is this required or useful ?
   */
  protected native void closeConnection() /*-{
    
  }-*/;
  
  /**
   * Handle connection.on('data', function(data) .
   * TODO NOT REQUIRED ? because end nodes will communicate directly
   * @param data The data received from a remote source.
   *   This can be a JSON string, an XML string, or a plain text string.
   *   If it's JSON, then it's probably from a remote IXholon node.
   *   If it's plain text, then this may be a human-generated command for an Avatar
   * @param node The IXholon node that this CrossApp instance sends to, using call() or msg() .
   * @param myself This instance of CrossApp.
   */
  protected native void onData(String data, IXholon node, IXholon myself) /*-{
    if (data.substring(0,1) == "{") {
      var obj = $wnd.JSON.parse(data);
      if (obj) {
        var objSignal = obj.signal ? obj.signal : -9;
        var objData = obj.data ? obj.data : "";
        if (objData.substring(0,1) == "<") {
          // if objData is XML, then JSON.parse(data) will have converted \" back to "
          node.append(objData);
        }
        else if (this.onDataJsonSync) {
          node.call(objSignal, objData, myself);
        }
        else {
          node.msg(objSignal, objData, myself);
        }
      }
    }
    else if (data.substring(0,1) == "<") {
      node.append(data);
    }
    else {
      if (this.onDataTextAction) {
        node.action(data);
      }
      else if (this.onDataTextSync) {
        var respMsg = node.call(-9, data, myself);
        // TODO myself.connexn.send(respMsg.data);
      }
      else {
        node.msg(-9, data, myself);
      }
    }
  }-*/;
  
  /**
   * Send a message to a remote app.
   * TODO NOT REQUIRED ? because end nodes will communicate directly
   * proxy.msg(103, "Proxy 1", xh.root().first());
   * {"signal":101,"data":"One two three"}
   * JSON.stringify(data) will escape any embedded " quotes to \", and will put " around the string .
   * @param signal A Xholon IMessage signal.
   * @param data A Xholon IMessage data.
   */
  protected native boolean sendRemote(int signal, String data) /*-{
    //var jsonStr = '{"signal":' + signal + ',"data":' + $wnd.JSON.stringify(data) + '}';
    // this.connexn.send(jsonStr);
    $wnd.console.log("Jake's CrossApp is sending a message to Helen " + signal);
    $wnd.console.log(data);
    this.remoteNode.msg(signal, data, this);
    return true;
  }-*/;
  
  /**
   * Send a message to a remote app using a CrossApp connection.
   * TODO NOT REQUIRED ? because end nodes will communicate directly
   * Typically this will be an XML string, that's a serialization of a IXholon node or subtree.
   * @param data A Xholon IMessage data.
   */
  protected native boolean sendRemote(String data) /*-{
    // this.connexn.send(data);
    return true;
  }-*/;
  
  // actions
  private static final String showThisNode = "Show This Node";
	
	/** action list */
	private String[] actions = {showThisNode};
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getActionList()
	 */
	public String[] getActionList()
	{
		return actions;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setActionList(java.lang.String[])
	 */
	public void setActionList(String[] actionList)
	{
		actions = actionList;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#doAction(java.lang.String)
	 */
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
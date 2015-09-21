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
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.util.ClassHelper;

/**
 * Abstract base class for classes that provide access to remote nodes.
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on September 13, 2015)
 */
public abstract class AbstractRemoteNode extends XholonWithPorts implements IRemoteNode {
  
  public AbstractRemoteNode() {
    // port[0] will connect to the local node, for processing received data
    // port[1] will optionally connect to a remote node, where the 2 ends are colocated
    this.port = new IXholon[2];
  }
  
  protected String formatName = "Xml";
  protected String efParams = "{\"xhAttrStyle\":1,\"nameTemplate\":\"^^C^^^\",\"xhAttrReturnAll\":true,\"writeStartDocument\":false,\"writeXholonId\":false,\"writeXholonRoleName\":true,\"writePorts\":true,\"writeAnnotations\":true,\"shouldPrettyPrint\":true,\"writeAttributes\":true,\"writeStandardAttributes\":true,\"shouldWriteVal\":false,\"shouldWriteAllPorts\":false}";
  
  public String roleName = null;
  
  @Override
  public void setRoleName(String roleName) {this.roleName = roleName;}
  
  @Override
  public String getRoleName() {return roleName;}

  protected native void setOnDataJsonSync(boolean onDataJsonSync) /*-{
    this.onDataJsonSync = onDataJsonSync;
  }-*/;
  
  protected native boolean isOnDataJsonSync() /*-{
    return this.onDataJsonSync;
  }-*/;
  
  protected native void setOnDataTextSync(boolean onDataTextSync) /*-{
    this.onDataTextSync = onDataTextSync;
  }-*/;
  
  protected native boolean isOnDataTextSync() /*-{
    return this.onDataTextSync;
  }-*/;
  
  protected native void setOnDataTextAction(boolean onDataTextAction) /*-{
    this.onDataTextAction = onDataTextAction;
  }-*/;
  
  protected native boolean isOnDataTextAction() /*-{
    return this.onDataTextAction;
  }-*/;
  
  /**
   * Is the RemoteNode usable?
   * Is a required library available?
   * For example, PeerJS requires $wnd.Peer .
  */
  public abstract boolean isUsable();
  
  /**
   * Two end nodes are colocated if they can communicate directly using Xholon messaging.
   * For example, in an app that uses the CrossApp concrete class, nodes are colocated.
   * Probably for all other concrete classes, nodes are not colocated.
   */
  public boolean isColocated() {
    return false;
  }

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
      if (this.isColocated()) {
        txRemote(msg);
        return;
      }
      // this message is from the local reffedNode
      // - if data is a IXholon node, then:
      //   serialize the node and its subtree as XML,
      //   optionally remove the node from the local CSH,
      //   send the XML to the remote location as plain text or JSON,
      //   where the remote location will append/prepend/before/appear the XML to the remote CSH
      Object data = msg.getData();
      if (ClassHelper.isAssignableFrom(Xholon.class, data.getClass())) {
        String serStr = serialize((IXholon)data, formatName, efParams);
        int sendJson = signal & 2; // 0b10
        int removeNode = signal & 4;  // 0b100
        if (sendJson == 2) {
          if (txRemote(signal, serStr) && removeNode == 4) {
            ((IXholon)data).removeChild(); // remove the node from the Xholon CSH
            if ((IXholon)data == msg.getSender()) {
              // close the connection with the remote end, if the removed node is the reffed/ing node
              this.closeConnection();
            }
          }
        }
        else {
          if (txRemote(serStr) && removeNode == 4) {
            ((IXholon)data).removeChild(); // remove the node from the Xholon CSH
            if ((IXholon)data == msg.getSender()) {
              // close the connection with the remote end, if the removed node is the reffed/ing node
              this.closeConnection();
            }
          }
        }
      }
      else {
        if (txRemote(signal, (String)data)) {}
      }
      break; // end case
    } // end switch
  }
  
  /**
   * Catch nodes that are being appended to this instance of IRemoteNode.
   * Serialize the node, and send it to the remote end of the connection.
   * @param node 
   *   This could for example be an Avatar node travelling thru a port.
   */
  @Override
  public void setFirstChild(IXholon node) {
    String serStr = serialize(node, formatName, efParams);
    txRemote(serStr);
  }
  
  @Override
  public IMessage processReceivedSyncMessage(IMessage msg) {
    consoleLog(msg.getVal_Object());
    switch (msg.getSignal()) {
    
    // listen for a remote connection
    // msg.getData() must be a String in CSV format
    case SIG_LISTEN_REQ:
    {
      this.setPort(0, msg.getSender());
      Object listenParams = fixListenParams((String)msg.getData());
      listen(listenParams, msg.getSender());
      return new Message(SIG_LISTEN_RESP, null, this, msg.getSender());
    }
    
    // initiate a remote connection
    // msg.getData() must be a String in CSV format
    case SIG_CONNECT_REQ:
    {
      this.setPort(0, msg.getSender());
      Object connectParams = fixConnectParams((String)msg.getData());
      connect(connectParams, msg.getSender());
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
    
    }
  }
  
  /**
   * Fix listen parameters.
   * Add default values for missing parameters, and return them as a JavaScript Object.
   * @param listenParams Zero or more parameters in CSV format, or null.
   * @return Fixed parameters as a JavaScript Object.
   */
  protected abstract Object fixListenParams(String listenParams);
  
  protected abstract Object fixConnectParams(String connectParams);
  
  /**
   * Listen for remote apps that want to reference a specified node within this app.
   * @param listenParams Zero or more parameters in JavaScript Object or CSV format, or null.
   *   PeerJS uses: String localid, String key, int debug
   *   CrossApp uses: String localid (not required), String remoteXPathExpr
   * @param reffedNode The node in this app that can be reffed by nodes in other apps.
   */
  protected abstract void listen(Object listenParams, IXholon reffedNode);
  
  /**
   * Connect to a remote node that's listening for connection requests.
   * @param connectParams Zero or more parameters in JavaScript Object or CSV format, or null.
   *   PeerJS uses: String remoteid, String key, int debug
   *   CrossApp uses: String remoteid, String remoteUrl, boolean useIframe
   * @param reffingNode The node in this app that is trying to reference nodes in other apps.
   */
  protected abstract void connect(Object connectParams, IXholon reffingNode);
  
  protected boolean txRemote(int signal, Object data) {return false;}
  
  protected boolean txRemote(Object data) {return false;}
  
  protected boolean txRemote(IMessage msg) {return false;}
  
  /**
   * Receive data from a remote source.
   * Handle connection.on('data', function(data) in PeerJS.
   * @param remoteData The data received from a remote source.
   *   This can be a JSON string, an XML string, or a plain text string.
   *   If it's JSON, then it's probably from a remote IXholon node.
   *   If it's plain text, then this may be a human-generated command for an Avatar,
   *     such as from PeerjsChat.html
   * @param localNode The IXholon node that this IRemoteNode instance sends to, using call() or msg() .
   */
  protected native void rxRemote(Object remoteData, IXholon localNode) /*-{
    if (remoteData.substring(0,1) == "{") {
      var obj = $wnd.JSON.parse(remoteData);
      if (obj) {
        var objSignal = obj.signal ? obj.signal : -9;
        var objData = obj.data ? obj.data : "";
        if (objData.substring(0,1) == "<") {
          // if objData is XML, then JSON.parse(remoteData) will have converted \" back to "
          localNode.append(objData);
        }
        else if (this.onDataJsonSync) {
          localNode.call(objSignal, objData, this);
        }
        else {
          localNode.msg(objSignal, objData, this);
        }
      }
    }
    else if (remoteData.substring(0,1) == "<") {
      localNode.append(remoteData);
    }
    else {
      if (this.onDataTextAction) {
        localNode.action(remoteData);
      }
      else if (this.onDataTextSync) {
        var respMsg = localNode.call(-9, remoteData, this);
        this.@org.primordion.xholon.service.remotenode.AbstractRemoteNode::txRemote(Ljava/lang/Object;)(respMsg.data);
      }
      else {
        localNode.msg(-9, remoteData, this);
      }
    }
  }-*/;
  
  /**
   * Close the data connection.
   */
  protected void closeConnection() {}
  
  /**
   * Serialize a IXholon node as XML, or as another format.
   * @param node The node and subtree that should be serialized.
   * @param formatName External format name (ex: "Xml").
   * @param efParams External format parameters.
   * @return A serialization of the node.
   */
  protected native String serialize(IXholon node, String formatName, String efParams) /*-{
    return $wnd.xh.xport(formatName, node, efParams, false, true);
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
  
  @Override
  public String toString() {
    String outStr = getName();
    if ((port != null) && (port.length > 0)) {
      outStr += " [";
      for (int i = 0; i < port.length; i++) {
        if (port[i] != null) {
          outStr += " port:" + port[i].getName();
        }
      }
      outStr += "]";
    }
    return outStr;
  }
}

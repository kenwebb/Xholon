/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2016 Ken Webb
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

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IObservable;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.Message;
import org.primordion.xholon.base.XholonMap;
import org.primordion.xholon.service.broadcast.IBroadcastService;

/**
 * Broadcast Service.
 * This service maintains a simple Map that can be used by any node to broadcast a message to zero or more other registered nodes.
 * The immediate need for this class, is to implement the Scratch "broadcast" and "broadcastandwait" blocks.
 *  - Scratch "broadcast" can use Xholon async sendMessage()
 *  - Scratch "broadcastandwait" can use sync sendSyncMessage()
 * 
 * Example (using Chrome Developer Tools):
 * <pre>
var hello = xh.root().first();
console.log(hello.name()); //"hello_2"
var bs = xh.service("BroadcastService");
console.log(bs.name()); //"broadcastService_29"
bs.call(-3898, "Test01", hello); // register to receive broadcasts

var world = xh.root().first().next();
bs.call(-3896, "Test01", world); // broadcast
 * </pre>
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on February 28, 2016)
 */
public class BroadcastService extends AbstractXholonService implements IBroadcastService {
  
  /**
   * The firstChild of BroadcastService is a XholonMap.
   */
  protected XholonMap map = null;
  
  @Override
  public IXholon getService(String serviceName)
  {
    if (serviceName.startsWith(getXhcName())) {
      map = (XholonMap)this.getFirstChild();
      return this;
    }
    else {
      return null;
    }
  }
  
  @Override
  public void processReceivedMessage(IMessage msg) {
    processReceivedCommon(msg);
  }
  
  @Override
  public IMessage processReceivedSyncMessage(IMessage msg) {
    Object data = processReceivedCommon(msg);
    return new Message(SIG_BROADCAST_RESP, data, this, msg.getSender());
  }
  
  /**
   * Process a received async or sync message.
   */
  protected Object processReceivedCommon(IMessage msg) {
    String data = (String)msg.getData();
    IObservable broadcaster = (IObservable)map.get(data);
    
    switch (msg.getSignal()) {
    case SIG_ADD_BROADCAST_RECEIVER_REQ:
      if (broadcaster == null) {
        broadcaster = (IObservable)createInstance("org.primordion.xholon.service.broadcast.Broadcaster");
        broadcaster.setId(getNextId());
        IXholonClass bcXholonClass = getApp().getClassNode("XholonServiceImpl");
        broadcaster.setXhc(bcXholonClass);
        broadcaster.setRoleName(data);
        broadcaster.appendChild(map); // append broadcaster as a child of map
      }
      broadcaster.addObserver(msg.getSender());
      break;
    case SIG_DELETE_BROADCAST_RECEIVER_REQ:
      if (broadcaster != null) {
        broadcaster.deleteObserver(msg.getSender());
      }
      break;
    case SIG_BROADCAST_REQ:
      if (broadcaster != null) {
        broadcaster.setVal(SENDMSG_TYPE_ASYNC);
        broadcaster.notifyObservers(data);
      }
      break;
    case SIG_BROADCAST_SYNC_REQ:
      if (broadcaster != null) {
        broadcaster.setVal(SENDMSG_TYPE_SYNC);
        broadcaster.notifyObservers(data);
      }
      break;
    case SIG_BROADCAST_SYSTEM_REQ:
      if (broadcaster != null) {
        broadcaster.setVal(SENDMSG_TYPE_SYSTEM);
        broadcaster.notifyObservers(data);
      }
      break;
    default:
      //super.processReceivedMessage(msg);
    }
    return null;
  }
  
}

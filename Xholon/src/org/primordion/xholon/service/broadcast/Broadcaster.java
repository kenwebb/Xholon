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

package org.primordion.xholon.service.broadcast;

import java.util.ArrayList;
import java.util.List;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IObservable;
//import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Message;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.util.ClassHelper;

/**
 * Broadcast messages to registered IXholon nodes.
 * This class is part of the BroadcastService.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on February 28, 2016)
 */
public class Broadcaster extends Xholon implements IObservable, IBroadcastService {
  
  protected String roleName = null;
  protected int sendMsgType = SENDMSG_TYPE_ASYNC;
  
  /** Broadcast receivers that register themselves with this Broadcaster, to receive messages. */
  protected List receivers = null;
	
  public Broadcaster() {
    receivers = new ArrayList();
  }
  
  @Override
  public String getRoleName() {
    return this.roleName;
  }
  
  @Override
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
  
  @Override
  // XholonMap requires this method
  public Object getVal_Object() {
    return this;
  }
  
  @Override
  public double getVal() {
    return sendMsgType;
  }
  
  @Override
  public void setVal(double val) {
    sendMsgType = (int)val;
  }
  
  @Override
	public void addObserver(IXholon r) {
		receivers.add(r);
	}
	
	@Override
	public int countObservers() {
	  return receivers.size();
	}
	
	@Override
	public void deleteObserver(IXholon r) {
	  receivers.remove(r);
	}
	
	@Override
	public void deleteObservers() {
	  receivers.clear();
	}
	
	@Override
	public void notifyObservers() {
	  // use the other notify method instead
	  // send message to all observers/receivers
	  for (int i = 0; i < receivers.size(); i++) {
			IXholon receiver = (IXholon)receivers.get(i);
			receiver.sendMessage(SIGNAL_BROADCAST, null, this);
		}
	}
	
	@Override
	public void notifyObservers(Object arg) {
	  // send message to all observers/receivers
	  for (int i = 0; i < receivers.size(); i++) {
			IXholon receiver = (IXholon)receivers.get(i);
			switch (sendMsgType) {
			case SENDMSG_TYPE_SYNC:
			  receiver.sendSyncMessage(SIGNAL_BROADCAST, arg, this);
			  break;
			case SENDMSG_TYPE_SYSTEM:
			  receiver.sendSystemMessage(SIGNAL_BROADCAST, arg, this);
			  break;
			case SENDMSG_TYPE_ASYNC:
			default:
			  receiver.sendMessage(SIGNAL_BROADCAST, arg, this);
			  break;
			}
		}
	}
	
	@Override
	public boolean hasChanged() {
	  return false;
	}
	
	@Override
	public Object getChangedData() {
	  return null;
	}
	
}

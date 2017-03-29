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

import org.primordion.xholon.base.ISignal;

/**
 * BroadcastService interface, including types of messages.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on February 28, 2016)
 */
public interface IBroadcastService {
  
  // REQUESTS
  // --------
  
  /**
   * Add a Xholon node to a list of broadcast receivers.
   */
  public static final int SIG_ADD_BROADCAST_RECEIVER_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 101; // -3898
  
  /**
   * Delete a Xholon node from a list of broadcast receivers.
   */
  public static final int SIG_DELETE_BROADCAST_RECEIVER_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 102; // -3897
  
  /**
   * Send a regular async message to each node in a list of broadcast receivers.
   */
  public static final int SIG_BROADCAST_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 103; // -3896
  
  /**
   * Send a sync message to each node in a list of broadcast receivers.
   */
  public static final int SIG_BROADCAST_SYNC_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 104; // -3895
  
  /**
   * Send a system message to each node in a list of broadcast receivers.
   */
  public static final int SIG_BROADCAST_SYSTEM_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 105; // -3894
  
  /**
   * Send a regular async message to each child of each node in a list of broadcast receivers, but NOT to the broadcast receiver itself.
   */
  public static final int SIG_BROADCAST_TO_CHILDREN_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 106; // -3893
  
  /**
   * Send a sync message to each child of each node in a list of broadcast receivers, but NOT to the broadcast receiver itself.
   */
  public static final int SIG_BROADCAST_TO_CHILDREN_SYNC_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 107; // -3892
  
  /**
   * Send a system message to each child of each node in a list of broadcast receivers, but NOT to the broadcast receiver itself.
   */
  public static final int SIG_BROADCAST_TO_CHILDREN_SYSTEM_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 108; // -3891
  
  // RESPONSES
  // ---------
  
  /**
   * A generic response to a request to do something.
   */
  public static final int SIG_BROADCAST_RESP = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 202; // -3797
  
  // MESSAGES TO RECEIVERS
  
  /**
   * Default message.
   * Perhaps this should be in ISignal.
   */
  public static final int SIGNAL_BROADCAST = -99;
  
  // 
  public static final int SENDMSG_TYPE_ASYNC  = 1; // sendMessage()  the default
  public static final int SENDMSG_TYPE_SYNC   = 2; // sendSyncMessage()
  public static final int SENDMSG_TYPE_SYSTEM = 3; // sendSystemMessage()
  
}

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

import org.primordion.xholon.base.ISignal;

/**
 * An interface for RemoteNode classes.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on September 2, 2015)
 */
public interface IRemoteNode {
  
  // listen for a remote connection
  public static final int SIG_LISTEN_REQ  = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 101; // -3898
  public static final int SIG_LISTEN_RESP = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 201; // -3798
  
  // initiate a remote connection
  public static final int SIG_CONNECT_REQ  = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 102; // -3897
  public static final int SIG_CONNECT_RESP = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 202; // -3797
  
  // set onDataJsonSync onDataTextSync onDataTextAction
  public static final int SIG_ON_DATA_JSON_SYNC_REQ  = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 103; // -3896
  public static final int SIG_ON_DATA_TEXT_SYNC_REQ  = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 104; // -3895
  public static final int SIG_ON_DATA_TEXT_ACTION_REQ  = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 105; // -3894
  public static final int SIG_ON_DATA_RESP = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 203; // -3796
  
}

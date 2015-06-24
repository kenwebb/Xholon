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

package org.primordion.xholon.service.meteor;

import org.primordion.xholon.base.ISignal;

/**
 * Contants for use with the Meteor Platform Service.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on June 22, 2015)
 */
public interface IMeteorPlatformService {
	
	public static final int SIG_EXISTS_METEOR_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 101; // -3898
  public static final int SIG_SHOULD_WRITE_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 103; // -3896
  public static final int SIG_SHOULD_READ_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 104; // -3895
  
  /**
   * A request to insert something in a local Meteor collection (database).
   */
  public static final int SIG_COLL_INSERT_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 102; // -3897
  
  public static final int SIG_COLL_FETCH_ALLITEMS_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 105; // -3894
  public static final int SIG_COLL_FETCH_ITEM_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 106; // -3893
  public static final int SIG_COLL_REMOVE_ALLITEMS_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 107; // -3892
  public static final int SIG_COLL_REMOVE_ITEM_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 108; // -3891
  public static final int SIG_COLL_LENGTH_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 109; // -3890
  public static final int SIG_COLL_UPDATE_ITEM_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 110; // -3889
  public static final int SIG_COLL_FETCH_ALLSESSIONITEMS_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 111; // -3888
  public static final int SIG_COLL_REMOVE_ALLSESSIONITEMS_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 112; // -3887
  
  /**
   * A generic response to a request to do something.
   */
  public static final int SIG_METEOR_RESP = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 202; // -3797
	
}

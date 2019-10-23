/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2019 Ken Webb
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

package org.primordion.xholon.mech.behavrtree;

import org.primordion.xholon.base.Xholon;

/**
 * Behavior Tree - Failure.
 * tick() method unconditionally returns BT_STATUS_FAILURE .
 * To block all children of a SequenceBT:
 *   seqNode.prepend("<FailureBT/>");
 * Can drag in to a SequenceBT:
 *   <FailureBT patch="prepend"/>
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on October 22, 2019)
 */
public class Failure extends Xholon implements IBehaviorTree {
  private static final long serialVersionUID = 0L;
  
  @Override
  public Object tick(Object obj) {
    return BT_STATUS_FAILURE;
  };
  
}

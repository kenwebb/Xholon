/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2017 Ken Webb
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

package org.primordion.xholon.base;

import org.primordion.xholon.util.MiscRandom;

/**
 * An OrNode can have multiple children, but at a given point in time,
 * only one of these children can be active.
 * An OrNodeRandom changes it's onlyChild randomly each time step.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on March 8, 2017)
 */
public class OrNodeRandom extends OrNode implements IOrNode {
  
  // indexes into ranActMethod array, and the shouldUpdateOnlyChild array
  protected static final int RAN_PREACT_IX  = 0;
  protected static final int RAN_ACT_IX     = 1;
  protected static final int RAN_POSTACT_IX = 2;
  
  protected boolean[] ranActMethod = {false, false, false};
  
  /**
   * Prevent updating the only child the first time through
   */
  protected boolean[] shouldUpdateOnlyChild = {false, false, false};
  
  /**
   * Update the onlyChild to current onlyChild.getNextSibling().
   * Any sequence of preAct() act() postAct() within the same timeStep should only change the onlyChild once.
   */
  protected void updateOnlyChild(int ranActMethodIx) {
    if (shouldUpdateOnlyChild[ranActMethodIx] == false) {
      shouldUpdateOnlyChild[ranActMethodIx] = true;
      return;
    }
    switch (ranActMethodIx) {
    case RAN_PREACT_IX: // 0 preAct()
      ranActMethod[RAN_PREACT_IX] = true;
      ranActMethod[RAN_ACT_IX] = true; // set this to true in case act() method is not run
      break;
    case RAN_ACT_IX: // 1 act()
      if (ranActMethod[RAN_PREACT_IX] == true) {
        ranActMethod[RAN_PREACT_IX] = false;
        ranActMethod[RAN_ACT_IX] = true;
        return;
      }
      break;
    case RAN_POSTACT_IX: // 2 postAct()
      if (ranActMethod[RAN_ACT_IX] == true) {
        ranActMethod[RAN_ACT_IX] = false;
        return;
      }
      break;
    default:
      break;
    }
    int numChildren = this.getNumChildren(false);
    if (numChildren == 0) {return;}
    int onlyChildIx = MiscRandom.getRandomInt(0, numChildren);
    this.onlyChild = this.getFirstChild();
    while (onlyChild != null) {
      if (--onlyChildIx < 0) {
        break;
      }
      onlyChild = onlyChild.getNextSibling();
    }
  }
  
}

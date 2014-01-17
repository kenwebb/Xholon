/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2013 Ken Webb
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

package org.primordion.xholon.util;

import com.google.gwt.user.client.Random;
import java.util.Collections;
import java.util.List;

/**
 * This Xholon class implements the shuffle(java.util.List<?>) method which is missing from the
 * GWT implementation of java.util.Collections .
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on August 25, 2013)
 */
public class XholonCollections {
  
  /*public static void swap(List<?> list, int idx1, int idx2) {
    Object o1 = list.get(idx1);
    list.set(idx1, list.get(idx2));
    list.set(idx2, o1);
  }*/
  
  /**
   * Randomly permutes the specified list using a default source of randomness.
   * All permutations occur with approximately equal likelihood.
   * This implementation traverses the list backwards, from the last element up to the second,
   * repeatedly swapping a randomly selected element into the "current position".
   * Elements are randomly selected from the portion of the list that runs from the first
   * element to the current position, inclusive.
   * This method runs in linear time.
   * @param list - the list to be shuffled.
   * The performance will degrade if the list does not implement RandomAccess.
   * ArrayList and Vector do implement RandomAccess.
   */
  public static void shuffle(List<?> list) {
    for(int i = list.size(); i > 1; i--) {
      Collections.swap(list, i - 1, Random.nextInt(i));
    }
  }
  
}

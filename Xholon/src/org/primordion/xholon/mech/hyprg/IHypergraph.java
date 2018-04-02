/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2018 Ken Webb
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

package org.primordion.xholon.mech.hyprg;

import org.primordion.xholon.base.ISignal;

/**
 * An interface that contains msg signal types, and other constants of use to Hypergraph classes such as Hyperedge.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on April 2, 2018)
 */
public interface IHypergraph {
  
  // signals
  // There are two types of signals:
  // 1. system signals that should be processed by the Hypergraph entity
  // 2. user signals that should be passed through to receivers  SIGNAL_MIN_USER to SIGNAL_MAX_USER
  
  // must be >= 1
  // ISignal public static final int SIGNAL_MIN_USER = 1;
  // ISignal public static final int SIGNAL_MAX_USER = Integer.MAX_VALUE;
  
  /**
   * Test
   * ex: hypr.msg(101, "Testing un deux trois", this);
   */
  public static final int SIG_TEST = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 101; // -3898
  
  /**
   * Optional Xholon node that should be the content of the hyperedge.
   * The node can be a single node, a forest, or the root of a subtree.
   * ex: hypr.msg(101, contentNode, this);
   */
  public static final int SIG_CONTENT = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 102; // -3897
  
  /**
   * Response to a sync msg.
   */
  public static final int SIG_RESPONSE = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 201; // -3798
  
}

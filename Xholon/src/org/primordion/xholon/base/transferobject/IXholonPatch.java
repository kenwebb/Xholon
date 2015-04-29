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

package org.primordion.xholon.base.transferobject;

import org.primordion.xholon.base.IXholon;

/**
 * A standard format for patching Xholon apps at runtime.
 * This is based on RFC 5261.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="https://tools.ietf.org/html/rfc5261">RFC 5261 XML Patch spec</a>
 * @since 0.9.1 (Created on April 24, 2015)
 */
public interface IXholonPatch extends IXholon {
  
  // Patch Operations (op)
  public static final String PATCHOP_ADD     = "add";
  public static final String PATCHOP_MOVE    = "move"; // not part of RFC 5261
  public static final String PATCHOP_REPLACE = "replace";
  public static final String PATCHOP_REMOVE  = "remove";
  
  // Positioning of new data content (pos)
  public static final String POS_APPEND  = "append"; // default (not explicitly used ?)
  public static final String POS_PREPEND = "prepend";
  public static final String POS_BEFORE  = "before";
  public static final String POS_AFTER   = "after";
  
}

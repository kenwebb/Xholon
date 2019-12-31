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

package org.primordion.xholon.base;

/**
 * The nodes in a Xholon app are identified using multiple numbering sequences.
 * This may be required when generating an SQL representation of the app.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on December 27, 2019)
 */
public interface INumbering {
  public static final String NUMBERING_APP   = "a"; // app Application
  public static final String NUMBERING_BASE  = "b"; // base node Xholon (ex: XholonMap)
  public static final String NUMBERING_CNTRL = "c"; // control Control
  public static final String NUMBERING_MECH  = "m"; // mech Mechanism
  public static final String NUMBERING_NODE  = "n"; // node Xholon
  public static final String NUMBERING_SRVC  = "s"; // service
  public static final String NUMBERING_TYPE  = "t"; // type XholonClass
  public static final String NUMBERING_VIEW  = "v"; // view
  
  public static final String NUMBERING_APPSPECIFIC = "A"; // appspecific can use uppercase letters starting with "A"
  //public static final String NUMBERING_ = ""; // 

  public static final String NUMBERING_DEFAULT = NUMBERING_NODE; // default
}

/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2012 Ken Webb
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
 * Relationship types for use with Xholon.
 * A relationship is a reference from one IXholon node to another IXholon node.
 * These types can be used with Neo4j, Blueprints, Jena, Spring, etc.
 * Neo4j can't use them directly, but instead requires a copy that implements RelationshipType.
 * Blueprints can use the enum values as a name (ex: PORT.name()).
 * TODO Jena and Spring should use these.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on December 28, 2012)
 */
public enum XhRelTypes {
  
  /**
   * parentNode PARENT_NODE
   * <p>An IXholon is part of a parent IXholon.</p>
   */
  PARENT_NODE,
  
  /**
   * firstChild FIRST_CHILD
   * <p>An IXholon contains a first child IXholon.</p>
   */
  FIRST_CHILD,
  
  /**
   * nextSibling NEXT_SIBLING
   * <p>An IXholon has a next sibling IXholon.</p>
   */
  NEXT_SIBLING,
  
  /**
   * xhc XHC (reference to an IXholon node's IXholonClass)
   * <p>An IXholon is a type of IXholonClass.</p>
   */
  XHC,
  
  /**
   * port PORT (any other reference from one IXholon node to another)
   * <p>An IXholon references an IXholon.</p>
   */
  PORT,
  
  /**
   * app APP (referenced from an IXholonClass)
   * This is just a runtime convenience relationship,
   * that allows IXholon nodes to locate the IApplication node.
   */
  //APP,
  
  /**
   * mech MECH (referenced from an IXholonClass)
   * <p>An IXholonClass belongs to an IMechanism.</p>
   */
  MECH;
  
  /**
   * Return the relationship type as a camel-case String. Examples:
   * <p>PORT port</p>
   * <p>PARENT_NODE parentNode</p>
   * @return
   */
  public String toCamelCase() {
	  String cc = null;
	  switch (this) {
	  case PARENT_NODE: cc = "parentNode"; break;
	  case FIRST_CHILD: cc = "firstChild"; break;
	  case NEXT_SIBLING: cc = "nextSibling"; break;
	  case XHC: cc = "xhc"; break;
	  case PORT: cc = "port"; break;
	  case MECH: cc = "mech"; break;
	  default: break;
	  }
	  return cc;
  }
  
  // testing
  public static void main(String[] args) {
	  System.out.println(XhRelTypes.XHC);
	  XhRelTypes[] vals = XhRelTypes.values();
	  for (int i = 0; i < vals.length; i++) {
		  System.out.println(vals[i].ordinal() + " " + vals[i].name() + " " + vals[i].toString());
		  if ("NEXT_SIBLING".equals(vals[i].name())) {
			  // "The name uniquely identifies a relationship type"
			  System.out.println("nextSibling");
		  }
	  }
  }
  
}

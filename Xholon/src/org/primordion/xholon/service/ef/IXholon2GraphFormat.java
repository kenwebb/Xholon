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

package org.primordion.xholon.service.ef;

import org.primordion.xholon.base.IXholon;

/**
 * Export a Xholon model in an external format (ef)
 * that supports graphs but not necessarily tree hierarchies.
 * The ef typically has separate sets of node and edge tags.
 * Node tags may have node attributes.
 * Edge tags may have edge attributes.
 * <p>ex: GraphML GraphViz GML Xgmml Pajek</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on January 18, 2013)
 */
public interface IXholon2GraphFormat extends IXholon2ExternalFormat {
  
  /**
   * Write an IXholon node or subtree as nodes in the external format.
   * @param xhNode An IXholon node.
   */
  public abstract void writeNode(IXholon xhNode);
  
  /**
   * Write edges and edge attributes in the external format.
   * @param xhNode An IXholon node.
   */
  public abstract void writeEdges(IXholon xhNode);
  
  /**
   * Write node attributes in the external format.
   * @param xhNode An IXholon node.
   */
  public abstract void writeNodeAttributes(IXholon xhNode);
  
  /**
   * Write edge attributes in the external format.
   * @param xhNode An IXholon node.
   */
  //public abstract void writeEdgeAttributes(IXholon xhNode);
  
}

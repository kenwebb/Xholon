/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2014 Ken Webb
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

package org.primordion.xholon.script;

import org.primordion.xholon.base.IXholon;

/**
 * Print the contents of a Xholon tree or subtree, in a specified order.
 * It calls the existing methods in Xholon.java.
 * The default order is "pre-order".
 * Paste one of the following as the last child of the subtree root.
 * <pre>&lt;TreeTraversal/></pre>
 * <pre>&lt;TreeTraversal order="pre-order"/></pre>
 * <pre>&lt;TreeTraversal order="in-order"/></pre>
 * <pre>&lt;TreeTraversal order="post-order"/></pre>
 * <pre>&lt;TreeTraversal order="level-order"/></pre>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://en.wikipedia.org/wiki/Tree_traversal">wikipedia Tree traversal</a>
 * @since 0.9.1 (Created on March 14, 2014)
 */
public class TreeTraversal extends XholonScript {
  
  private String order = "pre-order";
  
  @Override
  public void postConfigure() {
    IXholon node = this.getParentNode();
    node.println(order + " tree traversal for node " + node.getName());
    if ("pre-order".equals(order)) {
      node.preOrderPrint(0);
    }
    else if ("in-order".equals(order)) {
      node.inOrderPrint(0);
    }
    else if ("post-order".equals(order)) {
      node.postOrderPrint(0);
    }
    else if ("level-order".equals(order)) {
      //node.levelOrderPrint(0);
      node.print("level-order tree traversal not yet implemented");
    }
    else {
      node.print("unknown tree traversal order: " + order + "\n"
        + "valid tree traversal orders are: pre-order in-order post-order");
    }
    node.print("\n\n");
    if (nextSibling != null) {
      nextSibling.postConfigure();
    }
    removeChild();
  }
  
  @Override
  public int setAttributeVal(String attrName, String attrVal) {
    if ("order".equals(attrName)) {
      order = attrVal;
    }
    return 0;
  }
  
}

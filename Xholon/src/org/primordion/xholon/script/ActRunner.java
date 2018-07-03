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

package org.primordion.xholon.script;

import org.primordion.xholon.base.IXholon;

/**
 * Store a collection of IXholon nodes in an internal structure, and invoke their act() methods each timestep.
 * This class is primarily intended to handle collections of behavior nodes, such as those created in XholonWorkbooks.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on July 2, 2018)
 */
public class ActRunner extends XholonScript {
  
  protected Object nodeArr = null;
  
  @Override
  public Object getVal_Object() {
    return nodeArr;
  }
  
  @Override
  public void setVal_Object(Object nodeArr) {
    this.nodeArr = nodeArr;
  }
  
  @Override
  public void postConfigure() {
    if (nodeArr == null) {
      nodeArr = this.makeNodeArr();
    }
    super.postConfigure();
  }
  
  @Override
  public void act() {
    // check for child nodes, and add them to the nodeArr
    IXholon newNode = this.getFirstChild();
    while (newNode != null) {
      IXholon nextNewNode = newNode.getNextSibling();
      newNode.removeChild();
      newNode.setNextSibling(null);
      putNode(nodeArr, newNode);
      newNode = nextNewNode;
    }
    // invoke act() on each node in nodeArr
    for (int i = 0; i < getNodeArrLen(nodeArr); i++) {
      IXholon node = getNode(nodeArr, i);
      node.act();
    }
    super.act();
  }
  
  protected native Object makeNodeArr() /*-{
    return [];
  }-*/;
  
  protected native int getNodeArrLen(Object nodeArr) /*-{
    return nodeArr.length;
  }-*/;
  
  protected native IXholon getNode(Object nodeArr, int ix) /*-{
    return nodeArr[ix];
  }-*/;
  
  protected native void putNode(Object nodeArr, IXholon node) /*-{
    nodeArr.push(node);
  }-*/;
  
}

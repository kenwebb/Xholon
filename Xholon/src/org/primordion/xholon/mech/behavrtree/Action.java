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

import com.google.gwt.core.client.JavaScriptObject;

import org.primordion.xholon.base.Behavior_gwtjs;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;

/**
 * Behavior Tree - Action.
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on October 15, 2019)
 */
public class Action extends Behavior_gwtjs implements IBehaviorTree {
  private static final long serialVersionUID = 0L;
  
  /**
   * The agent is also called an "ai entity". It's the owner of the Behavior Tree, the initial parent of RootBT node.
   * An Action operates directly on the agent, and does not know that it's a node in a Behavior Tree.
   */
  protected IXholon agent = null;
  
  @Override
  public void postConfigure() {
    // find and store agent node
    IXholon node = this;
    while (node != null) {
      if (BT_ROOT_NODE_XHCNAME.equals(node.getXhcName())) {
        this.agent = node.getParentNode();
        break;
      }
      node = node.getParentNode();
    }
    super.postConfigure();
  }
  
  @Override
  protected void configureJsoExtras(JavaScriptObject bobj) {
    this.configureJsoAgent(bobj, this.agent);
  }
  
  /**
   * Configure the JavaScriptObject, so it has access to the agent.
   */
	protected native void configureJsoAgent(JavaScriptObject bobj, IXholon agent) /*-{
    if (bobj != null) {
      bobj["agent"] = agent;
    }
    else {
      $wnd.console.log("ActionBT bobj is null");
    }
	}-*/;
	
  @Override
  public int setAttributeVal(String attrName, String attrVal) {
    return super.setAttributeVal(attrName, attrVal);
  }
  
  @Override
  public String toString() {
    String outStr = super.toString();
    outStr += " agent: " + (agent == null ? null : agent.getName());
    return outStr;
  }
  
  @Override
  public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {
    xmlWriter.writeAttribute("agent", (agent == null ? null : agent.getName()));
    super.toXmlAttributes(xholon2xml, xmlWriter);
  }
  
}

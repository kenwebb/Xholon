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

package org.primordion.xholon.script;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IXholon;

/**
 * Wander around a Xholon tree or subtree.
 * Paste one of the following as the last child of the subtree root:
 * <pre>&lt;TreeWanderer/></pre>
 * <pre>&lt;TreeWanderer roleName="Wanderer"/></pre>
 * <pre>&lt;TreeWanderer prob="0.20,0.24,0.26,0.30"/></pre>
 * <pre>&lt;TreeWanderer prob="0.0,0.0,1.0,0.0" roleName="Wanderer"/></pre>
 * <pre>&lt;TreeWanderer prob="0.0,0.0,1.0,0.0" roleName="OOO" exclude="Avatar"/></pre>
 * <pre>&lt;TreeWanderer prob="0.2,0.2,0.2,0.2" roleName="XXX" exclude="Avatar,TreeWanderer"/></pre>
 * Probabilities of: (the 4 raw probabilities must add up to 1.0)
 * [0] moving to parent (can never move higher than the wanderer's root)
 * [1] moving to first child
 * [2] moving to next sibling
 * [3] moving to previous sibling
 * 
 * TreeWanderer does nothing but wander.
 * To make it do something useful, you can add one or more behavior nodes, such as the following:
<TreeWandererbehavior implName="org.primordion.xholon.base.Behavior_gwtjs"><![CDATA[
var me, mename, beh = {
postConfigure: function() {
  me = this.cnode.parent();
  mename = me.role().substring(0,4);
  $wnd.xh.root().append(this.cnode.remove());
},

act: function() {
  me.println(mename + " is visiting " + me.parent().name("R^^^^^") + ".");
}
}
]]></TreeWandererbehavior>
 *
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on February 20, 2015)
 */
public class TreeWanderer extends XholonScript {
  
  protected String probStr = "0.25,0.25,0.25,0.25"; // default value
  protected double[] prob = {0.25, 0.5, 0.75, 1.0}; // default value converted to summation form
  
  protected String excludeStr = null; // "Avatar,TreeWanderer";
  protected String[] exclude = null; // {"Avatar"};
  
  protected IXholon wroot = null;
  protected IXholon wcontextNode = null;
  
  public String roleName = null;
  
  /**
   * If this avatar moves within the Xholon tree, it might be executed multiple times in the same timestep.
   * This variable helps to prevent that.
   */
  protected int actTimeStep = -1;
  
  protected IApplication app = null;
  
  public void setRoleName(String roleName) {this.roleName = roleName;}
	public String getRoleName() {return roleName;}
	
  @Override
  public void postConfigure() {
    //consoleLog("TreeWanderer postConfigure() start");
    wcontextNode = this.getParentNode();
    wroot = wcontextNode;
    app = this.getApp();
    initProb();
    initExclude();
    super.postConfigure();
    //consoleLog(" TreeWanderer postConfigure() end");
  }
  
  @Override
  public void act() {
    //consoleLog("TreeWanderer act() start");
    // check to see if this node has already been called this timestep
    int ts = app.getTimeStep();
    //consoleLog(" " + actTimeStep + " " + ts);
    
    if (actTimeStep < ts) { // parent
      //consoleLog(" actTimeStep < ts");
      actTimeStep = ts;
      double r = Math.random();
      //consoleLog(" " + r);
      if (r < prob[0]) {
        //consoleLog(" 0");
        if (wcontextNode != wroot) {
          //consoleLog("0 " + ts + " " + wcontextNode.getParentNode());
          this.removeChild();
          this.appendChild(wcontextNode.getParentNode());
          wcontextNode = this.getParentNode();
        }
      }
      else if (r < prob[1]) { // first
        //consoleLog(" 1");
        IXholon newParent = wcontextNode.getFirstChild();
        if (newParent == this) {
          newParent = this.getNextSibling();
        }
        if ((newParent != null) && (!exclude(newParent))) {
          //consoleLog("1 " + ts + " " + newParent);
          this.removeChild();
          this.appendChild(newParent);
          wcontextNode = this.getParentNode();
        }
      }
      else if (r < prob[2]) { // next
        //consoleLog(" 2");
        IXholon newParent = wcontextNode.getNextSibling();
        // the wanderer must remain within the scope of wroot; wroot must always be an ancestor node
        if ((newParent != null) && (newParent.hasAncestor(wroot.getName())) && (!exclude(newParent))) {
          //consoleLog("2 " + ts + " " + newParent);
          this.removeChild();
          this.appendChild(newParent);
          wcontextNode = this.getParentNode();
        }
      }
      else if (r < prob[3]) { // prev
        //consoleLog(" 3");
        IXholon newParent = wcontextNode.getPreviousSibling();
        // the wanderer must remain within the scope of wroot; wroot must always be an ancestor node
        if ((newParent != null) && (newParent.hasAncestor(wroot.getName())) && (!exclude(newParent))) {
          //consoleLog("3 " + ts + " " + newParent);
          this.removeChild();
          this.appendChild(newParent);
          wcontextNode = this.getParentNode();
        }
      }
      else {
        // else stay at same place
        //consoleLog("4 " + ts);
      }
    }
    
    super.act();
    //consoleLog(" TreeWanderer act() end");
  }
  
  @Override
  public int setAttributeVal(String attrName, String attrVal) {
    if ("prob".equals(attrName)) {
      probStr = attrVal;
    }
    else if ("exclude".equals(attrName)) {
      excludeStr = attrVal;
    }
    return 0;
  }
  
  /**
   * Initialize probabilities.
   */
  protected void initProb() {
    //consoleLog(probStr);
    if (probStr == null) {return;}
    String[] pa = probStr.split(",");
    //consoleLog(pa);
    double sum = 0.0;
    for (int i = 0; i < pa.length; i++) {
      sum += Double.parseDouble(pa[i]);
      prob[i] = sum;
      //consoleLog(prob[i]);
    }
  }
  
  /**
   * Whether or not a newParent node is on the exclusion list.
   * @param newParent A potential new parent for the wanderer.
   */
  protected boolean exclude(IXholon newParent) {
    if (exclude == null) {return false;}
    for (int i = 0; i < exclude.length; i++) {
      if (newParent.getXhc().hasAncestor(exclude[i])) {
        //consoleLog("excluding " + newParent.getName());
        return true;
      }
    }
    return false;
  }
  
  /**
   * Initialize exclusion list.
   */
  protected void initExclude() {
    //consoleLog(excludeStr);
    if (excludeStr == null) {return;}
    exclude = excludeStr.split(",");
    //consoleLog(exclude);
  }
  
}

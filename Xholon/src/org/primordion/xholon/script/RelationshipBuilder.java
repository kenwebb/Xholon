/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2016 Ken Webb
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

//import org.primordion.xholon.base.IXholon;

/**
 * This is a sample Xholon script, written in normal compiled Java.
 * It's located in the common default package for Java scripts.
 * 
 * Example:
 * <pre>
<Organizations>
<RelationshipBuilder types="Person,Organization" sep=","><![CDATA[
Andrea,works,NavCan
Luke,school,Churchill
]]></RelationshipBuilder>
</Organizations>
 * </pre>
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on December 22, 2016)
 */
public class RelationshipBuilder extends XholonScript {
  
  protected String types = null;
  
  protected String sep = " ";
  
  private String val = null;
  
  @Override
  public String getVal_String() {
    return val;
  }
  
  @Override
  public void setVal(String val) {
    this.val = val;
  }
  
  @Override
  public Object getVal_Object() {
    return val;
  }
  
  @Override
  public void setVal(Object val) {
    this.val = val.toString();
  }
  
  public void postConfigure() {
    this.build(types, sep);
    super.postConfigure();
    this.removeChild();
  }
  
  protected native void build(String types, String sep) /*-{
    var me = this;
    var meParent = me.parent();
    me.text = me.text();
    if (!me.text) {return;}
    me.println(me.text);
    var typesArr = types.split(",");
    if (typesArr.length == 1) {
      typesArr.push(typesArr[0]);
    }
    var sep = sep || " ";
    
    var nodeNames = [];
    var arr = me.text.split("\n");
    for (var i = 0; i < arr.length; i++) {
      var itemArr = arr[i].split(sep);
      var txtNodeA = itemArr[0].trim();
      var txtRel = itemArr[1].trim();
      var txtNodeB = itemArr[2].trim();
      // if nodeA and nodeB have different types, then assume that nodeA has already been created
      if ((typesArr[0] == typesArr[1]) && (nodeNames.indexOf(txtNodeA) === -1)) {
        nodeNames.push(txtNodeA);
        meParent.append("<" + typesArr[0] + ' roleName="' + txtNodeA + '"/>');
      }
      if (nodeNames.indexOf(txtNodeB) === -1) {
        nodeNames.push(txtNodeB);
        meParent.append("<" + typesArr[1] + ' roleName="' + txtNodeB + '"/>');
      }
      var nodeA = null;
      if (typesArr[0] == typesArr[1]) {
        nodeA = meParent.xpath(typesArr[0] + "[@roleName='" + txtNodeA + "']");
      }
      else {
        nodeA = $wnd.xh.root().xpath("descendant::" + typesArr[0] + "[@roleName='" + txtNodeA + "']");
      }
      var nodeB = meParent.xpath(typesArr[1] + "[@roleName='" + txtNodeB + "']");
      if (nodeA && nodeB) {
        if (!nodeA[txtRel]) {
          nodeA[txtRel] = [];
        }
        nodeA[txtRel].push(nodeB);
      }
    }
    // to confirm the links:
    //$wnd.console.log($wnd.xh.root().xpath("descendant::Person[@roleName='Ken']").links());
    //$wnd.console.log($wnd.xh.root().xpath("descendant::Person[@roleName='Carolyn']").links());
  }-*/;
  
  @Override
  public int setAttributeVal(String attrName, String attrVal) {
    if ("types".equals(attrName)) {
      this.types = attrVal;
    }
    else if ("sep".equals(attrName)) {
      this.sep = attrVal;
    }
    else if ("val".equals(attrName)) {
      this.val = attrVal;
    }
    return 0;
  }
  
}

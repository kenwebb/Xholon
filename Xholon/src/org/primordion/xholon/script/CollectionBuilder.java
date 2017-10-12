/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2017 Ken Webb
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

/**
 * This is a sample Xholon script, written in normal compiled Java.
 * It's located in the common default package for Java scripts.
 * 
 * Example:
 * <pre>
<CollectionBuilder roleName="Test" collName="Identifiers" eleName="IdentifierSE" sep=",">One,Two,Three</CollectionBuilder>
 * </pre>
 * 
 * TODO
 * - 
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on October 11, 2017)
 */
public class CollectionBuilder extends XholonScript {
  
  protected String sep = ","; // ","  " "  "\n"
  
  protected String collName = "XholonSet"; // "XholonList" "XholonSet" "Identifiers"
  
  protected String eleName = "Attribute_String"; // "Attribute_String" "IdentifierSE"
  
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
    this.build(collName, eleName, sep);
    super.postConfigure();
    this.removeChild();
  }
  
  /**
   * Build a collection.
   */
  protected native void build(String collName, String eleName, String sep) /*-{
    var me = this;
    var meParent = me.parent();
    me.text = me.text();
    if (!me.text) {return;}
    //me.println(me.text);
    var sep = sep || ",";
    var roleNameStr = "";
    if (this.role()) {
      roleNameStr += ' roleName="' + this.role() + '"';
    }
    var xmlStr = '<' + collName + roleNameStr + '>\n';
    var arr = me.text.split(sep);
    for (var i = 0; i < arr.length; i++) {
      var str = arr[i].trim();
      xmlStr += "  <" + eleName + ">" + str + "</" + eleName + ">\n";
    }
    xmlStr += '</' + collName + '>\n';
    //me.println(xmlStr);
    meParent.append(xmlStr);
  }-*/;
  
  @Override
  public int setAttributeVal(String attrName, String attrVal) {
    if ("collName".equals(attrName)) {
      this.collName = attrVal;
    }
    else if ("eleName".equals(attrName)) {
      this.eleName = attrVal;
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

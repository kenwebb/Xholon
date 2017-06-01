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

package org.primordion.xholon.mech.catt;

import java.util.List;
import java.util.ArrayList;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;

/**
 * Category Theory - AQL
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="">AQL website</a>
 * @since 0.9.1 (Created on June 1, 2017)
 */
public class CatAql extends XholonWithPorts {
  
  private String roleName = null;
  
  @Override
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
  
  @Override
  public String getRoleName() {
    return roleName;
  }
  
  /**
    * The AQL content will be in this variable.
    */
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
  public void setVal_String(String val) {
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
  
  @Override
  public void setVal_Object(Object val) {
    this.val = val.toString();
  }
  
  @Override
  public void postConfigure() {
    this.println(val);
    parseAqlContent(val.trim());
    super.postConfigure();
  }
  
  @Override
  public void act() {
    // is there anything to do?
    super.act();
  }
  
  protected void parseAqlContent(String aqlContent) {
    // typeside
    int posStart = aqlContent.indexOf("typeside", 0);
    if (posStart == -1) {return;}
    int posEnd = aqlContent.indexOf("}", posStart);
    if (posEnd == -1) {return;}
    parseTypeside(aqlContent.substring(posStart,posEnd));
    
    // schema
    posStart = aqlContent.indexOf("schema", posEnd);
    if (posStart == -1) {return;}
    posEnd = aqlContent.indexOf("}", posStart);
    if (posEnd == -1) {return;}
    parseSchema(aqlContent.substring(posStart,posEnd));
    
    // instance
    posStart = aqlContent.indexOf("instance", posEnd);
    if (posStart == -1) {return;}
    posEnd = aqlContent.indexOf("}", posStart);
    if (posEnd == -1) {return;}
    parseInstance(aqlContent.substring(posStart,posEnd));
  }
  
  protected void parseTypeside(String typeside) {
    this.println("parseTypeside:\n" + typeside + "\n");
  }
  
  protected void parseSchema(String schema) {
    this.println("parseSchema:\n" + schema + "\n");
  }
  
  protected void parseInstance(String instance) {
    this.println("parseInstance:\n" + instance + "\n");
  }
  
  @Override
  public int setAttributeVal(String attrName, String attrVal) {
    if ("val".equals(attrName)) {
      // this is the AQL content
      this.val = attrVal;
    }
    return 0;
  }
  
  @Override
  public void toXml(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {
    if (this.getXhc().hasAncestor("CatAql")) {
      // write start element
      xmlWriter.writeStartElement(this.getXhcName());
      xholon2xml.writeSpecial(this);
      // write children
      IXholon childNode = getFirstChild();
      while (childNode != null) {
        childNode.toXml(xholon2xml, xmlWriter);
        childNode = childNode.getNextSibling();
      }
      // write end element
      xmlWriter.writeEndElement(this.getXhcName());
    }
    else {
      super.toXml(xholon2xml, xmlWriter);
    }
  }
  
  @Override
  public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {}
  
}

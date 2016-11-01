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

package org.primordion.xholon.mech.spreadsheet;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;


/**
 * SpreadsheetCell
 * Has a value.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="https://github.com/handsontable/formula-parser">handsontable JS library website</a>
 * @since 0.9.1 (Created on Oct 14, 2016)
 */
public class SpreadsheetCell extends XholonWithPorts {
  
  /**
   * column "A" to "ZZ" + Row.roleName  (ex: A1)
   */
  private String roleName = null;
  
  @Override
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
  
  @Override
  public String getRoleName() {
    return roleName;
  }
  
  @Override
  public void setVal(String sval) {
    this.value = sval.trim();
    //this.println(this.value + " S " + this.value.getClass().getName());
  }
  
  @Override
  public void setVal(int ival) {
    this.value = new Integer(ival);
    //this.println(this.value + " i " + this.value.getClass().getName());
  }
  
  @Override
  public void setVal(double dval) {
    this.value = new Double(dval);
    //this.println(this.value + " d " + this.value.getClass().getName());
  }
  
  @Override
  public void setVal(float fval) {
    this.value = new Double(fval);
    //this.println(this.value + " f " + this.value.getClass().getName());
  }
  
  @Override
  public void setVal(boolean bval) {
    this.value = new Boolean(bval);
    //this.println(this.value + " b " + this.value.getClass().getName());
  }
  
  /**
   * The current value of this cell (ex: 43 "Hello World" "12345" true 123.456)
   */
  protected Object value = ""; //null;
  public Object getValue() {return this.value;}
  
  @Override
  public Object getVal_Object() {return this.value;}
  
  @Override
  // this is intended for use by SpreadsheetFormula
  public void setVal(Object obj) {this.value = obj;}
  
  @Override
  // this is intended for use by SpreadsheetFormula
  public void setVal_Object(Object obj) {this.value = obj;}
  
  @Override
  public void postConfigure() {
    if (this.roleName == null) {
      this.roleName = calcStandardRoleName();
    }
    super.postConfigure();
  }
  
  /**
   * Calculate the standard/expected roleName (the name of this cell).
   * @return the String value of an Ascii char.
   */
  protected String calcStandardRoleName() {
    //int colNameAscii = (int)'A' + (this.getId() - this.getParentNode().getFirstChild().getId());
    //return String.valueOf((char)colNameAscii);
    
    int colNameAscii = (int)'A';
    IXholon node = this.getParentNode().getFirstChild();
    while ((node != null) && (node != this)) {
      colNameAscii++;
      node = node.getNextSibling();
    }
    return String.valueOf((char)colNameAscii);
  }
  
  @Override
  public void toXml(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {
    xmlWriter.writeStartElement(this.getXhcName());
    if ((this.roleName != null) && (!calcStandardRoleName().equals(this.roleName))) {
      // this is a user-specified roleName
      xmlWriter.writeAttribute("roleName", this.roleName);
    }
    xmlWriter.writeText(this.value.toString());
    // write children
    IXholon childNode = getFirstChild();
    while (childNode != null) {
      childNode.toXml(xholon2xml, xmlWriter);
      childNode = childNode.getNextSibling();
    }
    xmlWriter.writeEndElement(this.getXhcName());
  }
  
  @Override
  public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {}

  @Override
  public String toString() {
    return this.getName() + " " + this.value;
  }
  
}

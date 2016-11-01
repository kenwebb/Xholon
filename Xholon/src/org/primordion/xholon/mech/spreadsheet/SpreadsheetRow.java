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
 * SpreadsheetRow
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on Oct 14, 2016)
 */
public class SpreadsheetRow extends XholonWithPorts {
  
  /**
   * "1" to "999"
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
  public void postConfigure() {
    if (this.roleName == null) {
      this.roleName = calcStandardRoleName();
    }
    super.postConfigure();
  }
  
  /**
   * Calculate the standard/expected roleName (the name of this row).
   * @return the String value of an int >= 1.
   */
  protected String calcStandardRoleName() {
    int rowNameInt = 1;
    IXholon node = this.getParentNode().getFirstChild();
    while ((node != null) && (node != this)) {
      rowNameInt++;
      node = node.getNextSibling();
    }
    return String.valueOf(rowNameInt);
  }
  
  @Override
  public void toXml(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {
    xmlWriter.writeStartElement(this.getXhcName());
    if ((this.roleName != null) && (!calcStandardRoleName().equals(this.roleName))) {
      // this is a user-specified roleName
      xmlWriter.writeAttribute("roleName", this.roleName);
    }
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
    return this.getName();
  }
  
}

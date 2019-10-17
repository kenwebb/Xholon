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

import java.util.ArrayList;
import java.util.List;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;

/**
 * Behavior Tree - Parallel.
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on October 15, 2019)
 */
public class Parallel extends Xholon implements IBehaviorTree {
  private static final long serialVersionUID = 0L;
  
  //private String state = BT_STATUS_NOT_TICKED; // do I need this ?
  
  // thresholds - these should be specified as XML attributes
  private int thrshsucc = 0; // threshold for Success
  private int thrshfail = 0; // threshold for Failure
  
  // actual numbers; these are counted by tick()
  private int numsucc = 0;
  private int numfail = 0;
  
  private String roleName = null;

  @Override
  public void setRoleName(String roleName) {this.roleName = roleName;}
  
  @Override
  public String getRoleName() {return roleName;}
  
  @Override
  public void postConfigure() {
    super.postConfigure();
  }
  
  @Override
  public Object tick(Object obj) {
    numsucc = 0;
    numfail = 0;
    IXholon child = this.getFirstChild();
    while (child != null) {
      String state = (String)child.tick(null);
      switch (state) {
      case BT_STATUS_SUCCESS: numsucc++; break;
      case BT_STATUS_FAILURE: numfail++; break;
      default: break;
      }
      child = child.getNextSibling();
    }
    if (numsucc > thrshsucc) {
      return BT_STATUS_SUCCESS;
    }
    else if (numfail > thrshfail) {
      return BT_STATUS_FAILURE;
    }
    else {
      return BT_STATUS_RUNNING;
    }
  };
  
  @Override
  public int setAttributeVal(String attrName, String attrVal) {
    if (attrName.equals("thrshsucc")) {
      this.thrshsucc = Integer.parseInt(attrVal);
    }
    else if (attrName.equals("thrshfail")) {
      this.thrshfail = Integer.parseInt(attrVal);
    }
    return 0;
  }
  
  @Override
  public String toString() {
    String outStr = getName();
    return outStr;
  }
  
  @Override
  public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {
    //if (connector != null) {xmlWriter.writeAttribute("connector", connector);}
    //xmlWriter.writeAttribute("weight", Double.toString(getVal()));
  }
  
}

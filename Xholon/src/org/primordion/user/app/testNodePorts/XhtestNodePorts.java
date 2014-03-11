package org.primordion.user.app.testNodePorts;

import java.util.ArrayList;
import java.util.List;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.base.XholonWithPorts;

/**
 * Test of org.primordion.xholon.script.port.java
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on December 3, 2012)
*/
@SuppressWarnings("serial")
public class XhtestNodePorts extends XholonWithPorts implements CetestNodePorts {
  
  public String roleName = null;
  
  private IXholon three = null;
  private IXholon[] ff = new IXholon[2];
  
  // Constructor
  public XhtestNodePorts() {super();}
  
  /*
   * @see org.primordion.xholon.base.IXholon#setRoleName(java.lang.String)
   */
  public void setRoleName(String roleName) {this.roleName = roleName;}
  
  /*
   * @see org.primordion.xholon.base.IXholon#getRoleName()
   */
  public String getRoleName() {return roleName;}
  
  public void act()
  {
    switch(xhc.getId()) {
    case TheSystemCE:
      processMessageQ();
      break;
    default:
      break;
    }
    super.act();
  }
  
  /*
   * @see org.primordion.xholon.base.IXholon#getAllPorts(org.primordion.xholon.base.IXholon)
   */
  public List getAllPorts() {
    List list = new ArrayList();
    // three
    if (three != null) {
      list.add(new PortInformation("three", three));
    }
    // ff[2]
    if (ff != null) {
      for (int i = 0; i < ff.length; i++) {
        if (ff[i] != null) {
          list.add(new PortInformation("ff", i, ff[i], null));
        }
      }
    }
    // port[?]
    if (port != null) {
      for (int i = 0; i < port.length; i++) {
        if (port[i] != null) {
          list.add(new PortInformation("port", i, port[i], null));
        }
      }
    }
    return list;
  }
  
  /*
   * @see org.primordion.xholon.base.Xholon#toString()
   */
  public String toString() {
    String outStr = getName();
    // three
    if (three != null) {
      outStr += " three:" + three.getName();
    }
    // ff[2]
    if (ff != null) {
      for (int i = 0; i < ff.length; i++) {
        if (ff[i] != null) {
          outStr += " ff[" + i + "]:" + ff[i].getName();
        }
      }
    }
    // port[?]
    if (port != null) {
      for (int i = 0; i < port.length; i++) {
        if (port[i] != null) {
          outStr += " port[" + i + "]:" + port[i].getName();
        }
      }
    }
    return outStr;
  }

  public IXholon getThree() {
    return three;
  }

  public void setThree(IXholon three) {
    this.three = three;
  }

  public IXholon getFf(int index) {
    return ff[index];
  }

  public void setFf(int index, IXholon reffedNode) {
    ff[index] = reffedNode;
  }
  
}

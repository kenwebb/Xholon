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

package org.primordion.xholon.mech.hyprg;

//import java.util.List;
//import java.util.ArrayList;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Message;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.base.XholonWithPorts;
//import org.primordion.xholon.io.xml.IXholon2Xml;
//import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.service.XholonHelperService;
import org.primordion.xholon.util.ClassHelper;

/**
 * Hyperedge
 * 
 * possible subclasses:
 * Hyperedge11  bind one source node to one target node (any of the standard ways of doing Xholon ports)
 * Hyperedgemn  bind m source nodes to n target nodes, where m and n >= 1 (as in Cell model with multiple substrates, products, etc.)
 * HyperedgePO  share access to a single passive object (or container?) (as in Spivak cables)
 * - contained nodes might be AO or PO
 * - OR use msg signals to specify details of the Hyperedge
 * 
 * see my notebook March 31, 2018
 * see XholonWorkbook 9e9aa6d9283efb51abb1983f52fa6d55 Hypergraphs
 *  
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon"> Xholon Project website</a>
 * @see <a href="https://www.ncbi.nlm.nih.gov/pmc/articles/PMC2673028/pdf/pcbi.1000385.pdf"> Hypergraphs and Cellular Networks, Steffen Klamt1, Utz-Uwe Haus2, Fabian Theis</a>
 * @since 0.9.1 (Created on April 2, 2018)
 */
public class Hyperedge extends XholonWithPorts implements IHypergraph {
  
  private String roleName = null;
  
  /**
   * The optional content node can be a single node, a forest, or the root of a subtree.
   * TODO Or the content should just be the children of Hyperedge.
   */
  //private IXholon content = null;
  
  private IXholon[] innies = null;
  
  private IXholon[] outies = null;
  
  private IXholon xholonHelperService = null;
  
  @Override
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
  
  @Override
  public String getRoleName() {
    return roleName;
  }
  
  @Override
  public void processReceivedMessage(IMessage msg)
  {
    switch (msg.getSignal()) {
    case SIG_TEST:
      this.consoleLog("Hyperedge has received msg: " + msg.getData());
      break;
    case SIG_CONTENT:
      content(msg);
      break;
    default:
      int sig = msg.getSignal();
      if ((sig >= ISignal.SIGNAL_MIN_USER) && (sig <= ISignal.SIGNAL_MAX_USER) && (this.port != null)) {
        this.consoleLog("Hyperedge has received user msg: " + msg.getData());
        if (this.getPort(0) != null) {
          this.consoleLog("Hyperedge is forwarding user msg to: " + this.getPort(0).getName());
          this.getPort(0).processReceivedMessage(msg);
        }
      }
      else {
        super.processReceivedMessage(msg);
      }
    }
    
  }
  
  @Override
  public IMessage processReceivedSyncMessage(IMessage msg)
  {
    Object respData = null;
    switch (msg.getSignal()) {
    case SIG_TEST:
      respData = "Hyperedge has received sync msg: " + msg.getData();
      break;
    case SIG_CONTENT:
      content(msg);
      respData = "Hyperedge has received content";
      break;
    default:
      int sig = msg.getSignal();
      if ((sig >= ISignal.SIGNAL_MIN_USER) && (sig <= ISignal.SIGNAL_MAX_USER) && (this.port != null)) {
        if (this.getPort(0) != null) {
          return this.getPort(0).processReceivedSyncMessage(msg);
        }
      }
      else {
        return super.processReceivedSyncMessage(msg);
      }
    }
    return new Message(SIG_RESPONSE, respData, this, msg.getSender());
  }
  
  /**
   * set content
   */
  protected void content(IMessage msg) {
    Object msgdata = msg.getData();
    if (msgdata == null) {
      // TODO remove children? or set this.content to null?
      return;
    }
    Class msgdataclass = msgdata.getClass();
    if (ClassHelper.isAssignableFrom(Xholon.class, msgdataclass)) {
      IXholon content = (IXholon)msg.getData();
      // this.@org.primordion.xholon.base.IXholon::removeChild()();
      // content.@org.primordion.xholon.base.IXholon::appendChild(Lorg/primordion/xholon/base/IXholon;)(this);
      content.removeChild();
      content.appendChild(this);
    }
    else if (ClassHelper.isAssignableFrom(String.class, msgdataclass)) {
      // this should be an XML string
      // $wnd.xh.service('XholonHelperService').call(-2013, content, this);
      if (xholonHelperService == null) {
        xholonHelperService = this.getService("XholonHelperService");
      }
      if (xholonHelperService != null) {
        ((XholonHelperService)xholonHelperService).pasteLastChild(this, (String)msgdata);
      }
    }
  }
  
  @Override
  public int setAttributeVal(String attrName, String attrVal) {
    if ("one".equals(attrName)) {
      //this.one = attrVal;
    }
    else if ("two".equals(attrName)) {
      //this.two = Boolean.parseBoolean(attrVal);
    }
    return 0;
  }
  
  @Override
  public String toString() {
    String outStr = null;
    outStr = this.getName();
    if (this.getFirstChild() != null) {
      outStr += " has content: true";
    }
    return outStr;
  }
  
}

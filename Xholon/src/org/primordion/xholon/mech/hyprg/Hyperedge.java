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

import java.util.List;
//import java.util.ArrayList;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.Message;
import org.primordion.xholon.base.PortInformation;
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
 * Note: Hyperedge only acts on its first child, which can be a AO or PO or Container. This is its "content".
 *   There may be additional children as well.
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
  
  /**
   * Incoming ports.
   */
  private IXholon[] innies = null;
  
  /**
   * Outgoing ports.
   */
  private IXholon[] outies = null;
  
  /**
   * Is this hyperedge a Tween Tree?
   * If it's a Tween Tree, then it's invisible to tree viewers. It's not in the place graph. It's only accessible using ports.
   * A Tween Tree is effectively part of the edge.
   * Possibly, as another alternative, the Hyperedge might be visible, but the encapsulated object would not be visible.
   * Possibly, instead of being a Tween Tree, it could be placed inside a HypergraphService.
   * So there may be more than a choice betweeen boolean true and false.
   */
  private boolean tweentree = false;
  
  /**
   * One way to specify whether ports are innies or outies.
   * stoichiometric matrix, where positive numbers signify a product and negative numbers signify a substrate
   * especially for use in biochemical networks
   * ex: "-1,1,1,-1"
   */
  private double[] stoich = null;
  
  /**
   * Whether or not to process incoming to outgoing ports during each act().
   * For this to be true, each port must have a corresponding stoich value.
   */
  private boolean shouldAct = false;
  
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
  public void postConfigure() {
    if (this.hasChildNodes()) {
      if ((this.getFirstChild().getXhType() & IXholonClass.XhtypePurePassiveObject) == IXholonClass.XhtypePurePassiveObject) {
        // the encapsulated node is a passive object (PO)
        // if this Hyperedge has any ports, then reverse all of those ports
        //  - create the same ports in the remote node, which is presumably a active object (AO)
        //   - these ports should ref this Hyperedge's encapsulated PO node
        //  - remove the ports from this Hyperedge
        this.reversePorts();
      }
    }
    if (this.tweentree) {
      //this.setParentNode(null);
      this.removeChild(); // disconnect this from its parent and siblings; ports will stay intact
    }
    if (this.stoich != null) {
      List portList = this.getLinks(false, true);
      if (portList.size() == stoich.length) {
        this.shouldAct = true;
      }
    }
    super.postConfigure();
  }
  
  @Override
  public void act() {
    if (this.shouldAct) {
      List portList = this.getLinks(false, true);
      this.println(this.getName() + " " + portList.size());
      if (portList.size() == stoich.length) { // confirm that these values are still equal
        for (int i = 0; i < portList.size(); i++) {
          PortInformation pi = (PortInformation)portList.get(i);
          IXholon rnode = pi.getReffedNode();
          rnode.incVal(stoich[i]);
        }
      }
    }
    super.act();
  }
  
  /**
   * Reverse the direction of all ports.
   */
  protected native void reversePorts() /*-{
    var links = this.links(false, true);
    for (var i = 0; i < links.length; i++) {
      var link = links[i];
      $wnd.console.log(link);
      if (link.reffedNode == null) {continue;}
      if (link.fieldName == "port") {
        $wnd.console.log(" this is a port port with index " + link.fieldNameIndex);
        link.reffedNode.port(link.fieldNameIndex, this.first());
        this.port(link.fieldNameIndex, null);
      }
      else {
        var ixstr = null;
        if (link.fieldNameIndex == -1) {
          // there is no index; this is not part of an array
          ixstr = " no index";
          link.reffedNode[link.fieldName] = this.first();
          delete this[link.fieldName];
        }
        else {
          // this is part of an array
          ixstr = " index " + link.fieldNameIndex;
          if (!link.reffedNode[link.fieldName]) {
            link.reffedNode[link.fieldName] = [];
          }
          link.reffedNode[link.fieldName][link.fieldNameIndex] = this.first();
          delete this[link.fieldName]; // it's OK to try to delete the array more than once
        }
        $wnd.console.log(" this port is a " + link.fieldName + ixstr);
      }
    }
  }-*/;
  
  @Override
  public void processReceivedMessage(IMessage msg)
  {
    switch (msg.getSignal()) {
    case SIG_TEST:
      this.consoleLog("Hyperedge has received test msg: " + msg.getData());
      break;
    case SIG_CONTENT:
      content(msg);
      break;
    default:
      int sig = msg.getSignal();
      if ((sig >= ISignal.SIGNAL_MIN_USER) && (sig <= ISignal.SIGNAL_MAX_USER) && (this.port != null)) {
        //this.consoleLog("Hyperedge has received user msg: " + msg.getData());
        if (this.getPort(0) != null) {
          //this.consoleLog("Hyperedge is forwarding user msg to: " + this.getPort(0).getName());
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
   * Set the optional encapsulated content of this Hyperedge.
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
      content.removeChild();
      content.appendChild(this);
    }
    else if (ClassHelper.isAssignableFrom(String.class, msgdataclass)) {
      // this should be an XML string
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
    if ("stoich".equals(attrName)) {
      String[] tempStr = attrVal.split(",");
      int len = tempStr.length;
      if (len > 0) {
        double[] tempD = new double[len];
        for (int i = 0; i < len; i++) {
          tempD[i] = Double.parseDouble(tempStr[i]);
        }
        this.stoich = tempD;
      }
    }
    else if ("tweentree".equals(attrName)) {
      this.tweentree = Boolean.parseBoolean(attrVal);
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
    if (this.stoich != null) {
      outStr += " stoich: " + stoich;
    }
    return outStr;
  }
  
}

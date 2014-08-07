/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2014 Ken Webb
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

package org.primordion.xholon.base;

/**
 * Avatar is based on the requirements for actors in Interactive Fiction (IF).
 * This basic avatar can be introduced as a node in any Xholon app.
 * It serves as your personal presence inside the app.
 * At any given time it's located within, or references, one specific node in the app.
 * Typically it's hidden from the other nodes in the app.
 * Typically it's invoked through a XholonConsole.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on August 6, 2014)
 */
public class Avatar extends XholonWithPorts {
  
  // Variables
  private String roleName = null;
  private double val;
  
  // the current location of the avatar; it's parent or the node it references
  private IXholon contextNode = null;
  
  private StringBuilder sb = null;
  
  public Avatar() {}
  
  // Setters and Getters
  public void setRoleName(String roleName) {this.roleName = roleName;}
  public String getRoleName() {return roleName;}
  
  public void setVal(double val) {this.val = val;}
  public double getVal() {return val;}
  
  public void incVal(double incAmount) {val += incAmount;}
  public void decVal(double decAmount) {val -= decAmount;}
  
  @Override
  public void postConfigure() {
    contextNode = this.getParentNode();
    super.postConfigure();
  }
  
  @Override
  public void processReceivedMessage(IMessage msg) {
    //this.consoleLog("Avatar received: ");
    //this.consoleLog(msg.getVal_Object());
    switch (msg.getSignal()) {
    case ISignal.SIGNAL_XHOLON_CONSOLE_REQ:
      String responseStr = processMsg(msg);
      if ((responseStr != null) && (responseStr.length() > 0)) {
        msg.getSender().sendMessage(ISignal.SIGNAL_XHOLON_CONSOLE_RSP, "\n" + responseStr, this);
      }
      break;
    default:
      break;
    }
  }
  
  /**
   * Do the detailed processing of a message.
   * @param msg 
   * @return 
   */
  protected String processMsg(IMessage msg) {
    String[] data = ((String)msg.getData()).trim().split(" ", 3);
    int len = data.length;
    //this.consoleLog(data[0]);
    sb = new StringBuilder();
    switch (data[0]) {
    case "appear":
      appear();
      break;
    case "drop":
      if (len > 1) {
        drop(data[1]);
      }
      break;
    case "enter":
      if (len > 1) {
        enter(data[1]);
      }
      break;
    case "x":
    case "examine":
      if (len > 1) {
        examine(data[1]);
      }
      break;
    case "exit":
      exit();
      break;
    case "go":
      if (len > 1) {
        go(data[1]);
      }
      break;
    case "help":
      help();
      break;
    case "i":
    case "inventory":
      inventory();
      break;
    case "look":
      look();
      break;
    case "search":
      if (len > 1) {
        search(data[1]);
      }
      break;
    case "take":
      if (len > 1) {
        take(data[1]);
      }
      break;
    case "vanish":
      vanish();
      break;
    default:
      sb.append("That's not a verb I recognise.");
      break;
    }
    return sb.toString();
  }
  
  /**
   * Become visible to the other nodes in the Xholon tree,
   * by appending this avatar to the contextNode.
   * If the avatar has a parent node (it's already a visible part of the tree), then do nothing.
   */
  protected void appear() {
    if (!this.hasParentNode()) {
      this.appendChild(contextNode);
    }
  }
  
  protected void drop(String thing) {
    sb.append("TODO drop ").append(thing);
  }
  
  protected void enter(String thing) {
    sb.append("TODO enter ").append(thing);
  }
  
  protected void examine(String thing) {
    sb.append("TODO examine ").append(thing);
  }
  
  protected void exit() {
    sb.append("exiting");
  }
  
  /**
   * Go to the node located in a specified direction.
   * @param arg The name or index of a port. ex: "north" "south" "east" "west" "0" "1"
   */
  protected void go(String portName) {
    //this.consoleLog("GO" + portName);
    if (portName == null) {return;}
    Object[] portArr = contextNode.getAllPorts().toArray();
    if ((portArr != null) && (portArr.length > 0)) {
      PortInformation pi = (PortInformation)portArr[0];
      String foundPortName = pi.getFieldName();
      if (portName.equals(foundPortName)) {
        IXholon reffedNode = pi.getReffedNode();
        sb.append("Going to ").append(reffedNode.getName());
        contextNode = reffedNode;
      }
      else {
        sb.append("Can only go ").append(foundPortName);
      }
    }
    else {
      sb.append("Can't go ").append(portName);
    }
  }
  
  protected void help() {
    sb.append("TODO help");
  }
  
  protected void inventory() {
    sb.append("You are carrying:");
    inventoryRecurse(this.getFirstChild(), " ");
  }
  
  protected void inventoryRecurse(IXholon node, String indent) {
    while (node != null) {
      sb.append("\n").append(indent).append("a ").append(node.getXhcName());
      inventoryRecurse(node.getFirstChild(), indent + " ");
      node = node.getNextSibling();
    }
  }
  
  protected void look() {
    sb.append("You are in a ").append(contextNode.getXhcName()).append(".");
    lookRecurse(contextNode.getFirstChild(), " ");
  }
  
  protected void lookRecurse(IXholon node, String indent) {
    while (node != null) {
      sb.append("\n").append(indent).append("You see a ").append(node.getXhcName());
      lookRecurse(node.getFirstChild(), indent + " ");
      node = node.getNextSibling();
    }
  }
  
  protected void search(String thing) {
    sb.append("TODO search ").append(thing);
  }
  
  protected void take(String thing) {
    sb.append("TODO take ").append(thing);
  }
  
  /**
   * Become invisible to other nodes in the Xholon tree.
   * The avatar still retains a link to it's parent in the tree, using contextNode.
   * Often it will be good to have an avatar vanish once a XholonConsole or equivalent attaches to it.
   * If the avatar does not have a parent node (it's not part of the tree and is already invisible),
   * then do nothing.
   */
  protected void vanish() {
    if (this.hasParentNode()) {
      this.removeChild();
    }
  }
  
}

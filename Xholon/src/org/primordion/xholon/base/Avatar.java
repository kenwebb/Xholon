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
 * Avatar is based on the requirements for actors in Interactive Fiction (IF), especially Inform 7.
 * This basic avatar can be inserted as a node in any Xholon app.
 * It serves as your personal presence inside the app.
 * At any given time it's located within, or references, one specific node in the app.
 * Typically it should be hidden from the other nodes in the app.
 * Typically it's invoked through a XholonConsole.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://en.wikipedia.org/wiki/Interactive_fiction">wikipedia Interactive fiction</a>
 * @see <a href="http://en.wikipedia.org/wiki/Inform">wikipedia Inform</a>
 * @since 0.9.1 (Created on August 6, 2014)
 */
public class Avatar extends XholonWithPorts {
  
  // Variables
  protected String roleName = null;
  protected double val;
  
  // the current location of the avatar; it's parent or the node it references
  protected IXholon contextNode = null;
  
  protected StringBuilder sb = null;
  
  // this is the name format required by findNode()
  protected String nameTemplate = IXholon.GETNAME_DEFAULT;
  
  protected static IXPath xpath = null;
  
  public Avatar() {}
  
  // Setters and Getters
  
  @Override
  public void setRoleName(String roleName) {this.roleName = roleName;}
  @Override
  public String getRoleName() {return roleName;}
  
  @Override
  public void setVal(double val) {this.val = val;}
  @Override
  public double getVal() {return val;}
  
  @Override
  public void incVal(double incAmount) {val += incAmount;}
  @Override
  public void decVal(double decAmount) {val -= decAmount;}
  
  @Override
  public void postConfigure() {
    contextNode = this.getParentNode();
    xpath = this.getXPath();
    super.postConfigure();
  }
  
  @Override
  public void processReceivedMessage(IMessage msg) {
    /*if (contextNode == null) {
      contextNode = this.getParentNode();
      xpath = this.getXPath();
    }*/
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
    sb = new StringBuilder();
    switch (data[0]) {
    case "appear":
      appear();
      break;
    case "drop":
      if (len > 1) {
        drop(data[1]);
      }
      else {
        dropAll();
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
      else {
        takeAll();
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
  
  /**
   * Move a specified thing from the avatar's inventory, to the current contextNode.
   * @param thing The Xholon name of the thing to drop.
   */
  protected void drop(String thing) {
    IXholon node = findNode(thing, this);
    if (node != null) {
      node.removeChild();
      node.appendChild(contextNode);
      sb.append("Dropped.");
    }
    else {
      sb.append("You're not carrying any such thing.");
    }
  }
  
  /**
   * Drop all things from the avatar's inventory, to the current contextNode.
   */
  protected void dropAll() {
    IXholon node = this.getFirstChild();
    while (node != null) {
      IXholon sib = node.getNextSibling();
      node.removeChild();
      node.appendChild(contextNode);
      node = sib;
    }
    sb.append("All dropped.");
  }
  
  /**
   * Move the avatar to the specified thing, which must be located inside the current contextNode.
   * @param thing The Xholon name of the thing to move to.
   */
  protected void enter(String thing) {
    IXholon node = findNode(thing, contextNode);
    if (node != null) {
      if (isContainerOrSupporter(node)) {
        if (this.hasParentNode()) {
          this.removeChild();
          this.appendChild(node);
        }
        contextNode = node;
        sb.append("Entered ").append(makeNodeName(node));
      }
    }
    else {
      sb.append("Can't enter ").append(thing);
    }
  }
  
  /**
   * Examine something that is either inside the context node, or is held by the avatar.
   * @param thing The Xholon name of the thing to examine.
   */
  protected void examine(String thing) {
    IXholon node = findNode(thing, contextNode);
    if (node != null) {
      examineNode(thing, node);
    }
    else {
      node = findNode(thing, this);
      if (node != null) {
        examineNode(thing, node);
      }
      else {
        sb.append("Can't find ").append(thing);
      }
    }
  }
  
  protected void examineNode(String thing, IXholon node) {
    if (node.hasAnnotation()) {
      sb.append(" ").append(node.getAnnotation());
    }
    else if (node.getXhc().hasAnnotation()) {
      sb.append(" ").append(node.getXhc().getAnnotation());
    }
    else {
      sb.append(" ").append(thing).append(" is a ").append(node.getXhcName());
    }
  }
  
  /**
   * Exit to the parent of the current contextNode.
   * If the contextNode has no parent, then do nothing.
   */
  protected void exit() {
    IXholon node = contextNode.getParentNode();
    if (node != null) {
      if (this.hasParentNode()) {
        this.removeChild();
        this.appendChild(node);
      }
      contextNode = node;
      sb.append("Exited to ").append(makeNodeName(node));
    }
    else {
      sb.append("Can't exit from ").append(makeNodeName(contextNode));
    }
  }
  
  /**
   * Move the avatar to the node located in a specified direction.
   * In practice, any valid Xholon port name can be used.
   * TODO handle more than the first port; handle port ports
   * @param arg The name or index of a Xholon port. ex: "north" "south" "east" "west" "0" "1"
   */
  protected void go(String portName) {
    if (portName == null) {return;}
    Object[] portArr = contextNode.getAllPorts().toArray();
    if ((portArr != null) && (portArr.length > 0)) {
      PortInformation pi = (PortInformation)portArr[0];
      String foundPortName = pi.getFieldName();
      if (portName.equals(foundPortName)) {
        IXholon reffedNode = pi.getReffedNode();
        sb.append("Going to ").append(makeNodeName(reffedNode));
        if (this.hasParentNode()) {
          this.removeChild();
          this.appendChild(reffedNode);
        }
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
  
  /**
   * Provide some minimal help to users.
   */
  protected void help() {
    sb
    .append("Basic commands:")
    .append("\nappear")
    .append("\ndrop [thing]")
    .append("\nenter thing")
    .append("\nexamine thing  (x thing)")
    .append("\nexit")
    .append("\ngo portName")
    .append("\nhelp")
    .append("\ninventory  (i)")
    .append("\nlook")
    .append("\nsearch thing")
    .append("\ntake [thing]")
    .append("\nvanish")
    ;
  }
  
  /**
   * List the items that the avatar is currently carrying.
   */
  protected void inventory() {
    sb.append("You are carrying:");
    inventoryRecurse(this.getFirstChild(), " ");
  }
  
  protected void inventoryRecurse(IXholon node, String indent) {
    while (node != null) {
      sb.append("\n").append(indent).append(makeNodeName(node));
      inventoryRecurse(node.getFirstChild(), indent + " ");
      node = node.getNextSibling();
    }
  }
  
  /**
   * Look at the avatar's current location, and at the items in that location.
   */
  protected void look() {
    sb.append("You are in ").append(makeNodeName(contextNode)).append(".");
    lookRecurse(contextNode.getFirstChild(), " ");
  }
  
  protected void lookRecurse(IXholon node, String indent) {
    while (node != null) {
      if (node != this) { // exclude this avatar and its children
        sb.append("\n").append(indent).append("You see ").append(makeNodeName(node));
        lookRecurse(node.getFirstChild(), indent + " ");
      }
      node = node.getNextSibling();
    }
  }
  
  /**
   * Search something that's possibly a container or supporter,
   * to determine what it contains ?
   */
  protected void search(String thing) {
    IXholon node = findNode(thing, contextNode);
    if (node != null) {
      searchNode(thing, node);
    }
    else {
      node = findNode(thing, this);
      if (node != null) {
        searchNode(thing, node);
      }
      else {
        sb.append("Can't find ").append(thing);
      }
    }
  }
  
  protected void searchNode(String thing, IXholon node) {
    sb.append("Searching ...");
    lookRecurse(node.getFirstChild(), " ");
  }
  
  /**
   * Move a specified thing from the current contextNode, to the avatar's inventory.
   * @param thing The Xholon name of the thing to take.
   */
  protected void take(String thing) {
    IXholon node = findNode(thing, contextNode);
    if (node != null) {
      node.removeChild();
      node.appendChild(this);
      sb.append("Taken.");
    }
    else {
      sb.append("You can't see any such thing.");
    }
  }
  
  /**
   * Move all things from the current contextNode, to the avatar's inventory.
   */
  protected void takeAll() {
    IXholon node = contextNode.getFirstChild();
    while (node != null) {
      IXholon sib = node.getNextSibling();
      if (node != this) { // don't try to add the avatar itself to the avatar's inventory
        node.removeChild();
        node.appendChild(this);
      }
      node = sib;
    }
    sb.append("All taken.");
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
  
  /**
   * Make a node name.
   * @param node 
   * @return 
   */
  protected String makeNodeName(IXholon node) {
    return node.getName(nameTemplate);
  }
  
  /**
   * Find a node in the tree, given a node name.
   * @param nodeName The name of the node, in nameTemplate format.
   * @param aRoot The node that's the root for searching.
   * @return A node, or null.
   */
  protected IXholon findNode(String nodeName, IXholon aRoot) {
    IXholon node = xpath.evaluate("descendant-or-self::*[@name='" + nodeName + "']", aRoot);
    return node;
  }
  
  /**
   * Does a specified node contain or support one or more other nodes.
   * Inform has classes called "container" and "supporter".
   * For the purposes of Avatar, a "container" or "supporter" is any node that contains other nodes,
   * or any node that's a subclass of "container" or "supporter".
   * @param node 
   * @return true or false
   */
  protected boolean isContainerOrSupporter(IXholon node) {
    // for now, everything is a potential container or supporter
    return true;
    /*if (node.hasChildNodes()) {return true;}
    if (node.getXhc().hasAncestor("container")) {return true;}
    if (node.getXhc().hasAncestor("supporter")) {return true;}
    return false;*/
  }
  
}

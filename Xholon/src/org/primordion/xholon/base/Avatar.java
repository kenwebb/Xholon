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
 * You can create a new Avatar by submitting "avatar" from a XholonConsole, while the app is running.
 * Comments are bracketed by [], and may not contain the ";" character.
 * 
 * It can also be invoked through Firebug, Google Developer Tools, or a similar tool
 * by making use of the Xholon JavaScript API:
var a = xh.root().xpath("StorySystem/Living_Room/Avatar");
a.action("help");
a.action("look");
a.action("go north");

// or
a.action("look").action("take briefcase_41").action("i").action("go north").action("drop").action("go south").action("look");
// which results in:
You are in living_Room_36.
 You see insurance_Salesman_39
 You see briefcase_41
  You see insurance_Paperwork_43
Taken.
You are carrying:
 briefcase_41
  insurance_Paperwork_43
Going to kitchen_45
All dropped.
Going to living_Room_36
You are in living_Room_36.
 You see insurance_Salesman_39
 * 
 * You can create an avatar using Firebug, etc. For example:
xh.root().xpath("StorySystem/Kitchen").append("<Avatar/>").last().action("look");
 * 
 * TODO
 * - in my Kidz Fun Finder app, I use an Avatarbehavior node
 * - can I do all of that stuff inside Avatar.java instead?
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://en.wikipedia.org/wiki/Interactive_fiction">wikipedia Interactive fiction</a>
 * @see <a href="http://en.wikipedia.org/wiki/Inform">wikipedia Inform</a>
 * @since 0.9.1 (Created on August 6, 2014)
 */
public class Avatar extends XholonWithPorts {
  
  // Constants
  protected static final String COMMENT_START = "["; // Inform 7 comment start
  protected static final String CMD_SEPARATOR = ";"; // Inform 6 command separator
  
  // Variables
  protected String roleName = null;
  protected double val;
  
  // the current location of the avatar; it's parent or the node it references
  protected IXholon contextNode = null;
  
  protected StringBuilder sb = null;
  
  // this is the name format required by findNode()
  protected String nameTemplate = IXholon.GETNAME_DEFAULT;
  
  protected static IXPath xpath = null;
  
  // Parameters that can be set through a user command
  
  // whether or not to loop with "go next" and "go prev"
  // "param loop true|false|toggle"
  protected boolean loop = true;
  
  /**
   * A node that this avatar should follow each timestep.
   * It's generally assumed that the leader is not an Avatar. ???
   * ex: If this avatar is a young child, then the leader could be a cat.
   */
  protected IXholon leader = null;
  
  /**
   * A node that should follow or accompany this avatar each timestep.
   * It's generally assumed that the follower is not an Avatar. ???
   * ex: If this avatar is a young child, then the follower could be a parent or other caregiver.
   */
  protected IXholon follower = null;
  
  // constructor
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
  public void act() {
    if (leader != null) {
      // I must follow the leader
      //consoleLog(this.getName() + " is following (accompanying) " + leader.getName());
      if (contextNode != leader.getParentNode()) {
        this.removeChild();
        this.appendChild(leader.getParentNode());
        //contextNode = this.getParentNode(); // handled by local appendChild(...)
      }
    }
    if (follower != null) {
      // I must pull the follower along with me
      //consoleLog(this.getName() + " is being followed (accompanied) by " + follower.getName());
      if (contextNode != follower.getParentNode()) {
        follower.removeChild();
        follower.appendChild(this.getParentNode());
        // TODO if follower is an Avatar, then change it's contextNode
         // handled by local appendChild(...)
      }
    }
    super.act();
  }
  
  @Override
  public void processReceivedMessage(IMessage msg) {
    switch (msg.getSignal()) {
    case ISignal.SIGNAL_XHOLON_CONSOLE_REQ:
      String responseStr = processCommands((String)msg.getData());
      if ((responseStr != null) && (responseStr.length() > 0)) {
        msg.getSender().sendMessage(ISignal.SIGNAL_XHOLON_CONSOLE_RSP, "\n" + responseStr, this);
      }
      break;
    default:
      break;
    }
  }
  
  @Override
  public IMessage processReceivedSyncMessage(IMessage msg) {
    switch (msg.getSignal()) {
    case ISignal.SIGNAL_XHOLON_CONSOLE_REQ:
      String responseStr = processCommands((String)msg.getData());
      return new Message(ISignal.SIGNAL_XHOLON_CONSOLE_RSP, "\n" + responseStr, this, msg.getSender());
    default:
      return super.processReceivedSyncMessage(msg);
    }
  }
  
  @Override
  public void doAction(String action) {
    String responseStr = processCommands(action);
    this.consoleLog(responseStr);
  }
  
  @Override
  public void appendChild(IXholon newParentNode) {
    super.appendChild(newParentNode);
    // make sure that any Avatar that moves has its contextNode updated
    contextNode = this.getParentNode();
  }
  
  /**
   * Process one or more commands.
   * @param cmds (ex: "help" "look;go north;look")
   * @return
   */
  protected String processCommands(String cmds) {
    String[] s = cmds.split(CMD_SEPARATOR, 100);
    sb = new StringBuilder();
    for (int i = 0; i < s.length; i++) {
      processCommand(s[i]);
    }
    return sb.toString();
  }
  
  /**
   * Process a command.
   * @param cmd (ex: "help")
   * 
   * TODO new commands Dec 1, 2014
   * eat X - remove X from the Xholon tree, where X is in the Avatar's inventory
   * smash X - remove the container X, leaving the components of X (this is a flatten operation)
   * build X - create a new node called X (which must be a valid XholonClass)
   *   can subsequently take things from the surroundings and drop them into X
   * exit X - equivalent to XPath "ancestor::NAME"
   * follow leaderX - follow a sibling
   *   if X is not an Avatar, may need to take and drop X each timestep
   * unfollow - stop following
   * lead followerX - cause follower to follow you
   *   if X is not an Avatar, may need to take X
   * unlead - stop leading
   * read X - see my notebook for Nov 28
   * stay duration - stay at current location for duration timesteps
   * script
   *   if a command string begins with "script", then treat it as a multi-timestep sequence
   *   this should be handled by processCommands(cmds)
   *   and handle scripts that are initially contained within the Avatar tag
   *   and handle scripts that are initially or later inserted as a Attribute_String
   *     this would allow drag and drop of commands and scripts
   * 
   * I need to add the act() function:
   *   to handle follow, lead, command sequences (scripts), and other commands that span multiple timesteps
   *   
   */
  protected void processCommand(String cmd) {
    cmd = cmd.trim();
    if (cmd.length() == 0) {return;}
    if (cmd.startsWith(COMMENT_START)) {return;}
    String[] data = cmd.split(" ", 3);
    int len = data.length;
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
    case "eat":
      if (len > 1) {
        eat(data[1]);
      }
      else {
        sb.append("Please specify which thing in the inventory to eat (ex: eat carrot).");
      }
      break;
    case "enter":
      if (len > 1) {
        enter(data[1]);
      }
      else {
        sb.append("Please specify which place to enter (ex: enter castle_32).");
      }
      break;
    case "x":
    case "examine":
      if (len > 1) {
        examine(data[1]);
      }
      else {
        sb.append("Please specify what to examine (ex: x book_123).");
      }
      break;
    case "exit":
      if (len == 1) {
        exit(null);
      }
      else {
        exit(data[1]);
      }
      break;
    case "follow": // follow the leader
    {
      if (len > 1) {
        IXholon node = findNode(data[1], contextNode);
        if (node != null) {
          leader = node;
          sb.append("Following.");
        }
        else {
          sb.append("You can't find this leader to follow.");
        }
      }
      else {
        sb.append("Please specify the leader (ex: follow Dorothy).");
      }
      break;
    }
    case "go":
      if (len > 1) {
        go(data[1]);
      }
      else {
        sb.append("Please specify where to go (ex: go north).");
      }
      break;
    case "help":
      help();
      break;
    case "i":
    case "inventory":
      inventory();
      break;
    case "lead": // lead a follower
    {
      if (len > 1) {
        IXholon node = findNode(data[1], contextNode);
        if (node != null) {
          follower = node;
          sb.append("Leading.");
        }
        else {
          sb.append("You can't find this follower to lead.");
        }
      }
      else {
        sb.append("Please specify the follower (ex: lead horse).");
      }
      break;
    }
    case "look":
      look();
      break;
    case "param":
      if (len > 2) {
        param(data[1], data[2]);
      }
      else {
        sb.append("Please specify the name and value of the param (ex: param loop false).");
      }
      break;
    case "search":
      if (len > 1) {
        search(data[1]);
      }
      else {
        sb.append("Please specify what to search (ex: search box_13).");
      }
      break;
    case "smash":
      if (len > 1) {
        smash(data[1]);
      }
      else {
        sb.append("Please specify which thing to smash (ex: smash tower).");
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
    case "unfollow": // stop following the leader
      leader = null;
      break;
    case "unlead": // stop leading a follower
      follower = null;
      break;
    case "vanish":
      vanish();
      break;
    default:
      sb.append(data[0]).append(" is not a verb I recognise.");
      break;
    }
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
   * Eat a specified thing in the avatar's inventory.
   * This removes the thing from the Xholon tree.
   * @param thing The Xholon name of the thing to eat.
   * TODO perhaps only eat it if it's a normally human-edible thing.
   *   But, the avatar could be some sort of people-eating monster, or a rock eater, etc.
   */
  protected void eat(String thing) {
    IXholon node = findNode(thing, this);
    if (node != null) {
      node.removeChild();
      sb.append("Yum.");
    }
    else {
      sb.append("You're not carrying any such thing. You need to take it first.");
    }
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
  
  /**
   * Examine one node.
   * @param thing The Xholon name of the thing to examine.
   * @param node The thing to examine.
   */
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
   * Exit to the parent or other ancestor of the current contextNode.
   * If the contextNode has no parent, then do nothing.
   * @param ancestor The XholonClass name of an ancestor in the CSH tree, or null.
   *   a value of null signifies exiting to the immediate parent
   *   by convention, this is usually uppercase (ex: exit House)
   */
  protected void exit(String ancestor) {
    IXholon node = null;
    if (ancestor == null) {
      node = contextNode.getParentNode();
    }
    else {
      node = xpath.evaluate("ancestor::" + ancestor, contextNode);
    }
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
   * TODO handle port ports
   * TODO handle an XPath expression as the portName
   *   go xpath(ancestor::X/descendant::Y)
   *   xpath.evaluate(portName, contextNode);
   * @param portName The name or index of a Xholon port (ex: "north" "south" "east" "west" "0" "1"),
   *   or a pseudo port name ("next" "prev" "xpath(...)")
   *   or an abbreviated name (ex: "n" "so" "eas" "w").
   */
  protected void go(String portName) {
    if (portName == null) {return;}
    if ("next".equals(portName)) {
      IXholon node = contextNode.getNextSibling();
      if (node == null) {
        if (loop) {
          moveto(contextNode.getFirstSibling());
        }
        else {
          sb.append("Can't go next.");
        }
      }
      else {moveto(node);}
      return;
    }
    else if ("prev".equals(portName)) {
      IXholon node = contextNode.getPreviousSibling();
      if (node == null) {
        if (loop) {
          moveto(contextNode.getLastSibling());
        }
        else {
          sb.append("Can't go prev.");
        }
      }
      else {moveto(node);}
      return;
    }
    else if (portName.startsWith("xpath")) {
      // go xpath(ancestor::X/descendant::Y)
      int begin = portName.indexOf("(");
      if (begin == -1) {return;}
      int end = portName.lastIndexOf(")");
      if (end == -1) {return;}
      String xpathExpression = portName.substring(begin+1, end);
      if (xpathExpression.length() == 0) {return;}
      IXholon node = xpath.evaluate(xpathExpression, contextNode);
      if (node != null) {
        moveto(node);
      }
      else {
        sb.append("Can't go " + portName + ". ");
      }
      return;
    }
    Object[] portArr = contextNode.getAllPorts().toArray();
    if ((portArr != null) && (portArr.length > 0)) {
      String foundPortNames = "";
      for (int i = 0; i < portArr.length; i++) {
        PortInformation pi = (PortInformation)portArr[i];
        String foundPortName = pi.getFieldName();
        //if (portName.equals(foundPortName)) {
        if (foundPortName.startsWith(portName)) {
          /*IXholon reffedNode = pi.getReffedNode();
          sb.append("Going to ").append(makeNodeName(reffedNode));
          if (this.hasParentNode()) {
            this.removeChild();
            this.appendChild(reffedNode);
          }
          contextNode = reffedNode;*/
          moveto(pi.getReffedNode());
          return;
        }
        else {
          foundPortNames += " " + foundPortName;
        }
      }
      sb.append("Can only go").append(foundPortNames);
    }
    else {
      sb.append("Can't go ").append(portName);
    }
  }
  
  protected void moveto(IXholon node) {
    if (node == null) {return;}
    sb.append("Going to ").append(makeNodeName(node));
    if (this.hasParentNode()) {
      this.removeChild();
      this.appendChild(node);
    }
    contextNode = node;
  }
  
  /**
   * Provide some minimal help to users.
   */
  protected void help() {
    sb
    .append("Basic commands:")
    .append("\nappear")
    .append("\ndrop [thing]")
    .append("\neat thing")
    .append("\nenter thing")
    .append("\nexamine thing  (x thing)")
    .append("\nexit [ancestor]")
    .append("\nfollow leader")
    .append("\ngo portName")
    .append("\nhelp")
    .append("\ninventory  (i)")
    .append("\nlead follower")
    .append("\nlook")
    .append("\nsearch thing")
    .append("\nsmash thing")
    .append("\ntake [thing]")
    .append("\nunfollow")
    .append("\nunlead")
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
  
  /**
   * 
   */
  protected void searchNode(String thing, IXholon node) {
    sb.append("Searching ...");
    lookRecurse(node.getFirstChild(), " ");
  }
  
  /**
   * Remove a thing from the Xholon tree,
   * leaving behind the components of the thing (this is a flatten operation).
   * If the thing doesn't exist, or if it hasn't any parent or any children,
   * then no action will be taken.
   * @param thing The Xholon name of the thing to smash.
   */
  protected void smash(String thing) {
    IXholon node = findNode(thing, contextNode);
    if (node != null) {
      IXholon parentNode = node.getParentNode();
      if (parentNode == null) {
        sb.append("You can't smash a thing that has no parent node.");
        return;
      }
      IXholon childNode = node.getFirstChild();
      if (childNode == null) {
        sb.append("You can't smash a thing that has no contents.");
        return;
      }
      while (childNode != null) {
        IXholon nextChildNode = childNode.getNextSibling();
        childNode.removeChild();
        childNode.appendChild(parentNode);
        childNode = nextChildNode;
      }
      node.removeChild();
      sb.append("Smashed.");
    }
    else {
      sb.append("You can't smash any such thing.");
    }
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
      sb.append("You can't take any such thing.");
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
   * Change the value of a parameter.
   * @param name 
   * @param value 
   */
  protected void param(String name, String value) {
    switch (name) {
    case "loop":
      switch (value) {
      case "true": loop = true; break;
      case "false": loop = false; break;
      case "toggle": loop = !loop; break;
      default: break;
      }
      break;
    default:
      break;
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
   * @param nodeName The name of the node, in nameTemplate format (ex: "abc:def_13"),
   *   or in abbreviated nameTemplate format (ex: "abc" "ab" "abc:def").
   * @param aRoot The node that's the root for searching.
   * @return A node, or null.
   */
  protected IXholon findNode(String nodeName, IXholon aRoot) {
    IXholon node = xpath.evaluate("descendant-or-self::*[@name='" + nodeName + "']", aRoot);
    if (node == null) {
      // search all immediate children for an abbreviation match
      node = aRoot.getFirstChild();
      while (node != null) {
        if (makeNodeName(node).startsWith(nodeName)) {
          break;
        }
        node = node.getNextSibling();
      }
    }
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

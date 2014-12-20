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

import com.google.gwt.dom.client.Element;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.XholonHelperService;

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
  protected static final int ACTIONIX_INITIAL = -1; // initial value of actionIx
  protected static final int WAITCOUNT_HIGH = Integer.MAX_VALUE; // waitCount max value
  protected static final String THIS_AVATAR = "this";
  
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
  
  /**
   * Whether or not to loop with "go next" and "go prev".
   * "param loop true|false|toggle"
   */
  protected boolean loop = true;
  
  /**
   * Whether or not to repeat the current command sequence when it's finished.
   * "param repeat true|false|toggle"
   */
  protected boolean repeat = false;
  
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
  
  /**
   * An optional array of actions to automatically perform, during successive timesteps.
   */
  protected String[] actions = new String[0];
  
  /**
   * Index into the actions array.
   */
  protected int actionIx = ACTIONIX_INITIAL;
  
  /**
   * Whether or not act() should call println() for each action.
   */
  protected boolean transcript = true;
  
  /**
   * Whether or not to write informative debug messages.
   */
  protected boolean debug = false;
  
  /**
   * An optional caption that act() should write to for each action.
   * Use "param caption SELECTOR" to set a value, and "param caption null" to disable it.
   * ex: "param caption #xhanim>#one>p"
   */
  protected Element caption = null;
  
  /**
   * An optional prefix if call println() or caption for each action.
   */
  protected String outPrefix = "";
  
  /**
   * If this avatar moves within the Xholon tree, it might be executed multiple times in the same timestep.
   * This variable helps to prevent that.
   */
  protected int actTimeStep = -1;
  
  /**
   * The number of timesteps that the avatar should wait
   * before starting to process commands in the current script.
   */
  protected int waitCount = 0;
  
  protected IApplication app = null;
  
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
  public void setVal(String actionsStr) {
    this.setVal_String(actionsStr);
  }
  
  @Override
  public void setVal_String(String actionsStr) {
    // set the contents of the actions array
    if ((actionsStr != null) && (actionsStr.length() > 0)) {
      actions = actionsStr.split("\n");
      actionIx = ACTIONIX_INITIAL;
    }
  }
  
  @Override
  public String getVal_String() {
    // get the value of the current item in the actions array, or null
    if ((actionIx > ACTIONIX_INITIAL) && (actionIx < actions.length)) {
      return actions[actionIx];
    }
    return null;
  }
  
  @Override
  public void postConfigure() {
    contextNode = this.getParentNode();
    app = this.getApp();
    xpath = this.getXPath();
    if (this.getFirstChild() != null) {
      this.setVal_String(this.getFirstChild().getVal_String());
      this.outPrefix = this.getName(IXholon.GETNAME_ROLENAME_OR_CLASSNAME) + ": ";
      this.getFirstChild().removeChild();
    }
    super.postConfigure();
  }
  
  @Override
  public void act() {
    // check to see if this node has already been called this timestep
    int ts = app.getTimeStep();
    
    if (actTimeStep < ts) {
      actTimeStep = ts;
      if (leader != null) {
        // I must follow the leader
        //consoleLog(this.getName() + " is following (accompanying) " + leader.getName());
        if (contextNode != leader.getParentNode()) {
          this.removeChild();
          this.appendChild(leader.getParentNode());
          //contextNode = this.getParentNode(); // handled by local appendChild(...)
        }
      }
      
      if (waitCount == 0) {
        if (++actionIx < actions.length) {
          String a = actions[actionIx].trim();
          if (a.length() > 0) {
            if (caption != null) {
              caption.setInnerText(outPrefix + a);
            }
            if (transcript) {
              this.println(outPrefix + a);
            }
            this.doAction(a);
          }
        }
        else if (repeat) {
          actionIx = ACTIONIX_INITIAL;
        }
      }
      else {
        waitCount--;
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
    if (transcript && (responseStr != null) && (responseStr.length() > 0)) {
      this.println(" " + responseStr);
    }
    if (debug) {
      this.consoleLog(responseStr);
    }
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
   * @return a response
   */
  protected String processCommands(String cmds) {
    if (cmds.startsWith("script;")) {
      // strip "script;" from the start of the string
      this.setVal_String(cmds.substring(7));
      return "Script initialized.";
    }
    sb = new StringBuilder();
    String[] s = cmds.split(CMD_SEPARATOR, 100);
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
   *   optionally include roleName  ex: "build Snowman [role Frosty]"
   * exit X - equivalent to XPath "ancestor::NAME"
   * follow leaderX - follow a sibling
   *   if X is not an Avatar, may need to take and drop X each timestep
   * unfollow - stop following
   * lead followerX - cause follower to follow you
   *   if X is not an Avatar, may need to take X
   * unlead - stop leading
   * read X - see my notebook for Nov 28
   * wait [duration] - wait at current location for duration timesteps
   *   TODO wait [until absolute timestep]
   * put X on|in Y - ex: "put dino in museum"
   * become thing role|type newRoleOrTypeName
   * 
   * if thing|xpath(expr) {action1} else {action2};
   * if thing|xpath(expr) then action1 else action2;
   * OR
   * if thing|xpath(expr)
   * then action1;...;actionM
   * else action2;...;actionN
   * examples:
   *   if xpath(Can/descendant::Rabbit) then follow it;   TODO handle "it"
   * OR
   * i/drop? inventory? look? exit? next? xpath?
   * i? thing [then] action1 [else action2]
   * look/next/take? thing [then] action1 [else action2]
   * exit? thing [then] action1 [else action2]
   * xpath? expr [then] action1 [else action2]
   * attr/val? expr [then] action1 [else action2]
   * 
   * TODO in general, should be able to replace a thing with an xpath(expr)
   *   or even more generally, be able to replace with an expression in ANY known path language
   *     css(selector)
   *     pcs(expr)
   * 
   * TODO write generic IXholon.java methods for testing existence of children, parent, siblings
   *   I already have find() methods
   *   look at latest XPath/XSLT/XQuery and CSS standards, and other path languages
   * 
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
    String[] data = cmd.split(" ", 4);
    int len = data.length;
    switch (data[0]) {
    case "appear":
      appear();
      break;
    case "become":
      if (len == 4) {
        become(data[1], data[2], data[3]);
      }
      else {
        sb.append("Please specify the correct number of parameters (ex: become Peter role Pete).");
      }
      break;
    case "build":
      if (len == 2) {
        build(data[1], null, null);
      }
      else if (len == 4) {
        build(data[1], data[2], data[3]);
      }
      else {
        sb.append("Please specify something to build (ex: build Car).");
      }
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
      if (len == 2) {
        go(data[1], null);
      }
      else if (len > 2) {
        go(data[1], data[2]);
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
    case "next":
      if (len > 1) {
        next(data[1]);
      }
      else {
        next();
      }
      break;
    case "param":
      if (len > 2) {
        param(data[1], data[2]);
      }
      else {
        sb.append("Please specify the name and value of the param (ex: param loop false).");
      }
      break;
    case "prev":
      prev();
      break;
    case "put":
      if (len > 3) {
        put(data[1], data[2], data[3]);
      }
      else {
        sb.append("Please specify the correct number of parameters (ex: put dino in museum).");
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
    case "wait":
      // TODO wait random(low,high)
      if (len > 1) {
        try {
          waitCount = Integer.parseInt(data[1]);
        } catch(NumberFormatException e) {
          sb.append("The wait time must be specified as an integer (ex: wait 10)");
        }
      }
      else {
        waitCount = WAITCOUNT_HIGH;
      }
      break;
    default:
      // TODO check for app-specified commands
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
   * Become something different by changing the roleName (or the XholonClass?).
   * become thing role|type newRoleOrTypeName
   *  - ex: "become Peter role Pete" "become Peter type Wolf" "become *rabbit type Wolf"
   *  - ex: "become this role Abc" "become this type Def"
   *    "this" signifies the avatar itself
   *  - it's difficult to change the XholonClass; I need to find the new XholonClass object
   *  - just do role for now
   * @param thing - The name of something (ex: "car"), or "this" to specify this avatar.
   * @param whatChanges - "role" or "type" (only "role" is currently implemented.
   * @param newRoleOrType - The new roleName for the thing.
   */
  protected void become(String thing, String whatChanges, String newRoleOrType) {
    IXholon node = null;
    if (THIS_AVATAR.equals(thing)) {
      node = this;
    }
    else {
      node = findNode(thing, contextNode);
      if (node == null) {
        node = findNode(thing, this);
      }
    }
    if (node != null) {
      if ("role".equals(whatChanges)) {
        node.setRoleName(newRoleOrType);
      }
      else if ("type".equals(whatChanges)) {
        sb.append("become X type Y  is not yet implemented");
      }
      else {
        sb.append("Please specify either role or type (ex: become Robert role Bob)");
      }
    }
  }
  
  /**
   * Build something, and append it as a child of the contextNode.
   * There may already be a XholonClass for the thing(s), but this isn't necessary.
   *  ex: "build GasTank"
   *  ex: "build <GasTank><IceCream/><HomePlate/></GasTank>"
   * TODO should be able to include roleName  ex: "build Snowman [role Frosty]"
   * @param thing The name of something (ex: "Car"), or an XML string (ex "<Car><Driver/></Car>");
   * @param ignore Optional string "role"
   * @param roleName Optional roleName for the thing
   */
  protected void build(String thing, String ignore, String roleName) {
    thing = thing.trim();
    if (!thing.startsWith("<")) {
      if (roleName == null) {
        thing = "<" + thing + "/>";
      }
      else {
        thing = "<" + thing + " roleName=\"" + roleName + "\"/>";
      }
    }
    //rememberButton3Selection(context); // XholonConsole.java has this
    ((XholonHelperService)app
      .getService(IXholonService.XHSRV_XHOLON_HELPER))
        .pasteLastChild(contextNode, thing);
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
   * handle an XPath expression as the portName
   *   go xpath(ancestor::X/descendant::Y)
   *   xpath.evaluate(portName, contextNode);
   * @param portName - The name or index of a Xholon port (ex: "north" "south" "east" "west" "0" "1"),
   *   or a pseudo port name ("next" "prev" "xpath(...)")
   *   or an abbreviated name (ex: "n" "so" "eas" "w").
   * @param nextTarget - Optional target for "next"
   */
  protected void go(String portName, String nextTarget) {
    if (portName == null) {return;}
    if ("next".equals(portName)) {
      /*IXholon node = contextNode.getNextSibling();
      if (node == null) {
        if (loop) {
          moveto(contextNode.getFirstSibling());
        }
        else {
          sb.append("Can't go next.");
        }
      }
      else {moveto(node);}*/
      if (nextTarget == null) {next();}
      else {next(nextTarget);}
      return;
    }
    else if ("prev".equals(portName)) {
      /*IXholon node = contextNode.getPreviousSibling();
      if (node == null) {
        if (loop) {
          moveto(contextNode.getLastSibling());
        }
        else {
          sb.append("Can't go prev.");
        }
      }
      else {moveto(node);}*/
      prev();
      return;
    }
    else if (portName.startsWith("xpath")) {
      IXholon node = evalXPathCmdArg(portName, contextNode);
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
        if (foundPortName.startsWith(portName)) {
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
  
  /**
   * next
   */
  protected void next() {
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
  }
  
  /**
   * next
   * @param nextTarget
   */
  protected void next(String nextTarget) {
    IXholon node = contextNode.getNextSibling();
    while (node != null) {
      if (makeNodeName(node).startsWith(nextTarget)) {
        break;
      }
      node = node.getNextSibling();
    }
    if (node != null) {
      moveto(node);
    }
    else {
      sb.append("Can't find next " + nextTarget);
    }
  }
  
  /**
   * prev
   */
  protected void prev() {
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
  }
  
  /**
   * Move to a specified node.
   * @param node 
   */
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
    .append("\nbecome thing role|type newRoleOrTypeName")
    .append("\nbuild thing [role roleName]")
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
    .append("\nnext [target]")
    .append("\nparam name value")
    .append("\nprev")
    .append("\nput thing1 in thing2")
    .append("\nsearch thing")
    .append("\nsmash thing")
    .append("\ntake [thing]")
    .append("\nunfollow")
    .append("\nunlead")
    .append("\nvanish")
    .append("\nwait [n]")
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
   * Put thing1 into thing2, where:
   *  thing1 is in this avatar's inventory
   *  thing2 is the name of one of the avatar's siblings,
   *    or an XPath expression.
   * ex: "put dino in museum"
   *     "put dino in xpath(../Museum)"
   *     "take dino;put dino in museum"
   * @param thing1 - 
   * @param in - the word "in"
   * @param thing2 - 
   */
  protected void put(String thing1, String in, String thing2) {
    IXholon node1 = findNode(thing1, this);
    if (node1 == null) {
      sb.append("Can't find ").append(thing1).append(". You need to take it first.");
      return;
    }
    IXholon node2 = null;
    if (thing2.startsWith("xpath")) {
      node2 = evalXPathCmdArg(thing2, contextNode);
    }
    else {
      node2 = findNode(thing2, contextNode);
    }
    if (node2 == null) {
      sb.append("Can't find ").append(thing2);
      return;
    }
    node1.removeChild();
    node1.appendChild(node2);
    sb.append("Put.");
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
    case "caption":
      if ("null".equals(value)) {caption = null;}
      else {caption = querySelector(value);}
      break;
    case "debug":
      switch (value) {
      case "true": debug = true; break;
      case "false": debug = false; break;
      case "toggle": debug = !debug; break;
      default: break;
      }
      break;
    case "loop":
      switch (value) {
      case "true": loop = true; break;
      case "false": loop = false; break;
      case "toggle": loop = !loop; break;
      default: break;
      }
      break;
    case "repeat":
      switch (value) {
      case "true": repeat = true; break;
      case "false": repeat = false; break;
      case "toggle": repeat = !repeat; break;
      default: break;
      }
      break;
    case "transcript":
      switch (value) {
      case "true": transcript = true; break;
      case "false": transcript = false; break;
      case "toggle": transcript = !transcript; break;
      default: break;
      }
      break;
    default:
      break;
    }
  }
  
  /**
   * Query for a DOM Element, given a selector.
   * ex: var caption = document.querySelector("#xhanim>#one>p");
   * @param selector - a CSS selector (ex: "#xhanim>#one>p")
   */
  protected native Element querySelector(String selector) /*-{
    return $doc.querySelector(selector);
  }-*/;
  
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
   *   If the first character is "*", then do a wildcard search (ex: "*def").
   *   TODO "*:def" incorrectly returns a non-existent script node ?
   * @param aRoot The node that's the root for searching.
   * @return A node, or null.
   */
  protected IXholon findNode(String nodeName, IXholon aRoot) {
    IXholon node = null;
    if (nodeName.startsWith("*")) {
      if (nodeName.length() < 2) {return null;}
      node = aRoot.getFirstChild();
      while (node != null) {
        if (makeNodeName(node).indexOf(nodeName.substring(1)) > -1) {
          break;
        }
        node = node.getNextSibling();
      }
      return node;
    }
    node = xpath.evaluate("descendant-or-self::*[@name='" + nodeName + "']", aRoot);
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
  
  /**
   * Evaluate a command argument that's specified as an XPath expression.
   * @param cmdArg - ex: "xpath()"
   * @param aRoot - the context node for the XPath evaluation
   */
  protected IXholon evalXPathCmdArg(String cmdArg, IXholon aRoot) {
    int begin = cmdArg.indexOf("(");
    if (begin == -1) {return null;}
    int end = cmdArg.lastIndexOf(")");
    if (end == -1) {return null;}
    String xpathExpression = cmdArg.substring(begin+1, end);
    if (xpathExpression.length() == 0) {return null;}
    return xpath.evaluate(xpathExpression, aRoot);
  }
  
}

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
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IControl;
import org.primordion.xholon.base.IGrid;
import org.primordion.xholon.io.gwt.HtmlScriptHelper;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.XholonHelperService;
import org.primordion.xholon.util.Misc;

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
  protected static final String COMMENT_END = "]"; // Inform 7 comment end
  protected static final String CMD_SEPARATOR = ";"; // Inform 6 command separator
  protected static final int ACTIONIX_INITIAL = -1; // initial value of actionIx
  protected static final int WAITCOUNT_HIGH = Integer.MAX_VALUE; // waitCount max value
  protected static final String THIS_AVATAR = "this";
  protected static final float SSU_FLOAT_DEFAULT = -1.0f;
  protected static final String WILDCARD = "*";
  protected static final String DOLLAR_CHILD_SEPARATOR = ">"; // ex: $One>Two>three
  protected static final String LEAD_PARENT_NODE = ".."; // ex: lead ..;
  protected static final String DEFAULT_HOP_PARAMS = "25"; // anim hop 25
  protected static final String DEFAULT_TURN_PARAMS = "45"; // anim turnright 45
  protected static final String DEFAULT_GROW_PARAMS = "2"; // anim grow 2
  protected static final String DEFAULT_MIRROR_PARAMS = "x"; // anim mirror x  or y
  protected static final String DEFAULT_VIEWELE_STR = "#xhanim>#one";
  protected static final String ALTERNATE_VIEWELE_STR = "#xhgraph";
  
  // Variables
  public String roleName = null;
  protected double val;
  
  // the current location of the avatar; its parent or the node it references
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
   * The root of an optional view, typically a HTML div that contains a d3cp SVG image.
   * This is used by the "anim" command.
   * Use "param view SELECTOR" to set a value, and "param view null" to disable it.
   * ex: "param view #xhanim>#one"
   */
  protected Element viewEle = null;
  
  /**
   * An optional caption Element that act() should write to for each action.
   * Use "param caption SELECTOR" to set a value, and "param caption null" to disable it.
   * ex: "param caption #xhanim>#one>p"
   */
  protected Element captionEle = null;
  
  /**
   * Whether or not to automatically write ongoing actions to the caption.
   * An avatar can only write to a caption if captionEle != null, and caption == true .
   * ex: "param caption true" or "param caption false"
   */
  protected boolean caption = true;
  
  /**
   * Whether or not act() should speak each action.
   */
  protected boolean speech = false;
  
  /**
   * Parameters for the SpeechSynthesisUtterance class in the Web Speech API.
   * This is a JSON string with parameter name/value pairs, or null.
   * ex: {"lang":"en-US", "voice":"Google UK English Female", "volume":1.0, "rate":1.2, "pitch":1.0}
   */
  //protected String speechParams = null; // ???
  protected String ssuLang = null; // ex: "en-US"
  protected Object ssuVoice = null; // ex: new SpeechSynthesisVoice("Google UK English Female")
  protected float ssuVolume = SSU_FLOAT_DEFAULT;
  protected float ssuRate = SSU_FLOAT_DEFAULT;
  protected float ssuPitch = SSU_FLOAT_DEFAULT;
  
  /**
   * An optional prefix if call println() or caption for each action.
   */
  protected String outPrefix = "";
  
  /**
   * If this avatar moves within the Xholon tree, it might be executed multiple times in the same timestep.
   * This variable helps to prevent that.
   * This variable is also used by the "wait" command.
   */
  protected int actTimeStep = -1;
  
  /**
   * The number of timesteps that the avatar should wait
   * before starting to process commands in the current script.
   */
  protected int waitCount = 0;
  
  protected IApplication app = null;
  
  /**
   * Whether or not to write changes to Meteor.
   * "param meteor true|false"
   */
  protected boolean meteor = false;
  
  /**
   * Whether or not to write "move" changes to Meteor.
   * "param meteormove true|false"
   */
  protected boolean meteormove = false;
  
  protected IXholon meteorService = null;
  
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
      if (transcript && ((actionIx > ACTIONIX_INITIAL) && (actionIx < actions.length))) {
        // show a warning message if a new script overlays an existing uncompleted one
        String msg = "overwriting script with " + (actions.length - actionIx) + " actions left.";
        this.println(this.getName(IXholon.GETNAME_ROLENAME_OR_CLASSNAME) + ": " + msg);
      }
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
  @SuppressWarnings("unchecked")
  public List getAllPorts() {
    List<PortInformation> xhcPortList = this.getXhc().getPortInformation();
    if ((xhcPortList == null) || (xhcPortList.size() == 0)) {
      //return xhcPortList;
      return super.getAllPorts(); // calls Xholon.getAllPorts()
    }
    List<PortInformation> realPortList = new ArrayList<PortInformation>();
    // eval the XPath expressions to determine the reffed nodes
    Iterator<PortInformation> portIt = xhcPortList.iterator();
    while (portIt.hasNext()) {
      PortInformation pi = (PortInformation)portIt.next();
      IXholon reffedNode = this.getXPath().evaluate(pi.getXpathExpression().trim(), this);
      if (reffedNode == null) {
        //xhcPortList.remove(pi); // causes java.util.ConcurrentModificationException
      }
      else {
        pi.setReffedNode(reffedNode);
        realPortList.add(pi);
      }
    }
    return realPortList;
  }
  
  @Override
  public void postConfigure() {
    contextNode = this.getParentNode();
    app = this.getApp();
    xpath = this.getXPath();
    if (isXhSvgAnimRequired()) {
      HtmlScriptHelper.requireScript("xhSvgAnim", null);
    }
    meteorService = app.getService(IXholonService.XHSRV_METEOR_PLATFORM);
    if (this.getFirstChild() != null) {
      this.setVal_String(this.getFirstChild().getVal_String());
      this.outPrefix = this.getName(IXholon.GETNAME_ROLENAME_OR_CLASSNAME) + ": ";
      this.getFirstChild().removeChild();
    }
    super.postConfigure();
  }
  
  /**
   * The xhSvgAnim.js script is required,
   * if it hasn't yet been loaded and if no other Avatar is currently trying to load it.
   * The first Avatar that returns true from this method, sets xh.anim to null,
   * to prevent other Avatars from trying to load xhSvgAnim.js .
   */
  protected native boolean isXhSvgAnimRequired() /*-{
    if (typeof $wnd.xh.anim == "undefined") {
      $wnd.xh.anim = null;
      return true;
    }
    return false;
  }-*/;
  
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
            if (caption && (captionEle != null) && (!a.startsWith("wait"))) {
              captionEle.setInnerText(outPrefix + a);
            }
            if (transcript) {
              this.println(outPrefix + a);
            }
            if (speech) {
              this.speak(outPrefix + a, ssuLang, ssuVoice, ssuVolume, ssuRate, ssuPitch);
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
        if (contextNode == follower) {
          // TODO
          consoleLog("contextNode == follower " + follower.getName());
        }
        else if (contextNode != follower.getParentNode()) {
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
        responseStr = COMMENT_START + responseStr + COMMENT_END + CMD_SEPARATOR;
        msg.getSender().sendMessage(ISignal.SIGNAL_XHOLON_CONSOLE_RSP, "\n" + responseStr, this);
      }
      break;
    default:
      super.processReceivedMessage(msg);
      break;
    }
  }
  
  @Override
  public IMessage processReceivedSyncMessage(IMessage msg) {
    switch (msg.getSignal()) {
    case ISignal.SIGNAL_XHOLON_CONSOLE_REQ:
      String responseStr = processCommands((String)msg.getData());
      if ((responseStr != null) && (responseStr.length() > 0)) {
        responseStr = COMMENT_START + responseStr + COMMENT_END + CMD_SEPARATOR;
      }
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
    if (speech && (responseStr != null) && (responseStr.length() > 0)) {
      this.speak(" " + responseStr, ssuLang, ssuVoice, ssuVolume, ssuRate, ssuPitch);
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
      if (cmds.length() == 7) {
        // return the script as a newline-separated string
        return join(actions, "\n");
      }
      // strip "script;" from the start of the string
      this.setVal_String(cmds.substring(7));
      return ""; //"Script initialized.";
    }
    sb = new StringBuilder();
    String[] s = cmds.split(CMD_SEPARATOR, 100);
    for (int i = 0; i < s.length; i++) {
      processCommand(s[i]);
    }
    return sb.toString();
  }
  
  protected String join(String[] arr, String sep) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < arr.length; i++) {
      if (i > 0) {
        sb.append(sep);
      }
      sb.append(arr[i]);
    }
    return sb.toString();
  }
  
  /**
   * Split a String into an array of substrings.
   * Split on spaces, unless between quotes.
   * ex: put "Sir William" in Coach  ->  ["put","Sir William","in","Coach"]
   * ex: one two "three four five" six  >  ["one", "two", "three four five", "six"]
   * @param str The string that will be split.
   * @param limit The maximum length of the resulting array.
   * @return An array of strings
   */
  protected native String[] split(String str, int limit) /*-{
    var result = str.match(/("[^"]+"|[^"\s]+)/g);
    // remove starting and end quotes in result
    for (var i = 0; i < result.length; i++) {
      if (result[i].charAt(0) == '"') {
        result[i] = result[i].substring(1, result[i].length-1);
      }
    }
    if (result.length > limit) {
      // limit number of entries in the result
      var last = result[limit-1];
      for (var j = limit; j < result.length; j++) {
        last += " " + result[j];
      }
      result[limit-1] = last;
      result.length = limit;
    }
    return result;
  }-*/;
  
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
   *   wait [til absolute_timestep]
   * put X in|on|under|ANY Y
   *   ex: "put dino in museum" "put glassSlipper on Cinderella" "put treasure under ground"
   *   see my notes on close siblings (notebook Dec 17,18,19 2014)
   * become thing role|type newRoleOrTypeName
   * walk [clockwise|counterclockwise] - continuously walk among siblings
   * unwalk
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
   * OR March 22, 2015
   * if xpath(expr)|otherExpr
   *   ...
   * elseif xpath(expr)|otherExpr
   *   ...
   * else
   *   ...
   * endif
   * where ... means one or more lines with commands, with one line executed each time step
   * OR
   * ifeq|ifne|iflt|ifgt|ifle|ifge xpath(expr)|otherExpr [attrname [attrvalue]]
   * 
   * if xpath(expr) action [elseif xpath(expr) action else action [endif]]
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
   * Async and sync messaging:
   * msg portName|etc [signal [data]]
   * call portName|etc [signal [data]]
   * ex: msg next 101 I am right behind you
   *     call xpath(ancestor::TheSystem/descendant::SomeNode) 1234 Do your thing
   *
   * appendto prependto insertBefore insertAfter
   * enter    
   * append prepend before after
   * take   
   * put X append/prepend/before/after Y
   *
   * avatar should be able to lead the container that it's in (ex: lead Coach)
   *   or maybe use a new command "drive", meaning "lead/move the thing that you are inside of"
   *   synonymns: ride pilot fly 
   *
   * be able to use a list of things wherever appropriate (ex: take one,two,three)
   * be able to use ALL wherever appropriate (ex: put * in Car)
   */
  protected void processCommand(String cmd) {
    cmd = cmd.trim();
    if (cmd.length() == 0) {return;}
    if (cmd.startsWith(COMMENT_START)) {return;}
    String[] data = null;
    if (cmd.indexOf('"') == -1) {
      data = cmd.split(" ", 4);
    }
    else {
      data = this.split(cmd, 4);
      //consoleLog(data);
    }
    int len = data.length;
    switch (data[0]) {
    case "anim":
      switch (len) {
      //case 1: anim(null, null, null); break; // anim;
      //case 2: anim(data[1], null, null); break; // anim this;
      case 3: anim(data[1], data[2], null); break; // anim this grow;
      case 4: anim(data[1], data[2], data[3]); break; // anim this grow 2;
      default:
        sb.append("Please specify a properly formatted animation")
        .append(" (ex: anim this hop 25;).");
        break;
      }
      break;
    case "appear":
      appear();
      break;
    case "become":
    case "set":
      if (len == 4) {
        become(data[1], data[2], data[3]);
      }
      else {
        sb.append("Please specify the correct number of parameters (ex: ")
        .append(data[0])
        .append(" Peter role Pete).");
      }
      break;
    case "breakpoint":
      breakpoint();
      break;
    case "build": // make
    case "append": // = build
    case "prepend":
    case "before":
    case "after":
      if (len == 2) {
        build(data[1], null, null, data[0]);
      }
      else if (len == 4) {
        build(data[1], data[2], data[3], data[0]);
      }
      else {
        sb.append("Please specify something to ")
        .append(data[0])
        .append(" (ex: ")
        .append(data[0])
        .append(" Car).");
      }
      break;
    case "drop": // leave discard
      if (len > 1) {
        drop(data[1]);
      }
      else {
        dropAll();
      }
      break;
    case "eat":
    case "unbuild": // unmake null void unbuild annihilate remove destroy obliterate undo erase delete
      if (len > 1) {
        eat(data[1], "eat".equals(data[0]) ? "Yum." : "Unbuilt.");
      }
      else {
        eatAll("eat".equals(data[0]) ? "Yummy." : "Unbuilt all.");
      }
      break;
    case "enter":
      if (len == 1) {
        enter(null);
      }
      else {
        enter(data[1]);
      }
      //else {
      //  sb.append("Please specify which place to enter (ex: enter castle_32).");
      //}
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
    case "get":
      if (len == 3) {
        sb.append(getAttributeVal(data[1], data[2]));
      }
      else {
        sb.append("Please specify the correct number of parameters (ex: get Peter role).");
      }
      break;
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
    case "group":
      if (len > 3) {
        group(data[1], data[2], data[3]);
      }
      else {
        sb.append("Please specify the correct number of parameters (ex: group Ja,El,Ma,Ca,Ly in Coach).");
      }
      break;
    case "help":
      help();
      break;
    case "i":
    case "inventory":
      inventory();
      break;
    case "if":
      if (len == 3) {
        iiff(data[1], data[2]);
      }
      else if (len > 3) {
        iiff(data[1], data[2] + " " + data[3]);
      }
      else {
        sb.append("Please specify the correct number of parameters (ex: if xpath(expr) command).");
      }
      break;
    case "ifeq":
    case "ifne":
    case "iflt":
    case "ifgt":
    case "ifle":
    case "ifge":
      if (len > 3) {
        iiff(data[0].substring(2), data[1], data[2] + " " + data[3]);
      }
      else {
        sb.append("Please specify the correct number of parameters (ex: ifeq xpath(expr) attrName attrValue command).");
      }
      break;
    case "lead": // lead a follower
    {
      if (len > 1) {
        IXholon node = null;
        if (LEAD_PARENT_NODE.equals(data[1])) {
          // this Avatar will lead its centext node (the node that it's inside of)
          node = contextNode;
        }
        else {
          node = findNode(data[1], contextNode);
        }
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
    case "out":
      if (len == 3) {
        out(data[1], data[2]);
      }
      else if (len > 3) {
        out(data[1], data[2] + " " + data[3]);
      }
      else {
        sb.append("Please specify a list of destinations and some text")
        .append(" (ex: out caption,speech One two three).");
      }
      break;
    case "param":
      if (len > 2) {
        param(data[1], data[2], data[3]);
      }
      else {
        sb.append("Please specify the name and value of the param (ex: param loop false).");
      }
      break;
    case "prev":
      if (len > 1) {
        prev(data[1]);
      }
      else {
        prev();
      }
      break;
    case "put":
      if (len > 3) {
        put(data[1], data[2], data[3]);
      }
      else {
        sb.append("Please specify the correct number of parameters (ex: put dino in museum).");
      }
      break;
    case "script":
      sb.append("The script command can only be used as the first in a sequence of commands.");
      break;
    case "search":
      if (len > 1) {
        search(data[1]);
      }
      else {
        sb.append("Please specify what to search (ex: search box_13).");
      }
      break;
    case "smash": // flatten
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
    case "wait": // Z
      // TODO wait random(low,high)
      if (len > 1) {
        try {
          if (len == 2) { // wait relative
            // subtract 1 to account for "wait N" already taking up 1 timestep
            // "wait 1" is equivalent to a single blank line in the script
            waitCount = Integer.parseInt(data[1]) - 1;
          }
          else { // wait til absolute
            waitCount = Integer.parseInt(data[2]) - actTimeStep;
          }
          if (waitCount < 0) {
            waitCount = 0;
          }
        } catch(NumberFormatException e) {
          sb.append("The wait time must be specified as an integer (ex: wait 10 OR wait til 123)");
        }
      }
      else {
        waitCount = WAITCOUNT_HIGH;
      }
      break;
    default:
      // TODO check for app-specific commands
      sb.append(data[0]).append(" is not a verb I recognise.");
      break;
    }
  }
  
  /**
   * Do a simple animation.
   * A simple animation does not change the model. It only effects the view.
   * Many of these are based on ScratchJr blocks.
   * anim THING turnright|turnleft|hop|grow|shrink|resetsize|hide|show PARAMS
   * @param thing 
   * @param animType 
   * @param params 
   */
  protected void anim(String thing, String animType, String params) {
    if (thing == null) {return;}
    if (animType == null) {return;}
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
      double duration = app.getTimeStepInterval() / 1000;
      if (viewEle == null) {
        viewEle = querySelector(DEFAULT_VIEWELE_STR);
      }
      if (viewEle == null) {
        viewEle = querySelector(ALTERNATE_VIEWELE_STR);
      }
      switch (animType) {
      case "hop":
        if (params == null) {params = DEFAULT_HOP_PARAMS;}
        makeJsObject(node, "anim", "{\"hop\": " + params + "}");
        animView(node, viewEle, duration);
        break;
      case "duck": // a downwards hop
        if (params == null) {params = DEFAULT_HOP_PARAMS;}
        makeJsObject(node, "anim", "{\"duck\": " + params + "}");
        animView(node, viewEle, duration);
        break;
      case "turnright":
        if (params == null) {params = DEFAULT_TURN_PARAMS;}
        makeJsObject(node, "anim", "{\"turnright\": " + params + "}");
        animView(node, viewEle, duration);
        break;
      case "turnleft":
        if (params == null) {params = DEFAULT_TURN_PARAMS;}
        makeJsObject(node, "anim", "{\"turnleft\": " + params + "}");
        animView(node, viewEle, duration);
        break;
      case "grow":
        if (params == null) {params = DEFAULT_GROW_PARAMS;}
        makeJsObject(node, "anim", "{\"grow\": " + params + "}");
        animView(node, viewEle, duration);
        break;
      case "shrink":
        if (params == null) {params = DEFAULT_GROW_PARAMS;}
        makeJsObject(node, "anim", "{\"shrink\": " + params + "}");
        animView(node, viewEle, duration);
        break;
      case "mirror":
        if (params == null) {params = DEFAULT_MIRROR_PARAMS;}
        makeJsObject(node, "anim", "{\"mirror\": \"" + params + "\"}");
        animView(node, viewEle, duration);
        break;
      case "hide":
        makeJsObject(node, "anim", "{\"hide\": " + true + "}");
        animView(node, viewEle, duration);
        break;
      case "show":
        makeJsObject(node, "anim", "{\"show\": " + true + "}");
        animView(node, viewEle, duration);
        break;
      default:
        // this may be a JSON string specifying one or more animations
        makeJsObject(node, "anim", animType);
        break;
      }
    }
  }
  
  protected native void makeJsObject(IXholon xhnode, String attrName, String jsonStr) /*-{
    xhnode[attrName] = $wnd.JSON.parse(jsonStr);
  }-*/;
  
  protected native void animView(IXholon xhnode, Element view, double duration) /*-{
    if ($wnd.xh.anim) {
      $wnd.xh.anim(xhnode, view, duration);
    }
  }-*/;
  
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
   * TODO optionally write this change to Meteor
   * @param thing - The name of something (ex: "car"), or "this" to specify this avatar.
   * @param whatChanges - "role" or "type" (only "role" is currently implemented.
   * @param newRoleOrType - The new roleName for the thing.
   */
  protected void become(String thing, String whatChanges, String newValue) {
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
        node.setRoleName(newValue);
      }
      else if ("val".equals(whatChanges)) {
        node.setVal(Double.parseDouble(newValue));
      }
      else if ("str".equals(whatChanges)) {
        node.setVal_String(newValue);
      }
      else if ("type".equals(whatChanges)) {
        sb.append("become X type Y  is not yet implemented");
        return;
      }
      else {
        //boolean rc = 
        setAttributeValNative(node, whatChanges, newValue);
        //if (!rc) {
        //  sb.append("Please specify either role or type (ex: become Robert role Bob)");
        //}
      }
      if (meteor && (meteorService != null)) {
        String[] data = {newValue, "add", "@" + whatChanges};
        meteorService.sendSyncMessage(-3897, data, node);
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
   * @param pos Desired position of the new thing relative to the context node
   *   ("append" "prepend" "before" "after")
   */
  protected void build(String thing, String ignore, String roleName, String pos) {
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
    XholonHelperService xhs = (XholonHelperService)app.getService(IXholonService.XHSRV_XHOLON_HELPER);
    switch (pos) {
    case "prepend":
      xhs.pasteFirstChild(contextNode, thing);
      break;
    case "before":
      xhs.pasteBefore(contextNode, thing);
      break;
    case "after":
      xhs.pasteAfter(contextNode, thing);
      break;
    case "append":
    case "build":
    default:
      xhs.pasteLastChild(contextNode, thing);
      pos = "append"; // meteor doesn't know about "build"
      break;
    }
    /*((XholonHelperService)app
      .getService(IXholonService.XHSRV_XHOLON_HELPER))
        .pasteLastChild(contextNode, thing);*/
    
    // Meteor
    //IXholon meteorService = app.getService(IXholonService.XHSRV_METEOR_PLATFORM);
    if (meteor && (meteorService != null)) {
      consoleLog("Avatar found the meteor service, and it's enabled");
      // send a SIG_SHOULD_WRITE_REQ message
      //meteorService.sendSyncMessage(-3896, true, this);
      // send a SIG_COLL_INSERT_REQ message
      // TODO pass the pos
      String[] data = {thing, "add", pos};
      meteorService.sendSyncMessage(-3897, data, contextNode);
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
   * Eat or unbuild a specified thing in the avatar's inventory.
   * This removes the thing from the Xholon tree.
   * @param thing The Xholon name of the thing to eat or unbuild.
   * @param response The response string.
   * TODO perhaps only eat it if it's a normally human-edible thing.
   *   But, the avatar could be some sort of people-eating monster, or a rock eater, etc.
   */
  protected void eat(String thing, String response) {
    IXholon node = findNode(thing, this);
    if (node != null) {
      // optionally write this change to Meteor
      if (meteor && (meteorService != null)) {
        String[] data = {null, "remove"};
        meteorService.sendSyncMessage(-3897, data, node);
      }
      node.removeChild();
      sb.append(response);
    }
    else {
      sb.append("You're not carrying any such thing. You need to take it first.");
    }
  }
  
  /**
   * Eat or unbuild everything in the avatar's inventory.
   */
  protected void eatAll(String response) {
    IXholon node = this.getFirstChild();
    while (node != null) {
      // optionally write this change to Meteor
      if (meteor && (meteorService != null)) {
        String[] data = {null, "remove"};
        meteorService.sendSyncMessage(-3897, data, node);
      }
      IXholon sib = node.getNextSibling();
      node.removeChild();
      node = sib;
    }
    sb.append(response);
  }
  
  /**
   * Move the avatar to the specified thing, which must be located inside the current contextNode.
   * @param thing The Xholon name of the thing to move to.
   */
  protected void enter(String thing) {
    IXholon node = null;
    if (thing == null) {
      node = contextNode.getFirstChild();
    }
    else {
      node = findNode(thing, contextNode);
    }
    if (node != null) {
      if (isContainerOrSupporter(node)) {
        /*if (this.hasParentNode()) {
          this.removeChild();
          this.appendChild(node);
        }
        contextNode = node;
        sb.append("Entered ").append(makeNodeName(node));*/
        moveto(node, "Entered");
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
      /*if (this.hasParentNode()) {
        this.removeChild();
        this.appendChild(node);
      }
      contextNode = node;
      sb.append("Exited to ").append(makeNodeName(node));*/
      moveto(node, "Exited to");
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
    
    switch (portName) {
    case "next":
      if (nextTarget == null) {next();}
      else {next(nextTarget);}
      return;
    case "prev":
      if (nextTarget == null) {prev();}
      else {prev(nextTarget);}
      return;
    case "N": goPort(IGrid.P_NORTH); return;
    case "E": goPort(IGrid.P_EAST); return;
    case "S": goPort(IGrid.P_SOUTH); return;
    case "W": goPort(IGrid.P_WEST); return;
    case "NE": goPort(IGrid.P_NORTHEAST); return;
    case "SE": goPort(IGrid.P_SOUTHEAST); return;
    case "SW": goPort(IGrid.P_SOUTHWEST); return;
    case "NW": goPort(IGrid.P_NORTHWEST); return;
    default: break;
    }
    
    if (portName.startsWith("port")) {
      // port0 port1 etc.
      if ((portName.length() > 4) && (Misc.getNumericValue(portName.charAt(4)) != -1)) {
        int portNum = Misc.atoi(portName, 4);
        goPort(portNum);
      }
      else {
        // ex: portx
        sb.append("Can't go ").append(portName);
      }
      return;
    }
    
    else if (portName.startsWith("xpath")) {
      IXholon node = evalXPathCmdArg(portName, contextNode);
      if (node != null) {
        moveto(node, null);
      }
      else {
        sb.append("Can't go " + portName + ". ");
      }
      return;
    }
    else if (portName.startsWith("$")) {
      evalDollarCmdArg(portName);
      return;
    }
    Object[] portArr = contextNode.getAllPorts().toArray();
    if ((portArr != null) && (portArr.length > 0)) {
      String foundPortNames = "";
      for (int i = 0; i < portArr.length; i++) {
        PortInformation pi = (PortInformation)portArr[i];
        String foundPortName = pi.getFieldName();
        if (foundPortName.startsWith(portName)) {
          moveto(pi.getReffedNode(), null);
          return;
        }
        else {
          foundPortNames += " " + foundPortName;
          if ("port".equals(foundPortName)) {
            foundPortNames += pi.getFieldNameIndexStr();
          }
        }
      }
      sb.append("Can only go").append(foundPortNames);
    }
    else {
      sb.append("Can't go ").append(portName);
    }
  }
  
  /**
   * go port
   */
  protected void goPort(int index) {
    moveto(contextNode.getPort(index), null);
  }
  
  /**
   * next
   */
  protected void next() {
    IXholon node = contextNode.getNextSibling();
    if (node == null) {
      if (loop) {
        moveto(contextNode.getFirstSibling(), null);
      }
      else {
        sb.append("Can't go next.");
      }
    }
    else {moveto(node, null);}
  }
  
  /**
   * next
   * @param nextTarget
   */
  protected void next(String nextTarget) {
    IXholon node = contextNode.getNextSibling();
    if (nextTarget.startsWith(WILDCARD)) {
      if (nextTarget.length() < 2) {
        next();
        return;
      }
      while (node != null) {
        if (makeNodeName(node).indexOf(nextTarget.substring(1)) > -1) {
          break;
        }
        node = node.getNextSibling();
      }
    }
    else {
      while (node != null) {
        if (makeNodeName(node).startsWith(nextTarget)) {
          break;
        }
        node = node.getNextSibling();
      }
    }
    if (node != null) {
      moveto(node, null);
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
        moveto(contextNode.getLastSibling(), null);
      }
      else {
        sb.append("Can't go prev.");
      }
    }
    else {moveto(node, null);}
  }
  
  /**
   * prev
   * @param prevTarget
   */
  protected void prev(String prevTarget) {
    IXholon node = contextNode.getPreviousSibling();
    if (prevTarget.startsWith(WILDCARD)) {
      if (prevTarget.length() < 2) {
        prev();
        return;
      }
      while (node != null) {
        if (makeNodeName(node).indexOf(prevTarget.substring(1)) > -1) {
          break;
        }
        node = node.getPreviousSibling();
      }
    }
    else {
      while (node != null) {
        if (makeNodeName(node).startsWith(prevTarget)) {
          break;
        }
        node = node.getPreviousSibling();
      }
    }
    if (node != null) {
      moveto(node, null);
    }
    else {
      sb.append("Can't find prev " + prevTarget);
    }
  }
  
  /**
   * Move to a specified node.
   * TODO add a new input arg: absRel
   * @param node 
   * @param sbText The first part of the text to write out if the move is successful.
   *   if this is null, then use the default
   *   ex: "Moving to" (default) "Exited to" "Entered"
   * @param absRel whether target xpath is absolute or relative
   *   if this is null, then use the default which is "absolute"
   *   ex: null "exit" "enter" "next" "prev"
   *   "exit"  ".." "./ancestor::NODENAME"
   *   "enter" "./NODENAME"
   *   "next"  "./following-sibling::*" "./following-sibling::NODENAME"
   *   "prev"  "./preceding-sibling::*" "./preceding-sibling::NODENAME"
   */
  protected void moveto(IXholon node, String sbText) {
    if (node == null) {
      sb.append("Can't move to node null.");
      return;
    }
    sb.append(sbText == null ? "Moving to " : sbText + " ").append(makeNodeName(node));
    if (this.hasParentNode()) {
      if (meteormove && (meteorService != null)) {
        String targetXpathExpr = xpath.getExpression(app.getRoot(), node);
        if (targetXpathExpr != null) {
          String[] data = {targetXpathExpr, "move", "append"};
          meteorService.sendSyncMessage(-3897, data, this);
        }
      }
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
    .append("\nanim THING turnright|turnleft|hop|duck|grow|shrink|mirror PARAMS")
    .append("\nappear")
    .append("\nbecome THING role ROLE")
    .append("\nbreakpoint")
    .append("\nbuild|append|prepend|before|after THING [role ROLE]")
    .append("\n[COMMENT]")
    .append("\ndrop [THING]")
    .append("\nenter [*]THING")
    .append("\nexamine|x THING")
    .append("\nexit [THING]")
    .append("\nfollow LEADER_THING")
    .append("\nget THING NAME")
    .append("\ngo portName|next|prev|N|E|S|W|NE|SE|SW|NW|port0|portN|xpath")
    .append("\ngroup THING1[,THINGi,...,THINGn] in|on|under THING2")
    .append("\nhelp")
    .append("\nif xpath command [elseif xpath command] [else command]")
    .append("\ninventory|i")
    .append("\nlead FOLLOWER_THING")
    .append("\nlook")
    .append("\nnext [[*]THING]")
    .append("\nout speech,caption,transcript,debug,all TEXT")
    .append("\nparam NAME VALUE")
    .append("\nprev [[*]THING]")
    .append("\nput THING1 in|on|under THING2")
    .append("\nsearch THING")
    .append("\nset THING NAME VALUE")
    .append("\nsmash THING")
    .append("\ntake [THING]")
    .append("\nunbuild|eat THING")
    .append("\nunfollow")
    .append("\nunlead")
    .append("\nvanish")
    .append("\nwait [DURATION]|[til TIMESTEP]")
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
   * If some other node exists (as specified by an XPath expression relative to this),
   * then do the specified command.
   * The entire if statement including the command must all be on the same line (for now).
   * if xpath(expr) command [elseif xpath(expr) command else command [endif]]
   * Examples:
   *  if xpath(parent::House) next garage;
   *  if xpath(Book) take *book;
   *  if xpath(Avatar/Book) drop *book;
   *  if xpath(Book) take *book else [no books left in library];
   *  if xpath(Avatar/Book) drop *book else [not carrying a book];
   * @param xpathExpr  ex: xpath(parent::House)
   * @param command  ex: become Robert role Bob
   */
  protected void iiff(String xpathExpr, String command) {
    IXholon node = evalXPathCmdArg(xpathExpr, contextNode);
    //consoleLog("if " + xpathExpr + " " + command);
    int elseifStart = command.indexOf(" elseif ");
    int elseStart = command.indexOf(" else ");
    if (node == null) {
      //consoleLog("search for elseif or else");
      if (elseifStart != -1) {
        //consoleLog(" elseif " + elseifStart);
        command = "if " + command.substring(elseifStart + 8);
        //consoleLog(command);
        processCommand(command);
      }
      else if (elseStart != -1) {
        //consoleLog(" else " + elseStart);
        command = command.substring(elseStart + 6);
        //consoleLog(command);
        processCommand(command);
      }
    }
    else {
      // remove any elseif or else clauses from the command
      //consoleLog("node was found");
      //consoleLog(node.getName(nameTemplate));
      if (elseifStart != -1) {command = command.substring(0, elseifStart);}
      else if (elseStart != -1) {command = command.substring(0, elseStart);}
      //consoleLog(command);
      processCommand(command);
    }
  }
  
  /**
   * If some other node exists (as specified by an XPath expression relative to this),
   * then do the specified command.
   * The entire if statement including the command must all be on the same line (for now).
   * ifeq xpath(expr) attrName attrValue command
   * Examples:
   * 
   * @param operation one of "eq ne lt gt le ge"
   * @param xpathExpr  ex: xpath(parent::House)
   * @param attrAndCommand  ex: val 123 become Robert role Bob
   */
  protected void iiff(String operation, String xpathExpr, String attrAndCommand) {
    //consoleLog(operation);
    //consoleLog(xpathExpr);
    //consoleLog(attrAndCommand);
    IXholon node = evalXPathCmdArg(xpathExpr, contextNode);
    int elseifStart = attrAndCommand.indexOf(" elseif ");
    int elseStart = attrAndCommand.indexOf(" else ");
    if (node == null) {
      if (elseifStart != -1) {
        attrAndCommand = "if " + attrAndCommand.substring(elseifStart + 8);
        processCommand(attrAndCommand);
      }
      else if (elseStart != -1) {
        attrAndCommand = attrAndCommand.substring(elseStart + 6);
        processCommand(attrAndCommand);
      }
    }
    else {
      if (elseifStart != -1) {attrAndCommand = attrAndCommand.substring(0, elseifStart);}
      else if (elseStart != -1) {attrAndCommand = attrAndCommand.substring(0, elseStart);}
      String[] attrAndCommandArr = attrAndCommand.split(" ", 3);
      if (attrAndCommandArr.length == 3) {
        String attrName = attrAndCommandArr[0];
        String attrValue = attrAndCommandArr[1];
        String command = attrAndCommandArr[2];
        //consoleLog(operation + " " + attrName + " " + attrValue + " " + command);
        switch (attrName) {
        case "val":
          try {
            double av = Double.parseDouble(attrValue);
            switch (operation) {
            // ifgt xpath(Book) val 5 next;
            case "eq": if (node.getVal() == av) {processCommand(command);} break;
            case "ne": if (node.getVal() != av) {processCommand(command);} break;
            case "lt": if (node.getVal() < av)  {processCommand(command);} break;
            case "gt": if (node.getVal() > av)  {processCommand(command);} break;
            case "le": if (node.getVal() <= av) {processCommand(command);} break;
            case "ge": if (node.getVal() >= av) {processCommand(command);} break;
            default: break;
            }
          } catch(NumberFormatException e) {
            sb.append("The attribute value must be a number (ex: 123  or  123.456)");
          }
          break;
        case "str":
          switch (operation) {
          // ifeq xpath(Book) str abc next;
          case "eq":
            if (attrValue.equals(node.getVal_String())) {processCommand(command);}
            break;
          case "ne":
            if (!attrValue.equals(node.getVal_String())) {processCommand(command);}
            break;
          default: break;
          }
          break;
        default:
          try {
            String av = (String)getAttributeValNative(node, attrName);
            switch (operation) {
            // ifeq xpath(Book) jsattr jsvalue next;
            case "eq":
              if (attrValue.equals(av)) {processCommand(command);}
              break;
            case "ne":
              if (!attrValue.equals(av)) {processCommand(command);}
              break;
            default: break;
            }
          } catch(Exception e) {
            sb.append("The attribute value must be a String");
          }
          break;
        }
      }
    }
  }
  
  /**
   * Get the value of a named attribute for a specified thing.
   */
  protected Object getAttributeVal(String thing, String attrName) {
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
      if ("role".equals(attrName)) {
        return node.getRoleName();
      }
      else if ("val".equals(attrName)) {
        return node.getVal();
      }
      else if ("str".equals(attrName)) {
        return node.getVal_String();
      }
      else if ("type".equals(attrName)) {
        return node.getXhcName();
      }
      else {
        return getAttributeValNative(node, attrName);
      }
    }
    return null;
  }
  
  protected native Object getAttributeValNative(IXholon node, String attrName) /*-{
	  return node[attrName];
	}-*/;
	
	protected native void setAttributeValNative(IXholon node, String attrName, Object attrValue) /*-{
	  //if (node[attrName]) {
	    node[attrName] = attrValue;
	    //return true;
	  //}
	  //return false;
	}-*/;
  
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
   * Write out text to one or more destinations.
   * This is independent of whether or not a param for that destination is true or false.
   * Examples:
out speech Flopsy, Mopsy, and Cottontail, who were good little bunnies, went down the lane to gather bananas
out all Two plus two equals five.
out speech,caption And they lived happily never after.
out canvas http://www.primordion.com/Xholon/abc/def.png
out canvas http://www.primordion.com/Xholon/gwtimages/peterrabbit/peter04.jpg
   * @param destinations A comma-separated list of output destinations, with no spaces between them.
   *   "speech" "caption" "transcript" "debug" "all" "caption,speech" and other combinations
   * @param text The text that should be written out.
   */
  protected void out(String destinations, String text) {
    String[] dests = destinations.split(",");
    for (int i = 0; i < dests.length; i++) {
      switch (dests[i]) {
      case "speech":
        this.speak(outPrefix + text, ssuLang, ssuVoice, ssuVolume, ssuRate, ssuPitch);
        break;
      case "caption":
        if (captionEle != null) {
          captionEle.setInnerText(outPrefix + text);
        }
        break;
      case "transcript":
        this.println(outPrefix + text);
        break;
      case "debug":
        this.consoleLog(outPrefix + text);
        break;
      case "all":
        // TODO do I really want this ?
        break;
      case "canvas":
        out2Canvas(text, "testing", "replace");
        break;
      default:
        break;
      }
    }
  }
  
  /**
   * Write a PNG or other image to the Xholon canvas.
   * @param url (ex: "http://www.primordion.com/Xholon/gwtimages/Middle-earth.jpg")
   *   (ex: "http://www.primordion.com/Xholon/gwtimages/peterrabbit/peter04.jpg")
   * @param canvasId 
   * @param mode "new" "replace" "fade|tween"
   */
  protected native void out2Canvas(String url, String canvasId, String mode) /*-{
    var xhcanvas = $doc.querySelector("div#xhcanvas");
    var canvas = null;
    if (mode == "new") {
      canvas = $doc.createElement("canvas");
      xhcanvas.appendChild(canvas);
    }
    else { // "replace"
      canvas = $doc.querySelector("div#xhcanvas>canvas");
      if (canvas == null) {
        canvas = $doc.createElement("canvas");
        xhcanvas.appendChild(canvas);
      }
    }
    canvas.id = canvasId;
    var ctx = canvas.getContext("2d");
    var image = new Image();
    image.onload = function() {
      canvas.width = image.width;
      canvas.height = image.height;
      ctx.drawImage(image, 0, 0);
    };
    image.src = url;
  }-*/;
  
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
   * Group things1 into thing2, where:
   *  things1 is a list of one or more of the avatar's siblings
   *  thing2 is the name of one of the avatar's siblings,
   *    or an XPath expression.
   * It combines take and put.
   * ex: "group Ja,El,Ma,Ca,Ly in Coach"
   * @param thing1 - 
   * @param in - the word "in"
   * @param thing2 - 
   */
  protected void group(String things1, String in, String thing2) {
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
    
    String[] arr1 = things1.split(",");
    for (int i = 0; i < arr1.length; i++) {
      IXholon node1 = findNode(arr1[i], contextNode);
      if (node1 == null) {
        sb.append("Can't find ").append(arr1[i]);
        //return;
      }
      else {
        node1.removeChild();
        node1.appendChild(node2);
      }
    }
    
    sb.append("Grouped.");
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
   * TODO optionally write this change to Meteor
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
   * @param valueRest The rest of the value, in case it contains one or more spaces.
   */
  protected void param(String name, String value, String valueRest) {
    //consoleLog(name + " " + value + " " + valueRest);
    switch (name) {
    case "caption":
      //if ("null".equals(value)) {captionEle = null;}
      //else {captionEle = querySelector(value);}
      
      switch (value) {
      case "true": caption = true; break;
      case "false": caption = false; break;
      case "null": captionEle = null; break;
      default: captionEle = querySelector(value); break;
      }
      break;
    case "view":
      switch (value) {
      case "null": viewEle = null; break;
      default: viewEle = querySelector(value); break;
      }
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
    case "speech":
      switch (value) {
      case "true": speech = true; break;
      case "false": speech = false; break;
      case "toggle": speech = !speech; break;
      default:
        if (value.charAt(0) == '{') {
          // parse this JSON string, and extract the values
          // ex: {"lang":"en-US", "voice":"Google UK English Female", "volume":1.0, "rate":1.2, "pitch":1.0}
          speech = true;
          try {
            JSONObject json = JSONParser.parseStrict(value + valueRest).isObject();
            if (json != null) {
              if (json.containsKey("lang")) {
                JSONString str = json.get("lang").isString();
                if (str != null) {
                  this.ssuLang = str.stringValue();
                }
              }
              if (json.containsKey("voice")) {
                JSONString str = json.get("voice").isString();
                if (str != null) {
                  this.ssuVoice = findVoice(str.stringValue());
                }
              }
              if (json.containsKey("volume")) {
                JSONNumber num = json.get("volume").isNumber();
                if (num != null) {
                  this.ssuVolume = (float)num.doubleValue();
                }
              }
              if (json.containsKey("rate")) {
                JSONNumber num = json.get("rate").isNumber();
                if (num != null) {
                  this.ssuRate = (float)num.doubleValue();
                }
              }
              if (json.containsKey("pitch")) {
                JSONNumber num = json.get("pitch").isNumber();
                if (num != null) {
                  this.ssuPitch = (float)num.doubleValue();
                }
              }
            }
          } catch(JSONException e) {
            consoleLog(e);
          }
        }
        break;
      }
      if (speech) {
        if (!isSpeechSupported()) {
          // this browser does not support speech, so disable it
          consoleLog("this browser does not support speech");
          speech = false;
        }
      }
      break;
    case "meteor":
      switch (value) {
      case "true": meteor = true; break;
      case "false": meteor = false; break;
      default: break;
      }
      break;
    case "meteormove":
      switch (value) {
      case "true": meteormove = true; break;
      case "false": meteormove = false; break;
      default: break;
      }
      break;
    case "meteor+move": // this is a shortcut
      switch (value) {
      case "true": meteor = true; meteormove = true; break;
      case "false": meteor = false; meteormove = false; break;
      default: break;
      }
      break;
    default:
      break;
    }
  }
  
  /**
   * Pause the app at the current Xholon timestep.
   * It's similar to a software breakpoint, but not as immediate.
   * Any actions remaining in this timestep will first be completed.
   * A timestep is equivalent to one line in a Avatar script,
   * possibly one line in each of the scripts of two or more simultaneously-active Avatars,
   * or other nodes with simultaneously-active Xholon.act() methods.
   */
  protected void breakpoint() {
    app.setControllerState(IControl.CS_PAUSED); // 4
  }
  
  /**
   * Query for a DOM Element, given a selector.
   * ex: var captionEle = document.querySelector("#xhanim>#one>p");
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
    if (nodeName.startsWith(WILDCARD)) {
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
    if (nodeName.startsWith("xpath")) {
      node = evalXPathCmdArg(nodeName, aRoot);
      if (node != null) {
        return node;
      }
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
  
  /**
   * Evaluate a command argument that starts with $ .
   * This is shorthand for a sequence of exit and enter commands.
   * Optionally, include one or more wait times.
   *   TODO don't handle these for now
   * @param cmdArg - $[ANCESTOR]>DESCENDANT
   * ex: "$Hert>Meryton>assembly"
   * ex: "$..>Meryton"
   * ex: "$>Meryton>house"  or  "$.>Meryton>house"
   * ex: "$Hert>1>Meryton>assembly"  ??? with wait times
   */
  protected void evalDollarCmdArg(String cmdArg) {
    String[] exitEnterArr = cmdArg.substring(1).split(DOLLAR_CHILD_SEPARATOR);
    if (exitEnterArr.length == 0) {return;}
    String expr = "";
    if (exitEnterArr[0].equals("..")) {
      expr += "exit;";
      exit(null);
    }
    else if (exitEnterArr[0].equals("")) {
      // there is no initial ancestor
    }
    else if (exitEnterArr[0].equals(".")) {
      // the initial ancestor is just the current contextNode
    }
    else {
      expr += "exit " + exitEnterArr[0] + ";";
      exit(exitEnterArr[0]);
    }
    for (int i = 1; i < exitEnterArr.length; i++) {
      expr += "enter " + exitEnterArr[i] + ";";
      enter(exitEnterArr[i]);
    }
    // TODO get this working correctly
    //doAction(expr); // NO ???
    consoleLog(expr);
  }
  
  /**
   * Speak the text.
   * ex: speak("The silly white rabbit jumped over the lion.");
   * TODO can I repeatedly reuse a single SpeechSynthesisUtterance instance?
   * @param text 
   */
  protected native void speak(String textToSpeak, String ssuLang, Object ssuVoice, float ssuVolume, float ssuRate, float ssuPitch) /*-{
    //$wnd.console.log("speak() " + typeof $wnd.speechSynthesis != "undefined");
    //$wnd.console.log(textToSpeak);
    // Firefox defines speechSynthesis but not SpeechSynthesisUtterance
    //if (typeof $wnd.speechSynthesis == "undefined") {return;}
    if (typeof $wnd.SpeechSynthesisUtterance == "undefined") {return;}
    var newUtterance = new $wnd.SpeechSynthesisUtterance();
    newUtterance.text = textToSpeak;
    if (ssuLang != null) {
      newUtterance.lang = ssuLang;
    }
    if (ssuVoice != null) {
      newUtterance.voice = ssuVoice;
    }
    if (ssuVolume != -1.0) {
      newUtterance.volume = ssuVolume;
    }
    if (ssuRate != -1.0) {
      newUtterance.rate = ssuRate;
    }
    if (ssuPitch != -1.0) {
      newUtterance.pitch = ssuPitch;
    }
    $wnd.speechSynthesis.speak(newUtterance); // add to the utterance queue
  }-*/;
  
  /**
   * Determine whether or not speech synthesis is supported for this browser.
   * @return true or false
   */
  protected native boolean isSpeechSupported() /*-{
    //$wnd.console.log("isSpeechSupported() " + typeof $wnd.speechSynthesis != "undefined");
    //return typeof $wnd.speechSynthesis != "undefined";
    return typeof $wnd.SpeechSynthesisUtterance != "undefined";
  }-*/;
  
  /**
   * Find a SpeechSynthesisVoice.
   * @param name
   * @return A SpeechSynthesisVoice object, or null.
   * ex: Object voice = findVoice("Google UK English Female");
   * 
   * TODO voices are loaded asynchronously, so need to wait for an event
window.speechSynthesis.onvoiceschanged = function() {
  var voices = $wnd.speechSynthesis.getVoices();
  
};
   */
  protected native Object findVoice(String name) /*-{
    var voices = $wnd.speechSynthesis.getVoices();
    for (var i = 0; i < voices.length; i++) {
      if (voices[i].name == name) {
        return voices[i];
      }
    }
    return null;
  }-*/;
  
}

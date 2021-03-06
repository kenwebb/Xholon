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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
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
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.io.xml.Xholon2Xml;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.XholonHelperService;
import org.primordion.xholon.service.meteor.IMeteorPlatformService;
import org.primordion.xholon.service.recipe.IRecipe;
import org.primordion.xholon.util.ClassHelper;
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
public class Avatar extends AbstractAvatar {
  
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
  protected static final String[] DEFAULT_OUTANIM = {"turnright", "30"}; // for use with "out anim"
  protected static final String TAKENOTES_COMMAND = ".. ";
  
  protected static final int SIG_FOLLOWLEADERTECH_CANON = 101;
  protected static final int SIG_GET_SCRIPT = 102; // get the remaining part of the Avatar script
  protected static final int SIG_GET_AVATAR_KEY_MAP_JSO = 103; // AvatarKeyMapJso
  
  protected static final int SPEECHOUT_NO_EFFECT = 0;
  protected static final int SPEECHOUT_ALWAYS    = 1;
  protected static final int SPEECHOUT_NEVER     = 2;
  
  protected static final String RB_DEFAULT_SERVICE_NAME = "RecipeService"; // recipe book default service name
  protected static final String RB_STYLE_NAME_MINECRAFT = "MinecraftStyleRecipeBook";
  protected static final String RB_STYLE_NAME_JSON_RULES_ENGINE = "JsonRulesEngineRecipeBook";
  protected static final String RB_STYLE_NAME_CEPTRE_JSON_RULES_ENGINE = "CeptreJsonRulesEngineRecipeBook";
  protected static final String RB_DEFAULT_STYLE_NAME = RB_STYLE_NAME_MINECRAFT; // recipe book default style name
  protected static final String RB_SEPARATOR = "-"; // separator between parts of a recipe book name
  
  // standard subtrees ST
  protected static final String SUBTREES_NAME_BEHAVIORS = "BehaviorsST";
  protected static final String SUBTREES_NAME_INVENTORY = "InventoryST";
  protected static final String SUBTREES_NAME_TOOLS     = "ToolsST"; // tools, catalysts
  protected static final String SUBTREES_DEFAULT_NAMES  = "BehaviorsST,InventoryST,ToolsST";
  
  /* MOVED TO AbstractAvatar
  // Variables
  public String roleName = null;
  protected double val;
  */
  
  // the current location of the avatar; its parent or the node it references
  protected IXholon contextNode = null;
  
  protected StringBuilder sb = null;
  
  /* MOVED TO AbstractAvatar
  // this is the name format required by findNode()
  protected String nameTemplate = IXholon.GETNAME_DEFAULT;
  */
  
  protected static IXPath xpath = null;
  
  // Parameters that can be set through a user command
  
  /**
   * Whether or not to loop with "go next" and "go prev".
   * "param loop true|false|toggle"
   */
  protected boolean loop = true;
  
  /**
   * Whether or not to repeat the current command sequence when it's finished.
   * "param repeat true|false|toggle [actionIxRepeatStart[,maxRepeats]]"
   * "param repeat true|false|toggle 3"
   * "param repeat true|false|toggle 3,10"
   * "param repeat true|false|toggle -1,10"
   */
  protected Object[] repeat = {false, ACTIONIX_INITIAL, Integer.MAX_VALUE};
  
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
   * Optional technique that the follower should use while following the leader.
   * "unison" "mirror" etc.  (see my Choreography workbook)
   */
  protected String followLeaderTechnique = null;
  
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
  protected String ssuLang = "en-US"; // default ex: "en-US"
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
  
  /* MOVED TO AbstractAvatar
  protected IApplication app = null;
  */
  
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
  
  /**
   * This is for use with "out anim".
   */
  protected String[] outAnim = DEFAULT_OUTANIM;
  
  /**
   * An optional start action that postConfigure() should take.
   * Typically this would be "go THING" to get this Avatar to its starting location.
   * ex: "xpath(One/Two[@roleName='Zwei']/Three)"
   * ex: "Speedy:degu"
   * ex: "Speedy"
   */
  protected String start = null;
  
  /**
   * An optional start position, relative to start.
   * Possible values: append prepend before after script
   */
  protected String startPos = null;
  
  /**
   * An optional end action that should be taken after the entire script is run.
   * Typically this would be "vanish" to remove this Avatar from the tree.
   */
  protected String end = null;
  
  /**
   * An optional instance of Chatbot, that will respond to requests that the avatar doesn't understand.
   */
  protected IXholon chatbot = null;
  
  /**
   * Whether or not to treat speech and other non-command input as notes to be recorded, or as errors.
   * This is mostly intended for use with speech recognition, to do speech-to-text.
   */
  protected boolean takenotes = false;
  
  /**
   * A recipe book that contains zero or more recipes.
   * The initial type of recipe book is in JSON format, and can be created using MinecraftStyleRecipe Book, and obtained from RecipeService.
   * For now, there can only be one unnamed recipe book.
   * TODO allow multiple named recipe books by using a Map
   */
  protected Object[] recipebook = new Object[2];
  
  /**
   * Whether or not to automatically update the context node when the user selects (clicks, touches) some other node.
   */
  protected boolean setCtxtOnselect = true;
  
  /**
   * Each Avatar can optionally have its own key map,
   * which associates keyboard keys with avatar actions, and is stored in JSON string format.
   */
  protected JavaScriptObject avatarKeyMap = null;
  
  /**
   * Whether or not this Avatar should request that it receive all key events.
   */
  protected boolean shouldReceiveKeyEvents = false;
  
  // constructor
  public Avatar() {}
  
  // Setters and Getters
  
  /* MOVED TO AbstractAvatar
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
  */
  
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
  public void setVal_Object(Object contextNode) {
    if (setCtxtOnselect && (contextNode != this)) {
      setContextNode((IXholon)contextNode);
    }
  }
  
  protected void setContextNode(IXholon contextNode) {
    if (this.contextNode != contextNode) {
      this.contextNode = contextNode;
      pushHistory(this.contextNode);
      if (chatbot != null) {
        chatbot.setVal_Object(contextNode);
      }
    }
    trace(this.contextNode); // TODO put this line just after pushHistory() above ?
  }
  
  protected native void pushHistory(IXholon contextNode) /*-{
    if (this["history"]) {
      this["history"].pushHistory(contextNode);
    }
  }-*/;
  
  protected native IXholon popHistory() /*-{
    if (this["history"]) {
      return this["history"].popHistory();
    }
    return null;
  }-*/;
  
  @Override
  public Object getVal_Object() {
    return this.contextNode;
  }
  
  /* MOVED TO AbstractAvatar
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
  */
  
  @Override
  public void postConfigure() {
    app = this.getApp();
    xpath = this.getXPath();
    if (isXhSvgAnimRequired()) {
      HtmlScriptHelper.requireScript("xhSvgAnim", null);
    }
    meteorService = app.getService(IXholonService.XHSRV_METEOR_PLATFORM);
    String defaultContent = this.getXhc().getDefaultContent();
    if (defaultContent != null) {
      defaultContent = defaultContent.trim();
      if (defaultContent.length() > 0) {
        if (this.isXml(defaultContent)) {
          // append the defaultContent XML String to this Avatar as inventory
          this.build(defaultContent, null, null, "buildandtake");
        }
        else {
          // make the defaultContent the Avatar behavior
          this.setVal_String(defaultContent);
        }
      }
    }
    if ((this.getFirstChild() != null) && ("Attribute_String".equals(this.getFirstChild().getXhcName()))) {
      this.setVal_String(this.getFirstChild().getVal_String());
      this.outPrefix = this.getName(IXholon.GETNAME_ROLENAME_OR_CLASSNAME) + ": ";
      this.getFirstChild().removeChild();
    }
    if ((this.getFirstChild() != null) && ("Attribute_String".equals(this.getFirstChild().getXhcName()))) {
      if ((this.getFirstChild().getRoleName() != null) && ("akm".equals(this.getFirstChild().getRoleName()))) {
        this.setAvatarKeyMap(this.getFirstChild().getVal_String());
        this.getFirstChild().removeChild();
        if (this.shouldReceiveKeyEvents) {
          // call IApplication method that will set things up so that this Avatar will be called to handle each key event
          app.addNonSystemAvatar4KeyEvents(this);
        }
      }
    }
    setStartContextNode();
    this.setSpeechOut(SPEECHOUT_NO_EFFECT);
    super.postConfigure();
  }
  
  /**
   * Is this an XML String?
   * @param str
   * @return true or false
   */
  protected boolean isXml(String str) {
    if (str.charAt(0) == '<') {
      return true;
    }
    else {
      return false;
    }
  }
  
  /**
   * Set the starting context node.
   * Handles start if only a roleName is specified (search the whole tree).
   * Handle roleName that contains spaces.
   * TODO handle xhcName with wrong case (ex: "degu" instead of "Degu") ?
   * examples:
<Avatar roleName="Harry" start="World" startPos="append">
  <Attribute_String><![CDATA[
    build School role Hogwarts;
    enter Hogwarts;
    look;
  ]]></Attribute_String>
</Avatar>

<Avatar start="Harry" startPos="script">
  <Attribute_String><![CDATA[
    build Classroom role Dummy;
    enter Dummy;
    look;
  ]]></Attribute_String>
</Avatar>

<Avatar roleName="Suzie" start="Hogwarts" end="vanish">
  <Attribute_String><![CDATA[
    build School role Abc;
    enter Abc;
    look;
  ]]></Attribute_String>
</Avatar>

<Avatar roleName="Abbie" start="Hogwarts:School">
  <Attribute_String><![CDATA[
    build School role def;
    enter def;
    look;
  ]]></Attribute_String>
</Avatar>
   */
  protected void setStartContextNode() {
    if (start == null) {
      //contextNode = this.getParentNode();
      setContextNode(this.getParentNode());
    }
    else {
      IXholon newContextNode = null;
      if (start.startsWith("xpath(")) {
        newContextNode = evalXPathCmdArg(start, this.getRootNode());
      }
      else {
        // roleName(ex: "Speedy")  xhcName(ex: "Degu")  roleName:xhcName(ex: "Speedy:Degu")
        String[] names = start.split(":");
        String xpathExpr = null;
        IXholon rootNode = this.getRootNode();
        if (names.length == 1) {
          // this might be a roleName
          XholonHelperService xhs = (XholonHelperService)app.getService(IXholonService.XHSRV_XHOLON_HELPER);
          newContextNode = xhs.findFirstDescWithRoleName(rootNode, names[0]);
          if (newContextNode == null) {
            // or it might be a xhcName
            xpathExpr = "descendant::" + names[0];
            newContextNode = xpath.evaluate(xpathExpr, rootNode);
          }
        }
        else {
          xpathExpr = "descendant::" + names[1] + "[@roleName='" + names[0] + "']";
          newContextNode = xpath.evaluate(xpathExpr, rootNode);
        }
      }
      sb = new StringBuilder();
      switch (startPos) {
      case "script":
        if (newContextNode == null) {
          // TODO if possible, treat this as an "append"
        }
        else if (ClassHelper.isAssignableFrom(Avatar.class, newContextNode.getClass())) {
          // transfer the script to the existing Avatar, and then vanish
          String actionz = "";
          for (int i = 0; i < this.actions.length; i++) {
            if (i == 0) {
              actionz += this.actions[i];
            }
            else {
              actionz += "\n" + this.actions[i];
            }
          }
          ((Avatar)newContextNode).setVal_String(actionz);
          this.vanish();
        }
        break;
      case "prepend":
      case "before":
      case "after":
        // TODO
        break;
      case "append":
      default: // null
        this.moveto(newContextNode, null);
        break;
      } // end switch
      
    }
  }
  
  /**
   * Do optional end action(s).
   */
  protected void end() {
    if (end != null) {
      consoleLog("end() " + end);
      this.processCommands(end);
      end = null;
    }
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
  
  /**
   * Is this the system Avatar?
   */
  protected native boolean isSystemAvatar() /*-{
    if (this == $wnd.xh.avatar()) {
      return true;
    }
    else {
      return false;
    }
  }-*/;
  
  @Override
  public void act() {
    // check to see if this node has already been called this timestep; and allow it to catch up if it missed being called on previous timesteps
    int ts = app.getTimeStep();
    
    // guard against this Avatar moving, and then having its nextSibling unavailable
    IXholon nextSib = nextSibling;
    
    while (actTimeStep < ts) {
      actTimeStep++;
      if (leader != null) {
        // I must follow the leader
        //consoleLog(this.getName() + " is following (accompanying) " + leader.getName());
        // there is no need to do anything if the leader and this Avatar are already co-located
        // prevent Avatar from being lead to the impossible postion of being directly inside itself
        if ((contextNode != leader.getParentNode()) && (this != leader.getParentNode())) {
          this.removeChild();
          this.appendChild(leader.getParentNode());
        }
        if (followLeaderTechnique != null) {
          doFollowLeaderTechnique();
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
        else if ((boolean)repeat[0]) {
          int maxRepeats = (int)repeat[2] - 1;
          if (maxRepeats <= 0) {
            repeat[0] = Boolean.FALSE;
          }
          else {
            actionIx = (int)repeat[1];
          }
          repeat[2] = new Integer(maxRepeats);
        }
        else {
          end();
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
          if (this.hasParentNode()) {
            // the avatar is visible
            follower.appendChild(this.getParentNode());
          }
          else {
            // the avatar is invisible, and has no parent in the CSH
            follower.appendChild(contextNode);
          }
          // TODO if follower is an Avatar, then change it's contextNode
           // handled by local appendChild(...)
        }
      }
      
      if (chatbot != null) {
        chatbot.act();
      }
      
    }
    
    // support user-defined Avatar behaviors
    IXholon bnode = this.subtree(SUBTREES_NAME_BEHAVIORS);
    if (bnode != null) {
      bnode.act();
    }
    
    //super.act();
    if (firstChild != null) {
      firstChild.act();
    }
    if (nextSib != null) {
      nextSib.act();
    }
  }
  
  /**
   * Optionally do a follow leader technique.
   * Example that can be pasted into the AYA play:
<_-.avatars>
  <Avatar roleName="Unison">follow AYA unison;</Avatar>
  <Avatar roleName="Mirror">follow AYA mirror;</Avatar>
  <Avatar roleName="Canon">follow AYA canon;</Avatar>
</_-.avatars>
   */
  protected void doFollowLeaderTechnique() {
    String leaderAction = this.leader.getVal_String();
    if (leaderAction != null) {
      // assume that there is only one action in the String
      leaderAction = leaderAction.trim();
      if (leaderAction.startsWith("anim ")) {
        consoleLog(leaderAction);
        switch (this.followLeaderTechnique) {
        case "unison":
          // do identical action
          this.doAction(leaderAction);
          break;
        case "canon":
          // delay the action by one timestep
          this.sendMessage(SIG_FOLLOWLEADERTECH_CANON, leaderAction, this);
          break;
        case "mirror":
          // do a mirrored version of the action
          if (leaderAction.endsWith(";")) {
            leaderAction = leaderAction.substring(0, leaderAction.length()-1);
          }
          String[] animArr = leaderAction.split(" ", 4);
          switch (animArr[2]) {
          case "hop":
            animArr[2] = "duck";
            break;
          case "duck":
            animArr[2] = "hop";
            break;
          case "turnright":
            animArr[2] = "turnleft";
            break;
          case "turnleft":
            animArr[2] = "turnright";
            break;
          case "grow":
            animArr[2] = "shrink";
            break;
          case "shrink":
            animArr[2] = "grow";
            break;
          case "mirror":
            // anim this mirror [x|y]
            if (animArr.length == 3) { // the default is "x", so use "y"
              animArr = "anim this mirror y".split(" ", 4);
            }
            else {
              if ("x".equals(animArr[3])) {
                animArr[3] = "y";
              }
              else {
                animArr[3] = "x";
              }
            }
            break;
          default: break;
          }
          String animStr = "";
          for (int i = 0; i < animArr.length; i++) {
            animStr += animArr[i] + " ";
          }
          this.doAction(animStr);
          break;
        default: break;
        }
      }
    }
  } // end doFollowLeaderTechnique()
  
  @Override
  public void processReceivedMessage(IMessage msg) {
    switch (msg.getSignal()) {
    case ISignal.SIGNAL_XHOLON_CONSOLE_REQ:
      String responseStr = processCommands((String)msg.getData());
      if ((responseStr != null) && (responseStr.length() > 0)) {
        if (!takenotes) {
          responseStr = COMMENT_START + responseStr + COMMENT_END + CMD_SEPARATOR;
        }
        msg.getSender().sendMessage(ISignal.SIGNAL_XHOLON_CONSOLE_RSP, "\n" + responseStr, this);
      }
      break;
    case SIG_FOLLOWLEADERTECH_CANON:
      this.doAction((String)msg.getData());
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
        if (!takenotes) {
          responseStr = COMMENT_START + responseStr + COMMENT_END + CMD_SEPARATOR;
        }
      }
      return new Message(ISignal.SIGNAL_XHOLON_CONSOLE_RSP, "\n" + responseStr, this, msg.getSender());
    case SIG_GET_SCRIPT: // 102
      String scriptStr = "";
      for (int i = this.actionIx + 1; i < actions.length; i++) {
        scriptStr += actions[i] + "\n";
      }
      return new Message(SIG_GET_SCRIPT, scriptStr, this, msg.getSender());
    case SIG_GET_AVATAR_KEY_MAP_JSO: // 103
      return new Message(SIG_GET_AVATAR_KEY_MAP_JSO, this.getAvatarKeyMapJso(), this, msg.getSender());
    default:
      return super.processReceivedSyncMessage(msg);
    }
  }
  
  @Override
  public void doAction(String action) {
    //this.consoleLog("" + app.getTimeStep() + " " + this.getName() + " " + action.substring(0, action.length() > 40 ? 40 : action.length()));
    this.setCurrentActions(action);
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
    setContextNode(this.getParentNode());
  }
  
  @Override
  public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {
    super.toXmlAttributes(xholon2xml, xmlWriter);
    String str = "";
    int xmlActionIx = 0;
    // optionally set xmlActionIx to the current value of this.actionIx
    if ((app != null) && (this.hasAncestor(app.getSrvRoot().getName()))) {
      // this Avatar is probably about to be sent to another app using RemoteNodeService
      xmlActionIx = this.actionIx + 1;
    }
    for (int i = xmlActionIx; i < actions.length; i++) {
      str += actions[i] + "\n";
    }
    if (str.length() > 0) {
      xmlWriter.writeStartElement("Attribute_String");
      //xmlWriter.writeText("<![CDATA[\n" + str + "]]>");
      xmlWriter.writeText(makeTextXmlEmbeddable(str));
      xmlWriter.writeEndElement("Attribute_String");
    }
  }
  
  /**
   * Process one or more commands.
   * @param cmds (ex: "help" "look;go north;look")
   * @return a response
   */
  protected String processCommands(String cmds) {
    sb = new StringBuilder();
    if (cmds.startsWith("script;")) {
      if (cmds.length() == 7) {
        // return the script as a newline-separated string
        return join(actions, "\n");
      }
      // strip "script;" from the start of the string
      this.setVal_String(cmds.substring(7));
      return ""; //"Script initialized.";
    }
    else if (cmds.startsWith("xport ")) {
      // xport commands may contain embedded spaces and semicolons (CMD_SEPARATOR)
      // convert double quotes in JSON to backslash quote
      processCommand(this.escapeDoubleQuotes(cmds));
    }
    else {
      String[] s = cmds.split(CMD_SEPARATOR, 100);
      for (int i = 0; i < s.length; i++) {
        //if (s[i].startsWith("xport ")) {
        //  // convert double quotes in JSON to backslash quote
        //  processCommand(this.escapeDoubleQuotes(s[i]));
        //}
        //else {
          processCommand(s[i]);
        //}
      }
    }
    return sb.toString();
  }
  
  /* MOVED TO AbstractAvatar
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
  */
  
  /**
   * Split a String into an array of substrings.
   * Split on spaces, unless between quotes.
   * ex: put "Sir William" in Coach  ->  ["put","Sir William","in","Coach"]
   * ex: one two "three four five" six  >  ["one", "two", "three four five", "six"]
   * @param str The string that will be split.
   * @param limit The maximum length of the resulting array.
   * @return An array of strings
   */
  // MOVED TO AbstractAvatar
  //protected native String[] split(String str, int limit) /*-{
  //  var result = str.match(/("[^"]+"|[^"\s]+)/g);
  //  // remove starting and end quotes in result
  //  for (var i = 0; i < result.length; i++) {
  //    if (result[i].charAt(0) == '"') {
  //      result[i] = result[i].substring(1, result[i].length-1);
  //    }
  //  }
  //  if (result.length > limit) {
  //    // limit number of entries in the result
  //    var last = result[limit-1];
  //    for (var j = limit; j < result.length; j++) {
  //      last += " " + result[j];
  //    }
  //    result[limit-1] = last;
  //    result.length = limit;
  //  }
  //  return result;
  //}-*/;
  
  // MOVED TO AbstractAvatar
  //protected native String escapeDoubleQuotes(String str) /*-{
  //  return str.replace(/"/g, '\\"');
  //}-*/;
  
  // MOVED TO AbstractAvatar
  //protected native String unescapeDoubleQuotes(String str) /*-{
  //  return str.replace(/\\"/g, '"');
  //}-*/;
  
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
   * 
   * freeze unfreeze
   * serialize to or deserialize from XML or iflang
   * 
   */
  protected void processCommand(String cmd) {
    cmd = cmd.trim();
    if (cmd.length() == 0) {return;}
    if (cmd.startsWith(COMMENT_START)) {return;}
    
    if (takenotes) {
      if (cmd.startsWith(TAKENOTES_COMMAND) && (cmd.length() > 3)) { // ex: ".. where"
        cmd = cmd.substring(3).trim();
      }
      else {
        sb.append(cmd);
        return;
      }
    }

    String[] data = null;
    if (cmd.indexOf('"') == -1) {
      data = cmd.split(" ", 4);
    }
    else if (cmd.startsWith("param speech")) { // or just cmd.startsWith("param")
      data = cmd.split(" ", 4);
    }
    else if (cmd.startsWith("xport")) {
      cmd = this.unescapeDoubleQuotes(cmd);
      data = cmd.split(" ", 3);
    }
    else {
      data = this.split(cmd, 4);
      //consoleLog(data);
    }
    int len = data.length;
    
    switch (data[0]) {
    case "anim":
    case "animate": // useful with speech recognition
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
    case "apply":
      if (len == 2) {
        apply(data[1], null, null);
      }
      else if (len == 4) {
        apply(data[1], data[2], data[3]);
      }
      else {
        sb.append("Please specify the correct number of parameters (ex: ")
        .append(data[0])
        .append(" axe to tree).");
      }
      break;
    case "back":
      back();
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
    case "break": // useful with speech recognition
    case "pause":
      breakpoint();
      break;
    case "bt": // commands for navigating a true binary tree representation of a Xholon tree
      if (len == 2) {
        this.bt(data[1]);
      }
      else {
        sb.append("Please specify a binary tree navigation parameter: ")
        .append("bt left|right|parent");
      }
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
    case "clone":
      if (len == 2) {
        clone(data[1], null);
      }
      else if (len == 3) {
        clone(data[1], data[2]);
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
    case "first":
      enter(null);
      break;
    case "flip":
      if (len == 1) {
        flip("next");
      }
      else {
        flip(data[1]); // "next" or "prev"
      }
      break;
    case "follow": // follow the leader
    {
      if (len > 1) {
        IXholon node = findNode(data[1], contextNode);
        if (node != null) {
          leader = node;
          if (len > 2) {
            followLeaderTechnique = data[2];
          }
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
    {
      IXholon portNode = contextNode;
      String portName = data[1];
      if (portName.startsWith("this.")) {
        portNode = this;
        portName = portName.substring(5);
      }
      if (len == 2) {
        go(portNode, portName, null);
      }
      else if (len > 2) {
        go(portNode, portName, data[2]);
      }
      else {
        sb.append("Please specify where to go (ex: go north).");
      }
      break;
    }
    case "group":
      if (len > 3) {
        group(data[1], data[2], data[3]);
      }
      else {
        sb.append("Please specify the correct number of parameters (ex: group Ja,El,Ma,Ca,Ly in Coach).");
      }
      break;
    case "help":
      if (len == 2) {
        if ("commands".equals(data[1])) {
          helpCommands();
        }
        if ("keymap".equals(data[1])) {
          helpKeymap();
        }
        else {
          sb.append("Please specify \"help|help commands|help keymap\".");
        }
      }
      else {
        helpCommands();
      }
      break;
    case "i":
    case "inventory":
      inventory();
      break;
    case "identity":
      // the identity morphism is a concept from Category Theory
      // this command is essentially the same as "who"
      sb.append("You are ").append(makeNodeName(this.getIdentity())).append(".");
      trace(this.contextNode);
      break;
    case "idle":
      // do nothing; an explicit command that's equivalent to all of the following: "" ";" "[];"
      // it's similar to identity(), in that it is like an arrow that moves the Avatar back to where it already is
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
      if ((len > 1) && ("all".equals(data[1]))) {
        lookAll();
      }
      else {
        look();
      }
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
    case "parameter": // useful with speech recognition
      if (len > 2) {
        param(data[1], data[2], data[3]);
      }
      else {
        sb.append("Please specify the name and value of the param (ex: param loop false).");
      }
      break;
    case "parent":
      exit(null);
      break;
    case "prev":
    case "previous": // useful with speech recognition
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
    case "recipe":
      if (len == 2) {
        recipe(data[1], null);
      }
      else if (len == 3) {
        recipe(data[1], data[2]);
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
    case "skip": // skip n lines in actions
      if (len > 1) {
        try {
          actionIx += Integer.parseInt(data[1]);
        } catch(NumberFormatException e) {
          sb.append("The number of lines to skip must be specified as an integer (ex: skip 2)");
        }
      }
      else {
        sb.append("Please specify how many lines to skip (ex: skip 2).");
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
    case "start":
      start();
      break;
    case "step":
      step();
      break;
    case "system":
      system();
      break;
    case "take":
      if (len > 1) {
        take(data[1]);
      }
      else {
        takeAll();
      }
      break;
    case "takeclone":
      if (len == 2) {
        takeClone(data[1], null);
      }
      else if (len == 3) {
        takeClone(data[1], data[2]);
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
    case "where":
      sb.append("You are in ").append(makeNodeName(contextNode)).append(".");
      break;
    case "who":
      sb.append("You are ").append(isSystemAvatar() ? "the system Avatar " : "").append(makeNodeName(this)).append(".");
      break;
    case "xport":
      // xport THING formatName,writeToTab,returnString,efParams
      if (len > 2) {
        xport(data[1], data[2]);
      }
      else {
        sb.append("Please specify the correct number of parameters (ex: xport helloWorld Xml,false,true,{...}).");
      }
      break;
    default:
      // TODO check for app-specific commands
      // with speech recognition, allow no-build, no-follow, no-lead in place of unbuild, unfollow, unlead
      if (data[0].startsWith("no")) {
        if ("nofollow".equals(data[0])) {
          leader = null; // unfollow
        }
        if ("no".equals(data[0])) {
          if ("follow".equals(data[1])) {
            leader = null; // unfollow
          }
          else if ("lead".equals(data[1])) {
            follower = null; // unlead
          }
          else if ("build".equals(data[1])) {
            // unbuild
            if (len > 2) {
              eat(data[2], "Unbuilt.");
            }
            else {
              eatAll("Unbuilt all.");
            }
          }
        }
      }
      else {
        if (chatbot != null) {
          IMessage rmsg = chatbot.sendSyncMessage(ISignal.SIGNAL_XHOLON_CONSOLE_REQ, cmd, this);
          String dataObj = (String)rmsg.getData();
          if ((dataObj != null) && (dataObj.length() > 1)) {
            // ignore initial "\n"
            sb.append(dataObj.substring(1));
          }
        }
        else {
          sb.append(cmd).append("?");
        }
      }
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
      case "right":  // useful with speech recognition
        if (params == null) {params = DEFAULT_TURN_PARAMS;}
        makeJsObject(node, "anim", "{\"turnright\": " + params + "}");
        animView(node, viewEle, duration);
        break;
      case "turnleft":
      case "left":  // useful with speech recognition
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
        makeJsObject(node, "anim", "{\"show\": " + false + "}");
        animView(node, viewEle, duration);
        break;
      case "show":
        makeJsObject(node, "anim", "{\"show\": " + true + "}");
        animView(node, viewEle, duration);
        break;
      default:
        // this may be a JSON string specifying one or more animations
        //makeJsObject(node, "anim", animType);
        //consoleLog(animType + "," + params);
        String jsonStr = "{\"" + animType + "\": \"" + params + "\"}";
        //consoleLog(jsonStr);
        makeJsObject(node, "anim", jsonStr);
        animView(node, viewEle, duration);
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
   * Use a tool, or apply a tool to one or more other things.
   * @param toolThing - The name of a tool in the inventory.
   * @param op - An operator, typically "to".
   * @param otherThings - Zero or more things that toolThing will operate on.
   */
  protected void apply(String toolThing, String op, String otherThings) {
    IXholon thing1 = findNode(toolThing, this);
    if (thing1 == null) {return;}
    JavaScriptObject othings = null;
    if (otherThings != null) {
      String[] othingNames = otherThings.split(",");
      int len = othingNames.length;
      othings = makeJsArr();
      for (int i = 0; i < len; i++) {
        IXholon node = findNode(othingNames[i], contextNode);
        pushJsArr(othings, node);
      }
    }
    IMessage rmsg = thing1.sendSyncMessage(101, othings, this);
    if (rmsg.getData() != null) {
      consoleLog(rmsg.getData());
    }
  }
  
  /**
   * Backtrack through Avatar history.
   */
  protected void back() {
    IXholon hnode = this.popHistory();
    if (hnode != null) {
      moveto(hnode, "Moving back to");
    }
  }
  
  protected native JavaScriptObject makeJsArr() /*-{
    return [];
  }-*/;
  
  protected native void pushJsArr(JavaScriptObject jsArr, IXholon node) /*-{
    jsArr.push(node);
  }-*/;
  
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
        IXholonClass xhClass = node.getClassNode(newValue);
        if (xhClass != null) {
          node.setXhc(xhClass);
        }
      }
      else {
        if ("null".equals(newValue)) {
          // ava.action("set P1 pppp null");
          setAttributeValNative(node, whatChanges, null);
        }
        else if (newValue.startsWith("xpath")) {
          // ava.action("set P1 pppp xpath(Cable[@roleName='7'])");
          IXholon otherNode = evalXPathCmdArg(newValue, contextNode);
          if (otherNode != null) {
            setAttributeValNative(node, whatChanges, otherNode);
          }
          //else {
          //  sb.append("Can't go " + portName + ". ");
          //}
        }
        else {
          setAttributeValNative(node, whatChanges, newValue);
        }
      }
      if (meteor && (meteorService != null) && existsMeteorAttr(whatChanges)) {
        String[] data = {newValue, "add", "@" + whatChanges};
        meteorService.sendSyncMessage(IMeteorPlatformService.SIG_COLL_INSERT_REQ, data, node); // -3897
      }
    }
  }
  
  protected native boolean existsMeteorAttr(String attrName) /*-{
    if (this["meteorattr"]) {
      var index = this["meteorattr"].indexOf(attrName);
      if (index == -1) {
        return false;
      }
      else {
        return true;
      }
    }
    return false;
  }-*/;
  
  protected native void makeMeteorAttr(String attrValues) /*-{
    //this["meteorattr"] = [];
    //var attrs = attrValues.split(",");
    //for (var i = 0; i < attrs.length; i++) {
    //  this["meteorattr"].push(attrs[i]);
    //}
    this["meteorattr"] = attrValues.split(",");
  }-*/;
  
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
    case "buildandtake":
      // build the thing directly into the Avatar as part of the inventory
      xhs.pasteLastChild(this, thing);
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
      meteorService.sendSyncMessage(IMeteorPlatformService.SIG_COLL_INSERT_REQ, data, contextNode);
    }
  }
  
  /**
   * Clone
   * example:  this.remove() is required
<script>
var a = this.parent();
this.remove();
a.action("clone hello;");
</script>
   * @param thing The node that should be cloned.
   * @param where "after" or "before" the thing
   */
  protected void clone(String thing, String where) {
    IXholon node = findNode(thing, contextNode);
    if (node != null) {
      switch (where) {
      case "before":
        ((XholonHelperService)app.getService(IXholonService.XHSRV_XHOLON_HELPER)).cloneBefore(node);
        break;
      case "after":
      default: // null
        ((XholonHelperService)app.getService(IXholonService.XHSRV_XHOLON_HELPER)).cloneAfter(node);
        break;
      }
    }
  }
  
  /**
   * Take a clone
   * example:
<script>
var a = this.parent();
a.action("takeclone hello;");
</script>

   * @param thing The node that should be cloned.
   * @param where As "last" or "first" child of the thing
   */
  protected void takeClone(String thing, String where) {
    IXholon node = findNode(thing, contextNode);
    if (node != null) {
      switch (where) {
      case "first":
        {
        ((XholonHelperService)app.getService(IXholonService.XHSRV_XHOLON_HELPER)).cloneFirstChild(node);
        IXholon clonedNode = node.getFirstChild();
        if (clonedNode != null) {
          clonedNode.removeChild();
          clonedNode.appendChild(this);
        }
        break;
        }
      case "last":
      default: // null
        {
        ((XholonHelperService)app.getService(IXholonService.XHSRV_XHOLON_HELPER)).cloneLastChild(node);
        IXholon clonedNode = node.getLastChild();
        if (clonedNode != null) {
          clonedNode.removeChild();
          clonedNode.appendChild(this);
        }
        break;
        }
      }
    }
  }
  
  /**
   * Move a specified thing from the avatar's inventory, to the current contextNode.
   * @param thing The Xholon name of the thing to drop.
   */
  protected void drop(String thing) {
    IXholon node = null;
    if ("first".equals(thing)) {
      node = this.getFirstChild();
    }
    else if ("last".equals(thing)) {
      node = this.getLastChild();
    }
    else {
      node = findNode(thing, this);
    }
    if (node != null) {
      if (meteor && (meteorService != null)) {
        thing = thing.trim();
        if (!thing.startsWith("<")) {
          /*String roleName = node.getRoleName();
          if (roleName == null) {
            thing = "<" + node.getXhcName() + "/>";
          }
          else {
            thing = "<" + node.getXhcName() + " roleName=\"" + roleName + "\"/>";
          }*/
          thing = this.makeMeteorXmlStr(node);
        }
        String[] data = {thing, "add", "append"};
        meteorService.sendSyncMessage(IMeteorPlatformService.SIG_COLL_INSERT_REQ, data, contextNode);
      }
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
   * TODO for Meteor, possibly put all nodes into a single XMLSring forest
   */
  protected void dropAll() {
    IXholon node = this.getFirstChild();
    while (node != null) {
      IXholon sib = node.getNextSibling();
      // Meteor
      if (meteor && (meteorService != null)) {
        /*String thing = "";
        String roleName = node.getRoleName();
        if (roleName == null) {
          thing = "<" + node.getXhcName() + "/>";
        }
        else {
          thing = "<" + node.getXhcName() + " roleName=\"" + roleName + "\"/>";
        }*/
        String thing = this.makeMeteorXmlStr(node);
        String[] data = {thing, "add", "append"};
        meteorService.sendSyncMessage(IMeteorPlatformService.SIG_COLL_INSERT_REQ, data, contextNode);
      }
      node.removeChild();
      node.appendChild(contextNode);
      node = sib;
    }
    sb.append("All dropped.");
  }
  
  /**
   * Make an XML string that can be used with Meteor.
   * Called from drop() and dropAll() and perhaps other methods.
   * For Xholon subtrees, use the code from io/Xml2Xholon.java multiply()
   */
  protected String makeMeteorXmlStr(IXholon node) {
    String xmlStr = null;
    if (node.hasChildNodes()) {
      IXholon2Xml xholon2Xml = new Xholon2Xml();
      xholon2Xml.setXhAttrStyle(IXholon2Xml.XHATTR_TO_XMLATTR); // XHATTR_TO_NULL);
      xholon2Xml.setWriteStartDocument(false); // don't write "<?xml version="1.0" encoding="UTF-8"?>" at start of document
      xholon2Xml.setShouldWriteAllPorts(false);
      xholon2Xml.setShouldWriteVal(false);
      xmlStr = xholon2Xml.xholon2XmlString(node);
    }
    else {
      String roleName = node.getRoleName();
      if (roleName == null) {
        xmlStr = "<" + node.getXhcName() + "/>";
      }
      else {
        xmlStr = "<" + node.getXhcName() + " roleName=\"" + roleName + "\"/>";
      }
    }
    return xmlStr;
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
      // NO don't involve Meteor; Meteor should already have been notified when Avatar took the thing
      /*if (meteor && (meteorService != null)) {
        String[] data = {null, "remove"};
        meteorService.sendSyncMessage(IMeteorPlatformService.SIG_COLL_INSERT_REQ, data, node);
      }*/
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
      // optionally write this change to Meteor; NO
      /*if (meteor && (meteorService != null)) {
        String[] data = {null, "remove"};
        meteorService.sendSyncMessage(IMeteorPlatformService.SIG_COLL_INSERT_REQ, data, node);
      }*/
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
    if ((node != null) && (node != this)) {
      if (isContainerOrSupporter(node)) {
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
    else if ((node.getXhc() != null) && node.getXhc().hasAnnotation()) {
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
    else if ("next".equals(ancestor)) {
      flipNext(this, contextNode);
      setContextNode(contextNode.getParentNode());
      return;
    }
    else if (ancestor.startsWith("prev")) {
      flipPrev(this, contextNode);
      setContextNode(contextNode.getParentNode());
      return;
    }
    else {
      node = xpath.evaluate("ancestor::" + ancestor, contextNode);
    }
    if (node != null) {
      moveto(node, "Exited to");
    }
    else {
      sb.append("Can't exit from ").append(makeNodeName(contextNode));
    }
  }
  
  /**
   * Flip the position of this avatar with the position of its next or previous sibling.
   * @param thing "next" or "prev[ious]"
   */
  protected void flip(String thing) {
    switch(thing) {
    case "parent":
      exit("prev");
      if (meteor) {
        meteor = false;
        take("next");
        meteor = true;
      }
      else {
        take("next");
      }
      break;
    case "next":
    {
      IXholon node = getNextSibling();
      if (node == null) {
        node = getFirstSibling();
      }
      if (node != null) {
        flipNext(this, node);
      }
      break;
    }
    case "prev":
    case "previous":
    {
      IXholon node = getPreviousSibling();
      if (node == null) {
        node = getLastSibling();
      }
      if (node != null) {
        flipPrev(this, node);
      }
      break;
    }
    default: break;
    }
  }
  
  protected native void flipNext(IXholon one, IXholon two) /*-{
    one.remove().insertAfter(two);
  }-*/;
  
  protected native void flipPrev(IXholon one, IXholon two) /*-{
    one.remove().insertBefore(two);
  }-*/;
  
  /**
   * Move the avatar to the node located in a specified direction.
   * In practice, any valid Xholon port name can be used.
   * TODO handle port ports
   * handle an XPath expression as the portName
   *   go xpath(ancestor::X/descendant::Y)
   *   xpath.evaluate(portName, contextNode);
   * @param portNode - The IXholon node that has the specfied port
   *   usually the contextNode, but could be this Avatar itself
   *   if portNode is null, then set it to the contextNode
   * @param portName - The name or index of a Xholon port (ex: "north" "south" "east" "west" "0" "1"),
   *   or a pseudo port name ("next" "prev" "xpath(...)")
   * @param nextTarget - Optional target for "next"
   */
  protected void go(IXholon portNode, String portName, String nextTarget) {
    if (portName == null) {return;}
    if (portNode == null) {portNode = contextNode;}
    
    // uniform case for comparison works better with speech recognition
    switch (portName.toUpperCase()) {
    case "NEXT":
      if (nextTarget == null) {next();}
      else {next(nextTarget);}
      return;
    case "PREV":
    case "PREVIOUS": // useful with speech recognition
      if (nextTarget == null) {prev();}
      else {prev(nextTarget);}
      return;
    case "N": goPort(portNode, IGrid.P_NORTH); return;
    case "NORTH":
    {
      // speech recognition recognizes "north east" "north west" rather than "northeast" "northwest"
      if (nextTarget == null) {
        goPort(portNode, IGrid.P_NORTH);
      }
      else if ("EAST".equalsIgnoreCase(nextTarget)) {
        goPort(portNode, IGrid.P_NORTHEAST);
      }
      else if ("WEST".equalsIgnoreCase(nextTarget)) {
        goPort(portNode, IGrid.P_NORTHWEST);
      }
      else {
        goPort(portNode, IGrid.P_NORTH);
      }
      return;
    }
    case "E":
    case "EAST": goPort(portNode, IGrid.P_EAST); return;
    case "S":
    case "SOUTH": goPort(portNode, IGrid.P_SOUTH); return;
    case "W":
    case "WEST": goPort(portNode, IGrid.P_WEST); return;
    case "NE":
    case "NA":
    case "ANY":
    case "NORTHEAST": goPort(portNode, IGrid.P_NORTHEAST); return;
    case "SE":
    case "SOUTHEAST": goPort(portNode, IGrid.P_SOUTHEAST); return;
    case "SW":
    case "SOUTHWEST": goPort(portNode, IGrid.P_SOUTHWEST); return;
    case "NW":
    case "NORTHWEST": goPort(portNode, IGrid.P_NORTHWEST); return;
    case "0":
    case "ZERO": goPort(portNode, 0); return;
    case "1":
    case "ONE": goPort(portNode, 1); return;
    case "2":
    case "TO": goPort(portNode, 2); return;
    case "3": goPort(portNode, 3); return;
    case "4": goPort(portNode, 4); return;
    case "5":
    case "V": goPort(portNode, 5); return;
    case "6": goPort(portNode, 6); return;
    case "7": goPort(portNode, 7); return;
    case "8": goPort(portNode, 8); return;
    case "9": goPort(portNode, 9); return;
    case "10": goPort(portNode, 10); return;
    case "HISTORY":
      back();
      return;
    default: break;
    }
    
    if (portName.startsWith("port")) {
      // port0 port1 etc.
      if ((portName.length() > 4) && (Misc.getNumericValue(portName.charAt(4)) != -1)) {
        int portNum = Misc.atoi(portName, 4);
        IXholon reffedNode = portNode.getPort(portNum);
        if (reffedNode == null) {
          tryAllPorts(portNode, portName);
        }
        else {
          moveto(reffedNode, null);
        }
      }
      else {
        // ex: portx
        sb.append("Can't go ").append(portName);
      }
      return;
    }
    
    else if (portName.startsWith("link")) {
      // link0 link1 etc.
      if ((portName.length() > 4) && (Misc.getNumericValue(portName.charAt(4)) != -1)) {
        int portNum = Misc.atoi(portName, 4);
        List linksList = portNode.getLinks(false, true);
        if (portNum < linksList.size()) {
          PortInformation pi = (PortInformation)linksList.get(portNum);
          IXholon reffedNode = null;
          if (pi != null) {
            reffedNode = pi.getReffedNode();
          }
          if (reffedNode == null) {
            sb.append("Can't go ").append(portName).append(": can't find reffedNode");
          }
          else {
            moveto(reffedNode, "Moving along " + pi.getFieldName() + " to");
          }
        }
        else {
          sb.append("Can't go ").append(portName).append(": index out of bounds");
        }
      }
      else {
        // ex: linkx
        sb.append("Can't go ").append(portName);
      }
      return;
    }
    
    else if (portName.startsWith("xpath")) {
      IXholon node = evalXPathCmdArg(portName, portNode);
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
    else if (goNamedLink(portNode, portName)) {
      return;
    }
    // if all else fails ...
    tryAllPorts(portNode, portName);
  }
  
  /**
   * Try the getAllPorts() method.
   * Search through the PortInformation array.
   * @param portName (ex: "port0" "port7").
   */
  protected void tryAllPorts(IXholon portNode, String portName) {
    Object[] portArr = portNode.getAllPorts().toArray();
    if ((portArr != null) && (portArr.length > 0)) {
      String foundPortNames = "";
      for (int i = 0; i < portArr.length; i++) {
        PortInformation pi = (PortInformation)portArr[i];
        String foundPortName = pi.getFieldName();
        IXholon reffedNode = pi.getReffedNode();
        // I think I'm using startsWith() to allow for users using abbreviated port names
        // ex: "go por" will find "port0" if it exists
        if (foundPortName.startsWith(portName)) {
          moveto(reffedNode, null);
          return;
        }
        else {
          foundPortNames += " " + foundPortName;
          if ("port".equals(foundPortName)) {
            String indexStr = pi.getFieldNameIndexStr(); // ex: "0"
            foundPortNames += indexStr;
            if ((foundPortName + indexStr).equals(portName)) { // ex: "port0"
              moveto(reffedNode, null);
              return;
            }
            else {
              // try matching portName with the name of the reffed node (ex: "france" "italy")
              if (reffedNode != null) {
                String reffedNodeName = makeNodeName(reffedNode);
                foundPortNames += "|" + reffedNodeName;
                if (reffedNodeName.startsWith(portName)) {
                  moveto(reffedNode, null);
                  return;
                }
              }
            }
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
  protected void goPort(IXholon portNode, int index) {
    moveto(portNode.getPort(index), null);
  }
  
  /**
   * Go to a named link, typically a link that's been set up using JavaScript.
   * ex: "go fk1"
   */
  protected boolean goNamedLink(IXholon portNode, String linkName) {
    List linksList = portNode.getLinks(false, true);
    for (int portNum = 0; portNum < linksList.size(); portNum++) {
      PortInformation pi = (PortInformation)linksList.get(portNum);
      if ((pi != null) && (linkName.equals(pi.getFieldName()))) {
        IXholon reffedNode = pi.getReffedNode();
        if (reffedNode == null) {
          sb.append("Can't go ").append(linkName).append(": can't find reffedNode");
          return false;
        }
        else {
          moveto(reffedNode, "Moving along " + linkName + " to");
          return true;
        }
      }
    }
    return false;
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
   * Binary Tree
   * "bt left|right|parent"
   * avatarKeyMap could be:
"LEFT":"bt left",
"RIGHT":"bt right",
"UP":"bt parent",
   */
  protected void bt(String whereto) {
    IXholon node = null;
    switch (whereto) {
    case "left":
      node = contextNode.getBinaryTreeLeft();
      break;
    case "right":
      node = contextNode.getBinaryTreeRight();
      break;
    case "parent":
      node = contextNode.getBinaryTreeParent();
      break;
    default:
      break;
    }
    if ((node == null) || (node == this)) {
      sb.append("Can't bt ").append(whereto).append(".");
    }
    else {
      moveto(node, null);
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
          meteorService.sendSyncMessage(IMeteorPlatformService.SIG_COLL_INSERT_REQ, data, this);
        }
      }
      this.removeChild();
      this.appendChild(node);
    }
    //contextNode = node;
    setContextNode(node);
  }
  
  /**
   * Provide some minimal help to users, by listing the Avatar commands.
   */
  protected void helpCommands() {
    sb
    .append("Basic commands:")
    .append("\nanim THING turnright|turnleft|hop|duck|grow|shrink|mirror PARAMS")
    .append("\napply TOOL_THING [to OTHER_THING|OTHER_THINGS]")
    .append("\nappear")
    .append("\nback")
    .append("\nbecome THING role ROLE")
    .append("\nbreakpoint")
    .append("\nbt left|right|parent")
    .append("\nbuild|append|prepend|before|after THING [role ROLE]")
    .append("\nclone THING [after|before]")
    .append("\n[COMMENT]")
    .append("\ndrop [THING]")
    .append("\nenter [*]THING")
    .append("\nexamine|x THING")
    .append("\nexit [THING]")
    .append("\nflip parent|prev|next|first")
    .append("\nfollow LEADER_THING [unison|mirror|canon]")
    .append("\nget THING NAME")
    .append("\ngo portName|next|prev|N|E|S|W|NE|SE|SW|NW|port0|portN|xpath|linkN")
    .append("\ngroup THING1[,THINGi,...,THINGn] in|on|under THING2")
    .append("\nhelp|help commands|help keymap")
    .append("\nidentity")
    .append("\nidle")
    .append("\nif xpath command [elseif xpath command] [else command]")
    .append("\ninventory|i")
    .append("\nlead FOLLOWER_THING")
    .append("\nlook [all]")
    .append("\nnext [[*]THING]")
    .append("\nout speech,caption,transcript,debug,all TEXT")
    .append("\nparam NAME VALUE")
    .append("\nprev [[*]THING]")
    .append("\nput THING1 in|on|under|before|after THING2")
    .append("\nrecipe RECIPE_NAME [RECIPE_BOOK_NAME]")
    .append("\nsearch THING")
    .append("\nset THING NAME VALUE")
    .append("\nsmash THING")
    .append("\nsystem")
    .append("\ntake [THING]")
    .append("\ntakeclone THING [last|first]")
    .append("\nunbuild|eat THING")
    .append("\nunfollow")
    .append("\nunlead")
    .append("\nvanish")
    .append("\nwait [DURATION]|[til TIMESTEP]")
    .append("\nwhere")
    .append("\nwho")
    .append("\nxport THING formatName,writeToTab,returnString,efParams")
    ;
  }
  
  /**
   * Provide the Avatar key map.
   */
  protected void helpKeymap() {
    sb.append("Key map:\n");
    String akm = this.getAvatarKeyMap();
    String akmout = helpKeymapNative(akm);
    sb.append(akmout);
  }
  
  protected native String helpKeymapNative(String akm) /*-{
    var akmout = "";
    var akmobj = $wnd.JSON.parse(akm);
    var keys = Object.keys(akmobj);
    for (var i = 0; i < keys.length; i++) {
      var key = keys[i];
      akmout += key + ": " + akmobj[key] + "\n";
    }
    return akmout;
  }-*/;
  
  public void setAvatarKeyMap(String jsonStr) {
    this.avatarKeyMap = JsonUtils.safeEval(jsonStr);
  }
  
  public String getAvatarKeyMap() {
    if (this.avatarKeyMap == null) {
      return app.getAvatarKeyMap();
    }
    else {
      return this.stringify(this.avatarKeyMap);
    }
  }
  
  protected JavaScriptObject getAvatarKeyMapJso() {
    return this.avatarKeyMap;
  }
  
  private native String stringify(JavaScriptObject jso) /*-{
    return $wnd.JSON.stringify(jso);
  }-*/;
  
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
    
    String elseifPart = null;
    String elsePart = null;
    if (elseifStart != -1) {elseifPart = attrAndCommand.substring(elseifStart + 8);}
    else if (elseStart != -1) {elsePart = attrAndCommand.substring(elseStart + 6);}
    
    if (node == null) {
      if (elseifStart != -1) {
        attrAndCommand = "if " + elseifPart; //attrAndCommand.substring(elseifStart + 8);
        processCommand(attrAndCommand);
      }
      else if (elseStart != -1) {
        attrAndCommand = elsePart; //attrAndCommand.substring(elseStart + 6);
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
        case "random":
          try {
            double av = Double.parseDouble(attrValue);
            double rnum = Math.random(); // get a number between 0.0 and 1.0
            switch (operation) {
              // ex: iflt xpath(.) random 0.5 next else idle;
              // ex: iflt xpath(.) random 0.02 next elseif ifgt xpath(.) random 0.98 prev else idle;
              case "lt": if (rnum < av)  {processCommand(command);} break;
              case "gt": if (rnum > av)  {processCommand(command);} break;
              default: break;
            }
          } catch(NumberFormatException e) {
            sb.append("The attribute value must be a number between 0.0 and 1.0 (ex: 0.5)");
          }
          break;
        default:
          try {
            String av = (String)getAttributeValNative(node, attrName, true);
            switch (operation) {
            // ifeq xpath(Book) jsattr jsvalue next;
            // ifeq xpath(Member) decision true go this.myclubhouse else ifeq xpath(Member) decision false go this.myhouse;
            // ifeq xpath(Member) decision true go this.myclubhouse elseif ifeq xpath(Member) decision false go this.mystore else go this.myhouse;
            case "eq":
              if (attrValue.equals(av)) {processCommand(command);}
              else {
                if (elseifPart != null) {
                  //attrAndCommand = "if " + attrAndCommand.substring(elseifStart + 8);
                  processCommand(elseifPart);
                }
                else if (elsePart != null) {
                  //attrAndCommand = attrAndCommand.substring(elseStart + 6);
                  processCommand(elsePart);
                }
              }
              break;
            case "ne":
              if (!attrValue.equals(av)) {processCommand(command);}
              else {
                if (elseifPart != null) {processCommand(elseifPart);}
                else if (elsePart != null) {processCommand(elsePart);}
              }
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
        // ava.action("get P1 pppp");
        // ava.action("get P1 f1\uFEFF");
        return getAttributeValNative(node, attrName, false);
      }
    }
    return null;
  }
  
  /**
   * @param toString Whether or not to ensure that the returned value is a String.
   */
  protected native Object getAttributeValNative(IXholon node, String attrName, boolean toString) /*-{
    if (toString) {
      return "" + node[attrName];
    }
    return node[attrName];
  }-*/;
  
  /**
   * ex: become this calories 1000;
   * ex: become this calories +=1;
   * ex: become this calories -=3;
   * ex: become this calories ++;
   * ex: become this calories --;
   */
  protected native void setAttributeValNative(IXholon node, String attrName, Object attrValue) /*-{
    if ((attrValue != null) && (attrValue.length > 1)) {
      var operator = attrValue.substring(0,2);
      var sum = 0;
      switch (operator) {
      case "+=":
        if (attrValue.length > 2) {
          sum = Number(node[attrName]);
          node[attrName] = sum + Number(attrValue.substring(2));
        }
        break;
      case "-=":
        if (attrValue.length > 2) {
          sum = Number(node[attrName]);
          node[attrName] = sum - Number(attrValue.substring(2));
        }
        break;
      case "++":
        sum = Number(node[attrName]);
        node[attrName] = sum + 1;
        break;
      case "--":
        sum = Number(node[attrName]);
        node[attrName] = sum - 1;
        break;
      default:
        node[attrName] = attrValue;
        break;
      }
    }
    else {
      node[attrName] = attrValue;
    }
  }-*/;
  
  @Override
  public int setAttributeVal(String attrName, String attrVal) {
    if ("start".equals(attrName)) {
      this.start = attrVal.trim();
    }
    else if ("startPos".equals(attrName)) {
      this.startPos = attrVal.trim();
    }
    else if ("end".equals(attrName)) {
      this.end = attrVal.trim();
    }
    else if ("params".equals(attrName)) {
      // ex: <Avatar params="param transcript false;param repeat true;param debug false;>"
      String params = attrVal.trim();
      String[] paramsArr = params.split(CMD_SEPARATOR);
      for (int i = 0; i < paramsArr.length; i++) {
        String paramStr = paramsArr[i].trim();
        String[] data = paramStr.split(" ", 4);
        if ((data.length > 2) && ("param".equals(data[0]))) {
          this.param(data[1], data[2], data[3]);
        }
      }
    }
    else if ("shouldReceiveKeyEvents".equals(attrName)) {
      this.shouldReceiveKeyEvents = Boolean.parseBoolean(attrVal.trim());
    }
    else {
      return super.setAttributeVal(attrName, attrVal);
    }
    return 0;
  }
  
  /**
   * Look at the avatar's current location, and at the child items in that location.
   */
  protected void look() {
    sb.append("You are in ").append(makeNodeName(contextNode)).append(".");
    lookChildren(contextNode.getFirstChild(), " ");
    lookLinks(contextNode, " ");
  }
  
  /**
   * Look at the avatar's current location, and at the complete subtree in that location.
   */
  protected void lookAll() {
    sb.append("You are in ").append(makeNodeName(contextNode)).append(".");
    lookRecurse(contextNode.getFirstChild(), " ");
  }
  
  protected void lookChildren(IXholon node, String indent) {
    while (node != null) {
      if (node != this) { // exclude this avatar and its children
        sb.append("\n").append(indent).append("You see ").append(makeNodeName(node));
      }
      node = node.getNextSibling();
    }
  }
  
  protected void lookRecurse(IXholon node, String indent) {
    while (node != null) {
      if (node != this) { // exclude this avatar and its children
        sb.append("\n").append(indent).append("You see ").append(makeNodeName(node));
        lookLinks(node, indent + " ");
        lookRecurse(node.getFirstChild(), indent + " ");
      }
      node = node.getNextSibling();
    }
  }
  
  protected void lookLinks(IXholon node, String indent) {
    JsArray<JavaScriptObject> arr = ((Xholon)node).getLinksNative(false, true);
    for (int i = 0; i < arr.length(); i++) {
      JavaScriptObject obj = arr.get(i);
      String fieldName = (String)getJsoPropertyValue(obj, "fieldName");
      String fieldNameIndexStr = (String)getJsoPropertyValue(obj, "fieldNameIndexStr");
      IXholon reffedNode = (IXholon)getJsoPropertyValue(obj, "reffedNode");
      sb.append("\n").append(indent).append("You see passage ")
      .append(fieldName)
      .append(PortInformation.PORTINFO_NOTANARRAY_STR.equals(fieldNameIndexStr) ? "" : fieldNameIndexStr)
      .append(" to ")
      .append(reffedNode == null ? "UNKNOWN" : makeNodeName(reffedNode));
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
    boolean hasSpoken = false;
    for (int i = 0; i < dests.length; i++) {
      switch (dests[i]) {
      case "speech":
        if (this.getSpeechOut() != SPEECHOUT_NEVER) {
          this.speak(outPrefix + text, ssuLang, ssuVoice, ssuVolume, ssuRate, ssuPitch);
        }
        hasSpoken = true;
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
      case "anim":
        // thing, animType, params)
        anim(THIS_AVATAR, outAnim[0], outAnim[1]);
        break;
      default:
        // ex: anim(turnright)  anim(grow 2)
        if ((dests[i].startsWith("anim(")) && (dests[i].length() > 7)) {
          String[] a = dests[i].substring(5, dests[i].length()-1).split(" ");
          switch (a.length) {
          case 1: anim(THIS_AVATAR, a[0], null); break;
          case 2: anim(THIS_AVATAR, a[0], a[1]); break;
          default: break;
          }
        }
        break;
      }
    }
    if (!hasSpoken && (this.getSpeechOut() == SPEECHOUT_ALWAYS)) {
      this.speak(outPrefix + text, ssuLang, ssuVoice, ssuVolume, ssuRate, ssuPitch);
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
   * ex: ava.action("take stump; put stump before leaves;");
   *     ava.action("take oakWood; put oakWood after leaves;");
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
    switch (in) {
      case "before": node1.insertBefore(node2); break;
      case "after": node1.insertAfter(node2); break;
      default: node1.appendChild(node2); break; // in|on|under
    }
    //node1.appendChild(node2);
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
   * Process a recipe.
   * recipe RECIPE_NAME [RECIPE_BOOK_NAME]
   * example:
var ava = xh.avatar();
ava.action("param debug true");
ava.action("param recipebook Island");
ava.action("i");
ava.action("recipe Hut");
ava.action("look");
   * 
ava.action("build Stick;build String;build Hook;take stick;take string;take hook;");
ava.action("i");
ava.action("recipe FishingRod");
ava.action("look");
   * 
   * @param rname Recipe name
   * @param rbname Recipe Book name, or null; assume it's null for now
   */
  protected void recipe(String rname, String rbname) {
    String thing = processRecipe(this, this.recipebook, rname);
    // meteor
    if (meteor && (meteorService != null) && (thing != null)) {
      //String thing = newNode.getXhcName(); // TODO add role?
      String[] data = {thing, "add", "append"};
      meteorService.sendSyncMessage(IMeteorPlatformService.SIG_COLL_INSERT_REQ, data, contextNode);
    }

  }
  
  protected String processRecipe(IXholon ava, Object[] rbook, String rname) {
    String rbname = (String)rbook[0];
    String[] rbpart = rbname.split("-");
    switch (rbpart[1]) {
    case "MinecraftStyleRecipeBook": return processRecipeMinecraft(ava, rbook[1], rname);
    case "JsonRulesEngineRecipeBook": return processRecipeJsonRulesEngine(ava, rbook[1], rname);
    default: return null;
    }
    
  }
  
  /**
   * "MinecraftStyleRecipeBook"
   */
  protected native String processRecipeMinecraft(IXholon ava, Object rbook, String rname) /*-{
    // Step 0
    var recipe = rbook[rname];
    if (!recipe) {return null;}
    //$wnd.console.log(recipe[0]);
    recipe = recipe[0]; // for now, assume that there is only one item in the list of alternative recipe formulations
    
    // Step 1 - create temporary JS dictionary from ava inventory
    var inventoryDict = {};
    var node = ava.first();
    if (!node) {return null;} // the inventory is empty
    while (node) {
      var xhcName = node.xhc().name();
      var itemArr = inventoryDict[xhcName];
      if (itemArr) {
        // add node to existing internal array
        itemArr.push(node);
      }
      else {
        itemArr = []; // create internal array
        itemArr.push(node); // add node to internal array
        inventoryDict[xhcName] = itemArr;
      }
      node = node.next();
    }
    //$wnd.console.log(inventoryDict);
    
    // Step 2 - use recipe ingredients to create tentative inventory removals list
    var ingrs = recipe["ingredients"];
    if (!ingrs) {return null;}
    var removalsArr = [];
    for (var i = 0; i < ingrs.length; i++) {
      var ingr = ingrs[i];
      var itemArr = inventoryDict[ingr];
      if (itemArr && itemArr.length > 0) {
        removalsArr.push(itemArr.pop());
      }
      else {
        return null; // missing ingredient
      }
    }
    //$wnd.console.log(removalsArr);
    
    // Step 3 - create new nodes based on recipe results
    var result = recipe["result"];
    if (!result) {return null;}
    //$wnd.console.log(result); // {multiplicity: 1, xhc: "Hut", role: "Cottage"}
    // TODO retain anything that is in both the ingredients and the result (ex: a catalyst)
    // <Hut roleName="Cottage" multiplicity="1"/>
    var xmlStr = "<";
    if (!result.xhc) {return null;}
    xmlStr += result.xhc;
    if (result.role) {xmlStr += ' roleName="' + result.role + '"';}
    if (result.multiplicity) {xmlStr += ' multiplicity="' + result.multiplicity + '"';}
    if (result.attrs) {
      for (var attr in result.attrs) {
        xmlStr += ' ' + attr + '="' + result.attrs[attr] + '"';
      }
    }
    xmlStr += "/>";
    ava.parent().append(xmlStr);
    var newNode = ava.parent().last();
    // remove or smash ingredient nodes
    for (var j = 0; j < removalsArr.length; j++) {
      var removedNode = removalsArr[j].remove();
      var rchildNode = removedNode.first();
      while (rchildNode) {
        // move any children of removed ingredient node to new node (ex: Basket(Fruit Stick) + Vine -> Backpack(Fruit Stick)
        var nextChildNode = rchildNode.next();
        newNode.append(rchildNode.remove());
        rchildNode = nextChildNode;
      }
    }
    return xmlStr;
  }-*/;
  
  /**
   * TODO
   * "JsonRulesEngineRecipeBook"
   */
  protected native String processRecipeJsonRulesEngine(IXholon ava, Object rbook, String rname) /*-{
    return null;
  }-*/;
  
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
   * Set this Avatar as the system Avatar.
   */
  protected native void system() /*-{
    $wnd.xh.avatar(this);
  }-*/;
  
  /**
   * Move a specified thing from the current contextNode, to the avatar's inventory.
   * @param thing The Xholon name of the thing to take.
   */
  protected void take(String thing) {
    IXholon node = findNode(thing, contextNode);
    if (node != null) {
      // optionally write this change to Meteor
      if (meteor && (meteorService != null)) {
        String[] data = {null, "remove"};
        meteorService.sendSyncMessage(IMeteorPlatformService.SIG_COLL_INSERT_REQ, data, node);
      }
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
        // optionally write this change to Meteor
        if (meteor && (meteorService != null)) {
          String[] data = {null, "remove"};
          meteorService.sendSyncMessage(IMeteorPlatformService.SIG_COLL_INSERT_REQ, data, node);
        }
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
   * Export a subtree using a specified Xholon external format.
   * xport THING formatName,writeToTab,returnString,writeToChildNode,efParams
   * ex: xport helloWorld Xml,false,true,{...}
   * 
   * test from Developer Tools:
var ava = xh.avatar();
ava.action('xport helloWorldSystem_0 Graphviz,true,true,true,{}');
ava.action('xport hello Xml,false,true,true,{"xhAttrStyle":1,"nameTemplate":"^^C^^^","xhAttrReturnAll":true,"writeStartDocument":false,"writeXholonId":false,"writeXholonRoleName":true,"writePorts":true,"writeAnnotations":true,"shouldPrettyPrint":true,"writeAttributes":true,"writeStandardAttributes":true,"shouldWriteVal":false,"shouldWriteAllPorts":false}');
   * 
   * formatName can be a nested comma separated string:
xport hello _other,Newick,true,true,true,{}
   * 
   * @param thing ex: helloWorld
   * @param params ex: Xml,false,true,true,{"xhAttrStyle":1,"nameTemplate":"^^C^^^","xhAttrReturnAll":true,"writeStartDocument":false,"writeXholonId":false,"writeXholonRoleName":true,"writePorts":true,"writeAnnotations":true,"shouldPrettyPrint":true,"writeAttributes":true,"writeStandardAttributes":true,"shouldWriteVal":false,"shouldWriteAllPorts":false}
   */
  protected void xport(String thing, String params) {
    IXholon node = findNode(thing, contextNode);
    if (node == null) {
      sb.append("Can't find ").append(thing).append(".");
      return;
    }
    String[] arr = params.split(",", 5);
    if (arr.length != 5) {
      sb.append("Please provide 5 params (formatName,writeToTab,returnString,writeToChildNode,efParams).");
      return;
    }
    String formatName = null;
    boolean writeToTab = false;
    String efParams = "{}";
    boolean returnString = true;
    boolean writeToChildNode = true;
    
    if (arr[0].startsWith("_")) {
      // this is a nested format name
      // ex: _other,Newick,true,true,true,{}
      formatName = arr[0] + "," + arr[1];
      writeToTab = this.parseBoolean(arr[2]);
      returnString = this.parseBoolean(arr[3]);
      // TODO writeToChildNode
      if (arr[4].startsWith("true,")) {
        writeToChildNode = true;
        efParams = arr[4].substring(5);
      }
      else if (arr[4].startsWith("false,")) {
        writeToChildNode = false;
        efParams = arr[4].substring(6);
      }
      else {
        sb.append("The value of writeToChildNode must be \"true\" or \"false\"");
        return;
      }
    }
    else {
      formatName = arr[0];
      writeToTab = this.parseBoolean(arr[1]);
      efParams = arr[4];
      returnString = this.parseBoolean(arr[2]);
      writeToChildNode = this.parseBoolean(arr[3]);
    }
    consoleLog(writeToChildNode);
    String result = xportNative(formatName, node, efParams, writeToTab, returnString);
    
    if (returnString) {
      if (writeToChildNode) {
        // add the XholonMap node if it doesn't already exist
        IXholon xholonMapNode = this.findFirstChildWithXhClass("XholonMap");
        if (xholonMapNode == null) {
          this.appendEfChild("<XholonMap roleName=\"ef\"/>", this);
          xholonMapNode = this.getLastChild();
        }
        
        String efChild = "<Attribute_String roleName=\"" + formatName + "\">"
          //+ "<![CDATA[" + result + "]]>"
          + makeTextXmlEmbeddable(result)
          + "</Attribute_String>";
        this.appendEfChild(efChild, xholonMapNode);
      }
      else {
        sb.append(result);
      }
    }
  }
  
  protected boolean parseBoolean(String str) {
    boolean bool = false;
    if ("true".equals(str)) {
      bool = true;
    }
    return bool;
  }
  
  protected native void appendEfChild(String content, IXholon contextNode) /*-{
    $wnd.xh.service('XholonHelperService').call(-2013, content, contextNode);
  }-*/;
  
  /**
   * $wnd.xh.xport = $entry(function(formatName, node, efParams, writeToTab, returnString)
   */
  protected native String xportNative(String formatName, IXholon node, String efParams, boolean writeToTab, boolean returnString) /*-{
    $wnd.console.log(formatName);
    $wnd.console.log(node.name());
    $wnd.console.log(efParams);
    $wnd.console.log(writeToTab);
    $wnd.console.log(returnString);
    return $wnd.xh.xport(formatName, node, efParams, writeToTab, returnString);
  }-*/;
  
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
    case "anim":
      // ex: param anim grow 1.1;  param anim turnleft;
      outAnim[0] = "null".equals(value) ? null : value;
      outAnim[1] = "null".equals(valueRest) ? null : valueRest;
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
      case "true":
        repeat[0] = true;
        if (valueRest != null) {
          try {
            String[] roptions = valueRest.split(",");
            switch (roptions.length) {
            case 1:
              repeat[1] = Integer.parseInt(roptions[0]);
              break;
            case 2:
              repeat[1] = Integer.parseInt(roptions[0]);
              repeat[2] = Integer.parseInt(roptions[1]);
              break;
            default: break;
            }
          } catch(NumberFormatException e) {consoleLog(e);}
        }
        break;
      case "false": repeat[0] = false; break;
      case "toggle": repeat[0] = !((boolean)repeat[0]); break;
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
            if (valueRest == null) {valueRest = "";}
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
    case "meteorattr":
      this.makeMeteorAttr(value);
      break;
    case "meteor+move": // this is a shortcut
      switch (value) {
      case "true": meteor = true; meteormove = true; break;
      case "false": meteor = false; meteormove = false; break;
      default: break;
      }
      break;
    case "chatbot":
      switch (value) {
      case "true":
        contextNode.appendChild("Chatbot", null, "org.primordion.xholon.base.Chatbot");
        this.chatbot = contextNode.getLastChild();
        if ((this.chatbot != null) && ("Chatbot".equals(this.chatbot.getXhcName()))) {
          this.chatbot.postConfigure();
        }
        this.chatbot.removeChild();
        break;
      case "false": chatbot = null; break;
      default: break;
      }
      break;
    case "takenotes":
      switch (value) {
      case "true": takenotes = true; break;
      case "false": takenotes = false; break;
      default: break;
      }
      break;
    case "shouldstep":
      /* whether or not the system Avatar's act() method should be called each time step, if it's invisible
       * example: (Story Template - system avatar - stay invisible, go inside Home, lead book Last..., move to School)
       * var ava = xh.avatar();
       * ava.action("param shouldstep true;enter;enter city;enter home;lead Last;next school;");
       */
      switch (value) {
      case "true": app.setShouldStepAvatar(true); break;
      case "false": app.setShouldStepAvatar(false); break;
      default: break;
      }
      break;
    case "recipebook":
      /**
       * param recipebook RecipeService-MinecraftStyleRecipeBook-Island;
       * param recipebook MinecraftStyleRecipeBook-Island;
       * param recipebook Island;
       * param recipebook JsonRulesEngineRecipeBook-Island;
       */
      String rbname = null;
      String[] rbnameArr = value.split("-");
      switch (rbnameArr.length) {
      case 1:
        rbname = RB_DEFAULT_SERVICE_NAME + RB_SEPARATOR + RB_DEFAULT_STYLE_NAME + RB_SEPARATOR + value;
        break;
      case 2:
        rbname = RB_DEFAULT_SERVICE_NAME + RB_SEPARATOR + value;
        break;
      case 3:
        rbname = value;
        break;
      default: break;
      }
      if (rbname != null) {
        IXholon rserv = this.getService("RecipeService");
        if (rserv != null) {
          IMessage rmsg = rserv.sendSyncMessage(IRecipe.SIG_GET_RECIPE_BOOK_REQ, rbname, this);
          this.recipebook[0] = rbname;
          this.recipebook[1] = rmsg.getData();
        }
      }
      break;
    case "setCtxtOnselect":
      switch (value) {
      case "true": setCtxtOnselect = true; break;
      case "false": setCtxtOnselect = false; break;
      default: break;
      }
      break;
    case "currentActions":
      switch (value) {
      case "true": configureCurrentActions(true); break;
      case "false": configureCurrentActions(false); break;
      default: break;
      }
      break;
    case "subtrees":
      if ("true".equals(value)) {
        if (valueRest == null) {
          this.subtrees(SUBTREES_DEFAULT_NAMES);
        }
        else {
          this.subtrees(valueRest);
        }
      }
      else { // "false"
        this.subtrees(null);
      }
      break;
    case "actTimeStep":
      // this value must be an Integer
      try {
        this.actTimeStep = Integer.parseInt(value);
        if (this.actTimeStep < 1) {
          // value is relative rather than absolute
          this.actTimeStep = app.getTimeStep() - this.actTimeStep;
        }
      } catch(NumberFormatException e) {consoleLog(e);}
      break;
    case "trace":
      switch (value) {
      case "start": configureTrace(true, this.contextNode); break;
      case "stop": configureTrace(false, null); break;
      case "pause": pauseTrace();  break;
      case "empty": configureTrace(true, this.contextNode); break;
      case "show": showTrace(valueRest); break;
      default: break;
      }
      break;
    default:
      break;
    }
  }
  
  /**
   * Create or delete a "currentActions" property in this Avatar.
   * This will initially be used to provide current Avatar actions for writing to a database.
   * The "currentActions" property is a Queue, implemented as a JavaScript Array.
   *  arr.push(str); // adds a String to the end of the array.
   *  var str = arr.shift(); // removes a String from the start of the array
   */
  protected native void configureCurrentActions(boolean bval) /*-{
    if (bval) {
      this["currentActions"] = [];
    }
    else {
      delete this["currentActions"];
    }
  }-*/;
  
  /**
   * Save current actions to a JavaScript property (an Array) in this Avatar.
   */
  protected native void setCurrentActions(String str) /*-{
    if (typeof this["currentActions"] !== "undefined") {
      this["currentActions"].push(str);
    }
  }-*/;
  
  /**
   * Whether or not to collect and/or display the path that the Avatar traces between nodes.
   * Probably it will be the responsibility of a separate behavior, such as in a specific app, to display the trace.
   * The "trace" property is a Queue, implemented as a JavaScript Array.
   *  arr.push(str); // adds a String to the end of the array.
   *  var str = arr.shift(); // removes a String from the start of the array
  */
  protected native void configureTrace(boolean bval, IXholon ctxtNode) /*-{
    if (bval) {
      this["trace"] = []; // start
      this["trace"].push(ctxtNode);
    }
    else {
      delete this["trace"]; // stop
    }
  }-*/;
  
  /**
   * pause trace, and unpause trace
   */
  protected native void pauseTrace() /*-{
    if (typeof this["trace"] !== "undefined") {
      if ((this["trace"].length > 0) && (this["trace"][0] == null)) {
        // currently paused, so unpause it
        this["trace"].shift(); // remove the null from the start of the array
      }
      else {
        // currently not paused, so pause it
        this["trace"].unshift(null); // add a null to the start of the array
      }
    }
  }-*/;
  
  /**
   * Save trace data to a JavaScript property (an Array) in this Avatar.
   */
  protected native void trace(IXholon node) /*-{
    if (typeof this["trace"] !== "undefined") {
      if ((this["trace"].length > 0) && (this["trace"][0] == null)) {
        // trace is paused
      }
      else {
        this["trace"].push(node);
      }
    }
  }-*/;
  
  /**
   * TODO By default, this might display using Graphviz digraph "->".
   * ex: digraph G {23->24->37->24->87}
   * usage:
var ava = xh.avatar();
ava.action("param trace start;");
// move Avatar by various means, including:
ava.action("identity");
ava.action("go xpath(.)");
// show
ava.action("param trace show");
var tarr = temp0;
var gvstr = "digraph G {";
tarr.forEach(function(node, index) {
  if (index > 0) {
    gvstr += "->";
  }
  gvstr += node.id();
})
gvstr += "}";
console.log(gvstr); // digraph G {0->2->3->2->3}

ava.action("param trace show R^^^^^ digraph");
ava.action("param trace show ^^^^i^ :");
ava.action("param trace show R^^^^^ :");
   */
  protected native void showTrace(String options) /*-{
    if (options && (typeof this["trace"] !== "undefined")) {
      var tarr = options.split(" ");
      if (tarr && tarr.length && tarr.length == 2) {
        // ex: ["R^^^^^", "digraph"] or  ["^^^^i^", ":"]
        var nameTemplate = tarr[0].trim();
        var outSepOption = tarr[1].trim(); // "digraph" "graph" ","
        var outSeparator = ",";
        var outStr = "";
        switch (outSepOption) {
          case "digraph": outSeparator = "->"; outStr = "digraph G {"; break;
          case "graph": outSeparator = "--"; outStr = "digraph G {"; break;
          default: break;
        }
        this["trace"].forEach(function(node, index) {
          if (node != null) {
            if (index > 0) {
              outStr += outSeparator;
            }
            outStr += node.name(nameTemplate);
          }
        })
        switch (outSepOption) {
          case "digraph":
          case "graph":
            outStr += "}";
            break;
          default: break;
        }
      }
      console.log(outStr);
    }
    else {
      console.log(this["trace"]);
    }
  }-*/;
  
  /**
   * Pause the app at the current Xholon timestep.
   * It's similar to a software breakpoint, but not as immediate.
   * Any actions remaining in this timestep will first be completed.
   * A timestep is equivalent to one line in a Avatar script,
   * possibly one line in each of the scripts of two or more simultaneously-active Avatars,
   * or other nodes with simultaneously-active Xholon.act() methods.
   */
  /* MOVED TO AbstractAvatar
  protected void breakpoint() {
    if (app.getControllerState() == IControl.CS_PAUSED) {
      // unpause
      app.setControllerState(IControl.CS_RUNNING); // 3
    }
    else {
      // pause
      app.setControllerState(IControl.CS_PAUSED); // 4
    }
  }
  */
  
  /* MOVED TO AbstractAvatar
  protected void start() {
    app.setControllerState(IControl.CS_RUNNING); // 3
  }
  */
  
  /* MOVED TO AbstractAvatar
  protected void step() {
    app.setControllerState(IControl.CS_STEPPING); // 5
  }
  */
  
  /**
   * Query for a DOM Element, given a selector.
   * ex: var captionEle = document.querySelector("#xhanim>#one>p");
   * @param selector - a CSS selector (ex: "#xhanim>#one>p")
   */
  // MOVED TO AbstractAvatar
  //protected native Element querySelector(String selector) /*-{
  //  return $doc.querySelector(selector);
  //}-*/;
  
  /**
   * Make a node name.
   * @param node 
   * @return 
   */
  /* MOVED TO AbstractAvatar
  protected String makeNodeName(IXholon node) {
    return node.getName(nameTemplate);
  }
  */
  
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
    else if ("next".equals(nodeName)) {
      return this.getNextSibling();
    }
    else if ("nextprev".equals(nodeName)) {
      // try to get next sibling, else get previous sibling
      IXholon anode = this.getNextSibling();
      if (anode == null) {
        anode = this.getPreviousSibling();
      }
      return anode;
    }
    else if ("prevnext".equals(nodeName)) {
      // try to get previous sibling, else get next sibling
      IXholon anode = this.getPreviousSibling();
      if (anode == null) {
        anode = this.getNextSibling();
      }
      return anode;
    }
    else if ("firstsib".equals(nodeName)) {
      return this.getFirstSibling();
    }
    else if ("lastsib".equals(nodeName)) {
      return this.getLastSibling();
    }
    else if ("parent".equals(nodeName)) {
      return this.getParentNode();
    }
    else if ("context".equals(nodeName)) {
      return this.contextNode;
    }
    else if (nodeName.startsWith("prev")) {
      return this.getPreviousSibling();
    }
    else if (nodeName.startsWith("xpath")) {
      node = evalXPathCmdArg(nodeName, aRoot);
      if (node != null) {
        return node;
      }
    }
    else if (nodeName.startsWith("subtree")) {
      node = evalSubtreeCmdArg(nodeName, aRoot);
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
   * Evaluate a command argument that's specified as a subtree expression.
   * @param cmdArg - ex: "subtree(ToolsST)TestTool"  ex: "subtree(InventoryST)xpath(One/Two)"
   * @param aRoot - the context node for the subtree
   */
  protected native IXholon evalSubtreeCmdArg(String cmdArg, IXholon aRoot) /*-{
    if (!aRoot["subtrees"]) {return null;}
    var begin = cmdArg.indexOf("(");
    if (begin == -1) {return null;}
    var end = cmdArg.indexOf(")", begin+1);
    if (end == -1) {return null;}
    var subtreeNodeName = cmdArg.substring(begin+1, end);
    if (subtreeNodeName.length == 0) {return null;}
    var subtreeRoot = aRoot["subtrees"][subtreeNodeName];
    if (subtreeRoot) {
      var nodeExpression = cmdArg.substring(end+1);
      if (nodeExpression) {
        nodeExpression = nodeExpression.trim();
        if (nodeExpression.indexOf("xpath") == -1) {
          return subtreeRoot.xpath(nodeExpression);
        }
        else {
          // handle internal xpath
          begin = nodeExpression.indexOf("(");
          if (begin == -1) {return null;}
          end = nodeExpression.indexOf(")", begin+1);
          if (end == -1) {return null;}
          var xpathExpression = nodeExpression.substring(begin+1, end);
          if (xpathExpression.length == 0) {return null;}
          return subtreeRoot.xpath(xpathExpression);
        }
      }
    }
    return null;
  }-*/;
  
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
  protected void speak(String textToSpeak, String ssuLang, Object ssuVoice, float ssuVolume, float ssuRate, float ssuPitch) {
    if (speakResponsiveVoice(textToSpeak, ssuLang, ssuVoice, ssuVolume, ssuRate, ssuPitch)) {return;}
    speakGoogle(textToSpeak, ssuLang, ssuVoice, ssuVolume, ssuRate, ssuPitch);
  }
  
  /**
   * Speak using the Google API.
   */
  protected native boolean speakGoogle(String textToSpeak, String ssuLang, Object ssuVoice, float ssuVolume, float ssuRate, float ssuPitch) /*-{
    // Firefox defines speechSynthesis but not SpeechSynthesisUtterance
    //if (typeof $wnd.speechSynthesis == "undefined") {return;}
    if (typeof $wnd.SpeechSynthesisUtterance == "undefined") {return false;}
    var newUtterance = new $wnd.SpeechSynthesisUtterance();
    newUtterance.text = textToSpeak.substring(0, 160); // long utterances crash the speech synthesizer
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
    return true;
  }-*/;
  
  /**
   * Speak using the ResponsiveVoice API.
   * params are "used to add optional pitch (range 0 to 2), rate (range 0 to 1.5), volume (range 0 to 1) and callbacks"
   */
  protected native boolean speakResponsiveVoice(String textToSpeak, String ssuLang, Object ssuVoice, float ssuVolume, float ssuRate, float ssuPitch) /*-{
    if (typeof $wnd.responsiveVoice == "undefined" || !$wnd.responsiveVoice.voiceSupport) {return false;}
    var params = {};
    var hasParams = false;
    if (ssuVolume != -1.0) {
      params.volume = ssuVolume;
      hasParams = true;
    }
    if (ssuRate != -1.0) {
      params.rate = ssuRate;
      hasParams = true;
    }
    if (ssuPitch != -1.0) {
      params.pitch = ssuPitch;
      hasParams = true;
    }
    if (hasParams) {
      $wnd.responsiveVoice.speak(textToSpeak, ssuVoice, params);
    }
    else {
      $wnd.responsiveVoice.speak(textToSpeak, ssuVoice);
    }
    return true;
  }-*/;
  
  /**
   * Determine whether or not speech synthesis is supported for this browser.
   * It checks for ResponsiveVoice API, or for Google API.
   * @return true or false
   */
  protected native boolean isSpeechSupported() /*-{
    var rc = typeof $wnd.responsiveVoice != "undefined" && $wnd.responsiveVoice.voiceSupport;
    if (!rc) {
      rc = typeof $wnd.SpeechSynthesisUtterance != "undefined";
    }
    return rc;
  }-*/;
  
  /**
   * Find a ResponsiveVoice or Google Voice.
   * @param name
   * @return A Voice object, or null.
   * ex: Object voice = findVoice("US English Female"); // ResponsiveVoice
   * ex: Object voice = findVoice("Google UK English Female"); // Google
   * 
   * TODO voices are loaded asynchronously, so need to wait for an event
// Google
window.speechSynthesis.onvoiceschanged = function() {
  var voices = $wnd.speechSynthesis.getVoices();
};

// ResponsiveVoice
var voices = $wnd.responsiveVoice.getVoices();
   */
  protected native Object findVoice(String name) /*-{
    var voices = null;
    if (typeof $wnd.responsiveVoice != "undefined" && $wnd.responsiveVoice.voiceSupport) {
      voices = $wnd.responsiveVoice.getVoices();
    }
    else if (typeof $wnd.SpeechSynthesisUtterance != "undefined") {
      voices = $wnd.speechSynthesis.getVoices();
    }
    else {
      return null;
    }
    for (var i = 0; i < voices.length; i++) {
      if (voices[i].name == name) {
        return voices[i];
      }
    }
    return null;
  }-*/;
  
  /**
   * Get the current value of the optional JavaScript variable speechOut.
   */
  protected native int getSpeechOut() /*-{
    if (!this.speechOut) {
      this.speechOut = 0; // no effect (default)
    }
    return this.speechOut;
  }-*/;
  
  /**
   * Get the current value of the optional JavaScript variable speechOut.
   */
  protected native void setSpeechOut(int speechOut) /*-{
    this.speechOut = speechOut;
  }-*/;
  
}

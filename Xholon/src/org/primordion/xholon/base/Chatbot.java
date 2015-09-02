/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2015 Ken Webb
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

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.util.MiscRandom;

/**
 * A chatbot (or chatterbot) can chat with a node in the Xholon tree.
 * At any given time it's located within, or references, one specific node in the app.
 * Typically it should be hidden from the other nodes in the app.
 * Typically it's invoked through a XholonConsole.
 * You can create a new Avatar by submitting "avatar" from a XholonConsole, while the app is running.
 * 
 * TODO
 * - handle eliza.getInitial(); at start of the chat
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://en.wikipedia.org/wiki/Chatterbot">wikipedia Chatterbot</a>
 * @see <a href="http://www.masswerk.at/elizabot/">elizabot</a>
 * @see XholonWorkbook Chatterbots d47cc95ddbcbe4e0e7d6
 * @since 0.9.1 (Created on April 4, 2015)
 */
public class Chatbot extends Xholon {
  
  // Variables
  public String roleName = null;
  protected double val;
  
  // the current location of the chatbot; its parent or the node it references
  protected IXholon contextNode = null;
  
  protected StringBuilder sb = null;
  
  // this is the name format
  protected String nameTemplate = IXholon.GETNAME_DEFAULT;
  
  protected static IXPath xpath = null;
  
  /**
   * An optional prefix if call println() or caption for each action.
   */
  protected String outPrefix = "";
  
  protected IApplication app = null;
  
  /**
   * A JavaScript object.
   */
  protected Object eliza = null;
  
  /**
   * Probability of returning an app-specific response.
   */
  protected double appSpecificResponseProb = 0.2;
  
  /**
   * Random app-specific responses to the user.
   */
  protected String[] rspnsList = {
  "Would you like to discuss _ ?",
  "Are you afraid of _ ?",
  "Have you heard the latest rumors about _ ?",
  "Is it true what they say about _ ?",
  "Does _ make you queasy ?",
  "Down at the pub they're whispering some surprising things about _.",
  "There's information about _ at the museum.",
  "I heard a song about _ on the radio.",
  "There was a story about _ on the evening news.",
  "The fortune teller says _ is in for hard times.",
  "Have you told your friends about _ ?",
  "There was an article about _ in the National Geographic.",
  "There was an expose about _ in the National Enquirer.",
  "I overheard someone talking about _.",
  "Have you googled for _ ?",
  "There was something online about _.",
  "Are you writing a story about _ ?",
  "Do you have a picture of _ ?",
  "What does \"_\" rhyme with ?",
  "What's the first letter in \"_\" ?",
  "What or who or where is _ ?",
  "Is _ a person, place, or thing ?",
  "Why is _ important ?",
  "Does talking about _ bother you ?",
  "What's your opinion on _ ?"
  };
  
  // constructor
  public Chatbot() {
    requireEliza();
  }
  
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
  public void setVal_Object(Object contextNode) {
    if (contextNode != this) {
      this.contextNode = (IXholon)contextNode;
    }
  }
  
  @Override
  public Object getVal_Object() {
    return this.contextNode;
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
    if (eliza == null) {
      eliza = initEliza();
    }
    super.act();
  }
  
  @Override
  public void processReceivedMessage(IMessage msg) {
    switch (msg.getSignal()) {
    case ISignal.SIGNAL_XHOLON_CONSOLE_REQ:
      String responseStr = processCommands((String)msg.getData());
      if ((responseStr != null) && (responseStr.length() > 0)) {
        //responseStr = COMMENT_START + responseStr + COMMENT_END + CMD_SEPARATOR;
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
      if ((responseStr != null) && (responseStr.length() > 0)) {
        //responseStr = COMMENT_START + responseStr + COMMENT_END + CMD_SEPARATOR;
      }
      return new Message(ISignal.SIGNAL_XHOLON_CONSOLE_RSP, "\n" + responseStr, this, msg.getSender());
    default:
      return super.processReceivedSyncMessage(msg);
    }
  }
  
  @Override
  public void doAction(String action) {
    String responseStr = processCommands(action);
    /*if (transcript && (responseStr != null) && (responseStr.length() > 0)) {
      this.println(" " + responseStr);
    }
    if (speech && (responseStr != null) && (responseStr.length() > 0)) {
      this.speak(" " + responseStr, ssuLang, ssuVoice, ssuVolume, ssuRate, ssuPitch);
    }
    if (debug) {
      this.consoleLog(responseStr);
    }*/
  }
  
  /**
   * Process one or more commands.
   * @param cmds (ex: "help")
   * @return a response
   */
  protected String processCommands(String cmds) {
    if (eliza == null) {
      eliza = initEliza();
    }
    sb = new StringBuilder();
    processCommand(cmds);
    return sb.toString();
  }
  
  protected void processCommand(String cmd) {
    cmd = cmd.trim();
    if (cmd.length() == 0) {return;}
    //if (cmd.startsWith(COMMENT_START)) {return;}
    //String[] data = null;
    /*if (cmd.indexOf('"') == -1) {
      data = cmd.split(" ", 4);
    }
    else {
      data = this.split(cmd, 4);
      //consoleLog(data);
    }*/
    //int len = data.length;
    switch (cmd) {
    case "help":
      help();
      break;
    case "vanish":
      vanish();
      break;
    default:
      if (eliza != null) {
        if (Math.random() < appSpecificResponseProb) {
          sb.append(doAppSpecificResponse(cmd));
        }
        else {
          sb.append(chat(eliza, cmd));
        }
      }
      break;
    }
  }
  
  /**
   * Provide some minimal help to users.
   */
  protected void help() {
    sb
    .append("Chatbot help");
  }
  
  /**
   * Become invisible to other nodes in the Xholon tree.
   * The chatbot still retains a link to it's parent in the tree, using contextNode.
   * Often it will be good to have a chatbot vanish once a XholonConsole or equivalent attaches to it.
   * If the chatbot does not have a parent node (it's not part of the tree and is already invisible),
   * then do nothing.
   */
  protected void vanish() {
    if (this.hasParentNode()) {
      this.removeChild();
    }
  }
  
  /**
   * Create a random app-specific response.
   * Examples:
   *  Have you been to Pemberley before?
   *  Does that have anything to do with the X that you are carrying?
   *  Are you afraid of going to X?
   *  Have you heard the latest rumors about X?
   * 
   * @param cmd The user's command/comment/question.
   */
  protected String doAppSpecificResponse(String cmd) {
    String cnName = contextNode.getRoleName();
    if (cnName == null) {
      cnName = "the " + contextNode.getXhcName();
    }
    //String rspns = "Would you like to discuss " + cnName + "?";
    int r = MiscRandom.getRandomInt(0, rspnsList.length);
    String str = rspnsList[r];
    int pos = str.indexOf("_");
    String rspns = str.substring(0, pos) + cnName + str.substring(pos+1);
    return rspns;
  }
  
  /**
   * Load the elizabot JavaScript files.
   */
  protected native void requireEliza() /*-{
    $wnd.xh.require("elizadata");
    $wnd.xh.require("elizabot");
  }-*/;
  
  protected native Object initEliza() /*-{
    if ($wnd.ElizaBot) {
      return new $wnd.ElizaBot();
    }
    return null;
  }-*/;
  
  protected native String chat(Object eliza, String humanStr) /*-{
    var reply = eliza.transform(humanStr);
    return reply;
  }-*/;
  
}

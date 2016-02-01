/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2016 Ken Webb
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

import java.util.HashMap;
import java.util.Map;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.Message;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.io.xml.XmlPrettyPrinter;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.XholonHelperService;

/**
 * Sprite as understood in Scratch and Snap.
 * Paste the following as a test:
<Sprite roleName="test01"><Attribute_String><![CDATA[
move (10) steps
move ((val) + (10)) steps
one [two] three (four) five <six> seven
[two] three (four) five <six>
]]></Attribute_String></Sprite>
 * 
 * TODO
 * - handle Control nesting properly
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="https://scratch.mit.edu">Scratch website</a>
 * @see <a href="http://snap.berkeley.edu">Snap website</a>
 * @since 0.9.1 (Created on January 15, 2016)
 */
public class Sprite extends AbstractAvatar {
  
  /**
   * The Sprite's script in simple text syntax, that mirrors exactly the Scratch/Snap block appearance.
   * TODO use the block syntax and names in Snap object.js
   * 
   * Example:
<Sprite roleName="test02"><![CDATA[
when I receive [postConfigure]
set [state] to [0]

when I receive [act]
if <(state) = 0> then
  say [Hello] for (0.5) secs
  broadcast [SIGNAL_ONE]
  set [state] to [1]

when I receive [SIGNAL_TWO]
say [Hello] for (0.5) secs
broadcast [SIGNAL_ONE]
]]></Sprite>
   * 
   * Example: TODO for now add END as final line in the script, to force processing of the "say" statement
<Sprite roleName="NestedBlocks01"><![CDATA[
when greenflag clicked
repeat (10)
  move (10) steps
  turn right (15) degrees
  say [Hello!] for (2) secs
  if <touching color [red] ?> then
    think [Touching red] for (2) secs
  else
    think [Hmm...]
  say ((123) + ((789) - (456)))
END
]]></Sprite>
   * 
   */
  protected String spriteScript = null;
  
  /**
   * Index into the spriteScript while parsing it.
   */
  protected int spriteScriptIx = 0;
  
  /**
   * The spriteScript in Xholon XML format.
   */
  protected String xhXmlStr = null;
  
  /**
   * Whether or not to write out the generated xhXmlStr, mostly for debug purposes.
   */
  protected boolean writeXhXmlStr = false;
  
  /**
   * The spriteScript as a Xholon subtree.
   */
  protected IXholon xhScriptRoot = null;
  
  /**
   * The number of spaces used to represent one level of indentation in the input (the spriteScript text).
   * This value will be calculated when tokenizing/parsing the first indented block, if any.
   */
  protected int indentIn1 = -1;
  
  /**
   * Whether or not xholonize() is currently parsing an lstring or comment where spaces should be retained,
   * or parsing something else where spaces should be dropped.
   */
  protected boolean retainSpaces = false;
  
  protected static final int BLOCKNAMEMAP_VALUEIX_SCRATCH = 0;
  protected static final int BLOCKNAMEMAP_VALUEIX_SNAP    = 1;
  
  /**
   * Character that starts a comment line.
   */
  protected static final String COMMENT_CHAR = ";";
  
  /**
   * Map from a block's naive name, to the internal Scratch name and to the internal Snap name.
   * The naive name is a concatenation of all the non-bracketed words in the graphical block.
   */
  protected Map<String, String[]> blockNameMap = null;
  
  protected String[] blockNameArr = {
  // naiveName, scratchName, snapName
  // Motion
  "movesteps", "forward:", "forward",
  "turnrightdegrees", "turnRight:", "turn",
  "turnleftdegrees", "turnLeft:", "turnLeft",
  "pointindirection", "heading:", "setHeading",
  "pointtowards", "pointTowards:", "doFaceTowards",
  "gotoxy", "gotoX:y:", "gotoXY",
  "goto", "gotoSpriteOrMouse:", "doGotoObject",
  "glidesecstoxy", "glideSecs:toX:y:elapsed:from:", "doGlide",
  "changexby", "changeXposBy:", "changeXPosition",
  "setxto", "xpos:", "setXPosition",
  "changeyby", "changeYposBy:", "changeYPosition",
  "setyto", "ypos:", "setYPosition",
  "ifonedge,bounce", "bounceOffEdge", "bounceOffEdge",
  "setrotationstyle", "setRotationStyle", "UNKNOWN",
  
  // Looks
  "sayforsecs", "say:duration:elapsed:from:", "doSayFor",
  "say", "say:", "bubble",
  "thinkforsecs", "think:duration:elapsed:from:", "doThinkFor",
  "think", "think:", "doThink",
  "show", "show", "show",
  "hide", "hide", "hide",
  "switchcostumeto", "lookLike:", "doSwitchToCostume",
  "nextcostume", "nextCostume", "doWearNextCostume",
  "switchbackdropto", "startScene", "UNKNOWN",
  "changeeffectby", "changeGraphicEffect:by:", "changeEffect",
  "seteffectto", "setGraphicEffect:to:", "setEffect",
  "cleargraphiceffects", "filterReset", "clearEffects",
  "changesizeby", "changeSizeBy:", "changeScale",
  "setsizeto%", "setSizeTo:", "setScale",
  "gotofront", "comeToFront", "comeToFront",
  "gobacklayers", "goBackByLayers:", "goBack",
  
  // Sound
  "playsound", "playSound:", "playSound",
  "playsounduntildone", "doPlaySoundAndWait", "doPlaySoundUntilDone",
  "stopallsounds", "stopAllSounds", "doStopAllSounds",
  "playdrumforbeats", "playDrum", "UNKNOWN",
  "restforbeats", "rest:elapsed:from:", "doRest",
  "playnoteforbeats", "noteOn:duration:elapsed:from:", "doPlayNote",
  "setinstrumentto", "instrument:", "UNKNOWN",
  "changevolumeby", "changeVolumeBy:", "UNKNOWN",
  "setvolumeto%", "setVolumeTo:", "UNKNOWN",
  "changetempoby", "changeTempoBy:", "doChangeTempo",
  "settempotobpm", "setTempoTo:", "doSetTempo",
  
  // Pen
  "clear", "clearPenTrails", "clear",
  "stamp", "stampCostume", "doStamp",
  "pendown", "putPenDown", "down",
  "penup", "putPenUp", "up",
  // if using "setPenHueTo:", the color must be a number from 0 to 199 in rainbow order from red to purple, so 150 is a bluish-purpleish
  // if using "penColor:", the color must be a decimal rgba value, for example 2858785 is a shade of green
  //"setpencolorto", "penColor:", "setColor", // set pen color to [COLOR]
  "changepencolorby", "changePenHueBy:", "changeHue",
  "setpencolorto", "setPenHueTo:", "setHue", // set pen color to (NUMBER)  ex: set pen color to (150)
  "changepenshadeby", "changePenShadeBy:", "changeBrightness",
  "setpenshadeto", "setPenShadeTo:", "setBrightness",
  "changepensizeby", "changePenSizeBy:", "changeSize",
  "setpensizeto", "penSize:", "setSize",
  
  // Data
  "setto", "setVar:to:", "doSetVar",
  "changeby", "changeVar:by:", "doChangeVar",
  "showvariable", "showVariable", "doShowVar",
  "hidevariable", "hideVariable", "doHideVar",
  "addto", "append:toList:", "doAddToList",
  "deleteof", "deleteLine:ofList:", "doDeleteFromList",
  "insertatof", "insert:at:ofList:", "doInsertInList",
  "replaceitemofwith", "setLine:ofList:to:", "doReplaceInList",
  "itemof", "getLine:ofList:", "reportListItem",
  "lengthof", "lineCountOfList:", "reportListLength",
  "contains?", "list:contains:", "reportListContainsItem",
  "showlist", "showList:", "UNKNOWN",
  "hidelist", "hideList:", "UNKNOWN",
  
  // Events
  "whengreenflagclicked", "whenGreenFlag", "receiveGo",
  "whenkeypressed", "whenKeyPressed", "receiveKey",
  "whenthisspriteclicked", "whenClicked", "UNKNOWN",
  "whenbackdropswitchesto", "whenSceneStarts", "UNKNOWN",
  "when>", "whenSensorGreaterThan", "UNKNOWN",
  "whenIreceive", "whenIReceive", "receiveMessage",
  "broadcast", "broadcast:", "doBroadcast",
  "broadcastandwait", "doBroadcastAndWait", "doBroadcastAndWait",
  
  // Control
  "waitsecs", "wait:elapsed:from:", "doWait",
  "repeat", "doRepeat", "doRepeat",
  "forever", "doForever", "doForever",
  "ifthen", "doIf", "doIf",
  "ifthenelse", "doIfElse", "doIfElse", // TODO how do I get the "else" part of the name ?
  "waituntil", "doWaitUntil", "doWaitUntil",
  "repeatuntil", "doUntil", "doUntil",
  "stop", "stopScripts", "doStopThis",
  "whenIstartasaclone", "whenCloned", "receiveOnClone",
  "createcloneof", "createCloneOf", "createClone",
  "deletethisclone", "deleteClone", "removeClone",
  
  // Sensing
  "touching?", "touching:", "reportTouchingObject", // angle
  "touchingcolor?", "touchingColor:", "reportTouchingColor", // angle
  "coloristouching?", "color:see:", "reportColorIsTouchingColor", // angle
  "distanceto", "distanceTo:", "reportDistanceTo", // ellipse
  "askandwait", "doAsk", "doAsk", // block
  "keypressed?", "keyPressed:", "reportKeyPressed", // angle
  "mousedown?", "mousePressed", "reportMouseDown", // angle
  "mousex", "mouseX", "reportMouseX", // ellipse
  "mousey", "mouseY", "reportMouseY", // ellipse
  "turnvideo", "setVideoState", "UNKNOWN",
  "setvideotransparencyto%", "setVideoTransparency", "UNKNOWN",
  "resettimer", "timerReset", "doResetTimer",
  "of", "getAttribute:of:", "reportAttributeOf",
  "dayssince2000", "timestamp", "UNKNOWN",
  "username", "getUserName", "UNKNOWN",
  
  // Operators
  "plus", "+", "reportSum",
  "minus", "-", "reportDifference",
  "times", "*", "reportProduct",
  "dividedby", "/", "reportQuotient",
  "pickrandomto", "randomFrom:to:", "reportRandom",
  "lt", "<", "reportLessThan",
  "eq", "=", "reportEquals",
  "gt", ">", "reportGreaterThan",
  "and", "&", "reportAnd",
  "or", "|", "reportOr",
  "not", "not", "reportNot",
  "join", "concatenate:with:", "reportJoinWords",
  "letterof", "letter:of:", "reportLetter",
  "lengthof", "stringLength:", "reportStringSize",
  "mod", "%", "reportModulus",
  "round", "rounded", "reportRound",
  //"of"  TODO
  
  // More Blocks
  "define", "procDef", "UNKNOWN"
  }; // end blockNameArr
  
  /**
   * Constructor.
   */
  public Sprite() {
    // test
    /*String str = "move (10) steps";
    consoleLog(str);
    consoleLog(this.tokenize(str));
    str = "move ((val) + (10)) steps";
    consoleLog(str);
    consoleLog(this.tokenize(str));*/
  }
  
  @Override
  public void setVal(String spriteScript) {
    this.setVal_String(spriteScript);
  }
  
  @Override
  public void setVal_String(String spriteScript) {
    this.spriteScript = spriteScript;
  }
  
  @Override
  public String getVal_String() {
    return spriteScript;
  }
  
  @Override
  public void postConfigure() {
    app = this.getApp();
    // the script has already been read and trimmed, so final newline is missing
    if (!spriteScript.endsWith("\n")) {
      // add trailing newline to make parsing easier
      spriteScript += "\n";
    }
    initBlockNameMap();
    
    //test();
        
    super.postConfigure();
  }
  
  protected void test() {
    // TEST 1
    xhXmlStr = this.xholonize();
    consoleLog(xhXmlStr);
    xhScriptRoot = makeXholonSubtree(xhXmlStr);
    xhScriptRoot.removeChild();
    
    // TEST 2
    String snapXmlStr = xholonSubtree2SnapXml(xhScriptRoot);
    consoleLog(snapXmlStr);
    
    // TEST 3
    String scratchJsonStr = xholonSubtree2ScratchJson(xhScriptRoot);
    consoleLog(scratchJsonStr);
  }
  
  @Override
  public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {
    super.toXmlAttributes(xholon2xml, xmlWriter);
    String str = "";
    str = spriteScript;
    if ((str != null) && (str.length() > 0)) {
      xmlWriter.writeStartElement("Attribute_String");
      xmlWriter.writeText(makeTextXmlEmbeddable(str));
      xmlWriter.writeEndElement("Attribute_String");
    }
  }
  
  /**
   * Request the scripts for this Sprite, in the original Xholon text format.
   */
  public static final int SIGNAL_SCRIPTS_XHOLONTEXT_REQ = 101;
  
  /**
   * Request the scripts for this Sprite, in Xholon XML text format.
   */
  public static final int SIGNAL_SCRIPTS_XHOLONXML_REQ = 102;
  
  /**
   * Request the scripts for this Sprite, in Scratch JSON text format.
   */
  public static final int SIGNAL_SCRIPTS_SCRATCHJSON_REQ = 103;
  
  /**
   * Request the scripts for this Sprite, in Snap XML text format.
   */
  public static final int SIGNAL_SCRIPTS_SNAPXML_REQ = 104;
  
  /**
   * Request to enable writing out the generated xhXmlStr.
   */
  public static final int SIGNAL_SCRIPTS_WRITEXHXML_REQ = 105;
  
  public static final int SIGNAL_SCRIPTS_RSP = 201;
  
  @Override
  public IMessage processReceivedSyncMessage(IMessage msg) {
    switch (msg.getSignal()) {
    
    case SIGNAL_SCRIPTS_XHOLONTEXT_REQ:
    {
      return new Message(SIGNAL_SCRIPTS_RSP, spriteScript, this, msg.getSender());
    }
    
    case SIGNAL_SCRIPTS_SCRATCHJSON_REQ:
    {
      if (xhXmlStr == null) {
        xhXmlStr = this.xholonize();
        if (writeXhXmlStr) {
          consoleLog(xhXmlStr);
        }
      }
      if (xhScriptRoot == null) {
        xhScriptRoot = makeXholonSubtree(xhXmlStr);
        xhScriptRoot.removeChild(); // detach it from the main Xholon tree
      }
      String scratchJsonStr = this.xholonSubtree2ScratchJson(xhScriptRoot);
      return new Message(SIGNAL_SCRIPTS_RSP, scratchJsonStr, this, msg.getSender());
    }
    
    case SIGNAL_SCRIPTS_SNAPXML_REQ:
    {
      if (xhXmlStr == null) {
        xhXmlStr = this.xholonize();
        if (writeXhXmlStr) {
          consoleLog(xhXmlStr);
        }
      }
      if (xhScriptRoot == null) {
        xhScriptRoot = makeXholonSubtree(xhXmlStr);
        xhScriptRoot.removeChild(); // detach it from the main Xholon tree
      }
      String snapXmlStr = this.xholonSubtree2SnapXml(xhScriptRoot);
      return new Message(SIGNAL_SCRIPTS_RSP, snapXmlStr, this, msg.getSender());
    }
    
    case SIGNAL_SCRIPTS_WRITEXHXML_REQ:
      writeXhXmlStr = (boolean)msg.getData();
      return new Message(SIGNAL_SCRIPTS_RSP, null, this, msg.getSender());
    
    default:
    {
      return super.processReceivedSyncMessage(msg);
    }
    
    } // end switch
  }
  
  /**
   * Initialize the block-name map.
   */
  protected void initBlockNameMap() {
    blockNameMap = new HashMap<String, String[]>();
    for (int i = 0; i < blockNameArr.length; i += 3) {
      String key = blockNameArr[i];
      String[] value = {blockNameArr[i+1], blockNameArr[i+2]};
      blockNameMap.put(key, value);
    }
  }
  
  /**
   * Write this Sprite's behavior/script text syntax as as an XML string.
   * @return an XML string
   */
  protected String xholonize() {
    spriteScriptIx = 0; // start at the beginning of the Sprite's text script
    
    StringBuilder sb = new StringBuilder();
    sb
    .append("<scripts>\n")
    .append("<sscript>\n");
    
    sb.append(xholonizeRecurse(new StringBuilder()));
    
    sb
    .append("</sscript>\n")
    .append("</scripts>");
    String xmlStr = sb.toString();
    String xmlStrPP = null;
    if (isShouldPrettyPrint()) {
			XmlPrettyPrinter p = new XmlPrettyPrinter();
			p.setOmitXmlDeclaration("yes");
			xmlStrPP = p.format(xmlStr);
			//this.println(xmlStrPP);
			return xmlStrPP;
		}
		else {
      //this.println(xmlStr);
      return xmlStr;
    }
  }
  
  /**
   * Write the Sprite's behavior (script) as a Xholon tree.
   * This is a temporary structure, and may or may not be inserted as a child of a Sprite node.
   * This can subsequently be used to generate Scratch JSON syntax, or Snap XML syntax.
   * @param xmlStr an XML string
   * @return a Xholon subtree, this subtree is still attached to the main Xholon tree as a child of a Sprite
   */
  protected IXholon makeXholonSubtree(String xmlStr) {
    XholonHelperService xhs = (XholonHelperService)app.getService(IXholonService.XHSRV_XHOLON_HELPER);
    xhs.pasteLastChild(this, xmlStr);
    return this.getLastChild();
  }
  
  /**
   * Whether or not to pretty-print the generated XML.
   */
  protected boolean isShouldPrettyPrint() {
    return true;
  }
  
  /**
   * Recursively build up a Xholon tree as an XML string.
   */
  protected String xholonizeRecurse(StringBuilder sb) {
    StringBuilder sbBlockNameOrValue = new StringBuilder();
    StringBuilder sbBlockContents = new StringBuilder();
    int state = 0; // initial spaces have to do with indenting/nesting
    int indentIn = 0;
    int prevIndentIn = 0;
    char c = '\0';
    while (spriteScriptIx < spriteScript.length()) {
      c = spriteScript.charAt(spriteScriptIx);
      spriteScriptIx++;
      switch (c) {
      
      // newline
      case '\n':
        final String blockNameOrValue = sbBlockNameOrValue.toString();
        if ("else".equals(blockNameOrValue)) {
          // TODO how do I change "ifthen" to "ifthenelse" ?
          sb
          .append("</sscript>\n");
        }
        else if (spriteScript.charAt(spriteScriptIx-2) == '\n') {
          // a blank line (2 newline characters in a row) means start a new script
          if (indentIn < prevIndentIn) {
            // this is the end of a nested Control block
            sb
            .append("</sscript>\n")
            .append("</block>\n");
          }
          sb
          .append("</sscript>\n")
          .append("\n")
          .append("<sscript>\n");
        }
        else if (blockNameOrValue.startsWith(COMMENT_CHAR)) {
          // this is not part of Scratch or Snap
          sb
          .append("<!-- ")
          .append(blockNameOrValue.substring(1))
          .append(" -->\n");
          retainSpaces = false;
        }
        else {
          if (indentIn < prevIndentIn) {
            // this is the end of a nested Control block
            sb
            .append("</sscript>\n")
            .append("</block>\n");
          }
          else if (indentIn > prevIndentIn) {
            // this is the start of a nested Control
            sb
            .append("<sscript>\n");
          }
          /*if (isControlBlock(blockNameOrValue)) {
            consoleLog("the following is a Control block:");
            consoleLog(blockNameOrValue);
          }
          if ("ifthen".equals(blockNameOrValue)) {
            consoleLog("ifthen");
            consoleLog(spriteScript.substring(spriteScriptIx));
          }*/
          boolean controlBlock = isControlBlock(blockNameOrValue);
          sb
          .append("<block roleName=\"")
          .append(blockNameOrValue)
          .append("\">\n")
          .append(sbBlockContents.toString());
          if (!controlBlock) {
            sb
            .append("</block>\n");
          }
        }
        //consoleLog(sb.toString());
        // start a new block
        sbBlockNameOrValue = new StringBuilder();
        sbBlockContents = new StringBuilder();
        state = 0; // this is required
        prevIndentIn = indentIn; // save the previous block's inputIn
        indentIn = 0;
        break;
      
      // space
      case ' ':
        if (state == 0) {
          indentIn++;
        }
        if (retainSpaces) {
          sbBlockNameOrValue.append(c);
        }
        break;
      
      // <
      case '<':
        char nextC = spriteScript.charAt(spriteScriptIx);
        if (nextC == ' ') {
          // this is the lt operator
          sbBlockNameOrValue.append(c);
        }
        else {
          // this is the start of an angle block
          sbBlockContents
          .append("<ablock roleName=\"")
          .append(xholonizeRecurse(new StringBuilder()))
          .append("</ablock>\n");
        }
        break;
      
      // (
      case '(':
        if (spriteScript.charAt(spriteScriptIx) == '(') {
          sbBlockContents
          .append("<eblock roleName=\"")
          .append(xholonizeRecurse(new StringBuilder()))
          .append("</eblock>\n");
        }
        else {
          sbBlockContents
          .append("<lnumber>")
          .append(xholonizeRecurse(new StringBuilder()))
          .append("</lnumber>\n");
        }
        break;
      
      // [
      case '[':
        retainSpaces = true;
        String bname = "lstring";
        switch(sbBlockNameOrValue.toString()) {
        case "touchingcolor":
        case "color":
        case "coloristouching":
          bname = "lcolor";
          break;
        default: break;
        }
        sbBlockContents
        .append("<").append(bname).append(">")
        .append(xholonizeRecurse(new StringBuilder()))
        .append("</").append(bname).append(">\n");
        retainSpaces = false;
        break;
      
      // >
      case '>':
        char prevC = spriteScript.charAt(spriteScriptIx-2);
        if (prevC == ' ') {
          // this is the gt operator
          sbBlockNameOrValue.append(c);
        }
        else {
          // this is the end of an angle block
          String strBN = sbBlockNameOrValue.toString(); // an actual blockNameOrValue "touchingcolor?"
          String strBC = sbBlockContents.toString();
          return strBN + "\">\n" + strBC;
        }
        break;
      
      // )
      case ')':
        String strBN = fixXholonizedBlockName(sbBlockNameOrValue.toString()); // a number "123", or an actual blockNameOrValue "lt"
        String strBC = sbBlockContents.toString();
        if (strBC.length() == 0) {
          // this is a number
          return fixXholonizedBlockName(strBN);
        }
        else {
          // this is the end of <eblock roleName="lt">\n ...
          return fixXholonizedBlockName(strBN) + "\">\n" + strBC;
        }
      
      // ]
      case ']':
        return sbBlockNameOrValue.toString() + sbBlockContents.toString();
      
      // ; comment
      case ';':
        retainSpaces = true;
        state = captureIndentIn1(state, indentIn);
        sbBlockNameOrValue.append(c);
        break;
      
      // any other character
      default:
        state = captureIndentIn1(state, indentIn);
        sbBlockNameOrValue.append(c);
        break;
      } // end switch
    } // end while
    
    // handle hanging nested blocks
    if (spriteScriptIx >= spriteScript.length()) {
      // the end of the script has been reached
      while (prevIndentIn > 0) {
        sb
        .append("</sscript>\n")
        .append("</block>\n");
        prevIndentIn -= indentIn1;
      }
    }
    
    return sb.toString();
  } // end xholonRecurse()
  
  /**
   * 
   */
  protected int captureIndentIn1(int state, int indentIn) {
    if (state == 0) {
      // handle indentation
      if ((indentIn1 == -1) && (indentIn > 0)) {
        // initialize the value of indentIn1
        indentIn1 = indentIn;
      }
      state = 1;
    }
    return state;
  }
  
  /**
   * Is this a Scratch Control block?
   */
  protected boolean isControlBlock(String blockNameOrValue) {
    boolean iscb = false;
    switch (blockNameOrValue) {
    case "repeat":
    case "forever":
    case "ifthen":
    case "ifthenelse":
    case "repeatuntil":
    //case "define": // "define" is a hat block
      iscb = true;
      break;
    default: break;
    }
    return iscb;
  }
  
  /**
   * Fix block names that don't work in XML, and/or that are single non-alphanumeric characters.
   */
  protected String fixXholonizedBlockName(String inBlockName) {
    String outBlockName = inBlockName;
    switch (inBlockName) {
    case "-": outBlockName = "minus"; break; // "-" fails during generation of Xholon subtree
    case "+": outBlockName = "plus"; break;
    case "*": outBlockName = "times"; break;
    case "/": outBlockName = "dividedby"; break;
    case "<": outBlockName = "lt"; break;
    case "=": outBlockName = "eq"; break;
    case ">": outBlockName = "gt"; break;
    default: break;
    }
    return outBlockName;
  }
  
  /**
   * UNUSED FOR NOW
   * Separate a block into tokens.
   * Tokens are delimited by spaces.
   * Start and end brackets < > ( ) [ ] are also individual tokens.
   * @param block  ex: "move (10) steps"
   * @return an array of tokens  ex: "movesteps", "0", "move", "(", "10", ")", "steps"
   *  result[0] = the block's naive name
   *  result[1] = the number of levels of indentation
   */
  protected String[] tokenize(String block) {
    consoleLog(block);
    String blockName = "";
    int bracketLevel = 0;
    String result = "";
    String delim = " ";
    int state = 0; // spaces initially have to do with indenting/nesting
    int indentIn = 0;
    for (int i = 0; i < block.length(); i++) {
      char c = block.charAt(i);
      switch (c) {
      case ' ':
        if (state == 0) {
          indentIn++;
        }
        else {
          result += delim;
        }
        break;
      case '<':
        result += c + delim;
        bracketLevel++;
        break;
      case '(':
        result += c + delim;
        bracketLevel++;
        break;
      case '[':
        result += c + delim;
        bracketLevel++;
        break;
      case '>':
        result += delim + c;
        bracketLevel--;
        break;
      case ')':
        result += delim + c;
        bracketLevel--;
        break;
      case ']':
        result += delim + c;
        bracketLevel--;
        break;
      default:
        if (state == 0) {
          // handle indentation
          if ((indentIn1 == -1) && (indentIn > 0)) {
            // initialize the value of indentIn1
            indentIn1 = indentIn;
          }
          if (indentIn == 0) {
            result += indentIn + delim;
          }
          else {
            result += "" + (indentIn/indentIn1) + delim;
          }
          state = 1;
        }
        result += c;
        if (bracketLevel == 0) {
          blockName += c;
        }
        break;
      }
    }
    
    result = blockName + delim + result;
    String[] resultArr = result.split(delim);
    consoleLog(resultArr);
    return resultArr;
  }
  
  /**
   * Walk the Xholon subtree for the Scratch/Snap code, and generate an XML String in Snap format.
   * @param root The root of the code subtree, typically the "scripts" node.
   */
  protected String xholonSubtree2SnapXml(IXholon root) {
    StringBuilder sb = new StringBuilder();
    String indent = "";
    xholonSubtree2SnapXmlRecurse(root, indent, sb);
    return sb.toString();
  }
  
  /**
   * Recursively generate Snap format.
   * TODO write x, y for each outer script
   */
  protected void xholonSubtree2SnapXmlRecurse(IXholon node, String indent, StringBuilder sb) {
    if (node == null) {return;}
    String xhNodeName = node.getXhcName();
    String xhRoleName = node.getRoleName();
    String xhContent = node.getVal_String();
    String tagName = null;
    String s = null;
    
    switch(xhNodeName) {
    case "scripts": tagName = "scripts"; break;
    case "sscript": tagName = "script"; break;
    case "block":
    case "ablock":
    case "eblock":
      tagName = "block";
      if ("ifthen".equals(xhRoleName)) {
        // distinguish "ifthen" from "ifthenelse" by how many children the node has (2 vs 3)
        int numChildren = node.getNumChildren(false);
        if (numChildren == 3) {
          xhRoleName = "ifthenelse";
        }
      }
      String[] arr = blockNameMap.get(xhRoleName);
      if (arr != null && arr.length > 1) {
        s = arr[BLOCKNAMEMAP_VALUEIX_SNAP];
      }
      if (s == null) {
        // assume this is a defined name (ex: DrawSquare)
        s = xhRoleName;
      }
      break;
    case "lnumber": tagName = "l"; break;
    case "lstring": tagName = "l"; break;
    case "lcolor": tagName = "color"; break;
    default: break;
    }
    
    sb
    .append(indent)
    .append("<")
    .append(tagName);
    if (s != null) {
      sb
      .append(" s=\"")
      .append(s)
      .append("\"");
    }
    sb
    .append(">");
    if (xhContent != null) {
      sb
      .append(xhContent);
    }
    else {
      sb.append("\n");
      IXholon childNode = node.getFirstChild();
      while (childNode != null) {
        xholonSubtree2SnapXmlRecurse(childNode, indent + "  ", sb);
        childNode = childNode.getNextSibling();
      }
      sb.append(indent);
    }
    
    sb
    .append("</")
    .append(tagName)
    .append(">\n");
  }
  
    /**
   * Walk the Xholon subtree for the Scratch/Snap code, and generate an JSON String in Scratch format.
   * @param root The root of the code subtree, typically the "scripts" node.
   */
  protected String xholonSubtree2ScratchJson(IXholon root) {
    StringBuilder sb = new StringBuilder();
    String indent = "";
    xholonSubtree2ScratchJsonRecurse(root, indent, sb);
    sb.append("]"); // matches initial "scripts": [
    return sb.toString();
  }
  
  /**
   * Recursively generate Scratch JSON format.
   */
  protected void xholonSubtree2ScratchJsonRecurse(IXholon node, String indent, StringBuilder sb) {
    if (node == null) {return;}
    String xhNodeName = node.getXhcName();
    String xhRoleName = node.getRoleName();
    String xhContent = node.getVal_String();
    String s = null;
    
    switch(xhNodeName) {
    case "scripts":
      sb.append("\"scripts\": [");
      break;
    case "sscript":
      sb.append("\n").append(indent).append("[");
      if ("scripts".equals(node.getParentNode().getXhcName())) {
        sb.append("25, 25, [");
      }
      break;
    case "block":
    case "ablock":
    case "eblock":
      if ("ifthen".equals(xhRoleName)) {
        // distinguish "ifthen" from "ifthenelse" by how many children the node has (2 vs 3)
        int numChildren = node.getNumChildren(false);
        if (numChildren == 3) {
          xhRoleName = "ifthenelse";
        }
      }
      String[] arr = blockNameMap.get(xhRoleName);
      if (arr != null && arr.length > 1) {
        s = arr[BLOCKNAMEMAP_VALUEIX_SCRATCH];
      }
      if (s == null) {
        // assume this is a defined name (ex: DrawSquare)
        // TODO I'm prepending call as a temporary fix
        s = "call\", \"" + xhRoleName;
      }
      sb.append("\n").append(indent).append("[\"").append(s).append("\"");
      if (node.hasChildNodes()) {
        sb.append(", ");
      }
      break;
    case "lnumber":
      sb.append(xhContent);
      break;
    case "lstring":
      sb.append("\"").append(xhContent).append("\"");
      break;
    case "lcolor":
      sb.append("\"").append(xhContent).append("\"");
      break;
    default: break;
    }
    
    IXholon childNode = node.getFirstChild();
    String childIndent = indent + "  ";
    if ("scripts".equals(xhNodeName)) {
      childIndent = indent;
    }
    while (childNode != null) {
      xholonSubtree2ScratchJsonRecurse(childNode, childIndent, sb);
      childNode = childNode.getNextSibling();
    }
    
    switch(xhNodeName) {
    case "sscript":
      sb.append("]");
      if ("scripts".equals(node.getParentNode().getXhcName())) {
        sb.append("]");
      }
      if (node.hasNextSibling()) {
        sb.append(",");
      }
      else {
        sb.append("\n").append(indent.substring(0, indent.length()-2));
      }
      break;
    case "block":
    case "ablock":
    case "eblock":
      sb.append("]");
      if (node.hasNextSibling()) {
        sb.append(",");
      }
      else {
        sb.append("\n").append(indent.substring(0, indent.length()-2));
      }
      break;
    default:
      if (node.hasNextSibling()) {
        sb.append(", ");
      }
      break;
    }
    
  }
  
}

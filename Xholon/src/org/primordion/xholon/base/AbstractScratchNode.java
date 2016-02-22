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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.Message;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.io.xml.XmlPrettyPrinter;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.XholonHelperService;

/**
 * Scratch node (Sprite or Stage) as understood in Scratch and Snap.
 * 
 * The following XML can be included in Xholon workbooks in the <_-.XholonClass>
  <!-- nodes that can appear in a Scratch script -->
  <scratchdetails/>
  <variables/>
  <variable/>
  <scripts/>
  <sscript/>
  <block/>
  <xblock>
    <ablock/> <!-- angle block     <...> -->
    <eblock/> <!-- ellipse block   (...) -->
  </xblock>
  <literal superClass="Attribute_String">
    <lnumber/> <!-- 123 -->
    <lstring/> <!-- my cat is crazy -->
    <lcolor/>  <!-- red -->
    <lvariable> <!-- username xhappname -->
      <bivariable/> <!-- bi  Scratch built-in variable -->
      <udvariable/> <!-- ud  user-defined variable -->
    </lvariable>
  </literal>
 *
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="https://scratch.mit.edu">Scratch website</a>
 * @see <a href="http://snap.berkeley.edu">Snap website</a>
 * @since 0.9.1 (Created on January 15, 2016)
 */
public abstract class AbstractScratchNode extends AbstractAvatar {
  
  /**
   * The Sprite's or Stage's script in simple text syntax, that mirrors exactly the Scratch/Snap block appearance.
   */
  protected String scratchScript = null;
  
  /**
   * Index into the scratchScript while parsing it.
   */
  protected int scratchScriptIx = 0;
  
  /**
   * The scratchScript in Xholon XML format.
   */
  protected String xhXmlStr = null;
  
  /**
   * Whether or not to write out the generated xhXmlStr, mostly for debug purposes.
   */
  protected boolean writeXhXmlStr = false;
  
  /**
   * Whether or not to write out the scripts as human-readable English text.
   */
  protected boolean writeEngTxt = false;
  /**
   * The scratchScript as a Xholon subtree.
   */
  protected IXholon xhScriptRoot = null;
  
  /**
   * The number of spaces used to represent one level of indentation in the input (the scratchScript text).
   * This value will be calculated when tokenizing/parsing the first indented block, if any.
   */
  protected int indentIn1 = -1;
  
  /**
   * A Scratch node may have user-defined variables.
   */
  protected StringBuilder sbVariables = null;
  
  /**
   * A Scratch node has standard Xholon attributes (ex: "xhrolename") that may be written as user-defined variables.
   */
  protected StringBuilder sbStandardAttributes = null;
  
  /**
   * Whether or not xholonize() is currently parsing an lstring or comment where spaces should be retained,
   * or parsing something else where spaces should be dropped.
   */
  protected boolean retainSpaces = false;
  
  protected static final int BLOCKNAMEMAP_VALUEIX_SCRATCH = 0;
  protected static final int BLOCKNAMEMAP_VALUEIX_SNAP    = 1;
  protected static final int BLOCKNAMEMAP_VALUEIX_ENGLISH = 2;
  
  /**
   * Character that starts a comment line.
   */
  protected static final String COMMENT_CHAR = ";";
  
  protected static final String ENGLISHTEXT_SEP_CHAR = "_";
  
  /**
   * Set containing the names of all user-defined variables.
   */
  protected Set<String> userDefinedVarNameSet = null;
  
  /**
   * Set contining the names of all Scratch function parameters  ex: "sides" "x" "y" .
   */
  protected Set<String> paramsNameSet = null;
  
  /**
   * Map from a Scratch function's name to its optional set of parameter types.
   * "%n"  "%s %n %b"
   */
  protected Map<String, String> functionTypeMap = null;
  
  /**
   * Map from a block's naive name, to the internal Scratch name and to the internal Snap name and to an English expression.
   * The naive name is a concatenation of all the non-bracketed words in the graphical block.
   */
  protected Map<String, String[]> blockNameMap = null;
  
  /**
   * These are the blocks that are common to all Scratch nodes, including Stage and Sprite.
   */
  protected String[] blockNameArr = {
  // naiveName, scratchName, snapName, EnglishSentenceOrPhrase
  // Motion - Stage has none of these, so there are no common blocks
  
  // Looks
  "switchbackdropto", "startScene", "UNKNOWN", "Switch backdrop to _.",
  "changeeffectby", "changeGraphicEffect:by:", "changeEffect", "Change _ effect by _.",
  "seteffectto", "setGraphicEffect:to:", "setEffect", "Set _ effect to _.",
  "cleargraphiceffects", "filterReset", "clearEffects", "Clear graphic effects.",
  
  // Sound
  "playsound", "playSound:", "playSound", "Play sound _.",
  "playsounduntildone", "doPlaySoundAndWait", "doPlaySoundUntilDone", "Play sound _ until done.",
  "stopallsounds", "stopAllSounds", "doStopAllSounds", "Stop all sounds.",
  "playdrumforbeats", "playDrum", "UNKNOWN", "Play drum _ for _ beats.",
  "restforbeats", "rest:elapsed:from:", "doRest", "Rest for _ beats.",
  "playnoteforbeats", "noteOn:duration:elapsed:from:", "doPlayNote", "Play note _ for _ beats.",
  "setinstrumentto", "instrument:", "UNKNOWN", "Set instrument to _.",
  "changevolumeby", "changeVolumeBy:", "UNKNOWN", "Change volume by _.",
  "setvolumeto%", "setVolumeTo:", "UNKNOWN", "Set volume to _ %.",
  "changetempoby", "changeTempoBy:", "doChangeTempo", "Change tempo by _.",
  "settempotobpm", "setTempoTo:", "doSetTempo", "Set tempo to _ bpm.",
  
  // Pen
  "clear", "clearPenTrails", "clear", "Clear.",
  
  // Data
  "setto", "setVar:to:", "doSetVar", "Set _ to _.",
  "changeby", "changeVar:by:", "doChangeVar", "Change _ by _.",
  "showvariable", "showVariable", "doShowVar", "Show variable _.",
  "hidevariable", "hideVariable", "doHideVar", "Hide variable _.",
  "addto", "append:toList:", "doAddToList", "Add _ to _.",
  "deleteof", "deleteLine:ofList:", "doDeleteFromList", "Delete _ of _.",
  "insertatof", "insert:at:ofList:", "doInsertInList", "Insert _ at _ of _.",
  "replaceitemofwith", "setLine:ofList:to:", "doReplaceInList", "Replace item _ of _ with _.",
  "itemof", "getLine:ofList:", "reportListItem", "item _ of _",
  "lengthof", "lineCountOfList:", "reportListLength", "length of _.",
  "contains?", "list:contains:", "reportListContainsItem", "_ contains _?",
  "showlist", "showList:", "UNKNOWN", "Show list _.",
  "hidelist", "hideList:", "UNKNOWN", "Hide list _.",
  
  // Events
  "whengreenflagclicked", "whenGreenFlag", "receiveGo", "When green flag clicked:",
  "whenkeypressed", "whenKeyPressed", "receiveKey", ".",
  "whenbackdropswitchesto", "whenSceneStarts", "UNKNOWN", ".",
  "when>", "whenSensorGreaterThan", "UNKNOWN", ".",
  "whenIreceive", "whenIReceive", "receiveMessage", "When I receive _:",
  "broadcast", "broadcast:", "doBroadcast", "Broadcast _.",
  "broadcastandwait", "doBroadcastAndWait", "doBroadcastAndWait", ".",
  
  // Control
  "waitsecs", "wait:elapsed:from:", "doWait", "Wait _ seconds.",
  "repeat", "doRepeat", "doRepeat", "Repeat _ times:",
  "forever", "doForever", "doForever", "Forever:",
  "ifthen", "doIf", "doIf", "If _ then _",
  "ifthenelse", "doIfElse", "doIfElse", "If _ then _ else _",
  "waituntil", "doWaitUntil", "doWaitUntil", "Wait until _.",
  "repeatuntil", "doUntil", "doUntil", "Repeat until _:",
  "stop", "stopScripts", "doStopThis", "Stop all.",
  "createcloneof", "createCloneOf", "createClone", "Create clone of _.",
  
  // Sensing
  "askandwait", "doAsk", "doAsk", "Ask _ and wait.", // block
  "keypressed?", "keyPressed:", "reportKeyPressed", "key _ pressed?", // angle
  "mousedown?", "mousePressed", "reportMouseDown", "mouse down?", // angle
  "mousex", "mouseX", "reportMouseX", "mouse X", // ellipse
  "mousey", "mouseY", "reportMouseY", "mouse y", // ellipse
  "turnvideo", "setVideoState", "UNKNOWN", "Turn video _.",
  "setvideotransparencyto%", "setVideoTransparency", "UNKNOWN", "Set video transparency to _ %.",
  "resettimer", "timerReset", "doResetTimer", "Reset timer.",
  "of", "getAttribute:of:", "reportAttributeOf", "_ of _",
  "dayssince2000", "timestamp", "UNKNOWN", "days since 2000",
  "username", "getUserName", "UNKNOWN", "username",
  
  // Operators
  "plus", "+", "reportSum", "_ + _",
  "minus", "-", "reportDifference", "_ - _",
  "times", "*", "reportProduct", "_ * _",
  "dividedby", "/", "reportQuotient", "_ / _",
  "pickrandomto", "randomFrom:to:", "reportRandom", "pick random _ to _",
  "lt", "<", "reportLessThan", "_ < _",
  "eq", "=", "reportEquals", "_ = _",
  "gt", ">", "reportGreaterThan", "_ > _",
  "and", "&", "reportAnd", "_ and _",
  "or", "|", "reportOr", "_ or _",
  "not", "not", "reportNot", "not _",
  "join", "concatenate:with:", "reportJoinWords", "join _ _",
  "letterof", "letter:of:", "reportLetter", "letter _ of _",
  "lengthof", "stringLength:", "reportStringSize", "length of _",
  "mod", "%", "reportModulus", "_ mod _",
  "round", "rounded", "reportRound", "round _",
  
  // More Blocks
  "define", "procDef", "UNKNOWN", "Define _:",
  
  // Xholon
  "consolelog", "consolelog", "consolelog", "consolelog _.",
  "xhprint", "xhprint", "xhprint", "xhprint _.",
  "xhprintln", "xhprintln", "xhprintln", "xhprintln _."
  }; // end blockNameArr
  
  /**
   * Constructor.
   */
  public AbstractScratchNode() {}
  
  @Override
  public void setVal(String scratchScript) {
    this.setVal_String(scratchScript);
  }
  
  @Override
  public void setVal_String(String scratchScript) {
    this.scratchScript = scratchScript;
  }
  
  @Override
  public String getVal_String() {
    return scratchScript;
  }
  
  @Override
  public void postConfigure() {
    app = this.getApp();
    if (scratchScript != null) {
      // the script has already been read and trimmed, so final newline is missing
      if (!scratchScript.endsWith("\n")) {
        // add trailing newline to make parsing easier
        scratchScript += "\n";
      }
      initBlockNameMap();
      userDefinedVarNameSet = new HashSet<String>();
      paramsNameSet = new HashSet<String>();
      functionTypeMap = new HashMap<String, String>();
      
      //test();
    }
        
    super.postConfigure();
  }
  
  /**
   * Make the Sprite- or Stage-specific array of block names.
   */
  protected abstract String[] makeBlockNameArr();
  
  protected void test() {
    // TEST 1
    xhXmlStr = this.xholonize();
    consoleLog(xhXmlStr);
    xhScriptRoot = makeXholonSubtree(xhXmlStr);
    xhScriptRoot.removeChild();
    
    // TEST 2
    //String snapXmlStr = xholonSubtree2SnapXml(xhScriptRoot);
    //consoleLog(snapXmlStr);
    
    // TEST 3
    //String scratchJsonStr = xholonSubtree2ScratchJson(xhScriptRoot);
    //consoleLog(scratchJsonStr);
    
    // TEST 4
    String englishTextStr = xholonSubtree2EnglishText(xhScriptRoot);
    consoleLog(englishTextStr);
  }
  
  @Override
  public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {
    super.toXmlAttributes(xholon2xml, xmlWriter);
    String str = scratchScript;
    if ((str != null) && (str.length() > 0)) {
      xmlWriter.writeStartElement("Attribute_String");
      xmlWriter.writeText(makeTextXmlEmbeddable(str));
      xmlWriter.writeEndElement("Attribute_String");
    }
  }
  
  /**
   * Request the scripts for this Scratch node, in the original Xholon text format.
   */
  public static final int SIGNAL_SCRIPTS_XHOLONTEXT_REQ = 101;
  
  /**
   * Request the scripts for this Scratch node, in Xholon XML text format.
   */
  public static final int SIGNAL_SCRIPTS_XHOLONXML_REQ = 102;
  
  /**
   * Request the scripts for this Scratch node, in Scratch JSON text format.
   */
  public static final int SIGNAL_SCRIPTS_SCRATCHJSON_REQ = 103;
  
  /**
   * Request the scripts for this Scratch node, in Snap XML text format.
   */
  public static final int SIGNAL_SCRIPTS_SNAPXML_REQ = 104;
  
  /**
   * Request to enable writing out the generated xhXmlStr.
   */
  public static final int SIGNAL_SCRIPTS_WRITEXHXML_REQ = 105;
  
  /**
   * Request to enable writing out the scripts as human-readable English text.
   */
  public static final int SIGNAL_SCRIPTS_WRITEENGTXT_REQ = 106;
  
  public static final int SIGNAL_SCRIPTS_RSP = 201;
  
  @Override
  public IMessage processReceivedSyncMessage(IMessage msg) {
    switch (msg.getSignal()) {
    
    case SIGNAL_SCRIPTS_XHOLONTEXT_REQ:
    {
      return new Message(SIGNAL_SCRIPTS_RSP, scratchScript, this, msg.getSender());
    }
    
    case SIGNAL_SCRIPTS_SCRATCHJSON_REQ:
    {
      if (xhXmlStr == null) {
        xhXmlStr = this.xholonize();
        if (writeXhXmlStr) {
          consoleLog(xhXmlStr);
        }
      }
      String scratchJsonStr = null;
      if ((xhXmlStr != null) && (xhXmlStr.length() > 0)) {
        if (xhScriptRoot == null) {
          xhScriptRoot = makeXholonSubtree(xhXmlStr);
          xhScriptRoot.removeChild(); // detach it from the main Xholon tree
        }
        if (writeEngTxt) {
          String englishTextStr = xholonSubtree2EnglishText(xhScriptRoot);
          consoleLog(englishTextStr);
        }
        scratchJsonStr = this.xholonSubtree2ScratchJson(xhScriptRoot);
      }
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
      if (writeEngTxt) {
        String englishTextStr = xholonSubtree2EnglishText(xhScriptRoot);
        consoleLog(englishTextStr);
      }
      String snapXmlStr = this.xholonSubtree2SnapXml(xhScriptRoot);
      return new Message(SIGNAL_SCRIPTS_RSP, snapXmlStr, this, msg.getSender());
    }
    
    case SIGNAL_SCRIPTS_WRITEXHXML_REQ:
      writeXhXmlStr = (boolean)msg.getData();
      return new Message(SIGNAL_SCRIPTS_RSP, null, this, msg.getSender());
    
    case SIGNAL_SCRIPTS_WRITEENGTXT_REQ:
      writeEngTxt = (boolean)msg.getData();
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
    blockNameArr = makeBlockNameArr();
    blockNameMap = new HashMap<String, String[]>();
    for (int i = 0; i < blockNameArr.length; i += 4) {
      String key = blockNameArr[i];
      String[] value = {blockNameArr[i+1], blockNameArr[i+2], blockNameArr[i+3]};
      blockNameMap.put(key, value);
    }
    //consoleLog(this.getName());
    //consoleLog(blockNameMap);
  }
  
  /**
   * Write this Scratch node's behavior/script text syntax as as an XML string.
   * @return an XML string
   */
  protected String xholonize() {
    if (scratchScript == null) {return "";}
    scratchScriptIx = 0; // start at the beginning of the Scratch node's text script
    
    sbVariables = new StringBuilder();
    sbStandardAttributes = new StringBuilder();
    StringBuilder sb = new StringBuilder();
    
    writeStandardAttributes(sbStandardAttributes);
    
    String scriptsStr = xholonizeRecurse(new StringBuilder());
    
    if (sbVariables.length() == 0) {
      // there were no user-defined Scratch variables, so provide another chance to write out standard Xholon attributes (ex: "xhrolename")
      String statts = sbStandardAttributes.toString();
      if ((statts != null) && (statts.length() > 0)) {
        sbVariables
        .append("<variables>\n")
        .append(statts)
        .append("</variables>\n");
      }
    }
    
    // include the full Scratch node name to make the XML more human-readable
    sb.append("<scratchdetails scratchname=\"").append(this.getName()).append("\">\n");
    sb.append(sbVariables.toString());
    sb
    .append("<scripts>\n")
    .append("<sscript>\n");
    
    sb.append(scriptsStr);
    
    sb
    .append("</sscript>\n")
    .append("</scripts>");
    sb.append("</scratchdetails>\n");
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
   * Write the Scratch node's behavior (script) as a Xholon tree.
   * This is a temporary structure, and may or may not be inserted as a child of a Scratch node.
   * This can subsequently be used to generate Scratch JSON syntax, or Snap XML syntax.
   * @param xmlStr an XML string
   * @return a Xholon subtree, this subtree is still attached to the main Xholon tree as a child of a Scratch node
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
    boolean parsingVars = false; // whether or not currently parsing variables
    while (scratchScriptIx < scratchScript.length()) {
      c = scratchScript.charAt(scratchScriptIx);
      scratchScriptIx++;
      switch (c) {
      
      // newline
      case '\n':
        final String blockNameOrValue = sbBlockNameOrValue.toString();
        if ("variables".equals(blockNameOrValue)) {
          parsingVars = true;
          retainSpaces = true;
          sbVariables
          .append("<variables>\n")
          .append(sbStandardAttributes.toString());
        }
        else if ("else".equals(blockNameOrValue)) {
          sb
          .append("</sscript>\n");
        }
        else if (scratchScript.charAt(scratchScriptIx-2) == '\n') {
          // a blank line (2 newline characters in a row) means start a new script
          if (parsingVars) {
            sbVariables
            .append("</variables>\n");
            parsingVars = false;
            retainSpaces = false;
          }
          else {
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
        }
        else if (blockNameOrValue.startsWith(COMMENT_CHAR)) {
          // this is not part of Scratch or Snap
          sb
          .append("<!-- ")
          .append(blockNameOrValue.substring(1))
          .append(" -->\n");
          retainSpaces = false;
        }
        else if (parsingVars) {
          String[] nameVal = blockNameOrValue.split(" ");
          if (nameVal.length == 2) {
            /*sbVariables
            .append("<variable>\n")
            .append("<udvariable>")
            .append(nameVal[0]) // name
            .append("</udvariable>\n")
            // it could be a number or string or color; Scratch/phosphorus is OK if it's just a string
            .append("<lstring>")
            .append(nameVal[1]) // initial value
            .append("</lstring>\n")
            .append("</variable>\n");
            userDefinedVarNameSet.add(nameVal[0]);*/
            writeAttribute(nameVal[0], nameVal[1], sbVariables); // name, initial value
          }
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
      {
        char nextC = scratchScript.charAt(scratchScriptIx);
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
      }
      
      // (
      case '(':
      {
        char nextC = scratchScript.charAt(scratchScriptIx);
        String ellipseContent = xholonizeRecurse(new StringBuilder());
        if (nextC == '(') {
          sbBlockContents
          .append("<eblock roleName=\"")
          .append(ellipseContent)
          .append("</eblock>\n");
        }
        else if (nextC == '[') {
          // TODO ([sqrt] of (9))
          sbBlockContents
          .append("<eblock roleName=\"")
          .append(ellipseContent)
          .append("</eblock>\n");
        }
        else if (isBuiltinVariable(ellipseContent)) {
          sbBlockContents
          .append("<bivariable>")
          .append(ellipseContent)
          .append("</bivariable>\n");
        }
        else if (isUserDefinedVariable(ellipseContent)) {
          sbBlockContents
          .append("<udvariable>")
          .append(ellipseContent)
          .append("</udvariable>\n");
        }
        else if (ellipseContent.charAt(0) >= 'a') {
          // TODO handle:  say (pick random (1) to (10))  etc.  all of these statements start with a lower-case letter
          sbBlockContents
          .append("<eblock roleName=\"")
          .append(ellipseContent)
          .append("</eblock>\n");
        }
        else {
          sbBlockContents
          .append("<lnumber>")
          .append(ellipseContent)
          .append("</lnumber>\n");
        }
        break;
      }
      
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
        String rectangleContent = xholonizeRecurse(new StringBuilder());
        if (isBuiltinVariable(rectangleContent)) {
          // "mouse-pointer" "left-right" "color"
          sbBlockContents
          .append("<bivariable>")
          .append(rectangleContent)
          .append("</bivariable>\n");
        }
        else if (isUserDefinedVariable(rectangleContent)) {
          // "sides" "degrees"
          sbBlockContents
          .append("<udvariable>")
          .append(rectangleContent)
          .append("</udvariable>\n");
        }
        else {
          sbBlockContents
          .append("<").append(bname).append(">")
          .append(rectangleContent)
          .append("</").append(bname).append(">\n");
        }
        retainSpaces = false;
        break;
      
      // >
      case '>':
        char prevC = scratchScript.charAt(scratchScriptIx-2);
        if (prevC == ' ') {
          // this is the gt operator
          sbBlockNameOrValue.append(c);
        }
        else {
          // this is the end of an angle block
          String strBN = fixXholonizedBlockName(sbBlockNameOrValue.toString()); // an actual blockNameOrValue "touchingcolor?"  "gt"
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
          return strBN;
        }
        else {
          // this is the end of <eblock roleName="lt">\n ...
          return strBN + "\">\n" + strBC;
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
    if (scratchScriptIx >= scratchScript.length()) {
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
   * Write standard Xholon attributes.
   * TODO there should be a efParam to control which attributes get written
   */
  protected String writeStandardAttributes(StringBuilder sbLocal) {
    // xhid
    // xhname
    
    // xhrolename
    String rn = this.getRoleName();
    if ((rn != null) && (rn.length() > 0)) {
      writeAttribute("xhrolename", rn, sbLocal);
    }
    
    // xhcname
    // xhval
    // xhstr
    
    return sbLocal.toString();
  }
  
  /**
   * Write a user-defined Scratch variable, or a Xholon attribute, as a variable.
   * Add the name to userDefinedVarNameSet.
   */
  protected void writeAttribute(String name, String value, StringBuilder sbLocal) {
    sbLocal
    .append("<variable>\n")
    .append("<udvariable>")
    .append(name) // name
    .append("</udvariable>\n")
    // it could be a number or string or color; Scratch/phosphorus is OK if it's just a string
    .append("<lstring>")
    .append(value) // value
    .append("</lstring>\n")
    .append("</variable>\n");
    userDefinedVarNameSet.add(name);
  }
  
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
   * Is this a builtin variable, either a standard Scratch variable such as "username" or a Xholon-based variable such as "xhappname"?
   */
  protected boolean isBuiltinVariable(String content) {
    boolean isbv = false;
    switch(content) {
    // Scratch
    case "username":
    case "mouse-pointer":
    case "left-right":
    case "color":
    case "xposition":
    case "yposition":
    case "direction":
    case "answer":
    // Xholon
    case "xhappname":
    case "xhrootname":
    case "xhmodelname":
      isbv = true;
      break;
    default: break;
    }
    return isbv;
  }
  
  /**
   * Is this a user-defined variable?
   */
  protected boolean isUserDefinedVariable(String content) {
    return userDefinedVarNameSet.contains(content);
  }
  
  /**
   * Is this a Scratch function parameter variable?
   * All function parameter variables are also user-defined variables.
   */
  protected boolean isParamVariable(String content) {
    return paramsNameSet.contains(content);
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
    if (sb.length() > 0) {
      sb.append("]"); // matches initial "scripts": [
    }
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
    case "scratchdetails":
      // ignore
      break;
    case "variables":
      sb.append(indent).append("\"variables\": [");
      break;
    case "variable":
      sb.append("\n").append(indent).append("{\n");
      break;
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
    {
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
        String functionTypeStr = functionTypeMap.get(xhRoleName);
        if (functionTypeStr == null) {
          functionTypeStr = "";
        }
        s = "call\", \"" + xhRoleName + functionTypeStr;
      }
      else if ("of".equals(xhRoleName)) {
        // distinguish "getAttribute:of:" from "computeFunction:of:" by type of second child node
        int numChildren = node.getNumChildren(false);
        if (numChildren == 2) {
          // "getAttribute:of:" is the default
          if ("lnumber".equals(node.getFirstChild().getNextSibling().getXhcName())) {
            s = "computeFunction:of:";
          }
        }
      }
      sb.append("\n").append(indent).append("[\"").append(s).append("\"");
      if (node.hasChildNodes()) {
        sb.append(", ");
      }
      break;
    }
    case "lnumber":
      sb.append(xhContent);
      break;
    case "lstring":
      if ("define".equals(node.getParentNode().getRoleName())) {
        // xhContent is the name of a Scratch function (New Block) definition  ex: "polygon"
        // it has an optional lstring nextSibling containing function parameters
        if (node == node.getParentNode().getFirstChild()) {
          // don't separately process the optional function parameters
          if (node.hasNextSibling()) {
            // convert define [polygon] [nsides,sx,by]  to  ["procDef", "polygon %n %s %b", ["sides", "x", "y"], [1, "", false], false]
            String paramsStr = node.getNextSibling().getVal_String();
            if (paramsStr != null) {
              String[] paramsArr = paramsStr.split(",");
              StringBuilder sbParams = new StringBuilder();
              sbParams.append("\"").append(xhContent);
              String functionTypeStr = "";
              int i;
              for (i = 0; i < paramsArr.length; i++) {
                functionTypeStr += " %" + paramsArr[i].charAt(0);
                //sbParams.append(" ").append("%").append(paramsArr[i].charAt(0));
              }
              functionTypeMap.put(xhContent, functionTypeStr);
              sbParams.append(functionTypeStr);
              sbParams.append("\", [");
              for (i = 0; i < paramsArr.length; i++) {
                if (i > 0) {
                  sbParams.append(", ");
                }
                String paramName = paramsArr[i].substring(1);
                paramsNameSet.add(paramName);
                sbParams.append("\"").append(paramName).append("\"");
              }
              //sbParams.append("], [1, \"\", false], false");
              sbParams.append("], [");
              for (i = 0; i < paramsArr.length; i++) {
                if (i > 0) {
                  sbParams.append(", ");
                }
                char paramType = paramsArr[i].charAt(0);
                switch (paramType) {
                case 'n': sbParams.append("1"); break;
                case 's': sbParams.append("\"\""); break;
                case 'b': sbParams.append("false"); break;
                default: break;
                }
              }
              sbParams.append("], false");
              sb.append(sbParams.toString());
            }
            node = node.getNextSibling(); // cause the optional params node to be ignored
          }
          else {
            sb.append("\"").append(xhContent).append("\"").append(", [], [], false");
          }
        } // end  if (node == node.getParentNode().getFirstChild()) {

      }
      else if ("variable".equals(node.getParentNode().getXhcName())) {
        // this is a Scratch user-defined variable's value
        // TODO the value could be a string, number, or boolean; is it OK to write all of these as strings?
        sb.append("\n").append(indent).append("\"value\": \"").append(xhContent).append("\",");
        sb.append("\n").append(indent).append("\"isPersistent\": false");
      }
      else {
        sb.append("\"").append(xhContent).append("\"");
      }
      break;
    case "lcolor":
      sb.append("\"").append(xhContent).append("\"");
      break;
    case "udvariable":
    {
      if ("variable".equals(node.getParentNode().getXhcName())) {
        // this is a Scratch user-defined variable's name
        sb.append(indent).append("\"name\": \"").append(xhContent).append("\"");
      }
      else {
        if (isParamVariable(xhContent)) {
          // TODO use "r" for number and string; use "b" for boolean
          sb.append("[\"getParam\", \"").append(xhContent).append("\", \"r\"]");
        }
        else {
          if (("setto".equals(node.getParentNode().getRoleName())) && (node == node.getParentNode().getFirstChild())) {
            // this is a special case; this is the name of the variable whose value is being set
            sb.append("\"").append(xhContent).append("\"");
          }
          else {
            sb.append("[\"readVariable\", \"").append(xhContent).append("\"]");
          }
        }
      }
      break;
    }
    case "bivariable":
    {
      String[] arr = blockNameMap.get(xhContent);
      if (arr != null && arr.length > 1) {
        s = arr[BLOCKNAMEMAP_VALUEIX_SCRATCH];
      }
      if (s == null) {
        s = xhContent;
      }
      sb.append("[\"").append(s).append("\"]");
      
      break;
    }
    default:
      break;
    } // end switch
    
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
    case "scratchdetails":
      // ignore
      break;
    case "variables":
      sb.append("\n").append(indent).append("],\n").append(indent);
      break;
    case "variable":
      sb.append("\n").append(indent).append("}");
      if (node.hasNextSibling()) {
        sb.append(",");
      }
      else {
        //sb.append("\n").append(indent.substring(0, indent.length()-2));
      }
      break;
    case "sscript":
      paramsNameSet.clear(); // the current script might be a "define"
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
  
  /**
   * Walk the Xholon subtree for the Scratch/Snap code, and generate an JSON String in Scratch format.
   * @param root The root of the code subtree, typically the "scripts" node.
   */
  protected String xholonSubtree2EnglishText(IXholon root) {
    StringBuilder sb = new StringBuilder();
    String indent = "";
    sb.append("-----> ").append(this.getName("R^^^^^")); //.append("\n");
    xholonSubtree2EnglishTextRecurse(root, indent, sb);
    //sb.append("]"); // matches initial "scripts": [
    return sb.toString();
  }
  
  /**
   * Recursively generate Scratch JSON format.
   */
  protected void xholonSubtree2EnglishTextRecurse(IXholon node, String indent, StringBuilder sb) {
    if (node == null) {return;}
    String xhNodeName = node.getXhcName();
    String xhRoleName = node.getRoleName();
    String xhContent = node.getVal_String();
    String s = null;
    String[] engArr = null;
    int engArrIx = 0;
    
    switch(xhNodeName) {
    case "scratchdetails":
      // ignore
      break;
    case "variables":
      //sb.append(indent).append("\"variables\": [");
      break;
    case "variable":
      //sb.append("\n").append(indent).append("{\n");
      break;
    case "scripts":
      //sb.append("\"scripts\": [");
      break;
    case "sscript":
      //sb.append("\n").append(indent).append("[");
      if ("scripts".equals(node.getParentNode().getXhcName())) {
        //sb.append("25, 25, [");
      }
      break;
    case "block":
    case "ablock":
    case "eblock":
    {
      if ("ifthen".equals(xhRoleName)) {
        // distinguish "ifthen" from "ifthenelse" by how many children the node has (2 vs 3)
        int numChildren = node.getNumChildren(false);
        if (numChildren == 3) {
          xhRoleName = "ifthenelse";
        }
      }
      String[] arr = blockNameMap.get(xhRoleName);
      if (arr != null && arr.length > 1) {
        s = arr[BLOCKNAMEMAP_VALUEIX_ENGLISH];
      }
      if (s == null) {
        // assume this is a defined name (ex: DrawSquare)
        // TODO I'm prepending call as a temporary fix
        String functionTypeStr = functionTypeMap.get(xhRoleName);
        if (functionTypeStr == null) {
          functionTypeStr = "";
        }
        s = "call\", \"" + xhRoleName + functionTypeStr;
      }
      else if ("of".equals(xhRoleName)) {
        // distinguish "getAttribute:of:" from "computeFunction:of:" by type of second child node
        int numChildren = node.getNumChildren(false);
        if (numChildren == 2) {
          // "getAttribute:of:" is the default
          if ("lnumber".equals(node.getFirstChild().getNextSibling().getXhcName())) {
            s = "Compute function _ of _";
          }
        }
      }
      engArr = s.split(ENGLISHTEXT_SEP_CHAR);
      engArrIx = 0;
      if ("block".equals(xhNodeName)) {
        sb.append("\n").append(indent);
      }
      sb.append(engArr[engArrIx++]);
      if (node.hasChildNodes()) {
        //sb.append(", ");
      }
      break;
    }
    case "lnumber":
      sb.append(xhContent);
      break;
    case "lstring":
      if ("define".equals(node.getParentNode().getRoleName())) {
        // xhContent is the name of a Scratch function (New Block) definition  ex: "polygon"
        // it has an optional lstring nextSibling containing function parameters
        if (node == node.getParentNode().getFirstChild()) {
          // don't separately process the optional function parameters
          if (node.hasNextSibling()) {
            // convert define [polygon] [nsides,sx,by]  to  ["procDef", "polygon %n %s %b", ["sides", "x", "y"], [1, "", false], false]
            String paramsStr = node.getNextSibling().getVal_String();
            if (paramsStr != null) {
              String[] paramsArr = paramsStr.split(",");
              StringBuilder sbParams = new StringBuilder();
              sbParams.append("\"").append(xhContent);
              String functionTypeStr = "";
              int i;
              for (i = 0; i < paramsArr.length; i++) {
                functionTypeStr += " %" + paramsArr[i].charAt(0);
                //sbParams.append(" ").append("%").append(paramsArr[i].charAt(0));
              }
              functionTypeMap.put(xhContent, functionTypeStr);
              sbParams.append(functionTypeStr);
              sbParams.append("\", [");
              for (i = 0; i < paramsArr.length; i++) {
                if (i > 0) {
                  sbParams.append(", ");
                }
                String paramName = paramsArr[i].substring(1);
                paramsNameSet.add(paramName);
                sbParams.append("\"").append(paramName).append("\"");
              }
              //sbParams.append("], [1, \"\", false], false");
              sbParams.append("], [");
              for (i = 0; i < paramsArr.length; i++) {
                if (i > 0) {
                  sbParams.append(", ");
                }
                char paramType = paramsArr[i].charAt(0);
                switch (paramType) {
                case 'n': sbParams.append("1"); break;
                case 's': sbParams.append("\"\""); break;
                case 'b': sbParams.append("false"); break;
                default: break;
                }
              }
              sbParams.append("], false");
              sb.append(sbParams.toString());
            }
            node = node.getNextSibling(); // cause the optional params node to be ignored
          }
          else {
            sb.append("").append(xhContent).append("").append(", [], [], false");
          }
        } // end  if (node == node.getParentNode().getFirstChild()) {

      }
      else if ("variable".equals(node.getParentNode().getXhcName())) {
        // this is a Scratch user-defined variable's value
        // TODO the value could be a string, number, or boolean; is it OK to write all of these as strings?
        //sb.append("\n").append(indent).append("\"value\": \"").append(xhContent).append("\",");
        //sb.append("\n").append(indent).append("\"isPersistent\": false");
      }
      else {
        sb.append("\"").append(xhContent).append("\"");
      }
      break;
    case "lcolor":
      sb.append(xhContent);
      break;
    case "udvariable":
    {
      if ("variable".equals(node.getParentNode().getXhcName())) {
        // this is a Scratch user-defined variable's name
        //sb.append(indent).append("\"name\": \"").append(xhContent).append("\"");
      }
      else {
        if (isParamVariable(xhContent)) {
          // TODO use "r" for number and string; use "b" for boolean
          //sb.append("[\"getParam\", \"").append(xhContent).append("\", \"r\"]");
        }
        else {
          if (("setto".equals(node.getParentNode().getRoleName())) && (node == node.getParentNode().getFirstChild())) {
            // this is a special case; this is the name of the variable whose value is being set
            sb.append(xhContent);
          }
          else {
            sb.append(xhContent);
          }
        }
      }
      break;
    }
    case "bivariable":
    {
      String[] arr = blockNameMap.get(xhContent);
      if (arr != null && arr.length > 1) {
        s = arr[BLOCKNAMEMAP_VALUEIX_ENGLISH];
      }
      if (s == null) {
        s = xhContent;
      }
      sb.append(s);
      
      break;
    }
    default:
      break;
    } // end switch
    
    IXholon childNode = node.getFirstChild();
    // determine the child indent
    String childIndent = "";
    switch(xhNodeName) {
    case "scriptdetails":
    case "scripts":
      break;
    case "sscript":
      if (!"scripts".equals(node.getParentNode().getXhcName())) {
        childIndent = indent + "  ";
      }
      break;
    default:
      childIndent = indent + "  ";
      break;
    }
    while (childNode != null) {
      xholonSubtree2EnglishTextRecurse(childNode, childIndent, sb);
      if ((engArr != null) && (engArrIx < engArr.length)) {
        sb.append(engArr[engArrIx++]);
      }
      childNode = childNode.getNextSibling();
    }
    
    switch(xhNodeName) {
    case "scratchdetails":
      // ignore
      break;
    case "variables":
      //sb.append("\n").append(indent).append("],\n").append(indent);
      break;
    case "variable":
      //sb.append("\n").append(indent).append("}");
      if (node.hasNextSibling()) {
        //sb.append(",");
      }
      else {
        
      }
      break;
    case "sscript":
      paramsNameSet.clear(); // the current script might be a "define"
      sb.append("");
      if ("scripts".equals(node.getParentNode().getXhcName())) {
        sb.append("");
      }
      if (node.hasNextSibling()) {
        sb.append("");
      }
      else {
        sb.append("\n").append(indent.substring(0, indent.length()-2));
      }
      break;
    case "block":
    case "ablock":
    case "eblock":
      //sb.append("]");
      if (node.hasNextSibling()) {
        sb.append("");
      }
      else if ("block".equals(xhNodeName)) {
        sb.append("\n").append(indent.substring(0, indent.length()-2));
      }
      break;
    default:
      if (node.hasNextSibling()) {
        sb.append("");
      }
      break;
    }
    
  }
}

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

import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
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
   * The number of spaces used to represent one level of indentation in the input (the spriteScript text).
   * This value will be calculated when tokenizing/parsing the first indented block, if any.
   */
  protected int indentIn1 = -1;
  
  /**
   * The current output indent level.
   * This is used while generating Xholon or Scratch or Snap content.
   */
  //protected int indentOutLevel = 0;
  //protected String indentOutStr = "                                        ";
  protected String indentOut1 = "  ";
  
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
  "turnrightdegrees", "turnRight", "turn",
  "turnleftdegrees", "DUMMY", "DUMMY",
  "pointindirection", "DUMMY", "DUMMY",
  "pointtowards", "DUMMY", "DUMMY",
  "gotoxy", "DUMMY", "DUMMY",
  "goto", "DUMMY", "DUMMY",
  "glidesecstoxy", "DUMMY", "DUMMY",
  "changexby", "DUMMY", "DUMMY",
  "setxto", "DUMMY", "DUMMY",
  "changeyby", "DUMMY", "DUMMY",
  "setyto", "DUMMY", "DUMMY",
  "ifonedge,bounce", "DUMMY", "DUMMY",
  "setrotationstyle", "DUMMY", "DUMMY",
  
  // Looks
  "sayforsecs", "say:duration:elapsed:from:", "doSayFor",
  "say", "say:", "bubble",
  "thinkforsecs", "think:duration:elapsed:from:", "doThinkFor",
  "think", "think:", "doThink",
  "show", "DUMMY", "DUMMY",
  "hide", "DUMMY", "DUMMY",
  "switchcostumeto", "DUMMY", "DUMMY",
  "nextcostume", "DUMMY", "DUMMY",
  "switchbackdropto", "DUMMY", "DUMMY",
  "changeeffectby", "DUMMY", "DUMMY",
  "seteffectto", "DUMMY", "DUMMY",
  "cleargraphiceffects", "DUMMY", "DUMMY",
  "changesizeby", "DUMMY", "DUMMY",
  "setsizeto%", "DUMMY", "DUMMY",
  "gotofront", "DUMMY", "DUMMY",
  "gobacklayers", "DUMMY", "DUMMY",
  
  // Sound
  "playsound", "DUMMY", "DUMMY",
  "playsounduntildone", "DUMMY", "DUMMY",
  "stopallsounds", "DUMMY", "DUMMY",
  "playdrumforbeats", "DUMMY", "DUMMY",
  "restforbeats", "DUMMY", "DUMMY",
  "playnoteforbeats", "DUMMY", "DUMMY",
  "setinstrumentto", "DUMMY", "DUMMY",
  "changevolumeby", "DUMMY", "DUMMY",
  "setvolumeto%", "DUMMY", "DUMMY",
  "changetempoby", "DUMMY", "DUMMY",
  "settempotobpm", "DUMMY", "DUMMY",
  
  // Pen
  "clear", "DUMMY", "DUMMY",
  "stamp", "DUMMY", "DUMMY",
  "pendown", "DUMMY", "DUMMY",
  "penup", "DUMMY", "DUMMY",
  "changepencolorby", "DUMMY", "DUMMY",
  "setpencolorto", "DUMMY", "DUMMY",
  "changepenshadeby", "DUMMY", "DUMMY",
  "setpenshadeto", "DUMMY", "DUMMY",
  "changepensizeby", "DUMMY", "DUMMY",
  "setpensizeto", "DUMMY", "DUMMY",
  
  // Data
  "DATA", "DUMMY", "DUMMY",
  
  // Events
  "whengreenflagclicked", "whenGreenFlag", "receiveGo",
  "whenkeypressed", "DUMMY", "DUMMY",
  "whenthisspriteclicked", "DUMMY", "DUMMY",
  "whenbackdropswitchesto", "DUMMY", "DUMMY",
  "when", "DUMMY", "DUMMY",
  "whenIreceive", "DUMMY", "DUMMY",
  "broadcast", "DUMMY", "DUMMY",
  "broadcastandwait", "DUMMY", "DUMMY",
  
  // Control
  "waitsecs", "DUMMY", "DUMMY",
  "repeat", "doRepeat", "doRepeat",
  "forever", "DUMMY", "DUMMY",
  "ifthen", "DUMMY", "DUMMY",
  "ifthenelse", "doIfElse", "doIfElse", // TODO how do I get the "else" part of the name ?
  "waituntil", "DUMMY", "DUMMY",
  "repeatuntil", "DUMMY", "DUMMY",
  "stop", "DUMMY", "DUMMY",
  "whenIstartasaclone", "DUMMY", "DUMMY",
  "createcloneof", "DUMMY", "DUMMY",
  "deletethisclone", "DUMMY", "DUMMY",
  
  // Sensing
  "touching?", "DUMMY", "DUMMY", // angle
  "touchingcolor?", "touchingColor:", "reportTouchingColor", // angle
  "coloristouching?", "DUMMY", "DUMMY", // angle
  "distanceto", "DUMMY", "DUMMY", // ellipse
  "askandwait", "DUMMY", "DUMMY", // block
  "keypressed?", "DUMMY", "DUMMY", // angle
  "mousedown?", "DUMMY", "DUMMY", // angle
  "mousex", "DUMMY", "DUMMY", // ellipse
  "mousey", "DUMMY", "DUMMY", // ellipse
  "turnvideo", "DUMMY", "DUMMY",
  "setvideotransparencyto%", "DUMMY", "DUMMY",
  "resettimer", "DUMMY", "DUMMY",
  "of", "DUMMY", "DUMMY",
  "dayssince2000", "DUMMY", "DUMMY",
  "username", "DUMMY", "DUMMY",
  
  // Operators
  "plus", "+", "reportSum",
  "minus", "-", "reportDifference",
  "times", "*", "DUMMY",
  "dividedby", "/", "DUMMY",
  "pickrandomto", "DUMMY", "DUMMY",
  "lt", "DUMMY", "DUMMY",
  "eq", "DUMMY", "DUMMY",
  "gt", "DUMMY", "DUMMY",
  "and", "DUMMY", "DUMMY",
  "or", "DUMMY", "DUMMY",
  "not", "DUMMY", "DUMMY",
  "join", "DUMMY", "DUMMY",
  "letterof", "DUMMY", "DUMMY",
  "lengthof", "DUMMY", "DUMMY",
  "mod", "MOD", "DUMMY",
  "round", "DUMMY", "DUMMY",
  //"of"  TODO
  
  // More Blocks
  "define", "DUMMY", "DUMMY"
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
    if ((this.getFirstChild() != null) && ("Attribute_String".equals(this.getFirstChild().getXhcName()))) {
      String str = this.getFirstChild().getVal_String();
      if (!str.endsWith("\n")) {
        // add trailing newline to make parsing easier
        str += "\n";
      }
      str += "END"; // temporarily required to force last line to be parsed
      this.setVal_String(str);
      this.getFirstChild().removeChild();
    }
    initBlockNameMap();
    
    // TEST 1
    //String jsonStr = this.scriptToJsonSyntax(this.getVal_String());
    //this.println(jsonStr);
    
    // TEST 2
    IXholon scriptRoot = this.xholonize();
    
    super.postConfigure();
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
   * Write the Sprite's entire text syntax as a Xholon tree.
   * This is a temporary structure, and may or may not be inserted as a child of a Sprite node.
   * This can subsequently be given to a parser to generate Scratch JSON syntax, or Snap XML syntax.
   * This method should be used instead of scriptToJsonSyntax() and tokenize() .
   */
  protected IXholon xholonize() {
    //indentOutLevel = 1;
    spriteScriptIx = 0; // start at the beginning of the Sprite's text script
    
    StringBuilder sb = new StringBuilder();
    sb.append("<scripts>\n").append(indentOut1).append("<sscript>\n");
    
    sb.append(xholonizeRecurse(new StringBuilder(), indentOut1 + indentOut1));
    
    sb.append(indentOut1).append("</sscript>\n").append("</scripts>\n");
    String xmlStr = sb.toString();
    this.println(xmlStr);
    
    XholonHelperService xhs = (XholonHelperService)app.getService(IXholonService.XHSRV_XHOLON_HELPER);
    xhs.pasteLastChild(this, xmlStr);
    
    return this.getLastChild();
  }
  
  /**
   * 
   */
  protected String xholonizeRecurse(StringBuilder sb, String indentOut) {
    String blockNameOrValue = "";
    StringBuilder sbBlockContents = new StringBuilder();
    int state = 0; // initial spaces have to do with indenting/nesting
    int indentIn = 0;
    char c = '\0';
    while (spriteScriptIx < spriteScript.length()) {
      c = spriteScript.charAt(spriteScriptIx);
      spriteScriptIx++;
      switch (c) {
      case '\n':
        if (spriteScript.charAt(spriteScriptIx-2) == '\n') {
          // a blank line (2 newline characters in a row) means start a new script
          sb
          .append(indentOut1)
          .append("</sscript>\n")
          .append("\n")
          .append(indentOut1)
          .append("<sscript>\n");
        }
        else if (blockNameOrValue.startsWith(COMMENT_CHAR)) {
          // this is not part of Scratch or Snap
          sb
          .append(indentOut)
          .append("<!-- ")
          .append(blockNameOrValue.substring(1))
          .append(" -->\n");
          retainSpaces = false;
        }
        else {
          sb
          .append(indentOut)
          .append("<block roleName=\"")
          .append(blockNameOrValue)
          .append("\">\n")
          .append(sbBlockContents.toString())
          .append(indentOut)
          .append("</block>\n");
        }
        // start a new block
        blockNameOrValue = "";
        sbBlockContents = new StringBuilder();
        state = 0;
        indentIn = 0;
        break;
      case ' ':
        if (state == 0) {
          indentIn++;
        }
        else {
          
        }
        if (retainSpaces) {
          blockNameOrValue += c;
        }
        break;
      case '<':
        char nextC = spriteScript.charAt(spriteScriptIx);
        if (nextC == ' ') {
          // this is the lt operator
          blockNameOrValue += c;
        }
        else {
          // this is the start of an angle block
          sbBlockContents
          .append(indentOut).append(indentOut1)
          .append("<ablock roleName=\"")
          .append(xholonizeRecurse(new StringBuilder(), indentOut + indentOut1))
          .append(indentOut).append(indentOut1)
          .append("</ablock>\n");
        }
        break;
      case '(':
        if (spriteScript.charAt(spriteScriptIx) == '(') {
          sbBlockContents
          .append(indentOut).append(indentOut1)
          .append("<eblock roleName=\"")
          .append(xholonizeRecurse(new StringBuilder(), indentOut + indentOut1))
          //.append("\">\n")
          .append(indentOut).append(indentOut1)
          .append("</eblock>\n");
        }
        else {
          sbBlockContents
          .append(indentOut).append(indentOut1)
          .append("<lnumber>")
          .append(xholonizeRecurse(new StringBuilder(), indentOut + indentOut1))
          .append("</lnumber>\n");
        }
        break;
      case '[':
        retainSpaces = true;
        //consoleLog("lstring or lcolor");
        //consoleLog(blockNameOrValue);
        String bname = "lstring";
        switch (blockNameOrValue) {
        case "touchingcolor":
        case "color":
        case "coloristouching":
          bname = "lcolor";
          break;
        default: break;
        }
        sbBlockContents
        .append(indentOut).append(indentOut1)
        .append("<").append(bname).append(">") // TODO or lcolor
        .append(xholonizeRecurse(new StringBuilder(), indentOut + indentOut1))
        .append("</").append(bname).append(">\n"); // TODO or lcolor
        retainSpaces = false;
        break;
      case '>':
        char prevC = spriteScript.charAt(spriteScriptIx-2);
        if (prevC == ' ') {
          // this is the gt operator
          blockNameOrValue += c;
        }
        else {
          // this is the end of an angle block
          String strBN = blockNameOrValue; // an actual blockNameOrValue "touchingcolor?"
          String strBC = sbBlockContents.toString();
          //consoleLog(strBN);
          //consoleLog(strBC);
          return strBN + "\">\n" + strBC;
        }
        break;
      case ')':
        String strBN = fixXholonizedBlockName(blockNameOrValue); // a number "123", or an actual blockNameOrValue "lt"
        String strBC = sbBlockContents.toString();
        //consoleLog(strBN);
        //consoleLog(strBC);
        if (strBC.length() == 0) {
          // this is a number
          return fixXholonizedBlockName(strBN);
        }
        else {
          // this is the end of <eblock roleName="lt">\n ...
          return fixXholonizedBlockName(strBN) + "\">\n" + strBC;
        }
        //return fixXholonizedBlockName(blockNameOrValue) + sbBlockContents.toString();
      case ']':
        return blockNameOrValue + sbBlockContents.toString();
      case ';':
        retainSpaces = true;
        state = captureIndentIn1(state, indentIn);
        blockNameOrValue += c;
        break;
      default:
        /*if (state == 0) {
          // handle indentation
          if ((indentIn1 == -1) && (indentIn > 0)) {
            // initialize the value of indentIn1
            indentIn1 = indentIn;
          }
          if (indentIn == 0) {
            //result += indentIn + delim;
            // TODO
          }
          else {
            //result += "" + (indentIn/indentIn1) + delim;
            // TODO
          }
          state = 1;
        }*/
        state = captureIndentIn1(state, indentIn);
        blockNameOrValue += c;
        break;
      }
    }
    return sb.toString();
  } // end xholonRecurse()
  
  protected int captureIndentIn1(int state, int indentIn) {
    if (state == 0) {
      // handle indentation
      if ((indentIn1 == -1) && (indentIn > 0)) {
        // initialize the value of indentIn1
        indentIn1 = indentIn;
      }
      if (indentIn == 0) {
        //result += indentIn + delim;
        // TODO
      }
      else {
        //result += "" + (indentIn/indentIn1) + delim;
        // TODO
      }
      state = 1;
    }
    return state;
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
  
}

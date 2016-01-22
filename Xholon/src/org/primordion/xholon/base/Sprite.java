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
   * Example:
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
  
  protected static final int BLOCKNAMEMAP_VALUEIX_SCRATCH = 0;
  protected static final int BLOCKNAMEMAP_VALUEIX_SNAP    = 1;
  
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
  // Looks
  "sayforsecs", "say:duration:elapsed:from:", "doSayFor",
  "say", "say:", "bubble",
  "thinkforsecs", "think:duration:elapsed:from:", "doThinkFor",
  "think", "think:", "doThink",
  // Sound
  "playsound", "DUMMY", "DUMMY",
  // Pen
  "clear", "DUMMY", "DUMMY",
  // Data
  "DATA", "DUMMY", "DUMMY",
  // Events
  "whengreenflagclicked", "whenGreenFlag", "receiveGo",
  // Control
  "waitsecs", "DUMMY", "DUMMY",
  "repeat", "doRepeat", "doRepeat",
  "CONTROL", "DUMMY", "DUMMY",
  "ifthen", "DUMMY", "DUMMY",
  "ifthenelse", "doIfElse", "doIfElse", // TODO how do I get the "else" part of the name ?
  // Sensing
  "touching?", "DUMMY", "DUMMY",
  "touchingcolor?", "touchingColor:", "reportTouchingColor",
  // Operators
  "+", "+", "reportSum",
  "-", "-", "reportDifference",
  "*", "*", "DUMMY",
  "/", "/", "DUMMY",
  "mod", "MOD", "DUMMY"
  // More Blocks
  
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
    this.xholonize();
    
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
    sb.append("<scripts>\n").append(indentOut1).append("<script>\n");
    
    sb.append(xholonizeRecurse(new StringBuilder(), indentOut1 + indentOut1));
    
    sb.append(indentOut1).append("</script>\n").append("</scripts>\n");
    this.println(sb.toString());
    return null;
  }
  
  /**
   * 
   */
  protected String xholonizeRecurse(StringBuilder sb, String indentOut) {
    //indentOutLevel++;
    //String indentOut = indentOutStr.substring(0, indentOutLevel);
    String blockName = "";
    StringBuilder sbBlockContents = new StringBuilder();
    int state = 0; // initial spaces have to do with indenting/nesting
    int indentIn = 0;
    //for (int i = spriteScriptIx; i < spriteScript.length(); i++) {
    while (spriteScriptIx < spriteScript.length()) {
      char c = spriteScript.charAt(spriteScriptIx);
      spriteScriptIx++;
      switch (c) {
      case '\n':
        sb
        .append(indentOut)
        .append("<block s=\"")
        .append(blockName)
        .append("\">\n")
        .append(sbBlockContents.toString())
        .append(indentOut)
        .append("</block>\n");
        // start a new block
        blockName = "";
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
        break;
      case '<':
        sbBlockContents
        .append(indentOut).append(indentOut1)
        .append("<ablock>")
        .append(xholonizeRecurse(new StringBuilder(), indentOut + indentOut1))
        .append("</ablock>\n");
        break;
      case '(':
        sbBlockContents
        .append(indentOut).append(indentOut1)
        .append("<eblock>")
        .append(xholonizeRecurse(new StringBuilder(), indentOut + indentOut1))
        .append("</eblock>\n");
        break;
      case '[':
        sbBlockContents
        .append(indentOut).append(indentOut1)
        .append("<rblock>")
        .append(xholonizeRecurse(new StringBuilder(), indentOut + indentOut1))
        .append("</rblock>\n");
        break;
      case '>':
        return blockName + sbBlockContents.toString();
      case ')':
        return blockName + sbBlockContents.toString();
      case ']':
        return blockName + sbBlockContents.toString();
      default:
        if (state == 0) {
          // handle indentation
          if ((indentIn1 == -1) && (indentIn > 0)) {
            // initialize the value of indentIn1
            indentIn1 = indentIn;
          }
          if (indentIn == 0) {
            //result += indentIn + delim;
          }
          else {
            //result += "" + (indentIn/indentIn1) + delim;
          }
          state = 1;
        }
        blockName += c;
        break;
      }
    }
    return sb.toString();
  } // end xholonRecurse()
  
  /**
   * Convert the Sprite script from text syntax to Scratch JSON syntax.
   * @param textSyntax a complete script in text syntax
   * @return a complete script in Scratch JSON syntax
   */
  protected String scriptToJsonSyntax(String textSyntax) {
    StringBuilder sb = new StringBuilder();
    //indentOutLevel = 0;
    String[] textSyntaxArr = textSyntax.split("\n");
    sb.append("[\n").append("[20, 20, [\n");
    for (int i = 0; i < textSyntaxArr.length; i++) {
      String block = textSyntaxArr[i];
      /*String comma = ",";
      if (i == textSyntaxArr.length-1) {
        // don't write trailing commas
        comma = "";
      }*/
      sb
      .append(blockToJsonSyntax(block, new StringBuilder()))
      .append((i == textSyntaxArr.length-1) ? "" : ",") // don't write trailing commas
      .append("\n");
      // TODO handle end of nesting for nested blocks: repeat, forever, if, if...else, repeat until
    }
    sb.append("]]\n").append("]");
    return sb.toString();
  }
  
  /**
   * Convert text syntax into Scratch JSON syntax.
   * @param a block defined using text syntax
   */
  protected String blockToJsonSyntax(String block, StringBuilder sbLocal) {
    String[] tokens = tokenize(block);
    String blockName = makeBlockNameJsonSyntax(tokens[0]);
    
    sbLocal
    .append("[")
    .append("\"")
    .append(blockName)
    .append("\"");
    
    // handle block parameters, which may be nested
    int index = 2;
    int nestingLevel = 0;
    while (index < tokens.length) {
      switch (tokens[index]) {
      case "<":
        index++;
        break;
      case "(":
        sbLocal.append(", ");
        StringBuilder sbEllipse = new StringBuilder();
        index = writeEllipseParam(tokens, index+1, sbEllipse);
        sbLocal.append(sbEllipse.toString());
        break;
      case "[":
        sbLocal.append(", ").append("\"");
        index = writeRectangleParam(tokens, index+1, sbLocal);
        sbLocal.append("\"");
        break;
      case ">":
        index++;
        break;
      case ")":
        index++;
        break;
      case "]":
        index++;
        break;
      default:
        index++;
        break;
      }
      
    }
    
    sbLocal
    .append("]");
    
    return sbLocal.toString();
  }
  
  protected int writeAngleParam(String[] tokens, int index, StringBuilder sbLocal) {
    String token = tokens[index];
    sbLocal.append(token);
    return index;
  }
  
  protected int writeEllipseParam(String[] tokens, int index, StringBuilder sbLocal) {
    String token = tokens[index];
    if ("(".equals(token)) {
      // this is a nested param
      sbLocal.append("[");
      StringBuilder sbEllipse = new StringBuilder();
      index = writeEllipseParam(tokens, index+1, sbEllipse);
      if (index >= tokens.length) {
        return index;
      }
      token = tokens[index];
      if (")".equals(token)) {
        index++;
        token = tokens[index];
        switch (token) {
        case "+":
        case "-":
        case "*":
        case "/":
        case "mod":
          sbLocal.append("\"").append(makeBlockNameJsonSyntax(token)).append("\"").append(", ");
          break;
        default: break;
        }
      }
      else {
      
      }
      sbLocal.append(sbEllipse.toString());
      sbLocal.append("]");
    }
    else {
      sbLocal.append(token);
    }
    return index + 1;
  }
  
  protected int writeRectangleParam(String[] tokens, int index, StringBuilder sbLocal) {
    boolean foundStringToken = false;
    while (index < tokens.length) {
      switch (tokens[index]) {
      case "<":
      case "(":
      case "[":
      case ">":
      case ")":
      case "]":
        return index;
      default:
        if (foundStringToken) {
          // add a space between the previously found String token and this String token
          sbLocal.append(" ");
        }
        else {
          foundStringToken = true;
        }
        sbLocal.append(tokens[index]);
        break;
      }
      index++;
    }
    return index;
  }
  
  /**
   * 
   */
  protected String makeBlockNameJsonSyntax(String naiveBlockName) {
    String[] value = blockNameMap.get(naiveBlockName);
    if (value == null) {
      return "UNKNOWN" + naiveBlockName;
    }
    else {
      return value[BLOCKNAMEMAP_VALUEIX_SCRATCH];
    }
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

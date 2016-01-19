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
   * The number of spaces used to represent one level of indentation.
   * This value will be calculated when tokenizing the first indented block, if any.
   */
  protected int indent1 = -1;
  
  /**
   * The current indent level.
   * This is used while generating Scratch or Snap content.
   */
  protected int currentIndentLevel = 0;
  
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
  "playsound", "", "",
  // Pen
  "clear", "", "",
  // Data
  "DATA", "", "",
  // Events
  "whengreenflagclicked", "whenGreenFlag", "receiveGo",
  // Control
  "waitsecs", "", "",
  "repeat", "doRepeat", "doRepeat",
  "CONTROL", "", "",
  "ifthen", "", "",
  "ifthenelse", "doIfElse", "doIfElse", // TODO how do I get the "else" part of the name ?
  // Sensing
  "touching?", "", "",
  "touchingcolor?", "touchingColor:", "reportTouchingColor"
  // Operators
  
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
      this.setVal_String(this.getFirstChild().getVal_String());
      this.getFirstChild().removeChild();
    }
    initBlockNameMap();
    
    // TEST
    String jsonStr = this.scriptToJsonSyntax(this.getVal_String());
    this.println(jsonStr);
    
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
   * Convert the Sprite script from text syntax to Scratch JSON syntax.
   */
  protected String scriptToJsonSyntax(String textSyntax) {
    StringBuilder sb = new StringBuilder();
    currentIndentLevel = 0;
    String[] textSyntaxArr = textSyntax.split("\n");
    sb.append("[\n").append("[20, 20, [\n");
    for (int i = 0; i < textSyntaxArr.length; i++) {
      String block = textSyntaxArr[i];
      sb.append(blockToJsonSyntax(block, new StringBuilder())).append(",").append("\n");
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
        
        break;
      case "(":
        sbLocal.append(", ").append(tokens[index+1]);
        index++;
        break;
      case "[":
        sbLocal.append(", ").append("\"");
        index = writeBracketedString(tokens, index+1, sbLocal);
        sbLocal.append("\"");
        break;
      case ">":
        
        break;
      case ")":
        
        break;
      case "]":
        
        break;
      default: break;
      }
      index++;
    }
    
    sbLocal
    .append("]");
    
    return sbLocal.toString();
  }
  
  protected int writeBracketedString(String[] tokens, int index, StringBuilder sbLocal) {
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
  
  protected String makeBlockNameJsonSyntax(String naiveBlockName) {
    String[] value = blockNameMap.get(naiveBlockName);
    if (value == null) {
      return "UNKNOWN" + naiveBlockName;
    }
    else {
      return value[BLOCKNAMEMAP_VALUEIX_SCRATCH];
    }
  }
  
  // NO
  /*protected String blockToJsonSyntax(String block) {
    String[] tokens = tokenize(block);
    switch (tokens[0]) {
    
    // these should be in one alphabetical list
    
    // "change x|y by (10)" Motion
    case "change":
      break;
    
    // "glide (1) secs to x: (-65) y: (-19)" Motion
    case "glide":
      break;
    
    // "go to x: (-65) y: (-19)"  "go to [THING]" Motion
    case "go":
      break;
    
    // "if on edge, bounce" Motion
    case "if":
      break;
    
    case "move": _move(tokens); break;
    
    // "point in direction (90)"  "point towards [THING]" Motion
    case "point":
      break;
    
    // "set x|y to (0)"  "set rotation style [left-right|OTHERSTYLE]" Motion
    case "set":
      break;
    
    // "turn right|left (15) degrees" Motion    clockwise instead of right? counterclockwise instead of left?
    case "turn":
      break;
    
    default:
      break;
    }
    
    return "";
  }*/
  
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
    int indent = 0;
    for (int i = 0; i < block.length(); i++) {
      char c = block.charAt(i);
      switch (c) {
      case ' ':
        if (state == 0) {
          indent++;
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
          if ((indent1 == -1) && (indent > 0)) {
            // initialize the value of indent1
            indent1 = indent;
          }
          if (indent == 0) {
            result += indent + delim;
          }
          else {
            result += "" + (indent/indent1) + delim;
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
   * NO ???
   * move
   * Motion: "move (10) steps"  "move 10 steps"  "move (amount) steps"  "move amount steps"  "move (amount + 2) steps"
   */
  protected void _move(String[] tokens) {
    String bname = "forward:";
    String steps = paramEllipse(tokens, 1);
  }
  
  // NO ???
  protected void paramAngle(String[] tokens, int startIx) {
  
  }
  
  /**
   * NO ???
   * Return the contents of an ellipse parameter, as a JSON string.
   * Examples: (10)  10  (amount)  amount  (amount + 3)
   * @param tokens The tokens in the current text block.
   * @param startIx Index of the token where the ellipse parameter starts.
   */
  protected String paramEllipse(String[] tokens, int startIx) {
    if ("(".equals(tokens[startIx])) {
      // this param contains one or more tokens enclosed in ()
      return null;
    }
    else {
      // this is a single token
      return tokens[startIx];
    }
  }
  
  // NO ???
  protected void paramRect(String[] tokens, int startIx) {
  
  }
  
}

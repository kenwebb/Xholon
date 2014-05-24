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

package org.primordion.xholon.script;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.util.Misc;

/**
 * This is a helper class that partially parses Lojban content into a Xholon XML subtree.
 * It's purpose is to automate some of the manual steps in converting Lojban text into a Xholon app.
 * You will need to manually edit the XML before it can become part of a Xholon app. 
 * jboski does the initial work of parsing the Lojban text into a parenthesized structure.
 * This class just needs to parse the parenthesis structure generated by jboski.
 * Each structurally significant opening and closing parenthesis in the input includes
 * a unique sequentially-ordered subscript.
 *   [1 ... ]1  bridi
 *   (2 ... )2  sumti
 *   «3 ... »3  selbri
 * 
 * Example usage. Paste the following as the last child of a Xholon node:
 * <pre>
&lt;Lojban>&lt;![CDATA[
[1(2[klama1 (go-er(s)) :] mi I, me)2 [is, does] «3(4sutra swift [type-of] klama go-ing)4»3 (5[klama2 (destination(s)) :] le the (6blanu blue [type-of] {7zdani home(s) be (8[zdani2 (reside-r(s)) :] la djan. [NAME])8}7)6)5 (9[klama3 (origin(s)) :] le the briju office(s))9]1
]]>&lt;/Lojban>

&lt;Lojban>&lt;![CDATA[
[1(2[lisri1 (story(ies)) :] dei this utterance)2 [is, does] «3lisri being story(ies)»3 (4[lisri2 (plot) :] le the <5nu event(s) of [6«7cpare climb-ing»7 (8[cpare2 (climbing surface(s)) :] lo any/some cmana mountain(s))8]6>5)4]1
]]>&lt;/Lojban>
 * </pre>
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="www.lojban.org">Lojban website</a>
 * @see <a href="www.lojban.org/jboski/">Lojban jboski parser</a>
 * @since 0.9.1 (Created on May 23, 2014)
 */
public class Lojban extends XholonScript {
  
  private String input = null;
  private int nextSubscript = 1;
  private StringBuilder sb;
  private String blanks = "                              ";
  private String text = "";
  
  @Override
  public void setVal(String str) {
    this.input = str;
  }
  
  @Override
  public void postConfigure() {
    IXholon node = this.getParentNode();
    node.println(input);
    sb = new StringBuilder();
    parse();
    node.println(sb.toString());
    removeChild();
  }
  
  protected void parse() {
    int ix = 0;
    while (ix < input.length()) {
      char c = input.charAt(ix);
      switch (c) {
      case '[': ix = parseBridiStart(ix); break;
      case ']': ix = parseEnd(ix, "bridi"); break;
      case '(': ix = parseSumtiStart(ix); break;
      case ')': ix = parseEnd(ix, "sumti"); break;
      case '«': ix = parseSelbriStart(ix); break;
      case '»': ix = parseEnd(ix, "selbri"); break;
      default: text += c; break;
      }
      ix++;
    }
  }
  
  protected int parseBridiStart(int ix) {
    int subscript = Misc.atoi(input, ix+1);
    if (subscript == 0) {
      text += input.charAt(ix);
    }
    else {
      processText(subscript);
      sb.append(blanks.substring(0,subscript-1)).append("<bridi>\n");
    }
    return fixIx(ix, subscript);
  }
  
  protected int parseSumtiStart(int ix) {
    int subscript = Misc.atoi(input, ix+1);
    if (subscript == 0) {
      text += input.charAt(ix);
      return fixIx(ix, subscript);
    }
    else {
      processText(subscript);
      sb.append(blanks.substring(0,subscript-1)).append("<sumti");
      ix = fixIx(ix, subscript);
      if ('[' == input.charAt(ix+1)) {
        // "(2[klama1 (go-er(s)) :] mi I, me)2"
        int sumtiIx = ix + 2;
        while (!(' ' == input.charAt(sumtiIx))) {
          sumtiIx++;
        }
        int x = Misc.atoi(input, sumtiIx-1);
        if (x != 0) {
          sb.append(" roleName=\"x").append(x).append("\"");
        }
      }
      sb.append(">\n");
      return ix;
    }
  }
  
  protected int parseSelbriStart(int ix) {
    int subscript = Misc.atoi(input, ix+1);
    if (subscript == 0) {
      text += input.charAt(ix);
    }
    else {
      processText(subscript);
      sb.append(blanks.substring(0,subscript-1)).append("<selbri>\n");
    }
    return fixIx(ix, subscript);
  }
  
  protected int parseEnd(int ix, String nodeName) {
    int subscript = Misc.atoi(input, ix+1);
    if (subscript == 0) {
      text += input.charAt(ix);
    }
    else {
      processText(subscript);
      sb.append(blanks.substring(0,subscript-1)).append("</").append(nodeName).append(">\n");
    }
    return fixIx(ix, subscript);
  }
  
  protected void processText(int subscript) {
    text = text.trim();
    if (!"".equals(text)) {
      sb.append(blanks.substring(0,subscript)).append("<!-- ").append(text).append(" -->\n");
      text = "";
    }
  }
  
  /**
   * Move the input index to the position of the last digit in the subscript.
   * @param ix current index into the input string
   * @param subscript a number >= 1
   * @return a possibly modified value of ix
   *  ix if subscript has a value of "0"
   *  ix+1 if subscript has a value of "1" to "9"
   *  ix+2 if subscript has a value of "10" to "99"
   *  ix+3 if subscript has a value of "100" to "999"
   */
  protected int fixIx(int ix, int subscript) {
    if (subscript > 99) {ix += 3;} // 100 has 3 chars
    else if (subscript > 9) {ix += 2;} // 10 has 2 chars
    else if (subscript > 0) {ix += 1;} // 1 has 1 char
    return ix;
  }
  
}


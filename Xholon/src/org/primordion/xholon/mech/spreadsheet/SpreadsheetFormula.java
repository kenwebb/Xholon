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

package org.primordion.xholon.mech.spreadsheet;

import com.google.gwt.core.client.JavaScriptObject;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;

/**
 * SpreadsheetFormula
 * Typically, an instance of this class will be the only child (optional) of a SpreadsheetCell node.
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="https://github.com/handsontable/formula-parser">handsontable JS library website</a>
 * @since 0.9.1 (Created on Oct 30, 2016)
 */
public class SpreadsheetFormula extends XholonWithPorts {
  
  /**
   * The roleName is optional.
   */
  private String roleName = null;
  
  @Override
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
  
  @Override
  public String getRoleName() {
    return roleName;
  }
  
  // value of the String between the start/end SpreadsheetFormula tags
  protected String sval = null;
  
  @Override
  public String getVal_String() {return sval;}
  
  @Override
  public void setVal(String sval) {
    this.sval = sval.trim();
  }
  
  /**
   * formula (ex: 5*SUM(A1:A3)  SUM(B1:B2)+SUM(C4:C6)  5*3 )
   */
  protected String formula = null;
  
  @Override
  public void postConfigure() {
    if ((this.sval != null) && (this.sval.length() > 0)) {
      if ((this.sval.length() > 1) && (this.sval.charAt(0) == '=')) {
        // the sval is a formula
        this.formula = this.sval.substring(1);
        this.formula2Value(this.formula);
      }
      else {
        // TODO this is an error
      }
    }
    super.postConfigure();
  }
  
  @Override
  public void act() {
    if (this.formula != null) {
      this.formula2Value(this.formula);
    }
    super.act();
  }
  
  /**
   * Calculate or recalculate the formula, if this cell has a formula.
   * @param formula An Excel-style formula.
   */
  protected void formula2Value(String formula) {
    JavaScriptObject p = getParser();
    if (p == null) {return;}
    JavaScriptObject jso = this.parseFormula(formula, p);
    //this.consoleLog(this.getParseError(jso));
    //this.consoleLog(this.getParseResult(jso));
    if (jso == null) {return;}
    if (this.getParseError(jso) == null) {
      this.getParentNode().setVal_Object(this.getParseResult(jso));
    }
    else {
      this.getParentNode().setVal_Object(this.getParseError(jso));
    }
  }
  
  /**
   * Get the Excel formula parser.
   */
  protected JavaScriptObject getParser() {
    return ((Spreadsheet)this.getParentNode().getParentNode().getParentNode()).getParser();
  }
  
  /**
   * Use third-party library to parse Excel-style formulas.
   * https://github.com/handsontable/formula-parser
   * "Javascript Library parsing Excel Formulas and more"
   * parser.parse('SUM(1, 6, 7)'); // It returns `Object {error: null, result: 14}`
   * @param formula An Excel-style formula.
   * @param parser An Excel formula parser.
   * @return A parse result or error object.
   */
  protected native JavaScriptObject parseFormula(String formula, JavaScriptObject parser) /*-{
    var obj = null;
    if (parser != null) {
      obj = parser.parse(formula);
    }
    return obj;
  }-*/;
  
  /**
   * Get a parse error string.
   * @param jso A parse result or error object.
   * @return An error string or null.
   */
  protected native String getParseError(JavaScriptObject jso) /*-{
    return jso.error;
  }-*/;
  
  /**
   * Get a parse result value.
   * @param jso A parse result or error object.
   */
  protected native Object getParseResult(JavaScriptObject jso) /*-{
    return jso.result;
  }-*/;
  
  @Override
  public void toXml(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {
    xmlWriter.writeStartElement(this.getXhcName());
    //xholon2xml.writeSpecial(this);
    xmlWriter.writeText("=" + this.formula);
    xmlWriter.writeEndElement(this.getXhcName());
  }
  
  @Override
  public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {}

  @Override
  public String toString() {
    return this.getName() + " " + this.formula;
  }
  
}

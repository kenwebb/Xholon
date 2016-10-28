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
import com.google.gwt.core.client.JsArrayMixed;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.util.IJavaTypes;
import org.primordion.xholon.util.Misc;

/**
 * SpreadsheetCell
 * Has a value and an optional formula/function.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="https://github.com/handsontable/formula-parser">handsontable JS library website</a>
 * @since 0.9.1 (Created on Oct 14, 2016)
 */
public class SpreadsheetCell extends XholonWithPorts implements IJavaTypes {
  
  /**
   * column "A" to "ZZ" + Row.roleName  (ex: A1)
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
  
  // value of the String between the start/end SpreadsheetCell tags
  protected String sval = null;
  
  @Override
  public String getVal_String() {return sval;}
  
  @Override
  public void setVal(String sval) {
    this.sval = sval.trim();
  }
  
  @Override
  public void setVal(int ival) {
    this.value = new Integer(ival);
    //this.value = ival; // this also works
    //this.println(this.value + " " + this.value.getClass().getName());
  }
  
  @Override
  public void setVal(double dval) {
    this.value = new Double(dval);
    //this.value = dval; // this also works
    //this.println(this.value + " " + this.value.getClass().getName());
  }
  
  @Override
  public void setVal(float fval) {
    this.value = new Double(fval);
    //this.value = fval; // this also works
    //this.println(this.value + " " + this.value.getClass().getName());
  }
  
  @Override
  public void setVal(boolean bval) {
    this.value = new Boolean(bval);
    //this.value = bval; // this also works
    //this.println(this.value + " " + this.value.getClass().getName());
  }
  
  /**
   * optional formula (ex: 5*SUM(A1:A3)  SUM(B1:B2)+SUM(C4:C6)  5*3 )
   */
  protected String formula = null;
  
  /**
   * The current value of this cell (ex: 43 "Hello World" "12345" true 123.456)
   */
  protected Object value = ""; //null;
  public Object getValue() {return value;}
  
  @Override
  public Object getVal_Object() {return value;}
  
  @Override
  public void postConfigure() {
    //this.println(this.getName() + ": " + this.sval);
    if ((this.sval != null) && (this.sval.length() > 0)) {
      if ((this.sval.length() > 1) && (this.sval.charAt(0) == '=')) {
        // the sval is a formula
        this.formula = this.sval.substring(1);
        this.formula2Value(this.formula);
        //this.println(this.value + " " + this.value.getClass().getName());
      }
      else {
        // the sval is a simple value
        str2Value(this.sval);
        //this.println(this.value + " " + this.value.getClass().getName());
      }
    }
    if (this.roleName == null) {
      int colNameAscii = (int)'A' + (this.getId() - this.getParentNode().getFirstChild().getId());
      this.roleName = String.valueOf((char)colNameAscii);
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
   */
  protected void formula2Value(String formula) {
    JavaScriptObject jso = this.parseFormula(formula, getParser());
    this.consoleLog(this.getParseError(jso));
    this.consoleLog(this.getParseResult(jso));
    if (this.getParseError(jso) == null) {
      this.value = this.getParseResult(jso);
    }
    else {
      this.value = this.getParseError(jso);
    }
  }
  
  protected JavaScriptObject getParser() {
    return ((Spreadsheet)this.getParentNode().getParentNode()).getParser();
  }
  
  /**
   * Use third-party library to parse Excel-style formulas.
   * https://github.com/handsontable/formula-parser
   * "Javascript Library parsing Excel Formulas and more"
   * parser.parse('SUM(1, 6, 7)'); // It returns `Object {error: null, result: 14}`
   */
  protected native JavaScriptObject parseFormula(String formula, JavaScriptObject parser) /*-{
    var obj = null;
    if (parser != null) {
      obj = parser.parse(formula);
    }
    return obj;
  }-*/;
  
  protected native String getParseError(JavaScriptObject jso) /*-{
    return jso.error;
  }-*/;
  
  /**
   * TODO what is the return type?
   */
  protected native Object getParseResult(JavaScriptObject jso) /*-{
    return jso.result;
  }-*/;
  
  /**
   * Determine a String's data type, and save it's value as a Java Object in this.value.
   * param str (ex: 123 123.45 false "a string" )
   */
  protected void str2Value(String str) {
    int dataType = Misc.getJavaDataType(str);
    switch (dataType) {
      case JAVACLASS_int:
      case JAVACLASS_long:
      case JAVACLASS_short:
        this.value = new Integer(str); break;
      case JAVACLASS_double:
      case JAVACLASS_float:
        this.value = new Double(str); break;
      case JAVACLASS_boolean: this.value = new Boolean(str); break;
      case JAVACLASS_String: this.value = str; break;
      default: break;
    }
  }
  
  @Override
  public String toString() {
    return this.getName() + " " + this.value;
  }
  
}

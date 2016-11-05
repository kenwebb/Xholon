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

package org.primordion.xholon.service.spreadsheet;

import com.google.gwt.core.client.JavaScriptObject;

import java.util.ArrayList;
import java.util.List;

import org.primordion.xholon.base.IMessage;
//import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Message;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.util.ClassHelper;

/**
 * Excel Formula Parser.
 * This class is part of the SpreadsheetService.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on November 3, 2016)
 */
public class FormulaParser extends Xholon implements ISpreadsheetService {
  
  /**
   * An instance of the formula parser.
   */
  private JavaScriptObject parser = null;
  
  /**
   * An instance of Spreadsheet.java .
   */
  private IXholon spreadsheetInstance = null;
  
  @Override
  public void processReceivedMessage(IMessage msg) {
    switch (msg.getSignal()) {
    default:
      super.processReceivedMessage(msg);
    }
  }
  
  @Override
  public IMessage processReceivedSyncMessage(IMessage msg) {
    switch (msg.getSignal()) {
    case SIG_GET_FORMULA_PARSER_REQ:
    {
      if (this.parser == null) {
        this.spreadsheetInstance = msg.getSender();
        this.parser = setupParser(this.spreadsheetInstance);
      }
      return new Message(SIG_SPREADSHEET_RESP, this.parser, this, msg.getSender());
    }
    case SIG_ENCODE_XPATH_REQ:
    {
      return new Message(SIG_SPREADSHEET_RESP, this.encodeXPath((String)msg.getData()), this, msg.getSender());
    }
    default:
    {
      return super.processReceivedSyncMessage(msg);
    }
    
    } // end switch
    
  } // end processReceivedSyncMessage()
	
	/**
   * Setup the handsontable parser.
   * @param spreadsheetInstance An instance of Spreadsheet.java .
   */
  protected native JavaScriptObject setupParser(IXholon spreadsheetInstance) /*-{
    var parser = null;
    //$wnd.console.log("Spreadsheet setupParser()");
    var $thisSprInst = spreadsheetInstance;
    var $this = this;
    //$wnd.console.log($thisSprInst);
    //$wnd.console.log($thisSprInst.name());
    if ($wnd.formulaParser) {
      parser = new $wnd.formulaParser.Parser();
    }
    if (!parser) {
      $thisSprInst.println("The Excel formula parser is missing. Make sure you are using XholonSpreadsheet.html instead of Xholon.html.");
      return;
    }
    
    // source: http://stackoverflow.com/questions/472418/why-is-4-not-an-instance-of-number/472465
    function typeOf(value) {
      var type = typeof value;
      switch(type) {
        case 'object':
          var t = Object.prototype.toString.call(value).match(/^\[object (.*)\]$/)[1];
          return value === null ? 'null' : t;
        case 'function':
          return 'Function';
        default:
          return type;
      }
    }
    
    function switchCellData(cellData) {
      switch (typeOf(cellData)) {
      case "number": break;
      case "string": break;
      case "boolean": break;
      case "Number": cellData = cellData.valueOf(); break;
      case "String": cellData = cellData.valueOf(); break;
      case "Boolean": cellData = cellData.valueOf(); break;
      case "Object":
        // assume this is a Java object
        var clazz = cellData.@java.lang.Object::getClass()();
        //$wnd.console.log(clazz);
        var clazzName = clazz.@java.lang.Class::getSimpleName()();
        //$wnd.console.log(clazzName);
        switch(clazzName) {
        case "Integer": cellData = cellData.@java.lang.Integer::intValue()(); break;
        case "Double": cellData = cellData.@java.lang.Double::doubleValue()(); break;
        case "Boolean": cellData = cellData.@java.lang.Boolean::booleanValue()(); break;
        case "String": break; // Java String
        default: break;
        }
        break;
      default: break;
      }
      return cellData;
    }
    
    // variable name rules in Jison parser
    // [A-Za-z]{1,}[A-Za-z_0-9]+    {return 'VARIABLE';}
    // [A-Za-z_]+                   {return 'VARIABLE';}
    parser.on('callVariable', function(name, done) {
      //$wnd.console.log("on callVariable " + name);
      var val = null;
      if (name.substring(0,5).toLowerCase() == "xpath") {
        // this is an XPath expression
        // ex: <Sfr>=INT(SUM(XPATH/descendant::Cytosol/Glucose/attribute::double))</Sfr>
        name = name.substring(5);
        var xpathExpr = $this.@org.primordion.xholon.service.spreadsheet.FormulaParser::decodeXPath(Ljava/lang/String;)(name);
        //$wnd.console.log("this is an XPath expression");
        //$wnd.console.log(xpathExpr);
        var node = $thisSprInst.xpath(xpathExpr);
        //$wnd.console.log(node);
        if (node) {
          val = node.obj();
          //$wnd.console.log(val);
        }
      }
      done(val);
    });
    
    parser.on('callCellValue', function(cellCoord, done) {
      //$wnd.console.log("on callCellValue " + cellCoord.row.index + "," + cellCoord.column.index);
      var xhRow = $thisSprInst.first();
      // get to the starting row, if cellCoord.row.index > 0
      for (var i = 0; i < cellCoord.row.index; i++) {
        xhRow = xhRow.next();
      }
      //$wnd.console.log(xhRow.name());
      var xhCell = xhRow.first();
      // get to the starting column, if cellCoord.column.index > 0
      for (var j = 0; j < cellCoord.column.index; j++) {
        xhCell = xhCell.next();
      }
      //$wnd.console.log(xhCell.name());
      var cellData = xhCell.obj();
      cellData = switchCellData(cellData);
      done(cellData);
    });
    
    parser.on('callRangeValue', function(startCellCoord, endCellCoord, done) {
      //$wnd.console.log("on callRangeValue " + startCellCoord.row.index + "," + startCellCoord.column.index + " " + endCellCoord.row.index + "," + endCellCoord.column.index);
      //$wnd.console.log($thisSprInst); // "this" is window
      //$wnd.console.log($thisSprInst.toString());
      //$wnd.console.log($thisSprInst.name());
      
      var xhRow = $thisSprInst.first();
      // get to the starting row, if startCellCoord.row.index > 0
      for (var i = 0; i < startCellCoord.row.index; i++) {
        xhRow = xhRow.next();
      }
      //$wnd.console.log(xhRow.name());
      var fragment = [];
      for (var row = startCellCoord.row.index; row <= endCellCoord.row.index; row++) {
        var xhCell = xhRow.first();
        // get to the starting column, if startCellCoord.column.index > 0
        for (var j = 0; j < startCellCoord.column.index; j++) {
          xhCell = xhCell.next();
        }
        //$wnd.console.log(xhCell.name());
        var colFragment = [];

        for (var col = startCellCoord.column.index; col <= endCellCoord.column.index; col++) {
          var cellData = xhCell.obj(); // TODO this is a Java Integer Double Boolean String; convert it to a JS type
          //$wnd.console.log(cellData);
          //$wnd.console.log(typeof cellData);
          cellData = switchCellData(cellData);
          colFragment.push(cellData);
          xhCell = xhCell.next();
        }
        fragment.push(colFragment);
        xhRow = xhRow.next();
      }
      if (fragment) {
        done(fragment);
      }
      
    });
    
    return parser;
  }-*/;
  
  /**
   * Encode an XPath expression, so it can be used as a formula-parser variable name
   * Example:
encode("XPATHdescendant::Spreadsheet[@roleName='test01']/Row[@roleName='5']/Cell[@roleName='B']");
   * The result would be:
"descendant_COLN__COLN_Spreadsheet_OPEN__AMPR_roleName_EQUL__QUOT_test_ZERO__ONEE__QUOT__CLOS__SLSH_Row_OPEN__AMPR_
roleName_EQUL__QUOT__FIVE__QUOT__CLOS__SLSH_Cell_OPEN__AMPR_roleName_EQUL__QUOT_B_QUOT__CLOS_"
   *
   * @param inn an XPath expression.
   * @return an encoded XPath expression.
   */
  public native String encodeXPath(String inn) /*-{
    var out = "";
    var ix = 0;
    while (ix < inn.length) {
      var achar = inn.charAt(ix);
      switch (achar) {
      case "_":  out += "__"; break;
      case ".":  out += "_DOTT_"; break;
      case "/":  out += "_SLSH_"; break;
      case "*":  out += "_STAR_"; break;
      case "[":  out += "_OPEN_"; break;
      case "]":  out += "_CLOS_"; break;
      case "-":  out += "_DASH_"; break;
      case ":":  out += "_COLN_"; break;
      case "@":  out += "_AMPR_"; break;
      case "'":  out += "_QUOT_"; break;
      case "=":  out += "_EQUL_"; break;
      case "0":  out += "_ZERO_"; break;
      case "1":  out += "_ONEE_"; break;
      case "2":  out += "_TWOO_"; break;
      case "3":  out += "_THRE_"; break;
      case "4":  out += "_FOUR_"; break;
      case "5":  out += "_FIVE_"; break;
      case "6":  out += "_SIXX_"; break;
      case "7":  out += "_SEVN_"; break;
      case "8":  out += "_EIGH_"; break;
      case "9":  out += "_NINE_"; break;
      default: out += achar; break;
      }
      ix++;
    }
    return out;
  }-*/;
  
  /**
   * Decode back to a standard XPath expression.
   * @param an encoded XPath expression.
   * @return the original XPath expression.
   */
  public native String decodeXPath(String inn) /*-{
    var out = "";
    var ix = 0;
    while (ix < inn.length) {
      var achar = inn.charAt(ix);
      switch (achar) {
      case "_":
        if (inn.charAt(ix+1) == "_") {
          out += "_";
          ix += 2;
        }
        else {
          var token = inn.substring(ix+1, ix+5); // all tokens are the same length
          //console.log(token);
          switch (token) {
          case "DOTT": out += "."; break;
          case "SLSH": out += "/"; break;
          case "STAR": out += "*"; break;
          case "OPEN": out += "["; break;
          case "CLOS": out += "]"; break;
          case "DASH": out += "-"; break;
          case "COLN": out += ":"; break;
          case "AMPR": out += "@"; break;
          case "QUOT": out += "'"; break;
          case "EQUL": out += "="; break;
          case "ZERO": out += "0"; break;
          case "ONEE": out += "1"; break;
          case "TWOO": out += "2"; break;
          case "THRE": out += "3"; break;
          case "FOUR": out += "4"; break;
          case "FIVE": out += "5"; break;
          case "SIXX": out += "6"; break;
          case "SEVN": out += "7"; break;
          case "EIGH": out += "8"; break;
          case "NINE": out += "9"; break;
          default: console.log("decode error " + token); break;
          }
          ix += 6;
        }
        break;
      default:
        out += achar;
        ix++;
        break;
      } // end switch
    } // end while
    return out;
  }-*/;
  
  @Override
  public String toString() {
    String str = this.getName();
    if (this.spreadsheetInstance == null) {
      str += " null";
    }
    else {
      str += " " + this.spreadsheetInstance.getName();
    }
    return str;
  }
  
}

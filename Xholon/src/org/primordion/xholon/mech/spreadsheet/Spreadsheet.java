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
import org.primordion.xholon.service.XholonHelperService;
//import org.primordion.xholon.io.xml.IXholon2Xml;
//import org.primordion.xholon.io.xml.IXmlWriter;

/**
 * Spreadsheet
 * Reads spreadsheet data in CSV format, with data and formulas.
 * This could be a .csv file, a URL, or text between start and end tags.
 * Generates a subtree from the CSV data, consisting of rows and cells.
 * 
 * example:
<pre>
<Spreadsheet>
1,9
2,8
3,7
=SUM(A1:A3),=SUM(B1:B3)
</Spreadsheet>
</pre>
 * 
 * TODO:
 * - cells should keep this Spreadsheet node informed of changes; only invoke act() if this node knows about changes
 * - handle imported CSV data that contains "" as text delimiter
 * - get #ERROR! when try to sum columns that contain null or ""
 * - handle parser.on('callVariable'
 *  - this could be any Xholon string including an xpath expression
 * - handle references to cells in other spreadsheets
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="https://github.com/handsontable/formula-parser">handsontable JS library website</a>
 * @since 0.9.1 (Created on Oct 14, 2016)
 */
public class Spreadsheet extends XholonWithPorts {
  
  private IXholon xholonHelperService = null;
  
  /**
   * A spreadsheet may have a name.
   * A cell in one spreadsheet can refer to a cell in another spreadsheet, by using the spreadsheet name.
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
  
  protected String val = null;
  
  @Override
  public String getVal_String() {return val;}
  
  @Override
  public void setVal(String val) {this.val = val;}
  
  protected boolean shouldAct = true;
  
  protected String fieldDelimiter = ","; // , ; : TAB SPACE
  
  protected String textDelimiter = "\""; // " '
  
  @Override
  public void postConfigure() {
    //this.println(val);
    this.setupParser();
    xholonHelperService = this.getService("XholonHelperService");
    if (val != null) {
      // parse the val into rows and cells
      String xmlStr = "<_-.sdata>";
      int rowNum = 1;
      String[] rows = val.split("\n");
      for (int i = 0; i < rows.length; i++) {
        xmlStr += "<Srw roleName=\"" + rowNum++ + "\">";
        // TODO be able to handle formulas and other text that contain embedded commas; how do I handle this in Avatar.java?
        String[] cells = rows[i].split(fieldDelimiter);
        int colNameAscii = (int)'A'; // TODO handle more than just 26 values (A-Z)
        for (int j = 0; j < cells.length; j++) {
          //this.println(cells[j].trim());
          xmlStr += "<Scl roleName=\"" + (char)colNameAscii + "\">" + cells[j].trim() + "</Scl>";
          colNameAscii++;
        }
        xmlStr += "</Srw>";
      }
      xmlStr += "</_-.sdata>";
      //this.println(xmlStr);
      ((XholonHelperService)xholonHelperService).pasteLastChild(this, xmlStr);
    }
    // DO NOT CALL f.postConfigure(); pasteLastChild() has already done this
    /*IXholon f = this.getFirstChild();
    if (f != null) {
      f.postConfigure();
    }*/
    IXholon n = this.getNextSibling();
    if (n != null) {
      n.postConfigure();
    }
  }
  
  @Override
  public void act() {
    /*if (shouldAct) {
      super.act();
    }*/
    this.writeHtmlTable(this.makeHtmlTable());
    IXholon n = this.getNextSibling();
    if (n != null) {
      n.act();
    }
  }
  
  @Override
  public int setAttributeVal(String attrName, String attrVal) {
    if ("fieldDelimiter".equals(attrName)) {
      this.fieldDelimiter = attrVal.trim();
    }
    else if ("textDelimiter".equals(attrName)) {
      this.textDelimiter = attrVal.trim();
    }
    else {
      return super.setAttributeVal(attrName, attrVal);
    }
    return 0;
  }
  
  /**
   * Convert the Xholon spreadsheet structure into a HTML table.
   */
  protected String makeHtmlTable() {
    String html = "<table>\n";
    IXholon row = this.getFirstChild();
    while (row != null) {
      html += "  <tr>\n";
      IXholon cell = row.getFirstChild();
      while (cell != null) {
        html += "    <td>";
        html += "" + cell.getVal_Object();
        html += "</td>\n";
        cell = cell.getNextSibling();
      }
      html += "  </tr>\n";
      row = row.getNextSibling();
    }
    html += "</table>\n";
    //this.println(html);
    return html;
  }
  
  protected native void writeHtmlTable(String html) /*-{
    var ele = $doc.querySelector("#xhappspecific");
    var div = $doc.createElement('div');
    ele.appendChild(div);
    div.innerHTML = html;
  }-*/;
  
  /**
   * Setup the handsontable parser.
   */
  protected native void setupParser() /*-{
    this.parser = null;
    //$wnd.console.log("Spreadsheet setupParser()");
    var $this = this;
    //$wnd.console.log($this);
    //$wnd.console.log($this.name());
    if ($wnd.formulaParser) {
      this.parser = new $wnd.formulaParser.Parser();
    }
    
    function switchCellData(cellData) {
      switch (typeof cellData) {
      case "number": break;
      case "string": break;
      case "boolean": break;
      case "object":
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
    
    this.parser.on('callVariable', function(name, done) {
      //$wnd.console.log("on callVariable " + name);
    });
    
    this.parser.on('callCellValue', function(cellCoord, done) {
      //$wnd.console.log("on callCellValue " + cellCoord.row.index + "," + cellCoord.column.index);
      var xhRow = $this.first();
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
    
    this.parser.on('callRangeValue', function(startCellCoord, endCellCoord, done) {
      //$wnd.console.log("on callRangeValue " + startCellCoord.row.index + "," + startCellCoord.column.index + " " + endCellCoord.row.index + "," + endCellCoord.column.index);
      //$wnd.console.log($this); // "this" is window
      //$wnd.console.log($this.toString());
      //$wnd.console.log($this.name());
      
      var xhRow = $this.first();
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
  }-*/;
  
  public native JavaScriptObject getParser() /*-{
    return this.parser;
  }-*/;

  
}

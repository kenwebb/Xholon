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
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.service.spreadsheet.ISpreadsheetService;
import org.primordion.xholon.service.XholonHelperService;

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
 * 
<pre>
<script><![CDATA[
// Test formula-parser variables
var xh = $wnd.xh;
var spr = xh.root().xpath("descendant::Spreadsheet");
if (spr && spr.parser) {
  spr.parser.setVariable('foo', [1,1,2,2,2]);
  var x = spr.parser.parse('COUNTIN(foo,1)');
  xh.root().println(x.error + " " + x.result); // null 2
  var y = spr.parser.parse('COUNTIN(foo,2)');
  xh.root().println(y.error + " " + y.result); // null 3
  
  //var encodedXpathExpr = encode("descendant::Spreadsheet[@roleName='test01']/Row[@roleName='5']/Cell[@roleName='B']");
  // the following name is an encoded XPath expression
  var z = spr.parser.parse('SUM(XPATHdescendant_COLN__COLN_Spreadsheet_OPEN__AMPR_roleName_EQUL__QUOT_test_ZERO__ONEE__QUOT__CLOS__SLSH_Row_OPEN__AMPR_roleName_EQUL__QUOT__FIVE__QUOT__CLOS__SLSH_Cell_OPEN__AMPR_roleName_EQUL__QUOT_B_QUOT__CLOS_)');
  xh.root().println(z.error + " " + z.result); // #NAME? null
  
}
]]></script>
</pre>
 * 
 * TODO:
 * - handle imported CSV data that contains "" as text delimiter
 * - get #ERROR! when try to sum columns that contain null or ""
 * - handle references to cells in other spreadsheets
 * 
 * - able to paste a new Spreadsheet into any Xholon app, and have the new spreadsheet work correctly
 *  - note that I have to use XholonSpreadsheet.html rather than Xholon.html
 *  - Spreadsheet with a single CSV content:
<pre>
<Spreadsheet roleName="One">
2,4,8,=SUM(A1:C1)
</Spreadsheet>
</pre>
 *  - Spreadsheet with child SpreadsheetRow and SpreadsheetCell nodes:
<pre>
<Spreadsheet roleName="Two">
  <SpreadsheetRow>
    <SpreadsheetCell>2</SpreadsheetCell>
    <SpreadsheetCell>4</SpreadsheetCell>
    <SpreadsheetCell>8</SpreadsheetCell>
    <SpreadsheetCell>
      <SpreadsheetFormula>=SUM(A1:C1)</SpreadsheetFormula>
    </SpreadsheetCell>
  </SpreadsheetRow>
</Spreadsheet>
</pre>
 * - 
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
  
  protected String spreadsheetName = null;
  
  protected IXholon spreadsheetService = null;
  
  @Override
  public void postConfigure() {
    //this.println(val);
    this.spreadsheetName = this.getName("r_c_i^"); // use underscore instead of colon
    
    // use SpreadsheeetService to get singleton parser
    this.spreadsheetService = this.getService("SpreadsheetService-FormulaParser");
    if (this.spreadsheetService == null) {
      this.consoleLog("unable to create SpreadsheetService");
    }
    else {
      IMessage msg = this.spreadsheetService.sendSyncMessage(ISpreadsheetService.SIG_GET_FORMULA_PARSER_REQ, null, this);
      Object parser = msg.getData();
      //this.consoleLog(parser);
      this.setParser(parser);
      //this.consoleLog(this.getParser());
    }
    //this.setupParser(); // TODO use the service instead of calling this.setupParser()
    if (this.getParser() == null) {
      shouldAct = false;
    }
    if (shouldAct) {
      shouldAct = false;
      xholonHelperService = this.getService("XholonHelperService");
      if (val == null) {
        // call f.postConfigure() which presumably is a SpreadsheetRow containing one or more SpreadsheetCell nodes
        IXholon f = this.getFirstChild();
        if (f != null) {
          f.postConfigure();
        }
      }
      else {
        // parse the CSV val into rows and cells
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
            String str = cells[j].trim();
            xmlStr += "<Scl roleName=\"" + (char)colNameAscii + "\">";
            if ((str.length() > 1) && (str.charAt(0) == '=')) {
              // this is a formula
              xmlStr += "<Sfr>" + str + "</Sfr>";
            }
            else {
              // this is a value
              xmlStr += str;
            }
            xmlStr += "</Scl>";
            colNameAscii++;
          }
          xmlStr += "</Srw>";
        }
        xmlStr += "</_-.sdata>";
        //this.println(xmlStr);
        ((XholonHelperService)xholonHelperService).pasteLastChild(this, xmlStr);
        // DO NOT CALL f.postConfigure(); pasteLastChild() has already done this
      }
      this.writeHtmlTable(this.makeHtmlTable(this.spreadsheetName), this.spreadsheetName);
      //shouldAct = false;
    }
    IXholon n = this.getNextSibling();
    if (n != null) {
      n.postConfigure();
    }
  }
  
  @Override
  public void act() {
    if (shouldAct) {
      shouldAct = false;
      IXholon f = this.getFirstChild();
      if (f != null) {
        f.act();
      }
      this.writeHtmlTable(this.makeHtmlTable(this.spreadsheetName), this.spreadsheetName);
      //shouldAct = false;
    }
    IXholon n = this.getNextSibling();
    if (n != null) {
      n.act();
    }
  }
  
  @Override
  public void doAction(String action) {
    if ("hasChanged".equals(action)) {
      shouldAct = true;
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
   * @param spreadsheetName 
   */
  protected String makeHtmlTable(String spreadsheetName) {
    String html = "<div>\n";
    html += "<hr />\n";
    html += "<h3>" + spreadsheetName + "</h3>\n";
    html += "<table>\n";
    IXholon row = this.getFirstChild();
    IXholon hcell = row.getFirstChild();
    html += "  <thead>\n    <tr>\n";
    while (hcell != null) {
      html += "      <th>";
      html += "" + hcell.getRoleName();
      html += "</th>\n";
      hcell = hcell.getNextSibling();
    }
    html += "    <tr>\n  </thead>\n";
    html += "  <tbody>\n";
    while (row != null) {
      html += "    <tr>\n";
      IXholon cell = row.getFirstChild();
      while (cell != null) {
        html += "      <td>";
        html += "" + cell.getVal_Object();
        html += "</td>\n";
        cell = cell.getNextSibling();
      }
      html += "    </tr>\n";
      row = row.getNextSibling();
    }
    html += "  </tbody>\n";
    html += "</table>\n";
    html += "</div>\n";
    //this.println(html);
    return html;
  }
  
  /**
   * Write this spreadsheet as an HTML table.
   * @param html 
   * @param spreadsheetName 
   */
  protected native void writeHtmlTable(String html, String spreadsheetName) /*-{
    var ele = $doc.querySelector("#xhappspecific");
    var div = ele.querySelector("#" + spreadsheetName);
    if (div == null) {
      div = $doc.createElement('div');
      div.id = spreadsheetName;
      ele.appendChild(div);
    }
    div.innerHTML = html;
  }-*/;
  
  public native JavaScriptObject getParser() /*-{
    return this.parser;
  }-*/;
  
  protected native void setParser(Object parser) /*-{
    this.parser = parser;
  }-*/;
  
  public IXholon getSpreadsheetService() {
    return spreadsheetService;
  }
  
  @Override
  public void toXml(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {
    xmlWriter.writeStartElement(this.getXhcName());
    if (this.roleName != null) {
      // this is a user-specified roleName
      xmlWriter.writeAttribute("roleName", this.roleName);
    }
    // write children
    IXholon childNode = getFirstChild();
    while (childNode != null) {
      childNode.toXml(xholon2xml, xmlWriter);
      childNode = childNode.getNextSibling();
    }
    xmlWriter.writeEndElement(this.getXhcName());
  }
  
  @Override
  public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {}

}

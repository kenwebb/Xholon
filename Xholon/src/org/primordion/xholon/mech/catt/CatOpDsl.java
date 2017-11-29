/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2017 Ken Webb
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

package org.primordion.xholon.mech.catt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.service.IXholonService;

/**
 * Category Theory - Operadics DSL
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href=""> website</a>
 * @since 0.9.1 (Created on November 28, 2017)
 */
public class CatOpDsl extends XholonWithPorts {
  

  // exports
  private boolean exportTex = false;
  private boolean exportGraphviz = false;
  private boolean exportXml = false;
  private boolean exportYaml = false;
  
  private IApplication app = null;
  private IXholon xholonHelperService = null;
  private IXholon xhRoot = null;
  private IXholonClass xhcRoot = null;
  private IXholon cattSystem = null;
  
  private String roleName = null;
  
  //private Map<String,String> xhClassNameReplacementMap = null;
  //private Map<String,IXholon> opdslGenerator2XhNodeMap = null;
  
  private String opdslWebInterfaceTextareaSelector = "#xhtabs > table > tbody > tr > td:nth-child(1) > div > div:nth-child(4) > div > div:nth-child(3) > div > div:nth-child(5) > div > div:nth-child(4) > table > tbody > tr > td > div > div > div > textarea";
  
  /**
   * Whether or not to use a PEG.js parser to validate the OpDSL content.
   */
  private boolean validatePEG = false;
  private String parserNamePEG = "PEGOPDSL";
  
  @Override
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
  
  @Override
  public String getRoleName() {
    return roleName;
  }
  
  /**
    * The OpDSL content will be in this variable.
    */
  private String val = null;
  
  @Override
  public String getVal_String() {
    return val;
  }
  
  @Override
  public void setVal(String val) {
    this.val = val;
  }
  
  @Override
  public void setVal_String(String val) {
    this.val = val;
  }
  
  @Override
  public Object getVal_Object() {
    return val;
  }
  
  @Override
  public void setVal(Object val) {
    this.val = val.toString();
  }
  
  @Override
  public void setVal_Object(Object val) {
    this.val = val.toString();
  }
  
  @Override
  public void postConfigure() {
    if (this.val == null) {
      // the content may be inside a <Attribute_String/> node
      // this may occur if there's a <xi:include ... />
      IXholon contentNode = this.getFirstChild();
      if (contentNode != null) {
        this.val = contentNode.getVal_String();
      }
    }
    if (this.val == null) {
      this.val = findOpDslContentInOpDslWebInterface(opdslWebInterfaceTextareaSelector);
    }
    if (this.val == null) {
      this.consoleLog("CatOpDsl does not contain any content.");
    }
    else {
      this.val = this.val.trim();
      if (this.val.length() > 100) {
        this.app = this.getApp();
        this.xhRoot = app.getRoot();
        this.xhcRoot = app.getXhcRoot();
        //this.xhClassNameReplacementMap = new HashMap<String,String>();
        //this.opdslGenerator2XhNodeMap = new HashMap<String,IXholon>();
        if (this.validatePEG) {
          this.validatePEG(val, parserNamePEG);
        }
        parseOpDslContent(val);
        cattSystem = xhRoot.getLastChild().getLastChild().getFirstChild(); // this gets the top-level Pack
        if (cattSystem == null) {
          this.consoleLog("CatOpDsl is unable to find top-level Pack cattSystem.");
          this.consoleLog(xhRoot.getLastChild());
        }
        else {
          if (this.exportXml) {
            this.exportXml(this.cattSystem);
          }
          if (this.exportYaml) {
            this.exportYaml(this.cattSystem);
          }
          if (this.exportGraphviz) {
            this.exportGraphviz(this.cattSystem);
          }
          if (this.exportTex) {
            this.exportTex(this.cattSystem);
          }
        }
      }
      else {
        this.consoleLog("CatOpDsl does not contain any usable OpDSL content.");
      }
    }
    super.postConfigure();
  }
  
  @Override
  public void act() {
    // is there anything to do?
    super.act();
  }
  
  /**
   * Try to find the OpDSL content inside the OpDSL Web Interface, as defined in OpDslWebInterface.java and OpDslWebInterface.ui.xml .
   */
  protected native String findOpDslContentInOpDslWebInterface(String selector) /*-{
    var ele = $doc.querySelector(selector);
    if (ele) {
      return ele.value;
    }
  }-*/;
  
  /**
   * Validate using a PEG.js parser.
   * PEGOPDSL.parse("typeside Ty = literal {  }");
   */
  protected native void validatePEG(String opdslContent, String parserName) /*-{
    if ($wnd[parserName]) {
      var result = $wnd[parserName].parse(opdslContent);
      $wnd.console.log(result);
    }
  }-*/;
  
  /**
   * Parse the OpDSL content.
   * This approach is temporary.
   */
  protected native void parseOpDslContent(String opdslContent) /*-{
    if ($wnd.xh.OperadBuilder) {
      $wnd.xh.OperadBuilder(this, opdslContent);
    }
    else {
      $wnd.console.log("can't find $wnd.xh.OperadBuilder");
    }
  }-*/;
  
  /**
   * Send a synchronous message to the XholonHelperService.
   * @param signal
   * @param data
   * @param sender
   * @return A response message.
   */
  protected IMessage sendXholonHelperService(int signal, Object data, IXholon sender)
  {
    // send the request to the XholonHelperService by sending it a sync message
    if (xholonHelperService == null) {
      xholonHelperService = app.getService(IXholonService.XHSRV_XHOLON_HELPER);
    }
    if (xholonHelperService == null) {
      return null;
    }
    else {
      if (sender == null) {sender = app;}
      return xholonHelperService.sendSyncMessage(signal, data, sender);
    }
  }
  
  protected native void exportTex(IXholon cattSystem) /*-{
    $wnd.xh.xport("_tex,TexWirDiaO", cattSystem, '{"scale":1,"nameTemplate":"r:c_i","bbNameTemplate":"R^^^^^","maxChars":-1,"showPortName":false,"nodesStyle":"rectangle","linksStyle":"default","siblingsPosition":"right=5 of","diffXhtypePosition":"below=10 of","bipartite":false,"intermedDashing":"loosely dotted","includePreamble":true,"documentclass":"[10pt,oneside,article,landscape]{memoir}","preambleFileName":"texWirDiaOpreamble.tex","includeBeginEndDoc":true,"title":"default","author":"default","includeBeginEndEqu":false}');
  }-*/;
  
  protected native void exportGraphviz(IXholon cattSystem) /*-{
    $wnd.xh.xport("Graphviz", cattSystem, '{"shouldShowLinkLabels":true,"shouldSpecifyShape":true,"shouldSpecifyArrowhead":true,"shouldDisplayGraph":true}');
  }-*/;
  
  protected native void exportXml(IXholon cattSystem) /*-{
    $wnd.xh.xport("Xml", cattSystem, '{"writeStartDocument":false,"writePorts":true,"shouldWriteVal":false,"shouldWriteAllPorts":false}');
  }-*/;
  
  protected native void exportYaml(IXholon cattSystem) /*-{
    //$wnd.xh.xport("Yaml", cattSystem,'{"writeStartDocument":true,"shouldWriteVal":false,"shouldWriteAllPorts":false}');
    $wnd.xh.xport("Yaml", cattSystem,'{"writeStartDocument":true,"shouldWriteVal":false,"shouldWriteAllPorts":false,"shouldWriteLinks":true}');
  }-*/;
  
  @Override
  public int setAttributeVal(String attrName, String attrVal) {
    if ("val".equals(attrName)) {
      // this is the OpDSL content
      this.val = attrVal;
    }
    else if ("exportTex".equals(attrName)) {
      this.exportTex = Boolean.parseBoolean(attrVal);
    }
    else if ("exportGraphviz".equals(attrName)) {
      this.exportGraphviz = Boolean.parseBoolean(attrVal);
    }
    else if ("exportYaml".equals(attrName)) {
      this.exportYaml = Boolean.parseBoolean(attrVal);
    }
    else if ("exportXml".equals(attrName)) {
      this.exportXml = Boolean.parseBoolean(attrVal);
    }
    else if ("validatePEG".equals(attrName)) {
      this.validatePEG = Boolean.parseBoolean(attrVal);
    }
    else if ("opdslWebInterfaceTextareaSelector".equals(attrName)) {
      this.opdslWebInterfaceTextareaSelector = attrVal;
    }
    return 0;
  }
  
  @Override
  public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {
    xmlWriter.writeAttribute("exportTex", Boolean.toString(this.exportTex));
    xmlWriter.writeAttribute("exportGraphviz", Boolean.toString(this.exportGraphviz));
    xmlWriter.writeAttribute("exportYaml", Boolean.toString(this.exportYaml));
    xmlWriter.writeAttribute("exportXml", Boolean.toString(this.exportXml));
    xmlWriter.writeAttribute("validatePEG", Boolean.toString(this.validatePEG));
    xmlWriter.writeAttribute("opdslWebInterfaceTextareaSelector", this.opdslWebInterfaceTextareaSelector);
  }
  
}

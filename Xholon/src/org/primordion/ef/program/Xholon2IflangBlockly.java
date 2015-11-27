/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2015 Ken Webb
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

package org.primordion.ef.program;

//import com.google.gwt.http.client.URL;
//import com.google.gwt.user.client.Window;

import java.util.Date;
import java.util.List;

import org.client.GwtEnvironment;
import org.primordion.xholon.base.IMechanism;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.util.ClassHelper;
import org.primordion.xholon.util.IJavaTypes;
import org.primordion.xholon.util.Misc;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Export a Xholon model as an Interactive Fiction (IF language) script,
 * in Google Blockly format.
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href=""></a>
 * @since 0.9.1 (Created on May 31, 2015)
 */
@SuppressWarnings("serial")
public class Xholon2IflangBlockly extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat, IXmlWriter {
  
  private String outFileName;
  private String outPath = "./ef/iflangblockly/";
  private String modelName;
  private IXholon root;
  private StringBuilder sb;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  /** used to pass node between writeNode() and writeAttribute() */
  private IXholon currentNode = null;
  
  private String endofline = "\n";
  
  /** Each Blockly block has an id (ex: id="1") */
  private int blockId = 1;
  
  /**
   * How many levels of "next block" have been written out.
   * This is used to write out matching "next" and "block" end elements.
   */
  private int nextBlockLevel = 0;
  
  /**
   * Constructor.
   */
  public Xholon2IflangBlockly() {}
  
	@Override
	public String getVal_String() {
	  return sb.toString();
	}
	
  /*
   * @see org.primordion.xholon.io.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
   */
  public boolean initialize(String fileName, String modelName, IXholon root) {
    timeNow = new Date();
    timeStamp = timeNow.getTime();
    if (fileName == null) {
      this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".iflangblockly";
    }
    else {
      this.outFileName = fileName;
    }
    this.modelName = modelName;
    this.root = root;
    return true;
  }
  
  /*
   * @see org.primordion.xholon.io.IXholon2ExternalFormat#writeAll()
   */
  public void writeAll() {
    sb = new StringBuilder();
    if (isWriteOneLineOnly()) {
      endofline = "";
    }
    else {
      endofline = "\n";
    }
    writeStartDocument();
    writeNode(root);
    writeEndDocument();
    String cs = sb.toString();
    writeToTarget(cs, outFileName, outPath, root);
  }
  
  /**
   * Write one node, and its child nodes.
   * @param node The current node in the Xholon composite structure hierarchy (CSH).
   */
  protected void writeNode(IXholon node) {
    if (node == null) {return;}
    String nodeName = node.getName(getNameTemplate());
    String xhcNodeName = node.getXhcName();
    if (xhcNodeName.endsWith("behavior")) {return;}
    writeStartBlock("xhiflang_build", "false", node == root ? false : true);
    writeField("COMMAND", "BUILD");
    writeField("THING", xhcNodeName);
    
    String rn = node.getRoleName();
    if ((rn != null) && (rn.length() > 0)) {
      writeValue("ROLE", "xhiflang_role", "", "ROLE", rn);
    }
    if (node.hasChildNodes()) {
      writeStartBlock("xhiflang_enter", null, true);
      writeField("THING", nodeName);
      IXholon childNode = node.getFirstChild();
      while (childNode != null) {
        writeNode(childNode);
        childNode = childNode.getNextSibling();
      }
      writeStartBlock("xhiflang_exit", null, true);
      writeField("THING", "");
    }
  }
  
  /**
   * Write the start of a block element.
   * ex: <block type="xhxml_rolename" id="2">
   * ex: <block type="xhiflang_build" id="4" inline="false">
   * @param type (ex: type="xhiflang_build")
   * @param inline (ex: inline="false")
   *   if the value is null, then do not include the inline attribute
   * @param wrapNext Whether or not to wrap a "next" element around the "block"
   */
  protected void writeStartBlock(String type, String inline, boolean wrapNext) {
    if (wrapNext) {
      sb.append("<next>\n");
      nextBlockLevel++;
    }
    sb
    .append("<block type=\"")
    .append(type)
    .append("\" id=\"")
    .append(blockId)
    .append("\"");
    blockId++;
    if ((inline != null) && (inline.length() > 3)) { // "true" or "false"
      sb
      .append(" inline=\"")
      .append(inline)
      .append("\"");
    }
    sb.append(">\n");
  }
  
  /**
   * Write the start and end of a field element.
   * ex: <field name="COMMAND">BUILD</field>
   * ex: <field name="THING">School</field>
   * ex: <field name="THING"></field>
   * @param name 
   * @param value 
   */
  protected void writeField(String name, String value) {
    if (value == null) {value = "";}
    sb
    .append(" <field name=\"")
    .append(name)
    .append("\">")
    .append(value)
    .append("</field>\n");
  }
  
  /**
   * Write the start, contents, and end of a value element.
   * ex: <value name="ROLE">
           <block type="xhiflang_role" id="8">
             <field name="ROLE">Potions</field>
           </block>
         </value>
   * @param valueName 
   * @param blockType 
   * @param blockInline 
   * @param fieldName 
   * @param fieldValue 
   */
  protected void writeValue(String valueName,
      String blockType, String blockInline,
      String fieldName, String fieldValue) {
    sb
    .append("<value name=\"")
    .append(valueName)
    .append("\">\n ");
    writeStartBlock(blockType, blockInline, false);
    sb.append(" ");
    writeField(fieldName, fieldValue);
    sb
    .append(" </block>\n")
    .append("</value>\n");
  }
  
  /**
   * Make a JavaScript object with all the parameters for this external format.
   * wrapInAvatarBehavior  <Avatar/> behavior  or  script:
   * writeOneLineOnly  entire script in one timestep, or spread across multiple timesteps
   * TODO unbuildAvatar whether to retain or remove the Avatar node when it's completed it's work
   *   only valid if wrapInAvatarBehavior == true
   */
  protected native void makeEfParams() /*-{
    var p = {};
    p.nameTemplate = "r:c^^^";
    p.wrapInAvatarBehavior = false;
    p.writeOneLineOnly = false; // prettyPrint = true;
    //p.shouldShowLinks = true;
    //p.shouldShowAttributes = true;
    //p.shouldShowMechanismIhNodes = false;
    //p.shouldWriteVal = false;
    //p.shouldWriteAllPorts = false;
    //p.writeToNewTab = true;
    this.efParams = p;
  }-*/;

  /**
   * Node name template.
   */
  public native String getNameTemplate() /*-{return this.efParams.nameTemplate;}-*/;
  public native void setNameTemplate(String nameTemplate) /*-{this.efParams.nameTemplate = nameTemplate;}-*/;
  
  /**
   * Whether or not to wrap the IF content inside Avatar behavior nodes.
   */
  public native boolean isWrapInAvatarBehavior() /*-{return this.efParams.wrapInAvatarBehavior;}-*/;
  //public native void setWrapInAvatarBehavior(boolean wrapInAvatarBehavior) /*-{this.efParams.wrapInAvatarBehavior = wrapInAvatarBehavior;}-*/;
  
  public native boolean isWriteOneLineOnly() /*-{return this.efParams.writeOneLineOnly;}-*/;
  //public native void setWriteOneLineOnly(boolean writeOneLineOnly) /*-{this.efParams.writeOneLineOnly = writeOneLineOnly;}-*/;

  public String getOutFileName() {
    return outFileName;
  }

  public void setOutFileName(String outFileName) {
    this.outFileName = outFileName;
  }

  public String getModelName() {
    return modelName;
  }

  public void setModelName(String modelName) {
    this.modelName = modelName;
  }

  public IXholon getRoot() {
    return root;
  }

  public void setRoot(IXholon root) {
    this.root = root;
  }

  public String getOutPath() {
    return outPath;
  }

  public void setOutPath(String outPath) {
    this.outPath = outPath;
  }
  
  // #############################################################################
  // methods required to implement IXmlWriter
  
  @Override
  // DO NOT IMPLEMENT THIS
  public void createNew(Object out) {}

  @Override
  public void writeStartDocument() {
    if (isWrapInAvatarBehavior()) {
      sb
      .append("<xml xmlns=\"http://www.w3.org/1999/xhtml\">").append(endofline);
      // <block type="xhxml_avatar" id="1" inline="false" x="74" y="18">
      sb
      .append("<block type=\"xhxml_avatar\" id=\"")
      .append(blockId++)
      .append("\" inline=\"false\" x=\"74\" y=\"18\">\n");
      writeValue("AVATAR", "xhxml_rolename", null, "ROLE", root.getXhcName() + "Avatar");
      sb.append("<statement name=\"BEHAVIOR\">\n");
      writeStartBlock("xhxml_behavior", "", false);
      sb.append("<statement name=\"NAME\">\n");
    }
    else {
      sb.append("script;");
    }
  }

  @Override
  public void writeEndDocument() {
    if (isWrapInAvatarBehavior()) {
      if (isWriteOneLineOnly()) {
        sb.append("\n");
      }
      while (nextBlockLevel-- > 0) {
        // write out matching block next end elements
        sb.append("</block>\n</next>\n");
      }
      sb
      .append("</block>\n")
      .append("</statement>\n")
      .append("</block>\n")
      .append("</statement>\n")
      .append("</block>\n")
      .append("</xml>\n");
    }
  }

  @Override
  // DO NOT IMPLEMENT THIS
  public void writeStartElement(String prefix, String localName, String namespaceURI) {}

  @Override
  // DO NOT IMPLEMENT THIS
  public void writeStartElement(String name) {}

  @Override
  // DO NOT IMPLEMENT THIS
  public void writeEndElement(String name, String namespaceURI) {}

  @Override
  // DO NOT IMPLEMENT THIS
  public void writeEndElement(String name) {}

  @Override
  // DO NOT IMPLEMENT THIS
  public void writeNamespace(String prefix, String namespaceURI) {}

  @Override
  // This is for use by Xholon.toXmlAttributes() only
  public void writeAttribute(String name, String value) {
    /*String nodeName = currentNode.getName(getNameTemplate());
    if ("Val".equalsIgnoreCase(name)) {
      if (isShouldWriteVal()) {
        sbAttrs.append(nodeName).append(".val(").append(value).append(")\n");
      }
      return;
    }
    if ("AllPorts".equalsIgnoreCase(name) && !isShouldWriteAllPorts()) {return;}
    if ("roleName".equalsIgnoreCase(name)) {return;} // roleName is already written out
    if ("implName".equalsIgnoreCase(name)) {return;}
    switch(Misc.getJavaDataType(value)) {
    case IJavaTypes.JAVACLASS_String:
      sbAttrs.append(nodeName).append(".").append(name).append(" = \"").append(value).append("\"\n");
      break;
    case IJavaTypes.JAVACLASS_int:
    case IJavaTypes.JAVACLASS_long:
    case IJavaTypes.JAVACLASS_double:
    case IJavaTypes.JAVACLASS_float:
    case IJavaTypes.JAVACLASS_boolean:
    case IJavaTypes.JAVACLASS_byte:
    case IJavaTypes.JAVACLASS_char:
    case IJavaTypes.JAVACLASS_short:
      sbAttrs.append(nodeName).append(".").append(name).append(" = ").append(value).append("\n");
      break;
    case IJavaTypes.JAVACLASS_UNKNOWN:
    default:
      break;
    }*/
    
  }

  @Override
  // DO NOT IMPLEMENT THIS
  public void writeText(String text) {}

  @Override
  public void writeComment(String data) {}

  @Override
  public String getWriterName() {
    return "Xholon2Iflang";
  }

  @Override
  // DO NOT IMPLEMENT THIS
  public void flush() {}

  /* 
   * @see org.primordion.xholon.io.xml.IXmlWriter#isShouldWriteVal()
   */
  public native boolean isShouldWriteVal() /*-{
    return false; //this.efParams.shouldWriteVal;
  }-*/;
  
  /* 
   * @see org.primordion.xholon.io.xml.IXmlWriter#setShouldWriteVal()
   */
  public native void setShouldWriteVal(boolean shouldWriteVal) /*-{
    //this.efParams.shouldWriteVal = shouldWriteVal;
  }-*/;
  
  /* 
   * @see org.primordion.xholon.io.xml.IXmlWriter#isShouldWriteAllPorts()
   */
  public native boolean isShouldWriteAllPorts() /*-{
    return false; //this.efParams.shouldWriteAllPorts;
  }-*/;
  
  /* 
   * @see org.primordion.xholon.io.xml.IXmlWriter#shouldWriteAllPorts()
   */
  public native void setShouldWriteAllPorts(boolean shouldWriteAllPorts) /*-{
    //this.efParams.shouldWriteAllPorts = shouldWriteAllPorts;
  }-*/;
  
}

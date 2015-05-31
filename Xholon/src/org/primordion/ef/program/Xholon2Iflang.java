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
 * Export a Xholon model as an Interactive Fiction (IF language) script.
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href=""></a>
 * @since 0.9.1 (Created on May 30, 2015)
 */
@SuppressWarnings("serial")
public class Xholon2Iflang extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat, IXmlWriter {
  
  private String outFileName;
  private String outPath = "./ef/iflang/";
  private String modelName;
  private IXholon root;
  private StringBuilder sb;
  private StringBuilder sbPorts;
  private StringBuilder sbAttrs;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  /** used to pass node between writeNode() and writeAttribute() */
  private IXholon currentNode = null;
  
  private String endofline = "\n";
  
  /**
   * Constructor.
   */
  public Xholon2Iflang() {}
  
  /*
   * @see org.primordion.xholon.io.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
   */
  public boolean initialize(String fileName, String modelName, IXholon root) {
    timeNow = new Date();
    timeStamp = timeNow.getTime();
    if (fileName == null) {
      this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".iflang";
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
    //sbPorts = new StringBuilder();
    writeStartDocument();
    //writeIhNode(root.getApp().getXhcRoot());
    writeNode(root);
    //if (sbPorts.length() > 0) {
    //  sb
    //  .append("# ports\n")
    //  .append(sbPorts.toString())
    //  .append("\n");
    //}
    writeEndDocument();
    //setWriteToTab(isWriteToNewTab());
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
    IXholon pnode = node.getParentNode();
    sb
    .append("build ")
    .append(xhcNodeName);
    
    String rn = node.getRoleName();
    if ((rn != null) && (rn.length() > 0)) {
      // put quote marks around roleName if it contains any space characters
      String quote = rn.indexOf(" ") == -1 ? "" : "\"";
      sb
      .append(" role ")
      .append(quote)
      .append(rn)
      .append(quote);
    }
    sb.append(";").append(endofline);
    //writeLinks(node);
    //writeAttributes(node);
    /*String text = node.getVal_String();
    if (text != null) {
      sb
      .append(nodeName)
      .append(".text(\"")
      .append(text)
      .append("\")\n");
    }*/
    /*if (node.hasAnnotation()) {
      sb
      .append(nodeName)
      .append(".anno(\"")
      .append(node.getAnnotation())
      .append("\")\n");
    }*/
    /*Object obj = node.getVal_Object();
    if (obj != null) {
      sb
      .append(nodeName)
      .append(".obj(")
      .append(obj)
      .append(")\n");
    }*/
    if (node.hasChildNodes()) {
      // put quote marks around nodeName if it contains any space characters
      String quote = nodeName.indexOf(" ") == -1 ? "" : "\"";
      sb
      .append("enter ")
      .append(quote)
      .append(nodeName)
      .append(quote)
      .append(";")
      .append(endofline);
      IXholon childNode = node.getFirstChild();
      while (childNode != null) {
        writeNode(childNode);
        childNode = childNode.getNextSibling();
      }
      sb
      .append("exit;").append(endofline);
    }
  }
  
  /**
   * Write links from this node to any others, where the Xholon node has connected ports.
   * @param node The current Xholon node.
   */
  protected void writeLinks(IXholon node) {
    //if (isShouldShowLinks() == false) {return;}
    List<PortInformation> portList = node.getAllPorts();
    for (int i = 0; i < portList.size(); i++) {
      writeLink(node, (PortInformation)portList.get(i));
    }
  }
  
  /**
   * Write one link.
   * @param node The node where the link originates.
   * @param portInfo Information about the port that represents the link.
   */
  protected void writeLink(IXholon node, final PortInformation portInfo) {
    if (portInfo == null) {return;}
    IXholon remoteNode = portInfo.getReffedNode();
    if (remoteNode == null) {return;}
    if (!remoteNode.hasAncestor(root.getName())) {
      // remoteNode is outside the scope (not a descendant) of root
      return;
    }
    sbPorts.append(node.getName(getNameTemplate()));
    int fieldNameIndex = portInfo.getFieldNameIndex();
    if (fieldNameIndex == PortInformation.PORTINFO_NOTANARRAY) {
      sbPorts
      .append(".")
      .append(portInfo.getFieldName())
      .append(" = ")
      .append(remoteNode.getName(getNameTemplate()))
      .append("\n");
    }
    else {
      sbPorts
      .append(".port")
      .append(fieldNameIndex)
      .append("(")
      .append(remoteNode.getName(getNameTemplate()))
      .append(")\n");
    }
  }
  
  /**
   * Write app-specific attributes.
   * @param node The node whose attributes should be written.
   */
  public void writeAttributes(IXholon node) {
    //if (!isShouldShowAttributes()) {return;}
    currentNode = node;
    sbAttrs = new StringBuilder();
    IXholon2Xml xholon2xml = node.getXholon2Xml();
    xholon2xml.setXhAttrStyle(IXholon2Xml.XHATTR_TO_XMLATTR);
    node.toXmlAttributes(xholon2xml, this);
    if (sbAttrs.length() > 0) {
      sb.append(sbAttrs.toString());
    }
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
    p.writeOneLineOnly = false;
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
      .append("<Avatar roleName=\"")
      .append(root.getXhcName())
      .append("Avatar\">")
      .append("<Attribute_String><![CDATA[\n");
    }
    else {
      sb.append("script;").append(endofline);
    }
    sb
    .append("[")
    .append(modelName)
    .append("];")
    .append(endofline);
  }

  @Override
  public void writeEndDocument() {
    if (isWrapInAvatarBehavior()) {
      if (isWriteOneLineOnly()) {
        sb.append("\n");
      }
      sb
      .append("]]></Attribute_String>")
      .append("</Avatar>\n");
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
    String nodeName = currentNode.getName(getNameTemplate());
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
    }
    
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

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

package org.primordion.ef.program;

import java.util.Date;
import java.util.List;

import org.primordion.xholon.base.IMechanism;
import org.primordion.xholon.base.IXholon;
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
 * Export a Xholon model as CoffeeScript.
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on July 23, 2014)
 */
@SuppressWarnings("serial")
public class Xholon2CoffeeScript extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat, IXmlWriter {
  
  private String outFileName;
  private String outPath = "./ef/coffee/";
  private String modelName;
  private IXholon root;
  private StringBuilder sb;
  private StringBuilder sbPorts;
  private StringBuilder sbTest;
  private StringBuilder sbAttrs;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  /** used to pass node between writeNode() and writeAttribute() */
  private IXholon currentNode = null;
  
  /**
   * Constructor.
   */
  public Xholon2CoffeeScript() {}
  
  /*
   * @see org.primordion.xholon.io.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
   */
  public boolean initialize(String fileName, String modelName, IXholon root) {
    timeNow = new Date();
    timeStamp = timeNow.getTime();
    if (fileName == null) {
      this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".coffee";
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
    sbPorts = new StringBuilder();
    sbTest = new StringBuilder().append("# test\n");
    writeStartDocument();
    writeIhNode(root.getApp().getXhcRoot());
    writeNode(root);
    if (sbPorts.length() > 0) {
      sb.append("# ports\n").append(sbPorts.toString()).append("\n");
    }
    sb.append(sbTest.toString());
    setWriteToTab(isWriteToNewTab());
    writeToTarget(sb.toString(), outFileName, outPath, root);
  }
  
  /**
   * Write one node, and its child nodes.
   * @param node The current node in the Xholon composite structure hierarchy (CSH).
   */
  protected void writeNode(IXholon node) {
    if (node == null) {return;}
    String nodeName = node.getName(getNameTemplate());
    sb
    .append(nodeName)
    .append(" = new ")
    .append(node.getXhcName())
    .append(" ")
    .append(node.getId())
    .append("\n");
    
    if (node == root) {
      sbTest.append("console.log ").append(nodeName).append("\n");
    }
    else if (node == node.getParentNode().getFirstChild()) {
      sb
      .append(node.getParentNode().getName(getNameTemplate()))
      .append(".first(")
      .append(nodeName)
      .append(")\n");
    }
    else {
      sb
      .append(node.getPreviousSibling().getName(getNameTemplate()))
      .append(".next(")
      .append(nodeName)
      .append(")\n");
    }
    
    String rn = node.getRoleName();
    if ((rn != null) && (rn.length() > 0)) {
      sb
      .append(nodeName)
      .append(".role(\"")
      .append(rn)
      .append("\")\n");
    }
    writeLinks(node);
    writeAttributes(node);
    sb.append("\n");
    if (node.hasChildNodes()) {
      IXholon childNode = node.getFirstChild();
      while (childNode != null) {
        writeNode(childNode);
        childNode = childNode.getNextSibling();
      }
    }
  }
  
  /**
   * Write an inheritance hierarchy (IH) node, and its child nodes.
   * Optionally only include app-specific IH nodes.
   * @param xhcNode The current node in the hierarchy.
   */
  protected void writeIhNode(IXholon xhcNode) {
    if (xhcNode == null) {return;}
    if ("XholonClass".equals(xhcNode.getName())) {
      sb
      .append("# ")
      .append(xhcNode.getId())
      .append("\n")
      .append("class ")
      .append(xhcNode.getName())
      .append("\n")
      .append("  constructor: (@id) ->\n")
      .append("  role: (@roleName) ->\n")
      .append("  first: (@firstChild) ->\n")
      .append("  next: (@nextSibling) ->\n");
      // add as many ports as specified by app MaxPorts
      for (int i = 0; i < root.getApp().getMaxPorts(); i++) {
        sb.append("  port").append(i).append(": (@port").append(id).append(") ->\n");
      }
      sb.append("\n");
    }
    else if (isShouldShowMechanismIhNodes() || (xhcNode.getId() < IMechanism.MECHANISM_ID_START)) {
      sb.append("# " + xhcNode.getId() + "\n");
      sb
      .append("class ")
      .append(xhcNode.getName())
      .append(" extends ")
      .append(xhcNode.getParentNode().getName())
      .append("\n\n");
    }
    else {} // this is a mechanism node that should not be shown, but should still be navigated
    
    if (xhcNode.hasChildNodes()) {
      IXholon childXhcNode = xhcNode.getFirstChild();
      while (childXhcNode != null) {
        writeIhNode(childXhcNode);
        childXhcNode = childXhcNode.getNextSibling();
      }
    }
  }
  
  /**
   * Write links from this node to any others, where the Xholon node has connected ports.
   * @param node The current Xholon node.
   */
  protected void writeLinks(IXholon node) {
    if (isShouldShowLinks() == false) {return;}
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
    if (!isShouldShowAttributes()) {return;}
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
   */
  protected native void makeEfParams() /*-{
    var p = {};
    p.nameTemplate = "^^c_i^";
    p.shouldShowLinks = true;
    p.shouldShowAttributes = true;
    p.shouldShowMechanismIhNodes = false;
    p.shouldWriteVal = true;
    p.shouldWriteAllPorts = true;
    p.writeToNewTab = true;
    this.efParams = p;
  }-*/;

  /**
   * Node name template.
   */
  public native String getNameTemplate() /*-{return this.efParams.nameTemplate;}-*/;
  public native void setNameTemplate(String nameTemplate) /*-{this.efParams.nameTemplate = nameTemplate;}-*/;
  
  /**
   * Whether or not to show links/ports between nodes.
   */
  public native boolean isShouldShowLinks() /*-{return this.efParams.shouldShowLinks;}-*/;
  //public native void setShouldShowLinks(boolean shouldShowLinks) /*-{this.efParams.shouldShowLinks = shouldShowLinks;}-*/;

  /**
   * Whether or not to show app-specific attributes of nodes.
   */
  public native boolean isShouldShowAttributes() /*-{return this.efParams.shouldShowAttributes;}-*/;
  //public native void sethouldShowAttributes(boolean shouldShowAttributes) /*-{this.efParams.shouldShowAttributes = shouldShowAttributes;}-*/;

  /**
   * Whether or not to show IH nodes that are part of a Xholon mechanism.
   * If this value is false, then only app-specific IH nodes will be shown.
   */
  public native boolean isShouldShowMechanismIhNodes() /*-{return this.efParams.shouldShowMechanismIhNodes;}-*/;
  //public native void setShouldShowMechanismIhNodes(boolean shouldShowMechanismIhNodes) /*-{this.efParams.shouldShowMechanismIhNodes = shouldShowMechanismIhNodes;}-*/;
  
  /**
   * Whether or not to write to a new tab each time step.
   * true create and write to a new tab
   * false write to the existing out tab
   */
  public native boolean isWriteToNewTab() /*-{return this.efParams.writeToNewTab;}-*/;
  //public native void setWriteToNewTab(boolean writeToNewTab) /*-{this.efParams.writeToNewTab = writeToNewTab;}-*/;
    
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
    sb.append("# ").append(modelName).append("\n\n");
  }

  @Override
  public void writeEndDocument() {}

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
    if ("Val".equalsIgnoreCase(name) && !isShouldWriteVal()) {return;}
    if ("AllPorts".equalsIgnoreCase(name) && !isShouldWriteAllPorts()) {return;}
    if ("roleName".equalsIgnoreCase(name)) {return;} // roleName is already written out
    if ("implName".equalsIgnoreCase(name)) {return;}
    String nodeName = currentNode.getName(getNameTemplate());
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
    return "Xholon2Future";
  }

  @Override
  // DO NOT IMPLEMENT THIS
  public void flush() {}

  /* 
   * @see org.primordion.xholon.io.xml.IXmlWriter#isShouldWriteVal()
   */
  public native boolean isShouldWriteVal() /*-{
    return this.efParams.shouldWriteVal;
  }-*/;
  
  /* 
   * @see org.primordion.xholon.io.xml.IXmlWriter#setShouldWriteVal()
   */
  public native void setShouldWriteVal(boolean shouldWriteVal) /*-{
    this.efParams.shouldWriteVal = shouldWriteVal;
  }-*/;
  
  /* 
   * @see org.primordion.xholon.io.xml.IXmlWriter#isShouldWriteAllPorts()
   */
  public native boolean isShouldWriteAllPorts() /*-{
    return this.efParams.shouldWriteAllPorts;
  }-*/;
  
  /* 
   * @see org.primordion.xholon.io.xml.IXmlWriter#shouldWriteAllPorts()
   */
  public native void setShouldWriteAllPorts(boolean shouldWriteAllPorts) /*-{
    this.efParams.shouldWriteAllPorts = shouldWriteAllPorts;
  }-*/;

}

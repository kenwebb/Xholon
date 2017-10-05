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

package org.primordion.ef.bigraph;

import java.util.Date;
import java.util.List;

import org.primordion.xholon.base.IMechanism;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.base.XhRelTypes;
//import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.service.ef.IXholon2GraphFormat;
//import org.primordion.xholon.util.MiscIo;

/**
 * Export a Xholon model in Big Red format.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 4, 2017)
 * @see <a href="https://github.com/ale-f/big-red">Big Red website</a>
 */
@SuppressWarnings("serial")
public class Xholon2BigRed extends AbstractXholon2ExternalFormat implements IXholon2GraphFormat, IXmlWriter {
  
  private String outFileName;
  private String outPath = "./ef/bigraph/";
  private String modelName;
  private String normalizedModelName;
  private IXholon root;
  protected StringBuilder sb;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  /** Whether or not to draw links between nodes. */
  private boolean shouldShowLinks = true;
  
  /** Whether or not to include an id on edges. */
  private boolean shouldShowEdgeId = true;
  
  /** Whether or not to show state machine nodes. */
  private boolean shouldShowStateMachineEntities = false;
  
  /** Template to use when writing out node names. */
  protected String nameTemplate = IXholon.GETNAME_DEFAULT; //"r:C^^^";
  
  /** The next edge id, used when writing an &lt;edge> element. */
  protected int nextEdgeId = 0;

  /** Big Red files are XML, but do not actually include the ".xml" extension */
  protected String fileNameExtension = ".xml";
  
  /** Whether or not to close "out" once everything is written. */
  protected boolean shouldClose = false;
  
  /**
   * The accumulating text for all GraphML node elements.
   */
  protected StringBuilder nodeSb = null;
  
  /**
   * The accumulating text for all GraphML edge elements.
   */
  protected StringBuilder edgeSb = null;
  
  /** Big Red spec XML */
  protected StringBuilder specSb = null;
  
  /** Big Red signature XML */
  protected StringBuilder sigSb = null;
  
  /** Big Red bigraph XML */
  protected StringBuilder bigraphSb = null;
  
  /**
   * Whether or not to actually write the output to some target such as a file or browser tab.
   */
  protected boolean shouldWriteToTarget = true;
  
  /**
   * Whether or not to write an attribute with the name "Val".
   */
  private boolean shouldWriteVal = false;
  
  /**
   * Whether or not to write an attribute with the name "AllPorts".
   */
  private boolean shouldWriteAllPorts = false;
  
  private String bigraphRootName = "0";
  
  private String attributeIndent = "";
  
  /**
   * Constructor.
   */
  public Xholon2BigRed() {}
  
  @Override
  public String getVal_String() {
    return sb.toString();
  }
  
  /*
   * @see org.primordion.xholon.io.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
   */
  public boolean initialize(String outFileName, String modelName, IXholon root) {
    timeNow = new Date();
    timeStamp = timeNow.getTime();
    if (outFileName == null) {
      this.outFileName = outPath + root.getXhcName() + "_" + root.getId()
        + "_" + timeStamp + fileNameExtension;
    }
    else {
      this.outFileName = outFileName;
    }
    this.modelName = modelName;
    this.normalizedModelName = normalizeModelName(this.modelName);
    this.root = root;
    
    this.nodeSb = new StringBuilder();
    this.edgeSb = new StringBuilder();
    
    this.specSb = new StringBuilder();
    this.sigSb = new StringBuilder();
    this.bigraphSb = new StringBuilder();
    
    return true;
  }
  
  /*
   * @see org.primordion.xholon.io.IXholon2ExternalFormat#writeAll()
   */
  public void writeAll() {
    writeSpec();
    this.writeSigStart();
    
    StringBuilder commentSb = new StringBuilder()
    .append("<!--")
    .append("\nTo view this file, download open-source Big Red from https://github.com/ale-f/big-red\n")
    .append("\nAutomatically generated by Xholon version 0.9.1, using Xholon2BigRed.java\n")
    .append(new Date())
    .append(" ")
    .append(timeStamp)
    .append("\n")
    .append("model: ")
    .append( this.modelName)
    .append("\n")
    .append("www.primordion.com/Xholon\n")
    .append("-->\n");
    this.writeBigraphStart(commentSb.toString());
    
    sb = new StringBuilder();
    
    nodeSb.append("  <bigraph:root name=\"" + bigraphRootName + "\">\n");
    writeNode(root);
    nodeSb.append("  </bigraph:root>\n");
    
    sb.append(edgeSb.toString());
    sb.append(nodeSb.toString());
    this.writeEndDocument();
    this.writeSigControls(root.getApp().getXhcRoot());
    this.writeSigEnd();
    this.bigraphSb.append(sb.toString());
    this.writeBigraphEnd();
    if (shouldWriteToTarget) {
      writeToTarget(this.specSb.toString(), outFileName, outPath, root);
      writeToTarget(this.sigSb.toString(), outFileName, outPath, root);
      writeToTarget(this.bigraphSb.toString(), outFileName, outPath, root);
    }
  }
  
  /**
   * Write Big Red spec.
   */
  protected void writeSpec() {
    this.specSb
    .append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n")
    .append("<spec:spec xmlns:spec=\"http://www.itu.dk/research/pls/xmlns/2012/simulation-spec\">\n")
    .append("  <signature:signature src=\"signatures/"
        + this.normalizedModelName + ".bigraph-signature\" xmlns:signature=\"http://www.itu.dk/research/pls/xmlns/2010/signature\"/>\n")
    .append("  <!-- rules -->\n")
    .append("  <bigraph:bigraph src=\"agents/"
        + this.normalizedModelName + ".bigraph-agent\" xmlns:bigraph=\"http://www.itu.dk/research/pls/xmlns/2010/bigraph\"/>\n")
    .append("</spec:spec>\n")
    ;
  }
  
  /**
   * Write start of Big Red signature.
   */
  protected void writeSigStart() {
    this.sigSb
    .append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n")
    .append("<signature:signature xmlns:signature=\"http://www.itu.dk/research/pls/xmlns/2010/signature\">\n")
    ;
  }
  
  /**
   * Write signature controls to sigSb, by recursively working through the entire Xholon IH.
   */
  protected void writeSigControls(IXholonClass xhcNode) {
    if (hasSig(xhcNode)) {
      String xhcNodeName = xhcNode.getName();
      this.sigSb
      .append("  <signature:control xmlns:big-red=\"http://www.itu.dk/research/pls/xmlns/2010/big-red\" big-red:label=\""
          + makeAbbreviation(xhcNodeName) + "\" kind=\"active\" name=\"" + xhcNodeName + "\">\n")
      ;
      String ports = obtainSigControlPorts(xhcNode);
      if ((ports != null) && (ports.length() > 0)) {
        // write ports to sigSb
        String[] portsArr = ports.split(",");
        for (int i = 0; i < portsArr.length; i++) {
          String portName = portsArr[i];
          this.sigSb
          .append("    <signature:port name=\"" + portName + "\">\n")
          .append("    </signature:port>\n")
          ;
        }
      }
      this.sigSb
      .append("  </signature:control>\n")
      ;
    }
    // handle children recursively
    IXholonClass childXhcNode = (IXholonClass)xhcNode.getFirstChild();
    while (childXhcNode != null) {
      writeSigControls(childXhcNode);
      childXhcNode = (IXholonClass)childXhcNode.getNextSibling();
    }
  }
  
  /**
   * Make an abbreviation of a name.
   * ex: "HelloWorldSystem" -> "HWS"
   */
  protected String makeAbbreviation(String inStr) {
    String outStr = "";
    for (int i = 0; i < inStr.length(); i++) {
      char c = inStr.charAt(i);
      if ((c >= 'A') && (c <= 'Z')) {
        outStr += c;
      }
    }
    if (outStr.length() == 0) {
      outStr = inStr.substring(0,1).toUpperCase();
    }
    return outStr;
  }
  
  /**
   * Try to obtain the list of signature control ports for the specified IXholonClass.
   * return true or false.
   */
  protected native boolean hasSig(IXholonClass xhcNode) /*-{
    if (xhcNode.signature) {
      return true;
    }
    else {
      return false;
    }
  }-*/;
  
  /**
   * Try to obtain the list of signature control ports for the specified IXholonClass.
   * return A comma-delimited String, or null.
   */
  protected native String obtainSigControlPorts(IXholonClass xhcNode) /*-{
    if (xhcNode.signature && xhcNode.signature.ports) {
      return xhcNode.signature.ports.toString();
    }
    else {
      return null;
    }
  }-*/;
  
  /**
   * Make and initialize a JavaScript signature object within the xhNode's Xholon Class.
   * If there is already a signature object, then do nothing.
   */
  protected native void makeSigControl(IXholon xhNode) /*-{
    var xhcNode = xhNode.xhc();
    if (!xhcNode.signature) {
      xhcNode.signature = {};
      xhcNode.signature.ports = [];
    }
  }-*/;
  
  /**
   * Add a port to the JavaScript signature object within the xhNode's Xholon Class.
   */
  protected native void addPortToSigControl(IXholon xhNode, String portName) /*-{
    var xhcNode = xhNode.xhc();
    if (xhcNode.signature && xhcNode.signature.ports && (xhcNode.signature.ports.indexOf(portName) == -1)) {
      xhcNode.signature.ports.push(portName);
    }
  }-*/;
  
  /**
   * Write end of Big Red signature.
   */
  protected void writeSigEnd() {
    this.sigSb
    .append("</signature:signature>\n")
    ;
  }
  
  /**
   * Write start of Big Red bigraph agent XML.
   */
  protected void writeBigraphStart(String commentStr) {
    this.bigraphSb
    .append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n")
    .append(commentStr)
    .append("<bigraph:bigraph xmlns:bigraph=\"http://www.itu.dk/research/pls/xmlns/2010/bigraph\">\n")
    .append("  <signature:signature src=\"../signatures/"
        + this.normalizedModelName + ".bigraph-signature\" xmlns:signature=\"http://www.itu.dk/research/pls/xmlns/2010/signature\"/>\n")
    ;
  }
  
  /**
   * Write end of Big Red bigraph agent XML.
   */
  protected void writeBigraphEnd() {
    this.bigraphSb
    .append("</bigraph:bigraph>\n")
    ;
  }
  
  @Override
  public void writeNode(IXholon xhNode) {
    this.writeNode(xhNode, "    ");
  }
  
  protected void writeNode(IXholon xhNode, String indent) {
    // only show state machine nodes if should show them, or if root is a StateMachineCE
    if ((xhNode.getXhcId() == CeStateMachineEntity.StateMachineCE)
        && (shouldShowStateMachineEntities == false)
        && (xhNode != root)) {
      return;
    }
    
    this.makeSigControl(xhNode);
    
    nodeSb.append(indent).append("<bigraph:node id=\"")
    .append(xhNode.getId())
    .append("\" label=\"")
    .append(xhNode.getName(nameTemplate))
    .append("\">\n");
    this.attributeIndent = indent + "  ";
    writeNodeAttributes(xhNode);
    this.attributeIndent = "";
    writeEdges(xhNode);
    if (xhNode.hasChildNodes()) {
      IXholon childNode = xhNode.getFirstChild();
      while (childNode != null) {
        writeNode(childNode, indent + "  ");
        childNode = childNode.getNextSibling();
      }
    }
    nodeSb.append(indent).append("</bigraph:node>\n");
  }
  
  @Override
  public void writeEdges(IXholon xhNode) {
    makeLinks(xhNode);
  }

  @Override
  public void writeNodeAttributes(IXholon xhNode) {
    IXholon2Xml xholon2xml = xhNode.getXholon2Xml();
    xholon2xml.setXhAttrStyle(IXholon2Xml.XHATTR_TO_XMLATTR);
    xhNode.toXmlAttributes(xholon2xml, this);
  }
  
  /**
   * Write links from this node to any others, where Xholon has connected ports.
   * @param node The current node.
   */
  @SuppressWarnings("unchecked")
  protected void makeLinks(IXholon node)
  {
    if (!shouldShowLinks) {return;}
    List<PortInformation> portList = node.getLinks(false, true);
    for (int i = 0; i < portList.size(); i++) {
      makeLink(node, portList.get(i));
    }
  }
  
  /**
   * Write one link.
   * @param node The node where the link originates.
   * @param portInfo Information about the port that represents the link.
   */
  protected void makeLink(IXholon node, PortInformation portInfo)
  {
    if (portInfo == null) {return;}
    
    this.addPortToSigControl(node, portInfo.getLocalNameNoBrackets());
    
    IXholon remoteNode = portInfo.getReffedNode();
    edgeSb.append("  <bigraph:edge");
    if (shouldShowEdgeId) {
      edgeSb.append(" id=\"")
      .append(getNextEdgeIdStr())
      .append("\"");
    }
    edgeSb.append(" source=\"")
    .append(node.getId())
    .append("\" target=\"")
    .append(remoteNode.getId())
    .append("\" label=\"")
    .append(portInfo.getLocalName())
    .append("\"/>\n");
  }
  
  /**
   * Write one link.
   * @param label
   * @param source
   * @param target
   */
  protected void makeLink(String label, IXholon source, IXholon target) {
    edgeSb.append("  <bigraph:edge");
    if (shouldShowEdgeId) {
      edgeSb.append(" id=\"")
      .append(getNextEdgeIdStr())
      .append("\"");
    }
    edgeSb.append(" source=\"")
    .append(source.getId())
    .append("\" target=\"")
    .append(target.getId())
    .append("\" label=\"")
    .append(label)
    .append("\"/>\n");
  }
  
  /**
   * Remove spaces and other special characters from modelName,
   * so it can be used as part of a file name in the Big Red XML text.
   */
  protected String normalizeModelName(String inModelName) {
    String outModelName = ""; //inModelName;
    for (int i = 0; i < inModelName.length(); i++) {
      char c = inModelName.charAt(i);
      if (((c >= 'A') && (c <= 'Z')) || ((c >= 'a') && (c <= 'z')) || ((c >= '0') && (c <= '9')) || (c == '_')) {
        outModelName += c;
      }
    }
    return outModelName;
  }

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

  public boolean isShouldShowLinks() {
    return shouldShowLinks;
  }

  public void setShouldShowLinks(boolean shouldShowLinks) {
    this.shouldShowLinks = shouldShowLinks;
  }

  public boolean isShouldShowStateMachineEntities() {
    return shouldShowStateMachineEntities;
  }

  public void setShouldShowStateMachineEntities(
      boolean shouldShowStateMachineEntities) {
    this.shouldShowStateMachineEntities = shouldShowStateMachineEntities;
  }

  public String getNameTemplate() {
    return nameTemplate;
  }

  public void setNameTemplate(String nameTemplate) {
    this.nameTemplate = nameTemplate;
  }
  
  public int getNextEdgeId() {
    return nextEdgeId++;
  }
  
  /**
   * Get the next edge id as a String.
   * @return
   */
  public String getNextEdgeIdStr() {
    return "e" + getNextEdgeId();
  }

  public void setNextEdgeId(int nextEdgeId) {
    this.nextEdgeId = nextEdgeId;
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
    
  }

  @Override
  public void writeEndDocument() {
    
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
  // All attributes are type="string".
  public void writeAttribute(String name, String value) {
    if ("Val".equalsIgnoreCase(name) && !isShouldWriteVal()) {return;}
    if ("AllPorts".equalsIgnoreCase(name) && !isShouldWriteAllPorts()) {return;}
    this.nodeSb
    .append(this.attributeIndent)
    .append("<!--<att name=\"")
    .append(name)
    .append("\" value=\"")
    .append(value)
    .append("\"/>-->\n");
  }

  @Override
  // DO NOT IMPLEMENT THIS
  public void writeText(String text) {}

  @Override
  public void writeComment(String data) {
    //sb.append("<!--" + data + "-->\n");
  }

  @Override
  public String getWriterName() {
    return "Xholon2BigRed";
  }

  @Override
  // DO NOT IMPLEMENT THIS
  public void flush() {}
  
  /* 
   * @see org.primordion.xholon.io.xml.IXmlWriter#isShouldWriteVal()
   */
  public boolean isShouldWriteVal() {
    return shouldWriteVal;
  }
  
  /* 
   * @see org.primordion.xholon.io.xml.IXmlWriter#setShouldWriteVal()
   */
  public void setShouldWriteVal(boolean shouldWriteVal) {
    this.shouldWriteVal = shouldWriteVal;
  }
  
  /* 
   * @see org.primordion.xholon.io.xml.IXmlWriter#isShouldWriteAllPorts()
   */
  public boolean isShouldWriteAllPorts() {
    return shouldWriteAllPorts;
  }
  
  /* 
   * @see org.primordion.xholon.io.xml.IXmlWriter#shouldWriteAllPorts()
   */
  public void setShouldWriteAllPorts(boolean shouldWriteAllPorts) {
    this.shouldWriteAllPorts = shouldWriteAllPorts;
  }

  public boolean isShouldShowEdgeId() {
    return shouldShowEdgeId;
  }

  public void setShouldShowEdgeId(boolean shouldShowEdgeId) {
    this.shouldShowEdgeId = shouldShowEdgeId;
  }

  public String getFileNameExtension() {
    return fileNameExtension;
  }

  public void setFileNameExtension(String fileNameExtension) {
    this.fileNameExtension = fileNameExtension;
  }

}

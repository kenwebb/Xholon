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

package org.primordion.ef.schema;

import java.util.Date;
import java.util.List;

import org.primordion.xholon.base.IMechanism;
import org.primordion.xholon.base.IReflection;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.base.ReflectionFactory;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.util.ClassHelper;
import org.primordion.xholon.util.IJavaTypes;
import org.primordion.xholon.util.Misc;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Export a Xholon model to W3C XML Schema language.
 * Write out schema types and elements (Xholon IH and CSH).
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://www.primordion.com/Xholon/gwt/wb/editwb.html?app=224ffd51902dfdebfa4b&src=gist">XML Schemas XholonWorkbook</a>
 * @since 0.9.1 (Created on April 12, 2015)
 */
@SuppressWarnings("serial")
public class Xholon2XmlSchema extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat, IXmlWriter {
  
  private static final String XSD_PREFIX = "xs"; // "xsd"
  
  private String outFileName;
  private String outPath = "./ef/xmlschema/";
  private String modelName;
  private IXholon root;
  private StringBuilder sb;
  private StringBuilder sbAttrs;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  /** Use GWT reflection to get node attributes. */
  private IReflection ir = null;
  
  /*private String nodeIdBoolean = null; // 00f4ada[Attribute_boolean]
  private String nodeIdByte = null; // 00f4adb[Attribute_byte]
  private String nodeIdChar = null; // 00f4adc[Attribute_char]
  private String nodeIdDouble = null; // 00f4add[Attribute_double]
  private String nodeIdFloat = null; // 00f4ade[Attribute_float]
  private String nodeIdInt = null; // 00f4adf[Attribute_int]
  private String nodeIdLong = null; // 00f4ae0[Attribute_long]
  private String nodeIdShort = null; // 00f4ae2[Attribute_short]
  private String nodeIdString = null; // 00f4ae3[Attribute_String]*/
  
  /** use this for optional indent on each line */
  private String indent = "                                                                      ";
  
  /**
   * Constructor.
   */
  public Xholon2XmlSchema() {}
  
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
      this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".xsd";
    }
    else {
      this.outFileName = fileName;
    }
    this.modelName = modelName;
    this.root = root;
    /*nodeIdBoolean = node2HexId(getIhPrefix(), root.getClassNode("Attribute_boolean").getId());
    nodeIdByte    = node2HexId(getIhPrefix(), root.getClassNode("Attribute_byte").getId());
    nodeIdChar    = node2HexId(getIhPrefix(), root.getClassNode("Attribute_char").getId());
    nodeIdDouble  = node2HexId(getIhPrefix(), root.getClassNode("Attribute_double").getId());
    nodeIdFloat   = node2HexId(getIhPrefix(), root.getClassNode("Attribute_float").getId());
    nodeIdInt     = node2HexId(getIhPrefix(), root.getClassNode("Attribute_int").getId());
    nodeIdLong    = node2HexId(getIhPrefix(), root.getClassNode("Attribute_long").getId());
    nodeIdShort   = node2HexId(getIhPrefix(), root.getClassNode("Attribute_short").getId());
    nodeIdString  = node2HexId(getIhPrefix(), root.getClassNode("Attribute_String").getId());
    root.consoleLog(nodeIdString + " " + nodeIdInt + " " + nodeIdBoolean);*/
    return true;
  }
  
  /*
   * @see org.primordion.xholon.io.IXholon2ExternalFormat#writeAll()
   <xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema">
   </xsd:schema>
   */
  public void writeAll() {
    ir = ReflectionFactory.instance();
    sb = new StringBuilder();
    //sb.append(getBracketOpen()).append(getBracketOpen());
    sb.append("<").append(XSD_PREFIX).append(":schema xmlns:")
    .append(XSD_PREFIX)
    .append("=\"http://www.w3.org/2001/XMLSchema\">\n");
    writeXsdAnnotation();
    if (isShouldUseTypes()) {
      writeIhNode(root.getApp().getXhcRoot());
    }
    //sb.append(getBracketClosed());
    //sb.append(getBracketOpen());
    writeNode(root, 0); // root is level 0
    //sb.append(getBracketClosed()).append(getBracketClosed());
    sb.append("</").append(XSD_PREFIX).append(":schema>\n");
    setWriteToTab(isWriteToNewTab());
    writeToTarget(sb.toString(), outFileName, outPath, root);
  }
  
  /**
   * Write an XSD annotation at the top of the document.
   <xsd:annotation>
    <xsd:documentation xml:lang="en">
     Purchase order schema for Example.com.
     Copyright 2000 Example.com. All rights reserved.
    </xsd:documentation>
  </xsd:annotation>
   */
  protected void writeXsdAnnotation() {
    sb
    .append(" <").append(XSD_PREFIX).append(":annotation>\n")
    .append("  <").append(XSD_PREFIX).append(":documentation xml:lang=\"en\">\n")
    .append("Automatically generated by Xholon version 0.9.1, using ")
		.append(this.getClass().getName())
		.append(".java\n")
		.append(new Date())
		.append(" ")
		.append(timeStamp)
		.append("\n")
		.append("model: ")
		.append(modelName)
		.append("\n")
		.append("www.primordion.com/Xholon/gwt/\n")
    .append("  </")
    .append(XSD_PREFIX)
    .append(":documentation>\n")
    .append(" </")
    .append(XSD_PREFIX)
    .append(":annotation>\n")
    ;
  }
  
  /**
   * Write one node, and its child nodes.
   * @param node The current node in the Xholon composite structure hierarchy (CSH).
   * @param level 
  <xs:element name="HelloWorldSystem">
    <xs:complexType>
      <xs:sequence>
        <xs:element name="Hello"/>
        <xs:element name="World"/>
      </xs:sequence>
    </xs:complexType>
  </xs:element>
   * TODO optionally include minOccurs and maxOccurs
   */
  protected void writeNode(IXholon node, int level) {
    if (isShouldInsertNewlines()) {sb.append("\n");}
    String tab = "";
    if (isShouldPrettyPrint()) {
      tab = indent;
      if (level < indent.length()) {
        tab = indent.substring(0, level+1);
      }
    }
    sb
    .append(tab)
    .append("<")
    .append(XSD_PREFIX)
    .append(":element name=\"")
    .append(node.getName(getNameTemplate()))
    .append("\"");
    if (getMinOccurs().length() != 0) {
      sb
      .append(" minOccurs=\"")
      .append(getMinOccurs())
      .append("\"");
    }
    if (getMaxOccurs().length() != 0) {
      sb
      .append(" maxOccurs=\"")
      .append(getMaxOccurs())
      .append("\"");
    }
    writeLinks(node);
    writeAttributes(node);
    if (node.hasChildNodes()) {
      sb
      .append(">\n")
      .append(tab + " ")
      .append("<")
      .append(XSD_PREFIX)
      .append(":complexType>\n")
      .append(tab + "  ")
      .append("<")
      .append(XSD_PREFIX)
      .append(":sequence>\n");
      IXholon childNode = node.getFirstChild();
      while (childNode != null) {
        writeNode(childNode, level + 3);
        childNode = childNode.getNextSibling();
      }
      sb
      .append(tab + "  ")
      .append("</")
      .append(XSD_PREFIX)
      .append(":sequence>\n")
      .append(tab + " ")
      .append("</")
      .append(XSD_PREFIX)
      .append(":complexType>\n")
      .append(tab)
      .append("</")
      .append(XSD_PREFIX)
      .append(":element>\n");
    }
    else {
      sb.append("/>\n");
    }
  }
  
  /**
   * Write an inheritance hierarchy (IH) node, and its child nodes.
   * Optionally only include app-specific IH nodes.
   * @param xhcNode The current node in the hierarchy.
   <xsd:complexType name="PurchaseOrderType">
    <xsd:sequence>
      <xsd:element name="shipTo" type="USAddress"/>
      <xsd:element name="billTo" type="USAddress"/>
      <xsd:element ref="comment" minOccurs="0"/>
      <xsd:element name="items"  type="Items"/>
    </xsd:sequence>
    <xsd:attribute name="orderDate" type="xsd:date"/>
  </xsd:complexType>
   */
  protected void writeIhNode(IXholon xhcNode) {
    if (isShouldShowMechanismIhNodes() || (xhcNode.getId() < IMechanism.MECHANISM_ID_START)) {
      if (isShouldInsertNewlines()) {sb.append("\n");}
      //sb.append(node2HexId(getIhPrefix(), xhcNode.getId()));
      sb
      //.append(getTextBracketOpen())
      .append("<").append(XSD_PREFIX).append(":complexType name=\"")
      .append(xhcNode.getName())
      .append("\">\n")
      //.append(getTextBracketClosed())
      .append("</").append(XSD_PREFIX).append(":complexType>\n")
      ;
      if (xhcNode.hasChildNodes()) {
        //sb.append(getBracketOpen());
        IXholon childXhcNode = xhcNode.getFirstChild();
        while (childXhcNode != null) {
          writeIhNode(childXhcNode);
          childXhcNode = childXhcNode.getNextSibling();
        }
        //sb.append(getBracketClosed());
      }
    }
    else {
      // this is a mechanism node that should not be shown, but should still be navigated
      if (xhcNode.hasChildNodes()) {
        IXholon childXhcNode = xhcNode.getFirstChild();
        while (childXhcNode != null) {
          writeIhNode(childXhcNode);
          childXhcNode = childXhcNode.getNextSibling();
        }
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
    //sb.append(getPortSymbol()).append(node2HexId(getCshPrefix(), remoteNode.getId()));
  }
  
  /**
   * Write app-specific attributes.
   * Example:
   *   ([pheneVal=9876.5432]>00f4add[myString=this is a string]>00f4ae3[myBool=true]>00f4ada)
   * @param node The node whose attributes should be written.
   */
  public void writeAttributes(IXholon node) {
    if (isShouldShowAttributes() == false) {return;}
    sbAttrs = new StringBuilder();
    IXholon2Xml xholon2xml = node.getXholon2Xml();
    xholon2xml.setXhAttrStyle(IXholon2Xml.XHATTR_TO_XMLATTR);
    node.toXmlAttributes(xholon2xml, this);
    if (sbAttrs.length() > 0) {
      sb
      //.append(getBracketOpen())
      .append(sbAttrs.toString())
      //.append(getBracketClosed())
      ;
    }
  }
  
  /**
   * Convert an integer to a lowercase hex string.
   * The result should be padded with leading zeros.
   * @param prefix A string to prepend to the result.
   * @param nodeId A node ID.
   */
  /*protected String node2HexId(String prefix, int nodeId) {
    String hexString = Integer.toHexString(nodeId); //.toUpperCase();
    while (hexString.length() < getHexStrLen()) {
      hexString = getPadCharacter() + hexString;
    }
    return prefix + hexString;
  }*/
  
  /**
   * Make a JavaScript object with all the parameters for this external format.
   */
  protected native void makeEfParams() /*-{
    var p = {};
    //p.bracketOpen = "(";
    //p.bracketClosed = ")";
    //p.textBracketOpen = "[";
    //p.textBracketClosed = "]";
    //p.portSymbol = ">";
    //p.ihPrefix = "0";
    //p.cshPrefix = "1";
    //p.padCharacter = "0";
    //p.hexStrLen = 6;
    p.nameTemplate = "R^^^^^";
    p.minOccurs = ""; // 0 1 2
    p.maxOccurs = ""; // 0 1 3 unbounded
    p.shouldUseTypes = false;
    p.shouldInsertNewlines = false;
    p.shouldShowLinks = false;
    p.shouldShowAttributes = false;
    p.shouldShowMechanismIhNodes = false;
    p.shouldWriteVal = false;
    p.shouldWriteAllPorts = false;
    p.writeToNewTab = true;
    p.shouldPrettyPrint = true;
    this.efParams = p;
  }-*/;

  /** Open bracket for containment. */
  //public native String getBracketOpen() /*-{return this.efParams.bracketOpen;}-*/;
  //public native void setBracketOpen(String bracketOpen) /*-{this.efParams.bracketOpen = bracketOpen;}-*/;

  /** Closed bracket for containment. */
  //public native String getBracketClosed() /*-{return this.efParams.bracketClosed;}-*/;
  //public native void setBracketClosed(String bracketClosed) /*-{this.efParams.bracketClosed = bracketClosed;}-*/;

  /** Open bracket for text. */
  //public native String getTextBracketOpen() /*-{return this.efParams.textBracketOpen;}-*/;
  //public native void setTextBracketOpen(String textBracketOpen) /*-{this.efParams.textBracketOpen = textBracketOpen;}-*/;

  /** Closed bracket for text. */
  //public native String getTextBracketClosed() /*-{return this.efParams.textBracketClosed;}-*/;
  //public native void setTextBracketClosed(String textBracketClosed) /*-{this.efParams.textBracketClosed = textBracketClosed;}-*/;

  /** Symbol indicating that the following ID is referenced by a port. */
  //public native String getPortSymbol() /*-{return this.efParams.portSymbol;}-*/;
  //public native void setPortSymbol(String portSymbol) /*-{this.efParams.portSymbol = portSymbol;}-*/;

  /** Prefix to ensure that IH IDs are unique among all IH and CSH IDs.e */
  //public native String getIhPrefix() /*-{return this.efParams.ihPrefix;}-*/;
  //public native void setIhPrefix(String ihPrefix) /*-{this.efParams.ihPrefix = ihPrefix;}-*/;

  /** Prefix to ensure that CSH IDs are unique among all IH and CSH IDs. */
  //public native String getCshPrefix() /*-{return this.efParams.cshPrefix;}-*/;
  //public native void setCshPrefix(String cshPrefix) /*-{this.efParams.cshPrefix = cshPrefix;}-*/;

  /** Character to use when left-padding the hex string. */
  //public native String getPadCharacter() /*-{return this.efParams.padCharacter;}-*/;
  //public native void setPadCharacter(String padCharacter) /*-{this.efParams.padCharacter = padCharacter;}-*/;

  /** Length of hex string ID, before prefixing it. */
  //public native int getHexStrLen() /*-{return this.efParams.hexStrLen;}-*/;
  //public native void setHexStrLen(int hexStrLen) /*-{this.efParams.hexStrLen = hexStrLen;}-*/;

  /** Xholon name template. */
  public native String getNameTemplate() /*-{return this.efParams.nameTemplate;}-*/;
  //public native void setNameTemplate(String nameTemplate) /*-{this.efParams.nameTemplate = nameTemplate;}-*/;

  /** Minimum number of occurences. */
  public native String getMinOccurs() /*-{return this.efParams.minOccurs;}-*/;
  //public native void setMinOccurs(String minOccurs) /*-{this.efParams.minOccurs = minOccurs;}-*/;

  /** Maximum number of occurences. */
  public native String getMaxOccurs() /*-{return this.efParams.maxOccurs;}-*/;
  //public native void setMaxOccurs(String maxOccurs) /*-{this.efParams.maxOccurs = maxOccurs;}-*/;

  /**
   * Whether or not to use simpleType/complexType instead of just using element.
   */
  public native boolean isShouldUseTypes() /*-{return this.efParams.shouldUseTypes;}-*/;
  //public native void setShouldUseTypes(boolean shouldUseTypes) /*-{this.efParams.shouldUseTypes = shouldUseTypes;}-*/;

  /**
   * Whether or not to format the output by inserting new lines.
   * This can be used for debugging.
   */
  public native boolean isShouldInsertNewlines() /*-{return this.efParams.shouldInsertNewlines;}-*/;
  //public native void setShouldInsertNewlines(boolean shouldInsertNewlines) /*-{this.efParams.shouldInsertNewlines = shouldInsertNewlines;}-*/;

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
    
  /**
   * Whether or not to pretty print.
   */
  public native boolean isShouldPrettyPrint() /*-{return this.efParams.shouldPrettyPrint;}-*/;
  //public native void setShouldPrettyPrint(boolean shouldPrettyPrint) /*-{this.efParams.shouldPrettyPrint = shouldPrettyPrint;}-*/;
    
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
  public void writeStartDocument() {}

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
    sbAttrs
    //.append(getTextBracketOpen())
    .append(name)
    .append("=")
    .append(value)
    //.append(getTextBracketClosed())
    //.append(getPortSymbol())
    ;
    /*String ihId = nodeIdString;
    switch(Misc.getJavaDataType(value)) {
    case IJavaTypes.JAVACLASS_UNKNOWN: break;
    case IJavaTypes.JAVACLASS_int: ihId = nodeIdInt; break;
    case IJavaTypes.JAVACLASS_long: ihId = nodeIdLong; break;
    case IJavaTypes.JAVACLASS_double: ihId = nodeIdDouble; break;
    case IJavaTypes.JAVACLASS_float: ihId = nodeIdFloat; break;
    case IJavaTypes.JAVACLASS_boolean: ihId = nodeIdBoolean; break;
    case IJavaTypes.JAVACLASS_String: break;
    case IJavaTypes.JAVACLASS_byte: ihId = nodeIdByte; break;
    case IJavaTypes.JAVACLASS_char: ihId = nodeIdChar; break;
    case IJavaTypes.JAVACLASS_short: ihId = nodeIdShort; break;
    default: break;
    }
    sbAttrs.append(ihId);*/
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

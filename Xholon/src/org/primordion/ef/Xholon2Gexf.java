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

package org.primordion.ef;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.primordion.xholon.base.IMechanism;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.base.XhRelTypes;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.service.ef.IXholon2GraphFormat;

/**
 * Export a Xholon model in GEXF format.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on April 24, 2017)
 * @see <a href="https://gephi.org/gexf/format/index.html">GEXF website</a>
 */
@SuppressWarnings("serial")
public class Xholon2Gexf extends AbstractXholon2ExternalFormat implements IXholon2GraphFormat, IXmlWriter {
  
  // A GEXF key element requires an "type" XML attribute.
  protected static final String KEY_ATTR_TYPE_BOOLEAN = "boolean";
  protected static final String KEY_ATTR_TYPE_INT = "int";
  protected static final String KEY_ATTR_TYPE_LONG = "long";
  protected static final String KEY_ATTR_TYPE_FLOAT = "float";
  protected static final String KEY_ATTR_TYPE_DOUBLE = "double";
  protected static final String KEY_ATTR_TYPE_STRING = "string";
  
  // A GEXF graph element requires an "edgedefault" XML attribute.
  // Xholon edges are all "directed"
  protected static final String GRAPH_EDGEDEFAULT_DIRECTED = "directed";
  protected static final String GRAPH_EDGEDEFAULT_UNDIRECTED = "undirected";
  
  /** StringBuilder/Buffer initial capacity. */
  protected static final int SB_INITIAL_CAPACITY = 1000; // default is 16
    
  // GraphML tools (readers, editors) - each supports different features
  /** base GraphML capabilities (primer up to 2.4.3) */
  protected static final int TOOL_GENERIC = 0;
  /** Gephi 0.8.2-beta */
  protected static final int TOOL_GEPHI = 1;
  /** yed 3.9.2 */
  protected static final int TOOL_YED = 2;
  protected static final int TOOL_BLUEPRINTS = 3;
  /** C++ Boost Graphics Library */
  protected static final int TOOL_BOOST = 4;
  /** JUNG 1.7.6 */
  protected static final int TOOL_JUNG = 5;
  protected static final int TOOL_NWB = 6;
  protected static final int TOOL_NETWORKX = 7;
  /** Cytoscape 3.0 */
  protected static final int TOOL_CYTOSCAPE = 8;
  protected int targetTool = TOOL_GEPHI;
  
  protected static final String INDENT_UNIT = " ";
  
  private String outFileName;
  private String outPath = "./ef/gexf/";
  private String modelName;
  private IXholon root;
  //private Writer out;
  private StringBuilder sb;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  /**
   * The accumulating text for all GEXF node elements.
   */
  protected StringBuilder nodeSb = null;
  
  /**
   * The accumulating text for all GEXF edge elements.
   */
  protected StringBuilder edgeSb = null;
  
  /**
   * Set of all node key id's that will be added to the GEXF content.
   * This is used to determine if a key has already been added to the keyNodeSb.
   */
  protected Set<String> keyNodeSet = null;
  
  /**
   * The accumulating text of all key elements for GEXF nodes.
   */
  protected StringBuilder keyNodeSb = null;
  
  /**
   * Set of all edge key id's that will be added to the GEXF content.
   * This is used to determine if a key has already been added to the keyEdgeSb.
   */
  protected Set<String> keyEdgeSet = null;
  
  /**
   * The accumulating text of all key elements for GEXF edges.
   */
  protected StringBuilder keyEdgeSb = null;
  
  /**
   * Used before call to writeNodeAttributes()
   */
  protected String currentNodeIndent = "";
  
  /**
   * Constructor.
   */
  public Xholon2Gexf() {}
  
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
      this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_"
          + timeStamp + getFileNameExtension();
    }
    else {
      this.outFileName = outFileName;
    }
    this.modelName = modelName;
    this.root = root;
    
    this.setFeatures(getTargetTool());
    this.nodeSb = new StringBuilder(SB_INITIAL_CAPACITY);
    this.edgeSb = new StringBuilder(SB_INITIAL_CAPACITY);
    this.keyNodeSet = new HashSet<String>();
    this.keyNodeSb = new StringBuilder(SB_INITIAL_CAPACITY);
    this.keyEdgeSet = new HashSet<String>();
    this.keyEdgeSb = new StringBuilder(SB_INITIAL_CAPACITY);
    
    return true;
  }
  
  /*
   * @see org.primordion.xholon.io.IXholon2ExternalFormat#writeAll()
   */
  public void writeAll() {
    sb = new StringBuilder();
    
    this.writeStartDocument();
    
    this.makeEdgeKey("fieldName", "fieldName", KEY_ATTR_TYPE_STRING, null, " ");
    if (isShowSubtreeRoot()) {
      writeNode(root, INDENT_UNIT);
    }
    else {
      // write children of root, if any
      IXholon childNode = root.getFirstChild();
      while (childNode != null) {
        writeNode(childNode, INDENT_UNIT);
        childNode = childNode.getNextSibling();
      }
    }
    if (keyNodeSb.length() > 0) {
      sb.append("<attributes class=\"node\">\n").append(keyNodeSb.toString()).append("</attributes>\n");
    }
    if (keyEdgeSb.length() > 0) {
      sb.append("<attributes class=\"edge\">\n").append(keyEdgeSb.toString()).append("</attributes>\n");
    }
    sb.append("<nodes>\n").append(nodeSb.toString()).append("</nodes>\n");
    sb.append("<edges>\n").append(edgeSb.toString()).append("</edges>\n");
    this.writeEndDocument();
    writeToTarget(sb.toString(), outFileName, outPath, root);
  }
    
  @SuppressWarnings("unchecked")
  @Override
  public void writeNode(IXholon xhNode) {}
  
  public void writeNode(IXholon xhNode, String indent) {
    // only show state machine nodes if should show them, or if root is a StateMachineCE
    if ((xhNode.getXhcId() == CeStateMachineEntity.StateMachineCE)
        && (isShouldShowStateMachineEntities() == false)
        && xhNode != root) {
      return;
    }
    
    nodeSb.append(indent).append("<node id=\"" + xhNode.getId() + "\" label=\"" + xhNode.getName(getNameTemplate()) + "\">\n");
    this.currentNodeIndent = indent;
    writeNodeAttributes(xhNode);
    writeEdges(xhNode, indent);
    if (isShowHierarchy() && isShowHierarchyAsNestedGraphs() && xhNode.hasChildNodes()) {
      nodeSb.append(indent).append(INDENT_UNIT).append("<nodes>\n");
    }
    else {
      nodeSb.append(indent).append("</node>\n");
    }
    
    // children
    IXholon childNode = xhNode.getFirstChild();
    while (childNode != null) {
      writeNode(childNode, indent + INDENT_UNIT + INDENT_UNIT);
      childNode = childNode.getNextSibling();
    }
    
    if (isShowHierarchy() && isShowHierarchyAsNestedGraphs() && xhNode.hasChildNodes()) {
      nodeSb.append(indent).append(INDENT_UNIT).append("</nodes>\n");
      nodeSb.append(indent).append("</node>\n");
    }

  }

  @Override
  public void writeEdges(IXholon xhNode) {}
  
  public void writeEdges(IXholon xhNode, String indent) {
    makeStandardEdges(xhNode, INDENT_UNIT);
    makeLinks(xhNode, INDENT_UNIT);
  }

  @Override
  public void writeNodeAttributes(IXholon xhNode) {
    IXholon2Xml xholon2xml = xhNode.getXholon2Xml();
    xholon2xml.setXhAttrStyle(IXholon2Xml.XHATTR_TO_XMLATTR);
    xhNode.toXmlAttributes(xholon2xml, this);
  }
  
  /**
   * Add a node's standard "ports" as GEXF edges.
   * These include parentNode, firstChild, nextSibling, xhc, mech.
   * @param node The current node in the Xholon hierarchy.
   */
  protected void makeStandardEdges(IXholon node, String indent)
  {
    if (isShowHierarchy() && !isShowHierarchyAsNestedGraphs()) {
      // show hierarchy as graph edges
      IXholon pn = node.getParentNode();
      if (!node.isRootNode() && (pn != null)
          && (!"Control".equals(pn.getXhcName()))) {
        this.makeLink(XhRelTypes.PARENT_NODE.toCamelCase(), node, pn, indent);
      }
      IXholon fc = node.getFirstChild();
      if (fc != null) {
        this.makeLink(XhRelTypes.FIRST_CHILD.toCamelCase(), node, fc, indent);
      }
      IXholon ns = node.getNextSibling();
      if (!node.isRootNode() && (ns != null)) {
        this.makeLink(XhRelTypes.NEXT_SIBLING.toCamelCase(), node, ns, indent);
      }
    }
    IXholon xhc = node.getXhc();
    if (xhc != null) {
      // this is an IXholon node, and not an IXholonClass node, so it can have an xhc
      if (isShowXhc()) {
        this.makeLink(XhRelTypes.XHC.toCamelCase(), node, xhc, indent);
      }
    }
    else {
      if (node instanceof IXholonClass) {
        // this is an IXholonClass node, which must belong to a mechanism
        IMechanism mech = ((IXholonClass)node).getMechanism();
        this.makeLink(XhRelTypes.MECH.toCamelCase(), node, mech, indent);
      }
    }
  }
  
  /**
   * Write links from this node to any others, where Xholon has connected ports.
   * @param node The current node.
   */
  @SuppressWarnings("unchecked")
  protected void makeLinks(IXholon node, String indent)
  {
    if (isShouldShowLinks() == false) {return;}
    List<PortInformation> portList = node.getAllPorts();
    for (int i = 0; i < portList.size(); i++) {
      makeLink(node, (PortInformation)portList.get(i), indent);
    }
  }
  
  /**
   * Write one link.
   * @param node The node where the link originates.
   * @param portInfo Information about the port that represents the link.
   */
  protected void makeLink(IXholon node, PortInformation portInfo, String indent)
  {
    if (portInfo == null) {return;}
    IXholon remoteNode = portInfo.getReffedNode();
    edgeSb
    .append(indent)
    .append("<edge");
    if (isShouldShowEdgeId()) {
      edgeSb.append(" id=\"" + getNextEdgeIdStr() + "\"");
    }
    edgeSb.append(" source=\"" + node.getId() + "\"")
    .append(" target=\"" + remoteNode.getId() + "\"");
    edgeSb.append(">\n");
    if (isShowPorts()) {
      edgeSb
      .append(indent).append(indent)
      .append("<attvalues>\n")
      .append(indent).append(indent).append(indent)
      .append("<attvalue for=\"fieldName\" value=\"")
      .append(portInfo.getLocalName())
      .append("\"/>\n")
      .append(indent).append(indent)
      .append("</attvalues>\n");
    }
    edgeSb
    .append(indent)
    .append("</edge>\n");
  }
  
  /**
   * Write one link.
   * @param label
   * @param source
   * @param target
   */
  protected void makeLink(String label, IXholon source, IXholon target, String indent) {
    edgeSb
    .append(indent)
    .append("<edge");
    if (isShouldShowEdgeId()) {
      edgeSb.append(" id=\"" + getNextEdgeIdStr() + "\"");
    }
    edgeSb.append(" source=\"" + source.getId() + "\"")
    .append(" target=\"" + target.getId() + "\"")
    .append(">\n");
    if (isShowPorts()) {
      edgeSb
      .append(indent).append(indent)
      .append("<attvalues>\n")
      .append(indent).append(indent).append(indent)
      .append("<attvalue for=\"fieldName\" value=\"")
      .append(label)
      .append("\"/>\n")
      .append(indent).append(indent)
      .append("</attvalues>\n");
    }
    edgeSb
    .append(indent)
    .append("</edge>\n");
  }
  
  /**
   * Make a node key.
   * @param id
   * @param attrName
   * @param attrType
   * @param defaultValue
   */
  protected void makeNodeKey(String id, String attrName, String attrType, String defaultValue, String indent) {
    if (!keyNodeSet.contains(id)) {
      keyNodeSet.add(id);
      makeKey(id, attrName, attrType, defaultValue, "node", keyNodeSb, indent);
    }
  }
  
  /**
   * Make an edge key.
   * @param id
   * @param attrName
   * @param attrType
   * @param defaultValue
   */
  protected void makeEdgeKey(String id, String attrName, String attrType, String defaultValue, String indent) {
    if (!keyEdgeSet.contains(id)) {
      keyEdgeSet.add(id);
      makeKey(id, attrName, attrType, defaultValue, "edge", keyEdgeSb, indent);
    }
  }
  
  /**
   * Make a key by appending a new key element to the keys buffer.
   * @param id
   * @param attrName
   * @param attrType
   * @param defaultValue
   * @param forr
   * @param keySb
   */
  protected void makeKey(String id, String attrName, String attrType,
      String defaultValue, String forr, StringBuilder keySb, String indent) {
    keySb
    .append(indent)
    .append("<attribute id=\"")
    .append(id)
    .append("\" title=\"")
    .append(attrName)
    .append("\" type=\"")
    .append(attrType);
    if (defaultValue == null) {
      keySb.append("\"/>\n");
    }
    else {
      keySb.append("\">\n")
      .append(indent).append(indent)
      .append("<default>")
      .append(defaultValue)
      .append("</default>\n")
      .append(indent)
      .append("</attribute>\n");
    }
  }
  
  /**
   * If writing GraphML for a specific tool (Gephi, yEd),
   * set the features that the tool can handle when reading.
   * TODO this is incomplete
   * @param tool An identifying number for the tool.
   */
  protected void setFeatures(int tool) {
    switch (tool) {
    case TOOL_GENERIC:
      this.setFeatureNestedGraphs(false);
      this.setFeatureHyperedges(false);
      this.setFeaturePorts(false);
      this.setFeatureParallelEdges(false);
      break;
    case TOOL_GEPHI:
      this.setFeatureNestedGraphs(true); // ?
      this.setFeatureHyperedges(false);
      this.setFeaturePorts(false); // ?
      this.setFeatureParallelEdges(false);
      break;
    case TOOL_YED:
      this.setFeatureNestedGraphs(true);
      this.setFeatureHyperedges(false); // ?
      this.setFeaturePorts(false); // ?
      this.setFeatureParallelEdges(false);
      break;
    case TOOL_NETWORKX:
      // "This implementation does not support mixed graphs (directed and unidirected edges together),
      // hypergraphs, nested graphs, or ports."
      this.setFeatureNestedGraphs(false);
      this.setFeatureHyperedges(false);
      this.setFeaturePorts(false);
      this.setFeatureParallelEdges(true); // converts to a MultiGraph
    default:
      break;
    }
  }
  
  /**
   * Make a JavaScript object with all the parameters for this external format.
   */
  protected native void makeEfParams() /*-{
    var p = {};
    p.featureNestedGraphs = true;
    p.featureHyperedges = false;
    p.featurePorts = true;
    p.featureParallelEdges = false;
    //p.targetTool = 2; //TOOL_YED;
    p.shouldShowLinks = true;
    p.shouldShowEdgeId = true;
    //p.linkColor = "#6666ff";
    p.shouldShowStateMachineEntities = false;
    p.nameTemplate = "r:C^^^";
    p.nextEdgeId = 0;
    p.showPorts = true;
    p.showHierarchy = true;
    p.showHierarchyAsNestedGraphs = true;
    p.showXhc = false;
    p.fileNameExtension = ".gexf";
    p.showSubtreeRoot = true;
    p.shouldWriteVal = false;
    p.shouldWriteAllPorts = false;
    p.shouldWriteLinks = false;
    this.efParams = p;
  }-*/;

  // GraphML optional features
  /** primer 3.1 Nested Graphs */
  public native boolean isFeatureNestedGraphs() /*-{return this.efParams.featureNestedGraphs;}-*/;
  public native void setFeatureNestedGraphs(boolean featureNestedGraphs) /*-{this.efParams.featureNestedGraphs = featureNestedGraphs;}-*/;

  /** primer 3.2 Hyperedges */
  public native boolean isFeatureHyperedges() /*-{return this.efParams.featureHyperedges;}-*/;
  public native void setFeatureHyperedges(boolean featureHyperedges) /*-{this.efParams.featureHyperedges = featureHyperedges;}-*/;

  /** primer 3.3 Ports */
  public native boolean isFeaturePorts() /*-{return this.efParams.featurePorts;}-*/;
  public native void setFeaturePorts(boolean featurePorts) /*-{this.efParams.featurePorts = featurePorts;}-*/;

  /**
   * Most GraphML software does not support parallel edges.
   * This class does not check to see if it's writing parallel edges.
   */
  public native boolean isFeatureParallelEdges() /*-{return this.efParams.featureParallelEdges;}-*/;
  public native void setFeatureParallelEdges(boolean featureParallelEdges) /*-{this.efParams.featureParallelEdges = featureParallelEdges;}-*/;

  public native int getTargetTool() /*-{return this.efParams.targetTool;}-*/;
  //public native void setTargetTool(int targetTool) /*-{this.efParams.targetTool = targetTool;}-*/;

  /** Whether or not to draw links between nodes. */
  public native boolean isShouldShowLinks() /*-{return this.efParams.shouldShowLinks;}-*/;
  //public native void setShouldShowLinks(boolean shouldShowLinks) /*-{this.efParams.shouldShowLinks = shouldShowLinks;}-*/;

  /**
   * "Optionally an identifier for the edge can be specified with the XML Attribute id. (Primer 2.3.3)"
   */
  public native boolean isShouldShowEdgeId() /*-{return this.efParams.shouldShowEdgeId;}-*/;
  //public native void setShouldShowEdgeId(boolean shouldShowEdgeId) /*-{this.efParams.shouldShowEdgeId = shouldShowEdgeId;}-*/;

  /** Color to use in drawing link arrows. UNUSED */
  //public native String getLinkColor() /*-{return this.efParams.linkColor;}-*/;
  //public native void setLinkColor(String linkColor) /*-{this.efParams.linkColor = linkColor;}-*/;

  /** Whether or not to show state machine nodes. */
  public native boolean isShouldShowStateMachineEntities() /*-{return this.efParams.shouldShowStateMachineEntities;}-*/;
  //public native void setShouldShowStateMachineEntities(boolean shouldShowStateMachineEntities) /*-{this.efParams.shouldShowStateMachineEntities = shouldShowStateMachineEntities;}-*/;

  /** Template to use when writing out node names. */
  public native String getNameTemplate() /*-{return this.efParams.nameTemplate;}-*/;
  //public native void setNameTemplate(String nameTemplate) /*-{this.efParams.nameTemplate = nameTemplate;}-*/;

  /** The next edge id, used when writing an <edge> element. */
  public native int getNextEdgeId() /*-{
    return this.efParams.nextEdgeId++;
  }-*/;
  //public native void setNextEdgeId(int nextEdgeId) /*-{this.efParams.nextEdgeId = nextEdgeId;}-*/;
  
  /**
   * Get the next edge id as a String.
   * @return
   */
  public String getNextEdgeIdStr() {
    return "e" + getNextEdgeId();
  };

  /** Whether or not ports are used with nodes and edges. */
  public native boolean isShowPorts() /*-{return this.efParams.showPorts;}-*/;
  //public native void setShowPorts(boolean showPorts) /*-{this.efParams.showPorts = showPorts;}-*/;

  /** Whether or not to write the hierarchical (tree) structure. */
  public native boolean isShowHierarchy() /*-{return this.efParams.showHierarchy;}-*/;
  //public native void setShowHierarchy(boolean showHierarchy) /*-{this.efParams.showHierarchy = showHierarchy;}-*/;

  /**
   * Whether or not to write the hierarchical (tree) structure as nested graphs (true)
   * or as sets of parentNode/firstChild/nextSibling edges.
   * This boolean is only valid if showHierarchy == true.
   */
  public native boolean isShowHierarchyAsNestedGraphs() /*-{return this.efParams.showHierarchyAsNestedGraphs;}-*/;
  //public native void setShowHierarchyAsNestedGraphs(boolean showHierarchyAsNestedGraphs) /*-{this.efParams.showHierarchyAsNestedGraphs = showHierarchyAsNestedGraphs;}-*/;

  /** Whether or not to include an edge from IXholon to IXholonClass. */
  public native boolean isShowXhc() /*-{return this.efParams.showXhc;}-*/;
  //public native void setShowXhc(boolean showXhc) /*-{this.efParams.showXhc = showXhc;}-*/;

  /** GEXF files can be .gexf or .xml */
  public native String getFileNameExtension() /*-{return this.efParams.fileNameExtension;}-*/;
  //public native void setFileNameExtension(String fileNameExtension) /*-{this.efParams.fileNameExtension = fileNameExtension;}-*/;

  /** Whether or not to write the root node in the subtree. */
  public native boolean isShowSubtreeRoot() /*-{return this.efParams.showSubtreeRoot;}-*/;
  //public native void setShowSubtreeRoot(boolean showSubtreeRoot) /*-{this.efParams.showSubtreeRoot = showSubtreeRoot;}-*/;

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
    sb
    .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
    .append("<gexf xmlns=\"http://www.gexf.net/1.3\"\n")
    .append("  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n")
    .append("  version=\"1.3\"\n")
    .append("  xsi:schemaLocation=\"http://www.gexf.net/1.3 http://www.gexf.net/1.3/gexf.xsd\"\n")
    .append("  deleteGexfAttrNodes=\"false\"\n") // used by Xholon Gexf.java import tool
    .append("  deleteGexfEdgeNodes=\"false\"\n") // used by Xholon Gexf.java import tool
    .append("  recurseSubtree=\"false\">\n"); // used by Xholon Gexf.java import tool
    
    StringBuilder commentSb = new StringBuilder()
    .append("\nTo view this file, download open-source Gephi from https://gephi.org/\n")
    .append("\nAutomatically generated by Xholon version 0.9.1, using Xholon2Gexf.java\n")
    .append(new Date())
    .append(" ")
    .append(this.timeStamp)
    .append("\n")
    .append("model: ")
    .append(this.modelName)
    .append("\n")
    .append("www.primordion.com/Xholon\n");
    this.writeComment(commentSb.toString());
    
    sb
    .append("\n<graph defaultedgetype=\"").append(GRAPH_EDGEDEFAULT_DIRECTED).append("\" mode=\"static\">\n");
  }

  @Override
  public void writeEndDocument() {
    sb
    .append("</graph>\n")
    .append("</gexf>\n");
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
    if ("Val".equalsIgnoreCase(name) && !isShouldWriteVal()) {return;}
    if ("AllPorts".equalsIgnoreCase(name) && !isShouldWriteAllPorts()) {return;}
    if ("Links".equalsIgnoreCase(name) && !isShouldWriteLinks()) {return;}
    this.makeNodeKey(name, name, KEY_ATTR_TYPE_STRING, null, INDENT_UNIT);
    this.nodeSb
    .append(this.currentNodeIndent).append(INDENT_UNIT)
    .append("<attvalues>\n")
    .append(this.currentNodeIndent).append(INDENT_UNIT).append(INDENT_UNIT)
    .append("<attvalue")
    .append(" for=\"")
    .append(name)
    .append("\" value=\"")
    .append(value)
    .append("\"/>\n")
    .append(this.currentNodeIndent).append(INDENT_UNIT)
    .append("</attvalues>\n");
  }

  @Override
  // DO NOT IMPLEMENT THIS
  public void writeText(String text) {}

  @Override
  public void writeComment(String data) {
    sb.append("<!--" + data + "-->\n");
  }

  @Override
  public String getWriterName() {
    return "Xholon2Gexf";
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
  
  @Override
  public native boolean isShouldWriteLinks() /*-{
    return this.efParams.shouldWriteLinks;
  }-*/;

  @Override
  public native void setShouldWriteLinks(boolean shouldWriteLinks) /*-{
    this.efParams.shouldWriteLinks = shouldWriteLinks;
  }-*/;
	
}

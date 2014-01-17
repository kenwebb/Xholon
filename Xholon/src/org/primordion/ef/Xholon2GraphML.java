/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2010 Ken Webb
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

//import java.io.File;
//import java.io.IOException;
//import java.io.Writer;
//import java.security.AccessControlException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.primordion.xholon.base.IMechanism;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.base.XhRelTypes;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.service.ef.IXholon2GraphFormat;
//import org.primordion.xholon.util.MiscIo;

/**
 * Export a Xholon model in GraphML format.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on February 12, 2010)
 * @see <a href="http://graphml.graphdrawing.org/index.html">GraphML website</a>
 * @see <a href="http://nwb.slis.indiana.edu">Network Workbench website</a>
 */
@SuppressWarnings("serial")
public class Xholon2GraphML extends AbstractXholon2ExternalFormat implements IXholon2GraphFormat, IXmlWriter {
	
	// A GraphML key element requires an "attr.type" XML attribute.
	protected static final String KEY_ATTR_TYPE_BOOLEAN = "boolean";
	protected static final String KEY_ATTR_TYPE_INT = "int";
	protected static final String KEY_ATTR_TYPE_LONG = "long";
	protected static final String KEY_ATTR_TYPE_FLOAT = "float";
	protected static final String KEY_ATTR_TYPE_DOUBLE = "double";
	protected static final String KEY_ATTR_TYPE_STRING = "string";
	
	// A GraphML graph element requires an "edgedefault" XML attribute.
	// Xholon edges are all "directed"
	protected static final String GRAPH_EDGEDEFAULT_DIRECTED = "directed";
	protected static final String GRAPH_EDGEDEFAULT_UNDIRECTED = "undirected";
	
	/** A GraphML graph element, including the required root graph element,
	can optionally have an "id" XML attribute. */
	protected static final String GRAPH_ROOT_ID = "G";
	
	/** StringBuilder/Buffer initial capacity. */
	protected static final int SB_INITIAL_CAPACITY = 1000; // default is 16
	
	// GraphML optional features
	/** primer 3.1 Nested Graphs */
	protected boolean featureNestedGraphs = false;
	/** primer 3.2 Hyperedges */
	protected boolean featureHyperedges = false;
	/** primer 3.3 Ports */
	protected boolean featurePorts = false;
	/**
	 * Most GraphML software does not support parallel edges.
	 * This class does not check to see if it's writing parallel edges.
	 */
	protected boolean featureParallelEdges = false;
	
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
	protected int targetTool = TOOL_YED;
	
	private String outFileName;
	private String outPath = "./ef/graphml/";
	private String modelName;
	private IXholon root;
	//private Writer out;
	private StringBuilder sb;
	
	/** Current date and time. */
	private Date timeNow;
	private long timeStamp;
	
	/** Whether or not to draw links between nodes. */
	private boolean shouldShowLinks = true;
	
	/**
	 * "Optionally an identifier for the edge can be specified with the XML Attribute id. (Primer 2.3.3)"
	 */
	private boolean shouldShowEdgeId = false;
	
	/** Color to use in drawing link arrows. */
	private String linkColor = "#6666ff";
	
	/** Whether or not to show state machine nodes. */
	private boolean shouldShowStateMachineEntities = false;
	
	/** Template to use when writing out node names. */
	protected String nameTemplate = "r:C^^^";
	
	/** The next edge id, used when writing an &lt;edge> element. */
	protected int nextEdgeId = 0;

	/** Whether or not ports are used with nodes and edges. */
	protected boolean showPorts = false;
	
	/** Whether or not to write the hierarchical (tree) structure. */
	protected boolean showHierarchy = true;
	
	/**
	 * Whether or not to write the hierarchical (tree) structure as nested graphs (true)
	 * or as sets of parentNode/firstChild/nextSibling edges.
	 * This boolean is only valid if showHierarchy == true.
	 */
	protected boolean showHierarchyAsNestedGraphs = true;
	
	/** Whether or not to include an edge from IXholon to IXholonClass. */
	protected boolean showXhc = false;
	
	/** GraphML files can be .graphml or .xml */
	protected String fileNameExtension = ".graphml";
	
	/** Whether or not to write the root node in the subtree. */
	protected boolean showSubtreeRoot = true;
	
	/**
	 * The accumulating text for all GraphML node elements.
	 */
	protected StringBuilder nodeSb = null;
	
	/**
	 * The accumulating text for all GraphML edge elements.
	 */
	protected StringBuilder edgeSb = null;
	
	/**
	 * Set of all node key id's that will be added to the GraphML content.
	 * This is used to determine if a key has already been added to the keyNodeSb.
	 */
	protected Set<String> keyNodeSet = null;
	
	/**
	 * The accumulating text of all key elements for GraphML nodes.
	 */
	protected StringBuilder keyNodeSb = null;
	
	/**
	 * Set of all edge key id's that will be added to the GraphML content.
	 * This is used to determine if a key has already been added to the keyEdgeSb.
	 */
	protected Set<String> keyEdgeSet = null;
	
	/**
	 * The accumulating text of all key elements for GraphML edges.
	 */
	protected StringBuilder keyEdgeSb = null;
	
	/**
	 * Constructor.
	 */
	public Xholon2GraphML() {}
	
	/*
	 * @see org.primordion.xholon.io.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
	 */
	public boolean initialize(String outFileName, String modelName, IXholon root) {
		timeNow = new Date();
		timeStamp = timeNow.getTime();
		if (outFileName == null) {
			this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_"
					+ timeStamp + fileNameExtension;
		}
		else {
			this.outFileName = outFileName;
		}
		this.modelName = modelName;
		this.root = root;
		
		this.setFeatures(targetTool);
		this.nodeSb = new StringBuilder(SB_INITIAL_CAPACITY).append("<!-- nodes -->\n");
		this.edgeSb = new StringBuilder(SB_INITIAL_CAPACITY).append("\n<!-- edges -->\n");
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
		/*boolean shouldClose = true;
		if (root.getApp().isUseAppOut()) {
			out = root.getApp().getOut();
			shouldClose = false;
		}
		else {
			try {
				// create any missing output directories
				File dirOut = new File(outPath);
				dirOut.mkdirs(); // will create a new directory only if there is no existing one
				out = MiscIo.openOutputFile(outFileName);
			} catch(AccessControlException e) {
				//out = new PrintWriter(System.out);
				out = root.getApp().getOut();
				shouldClose = false;
			}
		}*/
		sb = new StringBuilder();
		//try {
			this.writeStartDocument();
			
			StringBuilder commentSb = new StringBuilder()
			.append("\nTo view this file, download open-source Network Workbench from http://nwb.slis.indiana.edu\n")
			.append("Or use free yEd or GraphML Viewer from http://www.yworks.com\n")
			.append("\nAutomatically generated by Xholon version 0.8.1, using Xholon2GraphML.java\n")
			.append(new Date())
			.append(" ")
			.append(timeStamp)
			.append("\n")
			.append("model: ")
			.append( modelName)
			.append("\n")
			.append("www.primordion.com/Xholon\n");
			this.writeComment(commentSb.toString());
			
			this.makeNodeKey("name", "name", KEY_ATTR_TYPE_STRING, null);
			this.makeEdgeKey("fieldName", "fieldName", KEY_ATTR_TYPE_STRING, null);
			if (showSubtreeRoot) {
				writeNode(root);
			}
			else {
				// write children of root, if any
				IXholon childNode = root.getFirstChild();
				while (childNode != null) {
					writeNode(childNode);
					childNode = childNode.getNextSibling();
				}
			}
			sb.append(keyNodeSb.toString());
			sb.append(keyEdgeSb.toString());
			sb.append("<graph id=\"" + GRAPH_ROOT_ID
					+ "\" edgedefault=\"" + GRAPH_EDGEDEFAULT_DIRECTED + "\">\n");
			sb.append(nodeSb.toString());
			sb.append(edgeSb.toString());
			sb.append("</graph>\n");
			this.writeEndDocument();
			//out.write(sb.toString());
			//out.flush();
			writeToTarget(sb.toString(), outFileName, outPath, root);
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
		//if (shouldClose) {
		//	MiscIo.closeOutputFile(out);
		//}
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public void writeNode(IXholon xhNode) {
		// only show state machine nodes if should show them, or if root is a StateMachineCE
		if ((xhNode.getXhcId() == CeStateMachineEntity.StateMachineCE)
				&& (shouldShowStateMachineEntities == false)
				&& xhNode != root) {
			return;
		}
		
		nodeSb.append("<node id=\"" + xhNode.getId() + "\">\n");
		nodeSb.append("  <data key=\"name\">" + xhNode.getName(nameTemplate) + "</data>\n");
		writeNodeAttributes(xhNode);
		writeEdges(xhNode);
		if (showPorts) {
			List<PortInformation> portList = xhNode.getAllPorts();
			for (int i = 0; i < portList.size(); i++) {
				makeNodePort((PortInformation)portList.get(i));
			}
		}
		if (showHierarchy && showHierarchyAsNestedGraphs && xhNode.hasChildNodes()) {
			nodeSb.append("<graph edgedefault=\"" + GRAPH_EDGEDEFAULT_DIRECTED + "\">\n");
		}
		else {
			nodeSb.append("</node>\n");
		}
		
		// children
		IXholon childNode = xhNode.getFirstChild();
		while (childNode != null) {
			writeNode(childNode);
			childNode = childNode.getNextSibling();
		}
		
		if (showHierarchy && showHierarchyAsNestedGraphs && xhNode.hasChildNodes()) {
			nodeSb.append("</graph>\n");
			nodeSb.append("</node>\n");
		}

	}

	@Override
	public void writeEdges(IXholon xhNode) {
		makeStandardEdges(xhNode);
		makeLinks(xhNode);
	}

	@Override
	public void writeNodeAttributes(IXholon xhNode) {
		IXholon2Xml xholon2xml = xhNode.getXholon2Xml();
		xholon2xml.setXhAttrStyle(IXholon2Xml.XHATTR_TO_XMLATTR);
		xhNode.toXmlAttributes(xholon2xml, this);
	}
	
	/**
	 * Add a node's standard "ports" as GraphML edges.
	 * These include parentNode, firstChild, nextSibling, xhc, mech.
	 * @param node The current node in the Xholon hierarchy.
	 */
	protected void makeStandardEdges(IXholon node)
	{
		if (showHierarchy && !showHierarchyAsNestedGraphs) {
			// show hierarchy as graph edges
			IXholon pn = node.getParentNode();
			if (!node.isRootNode() && (pn != null)
					&& (!"Control".equals(pn.getXhcName()))) {
				this.makeLink(XhRelTypes.PARENT_NODE.toCamelCase(), node, pn);
			}
			IXholon fc = node.getFirstChild();
			if (fc != null) {
				this.makeLink(XhRelTypes.FIRST_CHILD.toCamelCase(), node, fc);
			}
			IXholon ns = node.getNextSibling();
			if (!node.isRootNode() && (ns != null)) {
				this.makeLink(XhRelTypes.NEXT_SIBLING.toCamelCase(), node, ns);
			}
		}
		IXholon xhc = node.getXhc();
		if (xhc != null) {
			// this is an IXholon node, and not an IXholonClass node, so it can have an xhc
			if (showXhc) {
				this.makeLink(XhRelTypes.XHC.toCamelCase(), node, xhc);
			}
		}
		else {
			if (node instanceof IXholonClass) {
				// this is an IXholonClass node, which must belong to a mechanism
				IMechanism mech = ((IXholonClass)node).getMechanism();
				this.makeLink(XhRelTypes.MECH.toCamelCase(), node, mech);
			}
		}
	}
	
	/**
	 * Write a port element for a node.
	 * @param portInfo
	 */
	protected void makeNodePort(PortInformation portInfo)
	{
		if (portInfo == null) {return;}
		String portName = portInfo.getFieldName();
		if ("port".equals(portName)) {
			// if the name of the Java field is "port", then it's part of an array
			portName += portInfo.getFieldNameIndex();
		}
		nodeSb.append("<port name=\"" + portName + "\"/>\n");
	}
	
	/**
	 * Write links from this node to any others, where Xholon has connected ports.
	 * @param node The current node.
	 */
	@SuppressWarnings("unchecked")
	protected void makeLinks(IXholon node)
	{
		if (shouldShowLinks == false) {return;}
		List<PortInformation> portList = node.getAllPorts();
		for (int i = 0; i < portList.size(); i++) {
			makeLink(node, (PortInformation)portList.get(i));
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
		IXholon remoteNode = portInfo.getReffedNode();
		edgeSb.append("<edge");
		if (shouldShowEdgeId) {
			edgeSb.append(" id=\"" + getNextEdgeIdStr() + "\"");
		}
		edgeSb.append(" source=\"" + node.getId() + "\"")
		.append(" target=\"" + remoteNode.getId() + "\"");
		if (showPorts) {
			makeEdgePort(portInfo);
		}
		edgeSb.append(">\n")
		.append("  <data key=\"fieldName\">")
		.append(portInfo.getLocalName())
		.append("</data>\n")
		.append("</edge>\n");
	}
	
	/**
	 * Write one link.
	 * @param label
	 * @param source
	 * @param target
	 */
	protected void makeLink(String label, IXholon source, IXholon target) {
		edgeSb.append("<edge");
		if (shouldShowEdgeId) {
			edgeSb.append(" id=\"" + getNextEdgeIdStr() + "\"");
		}
		edgeSb.append(" source=\"" + source.getId() + "\"")
		.append(" target=\"" + target.getId() + "\"")
		.append(">\n")
		.append("  <data key=\"fieldName\">")
		.append(label)
		.append("</data>\n")
		.append("</edge>\n");
	}
	
	/**
	 * Write a port element for an edge.
	 * Xholon only knows about sourceport, not targetport.
	 * @param portInfo
	 */
	protected void makeEdgePort(PortInformation portInfo)
	{
		if (portInfo == null) {return;}
		String portName = portInfo.getFieldName();
		if ("port".equals(portName)) {
			// if the name of the Java field is "port", then it's part of an array
			portName += portInfo.getFieldNameIndex();
		}
		edgeSb.append(" sourceport=\"" + portName + "\"");
	}
	
	/**
	 * Make a node key.
	 * @param id
	 * @param attrName
	 * @param attrType
	 * @param defaultValue
	 */
	protected void makeNodeKey(String id, String attrName, String attrType, String defaultValue) {
		if (!keyNodeSet.contains(id)) {
			keyNodeSet.add(id);
			makeKey(id, attrName, attrType, defaultValue, "node", keyNodeSb);
		}
	}
	
	/**
	 * Make an edge key.
	 * @param id
	 * @param attrName
	 * @param attrType
	 * @param defaultValue
	 */
	protected void makeEdgeKey(String id, String attrName, String attrType, String defaultValue) {
		if (!keyEdgeSet.contains(id)) {
			keyEdgeSet.add(id);
			makeKey(id, attrName, attrType, defaultValue, "edge", keyEdgeSb);
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
			String defaultValue, String forr, StringBuilder keySb) {
		keySb.append("<key id=\"")
		.append(id)
		.append("\" for=\"")
		.append(forr)
		.append("\" attr.name=\"")
		.append(attrName)
		.append("\" attr.type=\"")
		.append(attrType);
		if (defaultValue == null) {
			keySb.append("\"/>\n");
		}
		else {
			keySb.append("\">\n")
			.append("<default>")
			.append(defaultValue)
			.append("</default>\n")
			.append("</key>\n");
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
			this.featureNestedGraphs = false;
			this.featureHyperedges = false;
			this.featurePorts = false;
			this.featureParallelEdges = false;
			break;
		case TOOL_GEPHI:
			this.featureNestedGraphs = true; // ?
			this.featureHyperedges = false;
			this.featurePorts = false; // ?
			this.featureParallelEdges = false;
			break;
		case TOOL_YED:
			this.featureNestedGraphs = true;
			this.featureHyperedges = false; // ?
			this.featurePorts = false; // ?
			this.featureParallelEdges = false;
			break;
		case TOOL_NETWORKX:
			// "This implementation does not support mixed graphs (directed and unidirected edges together),
			// hypergraphs, nested graphs, or ports."
			this.featureNestedGraphs = false;
			this.featureHyperedges = false;
			this.featurePorts = false;
			this.featureParallelEdges = true; // converts to a MultiGraph
		default:
			break;
		}
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

	public String getLinkColor() {
		return linkColor;
	}

	public void setLinkColor(String linkColor) {
		this.linkColor = linkColor;
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

	public boolean isShowPorts() {
		return showPorts;
	}

	public void setShowPorts(boolean showPorts) {
		this.showPorts = showPorts;
	}

	public boolean isShowHierarchy() {
		return showHierarchyAsNestedGraphs;
	}

	public void setShowHierarchy(boolean showHierarchy) {
		this.showHierarchyAsNestedGraphs = showHierarchy;
	}

	public String getOutPath() {
		return outPath;
	}

	public void setOutPath(String outPath) {
		this.outPath = outPath;
	}

	public String getFileNameExtension() {
		return fileNameExtension;
	}

	public void setFileNameExtension(String fileNameExtension) {
		this.fileNameExtension = fileNameExtension;
	}

	// #############################################################################
	// methods required to implement IXmlWriter
	
	@Override
	// DO NOT IMPLEMENT THIS
	public void createNew(Object out) {}

	@Override
	public void writeStartDocument() {
		//try {
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			sb.append("<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\"\n");
			sb.append("  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
			sb.append("  xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns\n");
			sb.append("  http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd\">\n\n");
		//} catch(IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
	}

	@Override
	public void writeEndDocument() {
		//try {
			sb.append("</graphml>\n");
		//} catch(IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
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
		this.makeNodeKey(name, name, KEY_ATTR_TYPE_STRING, null);
		this.nodeSb
		.append("  <data key=\"")
		.append(name)
		.append("\">")
		.append(value)
		.append("</data>\n");
	}

	@Override
	// DO NOT IMPLEMENT THIS
	public void writeText(String text) {}

	@Override
	public void writeComment(String data) {
		//try {
			sb.append("<!--" + data + "-->\n");
		//} catch(IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
	}

	@Override
	public String getWriterName() {
		return "Xholon2GraphML";
	}

	@Override
	// DO NOT IMPLEMENT THIS
	public void flush() {}

	public int getTargetTool() {
		return targetTool;
	}

	public void setTargetTool(int targetTool) {
		this.targetTool = targetTool;
	}

	public boolean isShouldShowEdgeId() {
		return shouldShowEdgeId;
	}

	public void setShouldShowEdgeId(boolean shouldShowEdgeId) {
		this.shouldShowEdgeId = shouldShowEdgeId;
	}

	public boolean isShowHierarchyAsNestedGraphs() {
		return showHierarchyAsNestedGraphs;
	}

	public void setShowHierarchyAsNestedGraphs(boolean showHierarchyAsNestedGraphs) {
		this.showHierarchyAsNestedGraphs = showHierarchyAsNestedGraphs;
	}

	public boolean isShowXhc() {
		return showXhc;
	}

	public void setShowXhc(boolean showXhc) {
		this.showXhc = showXhc;
	}

	public boolean isShowSubtreeRoot() {
		return showSubtreeRoot;
	}

	public void setShowSubtreeRoot(boolean showSubtreeRoot) {
		this.showSubtreeRoot = showSubtreeRoot;
	}

}

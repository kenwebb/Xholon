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

package org.primordion.ef.other;

//import java.io.File;
//import java.io.IOException;
//import java.io.Writer;
//import java.security.AccessControlException;
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
 * Export a Xholon model in XGMML format.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on February 12, 2010)
 * @see <a href="http://nwb.slis.indiana.edu">Network Workbench website</a>
 * @see <a href="http://www.cs.rpi.edu/~puninj/XGMML">XGMML website</a>
 * @see <a href="http://cgi5.cs.rpi.edu/research/groups/pb/punin/public_html/XGMML/">XGMML website</a>
 */
@SuppressWarnings("serial")
public class Xholon2Xgmml extends AbstractXholon2ExternalFormat implements IXholon2GraphFormat, IXmlWriter {
	
	// An XGMML att element has a "type" attribute.
	protected static final String ATT_TYPE_STRING = "string"; // XGMML default
	protected static final String ATT_TYPE_REAL = "real";
	protected static final String ATT_TYPE_INTEGER = "integer";
	protected static final String ATT_TYPE_LIST = "list";
	
	// A XGMML graph element requires a "directed" attribute.
	protected static final String GRAPH_DIRECTED = "1"; // Xholon default
	protected static final String GRAPH_UNDIRECTED = "0"; // XGMML default
	
	/** StringBuilder/Buffer initial capacity. */
	protected static final int SB_INITIAL_CAPACITY = 1000; // default is 16
	
	// XGMML tools (readers, editors) - each supports different features
	/** base XGMML capabilities */
	protected static final int TOOL_GENERIC = 0;
	/** Gephi 0.8.2-beta; not present in 0.7+ */
	//protected static final int TOOL_GEPHI = 1;
	/** yed 3.9.2; doesn't work */
	//protected static final int TOOL_YED = 2;
	//protected static final int TOOL_BLUEPRINTS = 3;
	/** C++ Boost Graphics Library */
	//protected static final int TOOL_BOOST = 4;
	/** JUNG 1.7.6 */
	//protected static final int TOOL_JUNG = 5;
	protected static final int TOOL_NWB = 6;
	//protected static final int TOOL_NETWORKX = 7;
	/** Cytoscape 2.8.3, Cytoscape 3.0 */
	protected static final int TOOL_CYTOSCAPE = 8;
	protected int targetTool = TOOL_CYTOSCAPE;
	
	private String outFileName;
	private String outPath = "./ef/xgmml/";
	private String modelName;
	private IXholon root;
	//protected Writer out = null;
	protected StringBuilder sb;
	
	/** Current date and time. */
	private Date timeNow;
	private long timeStamp;
	
	/** Cytoscape 2.x MetaNode plugin. */
	protected boolean featureMetaNode = true;
	
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
	
	/** XGMML files can be .xgmml or .xml */
	protected String fileNameExtension = ".xgmml";
	
	/** XGMML official namespace */
	protected String namespace = "http://www.cs.rpi.edu/XGMML";
	
	/** Whether or not to write the root node in the subtree. */
	protected boolean showSubtreeRoot = false;
	
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
	
	/**
	 * Whether or not to actually write the output to some target such as a file or browser tab.
	 * This is useful for subclasses (such as Xholon2Gml) that only want their own (GML) format output.
	 */
	protected boolean shouldWriteToTarget = true;
	
	/**
	 * Whether or not to write an attribute with the name "Val".
	 */
	private boolean shouldWriteVal = true;
	
	/**
	 * Whether or not to write an attribute with the name "AllPorts".
	 */
	private boolean shouldWriteAllPorts = true;
	
	/**
	 * Constructor.
	 */
	public Xholon2Xgmml() {}
	
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
		this.root = root;
		
		this.nodeSb = new StringBuilder(SB_INITIAL_CAPACITY); //.append("<!-- nodes -->\n");
		
		// TODO I don't know if all edges can be at the root level,
		// or if they have to be children of graph element in a nested structure.
		// For now, place all edges at root level.
		this.edgeSb = new StringBuilder(SB_INITIAL_CAPACITY); //.append("\n<!-- edges -->\n");
		
		return true;
	}
	
	/*
	 * @see org.primordion.xholon.io.IXholon2ExternalFormat#writeAll()
	 */
	public void writeAll() {
		/*shouldClose = true;
		if (out != null) {}
		else if (root.getApp().isUseAppOut()) {
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
			writeStartDocument();			
			//writeNodes(root, 0);
			//writeEdges(root, 0);
			
			StringBuilder commentSb = new StringBuilder()
			.append("\nTo view this file, download open-source Network Workbench from http://nwb.slis.indiana.edu\n")
			.append("\nAutomatically generated by Xholon version 0.8.1, using Xholon2Xgmml.java\n")
			.append(new Date())
			.append(" ")
			.append(timeStamp)
			.append("\n")
			.append("model: ")
			.append( modelName)
			.append("\n")
			.append("www.primordion.com/Xholon\n");
			this.writeComment(commentSb.toString());
			
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
			sb.append(nodeSb.toString());
			sb.append(edgeSb.toString());
			this.writeEndDocument();
			//out.write(sb.toString());
			//out.flush();
			if (shouldWriteToTarget) {
			  writeToTarget(sb.toString(), outFileName, outPath, root);
			}
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
		//if (shouldClose) {
		//	MiscIo.closeOutputFile(out);
		//}
	}
	
	@Override
	public void writeNode(IXholon xhNode) {
		// only show state machine nodes if should show them, or if root is a StateMachineCE
		if ((xhNode.getXhcId() == CeStateMachineEntity.StateMachineCE)
				&& (shouldShowStateMachineEntities == false)
				&& (xhNode != root)) {
			return;
		}
		nodeSb.append("<node id=\"")
		.append(xhNode.getId())
		.append("\" label=\"")
		.append(xhNode.getName(nameTemplate))
		.append("\">\n");
		writeNodeAttributes(xhNode);
		writeEdges(xhNode);
		if (showHierarchy && showHierarchyAsNestedGraphs && xhNode.hasChildNodes()) {
		  if (featureMetaNode) {
			  nodeSb.append("<att type=\"string\" name=\"__groupViewer\" value=\"metaNode\"/>\n");
			  nodeSb.append("<att><graph>\n");
		  }
		  else {
			  nodeSb.append("<att><graph directed=\"1\">\n");
			}
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
			nodeSb.append("</graph></att>\n");
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
	 * Add a node's standard "ports" as XGMML edges.
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
	 * Write information about each node.
	 * @param node
	 * @param level
	 */
	/*protected void writeNodes(IXholon node, int level)
	{
		try {
			sb.append("<!-- nodes -->\n");
			writeNode(node, level);
		} catch (IOException e) {
			Xholon.getLogger().error("", e);
		}
	}*/
	
	/**
	 * Write information about the edges between nodes.
	 * @param node
	 * @param level
	 */
	/*protected void writeEdges(IXholon node, int level)
	{
		try {
			sb.append("\n<!-- edges -->\n");
			writeEdge(node, level);
		} catch (IOException e) {
			Xholon.getLogger().error("", e);
		}
	}*/
	
	/**
	 * Write links from this node to any others, where Xholon has connected ports.
	 * @param node The current node.
	 */
	@SuppressWarnings("unchecked")
	protected void makeLinks(IXholon node)
	{
		if (!shouldShowLinks) {return;}
		List<PortInformation> portList = node.getAllPorts();
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
		IXholon remoteNode = portInfo.getReffedNode();
		edgeSb.append("<edge");
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
		//.append("</edge>\n");
	}
	
	/*protected void writeLink(IXholon node, PortInformation portInfo)
	{
		if (portInfo == null) {return;}
		IXholon remoteNode = portInfo.getReffedNode();
		try {
			sb.append("<edge");
			sb.append(" source=\"" + node.getId() + "\"");
			sb.append(" target=\"" + remoteNode.getId() + "\"");
			sb.append(" label=\"" + getNextEdgeIdStr() + "\"");
			sb.append(">");
			sb.append("</edge>\n");
		} catch (IOException e) {
			Xholon.getLogger().error("", e);
		}
	}*/
	
	/**
	 * Write one link.
	 * @param label
	 * @param source
	 * @param target
	 */
	protected void makeLink(String label, IXholon source, IXholon target) {
		/*edgeSb.append("<edge");
		if (shouldShowEdgeId) {
			edgeSb.append(" id=\"" + getNextEdgeIdStr() + "\"");
		}
		edgeSb.append(" source=\"" + source.getId() + "\"")
		.append(" target=\"" + target.getId() + "\"")
		.append(">\n")
		.append("  <data key=\"fieldName\">")
		.append(label)
		.append("</data>\n")
		.append("</edge>\n");*/
		
		edgeSb.append("<edge");
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
		//.append("</edge>\n");
	}
	
	/**
	 * Write one node, and its child nodes.
	 * @param node The current node in the Xholon hierarchy.
	 * @param level Current level in the hierarchy.
	 */
	/*protected void writeNode(IXholon node, int level) {
		// only show state machine nodes if should show them, or if root is a StateMachineCE
		if ((node.getXhcId() == CeStateMachineEntity.StateMachineCE)
				&& (shouldShowStateMachineEntities == false)
				&& (level > 0)) {
			return;
		}
		
		try {
			sb.append("<node id=\"" + node.getId() + "\" label=\"" + node.getName(nameTemplate) + "\">\n");
			if (showHierarchyAsNestedGraphs && node.hasChildNodes()) {
			  if (featureMetaNode) {
			    sb.append("<att type=\"string\" name=\"__groupViewer\" value=\"metaNode\"/>\n");
			    sb.append("<att><graph>\n");
			  }
			  else {
				  sb.append("<att><graph directed=\"1\">\n");
				}
			}
			else {
				sb.append("</node>\n");
			}
			
			// children
			IXholon childNode = node.getFirstChild();
			while (childNode != null) {
				writeNode(childNode, level+1);
				childNode = childNode.getNextSibling();
			}
			
			if (showHierarchyAsNestedGraphs && node.hasChildNodes()) {
				sb.append("</graph></att>\n");
				sb.append("</node>\n");
			}
			
			
		} catch (IOException e) {
			Xholon.getLogger().error("", e);
		}
	}*/
	
	/**
	 * Write all edges for one node.
	 * @param node The current node in the Xholon hierarchy.
	 * @param level Current level in the hierarchy.
	 */
	/*protected void writeEdge(IXholon node, int level) {
		// only show state machine nodes if should show them, or if root is a StateMachineCE
		if ((node.getXhcId() == CeStateMachineEntity.StateMachineCE)
				&& (shouldShowStateMachineEntities == false)
				&& (level > 0)) {
			return;
		}
		writeLinks(node);
		
		// children
		IXholon childNode = node.getFirstChild();
		while (childNode != null) {
			writeEdge(childNode, level+1);
			childNode = childNode.getNextSibling();
		}
	}*/
	
	/**
	 * Write links from this node to any others, where Xholon has connected ports.
	 * @param node The current node.
	 */
	/*@SuppressWarnings("unchecked")
	protected void writeLinks(IXholon node)
	{
		if (shouldShowLinks == false) {return;}
		List<PortInformation> portList = node.getAllPorts();
		for (int i = 0; i < portList.size(); i++) {
			writeLink(node, portList.get(i));
		}
	}*/
	
	/**
	 * Does the specified node have children that are domain objects.
	 * That is, they are not state machines, or attributes.
	 * @param node The current node in the Xholon hierarchy.
	 * @return true or false
	 */
	protected boolean hasDomainChildNodes(IXholon node) {
		IXholon testNode = node.getFirstChild();
		while (testNode != null) {
			if ((testNode.getXhcId() == CeStateMachineEntity.StateMachineCE)) {}
			else if (testNode.getXhc().hasAncestor("Attribute")) {}
			else {
				return true;
			}
			testNode = testNode.getNextSibling();
		}
		return false;
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

	public boolean isShowHierarchyAsNestedGraphs() {
		return showHierarchyAsNestedGraphs;
	}

	public void setShowHierarchyAsNestedGraphs(boolean showHierarchyAsNestedGraphs) {
		this.showHierarchyAsNestedGraphs = showHierarchyAsNestedGraphs;
	}
	
	// #############################################################################
	// methods required to implement IXmlWriter
	
	@Override
	// DO NOT IMPLEMENT THIS
	public void createNew(Object out) {}

	@Override
	public void writeStartDocument() {
		StringBuilder sbSd = new StringBuilder(SB_INITIAL_CAPACITY)
		.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n")
		.append("<!--<!DOCTYPE graph PUBLIC \"-//John Punin//DTD graph description//EN\"")
		.append(" \"http://www.cs.rpi.edu/~puninj/XGMML/xgmml.dtd\">-->\n")
		.append("<graph ");
		if (targetTool == TOOL_CYTOSCAPE) {
		    sbSd.append("xmlns:dc=\"http://purl.org/dc/elements/1.1/\" ")
		    .append("xmlns:xlink=\"http://www.w3.org/1999/xlink\" ")
		    .append("xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" ")
		    .append("xmlns:cy=\"http://www.cytoscape.org\" ");
		}
	    sbSd.append("xmlns=\"")
	    .append(namespace)
	    .append("\" ")
	    .append("directed=\"")
	    .append(GRAPH_DIRECTED)
	    .append("\" id=\"")
	    .append(root.getId())
	    .append("\" label=\"")
	    .append(modelName)
	    .append("\">\n");
		//try {
			sb.append(sbSd.toString());
		//} catch(IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
	}

	@Override
	public void writeEndDocument() {
		//try {
			sb.append("</graph>\n");
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
	// All attributes are type="string", the XGMML default.
	public void writeAttribute(String name, String value) {
	  if ("Val".equalsIgnoreCase(name) && !isShouldWriteVal()) {return;}
	  if ("AllPorts".equalsIgnoreCase(name) && !isShouldWriteAllPorts()) {return;}
		this.nodeSb
		.append("  <att name=\"")
		.append(name)
		.append("\" value=\"")
		.append(value)
		.append("\"/>\n");
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
		return "Xholon2Xgmml";
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

	public boolean isFeatureMetaNode() {
		return featureMetaNode;
	}

	public void setFeatureMetaNode(boolean featureMetaNode) {
		this.featureMetaNode = featureMetaNode;
	}

	public boolean isShouldShowEdgeId() {
		return shouldShowEdgeId;
	}

	public void setShouldShowEdgeId(boolean shouldShowEdgeId) {
		this.shouldShowEdgeId = shouldShowEdgeId;
	}

	public boolean isShowHierarchy() {
		return showHierarchy;
	}

	public void setShowHierarchy(boolean showHierarchy) {
		this.showHierarchy = showHierarchy;
	}

	public boolean isShowXhc() {
		return showXhc;
	}

	public void setShowXhc(boolean showXhc) {
		this.showXhc = showXhc;
	}

	public String getFileNameExtension() {
		return fileNameExtension;
	}

	public void setFileNameExtension(String fileNameExtension) {
		this.fileNameExtension = fileNameExtension;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public int getTargetTool() {
		return targetTool;
	}

	public void setTargetTool(int targetTool) {
		this.targetTool = targetTool;
	}

	public boolean isShowSubtreeRoot() {
		return showSubtreeRoot;
	}

	public void setShowSubtreeRoot(boolean showSubtreeRoot) {
		this.showSubtreeRoot = showSubtreeRoot;
	}

}

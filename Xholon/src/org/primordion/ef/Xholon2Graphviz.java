/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2012 Ken Webb
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primordion.xholon.base.IDecoration;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;
//import org.primordion.xholon.util.MiscIo;

/**
 * Write a Xholon model in Graphviz dot format.
 * Example:
<pre>
digraph 0 { label=HelloWorldSystem
 layout=fdp;
 1 [label=Hello]
  1 -> 2;
 2 [label=World]
  2 -> 1;
}
</pre>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 14, 2012)
 */
@SuppressWarnings("serial")
public class Xholon2Graphviz extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {

	protected static final String GV_DEFAULT_FILE_EXT = ".gv"; // .gv .dot
	protected static final String GV_DEFAULT_GRAPH = "digraph"; // graph digraph
	protected static final String GV_DEFAULT_LAYOUT = "dot"; // dot neato twopi circo fdp sfdp
	protected static final String GV_DEFAULT_EDGEOP = "->"; // -- ->
	protected static final String GV_CLUSTER = "cluster";
	protected static final int GV_MAXLABELLEN_NULL = -1;
	
	protected String outFileName;
	protected String outPath = "./ef/graphviz/";
	protected String modelName;
	protected IXholon root;
	//protected Writer out;
	private StringBuilder sb;
	protected String gvFileExt = GV_DEFAULT_FILE_EXT;
	protected String gvGraph = GV_DEFAULT_GRAPH;
	protected String layout = GV_DEFAULT_LAYOUT;
	protected String edgeOp = GV_DEFAULT_EDGEOP;
	
	/** Current date and time. */
	protected Date timeNow;
	protected long timeStamp;
	
	/** Whether or not to show state machine nodes. */
	protected boolean shouldShowStateMachineEntities = true;
	
	/** Template to use when writing out node IDs. */
	protected String nameTemplateNodeId = "^^^^i^"; // id only
	
	/** Template to use when writing out node labels. */
	protected String nameTemplateNodeLabel = "R^^^^^"; // roleName or className, but not both
	//protected String nameTemplateNodeLabel = "r:C^^^"; // shouldQuoteLabels = true
	//protected String nameTemplateNodeLabel = "r^^^^^"; // roleName only
	//protected String nameTemplateNodeLabel = "^^C^^^"; // className only
	
	/** Whether or not labels/names should be quoted. */
	protected boolean shouldQuoteLabels = true;
	
	/** Whether or not to draw edges between nodes. */
	protected boolean shouldShowLinks = true;
	
	/** Whether or not to specify the layout engine. */
	protected boolean shouldSpecifyLayout = false;
	
	/**
	 * For use with indenting by level.
	 */
	protected String blanks = "                         ";
	
	/**
	 * Edges that need to be output by an ancestor node.
	 */
	protected Map<IXholon, String> edgeMap = null;
	
	/**
	 * Maximum length of a label.
	 * <p>GV_MAXLABELLEN_NULL - no max len </p>
	 * <p>0 - don't show labels</p>
	 */
	protected int maxLabelLen = GV_MAXLABELLEN_NULL;
	
	/**
	 * Whether or not to provide a fill color for nodes.
	 * If possible, the fill color will be taken from the XholonClass color.
	 */
	protected boolean shouldColor = true;
	
	/**
	 * Default fill color if shouldColor == true.
	 */
	protected String defaultColor = "\"#f0f8ff\"";
	
	/**
	 * Whether or not to specify a stylesheet file name in the output.
	 */
	protected boolean shouldSpecifyStylesheet = false;
	
	/**
	 * Name of a stylesheet (css) file.
	 */
	protected String stylesheet = "xholon.css";
	
	/**
	 * Whether or not to specify a rankdir.
	 */
	protected boolean shouldSpecifyRankdir = false;
	
	/**
	 * rankdir=LR|RL|BT
	 */
	protected String rankdir = "LR";
	
	/**
	 * Constructor
	 */
	public Xholon2Graphviz() {}
	
	/*
	 * @see org.primordion.ef.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
	 */
	public boolean initialize(String outFileName, String modelName, IXholon root) {
		timeNow = new Date();
		timeStamp = timeNow.getTime();
		if (outFileName == null) {
			this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + gvFileExt;
		}
		else {
			this.outFileName = outFileName;
		}
		this.modelName = modelName;
		this.root = root;
		return true;
	}

	/*
	 * @see org.primordion.ef.IXholon2ExternalFormat#writeAll()
	 */
	public void writeAll() {
		//boolean shouldClose = true;
		/*if (root.getApp().isUseAppOut()) {
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
				out = root.getApp().getOut();
				shouldClose = false;
			}
		}*/
		sb = new StringBuilder();
		//try {
			String fn = root.getXhcName() + "_" + root.getId() + "_" + timeStamp;
			sb.append(
				"/*\nAutomatically generated by Xholon version 0.8.1, using "
					+ this.getClass().getName() + ".java\n"
					+ new Date() + " " + timeStamp + "\n"
					+ "model: " + modelName + "\n"
					+ "www.primordion.com/Xholon\n\n");
			sb.append("To view this file, use Graphviz dot|fdp|neato|twopi|circo|sfdp from http://www.graphviz.org/\n");
			sb.append("For example, to generate an SVG file:\n");
			sb.append("  " + layout + " -Tsvg -O " + fn + gvFileExt + "\n");
			sb.append("Alternatively try one of these:\n");
			sb.append("  " + "dot" + " -Tsvg -O " + fn + gvFileExt + "\n");
			sb.append("  " + "dot" + " -Tsvg -O -Grankdir=LR " + fn + gvFileExt + "\n");
			sb.append("  " + "fdp" + " -Tsvg -O " + fn + gvFileExt + "\n");
			sb.append("  " + "neato" + " -Tsvg -O " + fn + gvFileExt + "\n");
			sb.append("  " + "circo" + " -Tsvg -O " + fn + gvFileExt + "\n");
			sb.append("Or use one of: -Tgif -Tjpg -Tpdf -Tpng -Txdot -Txlib\n");
			sb.append("See also: http://hughesbennett.net/Graphviz\n");
			sb.append("See also: http://graphviz-dev.appspot.com/\n");
			sb.append("See also: http://www.webgraphviz.com/\n");
			sb.append("See also: http://rise4fun.com/agl/\n");
			sb.append("*/\n");
			writeNode(root, 0); // root is level 0
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

	/**
	 * Write one node, and its child nodes.
	 * @param node The current node in the Xholon hierarchy.
	 * @param level Current level in the hierarchy.
	 */
	protected void writeNode(IXholon node, int level) {
		// only show state machine nodes if should show them, or if root is a StateMachineCE
		if ((node.getXhcId() == CeStateMachineEntity.StateMachineCE)
				&& (shouldShowStateMachineEntities == false)
				&& (level > 0)) {
			return;
		}
		String nodeId = makeNodeId(node);
		String nodeLabel = makeNodeLabel(node);
		if (shouldQuoteLabels) {
			//nodeId = "\"" + nodeId + "\"";
			if (nodeLabel != null) {
				nodeLabel = "\"" + nodeLabel + "\"";
			}
		}
		String tab = blanks.substring(0, level);
		//try {
			// children
			if (node.hasChildNodes()) {
				if (node == root) {
					sb.append(gvGraph + " ");
					sb.append(nodeId);
					sb.append(" {");
					if (nodeLabel != null) {
						sb.append(" label=" + nodeLabel);
					}
					sb.append("\n");
					if (shouldSpecifyLayout) {
						sb.append(" layout=" + layout + ";\n");
					}
					if (shouldSpecifyStylesheet) {
						sb.append(" stylesheet=\"" + stylesheet + "\";\n");
					}
					if (shouldSpecifyRankdir) {
						sb.append(" rankdir=" + rankdir + ";\n");
					}
					if (shouldColor) {
						sb.append(" node [style=filled,fillcolor=" + defaultColor + "]\n");
					}
				}
				else if (isCluster(node)) {
					sb.append(tab + "subgraph ");
					nodeId = GV_CLUSTER + nodeId;
					sb.append(nodeId);
					sb.append(" {");
					if (nodeLabel != null) {
						sb.append(" label=" + nodeLabel);
					}
					sb.append("\n");
				}
				else {
					// this is not a cluster (ex: Petri net Transition node)
					this.writeNonClusterNode(node, level, nodeId, nodeLabel, tab);
				}
				IXholon childNode = node.getFirstChild();
				while (childNode != null) {
					writeNode(childNode, level+1);
					childNode = childNode.getNextSibling();
				}
				if (isCluster(node) || node == root) {
					writeLinks(node, nodeId, tab);
					sb.append(tab + "}\n");
				}
			}
			else { // this node is not a cluster or root
				this.writeNonClusterNode(node, level, nodeId, nodeLabel, tab);
				/*sb.append(tab + nodeId);
				if (nodeLabel != null) {
					sb.append(" [label=" + nodeLabel + "]");
				}
				sb.append("\n");
				String colorStr = makeColor(node);
				if (colorStr != null) {
					sb.append(tab + " [" + colorStr + "]\n");
				}
				writeLinks(node, nodeId, tab);*/
			}
			
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
	}
	
	/**
	 * Write a node that is not a cluster and not root.
	 * @param node
	 * @param level
	 * @param nodeId
	 * @param nodeLabel
	 * @param tab
	 */
	protected void writeNonClusterNode(IXholon node, int level, String nodeId, String nodeLabel, String tab) {
		//try {
			sb.append(tab + nodeId);
			if (nodeLabel != null) {
				sb.append(" [label=" + nodeLabel + "]");
			}
			sb.append("\n");
			String colorStr = makeColor(node);
			if (colorStr != null) {
				sb.append(tab + " [" + colorStr + "]\n");
			}
			String shapeStr = makeShape(node);
			if (shapeStr != null) {
				sb.append(tab + " [" + shapeStr + "]\n");
			}
			writeLinks(node, nodeId, tab);
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
	}
	
	/**
	 * Write edges from this node to any others, where Xholon has connected ports.
	 * @param node The current node.
	 */
	@SuppressWarnings("unchecked")
	protected boolean writeLinks(IXholon node, String nodeId, String tab) {
		if (shouldShowLinks == false) {return false;}
		tab += " ";
		boolean rc = false;
		int nodeNum = 0;
		List<PortInformation> portList = node.getAllPorts();
		//System.out.println("Xholon2Graphviz writeLinks() " + portList.size());
		for (int i = 0; i < portList.size(); i++) {
			if (writeLink(nodeNum, node, nodeId, (PortInformation)portList.get(i), "", false, tab)) {
				rc = true;
			}
			nodeNum++;
		}
		// process edges where node is the first common ancestor
		if (edgeMap != null) {
			String value = (String)edgeMap.get(node);
			if (value != null) {
				//try {
					sb.append(tab + value + "\n");
				//} catch (IOException e) {
				//	Xholon.getLogger().error("", e);
				//}
			}
		}
		return rc;
	}
	
	/**
	 * Write one link.
	 * @param nodeNum Number of this node, used to distinguish multiple links between the same nodes.
	 * @param node The node where the link originates.
	 * @param nodeId
	 * @param portInfo Information about the port that represents the link.
	 * @param linkLabel An optional label for the link.
	 * It must be a fully formatted label preceeded by a blank, or a zero-length String.
	 * @param reverseArrows Whether or not to reverse the direction of the arrows.
	 * @param tab
	 */
	protected boolean writeLink(int nodeNum, IXholon node, String nodeId, final PortInformation portInfo,
			String linkLabel, boolean reverseArrows, String tab) {
	  //System.out.println("Xholon2Graphviz writeLink() " + portInfo);
		if (portInfo == null) {return false;}
		boolean rc = true;
		IXholon remoteNode = portInfo.getReffedNode();
		if (remoteNode == null) {return false;}
		if (!remoteNode.hasAncestor(root.getName())) {
			// remoteNode is outside the scope (not a descendant) of root
			return false;
		}
		String remoteNodeId = makeNodeId(remoteNode);
		if (isCluster(remoteNode)) {
			remoteNodeId = GV_CLUSTER + remoteNodeId;
		}
		StringBuilder sbEdge = new StringBuilder();
		if (reverseArrows) {
			sbEdge.append(remoteNodeId).append(" ").append(edgeOp).append(" ").append(nodeId).append(linkLabel).append(";");
		}
		else {
			sbEdge.append(nodeId).append(" ").append(edgeOp).append(" ").append(remoteNodeId).append(linkLabel).append(";");
		}
		String edgeStr = sbEdge.toString();
		if ((node.getParentNode() == remoteNode.getParentNode())
				|| (remoteNode.hasAncestor(node.getName()))) {
			// node and remoteNode are siblings, or remoteNode is a descendent of node
			//try {
				sb.append(tab + edgeStr + "\n");
			//} catch (IOException e) {
			//	Xholon.getLogger().error("", e);
			//	rc = false;
			//}
		}
		else {
			if (edgeMap == null) {
				edgeMap = new HashMap<IXholon, String>();
			}
			// find first common ancestor
			IXholon fca = findFirstCommonAncestor(node, remoteNode);
			if (fca == null) {return false;}
			// if common ancestor is not in edgeMap, then add it
			// add or append edgeStr to the edgeMap entry
			String value = (String)edgeMap.get(fca);
			if (value == null) {
				edgeMap.put(fca, edgeStr);
			}
			else {
				value += " " + edgeStr;
				edgeMap.put(fca, value);
			}
			rc = false;
		}
		return rc;
	}
	
	/**
	 * Try to make a custom color for a node.
	 * The XholonClass or Mechanism may define a color.
	 * @param node
	 * @return A color string, or null.
	 */
	protected String makeColor(IXholon node) {
		IXholon xhcNode = node.getXhc();
		if (shouldColor) {
			String color = null;
			while ((xhcNode != null) && (xhcNode instanceof IXholonClass)) {
				color = ((IDecoration)xhcNode).getColor();
				if (color == null) {
					color = ((IDecoration)((IXholonClass)xhcNode).getMechanism()).getColor();
				}
				if (color != null) {
					break;
				}
				xhcNode = xhcNode.getParentNode();
			}
			if ((color != null) && (color.length() > 2)) {
				// convert 0x2E8B57 to #2E8B57
				if (color.startsWith("0x")) {
					color = "#" + color.substring(2);
				}
				return "fillcolor=\"" + color + "\"";
			}
		}
		return null;
	}
	
	/**
	 * Try to make a custom shape for a node.
	 * @param node
	 * @return A shape string, or null.
	 */
	protected String makeShape(IXholon node) {
		return null;
	}
	
	/**
	 * Find the first common ancestor of two nodes.
	 * @param nodeA
	 * @param nodeB
	 * @return A common ancestor, or null.
	 */
	protected IXholon findFirstCommonAncestor(IXholon nodeA, IXholon nodeB) {
		IXholon pA = nodeA.getParentNode();
		while (pA != null) {
			IXholon pB = nodeB;
			while (pB != null) {
				if (pA == pB) {
					return pA;
				}
				if (pB == root) {break;}
				pB = pB.getParentNode();
			}
			if (pA == root) {break;}
			pA = pA.getParentNode();
		}
		return null;
	}
	
	/**
	 * Make a node ID.
	 * @param node
	 * @return
	 */
	protected String makeNodeId(IXholon node) {
		return node.getName(nameTemplateNodeId);
	}
	
	/**
	 * Make a node label.
	 * @param node
	 * @return A node label, or null if the node should not have a label.
	 */
	protected String makeNodeLabel(IXholon node) {
		if (maxLabelLen == 0) {
			return null;
		}
		String label = node.getName(nameTemplateNodeLabel);
		if (maxLabelLen > GV_MAXLABELLEN_NULL) {
			if (maxLabelLen < label.length()) {
				label = label.substring(0, maxLabelLen);
			}
		}
		return label;
	}
	
	/**
	 * Is this a cluster node?
	 * If a node is a cluster, then the GraphViz
	 * "layout engine will do the layout so that the nodes belonging to the cluster
	 * are drawn together, with the entire drawing of the cluster contained within
	 * a bounding rectangle"
	 * @param node
	 * @return
	 */
	protected boolean isCluster(IXholon node) {
		if (node.hasChildNodes() && (node != root)) {
			return true;
		}
		return false;
	}

	public String getGvFileExt() {
		return gvFileExt;
	}

	public void setGvFileExt(String gvFileExt) {
		this.gvFileExt = gvFileExt;
	}

	public String getGvGraph() {
		return gvGraph;
	}

	public void setGvGraph(String gvGraph) {
		this.gvGraph = gvGraph;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getEdgeOp() {
		return edgeOp;
	}

	public void setEdgeOp(String edgeOp) {
		this.edgeOp = edgeOp;
	}

	public boolean isShouldShowStateMachineEntities() {
		return shouldShowStateMachineEntities;
	}

	public void setShouldShowStateMachineEntities(
			boolean shouldShowStateMachineEntities) {
		this.shouldShowStateMachineEntities = shouldShowStateMachineEntities;
	}

	public String getNameTemplateNodeId() {
		return nameTemplateNodeId;
	}

	public void setNameTemplateNodeId(String nameTemplateNodeId) {
		this.nameTemplateNodeId = nameTemplateNodeId;
	}

	public String getNameTemplateNodeLabel() {
		return nameTemplateNodeLabel;
	}

	public void setNameTemplateNodeLabel(String nameTemplateNodeLabel) {
		this.nameTemplateNodeLabel = nameTemplateNodeLabel;
	}

	public boolean isShouldQuoteLabels() {
		return shouldQuoteLabels;
	}

	public void setShouldQuoteLabels(boolean shouldQuoteLabels) {
		this.shouldQuoteLabels = shouldQuoteLabels;
	}

	public boolean isShouldShowLinks() {
		return shouldShowLinks;
	}

	public void setShouldShowLinks(boolean shouldShowLinks) {
		this.shouldShowLinks = shouldShowLinks;
	}

	public boolean isShouldSpecifyLayout() {
		return shouldSpecifyLayout;
	}

	public void setShouldSpecifyLayout(boolean shouldSpecifyLayout) {
		this.shouldSpecifyLayout = shouldSpecifyLayout;
	}

	public int getMaxLabelLen() {
		return maxLabelLen;
	}

	public void setMaxLabelLen(int maxLabelLen) {
		this.maxLabelLen = maxLabelLen;
	}

	public boolean isShouldColor() {
		return shouldColor;
	}

	public void setShouldColor(boolean shouldColor) {
		this.shouldColor = shouldColor;
	}

	public String getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(String defaultColor) {
		this.defaultColor = defaultColor;
	}

	public boolean isShouldSpecifyStylesheet() {
		return shouldSpecifyStylesheet;
	}

	public void setShouldSpecifyStylesheet(boolean shouldSpecifyStylesheet) {
		this.shouldSpecifyStylesheet = shouldSpecifyStylesheet;
	}

	public String getStylesheet() {
		return stylesheet;
	}

	public void setStylesheet(String stylesheet) {
		this.stylesheet = stylesheet;
	}

	public boolean isShouldSpecifyRankdir() {
		return shouldSpecifyRankdir;
	}

	public void setShouldSpecifyRankdir(boolean shouldSpecifyRankdir) {
		this.shouldSpecifyRankdir = shouldSpecifyRankdir;
	}

	public String getRankdir() {
		return rankdir;
	}

	public void setRankdir(String rankdir) {
		this.rankdir = rankdir;
	}
	
}

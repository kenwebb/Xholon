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

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInformation;
//import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;
//import org.primordion.xholon.util.MiscIo;

/**
 * Export a Xholon model in Nwb format.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on February 12, 2010)
 * @see <a href="http://nwb.slis.indiana.edu">Network Workbench website</a>
 */
@SuppressWarnings("serial")
public class Xholon2Nwb extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
	
	/**
	 * In the NWB format, a node id of zero is not allowed.
	 * It's replaced with this value.
	 */
	private static final int NODEID_ZERO_REPLACEMENT = Integer.MAX_VALUE;
	
	/**
	 * The maximum number of community level columns, if community levels are shown.
	 */
	private static final int MAX_COMMUNITY_LEVELS = 4;
	
	/**
	 * The minimum number of community level columns, if community levels are shown.
	 */
	private static final int MIN_COMMUNITY_LEVELS = 1;
	
	private String outFileName;
	private String outPath = "./ef/nwb/";
	private String modelName;
	private IXholon root;
	//private Writer out;
	private StringBuilder sb;
	
	/** Current date and time. */
	private Date timeNow;
	private long timeStamp;
	
	/** Whether or not to draw links between nodes. */
	private boolean shouldShowLinks = true;
	
	/** Whether or not to show state machine nodes. */
	private boolean shouldShowStateMachineEntities = false;
	
	/** Template to use when writing out node names. */
	protected String nameTemplate = "r:C^^^";
	
	/**
	 * Whether or not to generate node columns that show community level.
	 * If community level is shown, it allows the Circular Hierarchy algorithm to be used in NWB.
	 */
	private boolean shouldShowCommunityLevels = true;
	
	/**
	 * The actual number of community levels to use.
	 */
	private int communityLevels = MIN_COMMUNITY_LEVELS;

	/**
	 * Constructor.
	 */
	public Xholon2Nwb() {}
	
	@Override
	public String getVal_String() {
	  return sb.toString();
	}
	
	/*
	 * @see org.primordion.xholon.io.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
	 */
	public boolean initialize(String mmFileName, String modelName, IXholon root) {
		timeNow = new Date();
		timeStamp = timeNow.getTime();
		if (mmFileName == null) {
			this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".nwb";
		}
		else {
			this.outFileName = mmFileName;
		}
		this.modelName = modelName;
		this.root = root;
		if (shouldShowCommunityLevels) {
			// don't include root itself or the root's immediate children as a level
			communityLevels = root.getNumLevels() - 1;
			//println(communityLevels);
			if (communityLevels < MIN_COMMUNITY_LEVELS) {
				communityLevels = MIN_COMMUNITY_LEVELS;
			}
			if (communityLevels > MAX_COMMUNITY_LEVELS) {
				communityLevels = MAX_COMMUNITY_LEVELS;
			}
		}
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
			sb.append("# To view this file, download open-source Network Workbench from http://nwb.slis.indiana.edu\n");
			sb.append(
				"#\n# Automatically generated by Xholon version 0.8.1, using Xholon2Nwb.java\n"
				+ "# " + new Date() + " " + timeStamp + "\n"
				+ "# model: " + modelName + "\n"
				+ "# www.primordion.com/Xholon\n\n");
			writeNodes(root, 0);
			writeEdges(root, 0);
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
	 * Write information about each node.
	 * @param node
	 * @param level
	 */
	protected void writeNodes(IXholon node, int level)
	{
		//try {
			sb.append("*Nodes\n");
			sb.append("id*int	label*string");
			if (shouldShowCommunityLevels) {
				for (int i = 0; i < communityLevels; i++) {
					sb.append("	blondel_community_level_" + i + "*string");
				}
			}
			sb.append("\n");
			writeNode(node, level);
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
	}
	
	/**
	 * Write information about the edges between nodes.
	 * @param node
	 * @param level
	 */
	protected void writeEdges(IXholon node, int level)
	{
		//try {
			sb.append("*DirectedEdges\n");
			sb.append("source*int	target*int\n");
			writeEdge(node, level);
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
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
		//try {
			sb.append("" + getNodeId(node) + " \"" + node.getName(nameTemplate) + "\"");
			if (shouldShowCommunityLevels) {
				int counter = communityLevels;
				IXholon communityNode = node;
				while (communityNode != null) {
					if (communityNode.isRootNode()) {
						sb.append(" \"community_" + getNodeId(communityNode) + "\"");
					}
					else {
						sb.append(" \"community_" + getNodeId(communityNode.getParentNode()) + "\"");
						communityNode = communityNode.getParentNode();
					}
					counter--;
					if (counter <= 0) {
						break;
					}
				}
			}
			sb.append("\n");
			
			// children
			IXholon childNode = node.getFirstChild();
			while (childNode != null) {
				writeNode(childNode, level+1);
				childNode = childNode.getNextSibling();
			}
			
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
	}
	
	/**
	 * Write all edges for one node.
	 * @param node The current node in the Xholon hierarchy.
	 * @param level Current level in the hierarchy.
	 */
	protected void writeEdge(IXholon node, int level) {
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
	}
	
	/**
	 * Write links from this node to any others, where Xholon has connected ports.
	 * @param node The current node.
	 */
	@SuppressWarnings("unchecked")
	protected void writeLinks(IXholon node)
	{
		if (shouldShowLinks == false) {return;}
		List<PortInformation> portList = node.getAllPorts();
		for (int i = 0; i < portList.size(); i++) {
			writeLink(node, portList.get(i));
		}
	}
	
	/**
	 * Write one link.
	 * @param node The node where the link originates.
	 * @param portInfo Information about the port that represents the link.
	 */
	protected void writeLink(IXholon node, PortInformation portInfo)
	{
		if (portInfo == null) {return;}
		IXholon remoteNode = portInfo.getReffedNode();
		//try {
			sb.append("" + getNodeId(node) + " " + getNodeId(remoteNode) + "\n");
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
	}
	
	/**
	 * Get the node's id.
	 * In the NWB format, an id must be > 0.
	 * @param node
	 * @return An int > 0.
	 */
	protected int getNodeId(IXholon node) {
		int nodeId = node.getId();
		if (nodeId == 0) {
			nodeId = NODEID_ZERO_REPLACEMENT;
		}
		return nodeId;
	}
	
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

	public String getOutPath() {
		return outPath;
	}

	public void setOutPath(String outPath) {
		this.outPath = outPath;
	}

	/*public Writer getOut() {
		return out;
	}

	public void setOut(Writer out) {
		this.out = out;
	}*/

	public boolean isShouldShowCommunityLevels() {
		return shouldShowCommunityLevels;
	}

	public void setShouldShowCommunityLevels(boolean shouldShowCommunityLevels) {
		this.shouldShowCommunityLevels = shouldShowCommunityLevels;
	}

	public int getCommunityLevels() {
		return communityLevels;
	}

	public void setCommunityLevels(int communityLevels) {
		this.communityLevels = communityLevels;
	}
}

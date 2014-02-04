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
import org.primordion.xholon.base.XPath;
//import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;
//import org.primordion.xholon.util.MiscIo;

/**
 * Export a Xholon model in ExTraVis format.
 * <p>TODO</p>
 * <ul>
 * <li>Insert a value for "Relations". This must currently be done manually.</li>
 * <li>If there are state machines, the counts may be wrong.</li>
 * </ul>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on February 16, 2010)
 * @see <a href="http://www.win.tue.nl/~dholten/extravis/index.htm">ExTraVis website</a>
 */
@SuppressWarnings("serial")
public class Xholon2Extravis extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
	
	private static final int DUMMY_INTEGER_VALUE = 9;
	
	private static final int ELEM_TYPE = 0;
	
	private String outFileName;
	private String outPath = "./ef/extravis/";
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
	
	/** An instance of the Xholon XPath class. */
	private XPath xpath = null;
	
	/** The number of elements in the hierarchy, excluding the root. */
	private int numHierarchyElements = 0;
	
	/** This number is calculated, and must be manually inseted into the created file. */
	//private int numRelations = 0;
	
	/**
	 * The number of digits in the maximum ID.
	 * examples:
	 * An id of 5 has 1 digit.
	 * An id of 42 has 2 digits.
	 * An id of 8309 has 4 digits.
	 */
	private int numDigits = 0;
	
	/**
	 * Constructor.
	 */
	public Xholon2Extravis() {}
	
	/*
	 * @see org.primordion.xholon.io.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
	 */
	public boolean initialize(String mmFileName, String modelName, IXholon root) {
		timeNow = new Date();
		timeStamp = timeNow.getTime();
		if (mmFileName == null) {
			this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".initial.rsf";
		}
		else {
			this.outFileName = mmFileName;
		}
		this.modelName = modelName;
		this.root = root;
		xpath = new XPath();
		numHierarchyElements = root.getNumChildren(true);
		// calculate numDigits
		numDigits = Integer.toString(root.getId() + numHierarchyElements).length();
		
		return true;
	}
	
	/*
	 * @see org.primordion.xholon.io.IXholon2ExternalFormat#writeAll()
	 */
	public void writeAll() {
		/*boolean shouldClose = true;
		if (root.getApp().isUseAppOut()) {
			out = root.getApp().getOut();
			sb = new StringBuilder(1000);
			shouldClose = false;
		}
		else {
			try {
				// create any missing output directories
				File dirOut = new File(outPath);
				dirOut.mkdirs(); // will create a new directory only if there is no existing one
				out = MiscIo.openOutputFile(outFileName);
				sb = new StringBuilder(1000);
			} catch(AccessControlException e) {
				//out = new PrintWriter(System.out);
				out = root.getApp().getOut();
				sb = new StringBuilder(1000);
				shouldClose = false;
			}
		}*/
		sb = new StringBuilder(1000);
		writeHeader(root);
		writeHierarchyElements(root, 0); // H
		writeParentChildRelations(root, 0); // PCR
		writeSignatures(root, 0); // S
		writeRelations(root, 0); // R
		//try {
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
	 * Write header lines at top of file.
	 * @param node
	 */
	protected void writeHeader(IXholon node)
	{
		//try {
			sb.append("\"LevelSeparator\" \"/\"\n");
			sb.append("\"ElmType\" \"" + ELEM_TYPE + "\" \"Node\"\n");
			sb.append("\"RelType\" \"0\" \"CallDynamicChronologic\" \"int:Increment\" \"sig:Signature\" \"string:Method\"\n");
			sb.append("\"HierarchyDepth\" \"" + (node.getNumLevels()+1) + "\"\n");
			sb.append("\"HierarchyElements\" \"" + (numHierarchyElements+1) + "\"\n");
			sb.append("\"ParentChildRelations\" \"" + numHierarchyElements + "\"\n");
			sb.append("\"Signatures\" \"1\"\n");
			sb.append("\"Relations\" \"\"\n"); // TODO
			sb.append("\"Root\" \"" + node.getId() + "\"\n");
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
	}
	
	/**
	 * Write information about each hierarchy element (H).
	 * @param node
	 * @param level
	 */
	protected void writeHierarchyElements(IXholon node, int level)
	{
		writeHierarchyElement(node, level);
	}
	
	/**
	 * Write information about each parent child relation (PCR).
	 * @param node
	 * @param level
	 */
	protected void writeParentChildRelations(IXholon node, int level)
	{
		writeParentChildRelation(node, level);
	}
	
	/**
	 * Write signature information.
	 * @param node
	 * @param level
	 */
	protected void writeSignatures(IXholon node, int level)
	{
		//try {
			sb.append("\"S\" \"0\" \"DummySignature\"\n");
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
	}
	
	/**
	 * Write information about the relations among hierarchy elements (R).
	 * @param node
	 * @param level
	 */
	protected void writeRelations(IXholon node, int level)
	{
		writeRelation(node, level);
		//System.out.println("numRelations:" + numRelations);
	}
	
	/**
	 * Write one node, and its child nodes.
	 * @param node The current node in the Xholon hierarchy.
	 * @param level Current level in the hierarchy.
	 */
	protected void writeHierarchyElement(IXholon node, int level) {
		// only show state machine nodes if should show them, or if root is a StateMachineCE
		if ((node.getXhcId() == CeStateMachineEntity.StateMachineCE)
				&& (shouldShowStateMachineEntities == false)
				&& (level > 0)) {
			return;
		}
		//try {
			sb.append("\"H\" \"" + node.getId() + "\" \""
					+ getHPrefix(node)
					+ xpath.getExpression(node, root, false) + "\" \"" + ELEM_TYPE + "\"");
			sb.append("\n");
			
			// children
			IXholon childNode = node.getFirstChild();
			while (childNode != null) {
				writeHierarchyElement(childNode, level+1);
				childNode = childNode.getNextSibling();
			}
			
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
	}
	
	/**
	 * Get a prefix for a HierarchyElement,
	 * to synchronize the sort order of the ID and the name.
	 * @param node
	 * @return
	 */
	protected String getHPrefix(IXholon node) {
		String hPrefix = Integer.toString(node.getId());
		StringBuilder sb = new StringBuilder();
		// append leading zeros
		for (int i = 0; i < numDigits - hPrefix.length(); i++) {
			sb.append("0");
		}
		return sb + hPrefix;
	}
	
	/**
	 * Write one node, and its child nodes.
	 * @param node The current node in the Xholon hierarchy.
	 * @param level Current level in the hierarchy.
	 */
	protected void writeParentChildRelation(IXholon node, int level) {
		// only show state machine nodes if should show them, or if root is a StateMachineCE
		if ((node.getXhcId() == CeStateMachineEntity.StateMachineCE)
				&& (shouldShowStateMachineEntities == false)
				&& (level > 0)) {
			return;
		}
		//try {
			if (node != root) {
				sb.append("\"PCR\" \"" + node.getParentNode().getId() + "\" \"" + node.getId() + "\"");
				sb.append("\n");
			}
			
			// children
			IXholon childNode = node.getFirstChild();
			while (childNode != null) {
				writeParentChildRelation(childNode, level+1);
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
	protected void writeRelation(IXholon node, int level) {
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
			writeRelation(childNode, level+1);
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
			sb.append("\"R\" \"" + node.getId() + "\" \"" + remoteNode.getId() + "\" \"" + ELEM_TYPE + "\""
					+ " \"" + DUMMY_INTEGER_VALUE + "\"" + " \"0\"" + " \"DummyString\""  + "\n");
			//numRelations++;
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
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

}

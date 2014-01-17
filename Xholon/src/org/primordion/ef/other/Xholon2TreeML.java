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

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;
//import org.primordion.xholon.util.MiscIo;

/**
 * Export a Xholon model in TreeML format.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on February 12, 2010)
 * @see <a href="http://nwb.slis.indiana.edu">Network Workbench website</a>
 * @see <a href="http://cs.marlboro.edu/courses/fall2006/tutorials/information_visualization/TreeML">TreeML website</a>
 */
@SuppressWarnings("serial")
public class Xholon2TreeML extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
	
	private String outFileName;
	private String outPath = "./ef/treeml/";
	private String modelName;
	private IXholon root;
	//private Writer out;
	private StringBuilder sb;
	
	/** Current date and time. */
	private Date timeNow;
	private long timeStamp;
	
	/** Whether or not to show state machine nodes. */
	private boolean shouldShowStateMachineEntities = false;
	
	/** Template to use when writing out node names. */
	protected String nameTemplate = "r:C^^^";

	/** Whether or not to include a &lt;declarations element at the top. */
	private boolean shouldShowDeclarations = true; 
	
	/**
	 * Constructor.
	 */
	public Xholon2TreeML() {}
	
	/*
	 * @see org.primordion.xholon.io.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
	 */
	public boolean initialize(String mmFileName, String modelName, IXholon root) {
		timeNow = new Date();
		timeStamp = timeNow.getTime();
		if (mmFileName == null) {
			this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".xml";
		}
		else {
			this.outFileName = mmFileName;
		}
		this.modelName = modelName;
		this.root = root;
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
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			sb.append("<!DOCTYPE tree SYSTEM \"http://www.nomencurator.org/InfoVis2003/download/treeml.dtd\">\n"); // optional DTD
			sb.append("<!-- To view this file, download open-source Network Workbench from http://nwb.slis.indiana.edu -->\n");
			sb.append(
				"<!--\nAutomatically generated by Xholon version 0.8.1, using Xholon2TreeML.java\n"
				+ new Date() + " " + timeStamp + "\n"
				+ "model: " + modelName + "\n"
				+ "www.primordion.com/Xholon\n-->\n");
			sb.append("<tree>\n");
			writeDeclarations();
			writeNode(root, 0); // root is level 0
			sb.append("</tree>\n");
			writeToTarget(sb.toString(), outFileName, outPath, root);
			//out.write(sb.toString());
			//out.flush();
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
		//if (shouldClose) {
		//	MiscIo.closeOutputFile(out);
		//}
	}
	
	
	protected void writeDeclarations() {
		if (!shouldShowDeclarations) {return;}
		//try {
			sb.append("<declarations>\n");
			sb.append("<attributeDecl name=\"label\" type=\"String\"/>\n");
			sb.append("</declarations>\n");
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
		String nodeName = "leaf";
		if (node.hasChildNodes()) {
			nodeName = "branch";
		}
		//try {
			sb.append("<" + nodeName + ">\n");
			writeAttributeNodes(node, level+1);
			
			// children
			IXholon childNode = node.getFirstChild();
			while (childNode != null) {
				writeNode(childNode, level+1);
				childNode = childNode.getNextSibling();
			}
			
			sb.append("</" + nodeName + ">\n");
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
	}
	
	protected void writeAttributeNodes(IXholon node, int level) {
		//try {
			sb.append("<attribute name=\"label\" value=\"" + node.getName(nameTemplate) + "\"/>\n");
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
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

	public boolean isShouldShowDeclarations() {
		return shouldShowDeclarations;
	}

	public void setShouldShowDeclarations(boolean shouldShowDeclarations) {
		this.shouldShowDeclarations = shouldShowDeclarations;
	}
}

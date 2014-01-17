/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2007, 2008 Ken Webb
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
import java.util.List;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;
//import org.primordion.xholon.util.MiscIo;
import org.primordion.xholon.util.StringHelper;

/**
 * Export a Xholon model in a mind map (FreeMind) format.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.7.1 (Created on October 12, 2007)
 * @see <a href="http://freemind.sourceforge.net/wiki/index.php/Main_Page">FreeMind</a>
 */
@SuppressWarnings("serial")
public class Xholon2MindMap extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
	
	private String mmFileName;
	private String modelName;
	private IXholon root;
	//private Writer mmOut;
	private StringBuilder mmSb;
	
	/** Current date and time. */
	private Date timeNow;
	private long timeStamp;
	
	/** Maximum hierarchy level at which nodes should still be unfolded (displayed by default). */
	private int maxFoldLevel = 0;
	
	/** Whether or not to draw links between nodes on the mind map. */
	private boolean shouldShowLinks = true;
	
	/** Color to use in drawing link arrows. */
	private String linkColor = "#6666ff";
	
	/** Color to use in drawing reverse link arrows. */
	private String reverseLinkColor = "#6666ff";
	
	/** Whether or not to show state machine nodes. */
	private boolean shouldShowStateMachineEntities = false;
	
	/** Whether or not to draw clouds around container nodes. */
	private boolean shouldShowClouds = true;
	
	/** Cloud fill color (default: "white"). */
	private String cloudFillColor = "#ffffff"; // white
	
	/** Alternate cloud fill color (default: ""). */
	private String cloudFillColorAlternate = "#f8fff4"; // light greenish
	
	/** Template to use when writing out node names. */
	protected String nameTemplate = "r:C^^^";
	
	/** Whether or not to split a camel case Xholon class name into multiple words. */
	protected boolean shouldSplitCamelCase = true;
	
	/** Whether or not split camel case should be all lower case. */
	protected boolean shouldBeLowerCase = false;
	
	/** Whether or not split camel case should have first word capitalized and rest lower case. */
	protected boolean shouldBeCapitalized = true;
	
	/** Whether or not to include Xholon annotations in the mind map output. */
	protected boolean shouldShowAnnotations = true;

	/**
	 * Constructor.
	 */
	public Xholon2MindMap() {}
	
	/*
	 * @see org.primordion.ef.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
	 */
	public boolean initialize(String mmFileName, String modelName, IXholon root) {
	  timeNow = new Date();
		timeStamp = timeNow.getTime();
		if (mmFileName == null) {
			this.mmFileName = "./mm/" + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".mm";
		}
		else {
			this.mmFileName = mmFileName;
		}
		this.modelName = modelName;
		this.root = root;
		return true;
	}
	
	/*
	 * @see org.primordion.ef.IXholon2ExternalFormat#writeAll()
	 */
	public void writeAll() {
	  /* GWT
		boolean shouldClose = true;
		if (root.getApp().isUseAppOut()) {
			mmOut = root.getApp().getOut();
			mmSb = new StringBuilder(1000);
			shouldClose = false;
		}
		else {
			try {
				// create any missing output directories
				File dirOut = new File("./mm/");
				dirOut.mkdirs(); // will create a new directory only if there is no existing one
				mmOut = MiscIo.openOutputFile(mmFileName);
				mmSb = new StringBuilder(1000);
			} catch(AccessControlException e) {
				//mmOut = new PrintWriter(System.out);
				mmOut = root.getApp().getOut();
				mmSb = new StringBuilder(1000);
				shouldClose = false;
			}
		}*/
		mmSb = new StringBuilder();
		//try {
		  mmSb.append("<map version=\"0.9.0\">\n");
			mmSb.append("<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->\n");
			mmSb.append(
				"<!--\nAutomatically generated by Xholon version 0.8, using Xholon2MindMap.java\n"
				+ new Date() + " " + timeStamp + "\n"
				+ "model: " + modelName + "\n"
				+ "www.primordion.com/Xholon\n-->\n");
			writeNode(root, 0); // root is level 0
			mmSb.append("</map>\n");
			//mmOut.write(mmSb.toString());
			//mmOut.flush();
			writeToTarget(mmSb.toString(), mmFileName, "./mm/", root);
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
		//if (shouldClose) {
		//	MiscIo.closeOutputFile(mmOut);
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
		// write out attributes in alphabetical order
		//try {
		  mmSb.append("<node");
			mmSb.append(" CREATED=\"" + timeStamp + "\"");
			writeFolded(node, level);
			mmSb.append(" ID=\"" + node.getId() + "\"");
			//mmSb.append("<node" + " MODIFIED=\"" + timeStamp + "\""); // probably never necessary
			// all nodes leading out of the root node should be positioned to the right
			if (level == 1) {
				mmSb.append(" POSITION=\"right\"");
			}
			String text = node.getName(nameTemplate);
			if (this.shouldSplitCamelCase) {
			  text = this.splitCamelCase(text, " ");
				if (shouldBeLowerCase) {
				  text = text.toLowerCase();
				}
				else if (shouldBeCapitalized) {
				  text = text.charAt(0) + text.substring(1).toLowerCase();
				}
			}
			
			if (shouldShowAnnotations && node.hasAnnotation()) {
			  mmSb.append(">\n"); // end of the start node tag
				mmSb.append(includeAnnotation(node, text) + "\n");
			}
			else {
				mmSb.append(" TEXT=\"" + text + "\"");
				mmSb.append(">\n"); // end of the start node tag
			}
			
			writeCloud(node, level);
			writeLinks(node);
			
			// children
			IXholon childNode = node.getFirstChild();
			while (childNode != null) {
				writeNode(childNode, level+1);
				childNode = childNode.getNextSibling();
			}
			
			mmSb.append("</node>\n");
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
	}
	
	/**
	 * Include Xholon Annotation text.
	 * @param node The current Xholon node.
	 * @param text The non-Annotation text.
	 * @return The original text plus the Annotation text.
	 */
	protected String includeAnnotation(IXholon node, String text) {
	  String htmlPrefix = "<richcontent TYPE=\"NODE\"><html><head></head><body><p>\n";
	  String htmlInfix = " <font size=\"2\">";
	  String htmlSuffix = "</font>\n</p></body></html></richcontent>";
	  return htmlPrefix + text + htmlInfix + node.getAnnotation() + htmlSuffix;
	}
	
	/**
	 * Write FOLDED attribute, or not.
	 * @param node The current node in the Xholon hierarchy.
	 * @param level Current level in the hierarchy.
	 */
	protected void writeFolded(IXholon node, int level) {
		// all nodes should be folded, except for the first level, and for lead nodes
		if ((level > maxFoldLevel) && (node.hasChildNodes())) {
			//try {
				mmSb.append(" FOLDED=\"true\"");
			//} catch (IOException e) {
			//	Xholon.getLogger().error("", e);
			//}
		}
	}
	
	/**
	 * Optionally, draw a cloud around the current node and its children.
	 * ex: &lt;cloud COLOR="#ccffcc"/&gt;
	 * @param node The current node in the Xholon hierarchy.
	 * @param level Current level in the hierarchy.
	 */
	protected void writeCloud(IXholon node, int level)
	{
		if (!shouldShowClouds) {return;}
		if (!node.isContainer()) {return;}
		if (!node.hasChildNodes()) {return;}
		if (!hasDomainChildNodes(node)) {return;}
		String fill = cloudFillColor;
		if (level % 2 == 0) {
			fill = cloudFillColorAlternate;
		}
		//try {
			mmSb.append("<cloud");
			mmSb.append(" COLOR=\"" + fill + "\"");
			mmSb.append("/>\n"); // end of the start cloud tag
		//} catch (IOException e) {
		//	Xholon.getLogger().error("", e);
		//}
	}
	/**
	 * Write links from this node to any others, where Xholon has connected ports.
	 * @param node The current node.
	 */
	@SuppressWarnings("unchecked")
	protected void writeLinks(IXholon node)
	{
	  if (shouldShowLinks == false) {return;}
		int nodeNum = 0;
		List<PortInformation> portList = node.getAllPorts();
		for (int i = 0; i < portList.size(); i++) {
			writeLink(nodeNum, node, (PortInformation)portList.get(i), false);
			nodeNum++;
		}
	}
	
	/**
	 * Write one link.
	 * @param nodeNum Number of this node, used to distinguish multiple links between the same nodes.
	 * @param node The node where the link originates.
	 * @param portInfo Information about the port that represents the link.
	 * @param reverseArrows Whether or not to reverse the direction of the arrows.
	 */
	protected void writeLink(int nodeNum, IXholon node, PortInformation portInfo, boolean reverseArrows)
	{
		if (portInfo == null) {return;}
		IXholon remoteNode = portInfo.getReffedNode();
		//try {
			mmSb.append("<arrowlink");
			if (reverseArrows) {
				mmSb.append(" COLOR=\"" + reverseLinkColor + "\"");
			}
			else {
				mmSb.append(" COLOR=\"" + linkColor + "\"");
			}
			mmSb.append(" DESTINATION=\"" + remoteNode.getId() + "\"");
			if (reverseArrows) {
				mmSb.append(" ENDARROW=\"None\"");
			}
			else {
				mmSb.append(" ENDARROW=\"Default\"");
			}
			mmSb.append(" ID=\"" + node.getId() + remoteNode.getId() + nodeNum + "\"");
			if (reverseArrows) {
				mmSb.append(" STARTARROW=\"Default\"");
			}
			else {
				mmSb.append(" STARTARROW=\"None\"");
			}
			mmSb.append("/>\n"); // end of the start arrowlink tag
			nodeNum++;
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
	
	/**
	 * Convert camel case to human readable.
	 * @see http://stackoverflow.com/questions/2559759/how-do-i-convert-camelcase-into-human-readable-names-in-java
	 * TODO if there's already an "_", don't do anything
	 * @param s A string that may contain camel case (ex: HelloWorld).
	 * @param r The replacement character (ex: "_").
	 * @return A string with human-readable spaces (ex: Hello World).
	 */
	/*protected String splitCamelCase(String s, String r) {
	  // this code quietly fails in GWT production mode on all browsers
		return s.replaceAll(
		  StringHelper.format("%s|%s|%s",
		  "(?<=[A-Z])(?=[A-Z][a-z])",
		  "(?<=[^A-Z])(?=[A-Z])",
		  "(?<=[A-Za-z])(?=[^A-Za-z])"
		  ),
		  r
		);
	}*/
	protected native String splitCamelCase(String s, String r) /*-{
	  return s.charAt(0).toUpperCase() + s.substr(1).replace(/[A-Z]/g, ' $&');
	}-*/;
	
	public String getMmFileName() {
		return mmFileName;
	}

	public void setMmFileName(String mmFileName) {
		this.mmFileName = mmFileName;
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

	public int getMaxFoldLevel() {
		return maxFoldLevel;
	}

	public void setMaxFoldLevel(int maxFoldLevel) {
		this.maxFoldLevel = maxFoldLevel;
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

	public String getReverseLinkColor() {
		return reverseLinkColor;
	}

	public void setReverseLinkColor(String reverseLinkColor) {
		this.reverseLinkColor = reverseLinkColor;
	}

	public boolean isShouldShowStateMachineEntities() {
		return shouldShowStateMachineEntities;
	}

	public void setShouldShowStateMachineEntities(
			boolean shouldShowStateMachineEntities) {
		this.shouldShowStateMachineEntities = shouldShowStateMachineEntities;
	}

	public boolean isShouldShowClouds() {
		return shouldShowClouds;
	}

	public void setShouldShowClouds(boolean shouldShowClouds) {
		this.shouldShowClouds = shouldShowClouds;
	}

	public String getCloudFillColor() {
		return cloudFillColor;
	}

	public void setCloudFillColor(String cloudFillColor) {
		this.cloudFillColor = cloudFillColor;
	}

	public String getCloudFillColorAlternate() {
		return cloudFillColorAlternate;
	}

	public void setCloudFillColorAlternate(String cloudFillColorAlternate) {
		this.cloudFillColorAlternate = cloudFillColorAlternate;
	}
	
	public String getNameTemplate() {
		return nameTemplate;
	}

	public void setNameTemplate(String nameTemplate) {
		this.nameTemplate = nameTemplate;
	}

	public boolean isShouldSplitCamelCase() {
		return shouldSplitCamelCase;
	}

	public void setShouldSplitCamelCase(boolean shouldSplitCamelCase) {
		this.shouldSplitCamelCase = shouldSplitCamelCase;
	}

	public boolean isShouldBeLowerCase() {
		return shouldBeLowerCase;
	}

	public void setShouldBeLowerCase(boolean shouldBeLowerCase) {
		this.shouldBeLowerCase = shouldBeLowerCase;
	}

	public boolean isShouldBeCapitalized() {
		return shouldBeCapitalized;
	}

	public void setShouldBeCapitalized(boolean shouldBeCapitalized) {
		this.shouldBeCapitalized = shouldBeCapitalized;
	}

	public boolean isShouldShowAnnotations() {
		return shouldShowAnnotations;
	}

	public void setShouldShowAnnotations(boolean shouldShowAnnotations) {
		this.shouldShowAnnotations = shouldShowAnnotations;
	}
}

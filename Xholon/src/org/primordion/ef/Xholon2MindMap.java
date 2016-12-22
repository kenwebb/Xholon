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
//import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;
//import org.primordion.xholon.util.MiscIo;
//import org.primordion.xholon.util.StringHelper;

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
	//private int maxFoldLevel = 0;
	
	/** Whether or not to draw links between nodes on the mind map. */
	//private boolean shouldShowLinks = true;
	
	/** Color to use in drawing link arrows. */
	//private String linkColor = "#6666ff";
	
	/** Color to use in drawing reverse link arrows. */
	//private String reverseLinkColor = "#6666ff";
	
	/** Whether or not to show state machine nodes. */
	//private boolean shouldShowStateMachineEntities = false;
	
	/** Whether or not to draw clouds around container nodes. */
	//private boolean shouldShowClouds = true;
	
	/** Cloud fill color (default: "white"). */
	//private String cloudFillColor = "#ffffff"; // white
	
	/** Alternate cloud fill color (default: ""). */
	//private String cloudFillColorAlternate = "#f8fff4"; // light greenish
	
	/** Template to use when writing out node names. */
	//protected String nameTemplate = "r:C^^^";
	
	/** Whether or not to split a camel case Xholon class name into multiple words. */
	//protected boolean shouldSplitCamelCase = true;
	
	/** Whether or not split camel case should be all lower case. */
	//protected boolean shouldBeLowerCase = false;
	
	/** Whether or not split camel case should have first word capitalized and rest lower case. */
	//protected boolean shouldBeCapitalized = true;
	
	/** Whether or not to include Xholon annotations in the mind map output. */
	//protected boolean shouldShowAnnotations = true;
	
	/** Position nodes to the right or left of the Freemind root node. */
	//protected String position = "right";
	
	/** Freemind version: "0.9.0" "1.0.1" */
	//protected String version = "0.9.0";

	/**
	 * Constructor.
	 */
	public Xholon2MindMap() {}
	
	@Override
	public String getVal_String() {
	  return mmSb.toString();
	}
	
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
		  mmSb.append("<map version=\"").append(getVersion()).append("\">\n");
			mmSb.append("<!-- To view this file, download free mind mapping software:\n");
			mmSb.append(" FreeMind from http://freemind.sourceforge.net/wiki/index.php/Main_Page or\n");
			mmSb.append(" Freeplane from http://freeplane.sourceforge.net/wiki/index.php/Main_Page\n");
			mmSb.append(
				"\nAutomatically generated by Xholon version 0.9.1, using Xholon2MindMap.java\n"
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
				&& (isShouldShowStateMachineEntities() == false)
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
			// all nodes leading out of the root node can be positioned to the right or left
			if (level == 1) {
				mmSb.append(" POSITION=\"").append(getPosition()).append("\"");
			}
			String text = node.getName(getNameTemplate());
			if (this.isShouldSplitCamelCase()) {
			  text = this.splitCamelCase(text, " ");
				if (isShouldBeLowerCase()) {
				  text = text.toLowerCase();
				}
				else if (isShouldBeCapitalized()) {
				  text = text.charAt(0) + text.substring(1).toLowerCase();
				}
			}
			
			if (isShouldShowAnnotations() && node.hasAnnotation()) {
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
		if ((level > getMaxFoldLevel()) && (node.hasChildNodes())) {
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
		if (!isShouldShowClouds()) {return;}
		if (!node.isContainer()) {return;}
		if (!node.hasChildNodes()) {return;}
		if (!hasDomainChildNodes(node)) {return;}
		String fill = getCloudFillColor();
		if (level % 2 == 0) {
			fill = getCloudFillColorAlternate();
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
	  if (isShouldShowLinks() == false) {return;}
	  int nodeNum = 0;
		//List<PortInformation> portList = node.getAllPorts();
		List<PortInformation> portList = node.getLinks(false, true);
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
				mmSb.append(" COLOR=\"" + getReverseLinkColor() + "\"");
			}
			else {
				mmSb.append(" COLOR=\"" + getLinkColor() + "\"");
			}
			mmSb.append(" DESTINATION=\"" + remoteNode.getId() + "\"");
			if (isShouldLabelLinks()) {
			  mmSb.append(" MIDDLE_LABEL=\"" + portInfo.getFieldName() + "\"");
			}
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

  /**
   * Make a JavaScript object with all the parameters for this external format.
   */
  protected native void makeEfParams() /*-{
    var p = {};
    p.maxFoldLevel = 0;
    p.shouldShowLinks = true;
    p.shouldLabelLinks = false; // Freeplane can label links
    p.linkColor = "#6666ff";
    p.reverseLinkColor = "#6666ff";
    p.shouldShowStateMachineEntities = false;
    p.shouldShowClouds = true;
    p.cloudFillColor = "#ffffff";
    p.cloudFillColorAlternate = "#f8fff4";
    p.nameTemplate = "r:C^^^";
    p.shouldSplitCamelCase = true;
    p.shouldBeLowerCase = false;
    p.shouldBeCapitalized = true;
    p.shouldShowAnnotations = true;
    p.position = "right";
    p.version = "0.9.0";
    this.efParams = p;
  }-*/;

  /** Maximum hierarchy level at which nodes should still be unfolded (displayed by default). */
  public native int getMaxFoldLevel() /*-{return this.efParams.maxFoldLevel;}-*/;
  public native void setMaxFoldLevel(int maxFoldLevel) /*-{this.efParams.maxFoldLevel = maxFoldLevel;}-*/;

  /** Whether or not to draw links between nodes on the mind map. */
  public native boolean isShouldShowLinks() /*-{return this.efParams.shouldShowLinks;}-*/;
  //public native void setShouldShowLinks(boolean shouldShowLinks) /*-{this.efParams.shouldShowLinks = shouldShowLinks;}-*/;

  /** Whether or not to label links between nodes on the mind map. */
  public native boolean isShouldLabelLinks() /*-{return this.efParams.shouldLabelLinks;}-*/;
  //public native void setShouldLabelLinks(boolean shouldLabelLinks) /*-{this.efParams.shouldLabelLinks = shouldLabelLinks;}-*/;

  /** Color to use in drawing link arrows. */
  public native String getLinkColor() /*-{return this.efParams.linkColor;}-*/;
  public native void setLinkColor(String linkColor) /*-{this.efParams.linkColor = linkColor;}-*/;

  /** Color to use in drawing reverse link arrows. */
  public native String getReverseLinkColor() /*-{return this.efParams.reverseLinkColor;}-*/;
  public native void setReverseLinkColor(String reverseLinkColor) /*-{this.efParams.reverseLinkColor = reverseLinkColor;}-*/;

  /** Whether or not to show state machine nodes. */
  public native boolean isShouldShowStateMachineEntities() /*-{return this.efParams.shouldShowStateMachineEntities;}-*/;
  //public native void setShouldShowStateMachineEntities(boolean shouldShowStateMachineEntities) /*-{this.efParams.shouldShowStateMachineEntities = shouldShowStateMachineEntities;}-*/;

  /** Whether or not to draw clouds around container nodes. */
  public native boolean isShouldShowClouds() /*-{return this.efParams.shouldShowClouds;}-*/;
  public native void setShouldShowClouds(boolean shouldShowClouds) /*-{this.efParams.shouldShowClouds = shouldShowClouds;}-*/;

  /** Cloud fill color (default: "white"). */
  public native String getCloudFillColor() /*-{return this.efParams.cloudFillColor;}-*/;
  //public native void setCloudFillColor(String cloudFillColor) /*-{this.efParams.cloudFillColor = cloudFillColor;}-*/;

  /** Alternate cloud fill color (default: "") light greenish. */
  public native String getCloudFillColorAlternate() /*-{return this.efParams.cloudFillColorAlternate;}-*/;
  //public native void setCloudFillColorAlternate(String cloudFillColorAlternate) /*-{this.efParams.cloudFillColorAlternate = cloudFillColorAlternate;}-*/;

  /** Template to use when writing out node names. */
  public native String getNameTemplate() /*-{return this.efParams.nameTemplate;}-*/;
  //public native void setNameTemplate(String nameTemplate) /*-{this.efParams.nameTemplate = nameTemplate;}-*/;

  /** Whether or not to split a camel case Xholon class name into multiple words. */
  public native boolean isShouldSplitCamelCase() /*-{return this.efParams.shouldSplitCamelCase;}-*/;
  //public native void setShouldSplitCamelCase(boolean shouldSplitCamelCase) /*-{this.efParams.shouldSplitCamelCase = shouldSplitCamelCase;}-*/;

  /** Whether or not split camel case should be all lower case. */
  public native boolean isShouldBeLowerCase() /*-{return this.efParams.shouldBeLowerCase;}-*/;
  //public native void setShouldBeLowerCase(boolean shouldBeLowerCase) /*-{this.efParams.shouldBeLowerCase = shouldBeLowerCase;}-*/;

  /** Whether or not split camel case should have first word capitalized and rest lower case. */
  public native boolean isShouldBeCapitalized() /*-{return this.efParams.shouldBeCapitalized;}-*/;
  //public native void setShouldBeCapitalized(boolean shouldBeCapitalized) /*-{this.efParams.shouldBeCapitalized = shouldBeCapitalized;}-*/;

  /** Whether or not to include Xholon annotations in the mind map output. */
  public native boolean isShouldShowAnnotations() /*-{return this.efParams.shouldShowAnnotations;}-*/;
  //public native void setShouldShowAnnotations(boolean shouldShowAnnotations) /*-{this.efParams.shouldShowAnnotations = shouldShowAnnotations;}-*/;
	
	/** Position nodes to the right or left of the Freemind root node. */
  public native String getPosition() /*-{return this.efParams.position;}-*/;
  //public native void setPosition(String position) /*-{this.efParams.position = position;}-*/;
  
  /** Freemind version: "0.9.0" "1.0.1". */
  public native String getVersion() /*-{return this.efParams.version;}-*/;
  //public native void setVersion(String version) /*-{this.efParams.version = version;}-*/;
  
}

/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2016 Ken Webb
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

import java.util.Date;
import java.util.List;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Export a Xholon model in KIELER KLay JSON format.
 * </pre>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on January 3, 2015)
 * @see <a href=""></a>
 */
@SuppressWarnings("serial")
public class Xholon2KLayJson extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
  
  private String outFileName;
  private String outPath = "./ef/json/";
  private String modelName;
  private IXholon root;
  private StringBuilder sb;
  private StringBuilder sbEdges;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  /** Whether or not to show state machine nodes. */
  private boolean shouldShowStateMachineEntities = false;
  
  /** Template to use when writing out node names. */
  //protected String nameTemplate = "r:C^^^";
  protected String nameTemplate = "^^C^^^"; // don't include role name
  
  /** Whether or not to format the output by inserting new lines. */
  //private boolean shouldInsertNewlines = true;

  /**
   * Constructor.
   */
  public Xholon2KLayJson() {}
  
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
      this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".json";
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
    sb = new StringBuilder();
    sbEdges = new StringBuilder();
    sb
    .append("{\n")
    .append("  \"id\": ").append("\"root\",\n")
    .append("  \"properties\": {\n")
    .append("    \"direction\": ").append("\"DOWN\",\n")
    .append("    \"spacing\": ").append("40\n")
    .append("  },\n")
    .append("  \"children\": [")
    ;
    writeNode(root, "  ");
    sb
    .append("],\n");
    
    if (sbEdges.toString().length() > 0) {
      sb
      .append("  \"edges\": [")
      .append(sbEdges.toString())
      .append("  ]\n");
    }
    sb
    .append("}\n");
    writeToTarget(sb.toString(), outFileName, outPath, root);
  }
  
  /**
   * Write one node, and its child nodes.
   * @param node The current node in the Xholon hierarchy.
   * @param indent Current space indent in the hierarchy.
   */
  protected void writeNode(IXholon node, String indent) {
    // only show state machine nodes if should show them, or if root is a StateMachineCE
    if ((node.getXhcId() == CeStateMachineEntity.StateMachineCE)
        && (shouldShowStateMachineEntities == false)) {
      return;
    }
    int nodeId = node.getId();
    String nodeLabel = node.getName(nameTemplate);
    sb
    .append("{\n")
    .append(indent).append("  \"id\": \"n").append(nodeId).append("\",\n")
    .append(indent).append("  \"labels\": [{\"text\": \"").append(nodeLabel).append("\"}],\n")
    .append(indent).append("  \"width\": ").append(20).append(",\n")
    .append(indent).append("  \"height\": ").append(20).append(",\n");
    makeLinks(node);
    if (node.hasChildNodes()) {
      sb.append(indent).append("  \"children\": [");
      IXholon childNode = node.getFirstChild();
      while (childNode != null) {
        writeNode(childNode, indent + "  ");
        childNode = childNode.getNextSibling();
        if (childNode != null) {
          sb.append(",");
        }
      }
      
      sb.append("],\n");
    }
    sb.append(indent).append("}");
  }
  
  /**
	 * Write links from this node to any others, where Xholon has connected ports.
	 * @param node The current node.
	 */
	@SuppressWarnings("unchecked")
	protected void makeLinks(IXholon node)
	{
		//if (isShouldShowLinks() == false) {return;}
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
		writeEdge(node, remoteNode);
	}
  
  protected void writeEdge(IXholon sourceNode, IXholon targetNode) {
    int sourceId = sourceNode.getId();
    int targetId = targetNode.getId();
    sbEdges
    .append("{\n")
    .append("  \"id\": \"e").append(sourceId).append("_").append(targetId).append("\",\n")
    .append("  \"source\": \"n").append(sourceId).append("\",\n")
    .append("  \"target\": \"n").append(targetId).append("\",\n")
    .append("},")
    ;
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

}

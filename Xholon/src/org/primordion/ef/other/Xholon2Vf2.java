/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2018 Ken Webb
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
 * Export a Xholon model in VF2 format.
 * 
Data File Format (source: pfllo site)
"t # N" means the Nth graph,
"v M L" means that the Mth vertex in this graph has label L,
"e P Q L" means that there is an edge connecting the Pth vertex with the Qth vertex. The edge has label L.

Each data file or query file end with "t # -1".
 * 
 * example:
t # 0
v 0 2
v 1 3
t # -1
 * 
 * example:
t # 8
v 0 two
v 1 three
e 0 1 dummy
t # -1
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon"> Xholon Project website</a>
 * @see <a href="https://github.com/pfllo/VF2"> I am adapting the VF2 code from this site</a>
 * @see <a href="https://gist.github.com/kenwebb/1d2b5e1c75410bed3ea074871d828396"> (Sub)Graph Isomorphism - VF2</a>
 * @since 0.9.1 (Created on August 28, 2018)
 * 
 * TODO
 * - optionally output tree edges: first next parent
 *  - warning: VF2 code can't handle bidirectional pairs of edges (ex: parent and first)
 *  - warning: VF2 code requires lower id first
 * - optionally include port/link name, or "2" or other default edge label
 * - efparams
 */
public class Xholon2Vf2 extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
  
  private String outFileName;
  private String outPath = "./ef/vf2/";
  private String modelName;
  private IXholon root;
  private StringBuilder sb;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  /** Whether or not to draw links between nodes. */
  private boolean shouldShowLinks = true;
  
  /** Whether or not to show state machine nodes. */
  private boolean shouldShowStateMachineEntities = false;
  
  /** Template to use when writing out node names. */
  protected String nameTemplate = "^^c^^^";
  
  /** Used in makeVf2id() */
  protected int nextVf2id = 0;

  /**
   * Constructor.
   */
  public Xholon2Vf2() {}
  
  @Override
  public String getVal_String() {
    return sb.toString();
  }
  
  @Override
  public boolean initialize(String outFileName, String modelName, IXholon root) {
    timeNow = new Date();
    timeStamp = timeNow.getTime();
    if (outFileName == null) {
      this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".net";
    }
    else {
      this.outFileName = outFileName;
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
    sb.append("t # 0\n");
    writeNodes(root, 0);
    writeEdges(root, 0);
    sb.append("t # -1\n");
    writeToTarget(sb.toString(), outFileName, outPath, root);
  }
  
  /**
   * Write information about each node.
   * @param node
   * @param level
   */
  protected void writeNodes(IXholon node, int level)
  {
    writeNode(node, level);
  }
  
  /**
   * Write information about the edges between nodes.
   * @param node
   * @param level
   */
  protected void writeEdges(IXholon node, int level)
  {
    writeEdge(node, level);
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
    // v 0 2
    makeVf2id(node, nextVf2id);
    sb
    .append("v ")
    //.append(getNodeId(node))
    .append(nextVf2id)
    .append(" ")
    .append(node.getName(nameTemplate))
    .append("\n");
    nextVf2id++;
    
    // children
    IXholon childNode = node.getFirstChild();
    while (childNode != null) {
      writeNode(childNode, level+1);
      childNode = childNode.getNextSibling();
    }
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
    // e 0 1 dummy
    // the lower of the 2 id's has to come first; so these edges are undirected
    int id1 = getNodeId(node);
    int id2 = getNodeId(remoteNode);
    if (id2 < id1) {
      // swap the 2 id's
      int idTemp = id1;
      id1 = id2;
      id2 = idTemp;
    }
    sb
    .append("e ")
    .append(id1)
    .append(" ")
    .append(id2)
    .append(" 2\n");
  }
  
  /**
   * Get the node's id.
   * @param node
   * @return An int.
   */
  protected native int getNodeId(IXholon node) /*-{
    return node["vf2id"];
  }-*/;
  
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
   * Make vf2id for one node, for use within the Xholon2Vf2 process.
   * node["vf2id"]
   */
  protected native void makeVf2id(IXholon node, int vf2id) /*-{
    node["vf2id"] = vf2id;
  }-*/;
  
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

}

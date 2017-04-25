/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2017 Ken Webb
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

package org.primordion.xholon.mech.gexf;

import java.util.List;
import java.util.ArrayList;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;

/**
 * GEXF (Graph Exchange XML Format)
 * TODO handle attribute default values; the GEXF Primer is vague about this
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="https://gephi.org/gexf/format/index.html">GEXF website</a>
 * @since 0.9.1 (Created on April 21, 2017)
 */
public class Gexf extends XholonWithPorts {
  
  private String roleName = null;
  
  /**
   * Whether or not to delete unneeded GEXF attribute and attvalue and meta nodes.
   */
  private boolean deleteGexfAttrNodes = false;
  
  /**
   * Whether or not to delete unneeded GEXF edge nodes.
   */
  private boolean deleteGexfEdgeNodes = false;
  
  private List<IXholon> removalsList = null;
  
  // <attributes class="node">
  private IXholon nodeAttributes = null;
  
  // <attributes class="edge">
  private IXholon edgeAttributes = null;
  
  // Whether or not to call super.postConfigure()  super.act()  etc.
  private boolean recurseSubtree = false;
  
  @Override
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
  
  @Override
  public String getRoleName() {
    return roleName;
  }
  
  @Override
  public void postConfigure() {
    removalsList = new ArrayList<IXholon>();
    this.fixSubtree(this);
    for (int i = 0; i < removalsList.size(); i++) {
      IXholon node = removalsList.get(i);
      if (node != null) {
        node.removeChild();
      }
    }
    removalsList = null;
    if (recurseSubtree) {
      super.postConfigure();
    }
  }
  
  @Override
  public void act() {
    if (recurseSubtree) {
      super.act();
    }
  }
  
  protected void fixSubtree(IXholon node) {
    handleLabel(node);
    switch (node.getXhcName()) {
    case "edge":
      this.handleEdge(node);
      break;
    case "edges":
      if (deleteGexfEdgeNodes) {
        removalsList.add(node);
      }
      break;
    case "attributes":
      switch (findAttributesClass(node)) {
      case "node":
        this.nodeAttributes = node;
        break;
      case "edge":
        this.nodeAttributes = node;
        break;
      default: break;
      }
      if (deleteGexfAttrNodes) {
        removalsList.add(node);
      }
      break;
    case "attvalues":
      if (deleteGexfAttrNodes) {
        removalsList.add(node);
      }
      break;
    case "attvalue":
      IXholon grandParent = node.getParentNode().getParentNode();
      switch (grandParent.getXhcName()) {
      case "node":
        this.makeAttribute(grandParent, node, this.nodeAttributes);
        break;
      case "edge":
        this.makeAttribute(grandParent, node, this.edgeAttributes);
        break;
      default: break;
      }
      break;
    case "meta":
      if (deleteGexfAttrNodes) {
        removalsList.add(node);
      }
      break;
    default: break;
    }
    IXholon child = node.getFirstChild();
    while (child != null) {
      this.fixSubtree(child);
      child = child.getNextSibling();
    }
    //switch (node.getXhcName()) {
    //case "node": node.consoleLog(node); break;
    //default: break;
    //}
  }
  
  protected native void handleLabel(IXholon node) /*-{
    if (node.label) {
      node.role(node.label);
    }
  }-*/;
  
  protected native String findAttributesClass(IXholon node) /*-{
    return node["class"];
  }-*/;
  
  /**
   * <attribute id="0" title="my−text−attribute" type="string"/>
   * <attvalue for="0" value="samplevalue"/>
   * TODO be able to use default value
   */
  protected native void makeAttribute(IXholon nodeOrEdge, IXholon attvalue, IXholon attributes) /*-{
    var attribute = attributes.first();
    while (attribute) {
      if (attvalue["for"] == attribute.id()) {
        switch (attribute.type) {
        case "string":
          nodeOrEdge[attribute.title] = attvalue.value;
          break;
        case "integer":
        case "float":
        case "double":
          nodeOrEdge[attribute.title] = Number(attvalue.value);
          break;
        case "boolean":
          switch (attvalue.value) {
          case "true": nodeOrEdge[attribute.title] = true; break;
          default: nodeOrEdge[attribute.title] = false; break;
          }
          break;
        case "liststring": // TODO
        default: break;
        }
        break; // break out of the while loop
      }
      attribute = attribute.next();
    }
  }-*/;
  
  protected native void handleEdge(IXholon edgeNode) /*-{
    var sourceId = edgeNode.source;
    var targetId = edgeNode.target;
    var sourceNode = null;
    var targetNode = null;
    var anode = edgeNode.xpath("../../nodes/node");
    while (anode) {
      if (anode.id() == sourceId) {
        sourceNode = anode;
      }
      if (anode.id() == targetId) {
        targetNode = anode;
      }
      if (sourceNode && targetNode) {
        var n = -1; // request next available port
        sourceNode.port(n, targetNode);
        break;
      }
      anode = anode.next();
    }
  }-*/;
  
  @Override
  public int setAttributeVal(String attrName, String attrVal) {
    if ("deleteGexfAttrNodes".equals(attrName)) {
      this.deleteGexfAttrNodes = Boolean.parseBoolean(attrVal);
    }
    else if ("deleteGexfEdgeNodes".equals(attrName)) {
      this.deleteGexfEdgeNodes = Boolean.parseBoolean(attrVal);
    }
    else if ("recurseSubtree".equals(attrName)) {
      this.recurseSubtree = Boolean.parseBoolean(attrVal);
    }
    return 0;
  }
  
  @Override
  public void toXml(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {
    if (this.getXhc().hasAncestor("Gexf")) {
      // write start element
      xmlWriter.writeStartElement(this.getXhcName());
      xholon2xml.writeSpecial(this);
      // write children
      IXholon childNode = getFirstChild();
      while (childNode != null) {
        childNode.toXml(xholon2xml, xmlWriter);
        childNode = childNode.getNextSibling();
      }
      // write end element
      xmlWriter.writeEndElement(this.getXhcName());
    }
    else {
      super.toXml(xholon2xml, xmlWriter);
    }
  }
  
  @Override
  public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {}
  
}

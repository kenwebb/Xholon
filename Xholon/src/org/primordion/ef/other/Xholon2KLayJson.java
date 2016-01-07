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

import com.google.gwt.core.client.JavaScriptObject;

import java.util.Date;
import java.util.List;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Export a Xholon model in KIELER KLay JSON format.
 * If you want to see the KLay diagram (using d3),
 * then run Xholon using XholonKLayD3.html .
 * TODO
 * - ports don't appear in the generated SVG, but the edges have a blank space where the ports should go
 * - put JavaScript code in KLay index.html, into this class; also the CSS?
 * - the node lables don not print on the diagram; they only appear as SVG tooltips
 * 
 * Requires these libraries:
<pre>
  <script type="text/javascript" src="d3.min.js"></script>
  <script type="text/javascript" src="klay.js"></script>
  <script type="text/javascript" src="klayjs-d3.js"></script>
</pre>
 * 
 * CSS:
<pre>
  <style>
    g.leaf > rect {
      stroke: #fff;
      stroke-width: 1px;
      opacity: .5;
    }
  
    g.compound > rect {
      opacity: 0.1;
    }
    
    .node {
    }
    
    .link {
      stroke: #999;
      stroke-opacity: .6;
      fill: none;
    }
    
    .port {
      stroke: #fff;
      stroke-width: 1px;
      opacity: .6;
    }
  </style>
</pre>
 * 
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
  
  /** Template to use when writing out node names. */
  //protected String nameTemplate = "r:C^^^";
  //protected String nameTemplate = "^^C^^^"; // don't include role name
  protected String nameTemplate = "R^^^^^";
  
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
    writeNode(root, "");
    String jsonStr = sb.toString();
    writeToTarget(jsonStr, outFileName, outPath, root);
    if (isDisplay()) {
      createD3(jsonStr, getWidth(), getHeight(), getSelection());
    }
  }
  
  /**
   * For info on these and other properties:
   * @see <a href="https://rtsys.informatik.uni-kiel.de/confluence/display/KIELER/KLay+Layered+Layout+Options">KLay+Layered+Layout+Options</a>
   * @see <a href="https://rtsys.informatik.uni-kiel.de/confluence/display/KIELER/KIML+Layout+Options">KIML+Layout+Options</a>
   */
  protected String writeProperties(StringBuilder sbLocal) {
    sbLocal
    .append("  \"properties\": {\n")
    .append("    \"direction\": \"").append(getDirection()).append("\",\n")
    .append("    \"spacing\": ").append(getSpacing()).append("\n")
    .append("  },\n");
    return sbLocal.toString();
  }
  
  /**
   * Write one node, and its child nodes.
   * @param node The current node in the Xholon hierarchy.
   * @param indent Current space indent in the hierarchy.
   */
  protected void writeNode(IXholon node, String indent) {
    // only show state machine nodes if should show them, or if root is a StateMachineCE
    if ((node.getXhcId() == CeStateMachineEntity.StateMachineCE)
        && (isShowStateMachineEntities() == false)) {
      return;
    }
    sb
    .append("{\n")
    .append(indent).append("  \"id\": \"").append(makeNodeId(node)).append("\",\n");
    if (node == root) {
      sb.append(writeProperties(new StringBuilder()));
    }
    sb
    .append(indent).append("  \"labels\": [{\"text\": \"").append(makeNodeLabel(node)).append("\"}],\n")
    .append(indent).append("  \"width\": ").append(getNodeWidth()).append(",\n")
    .append(indent).append("  \"height\": ").append(getNodeHeight())
    .append(writePorts(node, indent + "  ", new StringBuilder()));
    makeLinks(node, indent);
    if (node.hasChildNodes()) {
      sb.append(",\n")
      .append(indent).append("  \"children\": [");
      IXholon childNode = node.getFirstChild();
      while (childNode != null) {
        writeNode(childNode, indent + "  ");
        childNode = childNode.getNextSibling();
        if (childNode != null) {
          sb.append(",");
        }
      }
      
      sb.append("]\n");
    }
    else {
      sb.append("\n");
    }
    if (node == root) {
      if (sbEdges.length() > 0) {
        sb
        .append(indent)
        .append("  ,\n")
        .append("  \"edges\": [")
        .append(sbEdges.toString())
        .append("]\n");
      }
      else {
        sb.append("\n");
      }
    }
    sb.append(indent).append("}");
  }
  
  /**
	 * Write links from this node to any others, where Xholon has connected ports.
	 * @param node The current node.
	 */
	@SuppressWarnings("unchecked")
	protected void makeLinks(IXholon node, String indent)
	{
		if (!isShowEdges()) {return;}
		List<PortInformation> portList = node.getAllPorts();
		for (int i = 0; i < portList.size(); i++) {
			makeLink(node, (PortInformation)portList.get(i), indent);
		}
	}
	
	/**
	 * Write one link.
	 * @param node The node where the link originates.
	 * @param portInfo Information about the port that represents the link.
	 */
	protected void makeLink(IXholon node, PortInformation portInfo, String indent)
	{
		if (portInfo == null) {return;}
		IXholon remoteNode = portInfo.getReffedNode();
		writeEdge(node, remoteNode, portInfo, indent);
	}
  
  protected void writeEdge(IXholon sourceNode, IXholon targetNode, PortInformation sourcePortInfo, String indent) {
    String edgeLabel = makeEdgeLabel(sourceNode, targetNode);
    if (sbEdges.length() > 0) {
      sbEdges.append(",");
    }
    sbEdges
    .append("{\n")
    .append(indent).append("  \"id\": \"").append(edgeLabel).append("\",\n")
    .append(indent).append("  \"labels\": [{\"text\": \"").append(edgeLabel).append("\"}],\n")
    .append(indent).append("  \"source\": \"").append(makeNodeId(sourceNode)).append("\",\n");
    if (isShowPorts()) {
      sbEdges
      .append(indent).append("  \"sourcePort\": \"").append(makePortId(sourceNode, sourcePortInfo, new StringBuilder())).append("\",\n");
    }
    sbEdges
    .append(indent).append("  \"target\": \"").append(makeNodeId(targetNode)).append("\"\n")
    .append(indent).append("}");
  }
  
  protected String writePorts(IXholon node, String indent, StringBuilder sbLocal) {
    if (!isShowPorts()) {return "";}
		List<PortInformation> portList = node.getAllPorts();
		if (portList.size() == 0) {return "";}
		sbLocal
		.append(",\n")
		.append(indent)
		.append("\"ports\": [");
		for (int i = 0; i < portList.size(); i++) {
			if (i > 0) {
			  sbLocal.append(",");
			}
			writePort(node, (PortInformation)portList.get(i), indent, sbLocal);
		}
		sbLocal.append("]");
		return sbLocal.toString();
  }
  
  protected void writePort(IXholon node, PortInformation pi, String indent, StringBuilder sbLocal) {
    String fnIndex = pi.getFieldNameIndexStr();
    if (fnIndex == null) {
      fnIndex = "";
    }
    sbLocal
    .append("{\n")
    .append(indent).append("  \"id\": \"").append(makePortId(node, pi, new StringBuilder())).append("\",\n")
    .append(indent).append("  \"width\": ").append(getPortWidth()).append(",\n")
    .append(indent).append("  \"height\": ").append(getPortHeight()).append("\n")
    .append(indent).append("}");
  }
  
  protected String makePortId(IXholon node, PortInformation pi, StringBuilder sbLocal) {
    String fnIndex = pi.getFieldNameIndexStr();
    if (fnIndex == null) {
      fnIndex = "";
    }
    sbLocal
    .append(makeNodeId(node))
    .append("_")
    .append(pi.getFieldName())
    .append(fnIndex);
    return sbLocal.toString();
  }
  
  protected String makeNodeId(IXholon node) {
    return "n" + node.getId();
  }
  
  protected String makeNodeLabel(IXholon node) {
    return node.getName(nameTemplate);
  }
  
  protected String makeEdgeId(IXholon sourceNode, IXholon targetNode) {
    return makeEdgeLabel(sourceNode, targetNode);
  }
  
  protected String makeEdgeLabel(IXholon sourceNode, IXholon targetNode) {
    return "e" + sourceNode.getId() + "_" + targetNode.getId();
  }
  
  /**
   * Make a JavaScript object with all the parameters for this external format.
   */
  protected native void makeEfParams() /*-{
    var p = {};
    p.showEdges = true;
    p.showStateMachineEntities = false;
    p.showPorts = false;
    p.direction = "UNDEFINED"; // UNDEFINED, RIGHT, DOWN
    p.spacing = 20; // 20 is the KLay default
    p.width = 600;
    p.height = 800;
    p.nodeWidth = 20;
    p.nodeHeight = 20;
    p.portWidth = 5;
    p.portHeight = 5;
    p.selection = "#xhsvg";
    p.maxChars = 1;
    p.display = true;
    this.efParams = p;
  }-*/;
  
  public native boolean isShowEdges() /*-{return this.efParams.showEdges;}-*/;
  //public native void setShowEdges(boolean showEdges) /*-{this.efParams.showEdges = showEdges;}-*/;
  
  /** Whether or not to show state machine nodes. */
  public native boolean isShowStateMachineEntities() /*-{return this.efParams.showStateMachineEntities;}-*/;
  //public native void setShowStateMachineEntities(boolean showStateMachineEntities) /*-{this.efParams.showStateMachineEntities = showStateMachineEntities;}-*/;
  
  public native boolean isShowPorts() /*-{return this.efParams.showPorts;}-*/;
  //public native void setShowPorts(boolean showPorts) /*-{this.efParams.showPorts = showPorts;}-*/;
  
  public native String getDirection() /*-{return this.efParams.direction;}-*/;
  //public native void setDirection(String direction) /*-{this.efParams.direction = direction;}-*/;
  
  public native int getSpacing() /*-{return this.efParams.spacing;}-*/;
  //public native void setSpacing(int spacing) /*-{this.efParams.spacing = spacing;}-*/;
  
  public native int getWidth() /*-{return this.efParams.width;}-*/;
  //public native void setWidth(int width) /*-{this.efParams.width = width;}-*/;
  
  public native int getHeight() /*-{return this.efParams.height;}-*/;
  //public native void setHeight(int height) /*-{this.efParams.height = height;}-*/;
  
  public native int getNodeWidth() /*-{return this.efParams.nodeWidth;}-*/;
  //public native void setNodeWidth(int nodeWidth) /*-{this.efParams.nodeWidth = nodeWidth;}-*/;
  
  public native int getNodeHeight() /*-{return this.efParams.nodeHeight;}-*/;
  //public native void setNodeHeight(int nodeHeight) /*-{this.efParams.nodeHeight = nodeHeight;}-*/;
  
  public native int getPortWidth() /*-{return this.efParams.portWidth;}-*/;
  //public native void setPortWidth(int portWidth) /*-{this.efParams.portWidth = portWidth;}-*/;
  
  public native int getPortHeight() /*-{return this.efParams.portHeight;}-*/;
  //public native void setPortHeight(int portHeight) /*-{this.efParams.portHeight = portHeight;}-*/;
  
  public native String getSelection() /*-{return this.efParams.selection;}-*/;
  //public native void setSelection(String selection) /*-{this.efParams.selection = selection;}-*/;
  
  public native int getMaxChars() /*-{return this.efParams.maxChars;}-*/;
  //public native void setMaxChars(int maxChars) /*-{this.efParams.maxChars = maxChars;}-*/;
  
  public native boolean isDisplay() /*-{return this.efParams.display;}-*/;
  //public native void setDisplay(boolean display) /*-{this.efParams.display = display;}-*/;
  
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
  
  /**
   * Create a d3 SVG diagram in the browser.
   * TODO
   * - 
   */
  protected native void createD3(String jsonStr, int width, int height, String selection) /*-{
    var json = $wnd.JSON.parse(jsonStr);
    
    // KLay
    var zoom = $wnd.d3.behavior.zoom()
      .on("zoom", redraw);
    
    // KLay
    var idfun = function(d) { return d.id; };
    
    // d3cp
    var format = $wnd.d3.format(",d");
    var maxChars = 1;
    
    // d3cp
    if (this.efParams) {
      maxChars = this.efParams.maxChars;
      if (this.efParams.selection) {selection = this.efParams.selection;}
    }
    
    // d3cp
    // var pack = $wnd.d3.layout.pack() ???
    
    // d3cp
    var selectionNode = $wnd.d3.select(selection);
    
    // d3cp KLay
    var svg = selectionNode
      .append("svg")
      .attr("width", width)
      .attr("height", height)
      .call(zoom)
      .append("g") // the top-level g
      
    // KLay
    // group shizzle
    var root = svg.append("g");
    
    // KLay
    var layouter = $wnd.klay.d3kgraph()
      .size([width, height])
      .transformGroup(root)
      .options({
        edgeRouting: "ORTHOGONAL"
      });
    
    // d3cp
    var node = svg
      .data([json]);
    $wnd.console.log(node);
    $wnd.console.log(svg.data());
    
    layouter.on("finish", function(d) {
      
      var nodes = layouter.nodes();
      var links = layouter.links(nodes);
      
      var linkData = root.selectAll(".link")
          .data(links, idfun);
      var link = linkData.enter()
          .append("path")
          .attr("class", "link")
          .attr("d", "M0 0");
    
      var nodeData = root.selectAll(".node")
          .data(nodes, idfun);
      var node = nodeData.enter()
          .append("g")
          .attr("class", function(d) { 
            if (d.children) return "node compound"; else return "node leaf"; 
          });
          
      var atoms = node.append("rect")
          .attr("width", 10)
          .attr("height", 10)
          .attr("x", 0)
          .attr("y", 0);
    
      node.append("title")
          .text(function(d) {
             if (d.labels) {
               return d.labels[0].text;
             }
             else {
               return d.id;
             }
           }); 

      // apply edge routes
      link.transition().attr("d", function(d) {
        var path = "";
        path += "M" + d.sourcePoint.x + " " + d.sourcePoint.y + " ";
          (d.bendPoints || []).forEach(function(bp, i) {
            path += "L" + bp.x + " " + bp.y + " ";
          });
        path += "L" + d.targetPoint.x + " " + d.targetPoint.y + " ";
        return path;
      });
      
      // apply node positions
      node.transition()
        .attr("transform", function(d) { return "translate(" + (d.x || 0) + " " + (d.y || 0) + ")"});
      
      atoms.transition()
        .attr("width", function(d) { return d.width; })
        .attr("height", function(d) { return d.height; });
    }); // end layouter.on("finish",
    
    // 
    var graph = svg.data()[0];
    $wnd.console.log("about to do layouter.kgraph(graph);");
    $wnd.console.log(layouter);
    $wnd.console.log(layouter.kgraph);
    $wnd.console.log(graph);
    layouter.kgraph(graph);
    
    // redraw()
    function redraw() {
      svg.attr("transform", "translate(" + $wnd.d3.event.translate + ")"  + " scale(" + $wnd.d3.event.scale + ")");
    }
  }-*/;

}

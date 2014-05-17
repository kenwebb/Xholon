/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2013 Ken Webb
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

package org.primordion.ef.cytojs;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Image;

//import java.util.Date;
import java.util.List;

import org.client.HtmlElementCache;
import org.primordion.xholon.base.IDecoration;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
//import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.io.gwt.HtmlScriptHelper;
import org.primordion.xholon.service.ef.IXholon2GraphFormat;

/**
 * Export a Xholon model in Cytoscape.js format, as a network.
 * Cytoscape.js requires jQuery 1.4 or newer.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on December 26, 2013)
 * @see <a href="http://cytoscape.github.io/cytoscape.js">Cytoscape.js website</a>
 * @see <a href="http://jsbin.com/urabis/8/edit">jsbin playground</a>
 */
@SuppressWarnings("serial")
public class Xholon2CytojsNetwork extends AbstractXholon2Cytojs implements IXholon2GraphFormat {
  
  /** StringBuilder initial capacity. */
  //protected static final int SB_INITIAL_CAPACITY = 1000; // default is 16
  
  //private String outFileName;
  //private String outPath = "./ef/cytojs/";
  //private String modelName;
  //private IXholon root;
  //protected StringBuilder sb;
  
  /** Current date and time. */
  //private Date timeNow;
  //private long timeStamp;
  
  /**
   * Cytoscape data uses a JSON-like format.
   * The data is embedded inside a JavaScript function.
   */
  //protected String fileNameExtension = ".js";
  
  /** Template to use when writing out node names. */
  //protected String nameTemplate = "R^^^^^"; // "r:C^^^";
  
  /** Whether or not to show state machine nodes. */
  //protected boolean shouldShowStateMachineEntities = false;
  
  //protected boolean shouldUseBackgroundImage = true;
  
  /**
   * HTML element ID where Graphical Network View will be displayed.
   */
  //protected String viewElementId = "networkview"; // or "treeview"
  
  //protected int height = 607;
  //protected int width = 991;
  
  /**
   * Cytoschape.js layout
   * Many or all layouts have various parameters, but most have reasonable defaults.
   * random preset grid breadthfirst circle arbor null cose
   * default layout: "grid" ?
   */
  //protected String layout = "grid";
  
  /**
   * The accumulating text for all Cytoscape node elements.
   */
  //protected StringBuilder nodeSb = null;
  
  /**
   * The accumulating text for all Cytoscape edge elements.
   */
  //protected StringBuilder edgeSb = null;
  
  public Xholon2CytojsNetwork() {}
  
  /*@Override
  public boolean initialize(String outFileName, String modelName, IXholon root) {
    timeNow = new Date();
    timeStamp = timeNow.getTime();
    if (outFileName == null) {
      this.outFileName = outPath + root.getXhcName() + "_" + root.getId()
        + "_" + timeStamp + fileNameExtension;
    }
    else {
      this.outFileName = outFileName;
    }
    this.modelName = modelName;
    this.root = root;
    
    this.nodeSb = new StringBuilder(SB_INITIAL_CAPACITY).append("nodes: [\n");
    this.edgeSb = new StringBuilder(SB_INITIAL_CAPACITY).append("\nedges: [\n");
    
    root.consoleLog("about to call !isDefined_cytoscape()");
    if (!isDefined_cytoscape()) {
      loadCytoscape();
      return true; // do not return false; that just causes an error message
    }
    
    return true;
  }*/
  
  @Override
  public void writeAll() {
    //root.consoleLog("Xholon2CytojsNetwork writeAll()");
    if (!isDefined_cytoscape()) {return;}
    sb = new StringBuilder();
    writeNode(root); // writes to nodeSb and edgeSb
    writeStart();
    sb.append("elements: {\n");
    sb.append(nodeSb.toString()).append("],\n");
    sb.append(edgeSb.toString()).append("]\n");
    sb.append("},\n");
    writeEnd();
    writeToTarget(sb.toString(), outFileName, outPath, root);
    
    Element ele = HtmlElementCache.networkview;
    if (ele != null) {
      ele.getStyle().setHeight(height, Unit.PX);
      ele.getStyle().setWidth(width, Unit.PX);
    }
    
    HtmlScriptHelper.fromString(sb.toString(), false);
  }
  
  @Override
  public void writeNode(IXholon xhNode) {
    // { data: { id: 'j', name: 'Jerry', weight: 65, height: 174 } },
    // only show state machine nodes if should show them, or if root is a StateMachineCE
    if ((xhNode.getXhcId() == CeStateMachineEntity.StateMachineCE)
        && (shouldShowStateMachineEntities == false)
        && (xhNode != root) //&& (level > 0)
        )
        {
      return;
    }
    nodeSb
    .append("{ data: {")
    .append(" id: '").append(xhNode.getId()).append("'")
    .append(", name: '").append(xhNode.getName(nameTemplate)).append("'");
    writeNodeAttributes(xhNode);
    if (shouldUseBackgroundImage) {
      Image img = getImageIcon((IDecoration)xhNode.getXhc());
      //consoleLog("image " + img.getWidth() + " " + img.getHeight());
      //consoleLog(img.getUrl());
      nodeSb
      .append(", image: 'url(").append(img.getUrl()).append(")'");
    }
    nodeSb.append(" } },\n")
    ;
    writeEdges(xhNode);
    
    IXholon childNode = xhNode.getFirstChild();
    while (childNode != null) {
      writeNode(childNode); //, level+1);
      childNode = childNode.getNextSibling();
    }
  }
  
  @Override
  public void writeEdges(IXholon xhNode) {
    List<PortInformation> portList = xhNode.getAllPorts();
    for (int i = 0; i < portList.size(); i++) {
      writeEdge(xhNode, (PortInformation)portList.get(i));
    }
    
  }
  
  /**
   * Write one edge.
   * @param xhNode The node where the edge originates.
   * @param portInfo Information about the port that represents the edge.
   */
  protected void writeEdge(IXholon xhNode, PortInformation portInfo) {
    // { data: { source: 'j', target: 'e' } },
    if (portInfo == null) {return;}
    IXholon remoteNode = portInfo.getReffedNode();
    edgeSb
    .append("{ data: {")
    .append(" source: '").append(xhNode.getId()).append("',")
    .append(" target: '").append(remoteNode.getId()).append("'")
    .append(" } },\n")
    ;
  }

  @Override
  public void writeNodeAttributes(IXholon xhNode) {
    
  }
  
  /**
   * Write start.
   */
  protected void writeStart() {
    sb.append("// ")
		.append(modelName)
		.append("\n\n");
    sb.append("// try at http://jsbin.com/avIwUqUV/1/edit\n");
    sb.append("$(loadCy = function() {\n");
    writeOptions();
  }
  
  /**
   * Write end.
   */
  protected void writeEnd() {
    sb
    .append("    ready: function() {\n")
    .append("      cy = this;\n")
    // when user clicks a node, unconnected nodes will appear faded-out
    .append("      cy.elements().unselectify();\n")
    .append("      cy.on('tap', 'node', function(e) {\n")
    .append("        var node = e.cyTarget;\n")
    .append("        var neighborhood = node.neighborhood().add(node);\n")
    .append("        cy.elements().addClass('faded');\n")
    .append("        neighborhood.removeClass('faded');\n")
    .append("      });\n")
    .append("      cy.on('tap', function(e) {\n")
    .append("        if( e.cyTarget === cy ) {\n")
    .append("          cy.elements().removeClass('faded');\n")
    .append("        }\n")
    .append("      });\n")
    .append("    }\n")
    .append("  };\n")
    .append("  $('#")
    .append(viewElementId)
    .append("').cytoscape(options);\n")
    .append("});\n")
    ;
  }
  
  /**
   * Write options
   */
  protected void writeOptions() {
    sb
    .append("  options = {\n")
    .append("    showOverlay: false,\n")
    .append("    minZoom: 0.5,\n")
    .append("    maxZoom: 2,\n");
    if (layout != null) {
      sb
      .append("    // try layouts: grid random circle cose\n")
      .append("    layout: {")
      .append(" 'name': '").append(layout).append("'")
      //.append(", 'liveUpdate': false")
      .append(" },\n");
    }
    writeStyle();
  }
  
  /**
   * Write styles
   */
  protected void writeStyle() {
    sb
    .append("    style: cytoscape.stylesheet()\n")
    .append("  .selector('node')\n")
    .append("    .css({\n")
    .append("      'content': 'data(name)',\n")
    //.append("      'font-family': 'hekvetica',\n")
    .append("      'font-size': 8,\n") // 14 10
    /*
    .append("      'text-valign': 'center',\n")
    .append("      'color': 'white',\n")
    .append("      'text-outline-width': 2,\n")
    .append("      'text-outline-color': '#888',\n")
    */
    .append("      'background-image': 'data(image)'\n")
    .append("    })\n")
    .append("  .selector('edge')\n")
    .append("    .css({\n")
    .append("      'target-arrow-shape': 'triangle'\n")
    .append("    })\n")
    .append("  .selector(':selected')\n")
    .append("    .css({\n")
    .append("      'background-color': 'black',\n")
    .append("      'line-color': 'black',\n")
    .append("      'target-arrow-color': 'black',\n")
    .append("      'source-arrow-color': 'black'\n")
    .append("    })\n")
    .append("  .selector('.faded')\n")
    .append("    .css({\n")
    .append("      'opacity': 0.25,\n")
    .append("      'text-opacity': 0\n")
    .append("    }),\n")
    ;
  }
  
  /**
   * Load cytoscape library asynchronously.
   */
  /*protected void loadCytoscape() {
    require(this);
  }*/
  
  /**
   * use requirejs
   */
  //protected native void require(final IXholon2GraphFormat xh2Cyto) /*-{
  /*  $wnd.console.log("starting require ..");
    $wnd.console.log($wnd.requirejs);
    $wnd.console.log($wnd.require);
    $wnd.console.log($wnd.requirejs.config);
    $wnd.requirejs.config({
      enforceDefine: false,
      shim: {
        "cyto": {
          deps: ["jquery"]
        }
      },
      paths: {
        cyto: [
          "xholon/lib/cytoscape.min"
        ],
        jquery: [
          "//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min"
        ]
      }
    });
    $wnd.console.log("require 1");
    $wnd.require(["cyto", "jquery"], function(cyto, jquery) {
      $wnd.console.log("require 2");
      xh2Cyto.@org.primordion.ef.IXholon2GraphFormat::writeAll()();
    });
    $wnd.console.log("require ended");*/
  //}-*/;
  
  /**
   * Is $wnd.cytoscape defined.
   * @return it is defined (true), it's not defined (false)
   */
  //protected native boolean isDefined_cytoscape() /*-{
  //  $wnd.console.log("isDefined_cytoscape()");
  //  return typeof $wnd.cytoscape != "undefined";
  //}-*/;
  
}

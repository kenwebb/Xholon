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

package org.primordion.ef.other;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInformation;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.io.gwt.HtmlScriptHelper;
import org.primordion.xholon.service.ef.IXholon2GraphFormat;

/**
 * Export a Xholon model in Chap Network format.
 * This implementation uses JavaScript arrays rather than Google DataTable,
 * so there is NO dependency on the Google Visualization API.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on July 30, 2013)
 * @see <a href="chap.almende.com">Almende CHAP site</a>
 * @see <a href="chap.almende.com/visualization/network/">CHAP Network</a>
 * @see <a href="almende.github.io/chap-links-library/index.html">CHAP doc and examples</a>
 * @see <a href="almende.github.io/chap-links-library/js/network/doc/">CHAP doc</a>
 * @see <a href="http://visjs.org/"> new version (2017)</a>>
 */
@SuppressWarnings("serial")
public class Xholon2ChapNetwork extends AbstractXholon2ExternalFormat implements IXholon2GraphFormat {
	
	/** StringBuilder initial capacity. */
	protected static final int SB_INITIAL_CAPACITY = 1000; // default is 16
	
	private String outFileName;
	private String outPath = "./ef/chnet/";
	private String modelName;
	private IXholon root;
	
	/** Current date and time. */
	private Date timeNow;
	private long timeStamp;
	
	/**
	 * Start of the CHAP Network script.
	 */
	protected StringBuilder startSb = null;
	
	/**
	 * The accumulating text for all CHAP Network nodes.
	 */
	protected StringBuilder nodeSb = null;
	
	/**
	 * The accumulating text for all CHAP Network links.
	 */
	protected StringBuilder linkSb = null;
	
	/**
	 * CHAP Network options.
	 */
	protected StringBuilder optionsSb = null;
	
	/**
	 * End of the CHAP Network script.
	 */
	protected StringBuilder endSb = null;
	
	protected StringBuilder sb = null;
	
	/**
	 * HTML element ID where Graphical Network View will be displayed.
	 */
	protected String viewElementId = "networkview"; // or "treeview"
	
	/**
	 * Whether or not to show port links between nodes.
	 */
	//protected boolean showNetwork = true;
	
	/**
	 * Whether or not to show hierarchical parent-child relationships.
	 */
	//protected boolean showTree = false;
	
	protected String linksTableLable = "text"; // "text" OR "label"
	protected String nodesTableLable = "text"; // "text" OR "label"
	
  /**
   * Each node superClass may have its own shape.
   * The map is constructed by calling getNodesStyle() for an optional list of user-specified shapes.
   */
  protected Map<String, String> shapeMap = null;
  
	/**
	 * Constructor.
	 */
	public Xholon2ChapNetwork() {}
	
	/**
	 * Constructor.
	 */
	public Xholon2ChapNetwork(boolean showNetwork, boolean showTree, int maxTreeLevels, String viewElementId) {
	  this.setShowNetwork(showNetwork);
	  this.setShowTree(showTree);
	  this.setMaxTreeLevels(maxTreeLevels);
	  this.viewElementId = viewElementId;
		if ("treeview".equals(this.viewElementId)) {
		  setLinksLength(100);
		  outPath = "./ef/chtree/";
		}
		
	}
	
	@Override
	public String getVal_String() {
	  return sb.toString();
	}
	
	@Override
	public boolean initialize(String outFileName, String modelName, IXholon root) {
	  timeNow = new Date();
		timeStamp = timeNow.getTime();
		if (outFileName == null) {
			this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".js";
		}
		else {
			this.outFileName = outFileName;
		}
		this.modelName = modelName;
		this.root = root;
		this.makeShapeMap();
		
		// TODO why are there 2 "var nodesTable" and 2 "var linksTable" ?
		this.startSb = new StringBuilder(128)
		.append("var nodesTable = null;\n")
		.append("var linksTable = null;\n")
		.append("var network = null;\n")
		.append("function draw() {\n");
		
		this.nodeSb = new StringBuilder(SB_INITIAL_CAPACITY)
		.append("// nodes\n")
		.append("var nodesTable = [];\n");
		
		this.linkSb = new StringBuilder(SB_INITIAL_CAPACITY)
		.append("// links\n")
		.append("var linksTable = [];\n");
		
		if ("network-min".equals(this.getJsLibName())) { // old library
		  this.optionsSb = new StringBuilder(SB_INITIAL_CAPACITY)
		  .append("// options\n")
		  .append("var options = {\n")
		  .append("  'width': '" + getWidth() + "',\n")
		  .append("  'height': '" + getHeight() + "',\n")
		  .append("  'links': {\n")
		  .append("    'length': " + getLinksLength() + "\n")
		  .append("  },\n")
		  .append("  'nodes': {\n")
		  .append("    'style': '" + getNodesStyle() + "'\n")
		  .append("  },\n")
		  .append("  'stabilize': " + getStabilize() + "\n")
		  .append("};\n");
		  
		  this.endSb = new StringBuilder(128)
		  .append("network = new links.Network(document.getElementById('")
		  .append(viewElementId)
		  .append("'));\n")
		  .append("network.draw(nodesTable, linksTable, options);\n")
		  .append("}\n")
		  .append("draw();\n");
		}
		/*
var container = document.getElementById('mynetwork');
var data = {nodes: nodesTable,edges: linksTable};
var network = new vis.Network(container, data, options);
		*/
		else { // vis-network.min  new library
		  this.optionsSb = new StringBuilder(SB_INITIAL_CAPACITY)
		  .append("// options\n")
		  .append("var options = {\n")
		  .append("  'width': '" + getWidth() + "',\n")
		  .append("  'height': '" + getHeight() + "',\n")
		  .append("  'edges': {\n")
		  .append("    'length': " + getLinksLength() + "\n")
		  .append("  },\n")
		  .append("  'nodes': {\n")
		  .append("    'shape': '" + getNodesStyle() + "'\n")
		  .append("  },\n")
		  .append("  'autoResize': " + getStabilize() + "\n")
		  .append("};\n");
		  
		  this.endSb = new StringBuilder(128)
		  .append("var container = document.getElementById('")
		  .append(viewElementId)
		  .append("');\n")
		  .append("var data = {nodes: nodesTable, edges: linksTable};\n")
		  .append("network = new vis.Network(container, data, options);\n")
		  .append("}\n")
		  .append("draw();\n");
		}
		
		if (!isDefinedChapLinksNetwork(this.getJsLibName())) {
		  loadChapLinksNetwork();
      return true; // do not return false; that just causes an error message
    }
		
		return true;
	}

	@Override
	public void writeAll() {
	  if (!isDefinedChapLinksNetwork(this.getJsLibName())) {
	    return;
	  }
	  if ("vis-network.min".equals(this.getJsLibName())) {
	    linksTableLable = "label";
	    nodesTableLable = "label";
	  }
	  writeNode(root);
	  sb = new StringBuilder()
		.append("// ")
		.append(modelName)
		.append("\n// see http://almende.github.io/chap-links-library/network.html")
		.append("\n\n")
		.append(startSb.toString())
		.append(nodeSb.toString())
		.append(linkSb.toString())
		.append(optionsSb.toString())
		.append(endSb.toString());

		writeToTarget(sb.toString(), outFileName, outPath, root);
		
		if (root.getApp().isUseGwt()) {
			pasteScript("gnvScript", sb.toString());
		}
		else {
			// TODO possibly write to a new browser window; open window and then write data
			//System.out.println(sb.toString());
		}
	}

	@Override
	public void writeNode(IXholon xhNode) {
		// xhNode details
		String nodeName = xhNode.getName(getNameTemplate());
		if ((getMaxChars() != -1) && (nodeName.length() > getMaxChars())) {
		  nodeName = nodeName.substring(0, getMaxChars());
		}
		nodeSb
		.append("nodesTable.push({'id': ")
		.append(xhNode.getId())
		.append(", '" + nodesTableLable + "': '")
		.append(nodeName);
		String shape = this.makeShape(xhNode);
		if (shape != null) {
		  nodeSb
		  .append("', '" + "style" + "': '")
		  .append(shape);
		}
		nodeSb
		.append("'});\n");
		// xhNode outgoing edges
		writeEdges(xhNode);
		// xhNode children
		IXholon childNode = xhNode.getFirstChild();
		while (childNode != null) {
			writeNode(childNode);
			childNode = childNode.getNextSibling();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void writeEdges(IXholon xhNode) {
	  //System.out.println("writeEdges");
	  // show network
	  if (isShowNetwork()) {
		  //List<PortInformation> portList =
			//	  new org.primordion.xholon.base.ReflectionJavaMicro().getAllPorts(xhNode, false);
			List<PortInformation> portList = xhNode.getLinks(false, true);
		  for (int i = 0; i < portList.size(); i++) {
			  PortInformation pi = (PortInformation)portList.get(i);
		    linkSb.append("linksTable.push({'from': ")
			  .append(xhNode.getId())
			  .append(", 'to': ")
			  .append(pi.getReffedNode().getId())
			  .append(", '" + linksTableLable + "': '");
			  if (isShowPortName()) {
			    linkSb.append(pi.getFieldName());
			  }
			  else {
			    linkSb.append("");
			  }
			  if ("network-min".equals(this.getJsLibName())) {
			    linkSb.append("', 'style': '" + getLinksStyle() + "'");
			  }
			  else {
  			  // {from: 1, to: 3, arrows:'to'}
			    linkSb.append("', 'arrows': 'to'");
			  }
			  linkSb.append("});\n");
		  }
		}
		
		// show tree
		if (isShowTree()) {
		  if ((xhNode != root)
		      && ((xhNode.isLeaf() && (getMaxTreeLevels() > 0)) || (getMaxTreeLevels() > 1)) ) {
		    IXholon pNode = xhNode.getParentNode();
		    linkSb.append("linksTable.push({'from': ")
			  .append(pNode.getId())
			  .append(", 'to': ")
			  .append(xhNode.getId())
			  .append(", '" + linksTableLable + "': '")
			  .append("");
			  if ("network-min".equals(this.getJsLibName())) {
			    linkSb.append("', 'style': undefined");
			  }
			  else {
			    linkSb.append("'");
			  }
			  linkSb.append("});\n");
		  }
		}
		
	}

	@Override
	public void writeNodeAttributes(IXholon xhNode) {
		// nothing to do for now
	}
	
	protected void pasteScript(String scriptId, String scriptContent) {
	  HtmlScriptHelper.fromString(scriptContent, true);
	}
	
	/**
   * Load CHAP Links Network library asynchronously.
   */
  protected void loadChapLinksNetwork() {
    require(this, this.getJsLibName());
  }
  
  /**
   * use requirejs
   */
  protected native void require(final IXholon2GraphFormat xh2Chap, String jsLibName) /*-{
    if (!(jsLibName == "network-min")) {return;}
    $wnd.requirejs.config({
      enforceDefine: false,
      paths: {
        network: [
          //"xholon/lib/network-min"
          "xholon/lib/" + jsLibName
        ]
      }
    });
    $wnd.require(["network"], function(network) {
      xh2Chap.@org.primordion.xholon.service.ef.IXholon2GraphFormat::writeAll()();
    });
  }-*/;
  
  /**
   * Is $wnd.links.Network defined.
   * @return it is defined (true), it's not defined (false)
   */
  protected native boolean isDefinedChapLinksNetwork(String jsLibName) /*-{
    if (jsLibName == "network-min") {
      return (typeof $wnd.links != "undefined") && (typeof $wnd.links.Network != "undefined");
    }
    else { // vis-network.min
      //return (typeof $wnd.vis != "undefined") && (typeof $wnd.vis.Network != "undefined");
      return true;
    }
  }-*/;
  
  /**
   * Try to make a custom shape for a node.
   * @param node
   * @return A shape string, or null.
   */
  protected String makeShape(IXholon node) {
    if (shapeMap == null) {return null;}
    String shapeName = shapeMap.get(node.getXhcName()); // TODO also try superclasses
    //if (shapeName == null) {return null;}
    return shapeName;
  }
  
  /**
   * Optionally create the shapeMap, and add entries to it.
   * ex: "ellipse"
   * ex: "circle,Cable:point"
   * ex: "box,Pack:circle,Cable:point"
   */
  protected void makeShapeMap() {
    //if (!isShouldSpecifyShape()) {return;}
    String[] shapeArr = this.getNodesStyle().split(",");
    // ignore the first item in the array; this is the default
    if (shapeArr.length > 1) {
      shapeMap = new HashMap<String, String>();
      for (int i = 1; i < shapeArr.length; i++) {
        String[] entryArr = shapeArr[i].split(":");
        if (entryArr.length == 2) {
          String entryXhcName = entryArr[0].trim();
          String entryShapeName = entryArr[1].trim();
          shapeMap.put(entryXhcName, entryShapeName);
        }
      }
      setNodesStyle(shapeArr[0]); // set the default shape
    }
  }
  
  public native boolean isShowNetwork() /*-{return this.efParams.showNetwork;}-*/;
  public native void setShowNetwork(boolean showNetwork) /*-{this.efParams.showNetwork = showNetwork;}-*/;
  
  public native boolean isShowTree() /*-{return this.efParams.showTree;}-*/;
  public native void setShowTree(boolean showTree) /*-{this.efParams.showTree = showTree;}-*/;
  
  /** Number of tree levels to show, if showTree == true */
  public native int getMaxTreeLevels() /*-{return this.efParams.maxTreeLevels;}-*/;
  public native void setMaxTreeLevels(int maxTreeLevels) /*-{this.efParams.maxTreeLevels = maxTreeLevels;}-*/;

  /** Node name template */
  public native String getNameTemplate() /*-{return this.efParams.nameTemplate;}-*/;
  public native void setNameTemplate(String nameTemplate) /*-{this.efParams.nameTemplate = nameTemplate;}-*/;
  
  /** Number of characters to show in the node name */
  public native int getMaxChars() /*-{return this.efParams.maxChars;}-*/;
  public native void setMaxChars(int maxChars) /*-{this.efParams.maxChars = maxChars;}-*/;

  /** Name of the Network JavaScript library */
  public native String getJsLibName() /*-{return this.efParams.jsLibName;}-*/;
  public native void setJsLibName(String jsLibName) /*-{this.efParams.jsLibName = jsLibName;}-*/;
  
  /**
   * Make a JavaScript object with all the parameters for this external format.
   */
  protected native void makeEfParams() /*-{
    var p = {};
    p.showNetwork = true;
    p.showTree = false;
    p.maxTreeLevels = 1;
    p.width = "600px";
    p.height = "600px";
    p.nameTemplate = "R^^^^^";
    p.maxChars = -1; // -1 1 3
    p.linksLength = 50;
    p.showPortName = false;
    p.nodesStyle = "dot"; // "dot" "circle,Cable:dot" rect (default), circle, database, image, text, dot, star, triangle, triangleDown, and square
    p.linksStyle = "arrow-end"; // "arrow-end" "dash-line" "line"(their default) "arrow" "moving-arrows" OR undefined
    p.stabilize = "false";
    p.jsLibName = "network-min"; // network-min OR vis-network.min
    this.efParams = p;
  }-*/;


  public native String getWidth() /*-{return this.efParams.width;}-*/;
  //public native void setWidth(String width) /*-{this.efParams.width = width;}-*/;

  public native String getHeight() /*-{return this.efParams.height;}-*/;
  //public native void setHeight(String height) /*-{this.efParams.height = height;}-*/;

  /**
   * The length of a link.
   * 100
   */
  public native int getLinksLength() /*-{return this.efParams.linksLength;}-*/;
  public native void setLinksLength(int linksLength) /*-{this.efParams.linksLength = linksLength;}-*/;

  /**
   * Whether or not a link should show the name of the port.
   */
  public native boolean isShowPortName() /*-{return this.efParams.showPortName;}-*/;
  //public native void setShowPortName(boolean showPortName) /*-{this.efParams.showPortName = showPortName;}-*/;

  // rect text image
  public native String getNodesStyle() /*-{return this.efParams.nodesStyle;}-*/;
  public native void setNodesStyle(String nodesStyle) /*-{this.efParams.nodesStyle = nodesStyle;}-*/;

  public native String getLinksStyle() /*-{return this.efParams.linksStyle;}-*/;
  //public native void setLinksStyle(String linksStyle) /*-{this.efParams.linksStyle = linksStyle;}-*/;

  public native String getStabilize() /*-{return this.efParams.stabilize;}-*/;
  //public native void setStabilize(String stabilize) /*-{this.efParams.stabilize = stabilize;}-*/;
  
}

/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2012 Ken Webb
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primordion.xholon.base.IDecoration;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXPath;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.xholon.service.AbstractXholonService;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Write a Xholon model in Graphviz dot format.
 * Example:
<pre>
digraph 0 { label=HelloWorldSystem
 layout=fdp;
 1 [label=Hello]
  1 -> 2;
 2 [label=World]
  2 -> 1;
}
</pre>

 * For an example of handling cluster - cluster, and cluster -childNode edges, see:
 *  https://gist.github.com/kenwebb/f8be0168f91899ffa47f
 *
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 14, 2012)
 */
@SuppressWarnings("serial")
public class Xholon2Graphviz extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {

  protected static final int GV_MAXLABELLEN_NULL = -1;
  
  protected String outFileName;
  protected String outPath = "./ef/graphviz/";
  protected String modelName;
  protected IXholon root;
  private StringBuilder sb;
  
  /** Current date and time. */
  protected Date timeNow;
  protected long timeStamp;
  
  /** A local singleton instance of XPath. */
  protected IXPath xPathLocal = null;
  
  /**
   * For use with indenting by level.
   */
  protected String blanks = "                         ";
  
  /**
   * Edges that need to be output by an ancestor node.
   */
  protected Map<IXholon, String> edgeMap = null;
  
  /**
   * A very simple structure for searching through any filters that are specified.
   * There will probably never be very many filters,
   * so a simple sequential search through an array of filters may be adequate.
   * TODO handle node IDs
   *  - possibly use int[] for everything rather than String[]; convert names to IDs
   * Example filter strings:
   *   --Behavior,Script,StateMachineEntity
   *   --14,23,55
   *   ++StateMachineEntity
   *   ++17,33-35,62
   *   -+Row,HabitatCell   just do the agents that live in the grid
   *   +-Bank              process all Bank nodes, but not the BankAccounts inside the banks
   */
  protected String[] filter = null;
  
  /**
   * If one or more filters have been specified (filter != null)
   * then filter which nodes are processed (written out).
   * true process only those nodes that are specified in filter
   * false do not process any nodes that are specified in filter
   */
  protected boolean filterProcessing = false;
  
  /**
   * If one or more filters have been specified (filter != null)
   * then filter which nodes are visited during the tree traversal.
   * true traverse only to children of nodes that are specified in filter
   * false do not traverse to nodes that are specified in filter
   *   ignore this node's entire subtree
   */
  protected boolean filterTraversal = false;
  
  /**
   * Constructor
   */
  public Xholon2Graphviz() {}
  
  /*
   * @see org.primordion.ef.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
   */
  public boolean initialize(String outFileName, String modelName, IXholon root) {
    timeNow = new Date();
    timeStamp = timeNow.getTime();
    if (outFileName == null) {
      this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + getGvFileExt();
    }
    else {
      this.outFileName = outFileName;
    }
    this.modelName = modelName;
    this.root = root;
    this.edgeMap = null;
    setXPathLocal((IXPath)root.getService(AbstractXholonService.XHSRV_XPATH));
    initializeFilter();
    
    return true;
  }
  
  /**
   * Initialize filters.
   */
  protected void initializeFilter() {
    String fstr = getFilter();
    if ((fstr != null) && (fstr.length() > 2)) {
      // for now assume that all filter strings begin with one of "--" "-+" "+-" "++"
      switch (fstr.charAt(0)) {
      case '-': filterProcessing = false; break;
      case '+': filterProcessing = true;  break;
      default: break;
      }
      switch (fstr.charAt(1)) {
      case '-': filterTraversal = false; break;
      case '+': filterTraversal = true;  break;
      default: break;
      }
      filter = fstr.substring(2).split(",");
    }
  }
  
  /**
   * Filter using filterTraversal.
   * TODO this is an initial simplistic version that needs to be extended
   * TODO handle filters that specify node ID rather than node XholonClass
   * @param node A Xholon node that may potentially be filtered out.
   * @return The input node, or null.
   */
  protected IXholon filter(IXholon node) {
    if (node == null) {return null;}
    if (filter == null) {return node;}
    IXholonClass xhcNode = node.getXhc();
    
    // "+" return only those nodes that are specified in filter
    if (filterTraversal) {
      for (int i = 0; i < filter.length; i++) {
        if (xhcNode.hasAncestor(filter[i])) {
          return node;
        }
      }
      return filter(node.getNextSibling());
    }
    // "-" pass over nodes that are specified in filter
    else {
      for (int i = 0; i < filter.length; i++) {
        if (xhcNode.hasAncestor(filter[i])) {
          return filter(node.getNextSibling());
        }
      }
      return node;
    }
  }

  /*
   * @see org.primordion.ef.IXholon2ExternalFormat#writeAll()
   */
  public void writeAll() {
    sb = new StringBuilder();
    String fn = root.getXhcName() + "_" + root.getId() + "_" + timeStamp;
    sb.append(
      "/*\nAutomatically generated by Xholon version 0.8.1, using "
        + this.getClass().getName() + ".java\n"
        + new Date() + " " + timeStamp + "\n"
        + "model: " + modelName + "\n"
        + "www.primordion.com/Xholon\n\n");
    sb.append("To view this file, use Graphviz dot|fdp|neato|twopi|circo|sfdp from http://www.graphviz.org/\n");
    sb.append("For example, to generate an SVG file:\n");
    sb.append("  " + getLayout() + " -Tsvg -O " + fn + getGvFileExt() + "\n");
    sb.append("Alternatively try one of these:\n");
    sb.append("  " + "dot" + " -Tsvg -O " + fn + getGvFileExt() + "\n");
    sb.append("  " + "dot" + " -Tsvg -O -Grankdir=LR " + fn + getGvFileExt() + "\n");
    sb.append("  " + "fdp" + " -Tsvg -O " + fn + getGvFileExt() + "\n");
    sb.append("  " + "neato" + " -Tsvg -O " + fn + getGvFileExt() + "\n");
    sb.append("  " + "circo" + " -Tsvg -O " + fn + getGvFileExt() + "\n");
    sb.append("Or use one of: -Tgif -Tjpg -Tpdf -Tpng -Txdot -Txlib\n");
    sb.append("See also: http://hughesbennett.net/Graphviz\n");
    sb.append("See also: http://graphviz-dev.appspot.com/\n");
    sb.append("See also: http://www.webgraphviz.com/\n");
    sb.append("See also: http://rise4fun.com/agl/\n");
    sb
    .append("\nTo repeat this Xholon export:\n")
    .append(" $wnd.xh.xport(\"Graphviz\", ");
    if (root.isRootNode()) {
      sb.append("$wnd.xh.root()");
    }
    else {
      sb
      .append("$wnd.xh.root().parent().xpath(\"")
      .append(getXPathLocal().getExpression(root, root.getRootNode(), false))
      .append("\")");
    }
    sb
    .append(", '")
    .append(getEfParamsAsJsonString())
    .append("');\n");
    sb.append("*/\n");
    writeNode(root, 0); // root is level 0
    
    writeToTarget(sb.toString(), outFileName, outPath, root);
    
    if (isShouldDisplayGraph()) {
      if (!isDefinedViz()) {
        loadVizjs();
      }
      else {
        displayVizjs();
      }
    }
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
    String nodeId = makeNodeId(node);
    String nodeLabel = makeNodeLabel(node);
    if (isShouldQuoteLabels()) {
      if (nodeLabel != null) {
        nodeLabel = "\"" + nodeLabel + "\"";
      }
    }
    String tab = blanks.substring(0, level);
    IXholon childNode = filter(node.getFirstChild()); // pre-cache the filtered firstChild
    //if (node.hasChildNodes()) {
    if (childNode != null) {
      if (node == root) {
        sb.append(getGvGraph() + " ");
        sb.append(nodeId);
        sb.append(" {\n");
        
        // graph attributes
        sb.append(" graph [");
        String graphSep = "";
        if (nodeLabel != null) {
          sb.append("label=" + nodeLabel);
          graphSep = ",";
        }
        if (isShouldDisplayGraph() && "svg".equals(getOutputFormat())) {
          sb.append(graphSep).append("id=\"" + getGraphvizNodeId(node) + "\"");
          graphSep = ",";
        }
        if (isShouldSpecifyLayout()) {
          sb.append(graphSep).append("layout=" + getLayout());
          graphSep = ",";
        }
        if (isShouldSpecifyStylesheet()) {
          sb.append(graphSep).append("stylesheet=\"" + getStylesheet() + "\"");
          graphSep = ",";
        }
        if (isShouldSpecifyRankdir()) {
          sb.append(graphSep).append("rankdir=" + getRankdir());
          graphSep = ",";
        }
        if (isShouldSpecifySize()) {
          sb.append(graphSep).append("size=" + getSize());
          graphSep = ",";
        }
        if (isShouldSpecifyFontname()) {
          sb.append(graphSep).append("fontname=" + getFontname());
          graphSep = ",";
        }
        if (!"".equals(getGvCluster())) {
          sb.append(graphSep).append("compound=true");
          graphSep = ",";
        }
        sb.append("]\n");
        
        // node attributes
        if (isShouldColor() || isShouldSpecifyShape() || isShouldSpecifyFontname()) {
          String colorShapeSep = "";
          sb.append(" node [");
          if (isShouldColor()) {
            sb
            .append("style=filled,fillcolor=\"")
            .append(getDefaultColor())
            .append("\"");
            colorShapeSep = ",";
          }
          if (isShouldSpecifyShape()) {
            sb
            .append(colorShapeSep)
            .append("shape=")
            .append(getShape());
            colorShapeSep = ",";
          }
          if (isShouldSpecifyFontname()) {
            sb.append(colorShapeSep).append("fontname=" + getFontname());
          }
          sb.append("]\n");
        }
        
        // edge attributes
        if (isShouldSpecifyArrowhead()) {
          sb
          .append(" edge [")
          .append("arrowhead=")
          .append(getArrowhead())
          .append("]\n");
        }
        
      } // end (node == root)
      else if (isCluster(node)) {
        sb.append(tab + "subgraph ");
        nodeId = getGvCluster() + nodeId;
        sb.append(nodeId);
        sb.append(" {");
        if (nodeLabel != null) {
          sb.append(" label=" + nodeLabel);
        }
        if (isShouldDisplayGraph() && "svg".equals(getOutputFormat())) {
          sb.append(" id=\"" + getGraphvizNodeId(node) + "\"");
        }
        sb.append("\n");
        // TODO cluster rule #2: if the node has ports or is reffed by ports
        if (false) {
          sb.append(tab).append(" ").append(nodeId).append(" [shape=point,style=invis];\n");
        }
      }
      else {
        // this is not a cluster (ex: Petri net Transition node)
        this.writeNonClusterNode(node, level, nodeId, nodeLabel, tab);
      }
      while (childNode != null) {
        writeNode(childNode, level+1);
        childNode = filter(childNode.getNextSibling());
      }
      if (isCluster(node) || node == root) {
        writeLinks(node, nodeId, tab);
        sb.append(tab + "}\n");
      }
    }
    else { // this node is not a cluster or root
      this.writeNonClusterNode(node, level, nodeId, nodeLabel, tab);
    }
  }
  
  /**
   * Write a node that is not a cluster and not root.
   * @param node
   * @param level
   * @param nodeId
   * @param nodeLabel
   * @param tab
   */
  protected void writeNonClusterNode(IXholon node, int level, String nodeId, String nodeLabel, String tab) {
    sb.append(tab + nodeId);
    if (nodeLabel != null) {
      sb.append(" [label=" + nodeLabel);
      if (isShouldDisplayGraph() && "svg".equals(getOutputFormat())) {
        sb.append(" id=\"" + getGraphvizNodeId(node) + "\"");
      }
      sb.append("]");
    }
    sb.append("\n");
    String colorStr = makeColor(node);
    if (colorStr != null) {
      sb.append(tab + " [" + colorStr + "]\n");
    }
    String shapeStr = makeShape(node);
    if (shapeStr != null) {
      sb.append(tab + " [" + shapeStr + "]\n");
    }
    writeLinks(node, nodeId, tab);
  }
  
  /**
   * Write edges from this node to any others, where Xholon has connected ports.
   * @param node The current node.
   */
  @SuppressWarnings("unchecked")
  protected boolean writeLinks(IXholon node, String nodeId, String tab) {
    if (isShouldShowLinks() == false) {return false;}
    tab += " ";
    boolean rc = false;
    int nodeNum = 0;
    List<PortInformation> portList = node.getAllPorts();
    for (int i = 0; i < portList.size(); i++) {
      if (writeLink(nodeNum, node, nodeId, (PortInformation)portList.get(i), "", false, tab)) {
        rc = true;
      }
      nodeNum++;
    }
    // process edges where node is the first common ancestor
    /*if (edgeMap != null) {
      String value = (String)edgeMap.get(node);
      if (value != null) {
        sb.append(tab + value + "\n");
      }
    }*/
    processEdgeMapValue(node, tab);
    return rc;
  }
  
  /**
   * Process edges where node is the first common ancestor
   */
  protected void processEdgeMapValue(IXholon node, String tab) {
    if (edgeMap != null) {
      String value = (String)edgeMap.get(node);
      if (value != null) {
        sb.append(tab + value + "\n");
      }
    }
  }
  
  /**
   * Write one link.
   * @param nodeNum Number of this node, used to distinguish multiple links between the same nodes.
   * @param node The node where the link originates.
   * @param nodeId
   * @param portInfo Information about the port that represents the link.
   * @param linkLabel An optional label for the link.
   * It must be a fully formatted label preceeded by a blank, or a zero-length String.
   * @param reverseArrows Whether or not to reverse the direction of the arrows.
   * @param tab
   */
  protected boolean writeLink(int nodeNum, IXholon node, String nodeId, final PortInformation portInfo,
      String linkLabel, boolean reverseArrows, String tab) {
    // TODO cluster rule #3: add " [ltail=CLUSTER_NNN] and/or "lhead=CLUSTER_NNN"
    //   ex: [ltail=cluster0,lhead=cluster1]
    if (portInfo == null) {return false;}
    boolean rc = true;
    IXholon remoteNode = portInfo.getReffedNode();
    if (remoteNode == null) {return false;}
    if (!remoteNode.hasAncestor(root.getName())) {
      // remoteNode is outside the scope (not a descendant) of root
      return false;
    }
    String remoteNodeId = makeNodeId(remoteNode);
    if (isCluster(remoteNode)) {
      remoteNodeId = getGvCluster() + remoteNodeId;
    }
    StringBuilder sbEdge = new StringBuilder();
    if (reverseArrows) {
      sbEdge.append(remoteNodeId).append(" ").append(getEdgeOp()).append(" ").append(nodeId).append(linkLabel).append(";");
    }
    else {
      sbEdge.append(nodeId).append(" ").append(getEdgeOp()).append(" ").append(remoteNodeId).append(linkLabel).append(";");
    }
    String edgeStr = sbEdge.toString();
    if ((node.getParentNode() == remoteNode.getParentNode())
        || (remoteNode.hasAncestor(node.getName()))) {
      // node and remoteNode are siblings, or remoteNode is a descendent of node
      sb.append(tab + edgeStr + "\n");
    }
    else {
      if (edgeMap == null) {
        edgeMap = new HashMap<IXholon, String>();
      }
      // find first common ancestor
      IXholon fca = findFirstCommonAncestor(node, remoteNode);
      if (fca == null) {return false;}
      // if common ancestor is not in edgeMap, then add it
      // add or append edgeStr to the edgeMap entry
      String value = (String)edgeMap.get(fca);
      if (value == null) {
        edgeMap.put(fca, edgeStr);
      }
      else {
        value += " " + edgeStr;
        edgeMap.put(fca, value);
      }
      rc = false;
    }
    return rc;
  }
  
  /**
   * Try to make a custom color for a node.
   * The XholonClass or Mechanism may define a color.
   * @param node
   * @return A color string, or null.
   */
  protected String makeColor(IXholon node) {
    IXholon xhcNode = node.getXhc();
    if (isShouldColor()) {
      String color = null;
      while ((xhcNode != null) && (xhcNode instanceof IXholonClass)) {
        color = ((IDecoration)xhcNode).getColor();
        if (color == null) {
          color = ((IDecoration)((IXholonClass)xhcNode).getMechanism()).getColor();
        }
        if (color != null) {
          break;
        }
        xhcNode = xhcNode.getParentNode();
      }
      if ((color != null) && (color.length() > 2)) {
        // convert 0x2E8B57 to #2E8B57
        if (color.startsWith("0x")) {
          color = "#" + color.substring(2);
        }
        return "fillcolor=\"" + color + "\"";
      }
    }
    return null;
  }
  
  /**
   * Try to make a custom shape for a node.
   * @param node
   * @return A shape string, or null.
   */
  protected String makeShape(IXholon node) {
    return null;
  }
  
  /**
   * Find the first common ancestor of two nodes.
   * @param nodeA
   * @param nodeB
   * @return A common ancestor, or null.
   */
  protected IXholon findFirstCommonAncestor(IXholon nodeA, IXholon nodeB) {
    IXholon pA = nodeA.getParentNode();
    while (pA != null) {
      IXholon pB = nodeB;
      while (pB != null) {
        if (pA == pB) {
          return pA;
        }
        if (pB == root) {break;}
        pB = pB.getParentNode();
      }
      if (pA == root) {break;}
      pA = pA.getParentNode();
    }
    return null;
  }
  
  /**
   * Make a node ID.
   * @param node
   * @return
   */
  protected String makeNodeId(IXholon node) {
    return node.getName(getNameTemplateNodeId());
  }
  
  /**
   * Make a node label.
   * @param node
   * @return A node label, or null if the node should not have a label.
   */
  protected String makeNodeLabel(IXholon node) {
    if (getMaxLabelLen() == 0) {
      return null;
    }
    String label = node.getName(getNameTemplateNodeLabel());
    if (getMaxLabelLen() > GV_MAXLABELLEN_NULL) {
      if (getMaxLabelLen() < label.length()) {
        label = label.substring(0, getMaxLabelLen());
      }
    }
    return label;
  }
  
  /**
   * Is this a cluster node?
   * If a node is a cluster, then the GraphViz
   * "layout engine will do the layout so that the nodes belonging to the cluster
   * are drawn together, with the entire drawing of the cluster contained within
   * a bounding rectangle"
   * @param node
   * @return
   */
  protected boolean isCluster(IXholon node) {
    if (node.hasChildNodes() && (node != root)) {
      return true;
    }
    return false;
  }
  
  /**
   * Get a value for the node's Graphviz id attribute.
   * Graphiz will use this if the output format is SVG.
   * @param node
   * @return
   */
  protected String getGraphvizNodeId(IXholon node) {
    return getXPathLocal().getExpression(node, root, false);
  }
  
  /**
   * Load viz.js library asynchronously.
   */
  protected void loadVizjs() {
    require(this);
  }
  
  /**
   * Display the Graphviz content as SVG or other output format, using viz.js .
   */
  public void displayVizjs() {
    String of = getOutputFormat();
    String out = graphviz2of(sb.toString(), of);
    if (out != null) {
      if ("".equals(out)) {
        out = "Error: renderer for " + of + " is unavailable";
        of = "error";
      }
      /*if ("gif".equals(of) || "jpg".equals(of) || "png".equals(of)) {
        // TODO
        return;
      }*/
      writeToTarget(out, outFileName + "." + of, outPath, root);
      if ("svg".equals(of)) {
        root.getApp().makeSvgClient(out);
      }
    }
  }
  
  /**
   * Convert Graphviz content to a Graphviz-supported output format.
   * svg = Viz("digraph { a -> b; }", "svg");
   * @param graphvizContent Content in the DOT language.
   * @param outputFormat ex: "svg" "xdot" "dot" "canon" "plain" "ps" "tk"
   *   "vrml"NO "pdf"NO "png"NO "jpg"NO "gif"NO
   */
  protected native String graphviz2of(String graphvizContent, String outputFormat) /*-{
    var out = $wnd.Viz(graphvizContent, outputFormat);
    return out;
  }-*/;
  
  /**
   * use requirejs
   * viz.js is a 2.5MB file, so wait longer than the default 7 seconds
   */
  protected native void require(final IXholon2ExternalFormat xh2Graphviz) /*-{
    $wnd.requirejs.config({
      enforceDefine: false,
      paths: {
        viz: [
          "xholon/lib/viz"
        ]
      },
      waitSeconds: 20
    });
    $wnd.require(["viz"], function(viz) {
      xh2Graphviz.@org.primordion.ef.Xholon2Graphviz::displayVizjs()();
    });
  }-*/;
  
  /**
   * Is $wnd.Viz defined.
   * @return it is defined (true), it's not defined (false)
   */
  protected native boolean isDefinedViz() /*-{
    return (typeof $wnd.Viz != "undefined");
  }-*/;
  
  public IXPath getXPathLocal() {
    return xPathLocal;
  }

  public void setXPathLocal(IXPath xPathLocal) {
    this.xPathLocal = xPathLocal;
  }
  
  /**
   * Make a JavaScript object with all the parameters for this external format.
   */
  protected native void makeEfParams() /*-{
    var p = {};
    p.gvFileExt = ".gv";
    p.gvGraph = "digraph";
    p.layout = "dot";
    p.edgeOp = "->";
    p.gvCluster = "";
    p.shouldShowStateMachineEntities = false;
    p.filter = "--Behavior,Script"; //,StateMachineEntity";
    p.nameTemplateNodeId = "^^^^i^";
    p.nameTemplateNodeLabel = "R^^^^^";
    p.shouldQuoteLabels = true;
    p.shouldShowLinks = true;
    p.shouldSpecifyLayout = false;
    p.maxLabelLen = -1;
    p.shouldColor = true;
    p.defaultColor = "#f0f8ff";
    p.shouldSpecifyShape = false;
    p.shape = "ellipse";
    
    p.shouldSpecifySize = false;
    p.size = "6";
    p.shouldSpecifyFontname = false;
    p.fontname = "\"Courier New\"";
    p.shouldSpecifyArrowhead = false;
    p.arrowhead = "vee";
    
    p.shouldSpecifyStylesheet = true;
    p.stylesheet = "Xholon.css";
    p.shouldSpecifyRankdir = false;
    p.rankdir = "LR";
    p.shouldDisplayGraph = false;
    p.outputFormat = "svg";
    this.efParams = p;
  }-*/;
  
  /** ".gv" or ".dot" */
  public native String getGvFileExt() /*-{return this.efParams.gvFileExt;}-*/;
  //public native void setGvFileExt(String gvFileExt) /*-{this.efParams.gvFileExt = gvFileExt;}-*/;

  /** "graph" or "digraph" */
  public native String getGvGraph() /*-{return this.efParams.gvGraph;}-*/;
  //public native void setGvGraph(String gvGraph) /*-{this.efParams.gvGraph = gvGraph;}-*/;

  /** one of "dot" "neato" "twopi" "circo" "fdp" "sfdp" */
  public native String getLayout() /*-{return this.efParams.layout;}-*/;
  //public native void setLayout(String layout) /*-{this.efParams.layout = layout;}-*/;

  /** "--" or "->" */
  public native String getEdgeOp() /*-{return this.efParams.edgeOp;}-*/;
  //public native void setEdgeOp(String edgeOp) /*-{this.efParams.edgeOp = edgeOp;}-*/;
  
  /** "cluster" or any other string */
  public native String getGvCluster() /*-{return this.efParams.gvCluster;}-*/;
  //public native void setGvCluster(String gvCluster) /*-{this.efParams.gvCluster = gvCluster;}-*/;

  /** Whether or not to show state machine nodes. */
  public native boolean isShouldShowStateMachineEntities() /*-{return this.efParams.shouldShowStateMachineEntities;}-*/;
  //public native void setShouldShowStateMachineEntities(boolean shouldShowStateMachineEntities) /*-{this.efParams.shouldShowStateMachineEntities = shouldShowStateMachineEntities;}-*/;

  public native String getFilter() /*-{return this.efParams.filter;}-*/;
  //public native void setFilter(String filter) /*-{this.efParams.filter = filter;}-*/;

  /**
   * Template to use when writing out node IDs.
   * "^^^^i^" id only
   */
  public native String getNameTemplateNodeId() /*-{return this.efParams.nameTemplateNodeId;}-*/;
  //public native void setNameTemplateNodeId(String nameTemplateNodeId) /*-{this.efParams.nameTemplateNodeId = nameTemplateNodeId;}-*/;

  /**
   * Template to use when writing out node labels.
   * "R^^^^^" roleName or className, but not both
   * "r:C^^^" shouldQuoteLabels = true
   * "r^^^^^" roleName only
   * "^^C^^^" className only
  */
  public native String getNameTemplateNodeLabel() /*-{return this.efParams.nameTemplateNodeLabel;}-*/;
  //public native void setNameTemplateNodeLabel(String nameTemplateNodeLabel) /*-{this.efParams.nameTemplateNodeLabel = nameTemplateNodeLabel;}-*/;

  /** Whether or not labels/names should be quoted. */
  public native boolean isShouldQuoteLabels() /*-{return this.efParams.shouldQuoteLabels;}-*/;
  //public native void setShouldQuoteLabels(boolean shouldQuoteLabels) /*-{this.efParams.shouldQuoteLabels = shouldQuoteLabels;}-*/;

  /** Whether or not to draw edges between nodes. */
  public native boolean isShouldShowLinks() /*-{return this.efParams.shouldShowLinks;}-*/;
  //public native void setShouldShowLinks(boolean shouldShowLinks) /*-{this.efParams.shouldShowLinks = shouldShowLinks;}-*/;

  /** Whether or not to specify the layout engine. */
  public native boolean isShouldSpecifyLayout() /*-{return this.efParams.shouldSpecifyLayout;}-*/;
  //public native void setShouldSpecifyLayout(boolean shouldSpecifyLayout) /*-{this.efParams.shouldSpecifyLayout = shouldSpecifyLayout;}-*/;

  /**
   * Maximum length of a label.
   * <p>GV_MAXLABELLEN_NULL - no max len </p>
   * <p>0 - don't show labels</p>
   */
  public native int getMaxLabelLen() /*-{return this.efParams.maxLabelLen;}-*/;
  //public native void setMaxLabelLen(int maxLabelLen) /*-{this.efParams.maxLabelLen = maxLabelLen;}-*/;

  /**
   * Whether or not to provide a fill color for nodes.
   * If possible, the fill color will be taken from the XholonClass color.
   */
  public native boolean isShouldColor() /*-{return this.efParams.shouldColor;}-*/;
  //public native void setShouldColor(boolean shouldColor) /*-{this.efParams.shouldColor = shouldColor;}-*/;

  /**
   * Default fill color if shouldColor == true.
   */
  public native String getDefaultColor() /*-{return this.efParams.defaultColor;}-*/;
  //public native void setDefaultColor(String defaultColor) /*-{this.efParams.defaultColor = defaultColor;}-*/;

  /**
   * Whether or not to specify a node shape in the Graphviz output.
   */
  public native boolean isShouldSpecifyShape() /*-{return this.efParams.shouldSpecifyShape;}-*/;
  //public native void setShouldSpecifyShape(boolean shouldSpecifyShape) /*-{this.efParams.shouldSpecifyShape = shouldSpecifyShape;}-*/;

  /**
   * Name of a Graphviz polygon-based shape.
   */
  public native String getShape() /*-{return this.efParams.shape;}-*/;
  //public native void setShape(String shape) /*-{this.efParams.shape = shape;}-*/;
  
  /**
   * Graphviz graph size.
   */
  public native boolean isShouldSpecifySize() /*-{return this.efParams.shouldSpecifySize;}-*/;
  public native String getSize() /*-{return this.efParams.size;}-*/;
  
  /**
   * Graphviz graph and node fontname.
   */
  public native boolean isShouldSpecifyFontname() /*-{return this.efParams.shouldSpecifyFontname;}-*/;
  public native String getFontname() /*-{return this.efParams.fontname;}-*/;
  
  /**
   * Graphviz edge arrowhead type.
   */
  public native boolean isShouldSpecifyArrowhead() /*-{return this.efParams.shouldSpecifyArrowhead;}-*/;
  public native String getArrowhead() /*-{return this.efParams.arrowhead;}-*/;
  
  /**
   * Whether or not to specify a stylesheet file name in the output.
   */
  public native boolean isShouldSpecifyStylesheet() /*-{return this.efParams.shouldSpecifyStylesheet;}-*/;
  //public native void setShouldSpecifyStylesheet(boolean shouldSpecifyStylesheet) /*-{this.efParams.shouldSpecifyStylesheet = shouldSpecifyStylesheet;}-*/;

  /**
   * Name of a stylesheet (css) file.
   */
  public native String getStylesheet() /*-{return this.efParams.stylesheet;}-*/;
  //public native void setStylesheet(String stylesheet) /*-{this.efParams.stylesheet = stylesheet;}-*/;
  
  /**
   * Whether or not to specify a rankdir.
   */
  public native boolean isShouldSpecifyRankdir() /*-{return this.efParams.shouldSpecifyRankdir;}-*/;
  //public native void setShouldSpecifyRankdir(boolean shouldSpecifyRankdir) /*-{this.efParams.shouldSpecifyRankdir = shouldSpecifyRankdir;}-*/;
  
  /** rankdir=LR|RL|BT */
  public native String getRankdir() /*-{return this.efParams.rankdir;}-*/;
  //public native void setRankdir(String rankdir) /*-{this.efParams.rankdir = rankdir;}-*/;
  
  /**
   * Should the generated Graphviz content be displayed on the HTML page, as SVG, using viz.js .
   */
  public native boolean isShouldDisplayGraph() /*-{return this.efParams.shouldDisplayGraph;}-*/;
  //public native void setShouldDisplayGraph(boolean shouldDisplayGraph) /*-{this.efParams.shouldDisplayGraph = shouldDisplayGraph;}-*/;
  
  /**
   * Get the Graphviz output format.
   */
  public native String getOutputFormat() /*-{return this.efParams.outputFormat;}-*/;
  //public native void setOutputFormat(String outputFormat) /*-{this.efParams.outputFormat = outputFormat;}-*/;
  
}

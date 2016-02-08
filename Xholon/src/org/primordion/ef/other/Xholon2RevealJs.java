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
import java.util.ArrayList;
import java.util.List;

import org.primordion.xholon.base.Annotation;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInformation;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.io.gwt.HtmlScriptHelper;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.XholonDirectoryService;
import org.primordion.xholon.service.ef.IXholon2GraphFormat;

/**
 * Export a Xholon model in RevealJs format.
 * RevealJs is "The HTML Presentation Framework".
 * It only supports one level of nesting.
 * TODO
 * - use links between slides to represent Xholon hierarchy (firstChild nextSibling parentNode)
 * - reprogram RevealJs arrow keys and arrow icons to navigate the Xholon hierarchy
 * - open new window with the generated HTML
 *
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on February 7, 2016)
 * @see <a href="https://github.com/hakimel/reveal.js">reveal.js site</a>
 */
@SuppressWarnings("serial")
public class Xholon2RevealJs extends AbstractXholon2ExternalFormat implements IXholon2GraphFormat {
  
  private String outFileName;
  private String outPath = "./ef/revealjs/";
  private String modelName;
  private IXholon root;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  /**
   * Start of the RevealJs archive.
   */
  protected StringBuilder startSb = null;
  
  /**
   * The accumulating text for all RevealJs passage nodes.
   */
  protected StringBuilder nodeSb = null;
  
  /**
   * End of the RevealJs archive.
   */
  protected StringBuilder endSb = null;
  
  protected StringBuilder sb = null;
  
  /** Annotation service. */
  protected IXholon annService = null;
  
  protected String indent1 = "  ";
  protected String indentInitial = "        ";
  
  /**
   * Constructor.
   */
  public Xholon2RevealJs() {}
  
	@Override
	public String getVal_String() {
	  return sb.toString();
	}
	
  @Override
  public boolean initialize(String outFileName, String modelName, IXholon root) {
    timeNow = new Date();
    timeStamp = timeNow.getTime();
    if (outFileName == null) {
      this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".xml";
    }
    else {
      this.outFileName = outFileName;
    }
    this.modelName = modelName;
    this.root = root;
    
    // 
    this.startSb = new StringBuilder()
    .append("<!-- \n")
    .append("Automatically generated by Xholon version 0.9.1, using org.primordion.ef.other.Xholon2RevealJs.java\n")
    .append(timeNow)
    .append("\nmodel: ").append(modelName).append("\n")
    .append("www.primordion.com/Xholon/gwt/\n")
    .append("To view this HTML file, run it with reveal.js, at https://github.com/hakimel/reveal.js\n")
    .append("-->\n")
    
    .append("<html>\n")
    .append("  <head>\n")
    .append("    <meta charset=\"utf-8\">\n")
    .append("    <link rel=\"stylesheet\" href=\"css/reveal.css\">\n")
    .append("    <link rel=\"stylesheet\" href=\"css/theme/").append(getTheme()).append(".css\">\n")
    .append("  </head>\n")
    .append("  <body>\n")
    .append("    <div class=\"reveal\">\n")
    .append("      <div class=\"slides\">\n");
    
    this.nodeSb = new StringBuilder();
    
    // 
    this.endSb = new StringBuilder()
    .append("      </div>\n")
    .append("    </div>\n")
    .append("    <script src=\"js/reveal.js\"></script>\n")
    .append("    <script>\n")
    .append("      Reveal.initialize();\n")
    .append("    </script>\n")
    .append("  </body>\n")
    .append("</html>\n");
    
    return true;
  }

  @Override
  public void writeAll() {
    writeNode(root);
    sb = new StringBuilder()
    .append(startSb.toString())
    .append(nodeSb.toString())
    .append(endSb.toString());
    
    writeToTarget(sb.toString(), outFileName, outPath, root);
    
  }

  @Override
  public void writeNode(IXholon xhNode) {
    // write root node as first slide, at same nested level as its immediate children
    boolean hasChildNodes = xhNode.hasChildNodes();
    nodeSb.append(indentInitial).append("<section id=\"").append(makeNodeId(xhNode)).append("\">\n");
    writeNodeDetails(xhNode, indentInitial + indent1);
    nodeSb.append(indentInitial).append("</section>\n");
    // xhNode children are at same level as root
    IXholon childNode = xhNode.getFirstChild();
    while (childNode != null) {
      writeNode(childNode, indentInitial);
      childNode = childNode.getNextSibling();
    }
  }
  
  /**
   * Write a node.
   */
  public void writeNode(IXholon xhNode, String indent) {
    boolean hasChildNodes = xhNode.hasChildNodes();
    nodeSb.append(indent).append("<section id=\"").append(makeNodeId(xhNode)).append("\">\n");
    // xhNode details
    if (hasChildNodes && (getShowHierarchy() == 1)) {
      // add an extra section level
      nodeSb.append(indent + indent1).append("<section id=\"").append(makeNodeId(xhNode)).append("\">\n");
      writeNodeDetails(xhNode, indent + indent1 + indent1);
      nodeSb.append(indent + indent1).append("</section>\n");
    }
    else {
      writeNodeDetails(xhNode, indent + indent1);
    }
    if (getShowHierarchy() == 2) {
      // Xholon way
      nodeSb.append(indent).append("</section>\n");
    }
    // xhNode children
    IXholon childNode = xhNode.getFirstChild();
    while (childNode != null) {
      writeNode(childNode, indent + indent1);
      childNode = childNode.getNextSibling();
    }
    if (getShowHierarchy() == 1) {
      // RevealJs way
      nodeSb.append(indent).append("</section>\n");
    }
  }
  
  /**
   * Write details for one node.
   * 
   */
  protected void writeNodeDetails(IXholon xhNode, String indent) {
    if (isShowNodeName()) {
      nodeSb.append(indent).append("<h2>").append(makeNodeName(xhNode)).append("</h2>\n");
    }
    String anno = findAnnotation(xhNode);
    if ((anno != null) && isShowAnnotations()) {
      // add Xholon annotations
      nodeSb.append(indent).append("<p>").append(anno).append("</p>\n");
    }
    if (isShowAttributes()) {
      writeNodeAttributes(xhNode, indent);
    }
    // xhNode outgoing links
    writeEdges(xhNode, indent);
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public void writeEdges(IXholon xhNode) {}
  
  public void writeEdges(IXholon xhNode, String indent) {
    // write Xholon ports
    List<PortInformation> portList = null;
    switch (getShowPorts()) {
    case 1:
      portList = new org.primordion.xholon.base.ReflectionJavaMicro().getAllPorts(xhNode, false);
      break;
    case 2:
      portList = xhNode.getAllPorts(); // used in Xholon2MindMap
      break;
    case 0:
    default:
      portList = new ArrayList<PortInformation>(); // zero-length list
      break;
    }
    for (int i = 0; i < portList.size(); i++) {
      PortInformation pi = (PortInformation)portList.get(i);
      IXholon reffedNode = pi.getReffedNode();
      //nodeSb
      //.append("\n");
      if (isScopeToRoot()) {
        // don't write port if it's not within scope of the root node
        if (!reffedNode.hasAncestor(root.getName())) {
          continue;
        }
      }
      nodeSb.append(indent).append("<p>Go to <a href=\"#").append(makeNodeName(reffedNode)).append("\">");
      if (isShowPortName()) {
        String fieldName = pi.getFieldName();
        nodeSb.append(fieldName);
        if ("port".equals(fieldName)) {
          nodeSb.append(pi.getFieldNameIndexStr());
        }
        nodeSb.append(" ");
      }
      nodeSb.append(makeNodeName(reffedNode)).append("</a></p>\n");
    }
    //if (portList.size() > 0) {
    //  nodeSb.append("\n");
    //}
    
    // optionally write parentNode  up
    if (isShowParentLink() && (xhNode != root)) {
      IXholon parent = xhNode.getParentNode();
      if (parent != null) {
        nodeSb.append(indent).append("<p>Go to parent <a href=\"#").append(makeNodeName(parent)).append("\">")
        .append(makeNodeName(parent)).append("</a></p>\n");
      }
    }
    
    // optionally write previousSibling  left
    if (isShowPrevSiblingLink() && (xhNode != root)) {
      IXholon prev = xhNode.getPreviousSibling();
      if (prev != null) {
        nodeSb.append(indent).append("<p>Go to previous sibling <a href=\"#").append(makeNodeName(prev)).append("\">")
        .append(makeNodeName(prev)).append("</a></p>\n");
      }
    }
    
    // optionally write nextSibling  right
    if (isShowNextSiblingLink() && (xhNode != root)) {
      IXholon next = xhNode.getNextSibling();
      if (next != null) {
        nodeSb.append(indent).append("<p>Go to next sibling <a href=\"#").append(makeNodeName(next)).append("\">")
        .append(makeNodeName(next)).append("</a></p>\n");
      }
    }
    
    // optionally write firstChild  down
    if (isShowFirstChildLink()) {
      IXholon first = xhNode.getFirstChild();
      if (first != null) {
        nodeSb.append(indent).append("<p>Go to first child <a href=\"#").append(makeNodeName(first)).append("\">")
        .append(makeNodeName(first)).append("</a></p>\n");
      }
    }
    
  }

  @Override
  public void writeNodeAttributes(IXholon xhNode) {}
  
  public void writeNodeAttributes(IXholon xhNode, String indent) {
    double val = xhNode.getVal();
    if ((val != 0.0) && (val != Double.MIN_VALUE) && (val != Double.MAX_VALUE)) {
      nodeSb.append(indent).append("<p>val ").append(val).append("</p>\n");
    }
    // TODO
  }
  
  /**
   * Make a standard name for a node.
   * @param xhNode 
   * @return a standard name
   */
  protected String makeNodeName(IXholon xhNode) {
    String nodeName = xhNode.getName(getNameTemplate());
    return nodeName;
  }
  
  /**
   * Make a standard ID for a node, for inclusion in <section> and for use in links.
   * @param xhNode 
   * @return a standard ID
   */
  protected String makeNodeId(IXholon xhNode) {
    String nodeId = xhNode.getName(getNameTemplate());
    return nodeId;
  }
  
  /**
   * Try to find a Xholon annotation for a node.
   * @param xhNode 
   * @return an annotation, or null
   */
  protected String findAnnotation(IXholon xhNode) {
    if (annService == null) {
      annService = xhNode.getService(IXholonService.XHSRV_XHOLON_DIRECTORY);
    }
    if (annService != null) {
      IXholon ann = (IXholon)((XholonDirectoryService)annService)
        .get(Annotation.makeUniqueKey(xhNode));
      if (ann != null) {
        return ann.getVal_String();
      }
    }
    return null;
  }
  
  /**
   * Make a JavaScript object with all the parameters for this external format.
   */
  protected native void makeEfParams() /*-{
    var p = {};
    p.revealjsVersion = "3.2.0";
    p.theme = "whiteKSW";
    p.options = "";
    p.showHierarchy = 2; // 1=RevealJs-way 2=Xholon-way
    p.showNodeName = true;
    p.showAnnotations = true;
    p.showAttributes = false;
    p.showPorts = 2; // 0=don't show  1=getPorts1  2=getPorts2
    p.showPortName = true;
    p.showParentLink = true;
    p.showPrevSiblingLink = true;
    p.showNextSiblingLink = true;
    p.showFirstChildLink = true;
    p.nameTemplate = "R^^_i^"; // "R^^^^^"
    p.scopeToRoot = true;
    this.efParams = p;
  }-*/;

  public native String getRevealjsVersion() /*-{return this.efParams.revealjsVersion;}-*/;
  //public native void setRevealjsVersion(String revealjsVersion) /*-{this.efParams.revealjsVersion = revealjsVersion;}-*/;

  public native String getTheme() /*-{return this.efParams.theme;}-*/;
  //public native void setTheme(String theme) /*-{this.efParams.theme = theme;}-*/;

  public native String getOptions() /*-{return this.efParams.options;}-*/;
  //public native void setOptions(String options) /*-{this.efParams.options = options;}-*/;

  public native int getShowHierarchy() /*-{return this.efParams.showHierarchy;}-*/;
  //public native void setShowHierarchy(int showHierarchy) /*-{this.efParams.showHierarchy = showHierarchy;}-*/;
  
  public native boolean isShowNodeName() /*-{return this.efParams.showNodeName;}-*/;
  //public native void setShowNodeName(boolean showNodeName) /*-{this.efParams.showNodeName = showNodeName;}-*/;
  
  public native boolean isShowAnnotations() /*-{return this.efParams.showAnnotations;}-*/;
  //public native void setShowAnnotations(boolean showAnnotations) /*-{this.efParams.showAnnotations = showAnnotations;}-*/;
  
  public native boolean isShowAttributes() /*-{return this.efParams.showAttributes;}-*/;
  //public native void setShowAttributes(boolean showAttributes) /*-{this.efParams.showAttributes = showAttributes;}-*/;
  
  public native int getShowPorts() /*-{return this.efParams.showPorts;}-*/;
  //public native void setShowPorts(int showPorts) /*-{this.efParams.showPorts = showPorts;}-*/;
  
  /**
   * Whether or not a link should show the name of the port.
   */
  public native boolean isShowPortName() /*-{return this.efParams.showPortName;}-*/;
  //public native void setShowPortName(boolean showPortName) /*-{this.efParams.showPortName = showPortName;}-*/;
  
  public native boolean isShowFirstChildLink() /*-{return this.efParams.showFirstChildLink;}-*/;
  //public native void setShowFirstChildLink(boolean showFirstChildLink) /*-{this.efParams.showFirstChildLink = showFirstChildLink;}-*/;
  
  public native boolean isShowNextSiblingLink() /*-{return this.efParams.showNextSiblingLink;}-*/;
  //public native void setShowNextSiblingLink(boolean showNextSiblingLink) /*-{this.efParams.showNextSiblingLink = showNextSiblingLink;}-*/;
  
  public native boolean isShowPrevSiblingLink() /*-{return this.efParams.showPrevSiblingLink;}-*/;
  //public native void setShowPrevSiblingLink(boolean showPrevSiblingLink) /*-{this.efParams.showPrevSiblingLink = showPrevSiblingLink;}-*/;
  
  public native boolean isShowParentLink() /*-{return this.efParams.showParentLink;}-*/;
  //public native void setShowParentLink(boolean showParentLink) /*-{this.efParams.showParentLink = showParentLink;}-*/;
  
  public native String getNameTemplate() /*-{return this.efParams.nameTemplate;}-*/;
  //public native void setNameTemplate(String nameTemplate) /*-{this.efParams.nameTemplate = nameTemplate;}-*/;
  
  public native boolean isScopeToRoot() /*-{return this.efParams.scopeToRoot;}-*/;
  //public native void setScopeToRoot(boolean scopeToRoot) /*-{this.efParams.scopeToRoot = scopeToRoot;}-*/;

}

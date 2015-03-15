/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2015 Ken Webb
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
 * Export a Xholon model in Springy.js format.
 * Springy is "a force directed graph layout algorithm in JavaScript".
 * A separate renderer is required, such as springyui.js.
 * The renderer handles node and edge data, such as color: label: etc.
 *
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on March 15, 2015)
 * @see <a href="http://getspringy.com">Springy.js site</a>
 */
@SuppressWarnings("serial")
public class Xholon2Springy extends AbstractXholon2ExternalFormat implements IXholon2GraphFormat {
  
  /** StringBuilder initial capacity. */
  protected static final int SB_INITIAL_CAPACITY = 1000; // default is 16
  
  private String outFileName;
  private String outPath = "./ef/springy/";
  private String modelName;
  private IXholon root;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  /**
   * Start
   */
  protected StringBuilder startSb = null;
  
  /**
   * The accumulating text for all nodes.
   */
  protected StringBuilder nodeSb = null;
  
  /**
   * The accumulating text for all links.
   */
  protected StringBuilder linkSb = null;
  
  /**
   * End
   */
  //protected StringBuilder endSb = null;
  
  /** Annotation service. */
  protected IXholon annService = null;
  
  /**
   * Constructor.
   */
  public Xholon2Springy() {}
  
  @Override
  public boolean initialize(String outFileName, String modelName, IXholon root) {
    require();
    
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
    
    // var graph = new Springy.Graph();
    this.startSb = new StringBuilder(SB_INITIAL_CAPACITY)
    .append("/**\n")
    .append(" * Automatically generated by Xholon version 0.9.1, using org.primordion.ef.Xholon2Springy.java\n")
    .append(" * ").append(timeNow).append("\n")
    .append(" * model: ").append(modelName).append("\n")
    .append(" * www.primordion.com/Xholon/gwt/\n")
    .append(" * Use this file with Springy.js, at http://getspringy.com\n")
    .append(" */\n\n")
    .append("var graph = new Springy.Graph();\n")
    ;
    
    this.nodeSb = new StringBuilder(SB_INITIAL_CAPACITY);
    this.linkSb = new StringBuilder(SB_INITIAL_CAPACITY);
    
    // not needed with Springy ?
    //this.endSb = new StringBuilder(128)
    //.append("</").append(TW_STORYDATA).append(">")
    //.append("\n");
    
    return true;
  }

  @Override
  public void writeAll() {
    writeNode(root);
    StringBuilder sb = new StringBuilder()
    .append(startSb.toString())
    .append("\n")
    .append(nodeSb.toString())
    .append("\n")
    .append(linkSb.toString())
    //.append(endSb.toString())
    ;
    writeToTarget(sb.toString(), outFileName, outPath, root);
  }

  @Override
  public void writeNode(IXholon xhNode) {
    // xhNode details
    writeNodeDetails(xhNode);
    // xhNode children
    IXholon childNode = xhNode.getFirstChild();
    while (childNode != null) {
      writeNode(childNode);
      childNode = childNode.getNextSibling();
    }
  }
  
  /**
   * Write details for one node.
   * var spruce = graph.newNode({label: 'Norway Spruce'});
   * var fir = graph.newNode({label: 'Sicilian Fir'});
   * var cards = graph.newNode({image: {src: 'http://upload.wikimedia.org/wikipedia/commons/1/1a/5_Zener_cards_%28icon%29.jpg'}});
   * @param xhNode 
   */
  protected void writeNodeDetails(IXholon xhNode) {
    nodeSb.append("var ")
    .append(makeNodeName(xhNode))
    .append(" = graph.newNode({label: '").append(makeNodeLabel(xhNode))
    .append("'});\n")
    ;
    /*if (isShowNodeName()) {
      nodeSb.append("\n").append(makeNodeName(xhNode));
    }
    String anno = findAnnotation(xhNode);
    if ((anno != null) && isShowAnnotations()) {
      // add Xholon annotations
      nodeSb.append("\n").append(anno);
    }
    if (isShowAttributes()) {
      writeNodeAttributes(xhNode);
    }*/
    // xhNode outgoing links
    writeEdges(xhNode);
    ;
  }
  
  @SuppressWarnings("unchecked")
  @Override
  // graph.newEdge(spruce, fir);
  public void writeEdges(IXholon xhNode) {
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
    String nodeName = makeNodeName(xhNode);
    for (int i = 0; i < portList.size(); i++) {
      PortInformation pi = (PortInformation)portList.get(i);
      IXholon reffedNode = pi.getReffedNode();
      ;
      if (isScopeToRoot()) {
        // don't write port if it's not within scope of the root node
        if (!reffedNode.hasAncestor(root.getName())) {
          continue;
        }
      }
      String label = null;
      if (isShowPortName()) {
        String fieldName = pi.getFieldName();
        label = fieldName;
        if ("port".equals(fieldName)) {
          label += pi.getFieldNameIndexStr();
        }
      }
      writeEdge(nodeName, makeNodeName(reffedNode), label);
    }
    
    // optionally write parentNode  up
    if (isShowParentLink() && (xhNode != root)) {
      IXholon parent = xhNode.getParentNode();
      if (parent != null) {
        // graph.newEdge(spruce, fir);
        String label = isShowPortName() ? "parent" : null;
        writeEdge(nodeName, makeNodeName(parent), label);
      }
    }
    
    // optionally write previousSibling  left
    if (isShowPrevSiblingLink() && (xhNode != root)) {
      IXholon prev = xhNode.getPreviousSibling();
      if (prev != null) {
        String label = isShowPortName() ? "prev" : null;
        writeEdge(nodeName, makeNodeName(prev), label);
      }
    }
    
    // optionally write nextSibling  right
    if (isShowNextSiblingLink() && (xhNode != root)) {
      IXholon next = xhNode.getNextSibling();
      if (next != null) {
        String label = isShowPortName() ? "next" : null;
        writeEdge(nodeName, makeNodeName(next), label);
      }
    }
    
    // optionally write firstChild  down
    if (isShowFirstChildLink()) {
      IXholon first = xhNode.getFirstChild();
      if (first != null) {
        String label = isShowPortName() ? "first" : null;
        writeEdge(nodeName, makeNodeName(first), label);
      }
    }
    
  }
  
  /**
   * Write one edge in Springy format.
   * TODO handle optional color, and other options?
   * graph.newEdge(dennis, michael, {color: '#00A0B0'});
   *   {color: '#00A0B0', label: 'Foo bar'}
   */
  protected void writeEdge(String sourceNodeName, String targetNodeName, String label) {
    linkSb.append("graph.newEdge(")
    .append(sourceNodeName)
    .append(", ")
    .append(targetNodeName)
    .append(", {");
    if (label != null) {
      linkSb.append("label: '")
      .append(label)
      .append("'");
    }
    linkSb.append("});\n");
  }

  @Override
  public void writeNodeAttributes(IXholon xhNode) {
    double val = xhNode.getVal();
    if ((val != 0.0) && (val != Double.MIN_VALUE) && (val != Double.MAX_VALUE)) {
      nodeSb.append("\nval ").append(val);
    }
    // TODO
  }
  
  /**
   * Make a standard name for a node.
   * @param xhNode 
   * @return a standard name, that can be used as a JavaScript var
   */
  protected String makeNodeName(IXholon xhNode) {
    String nodeName = "node" + xhNode.getId();
    return nodeName;
  }
  
  /**
   * Make a standard label for a node.
   * @param xhNode 
   * @return a standard label
   */
  protected String makeNodeLabel(IXholon xhNode) {
    String label = xhNode.getName(getNameTemplate());
		if (this.isShouldSplitCamelCase()) {
		  label = this.splitCamelCase(label, " ");
			if (isShouldBeLowerCase()) {
			  label = label.toLowerCase();
			}
			else if (isShouldBeCapitalized()) {
			  label = label.charAt(0) + label.substring(1).toLowerCase();
			}
		}
    return label;
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
	 * Convert camel case to human readable.
	 * @param s A string that may contain camel case (ex: HelloWorld).
	 * @param r The replacement character (ex: "_").  NOT USED
	 * @return A string with human-readable spaces (ex: Hello World).
	 */
	protected native String splitCamelCase(String s, String r) /*-{
	  return s.charAt(0).toUpperCase() + s.substr(1).replace(/[A-Z]/g, ' $&');
	}-*/;
  
  protected native void require() /*-{
    $wnd.xh.require("springy");
    //$wnd.xh.require("springyui");
  }-*/;
  
  /**
   * Make a JavaScript object with all the parameters for this external format.
   */
  protected native void makeEfParams() /*-{
    var p = {};
    //p.showAnnotations = true;
    //p.showAttributes = false;
    p.showPorts = 2; // 0=don't show  1=getPorts1  2=getPorts2
    p.showPortName = true;
    p.showParentLink = true;
    p.showPrevSiblingLink = false;
    p.showNextSiblingLink = true;
    p.showFirstChildLink = true;
    p.nameTemplate = "R^^_i^"; // "R^^^^^"
    p.underlineReplacement = "‗"; // "--" or "-" or " " or other (escapes don't work)
    p.scopeToRoot = true;
    p.shouldSplitCamelCase = true;
    p.shouldBeLowerCase = false;
    p.shouldBeCapitalized = true;
    this.efParams = p;
  }-*/;

  //public native boolean isShowAnnotations() /*-{return this.efParams.showAnnotations;}-*/;
  //public native void setShowAnnotations(boolean showAnnotations) /*-{this.efParams.showAnnotations = showAnnotations;}-*/;
  
  //public native boolean isShowAttributes() /*-{return this.efParams.showAttributes;}-*/;
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
  
  public native String getUnderlineReplacement() /*-{return this.efParams.underlineReplacement;}-*/;
  //public native void setUnderlineReplacement(String underlineReplacement) /*-{this.efParams.underlineReplacement = underlineReplacement;}-*/;

  public native boolean isScopeToRoot() /*-{return this.efParams.scopeToRoot;}-*/;
  //public native void setScopeToRoot(boolean scopeToRoot) /*-{this.efParams.scopeToRoot = scopeToRoot;}-*/;

  /** Whether or not to split a camel case Xholon class name into multiple words. */
  public native boolean isShouldSplitCamelCase() /*-{return this.efParams.shouldSplitCamelCase;}-*/;
  //public native void setShouldSplitCamelCase(boolean shouldSplitCamelCase) /*-{this.efParams.shouldSplitCamelCase = shouldSplitCamelCase;}-*/;

  /** Whether or not split camel case should be all lower case. */
  public native boolean isShouldBeLowerCase() /*-{return this.efParams.shouldBeLowerCase;}-*/;
  //public native void setShouldBeLowerCase(boolean shouldBeLowerCase) /*-{this.efParams.shouldBeLowerCase = shouldBeLowerCase;}-*/;

  /** Whether or not split camel case should have first word capitalized and rest lower case. */
  public native boolean isShouldBeCapitalized() /*-{return this.efParams.shouldBeCapitalized;}-*/;
  //public native void setShouldBeCapitalized(boolean shouldBeCapitalized) /*-{this.efParams.shouldBeCapitalized = shouldBeCapitalized;}-*/;

}

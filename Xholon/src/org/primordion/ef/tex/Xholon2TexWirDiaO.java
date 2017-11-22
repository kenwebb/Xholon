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

package org.primordion.ef.tex;

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
 * Xholon to TeX - Wiring Diagrams - Oriented.
 * Export a Xholon model to TeX TikZ/PGF format.
 * The exported content requires the "oriented WD" styles developed by David Spivak.
 * This class semi-automates the oriented Operad wiring diagrams that David Spivak currently hand codes.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on November 21, 2017)
 * @see <a href="https://en.wikipedia.org/wiki/PGF/TikZ">Wikipedia page</a>
 * @see <a href="http://math.mit.edu/~dspivak/">David Spivak home page</a>
 * @see <a href=""></a>
 */
@SuppressWarnings("serial")
public class Xholon2TexWirDiaO extends AbstractXholon2ExternalFormat implements IXholon2GraphFormat {
  
  private String outFileName;
  private String outPath = "./ef/twdo/";
  private String modelName;
  private IXholon root;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  /**
   * Start of the TeX script.
   */
  protected StringBuilder startSb = null;
  
  /**
   * The accumulating text for all TeX nodes.
   */
  protected StringBuilder nodeSb = null;
  
  /**
   * The accumulating text for all TeX links (\draw statements).
   */
  protected StringBuilder linkSb = null;
  
  /**
   * Options. TODO Is this needed?
   */
  //protected StringBuilder optionsSb = null;
  
  /**
   * End of the TeX script.
   */
  protected StringBuilder endSb = null;
  
  protected StringBuilder sb = null;
  
  /**
   * Each node superClass may have its own shape.
   * The map is constructed by calling getNodesStyle() for an optional list of user-specified shapes.
   */
  protected Map<String, String> shapeMap = null;
  
  /**
   * Constructor.
   */
  public Xholon2TexWirDiaO() {}
  
  @Override
  public String getVal_String() {
    return sb.toString();
  }
  
  @Override
  public boolean initialize(String outFileName, String modelName, IXholon root) {
    timeNow = new Date();
    timeStamp = timeNow.getTime();
    if (outFileName == null) {
      this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".tex";
    }
    else {
      this.outFileName = outFileName;
    }
    this.modelName = modelName;
    this.root = root;
    this.makeShapeMap();
    
    this.startSb = new StringBuilder()
    .append("\\begin{equation}\\label{")
    .append(modelName.replace(" ","_"))
    .append("}\\tag{").append(modelName)
    .append("}\n")
    .append("\\begin{tikzpicture}[oriented WD, bbx=1em, bby=1ex]\n");
    
    this.nodeSb = new StringBuilder()
    .append("% nodes\n");
    
    this.linkSb = new StringBuilder()
    .append("% links\n");
    
    this.endSb = new StringBuilder()
    .append("\\end{tikzpicture}\n")
    .append("\\end{equation}\n");
        
    return true;
  }

  @Override
  public void writeAll() {
    this.collectInPorts(root);
    writeNode(root);
    sb = new StringBuilder()
    .append("% ")
    .append(modelName)
    //.append("\n// see http://almende.github.io/chap-links-library/network.html")
    .append("\n\n")
    .append(startSb.toString())
    .append(nodeSb.toString())
    .append(linkSb.toString())
    //.append(optionsSb.toString())
    .append(endSb.toString());
    writeToTarget(sb.toString(), outFileName, outPath, root);
    this.removeInPorts(root);
  }

  @Override
  public void writeNode(IXholon xhNode) {
    // do xhNode children first
    // collect a String of child names for use by "fit"
    String fitNames = "";
    IXholon childNode = xhNode.getFirstChild();
    while (childNode != null) {
      writeNode(childNode);
      fitNames += " (" + childNode.getName(getBbNameTemplate()) + ")";
      childNode = childNode.getNextSibling();
    }
    
    String nodeName = xhNode.getName(getBbNameTemplate());
    if ((getMaxChars() != -1) && (nodeName.length() > getMaxChars())) {
      nodeName = nodeName.substring(0, getMaxChars());
    }
    int inportCount = this.getInportsCount(xhNode);
    List<PortInformation> portList = xhNode.getLinks(false, true);
    int outportCount = portList.size();
    if (xhNode == this.root) {
      // at least some of the ports on the root and other container nodes, are relay nodes that are "in" rather than "out" ports
      int tempCount = inportCount;
      inportCount = outportCount;
      outportCount = tempCount;
    }
    String position = "";
    String fit = "";
    
    IXholon prev = xhNode.getPreviousSibling();
    if (prev != null) {
      // xhNode is not the firstSibling
      if (prev.getXhc() == xhNode.getXhc()) {
        // xhNode has the same Xholon class as its previous sibling
        position = ", " + getSiblingsPosition() + " " + prev.getName(getBbNameTemplate());
      }
      else {
        IXholon firstSiblingWithXhClass = xhNode.getParentNode().findFirstChildWithXhClass(prev.getXhcName());
        if (firstSiblingWithXhClass != null) {
          position = ", " + getDiffXhtypePosition() + " " + firstSiblingWithXhClass.getName(getBbNameTemplate());
        }
        else {
          position = ", " + getDiffXhtypePosition() + " " + prev.getName(getBbNameTemplate());
        }
      }
    }
    else if (xhNode == this.root) {
      fit = ", fit={";
      fit += fitNames.trim();
      fit += "}";
      // ", bb name = $NAME$"
      fit += ", bb name = $" + xhNode.getName(getBbNameTemplate()) + "$";
    }
    else {
      // TODO this is a first sibling that probably should not hava a position
      //position = ", " + getDiffXhtypePosition() + " " + xhNode.getParentNode().getName(getBbNameTemplate());
    }
    
    nodeSb
    .append("  \\node[bb={").append(inportCount).append("}")
    .append("{").append(outportCount).append("}")
    //.append(", bb name=$TODO$") // should I do this ?
    .append(position)
    .append(fit)
    .append("]")
    .append(" (").append(nodeName).append(")")
    .append(" {");
    if (xhNode != this.root) {
      nodeSb
      .append("$")
      .append(xhNode.getName(getBbNameTemplate()));
      nodeSb.append("$");
    }
    nodeSb.append("}");
    ;
    String shape = this.makeShape(xhNode);
    if (shape != null) {
      // TODO
    }
    nodeSb
    .append(";")
    .append(" % ")
    .append(xhNode.getName(getNameTemplate()))
    .append("\n");
    // xhNode outgoing edges
    writeEdges(xhNode);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void writeEdges(IXholon xhNode) {
    List<PortInformation> portList = xhNode.getLinks(false, true);
    String outportName = "_out";
    String outportNameSuffix = "";
    if (xhNode == this.root) {
      outportName = "_in";
      outportNameSuffix = "'";
    }
    for (int i = 0; i < portList.size(); i++) {
      PortInformation pi = (PortInformation)portList.get(i);
      linkSb
      .append("  \\draw")
      .append("[ar]") // optional ?
      .append(" (")
      .append(xhNode.getName(getBbNameTemplate()))
      .append(outportName)
      .append(i+1) // out string is 1-based rather than 0-based
      .append(outportNameSuffix)
      .append(")")
      .append(" to")
      .append(" (")
      .append(pi.getReffedNode().getName(getBbNameTemplate()))
      .append("_in")
      .append(this.getInportsIxAndDec(pi.getReffedNode()))
      .append(")")
      .append(";");
      if (isShowPortName()) {
        linkSb
        .append(" % ")
        .append(pi.getLocalNameNoBrackets());
      }
      linkSb
      .append("\n");
    }
  }

  @Override
  public void writeNodeAttributes(IXholon xhNode) {
    // nothing to do for now
  }
  
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
  
  /**
   * Collect the in ports (conjugated ports) for all nodes in the Xholon tree or in a subtree.
   */
  protected void collectInPorts(IXholon node) {
    List<PortInformation> portList = node.getLinks(false, true);
    for (int i = 0; i < portList.size(); i++) {
      PortInformation pi = (PortInformation)portList.get(i);
      if (pi != null) {
        IXholon reffedNode = pi.getReffedNode();
        String fieldName = pi.getFieldName();
        // TODO optional index
        if ((reffedNode != null) && (fieldName != null)) {
          // create a structure in reffedNode to store the in port
          // reffedNode.conjports = {};
          storeInPort(reffedNode, fieldName, node);
        }
      }
    }
    // recurse
    IXholon childNode = node.getFirstChild();
    while (childNode != null) {
      collectInPorts(childNode);
      childNode = childNode.getNextSibling();
    }
  }
  
  protected void removeInPorts(IXholon node) {
    this.removeInPort(node);
    // recurse
    IXholon childNode = node.getFirstChild();
    while (childNode != null) {
      removeInPorts(childNode);
      childNode = childNode.getNextSibling();
    }
  }
  
  protected native void storeInPort(IXholon reffedNode, String fieldName, IXholon thisNode) /*-{
    if (!reffedNode["inports"]) {
      reffedNode["inports"] = 0;
      reffedNode["inportsIx"] = 0;
    }
    reffedNode["inports"]++;
    reffedNode["inportsIx"]++;
  }-*/;
  
  protected native void removeInPort(IXholon node) /*-{
    if (typeof node["inports"] !== "undefined") {
      delete(node["inports"]);
    }
    if (typeof node["inportsIx"] !== "undefined") {
      delete(node["inportsIx"]);
    }
  }-*/;
  
  protected native int getInportsCount(IXholon node) /*-{
    if (node["inports"]) {
      return node["inports"];
    }
    else {
      return 0;
    }
  }-*/;
  
  protected native int getInportsIxAndDec(IXholon node) /*-{
    var inportsCount = 0;
    if (node["inportsIx"]) {
      inportsCount = node["inportsIx"];
      node["inportsIx"]--;
    }
    return inportsCount;
  }-*/;
  
  /** Number of tree levels to show, if showTree == true */
  public native int getMaxTreeLevels() /*-{return this.efParams.maxTreeLevels;}-*/;
  public native void setMaxTreeLevels(int maxTreeLevels) /*-{this.efParams.maxTreeLevels = maxTreeLevels;}-*/;

  /** Node name template */
  public native String getNameTemplate() /*-{return this.efParams.nameTemplate;}-*/;
  public native void setNameTemplate(String nameTemplate) /*-{this.efParams.nameTemplate = nameTemplate;}-*/;
  
  /** bb name template */
  public native String getBbNameTemplate() /*-{return this.efParams.bbNameTemplate;}-*/;
  public native void setBbNameTemplate(String nameTemplate) /*-{this.efParams.bbNameTemplate = bbNameTemplate;}-*/;
  
  /** Number of characters to show in the node name */
  public native int getMaxChars() /*-{return this.efParams.maxChars;}-*/;
  public native void setMaxChars(int maxChars) /*-{this.efParams.maxChars = maxChars;}-*/;

  /**
   * Make a JavaScript object with all the parameters for this external format.
   */
  protected native void makeEfParams() /*-{
    var p = {};
    p.maxTreeLevels = 1;
    p.width = "600px";
    p.height = "600px";
    p.nameTemplate = "r:c_i"; // "r:c_i^" "R^^^^^"
    p.bbNameTemplate = "R^^^^^";
    p.maxChars = -1; // -1 1 3
    p.linksLength = 50;
    p.showPortName = false;
    p.nodesStyle = "rectangle"; // "rectangle"
    p.linksStyle = "arrow-end"; // "arrow-end" "dash-line" "line"(their default) "arrow" "moving-arrows" OR undefined
    p.siblingsPosition = "right=of";
    p.diffXhtypePosition = "below=of";
    p.bipartite = false; // ex: Packs and Cables
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

  public native String getSiblingsPosition() /*-{return this.efParams.siblingsPosition;}-*/;
  //public native void setSiblingsPosition(String siblingsPosition) /*-{this.efParams.siblingsPosition = siblingsPosition;}-*/;

  public native String getDiffXhtypePosition() /*-{return this.efParams.diffXhtypePosition;}-*/;
  //public native void setDiffXhtypePosition(String diffXhtypePosition) /*-{this.efParams.diffXhtypePosition = diffXhtypePosition;}-*/;

  public native String getBipartite() /*-{return this.efParams.bipartite;}-*/;
  //public native void setBipartite(String bipartite) /*-{this.efParams.bipartite = bipartite;}-*/;

}

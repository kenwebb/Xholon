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

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;

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
 * 
 * latex options that work
\documentclass[10pt,oneside,article,landscape]{memoir}
 * 
 * TODO
 * - limited to proper output for one intermediate level
 * - try a different approach
 *  - create all the tikz nodes, and only then add the node attributes including edges
 *  - I get error messages if a referenced tikz node does not yet exist
 * - option to show ports as small quares or circles or other shape
 * - try using "above=of" of firstChild - it sort-of works
 * - optionally remove spaces from names
 * - have some makeNames() methods rather than calling node.getName(template) directly
 * - new option in Xholon.getName(template) "M_____" ?
 *  - gen a name that would display as a subscripted or superscripted name in TeX/tikz
 *  - ex: Abc:pack_23  ->  Ab_23
 * 
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
    
    this.startSb = new StringBuilder();
    if (this.isIncludeBeginEndEqu()) {
      startSb
      .append("\\begin{equation}\\label{")
      .append(modelName.replace(" ","_"))
      .append("}\\tag{").append(modelName.replace(" ","").replace("_","")) // latex/pdflatex fail if tag ends with "_"
      .append("}\n");
    }
    startSb.append("\\begin{tikzpicture}[oriented WD, bbx=1em, bby=1ex, scale=").append(this.getScale()).append("]\n");
    
    this.nodeSb = new StringBuilder()
    .append("% nodes\n");
    
    this.linkSb = new StringBuilder()
    .append("% links\n");
    
    this.endSb = new StringBuilder()
    .append("\\end{tikzpicture}\n");
    if (this.isIncludeBeginEndEqu()) {
      endSb.append("\\end{equation}\n");
    }
        
    return true;
  }

  @Override
  public void writeAll() {
    String fn = root.getXhcName() + "_" + root.getId() + "_" + timeStamp; // + ".tex";
    this.collectInPorts(root);
    writeNode(root);
    sb = new StringBuilder();
    /*if (this.isIncludePreamble()) {
      sb.append("\\documentclass").append(this.getDocumentclass()).append("\n\n");
      // TODO getPreambleFileName()
      sb.append("% TODO  PREAMBLE GOES HERE ").append(this.getPreambleFileName()).append("\n\n");
    }*/
    if (this.isIncludeBeginEndDoc()) {
      sb.append("\\begin{document}\n");
      sb.append("\\author{");
      if ("default".equals(this.getAuthor())) {
        sb.append("AUTHOR"); // TODO
      }
      else {
        sb.append(this.getAuthor());
      }
      sb.append("}\n");
      sb.append("\\title{");
      if ("default".equals(this.getTitle())) {
        sb.append(modelName);
      }
      else {
        sb.append(this.getTitle());
      }
      sb.append("}\n");
    }
    sb
    .append("% ")
    .append(modelName)
    .append("\n")
    .append("% Automatically generated by Xholon version 0.9.1, using ")
    .append(this.getClass().getName())
    .append(".java\n")
    .append("% ")
    .append(new Date())
    .append(" ")
    .append(timeStamp)
    .append("\n")
    .append("% To convert to a SVG or PDF image, save to a text (.tex) file that includes the Spivak latex code, and run:\n")
    .append("% latex ").append(fn).append("\n") // or directly to PDF using pdflatex
    .append("% dvisvgm ").append(fn).append("\n")
    .append("% vprerex ").append(fn).append("\n")
    .append("% On linux (Ubuntu), select and copy this text to the system clipboard, and enter the following in a terminal window to create a PDF file:\n")
    .append("% xclip -o > ").append(fn).append(".tex").append("; pdflatex ").append(fn).append("\n") // "evince fn.pdf" to view it in document viewer
    .append(startSb.toString())
    .append(nodeSb.toString())
    .append(linkSb.toString())
    .append(endSb.toString());
    if (this.isIncludeBeginEndDoc()) {
      sb.append("\\end{document}\n");
    }
    if (this.isIncludePreamble()) {
      StringBuilder doccSb = new StringBuilder().append("\\documentclass").append(this.getDocumentclass()).append("\n\n");
      downloadPreambleAndWriteToTarget(this.getPreambleFileName(), doccSb.toString(), sb.toString(), outFileName, outPath, root);
    }
    else {
      writeToTarget(sb.toString(), outFileName, outPath, root);
    }
    this.removeInPorts(root);
  }

  @Override
  public void writeNode(IXholon node) {
    // do node children first
    // collect a String of child names for use by "fit"
    String fitNames = "";
    IXholon childNode = node.getFirstChild();
    while (childNode != null) {
      writeNode(childNode);
      fitNames += " (" + childNode.getName(getBbNameTemplate()) + ")";
      childNode = childNode.getNextSibling();
    }
    
    String nodeName = node.getName(getBbNameTemplate());
    if ((getMaxChars() != -1) && (nodeName.length() > getMaxChars())) {
      nodeName = nodeName.substring(0, getMaxChars());
    }
    int inportCount = this.getInportsCount(node);
    List<PortInformation> portList = node.getLinks(false, true);
    int outportCount = portList.size();
    if (node == this.root) {
      // at least some of the ports on the root and other container nodes, are relay nodes that are "in" rather than "out" ports
      int tempCount = inportCount;
      inportCount = outportCount;
      outportCount = tempCount;
    }
    else if (node.hasChildNodes()) {
      int tempCount = inportCount;
      inportCount = outportCount;
      outportCount = tempCount;
    }
    String position = "";
    String fit = "";
    
    IXholon prev = node.getPreviousSibling();
    if (prev != null) {
      // node is not the firstSibling
      if (prev.getXhc() == node.getXhc()) {
        // node has the same Xholon class as its previous sibling
        position = ", " + getSiblingsPosition() + " " + prev.getName(getBbNameTemplate());
      }
      else {
        IXholon firstSiblingWithXhClass = node.getParentNode().findFirstChildWithXhClass(prev.getXhcName());
        if (firstSiblingWithXhClass != null) {
          position = ", " + getDiffXhtypePosition() + " " + firstSiblingWithXhClass.getName(getBbNameTemplate());
        }
        else {
          position = ", " + getDiffXhtypePosition() + " " + prev.getName(getBbNameTemplate());
        }
      }
    }
    if (node == this.root) {
      fit = ", fit={";
      fit += fitNames.trim();
      fit += "}";
      // ", bb name = $NAME$"
      fit += ", bb name = $" + node.getName(getBbNameTemplate()) + "$";
    }
    else if (node.hasChildNodes()) {
      fit = ", fit={";
      fit += fitNames.trim();
      fit += "}";
      String intermedDashing = this.getIntermedDashing();
      if ((intermedDashing != "") && (intermedDashing != "none")) {
        fit += ", " + intermedDashing;
      }
      fit += ", bb name = $" + node.getName(getBbNameTemplate()) + "$";
    }
    else {
      // TODO this is a first sibling that probably should not have a position
      //position = ", " + getDiffXhtypePosition() + " " + node.getParentNode().getName(getBbNameTemplate());
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
    if ((node != this.root) && (!node.hasChildNodes())) {
      nodeSb
      .append("$")
      .append(node.getName(getBbNameTemplate()));
      nodeSb.append("$");
    }
    nodeSb.append("}");
    String shape = this.makeShape(node);
    if (shape != null) {
      // TODO
    }
    nodeSb
    .append(";")
    .append(" % ")
    .append(node.getName(getNameTemplate()))
    .append("\n");
    // node outgoing edges
    writeEdges(node);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void writeEdges(IXholon node) {
    List<PortInformation> portList = node.getLinks(false, true);
    String outportName = "_out";
    String outportNameSuffix = "";
    if (node == this.root) {
      outportName = "_in";
      outportNameSuffix = "'";
    }
    else if (node.hasChildNodes()) {
      // TODO I'm assuming that a node with child nodes only has relay ports that are connected to internal nodes
      outportName = "_in";
      outportNameSuffix = "'";
    }
    for (int i = 0; i < portList.size(); i++) {
      PortInformation pi = (PortInformation)portList.get(i);
      IXholon reffedNode = pi.getReffedNode();
      if (reffedNode == null) {continue;}
      if (!reffedNode.hasAncestor(root.getName())) {
        // remoteNode is outside the scope (not a descendant) of root
        reffedNode = root; // keep the port within the scope of root by placing a relay port on root
      }
      boolean selfReffing = (reffedNode == node); // does this port point to node; is it self-referencing?
      linkSb
      .append("  ")
      .append(selfReffing ? "% " : "")
      .append("\\draw")
      .append("[ar]") // optional ?
      .append(" (")
      .append(node.getName(getBbNameTemplate()))
      .append(outportName)
      .append(i+1) // out string is 1-based rather than 0-based
      .append(outportNameSuffix)
      .append(")")
      .append(" to")
      .append(" (")
      .append(reffedNode.getName(getBbNameTemplate()))
      .append("_in")
      .append(this.getInportsIxAndDec(reffedNode))
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
  public void writeNodeAttributes(IXholon node) {
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
  
  /*
   * Download the preamble contents, insert it between part1 and part2, and write it all to the target.
   */
  protected void downloadPreambleAndWriteToTarget(String preambleFileName, String part1, String part2, String outFileName, String outPath, IXholon root) {
    try {
      //final String _contentType = contentType;
      final IXholon _root = root;
      new RequestBuilder(RequestBuilder.GET, preambleFileName).sendRequest("", new RequestCallback() {
        @Override
        public void onResponseReceived(Request req, Response resp) {
          if (resp.getStatusCode() == resp.SC_OK) {
            //root.println(resp.getText());
            writeToTarget(part1 + resp.getText() + part2, outFileName, outPath, _root);
          }
          else {
            //root.println("status code:" + resp.getStatusCode());
            //root.println("status text:" + resp.getStatusText());
            //root.println("text:\n" + resp.getText());
            writeToTarget(part1
              + "% PREAMBLE" + preambleFileName + " status code:" + resp.getStatusCode() + " status text:" + resp.getStatusText() + "text:" + resp.getText() + "\n"
              + part2, outFileName, outPath, _root);
          }
        }

        @Override
        public void onError(Request req, Throwable e) {
          //root.println("onError:" + e.getMessage());
          writeToTarget(part1
            + "% PREAMBLE" + preambleFileName + " onError:" + e.getMessage() + "\n"
            + part2, outFileName, outPath, _root);
        }
      });
    } catch(RequestException e) {
      //root.println("RequestException:" + e.getMessage());
      writeToTarget(part1
        + "% PREAMBLE" + preambleFileName + " RequestException:" + e.getMessage() + "\n"
        + part2, outFileName, outPath, root);
    }
  }
  
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
    p.scale = 1; // 1 2 0.5
    p.nameTemplate = "r:c_i"; // "r:c_i^" "R^^^^^"
    p.bbNameTemplate = "R^^^^^";
    p.maxChars = -1; // -1 1 3
    p.showPortName = false; // as a comment
    p.nodesStyle = "rectangle"; // rectangle
    p.linksStyle = "default"; // default
    p.siblingsPosition = "right=of";
    p.diffXhtypePosition = "below=of";
    p.bipartite = false; // ex: Packs and Cables
    p.intermedDashing = "loosely dotted"; // dashed,dotted,none,loosely dashed,densely dashed,loosely dotted,densely dotted,solid(default)
    p.includePreamble = true; 
    p.documentclass = "[10pt,oneside,article,landscape]{memoir}"; // content of \documentclass line, the first line in the preamble
    p.preambleFileName = "texWirDiaOpreamble.tex";
    p.includeBeginEndDoc = true; // whether to include \begin{document} and \end{document} and \title{...} and \author{...}
    p.title = "default"; // default is model name
    p.author = "default"; // default is GWT user
    p.includeBeginEndEqu = true; // whether or not to include \begin{equation} and \end{equation}
    this.efParams = p;
  }-*/;

  public native boolean isIncludePreamble() /*-{return this.efParams.includePreamble;}-*/;
  //public native void setIncludePreamble(boolean includePreamble) /*-{this.efParams.includePreamble = includePreamble;}-*/;

  public native String getDocumentclass() /*-{return this.efParams.documentclass;}-*/;
  //public native void setDocumentclass(String documentclass) /*-{this.efParams.documentclass = documentclass;}-*/;

  public native String getPreambleFileName() /*-{return this.efParams.preambleFileName;}-*/;
  //public native void setPreambleFileName(String preambleFileName) /*-{this.efParams.preambleFileName = preambleFileName;}-*/;

  public native boolean isIncludeBeginEndDoc() /*-{return this.efParams.includeBeginEndDoc;}-*/;
  //public native void setIncludeBeginEndDoc(boolean includeBeginEndDoc) /*-{this.efParams.includeBeginEndDoc = includeBeginEndDoc;}-*/;

  public native String getTitle() /*-{return this.efParams.title;}-*/;
  //public native void setTitle(String title) /*-{this.efParams.title = title;}-*/;

  public native String getAuthor() /*-{return this.efParams.author;}-*/;
  //public native void setAuthor(String author) /*-{this.efParams.author = author;}-*/;

  public native boolean isIncludeBeginEndEqu() /*-{return this.efParams.includeBeginEndEqu;}-*/;
  //public native void setIncludeBeginEndEqu(boolean includeBeginEndEqu) /*-{this.efParams.includeBeginEndEqu = includeBeginEndEqu;}-*/;

  public native double getScale() /*-{return this.efParams.scale;}-*/;
  //public native void setScale(double scale) /*-{this.efParams.scale = scale;}-*/;

  /**
   * Whether or not a link should show the name of the port (as a comment).
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

  public native boolean isBipartite() /*-{return this.efParams.bipartite;}-*/;
  //public native void setBipartite(boolean bipartite) /*-{this.efParams.bipartite = bipartite;}-*/;

  public native String getIntermedDashing() /*-{return this.efParams.intermedDashing;}-*/;
  public native void setIntermedDashing(String intermedDashing) /*-{this.efParams.intermedDashing = intermedDashing;}-*/;
  
}

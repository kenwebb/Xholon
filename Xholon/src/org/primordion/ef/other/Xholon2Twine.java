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
 * Export a Xholon model in Twine Archive format.
 * "Twine is an open-source tool for telling interactive, nonlinear stories."
 * Twine doesn't have a concept of hierarchy, so I can only export leaf nodes.
 * But have options to show hierarchy using links.
 *   optional link from node to its firstChild
 *   optional link from node to its nextSibling
 *   maybe also links to parentNode, previousSibling
 *   maybe also direct links to all children
 *   maybe reverse links from reffed nodes back to nodes that ref them
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on March 10, 2015)
 * @see <a href="http://twinery.org">Twine site</a>
 */
@SuppressWarnings("serial")
public class Xholon2Twine extends AbstractXholon2ExternalFormat implements IXholon2GraphFormat {
  
  /** StringBuilder initial capacity. */
  protected static final int SB_INITIAL_CAPACITY = 1000; // default is 16
  
  protected static final String TW_STORYDATA = "tw-storydata";
  protected static final String TW_PASSAGEDATA = "tw-passagedata";
  
  private String outFileName;
  private String outPath = "./ef/twine/";
  private String modelName;
  private IXholon root;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  /**
   * Start of the Twine archive.
   */
  protected StringBuilder startSb = null;
  
  /**
   * The accumulating text for all Twine passage nodes.
   */
  protected StringBuilder nodeSb = null;
  
  /**
   * End of the Twine archive.
   */
  protected StringBuilder endSb = null;
  
  /** Annotation service. */
  protected IXholon annService = null;
  
  /**
   * Constructor.
   */
  public Xholon2Twine() {}
  
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
    
    // <tw-storydata name="Kenz Test A" startnode="1" creator="Twine" creator-version="2.0.3" format="Harlowe" options="">
    this.startSb = new StringBuilder(256)
    .append("<").append(TW_STORYDATA)
    .append(" name=\"").append(modelName)
    .append("\" startnode=\"").append("TODO") // not known at this point
    .append("\" creator=\"").append("Twine") // could I write "Xholon" here ?
    .append("\" creator-version=\"2.0.3") // let users specify this in ef ?
    .append("\" format=\"").append(this.getFormat()) // let users specify this in ef
    .append("\" options=\"").append(this.getOptions()) // let users specify this in ef
    .append("\">\n")
    .append("<style role=\"stylesheet\" id=\"twine-user-stylesheet\" type=\"text/twine-css\"></style>\n")
    .append("<script role=\"script\" id=\"twine-user-script\" type=\"text/twine-javascript\"></script>\n")
    ;
    
    this.nodeSb = new StringBuilder(SB_INITIAL_CAPACITY);
    
    // </tw-storydata>
    this.endSb = new StringBuilder(128)
    .append("</").append(TW_STORYDATA).append(">")
    .append("\n");
    
    return true;
  }

  @Override
  public void writeAll() {
    writeNode(root);
    StringBuilder sb = new StringBuilder()
    .append("<!-- ")
    .append(modelName)
    .append("   see http://twinery.org   ")
    .append(timeNow)
    .append(" -->\n\n")
    .append(startSb.toString())
    .append(nodeSb.toString())
    .append(endSb.toString());
    
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
   * <tw-passagedata pid="1" name="Helloo" tags="" position="1,1">Helloo iz the first word in the phrase Helloo [[Worldd]].</tw-passagedata>
   */
  protected void writeNodeDetails(IXholon xhNode) {
    nodeSb.append("<").append(TW_PASSAGEDATA)
    .append(" pid=\"").append(xhNode.getId())
    .append("\" name=\"").append(xhNode.getName(getNameTemplate()))
    .append("\" tags=\"").append("")
    .append("\" position=\"").append(xhNode.getId()).append(",").append(xhNode.getId()).append("\">")
    ;
    String anno = findAnnotation(xhNode);
    if (anno != null) {
      // add Xholon annotations
      nodeSb.append("\n").append(anno);
    }
    // xhNode outgoing links
    writeEdges(xhNode);
    nodeSb.append("</").append(TW_PASSAGEDATA).append(">\n")
    ;
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public void writeEdges(IXholon xhNode) {
    List<PortInformation> portList =
        new org.primordion.xholon.base.ReflectionJavaMicro().getAllPorts(xhNode, false);
    for (int i = 0; i < portList.size(); i++) {
      PortInformation pi = (PortInformation)portList.get(i);
      nodeSb
      .append("\n");
      //.append(pi.getFieldName())
      //.append(" [[")
      //.append(pi.getReffedNode().getName(getNameTemplate()))
      //.append("]]")
      ;
      if (isShowPortName()) {
        String fieldName = pi.getFieldName();
        nodeSb.append(fieldName);
        if ("port".equals(fieldName)) {
          // TODO this always has a value of 0 (in Cell Model); maybe use ...IndexStr() ?
          nodeSb.append(pi.getFieldNameIndexStr());
        }
        nodeSb.append(" ");
      }
      nodeSb.append("[[")
      .append(pi.getReffedNode().getName(getNameTemplate()))
      .append("]]");
    }
    if (portList.size() > 0) {
      nodeSb.append("\n");
    }
  }

  @Override
  public void writeNodeAttributes(IXholon xhNode) {
    // nothing to do for now
  }
  
  public String findAnnotation(IXholon xhNode) {
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
    p.format = "Harlowe";
    p.options = "";
    p.showPortName = false;
    p.showFirstChildLink = true;
    p.showNextSiblingLink = true;
    p.showPrevSiblingLink = false;
    p.showParentLink = true;
    p.nameTemplate = "R^^_i^"; // "R^^^^^"
    this.efParams = p;
  }-*/;


  public native String getFormat() /*-{return this.efParams.format;}-*/;
  //public native void setFormat(String format) /*-{this.efParams.format = format;}-*/;

  public native String getOptions() /*-{return this.efParams.options;}-*/;
  //public native void setOptions(String options) /*-{this.efParams.options = options;}-*/;

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
  
}

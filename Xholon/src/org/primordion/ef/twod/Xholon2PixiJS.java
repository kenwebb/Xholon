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

package org.primordion.ef.twod;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Image;

import java.util.Date;
//import java.util.List;

import org.client.HtmlElementCache;
import org.primordion.xholon.base.IDecoration;
import org.primordion.xholon.base.IXholon;
//import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.io.gwt.HtmlScriptHelper;
import org.primordion.xholon.service.ef.IXholon2GraphFormat;

/**
 * Export a Xholon model to a PixiJS (v4) JavaScript scenegraph.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on April 13, 2017)
 * @see <a href="http://www.pixijs.com/">PixiJS website</a>
 */
@SuppressWarnings("serial")
public class Xholon2PixiJS extends AbstractXholon2ExternalFormat implements IXholon2GraphFormat {
  
  private String outFileName;
  private String outPath = "./ef/pixijs/";
  private String modelName;
  private IXholon root;
  private StringBuilder sb;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  protected String fileNameExtension = ".js";
  
  public Xholon2PixiJS() {}
  
  @Override
  public boolean initialize(String outFileName, String modelName, IXholon root) {
    this.timeNow = new Date();
    this.timeStamp = timeNow.getTime();
    if (outFileName == null) {
      this.outFileName = this.outPath + root.getXhcName() + "_" + root.getId()
        + "_" + this.timeStamp + this.fileNameExtension;
    }
    else {
      this.outFileName = outFileName;
    }
    this.modelName = modelName;
    this.root = root;
    
    return true;
  }
  
  @Override
  public void writeAll() {
    sb = new StringBuilder();
    layout(root, this.getWidth(), this.getHeight());
    writeStart();
    writeNode(root);
    writeEnd();
    writeToTarget(sb.toString(), outFileName, outPath, root);
    
    Element ele = HtmlElementCache.treeview;
    if (ele != null) {
      ele.getStyle().setHeight(this.getHeight(), Unit.PX);
      ele.getStyle().setWidth(this.getWidth(), Unit.PX);
    }
    
    HtmlScriptHelper.fromString(sb.toString(), false);
  }
  
  /**
   * Optionally use a layouter to write posh, posw, posx, posy values to each Xholon node's _jsdata JavaScript Object.
   */
  protected void layout(IXholon root, int width, int height) {
    switch (getLayouter()) {
    case "KLayJson":
      layoutKLayJson(root, width, height);
      break;
    case "Graphviz": break;
    case "":
    case "None":
    default: break;
    }
  }
  
  protected native void layoutKLayJson(IXholon root, int width, int height) /*-{
    $wnd.xh.xport("_other,KLayJson", root,
     '{"nameTemplate":"R^^^^^", "showEdges":false, "showPorts":false, "direction":"UNDEFINED", "edgeRouting":"ORTHOGONAL", "spacing":20, "width":'
     + width
     + ', "height":'
     + height
     + ', "nodeWidth":20, "nodeHeight":20, "portWidth":5, "portHeight":5, "selection":"#xhsvg", "maxChars":3, "writeToTarget":true, "writeLayoutedToTarget":true, "display":false, "_jsdata":true, "showStateMachineEntities":false}');
  }-*/;
  
  @Override
  public void writeNode(IXholon xhNode) {
    // only show state machine nodes if should show them, or if root is a StateMachineCE
    if ((xhNode.getXhcId() == CeStateMachineEntity.StateMachineCE)
        && (isShouldShowStateMachineEntities() == false)
        && (xhNode != root)) {
      return;
    }
    String xhNodeName = "node" + xhNode.getId();
    String[] jsdata = getJsdata(xhNode).split(",");
    sb
    .append("var ").append(xhNodeName).append(" = new $wnd.PIXI.Graphics();\n")
    .append(xhNodeName).append(".beginFill(").append(((IDecoration)xhNode).getColor()).append(" || 0x808080);\n")
    .append(xhNodeName).append(".drawRect(0,0,").append(jsdata[1]).append(",").append(jsdata[0]).append(");\n")
    .append(xhNodeName).append(".endFill();\n")
    .append(xhNodeName).append(".x = ").append(jsdata[2]).append(";\n")
    .append(xhNodeName).append(".y = ").append(jsdata[3]).append(";\n");
    writeNodeAttributes(xhNode);
    
    writeEdges(xhNode);
    
    IXholon childNode = xhNode.getFirstChild();
    while (childNode != null) {
      writeNode(childNode);
      sb.append(xhNodeName).append(".addChild(node").append(childNode.getId()).append(");\n");
      childNode = childNode.getNextSibling();
    }
  }
  
  protected native String getJsdata(IXholon me) /*-{
    var _jsdata = "";
    if (me._jsdata) {
      _jsdata += me._jsdata.posh + ",";
      _jsdata += me._jsdata.posw + ",";
      _jsdata += me._jsdata.posx + ",";
      _jsdata += me._jsdata.posy;
    }
    else {
      _jsdata = "20,20,20,20";
    }
    return _jsdata;
  }-*/;
  
  @Override
  public void writeEdges(IXholon xhNode) {
    // no edges
  }

  @Override
  public void writeNodeAttributes(IXholon xhNode) {
    
  }
  
  /**
   * Write start.
   */
  protected void writeStart() {
    sb
    .append("var $wnd = window;\n")
    .append("var $doc = document;\n")
    .append("var renderer = $wnd.PIXI.autoDetectRenderer(").append(this.getWidth()).append(", ").append(this.getHeight()).append(");\n")
    .append("$doc.querySelector(\"div#xhcanvas\").appendChild(renderer.view);\n")
    .append("//Create a container object called the `stage`. The `stage` is the root container for all your objects.\n")
    .append("var stage = new $wnd.PIXI.Container();\n");
    writeOptions();
  }
  
  /**
   * Write end.
   */
  protected void writeEnd() {
    sb
    .append("stage.addChild(node").append(root.getId()).append(");\n")
    .append("renderer.render(stage);\n");
  }
  
  /**
   * Write options
   */
  protected void writeOptions() {
    sb.append("// options\n");
    writeStyle();
  }
  
  /**
   * Write styles
   */
  protected void writeStyle() {
    sb.append("// style\n");
  }
  
  /**
   * Make a JavaScript object with all the parameters for this external format.
   */
  protected native void makeEfParams() /*-{
    var p = {};
    p.shouldShowStateMachineEntities = false;
    p.nameTemplate = "^^C^^^";
    p.xhAttrReturnAll = true;
    p.writeXholonId = false;
    p.writeXholonRoleName = true;
    p.writePorts = false;
    p.writeAnnotations = true;
    p.shouldPrettyPrint = true;
    p.writeAttributes = true;
    p.writeStandardAttributes = true;
    p.shouldWriteVal = true;
    p.shouldWriteAllPorts = true;
    p.layouter = "KLayJson"; // "Graphviz", "" or "None"
    p.width = 600;
    p.height = 600;
    this.efParams = p;
  }-*/;

  public native boolean isShouldShowStateMachineEntities() /*-{
    return efParams.shouldShowStateMachineEntities;
  }-*/;

  public native void setShouldShowStateMachineEntities(
      boolean shouldShowStateMachineEntities) /*-{
    this.efParams.shouldShowStateMachineEntities = shouldShowStateMachineEntities;
  }-*/;
  
  public native String getNameTemplate() /*-{
    return this.efParams.nameTemplate;
  }-*/;

  public native void setNameTemplate(String nameTemplate) /*-{
    this.efParams.nameTemplate = nameTemplate;
  }-*/;

  /*
   * @see org.primordion.xholon.io.xml.IXholon2Xml#getXhAttrReturnAll()
   */
  public native boolean getXhAttrReturnAll() /*-{
    return this.efParams.xhAttrReturnAll;
  }-*/;
  
  /*
   * @see org.primordion.xholon.io.xml.IXholon2Xml#setXhAttrReturnAll(boolean)
   */
  public native void setXhAttrReturnAll(boolean xhAttrReturnAll) /*-{
    this.efParams.xhAttrReturnAll = xhAttrReturnAll;
  }-*/;
  
  /* 
   * @see org.primordion.xholon.io.xml.IXholon2Xml#isWriteXholonId()
   */
  public native boolean isWriteXholonId() /*-{
    return this.efParams.writeXholonId;
  }-*/;
  
  /* 
   * @see org.primordion.xholon.io.xml.IXholon2Xml#setWriteXholonId(boolean)
   */
  public native void setWriteXholonId(boolean writeXholonId) /*-{
    this.efParams.writeXholonId = writeXholonId;
  }-*/;
  
  /*
   * @see org.primordion.xholon.io.xml.IXholon2Xml#isWriteXholonRoleName()
   */
  public native boolean isWriteXholonRoleName() /*-{
    return this.efParams.writeXholonRoleName;
  }-*/;

  /*
   * @see org.primordion.xholon.io.xml.IXholon2Xml#setWriteXholonRoleName(boolean)
   */
  public native void setWriteXholonRoleName(boolean writeXholonRoleName) /*-{
    this.efParams.writeXholonRoleName = writeXholonRoleName;
  }-*/;

  /*
   * @see org.primordion.xholon.io.xml.IXholon2Xml#isWritePorts()
   */
  public native boolean isWritePorts() /*-{
    return this.efParams.writePorts;
  }-*/;
  
  /*
   * @see org.primordion.xholon.io.xml.IXholon2Xml#setWritePorts(boolean)
   */
  public native void setWritePorts(boolean writePorts) /*-{
    this.efParams.writePorts = writePorts;
  }-*/;

  public native boolean isShouldPrettyPrint() /*-{
    return this.efParams.shouldPrettyPrint;
  }-*/;

  public native void setShouldPrettyPrint(boolean shouldPrettyPrint) /*-{
    this.efParams.shouldPrettyPrint = shouldPrettyPrint;
  }-*/;
  
  /*
   * @see org.primordion.xholon.io.xml.IXholon2Xml#isWriteAttributes()
   */
  public native boolean isWriteAttributes() /*-{
    return this.efParams.writeAttributes;
  }-*/;

  /*
   * @see org.primordion.xholon.io.xml.IXholon2Xml#setWriteAttributes(boolean)
   */
  public native void setWriteAttributes(boolean writeAttributes) /*-{
    this.efParams.writeAttributes = writeAttributes;
  }-*/;

  /*
   * @see org.primordion.xholon.io.xml.IXholon2Xml#isWriteStandardAttributes()
   */
  public native boolean isWriteStandardAttributes() /*-{
    return this.efParams.writeStandardAttributes;
  }-*/;

  /*
   * @see org.primordion.xholon.io.xml.IXholon2Xml#setWriteStandardAttributes(boolean)
   */
  public native void setWriteStandardAttributes(boolean writeStandardAttributes) /*-{
    this.efParams.writeStandardAttributes = writeStandardAttributes;
  }-*/;
  
  public native String getLayouter() /*-{
    return this.efParams.layouter;
  }-*/;

  public native void setLayouter(String layouter) /*-{
    this.efParams.layouter = layouter;
  }-*/;
  
  public native int getWidth() /*-{
    return this.efParams.width;
  }-*/;
  
  public native void setWidth(int width) /*-{
    this.efParams.width = width;
  }-*/;
  
  public native int getHeight() /*-{
    return this.efParams.height;
  }-*/;
  
  public native void setHeight(int height) /*-{
    this.efParams.height = height;
  }-*/;
  
}

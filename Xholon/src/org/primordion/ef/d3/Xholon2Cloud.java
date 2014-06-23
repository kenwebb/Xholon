/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2014 Ken Webb
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

package org.primordion.ef.d3;

import com.google.gwt.core.client.JsArrayString;

import java.util.Date;

import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.xholon.io.gwt.HtmlScriptHelper;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Export a Xholon model in D3 cloud layout format.
 * The result is a tag cloud that can be displayed on the app page.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on June 22, 2014)
 * @see <a href="https://github.com/jasondavies/d3-cloud">d3-cloud</a>
 * @see <a href="http://www.jasondavies.com/wordcloud/">online generation</a>
 */
@SuppressWarnings("serial")
public class Xholon2Cloud extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
  
  private String outFileName;
  private String outPath = "./ef/d3cloud/";
  private String modelName;
  private IXholon root;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  /** Whether or not to show state machine nodes. */
  private boolean shouldShowStateMachineEntities = false;
  
  /** Use this to capture the words, instead of using StringBuilder sb */
  private JsArrayString wordsArr = null;

  /**
   * Constructor.
   */
  public Xholon2Cloud() {}
  
  /**
   * Get an empty native JavaScript array.
   */
  protected native JsArrayString getNativeArray() /*-{return [];}-*/;
  
  /*
   * @see org.primordion.xholon.io.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
   */
  public boolean initialize(String mmFileName, String modelName, IXholon root) {
    timeNow = new Date();
    timeStamp = timeNow.getTime();
    if (mmFileName == null) {
      this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".sexpr";
    }
    else {
      this.outFileName = mmFileName;
    }
    this.modelName = modelName;
    this.root = root;
    
    if (!isDefinedD3Layout()) {
      loadD3Cloud();
      return true; // do not return false; that just causes an error message
    }
    
    return true;
  }
  
  /*
   * @see org.primordion.xholon.io.IXholon2ExternalFormat#writeAll()
   */
  public void writeAll() {
    if (!isDefinedD3Layout()) {return;}
    wordsArr = getNativeArray();
    writeNode(root, 0); // root is level 0
    writeToTarget(wordsArr.join(), outFileName, outPath, root);
    if (root.getApp().isUseGwt()) {
			runScript(wordsArr, getWidth(), getHeight(), getFontFamily(),
			  getAngle(), getSizeIncrementer(), getSizeMultiplier(),
			  getTranslateX(), getTranslateY(), getPadding());
		}
		else {}
  }
  
  /**
   * Write one node, and its child nodes.
   * @param node The current node in the Xholon hierarchy.
   * @param level Current level in the hierarchy.
   */
  protected void writeNode(IXholon node, int level) {
    // only show state machine nodes if should show them, or if root is a StateMachineCE
    if ((node.getXhcId() == CeStateMachineEntity.StateMachineCE)
        && (shouldShowStateMachineEntities == false)
        && (level > 0)) {
      return;
    }
    if ((node != root) || isShouldShowRootNode()) {
      String nodeName = node.getName(getNameTemplate());
      wordsArr.push(nodeName);
    }
    
    // children
    if (node.hasChildNodes()) {
      IXholon childNode = node.getFirstChild();
      while (childNode != null) {
        writeNode(childNode, level+1);
        childNode = childNode.getNextSibling();
      }
    }
  }
  
  /**
   * Load D3 Cloud library asynchronously.
   */
  protected void loadD3Cloud() {
    require(this);
  }
  
  /**
   * use requirejs
   */
  protected native void require(final IXholon2ExternalFormat xh2D3Cloud) /*-{
    $wnd.requirejs.config({
      enforceDefine: false,
      paths: {
        cloud: [
          "xholon/lib/d3.layout.cloud"
        ]
      }
    });
    $wnd.require(["cloud"], function(cloud) {
      xh2D3Cloud.@org.primordion.xholon.service.ef.IXholon2ExternalFormat::writeAll()();
    });
  }-*/;
  
  /**
   * Is $wnd.d3.layout defined.
   * @return it is defined (true), it's not defined (false)
   */
  protected native boolean isDefinedD3Layout() /*-{
    return (typeof $wnd.d3 != "undefined")
      && (typeof $wnd.d3.layout != "undefined")
      && (typeof $wnd.d3.layout.cloud != "undefined");
  }-*/;
  
  /**
   * Run a d3-cloud script.
   * source: simple.html in d3-cloud distro
   * @param words 
   * @param width 
   * @param height 
   */
  protected native void runScript(JsArrayString wordzArr, int width, int height,
    String fontFamily, int angle, int sizeIncrementer, int sizeMultiplier,
    int translateX, int translateY, int padding) /*-{
    var fill = $wnd.d3.scale.category20();
    
    $wnd.d3.layout.cloud()
      .size([width, height])
      .words(wordzArr.map(function(d) {
        return {text: d, size: sizeIncrementer + Math.random() * sizeMultiplier};
      }))
      .padding(padding)
      .rotate(function() { return Math.floor(Math.random() * (angle + angle) - angle); })
      .font(fontFamily)
      .fontSize(function(d) { return d.size; })
      .on("end", draw)
      .start();
    
    function draw(words) {
      $wnd.d3.select("div#xhgraph").append("svg")
          .attr("width", width)
          .attr("height", height)
        .append("g")
          .attr("transform", "translate(" + translateX + "," + translateY + ")")
        .selectAll("text")
          .data(words)
        .enter().append("text")
          .style("font-size", function(d) { return d.size + "px"; })
          .style("font-family", fontFamily)
          .style("fill", function(d, i) { return fill(i); })
          .attr("text-anchor", "middle")
          .attr("transform", function(d) {
            return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
          })
          //.attr("id", function(d) { return d.text; })
          .text(function(d) { return d.text; });
    }
  }-*/;
  
  /**
   * Make a JavaScript object with all the parameters for this external format.
   * Param values:
   *  Countries of Europe: R^^^^^ 800 500 Impact 20 15 20 400 250 0
   *  Cell XholonClass: BLANK 1300 900 Impact 90 6 9 600 450 0
   */
  protected native void makeEfParams() /*-{
    var p = {};
    p.shouldShowRootNode = true;
    p.nameTemplate = "R^^^^^";
    p.width = 800;
    p.height = 500;
    p.fontFamily = "Impact";
    p.angle = 60;
    p.sizeIncrementer = 4; // 10
    p.sizeMultiplier = 30; // 90
    p.translateX = 350;
    p.translateY = 200;
    p.padding = 5;
    this.efParams = p;
  }-*/;
  
  public native boolean isShouldShowRootNode() /*-{return this.efParams.shouldShowRootNode;}-*/;
  //public native void setShouldShowRootNode(boolean shouldShowRootNode) /*-{this.efParams.shouldShowRootNode = shouldShowRootNode;}-*/;
  
  public native String getNameTemplate() /*-{return this.efParams.nameTemplate;}-*/;
  //public native void setNameTemplate(String nameTemplate) /*-{this.efParams.nameTemplate = nameTemplate;}-*/;
  
  public native int getWidth() /*-{return this.efParams.width;}-*/;
  //public native void setWidth(int width) /*-{this.efParams.width = width;}-*/;

  public native int getHeight() /*-{return this.efParams.height;}-*/;
  //public native void setHeight(int height) /*-{this.efParams.height = height;}-*/;
  
  public native String getFontFamily() /*-{return this.efParams.fontFamily;}-*/;
  //public native void setFontFamily(String fontFamily) /*-{this.efParams.fontFamily = fontFamily;}-*/;
  
  public native int getAngle() /*-{return this.efParams.angle;}-*/;
  //public native void setAngle(int angle) /*-{this.efParams.angle = angle;}-*/;
  
  public native int getSizeIncrementer() /*-{return this.efParams.sizeIncrementer;}-*/;
  //public native void setSizeIncrementer(int sizeIncrementer) /*-{this.efParams.sizeIncrementer = sizeIncrementer;}-*/;
  
  public native int getSizeMultiplier() /*-{return this.efParams.sizeMultiplier;}-*/;
  //public native void setSizeMultiplier(int sizeMultiplier) /*-{this.efParams.sizeMultiplier = sizeMultiplier;}-*/;
  
  public native int getTranslateX() /*-{return this.efParams.translateX;}-*/;
  //public native void setTranslateX(int translateX) /*-{this.efParams.width = translateX;}-*/;
  
  public native int getTranslateY() /*-{return this.efParams.translateY;}-*/;
  //public native void setTranslateY(int translateY) /*-{this.efParams.translateY = translateY;}-*/;
  
  public native int getPadding() /*-{return this.efParams.padding;}-*/;
  //public native void setPadding(int padding) /*-{this.efParams.padding = padding;}-*/;
  
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

  public boolean isShouldShowStateMachineEntities() {
    return shouldShowStateMachineEntities;
  }

  public void setShouldShowStateMachineEntities(
      boolean shouldShowStateMachineEntities) {
    this.shouldShowStateMachineEntities = shouldShowStateMachineEntities;
  }
  
  public String getOutPath() {
    return outPath;
  }

  public void setOutPath(String outPath) {
    this.outPath = outPath;
  }

}

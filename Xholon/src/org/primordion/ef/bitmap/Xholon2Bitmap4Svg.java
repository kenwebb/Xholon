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

package org.primordion.ef.bitmap;

import java.util.Date;

import org.primordion.xholon.base.IXholon;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Export a D3 CirclePack SVG image in PNG or other bitmap format.
 * Optionally also export and display stand-alone SVG with styles, and display canvas.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <p><a href="https://gist.github.com/Caged/4649511">Convert SVGs to PNGs</a>. This works OK if the SVG styles are inline. The SVG element must contain an xmlns attribute. Webkit also requires you specify a font size on text elements.</p>
 * @see <a href="https://gist.github.com/gustavohenke/9073132">A more complete SVG to PNG.</a>
 * @since 0.9.1 (Created on December 24, 2014)
 */
@SuppressWarnings("serial")
public class Xholon2Bitmap4Svg extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
  
  protected String outFileName;
  protected String outFileNameExt = ".png";
	protected String outPath = "./ef/bitmap/";
	protected String modelName;
	protected IXholon root;
	
	/** Current date and time. */
	protected Date timeNow;
	protected long timeStamp;
	
	public Xholon2Bitmap4Svg() {}
  
  @Override
  public boolean initialize(String outFileName, String modelName, IXholon root) {
    timeNow = new Date();
		timeStamp = timeNow.getTime();
		if (outFileName == null) {
			this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + outFileNameExt;
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
    String selector = getSelector(); // "#xhanim>#one>svg" or "#xhgraph>svg" or "#xhsvg>svg"
    Object svg = selectSvgElement(selector);
    String svgData = serializeSvg(svg).trim();
    
    // optionally get style and insert it into the SVG text
    String svgDataWithStyle = svgData;
    if (isIncludeInlineStyle()) {
      String style = makeCssStyle(svg);
      int insertPoint = svgData.indexOf("<svg");
      insertPoint = svgData.indexOf("<g", insertPoint);
      svgDataWithStyle = svgData.substring(0, insertPoint)
        + "\n<style>\n" + style + "\n</style>\n" + svgData.substring(insertPoint);
    }
    
    if (isWriteSvgTextToTab()) {
      writeToTarget(svgDataWithStyle);
    }
    makeCanvasImage(svg, svgDataWithStyle, this, getType());
  }
  
  /**
   * Write to a Xholon gui tab.
   * This can be called by Java or JavaScript.
   * @param data SVG or PNG (or other) text data.
   */
  public void writeToTarget(String data) {
    writeToTarget(data, outFileName, outPath, root);
  }
  
  /**
   * Select the SVG element.
   * @param 
   * @return 
   */
  protected native Object selectSvgElement(String selector) /*-{
    return $doc.querySelector(selector);
  }-*/;
  
  /**
   * Serialize SVG in the HTML DOM, to text.
   * @param selector ex: "#one>svg"
   * @return the serialized string, or null?
   */
  protected native String serializeSvg(Object svg) /*-{
    return new $wnd.XMLSerializer().serializeToString(svg);
  }-*/;
  
  /**
   * Make a PNG or other bitmap image, using a canvas as an intermediary.
   * @param svg 
   * @param svgData 
   * @param myself 
   * @param type 
   */
  protected native void makeCanvasImage(Object svg, String svgData, Xholon2Bitmap4Svg myself, String type) /*-{
    var canvas = $doc.createElement("canvas");
    var svgSize = svg.getBoundingClientRect();
    canvas.width = svgSize.width;
    canvas.height = svgSize.height;
    var ctx = canvas.getContext("2d");
    var img = $doc.createElement("img");
    img.setAttribute("src", "data:image/svg+xml;base64," + $wnd.btoa(svgData));
    var $this = this;
    img.onload = function() {
      ctx.drawImage(img, 0, 0);
      var bitmapData = canvas.toDataURL(type);
      if ($this.efParams.writeBitmapTextToTab) {
        myself.@org.primordion.ef.bitmap.Xholon2Bitmap4Svg::writeToTarget(Ljava/lang/String;)(bitmapData);
      }
      if ($this.efParams.displayStandAloneSvg) {
        $doc.querySelector("#xhsvg").appendChild(img);
      }
      if ($this.efParams.displayCanvas) {
        $doc.querySelector("#xhcanvas").appendChild(canvas);
      }
    };
  }-*/;
  
  /**
   * Make CSS style content.
   * This code is based on https://github.com/exupero/saveSvgAsPng
   * @param el An SVG element.
   * @return a CSS string.
   */
  protected native String makeCssStyle(Object el) /*-{
    var css = "";
    var sheets = $doc.styleSheets;
    for (var i = 0; i < sheets.length; i++) {
      var url = sheets[i].href;
      var isExternal = url && url.lastIndexOf('http',0) == 0 && url.lastIndexOf($wnd.location.host) == -1;
      if (isExternal) {
        $wnd.console.warn("Cannot include styles from other hosts: " + sheets[i].href);
        continue;
      }
      var rules = sheets[i].cssRules;
      if (rules != null) {
        for (var j = 0; j < rules.length; j++) {
          var rule = rules[j];
          if (typeof(rule.style) != "undefined") {
            var matches = el.querySelectorAll(rule.selectorText);
            if (matches.length > 0) {
              var selector = rule.selectorText;
              css += selector + " { " + rule.style.cssText + " }\n";
            }
          }
        }
      }
    }
    return css;
  }-*/;
  
  /**
   * Make a JavaScript object with all the parameters for this external format.
   */
  protected native void makeEfParams() /*-{
    var p = {};
    p.selector = "#xhanim>#one>svg"; // "#xhgraph>svg" "#xhsvg>svg"
    p.type = "image/png"; // "image/jpeg"
    p.includeInlineStyle = true;
    p.writeSvgTextToTab = false;
    p.writeBitmapTextToTab = true;
    p.displayStandAloneSvg = false;
    p.displayCanvas = false;
    this.efParams = p;
  }-*/;
  
  public native String getSelector() /*-{return this.efParams.selector;}-*/;
  //public native void setSelector(String selector) /*-{this.efParams.selector = selector;}-*/;
  
  public native String getType() /*-{return this.efParams.type;}-*/;
  //public native void setType(String type) /*-{this.efParams.type = type;}-*/;
  
  public native boolean isIncludeInlineStyle() /*-{return this.efParams.includeInlineStyle;}-*/;
  //public native void setIncludeInlineStyle(boolean includeInlineStyle) /*-{this.efParams.includeInlineStyle = includeInlineStyle;}-*/;
  
  public native boolean isWriteSvgTextToTab() /*-{return this.efParams.writeSvgTextToTab;}-*/;
  //public native void setWriteSvgTextToTab(boolean writeSvgTextToTab) /*-{this.efParams.writeSvgTextToTab = writeSvgTextToTab;}-*/;
  
  public native boolean isWriteBitmapTextToTab() /*-{return this.efParams.writeBitmapTextToTab;}-*/;
  //public native void setWriteBitmapTextToTab(boolean writeBitmapTextToTab) /*-{this.efParams.writeBitmapTextToTab = writeBitmapTextToTab;}-*/;
  
  public native boolean isDisplayStandAloneSvg() /*-{return this.efParams.displayStandAloneSvg;}-*/;
  //public native void setDisplayStandAloneSvg(boolean displayStandAloneSvg) /*-{this.efParams.displayStandAloneSvg = displayStandAloneSvg;}-*/;
  
    public native boolean isDisplayCanvas() /*-{return this.efParams.displayCanvas;}-*/;
  //public native void setDisplayCanvas(boolean displayCanvas) /*-{this.efParams.displayCanvas = displayCanvas;}-*/;

}

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

package org.primordion.xholon.script;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.io.gwt.HtmlScriptHelper;

/**
 * Animate a Xholon subtree using D3 Circle Pack.
 * I should be able to paste this into any Xholon app with a CSH that changes,
 * for example the simple Generic app that adds a new node each time step.
 * 
 * Examples:
 * <p><pre>&lt;Animate/></pre></p>
 * <p><pre>&lt;Animate xpath="./*" efParams='{"sort":"disable","width":600,"height":600,"mode":"new"}'/></pre></p>
 * <p><pre>&lt;Animate duration="1.234" efParams='{"sort":"disable","width":200,"height":200,"mode":"new"}'/></pre></p>
 * <p><pre>&lt;Animate xpath="" selection="div#xhsvg" writeToTab="true" duration="2" efParams='{"selection":"div#xhsvg","sort":"disable","width":200,"height":200,"mode":"new"}'/></pre></p>
 * <p><pre>&lt;Animate cssStyle=".d3cpnode circle {stroke-width: 0px;}" xpath="" selection="div#xhsvg" writeToTab="false" duration="5" efParams='{"selection":"div#xhsvg","sort":"disable","width":200,"height":200,"mode":"tween"}'/></pre></p>
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on November 22, 2014)
 */
public class Animate extends XholonScript {
  
  /** The name of the external format (ef) */
  private String formatName = "_d3,CirclePack";
  
  /** An XPath expression to get from the app's CSH root node to the animation root node */
  private String xpath = ".";
  
  /** Parameters that will be passed to the ef exporter */
  private String efParams = "{\"shouldIncludeDecorations\":true,\"sort\":\"disable\",\"width\":600,\"height\":600,\"mode\":\"tween\"}";
  
  /** Whether or not to write the generated ef-specific code to a Xholon tab */
  private boolean writeToTab = false;
  
  /** CSS selection for parent of the SVG content */
  private String selection = "#xhgraph";
  
  /** Tween duration in seconds */
  private double duration = 2;
  
  /** Optional style tag that should be added to HTML head */
  //$wnd.xh.css.style(".d3cpnode circle {stroke-width: 0px;}");
  private String cssStyle = null;
  
  private IApplication app = null;
  private IXholon xhAnimRoot = null;
  
  /*
	 * @see org.primordion.xholon.base.Xholon#postConfigure()
	 */
	public void postConfigure() {
	  HtmlScriptHelper.requireScript("xhSvgTween", null);
	  app = this.getApp();
	  xhAnimRoot = app.getRoot();
	  if (xpath.length() != 0) {
	    xhAnimRoot = this.getXPath().evaluate(xpath, xhAnimRoot);
	  }
	  // align the tween duration with the Xholon TimeStepInterval
	  app.setParam("TimeStepInterval", Integer.toString(((int)(duration * 1000.0))));
	  if (cssStyle != null) {
	    style(cssStyle);
	  }
	}
	
  /*
	 * @see org.primordion.xholon.base.Xholon#act()
	 */
	public void act() {
	  xport(formatName, xhAnimRoot, efParams, writeToTab);
	  tween(selection, duration);
	  super.act();
	}
	
	/**
	 * Here's what a typical call to xh.xport() with XholonJsApi.java looks like:
   * $wnd.xh.xport('_d3,CirclePack', root.first(),
   *  '{"shouldIncludeDecorations":true,"sort":"disable","width":600,"height":600,"mode":"tween"}',false);
	 */
	protected native void xport(String formatName, IXholon xhAnimRoot, String efParams, boolean writeToTab) /*-{
	  $wnd.xh.xport(formatName, xhAnimRoot, efParams, writeToTab);
	}-*/;
	
	protected native void tween(String selection, double duration) /*-{
	  if ($wnd.xh.tween) {
      $wnd.xh.tween(selection, duration);
    }
	}-*/;
	
	protected native void style(String cssStyle) /*-{
	  $wnd.xh.css.style(cssStyle);
	}-*/;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setAttributeVal(java.lang.String, java.lang.String)
	 */
	public int setAttributeVal(String attrName, String attrVal) {
		if ("formatName".equals(attrName)) {
			this.formatName = attrVal;
		}
		else if ("xpath".equals(attrName)) {
			this.xpath = attrVal;
		}
		else if ("efParams".equals(attrName)) {
			this.efParams = attrVal;
		}
		else if ("writeToTab".equals(attrName)) {
			this.writeToTab = Boolean.parseBoolean(attrVal);
		}
		else if ("selection".equals(attrName)) {
			this.selection = attrVal;
		}
		else if ("duration".equals(attrName)) {
			this.duration = Double.parseDouble(attrVal);
		}
		else if ("cssStyle".equals(attrName)) {
			this.cssStyle = attrVal;
		}
		return 0;
	}
	
}

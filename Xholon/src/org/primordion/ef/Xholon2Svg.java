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

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Export a Xholon model in SVG format.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 13, 2012)
 */
@SuppressWarnings("serial")
public class Xholon2Svg extends org.primordion.xholon.io.Xholon2Svg implements IXholon2ExternalFormat {
  
  @Override
  public native boolean canAdjustOptions() /*-{
    $wnd.console.log("org.primordion.ef.Xholon2Svg canAdjustOptions() ");
    $wnd.console.log(this);
    $wnd.console.log(this.efParams);
    if (this.efParams) {
      return true;
    }
    return false;
  }-*/;
  
  @Override
  public void adjustOptions(String outFileName, String modelName, IXholon root, String formatName) {
    consoleLog("org.primordion.ef.Xholon2Svg adjustOptions(...)");
    loadDatGui(outFileName, modelName, root, formatName);
  }
  
  /**
   * Allow user to change the values of options for this external format writer.
   * This should only be called if the dat.GUI library has been loaded.
   * TODO temporarily copied from ef.AbstractXholon2ExternalFormat
   */
  public native void adjustOptionsNative(String outFileName, String modelName, IXholon root, String formatName) /*-{
    if (this.efParams && ((typeof $wnd.dat != "undefined") && (typeof $wnd.dat.GUI != "undefined"))) {
	    if (typeof $wnd.xh.efParamsGui == "undefined") {
	      $wnd.xh.efParamsGui = new $wnd.dat.GUI();
	    }
      var gui = $wnd.xh.efParamsGui;
      var p = this.efParams;
	    var folder = gui.addFolder(formatName);
	    var $this = this;
      p.doExport = function() {
        $this.@org.primordion.xholon.service.ef.IXholon2ExternalFormat::initialize(Ljava/lang/String;Ljava/lang/String;Lorg/primordion/xholon/base/IXholon;)(outFileName, modelName, root);
        $this.@org.primordion.xholon.service.ef.IXholon2ExternalFormat::writeAll()();
      };
      //gui.remember(p); // causes page freeze in Firefox, but not in Chrome
      for (var prop in p) {
        var pname = prop;
        //if (pname == "EXPORT_TYPE") {continue;}
        var pval = p[pname];
        if ((pval.length == 7) && (pval.charAt(0) == "#")) {
          folder.addColor(p, pname); // ex: ""#ffae23""
        }
        else {
          folder.add(p, pname);
        }
      }
      folder.open();
	  }
  }-*/;
  
	/*
	 * @see org.primordion.ef.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
	 */
	public boolean initialize(String outFileName, String modelName, IXholon root) {
		this.setSvgPathName("./ef/svg/");
		return super.initialize(outFileName, modelName, root);
	}
	
	/**
   * Load dat.GUI library asynchronously.
   * TODO temporarily copied from ef.AbstractXholon2ExternalFormat
   */
  protected void loadDatGui(String outFileName, String modelName, IXholon root, String formatName) {
    //loadCss("xholon/lib/dat-gui.css");
    requireDatGui(this, outFileName, modelName, root, formatName);
  }
  
  /**
   * use requirejs to load dat.GUI
   * TODO temporarily copied from ef.AbstractXholon2ExternalFormat
   */
  protected native void requireDatGui(final Xholon2Svg ef, String outFileName, String modelName, IXholon root, String formatName) /*-{
    $wnd.requirejs.config({
      enforceDefine: false,
      paths: {
        datgui: [
          "xholon/lib/dat.gui.min"
        ]
      }
    });
    $wnd.require(["datgui"], function(datgui) {
      ef.@org.primordion.ef.Xholon2Svg::adjustOptionsNative(Ljava/lang/String;Ljava/lang/String;Lorg/primordion/xholon/base/IXholon;Ljava/lang/String;)(outFileName, modelName, root, formatName);
    });
  }-*/;

}

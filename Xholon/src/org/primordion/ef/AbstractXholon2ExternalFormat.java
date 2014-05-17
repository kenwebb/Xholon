package org.primordion.ef;

// Java Standard Edition (SE) imports
/*
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.security.AccessControlException;
import org.primordion.xholon.util.MiscIo;
*/

// GWT imports
/*import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;*/
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;

import org.client.RCImages;
import org.primordion.xholon.base.IDecoration;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.Xholon;

import org.primordion.xholon.io.XholonGwtTabPanelHelper;

/**
 * Abstract superclass for classes that write Xholon subtrees to external formats.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on January 19, 2013)
 */
@SuppressWarnings("serial")
public abstract class AbstractXholon2ExternalFormat extends Xholon {
  
  private boolean writeToTab = true;
  
  /**
	 * Constructor.
	 */
  public AbstractXholon2ExternalFormat() {
    makeEfParams();
  }
  
  /**
	 * Make a JavaScript object with all the parameters for this external format.
	 * Subclasses may override this.
	 */
	protected void makeEfParams() {}
  
  public native boolean canAdjustOptions() /*-{
    if (this.efParams) {
      return true;
    }
    return false;
  }-*/;
  
  /**
   * Allow user to change the values of options for this external format writer.
   * Use dat.GUI.js
   * Use Xholon require or require.js to load dat.GUI.js
   * @param 
   */
  public void adjustOptions(String outFileName, String modelName, IXholon root, String formatName) {
    loadDatGui(outFileName, modelName, root, formatName);
  }
  
  /**
   * Allow user to change the values of options for this external format writer.
   * This should only be called if the dat.GUI library has been loaded.
   */
  public native void adjustOptionsNative(String outFileName, String modelName, IXholon root, String formatName) /*-{
    if (this.efParams && ((typeof $wnd.dat != "undefined") && (typeof $wnd.dat.GUI != "undefined"))) {
	    if (typeof $wnd.xh.efParamsGui == "undefined") {
	      $wnd.xh.efParamsGui = new $wnd.dat.GUI();
	    }
      var gui = $wnd.xh.efParamsGui;
      var p = this.efParams;
	    var folder = null;
	    try {
	      folder = gui.addFolder(formatName);
	    } catch (e) {
	      // prevent 'Error: You already have a folder in this GUI by the name "_d3,CirclePack"'
	      folder = gui.addFolder(formatName + new Date().getTime());
	    }
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
  
  /**
   * Write the entire external-format text to an appropriate target.
   * @param efText The external-format text.
   * @param uri A file name, or a GWT-usable tooltip.
   * @param outPath A file system path name, or GWT-usable content type (ex: "_xhn"), or equivalent.
   * @param root The root node of the subtree being written out.
   */
  protected void writeToTarget(String efText, String uri, String outPath, IXholon root) {
    if (root.getApp().isUseGwt()) {
      if (writeToTab) {
        XholonGwtTabPanelHelper.addTab(efText, outPath, uri, false);
      }
      else {
        root.println(efText);
      }
    }
    else {
      writeToTargetFile(efText, uri, outPath, root);
    }
  }
  
  /**
   * Write the entire external-format text to a file,
   * or to Application.getOut(),
   * or to some equivalent operating system target.
   * This is typically used in a desktop or servlet environment.
   * @param efText The external-format text.
   * @param fileName A file name.
   * @param path A file system path name.
   * @param root The root node of the subtree being written out.
   */
  protected void writeToTargetFile(String efText, String fileName, String path, IXholon root) {
    // uncomment this for use in a desktop or servlet environment
    /*Writer out;
    boolean shouldClose = true;
    if (root.getApp().isUseAppOut()) {
      out = root.getApp().getOut();
      shouldClose = false;
    }
    else {
      try {
        // create any missing output directories
        File dirOut = new File(path);
        dirOut.mkdirs(); // will create a new directory only if there is no existing one
        out = MiscIo.openOutputFile(fileName);
      } catch(AccessControlException e) {
        out = root.getApp().getOut();
        shouldClose = false;
      }
    }
    try {
      out.write(efText);
      out.flush();
    } catch (IOException e) {
      Xholon.getLogger().error("", e);
    }
    if (shouldClose) {
      MiscIo.closeOutputFile(out);
    }*/
  }
  
    /**
   * Get the icon of a node.
   * ex iconFileName: "images/StateMachineEntity/stateMachine.png"
   * ex rcImages    : "StateMachineEntity_stateMachine"
   * @param xhcNode An instance of IXholonClass or IMechanism or other type that implements IDecoration.
   * @return A GWT image specific to that xhcNode, or the default GWT image.
   */
  protected Image getImageIcon(IDecoration xhcNode) {
    Image icon = null;
    String iconFileName = ((IDecoration)xhcNode).getIcon();
    if (iconFileName != null) {
      icon = rcImages(iconFileName.substring(7, iconFileName.length() - 4).replace("/", "_"));
    }
    else {
      if (xhcNode instanceof IXholonClass) {
        IXholon p = ((IXholonClass)xhcNode).getParentNode();
        if ((p != null) && (p instanceof IXholonClass)) {
          // try to get an icon from the parent class
          return getImageIcon((IDecoration)p);
        }
      }
    }
    if (icon == null) {
      icon = rcImages("Control_bullet_blue");
    }
    return icon;
  }
  
  /**
   * Get a GWT image.
   * @param resourceName A GWT resource name (ex "StateMachineEntity_stateMachine").
   * @return A GWT image, or null.
   */
  protected Image rcImages(String resourceName) {
    return new Image((ImageResource)RCImages.INSTANCE.getResource(resourceName));
  }
  
  /**
   * Get the toolTip of a node.
   * @param node
   * @return
   */
  protected String getToolTip(IDecoration node) {
    String toolTip = null;
    String toolTipStr = ((IDecoration)node).getToolTip();
    if (toolTipStr != null) {
      toolTip = toolTipStr;
    }
    else {
      if (node instanceof IXholonClass) {
        IXholon p = ((IXholonClass)node).getParentNode();
        if ((p != null) && (p instanceof IXholonClass)) {
          // try to get a color from the parent class
          return getToolTip((IDecoration)p);
        }
      }
    }
    return toolTip;
  }
  
  /**
   * Get the color of a node.
   * @param node
   * @return A color String (ex: "#2E8B57" "red").
   */
  protected String getColor(IDecoration node) {
    String color = ((IDecoration)node).getColor();
    if (color == null) {
      if (node instanceof IXholonClass) {
        IXholon p = ((IXholonClass)node).getParentNode();
        if ((p != null) && (p instanceof IXholonClass)) {
          // try to get a color from the parent class
          return getColor((IDecoration)p);
        }
      }
    }
    if (color != null) {
      if (color.startsWith("0x")) {
        color = "#" + color.substring(2);
      }
    }
    return color;
  }
  
  /**
   * Get the font of a node.
   * @param node
   * @return A font String (ex: "").
   */
  protected String getFont(IDecoration node) {
    String font = ((IDecoration)node).getFont();
    if (font == null) {
      if (node instanceof IXholonClass) {
        IXholon p = ((IXholonClass)node).getParentNode();
        if ((p != null) && (p instanceof IXholonClass)) {
          // try to get a color from the parent class
          return getFont((IDecoration)p);
        }
      }
    }
    if (font != null) {
      // the font is stored in <Font> tags in Java Font format (ex: "Arial-BOLD-18")
      // convert to CSS format (ex: "bold 18px arial,sans-serif")
      String[] fontFields = font.trim().toLowerCase().split("-");
      if (fontFields.length == 3) {
        font = fontFields[1] + " " + fontFields[2] + "px " + fontFields[0] + ",sans-serif";
      }
    }
    return font;
  }
  
  /**
   * Load dat.GUI library asynchronously.
   */
  protected void loadDatGui(String outFileName, String modelName, IXholon root, String formatName) {
    //loadCss("xholon/lib/dat-gui.css");
    requireDatGui(this, outFileName, modelName, root, formatName);
  }
  
  /**
   * use requirejs to load dat.GUI
   */
  protected native void requireDatGui(final AbstractXholon2ExternalFormat ef, String outFileName, String modelName, IXholon root, String formatName) /*-{
    $wnd.requirejs.config({
      enforceDefine: false,
      paths: {
        datgui: [
          "xholon/lib/dat.gui.min"
        ]
      }
    });
    $wnd.require(["datgui"], function(datgui) {
      ef.@org.primordion.ef.AbstractXholon2ExternalFormat::adjustOptionsNative(Ljava/lang/String;Ljava/lang/String;Lorg/primordion/xholon/base/IXholon;Ljava/lang/String;)(outFileName, modelName, root, formatName);
    });
  }-*/;
  
  protected native void loadCss(String url) /*-{
    var link = $doc.createElement("link");
    link.type = "text/css";
    link.rel = "stylesheet";
    link.href = url;
    $doc.getElementsByTagName("head")[0].appendChild(link);
  }-*/;
  
}

package org.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * This class makes available all the IDed elements in Xholon.html
 * as public static Element.
 */
public class HtmlElementCache {
  
  public static final String XH_ELEMENT_PREFIX = "xh";
  
  public static final Element xhtop = Document.get().getElementById("xhtop");
  
  public static final Element xholontitle = Document.get().getElementById("xholontitle");
  
  public static final Element timestep = Document.get().getElementById("timestep");
  
  public static final Element maxprocessloops = Document.get().getElementById("maxprocessloops");
  
  public static final Element xhgui = Document.get().getElementById("xhgui");
  
  public static final Element xhappspecific = Document.get().getElementById("xhappspecific");
  
  public static final Element xhconsole = Document.get().getElementById("xhconsole");
  
  // initially at load-time, xhout is the xhconsole element
  public static Element xhout = Document.get().getElementById("xhconsole");
  
  public static Element xhclipboard = null; //Document.get().getElementById("xhclipboard");
  
  public static final Element xhchart = Document.get().getElementById("xhchart");
  
  public static final Element xhcanvas = Document.get().getElementById("xhcanvas");
  
  public static final Element xhtabs = Document.get().getElementById("xhtabs");
  
  public static final Element xhgraph = Document.get().getElementById("xhgraph");
  public static final Element networkview = Document.get().getElementById("networkview");
  public static final Element treeview = Document.get().getElementById("treeview");
  public static final Element xhtreemap = Document.get().getElementById("xhtreemap");
  
  public static final Element xhanim = Document.get().getElementById("xhanim");
  
  public static final Element xhsvg = Document.get().getElementById("xhsvg");
  
  public static final Element xhimg = Document.get().getElementById("xhimg");
  
  /**
   * Append an HTML subtree as the last child of an existing HTML element.
   * The subtree is wrapped in a div element before appending.
   * @param ele An existing HTML element, typically one of the constants available thru this class.
   * @param htmlStr A properly formatted and trusted HTML string.
   * (ex: "<p>hello</p>")
   */
  public static void appendChild(Element ele, String htmlStr) {
    if (ele == null) {return;}
    if (htmlStr == null) {return;}
    HTMLPanel panel = new HTMLPanel(htmlStr);
    if (panel != null) {
      panel.getElement().setId(HTMLPanel.createUniqueId());
      DOM.appendChild((com.google.gwt.user.client.Element)ele, panel.getElement());
    }
  }
  
  /**
   * Get a comma-delimited list of top-level element names that start with "xh".
   * These are elements that actually exist at this time in this document,
   * which will typically be those defined as constants in this class.
   * A top-level element is defined as a direct child of the body element.
   * @return a comma-delimited list
   *   (ex: "xhtop,xhgui,xhappspecific,xhconsole,xhtabs,xhchart,xhcanvas,xhgraph,xhanim,xhsvg,xhimg").
   */
  public static String getTopLevelElementNames() {
    StringBuilder sb = new StringBuilder();
    Element ele = Document.get().getBody().getFirstChildElement();
    while (ele != null) {
      String id = ele.getId();
      if ("div".equalsIgnoreCase(ele.getNodeName()) && id.startsWith(XH_ELEMENT_PREFIX)) {
        sb.append(id).append(",");
      }
      ele = ele.getNextSiblingElement();
    }
    String str = sb.toString();
    int len = str.length();
    if (len > 0) {
      len--; // remove trailing comma
      str = str.substring(0, len);
    }
    return str;
  }
  
  /**
   * Toggle the display property of an existing element.
   * It toggles between "none" and "block". It doesn't handle "inline".
   * @param elementId The id of an existing Element, typically a div that starts with "xh".
   */
  public static void toggleElementDisplay(String elementId) {
    Element ele = Document.get().getElementById(elementId);
    if (ele == null) {return;}
    String oldValue = ele.getStyle().getDisplay();
    Display newValue = Display.NONE;
    if ("none".equals(oldValue)) {
      newValue = Display.BLOCK;
    }
    ele.getStyle().setDisplay(newValue);
  }
  
}

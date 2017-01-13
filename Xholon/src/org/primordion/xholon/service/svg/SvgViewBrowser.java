/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2013 Ken Webb
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

package org.primordion.xholon.service.svg;

//import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.Text;
//import com.google.gwt.event.dom.client.ContextMenuEvent;
//import com.google.gwt.event.dom.client.DragOverHandler;
//import com.google.gwt.event.dom.client.DragOverEvent;
//import com.google.gwt.event.dom.client.DropHandler;
//import com.google.gwt.event.dom.client.DropEvent;
//import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
//import com.google.gwt.user.client.ui.MenuBar;
//import com.google.gwt.user.client.ui.MenuItem;
//import com.google.gwt.user.client.ui.PopupPanel;
//import com.google.gwt.user.client.ui.RootPanel;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
//import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;

//import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Message;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.io.xml.IXml2Xholon;
import org.primordion.xholon.io.XholonGuiForHtml5;
import org.primordion.xholon.io.xml.Xml2Xholon;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.NodeSelectionService;
import org.primordion.xholon.util.Misc;

/**
 * Provides access to an existing Scalable Vector Graphics (SVG) image.
 * This class does NOT use any third-party SVG Java or JavaScript libraries.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on August 14, 2013)
 */
public class SvgViewBrowser extends Xholon implements ISvgView, EventListener {
  //, DragOverHandler, DropHandler {
  
  private static final String DEFAULT_FONTSIZE = "8px";
  
  private static final String SVG_NAMESPACE = "http://www.w3.org/2000/svg";
  
  /** Maximum RGB color value, used to invert a color. */
  private static final int RGBMAX = 0xffffff;
  
  // I've retained these constants from SVG Salamander
  public static final int AT_CSS = 0;
  public static final int AT_XML = 1;
  public static final int AT_AUTO = 2;  //Check CSS first, then XML
  
  /** XPath to the root viewables node. */
  private IXholon viewablesRoot = null;
  
  /**
   * Model viewables URI.
   * Correspondence between nodes in the model (the viewables),
   * and nodes in the SVG view (.xml) (optional)
   */
  private String viewablesUri = null;
  
  /**
   * The SVG image.
   */
  private JavaScriptObject diagram = null;
  
  /**
   * Textual representation of the currently selected node.
   */
  //private JTextField currentSelectionField = null;
  
  /**
   * Whether or not the SVG image has clickable nodes.
   * If it has clickable nodes, then the JTextField will be displayed.
   */
  private boolean isClickable = true;
  
  /**
   * The width of the Swing JFrame needs to be large enough to contain the SVG diagram,
   * and the border of the JFrame.
   */
  private int frameWidthInc = 11;

  /**
   * The height of the Swing JFrame needs to be large enough to contain the SVG diagram,
   * and the border of the JFrame.
   */
  private int frameHeightInc = 42;
  
  /**
   * The background color of the Swing JPanel.
   * This is useful when the SVG image has a transparent background.
   */
  private int backgroundColor = 0xFFFFFF;
  
  /**
   * It's not clear yet whether this will be useful or not.
   */
  private IXholon nodeSelectionService = null;
  
  /**
   * A local or remote SVG image. ex:
   * <p>"/org/primordion/user/app/climatechange/model04/Sun_climate_system_alternative_(German)_2008.svg"</p>
   * <p>"file:///home/ken/Documents/ClimateChange/images/Sun_climate_system_alternative_(German)_2008.svg"</p>
   * <p>"http://upload.wikimedia.org/wikipedia/commons/d/d2/Sun_climate_system_alternative_(German)_2008.svg"</p>
   */
  private String svgUri = null;
  
  /**
   * An optional fallback local or remote SVG image.
   */
  private String svgUriFallback = null;
  
  /**
   * Used as the title of the JFrame that contains the SVG image.
   */
  private String modelName = null;
  
  /**
   * An optional internationalization XML URI.
   */
  private String i18nUri = null;
  
  /**
   * An optional attributes XML URI. ex:
   * <p>"/org/primordion/user/app/Risk/SvgAttributes.xml"</p>
   */
  protected String svgAttributesUri = null;
  
  /**
   * The default String.format to use when writing text to the SVG image.
   */
  private String format = "%.0f";
  
  /**
   * Should all numeric text be set to 0 at the start?
   */
  private boolean shouldInitNumericText = false;
  
  /**
   * A service that provides additional methods for IXholon instances.
   */
  protected IXholon xholonHelperService = null;
  
  // indexes into the idScheme array
  protected static final int IX_RECT = 0;
  protected static final int IX_CIRCLE = 1;
  protected static final int IX_ELLIPSE = 2;
  protected static final int IX_LINE = 3;
  protected static final int IX_POLYGON = 4;
  protected static final int IX_POLYLINE = 5;
  protected static final int IX_PATH = 6;
  protected static final int IX_TEXT = 7;
  protected static final int IX_TSPAN = 8;
  protected static final int IX_G = 9;
  
  /**
   * Each SVG tool creates id's for new SVG shapes using it's own naming scheme.
   * For example, svg-edit id names all start with "svg", while Inkscape uses "rect", "text", "path".
   * Rect Circle Ellipse Line Polygon Polyline Path Text Tspan
   */
  protected String[] idScheme = {"rect","path","path","path","path","path","path","text","tspan","g"}; // Inkscape
  //protected String[] idScheme = {"svg","svg","svg","svg","svg","svg","svg","svg","svg"}; // svg-edit
  
  /** Indicates whether or not this object has changed. */
  protected boolean hasChanged = false;
  
  /** Mapping between SVGSalamander classes and SVG tag names. */
  final static HashMap class2TagMapping = new HashMap();
  
  /** An instance of SvgClient, or other IXholon node. */
  private IXholon client = null;
  
  /*
   * @see org.primordion.xholon.base.Xholon#setVal(boolean)
   */
  public void setVal(boolean val) {
    hasChanged = val;
  }
  
  public boolean getVal_boolean() {
    return hasChanged;
  }
  
  public Object getVal_Object() {
    return diagram;
  }
  
  /*
   * @see org.primordion.xholon.base.Xholon#act()
   */
  public void act() {
    super.act();
    if (hasChanged) {
      //svgPanel.repaint(); // GWT
      hasChanged = false;
    }
  }
  
  /*
   * @see org.primordion.xholon.base.Xholon#processReceivedMessage(org.primordion.xholon.base.IMessage)
   */
  public void processReceivedMessage(IMessage msg)
  {
    switch (msg.getSignal()) {
    default:
      super.processReceivedMessage(msg);
    }
  }
  
  /*
   * @see org.primordion.xholon.base.Xholon#processReceivedSyncMessage(org.primordion.xholon.base.IMessage)
   */
  public IMessage processReceivedSyncMessage(IMessage msg)
  {
    //System.out.println(msg);
    Object response = null;
    
    switch (msg.getSignal()) {
    case SIG_SETUP_SVGURI_REQ:
      setSvgUri((String)msg.getData());
      break;
    case SIG_SETUP_REQ:
      client = msg.getSender();
      //System.out.println("SvgViewBrowser processReceivedSyncMessage( client: " + client);
      response = setup(msg);
      break;
    case SIG_SETUP_SVGATTRIBUTESURI_REQ:
      setSvgAttributesUri((String)msg.getData());
      break;
    case SIG_DISPLAY_REQ:
      //display();
      break;
    case SIG_ACT_REQ:
      act();
      break;
    case SIG_MAKETEXT_REQ:
      final String[] args = ((String)msg.getData()).split(",");
      if (args.length > 3) {
        makeText(args[0], Double.parseDouble(args[1]), Double.parseDouble(args[2]), args[3]);
      }
      else if (args.length > 2) {
        makeText(args[0], Double.parseDouble(args[1]), Double.parseDouble(args[2]), DEFAULT_FONTSIZE);
      }
      break;
    case SIG_STYLE_REQ:
      style(msg, AT_CSS);
      break;
    case SIG_XMLATTR_REQ:
      style(msg, AT_XML);
      break;
    case SIG_ADD_VIEWBEHAVIOR_REQ:
      addViewBehavior((String)msg.getData());
      break;
    default:
      return super.processReceivedSyncMessage(msg);
    }
    return new Message(SIG_PROCESS_RSP, response, this, msg.getSender());
  }
  
  /**
   * Setup the SVG image, by parsing the SVG parameters, loading the image, and initializing it.
   * @param msg 
   * @return null
   */
  public Object setup(IMessage msg) {
    String setupStr = (String)msg.getData(); // A comma-separated list of parameters.
    if (setupStr == null || setupStr.length() == 0) {return null;}
    final String[] args = setupStr.split(",");
    if (args.length < 2) {return null;}
    if (MODELNAME_DEFAULT.equals(args[0])) {
      setModelName(getApp().getModelName());
    }
    else {
      setModelName(args[0]);
    }
    if (getSvgUri() == null) {
      if (SVGURI_DEFAULT.equals(args[1])) {
        String path = getApp().getJavaXhClassName();
        path = path.substring(0, path.lastIndexOf('.'));
        path = path.replaceAll("\\.", "/");
        setSvgUri("/" + path + "/default.svg");
      }
      else {
        setSvgUri(args[1]);
      }
    }
    if (args.length > 2 && args[2].length() > 0) {
      setSvgUriFallback(args[2]);
    }
    if (args.length > 3) {
      setI18nUri(args[3]);
    }
    if (args.length > 4) {
      setViewablesRoot(getXPath().evaluate(args[4], getApp().getRoot()));
    }
    else {
      setViewablesRoot(getApp().getRoot());
    }
    if (args.length > 5) {
      setViewablesUri(args[5]);
    }
    if (args.length > 6) {
      setFormat(args[6]);
    }
    if (args.length > 7) {
      setShouldInitNumericText(Boolean.parseBoolean(args[7]));
    }
    
    // if the svgUri starts with SVG_DATA_URI then it's an SVG String
    if (getSvgUri().startsWith(SVG_DATA_URI)) {
      String docName = "doc" + new Date().getTime();
      diagram = loadSvg(getSvgUri().substring(SVG_DATA_URI.length()), docName);
      finishSetup();
      client.sendSyncMessage(SIG_STATUS_OK_REQ, null, this);
    }
    else {
      loadSvg(svgUri);
    }
    
    return null;
  }
  
  /**
   * Finish the setup after the SVG diagram has loaded.
   */
  public void finishSetup() {
    // Note: this Element class is NOT the same Element class declared above as an import
    // That Element (com.google.gwt.dom.client.Element) is the superclass of this Element
    com.google.gwt.user.client.Element svg = (com.google.gwt.user.client.Element)diagram.cast();
    if (svg == null) {return;}
    
    // Fix attributes in the SVG image.
    initFromXml(this.getSvgAttributesUri());
    
    // Internationalize the text in the SVG image.
    initFromXml(this.getI18nUri());
    
    if (VIEWABLES_CREATE.equals(this.getViewablesUri())) {
      // Create SvgViewable nodes by scanning the SVG diagram content.
      createSvgViewables(svg); //diagram.getRoot());
    }
    else {
      // Initialize the correspondence between viewable nodes in the model, and SVG nodes.
      initFromXml(this.getViewablesUri());
    }
    
    // enable events
    DOM.sinkEvents(svg, Event.ONCLICK | Event.ONMOUSEDOWN | Event.ONMOUSEUP | Event.ONCONTEXTMENU);
    DOM.sinkBitlessEvent(svg, "dragover");
    DOM.sinkBitlessEvent(svg, "drop");
    DOM.setEventListener(svg, this);
  }
  
  /**
   * Load an SVG image from a URI.
   */
  protected void loadSvg(final String uri) {
    final IXholon this_ = this;
    final IXholon client_ = client;
    try {
      new RequestBuilder(RequestBuilder.GET, uri).sendRequest("", new RequestCallback() {
        @Override
        public void onResponseReceived(Request req, Response resp) {
          if (resp.getStatusCode() == resp.SC_OK) {
            diagram = loadSvg(resp.getText(), uri);
            finishSetup();
            client_.sendSyncMessage(SIG_STATUS_OK_REQ, null, this_);
          }
          else {
            if (uri.equals(svgUri) && (svgUriFallback != null)) {
              loadSvg(svgUriFallback);
            }
            else {
              String xmlString = xmlGwtresource2String(uri);
              if (xmlString == null) {
                client_.sendSyncMessage(SIG_STATUS_NOT_OK_REQ, resp.getText(), this_);
                this_.removeChild(); // remove self from the Xholon tree
              }
              else {
                diagram = loadSvg(xmlString, uri);
                finishSetup();
                client_.sendSyncMessage(SIG_STATUS_OK_REQ, null, this_);
              }
            }
          }
        }

        @Override
        public void onError(Request req, Throwable e) {
          client_.sendSyncMessage(SIG_STATUS_NOT_OK_REQ, e.getMessage(), this_);
          this_.removeChild(); // remove self from the Xholon tree
        }
      });
    } catch(RequestException e) {
      client_.sendSyncMessage(SIG_STATUS_NOT_OK_REQ, e.getMessage(), this_);
      this_.removeChild(); // remove self from the Xholon tree
    }
  }
  
  /**
   * Load an SVG image from a String.
   * @param svgString A string containing valid SVG content.
   * @param docName A name that uniquely identifies the document/image.
   * The id of the HTML element where the SVG content should be inserted.
   */
  protected native JavaScriptObject loadSvg(String svgString, String docName) /*-{
    // create a new div element as a child of "xhsvg" div.
    var div = $doc.getElementById("xhsvg");
    if (div) {
      var newDiv = $doc.createElement("div");
      if (newDiv) {
        newDiv.id = docName;
        div.appendChild(newDiv);
        newDiv.innerHTML = svgString;
        var node = newDiv.firstChild;
        while (node) {
          if (node.nodeName == "svg") {break;}
          node = node.nextSibling;
        }
        return node;
      }
    }
    return null;
  }-*/;
  
  /**
   * Display an already-loaded SVG image.
   */
  //protected void display() {
    /* GWT
    if (diagram == null) {return;}
    createSwingGui(diagram);
    svgPanel.addMouseListener(this);
    //svgPanel.addMouseMotionListener(this);
    if (shouldInitNumericText) {
      initNumericText(diagram.getRoot());
    }
    */
  //}
  
  protected String xmlGwtresource2String(String resourceName) {
    IXml2Xholon xml2Xholon = new Xml2Xholon();
    xml2Xholon.setApp(getApp());
    return xml2Xholon.xmlGwtresource2String(resourceName);
  }
  
  /**
   * Create and initialize a node subtree by reading XML.
   * @param uri The URI of the XML.
   */
  protected void initFromXml(String uri) {
    if (uri == null || uri.length() == 0) {return;}
    if (!uri.startsWith("http")) {
      uri = GWT.getHostPageBaseURL() + uri;
    }
    //System.out.println("SvgViewBrowser initFromXml() " + uri);
    IXml2Xholon xml2Xholon = new Xml2Xholon();
    IApplication app = getApp();
    xml2Xholon.setApp(getApp());
    xml2Xholon.setInheritanceHierarchy(((Application)app).getInherHier());
    xml2Xholon.setTreeNodeFactory(((Application)app).getFactory());
    xml2Xholon.xmlUri2Xholon(uri, this);
    IXholon myLastChild = this.getLastChild();
    //System.out.println("    myLastChild: " + myLastChild);
    if (myLastChild == null) {return;}
    IXholon firstNode = this.findFirstChildWithXhClass(myLastChild.getXhcId());
    //System.out.println("    firstNode: " + firstNode);
    firstNode.postConfigure();
  }
  
  /**
   * Create SvgViewable nodes by scanning the SVG content for tspan and text nodes
   * whose id references a node in the Xholon model.
   * @param svgElement An instance of an SvgElement subclass.
   */
  protected void createSvgViewables(Object svgElement) {
    if (svgElement == null) {return;}
    if (("text".equals(((Element)svgElement).getNodeName()))
        || ("tspan".equals(((Element)svgElement).getNodeName()))) {
      String svgId = ((Element)svgElement).getId();
      if (svgId != null) {
        int beginIndex = svgId.indexOf("[@val"); // ex: [@val] or [@val_char]
        if (beginIndex != -1) {
          int endIndex = svgId.indexOf("]", beginIndex);
          String attrName = svgId.substring(beginIndex+2, endIndex);
          String formatStr = this.getFormat();
          if ("val".equals(attrName)) {
            //this is a double
            formatStr = "%.1f";
          }
          else if ("val_char".equals(attrName)) {
            formatStr = "%c";
          }
          else if ("val_int".equals(attrName)) {
            formatStr = "%d";
          }
          else if ("val_String".equals(attrName)) {
            formatStr = "%s";
          }
          SvgViewable svgViewable = (SvgViewable)this.appendChild("SvgViewable", null);
          svgViewable.setSvgId(((Element)svgElement).getId());
          svgViewable.setSvgNode((Element)svgElement);
          String xhcName = ((Element)svgElement).getId().substring(0, beginIndex);
          // remove the first location in the String; this is the viewables root
          beginIndex = xhcName.indexOf('/');
          if (beginIndex == -1) {
            xhcName = "./"; // this is the viewables root
          }
          else {
            xhcName = xhcName.substring(beginIndex+1);
          }
          svgViewable.setXhcName(xhcName);
          IXholon xhNode = getXPath().evaluate(xhcName, this.getViewablesRoot());
          svgViewable.setXhNode(xhNode);
          svgViewable.setFormat(formatStr);
          svgViewable.postConfigure();
        }
      }
    }
    //System.out.println(((Element)svgElement).getNodeName() + " " + svgElement.getClass().getName());
    Element ele = ((Element)svgElement).getFirstChildElement();
    while (ele != null) {
      createSvgViewables(ele);
      ele = ele.getNextSiblingElement();
    }
  }
  
  /**
	 * Add a new view behavior.
	 * @param viewBehavior An XML string, containing a JavaScript behavior.
	 * This is typically an "SvgViewBrowserbehavior".
	 */
	protected void addViewBehavior(String viewBehavior) {
	  if (viewBehavior.startsWith("<")) {
	    // this is XML content
	    sendXholonHelperService(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, viewBehavior, this);
	  }
	  else {
	    // this is a URI (ex: "config/_commonsvg/stabilityBalls.js")
	    initFromXml(viewBehavior);
	  }
	}
  
  /**
   * Style a CSS attribute, or set an XML attribute, of an SVG node.
   * @param msg data: anId,styleName,styleValue
   * @param attributeType One of the possible AnimationElement.AT_ values.
   */
  protected void style(IMessage msg, int attributeType)
  {
    String styleStr = (String)msg.getData(); // A comma-separated list of parameters.
    if (styleStr == null || styleStr.length() == 0) {return;}
    final String[] args = styleStr.split(",");
    if (args.length < 3) {return;}
    int argIx = 0;
    while (args.length >= argIx + 3) {
      style(args[argIx++], args[argIx++], args[argIx++], attributeType);
    }
  }
  
  /**
   * 
   * @param anId either a Xholon id or an SVG id
   * @param styleName
   * @param styleValue
   * @param attributeType
   */
  protected void style(String anId, String styleName, String styleValue, int attributeType)
  {
    // first look for anId amongst the SvgViewable nodes
    IXholon node = this.getFirstChild();
    while (node != null) {
      if (anId == ((SvgViewable)node).getXhcName()) {
        anId = ((SvgViewable)node).getSvgId();
        break;
      }
      node = node.getNextSibling();
    }
    Element svgElement = DOM.getElementById(anId);
    if (svgElement != null) {
      style(svgElement, styleName, styleValue, attributeType);
    }
  }
  
  /**
   * Style an SVG node.
   * @param svgElement The SVG node that should be restyled.
   * @param styleName The name of the style attribute that will be changed.
   * ex: "fill"
   * @param styleValue The new value for the style attribute.
   * ex: "#ff0000"
   * @param attributeType One of the possible AnimationElement.AT_ values.
   */
  protected void style(Element svgElement, String styleName, String styleValue, int attributeType)
  {
    String val = doFunctions(svgElement, styleName, styleValue, attributeType);
    if (attributeType == AT_CSS) {
      svgElement.getStyle().setProperty(styleName, val);
    }
    else if (attributeType == AT_XML) {
      setAttribute(svgElement, styleName, val);
    }
    else {
      // TODO AT_AUTO ?
    }
  }
  
  /**
   * A styleValue may include a function.
   * @param styleValue 
   * ex: "inc(32)" "dec(32)" "32" "inc(123.45)" "inc(-17.48)"
   */
  protected String doFunctions(Element svgElement, String styleName, String styleValue, int attributeType) {
    if (styleValue.endsWith(")") && styleValue.length() > 5) {
      String oldVal = "";
      if (attributeType == AT_CSS) {
        oldVal = svgElement.getStyle().getProperty(styleName);
      }
      else if (attributeType == AT_XML) {
        oldVal = getAttribute(svgElement, styleName);
      }
      if (styleValue.startsWith("inc(")) {
        String newValue = sum(oldVal, styleValue.substring(4, styleValue.length()-1));
        if (newValue == null) {return styleValue;}
        return newValue;
      }
      else if (styleValue.startsWith("dec(")) {
        String newValue = sum(oldVal, "-" + styleValue.substring(4, styleValue.length()-1));
        if (newValue == null) {return styleValue;}
        return newValue;
      }
      else {
        // the styleValue is an unknown function
        return styleValue;
      }
    }
    else {
      // the styleValue is not a function
      return styleValue;
    }
  }
  
  /**
   * Sum two doubles or ints
   * @param val1 ex: "123.45" "123" "-123.45" "-123"
   */
  protected String sum(String val1, String val2) {
    try {
      double d1 = Double.parseDouble(val1);
      double d2 = Double.parseDouble(val2);
      double d3 = d1 + d2;
      return Double.toString(d3);
    } catch(NumberFormatException e) {
      return null;
    }
  }
  
  /**
   * 
   */
  private native void setAttribute(Element ele, String attr, String val) /*-{
    if (ele) {
      ele.setAttributeNS(null, attr, val);
    }
  }-*/;
  
  /**
   * 
   */
  private native String getAttribute(Element ele, String attr) /*-{
    if (ele) {
      return ele.getAttributeNS(null, attr);
    }
  }-*/;
  
  /**
   * Scale the diagram.
   * @param scale
   */
  protected native void scale(float scale) /*-{
    // SVG Salamander code
    //svgPanel.setScale(scale);
    //refresh();
    //
    
    // plain old JavaScript code TODO
    
  }-*/;
  
  /**
   * Scale the diagram.
   * Note: This doesn't seem to have any effect on what is actually displayed.
   * TODO An image will scale if I manually add a scale() attribute to the top level g.
   *   <pre>&lt;g id="layer1" transform="translate(-120.16872,-87.928404) scale(2.0)"></pre>
   */
  protected void scale(double scaleX, double scaleY)
  {
    /* SVG Salamander code
    Group top = (Group)diagram.getElement("g169");
    if (top == null) {return;}
    //println("g169:" + top);
    try {
      //println("hasAttribute transform:" + top.hasAttribute("transform", AnimationElement.AT_XML));
      Iterator it = top.getPresentationAttributes().iterator();
      while (it.hasNext()) {
        @SuppressWarnings("unused")
        Object attr = it.next();
        //println(attr + " " + attr.getClass());
      }
      //println("hasAttribute scale:" + top.hasAttribute("scale", AnimationElement.AT_XML));
      @SuppressWarnings("unused")
      StyleAttribute styleAttr = top.getPresAbsolute("transform");
      //println(styleAttr.getName() + " " + styleAttr.getStringValue());
      top.setAttribute("transform", AnimationElement.AT_XML, "scale(" + scaleX + "," + scaleY + ")");
      styleAttr = top.getPresAbsolute("transform");
      //println(styleAttr.getName() + " " + styleAttr.getStringValue());
    } catch (SVGElementException e) {
      logger.error("", e);
    }
    */
  }
  
  /**
   * Blank out the values of Tspan elements in a subtree.
   * @param svgElement The root element in the subtree.
   */
  protected void initNumericText(Object svgElement) {
    Element ele = (Element)svgElement;
    if ("tspan".equals(ele.getNodeName())) {
      String text = ele.getNodeValue();
      if (!text.isEmpty() && Misc.isdigit(text.charAt(0) - '0')) {
        setText(ele, "");
      }
    }
    Node node = ele.getFirstChild();
    while (node != null) {
      initNumericText(node);
      node = node.getNextSibling();
    }
  }
  
  /**
   * Set the text of a Tspan node.
   * @param svgNode A Tspan node.
   * @param text Some text.
   */
  protected void setText(Element svgNode, String text) {
    svgNode.getFirstChild().setNodeValue(text);
  }
  
  /**
   * Make a new Text Tspan subtree, and add it to the root of the SVG tree.
   * @param str The contents of the new text.
   * @param x X position of the new text.
   * @param y Y position of the new text.
   * @param fontSize Font size of the new text.
   */
  protected void makeText(String str, double x, double y, String fontSize) {
    // GWT code (innerHTML fails with SVG)
    if (diagram == null) {return;}
    Element svg = (Element)diagram.cast();
    Element text = createElementNS(SVG_NAMESPACE, "text");
    text.setAttribute("x", "" + x);
    text.setAttribute("y", "" + y);
    text.setAttribute("font-size", fontSize);
    Text textNode = createTextNode(str);
    text.appendChild(textNode);
    //System.out.println("SvgViewBrowser makeText: " + text);
    svg.appendChild(text);
  }
  
  private native Element createElementNS(final String ns, final String name)/*-{
    return $doc.createElementNS(ns, name);
  }-*/;
  
  private native Text createTextNode(String val)/*-{
    return $doc.createTextNode(val);
  }-*/;
  
  /*
   * @see org.primordion.xholon.base.Xholon#toString()
   */
  public String toString() {
    return super.toString() + " " + this.getClass().getName() + " " + diagram;
  }
  
  @Override
  public void onBrowserEvent(Event event) {
    System.out.println("Received event: " + event.getType());
    if (event.getTypeInt() == Event.ONMOUSEDOWN) {
      mousePressed(event);
    }
    else if (event.getTypeInt() == Event.ONMOUSEUP) {
      mouseReleased(event);
    }
    else if (event.getTypeInt() == Event.ONCONTEXTMENU) {
      //println("Event.ONCONTEXTMENU" + event);
      handleContextMenuEvent(event);
    }
    else if ("dragover".equals(event.getType())) {
      handleDragOverEvent(event);
    }
    else if ("drop".equals(event.getType())) {
      handleDropEvent(event);
    }
    else {
      consoleLog(event.getType());
      consoleLog(event.getEventTarget());
      consoleLog(event.getDataTransfer());
    }
  }
  
  /*public void onDragOver(DragOverEvent doe) {
    // this is never called
    consoleLog("onDragOver SvgViewBrowser");
    //handleDragOverEvent(doe);
  }*/
  
  /**
   * this is necessary; drop fails to do anything without this ???
   */
  public void handleDragOverEvent(Event doe) {
    //consoleLog("DragOverEvent SvgViewBrowser");
    //consoleLog(doe);  // a MouseEvent, type: "dragover"
    doe.preventDefault();
  }
	
	// drag and drop
	/*public void onDrop(DropEvent de) {
	  // this is never called
  	consoleLog("onDrop SvgViewBrowser");
	  //handleDropEvent(de);
	}*/
	
	/**
	 * This works with Google Chrome.
	 */
  public void handleDropEvent(Event de) {
    // Event is a wrapper for (subclass of) NativeEvent
    consoleLog("DropEvent SvgViewBrowser");
    consoleLog(de); // a MouseEvent, type: "drop"
    consoleLog(de.getDataTransfer());
    String data = de.getDataTransfer().getData("text");
    consoleLog(data);
    consoleLog(de.getEventTarget());
    Element ele = Element.as(de.getEventTarget());
    consoleLog(ele);
    de.stopPropagation();
    de.preventDefault();
    if (data == null) {return;}
    
    // handleDrop; paste data into node
    String svgNodeName = ele.getNodeName();
    String svgElementId = ele.getId();
    consoleLog(svgElementId);
    if (svgElementId == null) {return;}
    if (!svgElementId.startsWith(findIdScheme(svgNodeName))) {
      // the svgElementId is an XPath expression; use it to find the IXholon
      IXholon node = findXholonNode(svgElementId);
      if (node != null) {
        consoleLog(node);
        // assume this is XML content
	      sendXholonHelperService(ISignal.ACTION_PASTE_LASTCHILD_FROMDROP, data, node);
	      // do not refresh the SVG content
      }
    }
  }
  
  protected void handleContextMenuEvent(Event cme) {
    // allow the browser's default context menu
    if (cme.getShiftKey()) {return;}
    
    cme.preventDefault();
    cme.stopPropagation();
    Element ele = Element.as(((NativeEvent) cme).getEventTarget());
    String nodeName = ele.getNodeName();
    String svgElementId = ele.getId();
    if ((svgElementId == null) || ("".equals(svgElementId))) {
      Element parentEle = ele.getParentElement();
      if ("g".equals(parentEle.getNodeName())) {
        // Graphviz creates the id on the SVG "g" parent element
        svgElementId = parentEle.getId();
        if ((svgElementId == null) || ("".equals(svgElementId))) {return;}
      }
      else {
        return;
      }
    }
    if (!svgElementId.startsWith(findIdScheme(nodeName))) {
      IXholon node = findXholonNode(svgElementId);
      if (node != null) {
        int y = ele.getAbsoluteTop();
        new XholonGuiForHtml5().makeContextMenu(node, 0, y);
      }
    }
  }
  
  /**
	 * Show a popup menu for a specified node.
	 * @param node The IXholon that the popup menu is for.
	 */
	/*protected void showPopupMenu(final IXholon node, int x, int y) {
	  PopupPanel popup = new PopupPanel(true);
    popup.hide();
    MenuBar menu = new MenuBar();
    
    // Special
		String[] specialItems = node.getActionList();
		//System.out.println(specialItems);
		if (specialItems != null) {
			MenuBar specialSubMenu = new MenuBar(true);
			for (int i = 0; i < specialItems.length; i++) {
				final String actionName = specialItems[i];
		    specialSubMenu.addItem(specialItems[i], new Command() {
          @Override
          public void execute() {
            node.doAction(actionName);
          }
        });
			}
			menu.addItem("Special", specialSubMenu);
		}
		
		// Edit
		MenuBar editSubMenu = new MenuBar(true);
		editSubMenu.addItem("Copy", new Command() {
			public void execute() {
				sendXholonHelperService(ISignal.ACTION_COPY_TOCLIPBOARD, node, null);
			}
		});
		editSubMenu.addItem("Cut", new Command() {
			public void execute() {
			  sendXholonHelperService(ISignal.ACTION_CUT_TOCLIPBOARD, node, null);
			}
		}).setEnabled(false);
		editSubMenu.addItem("Paste Last Child", new Command() {
			public void execute() {
				sendXholonHelperService(ISignal.ACTION_PASTE_LASTCHILD_FROMCLIPBOARD, node, null);
			}
		});
		menu.addItem("Edit", editSubMenu);
		
		popup.add(menu);
		popup.setPopupPosition(x, y);
    popup.show();
	}*/
  
  protected void mousePressed(Event event) {
    //consoleLog("mousePressed");
    Element ele = Element.as(((NativeEvent) event).getEventTarget());
    //System.out.println("  ONMOUSEDOWN:" + ele.getNodeName() + " " + ele.getId());
    //println("  ONMOUSEDOWN:" + ele.getNodeName() + " " + ele.getId());
    String nodeName = ele.getNodeName();
    String svgElementId = ele.getId();
    String invertAttr = "stroke";
    if (("text".equals(nodeName)) || ("tspan".equals(nodeName))) {
      invertAttr = "fill";
    }
    if (svgElementId == null) {return;}
    if (!svgElementId.startsWith(findIdScheme(nodeName))) {
      invertStyle(ele, invertAttr);
      return;
    }
  }
  
  protected void mouseReleased(Event event) {
    //consoleLog("mouseReleased");
    Element ele = Element.as(((NativeEvent) event).getEventTarget());
    //consoleLog(ele);
    String nodeName = ele.getNodeName();
    
    String svgElementId = ele.getId();
    String invertAttr = "stroke";
    if (("text".equals(nodeName)) || ("tspan".equals(nodeName))) {
      invertAttr = "fill";
    }
    //consoleLog("|" + svgElementId + "|");
    if ((svgElementId == null) || ("".equals(svgElementId))) {
      Element parentEle = ele.getParentElement();
      //consoleLog("svgElementId == null 1");
      //consoleLog(parentEle);
      //consoleLog(parentEle.getNodeName());
      if ("g".equals(parentEle.getNodeName())) {
        // Graphviz creates the id on the SVG "g" parent element
        svgElementId = parentEle.getId();
        //consoleLog(svgElementId);
        if ((svgElementId == null) || ("".equals(svgElementId))) {return;}
      }
      else {
        return;
      }
    }
    if (!svgElementId.startsWith(findIdScheme(nodeName))) {
      //consoleLog("!svgElementId.startsWith(findIdScheme(nodeName))");
      invertStyle(ele, invertAttr);
      IXholon node = findXholonNode(svgElementId);
      if (node != null) {
        if ((event.getButton() & Event.BUTTON_RIGHT) == Event.BUTTON_RIGHT) {
          //sendXholonHelperService(ISignal.ACTION_START_XHOLON_CONSOLE, node, null);
        }
        else {
          rememberNodeSelection(node, event.getCtrlKey());
          //println(node.handleNodeSelection());
          println(this.handleNodeSelection(node, node.getName()));
        }
      }
      return;
    }
  }
  
  public String handleNodeSelection(IXholon node, String nodeName) {
    return getApp().handleNodeSelection(node, nodeName);
  }
  
  protected String findIdScheme(String nodeName) {
    if ("rect".equals(nodeName)) {return idScheme[IX_RECT];}
    if ("path".equals(nodeName)) {return idScheme[IX_PATH];}
    if ("text".equals(nodeName)) {return idScheme[IX_TEXT];}
    if ("tspan".equals(nodeName)) {return idScheme[IX_TSPAN];}
    if ("circle".equals(nodeName)) {return idScheme[IX_CIRCLE];}
    if ("ellipse".equals(nodeName)) {return idScheme[IX_ELLIPSE];}
    if ("line".equals(nodeName)) {return idScheme[IX_LINE];}
    if ("polygon".equals(nodeName)) {return idScheme[IX_POLYGON];}
    if ("polyline".equals(nodeName)) {return idScheme[IX_POLYLINE];}
    if ("g".equals(nodeName)) {return idScheme[IX_G];}
    return "svg";
  }

  /**
   * Remember which IXholon node(s) are currently selected.
   * This will allow services or other xholons to act on those nodes.
   * @param svgElementId ex: World/Europe/Iceland
   * @param isControlDown Whether or not the Control key is pressed down.
   */
  protected void rememberNodeSelection(IXholon node, boolean isControlDown)
  {
    if (node != null) {
      IXholon[] nodeArray = {node};
      if (nodeSelectionService == null) {
        nodeSelectionService = getService(IXholonService.XHSRV_NODE_SELECTION);
      }
      if (nodeSelectionService != null) {
        if (isControlDown) {
          // mouse Ctrl-click
          // append the new selection to what has already been remembered
          nodeSelectionService
            .sendSystemMessage(NodeSelectionService.SIG_APPEND_SELECTED_NODES_REQ, nodeArray, this);
        }
        else {
          // mouse click
          // the new selection should replace what was previously remembered
          nodeSelectionService
            .sendSystemMessage(NodeSelectionService.SIG_REMEMBER_SELECTED_NODES_REQ, nodeArray, this);
        }
      }
    }
  }
  
  /**
   * Find an IXholon node.
   * TODO just use getXPath().evaluate("descendant::" + svgElementId, getApp().getRoot().getParentNode());
   * @param svgElementId
   * @return
   */
  protected IXholon findXholonNode(String svgElementId) {
    /*IXholon node = getXPath().evaluate("/" + svgElementId, viewablesRoot);
    if (node == null) {
      // svgElementId may already include the root node
      node = getXPath().evaluate(svgElementId, viewablesRoot.getParentNode());
    }
    if (node == null) {
      // svgElementId may be a descendant that's not directly a child
      node = getXPath().evaluate("descendant::" + svgElementId, getApp().getRoot());
    }
    return node;*/
    
    IXholon theRootNode = getApp().getRoot();
    IXholon node = getXPath().evaluate("descendant::" + svgElementId, theRootNode);
    if (node == null) {
      if (theRootNode.getXhcName().equals(svgElementId)) {
        node = theRootNode;
      }
    }
    if (node == null) {
      // svgElementId may already include the root node
      node = getXPath().evaluate(svgElementId, viewablesRoot.getParentNode());
    }
    return node;
  }
  
  /**
   * Invert the color of the fill or stroke attribute of a style.
   * @param element
   * @param attrName "fill" or "stroke"
   */
  protected void invertStyle(Element element, String attrName) {
    String str = element.getStyle().getProperty(attrName);
    System.out.println("invertStyle: " + attrName + " str:" + str);
    if ((str.length() > 1)) {
      style(element, attrName, invertColor(str), AT_CSS);
    }
    else {
      str = getAttribute(element, attrName);
      if ((str != null) && (str.length() > 1)) {
        style(element, attrName, invertColor(str), AT_XML);
      }
    }
  }
  
  /**
   * Invert a color.
   * There are 256*256*256 = 16777216 (1000000 hex) possible color values.
   * There are 3 steps to this process:
   * (1) separate the 3 rgb values from rgb or hex format
   * (2) invert the 3 values
   * (3) reassemble into a single hex string
   * @param color An RGB hex color value (ex: #7f7f7f #000000 #ffffff).
   * @return An RGB hex color value (ex: #808080 #ffffff #000000).
   */
  protected String invertColor(String color) {
    if (color.charAt(0) == '#') {
      return reassemble(invert(separateValuesHex(color)));
    }
    else if (color.startsWith("rgb(") && color.endsWith(")")) {
      return reassemble(invert(separateValuesRgb(color)));
    }
    return color;
  }
  
  /*
   * Invert a color, step 1 rgb (ex: "rgb(161, 33, 241)")
   */
  protected int[] separateValuesRgb(String rgb) {
    int[] arr = new int[3];
    String[] vals = rgb.substring(4, rgb.length() - 1 ).split(",");
    arr[0] = Integer.parseInt(vals[0].trim());
    arr[1] = Integer.parseInt(vals[1].trim());
    arr[2] = Integer.parseInt(vals[2].trim());
    return arr;
  }
  
  /*
   * Invert a color, step 1 hex (ex: "#059AF3")
   */
  protected int[] separateValuesHex(String hex) {
    int[] arr = new int[3];
    arr[0] = Integer.parseInt(hex.substring(1,3),16);
    arr[1] = Integer.parseInt(hex.substring(3,5),16);
    arr[2] = Integer.parseInt(hex.substring(5),16);
    return arr;
  }
  
  /*
   * Invert a color, step 2
   */
  protected int[] invert(int[] arr) {
    arr[0] = 255 - arr[0];
    arr[1] = 255 - arr[1];
    arr[2] = 255 - arr[2];
    return arr;
  }
  
  /*
   * Invert a color, step 3
   */
  protected String reassemble(int[] arr) {
    String hex = "#" + (arr[0] < 16 ? "0" : "") + Integer.toHexString(arr[0]) + (arr[1] < 16 ? "0" : "") +
         Integer.toHexString(arr[1]) + (arr[2] < 16 ? "0" : "") + Integer.toHexString(arr[2]);
    return hex;
  }
  
  /**
   * Send a synchronous message to the XholonHelperService.
   * @param signal
   * @param data
   * @param sender
   * @return
   */
  protected IMessage sendXholonHelperService(int signal, Object data, IXholon sender)
  {
    // send the request to the XholonHelperService by sending it a sync message
    if (xholonHelperService == null) {
      xholonHelperService = getApp().getService(IXholonService.XHSRV_XHOLON_HELPER);
    }
    if (xholonHelperService == null) {
      return null;
    }
    else {
      if (sender == null) {sender = getApp();}
      return xholonHelperService.sendSyncMessage(signal, data, sender);
    }
  }
  
  /*
   * @see org.primordion.xholon.base.Xholon#println(java.lang.Object)
   */
  //public void println(Object obj) { // GWT
    //if (currentSelectionField != null) {
    //  currentSelectionField.setText((String)obj);
    //}
    //super.println(obj);
  //}
  
  /** An action. Create and display an XML structure. */
  private static final String makeSvgViewables = "Make Svg viewables";
  
  /** An action. This can be useful while editing the image using Inkscape, svg-edit, etc. */
  private static final String reloadSvgImage = "Reload SVG image";
  
  /** An action. Show the current content in SVG format. */
  private static final String showSvg = "Show SVG";
  
  /** Action list. */
  private String[] actions = {makeSvgViewables, reloadSvgImage, showSvg};
  
  /*
   * @see org.primordion.xholon.base.IXholon#getActionList()
   */
  public String[] getActionList()
  {
    return actions;
  }
  
  /*
   * @see org.primordion.xholon.base.Xholon#setActionList(java.lang.String[])
   */
  public void setActionList(String[] actionList)
  {
    actions = actionList;
  }
  
  /*
   * @see org.primordion.xholon.base.IXholon#doAction(java.lang.String)
   */
  public void doAction(String action)
  {
    System.out.println("ACTION: " + action);
    if (action == null) {return;}
    if (action.equals(makeSvgViewables)) {
      StringBuilder sb = new StringBuilder();
      writeSvgViewables(sb);
      System.out.println(sb.toString());
      println(sb.toString());
    }
    else if (action.equals(reloadSvgImage)) {
      System.out.println(action + " not implemented");
      //reloadSvgImage();
    }
    else if (action.equals(showSvg)) {
      showSvg();
    }
  }
  
  /**
   * Show the current content in SVG format.
   * @param out The stream to write the XML to.
   */
  /* SVG Salamander code
  protected void showSvg(PrintStream out) {
    out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    showSvgRecurse(diagram.getRoot(), out);
  }*/
  protected void showSvg() {
    System.out.println("showSvg");
    System.out.println(diagram);
    println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    println(diagram.toString());
  }
  
  /**
   * Show the SVG content, by recursively working through the internal structure.
   * @param svgElement An element that may be a Tspan or Text.
   * @param out The stream to write the XML to.
   */
  /* GWT
  @SuppressWarnings("unchecked")
  protected void showSvgRecurse(SVGElement svgElement, PrintStream out) {
    String tagName = getSvgTagName(svgElement);
    if (tagName == null) {return;}
    StringBuilder sb = new StringBuilder().append("<").append(tagName);
    
    // Non-style attributes (ex: cx="30" cy="80" rx="10" ry="20" style="...").
    // These are also called "presentation attributes", and "AT_XML".
    //
    Iterator attrIt = svgElement.getPresentationAttributes().iterator();
    while (attrIt.hasNext()) {
      String attrKey = (String)attrIt.next();
      StyleAttribute attr = svgElement.getPresAbsolute(attrKey);
      sb.append(" ").append(attr.getName()).append("=\"").append(attr.getStringValue()).append("\"");
    }
    if ("svg".equals(tagName)) {
      sb.append(" xmlns=\"").append(SVGElement.SVG_NS).append("\""); // xmlns="http://www.w3.org/2000/svg"
      sb.append(" xmlns:xlink=\"http://www.w3.org/1999/xlink\"");
      sb.append(" xmlns:sodipodi=\"http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd\"");
      sb.append(" xmlns:inkscape=\"http://www.inkscape.org/namespaces/inkscape\"");
    }
    out.print(sb.append(">"));
    if ("text".equals(tagName)) { // content consists of Tspan and String
      List textList = ((Text)svgElement).getContent();
      for (int i = 0; i < textList.size(); i++) {
        Object obj = textList.get(i);
        if (obj instanceof String) {
          out.print(normalizeXmlContent((String)obj));
          out.print("\n");
        }
        else {
          out.print("\n");
          showSvgRecurse((SVGElement)obj, out);
        }
        // TODO does Text have children that are not part of the content ? Text children ?
      }
      out.print(new StringBuilder().append("</").append(tagName).append(">\n"));
      return;
    }
    else if ("tspan".equals(tagName)) {
      out.print(normalizeXmlContent(((Tspan)svgElement).getText()));
    }
    out.print("\n");
    Iterator childIt = svgElement.getChildren(null).iterator(); // handle children
    while (childIt.hasNext()) {
      showSvgRecurse((SVGElement)childIt.next(), out);
    }
    out.print(new StringBuilder().append("</").append(tagName).append(">\n"));
  }*/
  
  /**
   * Reload the SVG image.
   * It's assumed that each child SvgViewable node still has a valid svgId.
   * The only editing that should be done to an image that will be reloaded,
   * is moving existing SVG nodes, changing their attributes and styles,
   * adding new elements that are unrelated to the Xholon model.
   * Do Not:
   *  - ungroup and regroup clickable/viewable elements (go inside existing groups instead)
   *  - 
   */
  /* GWT
  protected void reloadSvgImage() {
    try {
      internalDocUri = SVGCache.getSVGUniverse().loadSVG(internalDocUri.toURL(), true);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    diagram = SVGCache.getSVGUniverse().getDiagram(internalDocUri, false);
    svgPanel.setDiagram(diagram);
    // invoke all the SvgViewables so they can recreate their svgNode attributes
    IXholon node = this.getFirstChild();
    if (node != null) {
      node.reconfigure();
    }
    svgPanel.repaint(); // is this required ?
  }*/
  
  /**
   * Write potential SVG viewables to a StringBuilder, in XML format.
   * This can be edited, saved in an XML file (SvgViewables.xml),
   * and used as the input to the SvgViewable class.
   * It only writes Tspan nodes, and Text nodes that directly contain some text.
   * @param sb The StringBuilder to write the XML to.
   */
  protected void writeSvgViewables(StringBuilder sb) {
    if (diagram == null) {return;}
    Element svg = (Element)diagram.cast();
    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
    .append("<_-.SvgViewables>\n");
    writeSvgViewablesRecurse(svg, sb);
    sb.append("")
    .append("</_-.SvgViewables>\n");
  }
  
  /**
   * Recursively write SVG viewables to a StringBuilder. ex:
   * <p>  &lt;SvgViewable xhcName="some text" svgId="tspan3463"/></p>
   * @param svgElement An element that may be a Tspan or Text.
   * @param out The StringBuilder to write the XML to.
   */
  protected void writeSvgViewablesRecurse(Element ele, StringBuilder sb) {
    if ("tspan".equals(ele.getNodeName())) {
      sb.append("  <SvgViewable xhcName=\"" + ele.getFirstChild().getNodeValue().trim()
          + "\" svgId=\"" + ele.getId().trim() + "\"/>\n");
    }
    else if ("text".equals(ele.getNodeName())) {
      // look for TextNode nodes that are direct children of the SVG text node
      String text = "";
      Node node = ele.getFirstChild();
      while (node != null) {
        if (node.getNodeType() == Node.TEXT_NODE) {
          text += node.getNodeValue();
        }
        node = node.getNextSibling();
      }
      text = text.trim();
      if (text.length() > 0) {
        sb.append("  <SvgViewable xhcName=\"" + text
          + "\" svgId=\"" + ele.getId().trim() + "\"/>\n");
      }
    }
    Element node = (Element)ele.getFirstChildElement();
    while (node != null) {
      writeSvgViewablesRecurse(node, sb);
      node = (Element)node.getNextSiblingElement();
    }
  }
  
  /**
   * Normalize the content of the input String.
   * XML content may not contain the
   * &lt; or &amp; characters.
   * @param content The content that will appear between an XML start and end tag.
   * @return Valid XML content.
   */
  protected String normalizeXmlContent(String content)
  {
    String newContent;
    newContent = content.replaceAll("&", "&amp;");
    newContent = newContent.replaceAll("<", "&lt;");
    newContent = newContent.replaceAll(">", "&gt;");
    return newContent;
  }
  
  public IXholon getViewablesRoot() {
    return viewablesRoot;
  }

  public void setViewablesRoot(IXholon viewablesRoot) {
    this.viewablesRoot = viewablesRoot;
  }

  public String getViewablesUri() {
    return viewablesUri;
  }

  public void setViewablesUri(String viewablesUri) {
    this.viewablesUri = viewablesUri;
  }

  public String getSvgUriFallback() {
    return svgUriFallback;
  }

  public void setSvgUriFallback(String svgUri) {
    if (svgUri == null || svgUri.length() == 0) {return;}
    if (svgUri.charAt(0) == '/') {
      //this.svgUriFallback = "file://" + new java.io.File("bin" + svgUri).getAbsolutePath(); // GWT
    }
    else {
      this.svgUriFallback = svgUri;
    }
  }

  public String getSvgUri() {
    return svgUri;
  }

  public void setSvgUri(String svgUri) {
    if (svgUri == null || svgUri.length() == 0) {return;}
    if (svgUri.charAt(0) == '/') {
      //this.svgUri = "file://" + new java.io.File("bin" + svgUri).getAbsolutePath();
    }
    else {
      this.svgUri = svgUri;
    }
  }

  public String getSvgAttributesUri() {
    return svgAttributesUri;
  }

  public void setSvgAttributesUri(String svgAttributesUri) {
    this.svgAttributesUri = svgAttributesUri;
  }

  public String getModelName() {
    return modelName;
  }

  public void setModelName(String modelName) {
    this.modelName = modelName;
  }

  public String getI18nUri() {
    return i18nUri;
  }

  public void setI18nUri(String i18nUri) {
    if (i18nUri == null || i18nUri.length() == 0) {return;}
    this.i18nUri = i18nUri;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public boolean isShouldInitNumericText() {
    return shouldInitNumericText;
  }

  public void setShouldInitNumericText(boolean shouldInitNumericText) {
    this.shouldInitNumericText = shouldInitNumericText;
  }

  public JavaScriptObject getDiagram() {
    return diagram;
  }

  public void setDiagram(JavaScriptObject diagram) {
    this.diagram = diagram;
  }

}

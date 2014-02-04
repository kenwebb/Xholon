/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2008 Ken Webb
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

package org.primordion.xholon.io;

//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.StringWriter;
//import java.io.Writer;
//import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
//import java.util.Properties;

import org.primordion.xholon.util.XholonSortedNode;
//import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.IMessage;
//import org.primordion.xholon.base.IPort;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXPath;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Port;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.xholon.service.AbstractXholonService;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.NodeSelectionService;
import org.primordion.xholon.service.svg.ISvgView;
//import org.primordion.xholon.util.MiscIo;
import org.primordion.xholon.util.ClassHelper;
import org.primordion.xholon.util.StringHelper;
import org.primordion.xholon.util.StringTokenizer;

/**
 * Output a Xholon composite structure in SVG format.
 * The resulting file can be read by Inkscape and other SVG tools.
 * The file can be imported by other tools that understand SVG such as Visio.
 * <p>See the various profiles in config/Xholon2Svg.</p>
 * <p>This class can be pasted in at runtime, for example:</p>
<pre>&lt;Xholon2Svg/></pre>
<p>or</p>
<pre>
&lt;Xholon2Svg>
  &lt;Attribute_String roleName="strokeColor">#000000&lt;/Attribute_String>
  &lt;Attribute_String roleName="fontFamily">Arial&lt;/Attribute_String>
  &lt;Attribute_String roleName="shapeFillColor">#afc6e9&lt;/Attribute_String>
  &lt;Attribute_String roleName="shapeFillColorAlternate">#beb7c8&lt;/Attribute_String>
  &lt;Attribute_int roleName="levelTranslateX">50&lt;/Attribute_int>
  &lt;Attribute_int roleName="levelTranslateY">100&lt;/Attribute_int>
  &lt;Attribute_int roleName="siblingTranslateX">10&lt;/Attribute_int>
  &lt;Attribute_int roleName="siblingTranslateY">5&lt;/Attribute_int>
  &lt;!-- possible values for svgClientOption: null help run copy -->
  &lt;Attribute_String roleName="svgClientOption">run&lt;/Attribute_String>
  &lt;!-- create the SVG as a String rather than as a File -->
  &lt;Attribute_String roleName="svgFileName">data:image/svg+xml,&lt;/Attribute_String>
  &lt;Attribute_String roleName="writeValType">double&lt;/Attribute_String>
&lt;/Xholon2Svg>
</pre>

<p>Ports</p>
Two additional attributes can be used to specify ports or arrow heads (start and end of a connector line).
(Note that rect MUST be pasted in using & and NOT as the less-than symbol):
<pre>
  &lt;Attribute_String roleName="portTemplate">&lt;rect id="%s" x="%f" y="%f" width="3" height="3" fill="#ff0000" stroke="#0000ff" stroke-width="1px"/>&lt;/Attribute_String>
  &lt;Attribute_String roleName="portConjugatedTemplate">&lt;rect id="%s" x="%f" y="%f" width="3" height="3" fill="#0000ff" stroke="#e0e0f8" stroke-width="1px"/>&lt;/Attribute_String>
</pre>
or as a triangle:
<pre>
  &lt;Attribute_String roleName="portTemplate">&lt;polygon id="%s" points="0,0 0,6 6,3" transform="translate(%f,%f)" width="6" height="6" fill="#e0e0f8" stroke="#0000ff" stroke-width="1px"/>&lt;/Attribute_String>
</pre>

These attributes control the creation of ports and connectors between ports:
<pre>
  &lt;Attribute_boolean roleName="shouldShowPorts">false&lt;/Attribute_boolean>
  &lt;Attribute_boolean roleName="shouldShowConnectors">false&lt;/Attribute_boolean>
  &lt;Attribute_boolean roleName="useInkscape">false&lt;/Attribute_boolean>
  &lt;Attribute_boolean roleName="shouldCommentConnectors">false&lt;/Attribute_boolean>
</pre>

 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.7.1 (Created on July 8, 2008)
 * TODO make use of "defs" and "use" to define both types of port ???
 * TODO have an option to flatten the SVG group hierarchy
 *      - only write out leaf nodes, each in its own group
 *      - boolean shouldFlatten
 * TODO ports crash (NullPointerException ?) when a partial subtree excludes the other end of the port 
 */
public class Xholon2Svg extends Xholon implements IXholon2Gui {
  
  // General Properties
  
  /** A local singleton instance of XPath. */
  protected IXPath xPathLocal = null;
  
  /** The name of the file to output. */
  protected String svgFileName;
  
  /** The name of the Xholon model. */
  protected String modelName;
  
  /** The root node in the generated GUI. */
  protected IXholon root;
  
  /** The instance of Writer that writes to svgFileName. */
  //protected Writer svgOut;
  
  /**
   * The instance of StringBuilder that builds a String
   * that can be written to svgFileName or other target.
   */
  protected StringBuilder svgSb;
  
  /** The path where the guiFileName is located. */
  protected String svgPathName = "./gui/";
  
  /** Current date and time. */
  protected Date timeNow;
  
  /** The number of milliseconds since Jan 1, 1970. Same value as timeNow. */
  protected long timeStamp;
  
  /** ID of the root svg element. */
  protected String svgId;
  
  /** Whether or not to show state machines. */
  protected boolean showStates = false;
  
  /** */
  protected String nameTemplate = "r:C^^^";
  protected String nameTemplate4Ports = "^^c_i^"; //"^^c^^^";
  
  // SVG Properties
  
  /** Font family (default: "Verdana"). */
  // Windows: "Verdana", "Courier New", "Comic Sans MS"
  // Linux:   ""Bitstream Vera Sans", "Bitstream Vera Sans Mono"
  protected String fontFamily = "\"Courier New\",courier,monospace"; //"Verdana";
  
  /** Font size (default: 8.0f). */
  protected float fontSize = 8.0f;
  
  /** Font size increment (default: 1.0f). */
  protected float fontSizeInc = 1.0f;
  
  /** Font color (default: "black"). */
  protected String fontColor = "black";
  
  /** Stroke width in px (default: 1.0f). */
  protected float strokeWidth = 1.0f;
  
  /** Stroke width in px (default: 0.05f). */
  protected float strokeWidthInc = 0.05f;
  
  /** Stroke color (default: "#a121f1"). */
  protected String strokeColor = "#a121f1"; // purplish
  
  /** Shape fill color (default: "white"). */
  protected String shapeFillColor = "white";
  
  /** Alternate shape fill color (default: ""). */
  protected String shapeFillColorAlternate = "#efffdf"; // light greenish
  
  protected static final float ROUNDED_RECT_NULL = -1.0f;
  
  /** Optional rx value on rect, for a rounded rectangle. */
  protected float shapeRx = ROUNDED_RECT_NULL;
  
  /** Optional ry value on rect, for a rounded rectangle. */
  protected float shapeRy = ROUNDED_RECT_NULL;
  
  // width and height are for 11 x 8.5 inch paper (landscape)
  /** Page width in px (default: 990.0f). */
  protected float pageWidth = 990.0f;
  /** Page width in px (default: 765.0f). */
  protected float pageHeight = 765.0f;
  
  // Properties of individual SVG elements
  //Properties svgProperties = null;
  
  // ports
  //protected String portStrokeColor = "#0000ff"; // dark blue
  //protected String portFillColor = "#e0e0f8"; // light blue
  protected boolean shouldShowPorts = true;
  // ports as rectangles
  //protected String portTemplate = "<rect id=\"%s\" x=\"%f\" y=\"%f\" width=\"6\" height=\"6\" fill=\"#e0e0f8\" stroke=\"#0000ff\" stroke-width=\"2px\"/>";
  //protected String portConjugatedTemplate = "<rect id=\"%s\" x=\"%f\" y=\"%f\" width=\"6\" height=\"6\" fill=\"#0000ff\" stroke=\"#e0e0f8\" stroke-width=\"2px\"/>";
  // ports as circle (rounded rectangle) and triangle
  protected String portTemplate = "<rect id=\"%s\" x=\"%f\" y=\"%f\" ry=\"2.5\" width=\"6\" height=\"6\" fill=\"#0000ff\" stroke=\"#e0e0f8\" stroke-width=\"1px\"/>";
  protected String portConjugatedTemplate="<polygon id=\"%s\" points=\"0,0 0,6 6,3\" transform=\"translate(%f,%f)\" width=\"6\" height=\"6\" fill=\"#e0e0f8\" stroke=\"#0000ff\" stroke-width=\"1px\"/>";
  protected float portCenterX = 0.0f; //3.0f;
  protected float portCenterY = 0.0f; //3.0f;
  
  // Connector lines
  protected String connectorStrokeColor = "black";
  protected float connectorStrokeWidth = 1.0f;
  protected boolean shouldShowConnectors = true;
  
  /**
   * Whether or not to write connectors using Inkscape-specific syntax.
   */
  protected boolean useInkscape = true;
  
  /**
   * Should all the connector lines be commented out?
   * This is useful when using the Inkscape-specific syntax,
   * because Inkscape often looses the connector lines when the image is being edited.
   * Steps:
   * <ol>
   * <li>Use Xholon2Svg to write an SVG image,
   *     with shouldShowConnectors useInkscape shouldCommentConnectors set to true.</li>
   * <li>Edit the image using Inkscape or other tools.
   *     Make sure to retain the ports that the connector lines connect to.
   *     Save it in a way that preserves the commented-out content.</li>
   * <li>Use a text editor to remove the comments around the connector lines.</li>
   * <li>Reload the image back into Inkscape.
   *     Inkscape will automatically correct the positions of the connector lines.</li>
   * </ol>
   */
  protected boolean shouldCommentConnectors = true;
  
  // other options
  
  /**
   * Should flatten the composite structure hierarchy?
   * If this option is chosen, then only a root node
   * and its descendant leaf nodes are included in the generated SVG.
   */
  protected boolean shouldFlatten = false;
  
  /**
   * Should take selected nodes only?
   * If this option is chosen, then only a root node
   * and any descendant nodes that are available from the Node Selection Service,
   * are included in the generated SVG.
   */
  protected boolean selectedNodesOnly = false;
  
  /**
   * Whether to write a stylesheet (true), or inline XML attributes.
   */
  protected boolean shouldWriteStylesheet = true;
  
  /**
   * Root node in a tree of counts of how many times each IXholon is referenced by others.
   * This is used to construct the counted number of conjugated/end ports.
   */
  protected XholonSortedNode reffedRoot = null;
  
  /**
   * A collection of all non-conjugated/start ports.
   * This is used to generate SVG connectors.
   * ex: rtwo_12.port[1],19.0,34.0,cO2_5  .conjport[0]
   * ex: hello_1.port[0],17.0,34.0,world_2  .conjport[0]
   */
  protected List svgStartPort;
  
  /**
   * A collection of all conjugated/end ports.
   * This is used to generate SVG connectors.
   * ex: cO2_5.conjport[0]=23.0,44.0
   */
  protected Map svgEndPort;
  
  /** Whether or not reffedRoot has been initialized. */
  protected boolean rootInitialized = false;
  
  /** How far a new group (container) is translated in the x direction.
   * How far a new level in the containment hierarchy is translated in the x direction.
   */
  protected int levelTranslateX = 20;
  
  /** How far a new group (container) is translated in the y direction.
   * How far a new level in the containment hierarchy is translated in the x direction.
   */
  protected int levelTranslateY = 20;
  
  /** How far a new sibling node is translated in the x direction. */
  protected int siblingTranslateX = 2;
  
  /** How far a new sibling node is translated in the y direction. */
  protected int siblingTranslateY = 0;
  
  /** Do nothing. */
  protected static final String SVGCLIENT_NULL = "null";
  /** Provide the user with some help information, including an XML subtree that can be pasted into the app. */
  protected static final String SVGCLIENT_HELP = "help";
  /** Copy the XML subtree to the clipboard, so it can be manually pasted into the app. */
  protected static final String SVGCLIENT_COPY = "copy";
  /**
   * Run an instance of SvgClient, and display the generated SVG image.
   * This is done by pasting in the XML subtree provided by the "help" option.
   */
  protected static final String SVGCLIENT_RUN = "run";
  /**
   * SvgClient is a separate class that can display the generated SVG.
   * Possible options: "null", "help", "run", or "copy".
   */
  protected String svgClientOption = SVGCLIENT_RUN; //SVGCLIENT_HELP; // GWT
  
  /**
   * What data type to write for values maintained by passive objects, if any.
   * ex: "double" "int" etc.
   */
  protected String writeValType = null; // double int etc.
  
  /**
   * Output all nodes with an ID, starting sequentially with this value.
   */
  protected int nextSvgId = 1;
  
  /** Use for pretty-printing the output. */
  private String indent = "                              ";

  /**
   * This method will only be called if an instance of this class is pasted in.
   * It will not be called if an instance is created by XholonGui.
   * @see org.primordion.xholon.base.Xholon#postConfigure()
   */
  public void postConfigure() {
    if (this.hasChildNodes()) {
      this.getFirstChild().postConfigure();
    }
    initialize(null, getApp().getModelName(), this.getParentNode());
    writeAll();
    this.removeChild();
  }
  
  /*
   * @see org.primordion.xholon.io.IXholon2Gui#initialize(java.lang.Object, java.lang.String, org.primordion.xholon.base.IXholon)
   */
  public boolean initialize(Object out, String modelName, IXholon root) {
    timeNow = new Date();
    timeStamp = timeNow.getTime();
    svgId = "svg" + timeStamp;
    /* GWT
    if (this.svgFileName == null) {
      if (out == null) {
        this.svgFileName = svgPathName + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".svg";
      }
      else {
        this.svgFileName = (String)out;
      }
    }*/
    this.svgFileName = ISvgView.SVG_DATA_URI; // GWT
    this.modelName = modelName;
    this.root = root;
    setXPathLocal((IXPath)root.getService(AbstractXholonService.XHSRV_XPATH));
    return true;
  }
  
  /**
   * Populate the tree that contains counts of how many times each IXholon node is referenced.
   * by other IXholon nodes.
   */
  protected void populateReffedTree()
  {
    reffedRoot = new XholonSortedNode();
    root.visit(this);
  }
  
  /**
   * @see org.primordion.xholon.base.IXholon#visit(org.primordion.xholon.base.IXholon)
   * @param visitee The node in the tree that is currently being visited.
   */
  public boolean visit(IXholon visitee)
  {
    populateReffedTree(visitee);
    return true;
  }
  
  /**
   * Populate the tree with one node.
   * @param node
   */
  protected void populateReffedTree(IXholon node)
  {
    if (!showStates && isStateMachineEntity(node)) {return;}
    List portList = node.getAllPorts();
    for (int j = 0; j < portList.size(); j++) {
      PortInformation portInfo = (PortInformation)portList.get(j);
      IXholon reffedNode = portInfo.getReffedNode();
      if (reffedNode != null) {
        if (ClassHelper.isAssignableFrom(Port.class, reffedNode.getClass())) {
          Port iport = (Port)reffedNode;
          IXholon remote = iport.getLink();
          if (remote != null) {
            if (ClassHelper.isAssignableFrom(Port.class, remote.getClass())) {
              remote = remote.getParentNode().getParentNode();
            }
            if (!rootInitialized) {
              reffedRoot.setVal((Object)remote);
              rootInitialized = true;
            }
            else {
              reffedRoot.add(remote);
            }
          }
        }
        else if (!rootInitialized) {
          reffedRoot.setVal((Object)reffedNode);
          rootInitialized = true;
        }
        else {
          reffedRoot.add(reffedNode);
        }
      }
    }
  }
  
  /*
   * @see org.primordion.xholon.io.IXholon2Gui#setParams(java.lang.String)
   */
  public void setParams(String params) {
    
  }
  
  /*
   * @see org.primordion.xholon.io.IXholon2Gui#writeAll()
   */
  public void writeAll()
  {
    //boolean shouldClose = true;
    if (shouldShowConnectors) {
      svgStartPort = new ArrayList();
      svgEndPort = new HashMap();
    }
    populateReffedTree();
    initializeSvgProperties();
    if (ISvgView.SVG_DATA_URI.equals(svgFileName)) {
      //svgOut = new StringWriter();
      svgSb = new StringBuilder(1000);
    }
    else {
      if (root.getApp().isUseAppOut()) {
        svgFileName = null;
        //svgOut = root.getApp().getOut();
        svgSb = new StringBuilder(1000);
        //shouldClose = false;
      }
      else {
        //try {
          // create any missing output directories
          //File dirOut = new File(svgPathName);
          //dirOut.mkdirs(); // will create a new directory only if there is no existing one
          //svgOut = MiscIo.openOutputFile(svgFileName);
          svgSb = new StringBuilder(1000);
        //} catch(AccessControlException e) {
          //out = new PrintWriter(System.out);
          //svgOut = root.getApp().getOut();
          //svgSb = new StringBuilder(1000);
          //shouldClose = false;
        //}
      }
    }
    //try {
      // write file header
      svgSb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
      svgSb.append("<!--<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">-->\n");
      svgSb.append(
        "<!--\nAutomatically generated by Xholon version 0.8.1, using "
        + this.getClass().getName() + ".java\n"
        + new Date() + " " + timeStamp + "\n"
        + "model: " + modelName + "\n"
        + "www.primordion.com/Xholon\n-->\n");
      svgSb.append("<svg id=\"")
      .append(svgId)
      .append("\"")
      .append(" xmlns=\"http://www.w3.org/2000/svg\"")
      .append(" xmlns:xlink=\"http://www.w3.org/1999/xlink\"");
      if (useInkscape) {
        svgSb.append(" xmlns:sodipodi=\"http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd\""
          + " xmlns:inkscape=\"http://www.inkscape.org/namespaces/inkscape\"");
      }
      svgSb.append(" version=\"1.1\" width=\"" + pageWidth
          + "\" height=\"" + pageHeight + "\">\n");
      
      // optionally write a CSS stylesheet
      writeStylesheet();
      
      // write a title for the <svg> node
      svgSb.append(" <title>");
      if (root.hasAnnotation()) {
        svgSb.append(root.getAnnotation());
      }
      else {
        svgSb.append(root.getApp().getModelName());
      }
      svgSb.append("</title>\n");
      
      // group that contains all other groups, so entire diagram can be easily transformed
      svgSb.append(" <g id=\"toplevelgroup\" transform=\"translate(0,0) scale(1) rotate(0)\">\n");
      writeAllContour();
      writeConnectors();
      svgSb.append(" </g>\n");
      //writePort(20.0f, 30.0f, true, null);
      //writePort(20.0f, 30.0f, false, null);
      //writeConnector();
      svgSb.append("</svg>\n");
      //svgOut.write(svgSb.toString());
      //svgOut.flush();
    //} catch (IOException e) {
    //  Xholon.getLogger().error("writeAll()", e);
    //}
    //if (shouldClose) {
      //MiscIo.closeOutputFile(svgOut);
    //}
    doSvgClient();
  }
  
  /**
   * Do whatever option was selected to handle SvgClient.
   */
  protected void doSvgClient() {
    if (SVGCLIENT_HELP.equals(svgClientOption)) {
      showSvgClientHelp();
    }
    else if (SVGCLIENT_RUN.equals(svgClientOption)) {
      runSvgClient();
    }
    else if (SVGCLIENT_COPY.equals(svgClientOption)) {
      copySvgClient();
    }
  }
  
  /**
   * Run an instance of SvgClient, to display the generated SVG image.
   */
  protected void runSvgClient() {
    String xmlString = makeSvgClientXml();
    //root.println(xmlString);
    XholonGwtTabPanelHelper.addTab(xmlString, "svg", this.svgFileName, false);
    IXholon xholonHelperService = root.getApp().getService(IXholonService.XHSRV_XHOLON_HELPER);
    xholonHelperService.sendSyncMessage(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING,
        xmlString, root.getApp().getView());
  }
  
  /**
   * Copy the SvgClient XML subtree to the clipboard.
   */
  protected void copySvgClient() {
    String xmlString = makeSvgClientXml();
    IXholon xholonHelperService = root.getApp().getService(IXholonService.XHSRV_XHOLON_HELPER);
    xholonHelperService.sendSyncMessage(ISignal.ACTION_COPY_TOCLIPBOARD, xmlString, this);
  }
  
  /**
   * Help the user to use the generated SVG image.
   */
  protected void showSvgClientHelp()
  {
    if (svgFileName == null) {return;}
    StringBuilder sb = new StringBuilder()
    .append("To preview the raw generated SVG image:\n")
    .append(" * select and copy the following XML text,\n")
    .append(" * right-click the View node,\n") // (")
    .append(" * and select Edit --> Paste Last Child.\n")
    .append("Normally you will want to edit the image before making use of it.\n\n")
    .append(makeSvgClientXml())
    .append("\n");
    //System.out.println(sb);
    root.println(sb.toString());
    //showSvgImage(sb.toString());
  }
  
  /**
   * Show the SVG as a viewable image in the browser page.
   */
  protected native void showSvgImage(String text) /*-{
    var element = $doc.getElementById("xhsvg");
    if (element) {
      element.innerHTML = text;
    }
  }-*/;
  
  /**
   * Make the XML required to paste in a generated SVG image.
   * @return An XML subtree.
   */
  protected String makeSvgClientXml() {
    String viewablesRoot = this.getXPath().getExpression(root, root.getRootNode(), false);
    if (viewablesRoot.length() == 0) {
      viewablesRoot = "./";
    }
    else {
      int ix = viewablesRoot.indexOf('/');
      if (ix == -1) {
        // variablesRoot is the actual model root
        viewablesRoot = "./";
      }
      else {
        viewablesRoot = viewablesRoot.substring(ix+1);
      }
    }
    
    StringBuilder sb = new StringBuilder().append("<SvgClient>\n");
    if (ISvgView.SVG_DATA_URI.equals(svgFileName)) {
      sb.append(" <Attribute_String roleName=\"svgUri\">")
      .append("<![CDATA[")
      .append(ISvgView.SVG_DATA_URI)
      //.append(svgOut.toString())
      .append(svgSb.toString())
      .append("]]>")
      .append("</Attribute_String>\n");
      sb.append(" <Attribute_String roleName=\"setup\">${MODELNAME_DEFAULT},${SVGURI_DEFAULT}")
      .append(",,,").append(viewablesRoot).append(",${VIEWABLES_CREATE}")
      .append("</Attribute_String>\n");
    }
    else {
      /*sb.append("  <Attribute_String roleName=\"setup\">${MODELNAME_DEFAULT},")
      .append("file://")
      .append(new java.io.File(svgFileName.substring(2)).getAbsolutePath())
      .append(",,,").append(viewablesRoot).append(",${VIEWABLES_CREATE}")
      .append("</Attribute_String>\n");*/
    }
    return sb.append("</SvgClient>").toString();
  }
  
  /**
   * Write all nodes in a contour format.
   */
  protected void writeAllContour()
  {
    if (selectedNodesOnly) {
      writeSelectedNodesOnly();
    }
    else {
      writeContourNode(root, 0, 0); // root is level 0, siblingIndex 0
    }
  }
  
  /**
   * Only write the nodes that are available from the Node Selection Service.
   */
  protected void writeSelectedNodesOnly() {
    IXholon[] selectedNodes = null;
    IXholon service = getService(IXholonService.XHSRV_NODE_SELECTION);
    if (service != null) {
      IMessage nodesMsg = service.sendSyncMessage(
          NodeSelectionService.SIG_GET_SELECTED_NODES_REQ, null, this);
      selectedNodes = (IXholon[])nodesMsg.getData();
      for (int i = 0; i < selectedNodes.length; i++) {
        IXholon node = selectedNodes[i];
        this.writeContourLeaf(node, 0, i);
      }
    }
  }
  
  /**
   * Write one node, and its child nodes.
   * @param node The current node in the Xholon hierarchy.
   * @param level Current level in the hierarchy.
   */
  protected void writeTreeNode(IXholon node, int level)
  {
    
  }
  
  /**
   * Write one node, and its child nodes.
   * @param node The current node in the Xholon hierarchy.
   * @param level Current level in the hierarchy.
   * @param siblingIndex Index of this sibling within its container (0-indexed).
   */
  protected void writeContourNode(IXholon node, int level, int siblingIndex)
  {
    // optionally show state machines
    if (!showStates && isStateMachineEntity(node)) {return;}
    if (node == this) {return;}
    if (node.isContainer()) {
      // this is a container
      writeContourContainerChoose(node, level, siblingIndex);
    }
    else if (node.hasChildNodes() && hasDomainChildNodes(node)) {
      // this is an active or passive object that also has children
      writeContourContainerChoose(node, level, siblingIndex);
    }
    else {
      // this is an active or passive object that has no children
      writeContourLeaf(node, level, siblingIndex);
    }
  }
  
  /**
   * Execute a chosen way of handling a container.
   * @param node
   * @param level
   * @param siblingIndex
   */
  protected void writeContourContainerChoose(IXholon node, int level, int siblingIndex)
  {
    if (shouldFlatten && level != 0) {
      // skip this level, but retain the root level
      IXholon childNode = node.getFirstChild();
      siblingIndex = 0;
      while (childNode != null) {
        writeContourNode(childNode, level, siblingIndex++);
        childNode = childNode.getNextSibling();
      }
    }
    else {
      writeContourContainer(node, level, siblingIndex);
    }
  }
  
  /**
   * Write a container contour node (has children).
   * @param node The current node in the Xholon hierarchy.
   * @param level Current level in the hierarchy.
   * @param siblingIndex Index of this sibling within its container (0-indexed).
   */
  protected void writeContourContainer(IXholon node, int level, int siblingIndex)
  {
    String tab = indent;
		if (level < indent.length()) {
		  tab = indent.substring(0, level+1);
		}
		
    String nodeName = node.getName(nameTemplate); //.getXhcName();
    
    // initialize rectangle x, y, width, height with default values
    float rectX = 10.0f + siblingIndex * siblingTranslateX;
    float rectY = 10.0f + siblingIndex * siblingTranslateY;
    float rectWidth = nodeName.length() * 7.0f;
    if (rectWidth < 40.0f) {rectWidth = 40.0f;}
    float rectHeight = 50.0f;
    // set rectangle values from Properties if they exist
    String prop = getSvgProperty(node, ".rect");
    if (prop != null) {
      StringTokenizer st = new StringTokenizer(prop, ",");
      if (st.countTokens() == 4) { // x,y,width,height
        rectX = Integer.parseInt(st.nextToken());
        rectY = Integer.parseInt(st.nextToken());
        rectWidth = Integer.parseInt(st.nextToken());
        rectHeight = Integer.parseInt(st.nextToken());
      }
    }
    
    String fill = shapeFillColor;
    if (level % 2 == 0) {
      fill = shapeFillColorAlternate;
    }
    if (level != 0) {
      // group that includes this container and its children
      svgSb
      .append(tab)
      .append("<g")
      .append(" transform=\"translate(")
      .append(levelTranslateX)
      .append(",")
      .append(levelTranslateY)
      .append(")\"")
      .append(">\n");
    }
    // group that includes this container's rectangle and text/name
    svgSb.append(tab).append(" <g id=\"g").append(nextSvgId++).append("\">\n");
    //writeDescription(node, level);
    writeTitle(node, level+2);
    String nodeId = getId(node);
    svgSb
    .append(tab)
    .append("  <rect id=\"")
    .append(nodeId)
    .append("\"")
    .append(" x=\"")
    .append(rectX)
    .append("\"")
    .append(" y=\"")
    .append(rectY)
    .append("\"")
    .append(" width=\"")
    .append(rectWidth)
    .append("\"")
    .append(" height=\"")
    .append(rectHeight)
    .append("\"");
    if (!shouldWriteStylesheet) {
      svgSb
      .append(" fill=\"")
      .append(fill)
      .append("\"")
      .append(" stroke=\"")
      .append(strokeColor)
      .append("\"")
      .append(" stroke-width=\"")
      .append(strokeWidth)
      .append("px\"");
    }
    else if (level % 2 == 0) {
      svgSb.append(" class=\"rectalt\"");
    }
    if (shapeRx != ROUNDED_RECT_NULL) {
      svgSb.append(" rx=\"").append(shapeRx).append("\"");
    }
    if (shapeRy != ROUNDED_RECT_NULL) {
      svgSb.append(" ry=\"").append(shapeRy).append("\"");
    }
    svgSb.append("/>\n");
    svgSb
    .append(tab)
    .append("  <text id=\"text")
    .append(nextSvgId++)
    .append("\"")
    .append(" x=\"")
    .append((rectX+5.0f))
    .append("\"")
    .append(" y=\"")
    .append((rectY+10.0f))
    .append("\"");
    if (!shouldWriteStylesheet) {
      svgSb
      .append(" font-family=\"")
      .append(fontFamily)
      .append("\"")
      .append(" font-size=\"")
      .append(fontSize)
      .append("\"")
      .append(" fill=\"")
      .append(fontColor)
      .append("\"");
    }
    svgSb.append(">");
    
    svgSb.append(nodeName);
    writeVal(node, rectX+5.0f, rectY+18.0f);
    svgSb.append("</text>\n");
    writePorts(rectX - 3.0f, rectY + 4.0f, node, level+2); // 7.0f, 14.0f
    writeReffedPorts(rectX - 3.0f, rectY + 14.0f, node, level+2); // 7.0f, 24.0f
    
    svgSb.append(tab).append(" </g>\n");
    // children
    IXholon childNode = node.getFirstChild();
    siblingIndex = 0;
    while (childNode != null) {
      writeContourNode(childNode, level+1, siblingIndex++);
      childNode = childNode.getNextSibling();
    }
    if (level != 0) {
      svgSb.append(tab).append("</g>\n");
    }
  }
  
  /**
   * Write a leaf contour node (no children).
   * @param node The current node in the Xholon hierarchy.
   * @param level Current level in the hierarchy.
   * @param siblingIndex Index of this sibling within its container (0-indexed).
   */
  protected void writeContourLeaf(IXholon node, int level, int siblingIndex)
  {
    String tab = indent;
		if (level < indent.length()) {
		  tab = indent.substring(0, level+1);
		}
		
    String nodeName = node.getName(nameTemplate); //node.getXhcName();
    
    // initialize rectangle x, y, width, height with default values
    float rectX = 20.0f + siblingIndex * siblingTranslateX;
    float rectY = 30.0f + siblingIndex * siblingTranslateY;;
    float rectWidth = nodeName.length() * 7.0f;
    if (rectWidth < 40.0f) {rectWidth = 40.0f;}
    float rectHeight = 20.0f;
    // set rectangle values from Properties if they exist
    String prop = getSvgProperty(node, ".rect");
    if (prop != null) {
      StringTokenizer st = new StringTokenizer(prop, ",");
      if (st.countTokens() == 4) { // x,y,width,height
        rectX = Integer.parseInt(st.nextToken());
        rectY = Integer.parseInt(st.nextToken());
        rectWidth = Integer.parseInt(st.nextToken());
        rectHeight = Integer.parseInt(st.nextToken());
      }
    }
    
    String fill = shapeFillColor;
    if (level % 2 == 0) {
      fill = shapeFillColorAlternate;
    }
    svgSb.append(tab).append("<g id=\"g").append(nextSvgId++).append("\">\n");
    //writeDescription(node, level);
    writeTitle(node, level+1);
    String nodeId = getId(node);
    svgSb
    .append(tab)
    .append(" <rect id=\"")
    .append(nodeId)
    .append("\"")
    .append(" x=\"")
    .append(rectX)
    .append("\"")
    .append(" y=\"")
    .append(rectY)
    .append("\"")
    .append(" width=\"")
    .append(rectWidth)
    .append("\"")
    .append(" height=\"")
    .append(rectHeight)
    .append("\"");
    if (!shouldWriteStylesheet) {
      svgSb
      .append(" fill=\"")
      .append(fill)
      .append("\"")
      .append(" stroke=\"")
      .append(strokeColor)
      .append("\"")
      .append(" stroke-width=\"")
      .append(strokeWidth)
      .append("px\"");
    }
    else if (level % 2 == 0) {
      svgSb.append(" class=\"rectalt\"");
    }
    if (shapeRx != ROUNDED_RECT_NULL) {
      svgSb.append(" rx=\"").append(shapeRx).append("\"");
    }
    if (shapeRy != ROUNDED_RECT_NULL) {
      svgSb.append(" ry=\"").append(shapeRy).append("\"");
    }
    svgSb.append("/>\n");
    svgSb
    .append(tab)
    .append(" <text id=\"text")
    .append(nextSvgId++)
    .append("\"")
    .append(" x=\"")
    .append((rectX+5.0f))
    .append("\"")
    .append(" y=\"")
    .append((rectY+10.0f))
    .append("\"");
    if (!shouldWriteStylesheet) {
      svgSb
      .append(" font-family=\"")
      .append(fontFamily)
      .append("\"")
      .append(" font-size=\"")
      .append(fontSize)
      .append("\"")
      .append(" fill=\"")
      .append(fontColor)
      .append("\"");
    }
    svgSb.append(">");
    svgSb.append(nodeName);
    writeVal(node, rectX+5.0f, rectY+18.0f);
    svgSb.append("</text>\n");
    writePorts(rectX - 3.0f, rectY + 4.0f, node, level+1); // 17.0f, 34.0f
    writeReffedPorts(rectX - 3.0f, rectY + 14.0f, node, level+1); // 17.0f, 44.0f
    svgSb.append(tab).append("</g>\n");
  }
  
  /**
   * Write an SVG description, using the &lt;desc&gt; tag.
   * @param node The current node in the Xholon hierarchy.
   * @param level Current level in the hierarchy.
   */
  protected void writeDescription(IXholon node, int level)
  {
    String tab = indent;
		if (level < indent.length()) {
		  tab = indent.substring(0, level+1);
		}
		
    svgSb.append(tab).append("<desc>");
    String descStr = null;
    //if (ISwingEntity.class.isAssignableFrom(node.getClass())) {
    //  descStr = node.getName();
    //}
    //else {
      descStr = normalizeXmlContent(node.toString());
    //}
    svgSb.append(descStr);
    svgSb.append("</desc>\n");
  }
  
  /**
   * Write an SVG description, using the &lt;title&gt; tag.
   * @param node The current node in the Xholon hierarchy.
   * @param level Current level in the hierarchy.
   */
  protected void writeTitle(IXholon node, int level)
  {
    String tab = indent;
		if (level < indent.length()) {
		  tab = indent.substring(0, level+1);
		}
		
    svgSb.append(tab).append("<title>");
    String titleStr = null;
    titleStr = normalizeXmlContent(node.toString());
    svgSb.append(titleStr);
    svgSb.append("</title>\n");
  }
  
  /**
   * Write val (double, int, etc.), if one exists.
   * @param node The current node in the Xholon hierarchy.
   * @param rectX The SVG X position of the val.
   * @param rectY The SVG Y position of the val.
   */
  protected void writeVal(IXholon node, float rectX, float rectY)
  {
    if (writeValType == null) {return;}
    if (node.isPassiveObject()) {
      String attrStr = "";
      String valStr = "";
      if ("double".equals(writeValType)) {
        attrStr = "[@val]\"";
        valStr = "" + StringHelper.format("%.1f", node.getVal()); // GWT
      }
      else if ("int".equals(writeValType)) {
        attrStr = "[@val_int]\"";
        valStr = "" + StringHelper.format("%d", node.getVal_int());
      }
      else if ("char".equals(writeValType)) {
        attrStr = "[@val_char]\"";
        valStr = "" + StringHelper.format("%c", node.getVal_char());
      }
      else if ("float".equals(writeValType)) {
        attrStr = "[@val_float]\"";
        valStr = "" + StringHelper.format("%.1f", node.getVal_float());
      }
      else if ("boolean".equals(writeValType)) {
        attrStr = "[@val_boolean]\"";
        valStr = "" + StringHelper.format("%b", node.getVal_boolean());
      }
      else if ("String".equals(writeValType)) {
        attrStr = "[@val_String]\"";
        valStr = StringHelper.format("%s", node.getVal_String());
      }
      else if ("Object".equals(writeValType)) {
        attrStr = "[@val_Object]\"";
        valStr = StringHelper.format("%s", node.getVal_Object());
      }
      svgSb
      .append("<tspan id=\"")
      .append(getId(node))
      .append(attrStr)
      .append(" x=\"")
      .append(rectX)
      .append("\"")
      .append(" y=\"")
      .append(rectY)
      .append("\"")
      .append(" font-size=\"")
      .append((fontSize-2.0f))
      .append("\">")
      .append(valStr)
      .append("</tspan>");
    }
  }
  
  /**
   * Write ports.
   * @param x An SVG x coordinate.
   * @param y An SVG y coordinate.
   * @param node The current node in the Xholon hierarchy.
   * @param level Current level in the hierarchy.
   */
  protected void writePorts(float x, float y, IXholon node, int level)
  {
    List portList = node.getAllPorts();
    //System.out.println("Xholon2Svg writePorts " + portList.size());
    for (int i = 0; i < portList.size(); i++) {
      // ex: atmosphere_27.surface
      String portId = node.getName(nameTemplate4Ports) + "."
        + ((PortInformation)portList.get(i)).getLocalName();
      writePort(x, y, false, (PortInformation)portList.get(i), portId, level);
      
      // write connector
      // TODO figure out how to provide the x and y for the conjugated/end port
      //writeConnector(portId,
      //    ((PortInformation)portList.get(i)).getReffedNode().getName("portNameTemplate")
      //    + ".conjport[0]", x+1.0f, y+1.0f);
    }
  }
  
  /**
   * Write ports that are referenced by other ports.
   * These are conjugated ports.
   * @param x An SVG x coordinate.
   * @param y An SVG y coordinate.
   * @param node The current node in the Xholon hierarchy.
   * @param level Current level in the hierarchy.
   */
  protected void writeReffedPorts(float x, float y, IXholon node, int level)
  {
    int numReffedPorts = reffedRoot.getCount(node);
    //System.out.println("Xholon2Svg writeReffedPorts " + numReffedPorts);
    for (int i = 0; i < numReffedPorts; i++) {
      // ex: world_2.conjport[0]
      String portId = node.getName(nameTemplate4Ports) + ".conjport[" + i + "]";
      writePort(x, y, true, null, portId, level);
    }
  }
  
  /**
   * Write a port symbol.
   * @param x An SVG x coordinate.
   * @param y An SVG y coordinate.
   * @param isConjugated Should the port be conjugated or not.
   * @param portInfo Information about a port.
   * @param portId A unique id for this port, that can be used as the SVG id.
   */
  @SuppressWarnings("unchecked")
  protected void writePort(float x, float y, boolean isConjugated, PortInformation portInfo, String portId, int level)
  {
    if (!shouldShowPorts) {return;}
    
    String tab = indent;
		if (level < indent.length()) {
		  tab = indent.substring(0, level+1);
		}
		
    /*String stroke = portStrokeColor;
    String fill = portFillColor;
    if (isConjugated) {
      // reverse the two colors
      stroke = portFillColor;
      fill = portStrokeColor;
    }*/
    
    //try {
      if (portInfo != null) {
        svgSb
        .append(tab)
        .append("<!--")
        .append(portInfo.toString())
        .append("-->\n");
        
      }
      else {
        // this is a conjugated port
        
      }
      if (isConjugated) {
        //svgSb.append(String.format(portConjugatedTemplate, portId, x, y) + "\n");
        //"<polygon id=\"%s\" points=\"0,0 0,6 6,3\" transform=\"translate(%f,%f)\" width=\"6\" height=\"6\" fill=\"#e0e0f8\" stroke=\"#0000ff\" stroke-width=\"1px\"/>"
        svgSb
        .append(tab)
        .append("<polygon id=\"")
        .append(portId)
        .append("\" points=\"0,0 0,6 6,3\" transform=\"translate(")
        .append(x)
        .append(",")
        .append(y)
        .append(")\" width=\"6\" height=\"6\"");
        if (!shouldWriteStylesheet) {
          svgSb.append(" fill=\"#e0e0f8\" stroke=\"#0000ff\" stroke-width=\"1px\"");
        }
        svgSb.append("/>\n");

        StringBuilder sb = new StringBuilder().append(x).append(",").append(y);
        svgEndPort.put(portId, sb.toString());
      }
      else {
        //svgSb.append(String.format(portTemplate, portId, x, y) + "\n");
        //"<rect id=\"%s\" x=\"%f\" y=\"%f\" ry=\"2.5\" width=\"6\" height=\"6\" fill=\"#0000ff\" stroke=\"#e0e0f8\" stroke-width=\"1px\"/>"
        svgSb
        .append(tab)
        .append("<rect id=\"")
        .append(portId)
        .append("\" x=\"")
        .append(x)
        .append("\" y=\"")
        .append(y)
        .append("\" ry=\"2.5\" width=\"6\" height=\"6\"");
        if (!shouldWriteStylesheet) {
          svgSb.append(" fill=\"#0000ff\" stroke=\"#e0e0f8\" stroke-width=\"1px\"");
        }
        svgSb.append("/>\n");
        
        StringBuilder sb = new StringBuilder()
        .append(portId)
        .append(",")
        .append(x)
        .append(",")
        .append(y)
        .append(",")
        .append(portInfo.getReffedNode().getName(nameTemplate4Ports));
        svgStartPort.add(sb.toString());
      }
      /*svgSb.append("<rect x=\"" + x + "\" y=\"" + y + "\" width=\"" + "6" + "\" height=\"6\""
          + " fill=\"" + fill + "\""
          + " stroke=\"" + stroke + "\""
          + " stroke-width=\"" + "2" + "px\"/>\n");*/
    //} catch (IOException e) {
    //  Xholon.getLogger().error("writePort()", e);
    //}
    
  }
  
  /**
   * Write a connector line.
   * @deprecated
   */
  protected void writeConnector()
  {
    if (!shouldShowConnectors) {return;}
    svgSb
    .append("<line x1=\"20\" y1=\"40\" x2=\"20\" y2=\"90\"")
    .append(" stroke=\"")
    .append(connectorStrokeColor)
    .append("\"")
    .append(" stroke-width=\"")
    .append("1")
    .append("px\"/>\n");
  }
  
  /**
   * Write a connector line between two ports.
   * @param startPortId SVG id of the start port.
   * @param endPortId SVG id of the end port.
   * @param x X coordinate of the start port.
   * @param y Y coordinate of the start port.
   * @deprecated
<path
 style="fill:none;stroke:#000000;stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;stroke-opacity:1"
 d="M 284.11907,289.53756 C 314.49326,261.95355 306.48215,222.37412 308.8464,186.037"
 id="path5739"
 inkscape:connector-type="polyline"
 inkscape:connector-curvature="0"
 inkscape:connection-start="#rect153"
 inkscape:connection-start-point="d4"
 inkscape:connection-end="#rect72"
 inkscape:connection-end-point="d4"
 transform="translate(10.644262,10.644262)"
 sodipodi:nodetypes="cc" />
   */
  protected void writeConnector(String startPortId, String endPortId, float x, float y) {
    if (!shouldShowConnectors) {return;}
    if (useInkscape) {
      // TODO Inkscape doesn't act as if this is a connector
      svgSb.append("<path");
      svgSb.append(" stroke=\"#000000\" stroke-width=\"1px\"");
      //style="fill:none;stroke:#000000;stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;stroke-opacity:1"
      //d="M 284.11907,289.53756 C 314.49326,261.95355 306.48215,222.37412 308.8464,186.037"
      //svgSb.append(" d=\"33.202919,34.167287 24.642082,-0.6751\"");
      svgSb
      .append(" d=\"m ")
      .append(x)
      .append(",")
      .append(y )
      .append(" ")
      .append(20.0)
      .append(",")
      .append(0.0)
      .append("\"");
      //id="path5739"
      svgSb.append(" inkscape:connector-type=\"polyline\"");
      svgSb.append(" inkscape:connector-curvature=\"0\"");
      svgSb.append(" inkscape:connection-start=\"").append(startPortId).append("\"");
      svgSb.append(" inkscape:connection-start-point=\"d4\"");
      svgSb.append(" inkscape:connection-end=\"").append(endPortId).append("\"");
      svgSb.append(" inkscape:connection-end-point=\"d4\"");
      //transform="translate(10.644262,10.644262)"
      svgSb.append(" sodipodi:nodetypes=\"cc\"");
      svgSb.append(" />\n");
    
    }
    else {
      svgSb
      .append("<path")
      .append(" stroke=\"#000000\" stroke-width=\"1px\"")
      .append(" d=\"m ")
      .append(x)
      .append(",")
      .append(y )
      .append(" ")
      .append(20.0)
      .append(",")
      .append(0.0)
      .append("\"");
      svgSb.append(" />\n");
    }
  }
  
  /**
   * Write connector lines between ports.
   */
  protected void writeConnectors() {
    if (!shouldShowConnectors) {return;}
    Iterator itStart = svgStartPort.iterator();
    if (shouldCommentConnectors) {svgSb.append(" <!--\n");}
    while (itStart.hasNext()) {
      String startPortStr = (String)itStart.next();
      //System.out.println(str);
      // ex: hello_1.port[0],17.0,34.0,world_2
      StringTokenizer startPortSt = new StringTokenizer(startPortStr, ",");
      String startPortId = startPortSt.nextToken();
      float startX = Float.parseFloat(startPortSt.nextToken()) + portCenterX;
      float startY = Float.parseFloat(startPortSt.nextToken()) + portCenterY;
      String endPortId = startPortSt.nextToken() + ".conjport[0]";
      // ex: naOH_4.conjport[0]=21.0,44.0
      String endPortStr = (String)svgEndPort.get(endPortId);
      if (endPortStr == null) {continue;}
      StringTokenizer endPortSt = new StringTokenizer(endPortStr, ",");
      float endX = Float.parseFloat(endPortSt.nextToken()) + portCenterX;
      float endY = Float.parseFloat(endPortSt.nextToken()) + portCenterY;
      if (useInkscape) {
        svgSb.append(" <path");
        svgSb.append(" stroke=\"")
        .append(connectorStrokeColor)
        .append("\" stroke-width=\"")
        .append(connectorStrokeWidth)
        .append("px\"");
        //style="fill:none;stroke:#000000;stroke-width:1px;stroke-linecap:butt;stroke-linejoin:miter;stroke-opacity:1"
        //d="M 284.11907,289.53756 C 314.49326,261.95355 306.48215,222.37412 308.8464,186.037"
        //svgSb.append(" d=\"33.202919,34.167287 24.642082,-0.6751\"");
        svgSb
        .append(" d=\"M ")
        .append(startX)
        .append(",")
        .append(startY )
        .append(" ")
        .append(endX)
        .append(",")
        .append(endY)
        .append("\"");
        //id="path5739"
        svgSb.append(" inkscape:connector-type=\"polyline\"");
        svgSb.append(" inkscape:connector-curvature=\"0\"");
        svgSb.append(" inkscape:connection-start=\"#").append(startPortId).append("\"");
        svgSb.append(" inkscape:connection-start-point=\"d4\"");
        svgSb.append(" inkscape:connection-end=\"#").append(endPortId).append("\"");
        svgSb.append(" inkscape:connection-end-point=\"d4\"");
        //transform="translate(10.644262,10.644262)"
        //svgSb.append(" sodipodi:nodetypes=\"cc\""); // NO
        svgSb.append(" />\n");
      
      }
      else {
        svgSb
        .append(" <path")
        .append(" stroke=\"")
        .append(connectorStrokeColor)
        .append("\" stroke-width=\"")
        .append(connectorStrokeWidth)
        .append("px\"")
        .append(" d=\"M ")
        .append(startX)
        .append(",")
        .append(startY )
        .append(" ")
        .append(endX)
        .append(",")
        .append(endY)
        .append("\"")
        .append(" />\n");
      }
    }
    if (shouldCommentConnectors) {svgSb.append(" -->\n");}
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
  
  /**
   * Initialize SVG properties.
   * This includes loading properties from any svg.properties file that exists
   * in the config folder for this Xholon app.
   * It may involve initializing other properties internally.
   */
  protected void initializeSvgProperties()
  {
    /* GWT
    // get the folder where the _xhn.xml config file is located
    // ex: "./config/HelloWorld/HelloWorld_xhn.xml"
    String configPathName = ((Application)root.getApp()).getConfigPathName();
    if (configPathName == null) {return;}
    String propsFileName = configPathName + "/svg.properties";
    // 
    if (!new File(propsFileName).exists()) {return;}
    svgProperties = new Properties();
    try {
      svgProperties.load(new BufferedInputStream(new FileInputStream(propsFileName)));
    } catch (FileNotFoundException e) {
      svgProperties = null;
      Xholon.getLogger().warn("Unable to find a svg.properties file.", e);
    } catch (IOException e) {
      svgProperties = null;
      Xholon.getLogger().warn("Unable to find a svg.properties file.", e);
    }
    if (svgProperties != null) {
      //System.out.println(svgProperties.toString());
    }
    
    // possibly initialize other SVG properties as well
    // calculate starting font size
    //float fontSz = (root.getNumLevels() / 2.0f) + fontSize;
    */
  }
  
  /**
   * Get an SVG property String, given a node and optional key suffix.
   * @param node An IXholon node whose attributes will be used as a key.
   * @param keySuffix An optional suffix that can be used as part of the key. (ex: ".rect").
   * @return An SVG property String, or null (ex: 20,30,200,50).
   */
  protected String getSvgProperty(IXholon node, String keySuffix)
  {
    /* GWT
    if (svgProperties == null) {return null;}
    String key = getXPathLocal().getExpression(node, root, false);
    if (key == null) {return null;}
    key += keySuffix;
    String value = svgProperties.getProperty(key);
    return value;*/
    return null;
  }
  
  /**
   * Get a value for the SVG id attribute.
   * @param node
   * @return
   */
  protected String getId(IXholon node)
  {
    //return node.getName("^:c_i^");
    return getXPathLocal().getExpression(node, root, false);
  }
  
  /**
   * Is this node a State Machine Entity?
   * @param node The current node in the Xholon hierarchy.
   * @return true or false
   */
  protected boolean isStateMachineEntity(IXholon node) {
    // do a quick check first, in case this is the StateMachine node itself
    if (node.getXhcId() == CeStateMachineEntity.StateMachineCE) {
      return true;
    }
    // do a more thorough check, in case this is some part of the state machine
    if (node.getXhc().hasAncestor("StateMachineEntity")) {
      return true;
    }
    return false;
  }
  
  /**
   * Does the specified node have children that are domain objects.
   * That is, they are not state machines, or attributes.
   * @param node The current node in the Xholon hierarchy.
   * @return true or false
   */
  protected boolean hasDomainChildNodes(IXholon node) {
    IXholon testNode = node.getFirstChild();
    while (testNode != null) {
      if ((testNode.getXhcId() == CeStateMachineEntity.StateMachineCE)) {}
      else if (testNode.getXhc().hasAncestor("Attribute")) {}
      else {
        return true;
      }
      testNode = testNode.getNextSibling();
    }
    return false;
  }
  
  /**
   * Optionally write a CSS stylesheet.
  */
  protected void writeStylesheet() {
    if (!shouldWriteStylesheet) {return;}
    StringBuilder sbStyle = new StringBuilder()
    .append("\n<style>\n")
    
    .append("svg#").append(svgId).append(" rect {\n")
    .append("  fill: ").append(shapeFillColor).append(";\n")
    .append("  stroke: ").append(strokeColor).append(";\n")
    .append("  stroke-width: ").append(strokeWidth).append("px;\n")
    .append("}\n")
    
    .append("svg#").append(svgId).append(" rect.rectalt {\n")
    .append("  fill: ").append(shapeFillColorAlternate).append(";\n")
    .append("}\n")
    
    .append("svg#").append(svgId).append(" rect:active {\n")
    .append("  stroke-width: ").append(strokeWidth * 2).append("px;\n")
    .append("}\n")
    
    .append("svg#").append(svgId).append(" text {\n")
    .append("  font-family: ").append(fontFamily).append(";\n")
    .append("  font-size: ").append(fontSize).append("px;\n")
    //.append("  font-weight: ").append("bold").append(";\n")
    .append("  fill: ").append(fontColor).append(";\n")
    .append("  pointer-events: ").append("none").append(";\n")
    .append("}\n")
    
    .append("svg#").append(svgId).append(" rect[id*=\".port[\"] {\n")
    .append("  fill: ").append("#0000ff").append(";\n")
    .append("  stroke: ").append("#e0e0f8").append(";\n")
    .append("  stroke-width: ").append(strokeWidth).append("px;\n")
    .append("}\n")
    
    .append("svg#").append(svgId).append(" rect[id*=\".conjport[\"],")
    .append(" svg#").append(svgId).append(" polygon[id*=\".conjport[\"] {\n")
    .append("  fill: ").append("#e0e0f8").append(";\n")
    .append("  stroke: ").append("#0000ff").append(";\n")
    .append("  stroke-width: ").append(strokeWidth).append("px;\n")
    .append("}\n")
    
    .append("</style>\n");
    
    svgSb.append(sbStyle.toString());
  }
  
  public String getSvgFileName() {
    return svgFileName;
  }

  public void setSvgFileName(String svgFileName) {
    this.svgFileName = svgFileName;
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

  //public Writer getSvgOut() {
  //  return svgOut;
  //}

  //public void setSvgOut(Writer svgOut) {
  //  this.svgOut = svgOut;
  //}

  public String getSvgPathName() {
    return svgPathName;
  }

  public void setSvgPathName(String svgPathName) {
    this.svgPathName = svgPathName;
  }

  public Date getTimeNow() {
    return timeNow;
  }

  public void setTimeNow(Date timeNow) {
    this.timeNow = timeNow;
  }

  public long getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(long timeStamp) {
    this.timeStamp = timeStamp;
  }

  public boolean isShowStates() {
    return showStates;
  }

  public void setShowStates(boolean showStates) {
    this.showStates = showStates;
  }

  public String getNameTemplate() {
    return nameTemplate;
  }

  public void setNameTemplate(String nameTemplate) {
    this.nameTemplate = nameTemplate;
  }

  public String getFontFamily() {
    return fontFamily;
  }

  public void setFontFamily(String fontFamily) {
    this.fontFamily = fontFamily;
  }

  public float getFontSize() {
    return fontSize;
  }

  public void setFontSize(float fontSize) {
    this.fontSize = fontSize;
  }

  public float getFontSizeInc() {
    return fontSizeInc;
  }

  public void setFontSizeInc(float fontSizeInc) {
    this.fontSizeInc = fontSizeInc;
  }

  public String getFontColor() {
    return fontColor;
  }

  public void setFontColor(String fontColor) {
    this.fontColor = fontColor;
  }

  public float getStrokeWidth() {
    return strokeWidth;
  }

  public void setStrokeWidth(float strokeWidth) {
    this.strokeWidth = strokeWidth;
  }

  public float getStrokeWidthInc() {
    return strokeWidthInc;
  }

  public void setStrokeWidthInc(float strokeWidthInc) {
    this.strokeWidthInc = strokeWidthInc;
  }

  public String getStrokeColor() {
    return strokeColor;
  }

  public void setStrokeColor(String strokeColor) {
    this.strokeColor = strokeColor;
  }

  public String getShapeFillColor() {
    return shapeFillColor;
  }

  public void setShapeFillColor(String shapeFillColor) {
    this.shapeFillColor = shapeFillColor;
  }

  public String getShapeFillColorAlternate() {
    return shapeFillColorAlternate;
  }

  public void setShapeFillColorAlternate(String shapeFillColorAlternate) {
    this.shapeFillColorAlternate = shapeFillColorAlternate;
  }

  public float getPageWidth() {
    return pageWidth;
  }

  public void setPageWidth(float pageWidth) {
    this.pageWidth = pageWidth;
  }

  public float getPageHeight() {
    return pageHeight;
  }

  public void setPageHeight(float pageHeight) {
    this.pageHeight = pageHeight;
  }

  /*public String getPortStrokeColor() {
    return portStrokeColor;
  }

  public void setPortStrokeColor(String portStrokeColor) {
    this.portStrokeColor = portStrokeColor;
  }

  public String getPortFillColor() {
    return portFillColor;
  }

  public void setPortFillColor(String portFillColor) {
    this.portFillColor = portFillColor;
  }*/

  public boolean isShouldShowPorts() {
    return shouldShowPorts;
  }

  public void setShouldShowPorts(boolean shouldShowPorts) {
    this.shouldShowPorts = shouldShowPorts;
  }

  public String getPortTemplate() {
    return portTemplate;
  }

  public void setPortTemplate(String portTemplate) {
    this.portTemplate = portTemplate;
  }

  public String getPortConjugatedTemplate() {
    return portConjugatedTemplate;
  }

  public void setPortConjugatedTemplate(String portConjugatedTemplate) {
    this.portConjugatedTemplate = portConjugatedTemplate;
  }

  public String getConnectorStrokeColor() {
    return connectorStrokeColor;
  }

  public void setConnectorStrokeColor(String connectorStrokeColor) {
    this.connectorStrokeColor = connectorStrokeColor;
  }

  public boolean isShouldShowConnectors() {
    return shouldShowConnectors;
  }

  public void setShouldShowConnectors(boolean shouldShowConnectors) {
    this.shouldShowConnectors = shouldShowConnectors;
  }

  public boolean isUseInkscape() {
    return useInkscape;
  }

  public void setUseInkscape(boolean useInkscape) {
    this.useInkscape = useInkscape;
  }

  public boolean isShouldCommentConnectors() {
    return shouldCommentConnectors;
  }

  public void setShouldCommentConnectors(boolean shouldCommentConnectors) {
    this.shouldCommentConnectors = shouldCommentConnectors;
  }

  public XholonSortedNode getReffedRoot() {
    return reffedRoot;
  }

  public void setReffedRoot(XholonSortedNode reffedRoot) {
    this.reffedRoot = reffedRoot;
  }
  
  public IXPath getXPathLocal() {
    return xPathLocal;
  }

  public void setXPathLocal(IXPath xPathLocal) {
    this.xPathLocal = xPathLocal;
  }

  public int getLevelTranslateX() {
    return levelTranslateX;
  }

  public void setLevelTranslateX(int levelTranslateX) {
    this.levelTranslateX = levelTranslateX;
  }

  public int getLevelTranslateY() {
    return levelTranslateY;
  }

  public void setLevelTranslateY(int levelTranslateY) {
    this.levelTranslateY = levelTranslateY;
  }

  public int getSiblingTranslateX() {
    return siblingTranslateX;
  }

  public void setSiblingTranslateX(int siblingTranslateX) {
    this.siblingTranslateX = siblingTranslateX;
  }

  public int getSiblingTranslateY() {
    return siblingTranslateY;
  }

  public void setSiblingTranslateY(int siblingTranslateY) {
    this.siblingTranslateY = siblingTranslateY;
  }

  public String getSvgClientOption() {
    return svgClientOption;
  }

  public void setSvgClientOption(String svgClientOption) {
    this.svgClientOption = svgClientOption;
  }

  public String getWriteValType() {
    return writeValType;
  }

  public void setWriteValType(String writeValType) {
    this.writeValType = writeValType;
  }

  public boolean isShouldFlatten() {
    return shouldFlatten;
  }

  public void setShouldFlatten(boolean shouldFlatten) {
    this.shouldFlatten = shouldFlatten;
  }

  public boolean isSelectedNodesOnly() {
    return selectedNodesOnly;
  }

  public void setSelectedNodesOnly(boolean selectedNodesOnly) {
    this.selectedNodesOnly = selectedNodesOnly;
  }

  public float getConnectorStrokeWidth() {
    return connectorStrokeWidth;
  }

  public void setConnectorStrokeWidth(float connectorStrokeWidth) {
    this.connectorStrokeWidth = connectorStrokeWidth;
  }

  public float getShapeRx() {
    return shapeRx;
  }

  public void setShapeRx(float shapeRx) {
    this.shapeRx = shapeRx;
  }

  public float getShapeRy() {
    return shapeRy;
  }

  public void setShapeRy(float shapeRy) {
    this.shapeRy = shapeRy;
  }

}

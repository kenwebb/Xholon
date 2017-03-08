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

package org.primordion.xholon.app;

import com.google.gwt.core.client.GWT;

//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
//import java.util.Properties;
import java.util.Map.Entry;

//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.servlet.ServletUtilities;
import org.primordion.xholon.base.IControl;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.exception.XholonConfigurationException;
import org.primordion.xholon.io.IChartViewer;
import org.primordion.xholon.service.ExternalFormatService;
import org.primordion.xholon.service.IXholonService;
//import org.primordion.xholon.service.XmlValidationService;
//import org.primordion.xholon.service.xmlvalidation.IXholonXmlValidator;

/**
 * HtmlApplication writes out information about a Xholon application, as HTML.
 * It will typically be used inside a servlet,
 * or it can be called from the command line.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 9, 2012)
 * 
 * TODO March 1 2017 - this code is incomplete and does not work yet
 */
public class HtmlApplication {

  /**
   * Default Xholon Application class name.
   */
  protected static final String DEFAULT_CN =
    "org.primordion.user.app.Chameleon.AppChameleon";
    // "org.primordion.xholon.app.Chameleon.AppChameleonGWTGEN";
  
  /**
   * Default Xholon config file name.
   */
  protected static final String DEFAULT_CFN =
    //"/org/primordion/user/app/Chameleon/Chameleon_xhn.xml";
    "/org/primordion/xholon/app/Chameleon/Chameleon_xhn.xml";
  
  /**
   * Default CSH prefix.
   */
  protected static final String DEFAULT_CSH_PREFIX =
    "https://gist.github.com/raw";
  
  /**
   * Default list of gist IDs that can be used with CSH.
   */
  protected static final String[] DEFAULT_CSH_WHITELIST = {
    "/3457105/" // Feinberg
  };
  
  /**
   * Default list of XML root nodes that can be validated with a schema.
   * ex: XML content with a root node of &lt;HelloWorldSystem> can be
   * validated using the HelloWorldSystem.xsd schema.
   */
  protected static final String[] DEFAULT_VALIDATABLE_WHITELIST = {
    "HelloWorldSystem",
    "ReactionNetworkSystem"
  };
  
  /**
   * XML submitted by users must begin with these five characters.
   */
  protected static final String DEFAULT_XML_MARK = "<?xml";
  
  /**
   * A URI submitted by users must begin with these four characters.
   */
  protected static final String DEFAULT_URI_MARK = "http";
  
  /**
   * Default location of schemas.
   * This value is prefixed to the schema name.
   */
  //protected static final String DEFAULT_SCHEMA_PREFIX = "./xsd/";
  protected static final String DEFAULT_SCHEMA_PREFIX = "http://www.primordion.com/Xholon/schema/";
  
  /**
   * File extension of schemas.
   * This value is suffixed to the schema name.
   */
  protected static final String DEFAULT_SCHEMA_SUFFIX = ".xsd";
  
  /**
   * The absolute maximum number of loops the app is allowed to run.
   */
  protected static final int DEFAULT_MAX_PROCESS_LOOPS = 10000;
  
  /**
   * The Xholon application.
   */
  protected IApplication app = null;
  
  protected IXholon xholonHelperService = null;
  
  /**
   * Default Xholon Application class name.
   */
  protected String defaultCn = DEFAULT_CN;
  
  /**
   * Default Xholon config file name.
   */
  protected String defaultCfn = DEFAULT_CFN;
  
  /**
   * Default CSH prefix.
   */
  protected String defaultCshPrefix = DEFAULT_CSH_PREFIX;
  
  /**
   * Default list of gist IDs that can be used with CSH.
   */
  protected String[] defaultCshWhitelist = DEFAULT_CSH_WHITELIST;
  
  /**
   * Default list of XML root nodes that can be validated with a schema.
   */
  protected String[] defaultValidatableWhitelist = DEFAULT_VALIDATABLE_WHITELIST;
  
  /**
   * A path or URL of a composite structure hierarchy.
   * This will be pasted in as the last child of root.
   */
  protected String csh = null;
  
  /**
   * The Writer associated with the get response.
   */
  //protected PrintWriter out = null;
  protected StringBuilder sb = null;
  protected StringBuilder sbHelp = null;
  
  /**
   * Maximum number of external formats (ef) that can be specified at one time.
   */
  protected int maxEf = 50;
  
  /**
   * A possible displayable annotation.
   */
  protected String annotation = null;
  
  /**
   * Whether or not to write certain visible meta-information to the HTML.
   */
  protected boolean debug = true;
  
  /**
   * Handle requests to write out information about a Xholon application, as HTML.
   * @param className The class name of a Xholon Application.
   * ex: <p>org.primordion.user.app.petrinet.feinberg1.Appfeinberg1</p>
   * @param configFileName The name of a Xholon config file.
   * ex: <p>/org/primordion/user/app/petrinet/feinberg1/_xhn.xml</p>
   * Or a partial config file name (with className == null)
   * ex: petrinet/feinberg1
   * @param cshArg The ID and fileName of a whitelisted gist at github.
   * ex: <p>/3457105/crn_1_1_csh.xml</p>
   * <p>Or a complete URI starting with http or https (will need to be validated against a schema)</p>
   * ex: <p>https://gist.github.com/raw/4567890/someones_csh.xml</p>
   * <p>Or an XML String originating from a user client (will need to be validated against a schema)</p>
   * ex: <p>&lt;?xml ...</p>
   * @param ef A list of one or more external formats, or null.
   * ex: <p>Xml,Newick,Yuml,Pnml,Sbml4PetriNets,MindMap4PetriNets</p>
   * @param ed A list of one or more external formats, or null.
   * These will be wrapped inside an editor in the browser, such as CodeMirror.
   * The only one really useful/implemented right now is an editor for XML.
   * ex: <p>Xml</p>
   * @param act A list of Xholon class actions, or null.
   * ex: <p>AnalysisPetriNet_Show+python+script,AnalysisCRN_Show+all</p>
   * @param params A list of application parameters, or null.
   * Typically these override values in _xhn.xml.
   * ex: <p>MaxProcessLoops=100</p>
   * @param outArg The PrintWriter to write the information to.
   * If null, then System.out will be wrapped as a PrintWriter.
   * Typically, this will be a servlet's response.getWriter().
   */
  public void doGet(
      String className,
      IApplication appArg,
      String configFileName,
      String cshArg,
      String ef,
      String ed,
      String act
      //Properties params
      //PrintWriter outArg
      ) {
    
    app = appArg;
    app.consoleLog("HtmlApplication starting ...");
    app.consoleLog(className);
    app.consoleLog(appArg);
    app.consoleLog(configFileName);
    app.consoleLog(cshArg);
    app.consoleLog(ef);
    app.consoleLog(ed);
    app.consoleLog(act);
    
    StringBuilder sb = new StringBuilder();
    /*if (outArg == null) {
      out = new PrintWriter(System.out, true);
    }
    else {
      out = outArg;
    }*/
    sb.append("<html>");
    /*if ((className == null) && (configFileName != null)) {
      // construct complete names from a partial configFileName
      String appSuffix = configFileName;
      int beginIndex = configFileName.lastIndexOf('/');
      if (beginIndex != -1) {
        if (configFileName.length() > beginIndex + 1) {
          beginIndex++;
        }
        appSuffix = configFileName.substring(beginIndex);
      }
      className = "org.primordion.user.app." + configFileName.replace('/', '.')
        + ".App" + appSuffix;
      configFileName = "/org/primordion/user/app/" + configFileName + "/_xhn.xml";
    }
    else if ((className == null) || (configFileName == null)) {
      className = defaultCn;
      configFileName = defaultCfn;
    }
    app.consoleLog(className);*/
    app.consoleLog(configFileName);
    //Throwable err = null;
    /*try {
      app = (Application)Class.forName(className).newInstance();
    } catch (ClassNotFoundException e) { err = e;
    } catch (InstantiationException e) { err = e;
    } catch (IllegalAccessException e) { err = e;
    }*/
    if (app == null) {
      //Xholon.getLogger().error("Unable to create instance of " + className, err);
      sb.append("<p>" + "Unable to create instance of "
          + className + "</p>\n</html>");
      return;
    }
    app.setUseAppOut(true);
    app.setConfigFileName(configFileName);
    if (cshArg != null) {
      if (cshArg.startsWith(DEFAULT_XML_MARK)) {
        // this is an XML String originating from a user client
        String result = validateXmlString(cshArg);
        if (result == null) {
          // the XML String is valid
          csh = cshArg;
        }
        else {
          // the XML String is invalid
          Xholon.getLogger().error(result);
          sb.append("<p>" + result + "</p></html>");
          return;
        }
      }
      else if (isWhiteListed(cshArg)) {
        csh = defaultCshPrefix + cshArg;
      }
      else if (cshArg.startsWith(DEFAULT_URI_MARK)) {
        // this is a user-specified URI
        String result = validateXmlUriContent(cshArg);
        if (result == null) {
          // the XML is valid
          csh = cshArg;
        }
        else {
          // the XML String is invalid
          Xholon.getLogger().error(result);
          sb.append("<p>" + result + "</p></html>");
          return;
        }
      }
      else {
        Xholon.getLogger().error(cshArg + " is unavailable.");
        sb.append("<p>" + cshArg + " is unavailable." + "</p></html>");
        return;
      }
    }
    app.consoleLog("1");
    app.consoleLog(sb.toString());
    if (!initApp()) {
      sb.append("</html>");
      return;
    }
    //doParams(params);
    app.consoleLog("2");
    if (cshArg != null) {
      StringBuilder sbCshArg = new StringBuilder()
      .append(app.getModelName());
      if (cshArg.startsWith(DEFAULT_XML_MARK)) {
        // TODO
      }
      else {
        sbCshArg.append(" (").append(cshArg).append(")");
      }
      app.setParam("ModelName", sbCshArg.toString());
    }
    sb.append("<head>");
    sb.append("<title>" + app.getModelName() + "</title>");
    app.consoleLog("3");
    writeEditHead(ed);
    app.consoleLog("4");
    sb.append("</head>");
    
    sb.append("<body>");
    sb.append("<h1>" + app.getModelName() + "</h1>");
    
    if (debug) {
      sb.append("<!--<div class='debug' style='font-size:10px;'><pre>");
      sb.append(new Date());
      sb.append("ef=" + ef);
      sb.append("ed=" + ed);
      sb.append("act=" + act);
      sb.append("</pre></div>-->");
    }
    app.consoleLog("5");
    app.consoleLog(sb.toString());

    if (annotation != null) {
      sb.append("<div class='annotation'>" + annotation + "</div>");
    }

    doEdits(ed);
    //doFormats(ef); // old
    app.consoleLog("6");
    app.consoleLog(sb.toString());
    
    //app.setOut(out);
    doActions(act);
    app.consoleLog("7");
    
    // validate args
    if (app.getMaxProcessLoops() == -1) { // run forever not allowed
      app.setMaxProcessLoops(1000);
    }
    else if (app.getMaxProcessLoops() < 1) { // don't run at all
      sb.append("</body>");
      sb.append("</html>");
      return;
    }
    else if (app.getMaxProcessLoops() > DEFAULT_MAX_PROCESS_LOOPS) {
      app.setMaxProcessLoops(DEFAULT_MAX_PROCESS_LOOPS);
    }
    app.consoleLog("8");
    app.setTimeStepInterval(0);
    sb.append("<div class='results'>");
    sb.append("<h3>The results of running the model.</h3>");
    sb.append("<pre>");
    runApp();
    doFormats(ef); // new
    sb.append("</pre>");
    sb.append("</div>");
    app.consoleLog("9");
    String chartFilename = doLineChart();
    if (chartFilename != null) {
      sb.append("<div class='lineChart'>");
      sb.append("<h3>Line chart.</h3>");
      sb.append("<img src=\"/xhsrv/DisplayChart?filename=" + chartFilename + "\"/>");
      sb.append("</div>");
    }
    sb.append("</body>");
    sb.append("</html>");
    app.consoleLog(sb.toString());
  }
  
  /**
   * Initialize the application. This code is from
   * org.primordion.xholon.app.Application.runApp()
   * @param app
   */
  protected boolean initApp() {
    /*try {
      app.initialize(app.getConfigFileName());
    } catch (XholonConfigurationException e) {
      return false;
    }
    app.initControl();
    app.initViewers();
    IXholon node = app.getRoot();
    if (csh == null) {
      annotation = node.getAnnotation();
    }
    else {
      
      sendXholonHelperService(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, csh, node);
      if (node.hasChildNodes()) {
        annotation = node.getFirstChild().getAnnotation();
      }
    }
    app.setControllerState(IControl.CS_RUNNING);*/
    
    app.consoleLog("initApp() starting");
    /*try {
			app.initialize(app.getConfigFileName());
		} catch (XholonConfigurationException e) {return false;}
		app.consoleLog("initApp() 1");
		if (app.getRoot() == null) {return false;}
		app.consoleLog("initApp() 2");
		app.initControl();
		app.consoleLog("initApp() 3");
		app.initViewers();
		app.consoleLog("initApp() 4");
		app.setControllerState(IControl.CS_RUNNING);*/
		app.consoleLog("initApp() 9");
    return true;
  }

  /**
   * Run the application. This code is from
   * org.primordion.xholon.app.Application.runApp()
   * @param app
   */
  protected void runApp() {
    app.consoleLog("runApp() starting");
    /*boolean rc = app.loadWorkbook();
		if (rc == false) {
		  // no workbook is being asynchronously loaded
		  app.process();
		}*/
		app.runApp();
  }
  
  /**
   * Write the necessary scripts and stylesheets to support an editor (ex: CodeMirror).
   * CodeMirror editor, syntax highlighter, hints.
   * @param ed A list of one or more edit formats, or null or empty (ex: Xml).
   */
  protected void writeEditHead(String ed) {
    if ((ed == null) || (ed.length() == 0)) {return;}
    sb.append("<script type=\"text/javascript\" src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.5.2/jquery.min.js\"></script>");
    sb.append("<script src=\"http://www.primordion.com/Xholon/webEdition/lib/codemirror/codemirror.js\"></script>");
    sb.append("<link rel=\"stylesheet\" href=\"http://www.primordion.com/Xholon/webEdition/lib/codemirror/codemirror.css\" />");
    sb.append("<script src=\"http://www.primordion.com/Xholon/webEdition/lib/codemirror/xml.js\"></script>");
    sb.append("<style type=\"text/css\">.CodeMirror {border: 1px solid #eee;} .CodeMirror-scroll {height: auto; overflow-y: hidden; overflow-x: auto; width: 100%}</style>");
  }
  
  /**
   * Do all external formats (ef).
   * @param formats A comma-delimited list of formats, or null.
   */
  protected void doFormats(String formats) {
    app.consoleLog("doFormats() starting " + formats);
    if (formats == null || formats.length() == 0) {return;}
    String[] arr = formats.split(",", maxEf);
    for (int i = 0; i < arr.length; i++) {
      doFormat(arr[i]);
    }
    app.consoleLog("doFormats() end ");
  }
  
  /**
   * Do one external format.
   * @param formatName
   */
  protected void doFormat(String formatName) {
    //Writer sw = new StringWriter();
    //app.setOut(sw);
    app.consoleLog("doFormat() starting " + formatName);
    final ExternalFormatService efs = new ExternalFormatService();
    app.consoleLog("doFormat() 1");
    app.consoleLog(efs);
    app.consoleLog(app.getRoot());
    efs.writeAll(efs.initExternalFormatWriter(app.getRoot(), formatName, null, false));
    app.consoleLog("doFormat() 2");
    sb.append("<div class='ef" + formatName + "'>");
    sb.append("<h3>The model exported in " + formatName + " format.</h3>");
    sb.append("<pre>");
    // escape XML characters: &lt; etc
    //sb.append(escape(sw.toString())); // TODO GWT ???
    sb.append("</pre>");
    sb.append("</div>");
    app.consoleLog("doFormat() end");
  }
  
  /**
   * Do all edits (ed).
   * @param formats A comma-delimited list of formats, or null.
   */
  protected void doEdits(String formats) {
    if (formats == null || formats.length() == 0) {return;}
    String[] arr = formats.split(",", maxEf);
    for (int i = 0; i < arr.length; i++) {
      doEdit(arr[i]);
    }
    
    // CodeMirror script
    sb.append(
    "<script>\n" +
        "  var editor = CodeMirror.fromTextArea(document.getElementById(\"xml\"), {\n" +
        "   mode: {name: \"xml\", alignCDATA: true},\n" +
        "   lineNumbers: true\n" +
        "  });\n" +
        "</script>");
    
  }
  
  /**
   * Do one edit.
   * Only allow it for now if the formatName is "Xml".
   * @param formatName
   */
  protected void doEdit(String formatName) {
    if (!"Xml".equals(formatName)) {return;}
    //Writer sw = new StringWriter();
    //app.setOut(sw);
    final ExternalFormatService efs = new ExternalFormatService();
    efs.writeAll(efs.initExternalFormatWriter(app.getRoot(), formatName, null, false));
    sb.append("<div class='ed" + formatName + "'>");
    sb.append("<h3>The model in an edit window.</h3>");
    sb.append("<p>");
    // write instructions on using Chameleon, and pasting in from XML editor
    sb.append("When finished editing,");
    sb.append("<a href=\"http://www.primordion.com/Xholon/jnlp/Chameleon_1.jnlp\">");
    sb.append("download the Java app</a> to your own computer,");
    sb.append("and click File > Open.");
    sb.append("Then select the entire contents of the edit window below,");
    sb.append("and drag it to the Chameleon node in the app.");
    sb.append("</p>");
    sb.append("<textarea id=\"xml\" class=\"xml\">");
    // escape XML characters: &lt; etc
    //sb.append(escape(sw.toString())); // TODO GWT ???
    sb.append("</textarea>");
    sb.append("</div>");
  }
  
  /**
   * Do all actions (act).
   * @param actions A comma-delimited list of actions, or null.
   */
  protected void doActions(String actions) {
    if (actions == null || actions.length() == 0) {return;}
    String[] arr = actions.split(",", 25);
    for (int i = 0; i < arr.length; i++) {
      doAction(arr[i]);
    }
  }
  
  /**
   * Do one action.
   * ex: AnalysisPetriNet_Show+python+script
   * @param actionCommand
   */
  protected void doAction(String actionCommand) {
    String[] arr = actionCommand.split("_");
    if (arr.length != 2) {return;}
    String targetXhcName = arr[0];
    String str = arr[1];
    // the Chrome browser already converts "+" to " "
    // but the conversion is needed if this is run on a desktop
    StringBuilder sb = new StringBuilder();
    StringBuilder sbDivClass = new StringBuilder();
    for (int i = 0; i < str.length(); ++i) {
      char c = str.charAt(i);
      switch (c) {
      case '+':
        sb.append(" ");
        break;
      case ' ':
        sb.append(" ");
        break;
      default:
        sb.append(c);
        sbDivClass.append(c);
        break;
      }
    }
    String actionName = sb.toString();
    IXholon targetNode = find(app.getRoot(), targetXhcName);
    if (targetNode != null) {
      sb.append("<div class='act" + targetXhcName + sbDivClass + "'>");
      sb.append("<h3>The result of the " + actionCommand + " action.</h3>");
      sb.append("<pre>");
      targetNode.doAction(actionName);
      sb.append("</pre>");
      sb.append("</div>");
    }
  }
  
  /**
   * Do application parameters.
   * @param params
   */
  /*protected void doParams(Properties params) {
    if (params == null) {return;}
    Iterator<Entry<Object, Object>> it = params.entrySet().iterator();
    while (it.hasNext()) {
      Entry<Object, Object> entry = it.next();
      app.setParam((String)entry.getKey(), (String)entry.getValue());
    }
  }*/
  
  /**
   * Find a single node in the composite structure.
   * @param node
   * @param targetXhcName
   * @return
   */
  protected IXholon find(IXholon node, String targetXhcName) {
    if (node == null) {return null;}
    if (targetXhcName == null) {return null;}
    if (targetXhcName.equals(node.getXhcName())) {return node;}
    IXholon childNode = node.getFirstChild();
    while (childNode != null) {
      IXholon foundNode = find(childNode, targetXhcName);
      if (foundNode != null) {
        return foundNode;
      }
      childNode = childNode.getNextSibling();
    }
    return null;
  }
  
  /**
   * Do a JFreeChart line chart, and save it as a temporary PNG file.
   * @return The JFreeChart name of the PNG file.
   */
  protected String doLineChart() {
    Object chart = getLineChart();
    if (chart == null) {return null;}
    String chartFilename = null;
    /*ServletUtilities.getTempFilePrefix();
    ServletUtilities.getTempOneTimeFilePrefix();
    try {
      chartFilename = ServletUtilities.saveChartAsPNG((JFreeChart)chart, 1000, 700, null);
    } catch (IOException e) {
      chartFilename = null;
    }*/
    return chartFilename;
  }
  
  /**
   * Get a JFreeChart line chart.
   * @return A JFreeChart object, ready to be displayed.
   */
  public Object getLineChart() {
    if (!app.getUseDataPlotter()) {return null;}
    if (!app.getUseJFreeChart()) {return null;}
    IChartViewer chartViewer = app.getChartViewer();
    if (chartViewer == null) {return null;}
    return chartViewer.createChart(true);
  }

  /**
   * Provide help information.
   * @param helpParam 
   * @param outArg 
   */
  public void help(String helpParam) { //, PrintWriter outArg) {
    /*if (outArg == null) {
      out = new PrintWriter(System.out, true);
    }
    else {
      out = outArg;
    }*/
    if (sbHelp == null) {sbHelp = new StringBuilder();}
    sbHelp.append("<html>");
    sbHelp.append("<head><title>Xholon Help</title></head>");
    sbHelp.append("<body>");
    sbHelp.append("<h1>Xholon Help - List of external formats</h1>");
    // return list of external formats
    String[] efList = new ExternalFormatService().getActionList();
    int i = 0;
    sbHelp.append("<pre>");
    for (i = 0; i < efList.length; i++) {
      if (i > 0) {
        sbHelp.append(",");
      }
      sbHelp.append(efList[i]);
    }
    sbHelp.append("</pre>");
    for (i = 0; i < efList.length; i++) {
      sbHelp.append("<p>" + efList[i] + "</p>");
    }
    sbHelp.append("</body>");
    sbHelp.append("</html>");
    this.consoleLog(sbHelp.toString());
  }
  
  /**
   * Escape XML and other special characters embedded in the HTML.
   * @param str The String that needs to be escaped.
   * @return The result String.
   */
  protected String escape(String str) {
    if (str == null || str.length() == 0) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < str.length(); ++i) {
      char c = str.charAt(i);
      switch (c) {
      case '<':
        sb.append("&lt;");
        break;
      case '>':
        sb.append("&gt;");
        break;
      case '&':
        sb.append("&amp;");
        break;
      case '"':
        sb.append("&quot;");
        break;
      case '\'':
        sb.append("&apos;");
        break;
      default:
        sb.append(c);
        break;
      }
    }
    return sb.toString();
  }
  
  /**
   * Send a synchronous message to the XholonHelperService.
   * @param signal
   * @param data
   * @param sender
   * @return A response message.
   */
  protected IMessage sendXholonHelperService(int signal, Object data, IXholon sender)
  {
    // send the request to the XholonHelperService by sending it a sync message
    if (xholonHelperService == null) {
      xholonHelperService = app.getService(IXholonService.XHSRV_XHOLON_HELPER);
    }
    if (xholonHelperService == null) {
      return null;
    }
    else {
      if (sender == null) {sender = app;}
      return xholonHelperService.sendSyncMessage(signal, data, sender);
    }
  }
  
  /**
   * Is the specified csh on the white list?
   * @param cshIn
   * @return
   */
  protected boolean isWhiteListed(String cshIn) {
    if (cshIn == null) {return false;}
    for (int i = 0; i < defaultCshWhitelist.length; i++) {
      if (cshIn.startsWith(defaultCshWhitelist[i])) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * Validate this XML content against a schema?
   * @param xmlString An XML String.
   * @return true or false
   */
  protected String validateXmlString(String xmlString) {
    String schemaName = null;
    String rootName = null;
    // find root node; first bytes are "<?xml"; there may also be comments
    // and processing instructions and doc types (start with "<!" or "<?")
    int pos = 0;
    int len = xmlString.length();
    while (rootName == null) {
      pos = xmlString.indexOf('<', pos);
      if (pos == -1) {return "Valid root node not found";}
      pos++;
      if (pos >= len) {return "XML has unterminated bracket";}
      if (xmlString.charAt(pos) == '?') {continue;}
      if (xmlString.charAt(pos) == '!') {continue;}
      // found a candidate root node
      rootName = xmlString.substring(pos, xmlString.indexOf('>', pos));
    }
    if ((rootName == null) || (rootName.length() == 0)) {return "No candidate root node could be found";}
    for (int i = 0; i < defaultValidatableWhitelist.length; i++) {
      if (rootName.equals(defaultValidatableWhitelist[i])) {
        schemaName = DEFAULT_SCHEMA_PREFIX + rootName + DEFAULT_SCHEMA_SUFFIX;
        break;
      }
    }
    if (schemaName == null) {return "No schema available for " + rootName;}
    /*IXholon xmlValidator = new XmlValidationService().getService(IXholonService.XHSRV_XML_VALIDATION);
    String[] data = {
      //"./src/org/primordion/user/app/petrinet/feinberg1/csh02.xsd",
      //"./dtd/helloworld.dtd",
      //"./xsd/HelloWorldSystem.xsd",
      schemaName,
      xmlString};
    IMessage msg = xmlValidator.sendSyncMessage(IXholonXmlValidator.SIG_VALIDATE_REQ, data, null);
    if (IXholonXmlValidator.XML_IS_VALID.equals(msg.getData())) {
      // the XML source is valid
      return null;
    }
    else {
      // the XML source is invalid
      return (String)msg.getData();
    }*/
    return null;
  }
  
  /**
   * Validate this XML content against a schema?
   * @param xmlString A URI beginning with "http".
   * @return true or false
   */
  protected String validateXmlUriContent(String uri) {
    /*String xmlString = "";
    InputStream in = null;
    try {
      in = new URL(uri).openStream();
      InputStreamReader inR = new InputStreamReader(in);
      BufferedReader buf = new BufferedReader(inR);
      String line;
      while ( ( line = buf.readLine() ) != null ) {
        xmlString += line + "\n";
      }
      in.close();
      if (xmlString.length() > 0) {
        return validateXmlString(xmlString);
      }
    } catch (MalformedURLException e) {
      Xholon.getLogger().error("", e);
    } catch (IOException e) {
      Xholon.getLogger().error("", e);
    }*/
    return "No content returned for URI " + uri;
  }
  
  /**
   * Write out a usage example.
   */
  public void usage() {
    consoleLog("HtmlApplication usage");
    consoleLog(" -className org.primordion.user.app.helloworldjnlp.AppHelloWorld");
    consoleLog(" -configFileName /org/primordion/user/app/helloworldjnlp/HelloWorld_xhn.xml");
    consoleLog(" -ef Xml,Yuml,Newick");
    consoleLog(" -act AnalysisPetriNet_Show+python+script,AnalysisCRN_Show+all");
    consoleLog(" -MaxProcessLoops 100");
  }
  
  protected native void consoleLog(String str) /*-{
    $wnd.console.log(str);
  }-*/;

  /**
   * Entry point when running this as a desktop or JNLP app.
   * @param args
   */
  /*public static void main(String[] args) {
    HtmlApplication htmlApp = new HtmlApplication();
    htmlApp.gwtMain(args);
  }*/
  
  /**
   * Entry point for GWT.
   */
  public void gwtMain(String[] args) {
    String className = null;
    String configFileName = null;
    String csh = null;
    String ef = null;
    String ed = null;
    String act = null;
    //Properties params = null;
    boolean help = false;
    // get command line arguments
    if ((args != null) && (args.length > 0)) {
      int i = 0;
      String arg;
      while ((i < args.length) && (args[i].startsWith("-"))) {
        arg = args[i++];
        if (arg.equals("-className")) {
          if (i < args.length) {
            className = args[i++];
          }
        } else if (arg.equals("-configFileName")) {
          if (i < args.length) {
            configFileName = args[i++];
          }
        } else if (arg.equals("-csh")) {
          if (i < args.length) {
            csh = args[i++];
          }
        } else if (arg.equals("-ef")) {
          if (i < args.length) {
            ef = args[i++];
          }
        } else if (arg.equals("-ed")) {
          if (i < args.length) {
            ed = args[i++];
          }
        } else if (arg.equals("-act")) {
          if (i < args.length) {
            act = args[i++];
          }
        } else if (arg.equals("-helpEf")) {
          this.help("ef");
          return;
        } else if (arg.equals("--help") || arg.equals("-h")) {
          help = true;
          break;
        } else {
          //Xholon.getLogger().warn("Unknown command line argument found in HtmlApplication main: " + arg);
          //if (params == null) {
          //  params = new Properties();
          //}
          // ex: -MaxProcessLoops 100
          //params.setProperty(arg.substring(1), args[i++]);
        }
      }
      if (help) {
        this.usage();
      }
      else {
        // String className, IApplication appArg, String configFileName, String cshArg, String ef, String ed, String act
        className = null; //"org.primordion.user.app.helloworldjnlp.AppHelloWorld";
        IApplication xhapp = (IApplication)GWT.create(org.primordion.user.app.helloworldjnlp.AppHelloWorld.class);
        configFileName = GWT.getHostPageBaseURL() + "config/helloworldjnlp/HelloWorld_xhn.xml";
        //csh = "org/primordion/user/app/helloworldjnlp/CompositeStructureHierarchy.xml";
        ef = "Newick";
        this.doGet(className, xhapp, configFileName, csh, ef, ed, act);
      }
    }
  }

  public int getMaxEf() {
    return maxEf;
  }

  public void setMaxEf(int maxEf) {
    this.maxEf = maxEf;
  }

}

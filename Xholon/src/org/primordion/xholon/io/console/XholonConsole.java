/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2009 Ken Webb
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

package org.primordion.xholon.io.console;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.TextAreaElement;
//import com.google.gwt.event.dom.client.ClickEvent;
//import com.google.gwt.event.dom.client.ClickHandler;
//import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
//import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
//import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
//import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
//import com.google.gwt.user.client.Element;

import java.util.List;

//import org.primordion.xholon.app.Application;
import org.primordion.xholon.app.IApplication;
//import org.primordion.xholon.base.Control;
//import org.primordion.xholon.base.IControl;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IQueue;
import org.primordion.xholon.base.IReflection;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.Queue;
import org.primordion.xholon.base.ReflectionFactory;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.io.Specials;
import org.primordion.xholon.io.ISwingEntity;
import org.primordion.xholon.io.XholonGwtTabPanelHelper;
//import org.primordion.xholon.io.xml.IXml2Xholon;
//import org.primordion.xholon.io.xml.Xml2Xholon;
//import org.primordion.xholon.io.xml.Xml2XholonClass;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.NodeSelectionService;
import org.primordion.xholon.service.XholonHelperService;
//import org.primordion.xholon.util.MiscIo;

/**
 * Provide an Xholon console capability for interacting with a Xholon application.
 * A XholonConsole can be opened for any node in the Xholon tree.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 15, 2009)
 */
public class XholonConsole extends XholonWithPorts implements IXholonConsole {
  
  interface XhConsUiBinder extends UiBinder<Widget, XholonConsole> {}
  private static XhConsUiBinder uiBinder = GWT.create(XhConsUiBinder.class);
  
  // Special menu
  @UiField MenuItem special;
  
  // History menu
  @UiField MenuItem history;
  
  // File menu
  @UiField MenuItem fileopen;
  @UiField MenuItem filesave;
  @UiField MenuItem filesaveas;
  @UiField MenuItem closeguiMI;
  
  // Edit menu
  @UiField MenuItem clearcommand;
  @UiField MenuItem increasefontsize;
  @UiField MenuItem decreasefontsize;
  
  // Mode menu
  @UiField MenuItem modedefault;
  @UiField MenuItem modexpath;
  @UiField MenuItem modejs;
  @UiField MenuItem modeattr;
  @UiField MenuItem terminal;
  
  // Help menu
  @UiField MenuItem help;
  @UiField MenuItem xpath;
  @UiField MenuItem jsscript;
  @UiField MenuItem behaviorScript;
  @UiField MenuItem behaviorScriptProto;
  @UiField MenuItem behaviorScriptInstance;
  
  @UiField MenuItem submit;
  
  // buttons
  //@UiField Button submit;
  //@UiField Button closeguiB;
  
  @UiField HTML commandPane;
  @UiField TextAreaElement commandPaneTAE;
  
  /** Path and name of the Inheritance Hierarchy XML file. */
  //private String xmlIhFilePathAndName = "./config/console/XholonConsole_InheritanceHierarchy.xml";
  
  /** Path and name of the Class Details XML file. */
  //private String xmlCdFilePathAndName = "./config/console/XholonConsole_ClassDetails.xml";
  
  /** Path and name of the Composite Structure Hierarchy XML file. */
  //private String xmlFilePathAndName = "./config/console/XholonConsole_CompositeStructureHierarchy.xml";
  
  /** The Application that the context node is running in. */
  private IApplication app = null;
  
  /** The Xholon node that the XholonConsole node is associated with. */
  IXholon context = null;
  
  /** The GUI pane where the user enters commands and text. */
  //private IXholon commandPane = null;
  
  /** The JLabel of the GUI pane where the user enters commands and text. */
  //private IXholon commandPaneLabel = null;
  
  /** The GUI pane where the user can view the results of a command. */
  //private IXholon resultPane = null;
  
  /** The GUI JFrame. */
  //private IXholon frame = null;
  
  /**
   * The GUI has one file open at a time.
   * If a file has already been opened during the lifetime of this XholonConsole instance,
   * then the latest such file will remain available for reuse, such as for the Save operation. */
  //private File latestOpenedFile = null;
  
  /** The name of the context node. */
  private String roleName = null;
  
  /**
   * A service that provides access to the XholonConsole.
   */
  protected XholonHelperService xholonHelperService = null;
  
  /**
   * A service that will keep track of which IXholon node(s) are currently selected.
   * This will allow other services or xholons to act on those nodes.*/
  protected IXholon nodeSelectionService = null;
  
  /**
   * An escape character that can be used as the first character of text.
   * It allows URIs such as "http:" to escape being interpreted as modes.
   * Enter a URI as ":http://www.example.com".
   */
  protected static final char MODE_ESCAPE = ':';
  
  /**
   * The value "default:" is only used when changing the mode to "".
   */
  protected static final String MODE_DEFAULT = "default:";
  
  /**
   * Possible values for mode.
   */
  protected String[] modes = {MODE_DEFAULT,"xpath:","js:","attr:"};
  
  /**
   * User friendly labels for the different modes.
   */
  protected String[] modeLabels = {
      "all features",
      "XPath navigation",
      "JavaScript scripting",
      "Attribute set and get"};
  
  /**
   * The language that the console is currently able to parse and respond to.
   * The mode can be set by selecting from a menu,
   * or through a command that consists of just the mode String (ex: "xpath:").
   * ex: ""default "xpath:" "js:" "attr:"
   * 
   */
  protected String mode = "";
  
  /**
   * An output device.
   */
  //protected IXholonConsole console = null;
  
  /**
   * The HTML widget that implements the XholonConsole.
   */
  protected Widget widget = null;
  
  protected static final int SENDMESSAGE_ASYNC = 1;
  protected static final int SENDMESSAGE_SYNC  = 2;
  /**
   * Whether to call sendMessage() or sendSyncMessage().
   * 1 async (the default)
   * 2 sync  (used with Avatar)
   */
  protected int sendMsgAsyncOrSync = SENDMESSAGE_ASYNC;
  
  /**
   * Whether or not the console is acting as a terminal.
   */
  protected boolean terminalEnabled = false;
  
  /**
   * History circular queue.
   */
  protected IQueue historyQ = null;
  
  /**
   * Maximum size of the history circular queue.
   */
  protected int historyQSize = 20;
  
  /**
   * constructor
   */
  public XholonConsole() {}
  
  /**
   * constructor
   */
  public XholonConsole(IApplication app, IXholon context)
  {
    this.app = app;
    this.context = context;
  }
  
  /*
   * @see org.primordion.xholon.base.Xholon#getRoleName()
   */
  public String getRoleName() {
    return roleName;
  }

  /*
   * @see org.primordion.xholon.base.Xholon#setRoleName(java.lang.String)
   */
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  /*
   * @see org.primordion.xholon.io.xquerygui.IXQueryGui#getXmlIhFilePathAndName()
   */
  //public String getXmlIhFilePathAndName() {
  //  return xmlIhFilePathAndName;
  //}

  /*
   * @see org.primordion.xholon.io.xquerygui.IXQueryGui#setXmlIhFilePathAndName(java.lang.String)
   */
  //public void setXmlIhFilePathAndName(String xmlIhFilePathAndName) {
  //  this.xmlIhFilePathAndName = xmlIhFilePathAndName;
  //}

  /*
   * @see org.primordion.xholon.io.xquerygui.IXQueryGui#getXmlCdFilePathAndName()
   */
  //public String getXmlCdFilePathAndName() {
  //  return xmlCdFilePathAndName;
  //}

  /*
   * @see org.primordion.xholon.io.xquerygui.IXQueryGui#setXmlCdFilePathAndName(java.lang.String)
   */
  //public void setXmlCdFilePathAndName(String xmlCdFilePathAndName) {
  //  this.xmlCdFilePathAndName = xmlCdFilePathAndName;
  //}

  /*
   * @see org.primordion.xholon.io.xquerygui.IXQueryGui#getXmlFilePathAndName()
   */
  //public String getXmlFilePathAndName() {
  //  return xmlFilePathAndName;
  //}

  /*
   * @see org.primordion.xholon.io.xquerygui.IXQueryGui#setXmlFilePathAndName(java.lang.String)
   */
  //public void setXmlFilePathAndName(String xmlFilePathAndName) {
  //  this.xmlFilePathAndName = xmlFilePathAndName;
  //}

  /*
   * @see org.primordion.xholon.io.xquerygui.IXQueryGui#getContext()
   */
  public IXholon getContext() {
    return context;
  }

  /*
   * @see org.primordion.xholon.io.xquerygui.IXQueryGui#setContext(org.primordion.xholon.base.IXholon)
   */
  public void setContext(IXholon context) {
    this.context = context;
  }

  /*
   * @see org.primordion.xholon.io.console.IXholonConsole#getCommandPane()
   */
  public IXholon getCommandPane() {
    return null; //commandPane;
  }

  /*
   * @see org.primordion.xholon.io.console.IXholonConsole#setCommandPane(org.primordion.xholon.base.IXholon)
   */
  public void setCommandPane(IXholon commandPane) {
    //this.commandPane = commandPane;
  }

  /*
   * @see org.primordion.xholon.io.xquerygui.IXQueryGui#getResultPane()
   */
  //public IXholon getResultPane() {
  //  return resultPane;
  //}

  /*
   * @see org.primordion.xholon.io.xquerygui.IXQueryGui#setResultPane(org.primordion.xholon.base.IXholon)
   */
  //public void setResultPane(IXholon resultPane) {
  //  this.resultPane = resultPane;
  //}

  /*
   * @see org.primordion.xholon.io.xquerygui.IXQueryGui#getLatestOpenedFile()
   */
  //public File getLatestOpenedFile() {
  //  return latestOpenedFile;
  //}

  /*
   * @see org.primordion.xholon.io.xquerygui.IXQueryGui#setLatestOpenedFile(java.io.File)
   */
  //public void setLatestOpenedFile(File latestOpenedFile) {
  //  this.latestOpenedFile = latestOpenedFile;
  //}
  
  public int getSendMsgAsyncOrSync() {
    return sendMsgAsyncOrSync;
  }
  
  public void setSendMsgAsyncOrSync(int sendMsgAsyncOrSync) {
    this.sendMsgAsyncOrSync = sendMsgAsyncOrSync;
  }

  /*
   * @see org.primordion.xholon.base.Xholon#getApp()
   */
  public IApplication getApp() {
    return app;
  }
  
  /*
   * @see org.primordion.xholon.base.Xholon#setApp(org.primordion.xholon.app.IApplication)
   */
  public void setApp(IApplication app) {
    this.app = app;
  }

  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    if (mode.equals(MODE_DEFAULT)) {
      this.mode = "";
    }
    else {
      this.mode = mode;
    }
    //setTabHeader();
  }

  //public IXholonConsole getConsole() {
  //  return console;
  //}

  //public void setConsole(IXholonConsole console) {
  //  this.console = console;
  //}
  
  public Object getWidget() {
    return widget;
  }

  public boolean isTerminalEnabled() {
    return terminalEnabled;
  }
  
  public void setTerminalEnabled(boolean terminalEnabled) {
    this.terminalEnabled = terminalEnabled;
  }
  
  /*
   * @see org.primordion.xholon.base.XholonWithPorts#postConfigure()
   */
  public void postConfigure()
  {
    // set various Xholon attributes for XholonConsole
    IXholonClass ixc = app.getClassNode("XholonConsole");
    if (ixc != null) {
      setXhc(ixc);
    }
    setId(app.getNextId());
    setRoleName(context.getName());
    
    if (context.getXhc().hasAncestor("Avatar")) {
      setTerminalEnabled(true);
      sendMsgAsyncOrSync = SENDMESSAGE_SYNC;
    }
    
    historyQ = new Queue(historyQSize);
    
    // append this new node to the View
    /*
    IXholon view = app.getAppRoot().getFirstChild();
    while (view != null) {
      if ("View".equals(view.getRoleName())) {
        break;
      }
      view = view.getNextSibling();
    }
    if (view != null) {
      IXholon viewXholonConsole = view.getFirstChild();
      while (viewXholonConsole != null) {
        if ("XholonConsoles".equals(viewXholonConsole.getRoleName())) {
          break;
        }
        viewXholonConsole = viewXholonConsole.getNextSibling();
      }
      if (viewXholonConsole == null) {
        // create a container XholonConsole node
        viewXholonConsole =
          ((IControl)view).setControlNode("XholonConsoles", (Control)view, view.getXhc());
      }
      appendChild(viewXholonConsole);
    }
    */
    readGuiFromXml();
    
    // Special menu
    String specialXml = app.rcConfig("special", app.findGwtClientBundle());
    if (specialXml != null) {
      //node.consoleLog("special.xml");
      //node.consoleLog(specialXml);
      final Specials specials = new Specials();
      specials.xmlString2Items(specialXml, this);
      String[] moreSpecialItems = specials.getActionList();
      if (moreSpecialItems != null) {
        MenuBar specialSubMenu = new MenuBar(true);
        for (int i = 0; i < moreSpecialItems.length; i++) {
          final String actionName = moreSpecialItems[i];
          specialSubMenu.addItem(actionName, new Command() {
            @Override
            public void execute() {
              //context.consoleLog("execute() " + actionName);
              specials.doAction(actionName);
            }
          });
        }
        special.setSubMenu(specialSubMenu);
      }
    }
    else {
      //special.setEnabled(false);
      special.getParentMenu().removeItem(special);
    }
    
    // History menu
    // TODO History menu is only updated if I click on "History"
    // TODO don't add duplicates to the historyQ
    history.setScheduledCommand(new Command() {
	    @Override
      public void execute() {
        if (historyQ.getSize() > 0) {
          MenuBar historySubMenu = new MenuBar(true);
          List<String> hitems = (List<String>)historyQ.getItems();
          for (int i = 0; i < hitems.size(); i++) {
            final String hitem = hitems.get(i);
            historySubMenu.addItem(limitLength(hitem, 80), new Command() {
              @Override
              public void execute() {
                if (isTerminalEnabled()) {
                  // replace the content of the last line with hitem
                  String taval = getCommand();
                  int lastNewlinePos = taval.lastIndexOf("\n");
                  if (lastNewlinePos == -1) {
                    setResult(hitem, true);
                  }
                  else {
                    setResult(taval.substring(0, lastNewlinePos+1) + hitem, true);
                  }
                }
                else {
                  // replace entire console contents with hitem
                  setResult(hitem, true);
                }
              }
            });
          }
          history.setSubMenu(historySubMenu);
        }
      }
    });
    
    // File menu
    fileopen.setEnabled(false);
    filesave.setEnabled(false);
    filesaveas.setEnabled(false);
    closeguiMI.setEnabled(false);
    
    // Edit menu
    
    clearcommand.setScheduledCommand(new Command() {
	    @Override
      public void execute() {
        clearCommand();
      }
    });
    
    increasefontsize.setScheduledCommand(new Command() {
	    @Override
      public void execute() {
        changeFontSize(2.0);
      }
    });
    
    decreasefontsize.setScheduledCommand(new Command() {
	    @Override
      public void execute() {
        changeFontSize(-2.0);
      }
    });
    
    // Mode menu
    //modedefault.setEnabled(true);
    //modexpath.setEnabled(true);
    //modejs.setEnabled(true);
    modeattr.setEnabled(false);
    
    modedefault.setScheduledCommand(new Command() {
	    @Override
      public void execute() {
        setMode("default:");
      }
    });
    
    modexpath.setScheduledCommand(new Command() {
	    @Override
      public void execute() {
        setMode("xpath:");
      }
    });
    
    modejs.setScheduledCommand(new Command() {
	    @Override
      public void execute() {
        setMode("js:");
      }
    });
    
    modeattr.setScheduledCommand(new Command() {
	    @Override
      public void execute() {
        setMode("attr:");
      }
    });
    
    terminal.setScheduledCommand(new Command() {
	    @Override
      public void execute() {
        terminalEnabled = !terminalEnabled;
      }
    });
    
    // Help menu
    
    help.setScheduledCommand(new Command() {
	    @Override
      public void execute() {
        help();
      }
    });
    
    xpath.setScheduledCommand(new Command() {
	    @Override
      public void execute() {
        xpathExamples();
      }
    });
    
    jsscript.setScheduledCommand(new Command() {
	    @Override
      public void execute() {
        jsScript();
      }
    });
    
    behaviorScript.setScheduledCommand(new Command() {
	    @Override
      public void execute() {
        behaviorScript();
      }
    });
    
    behaviorScriptProto.setScheduledCommand(new Command() {
	    @Override
      public void execute() {
        behaviorScriptProto();
      }
    });
    
    behaviorScriptInstance.setScheduledCommand(new Command() {
	    @Override
      public void execute() {
        behaviorScriptInstance();
      }
    });
    
    // handle the submit MenuItem
    submit.setScheduledCommand(new Command() {
	    @Override
      public void execute() {
        submit();
      }
    });
    
    // TextArea  handle ENTER key
    TextArea.wrap(commandPaneTAE).addKeyDownHandler(new KeyDownHandler() {
      @Override
      public void onKeyDown(KeyDownEvent event) {
         if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
            if (isTerminalEnabled()) {
              submit();
            }
         }
      }
    });
  }
  
  /**
   * Submit a command for processing, and write out the result.
   */
  protected void submit() {
    if (xholonHelperService == null) {
      xholonHelperService = (XholonHelperService)app
        .getService(IXholonService.XHSRV_XHOLON_HELPER);
    }
    String result = doXholonConsoleCommandOrText(getCommand());
    if (result == null) {
      result = "";
    }
    else {
      setResult(result, false);
    }
  }
  
  /*
   * @see org.primordion.xholon.base.Xholon#processReceivedMessage(org.primordion.xholon.base.IMessage)
   */
  public void processReceivedMessage(IMessage msg)
  {
    switch (msg.getSignal()) {
    case ISwingEntity.SWIG_ACTION_EVENT: // -1001 JButton JMenuItem
      
      if (msg.getData() == null) {return;}
      
      // Submit Command or Text
      if ("submit".equals(msg.getData())) {
        submit();
      }
      
      // Edit
      else if ("clearresults".equals(msg.getData())) {
        clearResults();
      }
      else if ("clearcommand".equals(msg.getData())) {
        clearCommand();
      }
      else if ("increasefontsize".equals(msg.getData())) {
        changeFontSize(2.0);
      }
      else if ("decreasefontsize".equals(msg.getData())) {
        changeFontSize(-2.0);
      }
      
      // File
      else if ("fileopen".equals(msg.getData())) {
        openQueryFile();
      }
      else if ("filesave".equals(msg.getData())) {
        saveCommandFile();
      }
      else if ("filesaveas".equals(msg.getData())) {
        saveCommandFileAs();
      }
      else if ("closegui".equals(msg.getData())) {
        closeGui();
      }
      
      // Help
      else if ("help".equals(msg.getData())) {
        help();
      }
      else if ("xpath".equals(msg.getData())) {
        xpathExamples();
      }
      else if ("jsscript".equals(msg.getData())) {
        jsScript();
      }
      else if ("behaviorScript".equals(msg.getData())) {
        behaviorScript();
      }
      else if ("behaviorScriptProto".equals(msg.getData())) {
        behaviorScriptProto();
      }
      else if ("behaviorScriptInstance".equals(msg.getData())) {
        behaviorScriptInstance();
      }
      else if (((String)msg.getData()).startsWith("mode")) {
        setMode(((String)msg.getData()).substring(4));
      }
      else {
        //System.out.println(msg.getData());
      }
      break;
    case ISignal.SIGNAL_XHOLON_CONSOLE_RSP:
      Object dataObj = msg.getData();
      if (dataObj == null) {
        //setResult("null");
      }
      else {
        setResult(dataObj.toString(), false);
      }
      break;
    default:
      //System.out.println(msg.getSignal());
      break;
    }
  }
  
  @Override
  public void doAction(String action) {
    // clear the console, write action to the console
    setCommand(action, true);
    // submit
    String result = doXholonConsoleCommandOrText(getCommand());
    // write result to the console
    if (result == null) {
      result = "";
    }
    else {
      setResult(result, false);
    }
  }

  /**
   * Provide the user with some brief help information.
   */
  protected void help()
  {
    StringBuilder sb = new StringBuilder()
    .append("Type one of the following into the console window:\n")
    .append("* An xpath expression, to navigate to another node,\n")
    .append("  xpath:Hello\n")
    .append("* An XML string that will be pasted in as new content,\n")
    .append("  <World/>\n")
    .append("* \"attributes\" to see a list for this node,\n")
    .append("* \"avatar\" to create a command-driven avatar,\n")
    //.append("* A URI preceeded by a colon\n")
    //.append("  :https://raw.github.com/gist/3374405/xholonWorkbook.xml\n")
    .append("* One of the following modes,\n");
    for (int i = 0; i < modes.length; i++) {
      sb.append("  ").append(modes[i]);
    }
    sb.append("\n");
    sb.append("* Some other string that's meaningful to the current app.\n")
    .append("Then click the Submit button.\n")
    //.append("Alternatively, highlight text already in this console window.")
    ;
    
    // node-specific actions
    String[] al = context.getActionList();
    if (al != null) {
      sb.append("\n\nActions specific to this node:\n");
      for (int i = 0; i < al.length; i++) {
        sb.append(al[i]).append("\n");
      }
    }
    else {sb.append("\n");}
    
    // mode
    String currentMode = mode;
    if (currentMode.length() == 0) {
      currentMode = MODE_DEFAULT;
    }
    sb.append("The current mode is ")
    .append(currentMode)
    .append(" which enables the use of ");
    for (int i = 0; i < modes.length; i++) {
      if (currentMode.equals(modes[i])) {
        sb.append(modeLabels[i]);
        break;
      }
    }
    sb.append(".\n");
    
    setResult(sb.toString(), true);
  }
  
  /**
   * Provide the user with a JavaScript starting point.
   */
  protected void jsScript()
  {
    StringBuilder sb = new StringBuilder()
    .append("<script><![CDATA[\n")
    //.append("$wnd.console.log(\"Testing 1 2 3\");\n")
    .append("$wnd.xh.root().println(\"Testing 1 2 3\");\n")
    .append("]]></script>\n");
    setResult(sb.toString(), true);
  }
  
  /**
   * Provide the user with XPath examples.
   */
  protected void xpathExamples()
  {
    StringBuilder sb = new StringBuilder()
    .append("Sample XPath expressions that change the context node.\n")
    .append("Parent: xpath ..\n")
    .append("Ancestor: xpath ancestor::Bestiary\n")
    .append("First child: xpath *\n")
    .append("Child: xpath Cat\n")
    .append("Descendant: xpath descendant::Dog\n")
    .append("Descendant of root: xpath /descendant::Termite\n")
    .append("Descendant of ancestor: xpath ancestor::Bestiary/descendant::Termite\n")
    .append("Following sibling: xpath following-sibling::*\n")
    .append("Preceding sibling: xpath preceding-sibling::*\n")
    .append("Path: xpath /descendant::Grid/Row[50]/HabitatCell[50]\n")
    .append("Last: xpath /descendant::Grid/Row[last()]/HabitatCell[last()]\n")
    .append("Find by name (Licorice): xpath /descendant-or-self::*[@name='cat_2']\n");
    setResult(sb.toString(), true);
  }
  
  /**
   * Provide the user with a org.primordion.xholon.base.Behavior_gwtjs starting point.
   * JavaScript (js) is the default language for writing behaviors.
   * <p>lang = "gwtjs" </p>
   */
  protected void behaviorScript()
  {
    //String lang = "gwtjs";
    //String commentStr = "//";
    String xhcName = context.getXhcName();
    String me = "my" + xhcName;
    StringBuilder sbEx = new StringBuilder()
    .append("var ").append(me).append(", testing;\n")
    .append("var beh = {\n")
    .append("  postConfigure: function() {\n")
    .append("    ").append(me).append(" = this.cnode.parent();\n")
    .append("    testing = Math.floor(Math.random() * 10);\n")
    .append("  },\n")
    .append("  act: function() {\n")
    .append("    ").append(me).append(".println(this.toString());\n")
    .append("  },\n")
    .append("  toString: function() {\n")
    .append("    return \"testing:\" + testing;\n")
    .append("  }\n")
    .append("}\n")
    ;
    
    /*String prefix = "Xing";
    StringBuilder sb = new StringBuilder()
    .append("<")
    .append(prefix)
    .append(xhcName)
    .append("behavior implName=\"org.primordion.xholon.base.Behavior_gwtjs\"")
    .append("><![CDATA[\n")
    .append(sbEx.toString())
    .append("]]></")
    .append(prefix)
    .append(xhcName)
    .append("behavior>\n");*/
    setResult(wrapBehaviorScript(sbEx.toString(), "Xing", xhcName), true);
  }
  
  /**
   * Provide the user with a org.primordion.xholon.base.Behavior_gwtjs starting point,
   * that uses a JavaScript prototype.
   * JavaScript (js) is the default language for writing behaviors.
   * <p>lang = "gwtjs" </p>
   */
  protected void behaviorScriptProto()
  {
    String wndXh = "$wnd.xh.";
    String xhcName = context.getXhcName();
    String protoName = xhcName + "behavior";
    String cnodeParentName = "" + Character.toLowerCase(xhcName.charAt(0))
      + (xhcName.length() == 0 ? "" : xhcName.substring(1));
    StringBuilder sbEx = new StringBuilder()
    .append(wndXh).append(protoName).append(" = function ").append(protoName).append("() {}\n\n")
    
    // postConfigure
    .append(wndXh).append(protoName).append(".prototype.postConfigure = function() {\n")
    .append("  this.").append(cnodeParentName).append(" = this.cnode.parent();\n")
    .append("  this.testing = Math.floor(Math.random() * 10);\n")
    .append("};\n\n")
    
    // act
    .append(wndXh).append(protoName).append(".prototype.act = function() {\n")
    .append("  this.").append(cnodeParentName).append(".println(this.toString());\n")
    .append("};\n\n")
    
    // toString
    .append(wndXh).append(protoName).append(".prototype.toString = function() {\n")
    .append("  ").append("return \"testing: \" + this.testing;\n")
    .append("};\n\n")
    ;
    
    setResult(wrapBehaviorScript(sbEx.toString(), "", xhcName), true);
  }
  
    /**
   * Provide the user with a org.primordion.xholon.base.Behavior_gwtjs starting point,
   * that uses a JavaScript prototype instance.
   * JavaScript (js) is the default language for writing behaviors.
   * <p>lang = "gwtjs" </p>
   */
  protected void behaviorScriptInstance()
  {
    String wndXh = "$wnd.xh.";
    String xhcName = context.getXhcName();
    String protoName = xhcName + "behavior";
    //String cnodeParentName = "" + Character.toLowerCase(xhcName.charAt(0))
    //  + (xhcName.length() == 0 ? "" : xhcName.substring(1));
    StringBuilder sbEx = new StringBuilder()
    .append("var beh = new ").append(wndXh).append(protoName).append("();\n")
    ;
    setResult(wrapBehaviorScript(sbEx.toString(), "", xhcName), true);
  }
  
  /**
   * Wrap the behavior script in an XML tag.
   * @param jsContent The JavaScript content.
   * @param prefix An optional prefix for the XML tag name.
   * @param xhcName The XholonClass name.
   * @return The complete XML wrapper with the wrapped JavaScript.
   */
  protected String wrapBehaviorScript(String jsContent, String prefix, String xhcName) {
    StringBuilder sb = new StringBuilder()
    .append("<")
    .append(prefix)
    .append(xhcName)
    .append("behavior implName=\"org.primordion.xholon.base.Behavior_gwtjs\"")
    .append("><![CDATA[\n")
    .append(jsContent)
    .append("]]></")
    .append(prefix)
    .append(xhcName)
    .append("behavior>\n");
    return sb.toString();
  }
  
  /**
   * Increase or decrease the font size of the command text pane.
   */
  protected void changeFontSize(double incDec)
  {
    Element pre = commandPane.getElement().getFirstChildElement();
    String fontSizeStr = pre.getStyle().getFontSize(); // ex: "12px"
    if (fontSizeStr.endsWith("px") && fontSizeStr.length() > 2) {
      double fontSize = Double.parseDouble(fontSizeStr.substring(0, fontSizeStr.length()-2));
      fontSize += incDec;
      pre.getStyle().setFontSize(fontSize, Unit.PX);
    }
  }
  
  /**
   * Do a XholonConsole command.
   * @param commandOrTextString A command or otherwise meaningful text string, or null.
   * This method trims leading and trailing white space.
   * @return A String that's meaningful to the caller, or null.
   */
  protected String doXholonConsoleCommandOrText(String commandOrTextString)
  {
    //context.println("doXholonConsoleCommandOrText1");
    if (commandOrTextString == null) {return "";}
    commandOrTextString = commandOrTextString.trim();
    //context.println(commandOrTextString);
    if (commandOrTextString.length() == 0) {return "";}
    
    if (isTerminalEnabled()) {
      // the last line is the command
      int lastNewlinePos = commandOrTextString.lastIndexOf("\n");
      if (lastNewlinePos != -1) {
        commandOrTextString = commandOrTextString.substring(lastNewlinePos);
      }
      commandOrTextString += "\n";
    }
    
    if (commandOrTextString.endsWith(":")) {
      // this is probably a request to change the mode
      for (int i = 0; i < modes.length; i++) {
        if (commandOrTextString.equals(modes[i])) {
          mode = commandOrTextString;
          if (mode.equals(MODE_DEFAULT)) {
            mode = "";
          }
          setTabHeader();
          return null;
        }
      }
    }
    int firstColonPos = commandOrTextString.indexOf(":");
    if (commandOrTextString.charAt(0) == '<') { // TODO or .startsWith("xml:")
      rememberButton3Selection(context);
      // this is an XML String, either normal XML content or an XML script
      xholonHelperService.pasteLastChild(context, commandOrTextString);
      addToHistory(commandOrTextString);
      return null;
    }
    else if (commandOrTextString.charAt(0) == MODE_ESCAPE) {
      /* TODO
      // ":" is being used as an escape character
      // this is probably a URI such as "http://" "https://" "db:" etc
      // type these as ":http://" etc
      rememberButton3Selection(context);
      xholonHelperService.pasteLastChild(context, commandOrTextString.substring(1));
      */
      return null;
    }
    else if (commandOrTextString.startsWith("xpath")) {
      // "xpath " or "xpath:"
      if (commandOrTextString.length() < 7) {return null;}
      if (" :".indexOf(commandOrTextString.charAt(5)) == -1) {return null;}
      IXholon xpathResult = getXPath().evaluate(commandOrTextString.substring(6).trim(), context);
      if (xpathResult != null) {
        context = xpathResult;
        rememberButton3Selection(context);
        setTabHeader();
        //((JLabel)commandPaneLabel.getVal_Object()).setText(getXPath()
        //    .getExpression(context, app.getRoot(), false));
        addToHistory(commandOrTextString);
      }
      return null;
    }
    else if (commandOrTextString.startsWith("attr:")) {
      /* TODO allow this to work
      // simple one-line javascript setter/getter
      if (commandOrTextString.length() < 6) {return null;}
      String attrText = commandOrTextString.substring(5).trim();
      if (attrText.endsWith(";")) {
        attrText = attrText.substring(0, attrText.length()-1);
      }
      StringBuilder sb = new StringBuilder()
      .append("<script implName=\"lang:javascript:inline:\"><![CDATA[\n");
      if (attrText.startsWith("get")) {
        // attr:getMass(); OR attr:getMass()
        sb.append("println(contextNodeKey.").append(attrText).append(");\n");
      }
      else if (attrText.startsWith("is")) {
        sb.append("println(contextNodeKey.").append(attrText).append(");\n");
      }
      else if (attrText.startsWith("set")){
        // attr:setMass(123.456);
        sb.append("contextNodeKey.").append(attrText).append(";\n");
      }
      else if (attrText.indexOf("=") != -1) {
        // attr:mass = 9876.1 OR attr:mass=2.3;
        sb.append("contextNodeKey.").append(attrText).append(";\n");
      }
      else {
        // attr:mass
        sb.append("println(contextNodeKey.").append(attrText).append(");\n");
      }
      sb.append("]]></script>");
      rememberButton3Selection(context);
      xholonHelperService.pasteLastChild(context, sb.toString());
      */
      return null;
    }
    else if ((firstColonPos > 0) && (firstColonPos < 11)) {
      // allow length from "x:" to "javascript:"
      // this is probably the name of some scripting language
      String scrText = commandOrTextString.substring(firstColonPos+1).trim();
      StringBuilder sb = new StringBuilder()
      .append("<script><![CDATA[\n")
      .append(scrText)
      .append("]]></script>");
      //System.out.println(sb);
      rememberButton3Selection(context);
      xholonHelperService.pasteLastChild(context, sb.toString());
      return null;
    }
    else if (commandOrTextString.equalsIgnoreCase("attributes")) {
      IReflection ir = ReflectionFactory.instance();
      Object rowData[][] = null;
      rowData = ir.getAttributes(context);
      Object columnNames[] = new Object[2];
      columnNames[0] = "Name";
      columnNames[1] = "Value";
      StringBuilder sb = new StringBuilder();
      sb.append(columnNames[0]).append(": [").append(columnNames[1]+ "]\n");
      for (int i = 0; i < rowData.length; i++) {
        sb.append(rowData[i][0]).append(": [").append(rowData[i][1]+ "]\n");
      }
      return sb.toString();
    }
    else if (commandOrTextString.equalsIgnoreCase("avatar")) {
      // CSH <Avatar/>
      context.appendChild("Avatar", null, "org.primordion.xholon.base.Avatar");
      IXholon avatar = context.getLastChild();
      if ((avatar != null) && ("Avatar".equals(avatar.getXhcName()))) {
        avatar.postConfigure();
        context = avatar;
        rememberButton3Selection(context);
        setTabHeader();
        sendMsgAsyncOrSync = SENDMESSAGE_SYNC;
        context.sendSyncMessage(ISignal.SIGNAL_XHOLON_CONSOLE_REQ, "vanish", this);
        IMessage rmsg = context.sendSyncMessage(ISignal.SIGNAL_XHOLON_CONSOLE_REQ, "help", this);
        Object dataObj = rmsg.getData();
        if (dataObj != null) {
          setResult(dataObj.toString(), false);
        }
        setTerminalEnabled(true);
      }
      return null;
    }
    else if (mode.length() > 1) {
      return doXholonConsoleCommandOrText(mode + commandOrTextString);
    }
    else {
      addToHistory(commandOrTextString);
      if (sendMsgAsyncOrSync == SENDMESSAGE_ASYNC) {
        context.sendMessage(ISignal.SIGNAL_XHOLON_CONSOLE_REQ, commandOrTextString, this);
      }
      else {
        // this is used for Avatars
        IXholon contextParent = context.getParentNode(); // cache the parent
        IMessage rmsg = context.sendSyncMessage(ISignal.SIGNAL_XHOLON_CONSOLE_REQ, commandOrTextString, this);
        Object dataObj = rmsg.getData();
        if (dataObj != null) {
          String dataStr = dataObj.toString();
          if (isTerminalEnabled() && (!dataStr.endsWith("\n"))) {
            dataStr += "\n";
          }
          setResult(dataStr, false);
        }
        if (context.getParentNode() != contextParent) {
          // the context node has moved and has a new parent, so update the XholonConsole header with tooltip
          // note that the context node itself has NOT changed
          setTabHeader();
        }
      }
      return null;
    }
  }
  
  /**
   * Get the contents of the command, as typed in by the user into the XholonConsole GUI.
   * @return The String typed in by the user, or only the selected part of the String.
   */
  protected String getCommand()
  {
    /*JTextPane jTextPane = (JTextPane)commandPane.getVal_Object();
    String contents = jTextPane.getSelectedText();
    if (contents == null) {
      contents = jTextPane.getText();
    }*/
    Element ele = commandPane.getElement().getFirstChildElement();
    //consoleLog(ele);
    // pre
    //String contents = ele.getInnerText();
    // textarea
    TextAreaElement ta = TextAreaElement.as(ele);
    String contents = ta.getValue();
    //consoleLog(contents);
    return contents;
  }
  
  /*
   * @see org.primordion.xholon.io.console.IXholonConsole#log(java.lang.Object)
   */
  public void log(Object obj) {
    if (obj instanceof String) {
      setResult((String)obj, false);
    }
  }
  
  /**
   * For now, the command and the result are in the same window,
   * so just call the existing setResult() method.
   */
  public void setCommand(String result, boolean replace) {
    setResult(result, replace);
  }
  
  /**
   * Set the result of the query into the XholonConsole GUI,
   * and make it visible to the user.
   * For now, the resultPane and commandPane are the same.
   * @param result
   * @param replace whether to replace current text (true), or append to end of current text (false)
   */
  public void setResult(String result, boolean replace)
  {
    //Window.alert(result);
    
    // get the element
    Element ele = commandPane.getElement().getFirstChildElement();
    
    // <pre>
    //ele.setInnerText(ele.getInnerText() + result);
    
    // these leave the escaped chars in the text
    //ele.setInnerHTML(ele.getInnerHTML() + result);
    //ele.setInnerText(ele.getInnerText() + SafeHtmlUtils.htmlEscape(result));
    
    // textarea
    TextAreaElement ta = TextAreaElement.as(ele);
    if (replace) {
      ta.setValue(result);
    }
    else {
      ta.setValue(ta.getValue() + result);
    }
    // optionally scroll to the bottom of the text area
    if (isTerminalEnabled()) {
      ta.setScrollTop(ta.getScrollHeight());
    }
  }
  
  /**
   * Clear the contents of the result pane.
   */
  protected void clearResults()
  {
    /*
    JTextPane jTextPane = (JTextPane)resultPane.getVal_Object();
    jTextPane.setText("");
    */
  }
  
  /**
   * Clear the contents of the command pane.
   */
  protected void clearCommand()
  {
    Element ele = commandPane.getElement().getFirstChildElement();
    TextAreaElement ta = TextAreaElement.as(ele);
    // pre
    //ele.setInnerText("");
    // textarea
    ta.setValue("");
  }
  
  /**
   * Set the name and title of the tab header.
   */
  public void setTabHeader() {
    //String modeStr = mode;
    //if (mode.length() == 0) {modeStr = MODE_DEFAULT;}
    
    String idStr = context.getName("R^^_i^");
    String xpathExpression = getXPath().getExpression(context, app.getRoot(), false);
    String tooltip = xpathExpression == null ? idStr : xpathExpression;
    XholonGwtTabPanelHelper.updateTabHeader(idStr, tooltip, -1);
    
    //((JFrame)frame.getVal_Object()).setTitle(context.getName()
    //    + " (" + app.getModelName() + ")[XholonConsole mode=" + modeStr + "]");
  }
  
  /**
   * Close the GUI.
   */
  protected void closeGui()
  {
    Window.alert("closegui selected");
    /*IXholon frame = getXPath().evaluate("descendant::JFrame", this);
    ((JFrame)frame.getVal_Object()).dispose();
    // remove the frame hierarchy from this XholonConsole node
    frame.removeChild();
    // remove the XholonConsole from the composite structure tree
    removeChild();*/
  }
  
  /**
   * Open a file that contains XholonConsole commands or text,
   * and load the contents of that file into the Command part of the XholonConsole.
   */
  protected void openQueryFile()
  {
    /*
    final ScriptFilter xhnFilter = new ScriptFilter();
    JFileChooser chooser = new JFileChooser();
    chooser.addChoosableFileFilter(xhnFilter);
    chooser.setCurrentDirectory(new File("./script"));
    int state = chooser.showOpenDialog(null);
    File file = chooser.getSelectedFile();
    if (file != null && state == JFileChooser.APPROVE_OPTION) {
      latestOpenedFile = file;
      String fileName = file.getAbsolutePath();
      //System.out.println(fileName);
      BufferedReader r = (BufferedReader)MiscIo.openInputFile(fileName);
      String xqueryStr = "";
      try {
        String buffer = r.readLine();
        while (buffer != null) {
          xqueryStr += buffer + "\n";
          buffer = r.readLine();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      MiscIo.closeInputFile(r);
      JTextPane jTextPane = (JTextPane)commandPane.getVal_Object();
      jTextPane.setText(xqueryStr);
    }
    */
  }
  
  /**
   * Save the contents of the Command panel in an already specified file.
   */
  protected void saveCommandFile()
  {
    /*
    if (latestOpenedFile == null) {
      saveCommandFileAs();
    }
    else {
      File file = latestOpenedFile;
      String fileName = file.getAbsolutePath();
      FileWriter w = (FileWriter)MiscIo.openOutputFile(fileName);
      String xqueryStr = getCommand();
      try {
        w.write(xqueryStr);
      } catch (IOException e) {
        e.printStackTrace();
      }
      MiscIo.closeOutputFile(w);
    }
    */
  }
  
  /**
   * Save the contents of the Command panel in a file.
   */
  protected void saveCommandFileAs()
  {
    /*
    final ScriptFilter xhnFilter = new ScriptFilter();
    JFileChooser chooser = new JFileChooser();
    chooser.addChoosableFileFilter(xhnFilter);
    chooser.setCurrentDirectory(new File("./script"));
    int state = chooser.showSaveDialog(null);
    File file = chooser.getSelectedFile();
    if (file != null && state == JFileChooser.APPROVE_OPTION) {
      latestOpenedFile = file;
      String fileName = file.getAbsolutePath();
      FileWriter w = (FileWriter)MiscIo.openOutputFile(fileName);
      String xqueryStr = getCommand();
      try {
        w.write(xqueryStr);
      } catch (IOException e) {
        e.printStackTrace();
      }
      MiscIo.closeOutputFile(w);
    }
    */
  }
  
  /**
   * Read the XholonConsole GUI from a UIBinder XML file.
   */
  protected void readGuiFromXml() {
    widget = uiBinder.createAndBindUi(this);
    //RootPanel.get("xhappspecific").add(widget);
  }
  
    /**
     * FileFilter that filters on files with .bsh .groovy .js .rb .py extension.
     * @author Ken
     */
    /*class ScriptFilter extends FileFilter {
      public boolean accept(File f) {
        boolean accept = f.isDirectory();
        if (! accept) {
          String suffix = getSuffix(f);
          if (suffix != null) {
            accept = suffix.equals("bsh")
            || suffix.equals("groovy")
            || suffix.equals("js")
            || suffix.equals("rb")
            || suffix.equals("py");
          }
        }
        return accept;
      }
      public String getDescription() {
        return "Script Files(*.bsh *.groovy *.js *.rb *.py)";
      }
      private String getSuffix(File f) {
        String s = f.getPath();
        String suffix = null;
        int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
          suffix = s.substring(i+1).toLowerCase();
        }
        return suffix;
      }
    }*/
    
    /**
   * Remember the current context node as a MouseEvent.BUTTON3 selection (Right-Click).
   * @param node The context node.
   */
  protected void rememberButton3Selection(IXholon node) {
    // pass the node to the NodeSelectionService by sending it as an async message
    if (nodeSelectionService == null) {
      nodeSelectionService = app.getService(IXholonService.XHSRV_NODE_SELECTION);
    }
    if (nodeSelectionService != null) {
      nodeSelectionService
        .sendSyncMessage(NodeSelectionService.SIG_REMEMBER_BUTTON3_NODE_REQ, node, app);
    }
  }
  
  /**
   * Add an item to history.
   * @param historyItem 
   */
  protected void addToHistory(String historyItem) {
    if (historyQ.getSize() == historyQ.getMaxSize()) {
      historyQ.dequeue();
    }
    historyQ.enqueue(historyItem);
  }
  
  /**
   * Limit the length of a string.
   */
  protected String limitLength(String str, int maxLen) {
    return str.length() > 80 ? str.substring(0, 80) : str;
  }
  
}

/* AQL Web Interface
 * Copyright (C) 2017 Ken Webb
 * BSD License
 */

package org.primordion.xholon.io.ngui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

import java.util.List;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IQueue;
import org.primordion.xholon.base.IReflection;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.Queue;
import org.primordion.xholon.base.ReflectionFactory;
import org.primordion.xholon.base.XholonWithPorts;
//import org.primordion.xholon.io.Specials;
import org.primordion.xholon.io.ISwingEntity;
import org.primordion.xholon.io.XholonGwtTabPanelHelper;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.NodeSelectionService;
import org.primordion.xholon.service.XholonHelperService;

/**
 * Provide a AQL Web Interface.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on June 27, 2017)
 */
public class AqlWebInterface extends XholonWithPorts implements INamedGui {
  
  interface AqlWebUiBinder extends UiBinder<Widget, AqlWebInterface> {}
  private static AqlWebUiBinder uiBinder = GWT.create(AqlWebUiBinder.class);
  
  // File menu
  @UiField MenuItem fileneweasik;
  @UiField MenuItem filenewaql;
  @UiField MenuItem fileopen;
  @UiField MenuItem filesave;
  @UiField MenuItem filesaveas;
  @UiField MenuItem filesaveall;
  @UiField MenuItem fileclose;
  @UiField MenuItem filequit;
  
  // Edit menu
  @UiField MenuItem editfind;
  @UiField MenuItem editcopyasrtf;
  @UiField MenuItem editfoldall;
  @UiField MenuItem editunfoldall;
  
  // Tools menu
  @UiField MenuItem toolsrun;
  @UiField MenuItem toolsabort;
  @UiField MenuItem toolsoptions;
  @UiField MenuItem toolslegacyoptions;
  @UiField MenuItem toolsedchaser;
  @UiField MenuItem toolssqlloader;
  @UiField MenuItem toolssqlmapper;
  @UiField MenuItem toolssqlchecker;
  @UiField MenuItem toolsnrshredder;
  @UiField MenuItem toolseasik;
  
  // AQL menu
  @UiField MenuItem aqloutline;
  @UiField MenuItem aqlinfermapping;
  @UiField MenuItem aqlinferquery;
  @UiField MenuItem aqlinfertransform;
  @UiField MenuItem aqlinferinstance;
  @UiField MenuItem aqlemithtml;
  
  // Help menu
  @UiField MenuItem helpabout;
  
  // buttons
  //@UiField Button submit;
  //@UiField Button closeguiB;
  
  @UiField HTML commandPane;
  @UiField TextAreaElement commandPaneTAE;
  
  /** The Xholon node that the XholonConsole node is associated with. */
  IXholon context = null;
  
  /** The name of the context node. */
  private String roleName = null;
  
  /**
   * The HTML widget that implements the XholonConsole.
   */
  protected Widget widget = null;
  
  /**
   * constructor
   */
  public AqlWebInterface() {}
  
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

  public Object getWidget() {
    return widget;
  }

  /*
   * @see org.primordion.xholon.base.XholonWithPorts#postConfigure()
   */
  public void postConfigure()
  {
    readGuiFromXml();
    
    // File menu
    fileopen.setEnabled(false);
    filesave.setEnabled(false);
    filesaveas.setEnabled(false);
    
    // Edit menu
    
    /*clearcommand.setScheduledCommand(new Command() {
	    @Override
      public void execute() {
        clearCommand();
      }
    });
    */
    
    // Help menu
    
    helpabout.setScheduledCommand(new Command() {
	    @Override
      public void execute() {
        help();
      }
    });
    
  }
  
  /**
   * Provide the user with some brief help information.
   */
  protected void help()
  {
    String helpStr = "Help is on its way.";
    Window.alert(helpStr);
  }
  
  /**
   * Get the contents of the command, as typed in by the user into the XholonConsole GUI.
   * @return The String typed in by the user, or only the selected part of the String.
   */
  protected String getCommand()
  {
    /*Element ele = commandPane.getElement().getFirstChildElement();
    TextAreaElement ta = TextAreaElement.as(ele);
    String contents = ta.getValue();
    return contents;*/
    return "";
  }
  
  /*
   * @see org.primordion.xholon.io.console.IXholonConsole#log(java.lang.Object)
   */
  public void log(Object obj) {
    /*if (obj instanceof String) {
      setResult((String)obj, false);
    }*/
  }
  
  /**
   * For now, the command and the result are in the same window,
   * so just call the existing setResult() method.
   */
  public void setCommand(String result, boolean replace) {
    //setResult(result, replace);
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
    /*
    //Window.alert(result);
    
    // get the element
    Element ele = commandPane.getElement().getFirstChildElement();
    
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
    }*/
  }
  
  /**
   * Clear the contents of the result pane.
   */
  protected void clearResults()
  {
    
  }
  
  /**
   * Clear the contents of the command pane.
   */
  protected void clearCommand()
  {
    /*Element ele = commandPane.getElement().getFirstChildElement();
    TextAreaElement ta = TextAreaElement.as(ele);
    ta.setValue("");*/
  }
  
  /**
   * Set the name and title of the tab header.
   */
  public void setTabHeader() {
    String idStr = "Categorical Data IDE";
    String tooltip = "Categorical Data IDE";
    XholonGwtTabPanelHelper.updateTabHeader(idStr, tooltip, -1);
  }
  
  /**
   * Close the GUI.
   */
  protected void closeGui()
  {
   // Window.alert("closegui selected");
  }
  
  /**
   * Open a file that contains XholonConsole commands or text,
   * and load the contents of that file into the Command part of the XholonConsole.
   */
  protected void openQueryFile()
  {

  }
  
  /**
   * Save the contents of the Command panel in an already specified file.
   */
  protected void saveCommandFile()
  {
    
  }
  
  /**
   * Save the contents of the Command panel in a file.
   */
  protected void saveCommandFileAs()
  {
    
  }
  
  /**
   * Read the XholonConsole GUI from a UIBinder XML file.
   */
  protected void readGuiFromXml() {
    widget = uiBinder.createAndBindUi(this);
  }
      
  /**
   * Limit the length of a string.
   */
  protected String limitLength(String str, int maxLen) {
    return str.length() > 80 ? str.substring(0, 80) : str;
  }
  
}

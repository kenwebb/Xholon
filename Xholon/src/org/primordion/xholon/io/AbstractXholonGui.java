package org.primordion.xholon.io;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import org.client.GwtEnvironment;
//import org.primordion.xholon.app.Application;
import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IReflection;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.IXPath;
import org.primordion.xholon.base.ReflectionFactory;
import org.primordion.xholon.base.XholonClass;
import org.primordion.xholon.base.XPath;
//import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.xholon.exception.XholonConfigurationException;
//import org.primordion.xholon.io.IViewer;
import org.primordion.xholon.service.ExternalFormatService;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.NodeSelectionService;
import org.primordion.xholon.util.ClassHelper;

/**
 * Abstract base class for Xholon Gui implementations.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on November 26, 2013)
 */
public abstract class AbstractXholonGui implements IXholonGui {
  
  protected IXholon xhRoot;
  protected static IXPath xpath = new XPath();         // XPath routines
  
  protected String modelName = null;
  
  protected IApplication app = null;
  
  /**
   * A service that will keep track of which IXholon node(s) are currently selected.
   * This will allow other services or xholons to act on those nodes.*/
  protected IXholon nodeSelectionService = null;
  
  /**
   * A service that provides additional methods for IXholon instances.
   */
  protected IXholon xholonHelperService = null;
  
  /**
   * Text that can appear as an overlay in the GUI.
   */
  protected static final String splashText = "<div style=\"background-color: #f0f8ff\">"
    + "<p><center><strong><em>Welcome to Xholon (version 0.9.1)</em></strong></center></p>"
    + "<p>The Xholon GUI allows you to execute existing Xholon applications, and observe them at run-time.</p>"
    + "<ul style=\"margin: 10\">"
    + "<li>Press the <strong>Start</strong> button. While the application is running, you can press Pause (toggle pause/unpause), Step, Stop, and Refresh. Observe the (optional) output in the out tab.</li>"
    + "<li>Expand <strong>Application &gt; View</strong> to see which if any viewers have been enabled.</li>"
    + "<li>Expand <strong>Application &gt; Model</strong> to observe the hierarchical runtime structure and behavior (CompositeStructureHierarchy) of the application. Details of each node will display at the bottom of the GUI.</li>"
    + "<li>Select <strong>Information</strong> from the Help menu for (optional) details about the application currently running.</li>"
    + "<li>Right-click any node to open a context menu.</li>"
    + "</ul>"
    + "<p>Copyright (C) 2005 - 2017 Ken Webb</p>"
    + "<p>Xholon library is licensed under GNU Lesser General Public License.</p>"
    + "<p><center><a href=\"http://www.primordion.com/Xholon/gwt/\">www.primordion.com/Xholon/gwt/</a><center></p>"
    + "<center><a href=\"http://www.primordion.com/Xholon/wiki/index.html\">Xholon wiki</a><center>"
    + "</div>";

  /**
   * Constructor.
   */
  public AbstractXholonGui() {}
  
  /**
   * Get name of the model.
   * @return Model name.
   */
  public String getModelName() {
    return modelName;
  }
  
  /**
   * Show the entire IXholon tree in a GUI.
   * @param node Node from which to start showing the tree.
   */
  public abstract void showTree(IXholon node);
  
  /**
   * Handle selection of a node,
   * typically caused by clicking or ctrl-clicking on a node with the left mouse button.
   * @param nodeName 
   * @param guiItem 
   * @param isCtrlPressed 
   */
  public void handleNodeSelection(String nodeName, Object guiItem, boolean isCtrlPressed) {
    IXholon node = xpath.evaluate("descendant-or-self::*[@name='" + nodeName + "']", xhRoot);
    if (node == null) {
      // this is probably a XholonClass or a Mechanism
      if (isInInheritanceHierarchy(guiItem)) {
        IXholonClass xhcNode = xhRoot.getClassNode(nodeName);
        if (xhcNode == null) {
          setText(nodeName);
        }
        else {
          rememberNodeSelection(xhcNode, isCtrlPressed);
          setText(xhcNode.toString());
        }
      }
      else if (isInMechanismHierarchy(guiItem)) {
        IXholon mechNode = app.getMechRoot();
        mechNode = xpath.evaluate("descendant-or-self::*[@name='" + nodeName + "']", mechNode);
        if (mechNode == null) {
          setText(nodeName);
        }
        else {
          rememberNodeSelection(mechNode, isCtrlPressed);
          setText(mechNode.toString());
        }
      }
      else {
        setText(nodeName);
      }
    }
    else {
      if (isInInheritanceHierarchy(guiItem)) {
        IXholonClass xhcNode = xhRoot.getClassNode(nodeName);
        if (xhcNode == null) {
          setText(nodeName);
        }
        else {
          rememberNodeSelection(xhcNode, isCtrlPressed);
          setText(xhcNode.toString());
        }
      }
      else {
        rememberNodeSelection(node, isCtrlPressed);
        if (nodeName.equals("Refresh")) {
          refresh();
        }
        else {
          setText(app.handleNodeSelection(node, nodeName));
        }
      }
    }
  }
  
  /**
   * Write out some text to the GUI.
   */
  protected abstract void setText(String text);
  
  /**
   * Send a synchronous message to the XholonHelperService.
   * @param signal
   * @param data
   * @param sender
   * @return
   */
  public IMessage sendXholonHelperService(int signal, Object data, IXholon sender) {
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
   * Remember a MouseEvent.BUTTON3 selection (Right-Click).
   * @param node
   */
  protected void rememberButton3Selection(IXholon node) {
    // pass the node to the NodeSelectionService by sending it as a sync message
    if (nodeSelectionService == null) {
      nodeSelectionService = app.getService(IXholonService.XHSRV_NODE_SELECTION);
    }
    if (nodeSelectionService != null) {
      nodeSelectionService
        .sendSyncMessage(NodeSelectionService.SIG_REMEMBER_BUTTON3_NODE_REQ, node, app);
    }
  }
  
  /**
   * Remember which IXholon node(s) are currently selected.
   * This will allow services or other xholons to act on those nodes.
   * This allows multi-selection of nodes.
   * @param svgElementId ex: World/Europe/Iceland
   * @param isControlDown Whether or not the Control key is pressed down.
   */
  protected void rememberNodeSelection(IXholon node, boolean isControlDown) {
    if (node != null) {
      IXholon[] nodeArray = {node};
      if (nodeSelectionService == null) {
        nodeSelectionService = app.getService(IXholonService.XHSRV_NODE_SELECTION);
      }
      if (nodeSelectionService != null) {
        if (isControlDown) {
          // mouse Ctrl-click
          // append the new selection to what has already been remembered
          nodeSelectionService
            .sendSystemMessage(NodeSelectionService.SIG_APPEND_SELECTED_NODES_REQ, nodeArray, app);
        }
        else {
          // mouse click
          // the new selection should replace what was previously remembered
          app.setAvatarContextNode(node);
          nodeSelectionService
            .sendSystemMessage(NodeSelectionService.SIG_REMEMBER_SELECTED_NODES_REQ, nodeArray, app);
        }
      }
    }
  }
  
  /**
   * Refresh the entire hierarchical structure (tree, contour, etc.) being displayed in the viewer.
   * Subclasses may override this to refresh the GUI.
   */
  public void refresh() {}
  
  /**
   * Refresh the current node in the hierarchical structure, including its entire sub-tree.
   * Subclasses may override this to refresh the sub-tree in the GUI.
   * @param guiItem The current item in the GUI.
   * @param node The current IXholon node, corresponding to the guiItem.
   */
  public void refresh(Object guiItem, IXholon xhNode) {}
  
  /**
   * Execute a model.
   * @param mName The model name.
   * @param app Instance of the model's IApplication class.
   */
  public void execModel(String mName, IApplication app) {
    this.modelName = mName;
    this.app = app;
    if (app != null) {
      try {
        app.initialize(modelName);
      } catch (XholonConfigurationException e) {return;}
      if (app.getRoot() == null) {
        return;
      }
      app.initControl();
      app.initViewers();
      app.initGui(this);
      boolean rc = app.loadWorkbook();
      if (rc == false) {
        // no workbook is being asynchronously loaded
        showTree(app.getAppRoot());
        app.process();
      }
    }
  }
  
  /**
   * Make a context menu for a specified GUI item.
   * @param guiItem
   * @param posX 
   * @param posY 
   */
  public void makeContextMenu(Object guiItem, int posX, int posY) {
    if (guiItem == null) {return;}
    IXholon nnode = getXholonNode(guiItem);
    if (nnode == null) {return;}
    
    final IXholon node = nnode;
    final Object guiItemFinal = guiItem;
    rememberButton3Selection(node);
    PopupPanel popup = new PopupPanel(true);
    popup.hide();
    //setPopupPosition(int left, int top)
    popup.setPopupPosition(posX, posY);
    MenuBar menu = new MenuBar();
    
    // Special
    String[] specialItems = node.getActionList();
    String specialXml = app.rcConfig("special", app.findGwtClientBundle());
    //System.out.println(specialItems);
    if ((specialItems != null) || (specialXml != null)) {
      MenuBar specialSubMenu = new MenuBar(true);
      if (specialItems != null) {
        for (int i = 0; i < specialItems.length; i++) {
          final String actionName = specialItems[i];
          specialSubMenu.addItem(specialItems[i], new Command() {
            @Override
            public void execute() {
              node.doAction(actionName);
              refresh(guiItemFinal, node);
            }
          });
        }
      }
      if (specialXml != null) {
        //node.consoleLog("special.xml");
        //node.consoleLog(specialXml);
        final Specials specials = new Specials();
        specials.xmlString2Items(specialXml, node);
        String[] moreSpecialItems = specials.getActionList();
        if (moreSpecialItems != null) {
          for (int i = 0; i < moreSpecialItems.length; i++) {
            final String actionName = moreSpecialItems[i];
            specialSubMenu.addItem(moreSpecialItems[i], new Command() {
              @Override
              public void execute() {
                //node.consoleLog("execute() " + actionName);
                specials.doAction(actionName);
                //refresh(guiItemFinal, node);
              }
            });
          }
        }
      }
      menu.addItem("Special", specialSubMenu);
    }
    
    // Edit
    MenuBar editSubMenu = new MenuBar(true);
    editSubMenu.addItem("Copy", new Command() {
      public void execute() {
        sendXholonHelperService(ISignal.ACTION_COPY_TOCLIPBOARD, node, null);
        //currentSelectionField.setText((String)node.handleNodeSelection());
      }
    });
    editSubMenu.addItem("Cut", new Command() {
      public void execute() {
        sendXholonHelperService(ISignal.ACTION_CUT_TOCLIPBOARD, node, null);
        refresh(guiItemFinal, node);
        //currentSelectionField.setText((String)node.handleNodeSelection());
      }
    });
    editSubMenu.addItem("Paste Last Child", new Command() {
      public void execute() {
        sendXholonHelperService(ISignal.ACTION_PASTE_LASTCHILD_FROMCLIPBOARD, node, null);
        refresh(guiItemFinal, node);
        //currentSelectionField.setText((String)node.handleNodeSelection());
      }
    });
    editSubMenu.addItem("Paste First Child", new Command() {
      public void execute() {
        sendXholonHelperService(ISignal.ACTION_PASTE_FIRSTCHILD_FROMCLIPBOARD, node, null);
        refresh(guiItemFinal, node);
        //currentSelectionField.setText((String)node.handleNodeSelection());
      }
    });
    editSubMenu.addItem("Paste After", new Command() {
      public void execute() {
        sendXholonHelperService(ISignal.ACTION_PASTE_AFTER_FROMCLIPBOARD, node, null);
        refresh(guiItemFinal, node);
        //currentSelectionField.setText((String)node.handleNodeSelection());
      }
    });
    editSubMenu.addItem("Paste Before", new Command() {
      public void execute() {
        sendXholonHelperService(ISignal.ACTION_PASTE_BEFORE_FROMCLIPBOARD, node, null);
        refresh(guiItemFinal, node);
        //currentSelectionField.setText((String)node.handleNodeSelection());
      }
    });
    editSubMenu.addItem("Paste Replacement", new Command() {
      public void execute() {
        sendXholonHelperService(ISignal.ACTION_PASTE_REPLACEMENT_FROMCLIPBOARD, node, null);
        refresh(guiItemFinal, node);
        //currentSelectionField.setText((String)node.handleNodeSelection());
      }
    });
    editSubMenu.addItem("Paste Merge", new Command() {
      public void execute() {
        sendXholonHelperService(ISignal.ACTION_PASTE_MERGE_FROMCLIPBOARD, node, null);
        refresh(guiItemFinal, node);
        //currentSelectionField.setText((String)node.handleNodeSelection());
      }
    });
    editSubMenu.addItem("Clone Last Child", new Command() {
      public void execute() {
        sendXholonHelperService(ISignal.ACTION_CLONE_LASTCHILD, node, null);
      }
    });
    editSubMenu.addItem("Clone First Child", new Command() {
      public void execute() {
        sendXholonHelperService(ISignal.ACTION_CLONE_FIRSTCHILD, node, null);
      }
    });
    editSubMenu.addItem("Clone After", new Command() {
      public void execute() {
        sendXholonHelperService(ISignal.ACTION_CLONE_AFTER, node, null);
      }
    });
    editSubMenu.addItem("Clone Before", new Command() {
      public void execute() {
        sendXholonHelperService(ISignal.ACTION_CLONE_BEFORE, node, null);
      }
    });
    editSubMenu.addItem("XQuery Thru Clipboard", new Command() {
      public void execute() {
        
      }
    }).setEnabled(false);
    editSubMenu.addItem("XQuery GUI", new Command() {
      public void execute() {
        
      }
    }).setEnabled(false);
    editSubMenu.addItem("Log Browser Console", new Command() {
      public void execute() {
        node.consoleLog(node);
      }
    });
    menu.addItem("Edit", editSubMenu);

    // Console
    menu.addItem("Xholon Console", new Command() {
      public void execute() {
        sendXholonHelperService(ISignal.ACTION_START_XHOLON_CONSOLE, node, null);
        //currentSelectionField.setText((String)node.handleNodeSelection());
      }
    });
    
    // Attributes
    menu.addItem("Attributes", new Command() {
      public void execute() {
        createAttributesGui(node, false);
        //currentSelectionField.setText((String)node.handleNodeSelection());
      }
    });

    // Annotation
    MenuItem annMenuItem = menu.addItem("Annotation", new Command() {
      public void execute() {
        node.showAnnotation();
      }
    });
    if (node.hasAnnotation()) {
      annMenuItem.setEnabled(true);
    }
    else {
      annMenuItem.setEnabled(false);
    }

    // Search Engine
    MenuBar searchSubMenu = new MenuBar(true);
    final IXholon ses = app.getService(IXholonService.XHSRV_SEARCH_ENGINE);
    if (ses != null) {
      String[] sesItems = ses.getActionList();
      if (sesItems != null) {
        for (int i = 0; i < sesItems.length; i++) {
          final String menuName = sesItems[i];
          searchSubMenu.addItem(menuName, new Command() {
            public void execute() {
              String str = menuName + "," + node.getXhcName();
              String roleNameStr = node.getRoleName();
              if (roleNameStr != null && roleNameStr.length() != 0) {
                str += "," + roleNameStr;
              }
              ses.doAction(str);
            }
          });
        }
      }
    }
    
    // https://github.com/kenwebb/Xholon/blob/master/Xholon/src/org/primordion/xholon/base/Attribute.java
    searchSubMenu.addItem("Java source code", new Command() {
      public void execute() {
        StringBuilder sourceUrl = new StringBuilder();
        //sourceUrl.append("http://xholon.cvs.sourceforge.net/viewvc/xholon/xholon/src/");
        sourceUrl.append("https://github.com/kenwebb/Xholon/blob/master/Xholon/src/");
        String className = null;
        if (ClassHelper.isAssignableFrom(XholonClass.class, node.getClass())) {
          className = ((IXholonClass)node).getImplName();
        }
        if (className == null) {
          className = node.getClass().getName();
        }
        if (className != null) {
          if (className.endsWith("GWTGEN")) {
            // there is no source code for this GWT-generated class
            // use the original code (the superclass) instead
            className = className.substring(0, className.length() - 6);
          }
          sourceUrl.append(className.replace('.', '/'));
          //sourceUrl.append(".java?view=markup");
          sourceUrl.append(".java");
          Window.open(sourceUrl.toString(), "_blank", ""); // starts with "http" or "https"
        }
        
      };
    });
    
    searchSubMenu.addItem("Java javadoc", new Command() {
      public void execute() {
        // http://www.primordion.com/Xholon/gwt/javadoc/org/primordion/xholon/xmiapps/XhElevator.html
        StringBuilder javadocUrl = new StringBuilder();
        javadocUrl.append(GwtEnvironment.gwtHostPageBaseURL + "javadoc/");
        String className = null;
        if (ClassHelper.isAssignableFrom(XholonClass.class, node.getClass())) {
          className = ((IXholonClass)node).getImplName();
        }
        if (className == null) {
          className = node.getClass().getName();
        }
        if (className != null) {
          if (className.endsWith("GWTGEN")) {
            // there is no javadoc for this GWT-generated class
            // use the original code (the superclass) instead
            className = className.substring(0, className.length() - 6);
          }
          javadocUrl.append(className.replace('.', '/'));
          javadocUrl.append(".html");
          Window.open(javadocUrl.toString(), "_blank", ""); // starts with "http"
        }
        
      };
    });
    
    menu.addItem("Search Engine", searchSubMenu);

    // Export to external formats
    final ExternalFormatService efs = GWT.create(org.primordion.xholon.service.ExternalFormatService.class);
    String[] efItems = efs.getActionList();
    if (efItems != null) {
      MenuBar efSubMenu = new MenuBar(true);
      for (int i = 0; i < efItems.length; i++) {
        //System.out.println(efItems[i]);
        if (efItems[i].startsWith("_")) {
          // this is a submenu item
          String[] efItemSubSubMenuStr = efItems[i].substring(1).split(",");
          if (efItemSubSubMenuStr.length >= 2) {
            final String efItemSubSubMenuName = efItemSubSubMenuStr[0];
            MenuBar efSubSubMenu = new MenuBar(true);
            for (int j = 1; j < efItemSubSubMenuStr.length; j++) {
              final String efItem = efItemSubSubMenuStr[j];
              efSubSubMenu.addItem(efItem, new Command() {
                @Override
                public void execute() {
                  IXholon2ExternalFormat xholon2ef = efs.initExternalFormatWriter(node,
                      "_" + efItemSubSubMenuName + "," +
                      efItem, null, true);
                  efs.writeAll(xholon2ef);
                }
              });
            }
            efSubMenu.addItem(efItemSubSubMenuName, efSubSubMenu);
          }
        }
        else {
          final String efItem = efItems[i];
          efSubMenu.addItem(efItems[i], new Command() {
            @Override
            public void execute() {
              IXholon2ExternalFormat xholon2ef = efs.initExternalFormatWriter(node, efItem, null, true);
              efs.writeAll(xholon2ef);
            }
          });

        }
      }
      menu.addItem("Export", efSubMenu);
    }

    // Graphical Network Viewer
    // use ef Xholon2ChapNetwork.java instead
    /*menu.addItem("Graphical Network Viewer", new Command() {
      public void execute() {
        if ((node.getXhcId() == CeStateMachineEntity.StateMachineCE)
            || (node.getXhcId() == CeStateMachineEntity.RegionCE)
            || (node.getXhcId() == CeStateMachineEntity.StateCE)) {
          String myParams = null;
          IViewer viewer = app.invokeGraphicalNetworkViewer(node, myParams);
          //currentSelectionField.setText((String)node.handleNodeSelection());
          //StateMachineXhym smXhym = new StateMachineXhym();
          //smXhym.postConfigure(node, (IGraphicalNetworkViewer)viewer);
        }
        else if (ClassHelper.isAssignableFrom(Application.class, node.getClass())) {
          String myParams = null;
          app.invokeGraphicalNetworkViewer(node, myParams);
          //currentSelectionField.setText((String)node.handleNodeSelection());
        }
        else {
          String myParams = null;
          app.invokeGraphicalNetworkViewer(node, myParams);
          //currentSelectionField.setText((String)node.handleNodeSelection());
        }
      }
    });*/

    // Graphical Tree Viewer
    // use ef Xholon2ChapTree.java instead
    /*menu.addItem("Graphical Tree Viewer", new Command() {
      public void execute() {
        String myParams = null;
        IViewer viewer = app.invokeGraphicalTreeViewer(node, myParams);
        //currentSelectionField.setText((String)node.handleNodeSelection());
      }
    });*/
    
    // Control
    MenuBar controlSubMenu = new MenuBar(true);
    controlSubMenu.addItem("Start", new Command() {
      public void execute() {
        IXholon cnode = app.getControlRoot().getFirstChild();
        cnode.handleNodeSelection();
      }
    });
    controlSubMenu.addItem("Pause", new Command() {
      public void execute() {
        IXholon cnode = app.getControlRoot().getFirstChild().getNextSibling();
        cnode.handleNodeSelection();
      }
    });
    controlSubMenu.addItem("Step", new Command() {
      public void execute() {
        IXholon cnode = app.getControlRoot().getFirstChild().getNextSibling().getNextSibling();
        cnode.handleNodeSelection();
      }
    });
    controlSubMenu.addItem("Stop", new Command() {
      public void execute() {
        IXholon cnode = app.getControlRoot().getFirstChild().getNextSibling().getNextSibling().getNextSibling();
        cnode.handleNodeSelection();
      }
    });
    controlSubMenu.addItem("Refresh", new Command() {
      public void execute() {
        refresh();
      }
    });
    menu.addItem("Control", controlSubMenu);
    
    // Help
    MenuBar helpSubMenu = new MenuBar(true);
    helpSubMenu.addItem("About", new Command() {
      public void execute() {
        if (app != null) {
          app.about();
        }
      }
    });
    helpSubMenu.addItem("Getting Started", new Command() {
      public void execute() {
        gettingStarted("Getting Started with Xholon", splashText, 375, 475);
      }
    });
    helpSubMenu.addItem("Information", new Command() {
      public void execute() {
        if (app != null) {
          app.information();
        }
      }
    });
    helpSubMenu.addItem("JavaScript API", new Command() {
      public void execute() {
        Window.open(GwtEnvironment.gwtHostPageBaseURL + "jsapidoc/modules/XholonJsApi.html", "_blank", "");
      }
    });
    menu.addItem("Help", helpSubMenu);
    
    popup.add(menu);
    popup.show();
  }
  
  /**
   * Get the IXholon node that corresponds with a GUI item.
   * @param guiItem
   * @return
   */
  protected IXholon getXholonNode(final Object guiItem) {
    IXholon node = null;
    if (guiItem != null) {
      String nodeName = getGuiItemName(guiItem);
      if (isInInheritanceHierarchy(guiItem)) {
        // this is a XholonClass
        node = xhRoot.getClassNode(nodeName);
      }
      else if (isInMechanismHierarchy(guiItem)) {
        // this is a Mechanism
        IXholon mechNode = app.getMechRoot();
        node = xpath.evaluate("descendant-or-self::*[@name='" + nodeName + "']", mechNode);
      }
      else {
        // this is probably a regular Xholon
        node = xpath.evaluate("descendant-or-self::*[@name='" + nodeName + "']", xhRoot);
      }
    }
    return node;
  }
  
  /**
   * Try to get a IXholon node, knowing only the nodeName (the name used by a guiItem).
   * This is used by XholonGuiClassicTree.
   * @param nodeName (ex: "hello_2" "Hello")
   * @return An instance of IXholon, or null.
   */
  public IXholon getXholonNode(String nodeName) {
    IXholon node = xpath.evaluate("descendant-or-self::*[@name='" + nodeName + "']", xhRoot);
    if (node == null) {
      if ("Application".equals(nodeName)) {
        node = app;
      }
    }
    if (node == null) {
      node = xhRoot.getClassNode(nodeName);
    }
    if (node == null) {
      IXholon mechNode = app.getMechRoot();
      node = xpath.evaluate("descendant-or-self::*[@name='" + nodeName + "']", mechNode);
    }
    return node;
  }
  
  /**
   * Handle data dragged-and-dropped onto an HTML Element (typically a TreeItem).
   * @param nodeName The text (Label) name of the Element; an IXholon node name.
   * @param data The content dropped onto the Element (ex: a XhgolonWorkbook).
   */
  public void handleDrop(String nodeName, Object data) {
    IXholon node = getXholonNode(nodeName);
    if (node != null) {
      sendXholonHelperService(ISignal.ACTION_PASTE_LASTCHILD_FROMDROP, data, node);
      Object guiItem = getGuiItem(nodeName);
      if (guiItem != null) {
        refresh(guiItem, node);
      }
    }
  }
  
  protected abstract String getGuiItemName(final Object guiItem);
  
  protected abstract Object getGuiItem(String nodeName);
  
  protected Object getGuiItem(IXholon node) {
    if (node == null) {return null;}
    String nodeName = node.getName();
    if (nodeName != null) {
      return getGuiItem(nodeName);
    }
    return null;
  }
  
  /**
   * Is the current node within the inheritance hierarchy?
   * @param selPath A TreePath whose last path component is the current node,
   * and which may include the "InheritanceHierarchy" node.
   * @return true or false
   */
  protected boolean isInInheritanceHierarchy(IXholon node) {
    return node.hasAncestor("XholonClass"); //InheritanceHierarchy");
  }
  
  protected abstract boolean isInInheritanceHierarchy(Object guiItem);
  
  /**
   * Is the current node within the mechanism hierarchy?
   * @param selPath A TreePath whose last path component is the current node,
   * and which may include the "MechanismHierarchy" node.
   * @return true or false
   */
  protected boolean isInMechanismHierarchy(IXholon node) {
    return node.hasAncestor("MechanismHierarchy");
  }
  
  protected abstract boolean isInMechanismHierarchy(Object guiItem);
  
  /**
   * Provide some "getting started" help information.
   */
  protected void gettingStarted(String title, String htmlText, int width, int height) {
    final DialogBox db = new DialogBox(true);
    db.setText(title);
    // htmlText is safe to use here; it's just the static final splashText in this class
    db.setWidget(new HTML(htmlText)); // HTML(String) is safe to use here
    
    //Button ok = new Button("OK");
    //ok.addClickHandler(new ClickHandler() {
    //  public void onClick(ClickEvent event) {
    //    db.hide();
    //  }
    //});
    //db.setWidget(ok);
    
    db.show();
  }
  
  /**
   * Create a GUI to interact with the attributes of an IXholon node.
   * @param node An IXholon node.
   * @param modal Whether or not the attributes GUI is modal.
   */
  protected void createAttributesGui(IXholon node, boolean modal) {
    final IReflection ir = ReflectionFactory.instance();
    Object rowData[][] = null;
    if ("Application".equals(node.getRoleName())) {
      node = app;
    }
    final IXholon attrNode = node;
    rowData = ir.getAttributes(attrNode);
    
    Object columnNames[] = new Object[2];
    columnNames[0] = "Name";
    columnNames[1] = "Value";
    final Grid attrTable = new Grid(rowData.length + 1, 2); // (rowData, columnNames);
    attrTable.setText(0, 0, (String)columnNames[0]);
    attrTable.setText(0, 1, (String)columnNames[1]);
    for (int i = 0; i < rowData.length; i++) {
      attrTable.setText(i+1, 0, (String)rowData[i][0]);
      if (rowData[i][1] == null) {
        attrTable.setText(i+1, 1, "");
      }
      else {
        attrTable.setText(i+1, 1, rowData[i][1].toString());
      }
    }
    
    attrTable.addClickHandler(new ClickHandler() {
      
      public void onClick(ClickEvent event) {
         Cell cell = attrTable.getCellForEvent(event);
         if (cell != null) {
           int cellIndex = cell.getCellIndex();
           if (cellIndex == 0) {return;}
           final int rowIndex = cell.getRowIndex();
           if ((attrNode != app) &&
               (app.getAppSpecificAttribute(attrNode, (Class<IXholon>)attrNode.getClass(),
                attrTable.getText(rowIndex, 0)) == null)) {
             // this is a non-editable attribute
             return;
           }
           Element ele = cell.getElement();
           String text = ele.getInnerText();
           //System.out.println(text);
           
           final TextBox textBox = new TextBox();
           // style it like a label so it looks like the original Element
           //textBox.setStylePrimaryName("gwt-Label"); // does nothing
           // set the style manually so it looks like the original Element
           textBox.getElement().setAttribute("style",
               "border-width: 0px; padding-top: 0px; padding-bottom: 0px;");
           int textLen = text.length();
           textBox.setVisibleLength(textLen == 0 ? 5 : textLen); // can't be 0
           textBox.setText(text); // or setValue( ?
           attrTable.setWidget(rowIndex, 1, textBox);
           textBox.setFocus(true);
           // add a ChangeHandler to tell when user has finished editing
           textBox.addChangeHandler(new ChangeHandler() {
             public void onChange(ChangeEvent event) {
              if (attrNode == app) {
                // change the value of an Application parameter
                //ir.setParam(attrTable.getText(rowIndex, 0), textBox.getText(), app);
                app.setParam(attrTable.getText(rowIndex, 0), textBox.getText());
              }
              else {
               // change the value of the Xholon node
               app.setAppSpecificAttribute(attrNode, (Class<IXholon>)attrNode.getClass(),
                attrTable.getText(rowIndex, 0), textBox.getText());
               }
              // set the changed text back into the Attributes panel
              attrTable.setText(rowIndex, 1, textBox.getText());
             }
           });
           // add a BlurHandler
           textBox.addBlurHandler(new BlurHandler() {
             public void onBlur(BlurEvent event) {
               attrTable.setText(rowIndex, 1, textBox.getText());
             }
           });
         }
      }
      
    });
    
    ScrollPanel scrollPanel = new ScrollPanel(attrTable);
    scrollPanel.setSize("500px", "500px");
    
    PopupPanel popup = new PopupPanel(true);
    popup.setTitle("Atttributes of " + attrNode.getName());
    popup.add(scrollPanel);
    popup.center();
  }

}

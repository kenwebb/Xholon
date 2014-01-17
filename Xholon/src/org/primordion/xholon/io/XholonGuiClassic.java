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

package org.primordion.xholon.io;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
//import com.google.gwt.dom.client.TextAreaElement;

import com.google.gwt.user.client.Command;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.Image;
//import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
//import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

//import com.google.gwt.event.dom.client.BlurHandler;
//import com.google.gwt.event.dom.client.BlurEvent;

//import com.google.gwt.event.dom.client.ChangeHandler;
//import com.google.gwt.event.dom.client.ChangeEvent;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

//import com.google.gwt.event.dom.client.DoubleClickHandler;
//import com.google.gwt.event.dom.client.DoubleClickEvent;

//import com.google.gwt.event.dom.client.ContextMenuHandler;
//import com.google.gwt.event.dom.client.ContextMenuEvent;

import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;

import com.google.gwt.resources.client.ImageResource;

import org.client.GwtEnvironment;
import org.client.RCImages;
import org.primordion.xholon.app.Application;
import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IDecoration;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IReflection;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.ReflectionFactory;
import org.primordion.xholon.base.XholonClass;
import org.primordion.xholon.common.mechanism.CeControl;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.xholon.exception.XholonConfigurationException;
import org.primordion.xholon.io.IViewer;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.NodeSelectionService;
import org.primordion.xholon.util.ClassHelper;

/**
 * Optional GUI for a Xholon GWT-based application.
 * Uses the Java GWT class Tree.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on July 22, 2013)
 */
public class XholonGuiClassic extends AbstractXholonGui { //DockPanel {
	private static final long serialVersionUID = 1L;
	private TreeItem tiRoot;
  private XholonGuiClassicTree tree;
  private TextBox currentSelectionField;
  
  protected String modelDirectory = "./config/"; // default model directory
  
  // icons for Controller nodes
  private static final Image ICON_START   = rcImages("Control_control_play_blue");
  private static final Image ICON_PAUSE   = rcImages("Control_control_pause_blue");
  private static final Image ICON_STEP    = rcImages("Control_control_fastforward_blue");
  private static final Image ICON_STOP    = rcImages("Control_control_stop_blue");
  private static final Image ICON_REFRESH = rcImages("Control_control_repeat_blue");
  
  /**
   * A service that will keep track of which IXholon node(s) are currently selected.
   * This will allow other services or xholons to act on those nodes.*/
  //protected IXholon nodeSelectionService = null;
  
  /**
   * A service that provides additional methods for IXholon instances.
   */
  //protected IXholon xholonHelperService = null;
      	
  /**
   * Constructor.
   */
	public XholonGuiClassic() {}
	
	public Object getGuiRoot() {
	  return tiRoot;
	}
	
	protected static Image rcImages(String resourceName) {
	  return new Image((ImageResource)RCImages.INSTANCE.getResource(resourceName));
	}
	
  /**
	 * Show the entire IXholon tree as a Tree.
	 * @param node Node from which to start showing the tree.
	 */
	public void showTree(IXholon node) {
		if (tiRoot != null ) { // an existing model is already running
			//removeAll();
		}
		if (app == null) {
		  app = node.getApp();
		}
    xhRoot = node;
		tiRoot = new TreeItem();
		createNode(xhRoot, tiRoot, null);
		tree = new XholonGuiClassicTree(this);
    tree.addItem(tiRoot);
    
    tree.addSelectionHandler(new SelectionHandler<TreeItem>(){
      public void onSelection(SelectionEvent<TreeItem> event) {
        TreeItem ti = event.getSelectedItem();
        handleNodeSelection(ti.getText(), ti, tree.isCtrlPressed());
      }
    });
    
    ScrollPanel scrollPanel = new ScrollPanel(tree);
    scrollPanel.setSize("500px", "500px");
    
    DockPanel panel = new DockPanel(); //this;
    panel.setStylePrimaryName("xhgui");
    
    panel.add(scrollPanel, DockPanel.CENTER);
    currentSelectionField = new TextBox();
    currentSelectionField.setVisibleLength(60); // 58
    currentSelectionField.setMaxLength(200);
    currentSelectionField.setText("Click on any node to show details");
    panel.add(currentSelectionField, DockPanel.SOUTH);
    
    // main menu at top of GUI
    MenuBar mainMenu = new MenuBar();
    mainMenu.setStylePrimaryName("mainMenu");
    mainMenu.addItem("File", new Command() {
      @Override
      public void execute() {
        
      }
    });
    mainMenu.addItem("Application", new Command() {
      @Override
      public void execute() {
        
      }
    });
    MenuBar helpMenu = new MenuBar(true);
    helpMenu.addItem("About", new Command() {
      @Override
      public void execute() {
        if (app == null) {
					//app.println("Application not yet loaded.");
				}
				else {
					app.about();
				}
      }
    });
    helpMenu.addItem("Getting Started", new Command() {
      @Override
      public void execute() {
        gettingStarted("Getting Started with Xholon", splashText, 375, 475);
      }
    });
    helpMenu.addItem("Information", new Command() {
      @Override
      public void execute() {
        if (app == null) {
					//app.println("Application not yet loaded.");
				}
				else {
					app.information();
				}
      }
    });
    helpMenu.addItem("JavaScript API", new Command() {
      @Override
      public void execute() {
        Window.open(GwtEnvironment.gwtHostPageBaseURL + "jsapidoc/", "_blank", "");
      }
    });
    mainMenu.addItem("Help", helpMenu);
    HorizontalPanel mainMenuHp = new HorizontalPanel();
    mainMenuHp.setStylePrimaryName("mainMenu");
    mainMenuHp.add(mainMenu);
    
    // toolbar with Controller buttons just below main menu
    HorizontalPanel toolbar = new HorizontalPanel();
    toolbar.setStylePrimaryName("toolbar");
    IXholon controlNode = app.getControlRoot().getFirstChild();
    while (controlNode != null) {
      toolbar.add(makeToolbarWidget(controlNode));
      controlNode = controlNode.getNextSibling();
    }
    
    VerticalPanel topVp = new VerticalPanel();
    topVp.setStylePrimaryName("mainMenu");
    topVp.setWidth("500px"); // 474
    topVp.add(mainMenuHp);
    topVp.add(toolbar);
    panel.add(topVp, DockPanel.NORTH);
    
    RootPanel.get("xhgui").add(panel);
    
    //((Application)app).clearConsole();
    // clear the contents of the xhconsole
    /*Element element = Document.get().getElementById("xhconsole").getFirstChildElement();
    if (element != null) {
        TextAreaElement textfield = element.cast();
        textfield.setValue("");
    }*/
  }
  
  @Override
  protected void setText(String text) {
    currentSelectionField.setText(text);
  }
	
  /**
	 * Configure one IXholon node, and recursively create all left children and right siblings.
	 * @param xhNode Current IXholon node.
	 * @param tiNode Current TreeItem.
	 * @param parent Parent of current TreeItem.
	 */
	protected void createNode(IXholon xhNode, TreeItem tiNode, TreeItem parent) {
		tiNode.setWidget(makeTreeItemWidget(xhNode));
		TreeItem node;
		IXholon leftChild = xhNode.getFirstChild();
		IXholon rightSibling = xhNode.getNextSibling();
		if (leftChild != null) {
			node = new TreeItem();
			tiNode.addItem(node);
			createNode(leftChild, node, tiNode);
		}
		if (rightSibling != null) {
			node = new TreeItem();
			parent.addItem(node);
			createNode(rightSibling, node, parent);
		}
	}
	
		/**
	 * Create one TreeItem node, and recursively create all children.
	 * @param xhNode Current IXholon node.
	 * @param tiNode Current TreeItem.
	 */
	protected void createNodeChildren(IXholon xhNode, TreeItem tiNode) {
		tiNode.setWidget(makeTreeItemWidget(xhNode));
		TreeItem node;
		IXholon leftChild = xhNode.getFirstChild();
		if (leftChild != null) {
			node = new TreeItem();
			tiNode.addItem(node);
			createNode(leftChild, node, tiNode);
		}
	}
	
	/**
	 * Make a toolbar widget.
	 */
	protected Widget makeToolbarWidget(IXholon node) {
	  final String nodeName = node.getName();
	  IXholonClass xhcNode = node.getXhc();
	  Image icon = null;
	  if (xhcNode.getId() == CeControl.ControlCE) {
			if ("Start".equals(node.getRoleName()))
				{icon = rcImages("Control_control_play_blue");}
			else if ("Pause".equals(node.getRoleName()))
				{icon = rcImages("Control_control_pause_blue");}
			else if ("Step".equals(node.getRoleName()))
				{icon = rcImages("Control_control_fastforward_blue");}
			else if ("Stop".equals(node.getRoleName()))
				{icon = rcImages("Control_control_stop_blue");}
			else if ("Refresh".equals(node.getRoleName()))
				{icon = rcImages("Control_control_repeat_blue");}
		}
		if (icon == null) {
		  icon = rcImages("Control_bullet_blue");
		}
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(icon);
		final IXholon nnode = node;
		Button button = new Button(nodeName, new ClickHandler() {
      public void onClick(ClickEvent event) {
        //String actionCommand = null; //event.getActionCommand();
		    if ("Refresh".equals(nodeName)) {
			    refresh();
		    }
		    else {
			    //IXholon node = xpath.evaluate("descendant-or-self::*[@name='" + actionCommand + "']", xhRoot);
			    nnode.handleNodeSelection();
		    }
      }
    });
    button.setStylePrimaryName("controlButton");
		hp.add(button);
		return hp;
	}
	
	/**
	 * Make a TreeItem widget.
	 */
	protected Widget makeTreeItemWidget(IXholon node) {
		// must create a new Image for each new panel; can't reuse ICON_ constants
	  String nodeName = node.getName();
	  IXholonClass xhcNode = node.getXhc();
	  String color = null;
	  String font = null;
	  Image icon = null;
	  String toolTip = null;
	  if (xhcNode == null) {
			if ("Application".equals(nodeName)) {
				icon = rcImages("Control_application_side_tree");
			}
		  else if (isInInheritanceHierarchy(node)) {
			  xhcNode = xhRoot.getClassNode(nodeName);
		    //System.out.println("makeTreeItemWidget( isInInheritanceHierarchy1 for " + nodeName + " " + xhcNode);
			  if (xhcNode == null) {}
			  else {
				  color = getColor((IDecoration)xhcNode);
				  if (color == null) {
				  	color = getColor((IDecoration)xhcNode.getMechanism());
				  }
				  font = getFont((IDecoration)xhcNode);
				  if (font == null) {
				  	font = getFont((IDecoration)xhcNode.getMechanism());
				  }
				  icon = getImageIcon((IDecoration)xhcNode);
				  if (icon == null) {
					  icon = getImageIcon((IDecoration)xhcNode.getMechanism());
				  }
				  toolTip = getToolTip((IDecoration)xhcNode);
				  if (toolTip == null) {
				  	toolTip = getToolTip((IDecoration)xhcNode.getMechanism());
				  }
			  }
		  }
			else {
			  System.out.println("makeTreeItemWidget( xhcNode = null for " + nodeName);
			}
		}
		else if (xhcNode.getId() == CeControl.ControlCE) {
			if ("Controller".equals(node.getRoleName()))
				{icon = rcImages("Control_controller");}
			else if ("View".equals(node.getRoleName()))
				{icon = rcImages("Control_rainbow");}
			else if ("Model".equals(node.getRoleName()))
				{icon = rcImages("Control_bricks");}
			else if ("Start".equals(node.getRoleName()))
				{icon = ICON_START;}
			else if ("Pause".equals(node.getRoleName()))
				{icon = ICON_PAUSE;}
			else if ("Step".equals(node.getRoleName()))
				{icon = ICON_STEP;}
			else if ("Stop".equals(node.getRoleName()))
				{icon = ICON_STOP;}
			else if ("Refresh".equals(node.getRoleName()))
				{icon = ICON_REFRESH;}
			else
				{icon = rcImages("Control_layout_content");}
		}
		else if (isInMechanismHierarchy(node)) {
			IXholon mechNode = app.getMechRoot();
			mechNode = xpath.evaluate("descendant-or-self::*[@name='" + nodeName + "']", mechNode);
			if (mechNode == null) {}
			else {
				color = getColor((IDecoration)mechNode);
				font = getFont((IDecoration)mechNode);
				icon = getImageIcon((IDecoration)node);
				toolTip = getToolTip((IDecoration)mechNode);
			}
		}
		
		if (icon == null) {
		  //icon = new Image("images/Control/bullet_blue.png");
		  color = getColor((IDecoration)xhcNode);
		  if (color == null) {
		    color = getColor((IDecoration)xhcNode.getMechanism());
		  }
		  font = getFont((IDecoration)xhcNode);
		  if (font == null) {
		    font = getFont((IDecoration)xhcNode.getMechanism());
		  }
		  icon = getImageIcon((IDecoration)xhcNode);
		  if (icon == null) {
		    icon = getImageIcon((IDecoration)xhcNode.getMechanism());
		  }
		  toolTip = getToolTip((IDecoration)xhcNode);
		  if (toolTip == null) {
		    toolTip = getToolTip((IDecoration)xhcNode.getMechanism());
		  }
		}
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(icon);
		Label label = new Label(nodeName);
		if ((color != null) || (font != null)) {
		  // TODO set the label's color
		  setStyle(label.getElement(), color, font);
		}
		hp.add(label);
		if (toolTip != null) {
		  hp.setTitle(toolTip);
		}
		return hp;
	}
	
	protected native void setStyle(Element element, String color, String font) /*-{
	  if (color) {element.style.color = color;}
	  if (font) {element.style.font = font;}
	}-*/;
	
	/**
	 * Get the icon of a node.
	 * ex iconFileName: "images/StateMachineEntity/stateMachine.png"
	 * ex rcImages    : "StateMachineEntity_stateMachine"
	 * @param node An instance of IXholonClass or IMechanism or other type that implements IDecoration.
	 * @return A GWT image specific to that xhcNode, or the default GWT image.
	 */
	protected Image getImageIcon(IDecoration node) {
		Image icon = null;
		String iconFileName = ((IDecoration)node).getIcon();
	  if (iconFileName != null) {
			icon = rcImages(iconFileName.substring(7, iconFileName.length() - 4).replace("/", "_"));
		}
		else {
			if (node instanceof IXholonClass) {
				IXholon p = ((IXholonClass)node).getParentNode();
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
	 * Refresh the entire tree being displayed in the viewer.
	 */
	public void refresh() {
		//System.out.println("refreshing the tree ...");
		setText("refreshing the tree ...");
		// create a new tree, starting from the same root as before
		tiRoot.removeItems(); // remove all children
		createNode(xhRoot, tiRoot, null);
		setText("tree refreshed");
	}
	
	/**
	 * Refresh the current node, including its entire sub-tree.
	 * @param ti The current Tree item.
	 * @param node The current IXholon node.
	 */
	public void refresh(Object guiItem, IXholon xhNode) {
	  TreeItem ti = (TreeItem)guiItem;
		if (ti == null) {return;}
		if (xhNode == null) {return;}
		ti.removeItems();
		createNodeChildren(xhNode, ti);
	}
	
  protected String getGuiItemName(final Object guiItem) {
    final TreeItem ti = (TreeItem)guiItem;
    return (String)ti.getText();
  }
  
  protected Object getGuiItem(String nodeName) {
    // search recursively from tiRoot
    return getGuiItemRecurse(nodeName, tiRoot);
  }
  
  protected TreeItem getGuiItemRecurse(String nodeName, TreeItem ti) {
    if (nodeName.equals(ti.getText())) {
      return ti;
    }
    for (int i = 0; i < ti.getChildCount(); i++) {
      TreeItem tiChild = getGuiItemRecurse(nodeName, ti.getChild(i));
      if (tiChild != null) {
        return tiChild;
      }
    }
    return null;
  }
    
  protected boolean isInInheritanceHierarchy(Object guiItem) {
    TreeItem treeItem = (TreeItem)guiItem;
    while (treeItem != null) {
      if (treeItem.getText().equals("InheritanceHierarchy")) {
        return true;
      }
      treeItem = treeItem.getParentItem();
    }
    return false;
  }
	
  protected boolean isInMechanismHierarchy(Object guiItem) {
    TreeItem treeItem = (TreeItem)guiItem;
    while (treeItem != null) {
      if (treeItem.getText().equals("MechanismHierarchy")) {
        return true;
      }
      treeItem = treeItem.getParentItem();
    }
    return false;
  }
    
	public String getModelDirectory() {
		return modelDirectory;
	}

	public void setModelDirectory(String modelDirectory) {
		this.modelDirectory = modelDirectory;
	}
	
}


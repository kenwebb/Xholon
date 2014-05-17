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

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Node;

import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;

import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;

/**
 * A subclass of GWT Tree that knows how to process a right-click on a TreeItem.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on July 31, 2013)
 */
class XholonGuiClassicTree extends Tree
  implements ContextMenuHandler, MouseDownHandler, EventListener, DragOverHandler, DropHandler {
  
  /**
   * Height of a TreeItem widget, in pixels.
   * This is the same even after zooming in or out (Firefox tested).
   * Note that ti.getOffsetHeight() returns the height of the entire Tree.
   */
  private static final int TI_HEIGHT = 25;
  
  //private PopupPanel contextMenu;
  
  private TreeItem contextMenuTreeItem;
  
  private IXholonGui gui = null;
  
  // used to cache a Ctrl key event (and all other events)
  private Event currentEvent;
  
  public XholonGuiClassicTree(IXholonGui gui) {
    this.gui = gui;
    addMouseDownHandler(this);
    addDomHandler(this, ContextMenuEvent.getType());
    // Drag and Drop
  	//consoleLog("adding DragOverHandler to Tree ...");
    addDomHandler(this, DragOverEvent.getType());
  	//consoleLog("adding DropHandler to Tree ...");
    addDomHandler(this, DropEvent.getType());
  }
  
  // Call this method from within the onSelection method to determine if the ctrl key was pressed.
  public boolean isCtrlPressed() {
    return currentEvent != null ? currentEvent.getCtrlKey() : false;
  }
  
  public boolean isShiftPressed() {
    return currentEvent != null ? currentEvent.getShiftKey() : false;
  }

  @Override
  public void onBrowserEvent(Event event) {
    currentEvent = event;
    System.out.println("XholonGuiClassicTree received event: " + event.getType());
    super.onBrowserEvent(event);
    currentEvent = null;
  }
  
  /**
	 * Handle a context menu (right-click) event.
	 */
	public void onContextMenu(ContextMenuEvent event) {
	  
	  // allow the browser's default context menu
	  if (isShiftPressed()) {return;}
	  
	  //System.out.println("right-click on: " + contextMenuTreeItem.getText());
	  
	  // prevent the browser from opening its default context menu
	  event.preventDefault();
	  event.stopPropagation();
	  
	  int x = 0; //event.getNativeEvent().getClientX();
    int y = 0; //event.getNativeEvent().getClientY();
	  
	  gui.makeContextMenu(contextMenuTreeItem, x, y);
	}
	
	// not sure if I really need this
	public void onMouseDown(MouseDownEvent event) {
	  if (event.getNativeButton() == NativeEvent.BUTTON_RIGHT) {
	    contextMenuTreeItem = getTreeItemAt((TreeItem)gui.getGuiRoot(), event.getNativeEvent().getClientY());
	    //consoleLog("mouse-down on: " + contextMenuTreeItem.getText());
	    event.preventDefault();
	    event.stopPropagation();
	  }
	}
	
	// ondragover - this is necessary; drop fails to do anything without this ???
  public void onDragOver(DragOverEvent doe) {
    //consoleLog("DragOverEvent Tree");
    //consoleLog(doe.getNativeEvent());
    doe.preventDefault();
  }
	
	// drag and drop
  public void onDrop(DropEvent de) {
    String data = de.getData("text");
    if (data == null) {return;}
    //consoleLog("DropEvent Tree");
    //consoleLog(de);
    //consoleLog(de.getType());
    //consoleLog(de.getDataTransfer());
    //consoleLog(data);
    //consoleLog(de.getNativeEvent());
    //consoleLog(de.getNativeEvent().getString());
    //consoleLog(de.getNativeEvent().getEventTarget());
    Element htmlEle = Element.as(de.getNativeEvent().getEventTarget());
    //consoleLog(htmlEle);
    //consoleLog(htmlEle.getInnerText()); // "hello_2"
    Node htmlNode = htmlEle.getFirstChild();
    String nodeName = htmlNode.getNodeValue(); // "hello_2"
    //consoleLog(nodeName);
    de.stopPropagation();
    de.preventDefault();
    if (nodeName == null) {return;}
    gui.handleDrop(nodeName, data);
  }
  
  public native void consoleLog(Object obj) /*-{
	  if ($wnd.console && $wnd.console.log) {
	    $wnd.console.log(obj);
	  }
	}-*/;
	
	/**
	 * Get the TreeItem at the location where the user clicked.
	 * This is a recursive method that searches the tree top-down.
	 * @param ti The current TreeItem being checked.
	 * @param clickY The Y coordinate (in pixels) where the user clicked.
	 */
	protected TreeItem getTreeItemAt(TreeItem ti, int clickY) {
	  int top = ti.getAbsoluteTop();
	  if (top == 0) {return null;} // the TreeItem is not visible
	  if ((top >= 0) && (clickY >= top) && (clickY < top + TI_HEIGHT)) {
	    return ti;
	  }
	  for (int i = 0 ; i < ti.getChildCount(); i++) {
	    TreeItem childTi = getTreeItemAt(ti.getChild(i), clickY);
	    if (childTi != null) {
	      return childTi;
	    }
	  }
	  return null;
	}
	
}

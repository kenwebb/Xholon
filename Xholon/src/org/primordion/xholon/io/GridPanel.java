/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2005, 2006, 2007, 2008 Ken Webb
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

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
//mport com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Image;
//import com.google.gwt.user.client.ui.MenuBar;
//import com.google.gwt.user.client.ui.MenuItem;
//import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.AbstractGrid;
import org.primordion.xholon.base.IGrid;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.io.XholonGwtTabPanelHelper;
import org.primordion.xholon.io.console.IXholonConsole;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.NodeSelectionService;
import org.primordion.xholon.util.Misc;

/**
 * A graphic panel in which to display 2D grids.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.2 (Created on November 27, 2005)
 */
public abstract class GridPanel extends Xholon implements IGridPanel {
  
  private static final long serialVersionUID = 8201866815042917543L;
  
  protected IXholon gridOwner = null; // Xholon that owns the Row and GridCell xholons
  protected IApplication app = null; // The IApplication instance that owns the model
  protected static boolean isFrozen = false; // whether or not the display is currently frozen
  protected String backgroundImage = "default.png"; // optional background image

  // Locations and sizes
  protected AbstractGrid upperLeft = null; // xholon at upper left corner of the grid
  protected int cellSize = 5; // default size of a grid cell, in pixels
  protected int nRows = 0;
  protected int nCols = 0;
  protected int neighType = IGrid.NEIGHBORHOOD_MOORE;
  
  /**
   *  Whether or not to use shapes for drawing.
   */
  protected boolean useShapes = false;
  
  /**
   * A service that provides additional methods for IXholon instances.
   */
  protected IXholon xholonHelperService = null;
  
  /**
   * A service that will keep track of which IXholon node(s) are currently selected,
   * specifically which GridPanel.
   * This will allow other services or xholons to act on those nodes.
   */
  protected IXholon nodeSelectionService = null;
  
  /**
   * A GWT Canvas object.
   */
  protected Canvas canvas = null;
  
  /**
   * A GWT rendering context.
   */
  protected Context2d context = null;
  
  /**
   * Current X coordinate as continuously calculated by getSelectedGridCell(MouseEvent me) .
   * This is needed by getSelectedGridCell(ContextMenuEvent cme) .
   */
  protected int currentX = 0;
  
  /**
   * Current Y coordinate as continuously calculated by getSelectedGridCell(MouseEvent me) .
   * This is needed by getSelectedGridCell(ContextMenuEvent cme) .
   */
  protected int currentY = 0;
  
  /**
   * hexagon x coordinates
   * for use with drawHexGrid()
   */
  protected int[] xCoorHex = null;
  
  /**
   * hexagon y coordinates
   * for use with drawHexGrid()
   */
  protected int[] yCoorHex = null;
  
  /**
   * The numeric index of an optional context-specific tab in the HTML page.
   * This tab is shared amongst all grid cells, only one of which is currently the context node.
   * A value of -1 means there is no tab.
   */
  protected int contextTabIndex = -1;
  
  /**
   * The IXholonConsole associated with the optional context-specific tab in the HTML page.
   */
  protected IXholonConsole xholonConsole = null;
  
  /**
   * Color of the optional context node when displayed on the grid.
   */
  protected static final CssColor COLOR_CONTEXT = CssColor.make("red");

  
  /**
   * Constructor
   */
  public GridPanel() {}
  
  /**
   * Constructor.
   * @param gridOwner Xholon that owns the grid.
   */
  public GridPanel(IXholon gridOwner) {}
  
  /*
   * @see org.primordion.xholon.io.IGridPanel#getGridOwner()
   */
  public IXholon getGridOwner() {return gridOwner;}
  
  /*
   * @see org.primordion.xholon.io.IGridPanel#initGridPanel(org.primordion.xholon.base.IXholon)
   */
  public void initGridPanel(IXholon gridOwner)
  {
    if (gridOwner == null) {
      System.out.println("GridPanel initGridOwner: gridOwner is null");
    }
    else {
      this.gridOwner = gridOwner;
      app = gridOwner.getApp();
      setUpperLeft();
      setNumRows();
      setNumCols();
      setNeighType(((AbstractGrid)gridOwner).getNeighType());
      
      canvas = Canvas.createIfSupported();
      if (canvas == null) {
        System.out.println("unable to create canvas");
        return;
      }
      int width = cellSize * nCols;
      int height = cellSize * nRows;
      canvas.setWidth(width + "px");
      canvas.setHeight(height + "px");
      canvas.setCoordinateSpaceWidth(width);
      canvas.setCoordinateSpaceHeight(height);
      RootPanel.get("xhcanvas").add(canvas);
      context = canvas.getContext2d();

      initEventHandling();
    }
    // setBackground() is a Swing Panel method ?
    //setBackground(Color.GRAY);
    //setBackground(CssColor.make("gray"));
  }
  
  /**
   * Intitialize event handling for the GridPanel.
   * The initialized listener will subsequenly handle mouse events.
   */
  protected void initEventHandling()
  {
    
    // Mouse Down
    canvas.addMouseDownHandler( new MouseDownHandler() {
      public void onMouseDown(final MouseDownEvent me) {
        System.out.println("MouseDownEvent");
        switch (me.getNativeButton()) {
          case NativeEvent.BUTTON_LEFT:
            handleButton1Event(me);
            break;
          case NativeEvent.BUTTON_MIDDLE:
            handleButton2Event(me);
            break;
          //case NativeEvent.BUTTON_RIGHT:
          //  handleButton3Event(me);
          //  break;
          default:
            break;
        }
      }
    });
    
    // Context Menu
    canvas.addDomHandler(
      new ContextMenuHandler() {
        public void onContextMenu(ContextMenuEvent cme) {
          handleContextMenuEvent(cme);
        }
      },
      ContextMenuEvent.getType());
    
    // Mouse Move
    // this is required, to generate X,Y coordinates for ContextMenu code
    canvas.addMouseMoveHandler( new MouseMoveHandler() {
      public void onMouseMove(final MouseMoveEvent me) {
        // TODO there doesn't seem to be any way to refresh the tooltip while the mouse remains in canvas
        IXholon selectedGridCell = getSelectedGridCell(me);
        canvas.setTitle(getToolTipText(selectedGridCell));
      }
    });
    
    // Drag Over
    // this is necessary; drop fails to do anything without this
    //consoleLog("adding DragOverHandler ...");
    canvas.addDragOverHandler( new DragOverHandler() {
      public void onDragOver(DragOverEvent doe) {
        //consoleLog("DragOverEvent");
        //consoleLog(doe.getNativeEvent());
        doe.preventDefault();
      }
    });
    
    // Drop
    //consoleLog("adding DropHandler ...");
    canvas.addDropHandler( new DropHandler() {
      public void onDrop(DropEvent de) {
        String data = de.getData("text");
        if (data == null) {return;}
        /*consoleLog("DropEvent");
        consoleLog(de);
        consoleLog(de.getType());
        consoleLog(de.getDataTransfer());
        consoleLog(data);
        consoleLog(de.getNativeEvent());*/
        de.stopPropagation();
        de.preventDefault();
        // TODO
        IXholon selectedGridCell = getSelectedGridCell(de);
        if (selectedGridCell == null) {return;}
        //consoleLog(selectedGridCell);
        sendXholonHelperService(ISignal.ACTION_PASTE_LASTCHILD_FROMDROP, data, selectedGridCell);
      }
    });
    
    // Key Up - arrow keys
    canvas.addKeyUpHandler(new KeyUpHandler() {
      @Override
      public void onKeyUp(KeyUpEvent kue) {
        //consoleLog("KeyUpEvent");
        //consoleLog(kue.getNativeKeyCode());
        if (kue.isArrow(kue.getNativeKeyCode())) { // int keyCode
          IXholon node = xholonConsole.getContext();
          if ((node == null) || !(node instanceof AbstractGrid)) {return;}
          kue.stopPropagation();
          kue.preventDefault();
          
          switch (getNeighType()) {
          case IGrid.NEIGHBORHOOD_MOORE:
          case IGrid.NEIGHBORHOOD_VON_NEUMANN:
          case IGrid.NEIGHBORHOOD_1DCA:
            if (kue.isUpArrow()) {node = node.getPort(IGrid.P_NORTH);} // 38
            else if (kue.isDownArrow()) {node = node.getPort(IGrid.P_SOUTH);} // 40
            else if (kue.isLeftArrow()) {node = node.getPort(IGrid.P_WEST);} // 37
            else if (kue.isRightArrow()) {node = node.getPort(IGrid.P_EAST);} // 39
            break;
          case IGrid.NEIGHBORHOOD_HEXAGONAL:
            // TODO
            break;
          default:
            break;
          }
          
          if (node != null) {
            updateContextNode(node);
          }
        }
      }
    });
  }
  
  /**
   * Get the grid cell that corresponds to a location on the grid that's been clicked.
   * @param me A MouseEvent.
   * @return A grid cell, or null;
   */
  protected IXholon getSelectedGridCell(final MouseEvent me) {
    currentX = me.getX();
    currentY = me.getY();
    int row = currentY / cellSize;
    int col = currentX / cellSize;
    /*consoleLog("getSelectedGridCell(final MouseEvent me");
    consoleLog(currentX);
    consoleLog(currentY);
    consoleLog(col);
    consoleLog(row);*/
    return getSelectedGridCell(row, col);
  }
  
  /**
   * Get the grid cell that corresponds to a location on the grid that's been right-clicked.
   * @param cme A ContextMenuEvent.
   * @return A grid cell, or null;
   */
  protected IXholon getSelectedGridCell(final ContextMenuEvent cme) {
    int row = currentY / cellSize;
    int col = currentX / cellSize;
    return getSelectedGridCell(row, col);
  }
  
  /**
   * Get the grid cell that corresponds to a drop target.
   * @param de A DropEvent.
   * @return A grid cell, or null;
   */
  protected IXholon getSelectedGridCell(final DropEvent de) {
    // alternatively, can get NativeEvent clientX Y and screenX Y; incorrect ?
    int row = currentY / cellSize;
    int col = currentX / cellSize;
    /*consoleLog("getSelectedGridCell(final DropEvent de)");
    consoleLog(currentX);
    consoleLog(currentY);
    consoleLog(col);
    consoleLog(row);*/
    /*consoleLog("NativeEvent");
    consoleLog(de.getNativeEvent().getClientX());
    consoleLog(de.getNativeEvent().getClientY());
    consoleLog(de.getNativeEvent().getScreenX());
    consoleLog(de.getNativeEvent().getScreenY());*/
    return getSelectedGridCell(row, col);
  }
  
  /**
   * Get the grid cell at the specified grid row and column.
   * @param row 
   * @param col 
   * @return An IXholon, or null.
   */
  protected IXholon getSelectedGridCell(int row, int col) {
    IXholon selectedGridCell = upperLeft.getParentNode();
    if (selectedGridCell == null) {return null;}
    for (int r = 0; r < row; r++) {
      selectedGridCell = selectedGridCell.getNextSibling();
      if (selectedGridCell == null) {return null;}
    }
    selectedGridCell = selectedGridCell.getFirstChild();
    if (selectedGridCell == null) {return null;}
    for (int c = 0; c < col; c++) {
      selectedGridCell = selectedGridCell.getNextSibling();
      if (selectedGridCell == null) {return null;}
    }
    return selectedGridCell;
  }
  
  /**
   * Handle MouseEvent.BUTTON1 .
   * @param me A mouse event.
   */
  protected void handleButton1Event(final MouseDownEvent me) {
    IXholon selectedGridCell = getSelectedGridCell(me);
    if (selectedGridCell == null) {return;}
    if (me.isControlKeyDown()) {
      // Ctrl click
      handleCtrlClick(selectedGridCell);
    }
    else {
      // simple click
      selectedGridCell.doAction("button1");
    }
  }
  
  /**
   * Handle MouseEvent.BUTTON2 .
   * @param me A mouse event.
   */
  protected void handleButton2Event(final MouseDownEvent me) {
    //System.out.println("button2");
  }
  
  /**
   * Handle a ContextMenuEvent (right-click).
   * @param cme A context menu event.
   */
  protected void handleContextMenuEvent(final ContextMenuEvent cme) {
    // allow the browser's default context menu
    if (cme.getNativeEvent().getShiftKey()) {return;}
    
    cme.preventDefault();
    cme.stopPropagation();
    //System.out.println("ContextMenuEvent: ");
    IXholon selectedGridCell = getSelectedGridCell(cme);
    if (selectedGridCell == null) {return;}
    rememberButton3GridPanel(this); // remember the GridPanel instance
    //System.out.println("ContextMenuEvent: " + selectedGridCell);
    showPopupMenu(selectedGridCell);
  }
  
  /**
   * Handle a Ctrl-click
   */
  protected void handleCtrlClick(IXholon selectedGridCell) {
    //consoleLog("Ctrl-click "); // + sgcName);
    // open or reuse xhtab on this grid cell
    if (contextTabIndex == -1) {
      IMessage respMsg = sendXholonHelperService(ISignal.ACTION_START_XHOLON_CONSOLE, selectedGridCell, null);
      if (respMsg != null) {
        Object[] data = (Object[])respMsg.getData();
        contextTabIndex = (Integer)data[0]; //respMsg.getData();
        xholonConsole = (IXholonConsole)data[1];
      }
    }
    else {
      updateContextNode(selectedGridCell);
    }
  }
  
  /**
   * Update the context node in the XholonConsole and in the tab.
   * @param selectedGridCell The new context node.
   */
  protected void updateContextNode(IXholon selectedGridCell) {
    String idStr = selectedGridCell.getName("R^^_i^");
    String xpathExpression = getXPath().getExpression(selectedGridCell, app.getRoot(), false);
    String tooltip = xpathExpression == null ? idStr : xpathExpression;
    XholonGwtTabPanelHelper.updateTabHeader(idStr, tooltip, contextTabIndex);
    // update the XholonConsole with the new context node
    xholonConsole.setContext(selectedGridCell);
  }
  
  /*
   * Get the text to display in a tooltip.
   * @param selectedGridCell
   */
  public String getToolTipText(final IXholon selectedGridCell) {
    if (selectedGridCell != null) {
      return selectedGridCell.toString();
    }
    else {
      return null;
    }
  }
  
  /**
   * Show a popup menu for a specified node.
   * @param node The IXholon that the popup menu is for.
   */
  protected void showPopupMenu(final IXholon node) {
    new XholonGuiForHtml5().makeContextMenu(node,
        0, //canvas.getAbsoluteLeft() + currentX + cellSize,
        canvas.getAbsoluteTop() + currentY + cellSize);
    
    /*PopupPanel popup = new PopupPanel(true);
    popup.hide();
    MenuBar menu = new MenuBar();
    
    // Special
    String[] specialItems = node.getActionList();
    System.out.println(specialItems);
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
        sendXholonHelperService(ISignal.ACTION_COPY_TOCLIPBOARD, node);
      }
    });
    editSubMenu.addItem("Cut", new Command() {
      public void execute() {
        // it shouldn't really cut the grid cell; maybe cut the firstChild?
        sendXholonHelperService(ISignal.ACTION_CUT_TOCLIPBOARD, node);
      }
    }).setEnabled(false);
    editSubMenu.addItem("Paste Last Child", new Command() {
      public void execute() {
        sendXholonHelperService(ISignal.ACTION_PASTE_LASTCHILD_FROMCLIPBOARD, node);
      }
    });
    menu.addItem("Edit", editSubMenu);
    
    popup.add(menu);
    popup.setPopupPosition(canvas.getAbsoluteLeft() + currentX + cellSize,
      canvas.getAbsoluteTop() + currentY + cellSize);
    popup.show();*/
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
    if (sender == null) {
      sender = app;
    }
    if (xholonHelperService == null) {
      xholonHelperService = app.getService(IXholonService.XHSRV_XHOLON_HELPER);
    }
    if (xholonHelperService == null) {
      return null;
    }
    else {
      return xholonHelperService.sendSyncMessage(signal, data, sender);
    }
  }
  
  /**
   * Remember a MouseEvent.BUTTON3 selection (Right-Click).
   * @param node
   */
  protected void rememberButton3GridPanel(IXholon node) {
    // pass the node to the NodeSelectionService by sending it as a message
    if (nodeSelectionService == null) {
      nodeSelectionService = app.getService(IXholonService.XHSRV_NODE_SELECTION);
    }
    if (nodeSelectionService != null) {
      nodeSelectionService
        .sendSyncMessage(NodeSelectionService.SIG_REMEMBER_BUTTON3_GRIDPANEL_REQ, node, app);
    }
  }
  
  /**
   * Toggle whether or not GridPanel is currently updating its display.
   */
  public static void toggleFrozen()
  {
    isFrozen = !isFrozen;
  }
  
  /**
   * Get whether or not instances of GridPanel are currently frozen.
   * @return true or false
   */
  public static boolean getIsFrozen() {return isFrozen;}
  
  /**
   * Set whether or not instances of GridPanel should be frozen frozen.
   * @param isFrozenArg true or false
   */
  public static void setIsFrozen(boolean isFrozenArg) {isFrozen = isFrozenArg;}
  
  /*
   * @see org.primordion.xholon.io.IGridPanel#getNumRows()
   */
  public int getNumRows() {return nRows;}
  
  /*
   * @see org.primordion.xholon.io.IGridPanel#getNumCols()
   */
  public int getNumCols() {return nCols;}
  
  /*
   * @see org.primordion.xholon.io.IGridPanel#getCellSize()
   */
  public int getCellSize() {return cellSize;}
  
  /*
   * @see org.primordion.xholon.io.IGridPanel#setCellSize(int)
   */
  public void setCellSize(int cellSize) {
    //System.out.println("GridPanel setCellSize " + cellSize);
    this.cellSize = cellSize;
  }
  
  /*
   * @see org.primordion.xholon.io.IGridPanel#getUseShapes()
   */
  public boolean getUseShapes() {return useShapes;}
  
  /*
   * @see org.primordion.xholon.io.IGridPanel#setUseShapes(boolean)
   */
  public void setUseShapes(boolean useShapes) {
    this.useShapes = useShapes;
  }
  
  /*
   * @see org.primordion.xholon.io.IGridPanel#getNeighType()
   */
  public int getNeighType() {return neighType;}
  
  /*
   * @see org.primordion.xholon.io.IGridPanel#setNeighType(int)
   */
  public void setNeighType(int neighType) {this.neighType = neighType;}
  
  /**
   * Set the xholon at the upper left corner of the grid.
   */
  protected void setUpperLeft()
  {
    AbstractGrid row = (AbstractGrid)gridOwner.getFirstChild();
    AbstractGrid col = null;
    while (row != null) {
      if (row.getXhcName().equals("Row")
          || row.getXhType() == IXholonClass.XhtypeGridEntity
          || row.getRoleName().equals("row")) { // first child of type "Row"
        col = (AbstractGrid)row.getFirstChild();
        while (col != null) {
          if (col.getXhcName().equals("GridCell")
              || col.getXhType() == IXholonClass.XhtypeGridEntityActivePassive
              || col.getRoleName().equals("gridcell")) { // first child of type "GridCell"
            upperLeft = col; // found it
            return;
          }
          col = (AbstractGrid)col.getNextSibling();
        }
        return;
      }
      row = (AbstractGrid)row.getNextSibling();
    }
  }
  
  /**
   * Set the number of rows in the grid, by counting the number of children of type "Row".
   */
  protected void setNumRows()
  {
    AbstractGrid row = (AbstractGrid)gridOwner.getFirstChild();
    while (row != null) {
      if (row.getXhcName().equals("Row")
          || row.getXhType() == IXholonClass.XhtypeGridEntity
          || row.getRoleName().equals("row")) {
        nRows++;
      }
      row = (AbstractGrid)row.getNextSibling();
    }
  }
  
  /**
   * Set the number of columns in the grid, by counting the number of children of type "GridCell"
   * contained within the first child of type "Row".
   */
  protected void setNumCols()
  {
    AbstractGrid row = (AbstractGrid)gridOwner.getFirstChild();
    AbstractGrid col = null;
    while (row != null) {
      if (row.getXhcName().equals("Row")
          || row.getXhType() == IXholonClass.XhtypeGridEntity
          || row.getRoleName().equals("row")) { // first child of type "Row"
        col = (AbstractGrid)row.getFirstChild();
        while (col != null) {
          if (col.getXhcName().equals("GridCell")
              || col.getXhType() == IXholonClass.XhtypeGridEntityActivePassive
              || col.getRoleName().equals("gridcell")) { // first child of type "GridCell"
            nCols++;
          }
          col = (AbstractGrid)col.getNextSibling();
        }
        return;
      }
      row = (AbstractGrid)row.getNextSibling();
    }
  }
  
  /*
   * @see org.primordion.xholon.io.IGridPanel#getColor(org.primordion.xholon.base.IXholon)
   */
  public com.google.gwt.canvas.dom.client.CssColor getColor(IXholon xhNode)
  {
    return CssColor.make("gray"); // default color
  }
  
  /*
   * @see org.primordion.xholon.io.IGridPanel#getShape(org.primordion.xholon.base.IXholon)
   */
  public int getShape(IXholon xhNode)
  {
    return GPSHAPE_NOSHAPE;
  }
  
  /*
   * @see org.primordion.xholon.io.IGridPanel#paintComponent(com.google.gwt.canvas.dom.client.Context2d)
   */
  public void paintComponent(Context2d c)
  {
    if (isFrozen) {return;}
    //drawBackgroundImage(c, backgroundImage);
    switch (getNeighType()) {
    case IGrid.NEIGHBORHOOD_MOORE:
    case IGrid.NEIGHBORHOOD_VON_NEUMANN:
      drawGrid(this.context);
      break;
    case IGrid.NEIGHBORHOOD_HEXAGONAL:
      drawHexGrid(this.context);
      break;
    case IGrid.NEIGHBORHOOD_1DCA:
      draw1dCaGrid(this.context);
      break;
    default:
      break;
    }
  }

  /**
   * Draw a background image on the grid.
   * @param ctx 
   * @param src 
   */
  protected void drawBackgroundImage(Context2d ctx, String src)
  {
    drawImage(ctx, src, 0, 0);
  }
  
  /**
   * Draw the grid.
   * @param ctx A GWT Context2d object.
   */
  protected void drawGrid(Context2d ctx)
  {
    AbstractGrid currentCell = upperLeft;
    AbstractGrid startOfRow = upperLeft;
    for (int i = 0; i < nRows; i++) {
      for (int j = 0; j < nCols; j++) {
        if ((xholonConsole != null) && (currentCell == xholonConsole.getContext())) {
          ctx.setFillStyle(COLOR_CONTEXT);
        }
        else {
          ctx.setFillStyle(getColor(currentCell));
        }
        ctx.beginPath();
        ctx.rect(j*cellSize, i*cellSize, cellSize, cellSize);
        ctx.closePath();
        ctx.fill();
        if (useShapes) {
          drawAgents(ctx, currentCell, j*cellSize, i*cellSize);
        }
        currentCell = (AbstractGrid)currentCell.port[IGrid.P_EAST]; // get next cell
      }
      startOfRow = (AbstractGrid)startOfRow.port[IGrid.P_SOUTH]; // get start of next row
      currentCell = (AbstractGrid)startOfRow;
    }
  }
  
  /**
   * Draw a hexagonal grid.
   * @param ctx A GWT Context2d object.
   */
  protected void drawHexGrid(Context2d ctx)
  {
    AbstractGrid currentCell = upperLeft;
    AbstractGrid startOfRow = upperLeft;
    
    if (xCoorHex == null) {
      // initialize
      xCoorHex = new int[6];
      xCoorHex[0] = (int)(0.25*cellSize);
      xCoorHex[1] = (int)(1.0*cellSize);
      xCoorHex[2] = (int)(1.25*cellSize);
      xCoorHex[3] = (int)(1.0*cellSize);
      xCoorHex[4] = (int)(0.25*cellSize);
      xCoorHex[5] = (int)(0.0*cellSize);
      
      yCoorHex = new int[6];
      yCoorHex[0] = (int)(1.0*cellSize);
      yCoorHex[1] = (int)(1.0*cellSize);
      yCoorHex[2] = (int)(0.5*cellSize);
      yCoorHex[3] = (int)(0.0*cellSize);
      yCoorHex[4] = (int)(0.0*cellSize);
      yCoorHex[5] = (int)(0.5*cellSize);
    }
    
    int nPoints = 6;
    int i,j;
    for (i = 0; i < nRows; i++) {
      for (j = 0; j < nCols; j++) {
        //ctx.setFillStyle(getColor(currentCell));
        if ((xholonConsole != null) && (currentCell == xholonConsole.getContext())) {
          ctx.setFillStyle(COLOR_CONTEXT);
        }
        else {
          ctx.setFillStyle(getColor(currentCell));
        }
        if (Misc.isEven(j)) {
          drawPolygon(ctx, j*cellSize, (int)(i*cellSize - 0.5*cellSize), xCoorHex, yCoorHex, nPoints);
          if (useShapes) {
            drawAgents(ctx, currentCell, j*cellSize, i*cellSize);
          }
          currentCell = (AbstractGrid)currentCell.port[IGrid.P_HEX1]; // get next cell
        }
        else { // odd
          drawPolygon(ctx, j*cellSize, (int)(i*cellSize), xCoorHex, yCoorHex, nPoints);
          if (useShapes) {
            drawAgents(ctx, currentCell, j*cellSize, i*cellSize);
          }
          currentCell = (AbstractGrid)currentCell.port[IGrid.P_HEX2]; // get next cell
        }
      }
      startOfRow = (AbstractGrid)startOfRow.port[IGrid.P_HEX3]; // get start of next row
      currentCell = (AbstractGrid)startOfRow;
      if (Misc.isEven(nCols)) {
        //hexagon.translate(-(j*cellSize), cellSize);
        // TODO ?
      }
      else { // odd
        //hexagon.translate(-(j*cellSize), (int)(0.5*cellSize));
        // TODO ?
      }
    }
  }
  
  /**
   * Draw a 1D CA grid.
   * @param ctx A GWT Context2d object.
   */
  protected void draw1dCaGrid(Context2d ctx)
  {
    AbstractGrid currentCell = upperLeft;
    AbstractGrid startOfRow = upperLeft;
    for (int i = 0; i < nRows; i++) {
      for (int j = 0; j < nCols; j++) {
        //ctx.setFillStyle(getColor(currentCell));
        if ((xholonConsole != null) && (currentCell == xholonConsole.getContext())) {
          ctx.setFillStyle(COLOR_CONTEXT);
        }
        else {
          ctx.setFillStyle(getColor(currentCell));
        }
        ctx.beginPath();
        ctx.rect(j*cellSize, i*cellSize, cellSize, cellSize);
        ctx.closePath();
        ctx.fill();
        if (useShapes) {
          drawAgents(ctx, currentCell, j*cellSize, i*cellSize);
        }
        currentCell = (AbstractGrid)currentCell.port[IGrid.P_CARIGHTNEIGHBOR]; // get next cell
      }
      startOfRow = (AbstractGrid)startOfRow.port[IGrid.P_CAFUTURESELF]; // get start of next row
      currentCell = (AbstractGrid)startOfRow;
    }
  }
  
  /**
   * Draw agents within the current grid cell.
   * Only one agent is drawn, for now.
   * GWT code to draw a triangle:
<code>
Canvas canvas = Canvas.createIfSupported();
Context2d context1 = canvas.getContext2d();
context1.beginPath();
context1.moveTo(25,0);
context1.lineTo(0,20);
context1.lineTo(25,40);
context1.lineTo(25,0);
context1.fill();
context1.closePath();
</code>
   * @param ctx A GWT Context2d object.
   * @param currentCell The grid cell that the agents will draw themselves in.
   * @param x X coordinate of the grid cell.
   * @param y Y coordinate of the grid cell.
   */
  protected void drawAgents(Context2d ctx, AbstractGrid currentCell, int x, int y)
  {
    IXholon agent = currentCell.getFirstChild();
    if (agent != null) {
      //g.setColor(getColor(agent));
      ctx.setFillStyle(getColor(agent));
      int shape = getShape(agent);
      switch (shape) {
      case GPSHAPE_CIRCLE: // OK
        ctx.beginPath();
        ctx.arc(x, y, cellSize*0.5, 0, Math.PI*2);
        ctx.closePath();
        ctx.fill();
        
        //g.fillArc(x, y, cellSize, cellSize, 0, 360);
        break;
      case GPSHAPE_TRIANGLE: // OK
        {
        int xCoor[] = {(int)(0.0*cellSize), (int)(1.0*cellSize), (int)(0.5*cellSize)};
        int yCoor[] = {(int)(1.0*cellSize), (int)(1.0*cellSize), (int)(0.0*cellSize)};
        drawPolygon(ctx, x, y, xCoor, yCoor, 3);
        
        //Polygon triangle = new Polygon(xCoor, yCoor, 3);
        //triangle.translate(x, y);
        //g.fillPolygon(triangle);
        
        /*ctx.save();
        ctx.translate(x, y);
        ctx.beginPath();
        ctx.moveTo(0.0*cellSize, 1.0*cellSize);
        ctx.lineTo(1.0*cellSize, 1.0*cellSize);
        ctx.lineTo(0.5*cellSize, 0.0*cellSize);
        ctx.lineTo(0.0*cellSize, 1.0*cellSize);
        ctx.fill();
        ctx.closePath();
        ctx.restore();*/
        }
        break;
      case GPSHAPE_RECTANGLE: // OK
        {
        int xCoor[] =
          {(int)(0.1*cellSize), (int)(0.9*cellSize),
           (int)(0.9*cellSize), (int)(0.1*cellSize)};
        int yCoor[] =
          {(int)(0.9*cellSize), (int)(0.9*cellSize),
           (int)(0.1*cellSize), (int)(0.1*cellSize)};
        drawPolygon(ctx, x, y, xCoor, yCoor, 4);
        
        //Polygon rectangle = new Polygon(xCoor, yCoor, 4);
        //rectangle.translate(x, y);
        //g.fillPolygon(rectangle);
        
        /*ctx.save();
        ctx.translate(x, y);
        ctx.beginPath();
        ctx.moveTo(0.1*cellSize, 0.9*cellSize);
        ctx.lineTo(0.9*cellSize, 0.9*cellSize);
        ctx.lineTo(0.9*cellSize, 0.1*cellSize);
        ctx.lineTo(0.1*cellSize, 0.1*cellSize);
        ctx.lineTo(0.1*cellSize, 0.9*cellSize);
        ctx.fill();
        ctx.closePath();
        ctx.restore();*/
        }
        break;
      case GPSHAPE_PENTAGON:
        {
        int xCoor[] =
            {(int)(0.2*cellSize), (int)(0.8*cellSize),
             (int)(1.0*cellSize), (int)(0.5*cellSize),  (int)(0.0*cellSize)};
        int yCoor[] =
            {(int)(1.0*cellSize),  (int)(1.0*cellSize),
             (int)(0.5*cellSize),  (int)(0.0*cellSize), (int)(0.5*cellSize)};
        drawPolygon(ctx, x, y, xCoor, yCoor, 5);
        
        //Polygon pentagon = new Polygon(xCoor, yCoor, 5);
        //pentagon.translate(x, y);
        //g.fillPolygon(pentagon);
        }
        break;
      case GPSHAPE_HEXAGON:
        {
        int xCoor[] =
            {(int)(0.25*cellSize), (int)(0.75*cellSize),  (int)(1.0*cellSize),
             (int)(0.75*cellSize), (int)(0.25*cellSize),  (int)(0.0*cellSize)};
        int yCoor[] =
            {(int)(1.0*cellSize),  (int)(1.0*cellSize), (int)(0.5*cellSize),
             (int)(0.0*cellSize),  (int)(0.0*cellSize), (int)(0.5*cellSize)};
        drawPolygon(ctx, x, y, xCoor, yCoor, 6);
        
        //ctx.beginPath();
        //drawPolygon(ctx, x, y, 0.5, 6, 0, false);
        //ctx.fill();
        
        //Polygon hexagon = new Polygon(xCoor, yCoor, 6);
        //hexagon.translate(x, y);
        //g.fillPolygon(hexagon);
        }
        break;
      case GPSHAPE_OCTOGON:
        {
        int xCoor[] =
            {(int)(0.3*cellSize), (int)(0.7*cellSize), (int)(1.0*cellSize), (int)(1.0*cellSize),
             (int)(0.7*cellSize), (int)(0.3*cellSize), (int)(0.0*cellSize), (int)(0.0*cellSize)};
        int yCoor[] =
            {(int)(1.0*cellSize), (int)(1.0*cellSize), (int)(0.7*cellSize), (int)(0.3*cellSize),
             (int)(0.0*cellSize), (int)(0.0*cellSize), (int)(0.3*cellSize), (int)(0.7*cellSize)};
        drawPolygon(ctx, x, y, xCoor, yCoor, 8);
        
        //Polygon octogon = new Polygon(xCoor, yCoor, 8);
        //octogon.translate(x, y);
        //g.fillPolygon(octogon);
        }
        break;
      case GPSHAPE_STAR:
      {
        int xCoor[] =
            {(int)(0.5*cellSize), (int)(1.0*cellSize), (int)(0.8*cellSize), (int)(1.0*cellSize), (int)(0.7*cellSize),
             (int)(0.5*cellSize), (int)(0.3*cellSize), (int)(0.0*cellSize), (int)(0.2*cellSize), (int)(0.0*cellSize)};
        int yCoor[] =
            {(int)(0.8*cellSize), (int)(1.0*cellSize), (int)(0.7*cellSize), (int)(0.4*cellSize), (int)(0.4*cellSize),
             (int)(0.0*cellSize), (int)(0.4*cellSize), (int)(0.4*cellSize), (int)(0.7*cellSize), (int)(1.0*cellSize)};
        drawPolygon(ctx, x, y, xCoor, yCoor, 10);
        
        //Polygon star = new Polygon(xCoor, yCoor, 10);
        //star.translate(x, y);
        //g.fillPolygon(star);
        }
        break;
      case GPSHAPE_TURTLE:
      {
        int xCoor[] =
          {(int)(0.5*cellSize), (int)(1.0*cellSize),
           (int)(0.5*cellSize), (int)(0.0*cellSize)};
        int yCoor[] =
          {(int)(0.7*cellSize), (int)(1.0*cellSize),
           (int)(0.0*cellSize), (int)(1.0*cellSize)};
        drawPolygon(ctx, x, y, xCoor, yCoor, 4);
        
        //Polygon turtle = new Polygon(xCoor, yCoor, 4);
        //turtle.translate(x, y);
        //g.fillPolygon(turtle);
        }
        break;
      case GPSHAPE_NOSHAPE:
        break;
      default: // the agent's shape directly specifies the number of sides
      {
        ctx.beginPath();
        drawPolygon(ctx, x, y, cellSize*0.5, shape, 0, false);
        ctx.fill();
      }
        
      }
    }
  }
  
  /**
   * Draw a polygon.
   */
  protected void drawPolygon(Context2d ctx, int x, int y, int xCoor[], int yCoor[], int sides) {
    ctx.save();
    ctx.translate(x, y);
    ctx.beginPath();
    ctx.moveTo(xCoor[0], yCoor[0]);
    for (int i = 1; i < sides; i++) {
      ctx.lineTo(xCoor[i], yCoor[i]);
    }
    ctx.lineTo(xCoor[0], yCoor[0]);
    ctx.fill();
    ctx.closePath();
    ctx.restore();
  }
  
  /**
   * Draw a regular polygon.
   * Example:
<code>
context.beginPath();
polygon(context,350,125,100,6,-Math.PI/2);
context.fillStyle="rgba(51,128,255,0.75)";
context.fill();
context.stroke();
</code>
   * @param ctx A GWT Context2d object.
   * @param x X coordinate of the grid cell.
   * @param y Y coordinate of the grid cell.
   * @param radius 
   * @param sides The number of sides in the polygon. This value must be >= 3.
   * @param startAngle Angle in radians.
   * @param anticlockwise Draw clockwise (false) or anticlockwise (true).
   * @see http://www.storminthecastle.com/2013/07/24/how-you-can-draw-regular-polygons-with-the-html5-canvas-api/
   */
  protected void drawPolygon(Context2d ctx, int x, int y, double radius, int sides,
      double startAngle, boolean anticlockwise) {
    if (sides < 3) return;
    double a = (Math.PI * 2) / sides;
    a = anticlockwise ? -a : a;
    ctx.save();
    ctx.translate(x, y);
    ctx.rotate(startAngle);
    ctx.moveTo(radius, 0);
    for (int i = 1; i < sides; i++) {
      //double xcoor = radius * Math.cos(a * i);
      //double ycoor = radius * Math.sin(a * i);
      //System.out.println("i:" + i + " xcoor:" + xcoor + " ycoor:" + ycoor);
      //ctx.lineTo(xcoor, ycoor);
      ctx.lineTo(radius * Math.cos(a * i), radius * Math.sin(a * i));
    }
    ctx.closePath();
    ctx.restore();
  }
  
  /**
   * Draw an image on the canvas.
   * @param ctx - A GWT Context2d object.
   * @param src - URI designating the source of this image (ex: "myimage.png").
   * @param dx - the x coordinate of the upper-left corner of the destination rectangle
   * @param dy - the y coordinate of the upper-left corner of the destination rectangle
   */
  protected void drawImage(final Context2d ctx, final String src, final double dx, final double dy) {
    Image img = new Image(src);
    final ImageElement image = ImageElement.as(img.getElement());
    img.addLoadHandler(new LoadHandler() {
      @Override
      public void onLoad(LoadEvent event) {
        ctx.drawImage(image, dx, dy);
      }
    });
  }
  
  /**
   * Draw a scaled image on the canvas.
   * @param ctx - A GWT Context2d object.
   * @param src - URI designating the source of this image (ex: "myimage.png").
   * @param dx - the x coordinate of the upper-left corner of the destination rectangle
   * @param dy - the y coordinate of the upper-left corner of the destination rectangle
   * @param dw - the width of the destination rectangle
   * @param dh - the height of the destination rectangle
   */
  protected void drawImage(final Context2d ctx, final String src,
      final double dx, final double dy, final double dw, final double dh) {
    Image img = new Image(src);
    final ImageElement image = ImageElement.as(img.getElement());
    img.addLoadHandler(new LoadHandler() {
      @Override
      public void onLoad(LoadEvent event) {
        ctx.drawImage(image, dx, dy, dw, dh);
      }
    });
  }
  
  /**
   * Draw a scaled subset of an image on the canvas.
   * @param ctx - A GWT Context2d object.
   * @param src - URI designating the source of this image (ex: "myimage.png").
   * @param sx - the x coordinate of the upper-left corner of the source rectangle
   * @param sy - the y coordinate of the upper-left corner of the source rectangle
   * @param sw - the width of the source rectangle
   * @param sh - the width of the source rectangle
   * @param dx - the x coordinate of the upper-left corner of the destination rectangle
   * @param dy - the y coordinate of the upper-left corner of the destination rectangle
   * @param dw - the width of the destination rectangle
   * @param dh - the height of the destination rectangle
   */
  protected void drawImage(final Context2d ctx, final String src,
      final double sx, final double sy, final double sw, final double sh,
      final double dx, final double dy, final double dw, final double dh) {
    Image img = new Image(src);
    final ImageElement image = ImageElement.as(img.getElement());
    img.addLoadHandler(new LoadHandler() {
      @Override
      public void onLoad(LoadEvent event) {
        ctx.drawImage(image, sx, sy, sw, sh, dx, dy, dw, dh);
      }
    });
  }
  
  public String toDataUrl() {
    return canvas.toDataUrl();
  }
  
  public String toDataUrl(String type) {
    return canvas.toDataUrl(type);
  }
  
  public void setGridCellColor(CssColor gridCellColor) {
    // for use by a concrete subclass
  }
  
  public void setGridCellColor(int rgbColor) {
    // for use by a concrete subclass
  }
  
}

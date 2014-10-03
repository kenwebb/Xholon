package org.primordion.xholon.io;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.ImageResource;
//import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Grid;
//import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
//import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HTML;
//import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ResizeLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
//import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

// instantiate org.primordion.xholon.io.ScrolledTabLayoutPanel
import com.google.gwt.user.client.ui.TabLayoutPanel;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.MouseListenerCollection;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.Widget;

import org.client.RCImages;

/**
 * Add a new tab to the TabLayoutPanel.
 * If the panel does not already exist, create it and make it visible on the page.
 * TODO if there are too many tabs, the rightmost ones can't be seen
 *      instantiate ScrolledTabLayoutPanel instead of TabLayoutPanel:
 *      http://stackoverflow.com/questions/8839874/gwt-in-a-tablayoutpanel-how-do-i-make-tabs-wrap-if-there-are-too-many
 *      http://pastebin.com/8T4J2M0r
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on September 4, 2013)
 */
public class XholonGwtTabPanelHelper {
  
  /**
   * Initial height of the resize panel.
   */
  public static final String PANEL_HEIGHT_INITIAL = "170px";
  
  /**
   * This is used inside the inner DraggableMouseListener class.
   */
  static ResizeLayoutPanel resizePanel = null;
  
  /**
   * Add a tab to the TabLayoutPanel.
   * If the panel doesn't already exist, then create it first.
   * This is the component hierarchy that is generated:
   * <pre>
      RootPanel ("xhtabs")
        Grid
          ResizeLayoutPanel
            TabLayoutPanel (contains 1 or more tabs)
              HTML (tab header)
              ScrollPanel (tab contents)
                HTML
              ...
              HTML (tab header)
              ScrollPanel (tab contents)
                HTML
          ResizePushButton (extends PushButton)
   * </pre>
   * @param content The text or other content to place inside the tab's panel.
   * @param tabText The displayed header for the tab.
   * @param tooltip Text that can display as a tooltip on the tab's header.
   * @param isHtml Is the text HTML.
   *   true This is html-text that should go in the DOM.
   *   false This is plain-text that should just be displayed as is. It may in fact be HTML or XML.
   * @return index The index of the new tab, or -1 if unable to add the tab.
   */
  public static int addTab(Object content, String tabText, String tooltip, boolean isHtml) {
    int index = -1;
    RootPanel rp = RootPanel.get("xhtabs");
    TabLayoutPanel tabPanel = null;
    if (rp.getWidgetCount() == 0) {
      // make the TabLayoutPanel, and wrap it in a Grid with a resize button
      //tabPanel = new TabLayoutPanel(1.5, Unit.EM);
      tabPanel = new ScrolledTabLayoutPanel(1.5, Unit.EM, rcImages("scrollLeft"), rcImages("scrollRight"));
      resizePanel = new ResizeLayoutPanel();
      resizePanel.setHeight(PANEL_HEIGHT_INITIAL);
      resizePanel.setWidth((Window.getClientWidth() - 50) + "px");
      resizePanel.setWidget(tabPanel);
      Panel grid = makeGridPanel(resizePanel);
      rp.add(grid);
    }
    else {
      // get the existing TabLayoutPanel
      Grid grid = (Grid)rp.getWidget(0);
      resizePanel = (ResizeLayoutPanel)grid.getWidget(0, 0);
      tabPanel = (TabLayoutPanel)resizePanel.getWidget();
    }
    
    // construct tab header text, optionally with a tooltip
    String safeTabHeader = makeSafeTabHeader(tabText, tooltip);
    
    if (content instanceof String) {
      String text = (String)content;
      String htmlText = null;
      if (isHtml) {
        // text is HTML-text; this is used by out and clipboard, and should not be escaped
        // ex: <div id="xhout"><textarea rows=8 style="width: 100%; height: 100%; border: 1px;"></textarea></div>
        htmlText = text;
      }
      else {
        // maybe this is already a textarea ?
        String style = " style=\"width: 100%; height: 100%; border: 1px;\"";
	      String rows = " rows=8";
	      htmlText = "<textarea" + rows + style + ">" + SafeHtmlUtils.htmlEscape(text) + "</textarea>";
      }
      ScrollPanel scrollPanel = new ScrollPanel(new HTML(htmlText)); // this is safe
      tabPanel.add(scrollPanel, new HTML(safeTabHeader));  // this is safe
      index = tabPanel.getWidgetIndex(scrollPanel);
      tabPanel.selectTab(scrollPanel, false);
    }
    else {
      // assume this is a panel or widget, for example an IXholonConsole panel
      tabPanel.add((Widget)content, new HTML(safeTabHeader));  // this is safe
      index = tabPanel.getWidgetIndex((Widget)content);
      tabPanel.selectTab((Widget)content, false);
    }
    return index;
  }
  
  /**
   * Update the text displayed in a tab header.
   * @param tabText Text to display on the tab header.
   * @param tooltip Tooltip to display when focus is on the tab header.
   *   If null, then no tooltip will display.
   * @param index Index of the tab whose header is being updated.
   *   If -1, then the currently selected tab will be updated.
   */
  public static void updateTabHeader(String tabText, String tooltip, int index) {
    TabLayoutPanel tabPanel = (TabLayoutPanel)resizePanel.getWidget();
    
    // construct tab header text, optionally with a tooltip
    String safeTabHeader = makeSafeTabHeader(tabText, tooltip);
    
    if (index == -1) {
      index = tabPanel.getSelectedIndex();
    }
    HTML widget = (HTML)tabPanel.getTabWidget(index);
    widget.setHTML(safeTabHeader);  // this is safe
  }
  
  /**
   * Make a safe tab header, by ensuring that tabText and tooltip are safe HTML values,
   * that any HTML entities are escaped ( &><"' ).
   * ex: <div title="./ef/graphviz/ExtraCellularSpace_0_1387035686744.gv">./ef/graphviz/</div>
   * @param tabText Text to display on the tab header.
   * @param tooltip Tooltip to display when focus is on the tab header.
   *   If null, then no tooltip will display.
   */
  protected static String makeSafeTabHeader(String tabText, String tooltip) {
    StringBuilder hSb = new StringBuilder()
    .append("<div");
    if (tooltip != null) {
      String safeTooltip = SafeHtmlUtils.fromString(tooltip).asString();
      hSb
      .append(" title=\"")
      .append(safeTooltip)
      .append("\"");
    }
    String safeTabText = SafeHtmlUtils.fromString(tabText).asString();
    hSb
    .append(">")
    .append(safeTabText)
    .append("</div>");
    return hSb.toString();
  }
  
  /**
   * Get the widget at a specified tab index.
   * This is used to get a XholonConsole instance.
   * @param index Index of the requested tab.
   *   If -1, then the Widget in the currently selected tab will be returned.
   * @return an instance of Widget
   */
  public static Object getWidget(int index) {
    TabLayoutPanel tabPanel = (TabLayoutPanel)resizePanel.getWidget();
    if (index == -1) {
      index = tabPanel.getSelectedIndex();
    }
    Widget widget = tabPanel.getWidget(index);
    return widget;
  }
  
  /**
   * Remove all children of the root "xhconsole" element.
   * This is required at the end of load-time.
   */
  public static void clearConsoleContents() {
    RootPanel xhconsole = RootPanel.get("xhconsole");
    for (int i = 0; i < xhconsole.getWidgetCount(); i++) {
      xhconsole.getWidget(i).removeFromParent();
    }
  }
  
  public static void selectTab(int index) {
    TabLayoutPanel tabPanel = (TabLayoutPanel)resizePanel.getWidget();
    tabPanel.selectTab(index, false);
  }
  
  protected static ImageResource rcImages(String resourceName) {
	  return (ImageResource)RCImages.INSTANCE.getResource(resourceName);
	}
  
  /**
   * Resize the tabs panel.
   * @param width in pixels
   * @param height in pixels
   */
  protected static void resizeTabPanel(int width, int height) {
    resizePanel.setWidth(width + "px");
    resizePanel.setHeight(height + "px");
  }
  
  /**
	 * Wrap a resizable panel in a Grid, along with a resize button.
	 * @param childPanel The panel to be wrapped.
	 */
  protected static Panel makeGridPanel(Panel childPanel) {
		final Grid grid = new Grid(1, 2);
		grid.setBorderWidth(0);
		grid.setCellPadding(0);
		grid.setCellSpacing(0);
		grid.setWidget(0, 0, childPanel);
		grid.setWidget(0, 1, new ResizePushButton());
		grid.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		grid.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_BOTTOM);
		return grid;
	}

  // inner class that provides a resize button with events
  // see: http://ongwt.googlecode.com/svn/GS-socle/GWT-CC-Ext/trunk/src/main/java/gwtcc/core/client/widget/field/ListBox.java
  static class ResizePushButton extends PushButton implements SourcesMouseEvents {

    private final MouseListenerCollection listeners = new MouseListenerCollection();
    
    /**
     * constructor
     */
    public ResizePushButton() {
      super(" "); // upText
      init();
    }

    private void init() {
      Style style = this.getElement().getStyle();
      style.setWidth(10.0, Unit.PX);
      style.setHeight(10.0, Unit.PX);
      style.setBorderWidth(0.0, Unit.PX);
      style.setPadding(0.0, Unit.PX);
      DOM.sinkEvents(getElement(), DOM.getEventsSunk(getElement()) | Event.MOUSEEVENTS);
      this.addMouseListener(new DraggableMouseListener());
    }

    public void addMouseListener(final MouseListener listener) {
      listeners.add(listener);
    }

    public void removeMouseListener(final MouseListener listener) {
      listeners.remove(listener);
    }
    
    @Override
    public void onBrowserEvent(final Event event) {
      switch (DOM.eventGetType(event)) {
        case Event.ONMOUSEDOWN:
        case Event.ONMOUSEUP:
        case Event.ONMOUSEMOVE:
        case Event.ONMOUSEOVER:
        case Event.ONMOUSEOUT:
        listeners.fireMouseEvent(this, event);
        break;
      }
    }
    
    // MouseListenerAdapter is deprecated
    // use MouseDownHandler, MouseUpHandler, MouseOverHandler, MouseMoveHandler, and/or MouseOutHandler instead
    private class DraggableMouseListener extends MouseListenerAdapter {

      private int dragStartX;
      private int dragStartY;
      private boolean mouseDown = false;
      
      @Override
      public void onMouseDown(Widget sender, int x, int y) {
        DOM.setCapture(getElement());
        dragStartX = x;
        dragStartY = y;
        mouseDown = true;
      }
      
      @Override
      public void onMouseUp(Widget sender, int x, int y) {
        DOM.releaseCapture(getElement());
        mouseDown = false;
      }
      
      @Override
      public void onMouseMove(Widget sender, int x, int y) {
        if (mouseDown) {
          resize(x - dragStartX, y - dragStartY);
        }
      }
      
      @Override
      public void onMouseEnter(Widget sender) {
        DOM.setStyleAttribute(sender.getElement(), "cursor", "se-resize");
      }
      
      @Override
      public void onMouseLeave(Widget sender) {
        DOM.setStyleAttribute(sender.getElement(), "cursor", "default");
      }

      private void resize(int xMove, int yMove) {
        if ((xMove == 0) && (yMove == 0)) {return;}
        int width = resizePanel.getOffsetWidth() + xMove;
        int height = resizePanel.getOffsetHeight() + yMove;
        if ((width < 0) || (height < 0)) {return;}
        resizeTabPanel(width, height);
      }
    } // end class DraggableMouseListener

  } // end class ResizePushButton
  
}

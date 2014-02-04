package org.primordion.xholon.io;

//import com.google.gwt.core.client.JavaScriptObject;
//import com.google.gwt.dom.client.Document;
//import com.google.gwt.dom.client.Element;
//import com.google.gwt.json.client.JSONObject;
//import com.google.gwt.json.client.JSONParser;

//import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IXholon;

/**
 * This is a minimal GUI, mostly intended to expose the context-menu capabilities
 * of AbstractXholonGui, for the use of the canvas grid, SVG, and other Html5 features.
 * It's currently used by GridPanel and SvgViewBrowser, to pop-up a context menu.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on Nove 26, 2013)
 */
public class XholonGuiForHtml5 extends AbstractXholonGui {
  
  /**
   * Constructor.
   */
	public XholonGuiForHtml5() {}
	
	public Object getGuiRoot() {
	  return null;
	}
	
	public void showTree(IXholon node) {}
  
  @Override
  protected void setText(String text) {
    //xhRoot.println(text);
  }
  
	/**
	 * Refresh the entire tree being displayed in the viewer.
	 */
	public void refresh() {}
	
	/**
	 * Refresh the current node, including its entire sub-tree.
	 * @param guiItem 
	 * @param node The current IXholon node.
	 */
	public void refresh(Object guiItem, IXholon xhNode) {}
	
	public void makeContextMenu(Object guiItem, int posX, int posY) {
	  xhRoot = (IXholon)guiItem;
	  app = xhRoot.getApp();
	  super.makeContextMenu(guiItem, posX, posY);
	}
  
  protected IXholon getXholonNode(final Object guiItem) {
    IXholon node = (IXholon)guiItem;
    //if (node != null) {node.println("XholonGuiForHtml5 getXholonNode"); node.println(node.toString());}
    return node;
  }
  
  //protected native String getGuiItemName(final Object guiItem) /*-{
  //  return guiItem.name;
  //}-*/;
  protected String getGuiItemName(final Object guiItem) {
    //final TreeItem ti = (TreeItem)guiItem;
    //return (String)ti.getText();
    return ((IXholon)guiItem).getName();
  }
  
  protected Object getGuiItem(String nodeName) {
    // search recursively from root ???
    return null; //getGuiItemRecurse(nodeName, root);
  }
  
  protected boolean isInInheritanceHierarchy(Object guiItem) {
    return false;
  }
  
  protected boolean isInMechanismHierarchy(Object guiItem) {
    return false;
  }
	
}

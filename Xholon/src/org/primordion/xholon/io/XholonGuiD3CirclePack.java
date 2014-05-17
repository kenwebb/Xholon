package org.primordion.xholon.io;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IXholon;

/**
 * Xholon Gui based on D3 Circle Packing.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on Nove 26, 2013)
 */
public class XholonGuiD3CirclePack extends AbstractXholonGui {
  
  /**
   * The ID of the HTML <div> element that owns the <svg> node.
   */
  private String xhdivId = "xhgui";
  private String xhdiv = "#" + xhdivId;
  
  /**
   * Constructor.
   */
	public XholonGuiD3CirclePack() {}
	
	public XholonGuiD3CirclePack(String xhdivId, String modelName, IApplication app) {
	  this.setXhdivId(xhdivId);
	  this.modelName = modelName;
	  this.app = app;
	}
	
	public Object getGuiRoot() {
	  return null;
	}
	
	/**
	 * Show the entire IXholon tree as a Tree.
	 * @param node Node from which to start showing the tree.
	 */
	public void showTree(IXholon node) {
	  xhRoot = node;
	  Xholon2D3HierarchyJSON xholon2json = new Xholon2D3HierarchyJSON();
	  xholon2json.initialize(node);
	  xholon2json.setInsertDummyData(true);
	  xholon2json.writeAll();
	  String jsonStr = xholon2json.getJsonStr();
	  int numNodes = xholon2json.getNumNodes();
    int width = 100;
	  int height = 100;
	  if (numNodes > 1000) {
	    width = 1000;
	    height = 1000;
	  }
	  else if (numNodes > 100) {
	    width = numNodes;
	    height = numNodes;
	  }
	  JavaScriptObject json = ((JSONObject)JSONParser.parseLenient(jsonStr)).getJavaScriptObject();
	  Xholon2D3CirclePack xholon2cp = new Xholon2D3CirclePack();
	  xholon2cp.createD3(json, width, height, xhdiv, this);
	}
	
	@Override
  protected void setText(String text) {
    xhRoot.println(text);
  }
	
	/**
	 * Refresh the entire tree being displayed in the viewer.
	 */
	public void refresh() {
		Element div = Document.get().getElementById(xhdivId);
		if (div != null) {
		  setText("refreshing the tree ...");
		  Element svg = div.getFirstChildElement();
		  if (svg != null) {
		    svg.removeFromParent();
		    showTree(xhRoot);
		    setText("tree refreshed");
		  }
		  else {
		    setText("unable to refresh the tree, svg node does not exist");
		  }
		}
		else {
		  setText("unable to refresh the tree, can't find element id " + xhdivId);
		}
		
	}
	
	/**
	 * Refresh the current node, including its entire sub-tree.
	 * @param guiItem The current JSON (SVG __data__) item.
	 * @param node The current IXholon node.
	 */
	public void refresh(Object guiItem, IXholon xhNode) {
	  // for now, refresh the entire GUI (this causes a lot of needless global refreshing)
		//refresh();
	}
	
	protected native void removeChildren(Object guiItem) /*-{
	  //$wnd.console.log("removeChildren of:");
	  //$wnd.console.log(guiItem);
    //guiItem.children = null;
  }-*/;
  
  protected native void updateD3(Object guiItem, IXholon xhNode, Element svgRoot) /*-{
    //$wnd.console.log("updateD3:");
	  //$wnd.console.log(guiItem);
	  //$wnd.console.log(xhNode);
	  //$wnd.console.log(svgRoot);
    
  }-*/;
  
  protected native String getGuiItemName(final Object guiItem) /*-{
    return guiItem.name;
  }-*/;
  
  protected Object getGuiItem(String nodeName) {
    // search recursively from d3 circle pack root ???
    return null; //getGuiItemRecurse(nodeName, cpRoot);
  }
  
  protected boolean isInInheritanceHierarchy(Object guiItem) {
    return isInHierarchy(guiItem, "InheritanceHierarchy");
  }
  
  protected boolean isInMechanismHierarchy(Object guiItem) //TreeItem treeItem)
  {
    return isInHierarchy(guiItem, "MechanismHierarchy");
  }
  
  protected native boolean isInHierarchy(Object guiItem, String hierName) /*-{
    var d = guiItem;
    while (d != null) {
      if (d.name == hierName) {
        return true;
      }
      d = d.parent;
    }
    return false;
  }-*/;
	
	public String getXhdivId() {
	  return xhdivId;
	}
	
	public void setXhdivId(String xhdivId) {
	  this.xhdivId = xhdivId;
	  this.xhdiv = "#" + xhdivId;
	}
	
}

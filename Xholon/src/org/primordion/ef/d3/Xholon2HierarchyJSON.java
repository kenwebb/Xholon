package org.primordion.ef.d3;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.io.Xholon2D3HierarchyJSON;

import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Display the structure of a Xholon model in D3 Hierarchy JSON format.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on October 4, 2013)
 * @see <a href="http://d3js.org/">D3 website</a>
 */
public class Xholon2HierarchyJSON extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
  
  protected String outFileName;
	protected String outPath = "./ef/d3/";
	protected String modelName;
	protected IXholon root;
	
	/**
	 * This should be false by default.
	 * DO NOT include this in efParams.
	 */
	private boolean insertDummyData = false;
	
	private String jsonStr;
	
	public Xholon2HierarchyJSON() {}
	
	@Override
	public String getVal_String() {
	  return jsonStr;
	}
	
	/*
	 * @see org.primordion.ef.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
	 */
	public boolean initialize(String outFileName, String modelName, IXholon root) {
		if (outFileName == null) {
			this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + ".json";
		}
		else {
			this.outFileName = outFileName;
		}
		this.modelName = modelName;
		this.root = root;
		return true;
	}

	/*
	 * @see org.primordion.ef.IXholon2ExternalFormat#writeAll()
	 */
	public void writeAll() {
		// use the class in primordion.xholon.io to do much of the work
		// TODO the code in this method is partly redundant for some D3 layouts including CirclePack
		
		Xholon2D3HierarchyJSON xholon2json = new Xholon2D3HierarchyJSON();
	  xholon2json.initialize(root);
	  xholon2json.setShouldShowStateMachineEntities(isShouldShowStateMachineEntities());
	  xholon2json.setShouldIncludeDecorations(isShouldIncludeDecorations());
	  xholon2json.setSort(getSort());
	  xholon2json.setFilter(getFilter());
	  xholon2json.setInsertDummyData(isInsertDummyData());
	  xholon2json.setIncludeClass(isIncludeClass());
	  xholon2json.setUseSymbols(isUseSymbols());
	  xholon2json.setUseIcons(isUseIcons());
	  xholon2json.setUseAnno(isUseAnno());
	  xholon2json.writeAll();
	  jsonStr = xholon2json.getJsonStr();
	  int numNodes = xholon2json.getNumNodes();
		
		writeToTarget(jsonStr, outFileName, outPath, root);
		if (root.getApp().isUseGwt()) {
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
		  createD3(json, width, height, this.getSelection());
		}
	}
	
	/**
	 * Get whether or not to insert dummy data.
	 * @return true or false
	 */
	public boolean isInsertDummyData() {
	  return insertDummyData;
	}
	
	/**
	 * Set whether or not to insert dummy data.
	 * @param insertDummyData true or false
	 */
	public void setInsertDummyData(boolean insertDummyData) {
	  this.insertDummyData = insertDummyData;
	}
	
	/**
	 * Create a D3 construct from the JSON data.
	 * This method should be overridden by subclasses.
	 * typical values: width=600 height=600 selection:"#xhgraph"|"xhanim"
	 */
	protected void createD3(JavaScriptObject json, int width, int height, Object selection) {}
	
	/**
   * Make a JavaScript object with all the parameters for this external format.
   */
  protected native void makeEfParams() /*-{
    var p = {};
    p.shouldShowStateMachineEntities = false;
    p.shouldIncludeDecorations = true;
    p.sort = "default"; // "disable", "ascending", descending"
    p.filter = "--Behavior,Script";
    p.width = -1;
    p.height = -1;
    p.selection = "#xhgraph"; // "#xhgraph>div[1]", "#xhanim"
    p.mode = "new"; // "replace", "tween"
    p.labelContainers = false; // whether or not to label the container nodes
    p.labelContainersOptions = "top"; // "top" "bottom" "center"
    p.includeId = false;
    p.includeClass = false;
    p.shape = "circle"; // "ellipse", "rect"
    p.shapeParams = "5,5"; // rect rx,ry
    p.maxSvg = 50; // max allowable number of SVG subtrees, to prevent running out of memory
    p.useIcons = false;
    p.iconPos = "outside"; // "outside", "inside", "background", n s e w nw ne sw se   ex: "outside nw"
    p.useSymbols = false;
    p.useAnno = false; // whether or not to show Xholon annotations
    p.annoPos = "outside"; // "outside", "inside", "tooltip", n s e w nw ne sw se   ex: "outside nw"
    p.maxChars = 1;
    p.fontSizeMultiplier = 1.75;
    p.marble = ''; // '{"type":"default","maxChars":4}'
    p.supportTouch = false; // Hammer.js
    p._jsdata = false;
    this.efParams = p;
  }-*/;

  public native boolean isShouldShowStateMachineEntities() /*-{return this.efParams.shouldShowStateMachineEntities;}-*/;
  public native void setShouldShowStateMachineEntities(boolean shouldShowStateMachineEntities) /*-{this.efParams.shouldShowStateMachineEntities = shouldShowStateMachineEntities;}-*/;

  public native boolean isShouldIncludeDecorations() /*-{return this.efParams.shouldIncludeDecorations;}-*/;
  public native void setShouldIncludeDecorations(boolean shouldIncludeDecorations) /*-{this.efParams.shouldIncludeDecorations = shouldIncludeDecorations;}-*/;
  
  public native String getSort() /*-{return this.efParams.sort;}-*/;
  //public native void setSort(String sort) /*-{this.efParams.sort = sort;}-*/;
  
  public native String getFilter() /*-{return this.efParams.filter;}-*/;
  //public native void setFilter(String filter) /*-{this.efParams.filter = filter;}-*/;
  
  public native int getWidth() /*-{return this.efParams.width;}-*/;
  //public native void setWidth(String width) /*-{this.efParams.width = width;}-*/;
  
  public native int getHeight() /*-{return this.efParams.height;}-*/;
  //public native void setHeight(String height) /*-{this.efParams.height = height;}-*/;
  
  public native String getSelection() /*-{return this.efParams.selection;}-*/;
  //public native void setSelection(String selection) /*-{this.efParams.selection = selection;}-*/;
  
  public native String getMode() /*-{return this.efParams.mode;}-*/;
  //public native void setMode(String mode) /*-{this.efParams.mode = mode;}-*/;
  
  public native boolean isIncludeId() /*-{return this.efParams.includeId;}-*/;
  //public native void setIncludeId(boolean includeId) /*-{this.efParams.includeId = includeId;}-*/;
  
  public native boolean isIncludeClass() /*-{return this.efParams.includeClass;}-*/;
  //public native void setIncludeClass(boolean includeClass) /*-{this.efParams.includeClass = includeClass;}-*/;
  
  public native boolean isUseSymbols() /*-{return this.efParams.useSymbols;}-*/;
  //public native void setUseSymbols(boolean useSymbols) /*-{this.efParams.useSymbols = useSymbols;}-*/;
  
  public native boolean isUseIcons() /*-{return this.efParams.useIcons;}-*/;
  //public native void setUseIcons(boolean useIcons) /*-{this.efParams.useIcons = useIcons;}-*/;
  
  public native String getIconPos() /*-{return this.efParams.iconPos;}-*/;
  //public native void setIconPos(String iconPos) /*-{this.efParams.iconPos = iconPos;}-*/;
  
  public native boolean isUseAnno() /*-{return this.efParams.useAnno;}-*/;
  //public native void setUseAnno(boolean useAnno) /*-{this.efParams.useAnno = useAnno;}-*/;
  
  public native String getAnnoPos() /*-{return this.efParams.annoPos;}-*/;
  //public native void setAnnoPos(String annoPos) /*-{this.efParams.annoPos = annoPos;}-*/;
  
  public native boolean is_jsdata() /*-{return this.efParams._jsdata;}-*/;
  //public native void set_jsdata(boolean _jsdata) /*-{this.efParams._jsdata = _jsdata;}-*/;
    
}

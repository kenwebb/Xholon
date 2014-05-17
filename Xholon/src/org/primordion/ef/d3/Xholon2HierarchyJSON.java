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
	
	//private boolean shouldShowStateMachineEntities = false;
	//private boolean shouldIncludeDecorations = true;
	
	/**
	 * This should be false by default.
	 * DO NOT include this in efParams.
	 */
	private boolean insertDummyData = false;
	
	public Xholon2HierarchyJSON() {}
	
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
		// use the class in io to do much of the work
		Xholon2D3HierarchyJSON xholon2json = new Xholon2D3HierarchyJSON();
	  xholon2json.initialize(root);
	  xholon2json.setShouldShowStateMachineEntities(isShouldShowStateMachineEntities());
	  xholon2json.setShouldIncludeDecorations(isShouldIncludeDecorations());
	  xholon2json.setInsertDummyData(isInsertDummyData());
	  xholon2json.writeAll();
	  String jsonStr = xholon2json.getJsonStr();
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
		  createD3(json, width, height, "#xhgraph");
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
	 * typical values: width=600 height=600 selection:"#xhgraph"
	 */
	protected void createD3(JavaScriptObject json, int width, int height, Object selection) {}
	
	/**
   * Make a JavaScript object with all the parameters for this external format.
   */
  protected native void makeEfParams() /*-{
    var p = {};
    p.shouldShowStateMachineEntities = false;
    p.shouldIncludeDecorations = true;
    this.efParams = p;
  }-*/;

  public native boolean isShouldShowStateMachineEntities() /*-{return this.efParams.shouldShowStateMachineEntities;}-*/;
  public native void setShouldShowStateMachineEntities(boolean shouldShowStateMachineEntities) /*-{this.efParams.shouldShowStateMachineEntities = shouldShowStateMachineEntities;}-*/;

  public native boolean isShouldIncludeDecorations() /*-{return this.efParams.shouldIncludeDecorations;}-*/;
  public native void setShouldIncludeDecorations(boolean shouldIncludeDecorations) /*-{this.efParams.shouldIncludeDecorations = shouldIncludeDecorations;}-*/;
  
}

package org.primordion.ef.d3;

import com.google.gwt.core.client.JavaScriptObject;

import org.primordion.xholon.io.IXholonGui;
import org.primordion.xholon.io.XholonGuiD3CirclePack;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Display the structure of a Xholon model using D3 circle packing.
 * TODO after mouse hovers for about 0.5s, display toString() as the tooltip ?
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on October 4, 2013)
 * @see <a href="http://d3js.org/">D3 website</a>
 */
public class Xholon2CirclePack extends Xholon2HierarchyJSON implements IXholon2ExternalFormat {
	
	/**
	 * constructor
	 */
	public Xholon2CirclePack() {
	  this.setInsertDummyData(true);
	}
	
	/**
	 * Create a D3 Pack Layout from the JSON data.
	 * None of the input args are used.
	 */
	protected void createD3(JavaScriptObject json, int width, int height, Object selection) {
	  IXholonGui xholonGui = new XholonGuiD3CirclePack("xhgraph", this.modelName, this.root.getApp());
	  xholonGui.showTree(root);
	}
	
}

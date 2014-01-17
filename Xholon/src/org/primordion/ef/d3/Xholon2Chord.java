package org.primordion.ef.d3;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;

import org.primordion.xholon.base.IXholon;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Display the structure of a Xholon model as a D3 Chord Diagram.
 * TODO
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on October 7, 2013)
 * @see <a href="http://d3js.org/">D3 website</a>
 */
public class Xholon2Chord extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
	
	protected String outFileName;
	protected String outPath = "./ef/d3/";
	protected String modelName;
	protected IXholon root;
	private StringBuilder sb;
	
	private boolean shouldShowStateMachineEntities = false;
	
	public Xholon2Chord() {}
	
	public boolean initialize(String outFileName, String modelName, IXholon root) {
	  if (root == null) {return false;}
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
	
	public void writeAll() {
	  if (root == null) {return;}
		sb = new StringBuilder();
		
		writeNode(root, 0); // root is level 0
		String jsonStr = sb.toString();
		
		writeToTarget(jsonStr, outFileName, outPath, root);
		
		JavaScriptObject json = ((JSONObject)JSONParser.parseLenient(jsonStr)).getJavaScriptObject();
		int width = 500;
		int height = 500;
		createD3(json, width, height, "#xhgraph");
	}
	
	protected void writeNode(IXholon node, int level) {
	  // TODO
	  sb.append("{}");
	}
	
	protected native void createD3(JavaScriptObject json, int width, int height, Object selection) /*-{
	  // TODO
	  $wnd.alert("D3 Chord Diagram not yet implemented");
	}-*/;
	
}

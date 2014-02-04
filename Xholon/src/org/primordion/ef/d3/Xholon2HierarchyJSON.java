package org.primordion.ef.d3;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;

//import org.primordion.xholon.base.IDecoration;
import org.primordion.xholon.base.IXholon;
//import org.primordion.xholon.base.IXholonClass;
//import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
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
	//private StringBuilder sb;
	
	//private String indent = "                              ";
	private boolean shouldShowStateMachineEntities = false;
	private boolean shouldIncludeDecorations = true;
	
	//private int numNodes = 0;
	
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
		/*sb = new StringBuilder();
		// JSON doesn't allow comments
		numNodes = 0;
		writeNode(root, 0); // root is level 0
		String jsonStr = sb.toString() + "\n";*/
		
		// use the class in io to do much of the work
		Xholon2D3HierarchyJSON xholon2json = new Xholon2D3HierarchyJSON();
	  xholon2json.initialize(root);
	  xholon2json.setShouldShowStateMachineEntities(shouldShowStateMachineEntities);
	  xholon2json.setShouldIncludeDecorations(shouldIncludeDecorations);
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
	 * Write one node, and its child nodes.
	 * @param node The current node in the Xholon hierarchy.
	 * @param level Current level in the hierarchy.
	 */
	/*protected void writeNode(IXholon node, int level) {
		numNodes++;
		String tab = indent;
		if (level < indent.length()) {
		  tab = indent.substring(0, level);
		}
		sb
		.append(tab)
		.append("{\"name\": \"")
		.append(node.getName())
		.append("\", ");
		
		if (shouldIncludeDecorations) {
		  sb.append(getDecorationStr(node));
		}
		
		// only show nested state machine nodes if should show them, or if root is a StateMachineCE
		if ((node.getXhcId() == CeStateMachineEntity.StateMachineCE)
				&& (shouldShowStateMachineEntities == false)
				&& (level > 0)) {
			sb.append("\"size\": 1");
		}
		else if (node.hasChildNodes()) {
			IXholon childNode = node.getFirstChild();
			if (childNode.getNextSibling() == null) {
			  // this is a container with only one child; make it slightly larger so it won't be hidden
			  sb
			  .append("\"size\": 1.2, ")
			  // prevent it from accumulating a darker color
			  .append("\"opacity\": \"0\", ");
			}
			sb.append("\"children\": [\n");
			while (childNode != null) {
				writeNode(childNode, level+1);
				childNode = childNode.getNextSibling();
				if (childNode != null) {
					sb.append(",");
				}
				sb.append("\n");
			}
			sb
			.append(tab)
			.append("]");
		}
		else {
		  sb.append("\"size\": 1");
		}
		sb.append("}");
	}*/
	
	/**
	 * Get an optional decoration String, typically a color.
	 * @param node 
	 * @return a decoration String (ex: '"color": "#123456", '), or an empty String.
	 */
	/*protected String getDecorationStr(IXholon node) {
	  //node.println(node.getName());
	  StringBuilder sbd = new StringBuilder();
	  IXholonClass xhc = node.getXhc();
	  //node.println(xhc);
	  String color = null;
	  if (xhc == null) {
	    // this is a IH node, or the app node
	    //node.println("this is a IH node, or the app node");
	    if ("Application".equals(node.getName())) {
	      //node.println("this is the app node");
	    }
	    else {
	      //node.println("this is a IH node");
	      color = getColor((IDecoration)node);
	      //node.println("color is null");
		    if (color == null) {
		      //node.println(((IXholonClass)node).getMechanism());
		    	color = getColor((IDecoration)((IXholonClass)node).getMechanism());
		    	//node.println("color is " + color);
		    }
		  }
	  }
	  else {
	    // 0x2E8B57
	    if ("XholonMechanism".equals(xhc.getName())) {
	      // this is a Mechanism node
	      color = getColor((IDecoration)node);
	    }
	    else {
	      // this is a CSH node or a Control node
	      color = getColor((IDecoration)xhc);
		    if (color == null) {
		      color = getColor((IDecoration)xhc.getMechanism());
		    }
		  }
	  }
    //node.println(color);
    if (color != null) {
      sbd
      .append("\"color\": \"")
      .append(color)
      .append("\", ")
      .append("\"opacity\": \".10\", ");
    }
	  //node.println(sbd.toString());
	  return sbd.toString();
	}*/
	
	/**
	 * Get the color of a node.
	 * @param node
	 * @return A color String (ex: "#2E8B57" "red").
	 */
	/*protected String getColor(IDecoration node) {
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
	}*/
	
	/**
	 * Create a D3 construct from the JSON data.
	 * This method should be overridden by subclasses.
	 * typical values: width=600 height=600 selection:"#xhgraph"
	 */
	protected void createD3(JavaScriptObject json, int width, int height, Object selection) {}
	
}

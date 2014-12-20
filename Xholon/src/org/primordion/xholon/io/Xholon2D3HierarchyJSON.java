package org.primordion.xholon.io;

import org.primordion.xholon.base.IDecoration;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;

/**
 * Get the structure of a Xholon model in D3 Hierarchy JSON format.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on October 4, 2013)
 * @see <a href="http://d3js.org/">D3 website</a>
 */
public class Xholon2D3HierarchyJSON {
  
  protected IXholon root;
	private StringBuilder sb;
	
	private String indent = "                              ";
	private boolean shouldShowStateMachineEntities = false;
	private boolean shouldIncludeDecorations = true;
	private String sort = "default"; // "disable", "ascending", descending"
	private String filter = null;
	private boolean insertDummyData = false; // this should be false by default
	
	private int numNodes = 0;
	
	/** Opacity of a node that has a user defined color. */
	private double opacityUserDefinedColor = 0.1;
	
	/** Dummy nodes should be invisible. */
	private double opacityDummy = 0.0;
	
	private double sizeDefault = 1.0;
	
	/** Dummy nodes should be small. */
	private double sizeDummy = 0.2;
	
	/** This is an internal flag that is switched on and off within two different methods. */
	private boolean hasUserDefinedColor = false;
	
	/**
	 * Whether or not to check for and use symbols.
	 * Checking for symbols when none have been specified is an expensive operation.
	 * Also, sometimes symbols may have been specified, but they shouldn't be used.
	 */
	private boolean useSymbols = false;
	
	/** Whether to include "xhclass" in JSON output. */
	private boolean includeClass = false;
	
	public Xholon2D3HierarchyJSON() {}
	
	public boolean initialize(IXholon root) {
		this.root = root;
		return true;
	}

	public void writeAll() {
		sb = new StringBuilder();
		// JSON doesn't allow comments
		numNodes = 0;
		writeNode(root, 0); // root is level 0
		//root.consoleLog(sb.toString() + "\n");
	}
	
	public String getJsonStr() {
	  String jsonStr = sb.toString() + "\n";
	  return jsonStr;
	}
	
	public int getNumNodes() {
	  return numNodes;
	}
	
	/**
	 * Write one node, and its child nodes.
	 * @param node The current node in the Xholon hierarchy.
	 * @param level Current level in the hierarchy.
	 */
	protected void writeNode(IXholon node, int level) {
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
		
		if (includeClass) {
		  sb
		  .append("\"xhclass\": \"")
		  .append(node.getXhcName())
		  .append("\", ");
		}
		
		hasUserDefinedColor = false;
		if (isShouldIncludeDecorations()) {
		  sb.append(getDecorationStr(node));
		}
		
		// only show nested state machine nodes if should show them, or if root is a StateMachineCE
		if ((node.getXhcId() == CeStateMachineEntity.StateMachineCE)
				&& (isShouldShowStateMachineEntities() == false)
				&& (level > 0)) {
			sb.append("\"size\": ").append(sizeDefault);
		}
		else if (node.hasChildNodes()) {
			IXholon childNode = node.getFirstChild();
			StringBuilder dummy = new StringBuilder();
			if (isInsertDummyData() && childNode.getNextSibling() == null) {
			  // this is a container with only one child
			  if (!hasUserDefinedColor) {
			    sb
			    // prevent it from accumulating a darker color
			    .append("\"opacity\": ").append(opacityDummy).append(", ");
			  }
			  dummy.append(tab)
			   .append(" {\"name\": \"")
			   .append(node.getName());
			  if (includeClass) {
		      dummy
		      .append("\", ")
		      .append("\"xhclass\": \"")
		      .append(node.getXhcName());
		    }
			  dummy.append("\", \"size\": ").append(sizeDummy)
  	     .append(", \"opacity\": ").append(opacityDummy)
			   .append(", \"dummy\": ").append(1).append("},\n");
			}
			sb.append("\"children\": [\n")
			.append(dummy.toString());
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
		  sb.append("\"size\": ").append(sizeDefault);
		}
		sb.append("}");
	}
	
	/**
	 * Get an optional decoration String, typically a color.
	 * @param node 
	 * @return a decoration String (ex: '"color": "#123456", '), or an empty String.
	 */
	protected String getDecorationStr(IXholon node) {
	  StringBuilder sbd = new StringBuilder();
	  IXholonClass xhc = node.getXhc();
	  String color = null;
	  String symbol = null;
	  if (xhc == null) {
	    // this is a IH node, or the app node
	    if ("Application".equals(node.getName())) {
	      //node.println("this is the app node");
	    }
	    else {
	      color = getColor((IDecoration)node);
	      if (color == null) {
		      color = getColor((IDecoration)((IXholonClass)node).getMechanism());
		    }
		    if (useSymbols) {
		      symbol = getSymbol((IDecoration)node);
	        if (symbol == null) {
		        symbol = getSymbol((IDecoration)((IXholonClass)node).getMechanism());
		      }
		    }
		  }
	  }
	  else {
	    if ("XholonMechanism".equals(xhc.getName())) {
	      // this is a Mechanism node
	      color = getColor((IDecoration)node);
	      if (useSymbols) {
	        symbol = getSymbol((IDecoration)node);
	      }
	    }
	    else {
	      // this is a CSH node or a Control node
	      color = getColor((IDecoration)xhc);
		    if (color == null) {
		      color = getColor((IDecoration)xhc.getMechanism());
		    }
		    if (useSymbols) {
		      symbol = getSymbol((IDecoration)xhc);
	        if (symbol == null) {
		        symbol = getSymbol((IDecoration)xhc.getMechanism());
		      }
		    }
		  }
	  }
    if (color != null) {
      hasUserDefinedColor = true;
      sbd
      .append("\"color\": \"")
      .append(color)
      .append("\", ");
      if (isRGBA(color)) {
        // allow the rgba alpha value to have its full effect (result is rgba alpha * opacity)
        sbd.append("\"opacity\": ").append(1.0).append(", ");
      } else {
        sbd.append("\"opacity\": ").append(opacityUserDefinedColor).append(", ");
      }
    }
    if (symbol != null) {
      sbd
      .append("\"symbol\": \"")
      .append(symbol)
      .append("\", ");
    }
	  return sbd.toString();
	}
	
	/**
	 * Does this color specify an alpha channel?
	 * @param color without alpha: "#123456" "#123" "red" "rgb(1,2,3)"   with alpha: "rgba(1,2,3,0.5)"
	 *   Note that SVG does NOT support hex notation with an alpha channel (ex: "#12345678").
	 * @return true or false
	 */
	protected boolean isRGBA(String color) {
	  if (color.startsWith("rgba(")) {return true;}
	  return false;
	}
	
	/**
	 * Get the color of a node.
	 * @param node
	 * @return A color String (ex: "#2E8B57" "red").
	 */
	protected String getColor(IDecoration node) {
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
	}
	
	/**
	 * Get the optional symbol for a node.
	 * @param node
	 * @return A symbol String (ex: "&#33865;" "ß"), or null.
	 */
	protected String getSymbol(IDecoration node) {
	  String symbol = ((IDecoration)node).getSymbol();
		if (symbol == null) {
			if (node instanceof IXholonClass) {
				IXholon p = ((IXholonClass)node).getParentNode();
				if ((p != null) && (p instanceof IXholonClass)) {
					// try to get a color from the parent class
					return getSymbol((IDecoration)p);
				}
			}
		}
		return symbol;
	}
	
	// shouldShowStateMachineEntities
	public boolean isShouldShowStateMachineEntities() {
	  return shouldShowStateMachineEntities;
	}
	
	public void setShouldShowStateMachineEntities(boolean shouldShowStateMachineEntities) {
	  this.shouldShowStateMachineEntities = shouldShowStateMachineEntities;
	}
	
	// shouldIncludeDecorations
	public boolean isShouldIncludeDecorations() {
	  return shouldIncludeDecorations;
	}
	
	public void setShouldIncludeDecorations(boolean shouldIncludeDecorations) {
	  this.shouldIncludeDecorations = shouldIncludeDecorations;
	}
	
	// sort
	public String getSort() {return sort;}
	public void setSort(String sort) {this.sort = sort;}
	
	// filter
	public String getFilter() {return filter;}
	public void setFilter(String filter) {this.filter = filter;}
	
	// insertDummyData
	public boolean isInsertDummyData() {
	  return insertDummyData;
	}
	
	public void setInsertDummyData(boolean insertDummyData) {
	  this.insertDummyData = insertDummyData;
	}
	
	// useSymbols
	public boolean isUseSymbols() {return useSymbols;}
	public void setUseSymbols(boolean useSymbols) {this.useSymbols = useSymbols;}
	
	// includeClass
	public boolean isIncludeClass() {return includeClass;}
	public void setIncludeClass(boolean includeClass) {this.includeClass = includeClass;}
	
}

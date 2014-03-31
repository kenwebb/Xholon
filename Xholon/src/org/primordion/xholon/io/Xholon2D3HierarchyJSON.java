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
	private boolean insertDummyData = false; // this should be false by default
	
	private int numNodes = 0;
	
	public Xholon2D3HierarchyJSON() {}
	
	/*
	 * 
	 */
	public boolean initialize(IXholon root) {
		this.root = root;
		return true;
	}

	/*
	 * 
	 */
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
			String dummy = "";
			if (insertDummyData && childNode.getNextSibling() == null) {
			  // this is a container with only one child
			  sb
			  // prevent it from accumulating a darker color
			  .append("\"opacity\": \"0\", ");
			  dummy = tab
			   + " {\"name\": \""
			   + node.getName()
			   + "\", \"size\": 0.2, \"opacity\": \"0\", \"dummy\": 1},\n";
			}
			sb.append("\"children\": [\n")
			.append(dummy);
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
		  }
	  }
	  else {
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
    if (color != null) {
      sbd
      .append("\"color\": \"")
      .append(color)
      .append("\", ")
      .append("\"opacity\": \".10\", ");
    }
	  return sbd.toString();
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
	
	// shouldShowStateMachineEntities
	public boolean getShouldShowStateMachineEntities() {
	  return shouldShowStateMachineEntities;
	}
	
	public void setShouldShowStateMachineEntities(boolean shouldShowStateMachineEntities) {
	  this.shouldShowStateMachineEntities = shouldShowStateMachineEntities;
	}
	
	// shouldIncludeDecorations
	public boolean getShouldIncludeDecorations() {
	  return shouldIncludeDecorations;
	}
	
	public void setShouldIncludeDecorations(boolean shouldIncludeDecorations) {
	  this.shouldIncludeDecorations = shouldIncludeDecorations;
	}
	
	// insertDummyData
	public boolean isInsertDummyData() {
	  return insertDummyData;
	}
	
	public void setInsertDummyData(boolean insertDummyData) {
	  this.insertDummyData = insertDummyData;
	}
	
}

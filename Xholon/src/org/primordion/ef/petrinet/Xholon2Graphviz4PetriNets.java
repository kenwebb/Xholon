/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2012 Ken Webb
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA
 */

package org.primordion.ef.petrinet;

import java.util.List;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInformation;
import org.primordion.ef.Xholon2Graphviz;

/**
 * Write a Xholon Petri net model in Graphviz dot format.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 20, 2012)
 */
@SuppressWarnings("serial")
public class Xholon2Graphviz4PetriNets extends Xholon2Graphviz {

	/**
	 * Unicode black large circle.
	 */
	private static final char TOKEN_ONE = '\u2B24'; // 11044 decimal
	
	/**
	 * constructor
	 */
	public Xholon2Graphviz4PetriNets() {
		super();
	}
	
	/*
	 * @see org.primordion.ef.Xholon2Graphviz#writeNode(org.primordion.xholon.base.IXholon, int)
	 */
	protected void writeNode(IXholon node, int level) {
		if (!isShouldWritePlacesCluster() && node.getXhc().hasAncestor("Places")) {
			IXholon childNode = node.getFirstChild();
			while (childNode != null) {
				writeNode(childNode, level+1);
				childNode = childNode.getNextSibling();
			}
			return;
		}
		if (!isShouldWriteTransitionsCluster() && node.getXhc().hasAncestor("Transitions")) {
			IXholon childNode = node.getFirstChild();
			while (childNode != null) {
				writeNode(childNode, level+1);
				childNode = childNode.getNextSibling();
			}
			return;
		}
		if ("InputArcs".equals(node.getXhcName())) {return;}
		if ("OutputArcs".equals(node.getXhcName())) {return;}
		if ("QueueTransitions".equals(node.getXhcName())) {return;}
		if (!isShouldWriteAnalysisGridClusters()) {
			if (node.getXhc().hasAncestor("AnalysisPN")) {return;}
			if (node.getXhc().hasAncestor("GridOwner")) {return;}
		}
		super.writeNode(node, level);
	}
	
	/*
	 * @see org.primordion.ef.Xholon2Graphviz#writeLinks(org.primordion.xholon.base.IXholon, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	protected boolean writeLinks(IXholon node, String nodeId, String tab)
	{
		if (isShouldShowLinks() == false) {return false;}
		tab += " ";
		boolean rc = false;
		if (node.getXhc().hasAncestor("TransitionPN")) {
			// input arcs
			IXholon inputArcs = node.findFirstChildWithXhClass("InputArcs");
			if (inputArcs != null) {
				IXholon inputArc = inputArcs.getFirstChild();
				while (inputArc != null) {
					int nodeNum = 0;
					List<PortInformation> portList = inputArc.getAllPorts();
					for (int i = 0; i < portList.size(); i++) {
						if (writeLink(nodeNum, node, nodeId, portList.get(i),
								makeLinkLabel(inputArc), true, tab)) {
							rc = true;
						}
						nodeNum++;
					}
					inputArc = inputArc.getNextSibling();
				}
			}
			
			// output arcs
			IXholon outputArcs = node.findFirstChildWithXhClass("OutputArcs");
			if (outputArcs != null) {
				IXholon outputArc = outputArcs.getFirstChild();
				while (outputArc != null) {
					int nodeNum = 0;
					List<PortInformation> portList = outputArc.getAllPorts();
					for (int i = 0; i < portList.size(); i++) {
						if (writeLink(nodeNum, node, nodeId, portList.get(i),
								makeLinkLabel(outputArc), false, tab)) {
							rc = true;
						}
						nodeNum++;
					}
					outputArc = outputArc.getNextSibling();
				}
			}
			
		}
		else if ("PetriNet".equals(node.getXhcName())) {
		  // do not write links from PetriNet node
		  processEdgeMapValue(node, tab);
		}
		else {
			super.writeLinks(node, nodeId, tab);
		}
		return rc;
	}
	
	
	/**
	 * Make a link label.
	 * @param arc
	 * @return
	 */
	protected String makeLinkLabel(IXholon arc) {
		double val = arc.getVal(); // arc weight
		String linkLabel = "";
		if (val > 1.0) {
			linkLabel = " [label=" + (int)val + "]";
		}
		return linkLabel;
	}
	
	/*
	 * @see org.primordion.ef.Xholon2Graphviz#isCluster(org.primordion.xholon.base.IXholon)
	 */
	protected boolean isCluster(IXholon node) {
		if (node.getXhc().hasAncestor("TransitionPN")) {
			return false;
		}
		return super.isCluster(node);
	}
	
	/*
	 * @see org.primordion.ef.Xholon2Graphviz#makeColor(org.primordion.xholon.base.IXholon)
	 */
	protected String makeColor(IXholon node) {
		if (node.getXhc().hasAncestor("TransitionPN")) {
			return "fillcolor=\"" + getTransitionColor() + "\"";
		}
		else if (node.getXhc().hasAncestor("PlacePN")) {
			return "fillcolor=\"" + getPlaceColor() + "\"";
		}
		else {
			return super.makeColor(node);
		}
	}
	
	/*
	 * @see org.primordion.ef.Xholon2Graphviz#makeShape(org.primordion.xholon.base.IXholon)
	 */
	protected String makeShape(IXholon node) {
		if (node.getXhc().hasAncestor("TransitionPN")) {
			return "shape=" + getTransitionShape();
		}
		else if (node.getXhc().hasAncestor("PlacePN")) {
			return "shape=" + getPlaceShape();
		}
		else {
			return null;
		}
	}
	
	/*
	 * @see org.primordion.ef.Xholon2Graphviz#makeNodeLabel(org.primordion.xholon.base.IXholon)
	 */
	protected String makeNodeLabel(IXholon node) {
		String label = super.makeNodeLabel(node);
		if (isShouldWriteTokens() && node.getXhc().hasAncestor("PlacePN")) {
			double val = node.getVal();
			if (val == 1.0) {
				label += "\\n" + getTokenOne();
			}
			else if (val > 1.0) {
				label += "\\n" + (int)node.getVal();
			}
			else if (val > 0.0) {
				label += "\\n" + node.getVal();
			}
		}
		return label;
	}
	
	/**
   * Make a JavaScript object with all the parameters for this external format.
   */
	protected void makeEfParams() {
	  super.makeEfParams();
	  this.makeEfParams4PetriNet(TOKEN_ONE);
	}
	
	/**
   * Add new attributes
   * DO NOT delete superclass attributes
   * no need to change values of superclass attributes.
   */
  protected native void makeEfParams4PetriNet(char tokenOne) /*-{
    this.efParams.placeColor = "#ffff00";
    this.efParams.transitionColor = "#00ffff";
    this.efParams.placeShape = "ellipse";
    this.efParams.transitionShape = "box";
    this.efParams.shouldWritePlacesCluster = false;
    this.efParams.shouldWriteTransitionsCluster = false;
    this.efParams.shouldWriteAnalysisGridClusters = false;
    this.efParams.shouldWriteTokens = true;
    this.efParams.tokenOne = tokenOne;
  }-*/;


  public native String getPlaceColor() /*-{return this.efParams.placeColor;}-*/;
  //public native void setPlaceColor(String placeColor) /*-{this.efParams.placeColor = placeColor;}-*/;

  public native String getTransitionColor() /*-{return this.efParams.transitionColor;}-*/;
  //public native void setTransitionColor(String transitionColor) /*-{this.efParams.transitionColor = transitionColor;}-*/;

  public native String getPlaceShape() /*-{return this.efParams.placeShape;}-*/;
  //public native void setPlaceShape(String placeShape) /*-{this.efParams.placeShape = placeShape;}-*/;

  public native String getTransitionShape() /*-{return this.efParams.transitionShape;}-*/;
  //public native void setTransitionShape(String transitionShape) /*-{this.efParams.transitionShape = transitionShape;}-*/;

  public native boolean isShouldWritePlacesCluster() /*-{return this.efParams.shouldWritePlacesCluster;}-*/;
  //public native void setShouldWritePlacesCluster(boolean shouldWritePlacesCluster) /*-{this.efParams.shouldWritePlacesCluster = shouldWritePlacesCluster;}-*/;

  public native boolean isShouldWriteTransitionsCluster() /*-{return this.efParams.shouldWriteTransitionsCluster;}-*/;
  //public native void setShouldWriteTransitionsCluster(boolean shouldWriteTransitionsCluster) /*-{this.efParams.shouldWriteTransitionsCluster = shouldWriteTransitionsCluster;}-*/;

  public native boolean isShouldWriteAnalysisGridClusters() /*-{return this.efParams.shouldWriteAnalysisGridClusters;}-*/;
  //public native void setShouldWriteAnalysisGridClusters(boolean shouldWriteAnalysisGridClusters) /*-{this.efParams.shouldWriteAnalysisGridClusters = shouldWriteAnalysisGridClusters;}-*/;

  public native boolean isShouldWriteTokens() /*-{return this.efParams.shouldWriteTokens;}-*/;
  //public native void setShouldWriteTokens(boolean shouldWriteTokens) /*-{this.efParams.shouldWriteTokens = shouldWriteTokens;}-*/;
  
  public native char getTokenOne() /*-{return this.efParams.tokenOne;}-*/;
	
}

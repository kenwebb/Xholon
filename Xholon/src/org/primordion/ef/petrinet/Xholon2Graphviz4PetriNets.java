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
	private static final char TOKEN_ONE = '\u2B24';
	
	protected String placeColor = "#ffff00";
	protected String transitionColor = "#00ffff";
	
	protected String placeShape = "ellipse";
	protected String transitionShape = "box";
	
	protected boolean shouldWritePlacesCluster = false;
	protected boolean shouldWriteTransitionsCluster = false;
	protected boolean shouldWriteAnalysisGridClusters = false;
	
	protected boolean shouldWriteTokens = true;
	
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
		if (!shouldWritePlacesCluster && node.getXhc().hasAncestor("Places")) {
			IXholon childNode = node.getFirstChild();
			while (childNode != null) {
				writeNode(childNode, level+1);
				childNode = childNode.getNextSibling();
			}
			return;
		}
		if (!shouldWriteTransitionsCluster && node.getXhc().hasAncestor("Transitions")) {
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
		if (!shouldWriteAnalysisGridClusters) {
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
			return "fillcolor=\"" + transitionColor + "\"";
		}
		else if (node.getXhc().hasAncestor("PlacePN")) {
			return "fillcolor=\"" + placeColor + "\"";
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
			return "shape=" + transitionShape;
		}
		else if (node.getXhc().hasAncestor("PlacePN")) {
			return "shape=" + placeShape;
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
		if (shouldWriteTokens && node.getXhc().hasAncestor("PlacePN")) {
			double val = node.getVal();
			if (val == 1.0) {
				label += "\\n" + TOKEN_ONE;
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

	public String getPlaceColor() {
		return placeColor;
	}

	public void setPlaceColor(String placeColor) {
		this.placeColor = placeColor;
	}

	public String getTransitionColor() {
		return transitionColor;
	}

	public void setTransitionColor(String transitionColor) {
		this.transitionColor = transitionColor;
	}

	public String getPlaceShape() {
		return placeShape;
	}

	public void setPlaceShape(String placeShape) {
		this.placeShape = placeShape;
	}

	public String getTransitionShape() {
		return transitionShape;
	}

	public void setTransitionShape(String transitionShape) {
		this.transitionShape = transitionShape;
	}

	public boolean isShouldWritePlacesCluster() {
		return shouldWritePlacesCluster;
	}

	public void setShouldWritePlacesCluster(boolean shouldWritePlacesCluster) {
		this.shouldWritePlacesCluster = shouldWritePlacesCluster;
	}

	public boolean isShouldWriteTransitionsCluster() {
		return shouldWriteTransitionsCluster;
	}

	public void setShouldWriteTransitionsCluster(
			boolean shouldWriteTransitionsCluster) {
		this.shouldWriteTransitionsCluster = shouldWriteTransitionsCluster;
	}

	public boolean isShouldWriteAnalysisGridClusters() {
		return shouldWriteAnalysisGridClusters;
	}

	public void setShouldWriteAnalysisGridClusters(
			boolean shouldWriteAnalysisGridClusters) {
		this.shouldWriteAnalysisGridClusters = shouldWriteAnalysisGridClusters;
	}

	public boolean isShouldWriteTokens() {
		return shouldWriteTokens;
	}

	public void setShouldWriteTokens(boolean shouldWriteTokens) {
		this.shouldWriteTokens = shouldWriteTokens;
	}
	
}

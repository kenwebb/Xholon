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

package org.primordion.xholon.mech.petrinet.grid;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.mech.petrinet.InputArc;
import org.primordion.xholon.mech.petrinet.OutputArc;
import org.primordion.xholon.mech.petrinet.Transition;
import org.primordion.xholon.service.xholonhelper.IFindChildSibWith;

/**
 * A transition may fire each time step, depending on what places it's co-located with.
 * This class is intended for use in a Grid.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on July 30, 2012)
 */
public class FiringTransitionBehavior extends PneBehavior {
	private static final long serialVersionUID = -6626008679068611618L;
	
	/**
	 * Static reference to the XholonHelperService.
	 */
	private static IFindChildSibWith xhs = null;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#act()
	 */
	public void act() {
		fire();
		super.act();
	}
	
	/**
	 * Try to fire the Transition.
	 */
	protected void fire() {
		Transition tNode = (Transition)getPne();
		if (tNode == null) {return;}
		if (tNode.getInputArcs() == null) {return;}
		if (tNode.getInputArcs().hasChildNodes() && tNode.hasSiblingNodes()) {
			if (xhs == null) {
				xhs = (IFindChildSibWith)this.getService("XholonHelperService");
			}
			IXholon gridCell = tNode.getParentNode();
			GridOwner gridOwner = (GridOwner)getGridOwner();
			if (tNode.getFirstSibling() != null) {
				// make tNode the firstSibling of its sibling chain
				tNode.removeChild();
				tNode.insertFirstChild(gridCell);
			}
			if (isTransitionEnabled(tNode, gridCell)) {
				print("firing: " + tNode.getXhcName());
				removeInputPlaces(tNode);
				addOutputPlaces(tNode, gridCell, gridOwner);
			}
			else {
				abort(tNode);
			}
		}
	}
	
	/**
	 * Determine if this Transition is enabled.
	 * Structure the Transition's sibling chain to simplify subsequent processing.
	 * Place nodes in the grid are marked for removal by positioning them before the Transition node.
	 * The parentNode of these marked Place nodes are temporarily changed to point to the Place node in the PN.
	 * @param tNode This behavior's Transition node.
	 * @param gridCell Current location of the Transition node.
	 * @return true or false
	 */
	protected boolean isTransitionEnabled(Transition tNode, IXholon gridCell) {
		IXholon inputArc = tNode.getInputArcs().getFirstChild();
		while (inputArc != null) {
			IXholon inputPlace = ((InputArc)inputArc).getPlace();
			int xhcId = inputPlace.getXhcId();
			int weightCount = (int)inputArc.getVal(); // get arc weight
			if (weightCount > 0) {
				IXholon inputPlaceInGrid = xhs.findNextSiblingWithAncestorXhClass(tNode, xhcId);
				while ((inputPlaceInGrid != null) && (weightCount > 0)) {
					weightCount--;
					IXholon nextInputPlaceInGrid = null;
					if (weightCount > 0) {
						nextInputPlaceInGrid = xhs.findNextSiblingWithAncestorXhClass(inputPlaceInGrid, xhcId);
					}
					// mark the Place node for removal by positioning it before the Transition node
					inputPlaceInGrid.removeChild();
					inputPlaceInGrid.setNextSibling(gridCell.getFirstChild());
					gridCell.setFirstChild(inputPlaceInGrid);
					inputPlaceInGrid.setParentNode(inputPlace); // temporary parent
					inputPlaceInGrid = nextInputPlaceInGrid;
				}
			}
			if (weightCount > 0) {
				return false;
			}
			inputArc = inputArc.getNextSibling();
		}
		return true;
	}
	
	/**
	 * Abort the firing.
	 * Restore correct parentNode to all previous siblings of the Transition node.
	 * @param tNode This behavior's Transition node.
	 */
	protected void abort(Transition tNode) {
		// 
		IXholon node = tNode.getFirstSibling();
		while ((node != null) && (node != tNode)) {
			node.setParentNode(tNode.getParentNode()); // restore correct parent
			node = node.getNextSibling();
		}
	}
	
	/**
	 * Remove input Place nodes.
	 * Remove all previous siblings, which are all Place nodes marked for removal.
	 * @param tNode This behavior's Transition node.
	 */
	protected void removeInputPlaces(Transition tNode) {
		IXholon node = tNode.getFirstSibling();
		while ((node != null) && (node != tNode)) {
			print(" " + node.getName());
			IXholon nextSib = node.getNextSibling();
			node.getParentNode().decVal(1.0); // dec token count of place in the Petri net
			node.setParentNode(tNode.getParentNode()); // restore correct parent
			node.removeChild(); // will be garbage-collected later by Q
			node = nextSib;
		}
	}
	
	/**
	 * Add output Place nodes.
	 * @param tNode This behavior's Transition node.
	 * @param gridCell Current location of the Transition node.
	 * @param gridOwner Owner of the grid that the gridCell is in.
	 */
	protected void addOutputPlaces(Transition tNode, IXholon gridCell, GridOwner gridOwner) {
		IXholon outputArc = tNode.getOutputArcs().getFirstChild();
		while (outputArc != null) {
			IXholon outputPlace = ((OutputArc)outputArc).getPlace();
			for (int i = 0; i < outputArc.getVal(); i++) {
				println(" -> " + outputPlace.getXhcName());
				gridOwner.createPlaceInGrid(outputPlace, gridCell);
				outputPlace.incVal(1.0); // inc token count of place in the Petri net
			}
			outputArc = outputArc.getNextSibling();
		}
	}
	
}

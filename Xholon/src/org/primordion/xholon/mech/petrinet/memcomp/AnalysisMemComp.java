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

package org.primordion.xholon.mech.petrinet.memcomp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.mech.petrinet.PetriNet;

/**
 * Analysis and Reporting for Membrane Computing.
 * Analysis nodes are optional.
 * If used, an Analysis node must be a child of the PetriNet (PN) node,
 * typically located just after the QueueTransitions node.
 * An Analysis node might contain other nodes that provide additional analysis functionality.
 * These child nodes could for example by domain-specific or app-specific script nodes.
 * 
 * <p>Correspondences between PN and Membrane Computing terminology:</p>
<pre>
Petri net     Membrane Computing
------------- ------------------
              membrane
place         object
transition    rule
marking       
weight        
rate          
</pre>
 * <p>To add an Analysis node to a running Petri net app,
 * paste the following as last child of PetriNet node:</p>
<pre>&lt;AnalysisMemComp/></pre>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on August 17, 2012)
 */
public class AnalysisMemComp extends XholonWithPorts {
	private static final long serialVersionUID = -888071705129715503L;
	
	private static final String showAll = "Show all";
	private static final String showConcentrations = "Show concentrations (as double)";
	private static final String showConcentrationsInt = "Show concentrations (as int)";
	private static final String showMembranes = "Show membranes";
	private static final String showObjects = "Show objects";
	private static final String showRules = "Show rules";
	/** action list */
	private String[] actions = {showAll,showConcentrations,showConcentrationsInt,showMembranes,showObjects,showRules};
	
	/**
	 * The cutoff in number of tokens to decide if a place exists.
	 * If there are no instances of a place, then that place does not appear in the list returned.
	 * Normally, with a Xholon app, we want to see a place even if it's current token count is 0.0.
	 * So by default, this variable has a value of -1.0,
	 * which means use the place if it has a concentration greater than -1.0 .
	 * To exclude places that have a token count of 0.0, set this variable to 0.0 .
	 */
	private double tokensCutoff = -1.0;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getActionList()
	 */
	public String[] getActionList() {
		return actions;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setActionList(java.lang.String[])
	 */
	public void setActionList(String[] actionList)
	{
		actions = actionList;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#doAction(java.lang.String)
	 */
	public void doAction(String action) {
		if (showAll.equals(action)) {
			//println("concentrations " + getConcentrations(double.class));
			//println("concentrations " + getConcentrations(int.class));
			println("membranes " + getMembranes());
			println("objects " + getObjects());
			println("rules " + getRules());
		}
		else if (showConcentrations.equals(action)) {
			println("concentrations " + getConcentrations(double.class));
		}
		else if (showConcentrationsInt.equals(action)) {
			println("concentrations " + getConcentrations(int.class));
		}
		else if (showMembranes.equals(action)) {
			println("membranes " + getMembranes());
		}
		else if (showObjects.equals(action)) {
			println("objects " + getObjects());
		}
		else if (showRules.equals(action)) {
			println("rules " + getRules());
		}
	}
	
	/**
	 * Get list/vector of current concentrations of species in the CRN
	 *  = list/vector of current markings of places in the PN.
	 * <p>"(instantaneous) values of the molar concentrations of the species"
	 * "we abbreviate this list of numbers by the 'composition vector' c(t)"
	 * example of a vector: c_A(t),c_B(t),c_C(t),c_D(t),c_E(t)
	 * (Feinberg 1-2)</p>
	 * <p>This returns values only for "the set of species which are actually present in the reactor" (Feinberg 2-4).
	 * If there are no instances of a species, then that species does not appear in the list returned.</p>
	 * @param clazz The desired class of the items in the returned list.
	 * @return A list of double or int, for example: [140.0, 180.0, 200.0, 25.0, 80.0] or [1, 2, 3]
	 */
	public List<Object> getConcentrations(Class<?> clazz) {
		Iterator<IXholon> speciesIt = getObjectsRaw().iterator();
		List<Object> concentrations = new ArrayList<Object>();
		while (speciesIt.hasNext()) {
			IXholon species = speciesIt.next();
			double concentration = species.getVal();
			if (concentration > tokensCutoff) {
				if (clazz == int.class) {
					concentrations.add((int)concentration);
				}
				else { // double.class
					concentrations.add(concentration);
				}
			}
		}
		return concentrations;
	}
	
	/**
	 * Get set of membranes.
	 * A membrane is a node that is:
	 * - a direct parent of a transition or place node
	 * - not a Places or Transitions node
	 * @return
	 */
	public Set<String> getMembranes() {
		Set<String> set = new HashSet<String>();
		Iterator<IXholon> rulesIt = getRulesRaw().iterator();
		while (rulesIt.hasNext()) {
			IXholon node = rulesIt.next().getParentNode();
			if (!node.getXhc().hasAncestor("Transitions")) {
				set.add(makeNodeName(node, "MembranePN"));
			}
		}
		Iterator<IXholon> objectsIt = getObjectsRaw().iterator();
		while (objectsIt.hasNext()) {
			IXholon node = objectsIt.next().getParentNode();
			if (!node.getXhc().hasAncestor("Places")) {
				set.add(makeNodeName(node, "MembranePN"));
			}
		}
		return set;
	}
	
	/**
	 * Get list of species in the CRN = list of places in the PN.
	 * This returns only "the set of species which are actually present in the reactor" (Feinberg 2-4).
	 * If there are no instances of a species, then that species does not appear in the list returned.
	 * @return A list, for example: [A, B, C, D, E]
	 */
	public List<String> getObjects() {
		Iterator<IXholon> placesIt = getObjectsRaw().iterator();
		List<String> species = new ArrayList<String>();
		while (placesIt.hasNext()) {
			IXholon aSpecies = placesIt.next();
			if (aSpecies.getVal() > tokensCutoff) {
				species.add(makeNodeName(aSpecies, "PlacePN"));
			}
		}
		return species;
	}
	
	/**
	 * Get raw list of species in the CRN = list of places in the PN.
	 * @return
	 */
	protected List<IXholon> getObjectsRaw() {
		return ((PetriNet)this.getParentNode()).getPlaces();
	}
	
	/**
	 * Get list of reactions in the CRN = list of transitions in the PN.
	 * @return A list, for example: [A_BB, BB_A, AC_D, D_AC, D_BE, BE_AC]
	 */
	public List<String> getRules() {
		Iterator<IXholon> reactionsIt = getRulesRaw().iterator();
		List<String> reactions = new ArrayList<String>();
		while (reactionsIt.hasNext()) {
			IXholon reaction = reactionsIt.next();
			reactions.add(makeNodeName(reaction, "TransitionPN"));
		}
		return reactions;
	}
	
	/**
	 * Get list of reactions in the CRN = list of transitions in the PN.
	 * @return
	 */
	protected List<IXholon> getRulesRaw() {
		return ((PetriNet)this.getParentNode()).getTransitions();
	}
	
	/**
	 * Construct a node's name.
	 * @param node The node.
	 * @param baseXhcName The base XholonClass name for this type of node (PlacePN, TransitionPN).
	 * @return The node's xhcName or roleName.
	 */
	protected String makeNodeName(IXholon node, String baseXhcName) {
		if (!baseXhcName.equals(node.getXhcName())) {
			return node.getXhcName();
		}
		else if (node.getRoleName() == null) {
			return baseXhcName;
		}
		return node.getRoleName();
	}
	
}

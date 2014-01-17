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

package org.primordion.xholon.mech.petrinet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.primordion.xholon.base.IMechanism;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.mech.petrinet.Arc;
import org.primordion.xholon.mech.petrinet.PetriNet;

/**
 * Analysis and Reporting for Petri nets.
 * If used, an Analysis node must be a child of the PetriNet (PN) node,
 * typically located just after the QueueTransitions node.
 * An Analysis node might contain other nodes that provide additional analysis functionality.
 * These child nodes could for example by domain-specific or app-specific script nodes.
 * </p>
 * <p>To add an Analysis node to a running Petri net app,
 * paste the following as last child of PetriNet node:</p>
<pre>&lt;AnalysisPetriNet/></pre>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on August 15, 2012)
 */
public class AnalysisPetriNet extends XholonWithPorts {
	private static final long serialVersionUID = 2158160019420564922L;
	
	private static final String showAll = "Show all";
	private static final String showPlaces = "Show places";
	private static final String showTransitions = "Show transitions";
	private static final String showPythonScript = "Show python script";
	/** action list */
	private String[] actions = {showAll,showPlaces,showTransitions,showPythonScript};
	
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
			println("places " + getPlaces());
			println("transitions " + getTransitions());
		}
		else if (showPlaces.equals(action)) {
			println("places " + getPlaces());
		}
		else if (showTransitions.equals(action)) {
			println("transitions " + getTransitions());
		}
		else if (showPythonScript.equals(action)) {
			println(getPythonScript());
		}
	}
	
	/**
	 * Get list of places in the PN.
	 * @return A list, for example: [A, B, C, D, E]
	 */
	public List<String> getPlaces() {
		Iterator<IXholon> placesIt = getPlacesRaw().iterator();
		List<String> places = new ArrayList<String>();
		while (placesIt.hasNext()) {
			IXholon aPlace = placesIt.next();
			if (aPlace.getVal() > tokensCutoff) {
				places.add(makeNodeName(aPlace, "PlacePN"));
			}
		}
		return places;
	}
	
	/**
	 * Get raw list of places in the PN.
	 * @return
	 */
	protected List<IXholon> getPlacesRaw() {
		return ((PetriNet)this.getParentNode()).getPlaces();
	}
	
	/**
	 * Get list of transitions in the PN.
	 * @return A list, for example: [
	 * A_BB:A → B + B,
	 * BB_A:B + B → A,
	 * AC_D:A + C → D,
	 * D_AC,
	 * D_BE,
	 * BE_AC,
	 * F_:F → 0,
	 * _F:0 → F
	 * ]
	 */
	public List<String> getTransitions() {
		Iterator<IXholon> transitionsIt = getTransitionsRaw().iterator();
		List<String> transitions = new ArrayList<String>();
		while (transitionsIt.hasNext()) {
			IXholon transition = transitionsIt.next();
			transitions.add(makeNodeName(transition, "TransitionPN") + makeTransitionStr(transition));
		}
		return transitions;
	}
	
	/**
	 * Get raw list of transitions in the PN.
	 * @return
	 */
	protected List<IXholon> getTransitionsRaw() {
		return ((PetriNet)this.getParentNode()).getTransitions();
	}
	
	/**
	 * Get Petri net in python format.
	 * The Petri net runs with a simple python script. Example:
<pre>
combine = ("combine", [["H",2],["O",1]], [["H2O",1]])
split = ("split", [["H2O",1]], [["H",2],["O",1]])
petriNet = PetriNet(
    ["H","O","H2O"], # states
    [combine,split]  # transitions
)
initialLabelling = {"H":5, "O":3, "H2O":4}
steps = 20
petriNet.RunSimulation(steps, initialLabelling)
</pre>
	 * @see <a href="http://www.azimuthproject.org/azimuth/show/Blog+-+Petri+net+programming+%28part+1%29">David Tanzer blog article at Azimuth</a>
	 * @return A formatted string.
	 */
	protected String getPythonScript() {
		StringBuilder sb = new StringBuilder();
		StringBuilder sbTrans = new StringBuilder();
		sb.append("petriNet = PetriNet(\n");
		
		// states
		sb.append("    [");
		List<IXholon> states = getPlacesRaw();
		Iterator<IXholon> statesIt = states.iterator();
		while(statesIt.hasNext()) {
			IXholon state = statesIt.next();
			sb.append("\"")
			.append(makeNamePrefix(state))
			.append(makeNodeName(state, "PlacePN"))
			.append("\"");
			if (statesIt.hasNext()) {
				sb.append(", ");
			}
		}
		sb.append("], # states\n");
		
		// transitions
		sb.append("    [");
		List<IXholon> transitions = getTransitionsRaw();
		Iterator<IXholon> transitionsIt = transitions.iterator();
		while(transitionsIt.hasNext()) {
			Transition transition = (Transition)transitionsIt.next();
			String transitionStr = makeNodeName(transition, "TransitionPN");
			sb.append(transitionStr);
			if (transitionsIt.hasNext()) {
				sb.append(", ");
			}
			// transition details
			sbTrans.append(transitionStr)
			.append(" = (\"")
			.append(transitionStr)
			.append("\", ")
			.append(getPythonScriptArcs(transition.getInputArcs()))
			.append(", ")
			.append(getPythonScriptArcs(transition.getOutputArcs()))
			.append(")\n");
		}
		sb.append("] # transitions\n")
		.append(")\n");
		
		// initialLabelling
		sb.append("initialLabelling = {");
		statesIt = states.iterator();
		while(statesIt.hasNext()) {
			IXholon state = statesIt.next();
			sb.append("\"")
			.append(makeNamePrefix(state))
			.append(makeNodeName(state, "PlacePN"))
			.append("\"")
			.append(":")
			.append(state.getVal());
			if (statesIt.hasNext()) {
				sb.append(", ");
			}
		}
		sb.append("}\n");
		
		// steps, run
		int steps = this.getApp().getMaxProcessLoops();
		if (steps == -1 || steps > 1000) {
			steps = 1000;
		}
		sb.append("steps = ").append(steps).append("\n");
		sb.append("petriNet.RunSimulation(steps, initialLabelling)");
		
		// workaround for bug in JVM is to add .toString() for nested StringBuilder
		return new StringBuilder()
		.append("# To run this Petri net (in Ubuntu Linux):\n")
		.append("# (1) Save this python script to a .py file, for example pn_test.py .\n")
		.append("# (2) Ensure that David Tanzer's petri1.py is in the same directory as this script.\n")
		.append("# (3) Open a terminal window.\n")
		.append("# (4) Run:\n")
		.append("#     python pn_test.py\n")
		.append("# (5) Or run it while redirecting the output to a CSV file:\n")
		.append("#     python pn_test.py > pn_test.csv\n\n")
		.append("from petri1 import *\n\n")
		.append("# ")
		.append(this.getApp().getModelName())
		.append("\n")
		.append(sbTrans.toString())
		.append(sb.toString())
		.toString();
	}
	
	/**
	 * Get Petri net arcs in python format.
	 * @param arcs InputArcs or OutputArcs.
	 * @return
	 */
	protected String getPythonScriptArcs(IXholon arcs) {
		StringBuilder sbTrans = new StringBuilder();
		sbTrans.append("[");
		if (arcs != null) {
			IXholon arc = arcs.getFirstChild();
			while (arc != null) {
				sbTrans.append("[")
				.append("\"")
				.append(makeNamePrefix(((Arc)arc).getPlace()))
				.append(makeNodeName(((Arc)arc).getPlace(), "PlacePN"))
				.append("\"");
				if (arc.getVal() != 1.0) {
					sbTrans.append(",")
					.append(arc.getVal()); // weight
				}
				sbTrans.append("]");
				if (arc.hasNextSibling()) {
					sbTrans.append(", ");
				}
				arc = arc.getNextSibling();
			}
		}
		sbTrans.append("]");
		return sbTrans.toString();
	}
	
	/**
	 * Make a name prefix, to distinguish multiple instances with the same name.
	 * @param node
	 * @return
	 */
	protected String makeNamePrefix(IXholon node) {
		IXholon p = node.getParentNode().getParentNode();
		if (p != null && !"PetriNet".equals(p.getXhcName())) {
			return node.getParentNode().getXhcName() + "_";
		}
		return "";
	}
	
	/**
	 * Construct a node's name.
	 * @param node The node.
	 * @param baseXhcName The base XholonClass name for this type of node (PlacePN, TransitionPN).
	 * @return The node's xhcName or roleName.
	 */
	protected String makeNodeName(IXholon node, String baseXhcName) {
		if (node.getXhcId() < IMechanism.MECHANISM_ID_START) {
			// this is not a base class for this type of node (PlacePN, TransitionPN, etc.)
			return node.getXhcName();
		}
		else if (node.getRoleName() == null) {
			// this is a base class with not role name
			return node.getXhcName();
		}
		/*if (!baseXhcName.equals(node.getXhcName())) {
			return node.getXhcName();
		}
		else if (node.getRoleName() == null) {
			return baseXhcName;
		}*/
		// this is a base class with a role name
		return node.getRoleName();
	}
	
	/**
	 * 
	 */
	protected String makeTransitionStr(IXholon node) {
		StringBuilder sb = new StringBuilder();
		sb.append(":");
		sb.append(makeComplexStr(node, "InputArcs"));
		sb.append(" → ");
		sb.append(makeComplexStr(node, "OutputArcs"));
		return sb.toString();
	}
	
	/**
	 * 
	 * @param node
	 * @param inOutArcsName
	 * @return
	 */
	protected String makeComplexStr(IXholon node, String inOutArcsName) {
		IXholon arc = null;
		StringBuilder sb = new StringBuilder();
		IXholon arcs = node.findFirstChildWithXhClass(inOutArcsName);
		if (arcs == null) {
		//	sb.append("0");
		}
		else {
			arc = arcs.getFirstChild();
			//if (arc == null) {
			//	sb.append("0");
			//}
			while (arc != null) {
				int weight = (int)arc.getVal(); // weight
				if (weight == 0) {}
				else {
					while (weight > 0) {
						//sb.append(((Arc)arc).getPlace().getXhcName());
						sb.append(makeNodeName(((Arc)arc).getPlace(), "PlacePN"));
						if (weight > 1) {
							sb.append(" + ");
						}
						weight--;
					}
				}
				arc = arc.getNextSibling();
				if (arc != null) {
					sb.append(" + ");
				}
			}
		}
		String complexStr = sb.toString();
		if (complexStr.length() == 0) {
			return "0";
		}
		return complexStr;
	}
	
	/**
	 * Prevent tokensCutoff from being written out as an attribute.
	 * @see org.primordion.xholon.base.Xholon#toXmlAttributes(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.IXmlWriter)
	 */
	public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {}

	public double getTokensCutoff() {
		return tokensCutoff;
	}

	public void setTokensCutoff(double tokensCutoff) {
		this.tokensCutoff = tokensCutoff;
	}
}

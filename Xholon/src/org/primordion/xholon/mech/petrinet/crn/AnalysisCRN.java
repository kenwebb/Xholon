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

package org.primordion.xholon.mech.petrinet.crn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.primordion.xholon.base.IMechanism;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.mech.petrinet.Arc;
import org.primordion.xholon.mech.petrinet.PetriNet;
import org.primordion.xholon.mech.petrinet.Transition;

/**
 * Analysis and Reporting for Chemical Reaction Networks (CRN).
 * Analysis nodes are optional.
 * If used, an Analysis node must be a child of the PetriNet (PN) node,
 * typically located just after the QueueTransitions node.
 * An Analysis node might contain other nodes that provide additional analysis functionality.
 * These child nodes could for example by domain-specific or app-specific script nodes.
 * 
 * <p>A lot of this is taken from Feinberg's lectures on chemical reaction networks (CRN).</p>
 * <p>Correspondences between PN and CRN terminology:</p>
<pre>
Petri net     Chemical Reaction Network
------------- -------------------------
place         species
input/output arcs   complex
input arcs    reactant complex
output arcs   product complex
transition    reaction
marking       concentration
weight        stoichiometric coefficient
rate          reaction rate
</pre>
 * <p>To add an Analysis node to a running Petri net app,
 * paste the following as last child of PetriNet node:</p>
<pre>&lt;AnalysisCRN/></pre>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://www.che.eng.ohio-state.edu/~FEINBERG/LecturesOnReactionNetworks/">Feinberg lectures</a>
 * @since 0.8.1 (Created on August 7, 2012)
 */
public class AnalysisCRN extends XholonWithPorts {
	private static final long serialVersionUID = 169343210872835268L;
	
	private static final String showAll = "Show all";
	private static final String showConcentrations = "Show concentrations (as double)";
	private static final String showConcentrationsInt = "Show concentrations (as int)";
	private static final String showSpecies = "Show species";
	private static final String showComplexes = "Show complexes";
	private static final String showReactions = "Show reactions";
	private static final String showDifferentialEquations = "Show differential equations";
	private static final String showDifferentialEquationsTex = "Show differential equations (Tex)";
	private static final String showDifferentialEquationsMml = "Show differential equations (MathML)";
	private static final String showDifferentialEquationsCrn = "Show differential equations (CRN)";
	/** action list */
	private String[] actions = {showAll,showConcentrations,showConcentrationsInt,
			showSpecies,showComplexes,showReactions,showDifferentialEquations,
			showDifferentialEquationsTex,showDifferentialEquationsMml,
			showDifferentialEquationsCrn};
	
	/**
	 * Whether or not to output differential equations in TeX compatible format.
	 * Example:
	 * <p>$$  \frac{d}{d t} e_11 = +(0.0039*d_10) -(0.0039*b_8*e_11) $$</p>
	 */
	private boolean useTex = true;
	
	/**
	 * Whether or not to output differential equations in presentation MathML format.
	 * Example:
	 * <p></p>
	 */
	private boolean useMathML = false;
	
	/**
	 * Whether or not to output differential equation with rate constant symbols.
	 * For example, these may be Greek lowercase letters.
	 */
	private boolean useRateConstantSymbol = true;
	
	private String rateConstantSymbol = "αβγδεζηθικλμνξοπρστυφχψωΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩ";
	
	private String[] rateConstantSymbolTex = {
			"alpha", "beta", "gamma", "delta",
			"epsilon", "zeta", "eta", "theta",
			"iota", "kappa", "lambda", "mu",
			"nu", "xi", "omicron", "pi",
			"rho", "sigma", "tau", "upsilon",
			"phi", "chi", "psi", "omega",
			"Alpha", "Beta", "Gamma", "Delta",
			"Epsilon", "Zeta", "Eta", "Theta",
			"Iota", "Kappa", "Lambda", "Mu",
			"Nu", "Xi", "Omicron", "Pi",
			"Rho", "Sigma", "Tau", "Upsilon",
			"Phi", "Chi", "Psi", "Omega"
			};
	
	/**
	 * Whether or not to use notation similar to what Feinberg uses in his lectures/papers.
	 */
	private boolean useFeinbergNotation = true;
	
	/**
	 * The cutoff in concentration to decide if a species exists.
	 * "the set of species which are actually present in the reactor" (Feinberg 2-4).
	 * If there are no instances of a species, then that species does not appear in the list returned.
	 * Normally, with a Xholon app, we want to see a species even if it's current concentration is 0.0.
	 * So by default, this variable has a value of -1.0,
	 * which means use the species if it has a concentration greater than -1.0 .
	 * To exclude species that have a concentration of 0.0, set this variable to 0.0 .
	 */
	private double concentrationCutoff = -1.0;
	
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
			println("CRN = {S, C, R}");
			showSet("S", getSpecies());
			showSet("C", getComplexes());
			showSet("R", getReactions());
			println("S (concentrations) " + getConcentrations(double.class));
			println("S (concentrations) " + getConcentrations(int.class));
			println("R (names) " + getReactionNames());
			println("R (rate symbols) " + getReactionRateSymbols());
			println("R (rate constants) " + getReactionRateConstants());
			showDifferentialEquations();
		}
		else if (showConcentrations.equals(action)) {
			println("S (concentrations) " + getConcentrations(double.class));
		}
		else if (showConcentrationsInt.equals(action)) {
			println("S (concentrations) " + getConcentrations(int.class));
		}
		else if (showSpecies.equals(action)) {
			showSet("S", getSpecies());
		}
		else if (showComplexes.equals(action)) {
			showSet("C", getComplexes());
		}
		else if (showReactions.equals(action)) {
			showSet("R", getReactions());
		}
		else if (showDifferentialEquations.equals(action)) {
			useTex = false;
			useMathML = false;
			showDifferentialEquations();
		}
		else if (showDifferentialEquationsTex.equals(action)) {
			useTex = true;
			useMathML = false;
			showDifferentialEquations();
		}
		else if (showDifferentialEquationsMml.equals(action)) {
			useTex = false;
			useMathML = true;
			showDifferentialEquations();
		}
		else if (showDifferentialEquationsCrn.equals(action)) {
			useTex = false;
			useMathML = false;
			useFeinbergNotation = true;
			showDifferentialEquations();
			useFeinbergNotation = false;
		}
	}
	
	/**
	 * Show differential equations.
	 */
	protected void showDifferentialEquations() {
		println("differential equations (Mass Action kinetics assumed):");
		if (useMathML) {
			println("<math xmlns=\"http://www.w3.org/1998/Math/MathML\"><mtable>");
		}
		Iterator<String> deList = getDifferentialEquations().iterator();
		while (deList.hasNext()) {
			if (useTex) {
				println(deList.next());
			}
			else {
				println(deList.next());
			}
		}
		if (useMathML) {
			println("</mtable></math>");
		}
	}
	
	/**
	 * Show a Java collection as a CRN set.
	 * @param cName Printable name of the collection.
	 * @param c A Java list or set.
	 */
	protected void showSet(String cName, Collection<?> c) {
		print(cName + " = {");
		Iterator<?>cIt = c.iterator();
		while(cIt.hasNext()) {
			print(cIt.next());
			if (cIt.hasNext()) {
				print(", ");
			}
		}
		println("}");
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
		Iterator<IXholon> speciesIt = getSpeciesRaw().iterator();
		List<Object> concentrations = new ArrayList<Object>();
		while (speciesIt.hasNext()) {
			IXholon species = speciesIt.next();
			double concentration = species.getVal();
			if (concentration > concentrationCutoff) {
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
	 * Get list of species in the CRN = list of places in the PN.
	 * This returns only "the set of species which are actually present in the reactor" (Feinberg 2-4).
	 * If there are no instances of a species, then that species does not appear in the list returned.
	 * @return A list, for example: [A, B, C, D, E]
	 */
	public List<String> getSpecies() {
		Iterator<IXholon> placesIt = getSpeciesRaw().iterator();
		List<String> species = new ArrayList<String>();
		while (placesIt.hasNext()) {
			IXholon aSpecies = placesIt.next();
			if (aSpecies.getVal() > concentrationCutoff) {
				species.add(makeNodeName(aSpecies)); //, "PlacePN"));
			}
		}
		return species;
	}
	
	/**
	 * Get raw list of species in the CRN = list of places in the PN.
	 * @return
	 */
	protected List<IXholon> getSpeciesRaw() {
		return ((PetriNet)this.getParentNode()).getPlaces();
	}
	
	/**
	 * Get set of unique complexes in the CRN.
	 * The "complexes of a network are just the entities
	 * that appear at the heads and tails of the reaction arrows"
	 * "we regard the complexes to be vectors in IR^S""
	 * "it makes sense to add two complexes,
	 * to subtract one complex from another,
	 * to multiply a complex by a number,
	 * and to take the scalar product of a complex with any other vector."
	 * also "support of a complex. For example, supp(A+B) = {A,B},
	 * supp(2B) = {B}, supp(A) = {A}, and so on."
	 * There are reactant complexes, and product complexes.
	 * (Feinberg 2-2).
	 * The numbers in each complex are stoichiometric coefficients. (Feinberg 2-3)
	 * @returnA list, for example: [D, A+C, A, 2B, B+E]
	 */
	public Set<String> getComplexes() {
		Iterator<IXholon> complexesRawIt = getComplexesRaw().iterator();
		Set<String> set = new HashSet<String>();
		while (complexesRawIt.hasNext()) {
			IXholon arcs = complexesRawIt.next(); // InputArcs OutputArcs
			// examples: A 2B A+C 3D+4E+F
			/*StringBuilder complex = new StringBuilder();
			IXholon arc = arcs.getFirstChild();
			while (arc != null) {
				int weight = (int)arc.getVal(); // weight
				if (weight > 0) {
					if (weight > 1) {
						complex.append(weight);
					}
					complex.append(makeNodeName(((Arc)arc).getPlace(), "PlacePN"));
				}
				arc = arc.getNextSibling();
				if (arc != null) {
					complex.append("+");
				}
			}*/
			//set.add(complex.toString());
			set.add(getComplex(arcs));
		}
		return set;
	}
	
	/**
	 * Get the input or output complex for a reaction.
	 * @param arcs InputArcs OutputArcs
	 * @return examples: A 2B A+C 3D+4E+F
	 */
	protected String getComplex(IXholon arcs) {
		StringBuilder complex = new StringBuilder();
		IXholon arc = arcs.getFirstChild();
		while (arc != null) {
			int weight = (int)arc.getVal(); // weight
			if (weight > 0) {
				if (weight > 1) {
					complex.append(weight);
				}
				complex.append(makeNodeName(((Arc)arc).getPlace())); //, "PlacePN"));
			}
			arc = arc.getNextSibling();
			if (arc != null) {
				complex.append("+");
			}
		}
		return complex.toString();
	}
	
	/**
	 * Get the number of unique complexes in the CRN.
	 * "We shall reserve the symbol n to denote the number of complexes
	 * in a given network" (Feinberg 2-3).
	 * @return An int >= 0, for example: 5.
	 */
	public int getComplexesN() {
		return getComplexes().size();
	}
	
	/**
	 * Get raw list of all InputArcs and OutputArcs nodes.
	 * @return
	 */
	protected List<IXholon> getComplexesRaw() {
		Iterator<IXholon> reactionsIt = getReactionsRaw().iterator();
		List<IXholon> complexesRaw = new ArrayList<IXholon>();
		while (reactionsIt.hasNext()) {
			Transition transition = (Transition)reactionsIt.next();
			if (transition.getInputArcs() != null) {
				complexesRaw.add(transition.getInputArcs());
			}
			if (transition.getOutputArcs() != null) {
				complexesRaw.add(transition.getOutputArcs());
			}
		}
		return complexesRaw;
	}
	
	/**
	 * Get list of CRN reaction names.
	 * @return A list, for example: [A_BB, BB_A, AC_D, D_AC, D_BE, BE_AC]
	 */
	public List<String> getReactionNames() {
		Iterator<IXholon> reactionsIt = getReactionsRaw().iterator();
		List<String> reactions = new ArrayList<String>();
		while (reactionsIt.hasNext()) {
			IXholon reaction = reactionsIt.next();
			reactions.add(makeNodeName(reaction)); //, "TransitionPN"));
		}
		return reactions;
	}
	
	/**
	 * Get list of CRN reaction structures.
	 * These are the canonical names for reactions, as described by Feinberg.
	 * @return A list, for example: [A->2B, 2B->A, A+C->D, D->A+C, D->B+E, B+E->A+C]
	 */
	public List<String> getReactions() {
		Iterator<IXholon> reactionsIt = getReactionsRaw().iterator();
		List<String> reactions = new ArrayList<String>();
		while (reactionsIt.hasNext()) {
			IXholon reaction = reactionsIt.next();
			String rs = getComplex(((Transition)reaction).getInputArcs())
				+ "->" + getComplex(((Transition)reaction).getOutputArcs());
			reactions.add(rs);
		}
		return reactions;
	}
	
	/**
	 * Get a list of CRN reaction rate symbols.
	 * @return A list, for example: [α, β, γ, δ, ε, ξ]
	 */
	public List<String> getReactionRateSymbols() {
		Iterator<IXholon> reactionsIt = getReactionsRaw().iterator();
		List<String> reactions = new ArrayList<String>();
		while (reactionsIt.hasNext()) {
			IXholon reaction = reactionsIt.next();
			// for now, can't use rate symbols when all reactions are instances of TransitionPN/Reaction base class
			//if (reaction.getXhcId() < IMechanism.MECHANISM_ID_START) {
			//	reactions.add(((IDecoration)reaction.getXhc()).getSymbol());
			//}
			reactions.add(((Transition)reaction).getSymbol());
		}
		return reactions;
	}
	
	/**
	 * Get a list of CRN reaction rate constants.
	 * @return A list, for example: [0.1, 0.2, 0.33, 0.04, 0.005, 0.6]
	 */
	public List<Double> getReactionRateConstants() {
		Iterator<IXholon> reactionsIt = getReactionsRaw().iterator();
		List<Double> reactions = new ArrayList<Double>();
		while (reactionsIt.hasNext()) {
			IXholon reaction = reactionsIt.next();
			reactions.add(((Transition)reaction).getK());
		}
		return reactions;
	}
	
	/**
	 * Get list of CRN reactions.
	 * @return
	 */
	protected List<IXholon> getReactionsRaw() {
		return ((PetriNet)this.getParentNode()).getTransitions();
	}
	
	/**
	 * Get list of differential equations (DE) for the CRN.
	 * This assumes mass action kinetics.
	 * Each species has its own DE,
	 * which describes "the instantaneous rate of change"(Feinberg lectures p.1-4) of the species' concentration.
	 * There is one term in the DE for each reaction that that involves the species.
	 * See especially Feinberg lectures p.2-12.
	 * @return A list, for example: [Δc_9/Δt = -(0.0039*aC_D_23) +(0.0039*d_AC_29) +(0.0039*bE_AC_41)]
	 */
	public List<String> getDifferentialEquations() {
		Iterator<IXholon> placesIt = getSpeciesRaw().iterator();
		List<String> deList = new ArrayList<String>();
		while (placesIt.hasNext()) {
			IXholon aSpecies = placesIt.next();
			StringBuilder sb = new StringBuilder();
			// 
			if (useTex) {
				sb.append("$$ \\frac{d}{d t} ");
				if (useFeinbergNotation) {
					//sb.append(aSpecies.getXhcName());
					sb.append(makeNodeName(aSpecies)); //, "PlacePN"));
				}
				else {
					sb.append(aSpecies.getName());
				}
				sb.append(" =");
			}
			else if (useMathML) {
				sb.append("<mtr>\n");
				sb.append("  <mtd><mfrac>")
				.append("<mrow><mo>d</mo><mfenced><mrow><mi>")
				//.append(aSpecies.getXhcName())
				.append(makeNodeName(aSpecies)) //, "PlacePN"))
				.append("</mi></mrow></mfenced></mrow>");
				sb.append("<mrow><mo>d</mo><mi>t</mi></mrow></mfrac></mtd>\n");
				sb.append("  <mtd><mo>=</mo></mtd>\n");
/*
<mtd>
   <mfrac>
    <mrow>
     <mo>d</mo>
     <mfenced>
      <mrow>
       <mi>[A]</mi>
       <mo>&CenterDot;</mo>
       <msub><mi>V</mi><mi>pot</mi></msub>
      </mrow>
     </mfenced>
    </mrow>
    <mrow>
     <mo>d</mo><mi>t</mi>
    </mrow>
   </mfrac>
  </mtd>
 */
				sb.append("  <mtd columnalign='left'><mrow>\n");
			}
			else {
				sb.append("Δ")
				//.append(aSpecies.getName())
				.append(makeNodeName(aSpecies)) //, "PlacePN"))
				.append("/Δt")
				.append(" =");
			}
			if (aSpecies.getVal() > concentrationCutoff) {
				// get all reactions that involve aSpecies
				Iterator<IXholon> reactionsIt = getReactionsRaw().iterator();
				while (reactionsIt.hasNext()) {
					Transition aReaction = (Transition)reactionsIt.next();
					// an inputArc for a reaction is a minus for the species
					if (aReaction.getInputArcs() != null) {
						sb.append(this.getTerm(aSpecies, aReaction, aReaction.getInputArcs(), "-"));
					}
					// an outputArc for a reaction is a plus for the species
					if (aReaction.getOutputArcs() != null) {
						sb.append(this.getTerm(aSpecies, aReaction, aReaction.getOutputArcs(), "+"));
					}
				}
			}
			if (useTex) {
				sb.append(" $$");
			}
			else if (useMathML) {
				sb.append("  </mrow></mtd>\n</mtr>");
			}
			deList.add(sb.toString());
		}
		return deList;
	}
	
	/**
	 * Get one term in a differential equation (DE).
	 * @param aSpecies The species that the DE is being constructed for.
	 * @param aReaction The reaction that this term is being constructed for.
	 * @param arcs The inputArcs or outputArcs that will be searched for matches with aSpecies.
	 * @param plusMinus Whether a plus or minus sign will prepend the term. 
	 * @return The term, for example:
	 * <p>-(0.0039*a_7*c_9)</p>
	 * <p>+(0.001*b_8^2)</p>
	 */
	protected String getTerm(IXholon aSpecies, Transition aReaction, IXholon arcs, String plusMinus) {
		if (useMathML) {
			return this.getTermMathML(aSpecies, aReaction, arcs, plusMinus);
		}
		StringBuilder sb = new StringBuilder();
		StringBuilder sbSubTerm1 = new StringBuilder();
		StringBuilder sbSubTerm2 = new StringBuilder();
		boolean found = false;
		Arc arc = (Arc)arcs.getFirstChild();
		String aReactionRateConstant = Double.toString(aReaction.getK());
		if (useRateConstantSymbol) { // && (aReaction.getXhcId() < IMechanism.MECHANISM_ID_START)) {
			String rrc = ((Transition)aReaction).getSymbol();
			if (rrc != null) {
				aReactionRateConstant = rrc;
				if (useTex) {
					int index = rateConstantSymbol.indexOf(aReactionRateConstant);
					if (index != -1) {
						aReactionRateConstant = "\\" + rateConstantSymbolTex[index] + " ";
					}
				}
			}
		}
		while (arc != null) {
			IXholon someSpecies = arc.getPlace();
			if ((someSpecies != null) && (someSpecies == aSpecies)) {
				found = true;
				double w = arc.getVal();
				if (w > 1.0) {
					sbSubTerm1.append((int)w);
					if (!useFeinbergNotation) {
						sbSubTerm1.append("*");
					}
				}
				sb.append(" ")
				.append(plusMinus);
				if (!useFeinbergNotation) {
					sb.append("(");
				}
				IXholon inputArcs = aReaction.getInputArcs();
				Arc aReactionArc = (Arc)inputArcs.getFirstChild();
				while (aReactionArc != null) {
					double weight = aReactionArc.getVal();
					if (weight > 0.0) {
						IXholon termSpecies = aReactionArc.getPlace();
						if (useFeinbergNotation) {
							//sbSubTerm2.append(termSpecies.getXhcName());
							sbSubTerm2.append(makeNodeName(termSpecies)); //, "PlacePN"));
						}
						else {
							sbSubTerm2.append("*")
							.append(termSpecies.getName());
						}
						if (weight > 1.0) {
							sbSubTerm2.append("^")
							.append((int)weight);
						}
					}
					aReactionArc = (Arc)aReactionArc.getNextSibling();
				}
			}
			arc = (Arc)arc.getNextSibling();
		}
		if (found) {
			sb.append(sbSubTerm1.toString()).append(aReactionRateConstant).append(sbSubTerm2.toString());
			if (!useFeinbergNotation) {
				sb.append(")");
			}
		}
		return sb.toString();
	}
	
	/**
	 * Get one term in a differential equation (DE).
	 * @param aSpecies The species that the DE is being constructed for.
	 * @param aReaction The reaction that this term is being constructed for.
	 * @param arcs The inputArcs or outputArcs that will be searched for matches with aSpecies.
	 * @param plusMinus Whether a plus or minus sign will prepend the term. 
	 * @return The term, for example:
	 * <p>-(0.0039*a_7*c_9)</p>
	 * <mo>-</mo><mo>(</mo><mn>0.0039</mn><mo>&#8290;</mo><mi>a_7</mi><mo>&#8290;</mo><mi>c_9</mi><mo>)</mo>
	 * 
	 * <p>+(0.001*b_8^2)</p>
	 * <mo>+</mo><mo>(</mo><mn>0.001</mn><mo>&#8290;</mo><msup><mi>b_8</mi><mn>2</mn></msup><mo>)</mo>
	 */
	protected String getTermMathML(IXholon aSpecies, Transition aReaction, IXholon arcs, String plusMinus) {
		StringBuilder sb = new StringBuilder();
		StringBuilder sbSubTerm1 = new StringBuilder();
		StringBuilder sbSubTerm2 = new StringBuilder();
		boolean found = false;
		Arc arc = (Arc)arcs.getFirstChild();
		String aReactionRateConstant = Double.toString(aReaction.getK());
		/*if (useRateConstantSymbol) {
			String rrc = ((IDecoration)aReaction.getXhc()).getSymbol();
			if (rrc != null) {
				aReactionRateConstant = rrc;
				if (useTex) {
					int index = rateConstantSymbol.indexOf(aReactionRateConstant);
					if (index != -1) {
						aReactionRateConstant = "\\" + rateConstantSymbolTex[index] + " ";
					}
				}
			}
		}*/
		while (arc != null) {
			IXholon someSpecies = arc.getPlace();
			if ((someSpecies != null) && (someSpecies == aSpecies)) {
				found = true;
				double w = arc.getVal();
				if (w > 1.0) {
					sbSubTerm1.append("<mn>").append((int)w).append("</mn>");
					sbSubTerm1.append("<mo>&CenterDot;</mo>");
				}
				sb.append("    ").append("<mo>").append(plusMinus).append("</mo>");
				sb.append("<mo>(</mo>");
				IXholon inputArcs = aReaction.getInputArcs();
				Arc aReactionArc = (Arc)inputArcs.getFirstChild();
				while (aReactionArc != null) {
					double weight = aReactionArc.getVal();
					if (weight > 0.0) {
						IXholon termSpecies = aReactionArc.getPlace();
						/*if (useFeinbergNotation) {
							sbSubTerm2.append(termSpecies.getXhcName());
						}
						else {
							sbSubTerm2.append("*")
							.append(termSpecies.getName());
						}*/
						sbSubTerm2.append("<mo>&CenterDot;</mo>");
						
						if (weight > 1.0) {
							//sbSubTerm2.append("^")
							//.append((int)weight);
							sbSubTerm2.append("<msup><mi>")
							//.append(termSpecies.getXhcName())
							.append(makeNodeName(termSpecies)) //, "PlacePN"))
							.append("</mi>");
							sbSubTerm2.append("<mn>").append((int)weight).append("</mn></msup>");
						}
						else {
							sbSubTerm2.append("<mi>")
							//.append(termSpecies.getXhcName())
							.append(makeNodeName(termSpecies)) //, "PlacePN"))
							.append("</mi>");
						}
					}
					aReactionArc = (Arc)aReactionArc.getNextSibling();
				}
			}
			arc = (Arc)arc.getNextSibling();
		}
		if (found) {
			sb.append(sbSubTerm1.toString()).append("<mn>").append(aReactionRateConstant).append("</mn>")
			.append(sbSubTerm2.toString());
			sb.append("<mo>)</mo>\n");
		}
		return sb.toString();
	}
	
	/**
	 * Construct a node's name.
	 * @param node The node.
	 * @param baseXhcName The base XholonClass name for this type of node (PlacePN, TransitionPN).
	 * @return The node's xhcName or roleName.
	 */
	protected String makeNodeName(IXholon node) { //, String baseXhcName) {
		if (node.getXhcId() < IMechanism.MECHANISM_ID_START) {
			// this is not a base class for this type of node (PlacePN, TransitionPN, etc.)
			return node.getXhcName();
		}
		else if (node.getRoleName() == null) {
			// this is a base class with no role name
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
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setAttributeVal(java.lang.String, java.lang.String)
	 */
	public int setAttributeVal(String attrName, String attrVal) {
		if (attrName.equals("useTex")) {
			setUseTex(Boolean.parseBoolean(attrVal));
		}
		if (attrName.equals("useMathML")) {
			setUseMathML(Boolean.parseBoolean(attrVal));
		}
		return 0;
	}
	/*
	 * @see org.primordion.xholon.base.Xholon#toXmlAttributes(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.IXmlWriter)
	 */
	public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {
		xmlWriter.writeAttribute("useTex", Boolean.toString(useTex));
		xmlWriter.writeAttribute("useMathML", Boolean.toString(useMathML));
	}

	public boolean isUseTex() {
		return useTex;
	}

	public void setUseTex(boolean useTex) {
		this.useTex = useTex;
	}

	public boolean isUseRateConstantSymbol() {
		return useRateConstantSymbol;
	}

	public void setUseRateConstantSymbol(boolean useRateConstantSymbol) {
		this.useRateConstantSymbol = useRateConstantSymbol;
	}

	public double getConcentrationCutoff() {
		return concentrationCutoff;
	}

	public void setConcentrationCutoff(double concentrationCutoff) {
		this.concentrationCutoff = concentrationCutoff;
	}

	public boolean isUseMathML() {
		return useMathML;
	}

	public void setUseMathML(boolean useMathML) {
		this.useMathML = useMathML;
	}
	
}

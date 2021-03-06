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

/**
 * Each transition has a kinetics type.
 * "A kinetics for a reaction network is an assignment of a rate function to each reaction in the network."
 * (Feinberg lectures p.1-4)
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://en.wikipedia.org/wiki/Chemical_kinetics">Chemical kinetics at wikipedia</a>
 * @since 0.8.1 (Created on August 10, 2012)
 */
public interface IKinetics {
	
	/**
	 * The kinetics type is unspecified.
	 * Typically a Transition node will set its kineticsType to this value,
	 * which will force it to take the value specified for the entire Petri net.
	 */
	public static final int KINETICS_UNSPECIFIED = -1;
	
	/**
	 * Take no action at run time.
	 */
	public static final int KINETICS_NULL = 0;
	
	/**
	 * default
	 * Basic place/transition net kinetics as described in:
	 * http://en.wikipedia.org/wiki/Petri_net
	 * "a transition of a Petri net may fire whenever there are sufficient tokens at the start of all input arcs;
	 * when it fires, it consumes these tokens, and places tokens at the end of all output arcs."
	 */
	public static final int KINETICS_BASIC_PTNET = 1;
	
	/**
	 * Mass action kinetics as described in Baez (2011) Network Theory (Part 3),
	 * and in Feinberg (1980) Lectures on Chemical Reaction Networks (Lectures 1 and 2).
	 * <p>"the rate of an elementary reaction ... is proportional to the product
	 * of the concentrations of the participating molecules" (http://en.wikipedia.org/wiki/Law_of_mass_action)</p>
	 * <p>also see: http://en.wikipedia.org/wiki/Rate_equation</p>
	 */
	public static final int KINETICS_MASS_ACTION = 2;
	
	/**
	 * Using a Grid is also a type a kinetics.
	 * This option typically applies to all transitions.
	 * It's currently implemented with the help of GridOwner, and other classes in the grid package.
	 * Grid is an example of a custom kinetics, because all the work is done by behavior children
	 * of Transition nodes in the Grid.
	 */
	public static final int KINETICS_GRID = 3;
	
	/**
	 * Michaelis-Menten enzyme/catalyst kienetics.
	 * The rate is based on two parameters: Vmax and Km.
	 */
	public static final int KINETICS_MICHAELIS_MENTEN = 4;
	
	/**
	 * Membrane Computing, PSystems
	 * This is a variant of KINETICS_BASIC_PTNET.
	 */
	public static final int KINETICS_MAXIMAL_PARALLELISM = 5;
	
	/**
	 * The kinetics is defined in a Java subclass;
	 * or in a separate child node possibly written in a scripting language.
	 * No code is required in the Transition class to enable this.
	 */
	public static final int KINETICS_CUSTOM = 6;
	
	/**
	 * Diffuse or move tokens from one place to another place.
	 * Diffusion between two containers.
	 * In biology, the Transition node might be a Bilayer or TransportProtein.
	 * Assume that there is exactly one input place and exactly one output place.
	 *   OR assume that there are an equal number of input places and output places,
	 *      as for example might be the case with a cell Bilayer.
	 * Assume that the input place and output place have the same type of token.
	 * <p>According to Feinberg Lectures (p.2-27),
	 * "Species A and B diffuse from cell to cell at rates proportional
	 * to the difference in species concentration between the cells."</p>
	 * <p>For this type of kinetics, k is a "diffusion constant".</p>
	 * <p>It's probably not strictly necessary to have a separate diffusion kinetics.
	 * It's equivalent to using mass action.
	 * k(A2-A1) = +kA2 -kA1
	 * Diffusion is just a recognition that A1 and A2 are the same species.
	 * It's a convenience concept.</p>
	 */
	public static final int KINETICS_DIFFUSION = 7;
	
	/**
	 * AND
	 * A typical use is to represent an AND Gate in a digital circuit (2 inputs, 1 output).
	 * I use the definition from section 3.3.4 of the SICP book:
	 * "If both of its input signals become 1,
	 *  then one and-gate-delay time later the and-gate will force its output signal to be 1;
	 *  otherwise the output will be 0."
	 * AND Truth Table
	 * In1 In2 Out
	 * --- --- ---
	 * 1   1   1
	 * 1   0   0
	 * 0   1   0
	 * 0   0   0
	 */
	public static final int KINETICS_LOGIC_AND = 8;
	
	/**
	 * OR
	 * If any input has at least one token,
	 * [then consume that one token, NO]
	 * and add one token to all outputs.
	 * A typical use is to represent an OR Gate in a digital circuit (2 inputs, 1 output).
	 * I use the definition from section 3.3.4 of the SICP book:
	 * "The output will become 1 if at least one of the input signals is 1;
	 *  otherwise the output will become 0."
	 * OR Truth Table
	 * In1 In2 Out
	 * --- --- ---
	 * 1   1   1
	 * 1   0   1
	 * 0   1   1
	 * 0   0   0
	 */
	public static final int KINETICS_LOGIC_OR = 9;
	
	/**
	 * NOT
	 * NOT assumes a single input and typically one output.
	 * If there are more than one inputs, only the first is checked and the others are ignored.
	 * If the input has at least one token,
	 * [then consume that one token, NO]
	 * and remove all tokens from all outputs.
	 * If the input has no tokens,
	 * then place a token at all outputs.
	 * A typical use is to represent an Inverter in a digital circuit (1 input, 1 output).
	 * I use the definition from section 3.3.4 of the SICP book:
	 * "If the input signal to an inverter changes to 0,
	 *  then one inverter-delay later the inverter will change its output signal to 1.
	 *  If the input signal to an inverter changes to 1,
	 *  then one inverter-delay later the inverter will change its output signal to 0."
	 * NOT Truth Table
	 * In  Out
	 * --- ---
	 * 1   0
	 * 0   1
	 */
	public static final int KINETICS_LOGIC_NOT = 10;
	
	/**
	 * The kinetics is defined in a Xholon JavaScript behavior node.
	 * This node should implement a function that accepts an array of values (equal to the number of input arcs),
	 * and returns a single scalar value or possibly an array of values.
	 * The function to call (to send a sync message to) should be identified using an xpath expression.
	 */
	public static final int KINETICS_FUNCTION = 11;
	
	/**
	 * The default kinetics.
	 */
	public static final int KINETICS_DEFAULT = KINETICS_BASIC_PTNET;
	
	/**
	 * Minimum valid kinetics type.
	 */
	public static final int KINETICS_MIN_VALID = KINETICS_NULL;
	
	/**
	 * Maximum valid kinetics type.
	 */
	public static final int KINETICS_MAX_VALID = KINETICS_FUNCTION;
	
	/**
	 * Reaction rate (k) is unspecified; typically used by Transition.
	 */
	public static final double K_UNSPECIFIED = -1.0;
	
	/**
	 * Probability (p) is unspecified; typically used by Transition.
	 */
	public static final double P_UNSPECIFIED = -1.0;
	
	/**
	 * Michaelis-Menten km is unspecified; typically used by Transition.
	 */
	public static final double KM_UNSPECIFIED = -1.0;
	
	/**
	 * Michaelis-Menten vmax is unspecified; typically used by Transition.
	 */
	public static final double VMAX_UNSPECIFIED = -1.0;
	
	/**
	 * For use with KINETICS_LOGIC_ types.
	 */
	public static final double LOGIC_TRUE = 1.0;
	
	/**
	 * For use with KINETICS_LOGIC_ types.
	 */
	public static final double LOGIC_FALSE = 0.0;
	
	/**
	 * Get kinetics type.
	 * @return
	 */
	public abstract int getKineticsType();
	
	/**
	 * Set kinetics type.
	 * @param kineticsType
	 */
	public abstract void setKineticsType(int kineticsType);
}

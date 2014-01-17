/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2005, 2006, 2007, 2008 Ken Webb
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

package org.primordion.xholon.base;

import java.util.List;
import java.util.Vector;

/**
 * The XML Path Language (XPath) is an internet W3C standard, that defines how to locate nodes in a tree.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Sep 8, 2005)
 * @see <a href="http://www.w3.org/TR/xpath"> XML Path Language (XPath) Version 1.0</a>
 * (W3C Recommendation 16 November 1999).
 * @see JSR 206 Java API for XML Processing (JAXP) 1.3
 */
public interface IXPath extends IXholon {

	// XPath axes
	// Forward axes
	public static final int AXIS_CHILD            =  1; // child
	public static final int AXIS_DESCENDANT       =  2; // descendant
	public static final int AXIS_ATTRIBUTE        =  3; // attribute
	public static final int AXIS_SELF             =  4; // self
	public static final int AXIS_DESCENDANTORSELF =  5; // descendant-or-self
	public static final int AXIS_FOLLOWINGSIBLING =  6; // following-sibling
	public static final int AXIS_FOLLOWING        =  7; // following
	public static final int AXIS_NAMESPACE        =  8; // namespace
	// Reverse axes
	public static final int AXIS_PARENT           =  9; // parent
	public static final int AXIS_ANCESTOR         = 10; // ancestor
	public static final int AXIS_PRECEDINGSIBLING = 11; // preceding-sibling
	public static final int AXIS_PRECEDING        = 12; // preceding
	public static final int AXIS_ANCESTORORSELF   = 13; // ancestor-or-self
	// Default axis
	public static final int AXIS_DEFAULT = AXIS_CHILD;
	
	// Relational operators
	public static final int XPATH_OP_NOP = 0; // no operation
	public static final int XPATH_OP_EQ  = 1; // =
	public static final int XPATH_OP_NE  = 2; // !=
	public static final int XPATH_OP_LT  = 3; // <
	public static final int XPATH_OP_GT  = 4; // >
	public static final int XPATH_OP_LE  = 5; // <=
	public static final int XPATH_OP_GE  = 6; // >=

	/**
	 * Evaluate an XPath expression in the specified context and return the result as the specified type.
	 * @param expression The XPath expression.
	 * @param item The starting context (node or node list, for example).
	 * @param returnType The desired return type.
	 * @return Result of evaluating an XPath expression as an Object of returnType.
	 * @see JAXP 1.3
	 */
	public abstract Object evaluate(String expression, Object item, int returnType);
	
	/**
	 * Evaluate an XPath expression in the specified context and return the result as a String.
	 * @param expression The XPath expression.
	 * @param item The starting context (node or node list, for example).
	 * @return The string that is the result of evaluating the expression and converting the result to a String.
	 * @see JAXP 1.3
	 */
	public abstract String evaluate(String expression, Object item);
	
	/**
	 * Evaluate an XPath expression in the specified context and return the result as a IXholon.
	 * @param expression The XPath expression.
	 * @param item The starting context, a IXholon.
	 * @return The IXholon that is the result of evaluating the expression.
	 * <p></p>
	 * <p>Examples of expressions (location paths):</p>
	 * <p>   ancestor::AnElevatorSystem/Floor[1]/CallButton[1]</p>
	 * <p>   ancestor::Cytoplasm/Cytosol/Pyruvate</p>
	 * <p>   ancestor::ExtraCellularSolution/child::Glucose</p>
	 * <p>   ./DescendantName</p>
	 * <p>   ./child::DescendantName/TargetDescendantName</p>
	 */
	public abstract IXholon evaluate(String expression, IXholon item);
	
	/**
	 * Evaluate an XPath expression in the specified context and return the result as a IXholon.
	 * @param expression The XPath expression.
	 * @param item The starting context, a IXholon.
	 * @param pathList A list that should be filled in with the sequence of nodes found at
	 * each locationStep in the expression. If the input pathList is null, then the method should
	 * not do anything with the list.
	 * @return The IXholon that is the result of evaluating the expression.
	 */
	public abstract IXholon evaluate(final String expression, final IXholon item, List<IXholon> pathList);
	
	/**
	 * Get an XPath 1.0 compliant expression that uniquely identifies a path
	 * from an ancestor node to a descendant node.
	 * The expression will use only the child axis,
	 * supplemented if necessary with the attribute axis (ex: [@roleName='xyz'] ).
	 * A roleName or other attribute may be necessary to disambiguate between
	 * two or more children.
	 * This method is the inverse of "IXholon evaluate(String expression, IXholon item)" .
	 * @param descendant A Xholon node that is a descendant of ancestor.
	 * @param ancestor A Xholon node that is an ancestor of descendant.
	 * @param shouldEscape Whether or not to escape Properties key termination characters (=:).
	 * When creating a key in a .properties file, then shouldEscape must be true.
	 * When creating a key for querying an instance of Properties, then shouldEscape must be false.
	 * @return A unique XPath 1.0 expression, or the empty String.
	 */
	public abstract String getExpression(IXholon descendant, IXholon ancestor, boolean shouldEscape);

	/**
	 * Get an XPath 1.0 compliant expression that uniquely identifies a shortest path
	 * from a source node node to a target or referenced node.
	 * @param sourceNode A node in a Xholon tree.
	 * @param reffedNode A referenced node in a Xholon tree.
	 * @return An XPath expression, or null if a path between the nodes could not be found.
	 */
	public abstract String getExpression(IXholon sourceNode, IXholon reffedNode);
	
	/**
	 * Search for closest neighbors whose XholonClass names are xhcName.
	 * @param distance How far within the tree to search for neighbors.
	 * @param include Whether to include (P)arent and/or (S)iblings and/or (C)hildren.
	 * @param excludeXhcName Name of a XholonClass, used to limit the scope of the returned Vector.
	 * @param xhcName Name of a XholonClass.
	 * @param maxQuantity Maximum quantity of closest neighbors to return (may return fewer)
	 * @param matchSuperClasses Whether or not to match superclasses.
	 * @return Vector containing instances of IXholon, or null.
	 * 
	 * TODO Make this fully part of my XPath implementation,
	 * once it supports returning a NodeSet and not just a single node.
	 */
	public abstract Vector<IXholon> searchForClosestNeighbors(int distance, int include,
			String excludeXhcName, String xhcName,
			int maxQuantity, boolean matchSuperClasses, IXholon xhNode);
}

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

import org.primordion.xholon.base.Attribute.Attribute_boolean;
import org.primordion.xholon.base.Attribute.Attribute_char;
import org.primordion.xholon.base.Attribute.Attribute_double;
import org.primordion.xholon.base.Attribute.Attribute_float;
import org.primordion.xholon.base.Attribute.Attribute_int;
import org.primordion.xholon.base.Attribute.Attribute_Object;
import org.primordion.xholon.base.Attribute.Attribute_String;
import org.primordion.xholon.util.ClassHelper;
import org.primordion.xholon.util.Misc;
import org.primordion.xholon.util.MiscRandom;
import org.primordion.xholon.util.StringTokenizer;

/**
 * Simple XPath implementation.
 * This is NOT a complete implementation of XPath 1.0.
 * This implementation is reentrant and thread-safe.
 * It doesn't use any instance or class variables.
 * It doesn't use any other classes that use instance or class variables.
 * It allocates all memory on the stack, through local variables and method arguments.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Sep 8, 2005)
 */
public class XPath extends AbstractXPath {
	private static final long serialVersionUID = -8003054893484578246L;

	/** TODO
	 * - An instance of XPath could have a problem if it's running on thread A,
	 *   is interrupted by thread B, and then continues on thread A, if the processing
	 *   on thread B modifies the structure of the tree. Whether or not this is really
	 *   an issue depends on the actual application. For now, I am not implementing
	 *   any specific support for this scenario. (November 20, 2007)
	 */
	
	/*
	 * @see org.primordion.xholon.base.IXPath#evaluate(java.lang.String, org.primordion.xholon.base.IXholon, java.util.List)
	 */
	public IXholon evaluate(final String expression, final IXholon item, final List<IXholon> pathList)
	{
		int axis = IXPath.AXIS_DEFAULT;
		IXholon contextNode = null;
		if ((expression == null) || (expression.length() == 0)) {
			getLogger().debug("XPath expression is null or has zero length for context: " + item);
			return null;
		}
		String expr = expression;
		if (expression.charAt(0) == '/') {
			contextNode = item.getRootNode(); // this is an absolute location path
			expr = expression.substring(1);
		}
		else {
			contextNode = item; // this is a relative location path
		}
		int position = 1; // XPath position subscripts start with 1
		String predicates = null;
		String attrPredicate = null; // assume only one attribute predicate (ex: state=0)
		int predIx = 0; // index of predicate within the locationPath
		StringTokenizer st = new StringTokenizer(expr, "/:");
		String locationStep = null;
		while (st.hasMoreTokens()) {
			locationStep = st.nextToken();
			if (locationStep.equals("child")) {
				axis = IXPath.AXIS_CHILD;
				locationStep = st.nextToken();
			}
			else if (locationStep.equals("descendant")) {
				axis = IXPath.AXIS_DESCENDANT;
				locationStep = st.nextToken();
			}
			else if (locationStep.equals("attribute")) {
				axis = IXPath.AXIS_ATTRIBUTE;
				locationStep = st.nextToken();
			}
			else if (locationStep.equals("self")) {
				axis = IXPath.AXIS_SELF;
				locationStep = st.nextToken();
			}
			else if (locationStep.equals("descendant-or-self")) {
				axis = IXPath.AXIS_DESCENDANTORSELF;
				locationStep = st.nextToken();
			}
			else if (locationStep.equals("following-sibling")) {
				axis = IXPath.AXIS_FOLLOWINGSIBLING;
				locationStep = st.nextToken();
			}
			else if (locationStep.equals("following")){
				axis = IXPath.AXIS_FOLLOWING;
				locationStep = st.nextToken();
			}
			else if (locationStep.equals("namespace")){
				axis = IXPath.AXIS_NAMESPACE;
				locationStep = st.nextToken();
			}
			else if (locationStep.equals("parent")){
				axis = IXPath.AXIS_PARENT;
				locationStep = st.nextToken();
			}
			else if (locationStep.equals("ancestor")){
				axis = IXPath.AXIS_ANCESTOR;
				locationStep = st.nextToken();
			}
			else if (locationStep.equals("preceding-sibling")) {
				axis = IXPath.AXIS_PRECEDINGSIBLING;
				locationStep = st.nextToken();
			}
			else if (locationStep.equals("preceding")){
				axis = IXPath.AXIS_PRECEDING;
				locationStep = st.nextToken();
			}
			else if (locationStep.equals("ancestor-or-self")){
				axis = IXPath.AXIS_ANCESTORORSELF;
				locationStep = st.nextToken();
			}
			else {
				axis = IXPath.AXIS_DEFAULT;
			}
			position = 1; // XPath position subscripts start with 1
			attrPredicate = null;
			if (contextNode != null) {
				predIx = locationStep.indexOf('[', 0); // get first opening [
				if (predIx != -1) {
					predicates = locationStep.substring(predIx);
					locationStep = locationStep.substring(0, predIx);
				}
				else {
					predicates = null;
				}
				
				// handle . .. and possibly other abbreviations
				switch(locationStep.charAt(0)) {
				case '.':
					if ((locationStep.length() > 1) && (locationStep.charAt(1) == '.')) {
						axis = IXPath.AXIS_PARENT;
					}
					else {
						axis = IXPath.AXIS_SELF;
					}
					break;
				default:
					break;
				}
				
				switch (axis) {
				// Forward axes
				case IXPath.AXIS_CHILD:
					if ("*".equals(locationStep)) {
						contextNode = contextNode.getFirstChild();
					}
					else {
						boolean matchSuperClasses = true;
						if ((contextNode.getXhc() == null) || ("Control".equals(contextNode.getXhcName()))) {
							// contextNode is probably a XholonClass or a Control
							matchSuperClasses = false;
						}
						contextNode = searchForClosestNeighbor(1, IXholon.NINCLUDE_xxC, null,
								locationStep, matchSuperClasses, contextNode);
					}
					break;
				case IXPath.AXIS_DESCENDANT:
					// process later after get predicates
					break;
				case IXPath.AXIS_ATTRIBUTE:
				  /*
					// assume it's a port that references a IXholon
					if ("port".equals(locationStep)) {
						if (predicates != null) {
							// subtract 1 from XPath index to convert from 1-based to 0-based
							int portNum = Misc.atoi(predicates, 1) - 1; // ex: [2]
							contextNode = contextNode.getPort(portNum);
						}
					}
					// assume it's a Port instance that references a replication
					else if ("replication".equals(locationStep)) {
						if (predicates != null) {
							// subtract 1 from XPath index to convert from 1-based to 0-based
							int portNum = Misc.atoi(predicates, 1) - 1; // ex: [2]
							contextNode = contextNode.getPort(portNum);
						}
					}
					// assume it's a non-port scalar port with an arbitrary name (ex: "atmosphere")
					else {
					  contextNode = contextNode.getApp().getAppSpecificObjectVal(contextNode,
					      (Class<IXholon>)contextNode.getClass(), locationStep);
					}*/
					switch (locationStep) {
					case "port":
					  // assume it's a port that references a IXholon
						if (predicates != null) {
							// subtract 1 from XPath index to convert from 1-based to 0-based
							int portNum = Misc.atoi(predicates, 1) - 1; // ex: [2]
							contextNode = contextNode.getPort(portNum);
						}
					  break;
					case "replication":
					  // assume it's a Port instance that references a replication
					  if (predicates != null) {
							// subtract 1 from XPath index to convert from 1-based to 0-based
							int portNum = Misc.atoi(predicates, 1) - 1; // ex: [2]
							contextNode = contextNode.getPort(portNum);
						}
					  break;
					case "boolean":
					{
					  boolean val = contextNode.getVal_boolean();
					  contextNode = new Attribute_boolean();
					  contextNode.setVal(val);
					  break;
					}
					case "char":
					{
					  char val = contextNode.getVal_char();
					  contextNode = new Attribute_char();
					  contextNode.setVal(val);
					  break;
					}
					case "double":
					{
					  double val = contextNode.getVal();
					  contextNode = new Attribute_double();
					  contextNode.setVal(val);
					  break;
					}
					case "float":
					{
					  float val = contextNode.getVal_float();
					  contextNode = new Attribute_float();
					  contextNode.setVal(val);
					  break;
					}
					case "int":
					{
					  int val = contextNode.getVal_int();
					  contextNode = new Attribute_int();
					  contextNode.setVal(val);
					  break;
					}
					case "Object":
					{
					  Object val = contextNode.getVal_Object();
					  contextNode = new Attribute_Object();
					  contextNode.setVal(val);
					  break;
					}
					case "String":
					{
					  String val = contextNode.getVal_String();
					  contextNode = new Attribute_String();
					  contextNode.setVal(val);
					  break;
					}
					default:
					  // assume it's a non-port scalar port with an arbitrary name (ex: "atmosphere")
					  IXholon contextNodeTemp = contextNode.getApp().getAppSpecificObjectVal(contextNode,
					      (Class<IXholon>)contextNode.getClass(), locationStep);
					  if (contextNodeTemp == null) {
					    // call native method
					    Object obj = this.attributeNative(locationStep, contextNode);
					    if ((obj != null) && ClassHelper.isAssignableFrom(Xholon.class, obj.getClass())) {
					      contextNode = (IXholon)obj;
					    }
					  }
					  else {
					    contextNode = contextNodeTemp;
					  }
					  break;
					}
					predicates = null; // prevent it from being re-evaluated
					break;
				case IXPath.AXIS_SELF:
					// contextNode stays the same
					break;
				case IXPath.AXIS_DESCENDANTORSELF:
					// for now, this only handles one special case
					// JTreeIO needs this to search for node with a given name
					String nodeName = null;
					if (locationStep.charAt(0) == '*') {
						if (predicates.charAt(0) == '[') {
							if (predicates.charAt(1) == '@') {
								if (predicates.substring(2,8).equals("name='")) {
									if (predicates.lastIndexOf('\'') > 8) {
										nodeName = predicates.substring(8,predicates.lastIndexOf('\''));
									}
									else {
										// roleName precedes the instance name  ex: roleName:className_123
										// append the missing part
										predicates += ":" + st.nextToken();
										if (predicates.lastIndexOf('\'') > 8) {
											nodeName = predicates.substring(8,predicates.lastIndexOf('\''));
										}
										else {
											getLogger().debug("XPath expression is returning null: "
													+ expression + " context: " + item);
											return null;
										}
									}
								}
							}
						}
					}
					contextNode = searchDescSibSelfForName(contextNode, nodeName, true);
					if (contextNode == null) {
						getLogger().debug("XPath expression is returning null: "
								+ expression + " context: " + item);
					}
					return contextNode;
					// break;
				case IXPath.AXIS_FOLLOWINGSIBLING:
					if ("*".equals(locationStep)) {
						contextNode = contextNode.getNextSibling();
					}
					else {
						IXholon tempNode = contextNode.getNextSibling();
						if ((tempNode != null) && (tempNode.getXhcName().equals(locationStep))) {
						  contextNode = tempNode;
						}
						else {
						  contextNode = null;
						}
					}
					break;
				case IXPath.AXIS_FOLLOWING:
					break;
				case IXPath.AXIS_NAMESPACE:
					break;
				// Reverse axes
				case IXPath.AXIS_PARENT:
					contextNode = contextNode.getParentNode();
					break;
				case IXPath.AXIS_ANCESTOR:
					contextNode = searchAncestorsForClassName(contextNode, locationStep, true);
					break;
				case IXPath.AXIS_PRECEDINGSIBLING:
					if ("*".equals(locationStep)) {
						contextNode = contextNode.getPreviousSibling();
					}
					else {
						IXholon tempNode = contextNode.getPreviousSibling();
						if ((tempNode != null) && (tempNode.getXhcName().equals(locationStep))) {
						  contextNode = tempNode;
						}
						else {
						  contextNode = null;
						}
					}
					break;
				case IXPath.AXIS_PRECEDING:
					break;
				case IXPath.AXIS_ANCESTORORSELF:
					break;
				default:
					break;
				}
				// handle predicates (enclosed by [])
				if (predicates != null) {
					predIx = 0; // point to first opening [
					while (predIx != -1) {
						predIx++;
						if ((Misc.isdigit(Misc.getNumericValue(predicates.charAt(predIx))))
								|| (predicates.charAt(predIx) == '-')) {
							// this is a numeric position
							position = Misc.atoi(predicates, predIx);
						}
						else if (predicates.charAt(predIx) == '@') {
							// this is an attribute  ex: [@state=0]
							int endIx = predicates.indexOf(']', predIx);
							attrPredicate = predicates.substring(predIx+1, endIx);
						}
						else { // a function such as last() or random(1,last())
							int endIx = predicates.indexOf(']', predIx);
							attrPredicate = predicates.substring(predIx, endIx);
							position = doNumberFunction(contextNode, attrPredicate);
							attrPredicate = null;
						}
						predIx = predicates.indexOf('[', predIx);
					}
				}
				if (position == -1) { // -1 means 2 siblings with same name need to interact (ex: PingPong)
					if (contextNode == item) {
						if (contextNode.hasNextSibling()) {
							contextNode = contextNode.getNextSibling();
							if (!contextNode.getXhcName().equals(item.getXhcName())) {
								getLogger().debug("XPath evaluate() can't find Pong");
								return null;
							}
						}
					}
				}
				else {
					if (contextNode != null) {
						switch (axis) {
						case IXPath.AXIS_SELF:
						case IXPath.AXIS_PARENT:
							if (!(xpathPredicateTrue(attrPredicate, contextNode))) {
								getLogger().debug("XPath expression is returning null because of invalid predicate: "
										+ expression + " context: " + item);
								return null;
							}
							break;
						case IXPath.AXIS_DESCENDANT:
							contextNode = searchForNthNamedDescendant(locationStep, position - 1, attrPredicate, contextNode);
							break;
						default:
							// XPath subscripts start at 1
							// searchForNthNamedSibling() subscripts start at 0
							contextNode = searchForNthNamedSibling(locationStep, position - 1, attrPredicate, contextNode);
						} // end switch
					}
				}
			}
			else {
				getLogger().debug("XPath expression is returning null: "
						+ expression + " context: " + item);
				return null;
			}
			if (pathList != null) {
				pathList.add(contextNode);
			}
		} // end while
		if (contextNode == null) {
			getLogger().debug("XPath expression is returning null: "
					+ expression + " context: " + item);
		}
		return contextNode;
	}

	/** 
	 * Find a JavaScript variable/attribute (probably a port that references another node).
	 * Example:
	 * ava.parent().xpath("*[@a1﻿]/attribute::a1﻿").name();  results in "1:cable_320"  (note that the attribute a1 ends with the unicode FEFF character)
   * ava.parent().xpath("Pack[@a1﻿]/attribute::a1﻿").name();  results in "1:cable_320"
	 */
	protected native Object attributeNative(String attr, IXholon xhNode) /*-{
		return xhNode[attr];
	}-*/;
	
	/**
	 * Evaluate an XPath number function.
	 * @param contextNode The starting context.
	 * @param functionStr The function to evaluate.
	 * @return The int that is the result of evaluating the expression.
	 * <p>TODO Use JAXP Interface XPathFunction evaluate() instead.</p>
	 */
	protected int doNumberFunction(IXholon contextNode, String functionStr)
	{
		int startIx = 0;
		int endIx = functionStr.indexOf('(');
		String funcName = functionStr.substring(startIx, endIx);
		if ("last".equals(funcName)) { // ex: last()
			return last(contextNode);
		}
		else if ("random".equals(funcName)) { // ex: random(0,10) random(0,last())
			startIx = endIx + 1;
			endIx = functionStr.indexOf(',', startIx);
			String minVal = functionStr.substring(startIx, endIx);
			startIx = endIx + 1;
			endIx = functionStr.length() - 1;
			String maxVal = functionStr.substring(startIx, endIx);
			return random(contextNode, minVal, maxVal);
		}
		else {
			
		}
		return 1;
	}
	
	/**
	 * Is this XPath predicate true?
	 * @param attrPredicate A string that can evaluate to true or false.
	 * @param xhNode The IXholon node that called this function.
	 * @return true or false
	 */
	protected boolean xpathPredicateTrue(String attrPredicate, IXholon xhNode) {
		// parse three elements:	ex: state=0
		// 1. attribute name	ex: state
		// 2. operator			ex: =
		// 3. value				ex: 0
		// use reflection to determine if the expression is true or false
		if (attrPredicate == null) {
			return true;
		}
		int ix = 0;
		int len = attrPredicate.length();
		// get attribute name	ex: state
		while (ix < len) {
			char myChar = attrPredicate.charAt(ix);
			if (Misc.isMathSymbol(myChar)) {
				break;
			}
			ix++;
		}
		String attrName = attrPredicate.substring(0, ix);
		// get relational operator	ex: =
		int relOp = XPATH_OP_NOP;
		if (ix < len) {
			switch (attrPredicate.charAt(ix)) {
			case '=': // =
				relOp = XPATH_OP_EQ;
				break;
			case '!': // !=
				relOp = XPATH_OP_NE;
				ix++;
				break;
			case '<': // <  <=
				if (attrPredicate.charAt(ix+1) == '=') {
					relOp = XPATH_OP_LE;
					ix++;
				}
				else {
					relOp = XPATH_OP_LT;
				}
				break;
			case '>': // >  >=
				if (attrPredicate.charAt(ix+1) == '=') {
					relOp = XPATH_OP_GE;
					ix++;
				}
				else {
					relOp = XPATH_OP_GT;
				}
				break;
			default:
				break;
			}
			ix++; // point to first character after operator
			// use either type of reflection
			char quoteChar = attrPredicate.charAt(ix); // get quote char ' or "
			ix++; // point to first char beyond opening ' or "
			int endIx = attrPredicate.indexOf(quoteChar, ix); // point to closing quote
			String testStrVal = attrPredicate.substring(ix, endIx);
			String currentStrVal = ReflectionFactory.instance().getAttributeStringVal(xhNode, attrName);
			if (currentStrVal == null) {
				getLogger().error("XPath unable to get val of " + xhNode + "." + attrName);
			}
			return xpathStringPredicateTrue(relOp, currentStrVal, testStrVal);
		}
		else {
			// this is a simple existence predicate (ex: [@val] [@val_char] )
			// TODO this call to IReflection is not very efficient
			Object[][] rows = ReflectionFactory.instance().getAppSpecificAttributes(xhNode, true, false, true);
			for (int i = 0; i < rows.length; i++) {
				Object[] row = rows[i];
				//System.out.println(row[0] + "," + row[1] + "," + row[2]);
				if (attrName.equalsIgnoreCase((String)row[0])) {
					return true;
				}
			}
			//return false;
			return xpathPredicateTrueNative(attrPredicate, xhNode);
		}
	}
	
	/** 
	 * Check if a JavaScript variable (probably a port that references another node) exists and has a non-null non-undefined value.
	 * Example:
	 * ava.parent().xpath("*[@a1﻿]").name();  results in "P1:pack_301"  (note that the attribute a1 ends with the unicode FEFF character)
	 */
	protected native boolean xpathPredicateTrueNative(String attrPredicate, IXholon xhNode) /*-{
		if (xhNode[attrPredicate]) {
			return true;
		}
		else {
			return false;
		}
	}-*/;
	
	/**
	 * Is this XPath integer predicate true?
	 * @param relOp A relational operator (ex: = ).
	 * @param currentIntVal The current value.
	 * @param testIntVal A value that may match the current value.
	 * @return true or false
	 */
	protected boolean xpathIntPredicateTrue(int relOp, int currentIntVal, int testIntVal)
	{
		switch (relOp) {
		case XPATH_OP_EQ:
			if (currentIntVal == testIntVal) {
				return true;
			}
			break;
		case XPATH_OP_NE:
			if (currentIntVal != testIntVal) {
				return true;
			}
			break;
		case XPATH_OP_LT:
			if (currentIntVal < testIntVal) {
				return true;
			}
			break;
		case XPATH_OP_GT:
			if (currentIntVal > testIntVal) {
				return true;
			}
			break;
		case XPATH_OP_GE:
			if (currentIntVal >= testIntVal) {
				return true;
			}
			break;
		case XPATH_OP_LE:
			if (currentIntVal <= testIntVal) {
				return true;
			}
			break;
		default:
			break;
		}
		return false;
	}
	
	/**
	 * Is this XPath string predicate true?
	 * @param relOp A relational operator (ex: = ).
	 * @param currentStrVal The current value.
	 * @param testStrVal A value that may match the current value.
	 * @return true or false
	 */
	protected boolean xpathStringPredicateTrue(int relOp, String currentStrVal, String testStrVal)
	{
		switch (relOp) {
		case XPATH_OP_EQ:
			if (currentStrVal == null) {return false;}
			if (testStrVal == null) {return false;}
			if (currentStrVal.equals(testStrVal)) {
				return true;
			}
			break;
		default:
			break;
		}
		return false;
	}
	
	/**
	 * Search ancestors for class name.
	 * @param currentNode Xholon node from which to start searching (search includes this node).
	 * @param targetBecName The name of the XholonClass being searched for.
	 * @param matchSuperClasses Whether or not to match superclasses.
	 * @return Xholon instance, or null if not found
	 */
	protected IXholon searchAncestorsForClassName(IXholon currentNode, String targetBecName, boolean matchSuperClasses)
	{
		while (!currentNode.isRootNode()) {
			currentNode = currentNode.getParentNode();
			if (matchSuperClasses) {
				if ((currentNode.getXhc().hasAncestor(targetBecName))) {
					return currentNode; // found
				}
			}
			else {
				if ((currentNode.getXhcName().equals(targetBecName))) {
					return currentNode; // found
				}
			}
		}
		return null;
	}
	
	/**
	 * Search for nth named descendant.
	 * @param descendantName XholonClass name of a possible descendent in the composite structure tree.
	 * @param n
	 * @param attrPredicate A string that can evaluate to true or false.
	 * @param xhNode The IXholon context node.
	 * @return An instance of Xholon.
	 * 
	 * TODO ignoring n for now
	 */
	protected IXholon searchForNthNamedDescendant(String descendantName, int n, String attrPredicate, IXholon xhNode)
	{
		boolean matchSuperClasses = true;
		if ((xhNode.getXhc() == null) || ("Control".equals(xhNode.getXhcName()))) {
			// xhNode is probably a XholonClass or Control
			matchSuperClasses = false;
		}
		if ((n == 0) && (attrPredicate == null)) {
			return searchForClosestNeighbor(50, IXholon.NINCLUDE_xxC, null, descendantName, matchSuperClasses, xhNode);
		}
		Vector<IXholon> dV = searchForClosestNeighbors(50, IXholon.NINCLUDE_xxC, null,
				descendantName, 1000, matchSuperClasses, xhNode);
		IXholon node = null;
		for (int i = 0; i < dV.size(); i++) {
			node = (IXholon)dV.elementAt(i);
			if (xpathPredicateTrue(attrPredicate, node)) {
				return node;
			}
		}
		return null;
	}
	
	/**
	 * Search for the Nth sibling Xholon that has this siblingName.
	 * The current node, where the search is starting, must be the 0th sibling.
	 * If n = 0, then the current node is returned.
	 * Selects the Nth sibling that matches the attrPredicate.
	 * @param siblingName XholonClass name of the searched for sibling.
	 * The siblingName can be the wild card "*".
	 * @param n A number in the range from 0 to 1 less than the number of siblings with that name.
	 * @param attrPredicate A predicate on an attribute that must be true.
	 * @param xhNode The IXholon context node.
	 * @return Nth sibling, or null.
	 */
	protected IXholon searchForNthNamedSibling(String siblingName, int n, String attrPredicate, IXholon xhNode)
	{
		if ((n == 0) && (attrPredicate == null)) {
			return xhNode;
		}
		int i = -1;
		IXholon node = xhNode;
		while (i < n) {
			if (node == null) {
				return null;
			}
			else if (("*".equals(siblingName))
					|| ((node.getXhc() != null) && node.getXhc().hasAncestor(siblingName))
					|| siblingName.equals(node.getXhcName())) {
				if (xpathPredicateTrue(attrPredicate, node)) {
					i++;
					if (i == n) {
						return node;
					}
				}
				node = node.getNextSibling();
			}
			// OLD
			//else { // assume that all siblings with same name are adjacent to each other
			//	return null;
			//}
			// NEW  June 14, 2018
			else { // assume that siblings with same name may not be adjacent to each other
				node = node.getNextSibling();
			}
		}
		return node;
	}

	/**
	 * Search right siblings for first instance of the target class.
	 * @param currentNode Xholon node from which to start searching (search includes this node).
	 * @param targetBecName The name of the XholonClass being searched for.
	 * @param matchSubClasses Whether or not to match superclasses.
	 * @return Xholon instance, or null if not found.
	 * 
	 * TODO Make use of this function in evaluate() for following-sibling
	 */
	@SuppressWarnings("unchecked")
	protected IXholon searchRightSiblings(IXholon currentNode, String targetBecName, boolean matchSubClasses) {
		if (matchSubClasses == false) {
			while (currentNode != null) {
				if ((currentNode.getXhcName().equals(targetBecName))) {
					break; // found
				}
				else {
					currentNode = currentNode.getNextSibling();
				}
			}
		}
		// match on sub classes
		else {
			IXholonClass targetBec = currentNode.getClassNode(targetBecName);
			Vector<IXholon> v = targetBec.getChildNodes(true); // get all nested subclasses
			while (currentNode != null) {
				if (v.contains(currentNode.getXhc())) {
					break; // found
				}
				else {
					currentNode = currentNode.getNextSibling();
				}
			}
		}
		return currentNode;
	}
	
	/**
	 * Search all children, all next siblings, and self, for an instance of IXholon that has a name
	 * that matches the target name. It's possible that the input currentNode is some other class
	 * that implements IXholon, such as XholonClass, in which case this method will always return null.
	 * @param currentNode node from which to start searching (search includes this node)
	 * @param targetName name of the IXholon entity being searched for (ex: "gridCell_3")
	 * @param searchAllLevels
	 * 		true search entire subtree at all levels,
	 * 		false search only this level (right siblings only)
	 * @return Xholon instance, or null if not found.
	 */
	@SuppressWarnings("unchecked")
	protected IXholon searchDescSibSelfForName(IXholon currentNode, String targetName, boolean searchAllLevels) {
		if (currentNode.getName().equals(targetName)) {
			return currentNode;
		}
		Vector<IXholon> nodeV;
		if (searchAllLevels) {
			nodeV = currentNode.getChildNodes(true); // get all nested children
		}
		else {
			nodeV = currentNode.getChildNodes(false); // get all immediate children
		}
		for (int i = 0; i < nodeV.size(); i++) {
			currentNode = (IXholon)nodeV.elementAt(i);
			if (currentNode.getName().equals(targetName)) {
				//if (XholonClass.class.isAssignableFrom(currentNode.getClass())) {
				if (ClassHelper.isAssignableFrom(XholonClass.class, currentNode.getClass())) {
					return null;
				}
				else {
					return currentNode;
				}
			}
		}
		return (IXholon)null;
	}
	
	/**
	 * Search for closest neighbor whose XholonClass name is xhcName.
	 * @param distance How far within the tree to search for neighbors.
	 * @param include Whether to include (P)arent and/or (S)iblings and/or (C)hildren.
	 * @param excludeBecName Name of a XholonClass, used to limit the scope of the returned Vector.
	 * @param xhcName Name of a XholonClass.
	 * @param matchSuperClasses Whether or not to match superclasses.
	 * @param xhNode The IXholon context node.
	 * @return Closest neighbor, or null.
	 */
	protected IXholon searchForClosestNeighbor(int distance, int include, String excludeBecName,
			String xhcName, boolean matchSuperClasses, IXholon xhNode)
	{
		Vector<IXholon> v = searchForClosestNeighbors(distance, include, excludeBecName, xhcName, 1, matchSuperClasses, xhNode);
		if (v.size() > 0) {
			return (IXholon)v.elementAt(0);
		}
		else {
			return null;
		}
	}
	
	/**
	 * Search for closest neighbors whose XholonClass names are xhcName.
	 * @param distance How far within the tree to search for neighbors.
	 * @param include Whether to include (P)arent and/or (S)iblings and/or (C)hildren.
	 * @param excludeBecName Name of a XholonClass, used to limit the scope of the returned Vector.
	 * @param xhcName Name of a XholonClass.
	 * @param maxQuantity Maximum quantity of closest neighbors to return (may return fewer)
	 * @param matchSuperClasses Whether or not to match superclasses.
	 * @param xhNode A xholon node.
	 * @return Vector containing instances of IXholon, or null.
	 * 
	 * TODO Make this fully a part of XPath, once Xpath supports returning a NodeSet and not just a single node.
	 * TODO Make it protected; but for now XhLife needs to use it directly to get a Vector.
	 */
	@SuppressWarnings("unchecked")
	public Vector<IXholon> searchForClosestNeighbors(int distance, int include, String excludeBecName, String xhcName,
			int maxQuantity, boolean matchSuperClasses, IXholon xhNode)
	{
		int quantityFound = 0;
		Vector<IXholon> v = new Vector<IXholon>();
		IXholon node = null;
		Vector<IXholon> nV = xhNode.getNeighbors(distance, include, excludeBecName);
		for (int i = 0; i < nV.size(); i++) {
			node = (IXholon)nV.elementAt(i);
			if (matchSuperClasses) {
				if (node.getXhc().hasAncestor(xhcName)) {
					v.addElement(node);
					quantityFound++;
					if (quantityFound >= maxQuantity) {
						return v;
					}
				}
			}
			else {
				if (xhcName.equals(node.getXhcName())) {
					v.addElement(node);
					quantityFound++;
					if (quantityFound >= maxQuantity) {
						return v;
					}
				}
			}
		}
		return v;
	}
	
	/**
	 * Return count of the number of siblings of the same class.
	 * It's assumed that the contextNode is the first of that class.
	 * @param contextNode The current context node.
	 * @return A count.
	 */
	@SuppressWarnings("unchecked")
	protected int last(IXholon contextNode)
	{
		Vector<IXholon> lastV = contextNode.getSiblings(); // excludes this contextNode
		IXholonClass myBec = contextNode.getXhc();
		int count = 1;
		
		for (int i = 0; i < lastV.size(); i++) {
			IXholon myNode = (IXholon)lastV.elementAt(i);
			if (myNode.getXhc() == myBec) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Get a random number.
	 * @param contextNode
	 * @param minVal
	 * @param maxVal
	 * @return
	 */
	protected int random(IXholon contextNode, String minVal, String maxVal) {
		int min = 0;
		int max = 0;
		int intVal = Misc.getNumericValue(minVal.charAt(0));
		if (Misc.isdigit(intVal)) {
			min = Misc.atoi(minVal, 0);
		}
		else {
			// this must be an embedded xpath function
		}
		intVal = Misc.getNumericValue(maxVal.charAt(0));
		if (Misc.isdigit(intVal)) {
			max = Misc.atoi(maxVal, 0);
		}
		else {
			// this must be an embedded xpath function
			max = doNumberFunction(contextNode, maxVal);
		}
		return MiscRandom.getRandomInt(min, max + 1); // returns a value such that min <= value < max
	}
	
	/*
	 * @see org.primordion.xholon.base.IXPath#getExpression(org.primordion.xholon.base.IXholon, org.primordion.xholon.base.IXholon, boolean)
	 */
	public String getExpression(IXholon descendant, IXholon ancestor, boolean shouldEscape)
	{
		String escapeStr = "";
		if (shouldEscape) {
			escapeStr = "\\";
		}
		String expression = "";
		IXholon node = descendant;
		while (node != null) {
			if (expression.length() > 0) {
				// add separator between XPath locations
				expression = "/" + expression;
			}
			if (node.isUniqueSibling()) {
				expression = node.getXhcName() + expression;
			}
			else { // add roleName or other distinguishing attribute
				String attr = "[";
				if (node.isUniqueSiblingRoleName()) {
					attr += "@roleName" + escapeStr + "='" + node.getRoleName() + "'";
				}
				else if (node.getUid() != null) {
					// TODO the Uid may not be unique; Uid only used in state machines
					attr += "@uid" + escapeStr + "='" + node.getUid() + "'";
				}
				else {
					attr += node.getSelfAndSiblingsIndex(true) + 1;
					// Warning: id will be different if the composite structure is changed
					//attr += "@id" + escapeStr + "='" + node.getId() + "'";
				}
				attr += "]";
				expression = node.getXhcName() + attr + expression;
			}
			if (node == ancestor) {
				break;
			}
			node = node.getParentNode();
		}
		if (node == null) {
			return ""; // ancestor not found
		}
		return expression;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXPath#getExpression(org.primordion.xholon.base.IXholon, org.primordion.xholon.base.IXholon)
	 */
	public String getExpression(IXholon sourceNode, IXholon reffedNode)
	{
		// TODO in Collisions, drivers_25 to scenario_52 doesn't work
		//   it produces: descendant::Scenario[4]
		//   should be  : ancestor::CollisionSystem/Scenarios/Scenario[4]
		if (sourceNode == null) {return null;}
		if (reffedNode == null) {return null;}
		String sourceExpr = getExpression(sourceNode, sourceNode.getRootNode(), false);
		String reffedExpr = getExpression(reffedNode, reffedNode.getRootNode(), false);
		//System.out.println(sourceExpr);
		//System.out.println(reffedExpr);
		if (sourceExpr == null) {return null;}
		if (reffedExpr == null) {return null;}
		StringTokenizer stA = new StringTokenizer(sourceExpr, "/");
		StringTokenizer stB = new StringTokenizer(reffedExpr, "/");
		String tokenA = null;
		String tokenB = null;
		String latestCommonToken = null;
		while (stA.hasMoreTokens() && stB.hasMoreTokens()) {
			tokenA = stA.nextToken();
			tokenB = stB.nextToken();
			if (tokenA.equals(tokenB)) {
				latestCommonToken = tokenA;
			}
			else {
				break;
			}
		}
		String xpathExpr = null;
		boolean didDescendant = false; // whether "descendant::" was last String added to xpathExpr
		if (!stA.hasMoreTokens() && stB.hasMoreTokens()) {
			if (tokenA.equals(tokenB)) {
				// the source node is a parent or other ancestor of the reffed node
				xpathExpr = "descendant::";
				didDescendant = true;
			}
			else {
				xpathExpr = "ancestor::" + latestCommonToken + "/" + tokenB;
			}
		}
		else if (stA.hasMoreTokens() && !stB.hasMoreTokens()) {
			// the source node is a child or other descendant of the reffed node
			if (tokenA.equals(tokenB)) {
				xpathExpr = "ancestor::" + tokenB;
			}
			else {
				xpathExpr = "ancestor::" + latestCommonToken + "/" + tokenB;
			}
		}
		else {
			xpathExpr = "ancestor::" + latestCommonToken + "/" + tokenB;
		}
		while (stB.hasMoreTokens()) {
			// get rest of path to the reffed node
			if (didDescendant) {
				xpathExpr += stB.nextToken();
				didDescendant = false;
			}
			else {
				xpathExpr += "/" + stB.nextToken();
			}
		}
		return xpathExpr;
	}
	
}

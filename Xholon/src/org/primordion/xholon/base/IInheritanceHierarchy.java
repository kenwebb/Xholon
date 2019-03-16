/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2007, 2008 Ken Webb
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

import java.util.Map;

/**
 * An InheritanceHierarchy represents a hierarchy (tree) of XholonClass nodes that
 * inherit from other XholonClass nodes.
 * It encapsulates the root node of a XholonClass hierarchy (rootNode).
 * Primary roles of the InheritanceHierarchy are:
 * (1) to read a XholonClass hierarchy in from a file (ex: InheritanceHierarchy.xml)
 *     and store the nodes in a tree, and
 * (2) to read XholonClass details in from a file (ex: ClassDetails.txt).
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.6 (Created on April 16, 2007)
 */
public interface IInheritanceHierarchy extends IXholon {

	public static final int EOF = -1;

	public static final int CLASSID_NOT_FOUND = -1;

	public static final char NAVINFO_SEPARATOR = ContainmentData.CD_SEPARATOR; //'^';

	public static final int NEXTIDLOCAL_INITIAL = -1; // initial value of nextIdLocal

	// Algorithm to use when searching for a specified XholonClass node
	/** Search the entire tree starting at root. */
	public static final int GETCLASSNODE_TREESEARCH = 1;

	/** Use a hash table. This should normally be the default. */
	public static final int GETCLASSNODE_HASHTABLE = 2;

	/**
	 * Set parameter XInclude path.
	 * @param xiPath The path  ex: "./config/_common/" .
	 * */
	public abstract void setXincludePath(String xincludePath);
	
	/**
	 * Get parameter XInclude path.
	 * @return The path.
	 * */
	public abstract String getXincludePath();
	
	/**
	 * Get the search algorithm, either GETCLASSNODE_TREESEARCH or GETCLASSNODE_HASHTABLE.
	 * @return The search algorithm.
	 */
	public int getSearchAlgorithm();

	/**
	 * Set the search algorithm, either GETCLASSNODE_TREESEARCH or GETCLASSNODE_HASHTABLE.
	 * @param searchAlgorithm The search algorithm.
	 */
	public void setSearchAlgorithm(int searchAlgorithm);

	/**
	 * Get the internal hash table. This is only useful if the search algorithm is GETCLASSNODE_HASHTABLE.
	 * @return
	 */
	public Map getClassNodeHt();

	/**
	 * Set the internal hash table. This is only useful if the search algorithm is GETCLASSNODE_HASHTABLE.
	 * @param classNodeHt
	 */
	public void setClassNodeHt(Map classNodeHt);
	
	/**
	 * Get reference to a IXholonClass node, specified by its numeric ID.
	 * @param xhClassId The numeric ID.
	 * @return An instance of IXholonClass.
	 */
	public abstract IXholonClass getClassNode(int xhClassId);

	/**
	 * Get reference to a IXholonClass node, specified by its name.
	 * @param xhClassName Name of the IXholonClass (ex: "HelloWorld").
	 * @return An instance of IXholonClass, or null.
	 */
	public abstract IXholonClass getClassNode(String xhClassName);

	/**
	 * Create a hash entry for the specified IXholonClass.
	 * @param xhClass
	 */
	public abstract void createHashEntry(IXholonClass xhClass);
	
	/**
	 * Remove the hash entry for the specified IXholonClass.
	 * @param xhClass
	 */
	public abstract void removeHashEntry(IXholonClass xhClass);

}

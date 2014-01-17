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

import java.util.HashMap;
import java.util.Map;

import org.primordion.xholon.common.mechanism.CeXholonFramework;

/**
 * An InheritanceHierarchy provides fast access to all instances of IXholonClass
 * that are currently loaded into a Xholon application.
 * The default internal storage uses a hash table, but this can be changed to using a binary tree.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Sept 8, 2005)
 */
public class InheritanceHierarchy extends Xholon implements IInheritanceHierarchy {
	private static final long serialVersionUID = 8449141590429484740L;

	/** Which search algorithm to use for locating a XholonClass node. */
	private int searchAlgorithm = GETCLASSNODE_HASHTABLE;
	
	/** Hash table for fast searching by XholonClass name or ID. */
	private Map classNodeHt = null;
	
	/**
	 * Root of the inheritance hierarchy tree.
	 * This is only used if the searchAlgorithm is GETCLASSNODE_TREESEARCH.
	 */
	private XholonClass rootNode;
	
	/** Java Standard Edition */
	private String xincludePath = "./config/_common/";
	/** Java Micro Edition, MIDP */
	//private String xincludePath = "/config/_common/";
	
	/**
	 * constructor
	 */
	public InheritanceHierarchy()
	{
		rootNode = null;
		if (searchAlgorithm == GETCLASSNODE_HASHTABLE) {
			// create hash table for entire tree, for searching by name (a String) and id (an int)
			classNodeHt = new HashMap(100); // will need enough space for all names and IDs
		}
		// give the Xholon instance the same id as the XholonClass
		setId(CeXholonFramework.InheritanceHierarchyCE);
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getXhc()
	 */
	public IXholonClass getXhc()
	{
		if (xhc == null) {
			xhc = getClassNode(getId());
		}
		return xhc;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getXhcName()
	 */
	public String getXhcName()
	{
		return getXhc().getName();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getName()
	 */
	public String getName()
	{
		getXhc(); // make sure the XholonClass has been set
		return super.getName();
	}
	
	/*
	 * @see org.primordion.xholon.base.IInheritanceHierarchy#setXincludePath(java.lang.String)
	 */
	public void setXincludePath(String xincludePath) {this.xincludePath = xincludePath;}
	
	/*
	 * @see org.primordion.xholon.base.IInheritanceHierarchy#getXincludePath()
	 */
	public String getXincludePath() {return xincludePath;}

	/*
	 * @see org.primordion.xholon.base.IInheritanceHierarchy#getSearchAlgorithm()
	 */
	public int getSearchAlgorithm() {
		return searchAlgorithm;
	}

	/*
	 * @see org.primordion.xholon.base.IInheritanceHierarchy#setSearchAlgorithm(int)
	 */
	public void setSearchAlgorithm(int searchAlgorithm) {
		this.searchAlgorithm = searchAlgorithm;
	}

	/*
	 * @see org.primordion.xholon.base.IInheritanceHierarchy#getClassNodeHt()
	 */
	public Map getClassNodeHt() {
		return classNodeHt;
	}

	/*
	 * @see org.primordion.xholon.base.IInheritanceHierarchy#setClassNodeHt(java.util.Hashtable)
	 */
	public void setClassNodeHt(Map classNodeHt) {
		this.classNodeHt = classNodeHt;
	}

	/*
	 * @see org.primordion.xholon.base.IInheritanceHierarchy#getClassNode(int)
	 */
	public IXholonClass getClassNode( int xhClassId )
	{
		// Get value from hash table
		if (searchAlgorithm == GETCLASSNODE_HASHTABLE) {
			return (XholonClass)classNodeHt.get(new Integer(xhClassId));
		}
		// Recursively search the entire tree
		else if (searchAlgorithm == GETCLASSNODE_TREESEARCH) {
			return getClassNode(xhClassId, rootNode);
		}
		else {
			logger.warn("InheritanceHierarchy: getClassNode(int) invalid algorithm specified");
			return null;
		}
	}
	
	/**
	 * Search for a XholonClass node with the specified numeric class ID.
	 * @param xhClassId The numeric ID.
	 * @param xhClass Current instance of XholonClass within the tree.
	 * @return An instance of XholonClass, or null.
	 */
	protected XholonClass getClassNode( int xhClassId, XholonClass xhClass )
	{
		XholonClass xhClassReturn = null;
		if (xhClass.getId() == xhClassId) {
			return xhClass;
		}
		else {
			// execute recursively
			if (xhClass.firstChild != null) {
				xhClassReturn = getClassNode(xhClassId, (XholonClass)xhClass.firstChild);
				if (xhClassReturn != null) {
					return xhClassReturn;
				}
			}
			if (xhClass.nextSibling != null) {
				return getClassNode(xhClassId, (XholonClass)xhClass.nextSibling);
			}
		}
		return null;
	}
	
	/*
	 * @see org.primordion.xholon.base.IInheritanceHierarchy#getClassNode(java.lang.String)
	 */
	public IXholonClass getClassNode( String xhClassName ) {
		// Get value from hash table
		if (searchAlgorithm == GETCLASSNODE_HASHTABLE) {
			return (XholonClass)classNodeHt.get(xhClassName);
		}
		// Recursively search the entire tree
		else if (searchAlgorithm == GETCLASSNODE_TREESEARCH) {
			return getClassNode(xhClassName, rootNode);
		}
		else {
			logger.warn("InheritanceHierarchy: getClassNode(String) invalid algorithm specified");
			return null;
		}
	}

	/**
	 * Recursively search for a XholonClass node with the specified name.
	 * @param xhClassName The name being searched for.
	 * @param xhClass Current instance of XholonClass within the tree.
	 * @return An instance of XholonClass, or null.
	 */
	protected XholonClass getClassNode( String xhClassName, XholonClass xhClass )
	{
		XholonClass xhClassReturn = null;
		if (xhClass.getName().equals(xhClassName)) {
			return xhClass;
		}
		else {
			// execute recursively
			if (xhClass.firstChild != null) {
				xhClassReturn = getClassNode(xhClassName, (XholonClass)xhClass.firstChild);
				if (xhClassReturn != null) {
					return xhClassReturn;
				}
			}
			if (xhClass.nextSibling != null) {
				return getClassNode(xhClassName, (XholonClass)xhClass.nextSibling);
			}
		}
		return null;
	}
	
	/*
	 * @see org.primordion.xholon.base.IInheritanceHierarchy#createHashEntry(org.primordion.xholon.base.IXholonClass)
	 */
	public void createHashEntry(IXholonClass xhClass)
	{
		createHashEntry((XholonClass)xhClass, classNodeHt, String.class);
		createHashEntry((XholonClass)xhClass, classNodeHt, Integer.class);
	}
	
	/**
	 * Creates an entry in a hash table.
	 * Ensures that duplicate entries are not made.
	 * @param xhClass The XholonClass that a new entry will be created for.
	 *        When explicitly calling this function, xhClass should be the root of the inheritance tree.
	 * @param ht The Hashtable that the new entry will be created in.
	 * @param keyType String.class to specify name, or Integer.class to specify ID.
	 */
	protected void createHashEntry(XholonClass xhClass, Map ht, Class keyType)
	{
		// before adding the new entry, first check if this would be a duplicate entry
		if (keyType == String.class) {
			String key = xhClass.getName();
			if (!ht.containsKey(key)) {
				ht.put(key, xhClass);
			}
		}
		else { // keyType == Integer.class
			Integer key = new Integer(xhClass.getId());
			if (!ht.containsKey(key)) {
				ht.put(key, xhClass);
			}
		}
	}
	
}

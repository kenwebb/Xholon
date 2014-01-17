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

package org.primordion.xholon.util;

import java.util.ArrayList;
import java.util.List;

/**
 * XholonTreeNode is a node in a tree structure.
 * Each node has a parent, a first child, and a next sibling,
 * each of which is also a XholonTreeNode.
 * This is a partial implementation of the IXholon interface.
 * It is not dependant on any other classes in org.primordion,
 * and is designed to be used anywhere, including within the Xholon framework,
 * within a Xholon application, or within any non-Xholon application. 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Jun 8, 2005)
 */

public class XholonTreeNode {
	
	// constants
	public static final int DEFAULT_LEVEL = 0;

	/** Parent node, or null if root. */
	protected XholonTreeNode parent = null;
	
	/** First (leftmost) child, or null. */
	protected XholonTreeNode firstChild = null;
	
	/** Next (right) sibling, or null. */
	protected XholonTreeNode nextSibling = null;
	
	/** Constructor. */
	public XholonTreeNode()	{}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#remove()
	 */
	public void remove()
	{
		if (hasChildNodes()) {
			firstChild.remove();
		}
		if (hasNextSibling()) {
			nextSibling.remove();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getParentNode()
	 */
	public XholonTreeNode getParentNode()       { return parent; }
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getFirstChild()
	 */
	public XholonTreeNode getFirstChild()    { return firstChild; }
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getNextSibling()
	 */
	public XholonTreeNode getNextSibling() { return nextSibling; }
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getPreviousSibling()
	 */
	public XholonTreeNode getPreviousSibling() {
		if (isRootNode()) {
			return null; // this is the root node
		}
		XholonTreeNode node = getParentNode().getFirstChild();
		if (node == this) {
			return null; // this node is already the first sibling
		}
		XholonTreeNode leftNode = node;
		while (leftNode.getNextSibling() != null) {
			node = leftNode.getNextSibling();
			if (node == this) {
				return leftNode;
			}
			leftNode = node; // should never get here
		}
		return null;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setParentNode(org.primordion.xholon.base.TreeNode)
	 */
	public void setParentNode( XholonTreeNode treeNode )       { parent = treeNode; }
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setFirstChild(org.primordion.xholon.base.TreeNode)
	 */
	public void setFirstChild( XholonTreeNode treeNode )    { firstChild = treeNode; }
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setNextSibling(org.primordion.xholon.base.TreeNode)
	 */
	public void setNextSibling( XholonTreeNode treeNode ) { nextSibling = treeNode; }
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getRootNode()
	 */
	public XholonTreeNode getRootNode() { // get root node of this tree
		XholonTreeNode node = this;
		while (!node.isRootNode()) {
			node = node.getParentNode();
		}
		return node;
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#setParentChildLinks()
	 */
	public void setParentChildLinks( XholonTreeNode parent )
	{
		setParentNode( parent );
		parent.setFirstChild( this );
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#setParentSiblingLinks(org.primordion.xholon.base.TreeNode)
	 */
	public void setParentSiblingLinks( XholonTreeNode previousSibling )
	{
		setParentNode( previousSibling.getParentNode() ); // previousSibling already has parent
		previousSibling.setNextSibling( this );
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#removeChild()
	 */
	public void removeChild()
	{
		if (!isRootNode()) {
			XholonTreeNode lNode = getPreviousSibling();
			XholonTreeNode rNode = getNextSibling();
			if (lNode == null) { // this is the first (leftmost) sibling
				if (rNode == null) {
					getParentNode().setFirstChild(null);
				}
				else {
					getParentNode().setFirstChild(rNode); // nextSibling is new firstChild of parent
				}
			}
			else {
				if (rNode == null) {
					lNode.setNextSibling(null);
				}
				else {
					lNode.setNextSibling(rNode);
				}
			}
			setParentNode(null);
			setNextSibling(null);
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#insertAfter(org.primordion.xholon.base.IXholon)
	 */
	public void insertAfter(XholonTreeNode newLeftSibling)
	{
		if (newLeftSibling.hasNextSibling()) {
			parent = newLeftSibling.getParentNode();
			nextSibling = newLeftSibling.getNextSibling();
			newLeftSibling.setNextSibling(this);
		}
		else { // last sibling
			setParentSiblingLinks(newLeftSibling);
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#insertBefore(org.primordion.xholon.base.IXholon)
	 */
	public void insertBefore(XholonTreeNode newNextSibling)
	{
		parent = newNextSibling.getParentNode();
		if (parent.getFirstChild() == newNextSibling) {
			parent.setFirstChild(this);
		}
		else {
			XholonTreeNode lSibling = newNextSibling.getPreviousSibling();
			lSibling.setNextSibling(this);
		}
		setNextSibling(newNextSibling);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#insertFirstChild(org.primordion.xholon.base.IXholon)
	 */
	public void insertFirstChild(XholonTreeNode newParentNode)
	{
		if (newParentNode.getFirstChild() == null) {
			setParentChildLinks(newParentNode);
		}
		else {
			XholonTreeNode newSibling = newParentNode.getFirstChild();
			insertBefore(newSibling);
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#appendChild(org.primordion.xholon.base.IXholon)
	 */
	public void appendChild(XholonTreeNode newParentNode)
	{
		if (newParentNode.getFirstChild() == null) {
			setParentChildLinks(newParentNode);
		}
		else {
			setParentSiblingLinks(newParentNode.getLastChild());
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#swapNode(org.primordion.xholon.base.IXholon)
	 */
	public void swapNode(XholonTreeNode otherNode)
	{
		XholonTreeNode thisParent = parent;
		XholonTreeNode otherParent = otherNode.getParentNode();
		XholonTreeNode thisNextSibling = nextSibling;
		XholonTreeNode otherNextSibling = otherNode.getNextSibling();
		if (thisParent != otherParent) { // no change if both have same parent
			removeChild();
			otherNode.removeChild();
			// insert this node
			if (otherNextSibling == null) {
				appendChild(otherParent);
			}
			else {
				insertBefore(otherNextSibling);
			}
			// insert other node
			if (thisNextSibling == null) {
				otherNode.appendChild(thisParent);
			}
			else {
				otherNode.insertBefore(thisNextSibling);
			}
		}
	}
	
	//											standard tree utility functions
	
	/*
	 * @see org.primordion.xholon.base.IXholon#depth()
	 */
	public int depth()
	{
		if (isRootNode()) {
			return 0;
		}
		else {
			return 1 + getParentNode().depth();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#height()
	 */
	public int height()
	{
		int hL = 0;
		int hR = 0;
		if (isExternal()) {
			return 0;
		}
		else {
			if (hasChildNodes()) {
				hL = firstChild.height();
			}
			if (hasNextSibling()) {
				hR = nextSibling.height();
			}
			return hL > hR ? hL + 1 : hR + 1;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#treeSize()
	 */
	public int treeSize()
	{
		int lSize = 0;
		int rSize = 0;
		XholonTreeNode node = getFirstChild();
		if ( node != null ) {
			lSize = node.treeSize();
		}
		node = getNextSibling();
		if ( node != null ) {
			rSize = node.treeSize();
		}
		return ( 1 + lSize + rSize );
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#isRootNode()
	 */
	public boolean isRootNode()
	{
		return !(hasParentNode());
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#isExternal()
	 */
	public boolean isExternal()
	{
		return ( (firstChild == null) && (nextSibling == null) );
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#isInternal()
	 */
	public boolean isInternal()
	{
		return ( (firstChild != null) || (nextSibling != null) );
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#isLeaf()
	 */
	public boolean isLeaf()
	{
		return (firstChild == null);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getChildNodes(boolean)
	 */
	public List getChildNodes(boolean deep)
	{
		List v = new ArrayList();
		return getChildNodes(v, deep);
	}
	
	/**
	 * Helper function to recursively get all children.
	 * @param v List that gets filled with children.
	 * @param deep If true then return entire nested subtree, if false return only immediate children.
	 * @return List containing children.
	 */
	protected List getChildNodes( List v, boolean deep) {
		XholonTreeNode nextChild = getFirstChild();
		while (nextChild != null) {
			v.add(nextChild);
			if (deep) {
				((XholonTreeNode)nextChild).getChildNodes(v, deep);
			}
			nextChild = nextChild.getNextSibling();
		}
		return v;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getNthChild(int, boolean)
	 */
	public XholonTreeNode getNthChild(int n, boolean deep)
	{
		List v = getChildNodes(deep);
		if (v.size() > n) {
			return (XholonTreeNode)v.get(n);
		}
		else {
			return null;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#hasParentNode()
	 */
	public boolean hasParentNode()
	{
		if (parent == null) {
			return false;
		}
		else {
			return true;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#hasChildNodes()
	 */
	public boolean hasChildNodes()
	{
		return (firstChild != null);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#hasNextSibling()
	 */
	public boolean hasNextSibling()
	{
		return (nextSibling != null);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#hasChildOrSiblingNodes()
	 */
	public boolean hasChildOrSiblingNodes()
	{
		if (hasChildNodes()) {return true;}
		else {return hasNextSibling();}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getNumChildren(boolean)
	 */
	public int getNumChildren(boolean doFullSubtree)
	{
		// not very efficient
		return getChildNodes(doFullSubtree).size();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getLastChild()
	 */
	public XholonTreeNode getLastChild()
	{
		if (hasChildNodes()) {
			if (firstChild.hasNextSibling()) {
				return firstChild.getLastSibling();
			}
			else {
				return firstChild;
			}
		}
		return null;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getLastSibling()
	 */
	public XholonTreeNode getLastSibling()
	{
		XholonTreeNode node = this;
		while (node.hasNextSibling()) {
			node = node.getNextSibling();
		}
		if (node == this) {
			return null;
		}
		else {
			return node;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getFirstSibling()
	 */
	public XholonTreeNode getFirstSibling()
	{
		XholonTreeNode node = null;
		if (!isRootNode()) {
			node = parent.getFirstChild();
			if (node == this) {
				return null;
			}
			else {
				return node;
			}
		}
		else {
			return null;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getSiblings()
	 */
	public List getSiblings()
	{
		if (!isRootNode()) {
			List v = parent.getChildNodes(false);
			v.remove(this);
			return v;
		}
		else {
			return new ArrayList();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getNthSibling(int)
	 */
	public XholonTreeNode getNthSibling(int n)
	{
		List v = getSiblings();
		if (v.size() > n) {
			return (XholonTreeNode)v.get(n);
		}
		else {
			return null;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getNumSiblings()
	 */
	public int getNumSiblings()
	{
		// not very efficient
		return getSiblings().size();
	}
	
	/**
	 * Get the least common ancestor of this node and the specified other node.
	 * @param otherNode
	 * @return
	 */
	public XholonTreeNode getLeastCommonAncestor(XholonTreeNode otherNode)
	{
		if (otherNode == null) {return this;}
		if (otherNode == this) {return this;}
		//if (isRootNode()) {return this;}
		//if (otherNode.isRootNode()) {return otherNode;}
		XholonTreeNode thisNode = this;
		// 2 nodes cannot have a common parent unless they are at the same level (depth),
		// so start by adjusting the path with the greatest depth
		int thisDepth = depth();
		int otherDepth = otherNode.depth();
		while (thisDepth > otherDepth) {
			thisNode = thisNode.getParentNode();
			thisDepth--;
		}
		while (otherDepth > thisDepth) {
			otherNode = otherNode.getParentNode();
			otherDepth--;
		}
		// now compare ancestors in lock-step
		while (!thisNode.equals(otherNode)) {
			thisNode = thisNode.getParentNode();
			otherNode = otherNode.getParentNode();
		}
		return thisNode;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#print(java.lang.Object)
	 */
	public void print(Object obj) {
		System.out.print(obj);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#println(java.lang.Object)
	 */
	public void println(Object obj) {
		System.out.println(obj);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getName()
	 */
	public String getName()
	{
		return null;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setName(java.lang.String)
	 */
	public void setName(String name) {}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getVal()
	 */
	public double getVal()
	{ return 0.0; }
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setVal(double)
	 */
	public void setVal(double val)
	{}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#incVal(double)
	 */
	public void incVal(double incAmount)
	{}
	/*
	 * @see org.primordion.xholon.base.IXholon#incVal(int)
	 */
	public void incVal(int incAmount)
	{}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#decVal(double)
	 */
	public void decVal(double decAmount)
	{}
	/*
	 * @see org.primordion.xholon.base.IXholon#decVal(int)
	 */
	public void decVal(int decAmount)
	{}

	/*
	 * @see org.primordion.xholon.base.IXholon#getVal_boolean()
	 */
	public boolean getVal_boolean() {
		return false;
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#getVal_byte()
	 */
	public byte getVal_byte() {
		return 0;
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#getVal_char()
	 */
	public char getVal_char() {
		return '\u0000';
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#getVal_double()
	 */
	public double getVal_double() {
		return 0.0;
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#getVal_float()
	 */
	public float getVal_float() {
		return 0.0f;
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#getVal_int()
	 */
	public int getVal_int() {
		return 0;
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#getVal_long()
	 */
	public long getVal_long() {
		return 0L;
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#getVal_Object()
	 */
	public Object getVal_Object() {
		return null;
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#getVal_short()
	 */
	public short getVal_short() {
		return 0;
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#getVal_String()
	 */
	public String getVal_String() {
		return null;
	}

	/*
	 * @see org.primordion.xholon.base.IXholon#setVal(boolean)
	 */
	public void setVal(boolean val) {}

	/*
	 * @see org.primordion.xholon.base.IXholon#setVal(byte)
	 */
	public void setVal(byte val) {}

	/*
	 * @see org.primordion.xholon.base.IXholon#setVal(char)
	 */
	public void setVal(char val) {}

	/*
	 * @see org.primordion.xholon.base.IXholon#setVal(float)
	 */
	public void setVal(float val) {}

	/*
	 * @see org.primordion.xholon.base.IXholon#setVal(int)
	 */
	public void setVal(int val) {}

	/*
	 * @see org.primordion.xholon.base.IXholon#setVal(long)
	 */
	public void setVal(long val) {}

	/*
	 * @see org.primordion.xholon.base.IXholon#setVal(java.lang.Object)
	 */
	public void setVal(Object val) {}

	/*
	 * @see org.primordion.xholon.base.IXholon#setVal(short)
	 */
	public void setVal(short val) {}

	/*
	 * @see org.primordion.xholon.base.IXholon#setVal(java.lang.String)
	 */
	public void setVal(String val) {}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#preAct()
	 */
	public void preAct()
	{
		// execute recursively
		if (firstChild != null) {
			firstChild.preAct();
		}
		if (nextSibling != null) {
			nextSibling.preAct();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#act()
	 */
	public void act()
	{
		// execute recursively
		if (firstChild != null) {
			firstChild.act();
		}
		if (nextSibling != null) {
			nextSibling.act();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#postAct()
	 */
	public void postAct()
	{
		// execute recursively
		if (firstChild != null) {
			firstChild.postAct();
		}
		if (nextSibling != null) {
			nextSibling.postAct();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#cleanup()
	 */
	public void cleanup()
	{
		// execute recursively
		if (firstChild != null) {
			firstChild.cleanup();
		}
		if (nextSibling != null) {
			nextSibling.cleanup();
		}
	}
	
	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getName();
	}
}

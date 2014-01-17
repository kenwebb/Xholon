/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2008 Ken Webb
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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * A node in a tree that's arranged in a binary sorted order.
 * Instances of this class use the firstChild and nextSibling attributes in a different way from other Xholons.
 * used in:
src/org/primordion/xholon/io/Xholon2Svg.java:import org.primordion.tool.XholonSortedNode;
src/org/primordion/xholon/io/ef/other/Xholon2Umple.java:import org.primordion.tool.XholonSortedNode;
src/org/primordion/xholon/io/ef/other/Xholon2ManyEyes.java:import org.primordion.tool.XholonSortedNode;
src/org/primordion/xholon/io/ef/Xholon2Yuml.java:import org.primordion.tool.XholonSortedNode;
src/org/primordion/xholon/io/ef/Xholon2PlantUML.java
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.7.1 (Created on June 21, 2008)
 */
public class XholonSortedNode extends XholonTreeNode implements Collection {
	
	/** Each node has a value or contents, that determines the sort order. */
	private Object val = null;
	
	/** The number of identical objects that match the contents. */
	private int count = 1;
	
	/**
	 * Get the left child node.
	 * @return The left node.
	 */
	protected XholonSortedNode getLeft() {
		return (XholonSortedNode)getFirstChild();
	}
	
	/**
	 * Set the left child node, and its contents.
	 * @param o The intended contents of the left child node.
	 */
	protected void setLeft(Object o) {
		XholonSortedNode left = new XholonSortedNode();
		setFirstChild(left);
		left.setParentNode(this);
		//left.setName();
		left.setVal(o);
	}
	
	/**
	 * 
	 * @param left
	 */
	protected void setLeft(XholonSortedNode left) {
		setFirstChild(left);
	}
	
	/**
	 * Get the right child node.
	 * @return The right node.
	 */
	protected XholonSortedNode getRight() {
		return (XholonSortedNode)getNextSibling();
	}
	
	/**
	 * Set the right child node, and its contents.
	 * @param o The intended contents of the right child node.
	 */
	protected void setRight(Object o) {
		XholonSortedNode right = new XholonSortedNode();
		setNextSibling(right);
		right.setParentNode(this);
		//right.setName();
		right.setVal(o);
	}
	
	/**
	 * 
	 * @param right
	 */
	protected void setRight(XholonSortedNode right) {
		setNextSibling(right);
	}
	
	/*
	 * @see org.primordion.tool.XholonTreeNode#getVal_Object()
	 */
	public Object getVal_Object() {
		return val;
	}
	
	/*
	 * @see org.primordion.tool.XholonTreeNode#setVal(java.lang.Object)
	 */
	public void setVal(Object val) {
		this.val = val;
	}
	
	/**
	 * Get the count of objects of this kind.
	 * @return A count.
	 */
	public int getCount() {
		return count;
	}
	
	/**
	 * Set the count of objects of this kind.
	 * @param count A count.
	 */
	protected void setCount(int count) {
		this.count = count;
	}
	
	// disable various Xholon recursive methods
	public void configure() {}
	public void postConfigure() {}
	public void preAct() {}
	public void act() {}
	public void postAct() {}
	
	/*
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	public boolean add(Object o) {
		boolean rc = false;
		int cond = ((Comparable)val).compareTo(o);
		if (cond == 0) { // repeated object
			count++;
		}
		else if (cond < 0) { // this.val is less than o, so o goes into right subtree
			if (getRight() == null) {
				setRight(o);
			}
			else {
				rc = getRight().add(o);
			}
		}
		else { // this.val is greater than o, so o goes into left subtree
			if (getLeft() == null) {
				setLeft(o);
			}
			else {
				rc = getLeft().add(o);
			}
		}
		return rc;
	}
	
	/**
	 * Add an existing subTree.
	 * @param subTree
	 */
	protected boolean addSubTree(XholonSortedNode subTree) {
		Object o = subTree.getVal_Object();
		boolean rc = false;
		int cond = ((Comparable)val).compareTo(o);
		if (cond == 0) { // repeated object
			//count++;
		}
		else if (cond < 0) { // this.val is less than o, so subTree goes into right subtree
			if (getRight() == null) {
				setRight(subTree);
			}
			else {
				rc = getRight().addSubTree(subTree);
			}
		}
		else { // this.val is greater than o, so subTree goes into left subtree
			if (getLeft() == null) {
				setLeft(subTree);
			}
			else {
				rc = getLeft().addSubTree(subTree);
			}
		}
		return rc;
	}
	
	/*
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection c) {
		Iterator it = c.iterator();
		while (it.hasNext()) {
			add(it.next());
		}
		return true;
	}
	
	/*
	 * @see java.util.Collection#clear()
	 */
	public void clear() {
		remove();
	}
	
	/**
	 * Get the count of the number of items of this type.
	 * @param o
	 * @return
	 */
	public int getCount(Object o)
	{
		int cnt = 0;
		if (getLeft() != null) {
			cnt = getLeft().getCount(o);
			if (cnt > 0) {
				return cnt;
			}
		}
		if (getVal_Object() != null) {
			if (getVal_Object().equals(o)) {return getCount();}
		}
		if (getRight() != null) {
			cnt = getRight().getCount(o);
			if (cnt > 0) {
				return cnt;
			}
		}
		return cnt;
	}
	
	/*
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	public boolean contains(Object o) {
		if (getLeft() != null) {
			if (getLeft().contains(o)) {
				return true;
			}
		}
		if (getVal_Object().equals(o)) {return true;}
		if (getRight() != null) {
			if (getRight().contains(o)) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection c) {
		Iterator it = c.iterator();
		while (it.hasNext()) {
			Object node = it.next();
			if (!contains(node)) {
				return false;
			}
		}
		return true;
	}
	
	/*
	 * @see java.util.Collection#isEmpty()
	 */
	public boolean isEmpty() {
		return this.hasChildOrSiblingNodes();
	}
	
	/*
	 * @see java.util.Collection#iterator()
	 */
	public Iterator iterator() {
		List sortedListOfNodes = getSortedListOfNodes(new ArrayList());
		return sortedListOfNodes.iterator();
	}
	
	/**
	 * Get a sorted list of nodes, by traversing the subtree.
	 * @param ls
	 * @return
	 */
	public List getSortedListOfNodes(List ls) {
		if (getLeft() != null) {
			ls = getLeft().getSortedListOfNodes(ls);
		}
		ls.add(this);
		if (getRight() != null) {
			ls = getRight().getSortedListOfNodes(ls);
		}
		return ls;
	}
	
	/*
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	public boolean remove(Object o) {
		if (getLeft() != null) {
			if (getLeft().remove(o)) {
				return true;
			}
		}
		if (getVal_Object().equals(o)) {
			removeChild();
			return true;
		}
		if (getRight() != null) {
			if (getRight().remove(o)) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#removeChild()
	 */
	public void removeChild() {
		XholonSortedNode l = getLeft();
		XholonSortedNode r = getRight();
		if (l != null) {
			setLeft(l.getLeft());
			setRight(l.getRight());
			setVal(l.getVal_Object());
			setCount(l.getCount());
			if (r != null) {
				addSubTree(r);
			}
		}
		else {
			if (r != null) {
				setLeft(r.getLeft());
				setRight(r.getRight());
				setVal(r.getVal_Object());
				setCount(r.getCount());
			}
			else { // both l and r are null
				if (getParentNode() == null) {
					setVal(null);
					setCount(0);
				}
				else {
					getParentNode().setNextSibling(null);
				}
				setParentNode(null);
			}
		}
	}

	public boolean removeAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean retainAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/*
	 * @see java.util.Collection#size()
	 */
	public int size() {
		int size = 1;
		if (getLeft() != null) {
			size += getLeft().size();
		}
		if (getRight() != null) {
			size += getRight().size();
		}
		return size;
	}
	
	/*
	 * @see java.util.Collection#toArray()
	 */
	public Object[] toArray() {
		List ls = getSortedListOfNodes(new ArrayList());
		Object[] a  = new XholonSortedNode[ls.size()];
		for (int i = 0; i < ls.size(); i++) {
			a[i] = ls.get(i);
		}
		return a;
	}
	
	/*
	 * @see java.util.Collection#toArray(java.lang.Object[])
	 */
	public Object[] toArray(Object[] a) {
		List ls = getSortedListOfNodes(new ArrayList());
		int sz = ls.size();
		if (sz > a.length) {
			a = new XholonSortedNode[sz];
		}
		for (int i = 0; i < ls.size(); i++) {
			a[i] = ls.get(i);
		}
		return a;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#inOrderPrint(int)
	 */
	public void inOrderPrint( int level )
	{
		if (getLeft() != null) {
			getLeft().inOrderPrint( level + 1 );
		}
		println(getVal_Object() + " " + getCount());
		if (getRight() != null) {
			getRight().inOrderPrint( level + 1 );
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toString()
	 */
	public String toString() {
		return "" + getVal_Object() + " " + getCount();
	}
	
	/**
	 * Testing.
	 * @param args
	 */
	/*public static void main(String args[]) {
		String[] data = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "two"};
		//Integer[] data = {new Integer(17), new Integer(33), new Integer(44), new Integer(11),
		//		new Integer(11), new Integer(5)};
		XholonSortedNode root = new XholonSortedNode();
		//root.setId(0);
		root.setVal((Object)data[0]);
		for (int i = 1; i < data.length; i++) {
			root.add(data[i]);
		}
		System.out.println("size:" + root.size());
		System.out.println("\nView nodes using inOrderPrint():");
		root.inOrderPrint(XholonTreeNode.DEFAULT_LEVEL);
		
		System.out.println("\nView nodes using iterator:");
		Iterator it = root.iterator();
		while (it.hasNext()) {
			XholonSortedNode node = (XholonSortedNode)it.next();
			System.out.println(node.getVal_Object() + " " + node.getCount());
		}
		System.out.println();
		
		System.out.println(root.isEmpty());
		System.out.println(root.contains("three"));
		System.out.println(root.contains("zero"));
		
		Collection c = new ArrayList();
		for (int i = 0; i < data.length; i++) {
			c.add("X" + data[i]);
		}
		root.addAll(c);
		
		System.out.println("\nView nodes using array:");
		XholonSortedNode[] a = (XholonSortedNode[])root.toArray();
		for (int i = 0; i < a.length; i++) {
			XholonSortedNode node = a[i];
			System.out.println(node.getVal_Object() + " " + node.getCount());
		}
		System.out.println(root.containsAll(c));
		c.add("junk");
		System.out.println(root.containsAll(c));
		
		// remove
		System.out.println("\nView nodes after removing a node:");
		root.remove("four");
		root.inOrderPrint(XholonTreeNode.DEFAULT_LEVEL);
	}*/
}

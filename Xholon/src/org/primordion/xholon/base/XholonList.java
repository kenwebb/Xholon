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

package org.primordion.xholon.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.primordion.xholon.util.ClassHelper;

/**
 * Treat a collection of siblings as a list.
 * This class implements the Java List interface (java.util.List).
 * In this class, each xholon in the tree is considered as owning a List,
 * that consists of its immediate children.
 * All objects in the list are instances of IXholon.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.7.1 (Created on June 2, 2008)
 */
public class XholonList extends Xholon implements List, Serializable {
	private static final long serialVersionUID = 3194016192067889323L;
	
	/** Number of elements in the list. */
	int size = 0;
	
	/**
	 * Optional role name.
	 * Can be used to distinguish this XholonList from others in the same container. */
	String roleName = null;
	
	/**
	 * The object added to the list must implement IXholon.
	 * Otherwise this method will throw a ClassCastException.
	 * If the object is null, this method will throw a NullPointerException.
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean add(Object o) throws ClassCastException, NullPointerException {
		if (o == null) {throw new NullPointerException();}
		//if (!IXholon.class.isAssignableFrom(o.getClass())) {throw new ClassCastException();}
		if (!ClassHelper.isAssignableFrom(Xholon.class, o.getClass())) {throw new ClassCastException();}
		((IXholon)o).appendChild(this);
		size++;
		return true;
	}
	
	/*
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	public void add(int index, Object element) throws NullPointerException, IndexOutOfBoundsException {
		if (element == null) {throw new NullPointerException ();}
		if (index < 0 || index > size()) {throw new IndexOutOfBoundsException();}
		IXholon node = getFirstChild();
		while (node != null) {
			if (index == 0) {break;}
			index--;
			node = node.getNextSibling();
		}
		if (node == null) { // the list is currently empty
			((IXholon)element).appendChild(this);
		}
		else {
			((IXholon)element).insertBefore(node);
		}
		size++;
	}
	
	/**
	 * All elements in the Collection must implement IXholon.
	 * None of the elements in the Collection can be null.
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection c) {
		Iterator it = c.iterator();
		while (it.hasNext()) {
			add(it.next());
		}
		return true;
	}
	
	/*
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	public boolean addAll(int index, Collection c) {
		if (index < 0 || index >= size()) {throw new IndexOutOfBoundsException();}
		IXholon node = getFirstChild();
		while (node != null) {
			if (index == 0) {break;}
			index--;
			node = node.getNextSibling();
		}
		Iterator it = c.iterator();
		if (node == null) { // the list is currently empty
			while (it.hasNext()) {
				((IXholon)it.next()).appendChild(this);
				size++;
			}
		}
		else {
			while (it.hasNext()) {
				((IXholon)it.next()).insertBefore(node);
				size++;
			}
		}
		return true;
	}
	
	/*
	 * @see java.util.List#clear()
	 */
	public void clear() {
		IXholon node = getFirstChild();
		while (node != null) {
			node.removeChild();
			node = getFirstChild();
		}
		size = 0;
	}
	
	/*
	 * @see java.util.List#contains(java.lang.Object)
	 */
	public boolean contains(Object o) throws ClassCastException, NullPointerException {
		if (o == null) {throw new NullPointerException();}
		//if (!IXholon.class.isAssignableFrom(o.getClass())) {throw new ClassCastException();}
		if (!ClassHelper.isAssignableFrom(Xholon.class, o.getClass())) {throw new ClassCastException();}
		boolean found = false;
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.equals((IXholon)o)) {
				found = true;
			}
			node = node.getNextSibling();
		}
		return found;
	}
	
	/*
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection c) throws ClassCastException, NullPointerException {
		if (c == null) {throw new NullPointerException();}
		Iterator it = c.iterator();
		while (it.hasNext()) {
			IXholon node = (IXholon)it.next();
			if (!contains(node)) {
				return false;
			}
		}
		return true;
	}
	
	/*
	 * @see java.util.List#get(int)
	 */
	public Object get(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size()) {throw new IndexOutOfBoundsException();}
		IXholon node = getFirstChild();
		while (node != null) {
			if (index == 0) {break;}
			index--;
			node = node.getNextSibling();
		}
		return node;
	}
	
	/*
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	public int indexOf(Object o) {
		int index = 0;
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.equals((IXholon)o)) {
				break;
			}
			index++;
			node = node.getNextSibling();
		}
		if (node == null) {
			return -1;
		}
		else {
			return index;
		}
	}
	
	/*
	 * @see java.util.List#isEmpty()
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/*
	 * @see java.util.List#iterator()
	 */
	public Iterator iterator() {
		return new Itr();
	}
	
	/*
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	public int lastIndexOf(Object o) {
		int index = 0;
		int lastIndex = 0;
		IXholon node = getFirstChild();
		IXholon lastNode = node;
		while (node != null) {
			if (node.equals((IXholon)o)) {
				lastIndex = index;
				lastNode = node;
			}
			index++;
			node = node.getNextSibling();
		}
		if (lastNode == null) {
			return -1;
		}
		else {
			return lastIndex;
		}
	}
	
	/*
	 * @see java.util.List#listIterator()
	 */
	public ListIterator listIterator() {
		// return a list iterator backed by a Vector
		// TODO should be backed by a XholonList
		return getChildNodes(false).listIterator();
	}
	
	/*
	 * @see java.util.List#listIterator(int)
	 */
	public ListIterator listIterator(int index) {
		// return a list iterator backed by a Vector
		// TODO should be backed by a XholonList
		return getChildNodes(false).listIterator(index);
	}
	
	/*
	 * @see java.util.List#remove(int)
	 */
	public Object remove(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size()) {throw new IndexOutOfBoundsException();}
		IXholon node = ((IXholon)get(index));
		node.removeChild();
		size--;
		return node;
	}
	
	/*
	 * @see java.util.List#remove(java.lang.Object)
	 */
	public boolean remove(Object o) throws ClassCastException, NullPointerException {
		if (o == null) {throw new NullPointerException();}
		//if (!IXholon.class.isAssignableFrom(o.getClass())) {throw new ClassCastException();}
		if (!ClassHelper.isAssignableFrom(Xholon.class, o.getClass())) {throw new ClassCastException();}
		if (contains(o)) {
			((IXholon)o).removeChild();
			size--;
			return true;
		}
		else {
			return false;
		}
	}
	
	/*
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection c) throws ClassCastException, NullPointerException {
		if (c == null) {throw new NullPointerException();}
		boolean rc = false;
		Iterator it = c.iterator();
		while (it.hasNext()) {
			if (remove(it.next())) {
				rc = true;
			}
		}
		return rc;
	}
	
	/*
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection c) throws ClassCastException, NullPointerException {
		if (c == null) {throw new NullPointerException();}
		IXholon node = getFirstChild();
		while (node != null) {
			IXholon nextNode = node.getNextSibling();
			if (!c.contains(node)) {
				((IXholon)node).removeChild();
				size--;
			}
			node = nextNode;
		}
		return false;
	}
	
	/*
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	public Object set(int index, Object element) throws ClassCastException, NullPointerException, IndexOutOfBoundsException {
		if (element == null) {throw new NullPointerException();}
		//if (!IXholon.class.isAssignableFrom(element.getClass())) {throw new ClassCastException();}
		if (!ClassHelper.isAssignableFrom(Xholon.class, element.getClass())) {throw new ClassCastException();}
		if (index < 0 || index >= size()) {throw new IndexOutOfBoundsException();}
		IXholon nodeToBeReplaced = (IXholon)get(index);
		((IXholon)element).insertAfter(nodeToBeReplaced);
		nodeToBeReplaced.removeChild();
		return nodeToBeReplaced;
	}
	
	/*
	 * @see java.util.List#size()
	 */
	public int size() {
		// there's no easy way to keep track of items that may be inserted using the IXholon API
		// so recalculate the size whenever someone asks for it
		calculateSize();
		return size;
	}
	
	/*
	 * @see java.util.List#subList(int, int)
	 */
	public List subList(int fromIndex, int toIndex) {
		// return a Vector
		// TODO should return a XholonList
		return getChildNodes(false).subList(fromIndex, toIndex);
	}
	
	/*
	 * @see java.util.List#toArray()
	 */
	public Object[] toArray() {
		Object[] a  = new IXholon[size()];
		IXholon node = getFirstChild();
		int index = 0;
		while (node != null) {
			a[index++] = node;
			node = node.getNextSibling();
		}
		return a;
	}
	
	/*
	 * @see java.util.List#toArray(java.lang.Object[])
	 */
	public Object[] toArray(Object[] a) {
		int sz = size();
		if (sz > a.length) {
			a = new IXholon[sz];
		}
		IXholon node = getFirstChild();
		int index = 0;
		while (node != null) {
			a[index++] = node;
			node = node.getNextSibling();
		}
		return a;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#configure()
	 */
	public void configure() {
		calculateSize();
		super.configure();
	}
	
	/**
	 * Calculate or recalculate the size of the list.
	 */
	protected void calculateSize() {
		size = 0;
		IXholon node = getFirstChild();
		while (node != null) {
			size++;
			node = node.getNextSibling();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getRoleName()
	 */
	public String getRoleName() {
		return roleName;
	}

	/*
	 * @see org.primordion.xholon.base.Xholon#setRoleName(java.lang.String)
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/*
	 * @see org.primordion.xholon.base.Xholon#toString()
	 */
	public String toString() {
		return getName() + " size:" + size();
	}
	
	/**
	 * Implementation of the Iterator interface.
	 */
	private class Itr implements Iterator {
		
		/**
		 * Index of element to be returned by subsequent call to next.
		 */
		private int cursor = 0;
		
		/**
		 * The last node returned by next().
		 */
		private IXholon node = null;
		
		/*
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
		    return cursor != size();
		}
		
		/*
		 * @see java.util.Iterator#next()
		 */
		public Object next() {
		   	if (node == null && hasNext()) { // first call to next()
		   		node = getFirstChild();
		   	}
		   	else { // subsequent calls to next()
		   		node = node.getNextSibling();
		   	}
		   	if (node == null) {
		   		throw new NoSuchElementException();
		   	}
		   	else {
		   		cursor++;
		   		return node;
		   	}
		}
		
		/*
		 * @see java.util.Iterator#remove()
		 */
		public void remove() {
			if (node != null) {
				if (XholonList.this.remove(node)) {
					node = null;
					cursor--;
				}
			}
			//throw new UnsupportedOperationException();
		}
	}
	
	/**
	 * <p>Save the state of the <tt>XholonList</tt> instance to a stream (serialize it).</p>
	 * <p>Save all the xholon children in order.
	 * All of the children must themselves be Serializable.</p>
	 * 
	 */
	/* GWT
	private void writeObject(java.io.ObjectOutputStream oos)
		throws java.io.IOException {
		// Write out size
		oos.defaultWriteObject();
		// Write out all child elements in the proper order.
		IXholon node = getFirstChild();
		while (node != null) {
			oos.writeObject(node);
			node = node.getNextSibling();
		}
	}*/

	/**
	 * <p>Reconstitute the <tt>XholonList</tt> instance from a stream (deserialize it).</p>
	 */
	/* GWT
	private void readObject(java.io.ObjectInputStream ois)
		throws java.io.IOException, ClassNotFoundException {
		// Read in size
		ois.defaultReadObject();
		// Read in all child elements in the proper order.
		int sizeRead = size; // the number of elements that are supposed to be in the list
		size = 0; // the actual current size of the list is initially zero
		for (int i = 0; i < sizeRead; i++) {
			IXholon node = (IXholon)ois.readObject();
			println(node);
			this.add(node);
		}
	}*/
	
}

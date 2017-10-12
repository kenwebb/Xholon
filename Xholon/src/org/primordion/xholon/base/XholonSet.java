/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2009 Ken Webb
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
import java.util.Set;
import java.util.NoSuchElementException;

import org.primordion.xholon.util.ClassHelper;

/**
 * Treat a collection of siblings as a set.
 * This class implements the Java Set interface (java.util.Set).
 * All objects in the set are instances of IXholon.
 * 
 * From Oracle documentation:
 * A collection that contains no duplicate elements. More formally, sets contain no pair of elements e1 and e2 such that e1.equals(e2), and at most one null element.
 * As implied by its name, this interface models the mathematical set abstraction.
 * The additional stipulation on constructors is, not surprisingly, that all constructors must create a set that contains no duplicate elements (as defined above).
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://docs.oracle.com/javase/8/docs/api/java/util/Set.html">Oracle Java Set doc</a>
 * @since 0.8 (Created on August 28, 2009)
 */
public class XholonSet extends Xholon implements Set, Serializable {
	private static final long serialVersionUID = -2452518802587122786L;

	/** Number of elements in the list. */
	int size = 0;
	
	/**
	 * Optional role name.
	 * Can be used to distinguish this XholonSet from others in the same container. */
	String roleName = null;
	
	@Override
	public boolean add(Object o) throws ClassCastException, NullPointerException {
		if (o == null) {throw new NullPointerException();}
		if (!ClassHelper.isAssignableFrom(Xholon.class, o.getClass())) {throw new ClassCastException();}
		if (this.contains(o)) {return false;}
		((IXholon)o).appendChild(this);
		size++;
		return true;
	}

	@Override
	public boolean addAll(Collection c) {
		Iterator it = c.iterator();
		while (it.hasNext()) {
			add(it.next());
		}
		return true;
	}

	@Override
	public void clear() {
		IXholon node = getFirstChild();
		while (node != null) {
			node.removeChild();
			node = getFirstChild();
		}
		size = 0;
	}

	@Override
	public boolean contains(Object o) throws ClassCastException, NullPointerException {
		if (o == null) {throw new NullPointerException();}
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

	@Override
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

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator iterator() {
		return new Itr();
	}

	@Override
	public boolean remove(Object o) throws ClassCastException, NullPointerException {
		if (o == null) {throw new NullPointerException();}
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

	@Override
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

	@Override
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

	@Override
	public int size() {
		// there's no easy way to keep track of items that may be inserted using the IXholon API
		// so recalculate the size whenever someone asks for it
		calculateSize();
		return size;
	}

	@Override
	public Object[] toArray() {
		Object[] a = new IXholon[size()];
		IXholon node = getFirstChild();
		int index = 0;
		while (node != null) {
			a[index++] = node;
			node = node.getNextSibling();
		}
		return a;
	}

	@Override
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

	@Override
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
	
	@Override
	public String getRoleName() {
		return roleName;
	}

	@Override
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	@Override
	public Object getVal_Object() {
		return this.toArray();
	}

	@Override
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
		
		@Override
		public boolean hasNext() {
			return cursor != size();
		}
		
		@Override
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
		
		@Override
		public void remove() {
			if (node != null) {
				if (XholonSet.this.remove(node)) {
					node = null;
					cursor--;
				}
			}
		}
	}

}

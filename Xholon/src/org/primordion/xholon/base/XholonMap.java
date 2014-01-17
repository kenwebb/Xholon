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
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Treat a collection of IXholon siblings as a map.
 * This class implements the Java Map interface (java.util.Map).
 * All objects in the map are instances of IXholon.
 * A Map key is stored as a xholon roleName.
 * A map value is stored as a xholon val.
 * Only String keys are allowed.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on August 27, 2009)
 */
public class XholonMap extends Xholon implements Map, Serializable {
	private static final long serialVersionUID = -1200601818204244499L;
	
	private String roleName = null;
	
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
	
	/**
	 * This is for use by the XholonJsApi.
	 * 
	 * example (where places is an instance of XholonMap):
	 *   var placeArr = places.obj();
	 *   var coords = placeArr["Bree"].split(","); // "123,456"
	 *   var x = coords[0];
	 *   var y = coords[1];
	 */
	public Object getVal_Object() {
	  return mapAsJso(this, IXholon.GETNAME_ROLENAME_OR_CLASSNAME);
	}
	
	protected native Object mapAsJso(IXholon map, String nameTemplate) /*-{
	  var arr = [];
	  var node = map.first();
	  while (node) {
	    var key = node.name(nameTemplate);
	    var value = node.obj();
	    arr[key] = value;
	    node = node.next();
	  }
	  return arr;
	}-*/;

	/** Number of elements in the list. */
	transient int size = 0;
	
	/*
	 * @see java.util.Map#clear()
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
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	public boolean containsKey(Object key) {
		if (key == null) {return false;}
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getRoleName().equals((String)key)) {
				return true;
			}
			node = node.getNextSibling();
		}
		return false;
	}

	/*
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	public boolean containsValue(Object value) {
		if (value == null) {return false;}
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getVal_Object().equals((String)value)) {
				return true;
			}
			node = node.getNextSibling();
		}
		return false;
	}

	/*
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public Object get(Object key) {
		if (key == null) {return null;}
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getRoleName().equals((String)key)) {
				return node.getVal_Object();
			}
			node = node.getNextSibling();
		}
		return null;
	}
	
	/**
	 * Get the child node that matches the key.
	 * @param key A search key.
	 * @return A Xholon node, or null.
	 */
	protected IXholon getNode(String key) {
		if (key == null) {return null;}
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getRoleName().equals(key)) {
				return node;
			}
			node = node.getNextSibling();
		}
		return null;
	}


	/*
	 * @see java.util.Map#isEmpty()
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/*
	 * @see java.util.Map#keySet()
	 */
	public Set keySet() {
		return null;
	}
	
	/*
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public Object put(Object key, Object value) {
		Object existingValue = null;
		IXholon existingNode = getNode((String)key);
		if (existingNode == null) {
			// there is no existing node that matches the key, so create a new one
			// TODO create an Attribute_??? node that depends on the type of the value,
			//      or just create an Attribute_Object node
			String xhClassName = "Attribute_Object";
			IXholon node = appendChild(xhClassName, (String)key); //, implName);
			node.setVal(value);
			size++;
		}
		else {
			// there is an existing node that matches the key, so modify it
			existingValue = existingNode.getVal_Object();
			existingNode.setVal(value);
		}
		return existingValue;
	}

	/*
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	public void putAll(Map m) {
		Iterator it = m.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
            put(e.getKey(), e.getValue());
		}
	}

	/*
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	public Object remove(Object key) {
		return null;
	}

	/*
	 * @see java.util.Map#size()
	 */
	public int size() {
		// there's no easy way to keep track of items that may be inserted using the IXholon API
		// so recalculate the size whenever someone asks for it
		calculateSize();
		return size;
	}

	/*
	 * @see java.util.Map#values()
	 */
	public Collection values() {
		return null;
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
	 * @see org.primordion.xholon.base.Xholon#toString()
	 */
	public String toString() {
		return getName() + " size:" + size();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#appendChild(java.lang.String, java.lang.String)
	 */
	public IXholon appendChild(String xhClassName, String roleName) {
		IXholon node = super.appendChild(xhClassName, roleName);
		if (node != null) {
			size++;
		}
		return node;
	}

	/*
	 * @see org.primordion.xholon.base.Xholon#appendChild(java.lang.String, java.lang.String, java.lang.String)
	 */
	public IXholon appendChild(String xhClassName, String roleName, String implName) {
		IXholon node = super.appendChild(xhClassName, roleName, implName);
		if (node != null) {
			size++;
		}
		return node;
	}
	
	// iterators
	
    /**
     * TreeMap Iterator.
     */
    private class EntryIterator implements Iterator {
        private IXholon lastReturned = null;
        IXholon next;

        EntryIterator() {
            next = getFirstChild();
        }

        public boolean hasNext() {
            return next != null;
        }

        final Entry nextEntry() {
            if (next == null)
                throw new NoSuchElementException();
            lastReturned = next;
            next = next.getNextSibling();
            return new Entry(lastReturned);
        }

        public Object next() {
            return nextEntry();
        }

        public void remove() {
        	/* TODO
            if (lastReturned == null)
                throw new IllegalStateException();
            if (lastReturned.left != null && lastReturned.right != null) 
                next = lastReturned; 
            deleteEntry(lastReturned);
            lastReturned = null;
            */
        }
    }

	
	// entrySet
	
	private transient volatile Set entrySet = null;

	/*
	 * @see java.util.Map#entrySet()
	 */
	public Set entrySet() {
        if (entrySet == null) {
            entrySet = new AbstractSet() {
                public Iterator iterator() {
                    return new EntryIterator();
                }

                public boolean contains(Object o) {
                    if (!(o instanceof Map.Entry))
                        return false;
                    Map.Entry entry = (Map.Entry)o;
                    Object value = entry.getValue();
                    Entry p = getEntry(entry.getKey());
                    return p != null && valEquals(p.getValue(), value);
                }

                public boolean remove(Object o) {
                    if (!(o instanceof Map.Entry))
                        return false;
                    Map.Entry entry = (Map.Entry)o;
                    Object value = entry.getValue();
                    Entry p = getEntry(entry.getKey());
                    if (p != null && valEquals(p.getValue(), value)) {
                        deleteEntry(p);
                        return true;
                    }
                    return false;
                }

                public int size() {
                    return XholonMap.this.size();
                }

                public void clear() {
                    XholonMap.this.clear();
                }
            };
        }
        return entrySet;
    }
    
	/**
     * Returns this map's entry for the given key, or <tt>null</tt> if the map
     * does not contain an entry for the key.
     *
     * @return this map's entry for the given key, or <tt>null</tt> if the map
     *                does not contain an entry for the key.
     * @throws ClassCastException if the key cannot be compared with the keys
     *                  currently in the map.
     * @throws NullPointerException key is <tt>null</tt> and this map uses
     *                  natural order, or its comparator does not tolerate *
     *                  <tt>null</tt> keys.
     */
	private Entry getEntry(Object key) {
		if (key == null) {return null;}
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getRoleName().equals((String)key)) {
				return new Entry(node);
			}
			node = node.getNextSibling();
		}
		return null;
	}
	
	private void deleteEntry(Entry p) {
		// TODO ?
	}
	
	/**
     * Test two values  for equality.  Differs from o1.equals(o2) only in
     * that it copes with with <tt>null</tt> o1 properly.
     */
    private static boolean valEquals(Object o1, Object o2) {
        return (o1==null ? o2==null : o1.equals(o2));
    }
    
    /**
     * Entry class
     */
    static class Entry implements Map.Entry {
        Object key;
        Object value;
        
        /**
         * Make a new cell with given key, and value.
         */
        Entry(Object key, Object value) {
            this.key = key;
            this.value = value;
        }
        
        Entry(IXholon node) {
        	key = node.getRoleName();
        	value = node.getVal_Object();
        }

        /**
         * Returns the key.
         *
         * @return the key.
         */
        public Object getKey() { 
            return key; 
        }

        /**
         * Returns the value associated with the key.
         *
         * @return the value associated with the key.
         */
        public Object getValue() {
            return value;
        }

        /**
         * Replaces the value currently associated with the key with the given
         * value.
         *
         * @return the value associated with the key before this method was
         *           called.
         */
        public Object setValue(Object value) {
            Object oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry e = (Map.Entry)o;

            return valEquals(key,e.getKey()) && valEquals(value,e.getValue());
        }

        public int hashCode() {
            int keyHash = (key==null ? 0 : key.hashCode());
            int valueHash = (value==null ? 0 : value.hashCode());
            return keyHash ^ valueHash;
        }

        public String toString() {
            return key + "=" + value;
        }
    }
    
}

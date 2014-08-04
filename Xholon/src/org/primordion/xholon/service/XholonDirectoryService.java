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

package org.primordion.xholon.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.primordion.xholon.base.IXholon;

/**
 * The Xholon Directory Service can find an individual Object or IXholon instance given a unique key.
 * It's mostly intended to be used as a global object, for example:
 * <pre>IXholon ann = XholonDirectoryService.instance().get(Annotation.makeUniqueKey(this));</pre>
 * Or:
 * <pre>IXholon ann = ((XholonDirectoryService)getService(IXholonService.XHSRV_XHOLON_DIRECTORY))
			.get(Annotation.makeUniqueKey(this));</pre>
 * A typical use is storing and accessing a Xholon Annotation.
 * <p>TODO Possibly this service should be called XholonAttributeService ?</p>
 * <p>TODO Because this is a global object,
 * the entries in the xholonMap will persist even if a new application is run.
 * The Map needs to be emptied when a new application is loaded.
 * However, new values will just replace existing ones, so there's not much danger.</p>
 * <p>TODO There should be at least one action that would appear in XholonGui menu:
 * Show All Entries
 * </p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on September 3, 2009)
 */
public class XholonDirectoryService extends AbstractXholonService {
	private static final long serialVersionUID = 664636371334470681L;

	/**
	 * Contains String keys that can be used to get Object or IXholon values.
	 */
	private transient Map<String, Object> xholonMap = new HashMap<String, Object>();
	
	/**
	 * Get a Object or IXholon instance that matches a String key.
	 * @see java.util.Map
	 * @param key A unique non-null key.
	 * @return An instance of Object or IXholon, or null if none was found.
	 */
	public Object get(String key) {
		if (key == null) {return null;}
		return xholonMap.get(key);
	}
	
	/**
	 * Store a Object or IXholon instance for later retreival using a String key.
	 * @see java.util.Map
	 * @param key A unique non-null key.
	 * @param value A non-null instance of Object or IXholon.
	 */
	public void put(String key, Object value) {
		if (key == null) {return;}
		if (value == null) {return;}
		xholonMap.put(key, value);
	}
	
	/**
	 * Remove the Object or IXholon instance that matches a String key.
	 * @param key A unique non-null key.
	 * @return The Object or IXholon that has been removed, or null.
	 */
	public Object remove(String key) {
		if (key == null) {return null;}
		return xholonMap.remove(key);
	}
	
	/**
	 * Check if the service contains a Object or IXholon instance that matches a String key.
	 * @param key A unique non-null key.
	 * @return true or false
	 */
	public boolean containsKey(String key) {
		if (key == null) {return false;}
		return xholonMap.containsKey(key);
	}
	
	/**
	 * Remove all entries.
	 */
	public void clear() {
		xholonMap.clear();
	}
	
	/**
	 * Get the singleton instance of the Xholon Directory Service.
	 * @see org.primordion.xholon.service.AbstractXholonService#getService(java.lang.String)
	 */
	public IXholon getService(String serviceName)
	{
		if (serviceName.equals(getXhcName())) {
			return this;
		}
		else {
			return null;
		}
	}
	
	// actions
	private static final String showAllEntries = "Show All Entries";
	
	/** action list */
	private String[] actions = {
			showAllEntries};
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getActionList()
	 */
	public String[] getActionList()
	{
		return actions;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setActionList(java.lang.String[])
	 */
	public void setActionList(String[] actionList)
	{
		actions = actionList;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#doAction(java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	public void doAction(String action)
	{
		if (action == null) {return;}
		if (action.equals(showAllEntries)) {
			Iterator it = xholonMap.entrySet().iterator();
			while (it.hasNext()) {
				Entry entry = (Entry)it.next();
				this.println(entry.getKey() + "=" + entry.getValue());
			}
		}
	}
	
	/**
	 * Make and return a unique key for a Object or IXholon node.
	 * @param node An Object or IXholon instance.
	 * @param name The name of an attribute.
	 * @return A unique key, or null.
	 */
	public static String makeUniqueKey(Object node, String name)
	{
		if (node == null) {return null;}
		if (name == null) {return null;}
		return new StringBuffer()
			.append(node.hashCode())
			.append("_")
			.append(node.getClass().hashCode())
			.append("_")
			.append(name)
			.toString();
	}
	
}

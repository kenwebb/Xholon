/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2006, 2007, 2008 Ken Webb
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

/**
 * A port interface is either a provided or a required interface for one or more ports.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.4 (Created on August 21, 2006)
 */
public class PortInterface implements IPortInterface {

	/**
	 * The signal(s) allowed in a provided or required interface.
	 */
	private int[] nterface = null;
	
	/**
	 * The names of the signals, in the same quantity and order as in the nterface array.
	 */
	private String[] nterfaceNames = null;
	
	/*
	 * @see org.primordion.xholon.base.IPortInterface#getInterface()
	 */
	public int[] getInterface() {
		return nterface;
	}

	/*
	 * @see org.primordion.xholon.base.IPortInterface#setInterface(int[])
	 */
	public void setInterface(int[] nterface) {
		this.nterface = nterface;
	}

	/*
	 * @see org.primordion.xholon.base.IPortInterface#getInterfaceNames()
	 */
	public String[] getInterfaceNames() {
		return nterfaceNames;
	}

	/*
	 * @see org.primordion.xholon.base.IPortInterface#setInterfaceNames(java.lang.String[])
	 */
	public void setInterfaceNames(String[] nterfaceNames) {
		this.nterfaceNames = nterfaceNames;
	}

	/*
	 * @see org.primordion.xholon.base.IPortInterface#getSignalId(java.lang.String)
	 */
	public int getSignalId(String name) {
		if ((nterface == null) || (nterfaceNames == null)) {return SIGNAL_ID_NOT_FOUND;}
		if (nterface.length != nterfaceNames.length) {return SIGNAL_ID_NOT_FOUND;}
		for (int i = 0; i < nterfaceNames.length; i++) {
			if (nterfaceNames[i].equals(name)) {
				return nterface[i];
			}
		}
		return SIGNAL_ID_NOT_FOUND;
	}

	/*
	 * @see org.primordion.xholon.base.IPortInterface#getSignalName(int)
	 */
	public String getSignalName(int id) {
		if (nterface == null) {
			return Integer.toString(id);
		}
		for (int i = 0; i < nterface.length; i++) {
			if (nterface[i] == id) {
				if (nterfaceNames != null && nterfaceNames.length > i) {
					return nterfaceNames[i];
				}
				else {
					return Integer.toString(id);
				}
			}
		}
		return Integer.toString(id);
	}
	
	/*
	 * @see org.primordion.xholon.base.IPortInterface#getSize()
	 */
	public int getSize() {
		if (nterface == null) {
			return 0;
		}
		return nterface.length;
	}
}

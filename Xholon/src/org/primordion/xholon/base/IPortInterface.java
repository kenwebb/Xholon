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
 * <p>A provided interface is the set of behavioral features that the owning xholon provides
 * to its environment through a specific port.
 * In practical terms, on a non-conjugated port, these are the signals that are allowed
 * in incoming messages received at the port.
 * On a conjugated port, these are the signals that are allowed in outoing messages.</p>
 * <p>A required interface is the set of services that the owning xholon expects
 * from its environment through a specific port.
 * In practical terms, on a non-conjugated port, these are the signals that are allowed in outgoing messages
 * sent from the port.
 * On a conjugated port, these are the signals that are allowed in incoming messages.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.4 (Created on August 21, 2006)
 *
 */
public interface IPortInterface {

	// constants
	public static final int    SIGNAL_ID_NOT_FOUND   = 0;
	public static final String SIGNAL_NAME_NOT_FOUND = null;
	
	/**
	 * Get the provided or required interface of this port.
	 * @return An array of zero or more signal IDs.
	 */
	public abstract int[] getInterface();
	
	/**
	 * Set the provided or required interface of this port.
	 * @param nterface An array of zero or more signal IDs.
	 */
	public abstract void setInterface(int[] nterface);
	
	/**
	 * Get the names of the signals in the provided or required interface of this port.
	 * The array is orderd corresponding to the order of the signal IDs obtained by calling getInterface().
	 * @return An array of zero or more signal names.
	 */
	public abstract String[] getInterfaceNames();
	
	/**
	 * Set the names of the signals in the provided or required interface of this port.
	 * @param nterfaceNames An array of zero or more signal names.
	 * These should be in the same order as the corresponding signal IDs used in setInterface().
	 */
	public abstract void setInterfaceNames(String[] nterfaceNames);
	
	/**
	 * Get the name of an interface signal, given its ID.
	 * @param id A signal ID.
	 * @return The name corresponding to that ID, or a string version of the ID if no name is available,
	 * or SIGNAL_NAME_NOT_FOUND if it's an invalid ID.
	 */
	public abstract String getSignalName(int id);
	
	/**
	 * Get the ID of an interface signal, given its name.
	 * @param name The name of a signal.
	 * @return The ID corresponding to that signal, or SIGNAL_ID_NOT_FOUND if no ID is available.
	 */
	public abstract int getSignalId(String name);
	
	/**
	 * Get number of signals in this provided or required interface.
	 * @return The size of the signal array.
	 */
	public abstract int getSize();
}

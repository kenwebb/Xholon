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
 * IPort defines the abstract services that ports provide.
 * A port is a window into the world external to its owning xholon, or to some part or parts of the xholon.
 * A port has three basic responsibilites:
 * (1) Locate other xholons with which this xholon can interact and establish one-way links,
 * (2) Send messages from a sender xholon to one or more receiver xholons through a link,
 * (3) Know what message types can be sent and received across a particular link
 * and optionally use this knowledge to validate and restrict link establishment and/or message traffic.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.4 (Created on August 21, 2006)
 */
public interface IPort extends IXholon {

	/** XPath expression template wildcard. */
	public static final char XPATH_EXPR_WILDCARD = '?';
	/** XPath expression, no index */
	public static final int XPATH_EXPR_NO_INDEX = -1;
	
	/** There is no port index, which normally means that the port is a scalar. */
	public static final int PORTINDEX_NULL = -1;
	
	/**
	 * Set the provided interface of this port.
	 * These are the behavioral features that the owning xholon provides to its environment through this port.
	 * In practical terms, on a non-conjugated port, these are the signals that are allowed in incoming messages
	 * received at this port.
	 * On a conjugated port, these are the signals that are allowed in outoing messages.
	 * @param providedInterface An array of zero or more signal IDs.
	 */
	public abstract void setProvidedInterface(IPortInterface providedInterface);
	
	/**
	 * Get the provided interface of this port.
	 * These are the behavioral features that the owning xholon provides to its environment through this port.
	 * In practical terms, on a non-conjugated port, these are the signals that are allowed in incoming messages
	 * received at this port.
	 * On a conjugated port, these are the signals that are allowed in outoing messages.
	 * @return An array of zero or more signal IDs.
	 */
	public abstract IPortInterface getProvidedInterface();
	
	/**
	 * Set the required interface of this port.
	 * These are the services that the owning xholon expects from its environment through this port.
	 * In practical terms, on a non-conjugated port, these are the signals that are allowed in outgoing messages
	 * sent from this port.
	 * On a conjugated port, these are the signals that are allowed in incoming messages.
	 * @param requiredInterface An array of zero or more signal IDs.
	 */
	public abstract void setRequiredInterface(IPortInterface requiredInterface);
	
	/**
	 * Get the required interface of this port.
	 * These are the services that the owning xholon expects from its environment through this port.
	 * In practical terms, on a non-conjugated port, these are the signals that are allowed in outgoing messages
	 * sent from this port.
	 * On a conjugated port, these are the signals that are allowed in incoming messages.
	 * @return An array of zero or more signal IDs.
	 */
	public abstract IPortInterface getRequiredInterface();
	
	/**
	 * Set whether or not this is a conjugated port.
	 * In a non-conjugated port, the provided and required interfaces are as set.
	 * In a conjugated port, they are reversed.
	 * @param isConjugated true (conjugated) or false (non-conjugated). default: false
	 */
	public abstract void setIsConjugated(boolean isConjugated);
	
	/**
	 * Get whether or not this is a conjugated port.
	 * @return true (conjugated) or false (non-conjugated).
	 */
	public abstract boolean getIsConjugated();
	
	/**
	 * Set the replication factor of a remote port, and create the remote port.
	 * @param multiplicity The maximum number of instances of the remote port.
	 */
	public abstract void setReplications(int multiplicity);
	
	/**
	 * Set the link from a local port replication to a remote port or xholon.
	 * @param index Index of the local replicated port on which the link is to be set.
	 * @param context The xholon that owns the port and port replication.
	 * @param xpathExprTemplate XPath expression template.
	 * @param xholonIx Remote xholon index, used to fill in a value in the template.
	 *        If there is no index, then the value must be XPATH_EXPR_NO_INDEX.
	 * @param portIx Remote port index, used to fill in a value in the template.
	 *        If there is no index, then the value must be XPATH_EXPR_NO_INDEX.
	 * @param replicationIx Remote replication index, used to fill in a value in the template.
	 *        If there is no index, then the value must be XPATH_EXPR_NO_INDEX.
	 */
	public abstract boolean setLink(int index, IXholon context, String xpathExprTemplate, int xholonIx, int portIx, int replicationIx);
	
	/**
	 * Set the link from a local port replication to a remote port or xholon.
	 * Only a client can set up a link, which must be a link to a server.
	 * A client is a port specified in UML as isService=false, and in Xholon as isConjugated=true.
	 * A server is a port specified in UML as isService=true, and in Xholon as isConjugated=false.
	 * A link will consist of two one-directional connections if both ends can legally send signals.
	 * The provided and required interfaces are specified in terms of the server.
	 * If a server has one or more signals specified for its provided interface,
	 * then a connection will be established from its client(s) to the server.
	 * If a server has one or more signals specified for its required interface,
	 * then a connection will be established from the server to its client(s).
	 * TODO How to handle a symmetrical link where provided and required IF are equal.
	 * @param index Index of the local replicated port on which the link is to be set.
	 * @param context The xholon that owns the port and port replication.
	 * @param xpathExpression Complete XPath expression.
	 * @return false (remote port or xholon can't be found) or true (can be found)
	 */
	public abstract boolean setLink(int index, IXholon context, String xpathExpression);
	
	/**
	 * Get the link from a local port replication to a remote port or xholon.
	 * Hide the internal details of how this is done.
	 * Currently the nextSibling is being used internally to store the link, but this may change.
	 * @param index Index of the local replicated port.
	 * @return The remote port or xholon, or null if there is none.
	 */
	public abstract IXholon getLink(int index);
	
	/**
	 * Get the link from a local port replication to a remote port or xholon.
	 * This method must be called directly on a replication instance,
	 * rather than on the port that owns the replication.
	 * Hide the internal details of how this is done.
	 * Currently the nextSibling is being used internally to store the link, but this may change.
	 * @return The remote port or xholon, or null if there is none.
	 */
	public abstract IXholon getLink();
	
	/**
	 * Send a ROOM/UML2 message through a local instance of a replicated port
	 * to a remote replicated port instance or directly to a remote xholon,
	 * or from a remote port replication to its owning xholon.
	 * @param signal A distinguishing identifier for this message.
	 * @param data Any data that needs to be sent (optional).
	 * @param sender The sender of the message.
	 * @param index Index of a replicated port (0 indexed).
	 */
	public abstract void sendMessage(int signal, Object data, IXholon sender, int index);
}

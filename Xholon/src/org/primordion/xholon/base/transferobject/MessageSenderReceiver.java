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

package org.primordion.xholon.base.transferobject;

import java.io.Serializable;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Xholon;

/**
 * <p>This class can be used to hold a non-Xholon representation of a Message sender or receiver.
 * This can be useful during serialization/deserialization.
 * </p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on July 20, 2009)
 */
public class MessageSenderReceiver extends Xholon implements Serializable {
	private static final long serialVersionUID = 2407300735816447019L;
	
	// Node Selector types
	
	/** No node selector. */
	public static final int NS_NULL = -1;
	
	/** The node selector is the name of a XholonClass. */
	public static final int NS_XHOLONCLASS = -2;
	
	/** The node selector is a role name. */
	public static final int NS_ROLENAME = -3;
	
	/** The node selector is an XPath expression. */
	public static final int NS_XPATHEXPR = -4;
	
	/** The node selector is a remote name, such as the JNDI name. */
	public static final int NS_REMOTENAME = -5;
	
	/** The node selector is application specific. */
	public static final int NS_APPSPECIFIC = -6;
	
	/**
	 * Identifier.
	 * <p>If the id is < 0, then it identifies the type of node selector.</p>
	 * <p>If the id is >= 0, then the id is a Xholon id,
	 * and the node selector is the name of a XholonClass.</p>
	 */
	private int id = NS_NULL;
	
	/**
	 * A String that, in conjunction with the id,
	 * can be used to select a specific node in a Xholon model.
	 */
	private String nodeSelector = null;
	
	/**
	 * constructor
	 * @param senderReceiver
	 */
	public MessageSenderReceiver(IXholon senderReceiver) {
		if (senderReceiver != null) {
			id = senderReceiver.getId();
			nodeSelector = senderReceiver.getXhcName();
		}
	}
	
	/**
	 * constructor
	 * @param id
	 * @param nodeSelector
	 */
	public MessageSenderReceiver(int id, String nodeSelector) {
		this.id = id;
		this.nodeSelector = nodeSelector;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getId()
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Get the node selector.
	 * @return a String that, in conjunction with the id,
	 * can be used to select a specific node in a Xholon model.
	 */
	public String getNodeSelector() {
		return nodeSelector;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getXhcName()
	 */
	public String getXhcName() {
		if (id == NS_XHOLONCLASS) {
			return nodeSelector;
		}
		else {
			return nodeSelector;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getRoleName()
	 */
	public String getRoleName() {
		if (id == NS_ROLENAME) {
			return nodeSelector;
		}
		else {
			return null;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getName()
	 */
	public String getName() {
		if (id < 0) {
			if (nodeSelector == null) {
				return "UnknownClassName_0";
			}
			else {
				return nodeSelector;
			}
		}
		if ("".equals(nodeSelector)) {
			nodeSelector = "UnknownClassName";
		}
		
		return nodeSelector.substring(0,1).toLowerCase() + nodeSelector.substring(1) + "_" + id;
		// Convention: first letter of enities are lower case, classes upper case
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof MessageSenderReceiver)) {
			return false;
		}
		final MessageSenderReceiver that = (MessageSenderReceiver) o;
		if (!(this.id == that.getId())) {
			return false;
		}
		if (this.nodeSelector == null) {
			return false;
		}
		if (!(this.nodeSelector.equals(that.getNodeSelector()))) {
			return false;
		}
		return true;
	}
	
	/*
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		int result = id;
		result = 29 * result + this.nodeSelector.hashCode();
		return result;
	}
}

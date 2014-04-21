/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2012 Ken Webb
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

package org.primordion.xholon.service.nosql;

import org.primordion.xholon.base.ISignal;

/**
 * Contants for use with the NoSql Service.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on December 26, 2012)
 */
public interface INoSql {
	
	/**
	 * Add a node to the Neo4j graph. The node is typically/always an IXholon.
	 * The msg.sender is the resource to add.
	 * Optional msg.data contains parameters ?
	 */
	public static final int SIG_ADD_TOGRAPH_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 101;
	
	/**
	 * Add all nodes to the Neo4j graph (nodes, relationships, properties).
	 * The msg.sender is the root of the subtree to add.
	 * Optional msg.data contains parameters ?
	 */
	public static final int SIG_ADDALL_TOGRAPH_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 102;
	
	/**
	 * Show the contents of this Neo4j graph, such as to stdout.
	 * Optional msg.data contains parameters ?
	 */
	public static final int SIG_SHOW_GRAPH_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 103;
	
	/**
	 * Set the value of one or more attributes in the Neo4j impl class.
	 * msg.data must contain a java.util.Properties object, with one or more attribute name/value pairs.
	 */
	public static final int SIG_SET_ATTRIBUTE_VALS_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 104;
	
	/**
	 * Test the Neo4j Service.
	 */
	public static final int SIG_TEST_REQ = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 999;
	
	/**
	 * Response.
	 */
	public static final int SIG_RSP = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 201;
	
}

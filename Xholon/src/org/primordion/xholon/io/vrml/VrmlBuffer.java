/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2005, 2006, 2007, 2008 Ken Webb
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

package org.primordion.xholon.io.vrml;

/**
 * VRML Buffer. VRML data is accumulated in the buffer before being written to a file.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Oct 25, 2005)
 */
public class VrmlBuffer {
	
	private String bff;
	
	/**
	 * Constructor.
	 */
	public VrmlBuffer() {
		init();
	}
	
	/**
	 * Initialize the internal buffer.
	 */
	public void init() {
		bff = "";
	}
	
	/**
	 * Get the internal buffer.
	 * @return The internal buffer.
	 */
	public String get() {
		return bff;
	}

	/**
	 * Append a string to the internal buffer.
	 * @param s The string to append.
	 */
	public void write( String s ) {
	    bff += s;
	}
	
	/**
	 * Append an int to the internal buffer.
	 * @param int1 The int to append.
	 */
	public void write( int int1 ) {
	    bff += int1 + " ";
	}

	/**
	 * Append two int to the internal buffer, separated by a space.
	 * @param int1 The first int.
	 * @param int2 The second int.
	 */
	public void write( int int1, int int2 ) {
	    bff += int1 + " " + int2 + " ";
	}

	/**
	 * Append three int to the internal buffer, separated by spaces.
	 * @param int1 The first int.
	 * @param int2 The second int.
	 * @param int3 The third int.
	 */
	public void write( int int1, int int2, int int3 ) {
	    bff += int1 + " " + int2 + " " + int3 + " ";
	}

	/**
	 * Append a double to the internal buffer.
	 * @param double1 The double to append.
	 */
	public void write( double double1 ) {
	    bff += double1 + " ";
	}

	/**
	 * Append two double to the internal buffer, separated by a space.
	 * @param double1 The first double.
	 * @param double2 The second double.
	 */
	public void write( double double1, double double2 ) {
	    bff += double1 + " " + double2 + " ";
	}

	/**
	 * Append three double to the internal buffer, separated by a space.
	 * @param double1 The first double.
	 * @param double2 The second double.
	 * @param double3 The third double.
	 */
	public void write( double double1, double double2, double double3 ) {
	    bff += double1 + " " + double2 + " " + double3 + " ";
	}
	
	/**
	 * Write a double quote character to the internal buffer.
	 */
	public void writeQuote() {
		bff += "\"";
	}
	
	/**
	 * Write the node name to the internal buffer.
	 * Should pass in just name (ex: enzyme_123)
	 * @param nodeName The node name.
	 */
	public void writeNodeName( String nodeName )
	{
	    bff += nodeName + " ";
	}
	
	/**
	 * Write the node name to the internal buffer, as a VRML DEF.
	 * @param nodeName The node name.
	 */
	public void writeDefNodeName( String nodeName )
	{
	    bff += "DEF " + nodeName +" Transform {\n";
	}
}

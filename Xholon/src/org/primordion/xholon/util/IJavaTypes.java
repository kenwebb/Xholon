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

package org.primordion.xholon.util;

/**
 * Java types - primitives, array primitives, Map, List, Set.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on August 27, 2009)
 */
public interface IJavaTypes {
	
	// Java primitive types
	public static final int JAVACLASS_UNKNOWN = 0;
	public static final int JAVACLASS_int = 1;
	public static final int JAVACLASS_long = 2;
	public static final int JAVACLASS_double = 3;
	public static final int JAVACLASS_float = 4;
	public static final int JAVACLASS_boolean = 5;
	public static final int JAVACLASS_String = 6;
	public static final int JAVACLASS_byte = 7;
	public static final int JAVACLASS_char = 8;
	public static final int JAVACLASS_short = 9;
	
	// Java array types
	public static final int JAVACLASS_array = 16;
	public static final int JAVACLASS_arrayint = JAVACLASS_array | JAVACLASS_int;
	public static final int JAVACLASS_arraylong = JAVACLASS_array | JAVACLASS_long;
	public static final int JAVACLASS_arraydouble = JAVACLASS_array | JAVACLASS_double;
	public static final int JAVACLASS_arrayfloat = JAVACLASS_array | JAVACLASS_float;
	public static final int JAVACLASS_arrayboolean = JAVACLASS_array | JAVACLASS_boolean;
	public static final int JAVACLASS_arrayString = JAVACLASS_array | JAVACLASS_String;
	public static final int JAVACLASS_arraybyte = JAVACLASS_array | JAVACLASS_byte;
	public static final int JAVACLASS_arraychar = JAVACLASS_array | JAVACLASS_char;
	public static final int JAVACLASS_arrayshort = JAVACLASS_array | JAVACLASS_short;
	
	// Java collections
	public static final int JAVACLASS_List = 101;
	public static final int JAVACLASS_Map  = 102;
	public static final int JAVACLASS_Set  = 103;
	
}

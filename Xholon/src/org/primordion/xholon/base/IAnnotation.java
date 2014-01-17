/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2007, 2008 Ken Webb
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
 * Any Xholon node can be given a textual annotation.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.7.1 (Created on October 13, 2007)
 */
public interface IAnnotation extends IXholon {
	
	/**
	 * An annotation string could be any length.
	 * This constant defines a suggested maximum length to display.
	 */
	public static final int ANNOTATION_MAX_DISPLAY_LEN = 100;
	
	/**
	 * Whether or not this node should be removed from the tree.
	 */
	public static final boolean SHOULD_REMOVE_THIS_NODE = true;
	
}

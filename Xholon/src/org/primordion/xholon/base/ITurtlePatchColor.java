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
 * A turtle or patch is assigned a color in the turtle mechanism.
 * The turtle mechanism is based on Logo and NetLogo.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on February 28, 2007)
 * @see the NetLogo website http://ccl.northwestern.edu/netlogo/
 */
public interface ITurtlePatchColor {
	
	// NetLogo colors
	public static final int TPCOLOR_BLACK = 0;
	public static final int TPCOLOR_GRAY = 5;
	public static final int TPCOLOR_RED = 15;
	public static final int TPCOLOR_ORANGE = 25;
	public static final int TPCOLOR_BROWN = 35;
	public static final int TPCOLOR_YELLOW = 45;
	public static final int TPCOLOR_GREEN = 55;
	public static final int TPCOLOR_LIME = 65;
	public static final int TPCOLOR_TURQUOISE = 75;
	public static final int TPCOLOR_CYAN = 85;
	public static final int TPCOLOR_SKY = 95;
	public static final int TPCOLOR_BLUE = 105;
	public static final int TPCOLOR_VIOLET = 115;
	public static final int TPCOLOR_MAGENTA = 125;
	public static final int TPCOLOR_PINK = 135;
	public static final int TPCOLOR_WHITE = 140; // 9.9 in NetLogo
	
	public static final int TPCOLOR_DEFAULT = TPCOLOR_BLACK;
}

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
 * Numerical integration package.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on January 17, 2007)
 * TODO Do I need a set of concrete integration classes to actually do different types of numerical integration?
 */
public class IIntegration {

	// time step multipliers - time
	public static final int M_YEARLY = 1;
	public static final int M_MONTHLY = 12;
	public static final int M_WEEKLY = 52;
	public static final int M_DAILY = 365;
	public static final int M_HOURLY = M_DAILY * 24;
	public static final int M_MINUTELY = M_HOURLY * 60;
	public static final int M_SECONDLY = M_MINUTELY * 60;
	
	// time step multipliers - binary multiples
	public static final int M_1   =   1;
	public static final int M_2   =   2;
	public static final int M_4   =   4;
	public static final int M_8   =   8;
	public static final int M_16  =  16;
	public static final int M_32  =  32;
	public static final int M_64  =  64;
	public static final int M_128 = 128;
	public static final int M_256 = 256;
	public static final int M_512 = 512;
	public static final int M_1024 = 1024;
	public static final int M_2048 = 2048;
	public static final int M_4096 = 4096;
	public static final int M_8192 = 8192;
	public static final int M_16384 = 16384;
	public static final int M_32768 = 32768;
	public static final int M_65536 = 65536;
	public static final int M_131072 = 131072;
	public static final int M_262144 = 262144;
	
	// default
	public static final int M_DEFAULT = M_1;
}

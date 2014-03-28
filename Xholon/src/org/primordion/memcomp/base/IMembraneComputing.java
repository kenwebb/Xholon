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

package org.primordion.memcomp.base;

import org.primordion.xholon.base.IXholon;

/**
 * Membrane Computing.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on Jan 2, 2006)
 */
public interface IMembraneComputing extends IXholon {

	// Ports
	public static final int P_HERE = 0;
	public static final int P_OUT  = 1; // out membrane
	public static final int P_IN1  = 2; // in membranes
	public static final int P_IN2  = 3;
	public static final int P_IN3  = 4;
	public static final int P_IN4  = 5;
	public static final int P_OUTPUT = 0; // output membrane
	public static final int SIZE_MYAPP_PORTS = 6;
	
	// Signals sent in messages
	public static final int SIG_APPEND   = 100;
	public static final int SIG_DISSOLVE = 200;
	
	// Priority
	public static final int PRIORITY_DEFAULT = 100; // default priority = highest priority
	public static final int PRIORITY_LOWEST  =   0;

	// Symport rule directions
	public static final int SD_IN  = 1; // Symport direction in
	public static final int SD_OUT = 2; // Symport direction out
}

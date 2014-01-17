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

package org.primordion.ctrnn.AdapSysLab;

import org.primordion.xholon.base.IXholon;

/**
 * Continuous-Time Recurrent Neural Network (CTRNN).
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on Feb 9, 2006)
 */
public interface ICtrnn extends IXholon {
  
  /* GWT moved to XhAbstractCtrnn
	// Ports
	public static final int P_OUT1 = 0;
	public static final int P_OUT2 = 1;
	public static final int P_OUT3 = 2;
	public static final int SIZE_MYAPP_PORTS = 3;
	
	// Signals sent in messages
	public static final int SIG_SET_MOTOR_POWER   = 100; // set motor power
	public static final int SIG_READ_AD = 200; // read AD from photoreceptor

	public static final String NEURON_CLASS_NAME = "Neuron";
	public static final double DELTA_T = 0.1;
	*/
}

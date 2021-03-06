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

import java.util.Random;

/**
 * VRML Rotations.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Oct 25, 2005)
 */
public class Rotations {

	private double radsIn360Degrees;
	private double raFactor;
	private Random random;
	
	/**
	 * Constructor.
	 */
	public Rotations() {
		radsIn360Degrees = 6.283;
		raFactor = radsIn360Degrees;
		random = new Random();
	}
	
	/**
	 * Get a random rotation.
	 * @return X,Y,Z values of a rotation.
	 */
	public SFRotation get()
	{
		SFRotation rot = new SFRotation();
		rot.x = random.nextDouble();
		rot.y = random.nextDouble();
		rot.z = random.nextDouble();
		rot.angle = random.nextDouble();
		rot.angle *= raFactor;
		return rot;
	}
}

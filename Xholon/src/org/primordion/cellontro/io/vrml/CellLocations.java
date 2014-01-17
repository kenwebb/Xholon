/* Cellontro - models & simulates cells and other biological entities
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

package org.primordion.cellontro.io.vrml;

import org.primordion.xholon.io.vrml.SFVec3f;

/**
 * VRML CellLocations. Sets up an array of locations in 3D space that
 * cells can occupy.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Oct 26, 2005)
 */
public class CellLocations {

	public static final int NCells = 10;
	private SFVec3f loc[];
    private int next;
	private double sf; // scaling factor
	
	/**
	 * Constructor.
	 */
	public CellLocations() {
		loc = new SFVec3f[NCells];
		for (int i = 0; i < NCells; i++) {
			loc[i] = new SFVec3f();
		}
		next = 0;
		sf = 1.5;
		loc[0].x = -15000.0*sf; loc[0].y = - 5000.0*sf; loc[0].z =      0.0*sf;
		loc[1].x =  10000.0*sf; loc[1].y =   5000.0*sf; loc[1].z =  20000.0*sf;
		loc[2].x = -35000.0*sf; loc[2].y = -10000.0*sf; loc[2].z =  50000.0*sf;
		loc[3].x = -12000.0*sf; loc[3].y =  -2000.0*sf; loc[3].z = -50000.0*sf;
		loc[4].x =   9000.0*sf; loc[4].y =   7000.0*sf; loc[4].z = -60000.0*sf;
		loc[5].x = -50000.0*sf; loc[5].y =  -7000.0*sf; loc[5].z = -70000.0*sf;
		loc[6].x =      0.0*sf; loc[6].y = -17000.0*sf; loc[6].z = -90000.0*sf;
		loc[7].x = -25000.0*sf; loc[7].y =  60000.0*sf; loc[7].z = -85000.0*sf;
		loc[8].x = -10000.0*sf; loc[8].y = -27000.0*sf; loc[8].z = -80000.0*sf;
		loc[9].x =  20000.0*sf; loc[9].y = -50000.0*sf; loc[9].z = -75000.0*sf;

	}
	
	/**
	 * Get next XYZ location to place a cell.
	 * @return A location.
	 */
	SFVec3f getXYZ()
	{
		SFVec3f myLoc = loc[next];
	    next++;
	    if (next == NCells) {
	      next = 0;
	    }
	    return myLoc;
	}
	
	/**
	 * Get radius.
	 * @return Radius (nm).
	 */
	double getRadius()
	{
		return 12500.0;
	}
}

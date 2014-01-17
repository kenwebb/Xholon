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

import java.util.Random;
import org.primordion.xholon.io.vrml.SFVec3f;

/**
 * VRML CytoplasmLocations. Sets up an array of locations in 3D space that
 * organelles can occupy within a cell.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Oct 26, 2005)
 */
public class CytoplasmLocations {

	public static final int MaxOrganelleLocations = 200;
	private double pi;
    private SFVec3f locOr[];
    private int nextOr;
	private int numOrganelleLocations;
	private Random random;

	/**
	 * Constructor.
	 */
	public CytoplasmLocations() {
		locOr = new SFVec3f[MaxOrganelleLocations];
		for (int i = 0; i < MaxOrganelleLocations; i++) {
			locOr[i] = new SFVec3f();
		}
		pi = 3.1415926535;
		nextOr = 0;
		numOrganelleLocations = 0;
		random = new Random();

		createOrganelleLocations();
	}
	
	/**
	 * Get a random organelle x, y, z location.
	 * @return An organelle location.
	 */
	SFVec3f getOr()
	{
		SFVec3f myLoc = locOr[nextOr];
	    nextOr++;
	    if (nextOr == numOrganelleLocations) {
	      nextOr = 0;
	    }
	    return myLoc;

	}
	
	/**
	 * Create a set of random organelle locations.
	 */
	private void createOrganelleLocations()
	{
		double radius; // radius in nanometers, from center of cell
		double pAngle;
		double inc; // increment in degrees

		// organelle ring 1
		radius = 9500.0;
		pAngle = 30.0;
		inc = 30.0;
		numOrganelleLocations = doRing( radius, pAngle, inc, locOr, numOrganelleLocations, MaxOrganelleLocations);

		// organelle ring 2
		radius = 9500.0;
		pAngle = 60.0;
		inc = 20.0;
		numOrganelleLocations = doRing( radius, pAngle, inc, locOr, numOrganelleLocations, MaxOrganelleLocations);

		// organelle ring 3
		radius = 9500.0;
		pAngle = 90.0;
		inc = 15.0;
		numOrganelleLocations = doRing( radius, pAngle, inc, locOr, numOrganelleLocations, MaxOrganelleLocations);

		// organelle ring 4
		radius = 9500.0;
		pAngle = 120.0;
		inc = 20.0;
		numOrganelleLocations = doRing( radius, pAngle, inc, locOr, numOrganelleLocations, MaxOrganelleLocations);

		// organelle ring 5
		radius = 9500.0;
		pAngle = 150.0;
		inc = 30.0;
		numOrganelleLocations = doRing( radius, pAngle, inc, locOr, numOrganelleLocations, MaxOrganelleLocations);

		// organelle ring 6
		radius = 7500.0;
		pAngle = 15.0;
		inc = 45.0;
		numOrganelleLocations = doRing( radius, pAngle, inc, locOr, numOrganelleLocations, MaxOrganelleLocations);

		// organelle ring 7
		radius = 7500.0;
		pAngle = 45.0;
		inc = 30.0;
		numOrganelleLocations = doRing( radius, pAngle, inc, locOr, numOrganelleLocations, MaxOrganelleLocations);

		// organelle ring 8
		radius = 7500.0;
		pAngle = 75.0;
		inc = 20.0;
		numOrganelleLocations = doRing( radius, pAngle, inc, locOr, numOrganelleLocations, MaxOrganelleLocations);

		// organelle ring 9
		radius = 7500.0;
		pAngle = 105.0;
		inc = 20.0;
		numOrganelleLocations = doRing( radius, pAngle, inc, locOr, numOrganelleLocations, MaxOrganelleLocations);

		// organelle ring 10
		radius = 7500.0;
		pAngle = 135.0;
		inc = 30.0;
		numOrganelleLocations = doRing( radius, pAngle, inc, locOr, numOrganelleLocations, MaxOrganelleLocations);

		// organelle ring 11
		radius = 7500.0;
		pAngle = 165.0;
		inc = 45.0;
		numOrganelleLocations = doRing( radius, pAngle, inc, locOr, numOrganelleLocations, MaxOrganelleLocations);

		// shuffle the locOr array to get pseudo-random ordering
		shuffleLocations( locOr, numOrganelleLocations );
	}

	/**
	 * Create a complete 360 degree ring of locations.
	 * @param radius Radius of the ring.
	 * @param pAngle Angle of the ring (in degrees).
	 * @param inc Amount to increment the aqngle for each segment in the curve.
	 * @param loc An array of XYZ locations that is created by this function.
	 * @param next Next.
	 * @param maxLocs Maximum number of locations.
	 * @return Updated value of next.
	 */
	int doRing( double radius, double pAngle, double inc, SFVec3f loc[], int next, int maxLocs)
	{
		double tAngle;
		double theta;  // angle in radians ("longitude")
		double phi;    // angle in radians ("latitude")
		phi = pAngle / 180.0 * pi;
		for (tAngle = 0.0; tAngle < 360.0; tAngle+=inc) {
			if (next >= maxLocs) break;
			theta = tAngle / 180.0 * pi;
			loc[next].x = radius * Math.sin(phi) * Math.cos(theta);
			loc[next].y = radius * Math.sin(phi) * Math.sin(theta);
			loc[next].z = radius * Math.cos(phi);
			next++;
		}
		return next;
	}

	/**
	 * Shuffle or randomize the locations.
	 * @param loc Location array to be shuffled.
	 * @param numLocs Number of locations.
	 */
	private void shuffleLocations( SFVec3f loc[], int numLocs )
	{
		int j;
		double jd;
		SFVec3f swap = new SFVec3f();
		for (int i = 0; i < numLocs; i++) {
			// j = rand() / RAND_MAX * numLocs;
			jd = random.nextDouble(); // random number between 0.0 and 1.0
			jd *= numLocs;
			j = (int)jd;
			// printf( "%d ", j );
			swap.x = loc[i].x;
			swap.y = loc[i].y;
			swap.z = loc[i].z;
			loc[i].x = loc[j].x;
			loc[i].y = loc[j].y;
			loc[i].z = loc[j].z;
			loc[j].x = swap.x;
			loc[j].y = swap.y;
			loc[j].z = swap.z;
		}
	}
}

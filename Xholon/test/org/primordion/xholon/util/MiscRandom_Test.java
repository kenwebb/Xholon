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

package org.primordion.xholon.util;

import java.util.Date;

//import static org.junit.Assert.*;
//import org.junit.Test;
import com.google.gwt.junit.client.GWTTestCase;

/**
 * JUnit test case.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3
 */
public class MiscRandom_Test extends GWTTestCase {
  
  public String getModuleName() {
    return "org.Xholon";
  }
  
	public void testGetRandom() {
		assertEquals("[getRandom 1]", java.util.Random.class, MiscRandom.getRandom().getClass());
	}


	public void testGetRandomInt() {
		int rInt;
		for (int i = 0; i < 1000; i++) {
			rInt = MiscRandom.getRandomInt(10, 20);
			assertTrue("[getRandomInt 1]", rInt >= 10 && rInt < 20);
		}
	}

	public void testGetRandomDouble() {
		double rDouble;
		for (int i = 0; i < 100000; i++) {
			rDouble = MiscRandom.getRandomDouble(10.0, 20.0);
			assertTrue("[getRandomDouble 1]", rDouble >= 10.0 && rDouble <= 20.0);
		}
	}
	
	/*public void testGetRandomNormalDouble() {
		double rDouble;
		for (int i = 0; i < 100000; i++) {
			rDouble = Misc.getRandomNormalDouble(0.1, 0.03);
			System.out.println(" " + rDouble);
			//assertTrue("[getRandomDouble 1]", rDouble >= 0.0 && rDouble <= 20.0);
		}
	} */

	//public void testSeedRandomNumberGenerator() {
	//	assertTrue("[seedRandomNumberGenerator 1]", new Date().getTime() >= MiscRandom.seedRandomNumberGenerator());
	//}

	//public void testSeedRandomNumberGeneratorLong() {
	//	assertEquals("[seedRandomNumberGenerator(long) 1]", 1234L, MiscRandom.seedRandomNumberGenerator(1234L));
	//}

}

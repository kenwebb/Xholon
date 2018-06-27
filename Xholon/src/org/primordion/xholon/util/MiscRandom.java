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

package org.primordion.xholon.util;

import java.util.Date;
import java.util.Random;

/**
 * This provides general support for random numbers.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Jul 7, 2005)
 */
public class MiscRandom {


	private static Random random = new Random(); // random number generator
	
	/**
	 * Get the singleton instance of Random used by this application.
	 * @return A singleton instance of Random.
	 */
	public static Random getRandom() {
		return random;
	}
	
	/**
	 * Get random integer >= minVal and < maxVal.
	 * @param minVal The result will be >= this value.
	 * @param maxVal The result will be < this value.
	 * @return A pseudo-random integer.
	 */
	public static int getRandomInt( int minVal, int maxVal )
	{
		float rVal = (random.nextFloat() * (maxVal - minVal)) + minVal;
		int rNum = (int)rVal;
		
		return rNum;
	}

	/**
	 * Get random double >= minVal and <= maxVal.
	 * TODO confirm that this works correctly
	 * @param minVal The result will be >= this value.
	 * @param maxVal The result will be <= this value.
	 * @return A pseudo-random double.
	 */
	public static double getRandomDouble(double minVal, double maxVal)
	{
		double rVal = (random.nextDouble() * (maxVal - minVal)) + minVal;
		return rVal;
	}

	/**
	 * Seed the random number generator with the current data and time.
	 */
	public static long seedRandomNumberGenerator()
	{
		long seed = new Date().getTime();
		random.setSeed(seed);
		return seed;
	}
	
	/**
	 * Seed the random number generator with a known value.
	 */
	public static long seedRandomNumberGenerator(long seed)
	{
		random.setSeed(seed);
		return seed;
	}
	
	/**
	 * Seed the random number generator with a known value.
	 * This can be used when calling from JavaScript.
	 */
	public static void seedRandomNumberGeneratorInt(int seed)
	{
		random.setSeed((long)seed);
	}
	
	/**
	 * Emulate the JavaScript Math.random() function, while allowing for an initial seed.
	 * 
	 * "The Math.random() function returns a floating-point, pseudo-random number in the range 0–1 (inclusive of 0, but not 1)
	 * with approximately uniform distribution over that range — which you can then scale to your desired range.
	 * The implementation selects the initial seed to the random number generation algorithm; it cannot be chosen or reset by the user."
	 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Math/random
	 * 
	 * "Returns the next pseudorandom, uniformly distributed double value between 0.0 and 1.0 from this random number generator's sequence."
	 * https://docs.oracle.com/javase/8/docs/api/java/util/Random.html
	 */
	public static double random()
	{
		return random.nextDouble();
	}

}

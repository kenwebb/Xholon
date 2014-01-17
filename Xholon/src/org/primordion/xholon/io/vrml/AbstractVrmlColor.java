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

import org.primordion.xholon.io.vrml.HSVType;
import org.primordion.xholon.io.vrml.RGBTypeDouble;
import org.primordion.xholon.io.vrml.RGBTypeInt;

/**
 * VRML VrmlColor.
 * <p>ColorSpace downloaded Dec 31, 2002 from:
 * http://www.codeguru.com/dialog/ColorSpace.shtml</p>
 * <p>Author of convert routine:
 * "This article was contributed by Rajiv Ramachandran."</p>
 * <p>The rest of this file is my code (KSW).
 * Converted Oct 2005 from C++ to Java (KSW).</p>
 * <p>Converts RGB values to numbers between 0.0 and 1.0, for example:</p>
 * <p>White: 255 255 255 = 1.0 1.0 1.0</p>
 * <p>Black:   0   0   0 = 0.0 0.0 0.0</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Oct 27, 2005)
 */
public abstract class AbstractVrmlColor implements IVrmlColor {
	
	/**
	 * Convert from HSV to RGB color model.
	 * @param hsv HSV colors.
	 * @return RGB colors.
	 */
	public RGBTypeInt convertHSVtoRGB( HSVType hsv )
	{
		RGBTypeInt rgb = new RGBTypeInt();
		int h = hsv.h;
		int s = hsv.s;
		int v = hsv.v;
		if(h == 0 && s == 0)
		{
			rgb.r = rgb.g = rgb.b = v;
		}
		double min,max,delta,hue;
		
		max = v;
		delta = (max * s)/255.0;
		min = max - delta;

		hue = h;
		if(h > 300 || h <= 60)
		{
			rgb.r = (int)max;
			if(h > 300)
			{
				rgb.g = (int)min;
				hue = (hue - 360.0)/60.0;
				rgb.b = (int)((hue * delta - min) * -1);
			}
			else
			{
				rgb.b = (int)min;
				hue = hue / 60.0;
				rgb.g = (int)(hue * delta + min);
			}
		}
		else
		if(h > 60 && h < 180)
		{
			rgb.g = (int)max;
			if(h < 120)
			{
				rgb.b = (int)min;
				hue = (hue/60.0 - 2.0 ) * delta;
				rgb.r = (int)(min - hue);
			}
			else
			{
				rgb.r = (int)min;
				hue = (hue/60 - 2.0) * delta;
				rgb.b = (int)(min + hue);
			}
		}
		else
		{
			rgb.b = (int)max;
			if(h < 240)
			{
				rgb.r = (int)min;
				hue = (hue/60.0 - 4.0 ) * delta;
				rgb.g = (int)(min - hue);
			}
			else
			{
				rgb.g = (int)min;
				hue = (hue/60 - 4.0) * delta;
				rgb.r = (int)(min + hue);
			}
		}
		return rgb;
	}
	
	/**
	 * Test VrmlColor.
	 *TODO move this to a JUnit test case.
	 */
	public void testVrmlColor()
	{
		int i;
		HSVType hsv = new HSVType();
		RGBTypeInt rgb = new RGBTypeInt();
		RGBTypeDouble colorArray[] = new RGBTypeDouble[MaxColorArraySize];
		for (i = 0; i < MaxColorArraySize; i++) {
			colorArray[i] = new RGBTypeDouble();
		}
		int numColors = 10;
		int svType = 1;
		rgb.r = 0; rgb.g = 0; rgb.b = 0;
		hsv.h = 81; hsv.s = 100; hsv.v = 100;
		rgb = convertHSVtoRGB( hsv );
		System.out.println( "r=" + rgb.r +" g=" + rgb.g + " b=" + rgb.b );
		//System.out.println( "R=%.2f G=%.2f B=%.2f\n", rgb.r / 255.0, rgb.g / 255.0, rgb.b / 255.0 );
		fillColorArray( colorArray, numColors, svType );
		for (i = 0; i < numColors; i++)
			System.out.println( colorArray[i].r + " " + colorArray[i].g + " " + colorArray[i].b );
	}
}

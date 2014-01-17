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

import org.primordion.xholon.io.vrml.AbstractVrmlColor;
import org.primordion.xholon.io.vrml.HSVType;
import org.primordion.xholon.io.vrml.IVrmlColor;
import org.primordion.xholon.io.vrml.RGBTypeDouble;
import org.primordion.xholon.io.vrml.RGBTypeInt;

/**
 * VRML VrmlColorCell.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Oct 27, 2005)
 */
public class VrmlColorCell extends AbstractVrmlColor implements IVrmlColor {

	public static final int SvTypeSmallMolecule = 1;
	public static final int SvTypeEnzyme = 2;
	public static final int SvTypepHIndicator = 3;
	
	/**
	 * Fill color array.
	 * @param colorArray The array to be filled.
	 * @param numColors The number of colors to put in the array.
	 * @param svType The values placed in the array depend on whether is is for:
	 * small molecules, enzymes, or a pH indicator.
	 * @return 0
	 */
	public int fillColorArray( RGBTypeDouble colorArray[], int numColors, int svType )
	{
		HSVType hsv = new HSVType();
		RGBTypeInt rgb = new RGBTypeInt();
		if (numColors > MaxColorArraySize) {numColors = MaxColorArraySize;}
		int inc = MaxColorArraySize / numColors;
		rgb.r = 0; rgb.g = 0; rgb.b = 0;

		switch (svType ) {
		case SvTypeSmallMolecule: hsv.s = 128; hsv.v = 225; break;
		case SvTypeEnzyme: hsv.s = 255; hsv.v = 150; break;
		case SvTypepHIndicator: hsv.s = 72; hsv.v = 255; break;
		default: hsv.s = 128; hsv.v = 225; break;
		}

		hsv.h = 0;
		for (int i = 0; i < numColors; i++) {
			rgb = convertHSVtoRGB( hsv );
			colorArray[i].r = rgb.r / 255.0;
			colorArray[i].g = rgb.g / 255.0;
			colorArray[i].b = rgb.b / 255.0;
			hsv.h += inc;
		}
		return 0;
	}
	
	/**
	 * main - example of how to use this class
	 * @param args
	 */
	public static void main(String[] args)
	{
		AbstractVrmlColor vc = new VrmlColorCell();
		vc.testVrmlColor();
	}
}

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
package org.primordion.user.app.WolfSheepGrass;

import org.primordion.xholon.base.ITurtlePatchColor;
import org.primordion.xholon.base.Patch;
import org.primordion.xholon.util.MiscRandom;

/**
 * Wolf Sheep Grass.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on March 1, 2007)
 * @see The original NetLogo wolf sheep predation model is: Copyright 1998 Uri Wilensky. All rights reserved.
 * See http://ccl.northwestern.edu/netlogo/models/WolfSheepPredation for terms of use.
*/
public class Grass extends Patch implements CeWolfSheepGrass {
	
	// Parameters
	public static boolean growGrass = false;
	public static int grassRegrowthTime = 1;// = 30;
	
	// Variables
	public int countdown = 0;
	
	// Parameter setters and getters
	public static void setGrowGrass(boolean GrowGrassArg) {growGrass = GrowGrassArg;}
	public static boolean getGrowGrass() {return growGrass;}
	
	public static void setGrassRegrowthTime(int GrassRegrowthTimeArg) {grassRegrowthTime = GrassRegrowthTimeArg;}
	public static int getGrassRegrowthTime() {return grassRegrowthTime;}

	public void postConfigure()
	{
		setPcolor(ITurtlePatchColor.TPCOLOR_GREEN);
		if (growGrass) {
			countdown = MiscRandom.getRandomInt(0, grassRegrowthTime);
			if (MiscRandom.getRandomInt(0, 2) == 0) {
				setPcolor(ITurtlePatchColor.TPCOLOR_BROWN);
			}
		}
		if (getPcolor() == ITurtlePatchColor.TPCOLOR_GREEN) {
			aggregate(0.25);
		}
		super.postConfigure();
	}
	
	public void act()
	{
		switch(xhc.getId()) {
		case GrassCE:
			// if grass? [ ask patches [ grow-grass ] ]
			if (growGrass) {
				growGrass();
			}
			break;
		default:
			break;
		}
		super.act();
	}
	
	/**
	 * Grow grass.
	 */
	protected void growGrass()
	{
		//to grow-grass  ;; patch procedure
		//  ;; countdown on brown patches, if reach 0, grow some grass
		//  if pcolor = brown [
		//    ifelse countdown <= 0
		//      [ set pcolor green
		//        set countdown grass-regrowth-time ]
		//      [ set countdown (countdown - 1) ]
		//  ]
		//end
		if (getPcolor() == ITurtlePatchColor.TPCOLOR_BROWN) {
			if (countdown <= 0) {
				setPcolor(ITurtlePatchColor.TPCOLOR_GREEN);
				aggregate(0.25);
				countdown = grassRegrowthTime;
			}
			else {
				countdown--;
			}
		}
	}
}

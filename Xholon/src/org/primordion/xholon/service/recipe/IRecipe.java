/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2018 Ken Webb
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

package org.primordion.xholon.service.recipe;

import org.primordion.xholon.base.ISignal;

/**
 * An interface for Recipe classes.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on June 23, 2018)
 */
public interface IRecipe {
  
  // add a new recipe book
  public static final int SIG_ADD_RECIPE_BOOK_REQ  = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 101; // -3898
  public static final int SIG_RECIPE_RESP = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 201; // -3798
  
  // get a reference to an existing recipe book
  public static final int SIG_GET_RECIPE_BOOK_REQ  = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 102; // -3897
  
  // get a specific record/recipe from an existing recipe book
  public static final int SIG_GET_RECIPE_REQ  = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 103; // -3896
  
  // get the names of all recipes in an existing recipe book as a CSV string; ex result: "Basket,Hut,FishingRod"
  public static final int SIG_GET_RECIPE_NAMES_REQ  = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 104; // -3895
  
  // 
  //public static final int SIG_B_REQ  = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 105; // -3894
  
  // 
  //public static final int SIG_X_REQ  = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 106; // -3893
  
  // 
  //public static final int SIG_Y_REQ  = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 107; // -3892
  
  // 
  //public static final int SIG_Z_REQ  = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 108; // -3891
  
}

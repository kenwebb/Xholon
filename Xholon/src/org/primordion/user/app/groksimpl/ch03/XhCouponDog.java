/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2020 Ken Webb
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

package org.primordion.user.app.groksimpl.ch03;

import org.primordion.xholon.base.Xholon;

/**
 * Coupon Dog.
 * based on: https://gist.github.com/kenwebb/6424d56a48695ad96175a20ef48394ed  CouponDog.js
 *           https://www.manning.com/books/grokking-simplicity
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="https://gist.github.com/kenwebb/6424d56a48695ad96175a20ef48394ed">Coupon Dog workbook  Grokking Simplicity - book by Eric Normand</a>
 * @since 0.9.1 (Created on December 28, 2020
 */
public class XhCouponDog extends Xholon {
  
  @Override
  public void act() {
    CouponDog couponDog = new CouponDog();
    couponDog.act();
  }
  
}


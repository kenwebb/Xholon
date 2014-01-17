/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2014 Ken Webb
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

package org.primordion.xholon.app.Furcifer;

import org.primordion.xholon.base.IXholon;

/**
 * Test class with one or more named ports.
 */
public class Galaxy extends FurciferAll {
  
  // A named port (port with a name other than "port".
  private IXholon otherGal = null;
  
  public Galaxy() {}
  
  public IXholon getOtherGal() {
    return otherGal;
  }
  
  public void setOtherGal(IXholon otherGal) {
    this.otherGal = otherGal;
  }
  
}

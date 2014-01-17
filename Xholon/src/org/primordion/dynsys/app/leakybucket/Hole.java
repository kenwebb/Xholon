/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2011 Ken Webb
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

package org.primordion.dynsys.app.leakybucket;

import org.primordion.xholon.base.IXholon;

/**
 * A hole in the bottom of a bucket.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on December 18, 2011)
 */
public class Hole extends Xhleakybucket {
	
	// properties of the hole
	private IXholon radius = null;
	private IXholon area = null;
	
	public void postConfigure() {
		area.setVal(Math.PI * Math.pow(radius.getVal(), 2));
		super.postConfigure();
	}
	
	public IXholon getRadius() {
		return radius;
	}

	public void setRadius(IXholon radius) {
		this.radius = radius;
	}

	public IXholon getArea() {
		return area;
	}

	public void setArea(IXholon area) {
		this.area = area;
	}
	
}

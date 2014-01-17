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

package org.primordion.user.app.StupidModel.sm9;

import org.primordion.xholon.base.IQueue;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Queue;

/**
StupidModel9 queue - Xholon Java
<p>Xholon 0.5 http://www.primordion.com/Xholon</p>
*/
public class QueueStupidModel9 extends Queue implements IQueue {
	
	public void act()
	{
		shuffle(); // randomize the order of the bugs in the Q
		int nxt = nextOff; // point to head of queue
		for (int i = 0; i < currentSize; i++) {
			((IXholon)item[nxt]).act();
			nxt++;
			nxt %= maxSize;
		}
		super.act(); // call act() on each bug in the Q
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toString()
	 */
	public String toString()
	{
		return getName()
			+ " qSize:" + getSize()
			+ " firstBug:[" + item[nextOff] + "]";
	}
}

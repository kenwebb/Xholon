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

package org.primordion.user.app.StupidModel.sm12;

import org.primordion.xholon.base.IQueue;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Queue;

/**
StupidModel12 queue - Xholon Java
<p>Xholon 0.5 http://www.primordion.com/Xholon</p>
*/
public class QueueStupidModel12 extends Queue implements IQueue {
	
	/*
	 * @see org.primordion.xholon.base.Xholon#act()
	 */
	public void act()
	{
		//sort(); // sort the bugs in the Q
		int sz = getSize();
		if (sz == 0) {
			XhStupidModel12.setProcessingComplete(true);
		}
		for (int i = 0; i < sz; i++) {
			IXholon bug = (IXholon)dequeue();
			bug.act();
		}
		super.act();
	}
	
	/**
	 * Put the sending bug into the queue to schedule it next time step.
	 * @see org.primordion.xholon.base.Xholon#sendMessage(int, java.lang.Object, org.primordion.xholon.base.IXholon)
	 */
	public void sendMessage(int signal, Object data, IXholon sender)
	{
		if (enqueue(sender) == Q_FULL) {
			System.out.println("QueueStupidModel12 sendMessage() Q_FULL");
		}
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

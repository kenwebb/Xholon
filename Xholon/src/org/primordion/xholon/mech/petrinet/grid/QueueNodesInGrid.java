/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2012 Ken Webb
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

package org.primordion.xholon.mech.petrinet.grid;

import org.primordion.xholon.base.IQueue;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Queue;

/**
 * A queue that contains references to all Transition and Place nodes in the Grid.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on August 2, 2012)
*/
public class QueueNodesInGrid extends Queue implements IQueue {
	private static final long serialVersionUID = 8061749819534957674L;
	
	private boolean hasGarbage = false;
	
	/**
	 * constructor
	 */
	public QueueNodesInGrid() {
		super(1000, 1000, 100000);
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#act()
	 */
	public void act() {
		shuffle(); // randomize the order of the transition and place nodes in the Q
		int nxt = nextOff; // point to head of queue
		for (int i = 0; i < currentSize; i++) {
			IXholon node = ((IXholon)item[nxt]);
			if ((node != null) && (node.hasParentNode())) {
				// directly invoke the transition or place behavior(s) if any
				if (node.hasChildNodes()) {
					node.getFirstChild().act();
				}
			}
			else {
				//println(node.getName() + " has been removed from the Grid and is still in the Q");
				hasGarbage = true;
				if (node == null) {
					// TODO bug - node occassionally = null
					logger.warn("QueueNodesInGrid.act() node = null");
				}
			}
			nxt++;
			nxt %= maxSize;
		}
		if (hasGarbage) {
			collectGarbage();
		}
		// don't call super.act(), to avoid calling the Grid
	}
	
	/**
	 * Collect garbage.
	 * Dequeue nodes that have been removed from the Grid.
	 */
	protected void collectGarbage() {
		for (int i = 0; i < currentSize; i++) {
			IXholon node = (IXholon)dequeue();
			if ((node != null) && (node.hasParentNode())) {
				enqueue(node);
			}
		}
		hasGarbage = false;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toString()
	 */
	public String toString()
	{
		String outStr = getName()
		  + " qSize:" + getSize();
		if (getSize() > 0) {
			outStr += " firstTransition:[" + item[nextOff] + "]";
		}
		return outStr;
	}
	
}

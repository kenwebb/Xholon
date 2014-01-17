package org.primordion.user.app.Bestiary;

import org.primordion.xholon.base.IGrid;
import org.primordion.xholon.base.IQueue;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Queue;

/**
 * A queue that contains references to all beasts in the grid.
 * This permits faster access to the beasts at runtime.
 * One way to add new beasts at runtime,
 * is to paste them as children of this container node.
 * It will automatically move them to the grid,
 * and add them to the queue.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on May 27, 2010)
*/
public class QueueBestiary extends Queue implements IQueue {
	
	/*
	 * @see org.primordion.xholon.base.Xholon#postConfigure()
	 */
	public void postConfigure()
	{
		super.postConfigure();
		if (hasChildNodes()) {
			addBeastsToQueue();
			moveBeastsToGrid();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#act()
	 */
	public void act()
	{
		if (hasChildNodes()) {
			// at runtime, a user can paste new beasts into this container
			addBeastsToQueue();
			moveBeastsToGrid();
		}
		shuffle(); // randomize the order of the bugs in the Q
		int nxt = nextOff; // point to head of queue
		for (int i = 0; i < currentSize; i++) {
			((IXholon)item[nxt]).act();
			nxt++;
			nxt %= maxSize;
		}
		super.act(); // call act() on each bug in the Q
	}
	
	/**
	 * Add all beasts to the Q.
	 */
	protected void addBeastsToQueue()
	{
		// Put bugs into a scheduling Queue, so the order in which they act can be shuffled.
		setMaxSize(getSize() + getNumChildren(false));
		IXholon bug = getFirstChild();
		while (bug != null) {
			if (enqueue(bug) == IQueue.Q_FULL) {
				System.out.println("QueueBestiary postConfigure() schedulingQ Q_FULL");
			}
			bug = bug.getNextSibling();
		}
	}
	
	/**
	 * Move all beasts to random positions within the grid.
	 */
	protected void moveBeastsToGrid()
	{
		IXholon grid = getXPath().evaluate("ancestor::Bestiary/Grid", this);
		((IGrid)grid).moveXholonsToGrid(this, false);
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toString()
	 */
	public String toString()
	{
	  String outStr = getName()
		  + " qSize:" + getSize();
		if (getSize() > 0) {
			outStr += " firstBeast:[" + item[nextOff] + "]";
		}
		return outStr;
	}
}

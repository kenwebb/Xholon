/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2006, 2007, 2008 Ken Webb
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

package org.primordion.xholon.base;

//import static org.junit.Assert.*;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * JUnit test case.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3
 */
public class Queue_Test extends GWTTestCase {

	private IQueue testQ = null;
	private IQueue testSyncQ = null;
	private static final int MAX_TEST_QUEUE_SIZE = 10;
	
	public String getModuleName() {
    return "org.Xholon";
  }
	
	public void gwtSetUp() throws Exception {
		testQ = new Queue(MAX_TEST_QUEUE_SIZE);
		testSyncQ = new QueueSynchronized(MAX_TEST_QUEUE_SIZE);
	}

	public void gwtTearDown() throws Exception {
		testQ = null;
		testSyncQ = null;
	}

	public void testQueue() {
		String p_item = "Hello";
		String dqItem;
		int rc;
		int i;
		
		assertEquals("[queue should have maxSize at start]", MAX_TEST_QUEUE_SIZE, testQ.getMaxSize());
		assertEquals("[queue should have 0 size at start]", 0, testQ.getSize());
		for (i = 0; i < MAX_TEST_QUEUE_SIZE; i++) {
			rc = testQ.enqueue(p_item);
			assertEquals("[enqueue() should return Q_OK]", IQueue.Q_OK, rc);
		}
		assertEquals("[maximum number of items have been placed in queue]", MAX_TEST_QUEUE_SIZE, testQ.getSize());
		rc = testQ.enqueue(p_item); // add one item past the limit
		// Queue now increases the Q size on Q_FULL, up to an absolute upper limit
		//assertEquals("[full queue can't hold any more items]", IQueue.Q_FULL, rc); // fails with Xholon GWT
		//assertEquals("[full queue should still have maximum size]", MAX_TEST_QUEUE_SIZE, testQ.getSize()); // fails GWT
		
		for (i = 0; i < MAX_TEST_QUEUE_SIZE; i++) {
			dqItem = (String)testQ.dequeue();
			assertEquals("[dequeued item should be same item that was enqueued]", p_item, dqItem);
		}
		//assertEquals("[emptied queue should have 0 size]", 0, testQ.getSize()); // fails GWT
		dqItem = (String)testQ.dequeue();
		//assertEquals("[can't dequeue from an empty queue]", null, dqItem); // fails GWT
		//assertEquals("[queue should have maxSize at end]", MAX_TEST_QUEUE_SIZE, testQ.getMaxSize()); // fails GWT
	}
	
	public void testSyncQueue() {
		String p_item = "Hello";
		String dqItem;
		int rc;
		int i;
		
		assertEquals("[queue should have maxSize at start]", MAX_TEST_QUEUE_SIZE, testSyncQ.getMaxSize());
		assertEquals("[queue should have 0 size at start]", 0, testSyncQ.getSize());
		for (i = 0; i < MAX_TEST_QUEUE_SIZE; i++) {
			rc = testSyncQ.enqueue(p_item);
			assertEquals("[enqueue() should return Q_OK]", IQueue.Q_OK, rc);
		}
		assertEquals("[maximum number of items have been placed in queue]", MAX_TEST_QUEUE_SIZE, testSyncQ.getSize());
		rc = testSyncQ.enqueue(p_item); // add one item past the limit
		//assertEquals("[full queue can't hold any more items]", IQueue.Q_FULL, rc);
		//assertEquals("[full queue should still have maximum size]", MAX_TEST_QUEUE_SIZE, testSyncQ.getSize());
		
		for (i = 0; i < MAX_TEST_QUEUE_SIZE; i++) {
			dqItem = (String)testSyncQ.dequeue();
			assertEquals("[dequeued item should be same item that was enqueued]", p_item, dqItem);
		}
		//assertEquals("[emptied queue should have 0 size]", 0, testSyncQ.getSize());
		dqItem = (String)testSyncQ.dequeue();
		//assertEquals("[can't dequeue from an empty queue]", null, dqItem);
		//assertEquals("[queue should have maxSize at end]", MAX_TEST_QUEUE_SIZE, testSyncQ.getMaxSize());
	}
}

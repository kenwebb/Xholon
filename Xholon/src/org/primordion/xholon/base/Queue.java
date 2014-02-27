/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2005, 2006, 2007, 2008 Ken Webb
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

import java.util.Vector;

import org.primordion.xholon.common.mechanism.CeXholonFramework;
import org.primordion.xholon.util.XholonCollections;

/**
 * This is a generic Queue data structure that can be used to store references
 * to any type of data. Items are added at one end of the Queue and taken
 * off at the other end, which is why a Queue is called a First In First Out
 * (FIFO) structure.
 *
 * WARNING: A Queue can fill up to maximum capacity, or it may become empty.
 * Check the return value when calling the enqueue and dequeue functions to
 * see if it is Q_FULL or null.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Jun 8, 2005)
 */
public class Queue extends Xholon implements IQueue {
  private static final long serialVersionUID = -80056287055244349L;

  protected static final int DEFAULT_ABSOLUTE_MAX_MULTIPLIER = 5;
  
  // Queue Header
  protected transient int nextOn;      // next slot to use when enqueuing an item
  protected transient int nextOff;     // next slot to use when dequeuing an item
  protected int maxSize;     // max number of items in the queue body
  protected transient int currentSize; // current number of items in the queue body
  protected int incrementSize = 10; // additional capacity to add if the Q becomes full
  protected int absoluteMaxSize = 1000; // size beyond which the Q can never grow
  // Queue Body
  protected transient Object item[];   // item pointers that are added to queue

  /**
   * Don't use this constructor.
   */
  protected Queue()
  {
    nextOn      = 0;
    nextOff     = 0;
    maxSize     = 0;
    currentSize = 0;
    setId(CeXholonFramework.QueueCE);
  }

  /**
   * Use this constructor.
   * @param numItems Maximum number of items that can be in the queue.
   * This is in fact an initialCapacity that can be increased later.
   */
  public Queue(int numItems)
  {
    this(numItems, numItems, numItems*DEFAULT_ABSOLUTE_MAX_MULTIPLIER);
      /*nextOn      = 0;
      nextOff     = 0;
      maxSize     = numItems;
      currentSize = 0;
      incrementSize = numItems;
      absoluteMaxSize = numItems * 5;
    // allocate space for the item data structure
      item = new Object[numItems];
      setId(CeXholonFramework.QueueCE);*/
  }
  
  /**
   * An optional constructor.
   * @param numItems Maximum number of items that can be in the queue.
   * This is in fact an initialCapacity that can be increased later.
   * @param incrementSize Additional capacity to add if the Q becomes full.
   * @param absoluteMaxSize Size beyond which the Q can never grow.
   */
  public Queue(int numItems, int incrementSize, int absoluteMaxSize)
  {
    nextOn      = 0;
    nextOff     = 0;
    maxSize     = numItems;
    currentSize = 0;
    this.incrementSize = incrementSize;
    this.absoluteMaxSize = absoluteMaxSize;
    // allocate space for the item data structure
    item = new Object[numItems];
    setId(CeXholonFramework.QueueCE);
  }
  
  /*
   * @see org.primordion.xholon.base.IQueue#enqueue(java.lang.Object)
   */
  public int enqueue( Object p_item )
  {
    if (currentSize >= maxSize) {
      if (maxSize + incrementSize <= absoluteMaxSize) {
        setMaxSize(maxSize + incrementSize);
      }
      else if (maxSize < absoluteMaxSize) {
        setMaxSize(absoluteMaxSize);
      }
      else {
        Xholon.getLogger().error("Queue full in enqueue(), absoluteMaxSize exceeded "
            + absoluteMaxSize);
        return Q_FULL;
      }
    }

    item[nextOn] = p_item;
    currentSize++;
    nextOn++;
    nextOn %= maxSize;

    return Q_OK;
  }

  /*
   * @see org.primordion.xholon.base.IQueue#dequeue()
   */
  public Object dequeue()
  {
    Object p_item;
    if (currentSize == 0) {
      return null;
    }

    p_item = item[nextOff];
    currentSize--;
    nextOff++;
    nextOff %= maxSize;
    return p_item;
  }

  /*
   * @see org.primordion.xholon.base.IQueue#getSize()
   */
  public int getSize()
  {
      return currentSize;
  }
  
  /*
   * @see org.primordion.xholon.base.IQueue#getMaxSize()
   */
  public int getMaxSize()
  {
    return maxSize;
  }
  
  /*
   * @see org.primordion.xholon.base.IQueue#setMaxSize(int)
   */
  public void setMaxSize(int maxSize)
  {
    // TODO if new array is smaller than old array, some items may get lost
    Vector<Object> oldItemV = getItems();
    // allocate space for the new item data structure
    item = new Object[maxSize];
    this.nextOn  = 0;
    this.nextOff = 0;
    this.maxSize = maxSize;
    this.currentSize = 0;
    for (int i = 0; i < oldItemV.size(); i++) {
      enqueue(oldItemV.elementAt(i));
    }
  }
  
  /*
   * @see org.primordion.xholon.base.IQueue#shuffle()
   * TODO List and Collecrtions are not supported in J2ME
   */
  public void shuffle()
  {
    java.util.List<Object> list = new java.util.ArrayList<Object>(currentSize);
    int nxt = nextOff; // point to head of queue
    int i;
    for (i = 0; i < currentSize; i++) {
      list.add(item[nxt]);
      nxt++;
      nxt %= maxSize;
    }
    //java.util.Collections.shuffle(list); // not implemented in GWT
    XholonCollections.shuffle(list);
    nxt = nextOff;
    for (i = 0; i < currentSize; i++) {
      item[nxt] = list.get(i);
      nxt++;
      nxt %= maxSize;
    }
  }
  
  /*
   * @see org.primordion.xholon.base.IQueue#sort()
   */
  public void sort()
  {
    java.util.Arrays.sort(item);
  }
  
  /*
   * @see org.primordion.xholon.base.IQueue#empty()
   */
  public void empty() {
    nextOn      = 0;
    nextOff     = 0;
    currentSize = 0;
    item = new Object[maxSize];
  }
  
  public String[] getActionList() {
    String[] actionList = {"empty"};
    return actionList;
  }
  
  public void doAction(String action) {
    if ("empty".equals(action)) {
      empty();
    }
  }
  
  /*
   * @see org.primordion.xholon.base.Xholon#sendMessage(int, java.lang.Object, org.primordion.xholon.base.IXholon)
   */
  public void sendMessage(int signal, Object data, IXholon sender)
  {
    if (enqueue(new Message(signal, data, sender, this)) == Q_FULL) {
      println("Queue sendMessage Q_FULL");
    }
  }
  
  /*
   * @see org.primordion.xholon.base.IQueue#getItems()
   */
  public Vector<Object> getItems()
  {
    Vector<Object> itemVector = new Vector<Object>(currentSize);
    int next = nextOff;
    for (int i = 0; i < currentSize; i++) {
      itemVector.addElement(item[next]);
      next++;
      if (next == maxSize) {
        next = 0;
      }
    }
    return itemVector;
  }
  
  /*
   * This is intended for use in XholonJsApi.java .
   */
  @Override
  public Object getVal_Object() {
    return this.dequeue();
  }
  
  /*
   * This is intended for use in XholonJsApi.java .
   * Note that enqueue(obj) returns an int, which is not dealt with here.
   */
  @Override
  public void setVal_Object(Object obj) {
    this.enqueue(obj);
  }
  
}

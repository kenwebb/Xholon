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

import org.primordion.user.app.helloworldjnlp.XhHelloWorld;

/**
 * JUnit test case.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3
 */
public class Message_Test extends GWTTestCase {

	// constants
	private final int     TEST_SIGNAL   = 101;
	private final String  TEST_DATA     = "Hello";
	private final IXholon TEST_SENDER   = new XhHelloWorld();
	private final IXholon TEST_RECEIVER = new XhHelloWorld();
	// Message fields
	private int tSignal = 0;
	private String tData = null;
	private IXholon tSender = null;
	private IXholon tReceiver = null;
	// Message
	private IMessage tMessage = null;
	
	public String getModuleName() {
    return "org.Xholon";
  }
  
	public void gwtSetUp() throws Exception {
		tSignal   = TEST_SIGNAL;
		tData     = TEST_DATA;
		tSender   = TEST_SENDER;
		tReceiver = TEST_RECEIVER;
		tMessage = null;
	}

	public void gwtTearDown() throws Exception {
		tMessage = null;
	}

	public void testMessage() {
		tMessage = new Message(tSignal, tData, tSender, tReceiver);
		assertEquals("[signal]",   TEST_SIGNAL,   tMessage.getSignal());
		assertEquals("[data]",     TEST_DATA,     tMessage.getData());
		assertEquals("[sender]",   TEST_SENDER,   tMessage.getSender());
		assertEquals("[receiver]", TEST_RECEIVER, tMessage.getReceiver());
		assertNotNull(tMessage.toString());
	}
	
	public void testMessageSet() {
		tMessage = new Message(0, null, null, null);
		tMessage.setSignal(tSignal + 1);
		tMessage.setData(tData + " World");
		tMessage.setSender(tReceiver);
		tMessage.setReceiver(tSender);
		assertEquals("[set signal]",   TEST_SIGNAL + 1,      tMessage.getSignal());
		assertEquals("[set data]",     TEST_DATA + " World", tMessage.getData());
		assertEquals("[set sender]",   TEST_RECEIVER,        tMessage.getSender());
		assertEquals("[set receiver]", TEST_SENDER,          tMessage.getReceiver());
	}
	
	public void testMessageNull() {
		tMessage = new Message(tSignal, tData, tSender, tReceiver);
		tMessage.setSignal(0);
		tMessage.setData(null);
		tMessage.setSender(null);
		tMessage.setReceiver(null);
		assertNotNull("[null signal]", tMessage.getSignal());
		assertNull("[null data]",      tMessage.getData());
		assertNull("[null sender]",    tMessage.getSender());
		assertNull("[null receiver]",  tMessage.getReceiver());
		assertNotNull(tMessage.toString());
	}
}

/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2009 Ken Webb
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

package org.primordion.xholon.service.processor;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.Message;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.service.IXholonService;

/**
 * An abstract base class for TreeProcessor classes.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on August 31, 2009)
 */
public abstract class AbstractTreeProcessor extends Xholon implements ITreeProcessor {
	
	public AbstractTreeProcessor() {}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#processReceivedSyncMessage(org.primordion.xholon.base.IMessage)
	 */
	public IMessage processReceivedSyncMessage(IMessage msg)
	{
		switch (msg.getSignal()) {
		case IXholonService.SIG_PROCESS_REQUEST:
			return new Message(IXholonService.SIG_RESPONSE, processRequest(msg), msg.getReceiver(), msg.getSender());
		default:
			return super.processReceivedSyncMessage(msg);
		}
	}
	
	/**
	 * Process a received request.
	 * @param msg A received request message.
	 * @return A result that will be returned as the data in the response message.
	 */
	public abstract Object processRequest(IMessage msg);
	
}

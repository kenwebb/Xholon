/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2008 Ken Webb
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

package org.primordion.user.app.MeTTTa;

import java.util.List;

import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
//import org.primordion.xholon.base.LongRunner;
import org.primordion.xholon.base.IMessage;

/**
 * Xholon MeTTTa
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.7.1 (Created on May 10, 2008)
*/
public class MeTTTaWorker extends XhMeTTTa {

	// port
	private IXholon businessDelegate = null;
	
	@Override
	public void processReceivedMessage(IMessage msg)
	{
		switch(xhc.getId()) {
		case MeTTTaWorkerCE:
			switch (msg.getSignal()) {
			case SIG_GET_COMPUTER_MOVE: // message from Controller
			  Object data = getBusinessDelegate()
						.sendSyncMessage(msg.getSignal(), (List)msg.getData(), this) //parentNode)
						.getData();
				
				msg.getSender().sendMessage(SIG_SET_COMPUTER_MOVE, data, this); //parentNode);
				
				// GWT can't use LongRunner, which runs on a separate thread,
				// and it's unnecessary where everything runs on the browser (no server).
				/**
				 * Create an instance of LongRunner,
				 * and provide code for the construct() and finished() methods.
				 * This code is executed on a different thread.
				 */
				//LongRunner longRunner = new LongRunner(msg) {
				//	
				//	/*
				//	 * @see org.primordion.xholon.base.LongRunner#construct()
				//	 */
				//	public Object construct() {
				//		return getBusinessDelegate()
				//		.sendSyncMessage(this.getMsg().getSignal(), (List)this.getMsg().getData(), parentNode)
				//		.getData();
				//	}
				//	
				//	/*
				//	 * @see org.primordion.xholon.base.LongRunner#finished()
				//	 */
				//	public void finished() {
				//		// send response to the original SIG_GET_COMPUTER_MOVE message
				//		this.getMsg().getSender().sendMessage(SIG_SET_COMPUTER_MOVE, getVal_Object(), parentNode);
				//		// send msg to remove this LongRunner as a child of its MeTTTaWorker parent.
				//		parentNode.sendMessage(ISignal.SIGNAL_REMOVE_CHILD, this, this);
				//	}
				//	
				//};
				//
				// append the LongRunner instance as a child of this MeTTTaWorker instance
				//longRunner.appendChild(this);
				// start the LongRunner instance in its own thread
				//longRunner.start();
				break;
			//case ISignal.SIGNAL_REMOVE_CHILD:
			//	// execute the removeChild() method on the Xholon thread
			//	IXholon child = (IXholon)msg.getData();
			//	child.removeChild();
			//	break;
			default:
				logger.warn("Worker received an unexpected message:" + msg);
				break;
			}
			break;
		default:
			break;
		}
	}

	public IXholon getBusinessDelegate() {
		return businessDelegate;
	}

	public void setBusinessDelegate(IXholon businessDelegate) {
		this.businessDelegate = businessDelegate;
	}
}

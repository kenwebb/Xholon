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

package org.primordion.xholon.service;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Message;

/**
 * A dummy service for testing.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on August 29, 2009)
 */
public class DummyService extends AbstractXholonService {
	private static final long serialVersionUID = 789286574001917482L;
	
	public static final int SIG_TEST_REQUEST = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 101;
	public static final int SIG_TEST_RESPONSE = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 102;
	public static final int SIG_TEST_LOCATEOTHERSERVICE_REQUEST = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 103;
	public static final int SIG_TEST_LOCATEOTHERSERVICE_RESPONSE = ISignal.SIGNAL_MIN_XHOLON_SERVICE + 104;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#processReceivedSyncMessage(org.primordion.xholon.base.IMessage)
	 */
	public IMessage processReceivedSyncMessage(IMessage msg)
	{
		switch (msg.getSignal()) {
		case SIG_TEST_REQUEST:
			return new Message(SIG_TEST_RESPONSE, test((String)msg.getData()), msg.getReceiver(), msg.getSender());
		case SIG_TEST_LOCATEOTHERSERVICE_REQUEST:
			return new Message(SIG_TEST_LOCATEOTHERSERVICE_RESPONSE,
					testOtherService((String)msg.getData()), msg.getReceiver(), msg.getSender());
		default:
			return super.processReceivedSyncMessage(msg);
		}
	}
	
	/**
	 * Test this dummy service by returning a transformed version of its input.
	 * @param str A String that will be transformed in the output.
	 * @return A transformed String.
	 */
	public String test(String str)
	{
		return "[DummyService:" + str + ":" + str + "]";
	}
	
	/**
	 * Test the ability to locate another service.
	 * This method locates the other service and copies an XML representation to the clipboard.
	 * @param str Name of the other service, for example "AboutService".
	 * @return null
	 */
	public String testOtherService(String str)
	{
		IXholon otherService = getServiceLocatorService().getService(str);
		IXholon xholonHelperService = getApp().getService(IXholonService.XHSRV_XHOLON_HELPER);
		if (xholonHelperService != null) {
			xholonHelperService
				.sendSyncMessage(ISignal.ACTION_COPY_TOCLIPBOARD, otherService, this);
		}
		//otherService.doCopyToClipboard();
		return null;
	}
	
	/*
	 * @see org.primordion.xholon.service.AbstractXholonService#getService(java.lang.String)
	 */
	public IXholon getService(String serviceName)
	{
		if (serviceName.equals(getXhcName())) {
			return this;
		}
		else {
			return null;
		}
	}
	
}

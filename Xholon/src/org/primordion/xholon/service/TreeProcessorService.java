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

import java.util.List;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Message;

/**
 * A service that will process the nodes in a Xholon tree or subtree.
 * Instances of this service are distinguished from each other by their roleName.
 * This service will invoke zero or more TreeProcessor classes that are specified in a XholonList.
 * It can be used to preConfigure, postConfigure, or whatever.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on August 31, 2009)
 */
public class TreeProcessorService extends AbstractXholonService {
	private static final long serialVersionUID = -4896114332825594411L;
	
	String roleName = null;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#processReceivedSyncMessage(org.primordion.xholon.base.IMessage)
	 */
	public IMessage processReceivedSyncMessage(IMessage msg)
	{
		switch (msg.getSignal()) {
		case SIG_PROCESS_REQUEST:
			return new Message(SIG_RESPONSE, processRequest(msg), msg.getReceiver(), msg.getSender());
		case SIG_ADD_REQUEST:
			return new Message(SIG_RESPONSE, addConfigurer(msg), msg.getReceiver(), msg.getSender());
		default:
			return super.processReceivedSyncMessage(msg);
		}
	}
	
	/**
	 * Process a received request.
	 * @param msg
	 * @return
	 */
	protected Object processRequest(IMessage msg)
	{
		IXholon rootNode = (IXholon)msg.getData();
		IXholon configurerList = getFirstChild();
		IXholon implName = configurerList.getFirstChild();
		while (implName != null) {
			IXholon configurer = createInstance(implName.getVal_String());
			if (configurer != null) {
				rootNode = (IXholon)configurer.sendSyncMessage(msg.getSignal(), rootNode, this).getData();
			}
			implName = implName.getNextSibling();
		}
		return rootNode;
	}
	
	/**
	 * Add a new configurer to the list.
	 * @param msg
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Object addConfigurer(IMessage msg)
	{
		List<Object> configurerList = (List<Object>)getFirstChild();
		configurerList.add(msg.getData());
		return null;
	}
	
	/**
	 * 
	 * @see org.primordion.xholon.service.AbstractXholonService#getService(java.lang.String)
	 */
	public IXholon getService(String serviceName)
	{
		if (serviceName.equals(getRoleName())) {
			if (hasChildNodes()) {
				if (!"XholonList".equals(getFirstChild().getXhcName())) {
					// if there is a first child, it must be a XholonList
					return null;
				}
			}
			else {
				// create a XholonList child
				appendChild("XholonList", null);
			}
			return this;
		}
		else {
			return null;
		}
	}
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}

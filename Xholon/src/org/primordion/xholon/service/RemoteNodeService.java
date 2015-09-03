/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2015 Ken Webb
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

import java.util.Map;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;

/**
 * A service that creates nodes that proxy access to remote nodes.
 * The nodes are retained as children of this service.
 * Examples:
var proxy = xh.service("RemoteNodeService");
var proxy = xh.service("RemoteNodeService-PeerJS");
var resp = proxy.call(101, null, $wnd.xh.root().first());
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on September 2, 2015)
 */
public class RemoteNodeService extends AbstractXholonService {
	
	/**
	 * Name of the class that implements the service.
	 * This is the default.
	 */
	private String defaultImplName = "org.primordion.xholon.service.remotenode.PeerJS";
	
	/**
	 * Return an instance of an IRemoteNode (ex: PeerJS), or null if it can't be created.
	 * A new instance is created each time this method is called.
	 * @see org.primordion.xholon.service.AbstractXholonService#getService(java.lang.String)
	 */
	public IXholon getService(String serviceName)
	{
		if (serviceName.startsWith(getXhcName())) {
		  String implName = findImplName(serviceName);
		  IXholon instance = null;
			if (implName == null) {
			  instance = createInstance(defaultImplName);
			}
			else {
			  instance = createInstance(implName);
			}
			if (instance != null) {
			  IXholonClass xholonClass = getApp().getClassNode("XholonServiceImpl");
			  instance.setId(getNextId());
			  instance.setXhc(xholonClass);
			  instance.appendChild(this);
			}
			return instance;
		}
		return null;
	}
	
	/**
	 * Find the implName using the serviceName as a key.
	 * @param serviceName An extended service name.
	 * ex: RemoteNodeService-PeerJS
	 * @return An implName, or null.
	 */
	@SuppressWarnings("unchecked")
	protected String findImplName(String serviceName)
	{
		Map<String, String> map = (Map<String, String>)getFirstChild();
		return (String)map.get(serviceName);
	}

	public String getDefaultImplName() {
		return defaultImplName;
	}

	public void setDefaultImplName(String defaultImplName) {
		this.defaultImplName = defaultImplName;
	}
	
}

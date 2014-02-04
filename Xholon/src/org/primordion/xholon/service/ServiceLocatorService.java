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

//import java.io.IOException;
//import java.io.Writer;

import org.primordion.xholon.base.IXholon;
//import org.primordion.xholon.base.Xholon;

/**
 * A service that locates other services.
 * This service must be the first child of the XholonServices container.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on August 29, 2009)
 */
public class ServiceLocatorService extends AbstractXholonService {
	private static final long serialVersionUID = -8440302184848646489L;
	
	/**
	 * Activation Statuses field seperator.
	 */
	private String sep = ",";
	
	/**
	 * default constructor
	 */
	public ServiceLocatorService() {
		this.setActivated(true); // mark this ServiceLocatorService as activated
	}
	
	/**
	 * Search for and return a named service.
	 * @see org.primordion.xholon.base.IXholon#getService(java.lang.String)
	 */
	public IXholon getService(String serviceName)
	{
		if (serviceName == null) {return null;}
		if (serviceName.equals(getXhcName())) { // serviceName is "ServiceLocatorService"
			return this;
		}
		IXholon service = getNextSibling();
		while (service != null) {
			// give the service a chance to decide if it can handle this service name
			IXholon serviceImpl = service.getService(serviceName);
			if (serviceImpl != null) {
				((AbstractXholonService)service).setActivated(true);
				return serviceImpl;
			}
			service = service.getNextSibling();
		}
		return null;
	}
	
	private static final String showActivationStatuses = "Show activation statuses";
	private String[] actionList = {showActivationStatuses};
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getActionList()
	 */
	public String[] getActionList() {
		return actionList;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#doAction(java.lang.String)
	 */
	public void doAction(String action) {
		if (showActivationStatuses.equals(action)) {
			StringBuilder sb = new StringBuilder()
			.append("ServiceName").append(sep).append("activated").append(sep).append("implName\n")
			.append("-----------").append(sep).append("---------").append(sep).append("--------\n");
			IXholon service = this;
			while (service != null) {
				AbstractXholonService s = (AbstractXholonService)service;
				sb.append(s.getName(IXholon.GETNAME_ROLENAME_OR_CLASSNAME)).append(sep)
				.append(s.isActivated()).append(sep)
				.append(s.getClass().getName())
				.append("\n");
				service = service.getNextSibling();
			}
			if (this.getApp().isUseAppOut()) {
			  /* GWT
				Writer out = this.getApp().getOut();
				try {
					out.write(sb.toString());
					out.flush();
				} catch (IOException e) {
					Xholon.getLogger().warn("ServiceLocatorService unable to write to " + out);
				}*/
			}
			else {
				println(sb.toString());
			}
		}
	}
	
}

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

import org.primordion.xholon.base.IXholon;

/**
 * A service that presents some simple information about the Xholon application that's currently running.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on August 29, 2009)
 */
public class AboutService extends AbstractXholonService {
	private static final long serialVersionUID = 3113112367784652789L;
	
	/**
	 * Name of the class that implements the service.
	 */
	private String implName = "org.primordion.xholon.io.About";
	
	/**
	 * Return an instance of About, or null if it can't be created.
	 * A new instance of About is created and returned each time this method is successfully called.
	 * @see org.primordion.xholon.service.AbstractXholonService#getService(java.lang.String)
	 */
	public IXholon getService(String serviceName)
	{
		if (serviceName.equals(getXhcName())) {
			return createInstance(implName);
		}
		else {
			return null;
		}
	}
	
	public String getImplName() {
		return implName;
	}

	public void setImplName(String implName) {
		this.implName = implName;
	}
	
}

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

import java.util.Map;

import org.primordion.xholon.base.IXholon;

/**
 * A service that creates a variety of viewers.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on September 2, 2009)
 */
public class GenericViewerService extends AbstractXholonService {
	private static final long serialVersionUID = 1393432514257308665L;

	/**
	 * Return an instance of a viewer, or null if none can be created.
	 * A new instance of a viewer is created each time this method is successfully called.
	 * @see org.primordion.xholon.service.AbstractXholonService#getService(java.lang.String)
	 */
	public IXholon getService(String serviceName)
	{
		String implName = findImplName(serviceName);
		if (implName == null) {
			return null;
		}
		else {
			return createInstance(implName);
		}
	}
	
	/**
	 * Find the implName using the serviceName as a key.
	 * @param serviceName A service name.
	 * ex: GraphicalTreeViewerService
	 * @return An implName, or null.
	 */
	@SuppressWarnings("unchecked")
	protected String findImplName(String serviceName)
	{
		Map<String, String> map = (Map<String, String>)getFirstChild();
		return (String)map.get(serviceName);
	}
}

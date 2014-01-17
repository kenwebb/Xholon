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
 * A service that creates line charts.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on August 30, 2009)
 */
public class ChartViewerService extends AbstractXholonService {
	private static final long serialVersionUID = -3630623011455868059L;
	
	/**
	 * Name of the class that implements the service.
	 * This is the default.
	 */
	private String defaultImplName = "org.primordion.xholon.io.ChartViewerGnuplot";
	
	/**
	 * Return an instance of IChartViewer, or null if it can't be created.
	 * A new instance of IChartViewer is created each time this method is called.
	 * @see org.primordion.xholon.service.AbstractXholonService#getService(java.lang.String)
	 */
	public IXholon getService(String serviceName)
	{
		if (serviceName.startsWith(getXhcName())) {
			String implName = findImplName(serviceName);
			if (implName == null) {
				return createInstance(defaultImplName);
			}
			else {
				return createInstance(implName);
			}
		}
		return null;
	}
	
	/**
	 * Find the implName using the serviceName as a key.
	 * @param serviceName An extended service name.
	 * ex: ChartViewerService-JFreeChart
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

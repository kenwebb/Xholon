/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2012 Ken Webb
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
 * NoSqlService provides access to a variety of NoSQL databases.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://en.wikipedia.org/wiki/NoSQL">Wikipedia</a>
 * @see <a href="http://nosql-database.org/">NoSQL website</a>
 * @see <a href="http://www.neo4j.org/">Neo4j</a>
 * @since 0.8.1 (Created on December 26, 2012)
 */
public class NoSqlService extends AbstractXholonService {
	private static final long serialVersionUID = 5549350436461778791L;

	/**
	 * Name of the class that implements the service.
	 * This is the default.
	 * NoSqlService-Neo4jRestApi
	 */
	private String defaultImplName = "org.primordion.xholon.service.nosql.Neo4jRestApi";
	
	/**
	 * Name of the class that implements the service.
	 */
	private String implName = null;
	
	/**
	 * The singleton instance.
	 */
	private transient IXholon instance = null;
	
	/*
	 * @see org.primordion.xholon.service.AbstractXholonService#getService(java.lang.String)
	 */
	public IXholon getService(String serviceName)
	{
	  if (serviceName.startsWith(getXhcName())) {
		  if (instance == null) {
			  if (implName == null) {
					implName = findImplName(serviceName);
				}
				if (implName == null) {
					implName = defaultImplName;
				}
				instance = createInstance(implName);
				if (instance != null) {
				  instance.setId(getNextId());
					instance.setXhc(getApp().getClassNode("XholonServiceImpl"));
					instance.appendChild(this);
				}
			}
			return instance;
		}
		else {
		  return null;
		}
	}
	
	/**
	 * Find the implName using the serviceName as a key.
	 * @param serviceName An extended service name.
	 * ex: NoSqlService-Neo4j
	 * @return An implName, or null.
	 */
	@SuppressWarnings("unchecked")
	protected String findImplName(String serviceName)
	{
		Map<String, String> map = (Map<String, String>)getFirstChild();
		return map.get(serviceName);
	}
	
	public String getDefaultImplName() {
		return defaultImplName;
	}

	public void setDefaultImplName(String defaultImplName) {
		this.defaultImplName = defaultImplName;
	}

	public String getImplName() {
		return implName;
	}

	public void setImplName(String implName) {
		this.implName = implName;
	}

	public IXholon getInstance() {
		return instance;
	}

	public void setInstance(IXholon instance) {
		this.instance = instance;
	}
	
}

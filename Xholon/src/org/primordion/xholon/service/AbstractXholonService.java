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
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;

/**
 * An abstract base class that can be extended to build concrete services.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on August 29, 2009)
 */
public abstract class AbstractXholonService extends Xholon implements IXholonService {
	private static final long serialVersionUID = 2627137958616131550L;
	
	/**
	 * Whether or not this service has been activated.
	 * A service becomes activated once its getService() method is successfully called.
	 * Users or external classes can use this to decide whether the service is necessary
	 * in a given Xholon application.
	 */
	public boolean activated = false;
	
	// Prevent the act() methods from being called recursively.
	// A service is outside of time, so doesn't need to do any action every timestep.
	public void preAct() {}
	public void act() {}
	public void postAct() {}
	
	/**
	 * Give the service a chance to configure itself before it's returned to a client.
	 * For example, a service might need to lazily load components of itself.
	 * @see org.primordion.xholon.base.Xholon#getService(java.lang.String)
	 */
	public IXholon getService(String serviceName)
	{
		return null;
	}
	
	/**
	 * A service may need to locate another service.
	 * This method locates the ServiceLocatorService
	 * that can then be used to locate a specific service.
	 * By default, all services are right-siblings of the ServiceLocatorService,
	 * which is the assumption made here.
	 * As an example:
	 * <pre>IXholon otherService = getServiceLocatorService().getService("OtherService");</pre>
	 * @return
	 */
	public IXholon getServiceLocatorService()
	{
		// During initialization, an instance of XholonCreationService may not yet be part of the service chain,
		// and does not have a parent. If it needs to find another service, such as the ScriptService,
		// then it will need to search through the app.
		//
		if (getParentNode() == null) {
			return this.getApp().getService(XHSRV_SERVICE_LOCATOR);
		}
		return getParentNode().getFirstChild();
	}
	
	/**
	 * Create an instance of an encapsulated class that does all or part of the work of the service.
	 * @param implName Name of the class.
	 * @return An instance, or null.
	 */
	protected IXholon createInstance(String implName)
	{
		IXholon instance = null;
		// GWT
		System.out.println("AbstractXholonService.createInstance(" + implName);
		if ("org.primordion.xholon.io.About".equals(implName)) {
		  instance = new org.primordion.xholon.io.About();
		}
		else if ("org.primordion.xholon.base.XPath".equals(implName)) {
		  instance = new org.primordion.xholon.base.XPath();
		}
		else if ("org.primordion.xholon.service.xholonhelper.CutCopyPaste".equals(implName)) {
		  instance = new org.primordion.xholon.service.xholonhelper.CutCopyPaste();
		}
		else if ("org.primordion.xholon.io.ChartViewerGnuplot".equals(implName)) {
		  instance = new org.primordion.xholon.io.ChartViewerGnuplot();
		}
		else if ("org.primordion.xholon.io.ChartViewerGoogle2".equals(implName)) {
		  instance = new org.primordion.xholon.io.ChartViewerGoogle2();
		}
		else if ("org.primordion.xholon.io.ChartViewerC3".equals(implName)) {
		  instance = new org.primordion.xholon.io.ChartViewerC3();
		}
		else if ("org.primordion.xholon.io.ChartViewerNVD3".equals(implName)) {
		  instance = new org.primordion.xholon.io.ChartViewerNVD3();
		}
		
		else if ("org.primordion.xholon.io.HistogramViewerJFreeChart".equals(implName)) {
		  // TODO this is temporary
		  instance = new org.primordion.xholon.io.HistogramViewerGnuplot();
		}
		else if ("org.primordion.xholon.io.HistogramViewerGnuplot".equals(implName)) {
		  instance = new org.primordion.xholon.io.HistogramViewerGnuplot();
		}
		else if ("org.primordion.xholon.io.HistogramViewerGoogle2".equals(implName)) {
		  instance = new org.primordion.xholon.io.HistogramViewerGoogle2();
		}
		else if ("org.primordion.xholon.io.HistogramViewerD3".equals(implName)) {
		  instance = new org.primordion.xholon.io.HistogramViewerD3();
		}
		
		else if ("org.primordion.xholon.io.TreeViewerText".equals(implName)) {
		  instance = new org.primordion.xholon.io.TreeViewerText();
		}
		else if ("org.primordion.xholon.service.svg.SvgViewBrowser".equals(implName)) {
		  instance = new org.primordion.xholon.service.svg.SvgViewBrowser();
		}
		else if ("org.primordion.xholon.service.gist.Gist".equals(implName)) {
		  instance = new org.primordion.xholon.service.gist.Gist();
		}
		else if ("org.primordion.xholon.service.timeline.TimelineViewerChapJSON".equals(implName)) {
		  instance = new org.primordion.xholon.service.timeline.TimelineViewerChapJSON();
		}
		else if ("org.primordion.xholon.service.nosql.Neo4jRestApi".equals(implName)) {
		  instance = new org.primordion.xholon.service.nosql.Neo4jRestApi();
		}
		else if ("org.primordion.xholon.service.remotenode.PeerJS".equals(implName)) {
		  instance = new org.primordion.xholon.service.remotenode.PeerJS();
		}
		else {
		  //System.out.println("AbstractXholonService.createInstance( creating " + implName + " as DefaultXholonService");
		  instance = new org.primordion.xholon.service.DefaultXholonService();
		}
		
		/* GWT
		try {
			instance = (IXholon)Class.forName(implName).newInstance();
		} catch (InstantiationException e) {
			getLogger().error("Unable to instantiate " + implName + " class {}. " + e.getMessage());
		} catch (IllegalAccessException e) {
			getLogger().error("Unable to load " + implName + " class {}. " + e.getMessage());
		} catch (ClassNotFoundException e) {
			getLogger().error("Unable to load " + implName + " class " + implName);
		} catch (NoClassDefFoundError e) {
			getLogger().error("Unable to load " + implName + " class {}. " + e.getMessage());
		}*/
		return instance;
	}
	
	/**
	 * Services don't have annotations.
	 * @see org.primordion.xholon.base.Xholon#hasAnnotation()
	 */
	public boolean hasAnnotation()
	{
		return false;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#toXmlAttributes(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.XmlWriter)
	 */
	public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter)
	{
		// no attributes
	}
	
	public boolean isActivated() {
		return activated;
	}
	
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	
}

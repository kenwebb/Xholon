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

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.common.mechanism.CeXholonFramework;
import org.primordion.xholon.service.creation.ITreeNodeFactory;
//import org.primordion.xholon.service.creation.TreeNodeFactoryDynamic; // GWT
import org.primordion.xholon.service.creation.TreeNodeFactoryNew;
//import org.primordion.xholon.service.creation.TreeNodeFactoryStatic; // GWT

/**
 * This service acts as a factory to create (instantiate) Xholon and XholonClass nodes.
 * It is used in two different ways.
 * (1) It's used by IApplication.initialize(String) to bootstrap the application.
 * This is before the Service subtree exists.
 * In this case, IApplication has to directly instantiate an instance of XholonCreationService,
 * and then directly call getService() to get an instance of ITreeNodeFactory.
 * (2) Once the Service subtree has been created and configured,
 * then IApplication is free to call setInstance() to pass its instance of ITreeNodeFactory
 * to this instance of the XholonCreationService.
 * If IApplication does not call setInstance(), and if getService() is called,
 * then a new instance of ITreeNodeFactory will be returned.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 6, 2009)
 */
public class XholonCreationService extends AbstractXholonService {
	private static final long serialVersionUID = -2704777815998360539L;

	/**
	 * Name of the class that implements the service.
	 */
	private String implName = null;
	
	/**
	 * The singleton instance that implements ITreeNodeFactory.
	 */
	private transient ITreeNodeFactory instance = null;
	
	/**
	 * The instance of IApplication that this service belongs to.
	 */
	private transient IApplication app = null;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#postConfigure()
	 */
	public void postConfigure() {
		// the tree node factory already exists, so just go and get it
		//System.out.println("XholonCreationService postConfigure()");
		app = getApp();
		//System.out.println(app);
		if (app != null) {
			instance = app.getFactory();
			//System.out.println(instance);
			instance.appendChild(this);
		}
		super.postConfigure();
	}
	
	/**
	 * Get the singleton instance of a service class that implements ITreeNodeFactory.
	 * @see org.primordion.xholon.service.AbstractXholonService#getService(java.lang.String)
	 */
	public IXholon getService(String serviceName)
	{
		if (serviceName.equals(getXhcName())) {
			return getFactory();
		}
		else {
			return null;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getFactory()
	 */
	public ITreeNodeFactory getFactory()
	{
		if (instance == null) {
			createTreeNodeFactory();
		}
		return instance;
	}
	
	/**
	 * Create the tree node factory.
	 */
	protected void createTreeNodeFactory()
	{
		//if (getApp().isUseGwt()) {
			instance = new TreeNodeFactoryNew(
				getClass(getApp().getJavaXhClassName()),
				getClass(getApp().getJavaXhClassClassName()),
				getClass(getApp().getJavaActivityClassName()) );
		//}
		/* GWT
		else if (getApp().getTreeNodeFactoryDynamic()) {
			instance = new TreeNodeFactoryDynamic(
				getClass(getApp().getJavaXhClassName()),
				getClass(getApp().getJavaXhClassClassName()),
				getClass(getApp().getJavaActivityClassName()) );
			
		}
		else {
			instance = new TreeNodeFactoryStatic(
				getApp().getMaxXholons(),
				getApp().getMaxXholonClasses(),
				getApp().getMaxActivities(),
				getApp().getMaxStateMachineEntities(),
				getClass(getApp().getJavaXhClassName()),
				getClass(getApp().getJavaXhClassClassName()) );
		}*/
		// give the Xholon instance the same id as the XholonClass
		instance.setId(CeXholonFramework.TreeNodeFactoryCE);
		// set the XholonClass
		//xhc = getClassNode(getId());
		instance.appendChild(this);
	}
	
	/**
	 * Return the class given the class name.
	 * @param className The name of a Java class.
	 * @return The class.
	 */
	public Class<?> getClass(String className)
	{
	  System.out.println("XholonCreationService getClass():" + className);
		if (className == null) {return null;}
		Class<?> clazz = null;
		if ("org.primordion.xholon.base.XholonClass".equals(className)) {
	    clazz = (Class<?>)org.primordion.xholon.base.XholonClass.class;
	  }
	  else if ("org.primordion.cellontro.base.BioXholonClass".equals(className)) {
	    clazz = (Class<?>)org.primordion.cellontro.base.BioXholonClass.class;
	  }
	  else {
	    clazz = (Class<?>)app.findAppSpecificClass(className);
	  }
		/* GWT
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			logger.error("XholonCreationService getClass(): Unable to get class of " + className, e);
		}*/
		return clazz;
	}
	
	public String getImplName() {
		return implName;
	}

	public void setImplName(String implName) {
		this.implName = implName;
	}

	public ITreeNodeFactory getInstance() {
		return instance;
	}

	public void setInstance(ITreeNodeFactory instance) {
		this.instance = instance;
	}

	public IApplication getApp() {
		if (app == null) {
			app = super.getApp();
		}
		return app;
	}

	public void setApp(IApplication app) {
		this.app = app;
	}
	
}

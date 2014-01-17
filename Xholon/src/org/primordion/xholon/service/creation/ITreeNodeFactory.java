/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2005, 2006, 2007, 2008 Ken Webb
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

package org.primordion.xholon.service.creation;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.exception.XholonConfigurationException;

/**
 * A tree node factory creates and manages Xholon tree nodes.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.3 (Created on Feb 15, 2006)
 */
public interface ITreeNodeFactory extends IXholon {

	/**
	 * The default name of a static method that can be called on a class to return an instance of that class.
	 * ex: MyClass anInstance = MyClass.instance();
	 */
	public static final String INSTANCE_METHOD_NAME = "instance";
	
	/**
	 * Get number of treeNodes available for use.
	 * @param resourceType The type of node:
	 * Xholon, XholonClass, StateMachineEntity, or Activity.
	 * @return Number of available nodes.
	 */
	public abstract int getNumAvailNodes(int resourceType);

	/**
	 * Get a node instance of the specified Java class.
	 * @param xholonSubclass
	 * @return An IXholon instance.
	 * @throws XholonConfigurationException 
	 */
	public abstract IXholon getNode(Class xholonSubclass) throws XholonConfigurationException;
	
	/**
	 * Get a node instance of the specified Xholon Type.
	 * @param xhType
	 * @return An IXholon instance.
	 * @throws XholonConfigurationException 
	 */
	public abstract IXholon getNode(int xhType) throws XholonConfigurationException; // Xholon Type as specified in XholonClass
	
	/**
	 * Get a Xholon instance from the factory.
	 * @return The next available instance, or null.
	 * TODO Need to handle null (Q_EMPTY) condition gracefully.
	 * @throws XholonConfigurationException 
	 */
	public abstract IXholon getXholonNode() throws XholonConfigurationException;
	
	/**
	 * Get a Xholon instance from the factory, based on its full package name.
	 * @param implName The name of a Java class.
	 * @return An instance of that class, or null.
	 * @throws XholonConfigurationException 
	 */
	public abstract IXholon getXholonNode(String implName) throws XholonConfigurationException;

	/**
	 * Get a Xholon instance from the factory, based on its full package name.
	 * This method is intended to return singleton instances.
	 * @param implName The name of a Java class.
	 * @param methodName The name of a static method that will return an instance of the class.
	 * The default name of the method is INSTANCE_METHOD_NAME = "instance".
	 * @return An instance of that class, or null.
	 * @throws XholonConfigurationException
	 */
	public abstract IXholon getXholonNode(String implName, String methodName) throws XholonConfigurationException;
	
	/**
	 * Get a Xholon instance created in a scripting language, rather than Java.
	 * @param implName The name of a script or class in some scripting language, for example:
	 * <p><pre>lang:groovy:/Xholon/script/groovy/Test2.groovy</pre></p>
	 * <p><pre>lang:groovy:inline</pre></p>
	 * @return An instance of IXholon, or null.
	 * @throws XholonConfigurationException
	 */
	public abstract IXholon getXholonScriptNode(String implName) throws XholonConfigurationException;
	
	/**
	 * Get a Xholon instance of a Java class that is not a Xholon,
	 * that is that doesn't implement IXholon.
	 * @param clazz A Class object for the non-Xholon class.
	 * @return An instance of XholonProxy, or null. The proxy will wrap the non-Xholon onject.
	 * @throws XholonConfigurationException
	 */
	public abstract IXholon getNonXholonNode(Class clazz) throws XholonConfigurationException;
	
	/**
	 * Get a XholonClass instance from the factory.
	 * @return The next available instance, or null.
	 * @throws XholonConfigurationException 
	 */
	public abstract IXholonClass getXholonClassNode() throws XholonConfigurationException;

	/**
	 * Is a Java class with the specified name findable?
	 * Could it be loaded by the class loader?
	 * @param implName The full path name of a Java class.
	 * @return true or false
	 */
	public abstract boolean isClassFindable(String implName);
	
	/**
	 * Get a Activity node from the factory.
	 * @return The next available instance, or null.
	 * @throws XholonConfigurationException 
	 * @deprecated use implName attribute in the ClassDetails.xml file instead
	 */
	//public abstract Activity getActivityNode() throws XholonConfigurationException;

	/**
	 * Get a StateMaqchineEntity node from the factory.
	 * @return The next available instance, or null.
	 * @throws XholonConfigurationException 
	 * @deprecated use implName attribute in the ClassDetails.xml file instead
	 */
	//public abstract StateMachineEntity getStateMachineEntityNode() throws XholonConfigurationException;

	/**
	 * Get a GridEntity node from the factory.
	 * @return The next available instance, or null.
	 * @throws XholonConfigurationException 
	 * @deprecated use implName attribute in the ClassDetails.xml file instead
	 */
	//public abstract AbstractGrid getGridEntityNode() throws XholonConfigurationException;
	
	/**
	 * Return a TreeNode to the factory.
	 * @param node The TreeNode being returned.
	 */
	public abstract void returnTreeNode(IXholon node);

}
/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2007, 2008 Ken Webb
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

package org.primordion.xholon.base;

import java.util.List;

/**
 * Reflection supports a metadata capability.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on December 7, 2006)
 */
public interface IReflection {
	
	public static final String CONSTRUCTOR_ARG = "constructor-arg";
	
	/**
	 * Application uses this to set parameters.
	 * Parameters are typically specified in the _xhn.xml file.
	 * @param pName Parameter name.
	 * @param pValue Parameter value.
	 * @param obj The Application.
	 * @return Whether or not the parameter could be set.
	 */
	public abstract boolean setParam(String pName, String pValue, Object obj);
	
	/**
	 * Application uses this to get parameters.
	 * Parameters are typically specified in the _xhn.xml file.
	 * @param pName Parameter name.
	 * @param obj The Application.
	 * @return The value of the parameter, or null.
	 */
	public abstract String getParam(String pName, Object obj);
	
	/**
	 * Get all attributes that are accessible using a method that starts with "get".
	 * Only those get methods that have no input parameters are handled.
	 * ex: getVal() getRoleName() getUseDataPlotter()
	 * @param node An instance of IXholon.
	 * @return A two-dimensional array with a variable number of rows, each containing a Name/Value pair.
	 */
	public abstract Object[][] getAttributes(IXholon node);
	
	/**
	 * Get all application-specific attributes that are accessible using a method that starts with "get".
	 * Only those get methods that have no input parameters are handled.
	 * ex: getVal() getRoleName() getUseDataPlotter()
	 * @param node An instance of IXholon.
	 * @param returnAll Whether or not to return all attributes, rather than only those of immediate class.
	 * @param returnStatics Whether or not attributes returned by static methods should be returned.
	 * @param returnIfUnPaired Whether or not unpaired attributes will be returned.
	 * An unpaired attribute has a getter (getAbc() isAbc()) but no matching setter (setAbc(...)).
	 * @return A three-dimensional array with a variable number of rows,
	 * each containing a Name/Value/Type triplet.
	 */
	public abstract Object[][] getAppSpecificAttributes(IXholon node,
			boolean returnAll, boolean returnStatics, boolean returnIfUnPaired);
	
	/**
	 * XPath uses this to get the current value of a String attribute.
	 * @param xhnode A xholon instance.
	 * @param attrName The name of a String attribute.
	 * @return The current value of the String attribute.
	 */
	public abstract String getAttributeStringVal(IXholon xhNode, String attrName);
	
	/**
	 * Get the current value of an int attribute.
	 * This is currently used at configuration time to get the value of a constant,
	 * specifically, the values of port, FSM connection point, and signal id constants.
	 * These are all class variables defined as static, and possibly as final.
	 * @param xhnode A xholon instance.
	 * @param attrName The name of an int attribute.
	 * @return The current value of the int attribute.
	 */
	public abstract int getAttributeIntVal(IXholon xhNode, String attrName);
	
	/**
	 * Get the current value of an int attribute.
	 * This is currently used at configuration time to get the value of a constant,
	 * specifically, by StateMachineEntity,
	 * and by ReflectionJavaStandard.getAttributeIntVal(IXholon xhNode, String attrName)
	 * @param aClass A Java class, typically a Xh concrete class.
	 * @param attrName The name of an int attribute.
	 * @return The current value of the int attribute.
	 */
	public abstract int getAttributeIntVal(Class aClass, String attrName);
	
	/**
	 * Get the current value of a double attribute.
	 * @param xhnode A xholon instance.
	 * @param attrName The name of a double attribute.
	 * @return The current value of the double attribute.
	 */
	public abstract double getAttributeDoubleVal(IXholon xhNode, String attrName);
	
	/**
	 * Get the current value of a double attribute.
	 * @param aClass A Java class, typically a Xh concrete class.
	 * @param attrNameThe name of a double attribute.
	 * @return The current value of the double attribute.
	 */
	public abstract double getAttributeDoubleVal(Class aClass, String attrName);
	
	/**
	 * Get the first attribute of tNode, that has the "synthetic" modifier.
	 * If there is more than one synthetic attribute, only the first one found is returned.
	 * This is used in XholonAnonymous to locate the parentNode of tNode,
	 * where tNode is an instance of an anonymous inner class.
	 * @param tNode An instance of an anonymous inner class.
	 * @return The first synthetic attribute, or null if none is found.
	 */
	public abstract IXholon getAttributeSynthetic(IXholon tNode);

	/**
	 * Get the current value of a String attribute, using a getter.
	 * Currently, this is only used internally by getAttributeStringVal()
	 * to handle XPath requests.
	 * @param xhnode A xholon instance.
	 * @param attrName The name of a String attribute.
	 * @return The current value of the String attribute.
	 */
	public abstract String getAttributeStringVal_UsingGetter(Object tNode, String attrName);
	
	/**
	 * Set the value of a named attribute. The type could be any valid Java type.
	 * This is currently used at configuration time in Xholon.setVariableValue(String, int)
	 * to initialize the value of a variable owned by a specific xholon instance,
	 * and by XholonClass.configure().
	 * @param tNode A xholon instance.
	 * @param attrName The name of an attribute.
	 * @param val A String that contains the value that the attribute should be set to.
	 * @param valIx An index into the String. The location within the String where the value begins.
	 * @return The Java class type of the input val.
	 */
	public abstract int setAttributeVal(Object tNode, String attrName, String val, int valIx);
	
	/**
	 * Set the value of a named attribute, by calling a set method (setter).
	 * @param tNode A xholon instance.
	 * @param attrName The name of an attribute.
	 * @param val A String that contains the value that the attribute should be set to.
	 * @param valIx An index into the String. The location within the String where the value begins.
	 * @return The Java class type of the input val.
	 */
	public abstract int setAttributeVal_UsingSetter(Object tNode, String attrName, String val, int valIx);
	
	/**
	 * Set the value of a named attribute, by calling a set method (setter).
	 * @param tNode A xholon instance.
	 * @param attrName The name of an attribute, that has a corresponding setter.
	 * @param val A String that contains the value that the attribute should be set to.
	 * @param valIx An index into the String. The location within the String where the value begins.
	 * @param classType The Java class type, as defined in Misc.java, of the attribute identified by attrName.
	 * @return The Java class type of the input val.
	 */
	public abstract int setAttributeVal_UsingSetter(Object tNode, String attrName, String val, int valIx, int classType);
	
	/**
	 * Set the value of a named attribute, by calling a set method (setter).
	 * This is designed particularly to handle val arguments of type Map, List, Set,
	 * but should be useful for any type of val.
	 * @param tNode A xholon or Object instance
	 * @param attrName The name of an attribute, that has a corresponding setter.
	 * @param val A Map, List, Set or other Object instance.
	 * @param clazz The class or interface required by the set method.
	 * If clazz is null, then the runtime class of val will be used.
	 */
	public abstract void setAttributeVal_UsingSetter(Object tNode, String attrName, Object val, Class clazz);
	
	/**
	 * Set the value of an Object attribute.
	 * This is currently used to configure a port with name other than "port".
	 * @param tNode An IXholon instance that has a port.
	 * @param attrName The name of an IXholon port.
	 * @param val The IXholon that this port references.
	 * @return Whether or not the port could be set.
	 */
	public abstract boolean setAttributeObjectVal(IXholon tNode, String attrName, IXholon val);
	
	/**
	 * Set the value of an Object attribute, using a setter method.
	 * This is currently used to configure a port with name other than "port".
	 * @param tNode An IXholon instance that has a port.
	 * @param attrName The name of an IXholon port.
	 * @param val The IXholon that this port references.
	 * @return Whether or not the attribute could be set.
	 */
	public abstract boolean setAttributeObjectVal_UsingSetter(IXholon tNode, String attrName, IXholon val);
	
	/**
	 * Set the value of a boolean attribute.
	 * This is currently used at configuration time in Xholon.setVariableValue(String, int)
	 * to initialize the value of a variable owned by a specific xholon instance,
	 * and by XholonClass.configure().
	 * @param tNode An IXholon instance.
	 * @param attrName The name of a boolean attribute.
	 * @param val The value that this boolean attribute should be set to.
	 * @return Whether or not the attribute could be set.
	 */
	public abstract boolean setAttributeBooleanVal(IXholon tNode, String attrName, boolean val);
	
	/**
	 * Set the value of one slot in a named array attribute.
	 * This is currently used at configuration time in Xholon.setVariableValue(String, int)
	 * to initialize the value of a variable owned by a specific xholon instance,
	 * and by XholonClass.configure().
	 * @param tNode An IXholon instance.
	 * @param attrName The name of an array attribute.
	 * @param val A String that contains the value that the attribute should be set to.
	 * @param valIx An index into the String. The location within the String where the value begins.
	 * @param arraySubscript The Zero-indexed subscript of the slot within the array.
	 * @return The Java class type of the input val.
	 */
	public abstract int setAttributeArrayVal(
			IXholon tNode, String attrName, String val, int valIx, int arraySubscript);
	
	/**
	 * Set the value of one index of an Object attribute.
	 * This is currently used to configure a port with name other than "port".
	 * @param tNode An IXholon instance.
	 * @param attrName The name of an Object array attribute.
	 * @param val The value that this Object attribute should be set to.
	 * @param arraySubscript The Zero-indexed subscript of the slot within the array.
	 * @return Whether or not the attribute could be set.
	 */
	public abstract boolean setAttributeObjectArrayVal(IXholon tNode, String attrName, Object val, int arraySubscript);
	
	/**
	 * <p>Create an object using a constructor.
	 * The attributes, if any, will all be in &lt;Attribute_xxx> nodes that are children of the IXholon.
	 * For example:</p>
	 *<pre>&lt;Attribute_String roleName="constructor-arg">my value&lt;&lt;Attribute_String>
	 *&lt;Attribute_int roleName="constructor-arg">12345&lt;&lt;Attribute_int></pre>
	 * @param clazz The class of the object that is to be constructed.
	 * @param argsParent The xholon whose children describe the constructor arguments.
	 * @return A newly constructed object, or null.
	 */
	public abstract Object createObject_UsingConstructor(Class clazz, IXholon argsParent);
	
	/**
	 * Create an object using a constructor.
	 * @param clazz The class of the object that is to be constructed.
	 * @param val An array of argument values.
	 * @param argType An array of argument types.
	 * @return A newly constructed object, or null.
	 */
	public abstract Object createObject_UsingConstructor(Class clazz, Object[] val, Class[] argType);
	
	/**
	 * Get a list of all ports exiting from this xholon.
	 * @param xhNode An instance of IXholon.
	 * @param includeUnboundPorts Whether or not to include unbound ports, those whose reference is null.
	 * @return A list of the IXholon instances referenced by ports.
	 * The list will contain duplicate entries if more than one port references the same IXholon instance.
	 * The IXholons firstChild, nextSibling, and parentNode nodes are NOT included in the list.
	 * If there are no ports, then an empty List is returned.
	 */
	public abstract List getAllPorts(IXholon xhNode, boolean includeUnboundPorts);
	
	/**
	 * Get a list of ports exiting from this xholon, with the specified port name.
	 * @param xhNode An instance of IXholon.
	 * @param includeUnboundPorts Whether or not to include unbound ports, those whose reference is null.
	 * @param portName The name of the port. This is the name of an attribute in xhNode that references an IXholon.
	 * @return A list of the IXholon instances referenced by ports with the specified name.
	 * The list will contain duplicate entries if more than one port references the same IXholon instance.
	 * If there are no ports, then an empty List is returned.
	 * If the port is a scalar, then there will be either 0 or 1 entries in the List.
	 * If the port is an arraay, then there will be 0 or more entries in the List.
	 */
	public abstract List getAllPorts(IXholon xhNode, boolean includeUnboundPorts, String portName);
	
	/**
	 * Is this an attribute in the specified IXholon instance?
	 * @param tNode An IXholon instance.
	 * @param attrName The name of a possible attribute in that IXholon instance.
	 * @param clazz The Java class of the possible attribute.
	 * @return true or false
	 */
	public abstract boolean isAttribute(IXholon tNode, String attrName, Class clazz);
	
	/**
	 * Is this an attribute in the specified Object instance?
	 * @param tNode An Object instance, something other than an IXholon.
	 * @param attrName The name of a possible attribute in that Object instance.
	 * @param clazz The Java class of the possible attribute.
	 * @return true or false
	 */
	public abstract boolean isAttribute(Object obj, String attrName, Class clazz);
}

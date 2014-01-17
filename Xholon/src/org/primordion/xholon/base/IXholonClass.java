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

package org.primordion.xholon.base;

import java.util.List;

//import javax.xml.namespace.QName;
import org.primordion.xholon.io.xml.namespace.QName;

/**
 * A XholonClass is a concrete subclass of TreeNode.
 * A primary role of a XholonClass instance is to provide configuration instructions
 * for Xholon instances of its type.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.2 (Created on Oct 21, 2005)
 */
public interface IXholonClass extends IXholon {

	//                         Xholon Type
	// 
	//	 Xholon Types (see Webb & White, SAC 2004 paper)
	//	 There are three attributes that make up a Xholon type:
	//	   Beh whether or not the Xholon has Behavior
	//	   Fgs whether or not the Xholon has Fine-grained Structure
	//	   Con whether or not the Xholon is a container for other Xholons
	/** Mechanism - Composite Structure. */
	public static final int Xhtypexxxxxxxxx = 0;
	/** Pure Container. */
	public static final int XhtypexxxxxxCon = 1; // Pure Container
	/** Pure Passive Object. */
	public static final int XhtypexxxFgsxxx = 2; // Pure Passive Object
	/** Passive Object + Container. */
	public static final int XhtypexxxFgsCon = 3;
	/** Pure Active Object. */
	public static final int XhtypeBehxxxxxx = 4; // Pure Active Object
	/** Active Object + Container. */
	public static final int XhtypeBehxxxCon = 5;
	/** Active Object + Passive Object. */
	public static final int XhtypeBehFgsxxx = 6;
	/** Active Object + Passive Object + Container. */
	public static final int XhtypeBehFgsCon = 7;

	//	 Aliases for the three types actually used in this system
	public static final int XhtypeNone = Xhtypexxxxxxxxx;
	public static final int XhtypePureContainer = XhtypexxxxxxCon;
	public static final int XhtypePurePassiveObject = XhtypexxxFgsxxx;
	public static final int XhtypePureActiveObject = XhtypeBehxxxxxx;

	//   Other possible values for xhType (use multiples of 8)
	// 
	// non-UML Activity
	// used in AntSystem and may be used in other applications that use the Actvity class
	public static final int XhtypeActivity = 8;

	// Container with active behavior during Configuration that may involve setting up a relay port.
	public static final int XhtypeConfigContainer = 16;

	// State Machine entities
	public static final int XhtypeStateMachineEntity = 24;
	public static final int XhtypeStateMachineEntityActive = XhtypeStateMachineEntity
			+ XhtypePureActiveObject;
	
	// Ctrnn entities (Continuous-Time Recurrent Neural Network)
	public static final int XhtypeCtrnnEntity = 32;
	public static final int XhtypeCtrnnEntityActive = XhtypeCtrnnEntity + XhtypePureActiveObject;
	public static final int XhtypeCtrnnEntityActivePassive = XhtypeCtrnnEntity + XhtypeBehFgsxxx;
	
	// Membrane Computing entities
	public static final int XhtypeMemCompEntity = 40;
	public static final int XhtypeMemCompEntityActive = XhtypeMemCompEntity + XhtypePureActiveObject;
	public static final int XhtypeMemCompEntityPassive = XhtypeMemCompEntity + XhtypePurePassiveObject;
	public static final int XhtypeMemCompEntityActiveContainer = XhtypeMemCompEntity + XhtypeBehxxxCon;
	
	// Brane Calculus entities
	public static final int XhtypeBraneCalcEntity = 48;
	public static final int XhtypeBraneCalcEntityActive = XhtypeBraneCalcEntity + XhtypePureActiveObject;
	public static final int XhtypeBraneCalcEntityPassive = XhtypeBraneCalcEntity + XhtypePurePassiveObject;
	
	// Instances of the Java and xholon Port class
	public static final int XhtypePort = 56;
	
	// Grid entities
	public static final int XhtypeGridEntity = 64;
	public static final int XhtypeGridEntityActive = XhtypeGridEntity + XhtypePureActiveObject;
	public static final int XhtypeGridEntityActivePassive = XhtypeGridEntity + XhtypeBehFgsxxx;
	
	/**
	 * Default app-specific authority of the base Uniform Resource Identifier (URI),
	 * for use with getUri() setUri(String). 
	 */
	public static final String URI_APPSPECIFIC_AUTH_DEFAULT = "http://dbpedia.org/";

	/**
	 * Default app-specific path of the Uniform Resource Identifier (URI),
	 * for use with getUri() setUri(String).
	 */
	public static final String URI_APPSPECIFIC_PATH_DEFAULT = "resource/";

	/**
	 * Default app-specific Uniform Resource Identifier (URI),
	 * for use with getUri() setUri(String).
	 */
	public static final String URI_APPSPECIFIC_DEFAULT = URI_APPSPECIFIC_AUTH_DEFAULT + URI_APPSPECIFIC_PATH_DEFAULT;
	
	//                               Mechanism (Formalism)
	// 
	// Mechanism (Formalism) types. Each mechanism corresponds to a Java class.
	/** Composition mechanism, implemented in Java by IXholon and XholonWithPorts subclass. */
	//public static final int MtypeComposition = XhtypeNone;
	/** Activity mechanism, implemented in Java by IActivity. */
	//public static final int MtypeActivity = XhtypeActivity;
	/** an internal mechanism */
	//public static final int MtypeConfigContainer = XhtypeConfigContainer;
	/** State Machine mechanism. */
	//public static final int MtypeStateMachineEntity = XhtypeStateMachineEntity;
	/** Ctrnn mechanism. */
	//public static final int MtypeCtrnnEntity = XhtypeCtrnnEntity;
	/** Membrane Computing */
	//public static final int MtypeMemCompEntity = XhtypeMemCompEntity;
	/** Brane Calculus */
	//public static final int MtypeBraneCalcEntity = XhtypeBraneCalcEntity;
	/** Port */
	//public static final int MtypePort = XhtypePort;
	/** Grid */
	//public static final int MtypeGridEntity = XhtypeGridEntity;
	
	/**
	 * Mask that can be used to convert Xholon Type into a Mechanism Type.
	 * Convert lower order 3 bits to 000.
	 */
	//public static final int MTYPE_MASK = Integer.MAX_VALUE - 7;
	
	/**
	 * Set name of this XholonClass.
	 * @param className The new name.
	 */
	public abstract void setName(String className);
	
	/**
	 * Get the name of this IXholonClass as a QName.
	 * @return An instance of javax.xml.namespace.QName.
	 */
	public abstract QName getQName();
	
	/**
	 * Get the name of this IXholonClass in the format: prefix:name .
	 * The prefix is the XML namespace default prefix.
	 * If there is no prefix, then just the name is returned without the colon.
	 * @return A prefixed name.
	 */
	public abstract String getPrefixedName();
	
	/**
	 * Get the XML namespace default prefix.
	 * @return A prefix, or "".
	 */
	public abstract String getPrefix();
	
	/**
	 * Get only the local part of the name of this IXholonClass, without the prefix.
	 * @return The local part of the name.
	 */
	public abstract String getLocalPart();
	
	/**
	 * Set whether the XholonClass name must be prefixed.
	 * @param prefixed
	 */
	public abstract void setPrefixed(boolean prefixed);
	
	/**
	 * Get whether the XholonClass name must be prefixed.
	 * @return true or false
	 */
	public abstract boolean isPrefixed();
	
	/**
	 * Set navigation information.
	 * @param navInfo A configuration string describing ports, attributes
	 * and commands for this XholonClass and its Xholon instances.
	 */
	public abstract void setNavInfo(String navInfo);
	
	/**
	 * Get navigation information.
	 * @return A configuration string describing ports, attributes
	 */
	public abstract String getNavInfo();
	
	/**
	 * Set the list of information about each potential port.
	 * @param portInformation An array of PortInformation instances.
	 */
	public abstract void setPortInformation(List portInformation);
	
	/**
	 * Get information about each potential port,
	 * including the fieldName, fieldNameIndex, and xpathExpression.
	 * The reffedNode variable will be returned as null,
	 * unless it was set when calling setPortInformation().
	 * @return A list of PortInformation instances, or an empty list if there are no ports.
	 */
	public abstract List getPortInformation();

	/**
	 * Get xholon type. (ex: XhtypePureContainer, XhtypePurePassiveObject, XhtypePureActiveObject)
	 * @return The Xholon type.
	 */
	public abstract int getXhType();
	
	/**
	 * Get the name of the xholon type. (ex: "XhtypePureContainer", "XhtypePurePassiveObject", "XhtypePureActiveObject")
	 * @return The name of the xholon type.
	 */
	public abstract String getXhTypeName();
	
	/**
	 * Set xholon type.
	 * @param xhType The new Xholon type.
	 * (ex: XhtypePureContainer, XhtypePurePassiveObject, XhtypePureActiveObject)
	 */
	public abstract void setXhType(int xhType);
	
	/**
	 * Get the name of the Java class that implements this Xholon class.
	 * <p>ex: org.primordion.user.app.APartOfTheSystem</p>
	 * @return The full package name of a Java class, or null if the name is unknown.
	 */
	public abstract String getImplName();
	
	/**
	 * Set the name of the Java class that implements this Xholon class.
	 * <p>ex: org.primordion.user.app.APartOfTheSystem</p>
	 * @param implName The full package name of a Java class.
	 */
	public abstract void setImplName(String implName);
	
	/**
	 * 
	 * @return The default content of instances of this Xholon class.
	 */
	public abstract String getDefaultContent();
	
	/**
	 * 
	 * @param defaultContent The default content of instances of this Xholon class.
	 */
	public abstract void setDefaultContent(String defaultContent);
	
	/**
	 * Get the value of the prototype object, which is a JavaScriptObject.
	 */
	public abstract Object getPrototype();

	/*
	 * Set the value of the prototype object, which is a JavaScriptObject.
	 */
	public abstract void setPrototype(Object prototype);
	
	/**
	 * If the JavaScript code starts with a constructor,
	 * then create a protoype object,
	 * and return a String that can be used to create an object that derives from that prototype.
	 * Else, return null.
	 * @param jsCode JavaScript code.
	 * @return JavaScript code, or null.
	 */
	public abstract String prototype(String jsCode);
	
	/**
	 * Get the mechanism type for this xholon class.
	 * @return A mechanism class.
	 */
	//public abstract int getMechanismType();
	
	/**
	 * Get the name of the mechanism type for this xholon class.
	 * @return The name of a mechanism type.
	 */
	//public abstract String getMechanismName();
	
	/**
	 * Does this instance of XholonClass have the specified ancestor
	 * somewhere in its inheritance tree.
	 * @param tnName the name of the searched-for ancestor
	 * @return true or false
	 */
	public abstract boolean hasAncestor(String tnName);
	
	/**
	 * Get the mechanism that this IXholonClass belongs to.
	 * @return An instance of IMechanism, or null.
	 */
	public abstract IMechanism getMechanism();
	
	/**
	 * Set the mechanism that this IXholonClass belongs to.
	 * @param mechanism An instance of IMechanism.
	 */
	public abstract void setMechanism(IMechanism mechanism);
	
	/**
	 * Set the mechanism that this IXholonClass belongs to.
	 * @param name The name/roleName of the mechanism.
	 * @param namespaceUri A namespace URI that uniquely identifies the mechanism.
	 * @param defaultPrefix 
	 * @param rangeStart 
	 */
	public abstract void setMechanism(String name, String namespaceUri, String defaultPrefix, int rangeStart);
	
	/**
	 * Does this instance of XholonClass have the specified ancestor
	 * somewhere in its inheritance tree.
	 * @param tnId the ID of the searched-for ancestor
	 * @return true or false
	 */
	public abstract boolean hasAncestor(int tnId);
	
	/**
	 * Get configuration instructions.
	 * @return The instructions.
	 */
	public abstract String getConfigurationInstructions();

	/**
	 * Does this instance of IXholonClass have configuration instructions?
	 * @return true or false
	 */
	public abstract boolean hasConfigurationInstructions();
	
	/**
	 * Get the optional child superClass.
	 * If an IXholonClass is a container such as a XholonList,
	 * then it may know the default type (superClass) of its child instances.
	 * @return The default type of child nodes, or null.
	 */
	public abstract String getChildSuperClass();
	
	/**
	 * Set the optional child superClass.
	 * @param childSuperClass
	 */
	public abstract void setChildSuperClass(String childSuperClass);
	
	/*
	 * @see org.primordion.xholon.base.IXholon#preOrderPrint()
	 */
	public abstract void preOrderPrint(int level);

	/*
	 * @see org.primordion.xholon.base.IXholon#inOrderPrint(int)
	 */
	public abstract void inOrderPrint(int level);

	/*
	 * @see org.primordion.xholon.base.IXholon#postOrderPrint(int)
	 */
	public abstract void postOrderPrint(int level);

}

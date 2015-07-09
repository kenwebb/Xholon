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

import com.google.gwt.core.client.JavaScriptObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.primordion.xholon.io.xml.namespace.QName;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXml2Xholon;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.io.xml.Xml2XholonClass;
import org.primordion.xholon.util.Misc;
import org.primordion.xholon.util.MiscRandom;

/**
 * A XholonClass is a concrete subclass of Xholon.
 * A primary role of a XholonClass instance is to provide configuration instructions
 * for Xholon instances of its type.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.1 (Created on Jun 8, 2005)
 */
public class XholonClass extends Xholon implements IXholonClass, IDecoration {
	private static final long serialVersionUID = -4398615727197178752L;

	/** Name of the Xholon class. */
	private String name = null;
	
	/** Original navigation information, as read in from attributes file. */
	private transient String navInfo = null;
	
	/** Port information derived from navInfo. */
	private transient List<PortInformation> portInformation = null;
	
	/** Navigation information. Stripped of V variables. To be given to Xholon instances. */
	private String navInfoNoVariables = null;
	
	/** Encapsulates two separate things: (1) Xholon Type, (2) Mechanism (Formalism). */
	private int xhType = XhtypeNone;
	
	/** Name of the Java class that implements instances of this Xholon class. */
	private String implName = null;
	
	/**	The instance of Application that contains the entire model. */
	private IApplication app = null;
	
	/** The mechanism that this XholonClass belongs to. */
	private transient IMechanism mechanism = null;
	
	/**
	 * Optional object that can contain decorations such as color, font, icon, toolTip.
	 */
	private IDecoration decoration = null;
	
	/**
	 * Whether the XholonClass name must be prefixed.
	 * This is true if the XML that specified that class used a prefixed form.
	 */
	private boolean prefixed = false;
	
	/**
	 * The default content of instances of this Xholon class.
	 * It should be optional whether or not instances elect to use this.
	 */
	private String defaultContent = null;
	
	/**
	 * JavaScript prototype that might be created by createPrototype()
	 */
	private JavaScriptObject prototype = null;
	
	/**
	 * The default type (superClass) of a node's child instances.
	 * This might be used by a container such as a XholonList to specify the types of nodes it contains.
	 * Whether or not child nodes are actually of that type, is optional.
	 * ex: &lt;Reactions superClass="Transitions" childSuperClass="Reaction"/>
	 */
	private String childSuperClass = null;
	
	private transient String sourceCodeURLPrefix = "http://xholon.cvs.sf.net/viewvc/xholon/xholon/src/";
	private transient String sourceCodeURLSuffix = ".java?view=markup";
	private transient boolean shouldShowSourceCode = false;
	
	/**
	 * Constructor.
	 */
	public XholonClass() {}
	
	/* 
	 * @see org.primordion.xholon.base.IXholonClass#initialize()
	 */
	public void initialize()
	{
		super.initialize();
		id = 0;
		name = null;
		navInfo = null;
		navInfoNoVariables = null;
		xhType = XhtypeNone;
	}
	
	/**
	 * Get the next available id for assignment to an instance of XholonClass.
	 * @return A unique ID.
	 */
	protected int getNextId()
	{
		return getApp().getNextXholonClassId();
	}
	
	/**
	 * Set the next available id back to 0.
	 * This should only be called when loading a new model in, to replace an existing one.
	 */
	protected void resetNextId()
	{
		getApp().resetNextXholonClassId();
	}
	
	/**
	 * Get name, unique within this application, of this XholonClass instance.
	 * @return The unique name of this XholonClass instance.
	 */
	public String getName()
	{
		if (isPrefixed()) {
			return getPrefixedName();
		}
		else {
			return name;
		}
	}

	/* 
	 * @see org.primordion.xholon.base.IXholonClass#setName(java.lang.String)
	 */
	public void setName( String className )
	{
		name = className;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholonClass#getQName()
	 */
	public QName getQName()
	{
		IMechanism mech = getMechanism();
		if (mech == null) {
			return new QName(name);
		}
		else {
			return new QName(getMechanism().getNamespaceUri(), name, getMechanism().getDefaultPrefix());
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholonClass#getPrefixedName()
	 */
	public String getPrefixedName()
	{
		return getPrefix() + ":" + getLocalPart();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholonClass#getPrefix()
	 */
	public String getPrefix()
	{
		return getMechanism().getDefaultPrefix();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholonClass#getLocalPart()
	 */
	public String getLocalPart()
	{
		return name;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholonClass#setPrefixed(boolean)
	 */
	public void setPrefixed(boolean prefixed)
	{
		this.prefixed = prefixed;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholonClass#isPrefixed()
	 */
	public boolean isPrefixed()
	{
		return prefixed;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholonClass#getNavInfo()
	 */
	public String getNavInfo()
	{
		return navInfo;
	}
	
	/* 
	 * @see org.primordion.xholon.base.IXholonClass#setNavInfo(java.lang.String)
	 */
	public void setNavInfo(String navInfo) {
		this.navInfo = navInfo;
		navInfoNoVariables = navInfo;
	}
	
	/**
	 * TODO this method may not correctly handle all cases
	 * field name index may be a number or a String (ex: 1 P_PARTNER)
	 * @see org.primordion.xholon.base.IXholonClass#getPortInformation()
	 */
	public List<PortInformation> getPortInformation() {
	  //System.out.println("XholonClass getPortInformation1 " + portInformation + " " + this);
		if (portInformation == null) {
			portInformation = new ArrayList<PortInformation>();
			// xpath expression is bracketed by () or by "", depending on whether #xpointer(...) is used in the app
			char xpathStartChar = '('; // character just before start of xpath expression
			char xpathEndChar = ')'; // character just after end of xpath expression
			if (navInfoNoVariables != null) {
			  //consoleLog("navInfoNoVariables: " + navInfoNoVariables);
			  if (navInfoNoVariables.indexOf("#xpointer(") == -1) {
			    //consoleLog("navInfoNoVariables.indexOf(\"#xpointer(\") == -1");
			    xpathStartChar = '"';
			    xpathEndChar = '"';
			  }
				String[] str = navInfoNoVariables.split(String.valueOf(IInheritanceHierarchy.NAVINFO_SEPARATOR));
				for (int i = 0; i < str.length; i++) {
					// ex: PaccelerationDueToGravity[-1]="#xpointer(../../AccelerationDueToGravity)"Pvelocity[-1]="#xpointer(Velocity)"
					// ex: port[SamplePort]="#xpointer
					String fieldName = null;
					String fieldNameIndexStr = null;
					String xpathExpression = null;
					//consoleLog(str[i]);
					if (str[i].indexOf('[') == -1) {
						if (str[i].startsWith("")) { // // Gmt
							continue;
						}
						else {
							fieldName = "UNKNOWN_FIELD_NAME";
							fieldNameIndexStr = "-1";
							xpathExpression = "";
						}
					}
					else {
						if (str[i].startsWith("P")) {
							// skip the 'P' which means this is a non-"port" port
							fieldName = str[i].substring(1, str[i].indexOf('['));
						}
						else {
							fieldName = str[i].substring(0, str[i].indexOf('['));
						}
						fieldNameIndexStr = str[i].substring(str[i].indexOf('[')+1, str[i].indexOf(']'));
						int xpathStartPos = str[i].indexOf(xpathStartChar);
						if (xpathStartPos != -1) {
						  xpathStartPos++;
						  int xpathEndPos = str[i].indexOf(xpathEndChar, xpathStartPos);
						  if (xpathEndPos != -1) {
						    xpathExpression = str[i].substring(xpathStartPos, xpathEndPos).trim();
						  }
						}
						//xpathExpression = str[i].substring(str[i].indexOf(xpathStartChar)+1,
						//  str[i].indexOf(xpathEndChar)).trim();
						//consoleLog("xpathExpression: " + xpathExpression);
					}
					portInformation.add(new PortInformation(
							fieldName,
							//Integer.parseInt(fieldNameIndex),
							fieldNameIndexStr,
							null,
							xpathExpression));
				}
				//consoleLog("end");
			}
		}
		//System.out.println("XholonClass getPortInformation2 " + portInformation.size());
		return portInformation;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholonClass#setPortInformation(org.primordion.xholon.base.PortInformation[])
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setPortInformation(List portInformation) {
		this.portInformation = portInformation;
	}
	

	/*
	 * @see org.primordion.xholon.base.IXholonClass#getXhType()
	 */
	public int getXhType() {
		if (xhType == XhtypeNone) {
			//logger.warn("XholonClass.getXhType() == XhtypeNone: " + this.getName());
			// if a XholonClass has no xhType, it should take the xhType of its parent
			XholonClass iXhc = this;
			while ((!iXhc.isRootNode()) && (iXhc.xhType == XhtypeNone)) {
				if (iXhc.isRootNode()) {break;}
				iXhc = (XholonClass)iXhc.getParentNode();
			}
			setXhType(iXhc.xhType);
		}
		return xhType;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholonClass#setXhType(int)
	 */
	public void setXhType(int xhType) {this.xhType = xhType;}
	
	/*
	 * @see org.primordion.xholon.base.IXholonClass#getXhTypeName()
	 */
	public String getXhTypeName()
	{
		return getXhTypeName(getXhType());
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getXhcName()
	 */
	public String getXhcName()
	{
		return getName();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getUri()
	 */
	public String getUri() {
		StringBuilder uri = new StringBuilder();
		if ((id > 0) && (id < IMechanism.MECHANISM_ID_START)) {
			// this is an app-specific Xholon class
			uri.append(URI_APPSPECIFIC_DEFAULT)
			.append(this.getName());
		}
		else {
			// this is a xholon class from a named Xholon mechanism
			uri.append(getMechanism().getNamespaceUri())
			.append("/")
			.append(this.getName());
		}
		return uri.toString();
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholonClass#getMechanism()
	 */
	public IMechanism getMechanism()
	{
		if (mechanism == null) {
			return app.getDefaultMechanism();
		}
		else {
			return mechanism;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholonClass#setMechanism(org.primordion.xholon.base.IMechanism)
	 */
	public void setMechanism(IMechanism mechanism)
	{
		this.mechanism = mechanism;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholonClass#setMechanism(java.lang.String, java.lang.String, java.lang.String, int)
	 */
	public void setMechanism(String name, String namespaceUri, String defaultPrefix, int rangeStart)
	{
		if ((namespaceUri == null) || (namespaceUri.length() == 0)) {
			return;
		}
		IMechanism mechRoot = app.getMechRoot(); // mechRoot is XhMechanisms
		IMechanism mechNode = mechRoot.findMechanism(namespaceUri);
		if (mechNode == null) {
			// create a new Mechanism as a child of UserMechanisms
			if (defaultPrefix == null) {
				defaultPrefix = "";
			}
			mechNode = mechRoot.createNewUserMechanism(
					name, namespaceUri, defaultPrefix, rangeStart, IMechanism.RANGE_NULL, getApp());
		}
		setMechanism(mechNode);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#isContainer()
	 */
	public boolean isContainer()
	{
		return (getXhType() & XhtypePureContainer) == XhtypePureContainer;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#isActiveObject()
	 */
	public boolean isActiveObject()
	{
		return (getXhType() & XhtypePureActiveObject) == XhtypePureActiveObject;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#isPassiveObject()
	 */
	public boolean isPassiveObject()
	{
		return (getXhType() & XhtypePurePassiveObject) == XhtypePurePassiveObject;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholonClass#getImplName()
	 */
	public String getImplName() {return implName;}
	
	/*
	 * @see org.primordion.xholon.base.IXholonClass#setImplName(java.lang.String)
	 */
	public void setImplName(String implName) {this.implName = implName;}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getApp()
	 */
	public IApplication getApp() {return app;}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setApp(org.primordion.xholon.app.IApplication)
	 */
	public void setApp(IApplication app)
	{
		this.app = app;
	}
	
	/**
	 * Get the name of this xh type.
	 * @return The official parsable name of the specified xh type.
	 */
	public static String getXhTypeName(int xhType)
	{
		switch(xhType) {
		case XhtypePureContainer:      // 1
			return "XhtypePureContainer";
		case XhtypePurePassiveObject:  // 2
			return "XhtypePurePassiveObject";
		case XhtypexxxFgsCon:          // 3
			return "XhtypexxxFgsCon";  // Passive Object + Container
		case XhtypePureActiveObject:   // 4
			return "XhtypePureActiveObject";
		case XhtypeBehxxxCon:          // 5
			return "XhtypeBehxxxCon";  // Active Object + Container
		case XhtypeBehFgsxxx:          // 6
			return "XhtypeBehFgsxxx";  // Active Object + Passive Object
		case XhtypeBehFgsCon:          // 7
			return "XhtypeBehFgsCon";  // Active Object + PassiveObject + Container
		case XhtypePort:
			return "XhtypePort";
		case XhtypeNone:
		default:
			return "XhtypePureContainer";
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholonClass#hasAncestor(java.lang.String)
	 */
	public boolean hasAncestor(String tnName) {
		IXholonClass node = this;
		while (node != null) {
			if (node.getName().equals(tnName)) {
				return true;
			}
			if (node.isRootNode()) {break;}
			node = (IXholonClass)node.getParentNode();
		}
		return false;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholonClass#hasAncestor(int)
	 */
	public boolean hasAncestor(int tnId) {
		IXholonClass node = this;
		while (node != null) {
			if (node.getId() == tnId) {
				return true;
			}
			if (node.isRootNode()) {break;}
			node = (IXholonClass)node.getParentNode();
		}
		return false;
	}
	
	/* 
	 * @see org.primordion.xholon.base.IXholonClass#configure()
	 */
	public void configure() {
		String instructions = navInfo;
		boolean strippedClassVariables = false;
		int len = 0;
		if (instructions != null) {
			len = instructions.length();
		}
		int instructIx = 0;
		char inChar = '\0';
		int intVal = 0;
		int startPos = 0;
		IReflection reflect = ReflectionFactory.instance();
		int classType = Misc.JAVACLASS_UNKNOWN;
		while (instructIx < len) {
			switch( instructions.charAt(instructIx)) {
			
			// these types are ignored
			case 'p': // port
			case 'P': // port with another name
			case 'c': // cnpt (Connection Point)
			case ContainmentData.CD_REPLICATION: // replication (Port replication)
				instructIx++;
				while ((instructIx < len) && (instructions.charAt(instructIx) != IInheritanceHierarchy.NAVINFO_SEPARATOR)) {
					instructIx++;
				}
				instructIx++;
				break;
				
			case IInheritanceHierarchy.NAVINFO_SEPARATOR: // separates p, C, A, O
				instructIx++;
				break;

			case ContainmentData.CD_CONFIG: // 'C' configuration instruction for Xholon instances
				// examples:
				//    CGmt get global position in grid (row, col)
				//    Cf1 Cf2 Cf3 function argument (GP)
				while (instructIx < len) {
					if (instructions.charAt(instructIx) == IInheritanceHierarchy.NAVINFO_SEPARATOR) {
						instructIx++; // skip
						break;
					}
					else if (strippedClassVariables) { // add back if attributes et al have been stripped
						navInfoNoVariables += instructions.charAt(instructIx);
					}
					instructIx++; // skip
				}
				break;
				
			case 'A': // get initial value of attribute
				if (!strippedClassVariables) {
					// exclude these static class variables when pass navInfo to Xholon instances
					navInfoNoVariables = instructions.substring(0, instructIx);
					strippedClassVariables = true;
				}
				instructIx++;
				String varName = null;
				startPos = instructIx;
				while ((instructions.charAt(instructIx) != '[')
						&& (instructions.charAt(instructIx) != '=')) {
					instructIx++;
				}
				varName = instructions.substring(startPos, instructIx);
				// get array subscript, if this is an array variable
				int arraySubscript = -1;
				if (instructions.charAt(instructIx) == '[') {
					instructIx++; // point to start position of class name
					startPos = instructIx;
					while (instructions.charAt(instructIx) != ']') {
						instructIx++;
					}
					arraySubscript = Misc.atoi(instructions, startPos);
					instructIx++; // point to position just after ']'
				}
				instructIx++; // point just after the =
				if (instructions.charAt(instructIx) == ContainmentData.CD_RANDOM) { // random
					boolean b = (MiscRandom.getRandomInt(0, 2) == 0) ? false : true;
					reflect.setAttributeBooleanVal(this, varName, b);
					instructIx++;
				}
				else if (arraySubscript == -1) { // NOT an element in an array
					classType = reflect.setAttributeVal(this, varName, instructions, instructIx);
				}
				else { // IS an element in an array
					classType = reflect.setAttributeArrayVal(this, varName, instructions, instructIx, arraySubscript);
				}
				
				if (classType == Misc.JAVACLASS_boolean) {
					inChar = instructions.charAt(instructIx);
					if ((inChar == 't') || (inChar == 'T')) { // true
						instructIx += 4;
					}
					else if ((inChar == 'f') || (inChar == 'F')) { // false
						instructIx += 5;
					}
					else {
						// don't increment instructIx; this is an error
					}
				}
				else { // a number
					inChar = instructions.charAt(instructIx);
					intVal = Misc.getNumericValue(inChar);
					while (Misc.isdigit(intVal) || inChar == '-') {
						instructIx++;
						if (instructIx >= len) {
							break; // exit loop if reach end of string
						}
						inChar = instructions.charAt(instructIx);
						intVal = Misc.getNumericValue(inChar);
					}
				}
				break;
			
			default:
				if (Msg.errorM) println( "XholonClass configure: unrecognized char ["
						+ instructions.charAt(instructIx) + "]");
				instructIx++;
				break;
			} // end switch
		} // end while
		
		XholonClass iXhc = this;
		while ((!iXhc.isRootNode()) && (iXhc.xhType == XhtypeNone)) {
			if (iXhc.isRootNode()) {break;}
			iXhc = (XholonClass)iXhc.getParentNode();
		}
		setXhType(iXhc.xhType);
		
		// execute recursively
		if (firstChild != null) {
			firstChild.configure();
		}
		if (nextSibling != null) {
			nextSibling.configure();
		}
	}

	/* 
	 * @see org.primordion.xholon.base.IXholonClass#getConfigurationInstructions()
	 */
	public String getConfigurationInstructions()
	{
		return (navInfoNoVariables == null ? "" : navInfoNoVariables);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholonClass#hasConfigurationInstructions()
	 */
	public boolean hasConfigurationInstructions()
	{
		return !(navInfoNoVariables == null);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholonClass#getChildSuperClass()
	 */
	public String getChildSuperClass()
	{
		String csc = childSuperClass;
		if ((csc == null) && (this.parentNode instanceof XholonClass)) {
			return ((XholonClass) this.parentNode).getChildSuperClass();
		}
		return csc;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholonClass#setChildSuperClass(String)
	 */
	public void setChildSuperClass(String childSuperClass)
	{
		this.childSuperClass = childSuperClass;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getXml2Xholon()
	 */
	public IXml2Xholon getXml2Xholon()
	{
		IXml2Xholon xml2Xholon = null;
		xml2Xholon = new Xml2XholonClass();
		xml2Xholon.setInheritanceHierarchy(app.getInherHier());
		xml2Xholon.setTreeNodeFactory(app.getFactory());
		xml2Xholon.setApp(app);
		return xml2Xholon;
	}
	
	/**
	 * Get Xh type.
	 * @param token String representation of the Xh type.
	 * @return Xh type.
	 */
	public static int getXhType(String token) {
		int xhTypeRead = IXholonClass.XhtypeNone;
		if (token.equals("Xhtypexxxxxxxxx")) { // 0
			xhTypeRead = IXholonClass.Xhtypexxxxxxxxx;
		}
		else if (token.equals("XhtypePureContainer")) { // 1
			xhTypeRead = IXholonClass.XhtypexxxxxxCon;
		}
		else if (token.equals("XhtypePurePassiveObject")) { // 2
			xhTypeRead = IXholonClass.XhtypexxxFgsxxx;
		}
		else if (token.equals("XhtypexxxFgsCon")) { // 3
			xhTypeRead = IXholonClass.XhtypexxxFgsCon;
		}
		else if (token.equals("XhtypePureActiveObject")) { // 4
			xhTypeRead = IXholonClass.XhtypeBehxxxxxx;
		}
		else if (token.equals("XhtypeBehxxxCon")) { // 5
			xhTypeRead = IXholonClass.XhtypeBehxxxCon;
		}
		else if (token.equals("XhtypeBehFgsxxx")) { // 6
			xhTypeRead = IXholonClass.XhtypeBehFgsxxx;
		}
		else if (token.equals("XhtypeBehFgsCon")) { // 7
			xhTypeRead = IXholonClass.XhtypeBehFgsCon;
		}
		// Activity
		else if (token.equals("XhtypeActivity")) { // 8
			xhTypeRead = IXholonClass.XhtypeActivity;
		}
		// Config Container
		else if (token.equals("XhtypeConfigContainer")) { // 16
			xhTypeRead = IXholonClass.XhtypeConfigContainer;
		}
		// State Machine
		else if (token.equals("XhtypeStateMachineEntity")) { // 24
			xhTypeRead = IXholonClass.XhtypeStateMachineEntity;
		}
		else if (token.equals("XhtypeStateMachineEntityActive")) { // 24 + 4 = 28
			xhTypeRead = IXholonClass.XhtypeStateMachineEntityActive;
		}
		// Ctrnn
		else if (token.equals("XhtypeCtrnnEntity")) { // 32
			xhTypeRead = IXholonClass.XhtypeCtrnnEntity;
		}
		else if (token.equals("XhtypeCtrnnEntityActive")) { // 32 +
			xhTypeRead = IXholonClass.XhtypeCtrnnEntityActive;
		}
		else if (token.equals("XhtypeCtrnnEntityActivePassive")) { // 32 +
			xhTypeRead = IXholonClass.XhtypeCtrnnEntityActivePassive;
		}
		// Membrane Computing
		else if (token.equals("XhtypeMemCompEntity")) { // 40
			xhTypeRead = IXholonClass.XhtypeMemCompEntity;
		}
		else if (token.equals("XhtypeMemCompEntityActive")) { // 40 +
			xhTypeRead = IXholonClass.XhtypeMemCompEntityActive;
		}
		else if (token.equals("XhtypeMemCompEntityPassive")) { // 40 +
			xhTypeRead = IXholonClass.XhtypeMemCompEntityPassive;
		}
		else if (token.equals("XhtypeMemCompEntityActiveContainer")) { // 40 +
			xhTypeRead = IXholonClass.XhtypeMemCompEntityActiveContainer;
		}
		// Brane Calculus
		else if (token.equals("XhtypeBraneCalcEntity")) { // 48
			xhTypeRead = IXholonClass.XhtypeBraneCalcEntity;
		}
		else if (token.equals("XhtypeBraneCalcEntityActive")) { // 48 +
			xhTypeRead = IXholonClass.XhtypeBraneCalcEntityActive;
		}
		else if (token.equals("XhtypeBraneCalcEntityPassive")) { // 48 +
			xhTypeRead = IXholonClass.XhtypeBraneCalcEntityPassive;
		}
		else if (token.equals("XhtypePort")) { // 56
			xhTypeRead = IXholonClass.XhtypePort;
		}
		// Grid
		else if (token.equals("XhtypeGridEntity")) { // 64
			xhTypeRead = IXholonClass.XhtypeGridEntity;
		}
		else if (token.equals("XhtypeGridEntityActive")) { // 64 + 4 = 68
			xhTypeRead = IXholonClass.XhtypeGridEntityActive;
		}
		else if (token.equals("XhtypeGridEntityActivePassive")) { // 64 + 
			xhTypeRead = IXholonClass.XhtypeGridEntityActivePassive;
		}
		
		else {
			logger.warn("XholonClass: invalid xhType :" + token);
		}
		return xhTypeRead;
	}
	
	//	 tree traversal print functions

	/* 
	 * @see org.primordion.xholon.base.IXholonClass#preOrderPrint(int)
	 */
	public void preOrderPrint( int level )
	{
		printNode( level );
		if (firstChild != null)
			firstChild.preOrderPrint( level + 1 );
		if (nextSibling != null)
			nextSibling.preOrderPrint( level );
	}

	/* 
	 * @see org.primordion.xholon.base.IXholonClass#inOrderPrint(int)
	 */
	public void inOrderPrint( int level )
	{
		if (firstChild != null)
			firstChild.inOrderPrint( level + 1 );
		printNode( level );
		if (nextSibling != null)
			nextSibling.inOrderPrint( level );
	}

	/* 
	 * @see org.primordion.xholon.base.IXholonClass#postOrderPrint(int)
	 */
	public void postOrderPrint( int level )
	{
		if (firstChild != null)
			firstChild.postOrderPrint( level + 1 );
		if (nextSibling != null)
			nextSibling.postOrderPrint( level );
		printNode( level );
	}

	/**
	 * Called by the various xOrderPrint() routines.
	 * @param level The current level in the composite structure hierarchy tree.
	 */
	public void printNode( int level )
	{
		while (level > 0) {
			print( '.' );
			level--;
		}
		println( toString() ); //getName() );
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#toXml(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.XmlWriter)
	 */
	public void toXml(IXholon2Xml xholon2xml, IXmlWriter xmlWriter)
	{
		String localName = getXhcName();
		String prefix = null;
		String namespaceUri = null;
		// write start element
		if (isPrefixed()) {
			prefix = getPrefix();
			// remove the prefix from the name
			int beginIndex = localName.indexOf(':');
			if (beginIndex != -1) {
				localName = localName.substring(beginIndex+1);
			}
			namespaceUri = getMechanism().getNamespaceUri();
			xmlWriter.writeStartElement(prefix, localName, namespaceUri);
		}
		else {
			xmlWriter.writeStartElement(localName);
		}
		// write attributes
		if (xholon2xml.isWriteAttributes()) {
			toXmlAttributes(xholon2xml, xmlWriter);
		}
		xholon2xml.writeSpecial(this);
		// write ports
		// <port name="port" index="0" connector="#xpointer(ancestor::TheSystem/Compounds/C)"/>
		Iterator<PortInformation> piIt = this.getPortInformation().iterator();
		while (piIt.hasNext()) {
			PortInformation pi = piIt.next();
			xmlWriter.writeStartElement("port");
			xmlWriter.writeAttribute("name", pi.getFieldName());
			if (pi.getFieldNameIndex() != PortInformation.PORTINFO_NOTANARRAY) { // -1
				xmlWriter.writeAttribute("index", pi.getFieldNameIndexStr());
			}
			xmlWriter.writeAttribute("connector", pi.getXpathExpression());
			xmlWriter.writeEndElement("port");
		}
		// write children
		IXholon childNode = getFirstChild();
		while (childNode != null) {
			childNode.toXml(xholon2xml, xmlWriter);
			childNode = childNode.getNextSibling();
		}
		// write end element
		if (isPrefixed()) {
			xmlWriter.writeEndElement(localName, namespaceUri);
		}
		else {
			xmlWriter.writeEndElement(localName);
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#toXmlAttributes(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.XmlWriter)
	 */
	public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter)
	{
		toXmlAttribute(xholon2xml, xmlWriter, "id", String.valueOf(id), null);
		if ((getXhType() != XhtypePureContainer) && (getXhType() != XhtypeNone)) {
			toXmlAttribute(xholon2xml, xmlWriter, "xhType", getXhTypeName(), null);
		}
		if ((implName != null) && (implName.length() > 0)) {
			toXmlAttribute(xholon2xml, xmlWriter, "implName", implName, null);
			if (shouldShowSourceCode) {
				toXmlAttribute(xholon2xml, xmlWriter, "code",
					sourceCodeURLPrefix +
					implName.replace('.', '/') +
					sourceCodeURLSuffix, null);
			}
		}
		else if (getApp().getXhcRoot() == this) {} // XholonClass node
		else if (this.getId() < Mechanism.MECHANISM_ID_START) {
			// this is an app-specific XholonClass
			toXmlAttribute(xholon2xml, xmlWriter, "implName", this.getApp().getJavaXhClassName(), null);
			if (shouldShowSourceCode) {
				toXmlAttribute(xholon2xml, xmlWriter, "code",
					sourceCodeURLPrefix +
					this.getApp().getJavaXhClassName().replace('.', '/') +
					sourceCodeURLSuffix, null);
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#toXmlAttribute(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.XmlWriter, java.lang.String, java.lang.String, java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	public void toXmlAttribute(IXholon2Xml xholon2xml, IXmlWriter xmlWriter, String name, String value, Class clazz)
	{
		xmlWriter.writeAttribute(name, value);
	}
	
	/**
	 * Get a String representation of a QName.
	 * It will be in the format: {NamespaceURI}prefix:localPart
	 * @param qname
	 * @return
	 */
	protected String toStringQName(QName qname)
	{
		return "{" + qname.getNamespaceURI() + "}" + qname.getPrefix() + ":" + qname.getLocalPart();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o)
	{
		String thisName = getName();
		String otherName = ((IXholonClass)o).getName();
		return thisName.compareTo(otherName);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholonClass#getDecoration()
	 */
	public IDecoration getDecoration() {
	  return decoration;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholonClass#setDecoration(IDecoration)
	 */
	public void setDecoration(IDecoration decoration) {
	  this.decoration = decoration;
	}
	
	/*
	 * @see org.primordion.xholon.base.IDecoration#getColor()
	 */
	public String getColor() {
		if (decoration == null) {return null;}
		return decoration.getColor();
	}
	
	/*
	 * @see org.primordion.xholon.base.IDecoration#setColor(java.lang.String)
	 */
	public void setColor(String color) {
		if (decoration == null) {
			decoration = new Decoration();
		}
		decoration.setColor(color);
	}

	/*
	 * @see org.primordion.xholon.base.IDecoration#getOpacity()
	 */
	public String getOpacity() {
		if (decoration == null) {return null;}
		return decoration.getOpacity();
	}
	
	/*
	 * @see org.primordion.xholon.base.IDecoration#setOpacity(java.lang.String)
	 */
	public void setOpacity(String opacity) {
		if (decoration == null) {
			decoration = new Decoration();
		}
		decoration.setOpacity(opacity);
	}

	/*
	 * @see org.primordion.xholon.base.IDecoration#getFont()
	 */
	public String getFont() {
		if (decoration == null) {return null;}
		return decoration.getFont();
	}

	/*
	 * @see org.primordion.xholon.base.IDecoration#setFont(java.lang.String)
	 */
	public void setFont(String font) {
		if (decoration == null) {
			decoration = new Decoration();
		}
		decoration.setFont(font);
	}

	/*
	 * @see org.primordion.xholon.base.IDecoration#getIcon()
	 */
	public String getIcon() {
		if (decoration == null) {return null;}
		return decoration.getIcon();
	}

	/*
	 * @see org.primordion.xholon.base.IDecoration#setIcon(java.lang.String)
	 */
	public void setIcon(String icon) {
		if (decoration == null) {
			decoration = new Decoration();
		}
		decoration.setIcon(icon);
	}
	
	/*
	 * @see org.primordion.xholon.base.IDecoration#getToolTip()
	 */
	public String getToolTip() {
		if (decoration == null) {return null;}
		return decoration.getToolTip();
	}

	/*
	 * @see org.primordion.xholon.base.IDecoration#setToolTip(java.lang.String)
	 */
	public void setToolTip(String toolTip) {
		if (decoration == null) {
			decoration = new Decoration();
		}
		decoration.setToolTip(toolTip);
	}
	
	/*
	 * @see org.primordion.xholon.base.IDecoration#getSymbol()
	 */
	public String getSymbol() {
		if (decoration == null) {return null;}
		return decoration.getSymbol();
	}
	
	/*
	 * @see org.primordion.xholon.base.IDecoration#setSymbol(java.lang.String)
	 */
	public void setSymbol(String symbol) {
		if (decoration == null) {
			decoration = new Decoration();
		}
		decoration.setSymbol(symbol);
	}
	
	/*
	 * @see org.primordion.xholon.base.IDecoration#getFormat()
	 */
	public String getFormat() {
		if (decoration == null) {return null;}
		return decoration.getFormat();
	}
	
	/*
	 * @see org.primordion.xholon.base.IDecoration#setFormat(java.lang.String)
	 */
	public void setFormat(String format) {
		if (decoration == null) {
			decoration = new Decoration();
		}
		decoration.setFormat(format);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#getAnnotation()
	 */
	public String getAnnotation() {
	  if (decoration == null) {return null;}
		return decoration.getAnno();
	}
	public String getAnno() {return this.getAnnotation();}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#setAnnotation(java.lang.String)
	 */
	public void setAnnotation(String annotation) {
	  if (decoration == null) {
			decoration = new Decoration();
		}
		decoration.setAnno(annotation);
	}
	public void setAnno(String annotation) {this.setAnnotation(annotation);}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#hasAnnotation()
	 */
	public boolean hasAnnotation() {
	  if (decoration == null) {return false;}
	  return decoration.hasAnno();
	}
	public boolean hasAnno() {return this.hasAnnotation();}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#showAnnotation()
	 */
	public void showAnnotation() {
	  if (decoration == null) {return;}
	  decoration.showAnno();
	}
	public void showAnno() {this.showAnnotation();}
	
	/*
	 * @see org.primordion.xholon.base.IXholonClass#getDefaultContent()
	 */
	public String getDefaultContent() {
		return defaultContent;
	}

	/*
	 * @see org.primordion.xholon.base.IXholonClass#setDefaultContent(java.lang.String)
	 */
	public void setDefaultContent(String defaultContent) {
		this.defaultContent = defaultContent;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholonClass#getPrototype()
	 */
	public Object getPrototype() {
		return prototype;
	}

	/*
	 * @see org.primordion.xholon.base.IXholonClass#setPrototype(JavaScriptObject)
	 */
	public void setPrototype(Object prototype) {
		this.prototype = (JavaScriptObject)prototype;
	}
	
	/**
	 * If the JavaScript code starts with a constructor,
	 * then create a protoype object,
	 * and return a String that can be used to create an object that derives from that prototype.
	 * Else, return null.
	 * @param jsCode JavaScript code.
	 * @return JavaScript code, or null.
	 */
	public String prototype(String jsCode) {
	  jsCode = jsCode.trim();
	  if (jsCode.startsWith("$wnd.xh." + name + " = function " + name + "(")) {
	    prototype = createPrototype(jsCode);
	    return new StringBuilder()
	    .append("<")
	    .append(name)
	    .append(">\n")
	    //.append("  $wnd.console.log(\"beh calling ...\");\n")
	    //.append("  $wnd.console.log(this);\n") // window
	    //.append("  $wnd.console.log($wnd);\n")
	    // TODO "this" == window
	    //.append("  var beh = new this.xhc().")
	    //.append(name)
	    //.append("();\n")
	    //.append("  var beh = new $wnd.xhproto();\n")
	    .append("  var beh = new $wnd.xh.")
	    .append(name)
	    .append("();\n")
	    .append("</")
	    .append(name)
	    .append(">\n")
	    .toString();
	  }
	  else {
	    return null;
	  }
	}
	
	// return the protoype object as a JavaScriptObject
	protected native JavaScriptObject createPrototype(String scriptContent) /*-{
	  //$wnd.console.log("createPrototype()");
	  //$wnd.console.log(scriptContent);
	  var p = eval(scriptContent);
	  //$wnd.console.log(p);
	  return p; // returns a function()
	}-*/;

  @Override
	public List<IXholon> searchForReferencingNodes()
	{
	  // create list of IXholon nodes that reference (belong to) this XholonClass
		List<IXholon> reffingNodes = new ArrayList<IXholon>();
		searchForReferencingNodesRecurse((Xholon)app.getRoot(), reffingNodes);
		return reffingNodes;
	}
	
	@Override
	public void searchForReferencingNodesRecurse(Xholon node, List<IXholon> reffingNodes)
	{
	  if (node.getXhc() == this) {
	    reffingNodes.add(node);
	  }
	  IXholon childNode = node.getFirstChild();
	  while (childNode != null) {
	    searchForReferencingNodesRecurse((Xholon)childNode, reffingNodes);
	    childNode = childNode.getNextSibling();
	  }
	}
	
	/* 
	 * @see org.primordion.xholon.base.IXholonClass#toString()
	 */
	public String toString() {
		String outStr = "[id:" + getId() + "]"
		+ " [mechanism:" + toStringQName(getQName()) + "]"
		+ " [xholon type:" + getXhTypeName() + "]";
		if (getImplName() != null) {
			outStr += " [Java impl:" + getImplName() + "]";
		}
		return outStr;
	}

	public String getSourceCodeURLPrefix() {
		return sourceCodeURLPrefix;
	}

	public void setSourceCodeURLPrefix(String sourceCodeURLPrefix) {
		this.sourceCodeURLPrefix = sourceCodeURLPrefix;
	}

	public String getSourceCodeURLSuffix() {
		return sourceCodeURLSuffix;
	}

	public void setSourceCodeURLSuffix(String sourceCodeURLSuffix) {
		this.sourceCodeURLSuffix = sourceCodeURLSuffix;
	}

	public boolean isShouldShowSourceCode() {
		return shouldShowSourceCode;
	}

	public void setShouldShowSourceCode(boolean shouldShowSourceCode) {
		this.shouldShowSourceCode = shouldShowSourceCode;
	}
}

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

package org.primordion.xholon.io.xml;

//import com.google.gwt.core.client.GWT;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.client.GwtEnvironment;

import org.primordion.xholon.base.IDecoration;
import org.primordion.xholon.base.IReflection;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.base.ReflectionFactory;
import org.primordion.xholon.common.mechanism.CeAttribute;
import org.primordion.xholon.exception.XholonConfigurationException;
import org.primordion.xholon.io.XholonWorkbook;
//import org.primordion.xholon.proxy.AsmXholonBeanBuilder; // GWT
import org.primordion.xholon.util.MiscRandom;

/**
 * Transform XML into a Xholon sub-tree.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on August 1, 2009)
 */
@SuppressWarnings("serial")
public class Xml2Xholon extends AbstractXml2Xholon_gwt implements IXml2Xholon { // GWT
	
	/*
	 * @see org.primordion.xholon.io.xml.AbstractXml2Xholon#xml2Xh(org.primordion.xholon.base.IXholon, org.primordion.xholon.io.xml.XmlReader, int)
	 */
	@SuppressWarnings("unchecked")
	public IXholon xml2Xh(IXholon parentXholon, IXmlReader xmlReader, int eventType)
	{
	  int state = IXmlReader.NULL_EVENT;
		int multiplicity = 1;
		IXholon currentXholon = null;
		boolean includeContentsFound = true;
		String tagName = null;
		while (eventType != IXmlReader.END_DOCUMENT) {
		  switch (eventType) {
			case IXmlReader.START_TAG:
			  if (state == IXmlReader.START_TAG) {
				  if (includeContentsFound) {
						xml2Xh(currentXholon, xmlReader, eventType);
					}
					else {
						xml2Xh(parentXholon, xmlReader, eventType);
						includeContentsFound = true;
					}
					eventType = xmlReader.getEventType();
				}
				else {
				  tagName = xmlReader.getName();
					if ("attribute".equals(tagName)) {
						// ex: <attribute name="output" value="255.0"/>
						currentXholon = newXholon("Attribute_attribute", parentXholon, xmlReader);
						//currentXholon.setRoleName(xmlReader.getAttributeValue(0)); // name="xxx"
						//currentXholon.setVal(xmlReader.getAttributeValue(1)); // value="yyy"
						for (int i = 0; i < xmlReader.getAttributeCount(); i++) {
							String attrName = xmlReader.getAttributeName(i);
							String attrValue = xmlReader.getAttributeValue(i);
							if ("name".equals(attrName)) { // name="xxx"
								currentXholon.setRoleName(attrValue);
							}
							else if ("value".equals(attrName)) { // value="yyy"
								currentXholon.setVal(attrValue);
							}
						}
					}
					else if (("include".equals(tagName) && (xmlReader.getPrefix().equals(xincludePrefix)))
					    || ("xi:include".equals(tagName))) {
					  //consoleLog(xincludePrefix + ":" + tagName);
						// there may be a recursive hierarchy of include and fallback elements
						// keep passing the same parentXholon down the hierarchy until find a match
						// this is an OR hierarchy, rather than an AND hierarchy
						includeContentsFound = true;
						
						String uri = null;
						String savedRoleName = null;
						for (int i = 0; i < xmlReader.getAttributeCount(); i++) {
							String attrName = xmlReader.getAttributeName(i);
							String attrValue = xmlReader.getAttributeValue(i);
							if ("href".equals(attrName)) {
								uri = attrValue;
							}
							else if ("roleName".equals(attrName)) {
								savedRoleName = attrValue;
							}
						}
					  //consoleLog(xincludePrefix + ":" + tagName + " " + uri);
						
						// if the file name begins with ./, then this is the complete relative path
						// else, it needs to be appended to the end of the standard xinclude path
						if (uri.indexOf("://") != -1) {
							// this is a URI starting with http:// file:// etc.
						}
						else if (uri.charAt(0) == '#') {
							// this is a URL fragement that points to an element in the HTML page (ex: "#AUrlFragment")
							// let xmlUri2Xholon_internal() take care of this
						}
						else if (app.isUseGwt()) {
						  // assume that the uri does not start with . or / (ex: config/...)
						  uri = GwtEnvironment.gwtHostPageBaseURL + uri;
						}
						else if (uri.charAt(0) != '.') {
							uri = xincludePath + uri;
						}
						if (parentXholon == null) {
							// there's no point processing the include file if there's no parent to attach it to
							currentXholon = null;
						}
						else {
							//currentXholon = xmlFile2Xholon_internal(uri, parentXholon);
							currentXholon = xmlUri2Xholon_internal(uri, parentXholon);
						}
						if (currentXholon == null) {
							// the specified content could not be found and/or processed
							includeContentsFound = false;
						}
						else {
							if (savedRoleName != null) {
								// override the roleName of the top level node in the include file
								currentXholon.setRoleName(savedRoleName);
							}
							// set it to null so that if there is a child fallback element, it will have no effect
							currentXholon = null;
						}
					} // end xi:include processing
					else if ("fallback".equals(tagName) && (xmlReader.getPrefix().equals(xincludePrefix))) {
						// the parent of a fallback element must be an include element
						//consoleLog("fallback: " + parentXholon);
						currentXholon = parentXholon; // be ready to pass the parentXholon down another level
					}
					else if ((tagName.startsWith(XML_FOREST)) && (parentXholon != null)) {
						// ignore any element that has a parent and that starts with _-.
						// the children of this element are a forest
						// this node is probably the root node in an xinclude file
						currentXholon = parentXholon;
						//consoleLog("forest:" + tagName + " parentXholon:" + parentXholon);
					}
					else if ("XholonWorkbook".equals(tagName)) {
						return new XholonWorkbook().xml2Xh(app, xmlReader.getUnderlyingReader());
					}
					else if ("Color".equals(tagName)) {}
					else if ("Opacity".equals(tagName)) {}
					else if ("Font".equals(tagName)) {}
					else if ("Icon".equals(tagName)) {}
					else if ("ToolTip".equals(tagName)) {}
					else if ("Symbol".equals(tagName)) {}
					else if ("Format".equals(tagName)) {}
					else if ("Geo".equals(tagName)) {}
					else if ("Sound".equals(tagName)) {}
					else if ("Anno".equals(tagName)) {}
					else { // regular elements including Attribute_ nodes
					  currentXholon = newXholon(tagName, parentXholon, xmlReader);
						//consoleLog("  Xml2Xholon xml2Xh: " + currentXholon);
						if (currentXholon != null) {
						  int attrCount = xmlReader.getAttributeCount();
							for (int i = 0; i < attrCount; i++) {
							  String attrName = xmlReader.getAttributeName(i);
								String attrValue = xmlReader.getAttributeValue(i);
								if ("roleName".equals(attrName)) {
									currentXholon.setRoleName(attrValue);
									if (IReflection.CONSTRUCTOR_ARG.equals(attrValue)) {
										if (!currentXholon.getParentNode().isAttributeHandler()) {
											// save the parent of the node that has the constructor arg
											constructorArgs.add(currentXholon.getParentNode());
										}
									}
								}
								else if ("uid".equals(attrName)) {
									currentXholon.setUid(attrValue);
								}
								else if ("multiplicity".equals(attrName)) {
								  // handle multiplicity ranges m,n (0,1 0,13 2,3 1,5)
								  String[] range = attrValue.split(",");
								  if (range.length == 2) {
  						      multiplicity = MiscRandom.getRandomInt(
								      Integer.parseInt(range[0]),
								      Integer.parseInt(range[1])+1
								    );
								    attrValue = String.valueOf(multiplicity);
								  }
								  else if (range[0].startsWith(".") || range[0].startsWith("0.")) {
								    // range[0] is the probability that this node will be created
								    multiplicity = 0;
								    if (MiscRandom.getRandomDouble(0.0, 1.0) < Double.parseDouble(range[0])) {
								      multiplicity = 1;
								    }
								  }
								  else {
									  multiplicity = Integer.parseInt(range[0]);
									}
									if (multiplicity == 0) {
										// TODO should completely ignore this element and its entire subtree
										// currently I remove this node and its subtree in case END_TAG
										//consoleLog("multiplicity 0");
									}
								}
								else {
									currentXholon.setAttributeVal(attrName, attrValue);
								}
							}
						}
						if ("XholonNode".equals(tagName)) {
							// the entire contents of XholonNode should be treated as text rather than as XML
							String xholonNodeContent = xml2Text(xmlReader, eventType = xmlReader.next());
							currentXholon.setVal(xholonNodeContent.trim());
							state = IXmlReader.END_TAG;
							eventType = xmlReader.next();
							return currentXholon;
						}
					}
					state = IXmlReader.START_TAG;
					eventType = xmlReader.next();
				}
				break;
			case IXmlReader.END_TAG:
			  if (state == IXmlReader.END_TAG) {
					return null;
				}
				// handle multiplicity
				if (multiplicity > 1) {
					multiply(currentXholon, multiplicity - 1); // GWT
					multiplicity = 1; // reset to its default value
				}
				else if (multiplicity == 0) {
				  //consoleLog("removing " + currentXholon);
				  currentXholon.removeChild();
				  multiplicity = 1; // reset to its default value
				}
				
				state = IXmlReader.END_TAG;
				eventType = xmlReader.next();
				break;
			case IXmlReader.TEXT:
			  String textVal = xmlReader.getText().trim();
			  //consoleLog(tagName + " " + textVal + " " + parentXholon);
				if (textVal.length() > 0) {
					if ("Color".equals(tagName)) {
						((IDecoration)parentXholon).setColor(textVal);
					}
					else if ("Font".equals(tagName)) {
						((IDecoration)parentXholon).setFont(textVal);
					}
					else if ("Opacity".equals(tagName)) {
						((IDecoration)parentXholon).setOpacity(textVal);
					}
					else if ("Icon".equals(tagName)) {
						((IDecoration)parentXholon).setIcon(textVal);
					}
					else if ("ToolTip".equals(tagName)) {
						((IDecoration)parentXholon).setToolTip(textVal);
					}
					else if ("Symbol".equals(tagName)) {
						((IDecoration)parentXholon).setSymbol(textVal);
					}
					else if ("Format".equals(tagName)) {
						((IDecoration)parentXholon).setFormat(textVal);
					}
					else if ("Geo".equals(tagName)) {
						((IDecoration)parentXholon).setGeo(textVal);
					}
					else if ("Sound".equals(tagName)) {
						((IDecoration)parentXholon).setSound(textVal);
					}
					else if ("Anno".equals(tagName)) {
					  parentXholon.setAnnotation(textVal);
					}
					else {
					  setVal(currentXholon, textVal, "val");
					}
				}
				eventType = xmlReader.next();
				break;
			case IXmlReader.CDSECT:
			case IXmlReader.COMMENT:
			case IXmlReader.DOCDECL:
			case IXmlReader.ENTITY_REF:
			case IXmlReader.IGNORABLE_WHITESPACE:
			case IXmlReader.PROCESSING_INSTRUCTION:
			case IXmlReader.OTHER_TYPE:
			default:
				eventType = xmlReader.next();
				break;
			}
		}
		return currentXholon;
	}
	
	/**
	 * Read XML subtree as text.
	 * @param xmlReader
	 * @return
	 */
	protected String xml2Text(IXmlReader xmlReader, int eventType) {
	  StringBuilder sb = new StringBuilder();
		// look for first child START_TAG
		while (eventType != IXmlReader.START_TAG) {
			sb.append(xmlReader.getText()); // probably a TEXT eventType
			eventType = xmlReader.next();
			if (eventType == IXmlReader.END_TAG) {
				// the parent node has no content
				return sb.toString();
			}
		}
		while (eventType == IXmlReader.START_TAG) {
			sb.append(xmlReader.getText());
			eventType = xmlReader.next();
			while (eventType != IXmlReader.END_TAG) {
				sb.append(xmlReader.getText());
				eventType = xmlReader.next();
			}
			sb.append(xmlReader.getText());
			eventType = xmlReader.next();
			while (eventType != IXmlReader.START_TAG) {
				sb.append(xmlReader.getText()); // probably a TEXT eventType
				eventType = xmlReader.next();
				if (eventType == IXmlReader.END_TAG) {
					// this should be the parent node END_TAG
					return sb.toString();
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * Returns an instance of IXholon based on the current tag in the XmlReader.
	 * @param tagName The name of the current XML node.
	 * @param parentXholon The parent of the new xholon.
	 * @param xmlReader The XML reader.
	 * @return An IXholon, or null.
	 */
	@SuppressWarnings("unchecked")
	protected IXholon newXholon(String tagName, IXholon parentXholon, IXmlReader xmlReader)
	{
	  String prefix = xmlReader.getPrefix();
		IXholonClass xhcNode = null;
		if (inherHier != null) {
			if (prefix != null) {
				// if the XML element is explicity prefixed, then that is part of the XholonClass name
				xhcNode = inherHier.getClassNode(prefix + ":" + tagName);
			}
			else {
				xhcNode = inherHier.getClassNode(tagName);
			}
		}
		if (xhcNode == null) {
			String implName = null;
			int attrCount = xmlReader.getAttributeCount();
			for (int i = 0; i < attrCount; i++) {
				String attrName = xmlReader.getAttributeName(i);
				String attrValue = xmlReader.getAttributeValue(i);
				if ("implName".equals(attrName)) {
				  //consoleLog("Xml2Xholon newXholon4 " + tagName + " " + attrName + " " + attrValue);
					if (attrValue.endsWith("inline:")) {
						// this is an ad-hoc non-Java inline script
						// the next event should be the TEXT between the start and end tags
						xmlReader.next(); // WARNING this might be dangerous to do
						String textVal = xmlReader.getText().trim();
						if ((textVal != null) && (textVal.length() > 0)) {
							// this must be the inline script text
							implName = attrValue + textVal;
						}
					}
					else {
						implName = attrValue;
					}
					break;
				}
			}
			if (implName == null) {
				if ((parentXholon == null)
						|| (parentXholon.getXhc() == null)
						|| parentXholon.getXhc().getChildSuperClass() == null) {
					// this might make use of the standard names convention for sub-XholonClasses
					xhcNode = makeStandardSubXholonClass(tagName);
				}
			}
			if (xhcNode == null) {
				// try to create a new ad-hoc class node from the tagName
				xhcNode = makeAdHocXholonClass(tagName, implName, parentXholon);
			}
		}
		if (xhcNode == null) {
			String warnMsg = "Can't create instance of unknown XholonClass: ";
			if (prefix != null) {
				warnMsg += prefix + ":";
			}
			warnMsg += "<" + tagName + "/>";
			logger.warn(warnMsg);
			return null;
		}
		String implName = xhcNode.getImplName();
		// move up the inheritance hierarchy looking for first parent with a non-null implName
		IXholonClass xhcSearchNode = xhcNode;
		while ((!xhcSearchNode.isRootNode()) && (implName == null)) {
			xhcSearchNode = (IXholonClass)xhcSearchNode.getParentNode();
			implName = xhcSearchNode.getImplName();
		}
		
		IXholon newXholon = null;
		try {
		  // the following is commented out in the original Xholon
			/*if (implName != null && implName.startsWith("factory:")) {
				implName = getImplNameFromFactory(implName.substring(8), xmlReader);
				if (implName != null) { // has a Java implementation class been specified?
					newXholon = factory.getXholonNode(implName);
					if (xmlReader.getEventType() == IXmlReader.TEXT) {
						// the app-specific factory class read a next() text node
						newXholon.setVal_String(xmlReader.getText());
					}
				}
				else { // use the default implementation class
					newXholon = factory.getXholonNode();
				}
			}
			else*/
			if (implName != null) { // has a Java implementation class been specified?
				if (implName.startsWith(IMPLNAME_SPECIAL)) {
					// use ASM to create a subclass of the default Xh class, to handle custom ports
					List<PortInformation> piList = xhcNode.getPortInformation();
					Iterator<PortInformation> piIt = piList.iterator();
					List<String> pnList = new ArrayList<String>(piList.size());
					while (piIt.hasNext()) {
						PortInformation pi = piIt.next();
						if (pi.getFieldNameIndex() == PortInformation.PORTINFO_NOTANARRAY) {
							pnList.add(pi.getFieldName());
						}
						else {
							// this is an item in an array
							// the newXholon needs to know the size of the array
							if (pi.getFieldNameIndex() == 0) {
								String fieldName = pi.getFieldName();
								int arraySize = PortInformation.findArraySize(piList, fieldName);
								pnList.add(fieldName + "[" + arraySize + "]");
							}
						}
					}
					String javaXhClassName = app.getJavaXhClassName();
					if (javaXhClassName != null) {
						javaXhClassName = javaXhClassName.replace('.', '/');
					}
					//String[] portName = new String[pnList.size()];
					//newXholon = new AsmXholonBeanBuilder() // GWT
					//	.generateNewInstance(javaXhClassName + "_" + tagName,
					//			javaXhClassName, pnList.toArray(portName));
				}
				else {
					newXholon = factory.getXholonNode(implName);
				}
			}
			else { // use the default implementation class
			  //consoleLog("Xml2Xholon newXholon9 calling factory.getXholonNode()");
				newXholon = factory.getXholonNode();
			}
		} catch (XholonConfigurationException e) {
			logAnError(e);
			return null; // TODO not sure what should be returned in this case
		}
		
		if (newXholon == null) {return null;}
		if (newXholon.getId() != IXholon.XHOLON_ID_NULL) {
			newXholon.setId(app.getNextId());
		}
		// set the parent
		if (parentXholon != null) {
			newXholon.appendChild(parentXholon);
		}
		newXholon.setXhc(xhcNode);
		newXholon.setPorts();
		return newXholon;
	}
	
	/**
	 * Returns a concrete implName generated by an app-specific factory class.
	 * @param factoryImplName The name of a factory class
	 * (ex: "org.primordion.xholon.service.mathscieng.QuantityFactory").
	 * The factory class must have a method: public static String implName(Object obj)
	 * where obj is an instance of IXmlReader.
	 * @param xmlReader The XML reader.
	 * @return The name of a concrete class
	 * (ex: "org.primordion.xholon.service.mathscieng.Quantity"), or null.
	 */
	// the following is commented out in the original Xholon
	/*@SuppressWarnings("unchecked")
	protected String getImplNameFromFactory(String factoryImplName, IXmlReader xmlReader)
	{
		String implName = null;
		/* GWT
		try {
			Class clazz = Class.forName(factoryImplName);
			Class[] parameterTypes = new Class[1];
			parameterTypes[0] = Object.class;
			Method method = clazz.getMethod("implName", parameterTypes);
			Object[] args = new Object[1];
			args[0] = xmlReader;
			implName = (String)method.invoke(null, args);
		}
		catch (SecurityException e) {logAnError(e);}
		catch (IllegalArgumentException e) {logAnError(e);}
		catch (ClassNotFoundException e) {logAnError(e);}
		catch (NoSuchMethodException e) {logAnError(e);}
		catch (IllegalAccessException e) {logAnError(e);}
		catch (InvocationTargetException e) {logAnError(e);}
		return implName;
	}*/
	
	/**
	 * Use the XML tagName and/or implName to try to make an ad-hoc IXholonClass node.
	 * It's assumed that the XholonClass name is the same as the Java class name.
	 * This method is officially to be used for quick prototyping.
	 * This method may unofficially be used to make a Xholon Virus.
	 * The whole idea of a virus is that it makes use of its host's built-in
	 * machinery, so this method provides that built-in machinery.
	 * @param tagName An XML tag name.
	 * @param implName 
	 * @return A new or existing IXholonClass node, or null.
	 */
	protected IXholonClass makeAdHocXholonClass(String tagName, String implName, IXholon parentXholon)
	{
	  if (!ALLOW_AD_HOC_INSTANCES) {return null;}
		// determine if there is a Java class in the application's home Java package called tagName
		String defaultXhName = app.getJavaXhClassName();
		if (implName == null) {
			implName = defaultXhName.substring(0, defaultXhName.lastIndexOf('.')) + '.' + tagName;
		}
		if (implName.startsWith("lang:")) {
			// this is a non-Java script
		}
		else if (!factory.isClassFindable(implName)) {
		  // try the common default package for Java script classes
			implName = "org.primordion.xholon.script." + tagName;
			if (!factory.isClassFindable(implName)) {
				if (parentXholon != null) {
				  // parentXholon may be a container such as XholonList, that knows the default type of its children
					String childSuperClassName = parentXholon.getXhc().getChildSuperClass();
					if (childSuperClassName != null) {
					  IXholonClass childSuperClass = inherHier.getClassNode(childSuperClassName);
						if (childSuperClass != null) {
							IXholonClass newXholonClass = makeXholonClass(tagName);
							newXholonClass.appendChild(childSuperClass);
							inherHier.createHashEntry(newXholonClass);
							return newXholonClass;
						}
					}
				}
				
				// make it an instance of the application's default Java class
				implName = defaultXhName;
				if (!factory.isClassFindable(implName)) {
					return null;
				}
			}
		}
		else { // there is a findable Java class
			// try to find a XholonClass with the same name as the Java class
			// ex: org.primordion.xholon.mech.filesystem.Directory > Directory
			int beginIndex = implName.lastIndexOf('.');
			if (beginIndex == -1) {beginIndex = 0;}
			else {beginIndex++;}
			String candidateXholonClassName = implName.substring(beginIndex);
			IXholonClass childSuperClass = inherHier.getClassNode(candidateXholonClassName);
			if (childSuperClass != null) {
				IXholonClass newXholonClass = makeXholonClass(tagName);
				newXholonClass.appendChild(childSuperClass);
				inherHier.createHashEntry(newXholonClass);
				return newXholonClass;
			}
		}
		IXholonClass newXholonClass = makeXholonClass(tagName);
		newXholonClass.setXhType(IXholonClass.XhtypePureActiveObject);
		newXholonClass.setImplName(implName);
		logger.info("Created ad-hoc instance of unknown XholonClass: <" + tagName + "/>");
		return newXholonClass;
	}
	
	/**
	 * Use the XML tagName as the name of a subclass of a standard class.
	 * For example, if the tagName is "MyController", look for an existing IXholonClass called "Controller".
	 * If the tagName is found to designate a standard subclass, then a new IXholonClass is created,
	 * and is inserted into the IH tree as a subclass of the standard class.
	 * This method will try each part of the tagName that starts with a capitalized letter.
	 * For example, if the tagName is "OneTwoThree", then it will try "Three" and will then try "TwoThree".
	 * @param tagName An XML tag name.
	 * @return A new IXholonClass node, or null.
	 */
	protected IXholonClass makeStandardSubXholonClass(String tagName)
	{
		IXholonClass candidateSuperclass = null;
		int index = tagName.length() - 1;
		char ch = tagName.charAt(index);
		while ((candidateSuperclass == null) && (index > 1)) {
			// get the final capitalized part of the tagName
			// shortest possible matching tagName would be 3 characters, for example: XYz
			while ((index > 1) && (Character.isLowerCase(ch))) {
				index--;
				ch = tagName.charAt(index);
			}
			if (index <= 1) {
				return null;
			}
			String candidateSuperclassStr = tagName.substring(index);
			candidateSuperclass = inherHier.getClassNode(candidateSuperclassStr);
			index--;
			ch = tagName.charAt(index);
		}
		if (candidateSuperclass != null) {
			IXholonClass newXholonClass = null;
			newXholonClass = makeXholonClass(tagName);
			// use the XhType of the superclass
			newXholonClass.setXhType(candidateSuperclass.getXhType());
			// determine the implName; assume this is a Java class in the app's own package
			// determine if there is a Java class in the application's home Java package called tagName
			String defaultXhName = app.getJavaXhClassName();
			String implName = defaultXhName.substring(0, defaultXhName.lastIndexOf('.')) + '.' + tagName;
			newXholonClass.setImplName(implName);
			// make the new XholonClass a subclass of the candidate superclass
			newXholonClass.appendChild(candidateSuperclass);
			inherHier.createHashEntry(newXholonClass);
			return newXholonClass;
		}
		return null;
	}
	
	/**
	 * Multiply the current xholon a specified number of times.
	 * Each new xholon will be a clone of the current xholon,
	 * and will have the same parent as the current xholon.
	 * @param currentXholon The xholon that is to be multiplied.
	 * @param multiplicity The number of times to multiply the current xholon.
	 */
	protected void multiply(IXholon currentXholon, int multiplicity)
	{
		//consoleLog("Multiplying " + currentXholon + " " + multiplicity + " times.");
		IXholon parentXholon = currentXholon.getParentNode();
		IXholon2Xml xholon2Xml = new Xholon2Xml();
		// the attribute nodes should still be present in the tree, so no need to recreate them
		xholon2Xml.setXhAttrStyle(IXholon2Xml.XHATTR_TO_NULL);
		// first, transform currentXholon into XML
		String xmlString = xholon2Xml.xholon2XmlString(currentXholon);
		//consoleLog(xmlString);
		// then, pass the XML to xmlString2Xholon() multiplicity times
		for (int i = 0; i < multiplicity; i++) {
			xmlString2Xholon_internal(xmlString, parentXholon);
		}
	}
	
	/**
	 * Set the value of an attribute in a Xholon Java object.
	 * @param node The Xholon Java object that contains the attribute.
	 * @param val The value that the attribute will be set to.
	 * @param attrName Name of the Java attribute/variable whose value is to be set.
	 */
	protected void setVal(IXholon node, String val, String attrName)
	{
		if (node == null) {return;}
		if (attrName == null) {return;}
		if (node.getXhc().hasAncestor(CeAttribute.AttributeCE)) {
			node.setVal(val);
		}
		else {
			IReflection ir = ReflectionFactory.instance();
			ir.setAttributeVal(node, attrName, val, 0);
		}
	}
	
	/**
	 * Log an error message.
	 * This is a temporary method to handle a number of error-prone log messages
	 * that were previously in this class.
	 * It's possible that e.getCause() will return null,
	 * which was causing it to the class to fail.
	 * @param e
	 */
	protected void logAnError(Exception e) {
		if (e == null) {return;}
		String causeMsg = "";
		if (e.getCause() != null) {
			causeMsg = e.getCause().getMessage();
		}
		logger.error(e.getMessage() + causeMsg);
	}

}

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

import com.google.gwt.core.client.GWT;

import org.primordion.xholon.base.ContainmentData;
import org.primordion.xholon.base.IDecoration;
import org.primordion.xholon.base.IReflection;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.base.ReflectionFactory;
import org.primordion.xholon.base.XholonClass;
import org.primordion.xholon.common.mechanism.CeAttribute;
import org.primordion.xholon.exception.XholonConfigurationException;
//import org.primordion.xholon.io.XholonWorkbook; // GWT

/**
 * Transform XML into a XholonClass sub-tree.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on August 1, 2009)
 */
@SuppressWarnings("serial")
public class Xml2XholonClass extends AbstractXml2Xholon_gwt implements IXml2Xholon, IXml2XholonClass {
	
	/**
	 * The last id assigned to a node.
	 * This is used to pass this information from child nodes back to a parent.
	 */
	private int lastIdUsed = 0;
	

	/*
	 * @see org.primordion.xholon.io.xml.AbstractXml2Xholon#xml2Xh(org.primordion.xholon.base.IXholon, org.primordion.xholon.io.xml.XmlReader, int)
	 */
	public IXholon xml2Xh(IXholon parentXholon, IXmlReader xmlReader, int eventType)
	{
		int state = IXmlReader.NULL_EVENT;
		IXholonClass currentXhClass = null;
		while (eventType != IXmlReader.END_DOCUMENT) {
			switch (eventType) {
			case IXmlReader.START_TAG:
				if (state == IXmlReader.START_TAG) {
					xml2Xh(currentXhClass, xmlReader, eventType);
					eventType = xmlReader.getEventType();
				}
				else {
					String tagName = xmlReader.getName();
					System.out.println("Xml2XholonClass xml2Xh: " + tagName);
					if ("attribute".equals(tagName)) {
						// ex: <attribute name="output" value="255.0"/>
						currentXhClass = newXholon("Attribute_attribute", parentXholon, xmlReader);
						//currentXhClass.setRoleName(xmlReader.getAttributeValue(0));
						//currentXhClass.setVal(xmlReader.getAttributeValue(1));
						for (int i = 0; i < xmlReader.getAttributeCount(); i++) {
							String attrName = xmlReader.getAttributeName(i);
							String attrValue = xmlReader.getAttributeValue(i);
							if ("name".equals(attrName)) { // name="xxx"
								currentXhClass.setRoleName(attrValue);
							}
							else if ("value".equals(attrName)) { // value="yyy"
								currentXhClass.setVal(attrValue);
							}
						}
						
					}
					else if ("xholonClassDetails".equals(tagName)) {
						// class details
						return xml2XhDetails(parentXholon, xmlReader, eventType);
					}
					else if (("include".equals(tagName) && (xmlReader.getPrefix().equals(xincludePrefix)))
					    || ("xi:include".equals(tagName))) {
						//app.setNextXholonClassId(--nextId); // save this unused id as the next available one
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
						// if the file name begins with ./, then this is the complete relative path
						// else, it needs to be appended to the end of the standard xinclude path						
						if (uri.indexOf("://") != -1) {
							// this is a URI starting with http:// file:// etc.
						}
						else if (app.isUseGwt()) {
						  // assume that the uri does not start with . or / (ex: config/...)
						  uri = GWT.getHostPageBaseURL() + uri;
						}
						else if (uri.charAt(0) != '.') {
							uri = xincludePath + uri;
						}
						//currentXhClass = (IXholonClass)xmlFile2Xholon(uri, parentXholon);
						currentXhClass = (IXholonClass)xmlUri2Xholon(uri, parentXholon);
						if (savedRoleName != null) {
							// override the roleName of the top level node in the include file
							currentXhClass.setRoleName(savedRoleName);
						}
					}
					else if ("fallback".equals(tagName) && (xmlReader.getPrefix().equals(xincludePrefix))) {
						// the parent of a fallback element must be an include element
						//System.out.println("fallback: " + parentXholon);
						currentXhClass = (IXholonClass)parentXholon; // be ready to pass the parentXholon down another level
					}
					else if ((tagName.startsWith(XML_FOREST)) && (parentXholon != null)) {
						// ignore any element that has a parent and that starts with _-.
						// the children of this element are a forest
						// this node is probably the root node in an xinclude file
						currentXhClass = (IXholonClass)parentXholon;
						//System.out.println("forest:" + tagName + " parentXholon:" + parentXholon);
					}
					else if ("XholonWorkbook".equals(tagName)) {
						//return new XholonWorkbook().xml2Xh(app, xmlReader.getUnderlyingReader()); // GWT
					}
					else if ("parsererror".equals(tagName)) {
					  this.consoleLog("parsererror in IH (Xml2XholonClass.java)");
					  this.consoleLog(xmlReader.toString());
					}
					else { // regular elements including Attribute_ nodes
						currentXhClass = newXholon(tagName, parentXholon, xmlReader);
						int attrCount = xmlReader.getAttributeCount();
						for (int i = 0; i < attrCount; i++) {
							String attrName = xmlReader.getAttributeName(i);
							String attrValue = xmlReader.getAttributeValue(i);
							if ("roleName".equals(attrName)) {
								currentXhClass.setRoleName(attrValue);
							}
							else if ("implName".equals(attrName)) {
								currentXhClass.setImplName(attrValue);
							}
							else if ("xhType".equals(attrName)) {
								currentXhClass.setXhType(XholonClass.getXhType(attrValue));
							}
							else if ("id".equals(attrName)) {
								// ignore; already handled by a previous call to getId(...)
							}
							else if ("superClass".equals(attrName)) {
								// ignore; already handled by previous call to getSuperClass()
							}
							else if ("childSuperClass".equals(attrName)) {
								currentXhClass.setChildSuperClass(attrValue);
							}
							// "xmlns" can safely be ignored for now
							else if ("xmlns".equals(attrName)) {}
							else if ("xmlns:attr".equals(attrName)) {}
							else if ("xmlns:xi".equals(attrName)) {}
							else {
								//logger.error("Xml2XholonClass found unknown attribute: " + attrName);
								this.consoleLog("Xml2XholonClass found unknown attribute: " + attrName);
							}
						}
						inherHier.createHashEntry(currentXhClass);
					}
					state = IXmlReader.START_TAG;
					eventType = xmlReader.next();
				}
				break;
			case IXmlReader.END_TAG:
				if (state == IXmlReader.END_TAG) {
					return null;
				}
				
				state = IXmlReader.END_TAG;
				eventType = xmlReader.next();
				break;
			case IXmlReader.TEXT:
				String textVal = xmlReader.getText().trim();
				if (textVal.length() > 0) {
					setVal(currentXhClass, textVal, "val");
				}
				eventType = xmlReader.next();
				break;
			default:
				eventType = xmlReader.next();
				break;
			}
		}
		return currentXhClass;
	}
	
	/**
	 * Parse a collection of XholonClass detail elements,
	 * and add these to the corresponding existing XholonClass nodes.
	 * @param parentXholon A parent Xholon.
	 * @param xmlReader The XML Reader.
	 * @param eventType An XML Reader event type.
	 */
	protected IXholon xml2XhDetails(IXholon parentXholon, IXmlReader xmlReader, int eventType)
	{
		String navInfoRead = "";
		int xhTypeRead = IXholonClass.XhtypeNone;
		String implNameRead = null;
		String xhClassName = null;
		IXholonClass attrXhc = null;
		int idRead = 0;
		String textVal = null;
		String tagName = null;
		IXholonClass existingXholonClass = null;
		while (eventType != IXmlReader.END_DOCUMENT) {
			switch (eventType) {
			case IXmlReader.START_TAG:
				tagName = xmlReader.getName();
				if (!(tagName.equals("xholonClassDetails"))) {
					if (tagName.equals("port")) {
						// ex: <port name="poem" connector="#xpointer(ancestor::SpringIdol/Poems/Damoyselle)"/>
						// ex: <port name="song" index="-1" connector="#xpointer(ancestor::SpringIdol/Songs/Song)"/>
						// ex: <port name="port" index="PushbuttonMessages0" multiplicity="1" isConjugated="false" providedInterface="SIN_REQUESTED," requiredInterface="SOUT_ACK,SOUT_REQUEST_SATISFIED,">
						String myPortName = "port";
						String myIndex = "-1";
						String myConnector = null;
						String myMultiplicity = "1";
						String myIsConjugated = null;
						String myProvidedInterface = null;
						String myRequiredInterface = null;
						for (int i = 0; i < xmlReader.getAttributeCount(); i++) {
						  String attrName = xmlReader.getAttributeName(i);
						  if ("name".equals(attrName)) {
						    myPortName = xmlReader.getAttributeValue(i);
						  }
						  else if ("index".equals(attrName)) {
						    myIndex = xmlReader.getAttributeValue(i);
						  }
						  else if ("connector".equals(attrName)) {
						    myConnector = xmlReader.getAttributeValue(i);
						  }
						  else if ("multiplicity".equals(attrName)) {
						    myMultiplicity = xmlReader.getAttributeValue(i);
						  }
						  else if ("isConjugated".equals(attrName)) {
						    myIsConjugated = xmlReader.getAttributeValue(i);
						  }
						  else if ("providedInterface".equals(attrName)) {
						    myProvidedInterface = xmlReader.getAttributeValue(i);
						  }
						  else if ("requiredInterface".equals(attrName)) {
						    myRequiredInterface = xmlReader.getAttributeValue(i);
						  }
						}
						if ((!myPortName.equals("port")) && (!myPortName.equals("cnpt"))) {
							myPortName = "P" + myPortName;
						}
						if (myConnector == null) {
						  navInfoRead += myPortName // ex: name="port"
							+ "[" + myIndex + "]" // index
							+ "{" + myMultiplicity + "}" // multiplicity
							+ "{" + myIsConjugated + "}" // isConjugated
							+ "{" + myProvidedInterface + "}" // providedInterface
							+ "{" + myRequiredInterface + "}" // requiredInterface
							+ NAVINFO_SEPARATOR;
						}
						else {
						  navInfoRead += myPortName // ex: name="port"
								+ "[" + myIndex + "]=\"" // index
								+ myConnector // connector
								+ "\"" + NAVINFO_SEPARATOR;
						}
					}
					else if (tagName.equals("portReplication")) {
					  // ex: <portReplication name="replication" index="0" connector="#xpointer(ancestor::Elevator/ElevatorPanel/DoorControlButton[@roleName='bOpen']/attribute::port[2]/attribute::replication[1..*])"/>

					  String myName = null;
						String myIndex = null;
						String myConnector = null;
						for (int i = 0; i < 3; i++) {
						  String attrName = xmlReader.getAttributeName(i);
						  if ("name".equals(attrName)) {
						    myName = xmlReader.getAttributeValue(i);
						  }
						  else if ("index".equals(attrName)) {
						    myIndex = xmlReader.getAttributeValue(i);
						  }
						  else if ("connector".equals(attrName)) {
						    myConnector = xmlReader.getAttributeValue(i);
						  }
						}
						navInfoRead += myName // name="replication"
						+ "[" + myIndex + "]=\"" // index
						+ myConnector // connector
						+ "\"" + NAVINFO_SEPARATOR;
					}
					else if (tagName.equals("attribute")) {
						if (xmlReader.getAttributeCount() == 2) { // name, value
						  // ex: <attribute name="reversible" value="false"/>
						  String myName = null;
						  String myValue = null;
						  for (int i = 0; i < 2; i++) {
						    String attrName = xmlReader.getAttributeName(i);
						    if ("name".equals(attrName)) {
								  myName = xmlReader.getAttributeValue(i);
								}
								else if ("value".equals(attrName)) {
								  myValue = xmlReader.getAttributeValue(i);
								}
						  }
							navInfoRead += "A" + myName
							+ "="
							+ myValue
							+ NAVINFO_SEPARATOR;
						}
						else { // == 3 name, index, value
  					  // ex: <attribute name="geneVal" index="0" value="1500"/>
						  String myName = null;
						  String myIndex = null;
						  String myValue = null;
						  for (int i = 0; i < 3; i++) {
						    String attrName = xmlReader.getAttributeName(i);
						    if ("name".equals(attrName)) {
								  myName = xmlReader.getAttributeValue(i);
								}
								else if ("index".equals(attrName)) {
								  myIndex = xmlReader.getAttributeValue(i);
								}
								else if ("value".equals(attrName)) {
								  myValue = xmlReader.getAttributeValue(i);
								}
						  }
							navInfoRead += "A" + myName
							+ "[" + myIndex + "]="
							+ myValue
							+ NAVINFO_SEPARATOR;
						}
					}
// end of new code

					else if (tagName.equals("config")) { // 'C'
						navInfoRead += ContainmentData.CD_CONFIG + xmlReader.getAttributeValue(0)
						+ NAVINFO_SEPARATOR;
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
					else if ("DefaultContent".equals(tagName)) {}
					else if ("Anno".equals(tagName)) {}
					else { // must be a xholon class name
						xhClassName = tagName;
						
						String prefix = xmlReader.getPrefix();
						existingXholonClass = null;
						if ((prefix != null) && (prefix.length() > 0)) {
							existingXholonClass = inherHier.getClassNode(prefix + ":" + tagName);
						}
						else {
							existingXholonClass = inherHier.getClassNode(tagName);
						}
						if (existingXholonClass == null) {
							String warnMsg = "Can't modify class details for unknown XholonClass: ";
							if (prefix != null) {
								warnMsg += prefix + ":";
							}
							warnMsg += "<" + tagName + "/>";
							//logger.warn(warnMsg);
							this.consoleLog(warnMsg);
						}
						else {
							idRead = existingXholonClass.getId();
							int attrCount = xmlReader.getAttributeCount();
							for (int i = 0; i < attrCount; i++) {
								String attrName = xmlReader.getAttributeName(i);
								String attrValue = xmlReader.getAttributeValue(i);
								
								if ("xhType".equals(attrName)) {
									xhTypeRead = XholonClass.getXhType(attrValue);
								}
								else if ("implName".equals(attrName)) {
									implNameRead = attrValue;
								}
								// "xmlns" can safely be ignored for now
								else if ("xmlns".equals(attrName)) {}
								else if ("xmlns:attr".equals(attrName)) {}
								else if ("xmlns:xi".equals(attrName)) {}
								else {
									//logger.error("Xml2XholonClass found unknown attribute: " + attrName);
									this.consoleLog("Xml2XholonClass found unknown attribute: " + attrName);
								}
							}
						}
					}
				}
				break;
			case IXmlReader.END_TAG:
				tagName = xmlReader.getName();
				if ("Color".equals(tagName)) {}
				else if ("Opacity".equals(tagName)) {}
				else if ("Font".equals(tagName)) {}
				else if ("Icon".equals(tagName)) {}
				else if ("ToolTip".equals(tagName)) {}
				else if ("Symbol".equals(tagName)) {}
				else if ("Format".equals(tagName)) {}
				else if ("Geo".equals(tagName)) {}
				else if ("Sound".equals(tagName)) {}
				else if ("DefaultContent".equals(tagName)) {}
				else if ("Anno".equals(tagName)) {}
				else if (tagName.equals(xhClassName)) { // end of a xholon class name
					if (navInfoRead.length() > 0) {
						// remove final NAVINFO_SEPARATOR
						navInfoRead = navInfoRead.substring(0, navInfoRead.length()-1);
					}
					attrXhc = inherHier.getClassNode(idRead);
					if (attrXhc != null) {
						if (navInfoRead.length() > 0) {
							String oldNavInfo = attrXhc.getNavInfo();
							// if both navInfoRead.length(), and getNavInfo().length() > 0, then concatenate
							if ((oldNavInfo != null) && (oldNavInfo.length() > 0)) {
								attrXhc.setNavInfo(oldNavInfo + NAVINFO_SEPARATOR + navInfoRead);
							}
							else {
								attrXhc.setNavInfo(navInfoRead);
							}
						}
						if (xhTypeRead != IXholonClass.XhtypeNone) {
							attrXhc.setXhType(xhTypeRead);
						}
						if (implNameRead != null) {
							if ((textVal != null) && (textVal.length() > 0)
									&& (implNameRead.endsWith("inline:"))) {
								// this must be a script
								attrXhc.setImplName(implNameRead + textVal);
								//System.out.println(attrXhc.getImplName());
							}
							else {
								attrXhc.setImplName(implNameRead);
							}
						}
					}
					else {
						//logger.error("Invalid XholonClass (" + tagName + " " + idRead + ") , attrXhc is null");
						//if (Msg.errorM) {System.out.println("invalid XholonClass");}
						this.consoleLog("Invalid XholonClass (" + tagName + " " + idRead + ") , attrXhc is null");
					}
					idRead = 0;
					xhTypeRead = IXholonClass.XhtypeNone;
					navInfoRead = "";
					implNameRead = null;
				}
				break;
			case IXmlReader.TEXT:
				textVal = xmlReader.getText().trim();
				if (textVal.length() > 0) {
					if ("Color".equals(tagName)) {
						((IDecoration)existingXholonClass).setColor(textVal);
					}
					else if ("Font".equals(tagName)) {
						((IDecoration)existingXholonClass).setFont(textVal);
					}
					else if ("Opacity".equals(tagName)) {
						((IDecoration)existingXholonClass).setOpacity(textVal);
					}
					else if ("Icon".equals(tagName)) {
						((IDecoration)existingXholonClass).setIcon(textVal);
					}
					else if ("ToolTip".equals(tagName)) {
						((IDecoration)existingXholonClass).setToolTip(textVal);
					}
					else if ("Symbol".equals(tagName)) {
						((IDecoration)existingXholonClass).setSymbol(textVal);
					}
					else if ("Format".equals(tagName)) {
						((IDecoration)existingXholonClass).setFormat(textVal);
					}
					else if ("Geo".equals(tagName)) {
						((IDecoration)existingXholonClass).setGeo(textVal);
					}
					else if ("Sound".equals(tagName)) {
						((IDecoration)existingXholonClass).setSound(textVal);
					}
					else if ("DefaultContent".equals(tagName)) {
						existingXholonClass.setDefaultContent(textVal);
					}
					else if ("Anno".equals(tagName)) {
					  existingXholonClass.setAnnotation(textVal);
					}
				}
				break;
			default:
				break;
			} // end switch
			eventType = xmlReader.next();
		} // end while
		return null;
	} // end xml2XhDetails()
	
	/**
	 * Returns an instance of IXholonClass based on the current tag in the XmlReader.
	 * @param tagName The name of the current XML node.
	 * @param parentXholon The parent of the new xholon class.
	 * @param xmlReader The XML reader.
	 * @param nextId The id that the new node should have.
	 * @return An IXholonClass, or null.
	 */
	protected IXholonClass newXholon(String tagName, IXholon parentXholon, IXmlReader xmlReader)
	{
		String namespaceURI = xmlReader.getNamespaceURI();
		String prefix = xmlReader.getPrefix();
		IXholonClass existingXholonClass = null;
		if ((prefix != null) && (prefix.length() > 0)) {
			existingXholonClass = inherHier.getClassNode(prefix + ":" + tagName);
		}
		else {
			existingXholonClass = inherHier.getClassNode(tagName);
		}
		if (existingXholonClass != null) {
			// this is a duplicate XholonClass
			return existingXholonClass;
		}
		IXholonClass newXholonClass = null;
		try {
				newXholonClass = factory.getXholonClassNode();
		} catch (XholonConfigurationException e) {
			//logger.error(e.getMessage(), e.getCause());
			this.consoleLog(e.getMessage() + " " + e.getCause());
			return null; // TODO not sure what should be returned in this case
		}
		
		//newXholonClass.setId(nextId);
		setId(newXholonClass, xmlReader);
		newXholonClass.setName(tagName);
		newXholonClass.setApp(app);
		if ((prefix != null) && (prefix.length() > 0)) {
			newXholonClass.setPrefixed(true);
		}
		newXholonClass.setMechanism(tagName, namespaceURI, prefix, newXholonClass.getId());
		// set the parent
		if (parentXholon != null) {
			newXholonClass.appendChild(getSuperClass(parentXholon, xmlReader));
		}
		return newXholonClass;
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
		if (((IXholonClass)node).hasAncestor(CeAttribute.AttributeCE)) {
			node.setVal(val);
		}
		else {
			IReflection ir = ReflectionFactory.instance();
			ir.setAttributeVal(node, attrName, val, 0);
		}
	}
	
	/**
	 * Get the super class of the current node.
	 * ex: &lt;MeTTTaModel superClass="AppClientModel"/&gt;
	 * @param defaultSuperClass The default super class.
	 * @param xmlReader The XML Reader.
	 * @return The super class specified by an optional superClass XML attribute, or the default super class.
	 */
	protected IXholon getSuperClass(IXholon defaultSuperClass, IXmlReader xmlReader)
	{
		IXholon superClass = defaultSuperClass;
		int attrCount = xmlReader.getAttributeCount();
		if (attrCount > 0) {
			for (int attrNum = 0; attrNum < attrCount; attrNum++) {
				if (xmlReader.getAttributeName(attrNum).equals("superClass")) {
					String superClassName = xmlReader.getAttributeValue(attrNum);
					superClass = app.getClassNode(superClassName);
					if (superClass == null) {
						superClass = defaultSuperClass;
						//logger.warn("Xml2XholonClass unable to find superClass: " + superClassName);
						this.consoleLog("Xml2XholonClass unable to find superClass: " + superClassName);
					}
					break;
				}
			}
		}
		return superClass;
	}
	
	/**
	 * Set the ID of the current XholonClass.
	 * @param currentXhClass The current newly-created XholonClass.
	 * @param xmlReader The XML REader.
	 */
	protected void setId(IXholon currentXhClass, IXmlReader xmlReader) {
		int attrCount =xmlReader.getAttributeCount();
		String namespaceURI = xmlReader.getNamespaceURI();
		if (attrCount > 0) {
			for (int attrNum = 0; attrNum < attrCount; attrNum++) {
				String attrName = xmlReader.getAttributeName(attrNum);
				String attrValue = xmlReader.getAttributeValue(attrNum);
				if (attrName.equals("id")) {
					// this is probably a new mechanism, with a new id sequence
					int nextId = Integer.parseInt(attrValue);
					if ((namespaceURI == null) || (namespaceURI.length() == 0)
							|| (MECHANISM_DEFAULT_NAMESPACEURI.equals(xmlReader.getNamespaceURI()))) {
						// this XholonClass is in the default Xholon namespace
						app.setNextXholonClassId(nextId);
					}
					else {
						// this XholonClass is in some other namespace
						lastIdUsed = nextId;
					}
					break;
				}
			}
		}
		if ((namespaceURI == null) || (namespaceURI.length() == 0)
				|| (MECHANISM_DEFAULT_NAMESPACEURI.equals(namespaceURI))) {
			// this XholonClass is in the default Xholon namespace
			currentXhClass.setId(app.getNextXholonClassId());
		}
		else {
			// this XholonClass is in some other namespace
			currentXhClass.setId(lastIdUsed++);
		}
	}
}

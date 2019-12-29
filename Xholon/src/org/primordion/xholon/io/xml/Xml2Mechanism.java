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

import org.primordion.xholon.base.IDecoration;
import org.primordion.xholon.base.IMechanism;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.exception.XholonConfigurationException;

/**
 * Transform XML into a Xholon Mechanism sub-tree.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on August 1, 2009)
 */
@SuppressWarnings("serial")
public class Xml2Mechanism extends AbstractXml2Xholon_gwt implements IXml2Xholon {
	
	/** Name of the Java class that implements mechanism Xholons. */
	private String implName = "org.primordion.xholon.base.Mechanism";
	
	protected int nextMechId = 0;
	
	protected int getNextMechId() {
		return this.nextMechId++;
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.AbstractXml2Xholon#xml2Xh(org.primordion.xholon.base.IXholon, org.primordion.xholon.io.xml.XmlReader, int)
	 */
	public IXholon xml2Xh(IXholon parentXholon, IXmlReader xmlReader, int eventType)
	{
		//System.out.println(eventType);
		int state = IXmlReader.NULL_EVENT;
		IXholon currentXholon = null;
		String tagName = null;
		boolean includeContentsFound = true;
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
					if ("NamespaceUri".equals(tagName)) {}
					else if ("DefaultPrefix".equals(tagName)) {}
					else if ("RangeStart".equals(tagName)) {}
					else if ("RangeEnd".equals(tagName)) {}
					else if ("Color".equals(tagName)) {}
					else if ("Font".equals(tagName)) {}
					else if ("Icon".equals(tagName)) {}
					else if ("ToolTip".equals(tagName)) {}
					else if ("include".equals(tagName) && (xmlReader.getPrefix().equals(xincludePrefix))) {
						// there may be a recursive hierarchy of include and fallback elements
						// keep passing the same parentXholon down the hierarchy until find a match
						// this is an OR hierarchy, rather than an AND hierarchy
						includeContentsFound = true;
						//System.out.println("include: " + parentXholon);
						
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
						else if (uri.charAt(0) != '.') {
							uri = xincludePath + uri;
						}
						if (parentXholon == null) {
							// there's no point processing the include file if there's no parent to attach it to
							currentXholon = null;
						}
						else {
							//currentXholon = xmlFile2Xholon(uri, parentXholon);
							currentXholon = xmlUri2Xholon(uri, parentXholon);
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
					}
					else if ("fallback".equals(tagName) && (xmlReader.getPrefix().equals(xincludePrefix))) {
						// the parent of a fallback element must be an include element
						//System.out.println("fallback: " + parentXholon);
						currentXholon = parentXholon; // be ready to pass the parentXholon down another level
					}
					else { // regular elements
						currentXholon = newXholon(tagName, parentXholon, xmlReader);
						if (currentXholon != null) {
							int attrCount = xmlReader.getAttributeCount();
							for (int i = 0; i < attrCount; i++) {
								String attrName = xmlReader.getAttributeName(i);
								String attrValue = xmlReader.getAttributeValue(i);
								if ("roleName".equals(attrName)) {
									currentXholon.setRoleName(attrValue);
								}
								else {
									// TODO
								}
							}
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
				
				state = IXmlReader.END_TAG;
				eventType = xmlReader.next();
				break;
			case IXmlReader.TEXT:
				String textVal = xmlReader.getText().trim();
				if (textVal.length() > 0) {
					if ("NamespaceUri".equals(tagName)) {
						((IMechanism)parentXholon).setNamespaceUri(textVal);
					}
					else if ("DefaultPrefix".equals(tagName)) {
						((IMechanism)parentXholon).setDefaultPrefix(textVal);
					}
					else if ("RangeStart".equals(tagName)) {
						((IMechanism)parentXholon).setRangeStart(Integer.parseInt(textVal));
					}
					else if ("RangeEnd".equals(tagName)) {
						((IMechanism)parentXholon).setRangeEnd(Integer.parseInt(textVal));
					}
					else if ("Color".equals(tagName)) {
						((IDecoration)parentXholon).setColor(textVal);
					}
					else if ("Font".equals(tagName)) {
						((IDecoration)parentXholon).setFont(textVal);
					}
					else if ("Icon".equals(tagName)) {
						((IDecoration)parentXholon).setIcon(textVal);
					}
					else if ("ToolTip".equals(tagName)) {
						((IDecoration)parentXholon).setToolTip(textVal);
					}
				}
				eventType = xmlReader.next();
				break;
			default:
				eventType = xmlReader.next();
				break;
			}
		}
		return currentXholon;
	}
	
	/**
	 * Returns an instance of IXholon based on the current tag in the XmlReader.
	 * @param tagName The name of the current XML node.
	 * @param parentXholon The parent of the new xholon.
	 * @param xmlReader The XML reader. NOT USED
	 * @return An IXholon, or null.
	 */
	protected IXholon newXholon(String tagName, IXholon parentXholon, IXmlReader xmlReader)
	{
		IXholon newXholon = null;
		try {
			newXholon = factory.getXholonNode(implName);
			//newXholon.setId(app.getNextId());
			newXholon.setId(this.getNextMechId());
		} catch (XholonConfigurationException e) {
			logger.error(e.getMessage(), e.getCause());
			return null; // TODO not sure what should be returned in this case
		}
		// set the parent
		if (parentXholon != null) {
			newXholon.appendChild(parentXholon);
		}
		
		return newXholon;
	}
	
}

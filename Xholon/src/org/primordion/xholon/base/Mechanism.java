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

package org.primordion.xholon.base;

import java.util.ArrayList;
import java.util.List;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;

/**
 * A mechanism is a collection of related IXholonClasses.
 * It's the Xholon equivalent of an XML namespace.
 * A mechanism can be a "pure" mechanism, or it can be a viewer mechanism.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on April 10, 2009)
 */
public class Mechanism extends Xholon implements IMechanism, IDecoration, Comparable {
	private static final long serialVersionUID = 5213421348340546398L;

	/**
	 * The name of the mechanism.
	 * The name of the .xml file in config/_common,
	 * which is also (usually) the name of the root node for the mechanism.
	 * ex: Action StateMachineEntity SwingEntity
	 */
	private String roleName = null;
	
	/**
	 * URI that identifies the namespace for the mechanism.
	 * ex: http://www.primordion.com/namespace/Action
	 */
	private String namespaceUri = "";
	
	/**
	 * Default namespace prefix.
	 * ex: actn
	 */
	private String defaultPrefix = "";
	
	/**
	 * Start id for xholons in the mechanism.
	 */
	private int rangeStart = RANGE_NULL;
	
	/**
	 * End id for xholons in the mechanism.
	 */
	private int rangeEnd = RANGE_NULL;
	
	/**
	 * Optional object that can contain decorations such as color, font, icon, toolTip.
	 */
	private IDecoration decoration = null;
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getNamespaceUri() {
		return namespaceUri;
	}

	public void setNamespaceUri(String namespaceUri) {
		this.namespaceUri = namespaceUri;
	}

	public String getDefaultPrefix() {
		return defaultPrefix;
	}

	public void setDefaultPrefix(String defaultPrefix) {
		this.defaultPrefix = defaultPrefix;
	}

	public int getRangeStart() {
		return rangeStart;
	}

	public void setRangeStart(int rangeStart) {
		this.rangeStart = rangeStart;
	}

	public int getRangeEnd() {
		return rangeEnd;
	}

	public void setRangeEnd(int rangeEnd) {
		this.rangeEnd = rangeEnd;
	}
	
	/*
	 * @see org.primordion.xholon.base.IMechanism#getMembers()
	 */
	public IXholonClass[] getMembers() {
		List<IXholonClass> list = new ArrayList<IXholonClass>();
		IXholonClass rootXhc = this.getApp().getXhcRoot();
		getMembersRecurse(rootXhc, list);
		return (IXholonClass[])list.toArray(new IXholonClass[list.size()]);
	}
	
	/**
	 * Add members to the list, by recursively walking the IXholonClass inheritance hierarchy.
	 * @param xhcNode
	 * @param list
	 */
	protected void getMembersRecurse(final IXholonClass xhcNode, final List<IXholonClass> list) {
		if (xhcNode.getMechanism() == this) {
			list.add(xhcNode);
		}
		IXholonClass newXhcNode = (IXholonClass)xhcNode.getFirstChild();
		while (newXhcNode != null) {
			getMembersRecurse(newXhcNode, list);
			newXhcNode = (IXholonClass)newXhcNode.getNextSibling();
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IMechanism#getDecoration()
	 */
	public IDecoration getDecoration() {
	  return decoration;
	}
	
	/*
	 * @see org.primordion.xholon.base.IMechanism#setDecoration(IDecoration)
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
		decoration.setSymbol(format);
	}

	/*
	 * @see org.primordion.xholon.base.Xholon#configure()
	 */
	public void configure()
	{
		
		if (!isRootNode()) {
			// set this node's XholonClass, to the same XholonClass as that of the Mechanism root node
			setXhc(getRootNode().getXhc());
		}
		super.configure();
	}
	
	/*
	 * @see org.primordion.xholon.base.IMechanism#createNewUserMechanism(java.lang.String, java.lang.String, java.lang.String, int, int, org.primordion.xholon.app.IApplication)
	 */
	public IMechanism createNewUserMechanism(
			String roleName, String namespaceUri, String defaultPrefix,
			int rangeStart, int rangeEnd,
			IApplication app)
	{
		if (!("XhMechanisms".equals(getRoleName()))) {return null;}
		IMechanism userMechanisms = (IMechanism)getFirstChild();
		while (userMechanisms != null) {
			if ("UserMechanisms".equals(userMechanisms.getRoleName())) {
				break;
			}
			userMechanisms = (IMechanism)userMechanisms.getNextSibling();
		}
		if (userMechanisms != null) {
			IMechanism newNode = new Mechanism();
			newNode.setXhc(app.getClassNode("UserMechanism"));
			newNode.setRoleName(roleName);
			newNode.setNamespaceUri(namespaceUri);
			newNode.setDefaultPrefix(defaultPrefix);
			newNode.setRangeStart(rangeStart);
			newNode.setRangeEnd(rangeEnd);
			newNode.appendChild(userMechanisms);
			return newNode;
		}
		return null;
	}
	
	/*
	 * @see org.primordion.xholon.base.IMechanism#findMechanism(java.lang.String)
	 */
	public IMechanism findMechanism(String namespaceUri)
	{
		if (namespaceUri == null) {return null;}
		if ((this.namespaceUri != null) && (this.namespaceUri.equals(namespaceUri))) {
			return this;
		}
		IMechanism mechNode = null;
		// execute recursively
		if (firstChild != null) {
			mechNode = ((IMechanism)firstChild).findMechanism(namespaceUri);
			if (mechNode != null) {
				return mechNode;
			}
		}
		if (nextSibling != null) {
			mechNode = ((IMechanism)nextSibling).findMechanism(namespaceUri);
			if (mechNode != null) {
				return mechNode;
			}
		}
		return mechNode;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getName()
	 */
	public String getName() {
		if (roleName == null) {
			return "Mechanism";
		}
		else {
			return roleName;
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#toXml(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.XmlWriter)
	 */
	public void toXml(IXholon2Xml xholon2xml, IXmlWriter xmlWriter)
	{
		String localName = getXhcName();
		xmlWriter.writeStartElement(localName);
		xmlWriter.writeAttribute("roleName", getRoleName());
		toXmlAttributes(xholon2xml, xmlWriter);
		IXholon childNode = getFirstChild();
		while (childNode != null) {
			childNode.toXml(xholon2xml, xmlWriter);
			childNode = childNode.getNextSibling();
		}
		xmlWriter.writeEndElement(localName);
	}
	/*
	 * @see org.primordion.xholon.base.IXholon#toXmlAttributes(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.XmlWriter)
	 */
	public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter)
	{
		/*
		<XholonMechanism roleName="Action">
			<NamespaceUri>http://www.primordion.com/namespace/Action</NamespaceUri>
			<DefaultPrefix>actn</DefaultPrefix>
			<RangeStart>1010400</RangeStart>
			<RangeEnd>1010499</RangeEnd>
		</XholonMechanism>
		 */
		// don't save any attributes if there's no NamespaceUri
		if (getNamespaceUri() == null) {return;}
		if (getNamespaceUri().length() == 0) {return;}
		toXmlAttribute(xholon2xml, xmlWriter, "NamespaceUri", getNamespaceUri(), null);
		toXmlAttribute(xholon2xml, xmlWriter, "DefaultPrefix", getDefaultPrefix(), null);
		toXmlAttribute(xholon2xml, xmlWriter, "RangeStart", Integer.toString(getRangeStart()), null);
		toXmlAttribute(xholon2xml, xmlWriter, "RangeEnd", Integer.toString(getRangeEnd()), null);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#toXmlAttribute(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.XmlWriter, java.lang.String, java.lang.String, java.lang.Class)
	 */
	public void toXmlAttribute(IXholon2Xml xholon2xml, IXmlWriter xmlWriter, String name, String value, Class clazz)
	{
		xmlWriter.writeStartElement(name);
		xmlWriter.writeText(value);
		xmlWriter.writeEndElement(name);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.primordion.xholon.base.Xholon#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o)
	{
		return this.namespaceUri.compareTo(((IMechanism)o).getNamespaceUri());
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toString()
	 */
	public String toString() {
		String outStr = getName();
		if (getNamespaceUri() != null) {
			outStr += " [NamespaceUri " + getNamespaceUri() + "]";
		}
		if (getDefaultPrefix() != null) {
			outStr += " [DefaultPrefix " + getDefaultPrefix() + "]";
		}
		if (getRangeStart() != RANGE_NULL) {
			outStr += " [RangeStart " + getRangeStart() + "]";
		}
		if (getRangeEnd() != RANGE_NULL) {
			outStr += " [RangeEnd " + getRangeEnd() + "]";
		}
		if (getColor() != null) {
			outStr += " [Color " + getColor() + "]";
		}
		if (getFont() != null) {
			outStr += " [Font " + getFont() + "]";
		}
		if (getIcon() != null) {
			outStr += " [Icon " + getIcon() + "]";
		}
		if (getToolTip() != null) {
			int endIndex = getToolTip().length();
			if (endIndex > 10) {
				endIndex = 10;
			}
			outStr += " [ToolTip " + getToolTip().substring(0, endIndex) + "]";
		}
		return outStr;
	}
	
}

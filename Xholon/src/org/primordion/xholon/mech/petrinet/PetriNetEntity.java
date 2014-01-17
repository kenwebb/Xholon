/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2012 Ken Webb
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

package org.primordion.xholon.mech.petrinet;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.script.Plot;

/**
 * Petri net entity.
 * This is the default Java class for any Petri net entity that
 * does not have a more specific Java class.
 * This includes: InputArcs, OutputArcs, Places, Transitions
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on Oct 20, 2012)
 */
public class PetriNetEntity extends XholonWithPorts {
	private static final long serialVersionUID = 7930646098334429811L;
	
	private String roleName = null;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setRoleName(java.lang.String)
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getRoleName()
	 */
	public String getRoleName() {
		return roleName;
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#toXml(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.XmlWriter)
	 */
	public void toXml(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {
		if (this.getXhc().hasAncestor("Places")) {
			// write start element
			xmlWriter.writeStartElement(this.getXhcName());
			xholon2xml.writeSpecial(this);
			// write children
			IXholon childNode = getFirstChild();
			while (childNode != null) {
				childNode.toXml(xholon2xml, xmlWriter);
				childNode = childNode.getNextSibling();
			}
			// write Plot script after all the places
			Plot.writeXml(xholon2xml, xmlWriter, this.getApp());
			// write end element
			xmlWriter.writeEndElement(this.getXhcName());
		}
		else {
			super.toXml(xholon2xml, xmlWriter);
		}
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toXmlAttributes(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.IXmlWriter)
	 */
	public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {}
	
}

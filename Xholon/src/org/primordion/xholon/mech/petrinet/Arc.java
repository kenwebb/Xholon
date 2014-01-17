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

import java.util.ArrayList;
import java.util.List;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;

/**
 * Petri net Arc.
 * Typically an Arc is a child of a Petri net Transition node.
 * An Arc references a single Petri net Place node.
 * All arcs run physically from a Transition to a Place.
 *   But logically they run either way.
 * Separate InputArc and OutputArc classes
 *   both can have the same interface, so Arc owner can call all arcs without knowing which type they are
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on July 20, 2012)
 */
public abstract class Arc extends XholonWithPorts {
	private static final long serialVersionUID = -425469979211797030L;

	/**
	 * A port reference to a single Petri net Place.
	 */
	protected IXholon place = null;
	
	/**
	 * The specification of the port reference to a single Petri net Place,
	 * formatted as an XPath string.
	 */
	private String connector = null;
	
	/**
	 * The minimum number of tokens that must be present in an input place,
	 * before the Transition that owns this Arc will fire.
	 * Or, the number of tkens that will be created in an output place,
	 * if the Transition fires.
	 */
	protected Object weight = new Double(1.0);
	
	private String roleName = null;

	public void setRoleName(String roleName) {this.roleName = roleName;}
	public String getRoleName() {return roleName;}
	
	/**
	 * Set the arc weight.
	 */
	public void setVal(double val) {
		weight = new Double(val);
	}
	
	/**
	 * Set the arc weight.
	 */
	public void setVal(int val) {
		weight = new Double(val);
	}
	
	/**
	 * Set the arc weight.
	 */
	public void setVal(String val) {
		weight = new Double(val);
	}
	
	/**
	 * Get arc weight.
	 */
	public double getVal() {
		return (Double)weight;
	}
	
	/*
	 * @see org.primordion.xholon.base.XholonWithPorts#postConfigure()
	 */
	public void postConfigure() {
		super.postConfigure();
		// set value of place, from connector String, using XPath
		if (connector != null) {
			if (connector.startsWith("#xpointer(")) {
				// ex: #xpointer(ancestor::PetriNet/Places/Place[@roleName='p1'])
				String expression = connector.substring(10, connector.length() - 1);
				setPlace(this.getXPath().evaluate(expression, this));
			}
			else {
				// allow non-xpointer strings
				setPlace(this.getXPath().evaluate(connector, this));
			}
		}
		if (place == null) {
			// XPath was unable to find the place
			Xholon.logger.warn(this.getName() + " is unable to locate place " + connector);
			this.setVal(0.0); // set weight to 0 to make it inactive
		}
	}
	
	/**
	 * Is this an active arc ?
	 * An arc is active if it has a weight > 0.0
	 * @return true or false
	 */
	public boolean isActive() {
		return ((Double)weight > 0.0) ? true : false;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setAttributeVal(java.lang.String, java.lang.String)
	 */
	public int setAttributeVal(String attrName, String attrVal) {
		if (attrName.equals("connector")) {
			setConnector(attrVal);
		}
		else if (attrName.equals("weight")) {
			setVal(attrVal);
		}
		return 0;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal_boolean()
	 */
	public boolean getVal_boolean() {
		return place.getVal() >= this.getVal();
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#performVoidActivity(org.primordion.xholon.base.IXholon)
	 */
	public abstract void performVoidActivity(IXholon activity);
	
	public IXholon getPlace() {
		return place;
	}
	
	public void setPlace(IXholon place) {
		this.place = place;
	}
	
	public String getConnector() {
		return connector;
	}
	
	public void setConnector(String connector) {
		this.connector = connector;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getAllPorts()
	 */
	public List<PortInformation> getAllPorts() {
		List<PortInformation> portList = new ArrayList<PortInformation>();
		PortInformation portInfo = new PortInformation("place", this.getPlace());
		portList.add(portInfo);
		return portList;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toString()
	 */
	public String toString() {
		String outStr = getName();
		List<PortInformation> portList = getAllPorts();
		if (portList.size() > 0) {
			outStr += " [";
			for (int i = 0; i < portList.size(); i++) {
				PortInformation portInfo = (PortInformation)portList.get(i);
				outStr += " " + portInfo;
			}
			outStr += "]";
		}
		outStr += " weight:" + getVal();
		return outStr;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toXmlAttributes(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.IXmlWriter)
	 */
	public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {
		if (connector != null) {xmlWriter.writeAttribute("connector", connector);}
		xmlWriter.writeAttribute("weight", Double.toString(getVal()));
	}
	
}

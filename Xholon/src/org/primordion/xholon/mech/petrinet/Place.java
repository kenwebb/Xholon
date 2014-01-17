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

import java.util.List;

import org.primordion.xholon.base.PortInformation;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;

/**
 * Place
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on July 20, 2012)
 */
public class Place extends Xholon {
	private static final long serialVersionUID = 4272596417608985819L;

	/**
	 * The number of tokens currently at this Place.
	 */
	private Object token = new Double(0.0);
	
	/**
	 * The optional units that the tokens are measured in.
	 * If no units are specified, the units are assumed to be dimensionless or simple amounts.
	 * ex: m  °C  m/s
	 */
	private String units = null;
	
	private String roleName = null;

	public void setRoleName(String roleName) {this.roleName = roleName;}
	public String getRoleName() {return roleName;}
	
	public void setVal(double val) {
		token = new Double(val);
	}
	
	public void setVal(int val) {
		token = new Double(val);
	}
	
	public void setVal(String val) {
		token = new Double(val);
	}
	
	public double getVal() {
		return (Double)token;
	}
	
	public void incVal(double inc) {
		setVal(getVal() + inc);
	}
	
	public void decVal(double dec) {
		setVal(getVal() - dec);
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setAttributeVal(java.lang.String, java.lang.String)
	 */
	public int setAttributeVal(String attrName, String attrVal) {
		if (attrName.equals("token")) {
			setVal(attrVal);
		}
		else if (attrName.equals("units")) {
			setUnits(attrVal);
		}
		return 0;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toString()
	 */
	@SuppressWarnings("rawtypes")
	public String toString() {
		String outStr = getName();
		List portList = getAllPorts();
		if (portList.size() > 0) {
			outStr += " [";
			for (int i = 0; i < portList.size(); i++) {
				PortInformation portInfo = (PortInformation)portList.get(i);
				outStr += " " + portInfo;
			}
			outStr += "]";
		}
		outStr += " token:" + getVal();
		if (getUnits() != null) {
			outStr += " units:" + getUnits();
		}
		return outStr;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#toXmlAttributes(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.IXmlWriter)
	 */
	public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter) {
		if (roleName != null) {xmlWriter.writeAttribute("roleName", roleName);}
		xmlWriter.writeAttribute("token", Double.toString(getVal()));
		if (units != null) {xmlWriter.writeAttribute("units", units);}
	}
	
	public Object getToken() {
		return token;
	}
	
	public void setToken(Object token) {
		this.token = token;
	}
	
	public String getUnits() {
		return units;
	}
	
	public void setUnits(String units) {
		this.units = units;
	}
	
}

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

package org.primordion.xholon.mech.mathml.content;

import org.primordion.xholon.base.IReflection;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.ReflectionFactory;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;

/**
 * MathML 2.0 - ci
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://www.w3.org/Math/">MathML website</a>
 * @since 0.8 (Created on May 8, 2009)
 */
public class Ci extends XholonWithPorts {
	
	/**
	 * The Xholon node that contains the bound variable.
	 */
	private IXholon bvar = null;
	
	/**
	 * The name of the bound variable in the bvar node.
	 */
	private String bvarAttr = null;
	
	/**
	 * Set the Xholon node that contains the bound variable.
	 * @param bvar
	 */
	public void setBvar(IXholon bvar) {
		this.bvar = bvar;
	}
	
	/**
	 * Get the Xholon node that contains the bound variable.
	 * @return
	 */
	public IXholon getBvar() {
		return bvar;
	}

	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(java.lang.String)
	 */
	public void setVal(String val) {
		bvarAttr = val;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal_String()
	 */
	public String getVal_String() {
		return bvarAttr;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal()
	 */
	public double getVal()
	{
	  IReflection ir = ReflectionFactory.instance();
		return ir.getAttributeDoubleVal(bvar, bvarAttr);
	}
	
	/*
	 * @see org.primordion.xholon.base.IXholon#toXmlAttributes(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.XmlWriter)
	 */
	public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter)
	{
		xmlWriter.writeAttribute("type", "real");
		xmlWriter.writeText(Double.toString(getVal()));
	}
}

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

import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.mech.mathml.MathML;

/**
 * MathML 2.0 - cn
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://www.w3.org/Math/">MathML website</a>
 * @since 0.8 (Created on April 22, 2009)
 */
public class Cn extends MathML {
	
	// implements org.w3c.dom.mathml.MathMLCnElement
	//public String                 getType();
	//public void                   setType(String type);
	//public String                 getBase();
	//public void                   setBase(String base);
    //public int                    getNargs();
	
	/**
	 * The value of this constant.
	 * This is MathML type "real".
	 */
	private double val = 0.0;
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getVal()
	 */
	public double getVal()
	{
		return val;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(double)
	 */
	public void setVal(double val)
	{
		this.val = val;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(float)
	 */
	public void setVal(float val)
	{
		this.val = val;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#setVal(int)
	 */
	public void setVal(int val)
	{
		this.val = val;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#getAnnotation()
	 */
	public String getAnnotation()
	{
		return "<cn type=\"real\">" + val + "</cn>";
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#hasAnnotation()
	 */
	public boolean hasAnnotation()
	{
		return true;
	}
	
	/*
	 * @see org.primordion.xholon.base.Xholon#showAnnotation()
	 */
	public void showAnnotation()
	{
		System.out.println(getAnnotation());
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

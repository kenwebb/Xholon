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

package org.primordion.xholon.service;

import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;

/**
 * This is a very simple concrete extension of AbstractXolonService.
 * It can be used as a placeholder,
 * or where the functionality provided by AbstractXolonService is enough.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8(Created on August 30, 2009)
 */
public class DefaultXholonService extends AbstractXholonService {
	private static final long serialVersionUID = -2849056965626452959L;

	/*
	 * @see org.primordion.xholon.base.IXholon#toXmlAttributes(org.primordion.xholon.io.xml.IXholon2Xml, org.primordion.xholon.io.xml.XmlWriter)
	 */
	public void toXmlAttributes(IXholon2Xml xholon2xml, IXmlWriter xmlWriter)
	{
		if ("XholonInternalServices".equals(this.getXhcName())) {
			xmlWriter.writeNamespace("attr", "http://www.primordion.com/namespace/Attribute");
		}
		else {
			super.toXmlAttributes(xholon2xml, xmlWriter);
		}
	}
	
}

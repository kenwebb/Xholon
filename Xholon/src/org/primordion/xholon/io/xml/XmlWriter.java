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

import org.primordion.xholon.base.Xholon;

/**
 * The abstract base for classes that write XML.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on August 1, 2009)
 */
public abstract class XmlWriter extends Xholon implements IXmlWriter {
	
	/*
	 * @see org.primordion.xholon.io.xml.IXmlWriter#createNew(java.lang.Object)
	 */
	public abstract void createNew(Object out);
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXmlWriter#writeStartDocument()
	 */
	public abstract void writeStartDocument();
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXmlWriter#writeEndDocument()
	 */
	public abstract void writeEndDocument();
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXmlWriter#writeStartElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public abstract void writeStartElement(String prefix, String localName, String namespaceURI);
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXmlWriter#writeStartElement(java.lang.String)
	 */
	public abstract void writeStartElement(String name);
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXmlWriter#writeEndElement(java.lang.String, java.lang.String)
	 */
	public abstract void writeEndElement(String name, String namespaceURI);
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXmlWriter#writeEndElement(java.lang.String)
	 */
	public abstract void writeEndElement(String name);
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXmlWriter#writeNamespace(java.lang.String, java.lang.String)
	 */
	public abstract void writeNamespace(String prefix, String namespaceURI);
	/* 
	 * @see org.primordion.xholon.io.xml.IXmlWriter#writeAttribute(java.lang.String, java.lang.String)
	 */
	public abstract void writeAttribute(String name, String value);
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXmlWriter#writeText(java.lang.String)
	 */
	public abstract void writeText(String text);
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXmlWriter#writeComment(java.lang.String)
	 */
	public abstract void writeComment(String data);
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXmlWriter#getWriterName()
	 */
	public abstract String getWriterName();
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXmlWriter#flush()
	 */
	public abstract void flush();
}

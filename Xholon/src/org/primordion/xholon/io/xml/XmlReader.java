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
 * The abstract base for classes that read XML.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on August 1, 2009)
 */
public abstract class XmlReader extends Xholon implements IXmlReader {
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXmlReader#createNew(java.io.Reader)
	 */
	public abstract void createNew(Object in);
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXmlReader#getEventType()
	 */
	public abstract int getEventType();
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXmlReader#next()
	 */
	public abstract int next();
	
	/*
	 * @see org.primordion.xholon.io.xml.IXmlReader#nextToken()
	 */
	public int nextToken() {
		return next();
	}
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXmlReader#getName()
	 */
	public abstract String getName();
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXmlReader#getText()
	 */
	public abstract String getText();
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXmlReader#getAttributeName(int)
	 */
	public abstract String getAttributeName(int index);
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXmlReader#getAttributeValue(int)
	 */
	public abstract String getAttributeValue(int index);
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXmlReader#getAttributeCount()
	 */
	public abstract int getAttributeCount();
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXmlReader#getPrefix()
	 */
	public abstract String getPrefix();
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXmlReader#getNamespaceURI()
	 */
	public abstract String getNamespaceURI();
	
	/*
	 * @see org.primordion.xholon.io.xml.IXmlReader#getUnderlyingReader()
	 */
	public abstract Object getUnderlyingReader();
}

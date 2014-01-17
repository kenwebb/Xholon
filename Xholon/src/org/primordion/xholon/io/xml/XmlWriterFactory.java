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
 * This factory creates new instances of XmlWriter.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on August 1, 2009)
 */
@SuppressWarnings("serial")
public class XmlWriterFactory extends Xholon {
	
	/** The singleton instance of this class. */
	private static XmlWriterFactory xmlWriterFactory = null;
	
	/**
	 * Get a concrete instance of XMLWriter.
	 * @param out An output file, stream, string, or other type of writer,
	 * or a StringBuilder.
	 * @return An instance of XMLWriter, or null.
	 */
	public static IXmlWriter getXmlWriter(Object out)
	{
		IXmlWriter xmlWriter = null;
		if (xmlWriterFactory == null) {
			xmlWriterFactory = new XmlWriterFactory();
		}
		// try to make use of a XML pull parser (XPP)
		/* GWT
		if (xmlWriter == null) {
			xmlWriter = xmlWriterFactory.newXppInstance();
			if (xmlWriter != null) {
				xmlWriter.createNew(out);
			}
		}*/
		// if no XPP, then try to make use of a XML Stax stream reader
		/* GWT
		if (xmlWriter == null) {
			xmlWriter = xmlWriterFactory.newStaxInstance();
			if (xmlWriter != null) {
				xmlWriter.createNew(out);
			}
		}*/
		// if no Stax, then try to make use of a XML Str stream reader
		if (xmlWriter == null) {
			xmlWriter = xmlWriterFactory.newStrInstance();
			if (xmlWriter != null) {
				xmlWriter.createNew(out);
			}
		}
		if (xmlWriter == null) {
			// nothing more to try
		}
		return xmlWriter;
	}
	
	/**
	 * Create a new instance of an XML serializer (XPP).
	 * @return A new instance, or null.
	 */
	/* GWT
	protected IXmlWriter newXppInstance() {
		if (!exists("org.xmlpull.v1.XmlPullParserFactory")) {return null;}
    	Class clazz = null;
        try {
            clazz = Class.forName("org.primordion.xholon.io.xml.XmlXppWriter", false,
            		getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
        	getLogger().warn("Unable to find xpp XML writer.");
            return null;
        }
        try {
            if (clazz != null) {
            	return (IXmlWriter)clazz.newInstance();
            }
        } catch (IllegalAccessException e) {
        	getLogger().warn("Illegal access trying to instantiate xpp XML writer.");
            return null;
        } catch (InstantiationException e) {
        	getLogger().warn("Unable to instantiate xpp XML writer.");
            return null;
        }
        return null;
    }*/
	
	/**
	 * Create a new instance of a Stax writer.
	 * @return A new instance, or null.
	 */
	/* GWT
	protected IXmlWriter newStaxInstance() {
		if (!exists("javax.xml.stream.XMLInputFactory")) {return null;}
    	Class clazz = null;
        try {
            clazz = Class.forName("org.primordion.xholon.io.xml.XmlStaxWriter", false,
            		getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
        	getLogger().warn("Unable to find Stax XML writer.");
            return null;
        }
        try {
            if (clazz != null) {
            	return (IXmlWriter)clazz.newInstance();
            }
        } catch (IllegalAccessException e) {
        	getLogger().warn("Illegal access trying to instantiate Stax XML writer.");
            return null;
        } catch (InstantiationException e) {
        	getLogger().warn("Unable to instantiate Stax XML writer.");
            return null;
        }
        return null;
    }*/
	
		/**
	 * Create a new instance of a Str writer.
	 * @return A new instance, or null.
	 */
	protected IXmlWriter newStrInstance() {
	  return new XmlStrWriter();
	}
	
	/**
	 * Do the Reader classes (XPP or StAX) exist?
	 * @param classLoader A Java ClassLoader.
	 * @return true or false
	 */
	public boolean exists(String className)
	{
	  /* GWT
		try {
			getClass().getClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			//System.out.println("XmlWriterFactory: " + className + " classes do not exist.");
			return false;
		}
		//System.out.println("XmlWriterFactory: " + className + " classes do exist.");
		return true;*/
		return false;
	}
	
	/**
	 * testing
	 <?xml version="1.0" encoding="UTF-8"?>
	 <!-- This file was written by XmlXppWriter. -->
	 <One>
	 	<Two>
	 		<Three />
	 		<Four roleName="Dummy">Here's some text.</Four>
	 	</Two>
	 </One>
	 * @param args
	 */
	/*public static void main(String[] args) {
		java.io.Writer out = new java.io.StringWriter();
		IXmlWriter xmlWriter = XmlWriterFactory.getXmlWriter(out);
		xmlWriter.writeStartDocument();
		xmlWriter.writeComment(" This file was written by " + xmlWriter.getWriterName() + ". ");
		xmlWriter.writeStartElement("One");
		xmlWriter.writeStartElement("Two");
		xmlWriter.writeStartElement("Three");
		xmlWriter.writeEndElement("Three");
		xmlWriter.writeStartElement("Four");
		xmlWriter.writeAttribute("roleName", "Dummy");
		xmlWriter.writeText("Here's some text.");
		xmlWriter.writeEndElement("Four");
		xmlWriter.writeEndElement("Two");
		xmlWriter.writeEndElement("One");
		xmlWriter.writeEndDocument();
		System.out.println(out.toString());
	}*/
	
}

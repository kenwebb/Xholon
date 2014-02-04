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

package org.primordion.xholon.io.cherrytree;

//import java.io.Writer;

//import javax.xml.stream.FactoryConfigurationError;
//import javax.xml.stream.XMLOutputFactory;
//import javax.xml.stream.XMLStreamException;
//import javax.xml.stream.XMLStreamWriter;

//import org.primordion.xholon.io.xml.XmlWriter;
import org.primordion.xholon.io.xml.XmlStrWriter;

/**
 * A concrete class that knows how to write a Cherry Tree XML (.ctd) file through a Stax writer.
 * There is no underlying 3rd-party Cherry Tree StAX writer,
 * so this class will do the best it can on its own.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://www.giuspen.com/cherrytree/">Cherry Tree website</a>
 * @since 0.8.1 (Created on December 5, 2012)
 */
public class CherryTreeStrWriter extends XmlStrWriter {
	
	private static final String WRITERNAME = "CherryTreeStrWriter";
	
	//private XMLOutputFactory ctdWriterFactory = null;
	//private XMLStreamWriter ctdWriter = null;
	
	/**
	 * Accumulated text for the current node.
	 */
	private String richText = "";
	
	/**
	 * default constructor
	 */
	public CherryTreeStrWriter() {}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#createNew(java.lang.Object)
	 */
	//public void createNew(Object out) {
	  /* GWT
		// set factory
		try {
			ctdWriterFactory = XMLOutputFactory.newInstance();
		} catch (FactoryConfigurationError e) {
			logger.error("CherryTreeStaxWriter constructor", e);
		}
		// set writer
		try {
			ctdWriter = ctdWriterFactory.createXMLStreamWriter((Writer)out);
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}*/
	//}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeStartDocument()
	 */
	public void writeStartDocument()
	{
		/*try {
			ctdWriter.writeStartDocument();
			ctdWriter.writeStartElement("cherrytree");
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}*/
		super.writeStartDocument();
		super.writeStartElement("cherrytree");
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeEndDocument()
	 */
	public void writeEndDocument()
	{
		/*try {
			ctdWriter.writeEndElement(); // cherrytree
			ctdWriter.writeEndDocument();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}*/
		super.writeEndElement("cherrytree");
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeStartElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void writeStartElement(String prefix, String localName, String namespaceURI)
	{
		super.writeStartElement(prefix, localName, namespaceURI);
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeStartElement(java.lang.String)
	 */
	public void writeStartElement(String name)
	{
		//try {
			if ("Annotation".equals(name)) {return;}
			else {
				if (richText.length() > 0) {
					super.writeStartElement("rich_text");
					super.writeText(richText);
					super.writeEndElement("rich_text"); // rich_text
					richText = "";
				}
				super.writeStartElement("node");
				super.writeAttribute("name", name);
				super.writeAttribute("readonly", "False");
			}
		//} catch (XMLStreamException e) {
		//	e.printStackTrace();
		//}
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeEndElement(java.lang.String, java.lang.String)
	 */
	public void writeEndElement(String name, String namespaceURI)
	{
		writeEndElement(name);
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeEndElement(String name)
	 */
	public void writeEndElement(String name)
	{
		if ("Annotation".equals(name)) {return;}
		//try {
			if (richText.length() > 0) {
				super.writeStartElement("rich_text");
				super.writeText(richText);
				super.writeEndElement("rich_text"); // rich_text
				richText = "";
			}
			super.writeEndElement("node"); // node
		//} catch (XMLStreamException e) {
		//	e.printStackTrace();
		//}
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeNamespace(java.lang.String, java.lang.String)
	 */
	public void writeNamespace(String prefix, String namespaceURI)
	{
		// do nothing
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeAttribute(java.lang.String, java.lang.String)
	 */
	public void writeAttribute(String name, String value)
	{
		if ("prog_lang".equals(name)) {
			//try {
				super.writeAttribute(name, value);
			//} catch (XMLStreamException e) {
			//	e.printStackTrace();
			//}
		}
		else {
			// Cherry Tree ignores attributes, so turn them into rich_text text
			saveRichText(name + ": " + value);
		}
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeText(java.lang.String)
	 */
	public void writeText(String text)
	{
		saveRichText(text);
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeComment(java.lang.String)
	 */
	public void writeComment(String data)
	{
		/*try {
			ctdWriter.writeComment(data);
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}*/
		super.writeComment(data);
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#getWriterName()
	 */
	public String getWriterName()
	{
		return WRITERNAME;
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#flush()
	 */
	public void flush()
	{
		/*try {
			ctdWriter.flush();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}*/
		// no action required
	}
	
	/**
	 * Save some text for the current node.
	 * @param txt
	 */
	protected void saveRichText(String txt) {
		if (richText.length() == 0) {
			richText += txt;
		}
		else {
			richText += "\n" + txt;
		}
	}

}

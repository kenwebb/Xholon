/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2010 Ken Webb
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

package org.primordion.xholon.io.json;

import com.google.gwt.core.client.JsonUtils;

//import java.io.Writer;

//import javax.xml.stream.XMLStreamException;

//import org.codehaus.jettison.AbstractXMLStreamWriter;
//import org.codehaus.jettison.mapped.Configuration;
//import org.codehaus.jettison.mapped.MappedNamespaceConvention;
//import org.codehaus.jettison.mapped.MappedXMLStreamWriter;
import org.primordion.xholon.io.xml.XmlWriter;

/**
 * A concrete class that knows how to write JSON through a Stax writer.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on May 3, 2010)
 */
public class JsonStrWriter extends XmlWriter {
	
	private static final String WRITERNAME = "JsonStrWriter";
	
	//private AbstractXMLStreamWriter streamWriter = null;
	protected StringBuilder sb = null;
	
	/**
	 * By default, Jettison prepends a @ to each attribute,
	 * I assume to distinguish attributes from elements.
	 */
	private boolean suppressAtAttributes = true;
	
	/**
	 * default constructor
	 */
	public JsonStrWriter() {}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#createNew(java.lang.Object)
	 */
	public void createNew(Object out) {
		/*Configuration config = new Configuration();
		config.setSupressAtAttributes(suppressAtAttributes); // don't prepend @ to each attribute
    MappedNamespaceConvention con = new MappedNamespaceConvention(config);
    streamWriter = new MappedXMLStreamWriter(con, (Writer)out);*/
    if (out instanceof StringBuilder) {
			sb = (StringBuilder)out;
		}
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeStartDocument()
	 */
	public void writeStartDocument()
	{
			/*try {
				streamWriter.writeStartDocument();
			} catch (XMLStreamException e) {
				e.printStackTrace();
			}*/
			sb.append("{");
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeEndDocument()
	 */
	public void writeEndDocument()
	{
		/*try {
			streamWriter.writeEndDocument();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}*/
		
		// JSON does not allow dangling commas
		// if existing last char == ',' then replace that last char
		int len = sb.length();
		if (sb.charAt(len-1) == ',') {
		  sb.setCharAt(len-1, '}');
		}
		else {
		  sb.append("}");
		}
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeStartElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void writeStartElement(String prefix, String localName, String namespaceURI)
	{
		/*try {
			streamWriter.writeStartElement(prefix, localName, namespaceURI);
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}*/
		writeStartElement(localName);
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeStartElement(java.lang.String)
	 */
	public void writeStartElement(String name)
	{
		/*try {
			streamWriter.writeStartElement(name);
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}*/
		sb.append("\"").append(name).append("\":{");
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
		/*try {
			streamWriter.writeEndElement();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}*/
		
		// JSON does not allow dangling commas
		// if existing last char == ',' then replace that last char
		int len = sb.length();
		if (sb.charAt(len-1) == ',') {
		  sb.setCharAt(len-1, '}');
		  sb.append(',');
		}
		else {
		  sb.append("},");
		}
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeNamespace(java.lang.String, java.lang.String)
	 */
	public void writeNamespace(String prefix, String namespaceURI)
	{
		/*try {
			streamWriter.writeNamespace(prefix, namespaceURI);
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}*/
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeAttribute(java.lang.String, java.lang.String)
	 */
	public void writeAttribute(String name, String value)
	{
		/*try {
			streamWriter.writeAttribute(name, value);
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}*/
		
		// ex: "connector": "ancestor::PetriNet/Places/C"
		if ("Val".equalsIgnoreCase(name) && !isShouldWriteVal()) {return;}
	  if ("AllPorts".equalsIgnoreCase(name) && !isShouldWriteAllPorts()) {return;}
		sb.append("\"")
		.append(name)
		.append("\":")
		.append(JsonUtils.escapeValue(value)) // Returns a quoted, escaped JSON String.
		.append(",");
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeText(java.lang.String)
	 */
	public void writeText(String text)
	{
		/*try {
			streamWriter.writeCharacters(text);
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}*/
		sb.append("\"")
		.append("Text")
		.append("\":")
		.append(JsonUtils.escapeValue(text)) // Returns a quoted, escaped JSON String.
		.append(",");
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeComment(java.lang.String)
	 */
	public void writeComment(String data)
	{
		/*try {
			streamWriter.writeComment(data);
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}*/
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
			streamWriter.flush();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}*/
	}

	public boolean isSuppressAtAttributes() {
		return suppressAtAttributes;
	}

	public void setSuppressAtAttributes(boolean suppressAtAttributes) {
		this.suppressAtAttributes = suppressAtAttributes;
	}
}

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

package org.primordion.xholon.io.yaml;

//import java.io.IOException;
//import java.io.Writer;

import org.primordion.xholon.io.xml.XmlWriter;

/**
 * A concrete class that knows how to write YAML through a Stax writer.
 * There is no underlying 3rd-party YAML StAX writer,
 * so this class will do the best it can on its own.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on May 4, 2010)
 */
public class YamlStrWriter extends XmlWriter {
	
	private static final String WRITERNAME = "YamlStrWriter";
	
	//private Writer streamWriter = null;
	private StringBuilder sb = null;
	
	/**
	 * The indent level.
	 */
	private int level = -1;
	
	/**
	 * The character(s) to use for a single level of indentation.
	 * This must be some sequence of one or more spaces.
	 */
	private String indentCharacters = "  ";
	
	/**
	 * default constructor
	 */
	public YamlStrWriter() {}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#createNew(java.lang.Object)
	 */
	public void createNew(Object out) {
		//streamWriter = (Writer)out;
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
			streamWriter.write("%YAML 1.1\n");
			streamWriter.write("---");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		sb.append("%YAML 1.1\n").append("---");
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeEndDocument()
	 */
	public void writeEndDocument()
	{
		/*try {
			streamWriter.write("\n...\n");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		sb.append("\n...\n");
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeStartElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void writeStartElement(String prefix, String localName, String namespaceURI)
	{
		//writeStartElement(localName);
		sb.append(localName);
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeStartElement(java.lang.String)
	 */
	public void writeStartElement(String name)
	{
		level++;
		StringBuilder indent = new StringBuilder(level);
		for (int i = 0; i < level; i++) {
			indent.append(indentCharacters);
		}
		/*try {
			streamWriter.write("\n" + indent + name + ": ");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		sb.append("\n" + indent.toString() + name + ": ");
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeEndElement(java.lang.String, java.lang.String)
	 */
	public void writeEndElement(String name, String namespaceURI)
	{
		//writeEndElement(name);
		sb.append(name);
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeEndElement(String name)
	 */
	public void writeEndElement(String name)
	{
		level--;
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeNamespace(java.lang.String, java.lang.String)
	 */
	public void writeNamespace(String prefix, String namespaceURI)
	{
		
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeAttribute(java.lang.String, java.lang.String)
	 */
	public void writeAttribute(String name, String value)
	{
		StringBuilder indent = new StringBuilder(level);
		for (int i = 0; i < level+1; i++) {
			indent.append(indentCharacters);
		}
		/*try {
			streamWriter.write("\n" + indent + name + ": " + value);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		sb.append("\n" + indent.toString() + name + ": " + value);
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeText(java.lang.String)
	 */
	public void writeText(String text)
	{
		/*try {
			streamWriter.write(text);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		sb.append(text);
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeComment(java.lang.String)
	 */
	public void writeComment(String data)
	{
		/*try {
			streamWriter.write("# " + data);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		sb.append("# " + data);
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
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

	public String getIndentCharacters() {
		return indentCharacters;
	}

	public void setIndentCharacters(String indentCharacters) {
		this.indentCharacters = indentCharacters;
	}
}

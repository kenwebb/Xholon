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

/**
 * The abstract interface for classes that write XML.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on December 17, 2009)
 */
public interface IXmlWriter {

	/**
	 * Create a new instance of an XML writer, able to write to out.
	 * @param out An output file, stream, string, or other type of writer.
	 * This must be an instance of java.io.Writer.
	 */
	public abstract void createNew(Object out);

	/**
	 * Write the XML Declaration.
	 */
	public abstract void writeStartDocument();

	/**
	 * Closes any start tags and writes corresponding end tags.
	 */
	public abstract void writeEndDocument();

	/**
	 * Writes a start tag to the output
	 * @param localName local name of the tag, may not be null
	 * @param prefix the prefix of the tag, may not be null
	 * @param namespaceURI the uri to bind the prefix to, may not be null
	 */
	public abstract void writeStartElement(String prefix, String localName,
			String namespaceURI);

	/**
	 * Writes a start tag to the output.
	 * @param name
	 */
	public abstract void writeStartElement(String name);

	/**
	 * Writes an end tag to the output.
	 * For use by XPP which requires a namespace if one was provided in the writeStartElement().
	 * @param name
	 * @param namespaceURI
	 */
	public abstract void writeEndElement(String name, String namespaceURI);

	/**
	 * Writes an end tag to the output.
	 * @param name 
	 */
	public abstract void writeEndElement(String name);

	/**
	 * Writes a namespace to the output stream.
	 * For use by StAX.
	 * @param prefix the prefix to bind this namespace to
	 * @param namespaceURI the uri to bind the prefix to
	 */
	public abstract void writeNamespace(String prefix, String namespaceURI);

	/**
	 * Writes an attribute to the output stream.
	 * @param name
	 * @param value
	 */
	public abstract void writeAttribute(String name, String value);

	/**
	 * Write text to the output.
	 * @param text
	 */
	public abstract void writeText(String text);

	/**
	 * Writes an xml comment.
	 * @param data
	 */
	public abstract void writeComment(String data);

	/**
	 * Gets the name of the XmlWriter.
	 * @return A name, for example "XmlStaxWriter" or "XmlXppWriter".
	 */
	public abstract String getWriterName();

	/**
	 * Write any cached data to the underlying output mechanism.
	 */
	public abstract void flush();
  
  /**
	 * Whether or not to write an attribute with the name "Val".
	 */
  public abstract boolean isShouldWriteVal();
	
	/**
	 * Whether or not to write an attribute with the name "Val".
	 */
  public abstract void setShouldWriteVal(boolean shouldWriteVal);
	
	/**
	 * Whether or not to write an attribute with the name "AllPorts".
	 */
	public abstract boolean isShouldWriteAllPorts();
	
	/**
	 * Whether or not to write an attribute with the name "AllPorts".
	 */
	public abstract void setShouldWriteAllPorts(boolean shouldWriteAllPorts);
	
}

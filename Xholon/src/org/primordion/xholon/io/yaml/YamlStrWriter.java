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
	
	private static final int LAST_ELEMENT_WAS_NONE  = 0;
	private static final int LAST_ELEMENT_WAS_START = 1;
	private static final int LAST_ELEMENT_WAS_END   = 2;
	
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
	 * Which method was called last (most recently):
	 *   1 writeStartElement(String name)
	 *   2 writeEndElement(String name)
	 */
	private int lastElementWasStartOrEnd = LAST_ELEMENT_WAS_NONE;
	
	/**
	 * default constructor
	 */
	public YamlStrWriter() {}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#createNew(java.lang.Object)
	 */
	public void createNew(Object out) {
		if (out instanceof StringBuilder) {
			sb = (StringBuilder)out;
		}
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeStartDocument()
	 */
	public void writeStartDocument()
	{
		sb.append("%YAML 1.1\n").append("---");
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeEndDocument()
	 */
	public void writeEndDocument()
	{
		sb.append("\n...\n");
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeStartElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void writeStartElement(String prefix, String localName, String namespaceURI)
	{
		sb.append(localName);
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeStartElement(java.lang.String)
	 */
	public void writeStartElement(String name)
	{
		StringBuilder indent = new StringBuilder();
		if ("Annotation".equals(name)) {
		  // treat Annotation like an attribute
		  for (int i = 0; i < level+1; i++) {
			  indent.append(indentCharacters);
		  }
		  sb.append("\n").append(indent.toString()).append(name).append(": ");
		  return;
		}
		level++;
		for (int i = 0; i < level; i++) {
			indent.append(indentCharacters);
		}
		if (lastElementWasStartOrEnd == LAST_ELEMENT_WAS_START) {
		  sb.append("\n" + indent.toString() + "_:");
		  level++;
		  indent.append(indentCharacters);
		}
		String dash = "- ";
		sb.append("\n").append(indent.toString()).append(dash).append(name).append(": ");
		lastElementWasStartOrEnd = LAST_ELEMENT_WAS_START;
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeEndElement(java.lang.String, java.lang.String)
	 */
	public void writeEndElement(String name, String namespaceURI)
	{
		sb.append(name); // ???
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeEndElement(String name)
	 */
	public void writeEndElement(String name)
	{
		if ("Annotation".equals(name)) {
		  return;
		}
		level--;
		if (lastElementWasStartOrEnd == LAST_ELEMENT_WAS_END) {
		  level--;
		}
		lastElementWasStartOrEnd = LAST_ELEMENT_WAS_END;
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
	  if ("Val".equalsIgnoreCase(name) && !isShouldWriteVal()) {return;}
		StringBuilder indent = new StringBuilder();
		for (int i = 0; i < level+1; i++) {
			indent.append(indentCharacters);
		}
	  if ("AllPorts".equalsIgnoreCase(name)) {
	    if (!isShouldWriteAllPorts()) {return;}
	    value = this.handleLinksOrAllPorts(value, indent);
	    if (value == null) {
	      return;
	    }
	  }
	  if ("Links".equalsIgnoreCase(name)) {
	    if (!isShouldWriteLinks()) {return;}
	    value = this.handleLinksOrAllPorts(value, indent);
	    if (value == null) {
	      return;
	    }
	  }
		sb
		.append("\n")
		.append(indent.toString())
		.append(name)
		.append(": ")
		.append(value);
	}
	
	/**
	 * Handle "Links" or "AllPorts".
	 */
	protected String handleLinksOrAllPorts(String value, StringBuilder indent) {
		  value = value.trim();
	    if (value.length() > 2) {
	      // strip off start and end brackets []
	      String[] valueArr = value.substring(1, value.length()-1).split(",");
	      value = "";
	      for (int i = 0; i < valueArr.length; i++) {
	        String valueStr = valueArr[i].trim();
	        int pos = valueStr.indexOf(":"); // find first ":"
	        if (pos != -1) {
	          valueStr = valueStr.substring(0, pos) + ": " + valueStr.substring(pos+1);
	        }
	        value += "\n"
	          + indent.toString()
	          + indentCharacters
	          + valueStr;
	      }
	      return value;
	    }
	    else {
	      return null;
	    }
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeText(java.lang.String)
	 */
	public void writeText(String text)
	{
		sb.append(text);
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeComment(java.lang.String)
	 */
	public void writeComment(String data)
	{
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
	}

	public String getIndentCharacters() {
		return indentCharacters;
	}

	public void setIndentCharacters(String indentCharacters) {
		this.indentCharacters = indentCharacters;
	}
}

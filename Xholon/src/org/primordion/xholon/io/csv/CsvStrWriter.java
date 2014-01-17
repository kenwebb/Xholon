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

package org.primordion.xholon.io.csv;

//import java.io.IOException;
//import java.io.Writer;

import org.primordion.xholon.io.xml.XmlWriter;

/**
 * A concrete class that knows how to write CSV through a Stax writer.
 * There is no underlying 3rd-party CSV StAX writer,
 * so this class will do the best it can on its own.
 * TODO optionally have a separate row for each nested level,
 * to show the complete composite stucture.
 * One data row at the bottom.
 * One attribute row just above the data row.
 * One or more element rows at the top.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on November 10, 2012)
 */
public class CsvStrWriter extends XmlWriter {
	
	private static final String WRITERNAME = "CsvStrWriter";
	
	//private Writer streamWriter = null;
	private StringBuilder sb = null;
	
	private StringBuilder sbHeaderElem = null;
	private StringBuilder sbHeaderAttr = null;
	private StringBuilder sbData = null;
	
	/**
	 * The character that separates fields.
	 * Typically this is a comma or tab.
	 */
	private char fieldSeparator = ',';
	
	/**
	 * The character that delimits text in a field.
	 * The textDelimiter is only used when instances of the fieldSeparator
	 * exist inside the text.
	 * ex: abc,def,"mn,op",rst,uvw
	 */
	private char textDelimiter = '"';
	
	/**
	 * Optionally meta data that will be written as the first column.
	 */
	private String metaData_data = "XH";
	
	private String metaData_elem = "ELEM";
	private String metaData_attr = "ATTR";
	
	/**
	 * Whether or not to write a header row with elements.
	 */
	private boolean shouldWriteHeaderElem = true;
	
	/**
	 * Whether or not to write a header row with attributes.
	 */
	private boolean shouldWriteHeaderAttr = true;
	
	/**
	 * The name of the current element.
	 */
	private String currentElemName = "";
	
	/**
	 * An element may have more than one attribute.
	 */
	private boolean hasNewElemName = false;
	
	/**
	 * Whether or not to write text (= XML text).
	 * TODO confirm that this is working OK
	 */
	private boolean shouldWriteText = false;
	
	/**
	 * default constructor
	 */
	public CsvStrWriter() {}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#createNew(java.lang.Object)
	 */
	public void createNew(Object out) {
		//streamWriter = (Writer)out;
		if (out instanceof StringBuilder) {
			sb = (StringBuilder)out;
		}
		if (shouldWriteHeaderElem) {
			sbHeaderElem = new StringBuilder();
		}
		if (shouldWriteHeaderAttr) {
			sbHeaderAttr = new StringBuilder();
		}
		sbData = new StringBuilder();
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeStartDocument()
	 */
	public void writeStartDocument()
	{
		if (shouldWriteHeaderElem) {
			sbHeaderElem.append(metaData_elem);
		}
		if (shouldWriteHeaderAttr) {
			sbHeaderAttr.append(metaData_attr);
		}
		sbData.append(metaData_data);
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeEndDocument()
	 */
	public void writeEndDocument()
	{
		if (shouldWriteHeaderElem) {
			sbHeaderElem.append("\n");
		}
		if (shouldWriteHeaderAttr) {
			sbHeaderAttr.append("\n");
		}
		sbData.append("\n");
		//try {
			if (shouldWriteHeaderElem) {
				//streamWriter.write(sbHeaderElem.toString());
				sb.append(sbHeaderElem.toString());
			}
			if (shouldWriteHeaderAttr) {
				//streamWriter.write(sbHeaderAttr.toString());
				sb.append(sbHeaderAttr.toString());
			}
			//streamWriter.write(sbData.toString());
			sb.append(sbData.toString());
		//} catch (IOException e) {
		//	e.printStackTrace();
		//}
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeStartElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void writeStartElement(String prefix, String localName, String namespaceURI)
	{
		writeStartElement(localName);
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeStartElement(java.lang.String)
	 */
	public void writeStartElement(String name)
	{
		currentElemName = name;
		hasNewElemName = true;
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
		// do nothing
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
		appendHeaderElem();
		if (shouldWriteHeaderAttr) {
			if (name.indexOf(fieldSeparator) != -1) {
				sbHeaderAttr.append(fieldSeparator).append(textDelimiter).append(name).append(textDelimiter);
			}
			else {
				sbHeaderAttr.append(fieldSeparator).append(name);
			}
		}
		if (value.indexOf(fieldSeparator) != -1) {
			sbData.append(fieldSeparator).append(textDelimiter).append(value).append(textDelimiter);
		}
		else {
			sbData.append(fieldSeparator).append(value);
		}
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeText(java.lang.String)
	 */
	public void writeText(String text)
	{
		if (shouldWriteText && (text.trim().length() > 0)) {
			appendHeaderElem();
			if (shouldWriteHeaderAttr) {
				sbHeaderAttr.append(fieldSeparator).append("text");
			}
			if (text.indexOf(fieldSeparator) != -1) {
				sbData.append(fieldSeparator).append(textDelimiter).append(text).append(textDelimiter);
			}
			else {
				sbData.append(fieldSeparator).append(text);
			}
		}
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeComment(java.lang.String)
	 */
	public void writeComment(String data)
	{
		
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
	  /* GWT
		try {
			streamWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
	
	/**
	 * Append to the element header.
	 */
	protected void appendHeaderElem() {
		if (shouldWriteHeaderElem) {
			if (hasNewElemName) {
				if (currentElemName.indexOf(fieldSeparator) != -1) {
					sbHeaderElem.append(fieldSeparator).append(textDelimiter)
					.append(currentElemName).append(textDelimiter);
				}
				else {
					sbHeaderElem.append(fieldSeparator).append(currentElemName);
				}
				hasNewElemName = false;
			}
			else {
				sbHeaderElem.append(fieldSeparator);
			}
		}
	}

	public char getFieldSeparator() {
		return fieldSeparator;
	}

	public void setFieldSeparator(char fieldSeparator) {
		this.fieldSeparator = fieldSeparator;
	}

	public char getTextDelimiter() {
		return textDelimiter;
	}

	public void setTextDelimiter(char textDelimiter) {
		this.textDelimiter = textDelimiter;
	}

	public boolean isShouldWriteHeaderRow() {
		return shouldWriteHeaderAttr;
	}

	public void setShouldWriteHeaderRow(boolean shouldWriteHeaderRow) {
		this.shouldWriteHeaderAttr = shouldWriteHeaderRow;
	}

	public String getMetaData_elem() {
		return metaData_elem;
	}

	public void setMetaData_elem(String metaDataElem) {
		metaData_elem = metaDataElem;
	}

	public String getMetaData_attr() {
		return metaData_attr;
	}

	public void setMetaData_attr(String metaDataAttr) {
		metaData_attr = metaDataAttr;
	}

	public boolean isShouldWriteHeaderElem() {
		return shouldWriteHeaderElem;
	}

	public void setShouldWriteHeaderElem(boolean shouldWriteHeaderElem) {
		this.shouldWriteHeaderElem = shouldWriteHeaderElem;
	}

	public boolean isShouldWriteHeaderAttr() {
		return shouldWriteHeaderAttr;
	}

	public void setShouldWriteHeaderAttr(boolean shouldWriteHeaderAttr) {
		this.shouldWriteHeaderAttr = shouldWriteHeaderAttr;
	}

	public boolean isShouldWriteText() {
		return shouldWriteText;
	}

	public void setShouldWriteText(boolean shouldWriteText) {
		this.shouldWriteText = shouldWriteText;
	}

	public String getMetaData_data() {
		return metaData_data;
	}

	public void setMetaData_data(String metaDataData) {
		metaData_data = metaDataData;
	}

}

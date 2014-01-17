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

package org.primordion.xholon.io.htmlform;

//import java.io.IOException;
//import java.io.Writer;

import org.primordion.xholon.io.xml.XmlWriter;

/**
 * A concrete class that knows how to write an HTML Form through a Stax writer.
 * There is no underlying 3rd-party HTML StAX writer,
 * so this class will do the best it can on its own.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on November 10, 2012)
 */
public class HtmlFormStrWriter extends XmlWriter {
	
	private static final String WRITERNAME = "HtmlFormStaxWriter";
	
	//private Writer streamWriter = null;
	protected StringBuilder sb = null;
	
	/**
	 * An ID that can be incremented for each label+input, or other control.
	 */
	private int controlId = 0;
	
	/**
	 * default constructor
	 */
	public HtmlFormStrWriter() {}
	
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
		//try {
			//streamWriter.write("<div class='htmlform'>\n");
			sb.append("data:text/html;charset=utf-8,")
			.append("<div class='htmlform'>\n");
			StringBuilder sbSd = new StringBuilder()
			.append("<style type='text/css'>\n")
			.append("input {")
			.append("border:1px solid %231F4F82;padding:1px;margin:1px;background-color:%23F0F8FF;")
			.append("}\n")
			.append("fieldset {")
			.append("border: none;background-color:%23F8F0FF;")
			.append("}\n")
			.append("</style>\n")
			;
			//streamWriter.write(sbSd.toString());
			sb.append(sbSd.toString());
		//} catch (IOException e) {
		//	e.printStackTrace();
		//}
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeEndDocument()
	 */
	public void writeEndDocument()
	{
		/*try {
			streamWriter.write("</div>\n");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		sb.append("</div>\n");
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
		/*try {
			streamWriter.write("<fieldset>\n");
			streamWriter.write("  <legend>" + name + "</legend>\n");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		sb.append("<fieldset>\n");
	  sb.append("  <legend>" + name + "</legend>\n");
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
			streamWriter.write("</fieldset>\n");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		sb.append("</fieldset>\n");
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
		StringBuilder sbA = new StringBuilder()
		// label
		.append("<label for='attr")
		.append(controlId)
		.append("'>")
		.append(name)
		.append("</label>\n")
		// input
		.append("<input type='text' name='")
		.append(name)
		.append("' value='")
		.append(value)
		.append("' id='attr")
		.append(controlId)
		.append("'/>\n");
		controlId++;
		/*try {
			streamWriter.write(sbA.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		sb.append(sbA.toString());
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.XmlWriter#writeText(java.lang.String)
	 */
	public void writeText(String text)
	{
		/*try {
			streamWriter.write("<p>" + text + "</p>");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		sb.append("<p>" + text + "</p>");
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
		/*try {
			streamWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

}

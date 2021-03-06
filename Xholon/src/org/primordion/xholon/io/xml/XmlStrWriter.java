/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2013 Ken Webb
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
 * A concrete class that knows how to write XML to a StringBuilder using the Stax API.
 * There is no underlying 3rd-party StAX writer,
 * so this class will do the best it can on its own.
 * This class is especially intended for use in a GWT environment,
 * with no support for StAX or java.io.Writer.
 * Namespaces are not supported.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on July 28, 2013)
 */
@SuppressWarnings("serial")
public class XmlStrWriter extends XmlWriter {
	
	private static final String WRITERNAME = "XmlStrWriter";
	
	protected StringBuilder sb = null;
	
	/**
	 * Whether or not to write a ">" Greater-Than (Gt) symbol before writing
	 * a startElement, endElement, or text.
	 */
	private boolean shouldWriteGt = false;

	@Override
	public void createNew(Object out) {
		if (out instanceof StringBuilder) {
			sb = (StringBuilder)out;
		}
	}

	@Override
	public void writeStartDocument() {
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	}

	@Override
	public void writeEndDocument() {
		// no action required
	}

	@Override
	public void writeStartElement(String prefix, String localName, String namespaceURI) {
		writeStartElement(localName);
		writeAttribute("xmlns:" + prefix, namespaceURI);
	}

	@Override
	public void writeStartElement(String name) {
	  if (shouldWriteGt) {
	    sb.append(">");
	    shouldWriteGt = false;
	  }
		sb.append("<").append(name);
		shouldWriteGt = true;
	}

	@Override
	public void writeEndElement(String name, String namespaceURI) {
	  writeEndElement(name);
	}

	@Override
	public void writeEndElement(String name) {
	  if (shouldWriteGt) {
	    sb.append(">");
	    shouldWriteGt = false;
	  }
		sb.append("</").append(name).append(">");
	}

	@Override
	public void writeNamespace(String prefix, String namespaceURI) {
		// no action required (for now)
	}

	@Override
	public void writeAttribute(String name, String value) {
	  if ("Val".equalsIgnoreCase(name) && !isShouldWriteVal()) {return;}
	  //if ("AllPorts".equalsIgnoreCase(name) && !isShouldWriteAllPorts()) {return;}
	  //if ("Links".equalsIgnoreCase(name) && !isShouldWriteLinks()) {return;}
	  if ("AllPorts".equalsIgnoreCase(name)) {
	    if (!isShouldWriteAllPorts()) {return;}
	    value = this.handleLinksOrAllPorts(value);
	    if (value != null) {
	      this.writeText(value);
	    }
      return;
	  }
	  if ("Links".equalsIgnoreCase(name)) {
	    if (!isShouldWriteLinks()) {return;}
	    value = this.handleLinksOrAllPorts(value);
	    if (value != null) {
	      this.writeText(value);
	    }
      return;
	  }
	  sb.append(" ").append(name).append("=\"").append(value).append("\"");
	}

	/**
	 * Handle "Links" or "AllPorts".
	 */
	protected String handleLinksOrAllPorts(String value) {
		  value = value.trim();
	    if (value.length() > 2) {
	      // strip off start and end brackets []
	      String[] valueArr = value.substring(1, value.length()-1).split(",");
	      value = "";
	      for (int i = 0; i < valueArr.length; i++) {
	        String valueStr = valueArr[i].trim();
	        int pos = valueStr.indexOf(":"); // find first ":"
	        if (pos != -1) {
	          String nameStr = valueStr.substring(0, pos);
	          String remainingStr = valueStr.substring(pos+1);
	          // if this is a "Links", then the XPath expression almost certainly starts with "ancestor::", or with "./" if the node is the root node
	          String xpathStr = remainingStr; // the default
	          int xpathPos = remainingStr.indexOf("ancestor::");
	          if (xpathPos != -1) {
	            xpathStr = remainingStr.substring(xpathPos);
	          }
	          valueStr = "<port name=\"" + nameStr + "\" connector=\"" + xpathStr + "\"></port>";
	        }
	        value += valueStr;
	      }
	      return value;
	    }
	    else {
	      return null;
	    }
	}
	
	@Override
	public void writeText(String text) {
	  if (shouldWriteGt) {
	    sb.append(">");
	    shouldWriteGt = false;
	  }
		sb.append(text);
	}

	@Override
	public void writeComment(String data) {
	  if (shouldWriteGt) {
	    sb.append(">");
	    shouldWriteGt = false;
	  }
		sb.append("<!-- ").append(data).append(" -->");
	}

	@Override
	public String getWriterName() {
		return WRITERNAME;
	}

	@Override
	public void flush() {
		// no action required
	}

}

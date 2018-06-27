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

import org.primordion.xholon.base.IXholon;

/**
 * This is the interface for any class that transforms Xholon nodes into XML,
 * or some equivalent format.
 * It specifies methods to write to a String or to a File.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on August 1, 2009)
 */
public interface IXholon2Xml {

	/** <p>Don't transform a Xholon/Java attribute.</p> */
	public static final int XHATTR_TO_NULL = 0;
	
	/** <p>Transform a Xholon/Java attribute to an XML attribute.</p>
	 *  <p>ex: int state = 1 --> &lt;Node state="1"&gt;</p>
	 */
	public static final int XHATTR_TO_XMLATTR = 1;
	
	/** <p>Transform a Xholon/Java attribute to an XML element.
	 *  This allows the name, value and type to be saved to XML.</p>
	 *  <p>ex: int state = 1 --> &lt;Attribute_int roleName="State"&gt;1&lt;/Attribute_int&gt;</p>
	 */
	public static final int XHATTR_TO_XMLELEMENT = 2;

	/**
	 * Get how Xholon/Java attributes should be transformed.
	 * @return
	 */
	public abstract int getXhAttrStyle();

	/**
	 * Set how Xholon/Java attributes should be transformed.
	 * @param xhAttrStyle
	 */
	public abstract void setXhAttrStyle(int xhAttrStyle);
	
	/**
	 * Get whether to return only the attributes of the immediate concrete class (false),
	 * or attributes from all concrete classes in the inheritance hierarchy that extend
	 * from Xholon or XholonWithPorts (true).
	 * @return true or false
	 */
	public abstract boolean getXhAttrReturnAll();
	
	/**
	 * Set whether to return only the attributes of the immediate concrete class (false),
	 * or attributes from all concrete classes in the inheritance hierarchy that extend
	 * from Xholon or XholonWithPorts (true).
	 * @param returnAll true or false
	 */
	public abstract void setXhAttrReturnAll(boolean returnAll);

	/**
	 * Get whether the XML Declaration should be written at the start of a new XML document.
	 * @return true or false
	 */
	public abstract boolean isWriteStartDocument();

	/**
	 * Set whether the XML Declaration should be written at the start of a new XML document.
	 * @param writeStartDocument true or false
	 */
	public abstract void setWriteStartDocument(boolean writeStartDocument);

	/**
	 * Get whether the Xholon ID should be written out as an XML attribute for each node.
	 * @return true or false
	 */
	public abstract boolean isWriteXholonId();

	/**
	 * Set whether the Xholon ID should be written out as an XML attribute for each node.
	 * @param writeXholonId true or false
	 */
	public abstract void setWriteXholonId(boolean writeXholonId);
	
	/**
	 * Get whether the Xholon roleName should be written out as an XML attribute for each node.
	 * @return true or false
	 */
	public abstract boolean isWriteXholonRoleName();

	/**
	 * Set whether the Xholon roleName should be written out as an XML attribute for each node.
	 * @param writeXholonRoleName true or false
	 */
	public abstract void setWriteXholonRoleName(boolean writeXholonRoleName);
	
	/**
	 * Get template to use when writing out node names.
	 * @return ex: "r:C^^^"
	 */
	public abstract String getNameTemplate();

	/**
	 * Set template to use when writing out node names.
	 * @param nameTemplate ex: "r:C^^^"
	 */
	public abstract void setNameTemplate(String nameTemplate);
	
	/**
	 * Get whether attributes should be written out for each node.
	 * @return true or false
	 */
	public abstract boolean isWriteAttributes();
	
	/**
	 * Set whether attributes should be written out for each node.
	 * @param writeAttributes true or false
	 */
	public abstract void setWriteAttributes(boolean writeAttributes);
	
	/**
	 * Get whether standard attributes should be written out for each node.
	 * @return true or false
	 */
	public abstract boolean isWriteStandardAttributes();
	
	/**
	 * Set whether standard attributes should be written out for each node.
	 * @param writeStandardAttributes true or false
	 */
	public abstract void setWriteStandardAttributes(boolean writeStandardAttributes);
	
	/**
	 * Write something special for the current node.
	 * It could be one or more attributes, some text, a child node, a child subtree, etc.
	 * Typically, it should be written to the xmlWriter.
	 * @param node The current IXholon node.
	 */
	public abstract void writeSpecial(IXholon node);
	
	/**
	 * Get whether ports should be written out for each node.
	 * @return true or false
	 */
	public abstract boolean isWritePorts();
	
	/**
	 * Set whether ports should be written out for each node.
	 * @param writePorts true or false
	 */
	public abstract void setWritePorts(boolean writePorts);
	
	/**
	 * Get whether annotation should be written when available for the node.
	 * @return true or false
	 */
	public abstract boolean isWriteAnnotations();
	
	/**
	 * Set whether annotation should be written when available for the node.
	 * @param writeAnnotations true or false
	 */
	public abstract void setWriteAnnotations(boolean writeAnnotations);
	
	/**
	 * Try to find an annotation for the node.
	 * @param xhNode A Xholon node that might have an annotation.
	 * @return The text of an annotation, or null.
	 */
	public abstract String findAnnotation(IXholon xhNode);
	
	/**
	 * Transform a Xholon subtree into an XML String.
	 * @param xhNode The Xholon node to transform.
	 * @return An XML String.
	 */
	public abstract String xholon2XmlString(IXholon xhNode);

	/**
	 * Transform a Xholon subtree into an XML File.
	 * @param xhNode The Xholon node and/or sub-tree to transform.
	 * @param fileName The path and name of the XML file.
	 */
	public abstract void xholon2XmlFile(IXholon xhNode, String fileName);
	
	/**
	 * Get the root of the Xholon subtree being transformed into XML.
	 * @return The root of the Xholon subtree.
	 */
	public abstract IXholon get2XmlRoot();

	/**
	 * Get whether JavaScript attributes should be written out for each node.
	 * @return true or false
	 */
	public abstract boolean isWriteJavaScriptAttributes();
	
	/**
	 * Set whether JavaScript attributes should be written out for each node.
	 * @param writeAttributes true or false
	 */
	public abstract void setWriteJavaScriptAttributes(boolean writeJavaScriptAttributes);
	
	public abstract boolean isShouldWriteVal();
	public abstract void setShouldWriteVal(boolean shouldWriteVal);
	public abstract boolean isShouldWriteAllPorts();
	public abstract void setShouldWriteAllPorts(boolean shouldWriteAllPorts);

}

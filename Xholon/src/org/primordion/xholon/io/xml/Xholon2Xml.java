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

//import java.io.StringWriter;
//import java.io.Writer;

import org.primordion.xholon.base.Annotation;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.XholonDirectoryService;
//import org.primordion.xholon.util.MiscIo;

/**
 * Xholon to XML.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on August 1, 2009)
 */
public class Xholon2Xml extends Xholon implements IXholon2Xml {
	
	/** How to transform Xholon/Java attributes. */
	private int xhAttrStyle = XHATTR_TO_NULL;
	
	/**
	 * Whether to return only the attributes of the immediate concrete class (false),
	 * or attributes from all concrete classes in the inheritance hierarchy that extend
	 * from Xholon or XholonWithPorts (true).
	 */
	private boolean xhAttrReturnAll = true;
	
	/** Should the XML Declaration be written at the start of a new XML document. */
	private boolean writeStartDocument = true;
	
	/** Should the Xholon ID be written out as an XML attribute for each node. */
	private boolean writeXholonId = false;
	
	/** Should the Xholon roleName be written out as an XML attribute for each node. */
	private boolean writeXholonRoleName = true;
	
	/** Template to use when writing out node names. */
	private String nameTemplate = null;
	
	/** Should ports be written out as XML attributes for each node that has ports. */
	private boolean writePorts = false;
	
	/** Should annotations be written when available for nodes. */
	private boolean writeAnnotations = true;
	
	/** Annotation service. */
	private IXholon annService = null;
	
	/** The root of the Xholon subtree being transformed into XML */
	private IXholon xmlRoot = null;
	
	/** Should standard attributes be written out for each node. */
	private boolean writeStandardAttributes = true;
	
	/** Should attributes be written out for each node. */
	private boolean writeAttributes = true;
	
	/**
	 * Whether or not to write an attribute with the name "Val".
	 */
	private boolean shouldWriteVal = true;
	
	/**
	 * Whether or not to write an attribute with the name "AllPorts".
	 */
	private boolean shouldWriteAllPorts = true;
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#getXhAttrStyle()
	 */
	public int getXhAttrStyle() {
		return xhAttrStyle;
	}
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#setXhAttrStyle(int)
	 */
	public void setXhAttrStyle(int xhAttrStyle) {
		this.xhAttrStyle = xhAttrStyle;
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#getXhAttrReturnAll()
	 */
	public boolean getXhAttrReturnAll() {
		return xhAttrReturnAll;
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#setXhAttrReturnAll(boolean)
	 */
	public void setXhAttrReturnAll(boolean xhAttrReturnAll) {
		this.xhAttrReturnAll = xhAttrReturnAll;
	}
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#isWriteStartDocument()
	 */
	public boolean isWriteStartDocument() {
		return writeStartDocument;
	}
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#setWriteStartDocument(boolean)
	 */
	public void setWriteStartDocument(boolean writeStartDocument) {
		this.writeStartDocument = writeStartDocument;
	}
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#isWriteXholonId()
	 */
	public boolean isWriteXholonId() {
		return writeXholonId;
	}
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#setWriteXholonId(boolean)
	 */
	public void setWriteXholonId(boolean writeXholonId) {
		this.writeXholonId = writeXholonId;
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#isWriteXholonRoleName()
	 */
	public boolean isWriteXholonRoleName() {
		return writeXholonRoleName;
	}

	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#setWriteXholonRoleName(boolean)
	 */
	public void setWriteXholonRoleName(boolean writeXholonRoleName) {
		this.writeXholonRoleName = writeXholonRoleName;
	}
	
	public String getNameTemplate() {
		return nameTemplate;
	}

	public void setNameTemplate(String nameTemplate) {
		this.nameTemplate = nameTemplate;
	}

	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#isWritePorts()
	 */
	public boolean isWritePorts() {
		return writePorts;
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#setWritePorts(boolean)
	 */
	public void setWritePorts(boolean writePorts) {
		this.writePorts = writePorts;
	}

	/* 
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#xholon2XmlString(org.primordion.xholon.base.IXholon)
	 */
	public String xholon2XmlString(IXholon xhNode)
	{
		xmlRoot = xhNode;
		//Writer out = new StringWriter();
		StringBuilder out = new StringBuilder(); // for use with XmlStrWriter (GWT)
		IXmlWriter xmlWriter = XmlWriterFactory.getXmlWriter(out);
		xmlWriter.setShouldWriteVal(isShouldWriteVal());
		xmlWriter.setShouldWriteAllPorts(isShouldWriteAllPorts());
		if (isWriteStartDocument()) {
			xmlWriter.writeStartDocument();
		}
		xhNode.toXml(this, xmlWriter);
		xmlWriter.writeEndDocument();
		xmlWriter.flush();
		return out.toString();
	}
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#xholon2XmlFile(org.primordion.xholon.base.IXholon, java.lang.String)
	 */
	public void xholon2XmlFile(IXholon xhNode, String fileName)
	{
	  /* GWT
		xmlRoot = xhNode;
		Writer out = MiscIo.openOutputFile(fileName);
		if (out == null) {return;}
		IXmlWriter xmlWriter = XmlWriterFactory.getXmlWriter(out);
		if (isWriteStartDocument()) {
			xmlWriter.writeStartDocument();
		}
		xmlWriter.writeComment(" This file was written by " + xmlWriter.getWriterName() + ". ");
		xhNode.toXml(this, xmlWriter);
		xmlWriter.writeEndDocument();
		xmlWriter.flush();
		MiscIo.closeOutputFile(out);
		*/
	}

	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#findAnnotation(org.primordion.xholon.base.IXholon)
	 */
	public String findAnnotation(IXholon xhNode) {
		if (annService == null) {
			annService = xhNode.getService(IXholonService.XHSRV_XHOLON_DIRECTORY);
		}
		if (annService != null) {
			IXholon ann = (IXholon)((XholonDirectoryService)annService)
				.get(Annotation.makeUniqueKey(xhNode));
			if (ann != null) {
				return ann.getVal_String();
			}
		}
		return null;
	}

	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#isWriteAnnotations()
	 */
	public boolean isWriteAnnotations() {
		return writeAnnotations;
	}

	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#setWriteAnnotations(boolean)
	 */
	public void setWriteAnnotations(boolean writeAnnotations) {
		this.writeAnnotations = writeAnnotations;
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#get2XmlRoot()
	 */
	public IXholon get2XmlRoot() {
		return xmlRoot;
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#isWriteAttributes()
	 */
	public boolean isWriteAttributes() {
		return writeAttributes;
	}

	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#setWriteAttributes(boolean)
	 */
	public void setWriteAttributes(boolean writeAttributes) {
		this.writeAttributes = writeAttributes;
	}

	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#isWriteStandardAttributes()
	 */
	public boolean isWriteStandardAttributes() {
		return writeStandardAttributes;
	}

	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#setWriteStandardAttributes(boolean)
	 */
	public void setWriteStandardAttributes(boolean writeStandardAttributes) {
		this.writeStandardAttributes = writeStandardAttributes;
	}

	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#writeSpecial(org.primordion.xholon.base.IXholon)
	 */
	public void writeSpecial(IXholon node) {
		// do nothing
	}
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXmlWriter#isShouldWriteVal()
	 */
	public boolean isShouldWriteVal() {
	  return shouldWriteVal;
	}
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXmlWriter#setShouldWriteVal()
	 */
	public void setShouldWriteVal(boolean shouldWriteVal) {
	  this.shouldWriteVal = shouldWriteVal;
	}
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXmlWriter#isShouldWriteAllPorts()
	 */
	public boolean isShouldWriteAllPorts() {
	  return shouldWriteAllPorts;
	}
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXmlWriter#shouldWriteAllPorts()
	 */
	public void setShouldWriteAllPorts(boolean shouldWriteAllPorts) {
	  this.shouldWriteAllPorts = shouldWriteAllPorts;
	}
	
}

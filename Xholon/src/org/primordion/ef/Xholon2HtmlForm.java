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

package org.primordion.ef;

//import java.io.File;
//import java.io.IOException;
//import java.io.StringWriter;
//import java.io.Writer;
//import java.security.AccessControlException;
import java.util.Date;

import org.primordion.xholon.base.Annotation;
import org.primordion.xholon.base.IXholon;
//import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.io.htmlform.HtmlFormWriterFactory;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.XholonDirectoryService;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;
//import org.primordion.xholon.util.MiscIo;

/**
 * Export a Xholon model in HTML Form format.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on November 10, 2012)
 */
@SuppressWarnings("serial")
public class Xholon2HtmlForm extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat, IXholon2Xml {
	
	private String outFileName;
	private String outPath = "./ef/htmlform/";
	private String modelName;
	private IXholon root;
	//private Writer out;
	private StringBuilder sb;
	
	/** Current date and time. */
	private Date timeNow;
	private long timeStamp;
	
	/** Whether or not to show state machine nodes. */
	private boolean shouldShowStateMachineEntities = false;
	
	/** Template to use when writing out node names. */
	//protected String nameTemplate = "r:C^^^";
	protected String nameTemplate = "^^C^^^"; // don't include role name
	
	/** Whether or not labels/names should be quoted. */
	private boolean shouldQuoteLabels = false;
	
	/** Whether or not to format the output by inserting new lines. */
	//private boolean shouldInsertNewlines = true;
	
	/** How to transform Xholon/Java attributes. */
	private int xhAttrStyle = XHATTR_TO_XMLATTR; //XHATTR_TO_NULL;
	
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
	
	/** Should ports be written out as XML attributes for each node that has ports. */
	private boolean writePorts = false;
	
	private boolean shouldPrettyPrint = false;
	
	/** Should annotations be written when available for nodes. */
	private boolean writeAnnotations = false;
	
	/** Annotation service. */
	private IXholon annService = null;
	
	/** Should standard attributes be written out for each node. */
	private boolean writeStandardAttributes = true;
	
	/** Should attributes be written out for each node. */
	private boolean writeAttributes = true;

	/**
	 * Whether or not to write attributes that were probably defined using JavaScript.
	 */
	private boolean writeJavaScriptAttributes = true;
	
	/**
	 * Constructor.
	 */
	public Xholon2HtmlForm() {}
	
	@Override
	public String getVal_String() {
	  return sb.toString();
	}
	
	/*
	 * @see org.primordion.xholon.io.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
	 */
	public boolean initialize(String mmFileName, String modelName, IXholon root) {
		timeNow = new Date();
		timeStamp = timeNow.getTime();
		if (mmFileName == null) {
			this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".html";
		}
		else {
			this.outFileName = mmFileName;
		}
		this.modelName = modelName;
		this.root = root;
		return true;
	}
	
	/*
	 * @see org.primordion.xholon.io.IXholon2ExternalFormat#writeAll()
	 */
	public void writeAll() {
		/*boolean shouldClose = true;
		if (root.getApp().isUseAppOut()) {
			out = root.getApp().getOut();
			shouldClose = false;
		}
		else {
			try {
				// create any missing output directories
				File dirOut = new File(outPath);
				dirOut.mkdirs(); // will create a new directory only if there is no existing one
				out = MiscIo.openOutputFile(outFileName);
			} catch(AccessControlException e) {
				out = root.getApp().getOut();
				shouldClose = false;
			}
		}*/
		sb = new StringBuilder();
		if (shouldPrettyPrint) {
			xholon2Writer(root, sb);
			//String htmlStr = prettyPrint(sb.toString());
			// do the actual pretty print; use regular expression(s) to replace {},
			//try {
			//	out.write(htmlStr);
			//	out.flush();
			//} catch (IOException e) {
			//	Xholon.getLogger().error("", e);
			//}
		}
		else {
			xholon2Writer(root, sb);
		}
		writeToTarget(sb.toString(), outFileName, outPath, root);
		//if (shouldClose) {
		//	MiscIo.closeOutputFile(out);
		//}
	}
	
	/**
	 * Transform a Xholon subtree using a specified Writer.
	 * @param out An instance of Writer.
	 * @return The same instance of Writer.
	 */
	public Object xholon2Writer(IXholon xhNode, Object out) {
		IXmlWriter xmlWriter = HtmlFormWriterFactory.getXmlWriter(out);
		if (xmlWriter != null) {
			if (isWriteStartDocument()) {
				xmlWriter.writeStartDocument();
			}
			xmlWriter.writeComment(" This file was written by " + xmlWriter.getWriterName() + ". ");
			xhNode.toXml(this, xmlWriter);
			xmlWriter.writeEndDocument();
			xmlWriter.flush();
		}
		return out;
	}
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#xholon2XmlString(org.primordion.xholon.base.IXholon)
	 */
	public String xholon2XmlString(IXholon xhNode)
	{
		/*out = new StringWriter();
		IXmlWriter xmlWriter = HtmlFormWriterFactory.getXmlWriter(out);
		if (xmlWriter != null) {
			if (isWriteStartDocument()) {
				xmlWriter.writeStartDocument();
			}
			xhNode.toXml(this, xmlWriter);
			xmlWriter.writeEndDocument();
			xmlWriter.flush();
		}
		return out.toString();*/
		return null;
	}
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#xholon2XmlFile(org.primordion.xholon.base.IXholon, java.lang.String)
	 */
	public void xholon2XmlFile(IXholon xhNode, String fileName)
	{
	  /*
		out = MiscIo.openOutputFile(fileName);
		if (out == null) {return;}
		IXmlWriter xmlWriter = HtmlFormWriterFactory.getXmlWriter(out);
		if (xmlWriter != null) {
			if (isWriteStartDocument()) {
				xmlWriter.writeStartDocument();
			}
			xmlWriter.writeComment(" This file was written by " + xmlWriter.getWriterName() + ". ");
			xhNode.toXml(this, xmlWriter);
			xmlWriter.writeEndDocument();
			xmlWriter.flush();
		}
		MiscIo.closeOutputFile(out);*/
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
	
	/**
	 * Pretty print.
	 * This is just a first quick version, to limit line lengths for easier human viewing.
	 * Replacements, where | represents a new line:
	 * <p>"," to ",| "</p>
	 * <p>":{" to ":{| "</p>
	 * <p>"},{" to "},|{| "</p>
	 * <p>"}," to "},| "</p>
	 * <p>":[{" to ":[|{| "</p>
	 * <p>}]} to }|]|}</p>
	 * @param htmlStr a JSON string
	 * @return pretty printed JSON string
	 */
	protected String prettyPrint(String htmlStr) {
		/*String str = jsonStr
		.replaceAll("\",\"", "\\\",\\\n \\\"")
		.replaceAll("\":\\{\"", "\\\":{\\\n \\\"")
		.replaceAll("\"\\},\\{\"", "\\\"\\\n},\\\n{\\\n \\\"")
		.replaceAll("\"\\},\"", "\\\"\\\n},\\\n \\\"")
		.replaceAll("\":\\[\\{\"", "\\\":[\\\n{\\\n \\\"")
		.replaceAll("}]}", "}\\\n]\\\n}");
		return str;*/
		return htmlStr;
	}

	public String getOutFileName() {
		return outFileName;
	}

	public void setOutFileName(String outFileName) {
		this.outFileName = outFileName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public IXholon getRoot() {
		return root;
	}

	public void setRoot(IXholon root) {
		this.root = root;
	}

	public boolean isShouldShowStateMachineEntities() {
		return shouldShowStateMachineEntities;
	}

	public void setShouldShowStateMachineEntities(
			boolean shouldShowStateMachineEntities) {
		this.shouldShowStateMachineEntities = shouldShowStateMachineEntities;
	}
	
	public String getNameTemplate() {
		return nameTemplate;
	}

	public void setNameTemplate(String nameTemplate) {
		this.nameTemplate = nameTemplate;
	}

	public String getOutPath() {
		return outPath;
	}

	public void setOutPath(String outPath) {
		this.outPath = outPath;
	}

	/*public Writer getOut() {
		return out;
	}

	public void setOut(Writer out) {
		this.out = out;
	}*/

	public boolean isShouldQuoteLabels() {
		return shouldQuoteLabels;
	}

	public void setShouldQuoteLabels(boolean shouldQuoteLabels) {
		this.shouldQuoteLabels = shouldQuoteLabels;
	}

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

	public boolean isShouldPrettyPrint() {
		return shouldPrettyPrint;
	}

	public void setShouldPrettyPrint(boolean shouldPrettyPrint) {
		this.shouldPrettyPrint = shouldPrettyPrint;
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#get2XmlRoot()
	 */
	public IXholon get2XmlRoot() {
		return root;
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

	@Override
	public boolean isWriteJavaScriptAttributes() {
	  return writeJavaScriptAttributes;
	}
	
	@Override
	public void setWriteJavaScriptAttributes(boolean writeJavaScriptAttributes) {
	  this.writeJavaScriptAttributes = writeJavaScriptAttributes;
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#writeSpecial(org.primordion.xholon.base.IXholon)
	 */
	public void writeSpecial(IXholon node) {
		// do nothing
	}
	
	@Override
	public boolean isShouldWriteVal() {
	  return false; //shouldWriteVal;
	}
	
	@Override
	public void setShouldWriteVal(boolean shouldWriteVal) {
	  //this.shouldWriteVal = shouldWriteVal;
	}
	
	@Override
	public boolean isShouldWriteAllPorts() {
	  return false; //shouldWriteAllPorts;
	}
	
	@Override
	public void setShouldWriteAllPorts(boolean shouldWriteAllPorts) {
	  //this.shouldWriteAllPorts = shouldWriteAllPorts;
	}
	
}

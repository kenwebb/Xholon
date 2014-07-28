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
import org.primordion.xholon.io.json.JsonWriterFactory;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.XholonDirectoryService;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;
//import org.primordion.xholon.util.MiscIo;

/**
 * Export a Xholon model in JSON format.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on May 3, 2010)
 */
@SuppressWarnings("serial")
public class Xholon2Json extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat, IXholon2Xml {
	
	private String outFileName;
	private String outPath = "./ef/json/";
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
	//protected String nameTemplate = "^^C^^^"; // don't include role name
	
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
	//private boolean xhAttrReturnAll = true;
	
	/** Should the XML Declaration be written at the start of a new XML document. */
	private boolean writeStartDocument = true;
	
	/** Should the Xholon ID be written out as an XML attribute for each node. */
	//private boolean writeXholonId = false;
	
	/** Should the Xholon roleName be written out as an XML attribute for each node. */
	//private boolean writeXholonRoleName = true;
	
	/** Should ports be written out as XML attributes for each node that has ports. */
	//private boolean writePorts = false;
	
	//private boolean shouldPrettyPrint = true;
	
	/** Should annotations be written when available for nodes. */
	//private boolean writeAnnotations = true;
	
	/** Annotation service. */
	private IXholon annService = null;
	
	/** Should standard attributes be written out for each node. */
	//private boolean writeStandardAttributes = true;
	
	/** Should attributes be written out for each node. */
	//private boolean writeAttributes = true;

	/**
	 * Constructor.
	 */
	public Xholon2Json() {}
	
	/*
	 * @see org.primordion.xholon.io.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
	 */
	public boolean initialize(String mmFileName, String modelName, IXholon root) {
		timeNow = new Date();
		timeStamp = timeNow.getTime();
		if (mmFileName == null) {
			this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".json";
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
		
		//root.println(root);
		//test(root);
		
		sb = new StringBuilder();
		String jsonStr = null;
		if (isShouldPrettyPrint()) {
			xholon2Writer(root, sb);
			jsonStr = prettyPrint(sb.toString(), 2);
			//try {
			//	out.write(jsonStr);
			//	out.flush();
			//} catch (IOException e) {
			//	Xholon.getLogger().error("", e);
			//}
		}
		else {
			xholon2Writer(root, sb);
			jsonStr = sb.toString();
		}
		writeToTarget(jsonStr, outFileName, outPath, root);
		//if (shouldClose) {
		//	MiscIo.closeOutputFile(out);
		//}
	}
	
	protected native void test(IXholon xhNode) /*-{
	  $wnd.console.log(JSON);
	  $wnd.console.log(JSON.parse);
	  $wnd.console.log(JSON.stringify);
	  $wnd.console.log(JSON.parse("[]"));
	  $wnd.console.log(JSON.parse('{"abc": "ABC"}'));
	  //$wnd.console.log(xhNode); // fails
	  //$wnd.console.log(xhNode.@org.primordion.xholon.base.Xholon::getRootNode()()); // fails
	  //$wnd.console.log(JSON.stringify(xhNode)); // fails
	  
	  // TypeError: Converting circular structure to JSON (Chrome dev tools, GWT prod mode)
	  //JSON.stringify(xhApp_getRoot());
	  
	}-*/;
	
	/**
	 * Transform a Xholon subtree using a specified Writer.
	 * @param out An instance of Writer.
	 * @return The same instance of Writer.
	 */
	public Object xholon2Writer(IXholon xhNode, Object out) {
		IXmlWriter xmlWriter = JsonWriterFactory.getXmlWriter(out);
		if (xmlWriter != null) {
		  xmlWriter.setShouldWriteVal(isShouldWriteVal());
		  xmlWriter.setShouldWriteAllPorts(isShouldWriteAllPorts());
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
		IXmlWriter xmlWriter = JsonWriterFactory.getXmlWriter(out);
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
		/*out = MiscIo.openOutputFile(fileName);
		if (out == null) {return;}
		IXmlWriter xmlWriter = JsonWriterFactory.getXmlWriter(out);
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
	public native boolean isWriteAnnotations() /*-{
		return this.efParams.writeAnnotations;
	}-*/;

	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#setWriteAnnotations(boolean)
	 */
	public native void setWriteAnnotations(boolean writeAnnotations) /*-{
		this.efParams.writeAnnotations = writeAnnotations;
	}-*/;
	
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
	 * @param jsonStr a JSON string
	 * @return pretty printed JSON string
	 */
	/*protected String prettyPrint(String jsonStr) {
		String str = jsonStr
		.replaceAll("\",\"", "\\\",\\\n \\\"")
		.replaceAll("\":\\{\"", "\\\":{\\\n \\\"")
		.replaceAll("\"\\},\\{\"", "\\\"\\\n},\\\n{\\\n \\\"")
		.replaceAll("\"\\},\"", "\\\"\\\n},\\\n \\\"")
		.replaceAll("\":\\[\\{\"", "\\\":[\\\n{\\\n \\\"")
		.replaceAll("}]}", "}\\\n]\\\n}");
		return str;
	}*/
	
	/**
	 * Pretty print.
	 * @param jsonStr a JSON string
	 * @param indent successive levels in the stringification will each be indented
	 *   by this many space characters (up to 10).
	 * @return pretty printed JSON string
	 */
	protected native String prettyPrint(String jsonStr, int indent) /*-{
	  if (typeof JSON === 'undefined') {return jsonStr;}
    return JSON.stringify(JSON.parse(jsonStr), null, indent);
	}-*/;

	/**
   * Make a JavaScript object with all the parameters for this external format.
   */
  protected native void makeEfParams() /*-{
    var p = {};
    //p.shouldShowStateMachineEntities = false;
    p.nameTemplate = "^^C^^^";
    p.xhAttrReturnAll = true;
    p.writeXholonId = false;
	  p.writeXholonRoleName = true;
	  p.writePorts = false;
    p.writeAnnotations = true;
    p.shouldPrettyPrint = true;
    p.writeAttributes = true;
    p.writeStandardAttributes = true;
    p.shouldWriteVal = true;
    p.shouldWriteAllPorts = true;
    this.efParams = p;
  }-*/;

  /**
   * Whether or not to write an attribute with the name "Val".
   */
  public native boolean isShouldWriteVal() /*-{return this.efParams.shouldWriteVal;}-*/;
  //public native void setShouldWriteVal(boolean shouldWriteVal) /*-{this.efParams.shouldWriteVal = shouldWriteVal;}-*/;

  /**
   * Whether or not to write an attribute with the name "AllPorts".
   */
  public native boolean isShouldWriteAllPorts() /*-{return this.efParams.shouldWriteAllPorts;}-*/;
  //public native void setShouldWriteAllPorts(boolean shouldWriteAllPorts) /*-{this.efParams.shouldWriteAllPorts = shouldWriteAllPorts;}-*/;
  
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
	
	public native String getNameTemplate() /*-{
		return this.efParams.nameTemplate;
	}-*/;

	public native void setNameTemplate(String nameTemplate) /*-{
		this.efParams.nameTemplate = nameTemplate;
	}-*/;

	public String getOutPath() {
		return outPath;
	}

	public void setOutPath(String outPath) {
		this.outPath = outPath;
	}

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
	public native boolean getXhAttrReturnAll() /*-{
		return this.efParams.xhAttrReturnAll;
	}-*/;
	
	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#setXhAttrReturnAll(boolean)
	 */
	public native void setXhAttrReturnAll(boolean xhAttrReturnAll) /*-{
		this.efParams.xhAttrReturnAll = xhAttrReturnAll;
	}-*/;
	
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
	public native boolean isWriteXholonId() /*-{
		return this.efParams.writeXholonId;
	}-*/;
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#setWriteXholonId(boolean)
	 */
	public native void setWriteXholonId(boolean writeXholonId) /*-{
		this.efParams.writeXholonId = writeXholonId;
	}-*/;
	
	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#isWriteXholonRoleName()
	 */
	public native boolean isWriteXholonRoleName() /*-{
		return this.efParams.writeXholonRoleName;
	}-*/;

	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#setWriteXholonRoleName(boolean)
	 */
	public native void setWriteXholonRoleName(boolean writeXholonRoleName) /*-{
		this.efParams.writeXholonRoleName = writeXholonRoleName;
	}-*/;

	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#isWritePorts()
	 */
	public native boolean isWritePorts() /*-{
		return this.efParams.writePorts;
	}-*/;
	
	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#setWritePorts(boolean)
	 */
	public native void setWritePorts(boolean writePorts) /*-{
		this.efParams.writePorts = writePorts;
	}-*/;

	public native boolean isShouldPrettyPrint() /*-{
		return this.efParams.shouldPrettyPrint;
	}-*/;

	public native void setShouldPrettyPrint(boolean shouldPrettyPrint) /*-{
		this.efParams.shouldPrettyPrint = shouldPrettyPrint;
	}-*/;
	
	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#get2XmlRoot()
	 */
	public IXholon get2XmlRoot() {
		return root;
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#isWriteAttributes()
	 */
	public native boolean isWriteAttributes() /*-{
		return this.efParams.writeAttributes;
	}-*/;

	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#setWriteAttributes(boolean)
	 */
	public native void setWriteAttributes(boolean writeAttributes) /*-{
		this.efParams.writeAttributes = writeAttributes;
	}-*/;

	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#isWriteStandardAttributes()
	 */
	public native boolean isWriteStandardAttributes() /*-{
		return this.efParams.writeStandardAttributes;
	}-*/;

	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#setWriteStandardAttributes(boolean)
	 */
	public native void setWriteStandardAttributes(boolean writeStandardAttributes) /*-{
		this.efParams.writeStandardAttributes = writeStandardAttributes;
	}-*/;

	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#writeSpecial(org.primordion.xholon.base.IXholon)
	 */
	public void writeSpecial(IXholon node) {
		// do nothing
	}

}

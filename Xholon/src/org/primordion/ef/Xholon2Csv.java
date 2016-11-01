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
import org.primordion.xholon.io.csv.CsvStrWriter;
import org.primordion.xholon.io.csv.CsvWriterFactory;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.XholonDirectoryService;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;
//import org.primordion.xholon.util.MiscIo;

/**
 * Export a Xholon model in CSV format.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on November 10, 2012)
 */
@SuppressWarnings("serial")
public class Xholon2Csv extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat, IXholon2Xml {
	
	private String outFileName;
	private String outPath = "./ef/csv/";
	private String modelName;
	private IXholon root;
	//private Writer out;
	private StringBuilder sb;
	
	/** Current date and time. */
	private Date timeNow;
	private long timeStamp;
	
	/** Whether or not to show state machine nodes. */
	//private boolean shouldShowStateMachineEntities = false;
	
	/** Template to use when writing out node names. */
	//protected String nameTemplate = "r:C^^^";
	//protected String nameTemplate = "^^C^^^"; // don't include role name
	
	/** Whether or not labels/names should be quoted. */
	//private boolean shouldQuoteLabels = false;
	
	/** Whether or not to format the output by inserting new lines. */
	//private boolean shouldInsertNewlines = true;
	
	/** How to transform Xholon/Java attributes. */
	//private int xhAttrStyle = XHATTR_TO_XMLATTR; //XHATTR_TO_NULL;
	
	/**
	 * Whether to return only the attributes of the immediate concrete class (false),
	 * or attributes from all concrete classes in the inheritance hierarchy that extend
	 * from Xholon or XholonWithPorts (true).
	 */
	//private boolean xhAttrReturnAll = true;
	
	/** Should the XML Declaration be written at the start of a new XML document. */
	//private boolean writeStartDocument = true;
	
	/** Should the Xholon ID be written out as an XML attribute for each node. */
	//private boolean writeXholonId = false;
	
	/** Should the Xholon roleName be written out as an XML attribute for each node. */
	//private boolean writeXholonRoleName = true;
	
	/** Should ports be written out as XML attributes for each node that has ports. */
	//private boolean writePorts = false;
	
	//private boolean shouldPrettyPrint = false;
	
	/** Should annotations be written when available for nodes. */
	//private boolean writeAnnotations = false;
	
	/** Annotation service. */
	private IXholon annService = null;
	
	/** Should standard attributes be written out for each node. */
	//private boolean writeStandardAttributes = true;
	
	/** Should attributes be written out for each node. */
	//private boolean writeAttributes = true;

	/**
	 * Constructor.
	 */
	public Xholon2Csv() {}
	
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
			this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".csv";
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
		//if (root.getClass().getName().endsWith(".Spreadsheet")) {
		if ("Spreadsheet".equals(root.getXhcName())) {
		  spreadsheet2Sb(root, sb);
		}
		else {
		  xholon2Writer(root, sb);
		}
		writeToTarget(sb.toString(), outFileName, outPath, root);
		/* GWT
		if (shouldPrettyPrint) {
			Writer outpp = xholon2Writer(root, new StringWriter());
			String csvStr = prettyPrint(outpp.toString());
			// do the actual pretty print; use regular expression(s) to replace {},
			try {
				out.write(csvStr);
				out.flush();
			} catch (IOException e) {
				Xholon.getLogger().error("", e);
			}
		}
		else {
			xholon2Writer(root, out);
		}*/
		//if (shouldClose) {
		//	MiscIo.closeOutputFile(out);
		//}
	}
	
	/**
	 * Write a Spreadsheet subtree as CSV.
	 */
	protected void spreadsheet2Sb(IXholon xhNode, StringBuilder sprSb) {
	  sprSb.append("<").append(xhNode.getXhcName());
	  if (xhNode.getRoleName() != null) {
	    sprSb.append(" roleName=\"").append(xhNode.getRoleName()).append("\"");
	  }
	  sprSb.append(">\n");
	  // SpreadsheetRow
	  IXholon row = xhNode.getFirstChild();
	  while (row != null) {
	    // SpreadsheetCell
	    IXholon cell = row.getFirstChild();
      while (cell != null) {
        if (cell.getFirstChild() == null) {
          sprSb.append(cell.getVal_Object());
        }
        else {
          // SpreadsheetFormula
          sprSb.append(cell.getFirstChild().getVal_String());
        }
        cell = cell.getNextSibling();
        if (cell != null) {
          sprSb.append(",");
        }
      }
      sprSb.append("\n");
	    row = row.getNextSibling();
	  }
	  sprSb.append("</").append(xhNode.getXhcName()).append(">\n");
	}
	
	/**
	 * Transform a Xholon subtree using a specified StringBuilder.
	 * @param out An instance of StringBuilder.
	 * @return The same instance of StringBuilder.
	 */
	public Object xholon2Writer(IXholon xhNode, Object out) {
		IXmlWriter xmlWriter = CsvWriterFactory.getXmlWriter(out);
		if (xmlWriter != null) {
			if (isWriteStartDocument()) {
				xmlWriter.writeStartDocument();
			}
			((CsvStrWriter)xmlWriter).setShouldWriteText(this.isShouldWriteText());
			((CsvStrWriter)xmlWriter).setShouldWriteHeaderElem(this.isShouldWriteHeaderElem());
			((CsvStrWriter)xmlWriter).setShouldWriteHeaderAttr(this.isShouldWriteHeaderAttr());
			((CsvStrWriter)xmlWriter).setFieldSeparator(this.getFieldSeparator().charAt(0));
			((CsvStrWriter)xmlWriter).setTextDelimiter(this.getTextDelimiter().charAt(0));
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
	  /* GWT
		out = new StringWriter();
		IXmlWriter xmlWriter = CsvWriterFactory.getXmlWriter(out);
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
	  /* GWT
		out = MiscIo.openOutputFile(fileName);
		if (out == null) {return;}
		IXmlWriter xmlWriter = CsvWriterFactory.getXmlWriter(out);
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

	/**
   * Make a JavaScript object with all the parameters for this external format.
{"writeStartDocument":true,"shouldWriteText":true,"shouldWriteHeaderElem":true,"shouldWriteHeaderAttr":true,"fieldSeparator":",","textDelimiter":"\"","writeAnnotations":false,"shouldShowStateMachineEntities":false,"nameTemplate":"^^C^^^","shouldQuoteLabels":false,"xhAttrStyle":1,"xhAttrReturnAll":true,"writeXholonId":false,"writeXholonRoleName":true,"writePorts":false,"shouldPrettyPrint":false,"writeAttributes":true,"writeStandardAttributes":true}
   */
  protected native void makeEfParams() /*-{
    var p = {};
    // these attributes are used in CsvStrWriter
    p.writeStartDocument = true;
    p.shouldWriteText = false;
    p.shouldWriteHeaderElem = true;
    p.shouldWriteHeaderAttr = true;
    p.fieldSeparator = ','; // a Java char
    p.textDelimiter = '"'; // a Java char
    
    // these attributes are NOT used in CsvStrWriter
    p.writeAnnotations = false;
    p.shouldShowStateMachineEntities = false;
    p.nameTemplate = "^^C^^^"; // "r:C^^^"
    p.shouldQuoteLabels = false;
    p.xhAttrStyle = 1; // XHATTR_TO_XMLATTR
    p.xhAttrReturnAll = true;
    p.writeXholonId = false;
    p.writeXholonRoleName = true;
    p.writePorts = false;
    p.shouldPrettyPrint = false;
    p.writeAttributes = true;
    p.writeStandardAttributes = true;
    this.efParams = p;
  }-*/;

	public native boolean isShouldWriteText() /*-{return this.efParams.shouldWriteText;}-*/;
	public native void setShouldWriteText(boolean shouldWriteText) /*-{this.efParams.shouldWriteText = shouldWriteText;}-*/;
	
	public native boolean isShouldWriteHeaderElem() /*-{return this.efParams.shouldWriteHeaderElem;}-*/;
	public native void setShouldWriteHeaderElem(boolean shouldWriteHeaderElem) /*-{this.efParams.shouldWriteHeaderElem = shouldWriteHeaderElem;}-*/;
	
	public native boolean isShouldWriteHeaderAttr() /*-{return this.efParams.shouldWriteHeaderAttr;}-*/;
	public native void setShouldWriteHeaderAttr(boolean shouldWriteHeaderAttr) /*-{this.efParams.shouldWriteHeaderAttr = shouldWriteHeaderAttr;}-*/;
	
	public native String getFieldSeparator() /*-{return this.efParams.fieldSeparator;}-*/;
	public native void setFieldSeparator(String fieldSeparator) /*-{this.efParams.fieldSeparator = fieldSeparator;}-*/;
	
	public native String getTextDelimiter() /*-{return this.efParams.textDelimiter;}-*/;
	public native void setTextDelimiter(String textDelimiter) /*-{this.efParams.textDelimiter = textDelimiter;}-*/;
	
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
	 * @param csvStr a JSON string
	 * @return pretty printed JSON string
	 */
	protected String prettyPrint(String csvStr) {
		/*String str = jsonStr
		.replaceAll("\",\"", "\\\",\\\n \\\"")
		.replaceAll("\":\\{\"", "\\\":{\\\n \\\"")
		.replaceAll("\"\\},\\{\"", "\\\"\\\n},\\\n{\\\n \\\"")
		.replaceAll("\"\\},\"", "\\\"\\\n},\\\n \\\"")
		.replaceAll("\":\\[\\{\"", "\\\":[\\\n{\\\n \\\"")
		.replaceAll("}]}", "}\\\n]\\\n}");
		return str;*/
		return csvStr;
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

	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#isWriteAnnotations()
	 */
	public native boolean isWriteAnnotations() /*-{return this.efParams.writeAnnotations;}-*/;
	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#setWriteAnnotations(boolean)
	 */
	public native void setWriteAnnotations(boolean writeAnnotations) /*-{this.efParams.writeAnnotations = writeAnnotations;}-*/;
	
	public native boolean isShouldShowStateMachineEntities() /*-{return this.efParams.shouldShowStateMachineEntities;}-*/;
	public native void setShouldShowStateMachineEntities(boolean shouldShowStateMachineEntities)
	  /*-{this.efParams.shouldShowStateMachineEntities = shouldShowStateMachineEntities;}-*/;
	
	public native String getNameTemplate() /*-{return this.efParams.nameTemplate;}-*/;
	public native void setNameTemplate(String nameTemplate) /*-{this.efParams.nameTemplate = nameTemplate;}-*/;

	public String getOutPath() {
		return outPath;
	}

	public void setOutPath(String outPath) {
		this.outPath = outPath;
	}

  /* GWT
	public Writer getOut() {
		return out;
	}

	public void setOut(Writer out) {
		this.out = out;
	}*/

	public native boolean isShouldQuoteLabels() /*-{return this.efParams.shouldQuoteLabels;}-*/;
	public native void setShouldQuoteLabels(boolean shouldQuoteLabels) /*-{this.efParams.shouldQuoteLabels = shouldQuoteLabels;}-*/;
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#getXhAttrStyle()
	 */
	public native int getXhAttrStyle() /*-{return this.efParams.xhAttrStyle;}-*/;
	/* 
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#setXhAttrStyle(int)
	 */
	public native void setXhAttrStyle(int xhAttrStyle) /*-{this.efParams.xhAttrStyle = xhAttrStyle;}-*/;
	
	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#getXhAttrReturnAll()
	 */
	public native boolean getXhAttrReturnAll() /*-{return this.efParams.xhAttrReturnAll;}-*/;
	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#setXhAttrReturnAll(boolean)
	 */
	public native void setXhAttrReturnAll(boolean xhAttrReturnAll) /*-{this.efParams.xhAttrReturnAll = xhAttrReturnAll;}-*/;
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#isWriteStartDocument()
	 * Should the XML Declaration be written at the start of a new XML document.
	 */
	public native boolean isWriteStartDocument() /*-{return this.efParams.writeStartDocument;}-*/;
	/* 
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#setWriteStartDocument(boolean)
	 */
	public native void setWriteStartDocument(boolean writeStartDocument) /*-{this.efParams.writeStartDocument = writeStartDocument;}-*/;
	
	/* 
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#isWriteXholonId()
	 */
	public native boolean isWriteXholonId() /*-{return this.efParams.writeXholonId;}-*/;
	/* 
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#setWriteXholonId(boolean)
	 */
	public native void setWriteXholonId(boolean writeXholonId) /*-{this.efParams.writeXholonId = writeXholonId;}-*/;

	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#isWriteXholonRoleName()
	 */
	public native boolean isWriteXholonRoleName() /*-{return this.efParams.writeXholonRoleName;}-*/;
	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#setWriteXholonRoleName(boolean)
	 */
	public native void setWriteXholonRoleName(boolean writeXholonRoleName) /*-{this.efParams.writeXholonRoleName = writeXholonRoleName;}-*/;
	
	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#isWritePorts()
	 */
	public native boolean isWritePorts() /*-{return this.efParams.writePorts;}-*/;
	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#setWritePorts(boolean)
	 */
	public native void setWritePorts(boolean writePorts) /*-{this.efParams.writePorts = writePorts;}-*/;

	public native boolean isShouldPrettyPrint() /*-{return this.efParams.shouldPrettyPrint;}-*/;
	public native void setShouldPrettyPrint(boolean shouldPrettyPrint) /*-{this.efParams.shouldPrettyPrint = shouldPrettyPrint;}-*/;
	
	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#get2XmlRoot()
	 */
	public IXholon get2XmlRoot() {
		return root;
	}
	
	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#isWriteAttributes()
	 */
	public native boolean isWriteAttributes() /*-{return this.efParams.writeAttributes;}-*/;
	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#setWriteAttributes(boolean)
	 */
	public native void setWriteAttributes(boolean writeAttributes) /*-{this.efParams.writeAttributes = writeAttributes;}-*/;

	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#isWriteStandardAttributes()
	 */
	public native boolean isWriteStandardAttributes() /*-{return this.efParams.writeStandardAttributes;}-*/;
	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#setWriteStandardAttributes(boolean)
	 */
	public native void setWriteStandardAttributes(boolean writeStandardAttributes) /*-{this.efParams.writeStandardAttributes = writeStandardAttributes;}-*/;

	/*
	 * @see org.primordion.xholon.io.xml.IXholon2Xml#writeSpecial(org.primordion.xholon.base.IXholon)
	 */
	public void writeSpecial(IXholon node) {
		// do nothing
	}

}

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

package org.primordion.ef.other;

//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.StringWriter;
//import java.io.Writer;
//import java.security.AccessControlException;

//import javax.xml.transform.*;
//import javax.xml.transform.stream.StreamResult;
//import javax.xml.transform.stream.StreamSource;

import org.primordion.xholon.base.IXholon;
//import org.primordion.xholon.base.Xholon;
//import org.primordion.xholon.util.MiscIo;

/**
 * Export a Xholon model in GML format.
 * XGMML is the XML equivalent of GML.
 * This class first creates an XGMML String,
 * and then transforms that to GML using Java XSLT.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on April 21, 2013)
 */
@SuppressWarnings("serial")
public class Xholon2Gml extends Xholon2Xgmml {
	
	/**
	 * Name of the XSL stylesheet that specifies the XGMML to GML transformation.
	 */
	private String xslFileName = "togml.xsl";
	
	private String gmlOutPath = "./ef/gml/";
	
	//private Writer gmlOut = null;
	
	/**
	 * Constructor.
	 */
	public Xholon2Gml() {}
	
	@Override
	public boolean initialize(String outFileName, String modelName, IXholon root) {
		// for use by Xholon2Xgmml
		//out = new StringWriter();
		sb = new StringBuilder();
		shouldClose = false;
		// for use by Xholon2Gml
		this.setOutPath(gmlOutPath);
		this.setFileNameExtension(".gml");
		this.shouldWriteToTarget = false;
		return super.initialize(outFileName, modelName, root);
	}
	
	@Override
	public void writeAll() {
		// call Xholon2Xgmml to write it all as XGMML
		super.writeAll();
		//System.out.println(out.toString());
		
		// prepare to transform the XGMML to GML
		/*shouldClose = true;
		if (this.getRoot().getApp().isUseAppOut()) {
			gmlOut = this.getRoot().getApp().getOut();
			shouldClose = false;
		}
		else {
			try {
				// create any missing output directories
				File dirOut = new File(this.getOutPath());
				dirOut.mkdirs(); // will create a new directory only if there is no existing one
				gmlOut = MiscIo.openOutputFile(this.getOutFileName());
			} catch(AccessControlException e) {
				//out = new PrintWriter(System.out);
				gmlOut = this.getRoot().getApp().getOut();
				shouldClose = false;
			}
		}*/
		
		String gmlStr = toGML(sb.toString());
		writeToTarget(gmlStr, this.getOutFileName(), this.getOutPath(), this.getRoot());
		
		// use XSL to write the XGMML String to GML
		/* GWT use JSNI method
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			StreamSource xslStream = new StreamSource(this.getOutPath() + xslFileName);
			Transformer transformer = factory.newTransformer(xslStream);
			StreamSource in = new StreamSource(new ByteArrayInputStream(out.toString().getBytes()));
			StreamResult srOut = new StreamResult(gmlOut);
			transformer.transform(in, srOut);
		} catch (TransformerConfigurationException e) {
			Xholon.getLogger().error("", e);
		} catch (TransformerException e) {
			Xholon.getLogger().error("", e);
		} finally {
		}*/
		//if (shouldClose) {
		//	MiscIo.closeOutputFile(gmlOut);
		//}
	}
	
	public native String toGML(String xgmmlStr) /*-{

var xsl_string = '<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">\
   <xsl:output method="text"/>\
   <xsl:template match="text()"/>\
   <!--\
	===========	From XGMML   To GML  =================\
		   	   by John Punin\
-->\
   <xsl:template match="*">\
      <xsl:value-of select="name()"/> [\
<xsl:apply-templates select="@*"/>\
      <xsl:apply-templates/>]\
</xsl:template>\
   <!-- Numeric Attributes -->\
   <xsl:template match="@id|@directed|@Scale|@Graphic|@Rootnode\
		 |@source|@target|@x|@y|@z|@w|@h|@d|@width|@smooth\
		 |@splinesteps|@extent|@start|@visible">\
      <xsl:value-of select="name()"/> <xsl:text>  </xsl:text>\
      <xsl:value-of select="."/><xsl:text>\n</xsl:text>\
</xsl:template>\
   <!-- String  Attributes -->\
   <xsl:template match="@name|@value|@type|@weight|@labelanchor|@Vendor\
                  |@layout|@image|@bitmap|@arrow|@capstyle|@joinstyle\
		  |@justify|@font|@style|@stipple|@fill|@outline|@anchor">\
      <xsl:value-of select="name()"/> "<xsl:value-of select="."/>"\
</xsl:template>\
   <!--label-->\
   <xsl:template match="@label[contains(.,\'&quot;\')]">\
      <xsl:value-of select="name()"/> "<xsl:value-of select="translate(.,\'&quot;\',\' \')"/>"\
</xsl:template>\
   <xsl:template match="@label[not(contains(.,\'&quot;\'))]">\
      <xsl:value-of select="name()"/> "<xsl:value-of select="."/>"\
</xsl:template>\
</xsl:stylesheet>';

var xsl = (new DOMParser()).parseFromString(xsl_string, "text/xml");

function stringToXml(xml_string) {
  console.log(xml_string);
  return (new DOMParser()).parseFromString(xml_string, "text/xml");
}

function xmlToString(xml) {
  console.log("xmlToString1");
  console.log(xml.documentElement);
  var serializer = new XMLSerializer();
  console.log(serializer);
  var str = serializer.serializeToString(xml);
  console.log(str);
  console.log("xmlToString99");
  return str;
}

function isParseError(xml) {
  try {
    console.log(xml.documentElement.firstChild.firstChild.tagName);
    return xml.documentElement.tagName == "parsererror" ||
           xml.documentElement.firstChild.firstChild.tagName == "parsererror";
  }
  catch (ex) {
    return false;
  }
}

function togml(input) {
  console.log("togml0");
  console.log(input);
  var xml = stringToXml(input);

  if (isParseError(xml)) {
    console.log("parse error");
    return input;
  }
  console.log("togml1");
  var transformedXml = xslTransformation(xml, xsl);
  console.log("togml2");
  console.log(transformedXml);
  return xmlToString(transformedXml);
}

function xslTransformation(xml, xsl) {
  console.log("xslTransformation1");
  // code for IE
  if (window.ActiveXObject) {
    var ex = xml.transformNode(xsl);
    return ex;
  }
  // code for Mozilla, Firefox, Opera, etc.
  else if ($doc.implementation && $doc.implementation.createDocument) {
    console.log("xslTransformation2");
    var xsltProcessor = new XSLTProcessor();
    console.log(xsltProcessor);
    console.log(xsl_string);
    xsltProcessor.importStylesheet(xsl);
    console.log("xslTransformation3");
    var resultDocument = xsltProcessor.transformToDocument(xml, $doc);
    console.log("xslTransformation4");
    return resultDocument;
  }
  else {
    console.log("xslTransformation3");
  }
}

return togml(xgmmlStr);

  }-*/;

	@Override
	public String getWriterName() {
		return "Xholon2Gml";
	}
	
}

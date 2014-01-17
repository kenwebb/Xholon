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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.InputSource;

/**
 * Pretty-print an XML string.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on September 5, 2012)
 */
public class XmlPrettyPrinter {
	
	private int indentAmount = 2;
	private String omitXmlDeclaration = "no";
	private String encoding = "UTF-8";
	
  public String format(String xml) {
    /* Java SE version
    try {
    	TransformerFactory tf = SAXTransformerFactory.newInstance();
    	//tf.setAttribute("indent-number", new Integer(2));
    	Transformer serializer= tf.newTransformer();
        if (indentAmount == 0) {
        	serializer.setOutputProperty(OutputKeys.INDENT, "no");
        }
        else {
        	serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        }
        serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, omitXmlDeclaration);
        serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
        		Integer.toString(indentAmount));
        serializer.setOutputProperty(OutputKeys.ENCODING, encoding);
        Source xmlSource = new SAXSource(new InputSource(new ByteArrayInputStream(xml.getBytes())));
        StreamResult res = new StreamResult(new ByteArrayOutputStream());            
        serializer.transform(xmlSource, res);
        return new String(((ByteArrayOutputStream)res.getOutputStream()).toByteArray());
    } catch (Exception e) {
        return xml;
    }*/
    String indent = "yes";
    if (indentAmount == 0) {indent = "no";}
    return format(xml, omitXmlDeclaration, indent);
  }
  
  /**
   * GWT version
   * source: http://stackoverflow.com/questions/5657181/use-xslt-to-pretty-print-xml-xhtml-without-corrupting-namespace-info
   */
  public native String format(String inputXmlStr, String omitXmlDeclaration, String indent) /*-{

var xsl_string = '<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">\
<xsl:output method="xml" indent="' + indent + '" omit-xml-declaration="' + omitXmlDeclaration + '"/>\
<xsl:strip-space elements="*"/>\
<xsl:template match="/">\
  <xsl:copy-of select="."/>\
</xsl:template>\
</xsl:stylesheet>';

var xsl = (new DOMParser()).parseFromString(xsl_string, "text/xml");

function stringToXml(xml_string) {
  //console.log(xml_string);
  return (new DOMParser()).parseFromString(xml_string, "text/xml");
}

function xmlToString(xml) {
  //console.log("xmlToString1");
  //console.log(xml.documentElement);
  var serializer = new XMLSerializer();
  //console.log(serializer);
  var str = serializer.serializeToString(xml);
  //console.log(str);
  //console.log("xmlToString99");
  return str;
}

function isParseError(xml) {
  try {
    //console.log(xml.documentElement.firstChild.firstChild.tagName);
    return xml.documentElement.tagName == "parsererror" ||
           xml.documentElement.firstChild.firstChild.tagName == "parsererror";
  }
  catch (ex) {
    return false;
  }
}

function beautifyXml(input) {
  //console.log(input);
  var xml = stringToXml(input);

  if (isParseError(xml)) {
    //console.log("parse error");
    return input;
  }
  //console.log("beautifyXml1");
  var transformedXml = xslTransformation(xml, xsl);
  //console.log("beautifyXml2");
  //console.log(transformedXml);
  return xmlToString(transformedXml);
}

function xslTransformation(xml, xsl) {
  //console.log("xslTransformation1");
  // code for IE
  if (window.ActiveXObject) {
    var ex = xml.transformNode(xsl);
    return ex;
  }
  // code for Mozilla, Firefox, Opera, etc.
  else if ($doc.implementation && $doc.implementation.createDocument) {
    //console.log("xslTransformation2");
    var xsltProcessor = new XSLTProcessor();
    //console.log(xsltProcessor);
    //console.log(xsl_string);
    xsltProcessor.importStylesheet(xsl);
    //console.log("xslTransformation3");
    var resultDocument = xsltProcessor.transformToDocument(xml, $doc);
    //console.log("xslTransformation4");
    return resultDocument;
  }
  else {
    //console.log("xslTransformation3");
  }
}

return beautifyXml(inputXmlStr);

  }-*/;

  public int getIndentAmount() {
		return indentAmount;
	}

	public void setIndentAmount(int indentAmount) {
		this.indentAmount = indentAmount;
	}

	public String getOmitXmlDeclaration() {
		return omitXmlDeclaration;
	}

	public void setOmitXmlDeclaration(String omitXmlDeclaration) {
		this.omitXmlDeclaration = omitXmlDeclaration;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}

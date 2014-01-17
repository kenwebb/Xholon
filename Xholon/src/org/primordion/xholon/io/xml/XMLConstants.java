/*
 * Copyright (c) 2003, 2005, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.primordion.xholon.io.xml;

/**
 * <p>@see javax.xml.XMLConstants</p>
 * 
 * <p>Utility class to contain basic XML values as constants.</p>
 *
 * @author <a href="mailto:Jeff.Suttor@Sun.com">Jeff Suttor</a>
 * @version $Revision: 1.8 $, $Date: 2010/05/25 16:19:45 $
 * @see <a href="http://www.w3.org/TR/xml11/">Extensible Markup Language (XML) 1.1</a>
 * @see <a href="http://www.w3.org/TR/REC-xml">Extensible Markup Language (XML) 1.0 (Second Edition)</a>
 * @see <a href="http://www.w3.org/XML/xml-V10-2e-errata">XML 1.0 Second Edition Specification Errata</a>
 * @see <a href="http://www.w3.org/TR/xml-names11/">Namespaces in XML 1.1</a>
 * @see <a href="http://www.w3.org/TR/REC-xml-names">Namespaces in XML</a>
 * @see <a href="http://www.w3.org/XML/xml-names-19990114-errata">Namespaces in XML Errata</a>
 * @see <a href="http://www.w3.org/TR/xmlschema-1/">XML Schema Part 1: Structures</a>
 * @since 1.5
 * 
 */
public final class XMLConstants {

    private XMLConstants() {}

    public static final String NULL_NS_URI = "";

    public static final String DEFAULT_NS_PREFIX = "";

    public static final String XML_NS_URI =
        "http://www.w3.org/XML/1998/namespace";

    public static final String XML_NS_PREFIX = "xml";

    public static final String XMLNS_ATTRIBUTE_NS_URI =
        "http://www.w3.org/2000/xmlns/";

    public static final String XMLNS_ATTRIBUTE = "xmlns";

    public static final String W3C_XML_SCHEMA_NS_URI =
        "http://www.w3.org/2001/XMLSchema";

    public static final String W3C_XML_SCHEMA_INSTANCE_NS_URI =
        "http://www.w3.org/2001/XMLSchema-instance";

    public static final String W3C_XPATH_DATATYPE_NS_URI =
    		"http://www.w3.org/2003/11/xpath-datatypes";

    public static final String XML_DTD_NS_URI = "http://www.w3.org/TR/REC-xml";

    public static final String RELAXNG_NS_URI =
    		"http://relaxng.org/ns/structure/1.0";

    public static final String FEATURE_SECURE_PROCESSING =
    		"http://javax.xml.XMLConstants/feature/secure-processing";
}

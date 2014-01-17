/*
 * Copyright (c) 2003, 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.primordion.xholon.io.xml.namespace;

import org.primordion.xholon.io.xml.XMLConstants;

/**
 * <p>@see javax.xml.namespace.QName</p>
 * 
 * <p><code>QName</code> represents a <strong>qualified name</strong>
 * as defined in the XML specifications: <a
 * href="http://www.w3.org/TR/xmlschema-2/#QName">XML Schema Part2:
 * Datatypes specification</a>, <a
 * href="http://www.w3.org/TR/REC-xml-names/#ns-qualnames">Namespaces
 * in XML</a>, <a
 * href="http://www.w3.org/XML/xml-names-19990114-errata">Namespaces
 * in XML Errata</a>.</p>
 *
 * <p>The value of a <code>QName</code> contains a <strong>Namespace
 * URI</strong>, <strong>local part</strong> and
 * <strong>prefix</strong>.</p>
 *
 * <p>The prefix is included in <code>QName</code> to retain lexical
 * information <strong><em>when present</em></strong> in an {@link
 * javax.xml.transform.Source XML input source}. The prefix is
 * <strong><em>NOT</em></strong> used in {@link #equals(Object)
 * QName.equals(Object)} or to compute the {@link #hashCode()
 * QName.hashCode()}.  Equality and the hash code are defined using
 * <strong><em>only</em></strong> the Namespace URI and local part.</p>
 *
 * <p>If not specified, the Namespace URI is set to {@link
 * javax.xml.XMLConstants#NULL_NS_URI XMLConstants.NULL_NS_URI}.
 * If not specified, the prefix is set to {@link
 * javax.xml.XMLConstants#DEFAULT_NS_PREFIX
 * XMLConstants.DEFAULT_NS_PREFIX}.</p>
 *
 * <p><code>QName</code> is immutable.</p>
 *
 * @author <a href="mailto:Jeff.Suttor@Sun.com">Jeff Suttor</a>
 * @version $Revision: 1.8 $, $Date: 2010/03/18 03:06:17 $
 * @see <a href="http://www.w3.org/TR/xmlschema-2/#QName">
 *   XML Schema Part2: Datatypes specification</a>
 * @see <a href="http://www.w3.org/TR/REC-xml-names/#ns-qualnames">
 *   Namespaces in XML</a>
 * @see <a href="http://www.w3.org/XML/xml-names-19990114-errata">
 *   Namespaces in XML Errata</a>
 * @since 1.5
 */
public class QName {
	
    private final String namespaceURI;

    private final String localPart;

    private final String prefix;
    
    public QName(final String namespaceURI, final String localPart) {
        this(namespaceURI, localPart, XMLConstants.DEFAULT_NS_PREFIX);
    }
    
    public QName(String namespaceURI, String localPart, String prefix) {

        // map null Namespace URI to default
        // to preserve compatibility with QName 1.0
        if (namespaceURI == null) {
            this.namespaceURI = XMLConstants.NULL_NS_URI;
        } else {
            this.namespaceURI = namespaceURI;
        }

        // local part is required.
        // "" is allowed to preserve compatibility with QName 1.0
        if (localPart == null) {
            throw new IllegalArgumentException(
                    "local part cannot be \"null\" when creating a QName");
        }
        this.localPart = localPart;

        // prefix is required
        if (prefix == null) {
            throw new IllegalArgumentException(
                    "prefix cannot be \"null\" when creating a QName");
        }
        this.prefix = prefix;
    }
    
    public QName(String localPart) {
        this(
            XMLConstants.NULL_NS_URI,
            localPart,
            XMLConstants.DEFAULT_NS_PREFIX);
    }
    
    public String getNamespaceURI() {
        return namespaceURI;
    }
    
    public String getLocalPart() {
        return localPart;
    }
    
    public String getPrefix() {
        return prefix;
    }
    
    public final boolean equals(Object objectToTest) {
        if (objectToTest == this) {
            return true;
        }

        if (objectToTest == null || !(objectToTest instanceof QName)) {
            return false;
        }

        QName qName = (QName) objectToTest;

        return localPart.equals(qName.localPart)
            && namespaceURI.equals(qName.namespaceURI);
    }
    
    public final int hashCode() {
        return namespaceURI.hashCode() ^ localPart.hashCode();
    }
    
    public String toString() {
        if (namespaceURI.equals(XMLConstants.NULL_NS_URI)) {
            return localPart;
        } else {
            return "{" + namespaceURI + "}" + localPart;
        }
    }
    
    public static QName valueOf(String qNameAsString) {

        // null is not valid
        if (qNameAsString == null) {
            throw new IllegalArgumentException(
                    "cannot create QName from \"null\" or \"\" String");
        }

        // "" local part is valid to preserve compatible behavior with QName 1.0
        if (qNameAsString.length() == 0) {
            return new QName(
                XMLConstants.NULL_NS_URI,
                qNameAsString,
                XMLConstants.DEFAULT_NS_PREFIX);
        }

        // local part only?
        if (qNameAsString.charAt(0) != '{') {
            return new QName(
                XMLConstants.NULL_NS_URI,
                qNameAsString,
                XMLConstants.DEFAULT_NS_PREFIX);
        }

        // Namespace URI improperly specified?
        if (qNameAsString.startsWith("{" + XMLConstants.NULL_NS_URI + "}")) {
            throw new IllegalArgumentException(
                "Namespace URI .equals(XMLConstants.NULL_NS_URI), "
                + ".equals(\"" + XMLConstants.NULL_NS_URI + "\"), "
                + "only the local part, "
                + "\""
                + qNameAsString.substring(2 + XMLConstants.NULL_NS_URI.length())
                + "\", "
                + "should be provided.");
        }

        // Namespace URI and local part specified
        int endOfNamespaceURI = qNameAsString.indexOf('}');
        if (endOfNamespaceURI == -1) {
            throw new IllegalArgumentException(
                "cannot create QName from \""
                    + qNameAsString
                    + "\", missing closing \"}\"");
        }
        return new QName(
            qNameAsString.substring(1, endOfNamespaceURI),
            qNameAsString.substring(endOfNamespaceURI + 1),
            XMLConstants.DEFAULT_NS_PREFIX);
    }
    
}
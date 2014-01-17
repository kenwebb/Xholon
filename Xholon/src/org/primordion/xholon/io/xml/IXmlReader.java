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

/**
 * The abstract base for classes that read XML.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on December 19, 2009)
 */
public interface IXmlReader {
	
	/** No event type. */
	public static final int NULL_EVENT = 0;
	
	// the basic five XmlPull events, available thru next()
	/** Start document event type. */
	public static final int START_DOCUMENT = 1;
	/** Start tag event type. */
	public static final int START_TAG = 2;
	/** End document event. */
	public static final int END_DOCUMENT = 3;
	/** End tag event type. */
	public static final int END_TAG = 4;
	/** Text event type. */
	public static final int TEXT = 5;
	
	// additional XmlPull events, available thru nextToken()
	/** Comment event type. */
	public static final int COMMENT = 6;
	/** CDSECT event type. */
	public static final int CDSECT = 7;
	/** DOCDECL event type. */
	public static final int DOCDECL = 8;
	/** ENTITY_REF event type. */
	public static final int ENTITY_REF = 9;
	/** Processing Instruction event type. */
	public static final int PROCESSING_INSTRUCTION = 10;
	/** IGNORABLE_WHITESPACE event type. */
	public static final int IGNORABLE_WHITESPACE = 11;
	
	/** Some unknown event type. This is almost certainly an error. */
	public static final int OTHER_TYPE = 99;
	
	/**
	 * Create a new instance of an XML reader/parser, able to read from in.
	 * @param in An input file, stream, string, or other type of reader.
	 */
	public abstract void createNew(Object in);

	/**
	 * Returns an integer code that indicates the type of the event the cursor is pointing to.
	 * @return An event type.
	 */
	public abstract int getEventType();

	/**
	 * Get next parsing event.
	 * @return An event type.
	 */
	public abstract int next();
	
	/**
	 * Get next parsing event.
	 * This method is available with the XmlPull API, which describes it:
	 * "This method works similarly to next() but will expose additional
	 * event types (COMMENT, CDSECT, DOCDECL, ENTITY_REF, PROCESSING_INSTRUCTION,
	 * or IGNORABLE_WHITESPACE) if they are available in input."
	 * @return An event type.
	 */
	public abstract int nextToken();

	/**
	 * Returns the name of the current event.
	 * @return A name.
	 */
	public abstract String getName();

	/**
	 * Returns the current value of the parse event as a string.
	 * @return A value, or null.
	 */
	public abstract String getText();

	/**
	 * Returns the name of the attribute at the provided index.
	 * @param index An index.
	 * @return An attribute name.
	 */
	public abstract String getAttributeName(int index);

	/**
	 * Returns the value of the attribute at the provided index.
	 * @param index An index.
	 * @return An attribute value.
	 */
	public abstract String getAttributeValue(int index);

	/**
	 * Returns the count of attributes.
	 * @return The number of attributes.
	 */
	public abstract int getAttributeCount();

	/**
	 * Returns the prefix of the current event.
	 * @return A prefix, or null.
	 */
	public abstract String getPrefix();

	/**
	 * If the current event is a START_ELEMENT or END_ELEMENT  this method
	 * returns the URI of the prefix or the default namespace.
	 * Returns null if the event does not have a prefix.
	 * @return the URI bound to this elements prefix, the default namespace, or null
	 */
	public abstract String getNamespaceURI();

	/**
	 * Get the reader that underlies this XML reader.
	 * It may be a file, stream, string, or other type of reader.
	 * @return The reader, or null.
	 */
	public abstract Object getUnderlyingReader();
}
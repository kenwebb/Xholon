/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2005, 2006, 2007, 2008 Ken Webb
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

package org.primordion.xholon.base;

/**
 * <p>XPath constants.</p>
 * <p>Adapted November 14, 2005 by Ken Webb for use in the Xholon project.
 *    Replaced QName with int, and added IXHOLON</p>
 * @since 0.1 (Xholon)
 *
 * @author <a href="mailto:Norman.Walsh@Sun.COM">Norman Walsh</a>
 * @author <a href="mailto:Jeff.Suttor@Sun.COM">Jeff Suttor</a>
 * @see <a href="http://www.w3.org/TR/xpath">XML Path Language (XPath) Version 1.0</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 */
public class XPathConstants {
      
    /**
     * <p>Private constructor to prevent instantiation.</p>
     */
    private XPathConstants() { }
  
    /**
     * <p>The Xholon data type.</p>
     * <p>Maps to Java {@link org.primordion.xholon.base.IXholon}.</p>
     */
    public static final int IXHOLON = 1;
    
    /**
     * <p>The XPath 1.0 number data type.</p>
     * <p>Maps to Java {@link Double}.</p>
     */
    public static final int NUMBER = 2;

    /**
     * <p>The XPath 1.0 string data type.</p>
     * <p>Maps to Java {@link String}.</p>
     */
    public static final int STRING = 3;

    /**
     * <p>The XPath 1.0 boolean data type.</p>
     * <p>Maps to Java {@link Boolean}.</p>
     */
    public static final int BOOLEAN = 4;

    /**
     * <p>The XPath 1.0 NodeSet data type.</p>
     * <p>Maps to Java {@link org.w3c.dom.NodeList}.</p>
     */
    public static final int NODESET = 5;

    /**
     * <p>The XPath 1.0 Node data type.</p>
     * <p>Maps to Java {@link org.w3c.dom.Node}.</p>
     */
    public static final int NODE = 6;
    
    /**
     * <p>A list of all IXholon nodes that match the XPath expression.</p>
     * <p>Maps to Java {@link java.util.List}.</p>
     */
    public static final int IXHOLONLIST = 7;
    
    /**
     * <p>The list of IXholon nodes that form the path
     * to the destination node.</p>
     * <p>Maps to Java {@link java.util.List}.</p>
     */
    public static final int IXHOLONPATH = 8;

}
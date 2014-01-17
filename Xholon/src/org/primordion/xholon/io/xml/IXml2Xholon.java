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

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IInheritanceHierarchy;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.service.creation.ITreeNodeFactory;

/**
 * This is the interface for any class that reads XML from any source
 * and transforms it into Xholon nodes.
 * Possible sources include a File, a String, a URI.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8 (Created on August 1, 2009)
 */
public interface IXml2Xholon {

	/**
	 * An ad-hoc instance is a IXholon that has no officially declared IXholonClass.
	 * These can be used for initial prototyping, but may be disallowed once a system
	 * is in production, for security reasons.
	 */
	public static final boolean ALLOW_AD_HOC_INSTANCES = true;
	
	/** <p>A forest is a collection of nodes or subtrees that do not have a parent.
	 *  A forest can be specified in Xholon XML using an element whose name starts with _-.</p>
	 *  <p>This feature is intended to be used when pasting in a forest of nodes and/or subtrees,
	 *  instead of having to paste them one at a time.
	 *  It can also be used as the root node in an included file.
	 *  This feature is necessary because XML parsers will only accept content with a single root node.</p>
	 *  <p>An example:</p>
	 *  <pre>  &lt;_-.>
	 *    &lt;One/>
	 *    &lt;Two/>
	 *    &lt;Three/>
	 *  &lt;/_-.></pre>
	 *  <p>In the above example, &lt;_-.> will be correctly processed by XML,
	 *  but will be ignored by Xholon.
	 *  &lt;One/> &lt;Two/> and &lt;Three/> will become immediate children of
	 *  the parent of &lt;_-.> .</p>
	 *  <p>For better documentation, you may want to write the &lt;_-.> node as &lt;_-.forest></p>
	 *  <p>An example:</p>
	 *  <pre>  &lt;_-.forest>
	 *    &lt;One/>&lt;Two/>&lt;Three/>
	 *  &lt;/_-.forest></pre>
	 */
	public static final String XML_FOREST = "_-.";
	
	/**
	 * Indicate an uncertainty about how to create instances of a specific IXholonClass.
	 * Typically, use ASM to create a special subclass of the app's default Xh Java class,
	 * that can handle arbitrary port names.
	 * Additional characters may be included after the "__".
	 * This can be used in the class details (CD) content, for example:
	 * <pre>
	 * <abc implName="__sif"
	 * </pre>
	 */
	public static final String IMPLNAME_SPECIAL = "__";
	
	/**
	 * Set singleton TreeNodeFactory instance.
	 * @param factory The TreeNodeFactory instance.
	 */
	public abstract void setTreeNodeFactory(ITreeNodeFactory factory);

	/**
	 * Set parameter XInclude path.
	 * @param xiPath The path  ex: "./config/_common/" .
	 * */
	public abstract void setXincludePath(String xincludePath);

	/**
	 * Set the application node.
	 * @param app The application node.
	 */
	public abstract void setApp(IApplication app);

	/**
	 * Get parameter XInclude path.
	 * @return The path.
	 * */
	public abstract String getXincludePath();

	/**
	 * Get the XInclude prefix (example "xi").
	 * @return The XInclude prefix.
	 */
	public abstract String getXincludePrefix();

	/**
	 * Set the XInclude prefix (example xi).
	 * @param xincludePrefix The XInclude prefix.
	 */
	public abstract void setXincludePrefix(String xincludePrefix);

	/**
	 * Set InheritanceHierarchy instance.
	 * @param ih The InheritanceHierarchy instance.
	 */
	public abstract void setInheritanceHierarchy(IInheritanceHierarchy ih);

	/**
	 * Transform an XML String into a Xholon subtree.
	 * @param xmlString A String that contains XML.
	 * @param parentXholon The Xholon parent of the nodes that will be created from the XML String.
	 * @return The top level newly-created Xholon.
	 */
	public abstract IXholon xmlString2Xholon(String xmlString,
			IXholon parentXholon);
	
	/**
	 * Transform a non-XML String into a Xholon subtree.
	 * In fact, this method may support content type "text/xml".
	 * @param nonXmlString A String that contains some non-XML content.
	 * @param parentXholon The Xholon parent of the nodes that will be created from the non-XML String.
	 * @param contentType A MIME Type or Internet Media Type. ex: "application/json".
	 * @return The top level newly-created Xholon.
	 */
	public abstract IXholon nonXmlString2Xholon(String nonXmlString,
			IXholon parentXholon, String contentType);

	/**
	 * Transform an XML URI into a Xholon subtree.
	 * @param uri A URI, or a filename.
	 * @param parentXholon The Xholon parent of the nodes that will be created from the XML String.
	 * @return The top level newly-created Xholon.
	 */
	public abstract IXholon xmlUri2Xholon(String uri, IXholon parentXholon);

	/**
	 * Transform an XML File into a Xholon subtree.
	 * @param fileName The name of a file that contains XML.
	 * @param parentXholon The Xholon parent of the nodes that will be created from the XML String.
	 * @return The top level newly-created Xholon.
	 */
	public abstract IXholon xmlFile2Xholon(String fileName, IXholon parentXholon);

	/**
	 * Transform Apache Commons Virtual File System (VFS) content into a Xholon subtree.
	 * @param uri A URI in VFS format.
	 * @param parentXholon The Xholon parent of the nodes that will be created from the XML String.
	 * @return The top level newly-created Xholon.
	 */
	public abstract IXholon xmlVfs2Xholon(String uri, IXholon parentXholon);
	
	public abstract IXholon xmlGwtresource2Xholon(String resourceName, IXholon parentXholon);
	
	public abstract String xmlGwtresource2String(String resourceName);
	
	/**
	 * Recursively parses the stream provided by the XML Reader.
	 * Creates a XholonClass sub-tree from the stream.
	 * @param parentXholon A parent XholonClass.
	 * @param xmlReader The XML Reader.
	 * @param eventType An XML Reader event type.
	 * @return A newly created XholonClass sub-tree.
	 */
	public abstract IXholon xml2Xh(IXholon parentXholon, IXmlReader xmlReader, int eventType);
}

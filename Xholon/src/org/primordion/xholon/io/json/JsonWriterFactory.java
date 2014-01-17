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

package org.primordion.xholon.io.json;

//import java.io.Writer;

import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.io.xml.IXmlWriter;

/**
 * This factory creates new instances of XmlWriter (JsonStaxWriter or JsonStaxWriter_jackson).
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on May 3, 2010)
 */
public class JsonWriterFactory extends Xholon {
	
	/** The singleton instance of this class. */
	private static JsonWriterFactory jsonWriterFactory = null;
	
	/** Name of third-party Stax writer class (ex: jettison or jackson). */
	//private String thirdPartyStaxWriter = "org.codehaus.jettison.mapped.MappedXMLStreamWriter";
	private String thirdPartyStaxWriter = "com.fasterxml.jackson.core.JsonGenerator";
	
	/** Name of Xholon Stax writer class. */
	//private String xholonStaxWriter = "org.primordion.xholon.io.json.JsonStaxWriter";
	//private String xholonStaxWriter = "org.primordion.xholon.io.json.JsonStaxWriter_jackson";
	private String xholonStaxWriter = "org.primordion.xholon.io.json.JsonStrWriter";
	
	/**
	 * Get a concrete instance of XMLWriter.
	 * @param out An output file, stream, string, or other type of writer.
	 * @return An instance of XMLWriter, or null.
	 */
	public static IXmlWriter getXmlWriter(Object out)
	{
		IXmlWriter xmlWriter = null;
		if (jsonWriterFactory == null) {
			jsonWriterFactory = new JsonWriterFactory();
		}
		// try to make use of a JSON StAX stream writer
		if (xmlWriter == null) {
			xmlWriter = jsonWriterFactory.newStaxInstance();
			if (xmlWriter != null) {
				xmlWriter.createNew(out);
			}
		}
		if (xmlWriter == null) {
			// nothing more to try
		}
		return xmlWriter;
	}
	
	/**
	 * Create a new instance of a Stax writer.
	 * @return A new instance, or null.
	 */
	@SuppressWarnings("unchecked")
	protected IXmlWriter newStaxInstance() {
		/* Java SE code
		if (!exists(thirdPartyStaxWriter)) {return null;}
  	Class clazz = null;
    try {
        clazz = Class.forName(xholonStaxWriter, false,
        		getClass().getClassLoader());
    } catch (ClassNotFoundException e) {
    	getLogger().warn("Unable to find Stax XML writer.");
        return null;
    }
    try {
        if (clazz != null) {
        	return (IXmlWriter)clazz.newInstance();
        }
    } catch (IllegalAccessException e) {
    	getLogger().warn("Illegal access trying to instantiate Stax JSON writer.");
        return null;
    } catch (InstantiationException e) {
    	getLogger().warn("Unable to instantiate Stax JSON writer.");
        return null;
    }
    return null;*/
    
    // GWT code
    return new JsonStrWriter();
  }
	
	/**
	 * Do the Reader classes exist?
	 * @param classLoader A Java ClassLoader.
	 * @return true or false
	 */
	public boolean exists(String className)
	{
		/*try {
			getClass().getClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			return false;
		}*/
		return true;
	}
	
}

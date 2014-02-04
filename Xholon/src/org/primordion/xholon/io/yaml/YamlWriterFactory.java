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

package org.primordion.xholon.io.yaml;

//import java.io.Writer;

import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.io.xml.IXmlWriter;

/**
 * This factory creates new instances of XmlWriter (YamlStaxWriter).
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on May 4, 2010)
 */
public class YamlWriterFactory extends Xholon {
	
	/** The singleton instance of this class. */
	private static YamlWriterFactory yamlWriterFactory = null;
	
	//private static final String STR_WRITER_XHOLON = "org.primordion.xholon.io.yaml.YamlStrWriter";	
	//private static final String STAX_WRITER_XHOLON = "org.primordion.xholon.io.yaml.YamlStaxWriter";
	//private static final String STAX_WRITER_JACKSON = "org.primordion.xholon.io.yaml.YamlStaxWriter_jackson";
	
	/** Name of third-party Stax writer class (ex: Xholon or jackson). */
	//private String thirdPartyStaxWriter = "com.fasterxml.jackson.dataformat.yaml.YAMLFactory";
	
	/** Name of Xholon Stax writer class. */
	//private String xholonStaxWriter = STR_WRITER_XHOLON; //STAX_WRITER_JACKSON;
	
	/**
	 * Get a concrete instance of XMLWriter.
	 * @param out An output file, stream, string, or other type of Object.
	 * @return An instance of XMLWriter, or null.
	 */
	public static IXmlWriter getXmlWriter(Object out)
	{
		IXmlWriter xmlWriter = null;
		if (yamlWriterFactory == null) {
			yamlWriterFactory = new YamlWriterFactory();
		}
		// try to make use of a YAML StAX stream writer
		if (xmlWriter == null) {
			xmlWriter = yamlWriterFactory.newStaxInstance();
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
	protected IXmlWriter newStaxInstance() {
	  /* Java SE version
		if ((xholonStaxWriter.equals(STAX_WRITER_JACKSON)) && !exists(thirdPartyStaxWriter)) {
			xholonStaxWriter = STAX_WRITER_XHOLON;
		}
  	Class clazz = null;
    try {
        clazz = Class.forName(xholonStaxWriter, false,
        		getClass().getClassLoader());
    } catch (ClassNotFoundException e) {
    	getLogger().warn("Unable to find Stax YAML writer.");
        return null;
    }
    try {
        if (clazz != null) {
        	return (IXmlWriter)clazz.newInstance();
        }
    } catch (IllegalAccessException e) {
    	getLogger().warn("Illegal access trying to instantiate Stax YAML writer.");
        return null;
    } catch (InstantiationException e) {
    	getLogger().warn("Unable to instantiate Stax YAML writer.");
        return null;
    }
    return null;*/
    return new YamlStrWriter();
  }
	
	/**
	 * Do the Writer classes exist?
	 * @param classLoader A Java ClassLoader.
	 * @return true or false
	 */
	public boolean exists(String className)
	{
		/* GWT
		try {
			getClass().getClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			return false;
		}*/
		return true;
	}
	
}

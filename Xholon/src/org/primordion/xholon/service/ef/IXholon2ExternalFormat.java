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

package org.primordion.xholon.service.ef;

import org.primordion.xholon.base.IXholon;

/**
 * Export a Xholon model in an external format,
 * such as one of the formats supported by the Network Workbench (NWB).
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on February 12, 2010)
 */
public interface IXholon2ExternalFormat {
	
	/**
	 * The class that writes the external format, should write it to a String rather than to a file.
	 */
	public static final String STRING_WRITER = "stringwriter";
	
	/**
	 * Get the parameters for this external format, as a JSON string.
	 * @return a JSON string, or null.
	 */
	public abstract String getEfParamsAsJsonString();
	
	/**
   * Set one or more parameters for this external format.
   * The parameter must already exist, and cannot be a function.
   * @param jsonStr one or more name/value pairs in JSON format
   *  ex: {"one":"two","three":true,"four":1234}
   */
  public abstract void setEfParamsFromJsonString(String jsonStr);
	
	/**
	 * Allow user to change the values of options for this external format writer.
	 * @param outFileName Name of the output file.
	 * @param modelName Name of the model.
	 * @param root Root of the hierarchy to write out (composite or inheritance structure).
	 * @param formatName ex: "Graphviz"
	 */
	public abstract void adjustOptions(String outFileName, String modelName, IXholon root, String formatName);
	
	/**
	 * Is this external format writer capable of allowing the user to change the values of options.
	 * @return true or false
	 */
	public abstract boolean canAdjustOptions();
	
	/**
	 * Initialize.
	 * @param outFileName Name of the output file.
	 * @param modelName Name of the model.
	 * @param root Root of the hierarchy to write out (composite or inheritance structure).
	 * @return Whether or not the initialization succeeded.
	 */
	public abstract boolean initialize(String outFileName, String modelName, IXholon root);

	/**
	 * Write out all parts of the external-format file.
	 */
	public abstract void writeAll();
}

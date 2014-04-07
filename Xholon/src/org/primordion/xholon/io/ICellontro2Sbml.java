/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2007, 2008 Ken Webb
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

package org.primordion.xholon.io;

import org.primordion.xholon.base.IXholon;

/**
 * Transforms a Cellontro model to SBML format.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.5 (Created on February 17, 2007)
 */
public interface ICellontro2Sbml {

	/**
	 * Initialize.
	 * @param sbmlFileName Name of the output SBML file.
	 * @param modelName Name of the model.
	 * @param root Root of the composite structure hierarchy to write out.
	 * @return Whether or not the initialization succeeded.
	 *   If it failed, it is probably because this is not a Cellontro application.
	 */
	public abstract boolean initialize(String sbmlFileName, String modelName, IXholon root);

	/**
	 * Write out all parts of the SBML file.
	 */
	public abstract void writeAll();

}
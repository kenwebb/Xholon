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
 * Any node in an executing Xholon model can generate its own GUI.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.7.1 (Created on October 21, 2007)
 */
public interface IXholon2Gui {
	
	// various types of GUI output are possible
	public static final int GUI_TYPE_NONE           = 0;
	public static final int GUI_TYPE_TREE           = 1;
	public static final int GUI_TYPE_CONTOUR        = 2;
	public static final int GUI_TYPE_ROOTONLY       = 3;
	public static final int GUI_TYPE_DOMAINSPECIFIC = 4;
	
	/**
	 * Initialize.
	 * @param out Name of the output GUI file, or an instance of Writer, or another type of output object.
	 * @param modelName Name of the model.
	 * @param root Root of the hierarchy to write out (composite or inheritance structure).
	 * @return Whether or not the initialization succeeded.
	 */
	public abstract boolean initialize(Object out, String modelName, IXholon root);
	
	/**
	 * Set various run time parameters for generating and launching a GUI.
	 * @param params A String containing a comma delimited series of parameters.
	 * The parameters, in their proper order, are:
	 * <p>guiPathName guiType widthInPixels webSite shouldShowDescription shouldShowStateMachineEntities shouldLaunchGui</p>
	 * <p>ex: ./gui/,1,200,file:///C:/Xholon/gui/,true,false,true</p>
	 * <p>ex: /jetty-6.1.5/webapps/xholon/,2,200,http://localhost:8080/xholon/,false,false,true</p>
	 */
	public void setParams(String params);
	
	/**
	 * Write out all parts of the GUI file.
	 */
	public abstract void writeAll();
}

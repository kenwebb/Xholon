/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2013 Ken Webb
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
 * A class that contains all data required for the viewing of a grid.
 * This enables the creation of multiple grid viewers within the same application.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @since 0.5 (Created on February 14, 2007) as an inner class of app.Application
 * @since 0.9.0 (Created on July 13, 2013) as a separate class within io
 */
public class GridViewerDetails {
	// Grid parameters, read in from _xhn.xml
	/** Whether to use a graphical grid viewer. */
	public boolean useGridViewer = false;
	/** Name of the GridViewer Panel Java class. */
	public String gridPanelClassName = null;
	/** Grid viewer parameters. */
	public String gridViewerParams
		= "descendant::Row/..,5,Grid Viewer App";
	// Other Grid variables
	public IGridFrame gridFrame = null;  // frame for viewing grid as 2D color graphic
	public IGridPanel gridPanel = null; // grid for 2D viewing
	public IXholon gridOwner = null;    // Xholon that owns the grid
}

/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2012 Ken Webb
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

package org.primordion.ef.xholon;

import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Export a Xholon model in XHN format.
 * It writes out the contents of the original xhn.xml file.
 * These are the application parameters for the model.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on October 23, 2012)
 */
@SuppressWarnings("serial")
public class Xholon2Xhn extends DocRequester implements IXholon2ExternalFormat {

	/*
	 * @see org.primordion.ef.IXholon2ExternalFormat#writeAll()
	 */
	public void writeAll() {
	  writeAll(root.getApp().getConfigFileName(), "_xhn");
	}

}

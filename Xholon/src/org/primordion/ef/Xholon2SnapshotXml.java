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

package org.primordion.ef;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.io.Snapshot;
import org.primordion.xholon.io.SnapshotXML;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Export a Xholon model in Snapshot XML format.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on September 29, 2012)
 */
@SuppressWarnings("serial")
public class Xholon2SnapshotXml extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {

	private String outPath = "./ef/snapshot/";
	private String modelName;
	private IXholon root;
	private Snapshot snapshot;
	
	@Override
	public String getVal_String() {
	  return snapshot.getVal_String();
	}
	
	/*
	 * @see org.primordion.ef.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
	 */
	public boolean initialize(String outFileName, String modelName, IXholon root) {
		this.modelName = modelName;
		this.root = root;
		return true;
	}

	/*
	 * @see org.primordion.ef.IXholon2ExternalFormat#writeAll()
	 */
	public void writeAll() {
		snapshot = new SnapshotXML();
		Snapshot.setOutputPathGwtTabName(outPath);
		snapshot.saveSnapshot(root, modelName);
	}

}

/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2014 Ken Webb
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

package org.primordion.ef.bitmap;

import java.util.Date;

import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.io.IGridPanel;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.NodeSelectionService;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Export a GridPanel canvas in PNG format.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on January 2, 2014)
 */
@SuppressWarnings("serial")
public class Xholon2Png4Canvas extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
  
  protected String outFileName;
  protected String outFileNameExt = ".png";
	protected String outPath = "./ef/bitmap/";
	protected String modelName;
	protected IXholon root;
	
	/** Current date and time. */
	protected Date timeNow;
	protected long timeStamp;
	
	protected String type = "image/png";
	
  public Xholon2Png4Canvas() {}
  
  @Override
  public boolean initialize(String outFileName, String modelName, IXholon root) {
    timeNow = new Date();
		timeStamp = timeNow.getTime();
		if (outFileName == null) {
			this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + outFileNameExt;
		}
		else {
			this.outFileName = outFileName;
		}
		this.modelName = modelName;
		this.root = root;
    return true;
  }
  
  @Override
  public void writeAll() {
    IXholon nodeSelectionService = root.getService(IXholonService.XHSRV_NODE_SELECTION);
    if (nodeSelectionService != null) {
      IMessage msg = nodeSelectionService.sendSyncMessage(
          NodeSelectionService.SIG_GET_BUTTON3_GRIDPANEL_REQ, null, this);
      IGridPanel gridPanel = (IGridPanel)msg.getData();
      if (gridPanel != null) {
        String dataUrl = gridPanel.toDataUrl(type);
        writeToTarget(dataUrl, outFileName, outPath, root);
      }
    }
  }
  
}

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

package org.primordion.xholon.service.timeline;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Xholon;
import org.primordion.xholon.io.XholonGwtTabPanelHelper;

/**
 * <p>This is the abstract superclass for concrete classes that capture data
 * and display the data in a timeline.</p>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.0 (Created on January 5, 2014)
 */
@SuppressWarnings("serial")
public abstract class AbstractTimelineViewer extends Xholon implements ITimelineViewer {
  
  private boolean writeToTab = true;
  
  /**
   * Write the entire external-format text to an appropriate target.
   * @param efText The external-format text.
   * @param uri A file name, or a GWT-usable tooltip.
   * @param outPath A file system path name, or GWT-usable content type (ex: "_xhn"), or equivalent.
   * @param root The root node of the subtree being written out.
   */
  protected void writeToTarget(String efText, String uri, String outPath, IXholon root) {
    if (root.getApp().isUseGwt()) {
      if (writeToTab) {
        XholonGwtTabPanelHelper.addTab(efText, outPath, uri, false);
      }
      else {
        root.println(efText);
      }
    }
    else {
      //writeToTargetFile(efText, uri, outPath, root);
    }
  }
  
}

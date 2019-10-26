/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2019 Ken Webb
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

import org.primordion.xholon.base.AbstractGrid;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.Xholon;

/**
 * A viewport into a grid.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on October 24, 2019)
 */
public class GridPanelViewport extends Xholon {
  
  /**
   * Constructor
   * @params (ex: "5,5,30,30") OR (ex: "true,0,1,13,37") OR (ex: "false,0,1,13,37")
   */
  public GridPanelViewport(String params) {
    if (params == null) {return;}
    params = params.trim();
    if (params.startsWith("true,")) {
      params = params.substring(5);
      this.enabled = true;
    }
    else if (params.startsWith("false,")) {
      // return; // ???
      params = params.substring(6);
      this.enabled = false;
    }
    String[] paramsArr = params.split(",");
    if (paramsArr.length != 4) {return;}
    for (int i = 0; i < 4; i++) {
      try {
        int param = Integer.parseInt(paramsArr[i]);
        switch (i) {
        case 0: this.minX = param; break;
        case 1: this.maxX = param; break;
        case 2: this.minY = param; break;
        case 3: this.maxY = param; break;
      }
      } catch(NumberFormatException e) {}
    }
  }
  
  protected boolean enabled = true;
  protected int minX = 0;
  protected int maxX = 20;
  protected int minY = 0;
  protected int maxY = 20;
  
  public void setEnabled(boolean enabled) {this.enabled = enabled;}
  public boolean isEnabled() {return this.enabled;}
  
  public void setMinX(int minX) {this.minX = minX;}
  public int getMinX() {return this.minX;}
  
  public void setMaxX(int maxX) {this.maxX = maxX;}
  public int getMaxX() {return this.maxX;}
  
  public void setMinY(int minY) {this.minY = minY;}
  public int getMinY() {return this.minY;}
  
  public void setMaxY(int maxY) {this.maxY = maxY;}
  public int getMaxY() {return this.maxY;}
  
  public AbstractGrid getUpperLeft(final AbstractGrid row) {
    AbstractGrid roww = row;
    for (int i = 0; i < minY; i++) {
      roww = (AbstractGrid)roww.getNextSibling();
    }
    AbstractGrid coll = (AbstractGrid)roww.getFirstChild();
    // TODO find first real column, as is done in GridPanel.java ?
    for (int j = 0; j < minX; j++) {
      coll = (AbstractGrid)coll.getNextSibling();
    }
    return coll;
  }
  
  public int getNumRows() {
    return maxY - minY + 1;
  }
  
  public int getNumCols() {
    return maxX - minX + 1;
  }
  
}

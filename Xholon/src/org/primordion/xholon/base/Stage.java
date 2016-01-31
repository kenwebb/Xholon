/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2016 Ken Webb
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

package org.primordion.xholon.base;

import org.primordion.xholon.app.IApplication;

/**
 * Stage as understood in Scratch and Snap.
  * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="https://scratch.mit.edu">Scratch website</a>
 * @see <a href="http://snap.berkeley.edu">Snap website</a>
 * @since 0.9.1 (Created on January 29, 2016)
 */
public class Stage extends XholonWithPorts {
  
  /**
   * The Stage object created in phosphorus.
   */
  protected Object phosStage = null;
  
  protected IApplication app = null;
  
  /**
   * Constructor.
   */
  public Stage() {}
  
  @Override
  public void postConfigure() {
    app = this.getApp();
    super.postConfigure();
  }
  
  @Override
  public void act() {
    if (phosStage == null) {
      phosStage = this.findPhosStage();
    }
    /*int cs = app.getControllerState();
    consoleLog(app.getControllerStateName());
    switch (cs) {
    case IControl.CS_INITIALIZED: break;
    case IControl.CS_RUNNING: break;
    case IControl.CS_STEPPING: break;
    case IControl.CS_PAUSED: break;
    case IControl.CS_STOPPED: break;
    default: break;
    }*/
    
    if (phosStage != null) {
      controlPhosStage(phosStage);
    }
    super.act();
  }
  
  protected native Object findPhosStage() /*-{
    return $wnd.stage;
  }-*/;
  
  /**
   * Toggle the phosphorus Stage between paused and running.
   */
  protected native void controlPhosStage(Object stage) /*-{
    if (stage.isRunning) {
      stage.pause();
    } else {
      stage.start();
    }
  }-*/;
  
}


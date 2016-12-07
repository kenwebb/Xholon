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

package org.primordion.xholon.mech.story;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonWithPorts;

/**
 * Scenes
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on Dec 5, 2016)
 */
public class Scenes extends XholonWithPorts {
  
  private String roleName = null;
  
  @Override
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
  
  @Override
  public String getRoleName() {
    return roleName;
  }
  
  @Override
  public void postConfigure() {
    this.scenesbehaviorPostConfigure();
    super.postConfigure();
  }
  
  protected native void scenesbehaviorPostConfigure() /*-{
    var $this = this;
    var numberScenes = function() {
      // 0    20   21   35   36   50   extra     more extra
      // 2460-2473 3251-325f 32b1-32bf 2474-249b 249c-24fe
      var scene = $this.first();
      var sceneNum = 0x2460;
      while (scene) {
        scene.role(String.fromCharCode(sceneNum) + " " + scene.role());
        scene = scene.next();
        switch (sceneNum) {
        case 0x2473:
          sceneNum = 0x3251;
          break;
        case 0x325f:
          sceneNum = 0x32b1;
          break;
        case 0x32bf:
          // no more circled numbers available; use other enclosed numbers
          sceneNum = 0x2474;
          break;
        case 0x24fe:
          // no more enclosed numbers or characters available
          scene = null;
          break;
        default:
          sceneNum++;
          break;
        }
      }
    }
    
    numberScenes();
  }-*/;

  @Override
  public String toString() {
    return this.getName();
  }
  
}

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
 * StorySystem
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on Dec 5, 2016)
 */
public class StorySystem extends XholonWithPorts {
  
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
    this.storySystembehaviorPostConfigure();
    super.postConfigure();
  }
  
  protected native void storySystembehaviorPostConfigure() /*-{
    $wnd.xh.param("AppM","true");
    
    // SVG caption
    $wnd.xh.svg = {};
    $wnd.xh.svg.caption = $doc.createElement("span");
    $wnd.xh.svg.caption.textContent = $wnd.xh.param("ModelName");

    $wnd.xh.svg.scenenum = $doc.createElement("span");
    $wnd.xh.svg.scenenum.textContent = "0";
    
    var div = $doc.querySelector("#xhanim");
    
    div.appendChild($wnd.xh.svg.scenenum);
    div.appendChild($wnd.xh.svg.caption);
    
    // create a new div for this animation
    var one = $doc.createElement("div");
    one.setAttribute("id", "one");
    div.appendChild(one);
    
    // Make a Place roleName, given a full Scene roleName.
    // The returned roleName is partly capitalized.
    // Any "'" characters in the roleName are removed, so the roleName can be used in an XPath expression.
    // TODO possibly return a String array that contains a hierarchy of names
    $wnd.xh.movie = {};
    $wnd.xh.movie.makePlaceRoleName = function(sceneName, startPos, timewordsXsn, returnXml) {
      if (sceneName.length == 0) {return "";}
      if (startPos == 0) {
        switch (sceneName.split(" ")[0]) {
        case "INT.":
        case "EXT.":
          sceneName = sceneName.substring(5);
          break;
        case "INT./EXT.":
        case "EXT./INT.":
          sceneName = sceneName.substring(10);
          break;
        default:
          break;  
        }
      }
      else {
        sceneName = sceneName.substring(startPos);
      }
      var arr = sceneName.split(" - ");
      var len = arr.length;
      var timeword = null;
      if (len > 1) {
        var maybeTimeword = arr[len-1].trim();
        if (timewordsXsn.contains(maybeTimeword)) {
          timeword = arr.pop();
        }
      }
      len = arr.length;
      var prn = "";
      
      if (returnXml) {
        // return a modified sceneName
        var modSceneName = "";
        for (var i = 0; i < len; i++) {
          if (i > 0) {
            modSceneName += " - ";
          }
          modSceneName += arr[i].trim().capitalize().replace(/'/g, '').replace(/\//g, ' ');
        }
        return modSceneName;
      }
      else {
        // return a partial XPath expression
        for (var i = 0; i < len; i++) {
          var roleName = arr[i].trim().capitalize().replace(/'/g, '').replace(/\//g, ' ');
          if (i > 0) {
            prn += "/";
          }
          prn += "SceneLocation[@roleName='" + roleName + "']";
        }
        return [prn, timeword];
      }
      
    }
  }-*/;

  @Override
  public String toString() {
    return this.getName();
  }
  
}

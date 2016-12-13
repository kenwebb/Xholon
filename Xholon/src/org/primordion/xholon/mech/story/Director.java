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
 * Director
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on Dec 5, 2016)
 */
public class Director extends XholonWithPorts {
  
  private String roleName = null;
  
  private String sceneLocationRoot = "Universe";
  
  /**
   * If speech is supported, multiply scene.duration by this number to accomodate the extra time that speech takes.
   */
  private double speechMultiplier = 1.0;

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
    this.directorbehaviorPostConfigure(sceneLocationRoot, speechMultiplier);
    super.postConfigure();
  }
  
  @Override
  public void act() {
    this.directorbehaviorAct();
    super.act();
  }
  
  protected native void directorbehaviorPostConfigure(String sceneLocationRoot, double speechMultiplier) /*-{
    this.avatarCache = null;
    
    this.countdown = 0;
    var $this = this;
    var scene = null;
    this.highlightSelStr = null;
    this.highlightCountdown = 0;
    this.timeword = null;
    var animate = this.xpath("../../Animate");
    
    this.cacheAvatars = function() {
      var people = $this.xpath("../Characters");
      var person = people.first();
      if (person) {
        $this.avatarCache = {};
        cacheAvatarsRecurse(person);
      }
    }

    var cacheAvatarsRecurse = function(node) {
      while (node) {
        if (node.first()) {
          cacheAvatarsRecurse(node.first());
        }
        else {
          rgba2rgbOpacity(node);
          $this.avatarCache[node.role()] = node;
        }
        node = node.next();
      }
    }
    
    // TODO does this function actually do anything useful ?
    var refreshCache = function() {
      // refresh the cache by iterating thru the existing object properties
      for (var prop in $this.avatarCache) {
        var pname = prop;
        var pval = $this.avatarCache[pname];
        if (typeof pval != "function") {
          var newPname = pval.role();
          if (pname != newPname) {
            // update the property in the object
            // delete avatarCache[pname]; // should I delete the existing item (it's safe to do this)?
            //  But someone might have multiple names?
            $this.avatarCache[newPname] = pval;
          }
        }
      }
    }

    var fadeCache = function() {
      // fade color/opacity of all Avatars in the cache by iterating thru the existing object properties
      for (var prop in $this.avatarCache) {
        var pname = prop;
        var pval = $this.avatarCache[pname];
        if (typeof pval != "function") {
          // fade the color or the opacity
          //pval.color($wnd.d3.rgb(pval.color()).brighter(0.2).toString());
          pval.opacity(pval.opacity() * 0.9);
        }
      }
    }

    this.newScene = function() {
      if (scene == null) {
        // point to the opening scene
        scene = $this.xpath("../Scenes/Scene");
      }
      else {
        if (scene.next() != null) {
          scene = scene.next();
        }
        else {
          // keep checking for a new next scene in case the user appends a new one
          return;
        }
      }
      if (scene) {
        $this.println(scene);
        $wnd.xh.svg.scenenum.textContent = scene.role().charAt(0);
        if (scene.encloses && animate) {
          animate.attr("AnimRoot", scene.encloses);
        }
        $this.countdown = 2; //scene.duration
        if (scene.duration) {
          // optionally multiply by some number to accomodate the extra time that speech takes
          $this.countdown = scene.duration * speechMultiplier;
        }
        fadeCache();
        var ascript = scene.first();
        while (ascript) {
          var a = $this.avatarCache[ascript.avatar];
          if (a) {
            movePerson(a, scene);
            a.action("param transcript false;");
            a.action("script;" + ascript.first().text());
            //a.color($wnd.d3.rgb(a.color()).darker(0.2).toString()); // doesn't work with rgba()
            rgba2rgbOpacity(a); // set color and opacity back to default values
          }
          else {
            refreshCache();
            // try again to get "a"
            a = $this.avatarCache[ascript.avatar];
            if (a) {
              a.action("param transcript false;");
              a.action("script;" + ascript.first().text());
            }
          }
          ascript = ascript.next();
        }
        ascript = scene.first();
        if (ascript) {
          var a = $this.avatarCache[ascript.avatar];
          if (a) {
            highlightCurrentPlace(a);
          }
        }
      }
    }

    // highlight the place that this person is currently inside of
    // just set things up; don't actually make the changes yet
    var highlightCurrentPlace = function(person) {
      var pplace = person.parent();
      var pplaceName = pplace.name().replace(/ /g, '\\ ').replace(/:/g, '\\:'); // escape invalid characters " " and ":"
      $this.highlightSelStr = "svg>g>g#" + pplaceName + ">circle";
      $this.highlightCountdown = 1; // delay one timestep to give people a chance to get to the new place
    }

    var movePerson = function(person, scene) {
      var sourceXhcName = person.parent().xhc().name();
      // note that first 2 chars of scene.role() are special char and space
      if (sourceXhcName == "Characters") {
        var prnArr = $wnd.xh.movie.makePlaceRoleName(scene.role().substring(2), 0, $wnd.xh.movie.timewordsXsn, false);
        var prn = prnArr[0];
        if (prnArr[1] != "SAME") {
          $this.timeword = prnArr[1];
        }
        // place double quotes around the xpathExpr, in case prn contains spaces
        var xpathExpr = "go " + '"' + "xpath(../" + sceneLocationRoot + "/" + prn + ")" + '"' + ";";
        person.action(xpathExpr);
      }
      else {
        // move from current place, to possibly a new place
        var prnArr = $wnd.xh.movie.makePlaceRoleName(scene.role().substring(2), 0, $wnd.xh.movie.timewordsXsn, false);
        var prn = prnArr[0];
        if (prnArr[1] != "SAME") {
          $this.timeword = prnArr[1];
        }
        if (prn != person.parent().role()) {
          //var xpathExpr = "go " + '"' + "xpath(../" + prn + ")" + '"' + ";";
          var xpathExpr = "go " + '"' + "xpath(ancestor::" + sceneLocationRoot + "/" + prn + ")" + '"' + ";";
          person.action(xpathExpr);
        }
      }
    }

    // rgba() to rgb() + opacity (for use in Movie Script Parser)
    // ex: rgba(20,220,60,1.0) becomes rgb(20,220,60) and 1.0
    var rgba2rgbOpacity = function(node) {
      var color = node.xhc().color(); //.parent().color();
      if ((color != null) && (color.substring(0,5) == "rgba(")) {
        var rgba = color.substring(5, color.length - 1).split(",");
        var rgb = "rgb(" + rgba[0] + "," + rgba[1] + "," + rgba[2] + ")";
        var a = rgba[3];
        node.color(rgb);
        node.opacity(a);
      }
    }
    
    this.cacheAvatars();
    if (this.avatarCache != null) {
      this.newScene();
    }
  }-*/;

  protected native void directorbehaviorAct() /*-{
    if (this.avatarCache == null) {
      this.cacheAvatars();
    }
    if (this.avatarCache == null) {
      return;
    }
    if (this.highlightSelStr != null) {
      if (this.highlightCountdown == 1) {
        this.highlightCountdown = 0;
      }
      else {
        var place = null;
        try {
          // handle Uncaught DOMException: Failed to execute 'querySelector' on 'Document': 'svg>g>g#(present)\ day\:sceneLocation_75>circle' is not a valid selector.
          place = $doc.querySelector(this.highlightSelStr);
        } catch (e) {
          // caught the DOMException
        }
        if (place) {
          place.style.strokeWidth = "2"; // make the stroke visible
          // "NIGHT,DAY,EVENING,MORNING,DAWN,DUSK,EARLY,LATE,CONTINUOUS,SAME,SATURDAY"
          switch (this.timeword) {
          case "DAY": place.style.stroke = "yellow"; break;
          case "NIGHT": place.style.stroke = "black"; break;
          case "EVENING": place.style.stroke = "purple"; break;
          case "MORNING": place.style.stroke = "pink"; break;
          case "DAWN": place.style.stroke = "pink"; break;
          case "DUSK": place.style.stroke = "purple"; break;
          // SATURDAY (7th day) has 7 dashes and spaces, followed by a dot and space
          case "SATURDAY": place.style.strokeDasharray = "5,5,5,5,5,5,5,5,5,5,5,5,5,5,1,5"; break;
          default: break;
          } // end switch
        } // end if
        this.highlightSelStr = null;
      } // end else
    } // end if
    if (--this.countdown <= 0) {
      this.newScene();
    }

  }-*/;
  
  @Override
  public int setAttributeVal(String attrName, String attrVal) {
    if ("sceneLocationRoot".equals(attrName)) {
      this.sceneLocationRoot = attrVal;
    }
    else if ("speechMultiplier".equals(attrName)) {
      this.speechMultiplier = Double.parseDouble(attrVal);
    }
    return 0;
  }

  @Override
  public String toString() {
    return this.getName();
  }
  
}

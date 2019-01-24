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

package org.primordion.xholon.script;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.io.gwt.HtmlScriptHelper;

/**
 * Contain and control multiple Animate nodes.
 * 
 * Examples:
 * <p><pre>
 &lt;AnimateMulti>&lt;Animate .../>&lt;Animate .../>&lt;/AnimateMulti>
 * </pre></p>
 * 
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on January 24, 2019)
 */
public class AnimateMulti extends XholonScript {
  
  private IApplication app = null;
  
  /** Whether or not the animation is paused. The act() method does nothing if the animate is paused. */
  private boolean animPaused = false;
  
  /** Whether or not the speed is currently being adjusted. */
  private boolean adjustingSpeed = false;
  
  /** previous slowest requestedTimeStepInterval from all Animate nodes */
  private int prevSlowestRequestedTsi = Integer.MAX_VALUE; // Integer.MAX_VALUE is the slowest possible speed
  
  /** default options for Animation nodes */
  private Object options = null;
  
  @Override
  public Object getVal_Object() {
    return options;
  }
  
  @Override
  public void setVal_Object(Object options) {
    this.options = options;
  }
  
  @Override
  public void postConfigure() {
    app = this.getApp();
    this.consoleLog(this);
    super.postConfigure();
  }
  
  @Override
  public void act() {
    super.act(); // run all the Animate nodes
    if (!animPaused) {
      // check all child Animate nodes for changes in their required speed
      // find the slowest speed
      IXholon node = this.getFirstChild();
      int slowest = 0;
      while (node != null) {
        if ("Animate".equals(node.getXhcName())) {
          int requestedTimeStepInterval = (int)node.getVal();
          if (requestedTimeStepInterval > slowest) {
            slowest = requestedTimeStepInterval;
          }
        }
        node = node.getNextSibling();
      }
      if (slowest != prevSlowestRequestedTsi) {
        this.setTimeStepInterval(slowest);
        this.prevSlowestRequestedTsi = slowest;
      }
    }
  }
  
  // TODO possibly allow actions for all Animate nodes to be set from here
  
	/** An action. Pause the animation. */
  private static final String pauseAnimation = "Pause animation";
	/** An action. Unpause the animation. */
  private static final String unpauseAnimation = "Unpause animation";
	/** An action. Toggle the animation betwteen paused and unpaused. */
  private static final String toggleAnimation = "Toggle animation";
  /** An action. Speed up the animation. */
  private static final String faster = "Faster";
  /** An action. Slow down the animation. */
  private static final String slower = "Slower";
  /** An action. Run the animation at its fstst speed. */
  private static final String fastest = "Fastest";
  /** An action. Run the animation at its slooooweeeest speed. */
  private static final String slowest = "Slowest";
  /** An action. Adjust the animation speed - slow when the model is changing, and fast when it's not. */
  private static final String adjustSpeed = "Adjust speed"; // TODO or "Adapt speed"
  
	/** Action list. */
  private String[] actions = {toggleAnimation, pauseAnimation, unpauseAnimation, faster, slower, fastest, slowest, adjustSpeed};
  
  @Override
  public String[] getActionList()
  {
    return actions;
  }
  
  @Override
  public void setActionList(String[] actionList)
  {
    actions = actionList;
  }
  
  @Override
  public void doAction(String action) {
    if (action == null) {return;}
    if (action.equals(toggleAnimation)) {
      this.animPaused = !this.animPaused;
    }
    if (action.equals(pauseAnimation)) {
      this.animPaused = true;
    }
    else if (action.equals(unpauseAnimation)) {
      this.animPaused = false;
    }
    else if (action.equals(faster)) {
      faster();
    }
    else if (action.equals(slower)) {
      slower();
    }
    else if (action.equals(fastest)) {
      fastest();
    }
    else if (action.equals(slowest)) {
      slowest();
    }
    else if (action.equals(adjustSpeed)) {
      adjustSpeed();
    }
  }
  
  protected void faster() {
    int tsi = getTimeStepInterval();
    if (tsi > 1000) {tsi = 1000;}
    else if (tsi > 100) {tsi = 100;}
    else if (tsi > 10) {tsi = 10;}
    else if (tsi > 1) {tsi = 1;}
    else {tsi = 0;}
    this.setTimeStepInterval(tsi);
  }
  
  protected void slower() {
    int tsi = getTimeStepInterval();
    if (tsi == 0) {tsi = 1;}
    else if (tsi < 10) {tsi = 10;}
    else if (tsi < 100) {tsi = 100;}
    else if (tsi < 1000) {tsi = 1000;}
    else if (tsi < 2000) {tsi = 2000;}
    else if (tsi < 5000) {tsi = 5000;}
    else if (tsi < 10000) {tsi = 10000;}
    this.setTimeStepInterval(tsi);
  }
  
  protected void fastest() {
    //this.setTimeStepInterval(fastestSpeed);
  }
  
  protected void slowest() {
    //this.setTimeStepInterval(slowestSpeed);
  }
  
  protected void adjustSpeed() {
    this.adjustingSpeed = !this.adjustingSpeed;
  }
  
  // TODO possibly retain all the setAttributeVal() possibilities, and have them set that attr for all contained Animate nodes
  
  @Override
  public int setAttributeVal(String attrName, String attrVal) {
    if ("animPaused".equals(attrName)) {
      this.animPaused = Boolean.parseBoolean(attrVal);
    }
    else if ("options".equals(attrName)) {
      this.options = this.parseJsonStr(attrVal);
    }
    /*if ("formatName".equals(attrName)) {
      //this.formatName = attrVal;
    }
    else if ("xpath".equals(attrName)) {
      //this.xpath = attrVal;
    }
    else if ("efParams".equals(attrName)) {
      //this.efParams = attrVal;
    }
    else if ("writeToTab".equals(attrName)) {
      //this.writeToTab = Boolean.parseBoolean(attrVal);
    }
    else if ("selection".equals(attrName)) {
      //this.selection = attrVal;
    }
    else if ("duration".equals(attrName)) {
      //this.duration = Double.parseDouble(attrVal);
    }
    else if ("cssStyle".equals(attrName)) {
      //this.cssStyle = attrVal;
    }
    else if ("tweenScript".equals(attrName)) {
      //this.tweenScript = attrVal;
    }
    else if ("animPaused".equals(attrName)) {
      this.animPaused = Boolean.parseBoolean(attrVal);
    }
    else if ("fastestSpeed".equals(attrName)) {
      //this.fastestSpeed = Integer.parseInt(attrVal);
    }
    else if ("slowestSpeed".equals(attrName)) {
      //this.slowestSpeed = Integer.parseInt(attrVal);
    }
    else if ("adjustingSpeed".equals(attrName)) {
      //this.adjustingSpeed = Boolean.parseBoolean(attrVal);
    }*/
    return 0;
  }
  
  protected void setTimeStepInterval(int tsi) {
    app.setParam("TimeStepInterval", Integer.toString(tsi));
  }
  
  protected int getTimeStepInterval() {
    return Integer.parseInt(app.getParam("TimeStepInterval"));
  }
  
  protected native Object parseJsonStr(String jsonStr) /*-{
    return $wnd.JSON.parse(jsonStr);
  }-*/;
  
}

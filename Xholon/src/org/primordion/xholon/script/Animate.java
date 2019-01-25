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

package org.primordion.xholon.script;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.io.gwt.HtmlScriptHelper;

/**
 * Animate a Xholon subtree using D3 Circle Pack.
 * I should be able to paste this into any Xholon app with a CSH that changes,
 * for example the simple Generic app that adds a new node each time step.
 * 
 * Examples:
 * <p><pre>&lt;Animate/></pre></p>
 * <p><pre>&lt;Animate xpath="./*" efParams='{"sort":"disable","width":600,"height":600,"mode":"new"}'/></pre></p>
 * <p><pre>&lt;Animate duration="1.234" efParams='{"sort":"disable","width":200,"height":200,"mode":"new"}'/></pre></p>
 * <p><pre>&lt;Animate xpath="" selection="div#xhsvg" writeToTab="true" duration="2" efParams='{"selection":"div#xhsvg","sort":"disable","width":200,"height":200,"mode":"new"}'/></pre></p>
 * <p><pre>&lt;Animate cssStyle=".d3cpnode circle {stroke-width: 0px;}" xpath="" selection="div#xhsvg" writeToTab="false" duration="5" efParams='{"selection":"div#xhsvg","sort":"disable","width":200,"height":200,"mode":"tween"}'/></pre></p>
 * 
 * TODO the Animate instance should move itself to Xholon View; but it needs to run every timestep
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on November 22, 2014)
 */
public class Animate extends XholonScript {
  private static final int SPEED_FASTEST =    0; // shortest TimeStepInterval = fastest speed
  private static final int SPEED_SLOWEST = 1000; // longest TimeStepInterval = slowest speed
  
  /** The name of the external format (ef) */
  private String formatName = "_d3,CirclePack";
  
  /** Parameters that will be passed to the ef exporter */
  private String efParams = "{\"shouldIncludeDecorations\":true,\"sort\":\"disable\",\"width\":600,\"height\":600,\"mode\":\"tween\",\"includeId\":true}";
  
  /** Whether or not to write the generated ef-specific code to a Xholon tab */
  private boolean writeToTab = false;
  
  /** CSS selection for parent of the SVG content */
  private String selection = "#xhanim";
  
  /** Tween duration in seconds */
  private double duration = 2;
  
  /** Optional style tag that should be added to HTML head */
  //$wnd.xh.css.style(".d3cpnode circle {stroke-width: 0px;}");
  private String cssStyle = null;
  
  private IApplication app = null;
  
  /** An XPath expression to get from the app's CSH root node to the animation root node (xhAnimRoot) */
  private String xpath = ".";
  
  /** The root node of the Xholon subtree that is being animated, in this animation */
  private IXholon xhAnimRoot = null;
  
  /** Hash value from the previous call to xhAnimRoot.hashify() */
  private String hash = "";
  
  /** Name of the JavaScript .js file that does the tweening */
  private String tweenScript = "xhSvgTween";
  
  /** Whether or not the animation is paused. The act() method does nothing if the animate is paused. */
  private boolean animPaused = false;
  
  private int fastestSpeed = SPEED_FASTEST;
  private int slowestSpeed = SPEED_SLOWEST;
  
  /** Whether or not the speed is currently being adjusted. */
  private boolean adjustingSpeed = false;
  /** For use if adjustingSpeed == true */
  private int currentSpeed = slowestSpeed;
  
  /** for use when parent is an AnimateMulti. */
  private int requestedTimeStepInterval = -1;
  
  /** Whether or not this Animate node's parent is an AnimateMulti. */
  private boolean parentAnimateMulti = false;
  
  @Override
  public double getVal() {
    return requestedTimeStepInterval;
  }
  
  @Override
  public void setVal(double aval) {
    requestedTimeStepInterval = (int)aval;
  }
  
  /*
   * @see org.primordion.xholon.base.Xholon#postConfigure()
   */
  public void postConfigure() {
    HtmlScriptHelper.requireScript(tweenScript, null);
    app = this.getApp();
    xhAnimRoot = app.getRoot(); // default
    setAnimRoot(xpath);
    if ("AnimateMulti".equals(this.getParentNode().getXhcName())) {
      parentAnimateMulti = true;
    }
    // align the tween duration with the Xholon TimeStepInterval
    this.setTimeStepInterval((int)(duration * 1000.0));
    if (cssStyle != null) {
      style(cssStyle);
    }
    super.postConfigure();
  }
  
  /*
   * @see org.primordion.xholon.base.Xholon#act()
   */
  public void act() {
    if (!animPaused) {
      String newHash = xhAnimRoot.hashify(null); // use default hash type
      if (hash.equals(newHash)) {
        // the tree structure of the model has NOT changed, so the model and animation can run at the fastest speed
        if (this.adjustingSpeed && (this.currentSpeed == this.slowestSpeed)) {
          this.currentSpeed = this.fastestSpeed;
          this.setTimeStepInterval(this.fastestSpeed);
        }
      }
      else {
        // the tree structure of the model HAS changed, so the model and animation should run at the slowest speed
        if (this.adjustingSpeed && (this.currentSpeed == this.fastestSpeed)) {
          this.currentSpeed = this.slowestSpeed;
          this.setTimeStepInterval(this.slowestSpeed);
        }
        xport(formatName, xhAnimRoot, efParams, writeToTab);
        tween(selection, duration); // TODO duration never changes
        hash = newHash;
      }
    }
    super.act();
  }
  
  /**
   * Here's what a typical call to xh.xport() with XholonJsApi.java looks like:
   * $wnd.xh.xport('_d3,CirclePack', root.first(),
   *  '{"shouldIncludeDecorations":true,"sort":"disable","width":600,"height":600,"mode":"tween"}',false);
   */
  protected native void xport(String formatName, IXholon xhAnimRoot, String efParams, boolean writeToTab) /*-{
    $wnd.xh.xport(formatName, xhAnimRoot, efParams, writeToTab);
  }-*/;
  
  protected native void tween(String selection, double duration) /*-{
    if ($wnd.xh.tween) {
      $wnd.xh.tween(selection, duration);
    }
  }-*/;
  
  protected native void style(String cssStyle) /*-{
    $wnd.xh.css.style(cssStyle);
  }-*/;
  
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
    this.setTimeStepInterval(fastestSpeed);
  }
  
  protected void slowest() {
    this.setTimeStepInterval(slowestSpeed);
  }
  
  protected void adjustSpeed() {
    this.adjustingSpeed = !this.adjustingSpeed;
  }
  
  /*
   * @see org.primordion.xholon.base.Xholon#setAttributeVal(java.lang.String, java.lang.String)
   */
  public int setAttributeVal(String attrName, String attrVal) {
    if ("formatName".equals(attrName)) {
      this.formatName = attrVal;
    }
    else if ("xpath".equals(attrName)) {
      this.xpath = attrVal;
    }
    else if ("efParams".equals(attrName)) {
      this.efParams = attrVal;
    }
    else if ("writeToTab".equals(attrName)) {
      this.writeToTab = Boolean.parseBoolean(attrVal);
    }
    else if ("selection".equals(attrName)) {
      this.selection = attrVal;
    }
    else if ("duration".equals(attrName)) {
      this.duration = Double.parseDouble(attrVal);
    }
    else if ("cssStyle".equals(attrName)) {
      this.cssStyle = attrVal;
    }
    else if ("tweenScript".equals(attrName)) {
      this.tweenScript = attrVal;
    }
    else if ("animPaused".equals(attrName)) {
      this.animPaused = Boolean.parseBoolean(attrVal);
    }
    else if ("fastestSpeed".equals(attrName)) {
      this.fastestSpeed = Integer.parseInt(attrVal);
    }
    else if ("slowestSpeed".equals(attrName)) {
      this.slowestSpeed = Integer.parseInt(attrVal);
    }
    else if ("adjustingSpeed".equals(attrName)) {
      this.adjustingSpeed = Boolean.parseBoolean(attrVal);
    }
    return 0;
  }
  
  public void setAnimRoot(String xpath) {
    if (xpath == null) {return;}
    this.xpath = xpath;
    if (xpath.length() != 0) {
      this.xhAnimRoot = this.getXPath().evaluate(xpath, app.getRoot());
    }
  }
  
  public String getAnimRoot() {
    return xpath;
  }
  
  protected void setTimeStepInterval(int tsi) {
    if (this.parentAnimateMulti) {
      requestedTimeStepInterval = tsi;
    }
    else {
      app.setParam("TimeStepInterval", Integer.toString(tsi));
    }
  }
  
  protected int getTimeStepInterval() {
    return Integer.parseInt(app.getParam("TimeStepInterval"));
  }
  
}

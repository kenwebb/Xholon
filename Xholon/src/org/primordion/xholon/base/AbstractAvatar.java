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

import com.google.gwt.dom.client.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IControl;

/**
 * Abstract base class for Avatar classes.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on January 15, 2015)
 */
public abstract class AbstractAvatar extends XholonWithPorts {
  
  public String roleName = null;
  
  protected double val;
  
  protected String nameTemplate = IXholon.GETNAME_DEFAULT;
  
  protected IApplication app = null;
  
  @Override
  public void setRoleName(String roleName) {this.roleName = roleName;}
  @Override
  public String getRoleName() {return roleName;}
  
  @Override
  public void setVal(double val) {this.val = val;}
  @Override
  public double getVal() {return val;}
  
  @Override
  public void incVal(double incAmount) {val += incAmount;}
  @Override
  public void decVal(double decAmount) {val -= decAmount;}
  
  @Override
  @SuppressWarnings("unchecked")
  public List getAllPorts() {
    List<PortInformation> xhcPortList = this.getXhc().getPortInformation();
    if ((xhcPortList == null) || (xhcPortList.size() == 0)) {
      return super.getAllPorts(); // calls Xholon.getAllPorts()
    }
    List<PortInformation> realPortList = new ArrayList<PortInformation>();
    // eval the XPath expressions to determine the reffed nodes
    Iterator<PortInformation> portIt = xhcPortList.iterator();
    while (portIt.hasNext()) {
      PortInformation pi = (PortInformation)portIt.next();
      IXholon reffedNode = this.getXPath().evaluate(pi.getXpathExpression().trim(), this);
      if (reffedNode == null) {
        //xhcPortList.remove(pi); // causes java.util.ConcurrentModificationException
      }
      else {
        pi.setReffedNode(reffedNode);
        realPortList.add(pi);
      }
    }
    return realPortList;
  }
  
  protected String join(String[] arr, String sep) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < arr.length; i++) {
      if (i > 0) {
        sb.append(sep);
      }
      sb.append(arr[i]);
    }
    return sb.toString();
  }
  
  /**
   * Split a String into an array of substrings.
   * Split on spaces, unless between quotes.
   * ex: put "Sir William" in Coach  ->  ["put","Sir William","in","Coach"]
   * ex: one two "three four five" six  >  ["one", "two", "three four five", "six"]
   * @param str The string that will be split.
   * @param limit The maximum length of the resulting array.
   * @return An array of strings
   */
  protected native String[] split(String str, int limit) /*-{
    var result = str.match(/("[^"]+"|[^"\s]+)/g);
    // remove starting and end quotes in result
    for (var i = 0; i < result.length; i++) {
      if (result[i].charAt(0) == '"') {
        result[i] = result[i].substring(1, result[i].length-1);
      }
    }
    if (result.length > limit) {
      // limit number of entries in the result
      var last = result[limit-1];
      for (var j = limit; j < result.length; j++) {
        last += " " + result[j];
      }
      result[limit-1] = last;
      result.length = limit;
    }
    return result;
  }-*/;
  
  protected native String escapeDoubleQuotes(String str) /*-{
    return str.replace(/"/g, '\\"');
  }-*/;
  
  protected native String unescapeDoubleQuotes(String str) /*-{
    return str.replace(/\\"/g, '"');
  }-*/;
  
  /**
   * Pause the app at the current Xholon timestep.
   * It's similar to a software breakpoint, but not as immediate.
   * Any actions remaining in this timestep will first be completed.
   * A timestep is equivalent to one line in a Avatar script,
   * possibly one line in each of the scripts of two or more simultaneously-active Avatars,
   * or other nodes with simultaneously-active Xholon.act() methods.
   */
  protected void breakpoint() {
    if (app.getControllerState() == IControl.CS_PAUSED) {
      // unpause
      app.setControllerState(IControl.CS_RUNNING); // 3
    }
    else {
      // pause
      app.setControllerState(IControl.CS_PAUSED); // 4
    }
  }
  
  protected void start() {
    app.setControllerState(IControl.CS_RUNNING); // 3
  }
  
  protected void step() {
    app.setControllerState(IControl.CS_STEPPING); // 5
  }
  
  /**
   * Query for a DOM Element, given a selector.
   * ex: var captionEle = document.querySelector("#xhanim>#one>p");
   * @param selector - a CSS selector (ex: "#xhanim>#one>p")
   */
  protected native Element querySelector(String selector) /*-{
    return $doc.querySelector(selector);
  }-*/;
  
  /**
   * Make a node name.
   * @param node 
   * @return 
   */
  protected String makeNodeName(IXholon node) {
    return node.getName(nameTemplate);
  }
  
}

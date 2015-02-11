/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2015 Ken Webb
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

package org.primordion.ef.other;

import java.util.Date;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Export a Xholon model in web browser console format.
 * For example, using $wnd.console.log() .
 * 
 * In Chrome, if I export "node",
 *   then in Developer Tools (DT) I can right-click the node and select "Store as Global Variable".
 *  Chrome DT places the new global variable in the console.
 *  I can then do things like:
 *   temp1.name();
 *   temp2.portNames();
 *   temp2.port(0);
 *   temp2.port(0).name();
 *   temp2.port(3).val();
 *   temp2.port(3).val(10000.0);
 * 
 * In Firefox, if I export "node",
 *   then in Firebug (FB) I can right-click the node and select "Use in Command Line".
 *  Firefox FB places a new variable in the console.
 *  I can then do things like:
 *   $p.name();
 *  Or right-click the node and select "Inspect in DOM Panel".
 *
 * I can also use Firefox Web Developer.
 *
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on February 10, 2015)
 * @see <a href="https://developer.chrome.com/devtools/docs/console-api">Chrome</a>
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/console">Firefox</a>
 */
@SuppressWarnings("serial")
public class Xholon2BrowserConsole extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
  
  private String outFileName;
  private String outPath = "./ef/console/";
  private String modelName;
  private IXholon root;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  /** Whether or not to show state machine nodes. */
  private boolean shouldShowStateMachineEntities = false;
  
  /** Template to use when writing out node names. */
  //protected String nameTemplate = "r:C^^^"; // r:c_i^
  //protected String nameTemplate = "^^C^^^"; // don't include role name
  //protected String nameTemplate = "R^^^^^";
  
  /** Whether or not to format the output by inserting extra spaces and new lines. */
  //private boolean shouldPrettyPrint = true;
  
  /** Use for pretty-printing the output; 50 spaces. */
  //private String indent = "                                                  ";

  /**
   * Constructor.
   */
  public Xholon2BrowserConsole() {}
  
  /*
   * @see org.primordion.xholon.io.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
   */
  public boolean initialize(String mmFileName, String modelName, IXholon root) {
    timeNow = new Date();
    timeStamp = timeNow.getTime();
    if (mmFileName == null) {
      this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".console";
    }
    else {
      this.outFileName = mmFileName;
    }
    this.modelName = modelName;
    this.root = root;
    return true;
  }
  
  /*
   * @see org.primordion.xholon.io.IXholon2ExternalFormat#writeAll()
   */
  public void writeAll() {
    writeNode(root);
  }
  
  /**
   * Write one node, and its child nodes.
   * @param node The current node in the Xholon hierarchy.
   */
  protected void writeNode(IXholon node) {
    // only show state machine nodes if should show them, or if root is a StateMachineCE
    if ((node.getXhcId() == CeStateMachineEntity.StateMachineCE)
        && (shouldShowStateMachineEntities == false)
        && (node != root)) {
      return;
    }
    if ("node".equalsIgnoreCase(getLogNameOrNode())) {
      log(node);
    }
    else {
      log(node.getName(getNameTemplate()));
    }
    
    // children
    if (node.hasChildNodes()) {
      group();
      IXholon childNode = node.getFirstChild();
      while (childNode != null) {
        writeNode(childNode);
        childNode = childNode.getNextSibling();
      }
      groupEnd();
    }
  }
  
  protected native void log(Object obj) /*-{
    $wnd.console.log(obj);
  }-*/;
  
  protected native void group() /*-{
    $wnd.console.group();
  }-*/;
  
  protected native void groupEnd() /*-{
    $wnd.console.groupEnd();
  }-*/;
  
  /**
   * Make a JavaScript object with all the parameters for this external format.
   */
  protected native void makeEfParams() /*-{
    var p = {};
    p.logNameOrNode = "name";
    p.nameTemplate = "r:c_i^"; // "R^^^^^"
    this.efParams = p;
  }-*/;
  
  /** Whether to write out full node, or just the node name (using the name tamplate). */
  public native String getLogNameOrNode() /*-{return this.efParams.logNameOrNode;}-*/;
  //public native void setLogNameOrNode(String logNameOrNode) /*-{this.efParams.logNameOrNode = logNameOrNode;}-*/;

  /** Template to use when writing out node names. */
  public native String getNameTemplate() /*-{return this.efParams.nameTemplate;}-*/;
  //public native void setNameTemplate(String nameTemplate) /*-{this.efParams.nameTemplate = nameTemplate;}-*/;

  public String getOutFileName() {
    return outFileName;
  }

  public void setOutFileName(String outFileName) {
    this.outFileName = outFileName;
  }

  public String getModelName() {
    return modelName;
  }

  public void setModelName(String modelName) {
    this.modelName = modelName;
  }

  public IXholon getRoot() {
    return root;
  }

  public void setRoot(IXholon root) {
    this.root = root;
  }

  public boolean isShouldShowStateMachineEntities() {
    return shouldShowStateMachineEntities;
  }

  public void setShouldShowStateMachineEntities(
      boolean shouldShowStateMachineEntities) {
    this.shouldShowStateMachineEntities = shouldShowStateMachineEntities;
  }
  
  public String getOutPath() {
    return outPath;
  }

  public void setOutPath(String outPath) {
    this.outPath = outPath;
  }
  
}

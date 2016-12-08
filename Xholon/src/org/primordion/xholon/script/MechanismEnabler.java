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

package org.primordion.xholon.script;

import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IGrid;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.ISignal;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;
import org.primordion.xholon.io.xml.IXholon2Xml;
import org.primordion.xholon.io.xml.IXmlWriter;

/**
 * Enable a Xholon Mechanism.
 * Any Java classes specified in an implName, must already exist and must be handled in TreeNodeFactoryNew.java .
 * This is a Xholon script, written in normal compiled Java.
 * It's located in the common default package for Java scripts.
 * It can therefore be pasted into a running Xholon app simply as:
 * <p><pre>&lt;MechanismEnabler mechanism="Story"/></pre></p>
 * In this example, the existing StoryMechanism would be enabled.
 * 
 * More examples:
 * <p><pre>&lt;MechanismEnabler/></pre></p>
 * <p><pre>&lt;MechanismEnabler mechanism="Story" color="Pink"/></pre></p>
 * 
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on December 6, 2016)
 */
public class MechanismEnabler extends XholonScript {
  
  private static final String DEFAULT_MECHANISM = "Story";
  private static final String DEFAULT_MECHANISM_COLOR = null; //"Orange";
  
  /**
   * constructor
   */
  public MechanismEnabler() {
    // this code must be run immediately, before the rest of the CSH tree is instantiated
    // BUT mechanism and mechanismColor will not have had a chance to be set
    //this.enable();
  }
  
  // the name of the mechanism
  private String mechanism = DEFAULT_MECHANISM;
  
  private String mechanismColor = DEFAULT_MECHANISM_COLOR;

  /*@Override
  public void configure()
  {
    enable();
    super.configure();
    //this.removeChild();
  }*/
  
  @Override
  public void postConfigure()
  {
    //enable();
    super.postConfigure();
    this.removeChild();
  }
  
  /**
   * Enable a Xholon mechanism.
   */
  protected void enable() {
    if ((mechanism == null) || (mechanism.length() == 0)) {return;}
    switch (mechanism) {
    case "Story":
      enableStory();
      break;
    default:
      break;
    }
  }
  
  /**
   * Enable the Story mechanism.
   */
  protected void enableStory() {
    IApplication app = this.getApp();
    IXholon root = app.getRoot();
    IXholonClass xhcRoot = app.getXhcRoot();
    IXholon service = root.getService("XholonHelperService");
    if (service != null) {
      if (root.getClassNode("StoryEntity") == null) { // confirm that the mechanism has not already been enabled
        // IH
        String ihStr = new StringBuilder()
        .append("<StoryEntity xmlns=\"http://www.primordion.com/namespace/Story\" id=\"1014700\">\n")
        .append("  <StorySystem/>\n")
        .append("  <Screenplay/>\n")
        .append("  <Story/>\n")
        .append("  <Scene/>\n")
        .append("  <Scenes/>\n")
        .append("  <AvatarScript/>\n")
        .append("  <SceneLocation/>\n")
        .append("  <Character>\n")
        .append("    <Protagonist/>\n")
        .append("    <Antagonist/>\n")
        .append("  </Character>\n")
        .append("  <Characters/>\n")
        .append("  <Narrator/>\n")
        .append("  <Director/>\n")
        .append("</StoryEntity>")
        .toString();
        root.println(ihStr);
        service.sendSyncMessage(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, ihStr, xhcRoot); // -2013

        // CD
        StringBuilder cdSb = new StringBuilder()
        .append("<xholonClassDetails>\n");
        if ((mechanismColor != null) && (mechanismColor.length() > 0)) {
          cdSb
          .append("<StoryEntity>\n")
	        .append("  <Color>")
	        .append(mechanismColor)
	        .append("</Color>\n")
  	      .append("</StoryEntity>\n");
	      }
	      cdSb
	      .append("<StorySystem xhType=\"XhtypePureActiveObject\" implName=\"org.primordion.xholon.mech.story.StorySystem\"/>\n")
	      .append("<Screenplay xhType=\"XhtypePureActiveObject\" implName=\"org.primordion.xholon.mech.story.Screenplay\"/>\n")
	      .append("<Character xhType=\"XhtypePureActiveObject\" implName=\"org.primordion.xholon.base.Avatar\"><Color>rgba(255,255,255,1.0)</Color></Character>\n")
        .append("<Narrator xhType=\"XhtypePureActiveObject\" implName=\"org.primordion.xholon.base.Avatar\"/>\n")
	      .append("<Scenes xhType=\"XhtypePureActiveObject\" implName=\"org.primordion.xholon.mech.story.Scenes\"/>\n")
	      .append("<Director xhType=\"XhtypePureActiveObject\" implName=\"org.primordion.xholon.mech.story.Director\"/>\n")
	      .append("</xholonClassDetails>");
        String cdStr = cdSb.toString();
        root.println(cdStr);
        service.sendSyncMessage(ISignal.ACTION_PASTE_LASTCHILD_FROMSTRING, cdStr, xhcRoot); // -2013
      }
    }
  }
  
  @Override
  public int setAttributeVal(String attrName, String attrVal) {
    if ("mechanism".equals(attrName)) {
      this.mechanism = attrVal;
    }
    else if ("color".equals(attrName)) {
      this.mechanismColor = attrVal;
    }
    else if ("go".equals(attrName)) {
      this.enable();
    }
    return 0;
  }
  
  @Override
  public native IApplication getApp() /*-{
    return $wnd.xh.app();
  }-*/;
  
}

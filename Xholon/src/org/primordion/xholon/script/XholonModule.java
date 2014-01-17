/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2011 Ken Webb
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

import java.util.Map;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.app.IApplication;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.io.xml.IXml2Xholon;
import org.primordion.xholon.io.xml.Xml2Xholon;
import org.primordion.xholon.io.xml.Xml2XholonClass;
import org.primordion.xholon.service.IXholonService;
import org.primordion.xholon.service.XholonHelperService;

/**
 * A module specifies a complete set of Xholon classes and instances.
 * Use of this module script is preferable to spreading things among multiple files.
 * This Xholon script is written in normal compiled Java.
 * It's located in the common default package for Java scripts.
 * It can therefore be pasted into a running Xholon app simply as:
 * <pre>
&lt;XholonModule>
  &lt;XholonMap>
    &lt;Attribute_String roleName="ih">/org/primordion/user/app/solarsystem/ih.xml&lt;/Attribute_String>
    &lt;Attribute_String roleName="cd">/org/primordion/user/app/solarsystem/cd.xml&lt;/Attribute_String>
    &lt;Attribute_String roleName="csh">/org/primordion/user/app/solarsystem/cshSunPlanets.xml&lt;/Attribute_String>
  &lt;/XholonMap>
&lt;/XholonModule>
 * </pre>
 * or
 * <pre>
&lt;XholonModule xmlns:xi="http://www.w3.org/2001/XInclude">
  &lt;xi:include href="./org/primordion/user/app/solarsystem/moduleSunEarth.xml"/>
&lt;/XholonModule>
 * </pre>
 * or just (pasted or dragged in, but not through a XholonConsole)
 * <pre>
./org/primordion/user/app/solarsystem/moduleSunEarth_inline.xml
 * </pre>
 * or (pasted or dragged in, or submitted through a XholonConsole)
 * <pre>
&lt;_-.forest>&lt;xi:include xmlns:xi="http://www.w3.org/2001/XInclude" href="./org/primordion/user/app/solarsystem/moduleSunEarth_inline.xml"/>&lt;/_-.forest>
 * </pre>
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.8.1 (Created on November 5, 2011)
 */
@SuppressWarnings("serial")
public class XholonModule extends XholonScript {
  
  /*
   * @see org.primordion.xholon.base.Xholon#postConfigure()
   */
  public void postConfigure() {
    handleContent(this);
    removeChild();
  }
  
  /**
   * Handle the XML content.
   * @param node The new Xholon csh subtree will be inserted after this node.
   */
  @SuppressWarnings("unchecked")
  protected void handleContent(final IXholon node) {
    Map<String, String> map = (Map<String, String>)this.getFirstChild();
    if (map == null) {return;}
    String ih = null;
    if (map.containsKey("ih")) {
      ih = ((String)map.get("ih")).trim();
    }
    String cd = null;
    if (map.containsKey("cd")) {
      cd = ((String)map.get("cd")).trim();
    }
    String csh = null;
    if (map.containsKey("csh")) {
      csh = ((String)map.get("csh")).trim();
    }
    readModuleFromXml(ih, cd, csh);
  }
  
  /**
   * Read the module from XML files/streams/URIs.
   * @param ih A filename or URI, or inline XML content.
   * @param cd A filename or URI, or inline XML content.
   * @param csh A filename or URI, or inline XML content.
   */
  protected void readModuleFromXml(String ih, String cd, String csh)
  {
    IApplication app = this.getApp();
    // it's OK to read the InheritanceHierarchy and ClassDetails XML files more than once
    // read the InheritanceHierarchy.xml and ClassDetails.xml files
    if (ih != null) {
      IXml2Xholon xml2XholonClass = new Xml2XholonClass();
      xml2XholonClass.setApp(app);
      xml2XholonClass.setInheritanceHierarchy(((Application)app).getInherHier());
      xml2XholonClass.setTreeNodeFactory(((Application)app).getFactory());
      IXholon xhClass = null;
      if (isXml(ih)) {
        xhClass = xml2XholonClass.xmlString2Xholon(ih, app.getXhcRoot());
      }
      else {
        xhClass = xml2XholonClass.xmlUri2Xholon(ih, app.getXhcRoot());
      }
      if (cd != null) {
        if (isXml(cd)) {
          xml2XholonClass.xmlString2Xholon(cd, app.getXhcRoot());
        }
        else {
          xml2XholonClass.xmlUri2Xholon(cd, app.getXhcRoot());
        }
      }
      if (xhClass != null) {
        xhClass.configure();
        xhClass.postConfigure();
      }
    }
    // read the CompositeStructure.xml file
    if (csh != null) {
      if (isXml(csh)) {
          XholonHelperService xholonHelperService =
              (XholonHelperService)getService(IXholonService.XHSRV_XHOLON_HELPER);
          xholonHelperService.pasteAfter(this, csh);
      }
      else {
        IXml2Xholon xml2Xholon = new Xml2Xholon();
        xml2Xholon.setApp(app);
        xml2Xholon.setInheritanceHierarchy(((Application)app).getInherHier());
        xml2Xholon.setTreeNodeFactory(((Application)app).getFactory());
        IXholon myRootXhNode = null;
        myRootXhNode = xml2Xholon.xmlUri2Xholon(csh, null);
        if (myRootXhNode != null) {
          myRootXhNode.insertAfter(this);
          myRootXhNode.configure();
          myRootXhNode.postConfigure();
        }
      }
    }
  }
  
  /**
   * Is this an XML String?
   * @param str
   * @return true or false
   */
  protected boolean isXml(String str) {
    if (str.charAt(0) == '<') {
      return true;
    }
    else {
      return false;
    }
  }
  
}

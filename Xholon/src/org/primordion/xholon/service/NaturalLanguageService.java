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

package org.primordion.xholon.service;

import java.util.Map;

import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonClass;

/**
 * A service that connects to various online natural language services.
 * The nodes are retained as children of this service.
 * Examples:
var service = xh.service("NaturalLanguageService");
var service = xh.service("NaturalLanguageService-GoogleNL");
 *
 * Various implementations of INaturalLanguage are possible:
 * - 
 *
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on December 23, 2016)
 */
public class NaturalLanguageService extends AbstractXholonService {
  
  /**
   * Name of the class that implements the service.
   * This is the default.
   */
  private String defaultImplName = "org.primordion.xholon.service.naturallanguage.GoogleNL";
  
  /**
   * Return an instance of an INaturalLanguage (ex: GoogleNL), or null if it can't be created.
   * A new instance is created each time this method is called?  NO
   * @see org.primordion.xholon.service.AbstractXholonService#getService(java.lang.String)
   */
  public IXholon getService(String serviceName)
  {
    if (serviceName.startsWith(getXhcName())) {
      String implName = findImplName(serviceName);
      IXholon instance = null;
      if (implName == null) {
        instance = createInstance(defaultImplName);
      }
      else {
        instance = createInstance(implName);
      }
      if (instance != null) {
        String cName = instance.getClass().getName();
        cName = cName.substring(cName.lastIndexOf(".")+1);
        IXholonClass xholonClass = getApp().getClassNode(cName);
        if (xholonClass == null) {
          xholonClass = getApp().getClassNode("XholonServiceImpl");
        }
        instance.setId(getNextId());
        instance.setXhc(xholonClass);
        instance.appendChild(this);
      }
      return instance;
    }
    return null;
  }
  
  /**
   * Find the implName using the serviceName as a key.
   * @param serviceName An extended service name.
   * ex: NaturalLanguageService-GoogleNL
   * @return An implName, or null.
   */
  @SuppressWarnings("unchecked")
  protected String findImplName(String serviceName)
  {
    Map<String, String> map = (Map<String, String>)getFirstChild();
    return (String)map.get(serviceName);
  }

  public String getDefaultImplName() {
    return defaultImplName;
  }

  public void setDefaultImplName(String defaultImplName) {
    this.defaultImplName = defaultImplName;
  }
  
}

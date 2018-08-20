/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2018 Ken Webb
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

/**
 * A service that provides HTML5 Web Cryptography support for Xholon.
 * usage:
 *  var wcs = xh.service("WebCryptographyService");
 *  var obj = wcs.obj(); // may need to try this several times
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on August 19, 2018)
 */
public class WebCryptographyService extends AbstractHTML5Service {
  
  @Override
  public native Object getVal_Object() /*-{
    var obj = $wnd.xh["WebCryptographyService"];
    if (obj) {
      return obj;
    }
    return null;
  }-*/;
  
  /**
   * Load the Xholon WebCryptographyService.js JavaScript file.
   */
  protected native void requireHTML5() /*-{
    $wnd.xh.require("WebCryptographyService");
  }-*/;
  
}

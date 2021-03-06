/* Xholon Runtime Framework - executes event-driven & dynamic applications
 * Copyright (C) 2013 Ken Webb
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

package org.primordion.ef.threejs;

import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Generate and display a threejs scene, using an HTML5 WebGL (3D) canvas,
 * or using a 2D canvas as fallback.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://threejs.org">threejs website</a>
 * @since 0.9.0 (Created on December 21, 2013)
 */
@SuppressWarnings("serial")
public class Xholon2WebGL extends AbstractXholon2Threejs implements IXholon2ExternalFormat {
  
  public Xholon2WebGL() {
    if (supportsWebGL()) {
      renderer = "WebGLRenderer";
    }
    else {
      renderer = "CanvasRenderer";
    }
  }
  
  /**
   * Does the browser support WebGL.
   * @return true (supports WebGL), or false (may support canvas as a fallback).
   * source: https://github.com/mrdoob/three.js/blob/master/examples/js/Detector.js
   */
  public native boolean supportsWebGL() /*-{
    try {
      var canvas = $doc.createElement('canvas');
      return !! $wnd.WebGLRenderingContext
        && (canvas.getContext('webgl') || canvas.getContext('experimental-webgl'));
    } catch(e) {
      return false;
    }
  }-*/;
  
}

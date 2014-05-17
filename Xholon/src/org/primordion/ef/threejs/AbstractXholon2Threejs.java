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

import java.util.Date;

import org.primordion.xholon.base.IXholon;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.io.gwt.HtmlScriptHelper;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Generate and display a threejs scene.
 * This is an abstract superclass.
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="http://threejs.org">threejs website</a>
 * @since 0.9.0 (Created on December 20, 2013)
 */
@SuppressWarnings("serial")
public abstract class AbstractXholon2Threejs extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
  
  protected String outFileName;
  protected String outPath = "./ef/threejs/";
  protected String modelName;
  protected IXholon root;
  private StringBuilder sb;
  protected String fileExt = ".js";
  
  /** Current date and time. */
  protected Date timeNow;
  protected long timeStamp;

  // used in SphereGeometry constructor
  protected int widthSegments = 20;
  protected int heightSegments = 20;
  
  // used in MeshLambertMaterial constructor for text
  //protected String textColor = "000000"; // black text
  //protected String textColor = "0000ff"; // blue text
  protected String textColor = "ffffff"; // white text
  
  protected int width = 980; // px
  protected int height = 500; // px
  protected String unit = "px";
  
  protected String renderer = null;
  
  // use the following two with renderer.setClearColor(color, alpha)
  //protected int bgColor = 0xffffff; // background color white
  //protected int bgColor = 0xf8fff8; // background color light-green
  protected int bgColor = 0x000000; // background color black
  protected int bgOpacity = 1; // background opacity (alpha)
  
  public AbstractXholon2Threejs() {}
  
  /*
   * @see org.primordion.ef.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
   */
  public boolean initialize(String outFileName, String modelName, IXholon root) {
    root.consoleLog("AbstractXholon2Threejs initialize()");
    timeNow = new Date();
    timeStamp = timeNow.getTime();
    if (outFileName == null) {
      this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + fileExt;
    }
    else {
      this.outFileName = outFileName;
    }
    this.modelName = modelName;    
    this.root = root;
    root.consoleLog("about to call !isDefinedTHREE()");
    if (!isDefinedTHREE()) {
      loadThreejs();
      return true; // do not return false; that just causes an error message
    }
    return true;
  }

  /*
   * @see org.primordion.ef.IXholon2ExternalFormat#writeAll()
   */
  public void writeAll() {
    root.consoleLog("AbstractXholon2Threejs writeAll()");
    if (!isDefinedTHREE()) {return;}
    sb = new StringBuilder();
    writeHelloWorld(root);
    writeToTarget(sb.toString(), outFileName, outPath, root);
    HtmlScriptHelper.fromString(sb.toString(), true);
  }
  
  /**
   * Write HelloWorld content for testing.
   */
  protected void writeHelloWorld(IXholon node) {
    
    String text1 = "null_1";
    String text2 = "null_2";
    IXholon node1 = node.getFirstChild();
    if (node1 != null) {
      text1 = node1.getName();
      IXholon node2 = node1.getNextSibling();
      if (node2 != null) {
        text2 = node2.getName();
      }
    }
    
    String groupName = "group";
    sb.append("(function() {\n");
    writeDeclarations(groupName, "xhimg");
    sb
    .append("\ninit();\n")
    .append("animate();\n\n");
    
    sb
    .append("function init() {\n")
    .append("container = document.createElement('div');\n")
    .append("container.style.height = \"").append(height).append(unit).append("\";\n")
    .append("container.style.width = \"").append(width).append(unit).append("\";\n")
    .append("xhdiv.appendChild(container);\n");
    writeCameras();
    sb
    .append("scene = new THREE.Scene();\n")
    .append("group = new THREE.Object3D();\n");
    writeSphere(groupName, 1, -200, 200, 50, "ff0000");
    writeText(groupName, text1, 1, -220, 255, 10);
    writeSphere(groupName, 2, 200, 200, 100, "00ff00");
    writeText(groupName, text2, 2, 180, 310, 10);
    sb.append("scene.add(").append(groupName).append(");\n");
    writeLights();
    sb.append("scene.add(light);\n");
    writeRenderer();
    writeAddListeners();
    sb.append("}\n\n");
    
    writeListeners();
    writeAnimate();
    writeRender();
    sb.append("})()\n");
  }
  
  /**
   * Write one node, and its child nodes.
   * @param node The current node in the Xholon hierarchy.
   * @param level Current level in the hierarchy.
   */
  protected void writeNode(IXholon node, int level) {
    
  }
  
  protected void writeDeclarations(String groupName, String divName) {
    sb
    .append("var container;\n")
    .append("var camera, scene, renderer;\n")
    .append("var ").append(groupName).append(";\n")
    .append("var targetRotation = 0;\n")
    .append("var targetRotationOnMouseDown = 0;\n")
    .append("var mouseX = 0;\n")
    .append("var mouseXOnMouseDown = 0;\n")
    .append("var xhdiv = document.getElementById(\"").append(divName).append("\");\n")
    .append("xhdiv.style.height = \"").append(height).append(unit).append("\";\n")
    .append("var divHalfX = ").append(width).append(" / 2;\n")
    .append("var divHalfY = ").append(height).append(" / 2;\n")
    //.append("$wnd.console.log(\"divHalfX: \" + divHalfX);\n")
    //.append("$wnd.console.log(\"divHalfY: \" + divHalfY);\n")
    ;
  }
  
  protected void writeCameras() {
    sb
    .append("camera = new THREE.PerspectiveCamera(70, ")
      .append(width).append(" / ")
      .append(height).append(", 1, 1000);\n")
    .append("camera.position.y = 150;\n")
    .append("camera.position.z = 500;\n")
    ;
  }
  
  protected void writeLights() {
    sb
    .append("var light = new THREE.PointLight(0xffffff);\n")
    .append("light.position.x = 10;\n")
    .append("light.position.y = 250;\n")
    .append("light.position.z = 130;\n")
    ;
  }
  
  protected void writeRenderer() {
    sb
    .append("renderer = new THREE.").append(renderer).append("();\n")
    .append("renderer.setSize(").append(width).append(", ").append(height).append(");\n")
    .append("renderer.setClearColor(").append(bgColor).append(", ").append(bgOpacity).append(");\n")
    .append("container.appendChild(renderer.domElement);\n")
    ;
  }
  
  protected void writeAddListeners() {
    sb
    .append("container.addEventListener('mousedown', onDocumentMouseDown, false);\n")
    ;
  }
  
  protected void writeListeners() {
    sb
    .append("function onDocumentMouseDown(event) {\n")
    .append("  event.preventDefault();\n");
    writeListenerAdds();
    sb
    .append("  mouseXOnMouseDown = event.clientX - divHalfX;\n")
    .append("  targetRotationOnMouseDown = targetRotation;\n")
    .append("}\n\n");
    
    sb
    .append("function onDocumentMouseMove(event) {\n")
    .append("  mouseX = event.clientX - divHalfX;\n")
    .append("  targetRotation = targetRotationOnMouseDown + (mouseX - mouseXOnMouseDown) * 0.02;\n")
    .append("}\n\n");
    
    sb.append("function onDocumentMouseUp(event) {\n");
    writeListenerRemoves();
    sb.append("}\n\n");
    
    sb.append("function onDocumentMouseOut(event) {\n");
    writeListenerRemoves();
    sb.append("}\n\n");
  }
  
  protected void writeListenerAdds() {
    sb
    .append("  container.addEventListener('mousemove', onDocumentMouseMove, false);\n")
    .append("  container.addEventListener('mouseup', onDocumentMouseUp, false);\n")
    .append("  container.addEventListener('mouseout', onDocumentMouseOut, false);\n")
    ;
  }

  
  protected void writeListenerRemoves() {
    sb
    .append("  container.removeEventListener('mousemove', onDocumentMouseMove, false);\n")
    .append("  container.removeEventListener('mouseup', onDocumentMouseUp, false);\n")
    .append("  container.removeEventListener('mouseout', onDocumentMouseOut, false);\n")
    ;
  }
  
  protected void writeAnimate() {
    sb
    .append("function animate() {\n")
    .append("  requestAnimationFrame(animate);\n")
    .append("  render();\n")
    .append("}\n")
    ;
  }
  
  protected void writeRender() {
    sb
    .append("function render() {\n")
    .append("  group.rotation.y += (targetRotation - group.rotation.y) * 0.05;\n")
    .append("  renderer.render(scene, camera);\n")
    .append("}\n")
    ;
  }
  
  /*
  sphere1 = new THREE.Mesh(
    new THREE.SphereGeometry(50, 20, 20),
    new THREE.MeshLambertMaterial({color: 0xff0000, overdraw: true})
  );
  sphere1.position.y = 250;
  sphere1.position.x = -200;
  group.add(sphere1);
  */
  protected void writeSphere(String groupName, int id, int x, int y, int size, String color) {
    String meshName = "sphere" + id;
    sb
    .append("var ").append(meshName).append(" = new THREE.Mesh(\n")
    .append("  new THREE.SphereGeometry(").append(size).append(", ")
      .append(widthSegments).append(", ").append(heightSegments).append("),\n")
    .append("  new THREE.MeshLambertMaterial({color: 0x").append(color).append(", overdraw: true})\n")
    .append(");\n")
    .append(meshName).append(".position.x = ").append(x).append(";\n")
    .append(meshName).append(".position.y = ").append(y).append(";\n")
    .append(groupName).append(".add(").append(meshName).append(");\n")
    ;
  }
  
  /*
  text1 = new THREE.Mesh(
    new THREE.TextGeometry("hello_1", {size: 10, height: 0, curveSegments: 2, font: "helvetiker"}),
    new THREE.MeshBasicMaterial({color: 0x000000})
  );
  text1.position.y = 305;
  text1.position.x = -220;
  group.add(text1);
  */
  protected void writeText(String groupName, String text, int id, int x, int y, int size) {
    String meshName = "text" + id;
    sb
    .append("var ").append(meshName).append(" = new THREE.Mesh(\n")
     .append("  new THREE.TextGeometry(\"").append(text).append("\", {size: ").append(size).append(", ")
      .append("height: 0, ").append("curveSegments: 2, ").append("font: \"helvetiker\"").append("}),\n")
    .append("  new THREE.MeshBasicMaterial({color: 0x").append(textColor).append("})\n")
    .append(");\n")
    .append(meshName).append(".position.x = ").append(x).append(";\n")
    .append(meshName).append(".position.y = ").append(y).append(";\n")
    .append(groupName).append(".add(").append(meshName).append(");\n")
    ;
  }
  
  /**
   * Load threejs.org library asynchronously.
   */
  protected void loadThreejs() {
    // approach 1
    //HtmlScriptHelper.requireScript("three.min.js", "http://threejs.org/build/");
    //HtmlScriptHelper.requireScript("helvetiker_regular.typeface.js", "http://threejs.org/examples/fonts/");
    
    // approach 2
    require(this);
  }
  
  /**
   * use requirejs
   */
  protected native void require(final IXholon2ExternalFormat xh2Threejs) /*-{
    //$wnd.console.log("starting require ..");
    //$wnd.console.log($wnd.requirejs);
    //$wnd.console.log($wnd.require);
    //$wnd.console.log($wnd.requirejs.config);
    $wnd.requirejs.config({
      enforceDefine: false,
      shim: {
        "font": {
          deps: ["three"]
        }
      },
      paths: {
        three: [
          //"http://threejs.org/build/three.min"
          "xholon/lib/three.min"
        ],
        font: [
          //"http://threejs.org/examples/fonts/helvetiker_regular.typeface"
          "xholon/lib/helvetiker_regular.typeface"
        ]
      }
    });
    $wnd.require(["three", "font"], function(three, font) {
      xh2Threejs.@org.primordion.xholon.service.ef.IXholon2ExternalFormat::writeAll()();
    });
  }-*/;
  
  /**
   * Is $wnd.THREE defined.
   * @return it is defined (true), it's not defined (false)
   */
  protected native boolean isDefinedTHREE() /*-{
    return typeof $wnd.THREE != "undefined";
  }-*/;
  
}

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

package org.primordion.ef.geo;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;

import java.util.Date;

import org.primordion.xholon.base.IDecoration;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.common.mechanism.CeStateMachineEntity;
import org.primordion.xholon.io.gwt.HtmlScriptHelper;
import org.primordion.ef.AbstractXholon2ExternalFormat;
import org.primordion.xholon.service.ef.IXholon2ExternalFormat;

/**
 * Export a Xholon model as a Leaflet interactive map.
 * Example:
<pre>
		var map = L.map('map').setView([51.505, -0.09], 13);
		L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6IjZjNmRjNzk3ZmE2MTcwOTEwMGY0MzU3YjUzOWFmNWZhIn0.Y8bhBaUMqFiPrDRW9hieoQ', {
			maxZoom: 18,
			attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
				'<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
				'Imagery © <a href="http://mapbox.com">Mapbox</a>',
			id: 'mapbox.streets'
		}).addTo(map);
		L.marker([51.5, -0.09]).addTo(map)
			.bindPopup("<b>Hello world!</b><br />I am a popup.").openPopup();
		L.circle([51.508, -0.11], 500, {
			color: 'red',
			fillColor: '#f03',
			fillOpacity: 0.5
		}).addTo(map).bindPopup("I am a circle.");
		L.polygon([
			[51.509, -0.08],
			[51.503, -0.06],
			[51.51, -0.047]
		]).addTo(map).bindPopup("I am a polygon.");
		var popup = L.popup();
		function onMapClick(e) {
			popup
				.setLatLng(e.latlng)
				.setContent("You clicked the map at " + e.latlng.toString())
				.openOn(map);
		}
		map.on('click', onMapClick);
</pre>
 *
 * TODO
 * - optionally show annotations in popup
 * - enable use of GeoJSON
 * - enable use of TopoJSON
 * - try GeoJSON + CSS; Leaflet.geojsonCSS plugin
 * - OSM Buildings	JS library for visualizing 3D OSM building geometry on top of Leaflet
 * - look at other Leaflet plugins
 *
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on July 17, 2015)
 * @see <a href="http://leafletjs.com/">Leaflet web site</a>
 */
@SuppressWarnings("serial")
public class Xholon2Leaflet extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
  
  private String outFileName;
  private String outPath = "./ef/leaflet/";
  private String modelName;
  private IXholon root;
  private StringBuilder sb;
  
  /** Current date and time. */
  private Date timeNow;
  private long timeStamp;
  
  /**
   * Constructor.
   */
  public Xholon2Leaflet() {}
  
  /*
   * @see org.primordion.xholon.io.IXholon2ExternalFormat#initialize(java.lang.String, java.lang.String, org.primordion.xholon.base.IXholon)
   */
  public boolean initialize(String mmFileName, String modelName, IXholon root) {
    timeNow = new Date();
    timeStamp = timeNow.getTime();
    if (mmFileName == null) {
      this.outFileName = outPath + root.getXhcName() + "_" + root.getId() + "_" + timeStamp + ".leaflet";
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
    sb = new StringBuilder();
    String geo = ((IDecoration)root).getGeo();
    if ((geo == null) || (geo.length() == 0)) {return;}
    
    // set the size of the div that will contain the map
    Element div = Document.get().getElementById(getSelection()); // "xhmap"
    if (div == null) {return;}
    div.setAttribute("style","width: " + getWidth() + "px; height: " + getHeight() + "px");
    
    sb
    .append("var map = L.map('")
    .append(getSelection())
    .append("').setView(");
    if (geo.startsWith("[")) {
      sb.append(geo);
    }
    else {
      sb.append("[").append(geo).append("]");
    }
    sb
    .append(", 13);\n")
    .append("L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6IjZjNmRjNzk3ZmE2MTcwOTEwMGY0MzU3YjUzOWFmNWZhIn0.Y8bhBaUMqFiPrDRW9hieoQ', {\n")
    .append("	maxZoom: 18,\n")
    .append("	attribution: 'Map data &copy; <a href=\"http://openstreetmap.org\">OpenStreetMap</a> contributors, ' +\n")
    .append("		'<a href=\"http://creativecommons.org/licenses/by-sa/2.0/\">CC-BY-SA</a>, ' +\n")
    .append("		'Imagery © <a href=\"http://mapbox.com\">Mapbox</a>',\n")
    .append("	id: 'mapbox.streets'\n")
    .append("}).addTo(map);\n");
    
    writeNode(root, 0); // root is level 0
    
    sb
    .append("var popup = L.popup();\n")
    .append("function onMapClick(e) {\n")
    .append("	popup\n")
    .append("		.setLatLng(e.latlng)\n")
    .append("		.setContent(\"You clicked the map at \" + e.latlng.toString())\n")
    .append("		.openOn(map);\n")
    .append("}\n")
    .append("map.on('click', onMapClick);\n")
    ;
    writeToTarget(sb.toString(), outFileName, outPath, root);
    pasteScript("leafletScript", sb.toString());
  }
  
  /**
   * Write one node, and its child nodes.
   * @param node The current node in the Xholon hierarchy.
   * @param level Current level in the hierarchy.
   */
  protected void writeNode(IXholon node, int level) {
    // only show state machine nodes if should show them, or if root is a StateMachineCE
    if ((node.getXhcId() == CeStateMachineEntity.StateMachineCE)
        && (isShouldShowStateMachineEntities() == false)
        && (level > 0)) {
      return;
    }
    
    // don't include a marker for the subtree root, for example for a "City" node
    if (node != root) {
      String geo = ((IDecoration)node).getGeo();
      if ((geo != null) && (geo.length() > 0)) {
        String nodeName = node.getName(getNameTemplate());
        if (geo.startsWith("{")) {
          writeNodeGeoJSON(geo, nodeName);
        }
        else {
          switch (getShape()) {
          case "marker":
            sb.append("L.marker(");
            if (geo.startsWith("[")) {
              sb.append(geo);
            }
            else {
              sb.append("[").append(geo).append("]");
            }
            sb
            .append(").addTo(map).bindPopup(\"<b>")
            .append(nodeName)
            .append("</b>\");\n");
            break;
          case "circle":
            sb.append("L.circle(");
            if (geo.startsWith("[")) {
              sb.append(geo);
            }
            else {
              sb.append("[").append(geo).append("]");
            }
            sb
            .append(",")
            .append(getCircleRadius())
            .append(").addTo(map).bindPopup(\"<b>")
            .append(nodeName)
            .append("</b>\");\n");
            break;
          default: break;
          }
        }
      }
    }
    
    // children
    if (node.hasChildNodes()) {
      IXholon childNode = node.getFirstChild();
      while (childNode != null) {
        writeNode(childNode, level+1);
        childNode = childNode.getNextSibling();
      }
    }
  }
  
  /**
   * Write Leaflet content based on a GeoJSON string.
   * @param geoJSONStr 
   * @param popupStr 
L.geoJson(data, {
  pointToLayer: function(feature,latlng) {
    return L.marker(latlng);
  }
}).addTo(map);
   */
  protected void writeNodeGeoJSON(String geoJSONStr, String popupStr) {
    sb
    .append("L.geoJson(")
    .append(geoJSONStr)
    .append(", {\n")
    .append("  pointToLayer: function(feature,latlng) {\n");
    switch (getShape()) {
    case "marker":
      sb.append("    return L.marker(latlng);\n");
      break;
    case "circle":
      sb
      .append("    return L.circle(latlng,")
      .append(getCircleRadius())
      .append(");\n");
      break;
    default: break;
    }
    sb
    .append("  }")
    .append("}).addTo(map)")
    .append(".bindPopup(\"<b>")
    .append(popupStr)
    .append("</b>\")")
    .append(";\n");
  }
  
  protected void pasteScript(String scriptId, String scriptContent) {
	  HtmlScriptHelper.fromString(scriptContent, true);
	}

	/**
   * Make a JavaScript object with all the parameters for this external format.
   * annotations
   * circle radius
   * circle and other CSS
   */
  protected native void makeEfParams() /*-{
    var p = {};
    p.shouldShowStateMachineEntities = false;
    p.width = 600;
    p.height = 400;
    p.selection = "xhmap";
    p.nameTemplate = "r:c^^^"; // "r:C^^^" "^^C^^^" "R^^^^^"
    p.shape = "marker"; // "circle" "polygon" "polyline"
    p.circleRadius = 10; // radius in meters
    this.efParams = p;
  }-*/;
	
  public native boolean isShouldShowStateMachineEntities() /*-{return this.efParams.shouldShowStateMachineEntities;}-*/;
  public native void setShouldShowStateMachineEntities(boolean shouldShowStateMachineEntities) /*-{this.efParams.shouldShowStateMachineEntities = shouldShowStateMachineEntities;}-*/;
  
  public native int getWidth() /*-{return this.efParams.width;}-*/;
  //public native void setWidth(String width) /*-{this.efParams.width = width;}-*/;
  
  public native int getHeight() /*-{return this.efParams.height;}-*/;
  //public native void setHeight(String height) /*-{this.efParams.height = height;}-*/;
  
  public native String getSelection() /*-{return this.efParams.selection;}-*/;
  //public native void setSelection(String selection) /*-{this.efParams.selection = selection;}-*/;
  
  public native String getNameTemplate() /*-{return this.efParams.nameTemplate;}-*/;
  //public native void setNameTemplate(String nameTemplate) /*-{this.efParams.nameTemplate = nameTemplate;}-*/;

  public native String getShape() /*-{return this.efParams.shape;}-*/;
  //public native void setShape(String shape) /*-{this.efParams.shape = shape;}-*/;
  
  public native int getCircleRadius() /*-{return this.efParams.circleRadius;}-*/;
  //public native void setCircleRadius(String circleRadius) /*-{this.efParams.circleRadius = circleRadius;}-*/;
  
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

  public String getOutPath() {
    return outPath;
  }

  public void setOutPath(String outPath) {
    this.outPath = outPath;
  }
  
}

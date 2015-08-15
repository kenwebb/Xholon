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
 *
 * TODO
 * - optionally show annotations in popup
 * - DONE enable use of GeoJSON
 * - enable use of TopoJSON
 * - try GeoJSON + CSS; Leaflet.geojsonCSS plugin
 * - OSM Buildings  JS library for visualizing 3D OSM building geometry on top of Leaflet
 * - look at other Leaflet plugins
 *
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @since 0.9.1 (Created on July 17, 2015)
 * @see <a href="http://leafletjs.com/">Leaflet web site</a>
 */
@SuppressWarnings("serial")
public class Xholon2Leaflet extends AbstractXholon2ExternalFormat implements IXholon2ExternalFormat {
  
  public static final int IMPL_LEAFLETONLY = 1;
  //public static final int IMPL_D3SVGOVERLAY = 2;
  //public static final int IMPL_D3 = 3; // see bost.ocks.org/mike/leaflet/
  
  private String outFileName;
  private String outPath = "./ef/leaflet/";
  private String modelName;
  private IXholon root;
  
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
    String geo = ((IDecoration)root).getGeo();
    if ((geo == null) || (geo.length() == 0)) {return;}
    
    // set the size of the div that will contain the map
    Element div = Document.get().getElementById(getSelection()); // "xhmap"
    if (div == null) {return;}
    div.setAttribute("style","width: " + getWidth() + "px; height: " + getHeight() + "px");
    
    if (!geo.startsWith("[")) {
      geo = "[" + geo + "]";
    }
    Object map = createMapWithTiles(getSelection(), geo, getTileServer(), getZoom(), root.getParentNode());
    if (map != null) {
      createInitAnim();
      createRemoveAnim();
      writeNode(map, root, 0); // root is level 0
      createMapPopup(map);
    }
  }
  
  /**
   * Create a Leaflet map with tiles.
   * @param selection The id of the div where the Leaflet map will reside (ex: "xhmap").
   * @param geo The map latlng coordinate (ex: "[12.1,34.3]").
   * @param tileServer 
   *   most tile server values are from: https://leanpub.com/leaflet-tips-and-tricks/read
   * @param zoom Leaflet view zoom.
   * @param me The parent of the root of the Xholon subtree that is used to create this Leaflet map.
   */
  protected native Object createMapWithTiles(String selection, String geo, String tileServer, int zoom, IXholon me) /*-{
    var geoJSON = $wnd.JSON.parse(geo);
    var map = $wnd.L.map(selection).setView(geoJSON, zoom);
    map._initPathRoot();
    
    // store a reference to the map in Xholon for debugging, testing, etc.
    if (!$wnd.xh.leaflet) {
      $wnd.xh.leaflet = {};
    }
    $wnd.xh.leaflet.map = map;
    $wnd.xh.leaflet.leafletSvg = null;
    $wnd.xh.leaflet.d3cpArr = [];
    $wnd.xh.leaflet.me = me;
    
    var tlUrl = null;
    var tlAttrib = null;
    switch (tileServer) {
    case "openstreetmap":
    case "osm":
      tlUrl = 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
      tlAttrib = '&copy; ' + '<a href="http://openstreetmap.org">OpenStreetMap</a>' + ' Contributors';
      break;
    case "osmbw":
      tlUrl = 'http://{s}.www.toolserver.org/tiles/bw-mapnik/{z}/{x}/{y}.png';
      tlAttrib = '&copy; ' + '<a href="http://openstreetmap.org">OpenStreetMap</a>' + ' Contributors';
      break;
    case "outdoors":
      tlUrl = 'http://{s}.tile.thunderforest.com/outdoors/{z}/{x}/{y}.png';
      tlAttrib = '&copy; ' + '<a href="http://openstreetmap.org">OpenStreetMap</a>' + ' Contributors & ' + '<a href="http://thunderforest.com/">Thunderforest</a>';
      break;
    case "openaerial":
      tlUrl = 'http://otile{s}.mqcdn.com/tiles/1.0.0/sat/{z}/{x}/{y}.png';
      tlAttrib = 'Portions Courtesy NASA/JPL-Caltech and U.S. Depart. of Agriculture, Farm Service Agency. Tiles courtesy of ' + '<a href="http://www.mapquest.com//">MapQuest</a>' + '<img src="http://developer.mapquest.com/content/osm/mq_logo.png">';
      break;
    case "mapbox":
      tlUrl = 'https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6IjZjNmRjNzk3ZmE2MTcwOTEwMGY0MzU3YjUzOWFmNWZhIn0.Y8bhBaUMqFiPrDRW9hieoQ';
      tlAttrib = 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' + '<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' + 'Imagery © <a href="http://mapbox.com">Mapbox</a>';
      break;
    case "stamen": // watercolor-like
      tlUrl = 'http://{s}.tile.stamen.com/watercolor/{z}/{x}/{y}.jpg';
      tlAttrib = '&copy; ' + '<a href="http://openstreetmap.org">OpenStreetMap</a>' + ' Contributors & ' + '<a href="http://stamen.com">Stamen Design</a>';
      break;
    default: break;
    }
    if (tlUrl == null) {return null;}
    
    $wnd.L.tileLayer(tlUrl, {
      maxZoom: 18,
      attribution: tlAttrib,
      id: 'mapbox.streets'
    }).addTo(map);
    return map;
  }-*/;
  
  /**
   * Create a popup for the Leaflet map.
   * @param map The Leaflet map object.
   */
  protected native void createMapPopup(Object map) /*-{
    var popup = $wnd.L.popup();
    function onMapClick(e) {
      popup
        .setLatLng(e.latlng)
        .setContent("You clicked the map at " + e.latlng.toString())
        .openOn(map);
    }
    map.on('click', onMapClick);
  }-*/;
  
  /**
   * Write one node, and its child nodes.
   * @param node The current node in the Xholon hierarchy.
   * @param level Current level in the hierarchy.
   */
  protected void writeNode(Object map, IXholon node, int level) {
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
          createNodeGeoJSON(map, node, geo, nodeName, getShape(), getCircleRadius(), getPathOptions(node));
        }
        else {
          if (!geo.startsWith("[")) {
            geo = "[" + geo + "]";
          }
          createNodePath(map, node, geo, nodeName, getShape(), getCircleRadius(), getPathOptions(node));
        }
      }
    }
    
    // children
    if (node.hasChildNodes()) {
      IXholon childNode = node.getFirstChild();
      while (childNode != null) {
        writeNode(map, childNode, level+1);
        childNode = childNode.getNextSibling();
      }
    }
  }
  
  /**
   * Create Leaflet content as a Path (Circle, Polygon, Polyline, Rectangle).
   * L.circle([45.388446,-75.7407834],30,{"color": "red"}).addTo(map).bindPopup("<b>house</b>");
   * TODO I could add a tooltip. Use an efParam to specify tooltip or popup. Example:
   *   var marker = L.marker([-41.29042, 174.78219],{title: 'Hover Text'}).addTo(map);
   *   path = $wnd.L.marker(latlng, {popupStr});
   *   var ellipse = L.ellipse([51.5, -0.09], [500, 100], 90).addTo(map);
   */
  protected native void createNodePath(Object map, IXholon node, String latlng, String popupStr, String shape, int circleRadius, String pathOptions) /*-{
    latlng = $wnd.JSON.parse(latlng);
    var path = null;
    switch (shape) {
    case "marker":
      path = $wnd.L.marker(latlng);
      break;
    case "circle":
      if (pathOptions) {
        path = $wnd.L.circle(latlng, circleRadius, $wnd.JSON.parse(pathOptions));
      }
      else {
        path = $wnd.L.circle(latlng, circleRadius);
      }
      break;
    case "ellipse":
      if (pathOptions) {
        path = $wnd.L.ellipse(latlng, [circleRadius, circleRadius*1.3], 15, $wnd.JSON.parse(pathOptions));
      }
      else {
        path = $wnd.L.ellipse(latlng, [circleRadius, circleRadius*1.3], 15);
      }
      break;
    case "d3cp":
      // TODO
      break;
    default: break;
    }
    if (path) {
      path.addTo(map).bindPopup("<b>" + popupStr + "</b>");
    }
  }-*/;
  
  /**
   * Create Leaflet content based on a GeoJSON string.
   * Priority:
   * 1 use value from feature.properties
   * 2 use value from Xholon IDecoration (Xholon node, XholonClass)
   * 3 stay with Leaflet default (if I do nothing)
   */
  protected native void createNodeGeoJSON(Object map, IXholon node, String geoJSONStr, String popupStr, String shape, int circleRadius, String pathOptions) /*-{
    if (shape == "d3cp") {return;}
    var geoJSON = $wnd.JSON.parse(geoJSONStr);
    $wnd.L.geoJson(geoJSON, {
      pointToLayer: function(feature,latlng) {
        //$wnd.console.log(node.name());
        //$wnd.console.log(feature);
        switch (shape) {
        case "marker":
          return $wnd.L.marker(latlng);
        case "circle":
          //return L.circle(latlng,30,{color: "orange"});
          if (pathOptions) {
            return $wnd.L.circle(latlng, circleRadius, $wnd.JSON.parse(pathOptions));
          }
          else {
            return $wnd.L.circle(latlng, circleRadius);
          }
        case "ellipse":
          if (pathOptions) {
            return $wnd.L.ellipse(latlng, [circleRadius, circleRadius*1.3], 15, $wnd.JSON.parse(pathOptions));
          }
          else {
            return $wnd.L.ellipse(latlng, [circleRadius, circleRadius*1.3], 15);
          }
        default:
          return null;
        }
      },
      style: function(feature) {
        var props = feature.properties;
        var styles = {};
        if (props) {
          if (props.color) {styles.color = props.color;}
          if (props.opacity) {styles.opacity = props.opacity;}
          if (props.weight) {styles.weight = props.weight;}
          if (props.dashArray) {styles.dashArray = props.dashArray;}
          if (props.fillColor) {styles.fillColor = props.fillColor;}
          if (props.fillOpacity) {styles.fillOpacity = props.fillOpacity;}
        }
        //$wnd.console.log(styles);
        return styles;
      }
    }).addTo(map).bindPopup("<b>" + popupStr + "</b>");
  }-*/;
  
  /**
   * Get Leaflet Path (Circle, Polygon, Polyline, Rectangle) options.
   * {color: feature.properties.color}
   * For now, just return node's IDecoration color, or null
   * @param node 
   * @return an Object string, or a zero-length string. Example:
   *   ,{color: "red"}
   *
see http://leafletjs.com/reference.html#path for list/description of options
Styles (see leaflet-src.js)
this.options IDecoration SVG
------------ ----------- ---
color                    stroke, fill (if no fillColor)
opacity                  stroke-opacity
weight                   stroke-width
dashArray                stroke-dasharray
fillColor    Color       fill
fillOpacity  Opacity     fill-opacity
   *
   */
  protected String getPathOptions(IXholon node) {
    String color = ((IDecoration)node).getColor();
    if (color == null) {
      color = ((IDecoration)node.getXhc()).getColor();
    }
    if (color == null) {
      return null;
    }
    return "{\"color\": \"" + color + "\"}";
  }
  
  /**
   * Create the initAnim () function.
   * Examples:
    this.initAnim("City/Preschool[@roleName='Mothercraft']", 35, 35);
    this.initAnim("City/School[@roleName='Hilson']", 30, 30);
    this.initAnim("City/School[@roleName='New']", 30, 30);
    this.initAnim("City/House", 200, 200);
   */
  protected native void createInitAnim() /*-{
    var $efParams = this.efParams;
    $wnd.xh.leaflet.initAnim = function(xpathExpr, width, height) {
      if (width === undefined) {width = -1;}
      if (height === undefined) {height = -1;}
      var xhNode = $wnd.xh.leaflet.me.xpath(xpathExpr);
      var svgId = "xh" + xhNode.id();
      var efpSel = "div#" + $efParams.selection + " div.leaflet-overlay-pane";
      
      if ($wnd.xh.leaflet.leafletSvg == null) {
        // see Xholon2Leaflet.java IMPL_ constants
        switch ($efParams.impl) {
        case 1: // IMPL_LEAFLETONLY = 1
          var leafletSvg = $wnd.xh.leaflet.map.getPanes().overlayPane.querySelector("svg.leaflet-zoom-animated");
          //$wnd.console.log(leafletSvg);
          var gEle = $doc.createElementNS("http://www.w3.org/2000/svg", "g");
          gEle.setAttribute("id", "leaflet-d3cp");
          gEle.setAttribute("transform", "scale(1,1)"); // can only scale a G element, not an SVG ele
          //$wnd.console.log(gEle);
          leafletSvg.appendChild(gEle);
          $wnd.xh.leaflet.leafletSvg = leafletSvg.querySelector("g#leaflet-d3cp");
          //$wnd.console.log($wnd.xh.leaflet.leafletSvg);
          $wnd.xh.leaflet.leafletSvg.efpSel = efpSel + ">svg.leaflet-zoom-animated>g#leaflet-d3cp>svg#";
          //$wnd.console.log($wnd.xh.leaflet.leafletSvg.efpSel);
          break;
        //case 2: // IMPL_D3SVGOVERLAY = 2
          //$wnd.xh.leaflet.leafletSvg = $wnd.xh.leaflet.map.getPanes().overlayPane.querySelector("svg.d3-overlay>g>g>g");
          //$wnd.xh.leaflet.leafletSvg.efpSel = efpSel + ">svg.d3-overlay>g>g>g>svg#";
          //break;
        default: break;
        }
      }
      
      var svgEle = $doc.createElementNS("http://www.w3.org/2000/svg", "svg");
      svgEle.setAttribute("id", svgId);
      // assume that width and height have real values (not -1)
      var latLng = null;
      var geoStr = xhNode.geo();
      var geo = $wnd.JSON.parse(geoStr);
      if (geoStr.substring(0,1) == "{") {
        // assume geo is in GeoJSON format; GeoJSON latLng are in reverse order
        latLng = geo.geometry.coordinates.reverse();
      }
      else {
        // assume geo is a latLng array [123,456]
        latLng = geo;
      }
      var point = $wnd.xh.leaflet.map.latLngToLayerPoint(latLng);
      svgEle.setAttribute("x", point.x - width/2);
      svgEle.setAttribute("y", point.y - height/2);
      //$wnd.console.log(svgEle);
      $wnd.xh.leaflet.leafletSvg.appendChild(svgEle);
      
      $wnd.xh.leaflet.me.append('<Animate duration="2" selection="'
      + $wnd.xh.leaflet.leafletSvg.efpSel
      + svgId
      + '" xpath="./FunSystem/'
      + xpathExpr
      + '" efParams="{&quot;selection&quot;:&quot;'
      + $wnd.xh.leaflet.leafletSvg.efpSel
      + svgId
      + '&quot;,&quot;sort&quot;:&quot;disable&quot;,&quot;width&quot;:'
      + width
      + ',&quot;height&quot;:'
      + height
      + ',&quot;mode&quot;:&quot;tween&quot;,&quot;labelContainers&quot;:true'
      + ',&quot;includeId&quot;:true'
      + ',&quot;includeClass&quot;:false'
      + ',&quot;shape&quot;:&quot;circle&quot;}"/>');
  
      $wnd.xh.leaflet.d3cpArr.push({xpathExpr: xpathExpr, svgId: svgId, xhNode: xhNode});
    }
  }-*/;
  
  /**
   * Create the removeAnim() function.
   * Example: $wnd.xh.leaflet.removeAnim($funsys.first().next().next().next());
   */
  protected native void createRemoveAnim() /*-{
    $wnd.xh.leaflet.removeAnim = function(firstAnim) {
      // remove all the Xholon <Animate nodes
      var anim = firstAnim;
      while (anim) {
        var nextAnim = anim.next();
        anim.remove();
        anim = nextAnim;
      }
      
      // remove all the SVG d3cpObj elements
      for (var i = 0; i < $wnd.xh.leaflet.d3cpArr.length; i++) {
        var d3cpObj = $wnd.xh.leaflet.d3cpArr[i];
        var ele = $wnd.xh.leaflet.leafletSvg.querySelector("svg#" + d3cpObj.svgId);
        if (!ele) {return;}
        $wnd.xh.leaflet.leafletSvg.removeChild(ele);
      }
      
      // re-initialize some variables in preparation for the new zoom level
      $wnd.xh.leaflet.leafletSvg = null;
      $wnd.xh.leaflet.d3cpArr = [];
    }
  }-*/;
  
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
    p.tileServer = "mapbox"; // "mapbox" "stamen"
    p.zoom = 13;
    p.impl = 1;
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
  
  public native String getTileServer() /*-{return this.efParams.tileServer;}-*/;
  //public native void setTileServer(String tileServer) /*-{this.efParams.tileServer = tileServer;}-*/;
  
  public native int getZoom() /*-{return this.efParams.zoom;}-*/;
  //public native void setZoom(String zoom) /*-{this.efParams.zoom = zoom;}-*/;
  
  public native int getImpl() /*-{return this.efParams.impl;}-*/;
  //public native void setImpl(String impl) /*-{this.efParams.impl = impl;}-*/;
  
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

// Animate nodes within a single SVG image.
// xhSvgAnim.js
// Ken Webb  May 22, 2015
// MIT License, Copyright (C) 2015 Ken Webb
// Designed to animate D3-CirclePack SVG images.
// This script needs to be loaded, perhaps by D3 GUI .java ?
//   HtmlScriptHelper.requireScript("xhSvgAnim", null);

// usage: xh.anim(xhnode, "#xhanim>#one", 1);
//        

/* Chrome Developer Tools, and Firebug (The Black Geese)
var xhnode = xh.root().xpath("StorySystem/Village/House/Avatar");
var selection = document.querySelector("#xhgraph");
var duration = 2;
xh.anim(xhnode, selection, duration);
*/

if (typeof xh == "undefined") {
  xh = {};
}

xh.anim = function(xhnode, selection, duration) {
  console.log("xh.anim 1");
  if (typeof d3 == "undefined") {return;}
  
  /**
   * ex: hop 25;
   * @param g A group (g) element in an SVG image.
   * @param amount The number of pixels to hop in the y (up) direction.
   *   A positive number moves up, a negative number moves down.
   *   I multiple by -1, to reverse the SVG default where a negative number means up.
   */
  var hop = function(g, amount) {
    var matrix = g.transform.baseVal.getItem(0).matrix;
    var matrixf = matrix.f;
    d3.select(g).transition()
    .attr("transform", "translate(" + (matrix.e + 0) + "," + (matrix.f + (amount * (-1))) + ")")
    .duration(durationMs)
    .each("end", function() {
      d3.select(g).transition()
      .attr("transform", "translate(" + (matrix.e + 0) + "," + matrixf + ")")
      .duration(durationMs)
    });
  }
  
  /**
   * ex: duck 35;
   */
  var duck = function(g, amount) {
    hop(g, amount * -1);
  }
  
  /**
   * ex: turnright 45
   * the "end" event triggers a reverse rotate; only needed if g is still part of the active SVG image
   * I assume that the original angle is 0 degrees.
   * Can only turn up to 180 degrees; use d3.interpolateString() if I want to go higher than 180
   */
  var turnright = function(g, degrees) {
    var matrix = g.transform.baseVal.getItem(0).matrix;
    var cx = matrix.e;
    var cy = matrix.f;
    d3.select(g).transition()
    .attr("transform", "rotate(" + degrees + "," + cx + "," + cy + ")" + "translate(" + cx + "," + cy + ")")
    .duration(durationMs)
    .each("end", function() {
      d3.select(g).transition()
      .attr("transform", "rotate(" + 0 + "," + cx + "," + cy + ")" + "translate(" + cx + "," + cy + ")")
      .duration(durationMs)
    });
  }
  
  /**
   * ex: turnleft 45
   */
  var turnleft = function(g, degrees) {
    turnright(g, degrees * -1);
  }
  
  /**
   * ex: grow 2
   * I assume that the original scale is 1,1
   */
  var grow = function(g, scaleX, scaleY) {
    var matrix = g.transform.baseVal.getItem(0).matrix;
    var cx = matrix.e;
    var cy = matrix.f;
    d3.select(g).transition()
    .attr("transform", "translate(" + cx + "," + cy + ")" + "scale(" + scaleX + "," + scaleY + ")")
    .duration(durationMs)
    .each("end", function() {
      d3.select(g).transition()
      .attr("transform", "translate(" + cx + "," + cy + ")" + "scale(1,1)")
      .duration(durationMs)
    });
  }
  
  /**
   * ex: shrink 2
   */
  var shrink = function(g, scaleX, scaleY) {
    grow(g, 1/scaleX, 1/scaleY);
  }
  
  /**
   * ex: mirror x
   */
  var mirror = function(g, direction) {
    if (direction == "y") {
      grow(g, 1, -1);
    }
    else {
      grow(g, -1, 1);
    }
  }
  
  /**
   * show
   * Make the node visible again by returning it to its normal size.
   */
  var show = function(g, shouldShow) {
    var scale = 1;
    if (!shouldShow) {
      scale = 1/256;
    }
    var matrix = g.transform.baseVal.getItem(0).matrix;
    var cx = matrix.e;
    var cy = matrix.f;
    d3.select(g).transition()
    .attr("transform", "translate(" + cx + "," + cy + ")" + "scale(" + scale + ")")
    .duration(durationMs);
  }
  
  /**
   * Save the animation data, typically for later use by xhSvgTween.js .
   */
  var saveData = function(g, animName, animValue) {
    var d3Data = g.__data__;
    if (!d3Data) {
      d3Data = g.__data__ = {};
    }
    if (!d3Data.anim) {
      d3Data.anim = {};
    }
    d3Data.anim[animName] = animValue;
    console.log(d3Data);
  }
  
  if ((xhnode === undefined) || (xhnode === null)) {
    xhnode = xh.root();
  }
  if ((selection === undefined) || (selection === null)) {
    selection = document.querySelector("#xhanim>#one"); // "#xhgraph"
  }
  if ((duration === undefined) || (duration === null)) {
    duration = 1;
  }
  var durationMs = duration * 500; // convert seconds to ms, divided by 2 to accommodate the 2 half-cycles
  
  var animObj = xhnode.anim;
  if (!animObj) {
    return;
  }
  
  var svg = null;
  try {
    svg = selection.querySelector("svg");
  } catch(e) {
    console.log(e);
    return;
  }
  if (!svg) {return;}
  
  var nodeName = xhnode.name("^^c_i^");
  var g = svg.querySelector('g>g[id$=' + nodeName + ']');
  if (g == null) {
    return;
  }
  
  /*var d3Data = g.__data__;
  if (d3Data) {
    // save it for xhSvgTween.js
    d3Data.anim = animObj;
  }
  console.log(d3Data);*/
  
  for (aname in animObj) {
    console.log(aname);
    switch (aname) {
    case "hop":
      hop(g, animObj[aname]);
      break;
    case "duck":
      duck(g, animObj[aname]);
      break;
    case "turnright":
      turnright(g, animObj[aname]);
      break;
    case "turnleft":
      turnleft(g, animObj[aname]);
      break;
    case "grow":
      grow(g, animObj[aname], animObj[aname]);
      break;
    case "shrink":
      shrink(g, animObj[aname], animObj[aname]);
      break;
    case "mirror":
      mirror(g, animObj[aname]);
      break;
    //case "hide":
    //  hide(g);
    //  break;
    case "show":
      show(g, animObj[aname]);
      break;
    default: break;
    }
    saveData(g, aname, animObj[aname]);
  }
  
}

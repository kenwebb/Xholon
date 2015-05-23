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
    console.log("hopping");
    var matrix = g.transform.baseVal.getItem(0).matrix;
    //console.log("" + matrix.e + "," + matrix.f);
    d3.select(g).transition()
    .attr("transform", "translate(" + (matrix.e + 0) + "," + (matrix.f + (amount * (-1))) + ")")
    .duration(durationMs)
    // the "end" event triggers a reverse half-hop; only needed if g is still part of the active SVG image
    .each("end", function() {
      d3.select(g).transition()
      .attr("transform", "translate(" + (matrix.e + 0) + "," + (matrix.f + amount) + ")")
      .duration(durationMs)
    });
  }
  
  /**
   * ex: duck 35;
   */
  var duck = function(g, amount) {
    hop(g, amount * -1);
  }
  
  //console.log("xh.anim 2");
  if ((xhnode === undefined) || (xhnode === null)) {
    xhnode = xh.root();
  }
  if ((selection === undefined) || (selection === null)) {
    selection = document.querySelector("#xhanim>#one"); // "#xhgraph"
  }
  if ((duration === undefined) || (duration === null)) {
    duration = 5;
  }
  var durationMs = duration * 1000; // convert seconds to ms
  
  //console.log("xh.anim 3");
  var animObj = xhnode.anim;
  if (!animObj) {
    //console.log("xh.anim 4");
    return;
  }
  
  //console.log("xh.anim 5");
  console.log(animObj);
  console.log(selection);
  var svg = null;
  try {
    svg = selection.querySelector("svg");
  } catch(e) {
    console.log(e); // SYNTAX_ERR
    return;
  }
  if (!svg) {return;}
  console.log(svg);
  
  var nodeName = xhnode.name("^^c_i^");
  //console.log(nodeName);
  //console.log(svg);
  var g = svg.querySelector('g>g[id$=' + nodeName + ']');
  //console.log(g);
  if (g == null) {
    return;
  }
  
  //hop(g, -25);
  for (aname in animObj) {
    switch (aname) {
    case "hop":
      hop(g, animObj[aname]);
      break;
    case "duck":
      duck(g, animObj[aname]);
      break;
    default: break;
    }
  }
  
}

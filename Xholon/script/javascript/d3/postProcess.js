/**
 * Xholon D3 CirclePack post-processing library.
 * For now, these functions are pretty quick and dirty.
 */

/**
 * Scale an SVG image by a specified factor.
 */
(function(scaleFactor) {
var p = document.getElementById("xhgraph");
var arr = p.getElementsByTagName("svg");
for (var i = 0; i < arr.length; i++) {
  var svg = arr[i];
  var width = svg.width.baseVal.value;
  var height = svg.height.baseVal.value;
  //console.log(i + " " + width + "," + height);
  svg.width.baseVal.value = width * scaleFactor;
  svg.height.baseVal.value = height * scaleFactor;
  var g = svg.firstChild;
  var transform = g.attributes[0].value;
  //console.log(transform);
  g.attributes[0].value = transform + " scale(" + scaleFactor + ")";
}
})(2)

/**
 * Another version of the scale function.
 */
(function(scaleFactor) {
var p = document.getElementById("xhgraph");
var arr = p.getElementsByTagName("svg");
for (var i = 0; i < arr.length; i++) {
  var svg = arr[i];
  svg.width.baseVal.value *= scaleFactor;
  svg.height.baseVal.value *= scaleFactor;
  svg.firstChild.attributes[0].value += " scale(" + scaleFactor + ")";
}
})(2)

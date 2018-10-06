<script><![CDATA[
// Scale an SVG image.
(function(whSelector, tSelector, scaleFactor) {
  // change the width and height of the svg (or other root) element
  var whEle = $wnd.d3.select(whSelector);
  var w = scaleDim(whEle.attr("width"));  // ex: "134.0px"
  var h = scaleDim(whEle.attr("height")); // ex: "371.0px"
  if (w && h) {
    whEle.attr({width: w, height: h});
  }
  
  // change the scale transform of the top-level g element
  // translate(0,0) scale(1) rotate(2)
  var tEle = whEle.select(tSelector);  
  var transStr = tEle.attr("transform");
  var start = transStr.indexOf("scale(");
  if (start != -1) {
    start = start + 6;
    var end = transStr.indexOf(")", start);
    if (end != -1) {
      var scale = transStr.substring(start, end) * scaleFactor;
      var newTransStr = transStr.substring(0, start) + scale + transStr.substring(end);
      tEle.attr("transform", newTransStr);
    }
  }
  
  // scale the width or height dimension
  function scaleDim(dim) {
    if (dim) {
      newDim = Number(dim.substring(0, dim.length - 2));
      if (newDim) {
        newDim = (newDim * scaleFactor) + "px";
      }
    }
    return newDim;
  }
  
  })("#xhsvg svg", "g", 2.0);
]]></script>

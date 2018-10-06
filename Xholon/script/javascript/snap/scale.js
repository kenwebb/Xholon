<script><![CDATA[
// Scale an SVG image.
// This works in Firebug, or pasted into root node of app.
(function(whSelector, tSelector, scaleFactor) {
  // change the width and height of the svg (or other root) element
  var whEle = $wnd.Snap(whSelector);
  var w = scaleDim(whEle.attr("width"));  // ex: "134.0px"
  var h = scaleDim(whEle.attr("height")); // ex: "371.0px"
  if (w && h) {
    whEle.attr({width: w, height: h});
  }
  // change the scale transform of the top-level g element
  var tEle = whEle.select(tSelector);
  tEle.transform(tEle.transform().localMatrix.scale(scaleFactor).toTransformString());
  
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

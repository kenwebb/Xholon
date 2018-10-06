<script><![CDATA[
// filters only work on g elements
(function(selection, arg1, arg2, arg3, arg4) {
  var paper = $wnd.Snap(selection);
  //var f = paper.filter($wnd.Snap.filter.blur(arg1, arg2)); // x, [y]  5
  //var f = paper.filter($wnd.Snap.filter.brightness(arg1)); // amount 0..1  0.9
  //var f = paper.filter($wnd.Snap.filter.contrast(arg1)); // amount 0..1  0.9
  //var f = paper.filter($wnd.Snap.filter.grayscale(arg1)); // amount 0..1  0.1
  //var f = paper.filter($wnd.Snap.filter.hueRotate(arg1)); // angle
  var f = paper.filter($wnd.Snap.filter.invert(arg1)); // amount 0..1  1.0
  //var f = paper.filter($wnd.Snap.filter.saturate(arg1)); // amount 0..1  0.5
  //var f = paper.filter($wnd.Snap.filter.sepia(arg1)); // amount 0..1  1.0
  //var f = paper.filter($wnd.Snap.filter.shadow(arg1, arg2, arg3, arg4)); // dx, dy, [blur], [color]
  var c = paper.attr({filter: f});
})("#xhsvg svg g", 1.0);
]]></script>

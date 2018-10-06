<script><![CDATA[
// this can be done before or after scaling
(function(selection, x, y) {
  var paper = $wnd.Snap(selection);
  var f = paper.filter($wnd.Snap.filter.blur(x, y));
  var c = paper.attr({filter: f});
})("#xhsvg svg g", 0.5, 0);
]]></script>

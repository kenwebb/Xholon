<script><![CDATA[
// <g id="toplevelgroup" transform="translate(0,0) scale(1) rotate(0)">
(function(selection) {
  var idStr = "toplevelgroup";
  var tStr = "translate(0,0) scale(1) rotate(0)";
  var s = $wnd.Snap(selection);
  if (s.select("g#" + idStr)) {
    // there's already a toplevelgroup
    return;
  }
  var c = s.selectAll("svg>g,svg>circle,svg>ellipse,svg>image,svg>line,svg>path,svg>polygon,svg>polyline,svg>rect,svg>text");
  s.g().attr({id: idStr, transform: tStr}).append(c);
})("#xhsvg svg");
]]></script>

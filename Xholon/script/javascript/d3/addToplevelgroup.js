<script><![CDATA[
// <g id="toplevelgroup" transform="translate(0,0) scale(1) rotate(0)">
(function(selection) {
  var idStr = "toplevelgroup";
  var tStr = "translate(0,0) scale(1) rotate(0)";
  var s = $wnd.d3.select(selection);
  if (!s.select("g#" + idStr).empty()) {
    // there's already a toplevelgroup
    return;
  }
  var c = s.selectAll("svg>g,svg>circle,svg>ellipse,svg>image,svg>line,svg>path,svg>polygon,svg>polyline,svg>rect,svg>text");
  var g = s.append("g").attr({id: idStr, transform: tStr}).node();
  c.each(function() {
    // d3 can't append an existing node; use DOM method instead
    g.appendChild(this);
  });
})("#xhsvg svg");
]]></script>

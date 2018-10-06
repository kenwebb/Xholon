<script><![CDATA[
(function(selection) {
  var s = $wnd.d3.select(selection);
  // d3 doesn't have a way to do this
  // d3 doc suggests: https://code.google.com/p/innersvg/
  // Snap.svg does provide toString()
  //$wnd.xh.root().println(s.toString());
})(
"#xhsvg svg"
//"#xhsvg svg rect[id$='Hoist']"
);
]]></script>

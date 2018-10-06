<script><![CDATA[
// TODO this sort-of works, but the pieces end up invisible
(function(selection) {
  var s = $wnd.Snap(selection);
  var gall = s.selectAll("g");
  for (var i = 0; i < gall.length; i++) {
    gall[i].drag();
  }
})(
"#xhsvg svg"
);
]]></script>

<script><![CDATA[
(function(inc) {
  //var hoist = $wnd.d3.select("#xhsvg svg #AnElevatorSystem\\\/Elevator\\\[1\\\]\\\/Hoist");
  var hoist = $wnd.d3.select("#xhsvg svg rect[id$='Hoist']");
  var yTarget = hoist.attr("y") - inc;
  hoist.transition().duration(1000).attr("y", yTarget);
})(5);
]]></script>

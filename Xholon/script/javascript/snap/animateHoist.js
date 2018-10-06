<script><![CDATA[
(function(inc) {
  //var hoist = $wnd.Snap("#xhsvg svg #AnElevatorSystem\\\/Elevator\\\[1\\\]\\\/Hoist");
  var hoist = $wnd.Snap("#xhsvg svg rect[id$='Hoist']");
  var yTarget = hoist.attr("y") - inc;
  //console.log(yTarget);
  hoist.animate({y:yTarget.toString()}, 1000);
})(5);
]]></script>

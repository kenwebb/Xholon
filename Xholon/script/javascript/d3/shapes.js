<script><![CDATA[
// d3
// circle ellipse line path polygon polyline rect text
(function(selection, shape, attrs, draggable, d3, textContent) {
  var parent = d3.select(selection);
  var ele = parent.append(shape).attr(attrs);
  if (shape == "text" && textContent) {
    ele.text(textContent);
  }
  if (draggable) {
    var drag = d3.behavior.drag()
    .origin()
    .on("drag", dragmove);
    ele.call(drag);
  }
  
  // TODO this doesn't work yet
  function dragmove(d) {
    d3.select(this)
    .attr("cx", d3.event.x)
    .attr("cy", d3.event.y);
  }

})(
"#xhsvg svg", "circle", {cx: 20, cy: 20, r: 10}, false, $wnd.d3
//"#xhsvg svg", "ellipse", {cx: 20, cy: 20, rx: 20, ry: 10}, false, $wnd.d3

// line won't be visible unless a stroke color is defined (stroke: "green")
//"#xhsvg svg", "line", {x1: 20, y1: 20, x2: 40, y2: 50}, false, $wnd.d3

// path d=""
//"#xhsvg svg", "path", {d: "M20,20l40,40l30,50Z"}, false, $wnd.d3

//"#xhsvg svg", "polygon", {points: "0,0 0,6 6,3"}, false, $wnd.d3
//"#xhsvg svg", "polyline", {points: "0,0 0,6 6,3"}, false, $wnd.d3

//"#xhsvg svg", "rect", {x: 20, y: 20, width: 25, height: 35, rx: 5, ry: 5}, false, $wnd.d3

// text-anchor: "start" "middle" "end"
//"#xhsvg svg", "text", {x: 20, y: 20, dx: 0, dy: 0, textAnchor: "start"}, false, $wnd.d3, "Friday"

);
]]></script>

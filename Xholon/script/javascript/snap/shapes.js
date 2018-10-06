<script>
// Snap.svg
// circle ellipse g gradient image line path polygon polyline rect text
(function(selection, shape, attrs, draggable) {
  var paper = $wnd.Snap(selection);
  var ele = paper.el(shape).attr(attrs);
  if (draggable) {
    ele.drag();
  }
})(
//"#xhsvg svg", "circle", {cx: 20, cy: 20, r: 10}, true
//"#xhsvg svg", "ellipse", {x: 20, y: 20, rx: 20, ry: 10}, true
"#xhsvg svg", "rect", {x: 20, y: 20, width: 25, height: 35, rx: 5, ry: 5}, true
);
</script>

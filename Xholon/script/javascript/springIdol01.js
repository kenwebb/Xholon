<script>
// This works in Chrome developer tools (remove function arg),
// or pasted into root node of SpringIdol app.
(function(xh) {
  var root = xh.root();
  root.println(root.name());
  var kenny = xh.xpath("Performers/BaseSaxophonist", root);
  if (kenny != null) {
    root
    .println(kenny.name())
    .println(kenny.id())
    .println(kenny.role())
    .println(kenny.attrs().length)
    .println(kenny.portNames())
    .println(kenny.port("song").name())
    .println(kenny.port("instrument").name());
  }
})($wnd.xh);
</script>

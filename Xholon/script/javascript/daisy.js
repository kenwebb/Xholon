<script><![CDATA[
// Create a Daisy using the Xholon IF language.
// Tested with:
//   http://xholon.meteor.com/?app=ca00b929901de66fc721&src=gist&gui=none
// This works in Chrome developer tools (remove function arg $wnd.xh),
// or pasted into root node of a Xholon app.
(function(xh, xpathExpr, action) {
  var node = xh.root().xpath(xpathExpr);
  if (node != null) {
    node.append("<Avatar/>");
    node.last().action(action).remove();
  }
})($wnd.xh, "descendant::City", "param meteor true;build plant role Daisy;enter Daisy;build root;build stem;build flower;enter flower;build petal;build petal;build petal;build petal;build petal;build petal;build petal;");
]]></script>

<script implName="lang:javascript:inline:"><![CDATA[
var contextNode = contextNodeKey;
var sum = 0;
function count(node) {
  sum++;
  node = node.getFirstChild();
  while (node) {
    count(node);
    node = node.getNextSibling();
  }
}
count(contextNode);
println("There are " + sum +
  " nodes in the subtree rooted by " + contextNode + ".");
]]></script>
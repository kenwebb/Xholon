<script implName="lang:javascript:inline:"><![CDATA[
var cnode = contextNodeKey;
println('  context node: ' + cnode);
var node = cnode.getFirstChild();
while (node != null) {
  println(node.getXhcName());
  node = node.getNextSibling();
}
]]></script>

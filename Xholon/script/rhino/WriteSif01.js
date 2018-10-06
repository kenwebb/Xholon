<script implName="lang:javascript:inline:"><![CDATA[
// Write all children of the context node as SIF content.
// This is specifically designed to output all Enzymes in the BioSystems Cell Model.
var cnode = contextNodeKey;
var sum = 0;
function writeSif(node) {
  while (node) {
    sum++;
    print(node.getXhcName() + " port " + node.getPort(0).getXhcName());
    if (node.getPort(1)) {
      print(" " + node.getPort(1).getXhcName());
    }
    print(" " + node.getPort(3).getXhcName());
    if (node.getPort(4)) {
      print(" " + node.getPort(4).getXhcName());
    }
    print("\n");
    node = node.getNextSibling();
  }
}
println("<Sif>");
println("<!-- enzyme port substrate product(s) -->");
writeSif(cnode.getFirstChild().getNextSibling());
println("</Sif>");
println("There are " + sum +
  " nodes in the subtree rooted by " + contextNode + ".");
]]></script>

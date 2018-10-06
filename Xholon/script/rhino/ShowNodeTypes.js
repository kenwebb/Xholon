<script implName="lang:javascript:inline:"><![CDATA[
/*
 * Show a sorted list of all types (XholonClass) used by nodes in this Xholon app.
 * This is a Xholon script that can be invoked by pasting the following into any Xholon node:
 * <script implName="lang:javascript:./script/javascript/ShowNodeTypes.js"/>
 * or:
 * <script implName="lang:javascript:/home/ken/workspace/Xholon/script/javascript/ShowNodeTypes.js"/>
 */
var app = applicationKey; // Xholon application
var rootNode = app.getRoot(); // root node in the Xholon application
var contextNode = contextNodeKey;
var typeSet = new java.util.HashSet(); // a Java set

/*
 * Add a node and its children to the type set.
 */
function addNode(node, typeSet) {
  typeSet.add(node.getXhcName());
  node = node.getFirstChild();
  while (node) {
    addNode(node, typeSet);
    node = node.getNextSibling();
  }
}

addNode(rootNode, typeSet);
var typeArray = typeSet.toArray().sort();
for(var i in typeArray) {
  println(typeArray[i]);
}
println("contextNode:" + contextNode);
]]></script>

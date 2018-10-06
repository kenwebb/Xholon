<script implName="lang:BrowserJS:inline:"><![CDATA[
// Create a Xholon div structure in the current page of a web browser.
// This script works with styleDivStructure.js
(function(){
  var cnode = contextNodeKey;
  var subtree = '';
  function buildSubtree(node) {
    var nodeName = node.getXhcName();
    if (nodeName == 'Grid') {return;}
    subtree += '<div class="' + nodeName + '" title="' + node.toString() + '" name="' + node.getName() + '">';
    node = node.getFirstChild();
    while (node) {
      buildSubtree(node);
      node = node.getNextSibling();
    }
    subtree += '</div>\n';
  }
  buildSubtree(cnode);
  document.getElementById('usercontent').innerHTML = subtree;
  //alert(subtree);
})();
]]></script>

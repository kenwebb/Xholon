<script implName="lang:javascript:inline:"><![CDATA[
var contextNode = contextNodeKey;
function style(node) {
  if (node.getXhcName() == "JButton") {
    var component = node.getVal_Object();
    if (component) {
      component.setMinimumSize(new java.awt.Dimension(100, 100));
      component.setPreferredSize(new java.awt.Dimension(100, 100));
      component.setFont(new java.awt.Font("Monospaced", 7, 60));
      component.setBackground(java.awt.Color.PINK);
    }
  }
  node = node.getFirstChild();
  while (node) {
    style(node);
    node = node.getNextSibling();
  }
}
style(contextNode);
]]></script>
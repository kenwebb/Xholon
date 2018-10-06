/*
 * Check XhType.
 * Compare how each node is specified, vs how it is used.
 */

(function(root) {
  
var checkNodes = function(node) {
  checkNode(node);
  var childNode = node.first();
  while (childNode) {
    checkNodes(childNode);
    childNode = childNode.next();
  }
}
  
var checkNode = function(node) {
  // 0, 1, 2, 3, 4, 5, 6, 7
  // Xhtypexxxxxxxxx, XhtypePureContainer, XhtypePurePassiveObject, XhtypexxxFgsCon, XhtypePureActiveObject, XhtypeBehxxxCon, XhtypeBehFgsxxx, XhtypeBehFgsCon
  switch (node.xhType()) {
  case 0: // Xhtypexxxxxxxxx
    break;
  case 1: // XhtypePureContainer
    str += node.name() + ": " + "Pure Container\n";
    if (node.links(false, true).length > 0) {
      str += "  " + "has ports\n"; // this is an active object
    }
    if (node.val() > 0) {
      str += "  " + "has val\n"; // this is a passive object
    }
    break;
  case 2: // XhtypePurePassiveObject
    str += node.name() + ": " + "Pure Passive Object\n";
    if (node.first()) {
      str += "  " + "has children\n" ;
    }
    break;
  case 3: // XhtypexxxFgsCon
    str += node.name() + ": " + "Container + Passive Object\n";
    break;
  case 4: // XhtypePureActiveObject
    str += node.name() + ": " + "Pure Active Object\n";
    if (node.first()) {
      str += "  " + "has children\n" ;
    }
    break;
  case 5: // XhtypeBehxxxCon
    str += node.name() + ": " + "Container + Active Object\n";
    break;
  case 6: // XhtypeBehFgsxxx
    str += node.name() + ": " + "Passive Object + Active Object\n";
    if (node.first()) {
      str += "  " + "has children\n" ;
    }
    break;
  case 7: // XhtypeBehFgsCon
    str += node.name() + ": " + "Container + Passive Object + Active Object\n";
    break;
  default:
    str += node.name() + ": " + node.xhType() + "\n";
    break;
  }
}

var str = "";
checkNodes(root);
str += "\n";
root.println(str);

})(xh.root())

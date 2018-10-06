/*
 * Script to write out a set of attributes from a Xholon app.
 * for use with:
 *  http://127.0.0.1:8888/wb/editwb.html?app=Cell+Model+-+Sets&src=lstr
 *  https://gist.github.com/kenwebb/dcc6eca037a3c0073ebe55eb8ffeba68
*/

(function(root, attrName) {

var writeAttrs = function(node, attrName) {
  writeAttr(node, attrName);
  var childNode = node.first();
  while (childNode) {
    writeAttrs(childNode, attrName);
    childNode = childNode.next();
  }
}

var writeAttr = function(node, attrName) {
  if (node != root) {
    eleStr += ",";
  }
  switch (attrName) {
  case "NodeXE":
    eleStr += node.id();
    break;
  case "RoleNameXE":
    eleStr += node.role();
    break;
  case "ValXE":
    eleStr += node.val();
    break;
  case "XholonClassXE":
    // TODO ignore duplicates
    eleStr += node.xhc().id();
    break;
  case "XholonClassNameXE":
    // TODO ignore duplicates
    eleStr += node.xhc().name();
    break;
  case "NodeXE.parent":
    if (node.parent()) {
      eleStr += "(";
      eleStr += node.id();
      eleStr += ",";
      eleStr += node.parent().id();
      eleStr += ")\n";
    }
    break;
  case "NodeXE.ports":
    var links = node.links(false, true);
    if (links.length > 0) {
      for (var i = 0; i < links.length; i++) {
        eleStr += "(";
        eleStr += node.id();
        eleStr += ",";
        eleStr += links[i].reffedNode.id();
        eleStr += ")";
      }
    }
    break;
  case "XholonClassXE.parent":
    eleStr += "(";
    eleStr += node.xhc().id();
    eleStr += ",";
    eleStr += node.xhc().parent().id();
    eleStr += ")\n";
    break;
  default: break;
  }
}

//var root = xh.root();
var eleStr = "{";
writeAttrs(root, attrName); //"NodeXE");
eleStr += "}";
root.println(eleStr);

})(xh.root(), "NodeXE")

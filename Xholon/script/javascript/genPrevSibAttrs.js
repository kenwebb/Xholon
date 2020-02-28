var func = function(node) {
  root.println(node.name());
  node["previousSibling"] = node.prev();
  var cnode = node.first();
  while (cnode) {
    func(cnode);
    cnode = cnode.next();
  }
}

var root = xh.root();
func(root);

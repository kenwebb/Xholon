/*
 * This is a JavaScript Scratchpad.
 *
 * Enter some JavaScript, then Right Click or choose from the Execute Menu:
 * 1. Run to evaluate the selected text (Ctrl+R),
 * 2. Inspect to bring up an Object Inspector on the result (Ctrl+I), or,
 * 3. Display to insert the result in a comment after the selection. (Ctrl+L)
 */

func = function(node, indent) {
  //console.log(node.name());
  root.println(indent + node.name());
  var cnode = node.first();
  while (cnode) {
    func(cnode, indent + "  ");
    cnode = cnode.next();
  }
}

var root = xh.root();
var xhcRoot = root.xhc().parent();
func(xhcRoot, "");

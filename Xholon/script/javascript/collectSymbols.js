/**
 * Collect symbols and corresponding id values.
 * 
 * Harold and the Purple Crayon
 * http://127.0.0.1:8888/Xholon.html?app=1c39da6c295c64f866a5&src=gist&gui=clsc
 * 
 * log IH or CSH to console
 */

(function(top) {
//const top = temp0; // CSH or IH root
const iarr = []; // array of id values for nodes that also have a symbol
const sarr = []; // array of symbol values for nodes that have a symbol
const tarr = []; // array of type names for nodes that have a symbol
const rarr = []; // array of role names for nodes that have a symbol

const recurse = function(node) {
  console.log(node);
  if (node != null) {
    const symbol = node.symbol() || node.xhc().symbol();
    if (symbol) {
      iarr.push(node.id());
      sarr.push(symbol);
      tarr.push(node.xhc().name());
      rarr.push(node.role());
    }
    let child = node.first();
    while (child) {
      recurse(child);
      child = child.next();
    }
  }
}

recurse(top);
console.log(iarr);
console.log(sarr);
console.log(tarr);
console.log(rarr);

})(temp0)

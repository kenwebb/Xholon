/**
 * Generate a Set that is isomorphic to a collection of Xholon nodes (a Xholon subtree)
 * Ken Webb
 * May 18, 2019
 */
(function() {

let $wnd = window;
//const symbol = "0"; // but it's not a set if every element in the set has the same value
let counter = 0;

let writeNode = function(node, arr) {
  //arr.push(symbol);
  ++counter;
  arr.push(counter); // or arr.push(node.name());
  let cnode = node.first();
  while (cnode) {
    writeNode(cnode, arr);
    cnode = cnode.next();
  }
}

let node = $wnd.xh.root(); // or $wnd.xh.app();
let nodeArr = [];
writeNode(node, nodeArr);
node.println("There are " + nodeArr.length + " nodes.");
node.println(nodeArr); // 1,2,3,4,5
node.println(JSON.stringify(nodeArr)); // [1,2,3,4,5]  JSON array
node.println("{" + nodeArr + "}"); // {1,2,3,4,5}  math set
node.println("[" + nodeArr[0] + "," + nodeArr[nodeArr.length-1] + "]"); // [1,5]  math inclusive interval
node.println("[" + nodeArr[0] + "," + counter + "]"); // [1,5]  math inclusive interval

})()

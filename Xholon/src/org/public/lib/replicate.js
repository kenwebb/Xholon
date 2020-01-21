/**
 * Replicate a Xholon app in JavaScript.
 * replicate.js
 * Ken Webb
 * January 20, 2020
 * 
 * Build a replicon Object within each Xholon node.
 * Set various properties inside the replicon Object.
 *
 * http://127.0.0.1:8888/Xholon.html?app=HelloWorld&gui=clsc&jslib=replicate
 * 
 * Usage (from Dev Tools):
var arr = xh.replicate.buildAll(xh.app(), []);
console.log(xh.replicate.toJSON2(arr));
 * 
var arr = xh.replicate.buildAll(xh.app(), []);
var jsonstr = xh.replicate.toJSON2(arr);
console.log(jsonstr);
console.log(JSON.parse(jsonstr));
var csvstr = xh.replicate.toCSV(arr);
console.log(csvstr);
 *
 * Usage (from Xholon Console):
<script roleName="abc">
var node = $wnd.xh.root();
var arr = $wnd.xh.replicate.buildAll(node, []);
node.println($wnd.xh.replicate.toCSV(arr));
</script>
 * 
 * Notes (see my paper notebook from around January 19, 2020):
 * -----
  - treat any Xholon app as a system that anyone can attempt to replicate in another way
   - such as: JavaScript Objects, Haskell structures, Math sets pairs etc., ramda, Graphviz
 
  - a process for constructing a Xholon-like structure
    ---------
   1. specify n_
   2. build an Array/Set of size n
   3. add an Object to each array element
   4. connect all the objects into a singly-linked list, using a pointer called "succ" (successor) or similar name
    - this is the data structure used in a Turing Machine
   5. add additional relationships, creating a binary tree (BT): btparent btleft btright
    - the BT is more app-specific than the linked list
   6. add additional or redundant relationships, creating a Xholon/XML/HTML-flavored binary tree: parent first next
 
  - in the linked list, each non-terminal node has exactly one outgoing link (succ)
    ------------------
   - the terminal (last) node has zero outgoing links
   - this structure can also be called a degenerate binary tree
   - I can assign this structure a fractal value of 1.0
  
  - in a perfect binary tree, each node has exactly two or zero outgoing links
    ------------------------
   - I can assign this structure a fractal value of 2.0
  - there are also these concepts: complete binary tree, full binary tree
  - a typical real-world binary tree will have a fractal value > 1.0 and < 2.0
   - what would be a typical value, a healthy range of values, has research been done on this?
  
  - see wikipedia Binary_tree
    -------------
  
  - Category Theory
    ---------------
   - the Xholon root node in any subtree, is a terminal object
    - all nodes in the subtree have at least one path leading to the terminal object
   - there is also a concept of an initial object
    - empty set is conceptually an initial object with a non-existent element that leads to every leaf node
  
  - Array
    -----
   - a JavaScript Array with Objects as elements
    - is analogous/isomorphic with a Set of index numbers mapped to a Set of Objects
    Array ["zero","one","two","three"]
    Set Theory
     A = {0,1,2,3}
     B = {zero,one,two,three}
     P = {(0,zero),(1,one),(2,two),(3,three)}
  
  - Graphviz
    --------
digraph G {
  subgraph clusterA {0 1 2 3}
  subgraph clusterB {zero one two three}
  0 -> zero
  1 -> one
  2 -> two
  3 -> three
}
 * 
 */

if (typeof xh == "undefined") {
  xh = {};
}

if (typeof xh.replicate == "undefined") {
  xh.replicate = {};
}

/**
 * Build a replicon structure inside each Xholon node in the subtree rooted by node.
 * Push each replicon onto an Array.
 * @param node 
 * @param arr 
 */
xh.replicate.buildReplicon = function(node, arr) {
  node.replicon = {};
  var arrix = arr.length;
  node.replicon.arrix = arrix;
  if (arrix > 0) {
    arr[arrix-1].replicon.succ = node.replicon;
  }
  node.replicon.succ = null;
  arr.push(node);
  var child = node.first();
  while (child) {
    xh.replicate.buildReplicon(child, arr);
    child = child.next();
  }
}
// xh.replicate.buildReplicon(node, [])

/**
 * Add basic Xholon properties to each replicon in the Array.
 */
xh.replicate.addXhProperties = function(arr) {
  arr.forEach(function(node) {
    node.replicon.xhid = node.id();
    //node.replicon.xhlabel = node.name("R^^^^^");
    if (node.xhc()) {
      node.replicon.xhlabel = node.role() ? node.role() : '';
    }
    else {
      node.replicon.xhlabel = node.name();
    }
    // TODO xh.replicate.makePropsStr()
  })
}
// xh.replicate.addXhProperties(arr)

/*
 * Add Binary Tree (BT) relationships to each replicon in the Array: btparent btleft btright
 * Store either:
 *  1. the remote replicon itself (.replicon), or
 *  2. the arrix of the remote replicon (.replicon.arrix)
 *    - can later retrieve the remote node and replicon from arr[... .arrix]
 */
xh.replicate.addBtRelationships = function(arr) {
  arr.forEach(function(node) {
    if (node.btparent()) {
      node.replicon.btparent = node.btparent().replicon; // .arrix
    }
    else {
      node.replicon.btparent = null;
    }
    if (node.btleft()) {
      node.replicon.btleft = node.btleft().replicon; // .arrix
    }
    else {
      node.replicon.btleft = null;
    }
    if (node.btright()) {
      node.replicon.btright = node.btright().replicon; // .arrix
    }
    else {
      node.replicon.btright = null;
    }
  })
}
// xh.replicate.addBtRelationships(arr)

/**
 * Add Xholon relationships to each replicon in the Array.
 */
xh.replicate.addXhRelationships = function(arr) {
  arr.forEach(function(node) {
    if (node.parent()) {
      node.replicon.xhparent = node.parent().replicon; // .arrix
    }
    else {
      node.replicon.xhparent = null;
    }
    if (node.first()) {
      node.replicon.xhfirst = node.first().replicon; // .arrix
    }
    else {
      node.replicon.xhfirst = null;
    }
    if (node.next()) {
      node.replicon.xhnext = node.next().replicon; // .arrix
    }
    else {
      node.replicon.xhnext = null;
    }
    if (node.xhc()) {
      node.replicon.xhc = node.xhc().replicon; // .arrix
    }
    else {
      node.replicon.xhc = null;
    }
    // links
    var linksArr = node.links(false,true);
    if (linksArr) {
      node.replicon.links = xh.replicate.makeLinksStr(linksArr); // linksArr.length;
    }
    else {
      node.replicon.links = null;
    }
  })
}
// xh.replicate.addXhRelationships(arr)

/**
 * Make and return a string that specifies all links leading from a given node.
 * A link is an app-specific relationship.
 * ex: "edge||123 port|0|7 port|1|8"
 */
xh.replicate.makeLinksStr = function(linksArr) {
  const LINKS_SEP = ' ';
  const LINKS_ITEM_SEP = '|';
  const ARRIX_NULL = -1;
  var linksStr = '';
  var linksSep = ''; // initial value
  linksArr.forEach(function(link) {
    linksStr += linksSep;
    linksSep = LINKS_SEP;
    linksStr += link.fieldName + LINKS_ITEM_SEP;
    if (link.fieldNameIndex > -1) {
      linksStr += link.fieldNameIndex;
    }
    linksStr += LINKS_ITEM_SEP;
    linksStr += (link.reffedNode && link.reffedNode.replicon) ? (link.reffedNode.replicon.arrix) : ARRIX_NULL;
  })
  return linksStr;
}

/**
 * Make and return a string that specifies all app-specific attributes/properties within a given node.
var node = xh.root();
node.aaa = 123;
node.bbb = 'testbbb';
console.log(node);
console.log(node.attrs());
console.log(xh.attrs(node));
console.log(node.attr("State"));
 * 
 * ex: "state||1 thing|0|7.0 thing|1|7.1 junk||'I am junk' bbb||true"
 */
xh.replicate.makePropsStr = function(node) {
  return ''; // TODO
}

/**
 * Build an array of replicons, given the root node of a Xholon subtree.
 */
xh.replicate.buildAll = function(root, arr) {
  xh.replicate.buildReplicon(root, arr);
  xh.replicate.addXhProperties(arr);
  xh.replicate.addBtRelationships(arr);
  xh.replicate.addXhRelationships(arr);
  //console.log(arr);
  return arr;
}
// const arr = xh.replicate.buildAll(xh.root(), [])

// test
xh.replicate.report = function(arr) {
  arr.forEach(function(node, index) {
    if (!node) {
      console.log("no node for index " + index);
    }
    if (node && !node.replicon) {
      console.log(node.name() + " has no replicon");
    }
    if (node && node.replicon && !node.replicon.btparent) {
      console.log(node.name() + " has no replicon btparent");
    }
    if (node && node.replicon && !node.replicon.btleft) {
      console.log(node.name() + " has no replicon btleft");
    }
    if (node && node.replicon && !node.replicon.btright) {
      console.log(node.name() + " has no replicon btright");
    }
  })
}

// xh.replicate.report(arr);

/**
 * Build and return a string of Graphviz nodes, in Graphviz DOT notation.
 */
xh.replicate.toGraphvizNodes = function(arr) {
  var gvstr = "digraph G {\n";
  arr.forEach(function(node) {
    gvstr += xh.replicate.makeLabel02(node) + " ";
  })
  gvstr += "\n}\n";
  //console.log(gvstr); // 0..482
  return gvstr;
}
// xh.replicate.toGraphvizNodes(arr);

/**
 * Build and return Graphviz a btparent graph, in Graphviz DOT notation.
 */
xh.replicate.toGraphvizBtparentGraph = function(arr) {
  var gvstr = "digraph G {\n";
  arr.forEach(function(node) {
    var btparent = node.replicon.btparent;
    if (btparent) {
      gvstr += xh.replicate.makeLabel02(node) + " -> " + xh.replicate.makeLabel02(arr[btparent.arrix]) + "\n";
    }
  })
  gvstr += "\n}\n";
  //console.log(gvstr);
  return gvstr;
}
// xh.replicate.toGraphvizBtparentGraph(arr);

xh.replicate.makeLabel01 = function(replicon) {
  return replicon.arrix;
}

xh.replicate.makeLabel02 = function(node) {
  return node.name("R^^^^^").charAt(0) + node.replicon.arrix;
}

/**
 * to JSON format; a manually created JavaScript Object, stringified with pretty printing
{
  "arrix": 1,
  "xhid": 1,
  "xhlabel": "Controller",
  "succ": 2,
  "btparent": 0,
  "btleft": 2,
  "btright": 7,
  "xhparent": 0,
  "xhfirst": 2,
  "xhnext": 7,
  "xhc": -1,
  "links": 0
},
 */
xh.replicate.toJSON = function(arr) {
  const ARRIX_NULL = -1;
  var jsoarr = [];
  arr.forEach(function(node) {
    var replicon = node.replicon;
    var item = {};
    // as an array
    item.arrix = replicon.arrix;
    // with Xholon properties
    item.xhid = replicon.xhid;
    item.xhlabel = replicon.xhlabel;
    // as a linked list
    item.succ = replicon.succ ? replicon.succ.arrix : ARRIX_NULL;
    // as a binary tree
    item.btparent = replicon.btparent ? replicon.btparent.arrix : ARRIX_NULL;
    item.btleft = replicon.btleft ? replicon.btleft.arrix : ARRIX_NULL;
    item.btright = replicon.btright ? replicon.btright.arrix : ARRIX_NULL;
    // as a Xholon tree
    item.xhparent = replicon.xhparent ? replicon.xhparent.arrix : ARRIX_NULL;
    item.xhfirst = replicon.xhfirst ? replicon.xhfirst.arrix : ARRIX_NULL;
    item.xhnext = replicon.xhnext ? replicon.xhnext.arrix : ARRIX_NULL;
    // 
    item.xhc = replicon.xhc ? replicon.xhc.arrix : ARRIX_NULL;
    // TODO linkGraph references
    item.links = replicon.links ? replicon.links : '';
    jsoarr.push(item);
  })
  var jsonstr = JSON.stringify(jsoarr, null, 2);
  //console.log(jsonstr);
  return jsonstr;
}
// xh.replicate.toJSON(arr);

/*
 * to JSON format; a manually created and formatted string
[
{ "arrix": 0, "xhid": 0, "xhlabel": Application, "succ": 1, "btparent": -1, "btleft": 1, "btright": -1, "xhparent": -1, "xhfirst": 1, "xhnext": -1, "xhc": -113, "links": 13 },
...
]
 */
xh.replicate.toJSON2 = function(arr) {
  const ARRIX_NULL = -1;
  var jsonstr = '[\n';
  var comma = ''; // no prefixed common for the first item
  arr.forEach(function(node) {
    var replicon = node.replicon;
    var itemstr = comma + '{';
    comma = ",\n";
    // as an array
    itemstr += ' "arrix": ' + replicon.arrix + ',';
    // with Xholon properties
    itemstr += ' "xhid": ' + replicon.xhid + ',';
    itemstr += ' "xhlabel": "' + replicon.xhlabel + '",';
    // as a linked list
    itemstr += ' "succ": ' + (replicon.succ ? replicon.succ.arrix : ARRIX_NULL) + ',';
    // as a binary tree
    itemstr += ' "btparent": ' + (replicon.btparent ? replicon.btparent.arrix : ARRIX_NULL) + ',';
    itemstr += ' "btleft": ' + (replicon.btleft ? replicon.btleft.arrix : ARRIX_NULL) + ',';
    itemstr += ' "btright": ' + (replicon.btright ? replicon.btright.arrix : ARRIX_NULL) + ',';
    // as a Xholon tree
    itemstr += ' "xhparent": ' + (replicon.xhparent ? replicon.xhparent.arrix : ARRIX_NULL) + ',';
    itemstr += ' "xhfirst": ' + (replicon.xhfirst ? replicon.xhfirst.arrix : ARRIX_NULL) + ',';
    itemstr += ' "xhnext": ' + (replicon.xhnext ? replicon.xhnext.arrix : ARRIX_NULL) + ',';
    // 
    itemstr += ' "xhc": ' + (replicon.xhc ? replicon.xhc.arrix : ARRIX_NULL) + ',';
    // TODO linkGraph references
    itemstr += ' "links": "' + (replicon.links ? replicon.links : '') + '"';
    itemstr += ' }';
    jsonstr += itemstr;
  })
  jsonstr += '\n]';
  //console.log(jsonstr);
  return jsonstr;
}
// xh.replicate.toJSON2(arr);

/**
 * to CSV
 */
xh.replicate.toCSV = function(arr) {
  const ARRIX_NULL = -1;
  var csvstr = '"arrix","xhid","xhlabel","succ","btparent","btleft","btright","xhparent","xhfirst","xhnext","xhc","links"\n'; // header line
  arr.forEach(function(node) {
    var replicon = node.replicon;
    var itemstr = '';
    // as an array
    itemstr += replicon.arrix + ', ';
    // with Xholon properties
    itemstr += replicon.xhid + ', ';
    itemstr += replicon.xhlabel + ', ';
    // as a linked list
    itemstr += (replicon.succ ? replicon.succ.arrix : ARRIX_NULL) + ', ';
    // as a binary tree
    itemstr += (replicon.btparent ? replicon.btparent.arrix : ARRIX_NULL) + ', ';
    itemstr += (replicon.btleft ? replicon.btleft.arrix : ARRIX_NULL) + ', ';
    itemstr += (replicon.btright ? replicon.btright.arrix : ARRIX_NULL) + ', ';
    // as a Xholon tree
    itemstr += (replicon.xhparent ? replicon.xhparent.arrix : ARRIX_NULL) + ', ';
    itemstr += (replicon.xhfirst ? replicon.xhfirst.arrix : ARRIX_NULL) + ', ';
    itemstr += (replicon.xhnext ? replicon.xhnext.arrix : ARRIX_NULL) + ', ';
    // 
    itemstr += (replicon.xhc ? replicon.xhc.arrix : ARRIX_NULL) + ', ';
    // TODO linkGraph references
    itemstr += (replicon.links ? replicon.links : '');
    itemstr += '\n';
    csvstr += itemstr;
  })
  csvstr += '\n';
  //console.log(csvstr);
  return csvstr;
}
// xh.replicate.toCSV(arr);

/**
 * to a JavaScript Set
 */
xh.replicate.toSet = function(arr) {
  return new Set(arr);
}
// xh.replicate.toSet(arr);

/**
 * Lift the replicon linked list out of the array.
 * Each replicon Object has a "succ" property that points to the next replicon in the list.
 * Each replicon is a JavaScript Object.
 * @return the head of the linked list, from which all other replicons can be accessed by following the "succ" path/chain
 */
xh.replicate.asRepliconLinkedList = function(arr) {
  return arr[0].replicon;
}

// testing
//var arr = xh.replicate.buildAll(xh.app(), []);


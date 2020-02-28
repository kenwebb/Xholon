/**
 * Replicate a Xholon app in JavaScript.
 * replicate02.js
 * Ken Webb
 * January 20, 2020
 * 
 * Build a replicon Object within each Xholon node.
 * Set various properties inside the replicon Object.
 * ex: node.replicon = {}; node.replicon.arrix = index; node.replicon.succ = ...; node.replicon.btleft = ...;
 * 
 * TODO
 * - use btleft btright btparent rather than first next parent
 */

/**
 * Build a replicon structure inside each Xholon node in the subtree rooted by node.
 * Push each replicon onto an Array.
 * @param node 
 * @param arr 
 * @param arrix 
 */
var buildReplicon = function(node, arr) {
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
    buildReplicon(child, arr);
    child = child.next();
  }
}

/**
 * Add basic Xholon properties to each replicon in the Array.
 */
var addXhProperties = function(arr) {
  arr.forEach(function(node) {
    node.replicon.xhid = node.id();
    //node.replicon.xhlabel = node.name("R^^^^^");
    if (node.xhc()) {
      node.replicon.xhlabel = node.role() ? node.role() : '';
    }
    else {
      node.replicon.xhlabel = node.name();
    }
  })
}

/*
 * Add Binary Tree (BT) relationships to each replicon in the Array: btparent btleft btright
 * Store either:
 *  1. the remote replicon itself (.replicon), or
 *  2. the arrix of the remote replicon (.replicon.arrix)
 *    - can later retrieve the remote node and replicon from arr[... .arrix]
 */
var addBtRelationships = function(arr) {
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

/**
 * Add Xholon relationships to each replicon in the Array.
 */
var addXhRelationships = function(arr) {
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
      node.replicon.links = linksArr.length;
    }
    else {
      node.replicon.links = null;
    }
  })
}

/**
 * Build an array of replicons, given the root node of a Xholon subtree.
 */
var buildAll = function(root, arr) {
  buildReplicon(root, arr);
  addXhProperties(arr);
  addBtRelationships(arr);
  addXhRelationships(arr);
  //console.log(arr);
  return arr;
}

// test
var report = function(arr) {
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

// report(arr);

/**
 * Build and return a string of Graphviz nodes, in Graphviz DOT notation.
 */
var toGraphvizNodes = function(arr) {
  var gvstr = "digraph G {\n";
  arr.forEach(function(node) {
    gvstr += makeLabel02(node) + " ";
  })
  gvstr += "\n}\n";
  //console.log(gvstr); // 0..482
  return gvstr;
}

// toGraphvizNodes(arr);

/**
 * Build and return Graphviz a btparent graph, in Graphviz DOT notation.
 */
var toGraphvizBtparentGraph = function(arr) {
  var gvstr = "digraph G {\n";
  arr.forEach(function(node) {
    var btparent = node.replicon.btparent;
    if (btparent) {
      gvstr += makeLabel02(node) + " -> " + makeLabel02(arr[btparent.arrix]) + "\n";
    }
  })
  gvstr += "\n}\n";
  //console.log(gvstr);
  return gvstr;
}

// toGraphvizBtparentGraph(arr);

var makeLabel01 = function(replicon) {
  return replicon.arrix;
}

var makeLabel02 = function(node) {
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
var toJSON = function(arr) {
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
    item.links = replicon.links ? replicon.links : 0;
    jsoarr.push(item);
  })
  var jsonstr = JSON.stringify(jsoarr, null, 2);
  //console.log(jsonstr);
  return jsonstr;
}
// toJSON(arr);

/*
 * to JSON format; a manually created and formatted string
[
{ "arrix": 0, "xhid": 0, "xhlabel": Application, "succ": 1, "btparent": -1, "btleft": 1, "btright": -1, "xhparent": -1, "xhfirst": 1, "xhnext": -1, "xhc": -113, "links": 13 },
...
]
 */
var toJSON2 = function(arr) {
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
    itemstr += ' "xhlabel": ' + replicon.xhlabel + ',';
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
    itemstr += ' "links": ' + (replicon.links ? replicon.links : 0);
    itemstr += ' }';
    jsonstr += itemstr;
  })
  jsonstr += '\n]';
  //var jsonstr = JSON.stringify(jsoarr, null, 2);
  //console.log(jsonstr);
  return jsonstr;
}
// toJSON2(arr);

/**
 * to CSV
 */
var toCSV = function(arr) {
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
    itemstr += (replicon.links ? replicon.links : 0);
    itemstr += '\n';
    csvstr += itemstr;
  })
  csvstr += '\n';
  //var jsonstr = JSON.stringify(jsoarr, null, 2);
  //console.log(csvstr);
  return csvstr;
}
// toCSV(arr);

/**
 * to a JavaScript Set
 */
var toSet = function(arr) {
  return new Set(arr);
}
// toSet(arr);

var $wnd = $wnd || window;
var arr = buildAll($wnd.xh.app(), []);



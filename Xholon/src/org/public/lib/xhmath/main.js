/**
 * main.js
 * (C) Ken Webb, MIT license
 * May 23, 2019
 * 
 * @example
 * Usage:
 * xh.xhmath.buildArrayofNodes(xh.root()); // node
 * xh.xhmath.buildArrayofNodeLabels(xh.root(), "R^^^^^"); // node.name("R^^^^^")
 * xh.xhmath.buildArrayofNodeLabels(xh.root(), ""); // node.name()
 * xh.xhmath.buildArrayofNodeLabels(xh.root(), null); // node
 * 
 * xh.xhmath.buildArrayofPointedNodes();
 * xh.xhmath.buildArrayofPointedNodeLabels("");
 * 
 * var ct = xh.xhmath;
 * ct.asSet(ct.buildArrayofPointedNodeLabels(""));
 */

if (typeof window.xh == "undefined") {
  window.xh = {};
}

import {buildArrayofNodes, buildArrayofNodeLabels}
  from '/xholon/lib/xhmath/modules/basicset.js';
import {buildArrayofPointedNodes, buildArrayofPointedNodeLabels, buildArrayofNodesfromPointedNode, buildArrayofNodeLabelsfromPointedNode}
  from '/xholon/lib/xhmath/modules/pointedset.js';
import {buildArrayofXhTypeIds, buildArrayofXhTypelabels, buildArrayofXhTypelabelsAlt, buildArrayofXhTypePairs, buildArrayofXhTypePairsAlt}
  from '/xholon/lib/xhmath/modules/xhtype.js';
import {buildArrayofClassInstances, buildArrayofClassInstanceLabels, buildArrayofXholonClassNodes, buildArrayofXholonClassNodelabels}
  from '/xholon/lib/xhmath/modules/xhcset.js';

window.xh.xhmath = {};

window.xh.xhmath.buildArrayofNodes = buildArrayofNodes;
window.xh.xhmath.buildArrayofNodeLabels = buildArrayofNodeLabels;

window.xh.xhmath.buildArrayofPointedNodes = buildArrayofPointedNodes;
window.xh.xhmath.buildArrayofPointedNodeLabels = buildArrayofPointedNodeLabels;
window.xh.xhmath.buildArrayofNodesfromPointedNode = buildArrayofNodesfromPointedNode;
window.xh.xhmath.buildArrayofNodeLabelsfromPointedNode = buildArrayofNodeLabelsfromPointedNode;

window.xh.xhmath.buildArrayofXhTypeIds = buildArrayofXhTypeIds;
window.xh.xhmath.buildArrayofXhTypelabels = buildArrayofXhTypelabels;
window.xh.xhmath.buildArrayofXhTypelabelsAlt = buildArrayofXhTypelabelsAlt;
window.xh.xhmath.buildArrayofXhTypePairs = buildArrayofXhTypePairs;
window.xh.xhmath.buildArrayofXhTypePairsAlt = buildArrayofXhTypePairsAlt;

window.xh.xhmath.buildArrayofClassInstances = buildArrayofClassInstances;
window.xh.xhmath.buildArrayofClassInstanceLabels = buildArrayofClassInstanceLabels;
window.xh.xhmath.buildArrayofXholonClassNodes = buildArrayofXholonClassNodes;
window.xh.xhmath.buildArrayofXholonClassNodelabels = buildArrayofXholonClassNodelabels;

// apps
import {raghTest01, raghTest02, exportToGraphvizBT, exportToTableInNewTab}
  from '/xholon/lib/xhmath/apps/raghavendra.js';
window.xh.xhmath.raghTest01 = raghTest01;
window.xh.xhmath.raghTest02 = raghTest02;
window.xh.xhmath.exportToGraphvizBT = exportToGraphvizBT;
window.xh.xhmath.exportToTableInNewTab = exportToTableInNewTab;

function asSet(arr) {
  return new Set(arr);
}

/**
 * Generate an array made up of integers that is isomorphic/bijective with the input array.
 * @parram arrin
 * @param based 0-based (first integer is 0) or 1-based (first integer is 1) or other-based. Normal values are 0 or 1 as JavaScript Numbers
 * @return an array of numbers, typically integers starting with 0 or 1
 */
function asArrayofInt(arrin, based) {
  if (typeof based == "undefined") {based = 0;}
  var arrout = [];
  var iterator = arrin.keys(); 
  for (let key of iterator) {
    arrout.push(key + based);
  }
  return arrout;
}

function asString(arr) {
  return arr.toString();
}

function asJsonString(arr) {
  return JSON.stringify(arr);
}

function asMathString(arr) {
  return "{" + arr.toString() + "}";
}

function isSetExtended() {
  if (Set.__isExtended__) {
    console.log("The built-in JavaScript Set object has been extended using the set-extension.js library.");
    return true;
  }
  else {
    return false;
  }
}

window.xh.xhmath.asSet = asSet;
window.xh.xhmath.asArrayofInt = asArrayofInt;
window.xh.xhmath.asString = asString;
window.xh.xhmath.asJsonString = asJsonString;
window.xh.xhmath.asMathString = asMathString;
window.xh.xhmath.isSetExtended = isSetExtended;

/*
// this DOES work in Chrome, and it DOES work in Firefox starting with release 67.0
import('/xholon/lib/xhmath/modules/basicset.js')
  .then((module) => {
    // Do something with the module.
    console.log("testing module inside ...");
    let arr = subtreeNodes(xh.root());
    console.log(arr);
  });
*/

//console.log("testing module outside ...");

// exports for use by apps
export {
buildArrayofNodes, buildArrayofNodeLabels, buildArrayofPointedNodes,
buildArrayofPointedNodeLabels, buildArrayofNodesfromPointedNode, buildArrayofNodeLabelsfromPointedNode,
buildArrayofXhTypeIds, buildArrayofXhTypelabels, buildArrayofXhTypelabelsAlt, buildArrayofXhTypePairs, buildArrayofXhTypePairsAlt,
asSet, asArrayofInt, asString, asJsonString, asMathString, isSetExtended
}


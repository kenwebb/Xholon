/**
 * raghavendra.js
 * (C) Ken Webb, MIT license
 * May 28, 2019
 * 
 * @see https://arxiv.org/pdf/math/0301211.pdf
 *  BINARY TREES AND FIBRED CATEGORIES, N. RAGHAVENDRA
 * 
 * @example
 *  xh.xhmath.raghTest01(xh.root());
 */

// for now, import everything
import {
  buildArrayofNodes, buildArrayofNodeLabels, buildArrayofPointedNodes,
  buildArrayofPointedNodeLabels, buildArrayofNodesfromPointedNode, buildArrayofNodeLabelsfromPointedNode,
  buildArrayofXhTypeIds, buildArrayofXhTypelabels, buildArrayofXhTypelabelsAlt, buildArrayofXhTypePairs, buildArrayofXhTypePairsAlt,
  asSet, asArrayofInt, asString, asJsonString, asMathString, isSetExtended
  } from '/xholon/lib/xhmath/main.js';

// Σ = {0,1}
const LEFT_CHILD_SYMBOL  = 0;
const RIGHT_CHILD_SYMBOL = 1;
const Σ = [LEFT_CHILD_SYMBOL, RIGHT_CHILD_SYMBOL];

// (1) Q is a finite set, whose elements are called nodes.
let Q = null;

/** delta δ
 * (2) δ: Q × Σ → Q is a function, called the transition function.
 * @param q an instance of the set Q
 * @param a an instance of the set Σ
 * @return 
 * 
 * @example example 2.3:
+---+----+----+
|   | q0 | q1 |
+---+----+----+
| 0 | q0 | q0 |
| 1 | q1 | q1 |
+--------+----+

(Q,Σ) |-> Q
----- --- -
(0,0) |-> 0
(0,1) |-> 1
(1,0) |-> 0
(1,1) |-> 1

 */
function δ(q, a) {
  console.log("testing function δ ...");
  console.log(q);
  console.log(a);
}

// Definition 2.1. A binary graph is a pair Γ = (Q, δ)
const Γ = [Q, δ];

// Given such a binary graph Γ, we get a pair of functions λ: Q → Q and ρ: Q → Q, defined by ...

/**
 * a function λ: Q → Q
 * λ(q) = δ(q,0), for all q ∈ Q. We call λ(q) the left child of q
 */
function λ(q) {
  var leftNode = q.first() || q;
  console.log(leftNode);
  return leftNode;
}

// a function ρ: Q → Q
// ρ(q) = δ(q,1), for all q ∈ Q. We call ρ(q) the right child of q
function ρ(q) {
  var rightNode = q.next() || q;
  if (q == xh.root()) {
    rightNode = q;
  }
  console.log(rightNode);
  return rightNode;
}

function δtable(arrayOfNodes) {
  var table = [];
  for (var i = 0; i < arrayOfNodes.length; i++) {
    var node = arrayOfNodes[i];
    console.log(node.name());
    var leftNode = node.first() || node;
    var rightNode = node.next() || node;
    if (i == 0) {
      // this is the root or pointed node; ignore any rightNode node.next()
      rightNode = node;
    }
    var leftPair = [node, LEFT_CHILD_SYMBOL];
    var rightPair = [node, RIGHT_CHILD_SYMBOL];
    var leftRow = [leftPair, leftNode];
    var rightRow = [rightPair, rightNode];
    var column = [leftRow, rightRow]; // a column in the table
    console.log(column);
    table.push(column);
  }
  return table;
}

// xh.xhmath.raghTest01(xh.root(), null);
function raghTest01(aRootNode, labelTemplate) {
  //Q = buildArrayofNodes(aRootNode);
  Q = buildArrayofNodeLabels(aRootNode, labelTemplate);
  Γ[0] = Q;
  console.log(Q);
  console.log(Σ);
  console.log(Γ);
  δ("left", Σ[0]);
  var table = δtable(Q);
  return table;
}

// var table = xh.xhmath.raghTest02();
function raghTest02() {
  if (isSetExtended()) {
    const setQ = asSet(Q);
    const setΣ = asSet(Σ);
    const table = Set.cartesian(setQ, setΣ);
    console.log("raghTest02");
    console.log(table);
    return table;
  }
  return null;
}

function exportToGraphvizBT(aRootNode) {
  xh.xport("GraphvizBT", xh.root(), '{"gvFileExt":".gv","gvGraph":"graph","layout":"dot","edgeOp":"--","gvCluster":"","shouldShowStateMachineEntities":false,'
  + '"filter":"--Behavior,Script","nameTemplateNodeId":"^^^^i^","nameTemplateNodeLabel":"R^^^^^","shouldQuoteLabels":true,"shouldShowLinks":false,'
  + '"shouldShowLinkLabels":false,"shouldSpecifyLayout":false,"maxLabelLen":-1,"shouldColor":true,"defaultNodeColor":"#f0f8ff","bgGraphColor":"white",'
  + '"shouldSpecifyShape":true,"shape":"ellipse","shouldSpecifySize":true,"size":"22","shouldSpecifyFontname":true,"fontname":"Courier",'
  + '"shouldSpecifyArrowhead":true,"arrowhead":"vee","shouldSpecifyStylesheet":true,"stylesheet":"Xholon.css","shouldSpecifyRankdir":false,'
  + '"rankdir":"LR","shouldDisplayGraph":true,"outputFormat":"svg"}');
}

function exportToTableInNewTab(table) {
  xh.xport("_other,NewTab", xh.root(), '{"tabName":"Binary Graph Table"}');
  const xhtabs = document.getElementById("xhtabs");
  const textarea = xhtabs.getElementsByTagName("textarea");
  const mytextarea = textarea[textarea.length-1];
  //mytextarea.textContent = "testing";
  mytextarea.textContent = JSON.stringify(table);
}

export {raghTest01, raghTest02, exportToGraphvizBT, exportToTableInNewTab};


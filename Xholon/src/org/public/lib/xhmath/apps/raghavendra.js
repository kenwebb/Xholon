/**
 * raghavendra.js
 * (C) Ken Webb, MIT license
 * May 28, 2019
 * 
 * see: https://arxiv.org/pdf/math/0301211.pdf
 *  BINARY TREES AND FIBRED CATEGORIES, N. RAGHAVENDRA
 */

//if (typeof window.xh == "undefined") {
//  window.xh = {};
//}

// for now, import everything
import {
  buildArrayofNodes, buildArrayofNodeLabels, buildArrayofPointedNodes,
  buildArrayofPointedNodeLabels, buildArrayofNodesfromPointedNode, buildArrayofNodeLabelsfromPointedNode,
  buildArrayofXhTypeIds, buildArrayofXhTypeLables, buildArrayofXhTypeLablesAlt, buildArrayofXhTypePairs, buildArrayofXhTypePairsAlt,
  asSet, asArrayofInt, asString, asJsonString, asMathString
  } from '/xholon/lib/xhmath/main.js';

// Σ = {0,1}
const LEFT_CHILD_SYMBOL  = 0;
const RIGHT_CHILD_SYMBOL = 1;
const Σ = [LEFT_CHILD_SYMBOL, RIGHT_CHILD_SYMBOL];

// Q is a finite set, whose elements are called nodes.
let Q = null;

/** delta δ
 * δ: Q × Σ → Q is a function, called the transition function.
 * @param q an instance of the set Q
 * @param a an instance of the set Σ
 * @return 
 */
function δ(q, a) {
  console.log("testing function δ ...");
  console.log(q);
  console.log(a);
}

// Definition 2.1. A binary graph is a pair Γ = (Q, δ)
const Γ = [Q, δ];

function raghTest01(aRootNode) {
  Q = buildArrayofNodes(aRootNode);
  Γ[0] = Q;
  console.log(Q);
  console.log(Σ);
  console.log(Γ);
  δ("left", Σ[0]);
}

export {raghTest01};


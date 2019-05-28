/**
 * basicset.js
 * (C) Ken Webb, MIT license
 * May 23, 2019
 * 
 * Build sets (JS arrays) of nodes starting with a specified root node.
 * 
 * TODO:
 * - allow user to specify the order: pre-order, in-order, post-order, level-oreder, etc.
 *  - BUT, since the arrays are intended to represent math Sets, it probably doesn't really matter
 *  - see IXholon/Xholon tree traversal print functions
 *  - see https://en.wikipedia.org/wiki/Tree_traversal
 */

function writeNode(node, arr, labelTemplate) {
  if (labelTemplate == null) {
    arr.push(node);
  }
  else {
    arr.push(node.name(labelTemplate));
  }
  let cnode = node.first();
  while (cnode) {
    writeNode(cnode, arr, labelTemplate);
    cnode = cnode.next();
  }
}

function buildArrayofNodes(root) {
  const arr = [];
  if (root != null) {
    writeNode(root, arr, null);
  }
  return arr;
}

function buildArrayofNodeLabels(root, labelTemplate) {
  const arr = [];
  if (root != null) {
    writeNode(root, arr, labelTemplate);
  }
  return arr;
}

export {buildArrayofNodes, buildArrayofNodeLabels};


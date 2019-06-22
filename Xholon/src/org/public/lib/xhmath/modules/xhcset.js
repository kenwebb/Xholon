/**
 * xhcset.js
 * (C) Ken Webb, MIT license
 * May 25, 2019
 * 
 * Each Xholon node has a direct concrete XholonClass.
 * All nodes that have the same direct XholonClass from a Set.
 * The root XholonClass is called XholonClass.
 * There may be one or more intermediate XholonClass's between the direct XholonClass and the root XholonClass.
 */

function writeNode(node, arr, labelTemplate, xhc, asSuperclass) {
  if (xhc && node.xhc() && ((node.xhc() == xhc) || (asSuperclass && node.hasClass(xhc.name())))) {
    if (labelTemplate == null) {
      arr.push(node);
    }
    else {
      arr.push(node.name(labelTemplate));
    }
  }
  let cnode = node.first();
  while (cnode) {
    writeNode(cnode, arr, labelTemplate, xhc, asSuperclass);
    cnode = cnode.next();
  }
}

function writeXholonClassNode(node, arr, labelTemplate) {
  if (labelTemplate == null) {
    arr.push(node.xhc());
  }
  else {
    arr.push(node.xhc().name());
  }
  let cnode = node.first();
  while (cnode) {
    writeXholonClassNode(cnode, arr, labelTemplate);
    cnode = cnode.next();
  }
}

/**
 * Build and return an array of IXholon nodes that are instances of a specified IXholonClass.
 * @param {IXholon} root A root node.
 * @param {IXholonClass} xhc A XholonClass.
 * @param {boolean} asSuperclass Whether or not to include a node if it has xhc only as a superclass.
 * @return {Array} A set of Xholon node instances.
 */
function buildArrayofClassInstances(root, xhc, asSuperclass) {
  const arr = [];
  if (root != null) {
    writeNode(root, arr, null, xhc, asSuperclass);
  }
  return arr;
}

function buildArrayofClassInstanceLabels(root, labelTemplate, xhc, asSuperclass) {
  const arr = [];
  if (root != null) {
    writeNode(root, arr, labelTemplate, xhc, asSuperclass);
  }
  return arr;
}

/**
 * Build and return an array of IXholonClass nodes.
 * @param {IXholon} root A root node.
 * @return {Array} A multiset of IXholonClass node instances, typically containing duplicate elements.
 */
function buildArrayofXholonClassNodes(root) {
  const arr = [];
  if (root != null) {
    writeXholonClassNode(root, arr, null);
  }
  return arr;
}

function buildArrayofXholonClassNodelabels(root, labelTemplate) {
  const arr = [];
  if (root != null) {
    writeXholonClassNode(root, arr, labelTemplate);
  }
  return arr;
}

export {buildArrayofClassInstances, buildArrayofClassInstanceLabels, buildArrayofXholonClassNodes, buildArrayofXholonClassNodelabels};


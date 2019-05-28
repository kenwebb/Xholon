/**
 * pointedset.js
 * (C) Ken Webb, MIT license
 * May 24, 2019
 * 
 * Build sets (JS arrays) of nodes starting with a specified root node.
 * The overall Xholon pointed set could be just a set of integers (ex: [1,12]),
 * equipped with a mapping from each integer to an element in the set pointed to by the complete set of Xholon nodes,
 * the set pointed to by PE_APP which is the pointed element in the first pointed set.
 * 
 * PE_AVATAR is the only set that is not contained within the PE_APP set. How should I think about this.
 * I should probably leave out PE_AVATAR, since it's not an element in the total set pointed to by (rooted at) PE_APP.
 * 
 * I should distinguish betweeen 2 cases: returning a complete nested subtree, returning only the immediate children.
 * How do I use pointed sets to compose the entire set, without duplications.
 * Perhaps I can build the PE_APP-rooted set/structure from the botttom up.
 *  - start with "leaf" pointed sets
 *  - then construct the next level, tht maybe just composes already-existing pointed sets
 * 
 * How are pointed sets different from operads?
 * I can think about the PE_APP-rooted total set from a pointed set perspective, and simultaneously from an operad perspective (my understanding of what a Spivak operad is).
 * 
 * f: XhPSet --> XhTotalNodeSet
 * 
 * References
 * (1) https://en.wikipedia.org/wiki/Pointed_set
In mathematics, a pointed set (also based set or rooted set) is an ordered pair (X, x0) where X is a set and x0 is an element of X called the base point, also spelled basepoint.
Pointed sets are very simple algebraic structures. In the sense of universal algebra, a pointed set is a set X together with a single nullary operation ∗ : X0 → X , which picks out the basepoint. Pointed maps are the homomorphisms of these algebraic structures.
The class of all pointed sets together with the class of all based maps form a category.
 * (2) https://ncatlab.org/nlab/show/pointed+set
 * (3) https://en.wikipedia.org/wiki/Rooted_graph
In mathematics, and, in particular, in graph theory, a rooted graph is a graph in which one vertex has been distinguished as the root.
Rooted graphs may also be known (depending on their application) as pointed graphs or flow graphs. In some of the applications of these graphs, there is an additional requirement that the whole graph be reachable from the root vertex.
In topological graph theory, the notion of a rooted graph may be extended to consider multiple vertices or multiple edges as roots. The former are sometimes called vertex-rooted graphs in order to distinguish them from edge-rooted graphs in this context. Graphs with multiple nodes designated as roots are also of some interest in combinatorics, in the area of random graphs. These graphs are also called multiply rooted graphs.
A special case of interest are rooted trees, the trees with a distinguished root vertex. If the directed paths from the root in the rooted digraph are additionally restricted to be unique, then the notion obtained is that of (rooted) arborescence—the directed-graph equivalent of a rooted tree. A rooted graph contains an arborescence with the same root if and only if the whole graph can be reached from the root, and computer scientists have studied algorithmic problems of finding optimal arborescences.
Rooted graphs may be combined using the rooted product of graphs.
 * (4) https://ncatlab.org/nlab/show/pointed+object
In a category C with a terminal object 1, a pointed object is an object X equipped with a global element 1→X, often called its basepoint.
 */

import { buildArrayofNodes, buildArrayofNodeLabels } from '/xholon/lib/xhmath/modules/basicset.js';

// const for each pointed element in Xholon
// const pointedElementSet, that's an array of all individual pointed elements

// pointed elements; I can't do these assignments until Xholon starts up
// Application.java stores: appRoot avatar root view xhcRoot srvRoot controlRoot mechRoot avatarContextNode
let PE_APP  = null; // xh.app();
let PE_AVATAR = null; // this is the unique system avatar; it's the only pointed element that might move within the Xholon tree
//let PE_AVATAR_CONTEXT_NODE = null; // NO this may be equal to PE_ROOT

let PE_CONTROL_ROOT = null;
let PE_VIEW = null;
let PE_MODEL = null;

let PE_CSH = null;
let PE_ROOT = null; // xh.root();
let PE_SRV_ROOT = null;

let PE_IH = null;
let PE_XHC_ROOT = null;

let PE_MECH = null;
let PE_MECH_ROOT = null;

let POINTED_ARR = [];

function buildArrayofPointedNodes() {
  if (POINTED_ARR.length == 0) {
    PE_APP = window.xh.app();
    PE_AVATAR = window.xh.avatar();
    //PE_AVATAR_CONTEXT_NODE = window.xh.avatar().obj(); // NO
    PE_CONTROL_ROOT= PE_APP.first();
    PE_VIEW = PE_APP.first().next();
    PE_MODEL = PE_APP.first().next().next();
    PE_CSH = PE_MODEL.first();
    PE_ROOT = window.xh.root();
    PE_SRV_ROOT = PE_ROOT.next();
    PE_IH = PE_MODEL.first().next();
    PE_XHC_ROOT = PE_IH.first();
    PE_MECH = PE_MODEL.first().next().next();
    PE_MECH_ROOT = PE_MECH.first();
    
    POINTED_ARR = [PE_APP, PE_AVATAR, PE_CONTROL_ROOT, PE_VIEW, PE_MODEL, PE_CSH, PE_ROOT, PE_SRV_ROOT, PE_IH, PE_XHC_ROOT, PE_MECH, PE_MECH_ROOT];
  }
  return POINTED_ARR;
}

function buildArrayofPointedNodeLabels(labelTemplate) {
  const pointedArray = buildArrayofPointedNodes();
  const arr = pointedArray.map(node => node.name(labelTemplate));
  return arr;
}

function buildArrayofNodesfromPointedNode(pnode) {
  return buildArrayofNodes(pnode);
}

function buildArrayofNodeLabelsfromPointedNode(pnode, labelTemplate) {
  return buildArrayofNodeLabels(pnode, labelTemplate);
}

export { buildArrayofPointedNodes, buildArrayofPointedNodeLabels, buildArrayofNodesfromPointedNode, buildArrayofNodeLabelsfromPointedNode };


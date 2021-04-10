/*
  process01.js
  Ken Webb, 09 April 2021
  A process for creating an implementation of Xholon.
  
  Binary Tree Node - BTNode
  based on earlier content in xhadhoc.js
  
  name of this version of Xholon:
  XholonCM "Xholon Conceptual Model" see Jen April 9 email
  I can call it: Xholon CM edition
   - I already have these editions: JVM Web GWT, so now there's CM
  
  NOTES:
  - path languages and expressions are extremely important
  
  TODO:
  - basic BTNode should have bt properties: btleft() btright() btparent()
  - then add in the XML first next parent prperties
  - optionally use bt binary numbering for IDs
   - left and right are 0 and 1 (or vice-versa); binary string can be used as a path expression and as node name
  - parent is a shortcut, derived from a path expression composed of btparent() functions
  - btleft === first  btright === next
  
  - write a function that writes out a Xholon-GWT-edition XML string
   - be able to have Xholon read this, and export various formats, such as a graphviz bt graph
*/

// function that returns an empty JavaScript Object
(() => {
  const make = () => {}
  console.log(make())
})()

// function that returns a simple BTNode
(() => {
  const BTNode = (first = null, next = null) => ({first: first, next: next})
  const node = BTNode()
  console.log(node)
})()

// function that returns a true BTNode
(() => {
  const BTNode = (btleft = null, btright = null) => ({btleft: btleft, btright: btright})
  const node = BTNode()
  console.log(node)
})()

// function that returns a simple BTNode + identity function
(() => {
  const BTNode = (parent = null, first = null, next = null) => ({parent: parent, first: first, next: next})
  const identity = node => node
  const node = BTNode()
  console.log(identity(node))
})()

// simple BTNode with identity function + navigation functions + 2 instances combined into a Binary Tree (BT) rooted by node1
(() => {
  const BTNode = (parent = null, first = null, next = null) => ({parent: parent, first: first, next: next})
  const identity = node => node
  const parent = node => node.parent
  const first = node => node.first
  const next = node => node.next
  const node1 = BTNode()
  const node2 = BTNode()
  node1.first = node2
  node2.parent = node1
  console.log(identity(node1))
})()

// more complex BT
(() => {
  const BTNode = (parent = null, first = null, next = null) => ({parent: parent, first: first, next: next})
  const identity = node => node
  const parent = node => node.parent
  const first = node => node.first
  const next = node => node.next
  
  // for now, create a singly-linked list  THIS WORKS
  // this is OK for a linked-list, but won't work as a binary tree which requires a ROOT node with no siblings
  const NUM_NODES = 10
  const HEAD = Array(NUM_NODES)
  .fill(BTNode())
  .reduce((acc, curr, index) => acc.concat(Object.assign({}, curr, {next: acc[index-1], id: (NUM_NODES - index - 1)})), [])
  .reverse()[0]
  console.log(HEAD)
  console.log(next(HEAD))
})()

// path expressions using compose
(() => {
  const BTNode = (parent = null, first = null, next = null) => ({id: 0, parent: parent, first: first, next: next})
  const identity = node => node
  const parent = node => node.parent
  const first = node => node.first
  const next = node => node.next
  const id = node => node.id
  const compose = (...fns) => x => fns.reduceRight((y, f) => f(y), x)
  const trace = node => {console.log(id(node)); return node;}
  
  const NUM_NODES = 10
  const HEAD = Array(NUM_NODES)
  .fill(BTNode())
  .reduce((acc, curr, index) => acc.concat(Object.assign({}, curr, {next: acc[index-1], id: (NUM_NODES - index - 1)})), [])
  .reverse()[0]
  console.log(HEAD)
  console.log(JSON.stringify(HEAD, null, 2))
  console.log(compose(trace, next, trace, next, trace, next, trace)(HEAD))
  
  // http://webgraphviz.com/
  const _graphvizify = node => Number(id(node)).toString() + (next(node) ? " -> " + _graphvizify(next(node)) : "")
  const graphvizify = node => `digraph G {\n  rankdir=LR;\n  ${_graphvizify(node)}\n}\n`
  console.log(graphvizify(HEAD))
})()
/*
{
  "id": 0,
  "parent": null,
  "first": null,
  "next": {
    "id": 1,
    "parent": null,
    "first": null,
    "next": {
      "id": 2,
      "parent": null,
      "first": null,
      "next": {
        "id": 3,
        "parent": null,
        "first": null,
        "next": {
          "id": 4,
          "parent": null,
          "first": null,
          "next": {
            "id": 5,
            "parent": null,
            "first": null,
            "next": {
              "id": 6,
              "parent": null,
              "first": null,
              "next": {
                "id": 7,
                "parent": null,
                "first": null,
                "next": {
                  "id": 8,
                  "parent": null,
                  "first": null,
                  "next": {
                    "id": 9,
                    "parent": null,
                    "first": null
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
*/

/*
digraph G {
  rankdir=LR;
  0 -> 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9
}
*/

// optional ports; this is an example; Ports could be a mixin
// maybe (optionally) put ports of all types inside a "ports" attribute
// ports can be either a path expression or a pointer to a BTNode, or null
(() => {
  const Ports = () => ({
    port: [],
    // Xholon IPort
    // user-defined ports and port arrays
  })
})()

// complete set of attributes in a Xholon node (same for csh and ih nodes)
(() => {
  const Node = () => ({
    btleft: null,
    btright: null,
    btparent: null,
    first: null,
    next: null,
    parent: null,
    btid: "01", // ???
    id: 0,
    name: "", // IH name or CSH roleName
    xhc: null,
    ports: {},
    props: {},
    decorations: {},
    functions: {} // ??? act postConfigure
  })
  
  // is this a mixin?
  const DECORATIONS = {decorations: {color: "red", opacity: 0}}
  
  const node = Object.assign(Node(), DECORATIONS, {name: "Ottawa", ports: {0: "path expression", abc: "def"}})
  
  console.log(node)
})()

// Hello World
(() => {
  const Node = () => ({
    btleft: null,
    btright: null,
    btparent: null,
    first: null,
    next: null,
    parent: null,
    btid: "01",
    id: 0,
    name: "",
    xhc: null,
    ports: {},
    props: {},
    decorations: {},
    functions: {
      act: node => console.log("FUNC ACT: " + name(node)),
      configure: node => "TODO set up all PLaceGraph and LinkGraph connections",
      postConfigure: node => "TODO"
    }
  })
  
  const identity = node => node
  const parent = node => node.parent
  const first = node => node.first
  const next = node => node.next
  const id = node => node.id
  const xhc = node => node.xhc
  const name = node => node.xhc ? (node.name ? (node.name + ":" + xhc(node).name) : xhc(node).name) : node.name
  const act = node => node.functions.act(node)
  
  const DECORATIONS1 = {decorations: {color: "red", opacity: 0}}
  const DECORATIONS2 = {decorations: {color: "blue"}}
  
  const helloworldxhc  = Object.assign(Node(), {name: "HelloWorld"})
  const helloworld = Object.assign(Node(), {first: "./Hello", xhc: helloworldxhc})
  
  const helloxhc  = Object.assign(Node(), DECORATIONS1, {name: "Hello"})
  const hello = Object.assign(Node(), {name: "bonjour", ports: {0: "../World"}, parent: helloworld, next: "../World", xhc: helloxhc, props: {state: 0}})
  
  const worldxhc  = Object.assign(Node(), DECORATIONS2, {name: "World"})
  const world = Object.assign(Node(), {ports: {0: "../Hello"}, parent: helloworld, xhc: worldxhc, props: {state: 0}})
  
  console.log(helloworld)
  console.log(name(helloworld), name(helloworldxhc))
  console.log(name(hello), name(helloxhc))
  console.log(name(world), name(worldxhc))
  
  act(hello)
})()
/* RESULTS
HelloWorld HelloWorld
bonjour:Hello Hello
World World
FUNC ACT: bonjour:Hello
*/



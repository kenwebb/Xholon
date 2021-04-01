/*
  stuff01.js
  Ken Webb, 1 Feb 2021
  Binary Operation, Unit, JavaScript Types, reduce, etc.
  see Feb 1 ideas in my notebook
*/

((title) => {
  console.log(title)
  const DUMMY = [1,2,3,4,5]
  const UNIT = 0;
  const binop = (a, b) => a + b
  const funk1 = arr => arr.reduce((acc, curr) => binop(acc, curr), UNIT)
  console.log(funk1(DUMMY))
})("Array 1")

((title) => {
  console.log(title)
  // the data
  const DUMMY = [1,2,5,3,4]
  // Addition
  const UNIT_ADD = 0;
  const binopAdd = (a, b) => a + b
  // Multiplication
  const UNIT_MULT = 1
  const binopMult = (a, b) => a * b
  // Sorting
  //const UNIT_SORT = "NONE"
  const binopSort = (a, b) => a - b
  // a higher-order function, a machine, a mechanism
  const funk = (binop, unit, data) => data.reduce((acc, curr) => binop(acc, curr), unit)
  const sort = (binop, data) => data.slice().sort(binop)
  // testing
  console.log(funk(binopAdd, UNIT_ADD, DUMMY))
  console.log(funk(binopMult, UNIT_MULT, DUMMY))
  console.log(sort(binopSort, DUMMY))
})("Array 2")

/*
search: javascript binary search
see K&R C p.139
{
  value: 5,
  left: {} // less than or equal
  right: () // greater than
}
*/
// this works
(() => {
  const makenode = value => ({value: value, left: null, right: null})
  const ROOT = makenode(123)
  console.log(ROOT)
  
  const DATA = [5,3,7,1,8,4,3]
  const addnode = (acc, curr) => {const node = makenode(curr); console.log(node); return acc;}
  const test1 = DATA.reduce((acc, curr) => addnode(acc, curr), ROOT)
  
  // from K&R C p.141
  const addtree = (p, w) => {
    if (p === null) {
      p = makenode(w)
    }
    else if (w <= p.value) {
      p.left = addtree(p.left, w)
    }
    else {
      p.right = addtree(p.right, w)
    }
    return p;
  }
  const treeprint = p => {
    if (p !== null) {
      treeprint(p.left)
      console.log(p.value)
      treeprint(p.right)
    }
  }
  const TREE = addtree(null, 11)
  console.log(TREE)
  console.log(addtree(TREE, 12))
  console.log(addtree(TREE, 10))
  console.log(addtree(TREE, 15))
  console.log(addtree(TREE, 14))
  treeprint(TREE)
  
  const arrayify = (p, arr) => {
    if (p !== null) {
      arrayify(p.left, arr)
      arr.push(p.value)
      arrayify(p.right, arr)
    }
    return arr
  }
  console.log(arrayify(TREE, []))
})()

// renamed Binary Search Tree (BST), with sort - this works
(() => {
  const makenode = value => ({value: value, left: null, right: null})
  
  const addtree = (node, value) => {
    if (node === null) {
      node = makenode(value)
    }
    else if (value <= node.value) {
      node.left = addtree(node.left, value)
    }
    else {
      node.right = addtree(node.right, value)
    }
    return node;
  }

  const arrayify = (node, arr) => {
    if (node !== null) {
      arrayify(node.left, arr)
      arr.push(node.value)
      arrayify(node.right, arr)
    }
    return arr
  }
  
  const sort = arr => arrayify(arr.reduce((acc, curr) => addtree(acc, curr), null), [])
  
  const DATA = [5,3,7,1,8,4,3]
  //const TREE = DATA.reduce((acc, curr) => addtree(acc, curr), null)
  //console.log(TREE)
  //console.log(arrayify(TREE, []))
  
  console.log(sort(DATA))
})()

// better binary search tree - partly works
(() => {
  //const makenode = value => ({value: value, left: null, right: null})
  const makenode = value => ({value: value, left: {}, right: {}})
  
  // NO
  /*const addtree = (node, value) => 
    node === null ? makenode(value)
    : value <= node.value ? {node.left = addtree(node.left, value); return node;}
    : {node.right = addtree(node.right, value); return node;}
  */
  // YES
  /*const addtree = (node, value) => {
    if (node === null) {
      node = makenode(value)
    }
    else if (value <= node.value) {
      node.left = addtree(node.left, value)
    }
    else {
      node.right = addtree(node.right, value)
    }
    return node;
  }*/
  // MAYBE ???
  // Object.values(
  const addtree = (node, value) => 
    node === null || Object.values(node).length === 0 ? makenode(value)
    : value <= node.value ? Object.assign(node.left, addtree(node.left, value))
    : Object.assign(node.right, addtree(node.right, value))

  const arrayify = (node, arr) => {
    if (node !== {}) {
      arrayify(node.left, arr)
      arr.push(node.value)
      arrayify(node.right, arr)
    }
    return arr
  }
  
  const DATA = [5,3,7,1,8,4,3]
  const TREE = DATA.reduce((acc, curr) => addtree(acc, curr), null)
  console.log(TREE)
  console.log(arrayify(TREE, []))
})()

// sort an array, using Binary Search Tree (BST), compose - this works
((data) => {
  const makenode = value => ({value: value, left: null, right: null})
  
  const compareLE = (a, b) => a <= b
  
  const _addtree = comparator => (node, value) => {
    if (node === null) {
      node = makenode(value)
    }
    else if (comparator(value, node.value)) {
      node.left = addtree(node.left, value)
    }
    else {
      node.right = addtree(node.right, value)
    }
    return node;
  }
  const addtree = _addtree(compareLE)
  
  const arr2bst = arr => arr.reduce((tree, value) => addtree(tree, value), null)

  const bst2arr = arr => node => {
    if (node !== null) {
      bst2arr(arr)(node.left)
      arr.push(node.value)
      bst2arr(arr)(node.right)
    }
    return arr
  }
  
  const compose = (...fns) => x => fns.reduceRight((y, f) => f(y), x)
  const sort = arr => compose(bst2arr([]), arr2bst)(arr)
  
  console.log(data, sort(data))
})([5,3,7,1,8,4,3]) // OR ["one","two","three"]

// once again  TODO this works, but requires some fixing
// [Number] -step1-> [Object] -step2-> Tree -step3-> [Number]
((data) => {
  // Step 1 - [Number] -> [Object]
  const makenode = value => ({value: value, left: null, right: null})
  const makeobjarr = arr => arr.map((item) => makenode(item))
  console.log(data, makeobjarr(data))
  
  // Step 2 - [Object] -> [Object] where objects have left and right links to each other
  const compareLE = (a, b) => a <= b
  
  // (parent-node, child-node) -> updated parent-node
  const _treeify = comparator => (pnode, cnode) => {
    if (pnode === null) {
      pnode = cnode;
    }
    else if (pnode === cnode) {} // pnode and cnode are both the root node
    else if (comparator(cnode.value, pnode.value)) {
      pnode.left = treeify(pnode.left, cnode)
    }
    else {
      pnode.right = treeify(pnode.right, cnode)
    }
    return pnode;
  }
  const treeify = _treeify(compareLE)
  
  const arr2bst = arr => arr.map((item, index, arr) => {treeify(arr[0], item); return item;})
  
  // Step 3 - [Object] -> [Number]
  const bst2arr = arr => node => {
    if (node !== null) {
      bst2arr(arr)(node.left)
      arr.push(node.value)
      bst2arr(arr)(node.right)
    }
    return arr
  }
  
  const trace = arr => {console.log(arr); return arr;}
  const indexzero = arr => arr[0]
  const compose = (...fns) => x => fns.reduceRight((y, f) => f(y), x)
  const sort = arr => compose(bst2arr([]), trace, indexzero, arr2bst, makeobjarr)(arr)
  
  console.log(data, sort(data))
  
})([5,3,7,1,8,4,3]) // OR ["one","two","three"]

// random numbers
(() => {
  const _rand = min => max => () => Math.floor(Math.random() * (max - min)) + min
  const rand = _rand(1)(10)
  console.log(rand())
  
  const multirand = count => {
    const arr = []
    for (var i = 0; i < count; i++) {
      arr.push(rand())
    }
    return arr
  }
  
  console.log(multirand(100))
})()

// random numbers 2
// using recursion
// TODO use new JS tail recursion; not yet available
((min, max, count) => {
  const _rand = min => max => () => Math.floor(Math.random() * (max - min)) + min
  const rand = _rand(min)(max)
  const multirand = (count, arr) => count === 0 ? arr : multirand(--count, arr.concat(rand()))
  
  console.log(rand())
  console.log(multirand(count, []))
})(1, 10, 100)

// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/from
// Using arrow functions and Array.from()
//Array.from({length: 5}, (v, i) => i); // [0, 1, 2, 3, 4]
((min, max, count) => {
  const _rand = min => max => () => Math.floor(Math.random() * (max - min)) + min
  const rand = _rand(min)(max)
  const multirand = count => Array.from({length: count}, () => rand())
  
  console.log(rand())
  console.log(multirand(count))
})(2, 9, 100)

// TODO try this
// https://stackoverflow.com/questions/14810506/map-function-for-objects-instead-of-arrays



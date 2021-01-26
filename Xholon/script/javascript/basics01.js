// basics01.js
// JS snippets

(() => {
  // useful utilities that will not be explained just yet
  const compose = (...fns) => x => fns.reduceRight((y, f) => f(y), x)
  const pipe = (...fns) => x => fns.reduce((y, f) => f(y), x)
  
  // conjure something out of nothing, and give the something a name
  const hello = () => "hello" // or use shorthand: const hello = "hello"
  const world = () => "world"
  const sep = () => " "
  const join = (s, t) => s() + sep() + t
  console.log(join(hello, world()))
  
  const mercury = (() => "Mercury")()
  console.log(mercury)
  
  const one = () => 1
  const thirteen = () => 13
  console.log(one() + thirteen())
  
  const truer = () => true
  const obj = () => {}
  const arr = () => []
  
  const join2 = (sepp) => (s, t) => s() + sepp + t()
  const join22 = join2(" ")
  console.log(join22(hello, world))
  
  //compose(world, hello)() // NO  use reduce()
})()

// OR data starts with "_" and function starts with a lower-case letter
(() => {
const _hello = "hello"
const _world = "world"
const _sep = " "
const _mercury = "Mercury"
const _one = 1
const _thirteen = 13
const _truer = true
const _obj = {}
const _arr = []
const join = (sepp) => (s, t) => s + sepp + t
})()

// or DATA function
(() => {
  const HELLO = "hello"
  const WORLD = "world"
  const SEP = " "
  const join = (sep) => (s, t) => s + sep + t
  //const join = (sep) => (s, t) => `${s}${sep}${t}`
  const join2 = join(SEP)
  console.log(join(SEP)(HELLO, WORLD))
  console.log(join2(HELLO, WORLD))
  const HELLO_WORLD = join2(HELLO, WORLD)
  console.log(HELLO_WORLD)
})()

// or
((title) => {
  console.log(title)
  const HELLO = "hello"
  const WORLD = "world"
  const SEP = " "
  const join = (sep) => (s, t) => s + sep + t
  //const join = (sep) => (s, t) => `${s}${sep}${t}`
  const join2 = join(SEP)
  console.log(join(SEP)(HELLO, WORLD))
  console.log(join2(HELLO, WORLD))
  const HELLO_WORLD = join2(HELLO, WORLD)
  console.log(HELLO_WORLD)
})("exercise: Print \"hello world\"")

// mappings
((title) => {
  console.log(title)
  const STR = "The furry fox scrambled under the giant chicken."
  console.log(STR)
  const ARR = STR.split()
  console.log(ARR)
  const ARR2 = STR.split("")
  console.log(ARR2)
  const NUM = STR.length
  const BOOL = STR.length == ARR.length
  
  // functions
  const str2arrtext = (str) => str.split() // the complete sentence
  const str2arrwords = (str) => str.split(" ")
  const str2arrchars = (str) => str.split("")
  const str2length = (str) => str.length
  const arr2length = (arr) => arr.length
  console.log("This text contains " + arr2length(str2arrtext(STR)) + " sentence.")
  console.log("This text contains " + arr2length(str2arrwords(STR)) + " words.")
  console.log("This text contains " + arr2length(str2arrchars(STR)) + " characters.")
})("Mappings: 1 to 1")

// nested JSO or JSON to [Object]
((title) => {
  console.log(title)
  const JSO = {one: "Un", two: "Deux", three: {four: "Quatre"}}
  const ARR = [JSO, JSO.one, JSO.two, JSO.three, JSO.three.four]
  console.log(JSO.three)
  console.log(ARR[3])
  const JSONN = JSON.stringify(JSO)
  console.log(JSONN)
  const PATH = ["three", "four"]
  const move = (obj, item) => obj[item]
  const query = (obj, path) => path.reduce(move, obj)
  console.log(query(JSO, PATH))
})("nested JSO or JSON to [Object]")

// switch
((title) => {
  console.log(title)
  const one = (a, b) => a + b
  const two = (a, b) => a * b
  const three = (a, b) => a - b
  const nine = (op, a, b) => {switch(op) {
    case "one": return one(a, b);
    case "two": return two(a, b);
    case "three": return three(a, b);
    default: return null;}
  }
  console.log(nine("one", 5, 6))
  console.log(nine("two", 5, 6))
  console.log(nine("three", 5, 6))
  console.log(nine("one", "hello ", "world!"))
})("switch")

// ? :
((title) => {
  console.log(title)
  const ONE = "one"
  const TWO = "two"
  const THREE = "three"
  const ZILCH = ""
  const one = (a, b) => a + b
  const two = (a, b) => a * b
  const three = (a, b) => a - b
  const nine = (op, a, b) => op == ONE ? one(a, b) : op == TWO ? two(a, b) : op == THREE ? three(a, b) : null
  console.log(nine(ONE, 5, 6))
  console.log(nine(TWO, 5, 6))
  console.log(nine(THREE, 5, 6))
  console.log(nine(ONE, "hello ", "world!"))
  console.log(ZILCH)
  console.log(ONE)
})("? :")

// TEMPLATES
((title) => {console.log(title)})("TITLE")
((title) => {
  console.log(title)
  // TODO
})("TITLE")

// reduce
// google: javascript reduce
// https://www.freecodecamp.org/news/reduce-f47a7da511a9/
// the author at freecodecamp writes: "Everything in this post came from a fantastic video series on egghead called Introducing Reduce. I give Mykola Bilokonsky full credi"
// https://egghead.io/courses/reduce-data-with-javascript-array-reduce
((title) => {
  console.log(title)
  
  // 1 A Basic Reduction - my version
  const EUROS = [29.76, 41.85, 46.5]
  const sum = arr => arr.reduce((total, amount) => total + amount, 0)
  console.log(sum(EUROS)) // 118.11
  
  // 2 Finding an Average with the Reduce Method In JavaScript - my version
  //const EUROS = [29.76, 41.85, 46.5]
  //const sum = (arr) => arr.reduce((total, amount) => total + amount, 0)
  const average = arr => sum(arr) / arr.length
  console.log(average(EUROS)) // 39.37
  
  // 3 Creating a Tally with the Reduce Method In JavaScript
  const FRUIT_BASKET = "banana,cherry,orange,apple,cherry,orange,apple,banana,cherry,orange,fig".split(",")
  const tally = arr => arr.reduce((tally, fruit) => {
    tally[fruit] = (tally[fruit] || 0) + 1
    return tally
  } , {})
  console.log(tally(FRUIT_BASKET)) // { banana: 2, cherry: 3, orange: 3, apple: 2, fig: 1 }
  
  // 4 Flattening an array of arrays with the Reduce Method In JavaScript​​
  const DATA = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
  const flat = data => data.reduce((total, amount) => total.concat(amount), [])
  console.log(flat(DATA)) // [ 1, 2, 3, 4, 5, 6, 7, 8, 9 ]
  
  // 5 Piping with Reduce
  // "An interesting aspect of the reduce method in JavaScript is that you can reduce over functions as well as numbers and strings."
  // this is my version of it
  const increment = input => input + 1
  const decrement = input => input - 1
  const double = input => input * 2
  const halve = input => input / 2
  const PIPELINE = [increment, increment, increment, double, decrement, halve]
  const pipe = pipeline => pipeline.reduce((total, func) => func(total), 1)
  console.log(pipe(PIPELINE)) // 3.5
  
})("reduce A")

// https://stackoverflow.com/questions/15748656/javascript-reduce-on-object
((title) => {
  console.log(title)
  const OBJ = {a: {value:1}, b: {value:2}, c: {value:3}}
  
  // 1 
  const TOTAL = Object.values(OBJ).reduce((t, {value}) => t + value, 0)
  console.log(TOTAL) // 6
  const total = obj => Object.values(obj).reduce((t, {value}) => t + value, 0)
  console.log(total(OBJ)) // 6
  
  // 2 
  const OBJ2 = {a: 1, b: 2, c: 3}
  const TOTAL2 = Object.values(OBJ2).reduce((t, n) => t + n)
  console.log(TOTAL2) // 6
  const total2 = obj => Object.values(obj).reduce((t, n) => t + n)
  console.log(total2(OBJ2)) // 6
  
  // 3
  const TOTAL3 = Object.keys(OBJ).reduce((t, key) => t + OBJ[key].value, 0)
  console.log(TOTAL3) // 6
  const total3 = obj => Object.keys(obj).reduce((t, key) => t + obj[key].value, 0)
  console.log(total3(OBJ)) // 6
  
  // 4 
  const TOTAL4 = Object.entries(OBJ).reduce((t, pair) => {
    const [key, value] = pair;
    return t + value.value;
  }, 0)
  console.log(TOTAL4) // 6
  const total4 = obj => Object.entries(obj).reduce((t, pair) => {
    const [key, value] = pair;
    return t + value.value;
  }, 0)
  console.log(total4(OBJ)) // 6
  
  // 5 
  const TOTAL5 = Object.values(OBJ).map(a => a.value, OBJ).reduce((ac, key) => ac += key)
  console.log(TOTAL5) // 6
  const total5 = obj => Object.values(obj).map(a => a.value, obj).reduce((ac, key) => ac += key)
  console.log(total5(OBJ)) // 6
  
})("Javascript reduce() on Object")

// https://www.youtube.com/watch?v=kC3AasLEuBA
((title) => {
  console.log(title)
  const SCORES = [90, 30, 20, 75, 85, 95, 0, 55, 60, 40]
  const minMax = scores => scores.reduce((acc, score) => [Math.min(acc[0], score), Math.max(acc[1], score)], [100,0])
  console.log(minMax(SCORES))
})("Min Max in an array of numbers")

((title) => {
  console.log(title)
  const ROOT = xh.root()
  const ROOT_ARR = ROOT.subtreeAsArray()
  const name = node => node.name()
  const id = node => node.id()
  const xhcname = node => node.xhc().name()
  const showarr = func => xharr => xharr.reduce((acc, node) => acc.concat(func(node)), [])
  const showcsv = func => xharr => xharr.reduce((acc, node) => acc + func(node) + ",", "")
  const shownames = showarr(name)
  const showids = showarr(id)
  const shownamescsv = showcsv(name)
  console.log(shownames(ROOT_ARR))
  console.log(showids(ROOT_ARR))
  console.log(shownamescsv(ROOT_ARR))
  console.log(showarr(xhcname)(ROOT_ARR))
})("Xholon subtrees")

// TODO ???
((title) => {
  console.log(title)
  const ROOT = xh.root()
  const PATH = [first, next]
  const first = node => node.first()
  const next = node => node.next()
  const move = (func, node) => func(node)
  const query = (node, path) => path.reduce(move, node)
  console.log(query(ROOT, PATH))
})("Xholon paths")

// lists
// https://blog.jeremyfairbank.com/javascript/functional-javascript-lists-1/
//  - list as a tree of nodes that each have a head tail and value; cons
((title) => {
  console.log(title)
  // var list = new Cons(1, new Cons(3, new Cons(42, new Cons(28, Nil))));
  // var list = cons(1, cons(3, cons(42, cons(28, Nil))));
  const XHLIST = `<List>
  <Cons><Attribute_int>1</Attribute_int></Cons>
  <Cons><Attribute_int>3</Attribute_int></Cons>
  <Cons><Attribute_int>42</Attribute_int></Cons>
  <Cons><Attribute_int>28</Attribute_int></Cons>
  </List>`
  const ROOT = xh.root()
  
  // functions that can be used with Cons nodes
  const head = $this => $this.first()
  const tail = $this => $this.next()
  const isEmpty = $this => $this.isEmpty = false
  const val = $this => $this.val()
  console.log(head(ROOT).name())
  //console.log(tail(ROOT))
  
  ROOT.append(XHLIST)
  const LIST = ROOT.last()
  /*
  console.log(LIST.name())
  console.log(head(LIST).name())
  console.log(head(head(LIST)).name())
  console.log(head(head(LIST)).val())
  console.log(head(tail(head(LIST))).val())
  console.log(head(tail(tail(head(LIST)))).val())
  console.log(head(tail(tail(tail(head(LIST))))).val())
  */
  
  const pipe = (...fns) => x => fns.reduce((y, f) => f(y), x)
  const LIST_HEAD = head(LIST)
  console.log(pipe(head, val)(LIST_HEAD))
  console.log(pipe(tail, head, val)(LIST_HEAD))
  console.log(pipe(tail, tail, head, val)(LIST_HEAD))
  console.log(pipe(tail, tail, tail, head, val)(LIST_HEAD))
  
})("lists - Jeremy Fairbank approach, applied to Xholon")

// recursion
((title) => {
  console.log(title)
  const fib = n => n < 2 ? n : fib(n - 1) + fib(n - 2)
  console.log(fib(10))
})("fib")

// streams
// https://blog.jeremyfairbank.com/javascript/functional-javascript-streams-2/

// The Number Line
// see my notebook notes for January 8, 2020
// see https://en.wikipedia.org/wiki/Number_line
// see my Mathematics Encyclopedia
// see my Lakoff/Nunez book "Where mathematics comes from" p. 71
//   Arithmetic as motion along a path
// positive and negative integers, base case is 0
// The addition 1+2 on the real number line
((title) => {
  console.log(title)
  const BASE_CASE = 0
  const UNIT = 1
  const NUMBER_LINE = "__________".split("")
  console.log(NUMBER_LINE) // [ "_", "_", "_", "_", "_", "_", "_", "_", "_", "_" ]
  //const NUMBER_ARR = NUMBER_LINE.reduce((acc, current) => current + 1, [])
  //console.log(NUMBER_ARR)
  const ONE = ["_"]
  const TWO = ["_","_"]
  const RESULT = TWO.reduce((acc, current) => acc + 1, ONE.reduce((acc, current) => acc + 1, BASE_CASE))
  console.log(RESULT)
  console.log(ONE.concat(TWO))
  console.log(ONE.concat(TWO).join(""))
  console.log(ONE.concat(TWO).length)
  
  // [String] -> [Number]
  const NUMBERS = NUMBER_LINE.map((item, index) => index)
  console.log(NUMBERS) // [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ]
  
  // or use pipe/compose
  const pipe = (...fns) => x => fns.reduce((y, f) => f(y), x)
  //const count = basecase => arr => TODO
  //const RESULT2 = pipe(count(ONE), count(TWO))(BASE_CASE)
})("Number Line")

// "0_1_2_3_4_5"
((title) => {
  console.log("0123456789".split("").join("_"))
  const INTEGERS = "0123456789"
  const PATH = "_"
  console.log(INTEGERS.split("").join(PATH))
})("Number Line")

// Xholon XPath
// "*/../*[2]" has three path expressions, delimited by places,nodes
// a path expression is a funciion (declares a function), and each delimiter "/" is an Object instance of type IXholon (a node)
((title) => {
  console.log(title)
  const ROOT = xh.root()
  //const PATHS = ["*", "..", "*[2]"] // []  ["."]  ["*"]  ["*", ".."]  ["Hello", "..", "World"]
  const PATHS = "*/../*[2]".split("/")
  console.log(PATHS)
  const NODE = PATHS.reduce((node, pathexpr) => node.xpath(pathexpr), ROOT)
  console.log(NODE === null ? null : NODE.name()) // world_3
})("Xholon XPath")

// Xholon Avatar
((title) => {
  console.log(title)
  const ROOT = xh.root()
  const AVATAR = xh.avatar()
  const PATHS = "first;next".split(";")
  console.log(PATHS)
  PATHS.reduce((ava, pathexpr) => ava.action(pathexpr), AVATAR)
  // Entered hello_2
  // Moving to world_3
  console.log(AVATAR.action("where")) // You are in world_3.
})("Xholon Avatar")

// generate a number line corresponding to an arrayification of a Xholon CSH tree or subtree
// "0_1_2_3_4_5_SIZE_OF_SUBTREE"
// with Hello World model: 0_1_2
// with Cell model: 0_1_2_3_4_5_6_7_8_9_10_11_12_13_14_15_16_17_18_19_20_21_22_23_24_25_26_27_28
// the paths are the underscores "_", while the nodes are the integers
((title) => {
  console.log(title)
  const ROOT = xh.root()
  const LEN_SUBTREE = ROOT.numChildren(true)
  console.log(LEN_SUBTREE) // 3
  const ARRAY = new Array(LEN_SUBTREE).fill(0)
  const NUMBER_LINE = ARRAY.reduce((acc, curr, index) => acc + "_" + (index+1), "0") // "0" represents the ROOT node
  console.log(NUMBER_LINE)
  
  // another way to do it
  //const ROOT_ARR = ROOT.subtreeAsArray() // NO includes the root node
  //const ROOT_ARR = ROOT.childrenAsArray() // NO it only gets immediate children
  const ROOT_ARR = ROOT.subtreeAsArray().slice(1)
  console.log(ROOT_ARR.length)
  console.log(ROOT_ARR)
  const NUMBER_LINE2 = ROOT_ARR.reduce((acc, curr, index) => acc + "_" + (index+1), "0") // "0" represents the ROOT node
  console.log(NUMBER_LINE2)
})("Number Line")

// Mud Lake
// places are the signposts and other locations: A B ... L
// paths are the paths between places: A_B  B_C  ...
((title) => {
  // nodes
  const A = {}
  const B = {}
  const C = {a: A}
  // TODO consider using symbols?
  // paths
  A.b = B
  B.a = A
  B.c = C
  console.log(A)
  // put all the signposts into a common structure so the code can readily find them all, in this case using an ordered array
  // BUT the array doesn't include the names A B C
  const SIGNPOSTSarr = [A, B, C]
  console.log(SIGNPOSTSarr)
  const SIGNPOSTS = {A:A,B:B,C:C}
  console.log(SIGNPOSTS)
  // TODO function to write out GraphViz DOT program
  // see: https://stackoverflow.com/questions/15748656/javascript-reduce-on-object
  // see examples from this, elsewhere in this file (basics01.js)
  //   const total3 = obj => Object.keys(obj).reduce((t, key) => t + obj[key].value, 0)
  //   const TOTAL4 = Object.entries(OBJ).reduce((t, pair) => ...
  //const GV = SIGNPOSTS.reduce((acc, curr) => acc + "\n" + curr, "") // NO
  // TODO
  const GV = Object.entries(SIGNPOSTS).reduce((t, pair) => t + "\n" + pair[0] + " -> " + pair[1], "") // A -> [object Object]
  console.log(GV)
})("Mud Lake")

// Binary Tree with Pairs
((title) => {
  const LEFT = 0 // first
  const RIGHT = 1 // next
  
  const HelloWorld = {id: 11}
  const Hello = {id: 12}
  const World = {id: 13}
  const Universe = {id: 14}
  const pair = (left, right) => (m => m === LEFT ? left : m === RIGHT ? right : console.error(m, "argument not 0 or 1 -- pair"))
  
  HelloWorld.p = pair(Hello, null)
  console.log(HelloWorld)
  console.log(HelloWorld.p(LEFT))
  console.log(HelloWorld.p(RIGHT))
  //console.log(HelloWorld.p(2)) // an error
  
  Hello.p = pair(null, World)
  console.log(Hello)
  console.log(Hello.p(LEFT))
  console.log(Hello.p(RIGHT))
  
  World.p = pair(null, Universe)
  console.log(World)
  console.log(World.p(LEFT))
  console.log(World.p(RIGHT))
  
  console.log(HelloWorld.p(LEFT).p(RIGHT).p(RIGHT))
  
})("Binary Tree with Pairs")

// TODO build a tree from the Xholon model
// how to obtain parent and xhc of a node ?
// should LEFT be 0 or 1; see my binary tree stuff
((title) => {
  console.log(title)
  const LEFT = 0 // first
  const RIGHT = 1 // next
  const PARENT = 2 // parent
  const XHC = 3 // xhc
  const PORTS = 4 // ports
  
  // IH  types
  const XholonClassIH = {id: 0, name: "XholonClass"}
  const HelloWorldIH = {id: 1, name: "HelloWorld"}
  const HelloIH = {id: 2, name: "Hello"}
  const WorldIH = {id: 3, name: "World"}
  const UniverseIH = {id: 4, name: "Universe"}
  const linksIH = (left, right, parent) => (m => m === LEFT ? left : m === RIGHT ? right : m === PARENT ? parent : console.error(m, "ERROR -- linksIH"))
  const nameIH = (node) => node.name
  XholonClassIH.l = linksIH(HelloWorldIH, null, null)
  HelloWorldIH.l = linksIH(null, HelloIH, XholonClassIH)
  HelloIH.l = linksIH(null, WorldIH, XholonClassIH)
  WorldIH.l = linksIH(null, UniverseIH, XholonClassIH)
  UniverseIH.l = linksIH(null, null, XholonClassIH)
  
  // CSH
  const HelloWorld = {id: 11}
  const Hello = {id: 12, role: "Bonjour"}
  const World = {id: 13, role: "Earth"}
  const Universe = {id: 14, role: "Milky Way"}
  const links = (left, right, parent, xhc, ports) => (m => m === LEFT ? left : m === RIGHT ? right : m === PARENT ? parent : m === XHC ? xhc : m === PORTS ? ports : console.error(m, "ERROR -- links"))
  const role = (node) => node.role ? node.role + ":" : ""
  const name = (node) => role(node) + node.l(XHC).name + "_" + node.id
  
  HelloWorld.l = links(Hello, null, null, HelloWorldIH, [])
  console.log(HelloWorld)
  console.log(HelloWorld.l(LEFT))
  console.log(HelloWorld.l(RIGHT))
  console.log(HelloWorld.l(PARENT))
  console.log(HelloWorld.l(XHC))
  console.log(HelloWorld.l(PORTS))
  //console.log(HelloWorld.l(5)) // an error
  
  Hello.l = links(null, World, HelloWorld, HelloIH, [World, Universe])
  console.log(Hello)
  console.log(Hello.l(LEFT))
  console.log(Hello.l(RIGHT))
  console.log(Hello.l(PARENT))
  console.log(Hello.l(XHC))
  console.log(Hello.l(PORTS))
  console.log(Hello.l(PORTS)[0])
  //console.log(Hello.l(XHC).name + "_" + Hello.id)
  console.log(name(Hello)) // Hello_12
  
})("Xholon-like Binary Tree")

// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Closures
/*
function makeAdder(x) {
  return function(y) {
    return x + y;
  };
}

var add5 = makeAdder(5);
var add10 = makeAdder(10);

console.log(add5(2));  // 7
console.log(add10(2)); // 12
*/
((title) => {
  console.log(title);
  const makeAdder = x => y => x + y;
  const add5 = makeAdder(5);
  const add10 = makeAdder(10);
  console.log(add5(2));  // 7
  console.log(add10(2)); // 12
})("Closures 1")

// search: javascript+module+pattern
// https://coryrylan.com/blog/javascript-module-pattern-basics
((title) => {
  var myModule = (function() {
    'use strict';

    var _privateProperty = 'Hello World';
    var publicProperty = 'I am a public property';

    function _privateMethod() {
      console.log(_privateProperty);
    }

    function publicMethod() {
      _privateMethod();
    }

    return {
      publicMethod: publicMethod,
      publicProperty: publicProperty
    };
  })();

  myModule.publicMethod(); // outputs 'Hello World'
  console.log(myModule.publicProperty); // outputs 'I am a public property'
  console.log(myModule._privateProperty); // is undefined protected by the module closure
  myModule._privateMethod(); // is TypeError protected by the module closure
})("Modules 1")

var kenzModule = ((title) => {
  console.log(title)
  const myModule = (() => {
    const _privateProperty = 'Hello World';
    const publicProperty = 'I am a public property';
    const _privateMethod = () => console.log(_privateProperty);
    const publicMethod = () => _privateMethod();
    return {
      publicMethod: publicMethod,
      publicProperty: publicProperty
    };
  })();
  
  myModule.publicMethod(); // outputs 'Hello World'
  console.log(myModule.publicProperty); // outputs 'I am a public property'
  console.log(myModule._privateProperty); // is undefined protected by the module closure
  myModule._privateMethod(); // is TypeError protected by the module closure
})
kenzModule("Modules 2")

// MODULES ----------------------------------------
((title) => {
  console.log(title)
  const module1 = (() => ({add: (x, y) => x + y, mul: (x, y) => x * y}))()
  const module2 = (() => ({inc: x => ++x, dec: x => --x}))()
  console.log(module1)
  console.log(module2)
  console.log(module1.add(5, 17))
  console.log(module1.mul(5, 12))
  console.log(module2.inc(10))
  console.log(module2.dec(10))
  
  // another approach
  const {add, mul} = (() => ({add: (x, y) => x + y, mul: (x, y) => x * y}))()
  const {inc, dec} = (() => ({inc: x => ++x, dec: x => --x}))()
  console.log(add(6, 17))
  console.log(mul(6, 12))
  console.log(inc(43))
  console.log(dec(43))
})("Modules 3")

// create 2 modules; pass some of their functions into a third module
((title) => {
  console.log(title)
  const mone = (() => ({add: (x, y) => x + y, mul: (x, y) => x * y}))()
  const mtwo = (() => ({inc: x => ++x, dec: x => --x}))()
  const mthree = ((m1, m2) => {
    const {mul} = m1
    const {inc} = m2
    console.log(inc(55))
    console.log(mul(55, 2))
  })(mone, mtwo)
})("Modules 4")

// Xholon <script> with an arrow function
// this works when dragged into a Xholon node
// "this" is the script node
<script one="un" two="deux">
((title, three) => {
  this.println(title)
  this.println(this)
  this.println(this.one + " " + this.two + " " + three)
  this.println(this.parent().name())
})("Xholon script with an arrow function", "trois")
</script> ///

// this can be dragged into a Xholon node if FreeformText2XmlString.js is included as part of the URL
// http://127.0.0.1:8080/war/Xholon.html?app=HelloWorld&gui=clsc&jslib=FreeformText2XmlString
((title, one, two, three) => {
  this.println(title)
  this.println(this)
  this.println(one + " " + two + " " + three)
  this.println(this.parent().name())
})("Xholon script with an arrow function", "un", "deux", "trois")

// Nested functions - this version works (most console.log are commented out)
((title, one) => {
  //console.log(title)
  //console.log(one)
  ((two) => {
    //console.log(one + " " + two)
    ((three) => {
      console.log(one + " " + two + " " + three) // un deux trois
    })("trois")
  })("deux")
})("Nested functions", "un")

// Nested functions - NOTE all the console.log statements must be terminated by ";"
((title, one) => {
  console.log(title); // Nested functions
  console.log(one); // un
  console.log(this); // Window http://127.0.0.1:8080/war/Xholon.html?app=HelloWorld&gui=clsc&jslib=FreeformText2XmlString
  ((two) => {
    console.log(one + " " + two); // un deux
    ((three) => {
      console.log(one + " " + two + " " + three); // un deux trois
      console.log(this); // Window h...
    })("trois")
  })("deux")
})("Nested functions", "un")

// "Could not parse XML document:" until I removed the "&" characters
// this works now
<script>
((title, one) => {
  console.log(title); // Nested functions
  console.log(one); // un
  console.log(this); // the Xholon script node
  ((two) => {
    console.log(one + " " + two); // un deux
    ((three) => {
      console.log(one + " " + two + " " + three); // un deux trois
      console.log(this); // the Xholon script node
      console.log(this.parent()); // the parent of the Xholon script node
    })("trois")
  })("deux")
})("Nested functions", "un")
</script>
///

// this works in Dev Tools, and dragged into a Xholon app
((title, one) => {
  const println = str => this.xhc ? this.println(str) : console.log(str);
  println(title); // Nested functions
  println(one); // un
  println(this); // the Xholon script node, or Window
  ((two) => {
    println(one + " " + two); // un deux
    ((three) => {
      println(one + " " + two + " " + three); // un deux trois
      println(this); // the Xholon script node, or Window
      if (this.xhc) {println(this.parent());} // the parent of the Xholon script node
    })("trois")
  })("deux")
})("Nested functions", "un")

// root does NOT have a "this"
var root = function(title, one) {
  const println = str => this.xhc ? this.println(str) : console.log(str);
  println(title); // Nested functions
  println(one); // un
  println(this); // the Xholon script node, or Window
  const left = ((two) => {
    println(one + " " + two); // un deux
    const left = ((three) => {
      println(one + " " + two + " " + three); // un deux trois
      println(this); // the Xholon script node, or Window
      if (this.xhc) {println(this.parent());} // the parent of the Xholon script node
    })("trois")
    const right = ((four) => {
      println(one + " " + two + " " + four); // un deux 
      println(this); // the Xholon script node, or Window
      if (this.xhc) {println(this.parent());} // the parent of the Xholon script node
    })("quatre")
  })("deux")
}
root("Nested functions", "un")
console.log(root)

//
//function root(title, one) {
var root = (function(title, one) {
  const println = str => this.xhc ? this.println(str) : console.log(str);
  println(title); // Nested functions
  println(one); // un
  println(this); // the Xholon script node, or Window
  const left = ((two) => {
    println(one + " " + two); // un deux
    const left = ((three) => {
      println(one + " " + two + " " + three); // un deux trois
      println(this); // the Xholon script node, or Window
      if (this.xhc) {println(this.parent());} // the parent of the Xholon script node
    })("trois")
    const right = ((four) => {
      println(one + " " + two + " " + four); // un deux 
      println(this); // the Xholon script node, or Window
      if (this.xhc) {println(this.parent());} // the parent of the Xholon script node
    })("quatre")
  })("deux")
})
root("Nested functions", "un")
console.log(root)

// this works
var root = (function(title, one, $this) {
  const println = str => this.xhc ? this.println(str) : console.log(str);
  println(title); // Nested functions
  println(one); // un
  println(this); // the Xholon script node, or Window
  println($this);
  const left = ((two, parent, $this) => {
    println(one + " " + two); // un deux
    println(parent)
    const left = ((three, $this) => {
      println(one + " " + two + " " + three); // un deux trois
      println(this); // the Xholon script node, or Window
      println($this);
      if (this.xhc) {println(this.parent());} // the parent of the Xholon script node
    })
    const right = ((four, $this) => {
      println(one + " " + two + " " + four); // un deux 
      println(this); // the Xholon script node, or Window
      println($this);
      if (this.xhc) {println(this.parent());} // the parent of the Xholon script node
    })
    left("trois", left)
    right("quatre", right)
  })
  left("deux", $this, left)
})
root("Nested functions", "un", this.root)
console.log(root)

// this works in Xholon and in Dev Tools
(() => {
var root = (function(title, one, $this, parent) {
  const println = str => this.xhc ? this.println(str) : console.log(str);
  println(title); // Nested functions
  println(one); // un
  //println(this); // the Xholon script node, or Window
  println($this);
  const left = ((two, $this, parent) => {
    println(one + " " + two); // un deux
    println(parent);
    println($this);
    const left = ((three, $this, parent) => {
      println(one + " " + two + " " + three); // un deux trois
      //println(this); // the Xholon script node, or Window
      println($this);
      //if (this.xhc) {println(this.parent());} // the parent of the Xholon script node
    })
    const right = ((four, $this, parent) => {
      println(one + " " + two + " " + four); // un deux 
      //println(this); // the Xholon script node, or Window
      println($this);
      //if (this.xhc) {println(this.parent());} // the parent of the Xholon script node
    })
    left("trois", left, $this)
    right("quatre", right, $this)
  })
  left("deux", left, $this)
})
root("Nested functions", "un", root, null)
console.log(root)
})()

// 
(() => {
var root = (function(title, one, $this, parent) {
  const println = str => this.xhc ? this.println(str) : console.log(str);
  const left = ((two, $this, parent) => {
    //const left = ((three, $this, parent) => {})
    //const right = ((four, $this, parent) => {})
    //left("trois", left, $this)
    //right("quatre", right, $this)
    left: (three, $this, parent) => {println(three;)},
    right: (four, $this, parent) => {println(four;)}
  })
  left("deux", left, $this)
})
root("Nested functions", "un", root, null)
console.log(root)
})()

// https://stackoverflow.com/questions/111102/how-do-javascript-closures-work?rq=1
// 1 (my version)
(() => {
  const namespace = {};
  (n => {
    const numbers = []
    const format = n => Math.trunc(n)
    const tick = () => numbers.push(Math.random() * 100)
    const toString = () => numbers.map(format)
    n.counter = {tick,toString}
  })(namespace)
  const counter = namespace.counter
  counter.tick()
  counter.tick()
  console.log(counter.toString())
})()

// 2
function createObject() {
  let x = 42;
  return {
    log() { console.log(x) },
    increment() { x++ },
    update(value) { x = value }
  }
}
const o = createObject()
o.increment()
o.log() // 43
o.update(5)
o.log() // 5
const p = createObject()
p.log() // 42

// 3
function make_calculator() {
  var n = 0; // this calculator stores a single number n
  return {
    add: function(a) {
      n += a;
      return n;
    },
    multiply: function(a) {
      n *= a;
      return n;
    }
  };
}

first_calculator = make_calculator();
second_calculator = make_calculator();

first_calculator.add(3); // returns 3
second_calculator.add(400); // returns 400

first_calculator.multiply(11); // returns 33
second_calculator.multiply(10); // returns 4000

// https://www.freecodecamp.org/news/var-let-and-const-whats-the-difference/
(() => {
  const memoize = (fn) => {
    const cache = {};
    return (num) => {
      if (num in cache) {
        console.log('Fetching from cache for ' + num);
        return cache[num];
      }
      else {
        console.log('Calculating result for ' + num);
        let output = fn(num);
        cache[num] = output;
        return output;
      }
    }
  }
  factorial = memoize((x) => x === 0 ? 1 : x * factorial(x - 1));
  console.log(factorial(5));
  console.log(factorial(60));
  console.log(factorial(70));
  console.log(factorial(50));
})()

// Xholon blockly Avatar - this works
((roleName) => {
  const avastr =
`<Avatar startPos="append" end="vanish" roleName="${roleName}">
  <Attribute_String>
    build School role Hogwarts;
    enter Hogwarts;
    build Classroom role Potions;
    build Classroom role "Defence Against the Dark Arts";
    look;
    enter *classroom;
    look;
    exit;
    enter Defence;
    look;
  </Attribute_String>
</Avatar>`
  //console.log(avastr)
  //console.log(this)
  //console.log(this.parent())
  this.parent().append(avastr)
})("Harry")


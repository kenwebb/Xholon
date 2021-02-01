/*
  ct4sciences.js
  
  Ken Webb, 28 January 2021
  based on book Category Theory for the Sciences, by David I. Spivak, 2014
  
  My basic premise:
  - Category Theory is too difficult for non-mathematicians to learn directly
  - but it can be learned using a two-step process:
    1. learn JavaScript, especially its functional programming features
    2. read and write annotated JavaScript code to confirm what each section in the book means
  
  TODO
  - use the unicode prime symbol instead of 1 2 etc.  NO
    https://en.wikipedia.org/wiki/Prime_(symbol)
    ′  ″  ‴  ⁗
*/

// section 2.1 (p.9)
(() => {
  const is_put_into = (thing, bin) => bin.concat(thing)
  console.log(is_put_into("a thing", []))
})()

// section 2.1.1
(() => {
  const PENDULUM1 = "Penny"
  const PENDULUM2 = "Pendu"
  const PENDULUM3 = "Piney"
  const SET_OF_PENDULUMS = [PENDULUM1, PENDULUM2, PENDULUM3]
  const PAIR_OF_NAMED_ELEMENTS1 = [PENDULUM1, PENDULUM2]
  const PAIR_OF_NAMED_ELEMENTS2 = [PENDULUM1, PENDULUM1]
  const is_same = pair => pair[0] === pair[1]
  const is_pendulum = thing => thing.startsWith("P")
  const find_pendulums = things => things.reduce((acc, curr) => is_pendulum(curr) ? acc.concat(curr) : acc, [])
  console.log(is_same(PAIR_OF_NAMED_ELEMENTS1))
  console.log(is_same(PAIR_OF_NAMED_ELEMENTS2))
  console.log(SET_OF_PENDULUMS.concat("I am not a pendulum").map(item => is_pendulum(item)))
  console.log(find_pendulums("ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("")))
  console.log(find_pendulums(["Aardvark", "Baboon", "Cat", "Pangolin", "Zebra", "Panther"]))
})()

// section 2.1.2
// f: X -> Y is a function from set X to set Y, that sends each element in X to an element of Y
// Each element of set Y contains a count of how many elements in set X point to the element in Y.
// For example, the word "winding" in set X points to element 7 in set Y, because "winding" is seven letters long.
//   X            Y
//   "winding" -> 6
//   "winding" -> "******"
//   "winding" -> "winding winding waiting winding waiting waiting "
//   "winding" -> Y["winding".length]
(() => {
  const X = `
The long and winding road that leads to your door will never disappear
I've seen that road before
It always leads me here lead me to your door
The wild and windy night that the rain washed away
Has left a pool of tears crying for the day
Why leave me standing here let me know the way
Many times I've been alone and many times I've cried
Any way you'll never know the many ways I've tried
And still they lead me back to the long winding road
You left me standing here a long long time ago
Don't leave me waiting here lead me to your door
But still they lead me back to the long winding road
You left me standing here a long long time ago
Don't keep me waiting here Don't keep me waiting lead me to your door
`.trim().split(/\s+/g)
  
  const Y_SIZE = 10
  const Y1 = new Array(Y_SIZE).fill(0)
  const Y2 = new Array(Y_SIZE).fill("")
  const Y3 = new Array(Y_SIZE).fill("")
  
  const f1 = (acc, curr) => {acc[curr.length]++; return acc;}
  const f2 = (acc, curr, filler) => {acc[curr.length] += curr + filler; return acc;}
  const f3 = (acc, curr, filler) => {acc[curr.length] += filler; return acc;}
  const f = (x, y, funk, filler) => x.reduce((acc, curr) => funk(acc, curr, filler), y)
  
  console.log(f(X, Y1, f1)) // [ 0, 3, 21, 25, 61, 19, 5, 6, 3, 1 ]
  console.log(f(X, Y2, f2, " "))
  console.log(f(X, Y3, f3, "*")) // builds a histogram
})()

// section 2.2 - application 2.2.1.1
// see my DNA -> RNA -> AA model in biocell.js

// section 2.2 - application 2.2.1.2
// square diagram
/*
f: S -> A  text -> list of lists of words of each length
g: S -> B  text -> list of histogram entries
f_: A -> P list of lists of words -> list of lengths
g_: B -> P list of histogram entries -> list of lengths

S -> A

|    |
v    v

B -> P
*/
(() => {
  const S = `
The long and winding road that leads to your door will never disappear
I've seen that road before
It always leads me here lead me to your door
The wild and windy night that the rain washed away
Has left a pool of tears crying for the day
Why leave me standing here let me know the way
Many times I've been alone and many times I've cried
Any way you'll never know the many ways I've tried
And still they lead me back to the long winding road
You left me standing here a long long time ago
Don't leave me waiting here lead me to your door
But still they lead me back to the long winding road
You left me standing here a long long time ago
Don't keep me waiting here Don't keep me waiting lead me to your door
`.trim().split(/\s+/g)
  
  const Y_SIZE = 10
  const A = new Array(Y_SIZE).fill("")
  const B = new Array(Y_SIZE).fill("")
  const P = new Array(Y_SIZE).fill(0)
  
  const f = (acc, curr, index, filler) => {acc[curr.length] += curr + filler; return acc;}
  const g = (acc, curr, index, filler) => {acc[curr.length] += filler; return acc;}
  const f_ = (acc, curr, index) => {acc[index] = curr.split(" ").length - 1; return acc;}
  const g_ = (acc, curr, index) => {acc[index] = curr.length; return acc;}
  const funk = (x, y, funk, filler) => x.reduce((acc, curr, index) => funk(acc, curr, index, filler), y)
  
  console.log(funk(S, A, f, " "))
  console.log(funk(S, B, g, "*")) // builds a histogram
  console.log(funk(A, P, f_))
  console.log(funk(B, P, g_))
  
  // TODO compose each pair of functions, and then compare the two results
  const compose = (...fns) => x => fns.reduceRight((y, f) => f(y), x)
  //const ff = arr => 
  //const P1 = compose(gg, ff(S)
})()





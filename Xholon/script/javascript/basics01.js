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
  const JSO = {one: "Un", two: "Deux", three: {four: "Quatre"}}
  const ARR = [JSO, JSO.one, JSO.two, JSO.three, JSO.three.four]
  console.log(JSO.three)
  console.log(ARR[3])
  const JSONN = JSON.stringify(JSO)
  console.log(JSONN)
  const PATH = ["three", "four"]
  const query = (obj, path) => path.reduce((objj, item) => objj[item], obj)
  console.log(query(JSO, PATH))
})("nested JSO or JSON to [Object]")

